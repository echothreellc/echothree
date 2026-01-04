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

package com.echothree.model.control.batch.common.transfer;

import com.echothree.model.control.sequence.common.transfer.SequenceTypeTransfer;
import com.echothree.model.control.workflow.common.transfer.WorkflowEntranceTransfer;
import com.echothree.model.control.workflow.common.transfer.WorkflowTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class BatchTypeTransfer
        extends BaseTransfer {
    
    private String batchTypeName;
    private BatchTypeTransfer parentBatchType;
    private SequenceTypeTransfer batchSequenceType;
    private WorkflowTransfer batchWorkflow;
    private WorkflowEntranceTransfer batchWorkflowEntrance;
    private Boolean isDefault;
    private Integer sortOrder;
    private String description;
    
    /** Creates a new instance of BatchTypeTransfer */
    public BatchTypeTransfer(String batchTypeName, BatchTypeTransfer parentBatchType, SequenceTypeTransfer batchSequenceType,
            WorkflowTransfer batchWorkflow, WorkflowEntranceTransfer batchWorkflowEntrance, Boolean isDefault, Integer sortOrder,
            String description) {
        this.batchTypeName = batchTypeName;
        this.parentBatchType = parentBatchType;
        this.batchSequenceType = batchSequenceType;
        this.batchWorkflow = batchWorkflow;
        this.batchWorkflowEntrance = batchWorkflowEntrance;
        this.isDefault = isDefault;
        this.sortOrder = sortOrder;
        this.description = description;
    }

    public String getBatchTypeName() {
        return batchTypeName;
    }

    public void setBatchTypeName(String batchTypeName) {
        this.batchTypeName = batchTypeName;
    }

    public BatchTypeTransfer getParentBatchType() {
        return parentBatchType;
    }

    public void setParentBatchType(BatchTypeTransfer parentBatchType) {
        this.parentBatchType = parentBatchType;
    }

    public SequenceTypeTransfer getBatchSequenceType() {
        return batchSequenceType;
    }

    public void setBatchSequenceType(SequenceTypeTransfer batchSequenceType) {
        this.batchSequenceType = batchSequenceType;
    }

    public WorkflowTransfer getBatchWorkflow() {
        return batchWorkflow;
    }

    public void setBatchWorkflow(WorkflowTransfer batchWorkflow) {
        this.batchWorkflow = batchWorkflow;
    }

    public WorkflowEntranceTransfer getBatchWorkflowEntrance() {
        return batchWorkflowEntrance;
    }

    public void setBatchWorkflowEntrance(WorkflowEntranceTransfer batchWorkflowEntrance) {
        this.batchWorkflowEntrance = batchWorkflowEntrance;
    }

    public Boolean getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
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
