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

package com.echothree.model.control.workrequirement.common.transfer;

import com.echothree.model.control.sequence.common.transfer.SequenceTransfer;
import com.echothree.model.control.workeffort.common.transfer.WorkEffortTypeTransfer;
import com.echothree.model.control.workflow.common.transfer.WorkflowStepTransfer;
import com.echothree.util.common.transfer.BaseTransfer;
import com.echothree.util.common.transfer.ListWrapper;

public class WorkRequirementTypeTransfer
        extends BaseTransfer {
    
    private WorkEffortTypeTransfer workEffortType;
    private String workRequirementTypeName;
    private SequenceTransfer workRequirementSequence;
    private WorkflowStepTransfer workflowStep;
    private String estimatedTimeAllowed;
    private String maximumTimeAllowed;
    private Boolean allowReassignment;
    private Integer sortOrder;
    private String description;
    
    private ListWrapper<WorkRequirementScopeTransfer> workRequirementScopes;

    /** Creates a new instance of WorkRequirementTypeTransfer */
    public WorkRequirementTypeTransfer(WorkEffortTypeTransfer workEffortType, String workRequirementTypeName, SequenceTransfer workRequirementSequence,
            WorkflowStepTransfer workflowStep, String estimatedTimeAllowed, String maximumTimeAllowed,  Boolean allowReassignment, Integer sortOrder,
            String description) {
        this.workEffortType = workEffortType;
        this.workRequirementTypeName = workRequirementTypeName;
        this.workRequirementSequence = workRequirementSequence;
        this.workflowStep = workflowStep;
        this.estimatedTimeAllowed = estimatedTimeAllowed;
        this.maximumTimeAllowed = maximumTimeAllowed;
        this.allowReassignment = allowReassignment;
        this.sortOrder = sortOrder;
        this.description = description;
    }
    
    public WorkEffortTypeTransfer getWorkEffortType() {
        return workEffortType;
    }
    
    public void setWorkEffortType(WorkEffortTypeTransfer workEffortType) {
        this.workEffortType = workEffortType;
    }
    
    public String getWorkRequirementTypeName() {
        return workRequirementTypeName;
    }
    
    public void setWorkRequirementTypeName(String workRequirementTypeName) {
        this.workRequirementTypeName = workRequirementTypeName;
    }
    
    public SequenceTransfer getWorkRequirementSequence() {
        return workRequirementSequence;
    }
    
    public void setWorkRequirementSequence(SequenceTransfer workRequirementSequence) {
        this.workRequirementSequence = workRequirementSequence;
    }
    
    public WorkflowStepTransfer getWorkflowStep() {
        return workflowStep;
    }
    
    public void setWorkflowStep(WorkflowStepTransfer workflowStep) {
        this.workflowStep = workflowStep;
    }
    
    public String getEstimatedTimeAllowed() {
        return estimatedTimeAllowed;
    }
    
    public void setEstimatedTimeAllowed(String estimatedTimeAllowed) {
        this.estimatedTimeAllowed = estimatedTimeAllowed;
    }
    
    public String getMaximumTimeAllowed() {
        return maximumTimeAllowed;
    }
    
    public void setMaximumTimeAllowed(String maximumTimeAllowed) {
        this.maximumTimeAllowed = maximumTimeAllowed;
    }
    
    /**
     * Returns the allowReassignment.
     * @return the allowReassignment
     */
    public Boolean getAllowReassignment() {
        return allowReassignment;
    }

    /**
     * Sets the allowReassignment.
     * @param allowReassignment the allowReassignment to set
     */
    public void setAllowReassignment(Boolean allowReassignment) {
        this.allowReassignment = allowReassignment;
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

    /**
     * Returns the workRequirementScopes.
     * @return the workRequirementScopes
     */
    public ListWrapper<WorkRequirementScopeTransfer> getWorkRequirementScopes() {
        return workRequirementScopes;
    }

    /**
     * Sets the workRequirementScopes.
     * @param workRequirementScopes the workRequirementScopes to set
     */
    public void setWorkRequirementScopes(ListWrapper<WorkRequirementScopeTransfer> workRequirementScopes) {
        this.workRequirementScopes = workRequirementScopes;
    }

}
