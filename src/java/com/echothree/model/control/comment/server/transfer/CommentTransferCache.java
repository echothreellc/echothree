// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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

package com.echothree.model.control.comment.server.transfer;

import com.echothree.model.control.comment.common.CommentOptions;
import com.echothree.model.control.comment.common.transfer.CommentTransfer;
import com.echothree.model.control.comment.server.control.CommentControl;
import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.control.core.server.control.MimeTypeControl;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.comment.server.entity.Comment;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.transfer.ListWrapper;
import com.echothree.util.server.persistence.Session;

public class CommentTransferCache
        extends BaseCommentTransferCache<Comment, CommentTransfer> {

    EntityInstanceControl entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
    MimeTypeControl mimeTypeControl = Session.getModelController(MimeTypeControl.class);
    PartyControl partyControl = Session.getModelController(PartyControl.class);
    WorkflowControl workflowControl = Session.getModelController(WorkflowControl.class);
    
    boolean includeBlob;
    boolean includeClob;
    boolean includeString;
    boolean includeCommentUsages;
    boolean includeWorkflowStep;
    
    /** Creates a new instance of CommentTransferCache */
    public CommentTransferCache(CommentControl commentControl) {
        super(commentControl);

        var options = session.getOptions();
        if(options != null) {
            includeBlob = options.contains(CommentOptions.CommentIncludeBlob);
            includeClob = options.contains(CommentOptions.CommentIncludeClob);
            includeString = options.contains(CommentOptions.CommentIncludeString);
            includeCommentUsages = options.contains(CommentOptions.CommentIncludeCommentUsages);
            includeWorkflowStep = options.contains(CommentOptions.CommentIncludeWorkflowStep);
        }
        
        setIncludeEntityInstance(true);
    }
    
    public CommentTransfer getCommentTransfer(UserVisit userVisit, Comment comment) {
        var commentTransfer = get(comment);
        
        if(commentTransfer == null) {
            var commentDetail = comment.getLastDetail();
            var commentType = commentDetail.getCommentType();
            var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(comment.getPrimaryKey());
            
            commentTransfer = new CommentTransfer();
            put(userVisit, comment, commentTransfer, entityInstance);
            
            commentTransfer.setCommentName(commentDetail.getCommentName());
            commentTransfer.setCommentType(commentControl.getCommentTypeTransfer(userVisit, commentType));
            commentTransfer.setCommentedEntityInstance(entityInstanceControl.getEntityInstanceTransfer(userVisit, commentDetail.getCommentedEntityInstance(), false, false, false, false));
            commentTransfer.setCommentedByEntityInstance(entityInstanceControl.getEntityInstanceTransfer(userVisit, commentDetail.getCommentedByEntityInstance(), false, false, false, false));
            commentTransfer.setLanguage(partyControl.getLanguageTransfer(userVisit, commentDetail.getLanguage()));
            commentTransfer.setDescription(commentDetail.getDescription());
            var mimeType = commentDetail.getMimeType();
            commentTransfer.setMimeType(mimeType == null? null: mimeTypeControl.getMimeTypeTransfer(userVisit, mimeType));
            commentTransfer.setEntityInstance(entityInstanceControl.getEntityInstanceTransfer(userVisit, entityInstance, false, false, false, false));
            
            if(includeString) {
                var commentString = commentControl.getCommentString(comment);
                commentTransfer.setString(commentString == null? null: commentString.getString());
            }
            
            if(includeBlob) {
                var commentBlob = commentControl.getCommentBlob(comment);
                commentTransfer.setBlob(commentBlob == null? null: commentBlob.getBlob());
            }
            
            if(includeClob) {
                var commentClob = commentControl.getCommentClob(comment);
                commentTransfer.setClob(commentClob == null? null: commentClob.getClob());
            }
            
            if(includeCommentUsages) {
                commentTransfer.setCommentUsages(new ListWrapper<>(commentControl.getCommentUsageTransfersByComment(userVisit, comment)));
            }
            
            if(includeWorkflowStep) {
                var workflowEntrance = commentType.getLastDetail().getWorkflowEntrance();

                if(workflowEntrance != null) {
                    var workflow = workflowEntrance.getLastDetail().getWorkflow();

                    commentTransfer.setCommentStatus(workflowControl.getWorkflowEntityStatusTransferByEntityInstance(userVisit, workflow, entityInstance));
                }
            }
        }
        
        return commentTransfer;
    }
    
}
