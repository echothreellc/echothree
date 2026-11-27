// --------------------------------------------------------------------------------
// Copyright 2002-2026 Echo Three, LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
// --------------------------------------------------------------------------------

package com.echothree.ui.cli.dataloader.util.data.handler.comment;

import com.echothree.control.user.comment.common.CommentUtil;
import com.echothree.control.user.comment.common.CommentService;
import com.echothree.control.user.comment.common.form.CommentFormFactory;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class CommentTypeHandler
        extends BaseHandler {

    CommentService commentService;
    String componentVendorName;
    String entityTypeName;
    String commentTypeName;
    
    /** Creates a new instance of CommentTypeHandler */
    public CommentTypeHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String componentVendorName,
            String entityTypeName, String commentTypeName)
            throws SAXException {
        super(initialDataParser, parentHandler);
        
        commentService = lookupService(CommentService.class, CommentUtil::getHome);
        
        this.componentVendorName = componentVendorName;
        this.entityTypeName = entityTypeName;
        this.commentTypeName = commentTypeName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
    throws SAXException {
        if(localName.equals("commentTypeDescription")) {
            var commandForm = CommentFormFactory.getCreateCommentTypeDescriptionForm();

            commandForm.setComponentVendorName(componentVendorName);
            commandForm.setEntityTypeName(entityTypeName);
            commandForm.setCommentTypeName(commentTypeName);
            commandForm.set(getAttrsMap(attrs));

            checkCommandResult(commandForm, commentService.createCommentTypeDescription(initialDataParser.getUserVisit(), commandForm));
        } else if(localName.equals("commentUsageType")) {
            var commandForm = CommentFormFactory.getCreateCommentUsageTypeForm();

            commandForm.setComponentVendorName(componentVendorName);
            commandForm.setEntityTypeName(entityTypeName);
            commandForm.setCommentTypeName(commentTypeName);
            commandForm.set(getAttrsMap(attrs));

            checkCommandResult(commandForm, commentService.createCommentUsageType(initialDataParser.getUserVisit(), commandForm));

            initialDataParser.pushHandler(new CommentUsageTypeHandler(initialDataParser, this, componentVendorName,
                    entityTypeName, commentTypeName, commandForm.getCommentUsageTypeName()));
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
    throws SAXException {
        if(localName.equals("commentType")) {
            initialDataParser.popHandler();
        }
    }
    
}
