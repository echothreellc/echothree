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

import com.echothree.model.control.customer.common.transfer.CustomerTypeTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class CustomerTypeContactListGroupTransfer
        extends BaseTransfer {
    
    private CustomerTypeTransfer customerType;
    private ContactListGroupTransfer contactListGroup;
    private Boolean addWhenCreated;
    
    /** Creates a new instance of CustomerTypeContactListGroupTransfer */
    public CustomerTypeContactListGroupTransfer(CustomerTypeTransfer customerType, ContactListGroupTransfer contactListGroup, Boolean addWhenCreated) {
        this.customerType = customerType;
        this.contactListGroup = contactListGroup;
        this.addWhenCreated = addWhenCreated;
    }

    /**
     * Returns the customerType.
     * @return the customerType
     */
    public CustomerTypeTransfer getCustomerType() {
        return customerType;
    }

    /**
     * Sets the customerType.
     * @param customerType the customerType to set
     */
    public void setCustomerType(CustomerTypeTransfer customerType) {
        this.customerType = customerType;
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
     * Returns the addWhenCreated.
     * @return the addWhenCreated
     */
    public Boolean getAddWhenCreated() {
        return addWhenCreated;
    }

    /**
     * Sets the addWhenCreated.
     * @param addWhenCreated the addWhenCreated to set
     */
    public void setAddWhenCreated(Boolean addWhenCreated) {
        this.addWhenCreated = addWhenCreated;
    }
    
}
