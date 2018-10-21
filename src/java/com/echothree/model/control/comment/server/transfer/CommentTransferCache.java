// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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
import com.echothree.model.control.comment.remote.transfer.CommentTransfer;
import com.echothree.model.control.comment.server.CommentControl;
import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.workflow.server.WorkflowControl;
import com.echothree.model.data.comment.server.entity.Comment;
import com.echothree.model.data.comment.server.entity.CommentBlob;
import com.echothree.model.data.comment.server.entity.CommentClob;
import com.echothree.model.data.comment.server.entity.CommentDetail;
import com.echothree.model.data.comment.server.entity.CommentString;
import com.echothree.model.data.comment.server.entity.CommentType;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.MimeType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.workflow.server.entity.Workflow;
import com.echothree.model.data.workflow.server.entity.WorkflowEntrance;
import com.echothree.util.remote.transfer.ListWrapper;
import com.echothree.util.server.persistence.Session;
import java.util.Set;

public class CommentTransferCache
        extends BaseCommentTransferCache<Comment, CommentTransfer> {
    
    CoreControl coreControl;
    PartyControl partyControl;
    WorkflowControl workflowControl;
    
    boolean includeBlob;
    boolean includeClob;
    boolean includeString;
    boolean includeCommentUsages;
    boolean includeWorkflowStep;
    
    /** Creates a new instance of CommentTransferCache */
    public CommentTransferCache(UserVisit userVisit, CommentControl commentControl) {
        super(userVisit, commentControl);
        
        coreControl = (CoreControl)Session.getModelController(CoreControl.class);
        partyControl = (PartyControl)Session.getModelController(PartyControl.class);
        workflowControl = (WorkflowControl)Session.getModelController(WorkflowControl.class);
        
        Set<String> options = session.getOptions();
        if(options != null) {
            includeBlob = options.contains(CommentOptions.CommentIncludeBlob);
            includeClob = options.contains(CommentOptions.CommentIncludeClob);
            includeString = options.contains(CommentOptions.CommentIncludeString);
            includeCommentUsages = options.contains(CommentOptions.CommentIncludeCommentUsages);
            includeWorkflowStep = options.contains(CommentOptions.CommentIncludeWorkflowStep);
        }
        
        setIncludeEntityInstance(true);
    }
    
    public CommentTransfer getCommentTransfer(Comment comment) {
        CommentTransfer commentTransfer = get(comment);
        
        if(commentTransfer == null) {
            CommentDetail commentDetail = comment.getLastDetail();
            CommentType commentType = commentDetail.getCommentType();
            EntityInstance entityInstance = coreControl.getEntityInstanceByBasePK(comment.getPrimaryKey());
            
            commentTransfer = new CommentTransfer();
            put(comment, commentTransfer, entityInstance);
            
            commentTransfer.setCommentName(commentDetail.getCommentName());
            commentTransfer.setCommentType(commentControl.getCommentTypeTransfer(userVisit, commentType));
            commentTransfer.setCommentedEntityInstance(coreControl.getEntityInstanceTransfer(userVisit, commentDetail.getCommentedEntityInstance(), false, false, false, false, false));
            commentTransfer.setCommentedByEntityInstance(coreControl.getEntityInstanceTransfer(userVisit, commentDetail.getCommentedByEntityInstance(), false, false, false, false, false));
            commentTransfer.setLanguage(partyControl.getLanguageTransfer(userVisit, commentDetail.getLanguage()));
            commentTransfer.setDescription(commentDetail.getDescription());
            MimeType mimeType = commentDetail.getMimeType();
            commentTransfer.setMimeType(mimeType == null? null: coreControl.getMimeTypeTransfer(userVisit, mimeType));
            commentTransfer.setEntityInstance(coreControl.getEntityInstanceTransfer(userVisit, entityInstance, false, false, false, false, false));
            
            if(includeString) {
                CommentString commentString = commentControl.getCommentString(comment);
                commentTransfer.setString(commentString == null? null: commentString.getString());
            }
            
            if(includeBlob) {
                CommentBlob commentBlob = commentControl.getCommentBlob(comment);
                commentTransfer.setBlob(commentBlob == null? null: commentBlob.getBlob());
            }
            
            if(includeClob) {
                CommentClob commentClob = commentControl.getCommentClob(comment);
                commentTransfer.setClob(commentClob == null? null: commentClob.getClob());
            }
            
            if(includeCommentUsages) {
                commentTransfer.setCommentUsages(new ListWrapper<>(commentControl.getCommentUsageTransfersByComment(userVisit, comment)));
            }
            
            if(includeWorkflowStep) {
                WorkflowEntrance workflowEntrance = commentType.getLastDetail().getWorkflowEntrance();

                if(workflowEntrance != null) {
                    Workflow workflow = workflowEntrance.getLastDetail().getWorkflow();

                    commentTransfer.setCommentStatus(workflowControl.getWorkflowEntityStatusTransferByEntityInstance(userVisit, workflow, entityInstance));
                }
            }
        }
        
        return commentTransfer;
    }
    
}
