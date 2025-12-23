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

public class CampaignTransfer
        extends BaseTransfer {
    
    private String campaignName;
    private String valueSha1Hash;
    private String value;
    private Boolean isDefault;
    private Integer sortOrder;
    private String description;
    private WorkflowEntityStatusTransfer campaignStatus;
    
    /** Creates a new instance of CampaignTransfer */
    public CampaignTransfer(String campaignName, String valueSha1Hash, String value, Boolean isDefault, Integer sortOrder, String description,
            WorkflowEntityStatusTransfer campaignStatus) {
        this.campaignName = campaignName;
        this.valueSha1Hash = valueSha1Hash;
        this.value = value;
        this.isDefault = isDefault;
        this.sortOrder = sortOrder;
        this.description = description;
        this.campaignStatus = campaignStatus;
    }

    /**
     * Returns the campaignName.
     * @return the campaignName
     */
    public String getCampaignName() {
        return campaignName;
    }

    /**
     * Sets the campaignName.
     * @param campaignName the campaignName to set
     */
    public void setCampaignName(String campaignName) {
        this.campaignName = campaignName;
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
     * Returns the campaignStatus.
     * @return the campaignStatus
     */
    public WorkflowEntityStatusTransfer getCampaignStatus() {
        return campaignStatus;
    }

    /**
     * Sets the campaignStatus.
     * @param campaignStatus the campaignStatus to set
     */
    public void setCampaignStatus(WorkflowEntityStatusTransfer campaignStatus) {
        this.campaignStatus = campaignStatus;
    }
    
}
