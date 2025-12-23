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
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class CommentUsageTypeHandler
        extends BaseHandler {
    CommentService commentService;
    String componentVendorName;
    String entityTypeName;
    String commentTypeName;
    String commentUsageTypeName;
    
    /** Creates a new instance of CommentUsageTypeHandler */
    public CommentUsageTypeHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String componentVendorName,
            String entityTypeName,
    String commentTypeName, String commentUsageTypeName) {
        super(initialDataParser, parentHandler);
        
        try {
            commentService = CommentUtil.getHome();
        } catch (NamingException ne) {
            // TODO: Handle Exception
        }
        
        this.componentVendorName = componentVendorName;
        this.entityTypeName = entityTypeName;
        this.commentTypeName = commentTypeName;
        this.commentUsageTypeName = commentUsageTypeName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
    throws SAXException {
        if(localName.equals("commentUsageTypeDescription")) {
            String languageIsoName = null;
            String description = null;

            var count = attrs.getLength();
            for(var i = 0; i < count; i++) {
                if(attrs.getQName(i).equals("languageIsoName"))
                    languageIsoName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("description"))
                    description = attrs.getValue(i);
            }
            
            try {
                var form = CommentFormFactory.getCreateCommentUsageTypeDescriptionForm();
                
                form.setComponentVendorName(componentVendorName);
                form.setEntityTypeName(entityTypeName);
                form.setCommentTypeName(commentTypeName);
                form.setCommentUsageTypeName(commentUsageTypeName);
                form.setLanguageIsoName(languageIsoName);
                form.setDescription(description);
                
                commentService.createCommentUsageTypeDescription(initialDataParser.getUserVisit(), form);
            } catch (Exception e) {
                throw new SAXException(e);
            }
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
    throws SAXException {
        if(localName.equals("commentUsageType")) {
            initialDataParser.popHandler();
        }
    }
    
}
