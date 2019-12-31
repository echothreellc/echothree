// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

import com.echothree.model.control.contactlist.common.transfer.ContactListTransfer;
import com.echothree.model.control.contactlist.common.transfer.CustomerTypeContactListTransfer;
import com.echothree.model.control.contactlist.server.ContactListControl;
import com.echothree.model.control.customer.common.transfer.CustomerTypeTransfer;
import com.echothree.model.control.customer.server.CustomerControl;
import com.echothree.model.data.contactlist.server.entity.CustomerTypeContactList;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class CustomerTypeContactListTransferCache
        extends BaseContactListTransferCache<CustomerTypeContactList, CustomerTypeContactListTransfer> {
    
    CustomerControl customerControl = (CustomerControl)Session.getModelController(CustomerControl.class);
    
    /** Creates a new instance of CustomerTypeContactListTransferCache */
    public CustomerTypeContactListTransferCache(UserVisit userVisit, ContactListControl contactListControl) {
        super(userVisit, contactListControl);
    }
    
    public CustomerTypeContactListTransfer getCustomerTypeContactListTransfer(CustomerTypeContactList customerTypeContactList) {
        CustomerTypeContactListTransfer customerTypeContactListTransfer = get(customerTypeContactList);
        
        if(customerTypeContactListTransfer == null) {
            CustomerTypeTransfer customerTypeTransfer = customerControl.getCustomerTypeTransfer(userVisit, customerTypeContactList.getCustomerType());
            ContactListTransfer contactListTransfer = contactListControl.getContactListTransfer(userVisit, customerTypeContactList.getContactList());
            Boolean addWhenCreated = customerTypeContactList.getAddWhenCreated();
            
            customerTypeContactListTransfer = new CustomerTypeContactListTransfer(customerTypeTransfer, contactListTransfer, addWhenCreated);
            put(customerTypeContactList, customerTypeContactListTransfer);
        }
        
        return customerTypeContactListTransfer;
    }
    
}
