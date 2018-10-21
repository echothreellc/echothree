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

package com.echothree.model.control.inventory.remote.transfer;

import com.echothree.model.control.sequence.remote.transfer.SequenceTypeTransfer;
import com.echothree.model.control.workflow.remote.transfer.WorkflowEntranceTransfer;
import com.echothree.model.control.workflow.remote.transfer.WorkflowTransfer;
import com.echothree.util.remote.transfer.BaseTransfer;

public class LotTypeTransfer
        extends BaseTransfer {
    
    private String lotTypeName;
    private LotTypeTransfer parentLotType;
    private SequenceTypeTransfer lotSequenceType;
    private WorkflowTransfer lotWorkflow;
    private WorkflowEntranceTransfer lotWorkflowEntrance;
    private Boolean isDefault;
    private Integer sortOrder;
    private String description;
    
    /** Creates a new instance of LotTypeTransfer */
    public LotTypeTransfer(String lotTypeName, LotTypeTransfer parentLotType, SequenceTypeTransfer lotSequenceType,
            WorkflowTransfer lotWorkflow, WorkflowEntranceTransfer lotWorkflowEntrance, Boolean isDefault, Integer sortOrder,
            String description) {
        this.lotTypeName = lotTypeName;
        this.parentLotType = parentLotType;
        this.lotSequenceType = lotSequenceType;
        this.lotWorkflow = lotWorkflow;
        this.lotWorkflowEntrance = lotWorkflowEntrance;
        this.isDefault = isDefault;
        this.sortOrder = sortOrder;
        this.description = description;
    }

    public String getLotTypeName() {
        return lotTypeName;
    }

    public void setLotTypeName(String lotTypeName) {
        this.lotTypeName = lotTypeName;
    }

    public LotTypeTransfer getParentLotType() {
        return parentLotType;
    }

    public void setParentLotType(LotTypeTransfer parentLotType) {
        this.parentLotType = parentLotType;
    }

    public SequenceTypeTransfer getLotSequenceType() {
        return lotSequenceType;
    }

    public void setLotSequenceType(SequenceTypeTransfer lotSequenceType) {
        this.lotSequenceType = lotSequenceType;
    }

    public WorkflowTransfer getLotWorkflow() {
        return lotWorkflow;
    }

    public void setLotWorkflow(WorkflowTransfer lotWorkflow) {
        this.lotWorkflow = lotWorkflow;
    }

    public WorkflowEntranceTransfer getLotWorkflowEntrance() {
        return lotWorkflowEntrance;
    }

    public void setLotWorkflowEntrance(WorkflowEntranceTransfer lotWorkflowEntrance) {
        this.lotWorkflowEntrance = lotWorkflowEntrance;
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
