// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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

import com.echothree.util.common.transfer.BaseTransfer;

public class ContactListGroupTransfer
        extends BaseTransfer {
    
    private String contactListGroupName;
    private Boolean isDefault;
    private Integer sortOrder;
    private String description;
    
    /** Creates a new instance of ContactListGroupTransfer */
    public ContactListGroupTransfer(String contactListGroupName, Boolean isDefault, Integer sortOrder, String description) {
        this.contactListGroupName = contactListGroupName;
        this.isDefault = isDefault;
        this.sortOrder = sortOrder;
        this.description = description;
    }
    
    public String getContactListGroupName() {
        return contactListGroupName;
    }
    
    public void setContactListGroupName(String contactListGroupName) {
        this.contactListGroupName = contactListGroupName;
    }
    
    public Boolean getIsDefault() {
        return isDefault;
    }
    
    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }
    
    public Integer getSortOrder() {
        return sortOrder;
    }
    
    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
}
