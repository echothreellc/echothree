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

package com.echothree.model.control.order.common.transfer;

import com.echothree.model.control.sequence.common.transfer.SequenceTypeTransfer;
import com.echothree.model.control.workflow.common.transfer.WorkflowEntranceTransfer;
import com.echothree.model.control.workflow.common.transfer.WorkflowTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class OrderTypeTransfer
        extends BaseTransfer {
    
    private String orderTypeName;
    private SequenceTypeTransfer orderSequenceType;
    private WorkflowTransfer orderWorkflow;
    private WorkflowEntranceTransfer orderWorkflowEntrance;
    private Boolean isDefault;
    private Integer sortOrder;
    private String description;
    
    /** Creates a new instance of OrderTypeTransfer */
    public OrderTypeTransfer(String orderTypeName, SequenceTypeTransfer orderSequenceType,
            WorkflowTransfer orderWorkflow, WorkflowEntranceTransfer orderWorkflowEntrance, Boolean isDefault, Integer sortOrder,
            String description) {
        this.orderTypeName = orderTypeName;
        this.orderSequenceType = orderSequenceType;
        this.orderWorkflow = orderWorkflow;
        this.orderWorkflowEntrance = orderWorkflowEntrance;
        this.isDefault = isDefault;
        this.sortOrder = sortOrder;
        this.description = description;
    }

    public String getOrderTypeName() {
        return orderTypeName;
    }

    public void setOrderTypeName(String orderTypeName) {
        this.orderTypeName = orderTypeName;
    }

    public SequenceTypeTransfer getOrderSequenceType() {
        return orderSequenceType;
    }

    public void setOrderSequenceType(SequenceTypeTransfer orderSequenceType) {
        this.orderSequenceType = orderSequenceType;
    }

    public WorkflowTransfer getOrderWorkflow() {
        return orderWorkflow;
    }

    public void setOrderWorkflow(WorkflowTransfer orderWorkflow) {
        this.orderWorkflow = orderWorkflow;
    }

    public WorkflowEntranceTransfer getOrderWorkflowEntrance() {
        return orderWorkflowEntrance;
    }

    public void setOrderWorkflowEntrance(WorkflowEntranceTransfer orderWorkflowEntrance) {
        this.orderWorkflowEntrance = orderWorkflowEntrance;
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
