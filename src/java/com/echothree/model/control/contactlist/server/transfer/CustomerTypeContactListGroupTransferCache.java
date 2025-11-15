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

package com.echothree.model.control.contactlist.server.transfer;

import com.echothree.model.control.contactlist.common.transfer.CustomerTypeContactListGroupTransfer;
import com.echothree.model.control.contactlist.server.control.ContactListControl;
import com.echothree.model.control.customer.server.control.CustomerControl;
import com.echothree.model.data.contactlist.server.entity.CustomerTypeContactListGroup;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class CustomerTypeContactListGroupTransferCache
        extends BaseContactListTransferCache<CustomerTypeContactListGroup, CustomerTypeContactListGroupTransfer> {

    ContactListControl contactListControl = Session.getModelController(ContactListControl.class);
    CustomerControl customerControl = Session.getModelController(CustomerControl.class);
    
    /** Creates a new instance of CustomerTypeContactListGroupTransferCache */
    public CustomerTypeContactListGroupTransferCache() {
        super();
    }
    
    public CustomerTypeContactListGroupTransfer getCustomerTypeContactListGroupTransfer(UserVisit userVisit, CustomerTypeContactListGroup customerTypeContactListGroup) {
        var customerTypeContactListGroupTransfer = get(customerTypeContactListGroup);
        
        if(customerTypeContactListGroupTransfer == null) {
            var customerTypeTransfer = customerControl.getCustomerTypeTransfer(userVisit, customerTypeContactListGroup.getCustomerType());
            var contactListGroupTransfer = contactListControl.getContactListGroupTransfer(userVisit, customerTypeContactListGroup.getContactListGroup());
            var addWhenCreated = customerTypeContactListGroup.getAddWhenCreated();
            
            customerTypeContactListGroupTransfer = new CustomerTypeContactListGroupTransfer(customerTypeTransfer, contactListGroupTransfer, addWhenCreated);
            put(userVisit, customerTypeContactListGroup, customerTypeContactListGroupTransfer);
        }
        
        return customerTypeContactListGroupTransfer;
    }
    
}
