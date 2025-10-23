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

package com.echothree.model.control.inventory.common.transfer;

import com.echothree.model.control.sequence.common.transfer.SequenceTypeTransfer;
import com.echothree.model.control.workflow.common.transfer.WorkflowEntranceTransfer;
import com.echothree.model.control.workflow.common.transfer.WorkflowTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class InventoryTransactionTypeTransfer
        extends BaseTransfer {
    
    private String inventoryTransactionTypeName;
    private SequenceTypeTransfer inventoryTransactionSequenceType;
    private WorkflowTransfer inventoryTransactionWorkflow;
    private WorkflowEntranceTransfer inventoryTransactionWorkflowEntrance;
    private Boolean isDefault;
    private Integer sortOrder;
    private String description;
    
    /** Creates a new instance of InventoryTransactionTypeTransfer */
    public InventoryTransactionTypeTransfer(String inventoryTransactionTypeName, SequenceTypeTransfer inventoryTransactionSequenceType,
            WorkflowTransfer inventoryTransactionWorkflow, WorkflowEntranceTransfer inventoryTransactionWorkflowEntrance, Boolean isDefault, Integer sortOrder,
            String description) {
        this.inventoryTransactionTypeName = inventoryTransactionTypeName;
        this.inventoryTransactionSequenceType = inventoryTransactionSequenceType;
        this.inventoryTransactionWorkflow = inventoryTransactionWorkflow;
        this.inventoryTransactionWorkflowEntrance = inventoryTransactionWorkflowEntrance;
        this.isDefault = isDefault;
        this.sortOrder = sortOrder;
        this.description = description;
    }

    public String getInventoryTransactionTypeName() {
        return inventoryTransactionTypeName;
    }

    public void setInventoryTransactionTypeName(String inventoryTransactionTypeName) {
        this.inventoryTransactionTypeName = inventoryTransactionTypeName;
    }

    public SequenceTypeTransfer getInventoryTransactionSequenceType() {
        return inventoryTransactionSequenceType;
    }

    public void setInventoryTransactionSequenceType(SequenceTypeTransfer inventoryTransactionSequenceType) {
        this.inventoryTransactionSequenceType = inventoryTransactionSequenceType;
    }

    public WorkflowTransfer getInventoryTransactionWorkflow() {
        return inventoryTransactionWorkflow;
    }

    public void setInventoryTransactionWorkflow(WorkflowTransfer inventoryTransactionWorkflow) {
        this.inventoryTransactionWorkflow = inventoryTransactionWorkflow;
    }

    public WorkflowEntranceTransfer getInventoryTransactionWorkflowEntrance() {
        return inventoryTransactionWorkflowEntrance;
    }

    public void setInventoryTransactionWorkflowEntrance(WorkflowEntranceTransfer inventoryTransactionWorkflowEntrance) {
        this.inventoryTransactionWorkflowEntrance = inventoryTransactionWorkflowEntrance;
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
