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

package com.echothree.model.control.campaign.common.transfer;

import com.echothree.model.control.workflow.common.transfer.WorkflowEntityStatusTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class CampaignSourceTransfer
        extends BaseTransfer {
    
    private String campaignSourceName;
    private String valueSha1Hash;
    private String value;
    private Boolean isDefault;
    private Integer sortOrder;
    private String description;
    private WorkflowEntityStatusTransfer campaignSourceStatus;
    
    /** Creates a new instance of CampaignSourceTransfer */
    public CampaignSourceTransfer(String campaignSourceName, String valueSha1Hash, String value, Boolean isDefault, Integer sortOrder, String description,
            WorkflowEntityStatusTransfer campaignSourceStatus) {
        this.campaignSourceName = campaignSourceName;
        this.valueSha1Hash = valueSha1Hash;
        this.value = value;
        this.isDefault = isDefault;
        this.sortOrder = sortOrder;
        this.description = description;
        this.campaignSourceStatus = campaignSourceStatus;
    }

    /**
     * Returns the campaignSourceName.
     * @return the campaignSourceName
     */
    public String getCampaignSourceName() {
        return campaignSourceName;
    }

    /**
     * Sets the campaignSourceName.
     * @param campaignSourceName the campaignSourceName to set
     */
    public void setCampaignSourceName(String campaignSourceName) {
        this.campaignSourceName = campaignSourceName;
    }

    /**
     * Returns the valueSha1Hash.
     * @return the valueSha1Hash
     */
    public String getValueSha1Hash() {
        return valueSha1Hash;
    }

    /**
     * Sets the valueSha1Hash.
     * @param valueSha1Hash the valueSha1Hash to set
     */
    public void setValueSha1Hash(String valueSha1Hash) {
        this.valueSha1Hash = valueSha1Hash;
    }

    /**
     * Returns the value.
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the value.
     * @param value the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Returns the isDefault.
     * @return the isDefault
     */
    public Boolean getIsDefault() {
        return isDefault;
    }

    /**
     * Sets the isDefault.
     * @param isDefault the isDefault to set
     */
    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }

    /**
     * Returns the sortOrder.
     * @return the sortOrder
     */
    public Integer getSortOrder() {
        return sortOrder;
    }

    /**
     * Sets the sortOrder.
     * @param sortOrder the sortOrder to set
     */
    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    /**
     * Returns the description.
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description.
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the campaignSourceStatus.
     * @return the campaignSourceStatus
     */
    public WorkflowEntityStatusTransfer getCampaignSourceStatus() {
        return campaignSourceStatus;
    }

    /**
     * Sets the campaignSourceStatus.
     * @param campaignSourceStatus the campaignSourceStatus to set
     */
    public void setCampaignSourceStatus(WorkflowEntityStatusTransfer campaignSourceStatus) {
        this.campaignSourceStatus = campaignSourceStatus;
    }
    
}
