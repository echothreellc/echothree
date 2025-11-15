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

import com.echothree.model.control.contactlist.common.transfer.ContactListFrequencyDescriptionTransfer;
import com.echothree.model.control.contactlist.server.control.ContactListControl;
import com.echothree.model.data.contactlist.server.entity.ContactListFrequencyDescription;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class ContactListFrequencyDescriptionTransferCache
        extends BaseContactListDescriptionTransferCache<ContactListFrequencyDescription, ContactListFrequencyDescriptionTransfer> {

    ContactListControl contactListControl = Session.getModelController(ContactListControl.class);

    /** Creates a new instance of ContactListFrequencyDescriptionTransferCache */
    public ContactListFrequencyDescriptionTransferCache() {
        super();
    }
    
    public ContactListFrequencyDescriptionTransfer getContactListFrequencyDescriptionTransfer(UserVisit userVisit, ContactListFrequencyDescription contactListFrequencyDescription) {
        var contactListFrequencyDescriptionTransfer = get(contactListFrequencyDescription);
        
        if(contactListFrequencyDescriptionTransfer == null) {
            var contactListFrequencyTransfer = contactListControl.getContactListFrequencyTransfer(userVisit, contactListFrequencyDescription.getContactListFrequency());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, contactListFrequencyDescription.getLanguage());
            
            contactListFrequencyDescriptionTransfer = new ContactListFrequencyDescriptionTransfer(languageTransfer, contactListFrequencyTransfer, contactListFrequencyDescription.getDescription());
            put(userVisit, contactListFrequencyDescription, contactListFrequencyDescriptionTransfer);
        }
        
        return contactListFrequencyDescriptionTransfer;
    }
    
}
