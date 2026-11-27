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
import java.util.HashSet;
import java.util.Set;
import java.util.Map;
import java.util.Set;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class CommentsHandler
        extends BaseHandler {

    CommentService commentService;
    String entityRef;
    
    boolean inComment = false;
    private Map<String, Object> commentAttrs;
    char []clobComment = null;
    Set<String> commentUsages = new HashSet<>();
    
    /** Creates a new instance of CommentsHandler */
    public CommentsHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String entityRef)
            throws SAXException {
        super(initialDataParser, parentHandler);
        
        commentService = lookupService(CommentService.class, CommentUtil::getHome);
        
        this.entityRef = entityRef;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
    throws SAXException {
        if(localName.equals("comment")) {
            commentAttrs = getAttrsMap(attrs);
            commentAttrs.keySet().removeAll(Set.of("EntityRef", "ClobComment"));
            
            inComment = true;
        } else if(inComment && localName.equals("commentUsage")) {
            var commentUsageTypeName = attrs.getValue("commentUsageTypeName");

            if(commentUsageTypeName != null) {
                commentUsages.add(commentUsageTypeName);
            }
        }
    }
    
    @Override
    public void characters(char ch[], int start, int length)
    throws SAXException {
        if(inComment) {
            var oldLength = clobComment != null? clobComment.length: 0;
            var newClob = new char[oldLength + length];
            
            if(clobComment != null)
                System.arraycopy(clobComment, 0, newClob, 0, clobComment.length);
            System.arraycopy(ch, start, newClob, oldLength, length);
            clobComment = newClob;
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
    throws SAXException {
        if(localName.equals("comment")) {
            var form = CommentFormFactory.getCreateCommentForm();
            
            form.setEntityRef(entityRef);
            form.setClobComment(clobComment == null? null: new String(clobComment));
            form.set(commentAttrs);

            var commandResult = commentService.createComment(initialDataParser.getUserVisit(), form);
            checkCommandResult(form, commandResult);
            
            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = executionResult.getResult();
                var commentName = result.getCommentName();
                
                for(var commentUsageTypeName : commentUsages) {
                    var createCommentUsageForm = CommentFormFactory.getCreateCommentUsageForm();
                    
                    createCommentUsageForm.setCommentName(commentName);
                    createCommentUsageForm.setCommentUsageTypeName(commentUsageTypeName);
                    
                    checkCommandResult(createCommentUsageForm,
                            commentService.createCommentUsage(initialDataParser.getUserVisit(), createCommentUsageForm));
                }
            }
            
            inComment = false;
            commentAttrs = null;
            clobComment = null;
            commentUsages.clear();
        } else if(localName.equals("comments")) {
            initialDataParser.popHandler();
        }
    }
}
