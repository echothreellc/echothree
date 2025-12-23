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

import com.echothree.model.control.party.common.transfer.PartyTransfer;
import com.echothree.model.control.workflow.common.transfer.WorkflowEntityStatusTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class WorkAssignmentTransfer
        extends BaseTransfer {
    
    private WorkRequirementTransfer workRequirement;
    private Integer workAssignmentSequence;
    private PartyTransfer party;
    private Long unformattedStartTime;
    private String startTime;
    private Long unformattedEndTime;
    private String endTime;
    
    private WorkflowEntityStatusTransfer workAssignmentStatus;

    /** Creates a new instance of WorkAssignmentTransfer */
    public WorkAssignmentTransfer(WorkRequirementTransfer workRequirement, Integer workAssignmentSequence, PartyTransfer party, Long unformattedStartTime,
            String startTime, Long unformattedEndTime, String endTime, WorkflowEntityStatusTransfer workAssignmentStatus) {
        this.workRequirement = workRequirement;
        this.workAssignmentSequence = workAssignmentSequence;
        this.party = party;
        this.unformattedStartTime = unformattedStartTime;
        this.startTime = startTime;
        this.unformattedEndTime = unformattedEndTime;
        this.endTime = endTime;
        this.workAssignmentStatus = workAssignmentStatus;
    }

    /**
     * Returns the workRequirement.
     * @return the workRequirement
     */
    public WorkRequirementTransfer getWorkRequirement() {
        return workRequirement;
    }

    /**
     * Sets the workRequirement.
     * @param workRequirement the workRequirement to set
     */
    public void setWorkRequirement(WorkRequirementTransfer workRequirement) {
        this.workRequirement = workRequirement;
    }

    /**
     * Returns the workAssignmentSequence.
     * @return the workAssignmentSequence
     */
    public Integer getWorkAssignmentSequence() {
        return workAssignmentSequence;
    }

    /**
     * Sets the workAssignmentSequence.
     * @param workAssignmentSequence the workAssignmentSequence to set
     */
    public void setWorkAssignmentSequence(Integer workAssignmentSequence) {
        this.workAssignmentSequence = workAssignmentSequence;
    }

    /**
     * Returns the party.
     * @return the party
     */
    public PartyTransfer getParty() {
        return party;
    }

    /**
     * Sets the party.
     * @param party the party to set
     */
    public void setParty(PartyTransfer party) {
        this.party = party;
    }

    /**
     * Returns the unformattedStartTime.
     * @return the unformattedStartTime
     */
    public Long getUnformattedStartTime() {
        return unformattedStartTime;
    }

    /**
     * Sets the unformattedStartTime.
     * @param unformattedStartTime the unformattedStartTime to set
     */
    public void setUnformattedStartTime(Long unformattedStartTime) {
        this.unformattedStartTime = unformattedStartTime;
    }

    /**
     * Returns the startTime.
     * @return the startTime
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * Sets the startTime.
     * @param startTime the startTime to set
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /**
     * Returns the unformattedEndTime.
     * @return the unformattedEndTime
     */
    public Long getUnformattedEndTime() {
        return unformattedEndTime;
    }

    /**
     * Sets the unformattedEndTime.
     * @param unformattedEndTime the unformattedEndTime to set
     */
    public void setUnformattedEndTime(Long unformattedEndTime) {
        this.unformattedEndTime = unformattedEndTime;
    }

    /**
     * Returns the endTime.
     * @return the endTime
     */
    public String getEndTime() {
        return endTime;
    }

    /**
     * Sets the endTime.
     * @param endTime the endTime to set
     */
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    /**
     * Returns the workAssignmentStatus.
     * @return the workAssignmentStatus
     */
    public WorkflowEntityStatusTransfer getWorkAssignmentStatus() {
        return workAssignmentStatus;
    }

    /**
     * Sets the workAssignmentStatus.
     * @param workAssignmentStatus the workAssignmentStatus to set
     */
    public void setWorkAssignmentStatus(WorkflowEntityStatusTransfer workAssignmentStatus) {
        this.workAssignmentStatus = workAssignmentStatus;
    }

}
