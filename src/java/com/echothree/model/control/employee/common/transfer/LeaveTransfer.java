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
     * Returns the leaveName.
     * @return the leaveName
     */
    public String getLeaveName() {
        return leaveName;
    }

    /**
     * Sets the leaveName.
     * @param leaveName the leaveName to set
     */
    public void setLeaveName(String leaveName) {
        this.leaveName = leaveName;
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
     * Returns the company.
     * @return the company
     */
    public CompanyTransfer getCompany() {
        return company;
    }

    /**
     * Sets the company.
     * @param company the company to set
     */
    public void setCompany(CompanyTransfer company) {
        this.company = company;
    }

    /**
     * Returns the leaveType.
     * @return the leaveType
     */
    public LeaveTypeTransfer getLeaveType() {
        return leaveType;
    }

    /**
     * Sets the leaveType.
     * @param leaveType the leaveType to set
     */
    public void setLeaveType(LeaveTypeTransfer leaveType) {
        this.leaveType = leaveType;
    }

    /**
     * Returns the leaveReason.
     * @return the leaveReason
     */
    public LeaveReasonTransfer getLeaveReason() {
        return leaveReason;
    }

    /**
     * Sets the leaveReason.
     * @param leaveReason the leaveReason to set
     */
    public void setLeaveReason(LeaveReasonTransfer leaveReason) {
        this.leaveReason = leaveReason;
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
     * Returns the unformattedTotalTime.
     * @return the unformattedTotalTime
     */
    public Long getUnformattedTotalTime() {
        return unformattedTotalTime;
    }

    /**
     * Sets the unformattedTotalTime.
     * @param unformattedTotalTime the unformattedTotalTime to set
     */
    public void setUnformattedTotalTime(Long unformattedTotalTime) {
        this.unformattedTotalTime = unformattedTotalTime;
    }

    /**
     * Returns the totalTime.
     * @return the totalTime
     */
    public String getTotalTime() {
        return totalTime;
    }

    /**
     * Sets the totalTime.
     * @param totalTime the totalTime to set
     */
    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }

    /**
     * Returns the leaveStatus.
     * @return the leaveStatus
     */
    public WorkflowEntityStatusTransfer getLeaveStatus() {
        return leaveStatus;
    }

    /**
     * Sets the leaveStatus.
     * @param leaveStatus the leaveStatus to set
     */
    public void setLeaveStatus(WorkflowEntityStatusTransfer leaveStatus) {
        this.leaveStatus = leaveStatus;
    }

}
