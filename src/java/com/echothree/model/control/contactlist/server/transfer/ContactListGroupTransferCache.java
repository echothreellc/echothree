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

import com.echothree.model.control.contactlist.common.transfer.ContactListGroupTransfer;
import com.echothree.model.control.contactlist.server.control.ContactListControl;
import com.echothree.model.data.contactlist.server.entity.ContactListGroup;
import com.echothree.model.data.user.server.entity.UserVisit;

public class ContactListGroupTransferCache
        extends BaseContactListTransferCache<ContactListGroup, ContactListGroupTransfer> {
    
    /** Creates a new instance of ContactListGroupTransferCache */
    public ContactListGroupTransferCache(ContactListControl contactListControl) {
        super(contactListControl);
        
        setIncludeEntityInstance(true);
    }
    
    public ContactListGroupTransfer getContactListGroupTransfer(ContactListGroup contactListGroup) {
        var contactListGroupTransfer = get(contactListGroup);
        
        if(contactListGroupTransfer == null) {
            var contactListGroupDetail = contactListGroup.getLastDetail();
            var contactListGroupName = contactListGroupDetail.getContactListGroupName();
            var isDefault = contactListGroupDetail.getIsDefault();
            var sortOrder = contactListGroupDetail.getSortOrder();
            var description = contactListControl.getBestContactListGroupDescription(contactListGroup, getLanguage(userVisit));
            
            contactListGroupTransfer = new ContactListGroupTransfer(contactListGroupName, isDefault, sortOrder, description);
            put(userVisit, contactListGroup, contactListGroupTransfer);
        }
        
        return contactListGroupTransfer;
    }
    
}
