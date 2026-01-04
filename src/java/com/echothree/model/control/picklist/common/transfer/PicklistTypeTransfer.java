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

package com.echothree.model.control.picklist.common.transfer;

import com.echothree.model.control.sequence.common.transfer.SequenceTypeTransfer;
import com.echothree.model.control.workflow.common.transfer.WorkflowEntranceTransfer;
import com.echothree.model.control.workflow.common.transfer.WorkflowTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class PicklistTypeTransfer
        extends BaseTransfer {
    
    private String picklistTypeName;
    private PicklistTypeTransfer parentPicklistType;
    private SequenceTypeTransfer picklistSequenceType;
    private WorkflowTransfer picklistWorkflow;
    private WorkflowEntranceTransfer picklistWorkflowEntrance;
    private Boolean isDefault;
    private Integer sortOrder;
    private String description;
    
    /** Creates a new instance of PicklistTypeTransfer */
    public PicklistTypeTransfer(String picklistTypeName, PicklistTypeTransfer parentPicklistType, SequenceTypeTransfer picklistSequenceType,
            WorkflowTransfer picklistWorkflow, WorkflowEntranceTransfer picklistWorkflowEntrance, Boolean isDefault, Integer sortOrder,
            String description) {
        this.picklistTypeName = picklistTypeName;
        this.parentPicklistType = parentPicklistType;
        this.picklistSequenceType = picklistSequenceType;
        this.picklistWorkflow = picklistWorkflow;
        this.picklistWorkflowEntrance = picklistWorkflowEntrance;
        this.isDefault = isDefault;
        this.sortOrder = sortOrder;
        this.description = description;
    }

    public String getPicklistTypeName() {
        return picklistTypeName;
    }

    public void setPicklistTypeName(String picklistTypeName) {
        this.picklistTypeName = picklistTypeName;
    }

    public PicklistTypeTransfer getParentPicklistType() {
        return parentPicklistType;
    }

    public void setParentPicklistType(PicklistTypeTransfer parentPicklistType) {
        this.parentPicklistType = parentPicklistType;
    }

    public SequenceTypeTransfer getPicklistSequenceType() {
        return picklistSequenceType;
    }

    public void setPicklistSequenceType(SequenceTypeTransfer picklistSequenceType) {
        this.picklistSequenceType = picklistSequenceType;
    }

    public WorkflowTransfer getPicklistWorkflow() {
        return picklistWorkflow;
    }

    public void setPicklistWorkflow(WorkflowTransfer picklistWorkflow) {
        this.picklistWorkflow = picklistWorkflow;
    }

    public WorkflowEntranceTransfer getPicklistWorkflowEntrance() {
        return picklistWorkflowEntrance;
    }

    public void setPicklistWorkflowEntrance(WorkflowEntranceTransfer picklistWorkflowEntrance) {
        this.picklistWorkflowEntrance = picklistWorkflowEntrance;
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
