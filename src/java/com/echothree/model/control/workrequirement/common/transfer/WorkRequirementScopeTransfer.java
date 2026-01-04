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

import com.echothree.model.control.selector.common.transfer.SelectorTransfer;
import com.echothree.model.control.sequence.common.transfer.SequenceTransfer;
import com.echothree.model.control.workeffort.common.transfer.WorkEffortScopeTransfer;
import com.echothree.util.common.transfer.BaseTransfer;
import com.echothree.util.common.transfer.ListWrapper;

public class WorkRequirementScopeTransfer
        extends BaseTransfer {
    
    private WorkEffortScopeTransfer workEffortScope;
    private WorkRequirementTypeTransfer workRequirementType;
    private SequenceTransfer workRequirementSequence;
    private SequenceTransfer workTimeSequence;
    private SelectorTransfer workAssignmentSelector;
    private String estimatedTimeAllowed;
    private String maximumTimeAllowed;
    
    private ListWrapper<WorkRequirementTransfer> workRequirements;

    /** Creates a new instance of WorkRequirementScopeTransfer */
    public WorkRequirementScopeTransfer(WorkEffortScopeTransfer workEffortScope, WorkRequirementTypeTransfer workRequirementType,
            SequenceTransfer workRequirementSequence, SequenceTransfer workTimeSequence, SelectorTransfer workAssignmentSelector,
            String estimatedTimeAllowed, String maximumTimeAllowed) {
        this.workEffortScope = workEffortScope;
        this.workRequirementType = workRequirementType;
        this.workRequirementSequence = workRequirementSequence;
        this.workTimeSequence = workTimeSequence;
        this.workAssignmentSelector = workAssignmentSelector;
        this.estimatedTimeAllowed = estimatedTimeAllowed;
        this.maximumTimeAllowed = maximumTimeAllowed;
    }
    
    public WorkEffortScopeTransfer getWorkEffortScope() {
        return workEffortScope;
    }
    
    public void setWorkEffortScope(WorkEffortScopeTransfer workEffortScope) {
        this.workEffortScope = workEffortScope;
    }
    
    public WorkRequirementTypeTransfer getWorkRequirementType() {
        return workRequirementType;
    }
    
    public void setWorkRequirementType(WorkRequirementTypeTransfer workRequirementType) {
        this.workRequirementType = workRequirementType;
    }
    
    public SequenceTransfer getWorkRequirementSequence() {
        return workRequirementSequence;
    }
    
    public void setWorkRequirementSequence(SequenceTransfer workRequirementSequence) {
        this.workRequirementSequence = workRequirementSequence;
    }
    
    public SequenceTransfer getWorkTimeSequence() {
        return workTimeSequence;
    }
    
    public void setWorkTimeSequence(SequenceTransfer workTimeSequence) {
        this.workTimeSequence = workTimeSequence;
    }
    
    public SelectorTransfer getWorkAssignmentSelector() {
        return workAssignmentSelector;
    }
    
    public void setWorkAssignmentSelector(SelectorTransfer workAssignmentSelector) {
        this.workAssignmentSelector = workAssignmentSelector;
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
     * Returns the workRequirements.
     * @return the workRequirements
     */
    public ListWrapper<WorkRequirementTransfer> getWorkRequirements() {
        return workRequirements;
    }

    /**
     * Sets the workRequirements.
     * @param workRequirements the workRequirements to set
     */
    public void setWorkRequirements(ListWrapper<WorkRequirementTransfer> workRequirements) {
        this.workRequirements = workRequirements;
    }
    
}
