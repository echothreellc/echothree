// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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

package com.echothree.model.control.job.common.transfer;

import com.echothree.model.control.party.common.transfer.PartyTransfer;
import com.echothree.model.control.workflow.common.transfer.WorkflowEntityStatusTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class JobTransfer
        extends BaseTransfer {
    
    private String jobName;
    private PartyTransfer runAsParty;
    private Integer sortOrder;
    private String description;
    private WorkflowEntityStatusTransfer jobStatus;
    private Long unformattedLastStartTime;
    private String lastStartTime;
    private Long unformattedLastEndTime;
    private String lastEndTime;
    
    /** Creates a new instance of JobTransfer */
    public JobTransfer(String jobName, PartyTransfer runAsParty, Integer sortOrder, String description, WorkflowEntityStatusTransfer jobStatus,
            Long unformattedLastStartTime, String lastStartTime, Long unformattedLastEndTime, String lastEndTime) {
        this.jobName = jobName;
        this.runAsParty = runAsParty;
        this.sortOrder = sortOrder;
        this.description = description;
        this.jobStatus = jobStatus;
        this.unformattedLastStartTime = unformattedLastStartTime;
        this.lastStartTime = lastStartTime;
        this.unformattedLastEndTime = unformattedLastEndTime;
        this.lastEndTime = lastEndTime;
    }

    /**
     * @return the jobName
     */
    public String getJobName() {
        return jobName;
    }

    /**
     * @param jobName the jobName to set
     */
    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    /**
     * @return the runAsParty
     */
    public PartyTransfer getRunAsParty() {
        return runAsParty;
    }

    /**
     * @param runAsParty the runAsParty to set
     */
    public void setRunAsParty(PartyTransfer runAsParty) {
        this.runAsParty = runAsParty;
    }

    /**
     * @return the sortOrder
     */
    public Integer getSortOrder() {
        return sortOrder;
    }

    /**
     * @param sortOrder the sortOrder to set
     */
    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the jobStatus
     */
    public WorkflowEntityStatusTransfer getJobStatus() {
        return jobStatus;
    }

    /**
     * @param jobStatus the jobStatus to set
     */
    public void setJobStatus(WorkflowEntityStatusTransfer jobStatus) {
        this.jobStatus = jobStatus;
    }

    /**
     * @return the unformattedLastStartTime
     */
    public Long getUnformattedLastStartTime() {
        return unformattedLastStartTime;
    }

    /**
     * @param unformattedLastStartTime the unformattedLastStartTime to set
     */
    public void setUnformattedLastStartTime(Long unformattedLastStartTime) {
        this.unformattedLastStartTime = unformattedLastStartTime;
    }

    /**
     * @return the lastStartTime
     */
    public String getLastStartTime() {
        return lastStartTime;
    }

    /**
     * @param lastStartTime the lastStartTime to set
     */
    public void setLastStartTime(String lastStartTime) {
        this.lastStartTime = lastStartTime;
    }

    /**
     * @return the unformattedLastEndTime
     */
    public Long getUnformattedLastEndTime() {
        return unformattedLastEndTime;
    }

    /**
     * @param unformattedLastEndTime the unformattedLastEndTime to set
     */
    public void setUnformattedLastEndTime(Long unformattedLastEndTime) {
        this.unformattedLastEndTime = unformattedLastEndTime;
    }

    /**
     * @return the lastEndTime
     */
    public String getLastEndTime() {
        return lastEndTime;
    }

    /**
     * @param lastEndTime the lastEndTime to set
     */
    public void setLastEndTime(String lastEndTime) {
        this.lastEndTime = lastEndTime;
    }
    
}
