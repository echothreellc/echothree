// --------------------------------------------------------------------------------
// Copyright 2002-2022 Echo Three, LLC
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

package com.echothree.model.control.contact.server.transfer;

import com.echothree.model.control.contact.common.transfer.ContactMechanismTypeTransfer;
import com.echothree.model.control.contact.server.control.ContactControl;
import com.echothree.model.data.contact.server.entity.ContactMechanismType;
import com.echothree.model.data.user.server.entity.UserVisit;

public class ContactMechanismTypeTransferCache
        extends BaseContactTransferCache<ContactMechanismType, ContactMechanismTypeTransfer> {
    
    /** Creates a new instance of ContactMechanismTypeTransferCache */
    public ContactMechanismTypeTransferCache(UserVisit userVisit, ContactControl contactControl) {
        super(userVisit, contactControl);
    }
    
    public ContactMechanismTypeTransfer getContactMechanismTypeTransfer(ContactMechanismType contactMechanismType) {
        ContactMechanismTypeTransfer contactMechanismTypeTransfer = get(contactMechanismType);
        
        if(contactMechanismTypeTransfer == null) {
            String contactMechanismTypeName = contactMechanismType.getContactMechanismTypeName();
            ContactMechanismType parentContactMechanismType = contactMechanismType.getParentContactMechanismType();
            ContactMechanismTypeTransfer parentContactMechanismTypeTransfer = parentContactMechanismType == null? null: getContactMechanismTypeTransfer(parentContactMechanismType);
            Boolean isDefault = contactMechanismType.getIsDefault();
            Integer sortOrder = contactMechanismType.getSortOrder();
            String description = contactControl.getBestContactMechanismTypeDescription(contactMechanismType, getLanguage());
            
            contactMechanismTypeTransfer = new ContactMechanismTypeTransfer(contactMechanismTypeName,
                    parentContactMechanismTypeTransfer, isDefault, sortOrder, description);
            put(contactMechanismType, contactMechanismTypeTransfer);
        }
        
        return contactMechanismTypeTransfer;
    }
    
}
