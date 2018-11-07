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
import com.echothree.util.common.transfer.BaseTransfer;

public class EmploymentTransfer
        extends BaseTransfer {
    
    private String employmentName;
    private PartyTransfer party;
    private CompanyTransfer company;
    private Long unformattedStartTime;
    private String startTime;
    private Long unformattedEndTime;
    private String endTime;
    private TerminationTypeTransfer terminationType;
    private TerminationReasonTransfer terminationReason;
    
    /** Creates a new instance of EmploymentTransfer */
    public EmploymentTransfer(String employmentName, PartyTransfer party, CompanyTransfer company, Long unformattedStartTime, String startTime,
            Long unformattedEndTime, String endTime, TerminationTypeTransfer terminationType, TerminationReasonTransfer terminationReason) {
        this.employmentName = employmentName;
        this.party = party;
        this.company = company;
        this.unformattedStartTime = unformattedStartTime;
        this.startTime = startTime;
        this.unformattedEndTime = unformattedEndTime;
        this.endTime = endTime;
        this.terminationType = terminationType;
        this.terminationReason = terminationReason;
    }

    /**
     * @return the employmentName
     */
    public String getEmploymentName() {
        return employmentName;
    }

    /**
     * @param employmentName the employmentName to set
     */
    public void setEmploymentName(String employmentName) {
        this.employmentName = employmentName;
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
     * @return the terminationType
     */
    public TerminationTypeTransfer getTerminationType() {
        return terminationType;
    }

    /**
     * @param terminationType the terminationType to set
     */
    public void setTerminationType(TerminationTypeTransfer terminationType) {
        this.terminationType = terminationType;
    }

    /**
     * @return the terminationReason
     */
    public TerminationReasonTransfer getTerminationReason() {
        return terminationReason;
    }

    /**
     * @param terminationReason the terminationReason to set
     */
    public void setTerminationReason(TerminationReasonTransfer terminationReason) {
        this.terminationReason = terminationReason;
    }

}
