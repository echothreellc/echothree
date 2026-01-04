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

package com.echothree.model.control.comment.common.transfer;

import com.echothree.model.control.core.common.transfer.EntityTypeTransfer;
import com.echothree.model.control.core.common.transfer.MimeTypeUsageTypeTransfer;
import com.echothree.model.control.sequence.common.transfer.SequenceTransfer;
import com.echothree.model.control.workflow.common.transfer.WorkflowEntranceTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class CommentTypeTransfer
        extends BaseTransfer {
    
    private EntityTypeTransfer entityType;
    private String commentTypeName;
    private SequenceTransfer commentSequence;
    private WorkflowEntranceTransfer workflowEntrance;
    private MimeTypeUsageTypeTransfer mimeTypeUsageType;
    private Integer sortOrder;
    private String description;
    
    /** Creates a new instance of CommentTypeTransfer */
    public CommentTypeTransfer(EntityTypeTransfer entityType, String commentTypeName, SequenceTransfer commentSequence,
            WorkflowEntranceTransfer workflowEntrance, MimeTypeUsageTypeTransfer mimeTypeUsageType, Integer sortOrder, String description) {
        this.entityType = entityType;
        this.commentTypeName = commentTypeName;
        this.commentSequence = commentSequence;
        this.workflowEntrance = workflowEntrance;
        this.mimeTypeUsageType = mimeTypeUsageType;
        this.sortOrder = sortOrder;
        this.description = description;
    }
    
    public EntityTypeTransfer getEntityType() {
        return entityType;
    }
    
    public void setEntityType(EntityTypeTransfer entityType) {
        this.entityType = entityType;
    }
    
    public String getCommentTypeName() {
        return commentTypeName;
    }
    
    public void setCommentTypeName(String commentTypeName) {
        this.commentTypeName = commentTypeName;
    }
    
    public SequenceTransfer getCommentSequence() {
        return commentSequence;
    }
    
    public void setCommentSequence(SequenceTransfer commentSequence) {
        this.commentSequence = commentSequence;
    }
    
    public WorkflowEntranceTransfer getWorkflowEntrance() {
        return workflowEntrance;
    }
    
    public void setWorkflowEntrance(WorkflowEntranceTransfer workflowEntrance) {
        this.workflowEntrance = workflowEntrance;
    }
    
    public MimeTypeUsageTypeTransfer getMimeTypeUsageType() {
        return mimeTypeUsageType;
    }
    
    public void setMimeTypeUsageType(MimeTypeUsageTypeTransfer mimeTypeUsageType) {
        this.mimeTypeUsageType = mimeTypeUsageType;
    }
    
    public Integer getSortOrder() {
        return sortOrder;
    }
    
    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
}
