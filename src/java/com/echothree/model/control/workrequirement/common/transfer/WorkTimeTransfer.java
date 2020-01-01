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

import com.echothree.model.control.party.common.transfer.PartyTransfer;
import com.echothree.model.control.workflow.common.transfer.WorkflowEntityStatusTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class WorkTimeTransfer
        extends BaseTransfer {
    
    private WorkRequirementTransfer workRequirement;
    private Integer workTimeSequence;
    private PartyTransfer party;
    private Long unformattedStartTime;
    private String startTime;
    private Long unformattedEndTime;
    private String endTime;
    
    private WorkflowEntityStatusTransfer workTimeStatus;

    /** Creates a new instance of WorkTimeTransfer */
    public WorkTimeTransfer(WorkRequirementTransfer workRequirement, Integer workTimeSequence, PartyTransfer party, Long unformattedStartTime, String startTime,
            Long unformattedEndTime, String endTime, WorkflowEntityStatusTransfer workTimeStatus) {
        this.workRequirement = workRequirement;
        this.workTimeSequence = workTimeSequence;
        this.party = party;
        this.unformattedStartTime = unformattedStartTime;
        this.startTime = startTime;
        this.unformattedEndTime = unformattedEndTime;
        this.endTime = endTime;
        this.workTimeStatus = workTimeStatus;
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
     * @return the workTimeSequence
     */
    public Integer getWorkTimeSequence() {
        return workTimeSequence;
    }

    /**
     * @param workTimeSequence the workTimeSequence to set
     */
    public void setWorkTimeSequence(Integer workTimeSequence) {
        this.workTimeSequence = workTimeSequence;
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
     * @return the workTimeStatus
     */
    public WorkflowEntityStatusTransfer getWorkTimeStatus() {
        return workTimeStatus;
    }

    /**
     * @param workTimeStatus the workTimeStatus to set
     */
    public void setWorkTimeStatus(WorkflowEntityStatusTransfer workTimeStatus) {
        this.workTimeStatus = workTimeStatus;
    }

}
