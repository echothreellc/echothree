// --------------------------------------------------------------------------------
// Copyright 2002-2023 Echo Three, LLC
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
import com.echothree.model.control.core.common.transfer.EntityTypeTransfer;
import com.echothree.model.control.core.common.transfer.MimeTypeUsageTypeTransfer;
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.control.sequence.common.transfer.SequenceTransfer;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.control.workflow.common.transfer.WorkflowEntranceTransfer;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.comment.server.entity.CommentType;
import com.echothree.model.data.comment.server.entity.CommentTypeDetail;
import com.echothree.model.data.core.server.entity.MimeTypeUsageType;
import com.echothree.model.data.sequence.server.entity.Sequence;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.workflow.server.entity.WorkflowEntrance;
import com.echothree.util.server.persistence.Session;

public class CommentTypeTransferCache
        extends BaseCommentTransferCache<CommentType, CommentTypeTransfer> {
    
    CoreControl coreControl = Session.getModelController(CoreControl.class);
    SequenceControl sequenceControl = Session.getModelController(SequenceControl.class);
    WorkflowControl workflowControl = Session.getModelController(WorkflowControl.class);
    
    /** Creates a new instance of CommentTypeTransferCache */
    public CommentTypeTransferCache(UserVisit userVisit, CommentControl commentControl) {
        super(userVisit, commentControl);
        
        setIncludeEntityInstance(true);
    }
    
    public CommentTypeTransfer getCommentTypeTransfer(CommentType commentType) {
        CommentTypeTransfer commentTypeTransfer = get(commentType);
        
        if(commentTypeTransfer == null) {
            CommentTypeDetail commentTypeDetail = commentType.getLastDetail();
            EntityTypeTransfer entityTypeTransfer = coreControl.getEntityTypeTransfer(userVisit, commentTypeDetail.getEntityType());
            String commentTypeName = commentTypeDetail.getCommentTypeName();
            Sequence commentSequence = commentTypeDetail.getCommentSequence();
            SequenceTransfer commentSequenceTransfer = commentSequence == null? null: sequenceControl.getSequenceTransfer(userVisit, commentSequence);
            WorkflowEntrance workflowEntrance = commentTypeDetail.getWorkflowEntrance();
            WorkflowEntranceTransfer workflowEntranceTransfer = workflowEntrance == null? null: workflowControl.getWorkflowEntranceTransfer(userVisit, workflowEntrance);
            MimeTypeUsageType mimeTypeUsageType = commentTypeDetail.getMimeTypeUsageType();
            MimeTypeUsageTypeTransfer mimeTypeUsageTypeTransfer = mimeTypeUsageType == null? null: coreControl.getMimeTypeUsageTypeTransfer(userVisit, mimeTypeUsageType);
            Integer sortOrder = commentTypeDetail.getSortOrder();
            String description = commentControl.getBestCommentTypeDescription(commentType, getLanguage());
            
            commentTypeTransfer = new CommentTypeTransfer(entityTypeTransfer, commentTypeName, commentSequenceTransfer,
                    workflowEntranceTransfer, mimeTypeUsageTypeTransfer, sortOrder, description);
            put(commentType, commentTypeTransfer);
        }
        
        return commentTypeTransfer;
    }
    
}
