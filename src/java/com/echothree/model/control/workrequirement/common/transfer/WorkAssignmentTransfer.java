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
     * @return the workRequirement
     */
    public WorkRequirementTransfer getWorkRequirement() {
        return workRequirement;
    }

    /**
     * @param workRequirement the workRequirement to set
     */
    public void setWorkRequirement(WorkRequirementTransfer workRequirement) {
        this.workRequirement = workRequirement;
    }

    /**
     * @return the workAssignmentSequence
     */
    public Integer getWorkAssignmentSequence() {
        return workAssignmentSequence;
    }

    /**
     * @param workAssignmentSequence the workAssignmentSequence to set
     */
    public void setWorkAssignmentSequence(Integer workAssignmentSequence) {
        this.workAssignmentSequence = workAssignmentSequence;
    }

    /**
     * @return the party
     */
    public PartyTransfer getParty() {
        return party;
    }

    /**
     * @param party the party to set
     */
    public void setParty(PartyTransfer party) {
        this.party = party;
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
     * @return the unformattedEndTime
     */
    public Long getUnformattedEndTime() {
        return unformattedEndTime;
    }

    /**
     * @param unformattedEndTime the unformattedEndTime to set
     */
    public void setUnformattedEndTime(Long unformattedEndTime) {
        this.unformattedEndTime = unformattedEndTime;
    }

    /**
     * @return the endTime
     */
    public String getEndTime() {
        return endTime;
    }

    /**
     * @param endTime the endTime to set
     */
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    /**
     * @return the workAssignmentStatus
     */
    public WorkflowEntityStatusTransfer getWorkAssignmentStatus() {
        return workAssignmentStatus;
    }

    /**
     * @param workAssignmentStatus the workAssignmentStatus to set
     */
    public void setWorkAssignmentStatus(WorkflowEntityStatusTransfer workAssignmentStatus) {
        this.workAssignmentStatus = workAssignmentStatus;
    }

}
