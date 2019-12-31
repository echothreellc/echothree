// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

import com.echothree.model.control.workeffort.common.transfer.WorkEffortTransfer;
import com.echothree.model.control.workflow.common.transfer.WorkflowEntityStatusTransfer;
import com.echothree.util.common.transfer.BaseTransfer;
import com.echothree.util.common.transfer.ListWrapper;

public class WorkRequirementTransfer
        extends BaseTransfer {
    
    private String workRequirementName;
    private WorkEffortTransfer workEffort;
    private WorkRequirementScopeTransfer workRequirementScope;
    private Long unformattedStartTime;
    private String startTime;
    private Long unformattedRequiredTime;
    private String requiredTime;
    
    private WorkflowEntityStatusTransfer workRequirementStatus;

    private ListWrapper<WorkAssignmentTransfer> workAssignments;
    private ListWrapper<WorkTimeTransfer> workTimes;

    /** Creates a new instance of WorkRequirementTransfer */
    public WorkRequirementTransfer(String workRequirementName, WorkEffortTransfer workEffort, WorkRequirementScopeTransfer workRequirementScope,
            Long unformattedStartTime, String startTime, Long unformattedRequiredTime, String requiredTime, WorkflowEntityStatusTransfer workRequirementStatus) {
        this.workRequirementName = workRequirementName;
        this.workEffort = workEffort;
        this.workRequirementScope = workRequirementScope;
        this.unformattedStartTime = unformattedStartTime;
        this.startTime = startTime;
        this.unformattedRequiredTime = unformattedRequiredTime;
        this.requiredTime = requiredTime;
        this.workRequirementStatus = workRequirementStatus;
    }

    /**
     * @return the workRequirementName
     */
    public String getWorkRequirementName() {
        return workRequirementName;
    }

    /**
     * @param workRequirementName the workRequirementName to set
     */
    public void setWorkRequirementName(String workRequirementName) {
        this.workRequirementName = workRequirementName;
    }

    /**
     * @return the workEffort
     */
    public WorkEffortTransfer getWorkEffort() {
        return workEffort;
    }

    /**
     * @param workEffort the workEffort to set
     */
    public void setWorkEffort(WorkEffortTransfer workEffort) {
        this.workEffort = workEffort;
    }

    /**
     * @return the workRequirementScope
     */
    public WorkRequirementScopeTransfer getWorkRequirementScope() {
        return workRequirementScope;
    }

    /**
     * @param workRequirementScope the workRequirementScope to set
     */
    public void setWorkRequirementScope(WorkRequirementScopeTransfer workRequirementScope) {
        this.workRequirementScope = workRequirementScope;
    }

    /**
     * @return the unformattedStartTime
     */
    public Long getUnformattedStartTime() {
        return unformattedStartTime;
    }

    /**
     * @param unformattedStartTime the unformattedStartTime to set
     */
    public void setUnformattedStartTime(Long unformattedStartTime) {
        this.unformattedStartTime = unformattedStartTime;
    }

    /**
     * @return the startTime
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * @param startTime the startTime to set
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /**
     * @return the unformattedRequiredTime
     */
    public Long getUnformattedRequiredTime() {
        return unformattedRequiredTime;
    }

    /**
     * @param unformattedRequiredTime the unformattedRequiredTime to set
     */
    public void setUnformattedRequiredTime(Long unformattedRequiredTime) {
        this.unformattedRequiredTime = unformattedRequiredTime;
    }

    /**
     * @return the requiredTime
     */
    public String getRequiredTime() {
        return requiredTime;
    }

    /**
     * @param requiredTime the requiredTime to set
     */
    public void setRequiredTime(String requiredTime) {
        this.requiredTime = requiredTime;
    }

    /**
     * @return the workRequirementStatus
     */
    public WorkflowEntityStatusTransfer getWorkRequirementStatus() {
        return workRequirementStatus;
    }

    /**
     * @param workRequirementStatus the workRequirementStatus to set
     */
    public void setWorkRequirementStatus(WorkflowEntityStatusTransfer workRequirementStatus) {
        this.workRequirementStatus = workRequirementStatus;
    }

    /**
     * @return the workAssignments
     */
    public ListWrapper<WorkAssignmentTransfer> getWorkAssignments() {
        return workAssignments;
    }

    /**
     * @param workAssignments the workAssignments to set
     */
    public void setWorkAssignments(ListWrapper<WorkAssignmentTransfer> workAssignments) {
        this.workAssignments = workAssignments;
    }

    /**
     * @return the workTimes
     */
    public ListWrapper<WorkTimeTransfer> getWorkTimes() {
        return workTimes;
    }

    /**
     * @param workTimes the workTimes to set
     */
    public void setWorkTimes(ListWrapper<WorkTimeTransfer> workTimes) {
        this.workTimes = workTimes;
    }
    
}
