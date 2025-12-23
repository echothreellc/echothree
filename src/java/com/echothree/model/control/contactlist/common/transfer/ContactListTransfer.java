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

package com.echothree.model.control.contactlist.common.transfer;

import com.echothree.model.control.workflow.common.transfer.WorkflowEntranceTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class ContactListTransfer
        extends BaseTransfer {
    
    private String contactListName;
    private ContactListGroupTransfer contactListGroup;
    private ContactListTypeTransfer contactListType;
    private ContactListFrequencyTransfer contactListFrequency;
    private WorkflowEntranceTransfer defaultPartyContactListStatus;
    private Boolean isDefault;
    private Integer sortOrder;
    private String description;
    
    /** Creates a new instance of ContactListTransfer */
    public ContactListTransfer(String contactListName, ContactListGroupTransfer contactListGroup, ContactListTypeTransfer contactListType,
            ContactListFrequencyTransfer contactListFrequency, WorkflowEntranceTransfer defaultPartyContactListStatus, Boolean isDefault, Integer sortOrder,
            String description) {
        this.contactListName = contactListName;
        this.contactListGroup = contactListGroup;
        this.contactListType = contactListType;
        this.contactListFrequency = contactListFrequency;
        this.defaultPartyContactListStatus = defaultPartyContactListStatus;
        this.isDefault = isDefault;
        this.sortOrder = sortOrder;
        this.description = description;
    }

    /**
     * Returns the contactListName.
     * @return the contactListName
     */
    public String getContactListName() {
        return contactListName;
    }

    /**
     * Sets the contactListName.
     * @param contactListName the contactListName to set
     */
    public void setContactListName(String contactListName) {
        this.contactListName = contactListName;
    }

    /**
     * Returns the contactListGroup.
     * @return the contactListGroup
     */
    public ContactListGroupTransfer getContactListGroup() {
        return contactListGroup;
    }

    /**
     * Sets the contactListGroup.
     * @param contactListGroup the contactListGroup to set
     */
    public void setContactListGroup(ContactListGroupTransfer contactListGroup) {
        this.contactListGroup = contactListGroup;
    }

    /**
     * Returns the contactListType.
     * @return the contactListType
     */
    public ContactListTypeTransfer getContactListType() {
        return contactListType;
    }

    /**
     * Sets the contactListType.
     * @param contactListType the contactListType to set
     */
    public void setContactListType(ContactListTypeTransfer contactListType) {
        this.contactListType = contactListType;
    }

    /**
     * Returns the contactListFrequency.
     * @return the contactListFrequency
     */
    public ContactListFrequencyTransfer getContactListFrequency() {
        return contactListFrequency;
    }

    /**
     * Sets the contactListFrequency.
     * @param contactListFrequency the contactListFrequency to set
     */
    public void setContactListFrequency(ContactListFrequencyTransfer contactListFrequency) {
        this.contactListFrequency = contactListFrequency;
    }

    /**
     * Returns the defaultPartyContactListStatus.
     * @return the defaultPartyContactListStatus
     */
    public WorkflowEntranceTransfer getDefaultPartyContactListStatus() {
        return defaultPartyContactListStatus;
    }

    /**
     * Sets the defaultPartyContactListStatus.
     * @param defaultPartyContactListStatus the defaultPartyContactListStatus to set
     */
    public void setDefaultPartyContactListStatus(WorkflowEntranceTransfer defaultPartyContactListStatus) {
        this.defaultPartyContactListStatus = defaultPartyContactListStatus;
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
    
}
