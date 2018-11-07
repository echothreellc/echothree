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

package com.echothree.model.control.employee.common.transfer;

import com.echothree.model.control.party.common.transfer.CompanyTransfer;
import com.echothree.model.control.party.common.transfer.PartyTransfer;
import com.echothree.model.control.workflow.common.transfer.WorkflowEntityStatusTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class LeaveTransfer
        extends BaseTransfer {
    
    private String leaveName;
    private PartyTransfer party;
    private CompanyTransfer company;
    private LeaveTypeTransfer leaveType;
    private LeaveReasonTransfer leaveReason;
    private Long unformattedStartTime;
    private String startTime;
    private Long unformattedEndTime;
    private String endTime;
    private Long unformattedTotalTime;
    private String totalTime;
    private WorkflowEntityStatusTransfer leaveStatus;

    /** Creates a new instance of LeaveTransfer */
    public LeaveTransfer(String leaveName, PartyTransfer party, CompanyTransfer company, LeaveTypeTransfer leaveType, LeaveReasonTransfer leaveReason,
            Long unformattedStartTime, String startTime, Long unformattedEndTime, String endTime, Long unformattedTotalTime, String totalTime,
            WorkflowEntityStatusTransfer leaveStatus) {
        this.leaveName = leaveName;
        this.party = party;
        this.company = company;
        this.leaveType = leaveType;
        this.leaveReason = leaveReason;
        this.unformattedStartTime = unformattedStartTime;
        this.startTime = startTime;
        this.unformattedEndTime = unformattedEndTime;
        this.endTime = endTime;
        this.unformattedTotalTime = unformattedTotalTime;
        this.totalTime = totalTime;
        this.leaveStatus = leaveStatus;
    }

    /**
     * @return the leaveName
     */
    public String getLeaveName() {
        return leaveName;
    }

    /**
     * @param leaveName the leaveName to set
     */
    public void setLeaveName(String leaveName) {
        this.leaveName = leaveName;
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
     * @return the company
     */
    public CompanyTransfer getCompany() {
        return company;
    }

    /**
     * @param company the company to set
     */
    public void setCompany(CompanyTransfer company) {
        this.company = company;
    }

    /**
     * @return the leaveType
     */
    public LeaveTypeTransfer getLeaveType() {
        return leaveType;
    }

    /**
     * @param leaveType the leaveType to set
     */
    public void setLeaveType(LeaveTypeTransfer leaveType) {
        this.leaveType = leaveType;
    }

    /**
     * @return the leaveReason
     */
    public LeaveReasonTransfer getLeaveReason() {
        return leaveReason;
    }

    /**
     * @param leaveReason the leaveReason to set
     */
    public void setLeaveReason(LeaveReasonTransfer leaveReason) {
        this.leaveReason = leaveReason;
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
     * @return the unformattedTotalTime
     */
    public Long getUnformattedTotalTime() {
        return unformattedTotalTime;
    }

    /**
     * @param unformattedTotalTime the unformattedTotalTime to set
     */
    public void setUnformattedTotalTime(Long unformattedTotalTime) {
        this.unformattedTotalTime = unformattedTotalTime;
    }

    /**
     * @return the totalTime
     */
    public String getTotalTime() {
        return totalTime;
    }

    /**
     * @param totalTime the totalTime to set
     */
    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }

    /**
     * @return the leaveStatus
     */
    public WorkflowEntityStatusTransfer getLeaveStatus() {
        return leaveStatus;
    }

    /**
     * @param leaveStatus the leaveStatus to set
     */
    public void setLeaveStatus(WorkflowEntityStatusTransfer leaveStatus) {
        this.leaveStatus = leaveStatus;
    }

}
