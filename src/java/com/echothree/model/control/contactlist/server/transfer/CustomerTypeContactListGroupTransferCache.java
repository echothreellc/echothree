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

package com.echothree.model.control.contactlist.server.transfer;

import com.echothree.model.control.chain.server.ChainControl;
import com.echothree.model.control.contactlist.remote.transfer.ContactListGroupTransfer;
import com.echothree.model.control.contactlist.remote.transfer.CustomerTypeContactListGroupTransfer;
import com.echothree.model.control.contactlist.server.ContactListControl;
import com.echothree.model.control.customer.remote.transfer.CustomerTypeTransfer;
import com.echothree.model.control.customer.server.CustomerControl;
import com.echothree.model.data.contactlist.server.entity.CustomerTypeContactListGroup;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class CustomerTypeContactListGroupTransferCache
        extends BaseContactListTransferCache<CustomerTypeContactListGroup, CustomerTypeContactListGroupTransfer> {
    
    ChainControl chainControl = (ChainControl)Session.getModelController(ChainControl.class);
    CustomerControl customerControl = (CustomerControl)Session.getModelController(CustomerControl.class);
    
    /** Creates a new instance of CustomerTypeContactListGroupTransferCache */
    public CustomerTypeContactListGroupTransferCache(UserVisit userVisit, ContactListControl contactListControl) {
        super(userVisit, contactListControl);
    }
    
    public CustomerTypeContactListGroupTransfer getCustomerTypeContactListGroupTransfer(CustomerTypeContactListGroup customerTypeContactListGroup) {
        CustomerTypeContactListGroupTransfer customerTypeContactListGroupTransfer = get(customerTypeContactListGroup);
        
        if(customerTypeContactListGroupTransfer == null) {
            CustomerTypeTransfer customerTypeTransfer = customerControl.getCustomerTypeTransfer(userVisit, customerTypeContactListGroup.getCustomerType());
            ContactListGroupTransfer contactListGroupTransfer = contactListControl.getContactListGroupTransfer(userVisit, customerTypeContactListGroup.getContactListGroup());
            Boolean addWhenCreated = customerTypeContactListGroup.getAddWhenCreated();
            
            customerTypeContactListGroupTransfer = new CustomerTypeContactListGroupTransfer(customerTypeTransfer, contactListGroupTransfer, addWhenCreated);
            put(customerTypeContactListGroup, customerTypeContactListGroupTransfer);
        }
        
        return customerTypeContactListGroupTransfer;
    }
    
}
