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

package com.echothree.model.control.contact.server.transfer;

import com.echothree.model.control.contact.common.transfer.ContactInet4AddressTransfer;
import com.echothree.model.data.contact.server.entity.ContactInet4Address;
import com.echothree.model.data.user.server.entity.UserVisit;

public class ContactInet4AddressTransferCache
        extends BaseContactTransferCache<ContactInet4Address, ContactInet4AddressTransfer> {
    
    /** Creates a new instance of ContactInet4AddressTransferCache */
    public ContactInet4AddressTransferCache() {
        super();
    }
    
    public ContactInet4AddressTransfer getContactInet4AddressTransfer(UserVisit userVisit, ContactInet4Address contactInet4Address) {
        var contactInet4AddressTransfer = get(contactInet4Address);
        
        if(contactInet4AddressTransfer == null) {
            var inet4Address = formatInet4Address(contactInet4Address.getInet4Address());
            
            contactInet4AddressTransfer = new ContactInet4AddressTransfer(inet4Address);
            put(userVisit, contactInet4Address, contactInet4AddressTransfer);
        }
        
        return contactInet4AddressTransfer;
    }
    
}
