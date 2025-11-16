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

import com.echothree.model.control.comment.common.transfer.CommentTypeTransfer;
import com.echothree.model.control.comment.server.control.CommentControl;
import com.echothree.model.control.core.server.control.EntityTypeControl;
import com.echothree.model.control.core.server.control.MimeTypeControl;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.comment.server.entity.CommentType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class CommentTypeTransferCache
        extends BaseCommentTransferCache<CommentType, CommentTypeTransfer> {

    CommentControl commentControl = Session.getModelController(CommentControl.class);
    EntityTypeControl entityTypeControl = Session.getModelController(EntityTypeControl.class);
    MimeTypeControl mimeTypeControl = Session.getModelController(MimeTypeControl.class);
    SequenceControl sequenceControl = Session.getModelController(SequenceControl.class);
    WorkflowControl workflowControl = Session.getModelController(WorkflowControl.class);
    
    /** Creates a new instance of CommentTypeTransferCache */
    public CommentTypeTransferCache() {
        super();
        
        setIncludeEntityInstance(true);
    }
    
    public CommentTypeTransfer getCommentTypeTransfer(UserVisit userVisit, CommentType commentType) {
        var commentTypeTransfer = get(commentType);
        
        if(commentTypeTransfer == null) {
            var commentTypeDetail = commentType.getLastDetail();
            var entityTypeTransfer = entityTypeControl.getEntityTypeTransfer(userVisit, commentTypeDetail.getEntityType());
            var commentTypeName = commentTypeDetail.getCommentTypeName();
            var commentSequence = commentTypeDetail.getCommentSequence();
            var commentSequenceTransfer = commentSequence == null? null: sequenceControl.getSequenceTransfer(userVisit, commentSequence);
            var workflowEntrance = commentTypeDetail.getWorkflowEntrance();
            var workflowEntranceTransfer = workflowEntrance == null? null: workflowControl.getWorkflowEntranceTransfer(userVisit, workflowEntrance);
            var mimeTypeUsageType = commentTypeDetail.getMimeTypeUsageType();
            var mimeTypeUsageTypeTransfer = mimeTypeUsageType == null? null: mimeTypeControl.getMimeTypeUsageTypeTransfer(userVisit, mimeTypeUsageType);
            var sortOrder = commentTypeDetail.getSortOrder();
            var description = commentControl.getBestCommentTypeDescription(commentType, getLanguage(userVisit));
            
            commentTypeTransfer = new CommentTypeTransfer(entityTypeTransfer, commentTypeName, commentSequenceTransfer,
                    workflowEntranceTransfer, mimeTypeUsageTypeTransfer, sortOrder, description);
            put(userVisit, commentType, commentTypeTransfer);
        }
        
        return commentTypeTransfer;
    }
    
}
