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

import com.echothree.model.control.contact.common.transfer.ContactMechanismAliasTransfer;
import com.echothree.model.control.contact.server.control.ContactControl;
import com.echothree.model.data.contact.server.entity.ContactMechanismAlias;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class ContactMechanismAliasTransferCache
        extends BaseContactTransferCache<ContactMechanismAlias, ContactMechanismAliasTransfer> {

    ContactControl contactControl = Session.getModelController(ContactControl.class);

    /** Creates a new instance of ContactMechanismAliasTransferCache */
    public ContactMechanismAliasTransferCache() {
        super();
    }
    
    public ContactMechanismAliasTransfer getContactMechanismAliasTransfer(UserVisit userVisit, ContactMechanismAlias contactMechanismAlias) {
        var contactMechanismAliasTransfer = get(contactMechanismAlias);
        
        if(contactMechanismAliasTransfer == null) {
            var contactMechanism = contactControl.getContactMechanismTransfer(userVisit,
                    contactMechanismAlias.getContactMechanism());
            var contactMechanismAliasType = contactControl.getContactMechanismAliasTypeTransfer(userVisit,
                    contactMechanismAlias.getContactMechanismAliasType());
            var alias = contactMechanismAlias.getAlias();
            
            contactMechanismAliasTransfer = new ContactMechanismAliasTransfer(contactMechanism, contactMechanismAliasType, alias);
            put(userVisit, contactMechanismAlias, contactMechanismAliasTransfer);
        }
        
        return contactMechanismAliasTransfer;
    }
    
}
