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

package com.echothree.model.control.workflow.common.transfer;

import com.echothree.util.common.transfer.BaseTransfer;

public class WorkflowStepTransfer
        extends BaseTransfer {
    
    private WorkflowTransfer workflow;
    private String workflowStepName;
    private WorkflowStepTypeTransfer workflowStepType;
    private Boolean isDefault;
    private Integer sortOrder;
    private Boolean hasDestinations;
    private String description;
    
    /** Creates a new instance of WorkflowStepTransfer */
    public WorkflowStepTransfer(WorkflowTransfer workflow, String workflowStepName, WorkflowStepTypeTransfer workflowStepType,
            Boolean isDefault, Integer sortOrder, Boolean hasDestinations, String description) {
        this.workflow = workflow;
        this.workflowStepName = workflowStepName;
        this.workflowStepType = workflowStepType;
        this.isDefault = isDefault;
        this.sortOrder = sortOrder;
        this.hasDestinations = hasDestinations;
        this.description = description;
    }
    
    public WorkflowTransfer getWorkflow() {
        return workflow;
    }
    
    public void setWorkflow(WorkflowTransfer workflow) {
        this.workflow = workflow;
    }
    
    public String getWorkflowStepName() {
        return workflowStepName;
    }
    
    public void setWorkflowStepName(String workflowStepName) {
        this.workflowStepName = workflowStepName;
    }
    
    public WorkflowStepTypeTransfer getWorkflowStepType() {
        return workflowStepType;
    }
    
    public void setWorkflowStepType(WorkflowStepTypeTransfer workflowStepType) {
        this.workflowStepType = workflowStepType;
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
    
    public Boolean getHasDestinations() {
        return hasDestinations;
    }
    
    public void setHasDestinations(Boolean hasDestinations) {
        this.hasDestinations = hasDestinations;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
}
