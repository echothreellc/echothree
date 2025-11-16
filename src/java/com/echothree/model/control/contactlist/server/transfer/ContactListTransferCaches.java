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

import com.echothree.util.server.transfer.BaseTransferCaches;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
public class ContactListTransferCaches
        extends BaseTransferCaches {
    
    @Inject
    ContactListTypeDescriptionTransferCache contactListTypeDescriptionTransferCache;
    
    @Inject
    ContactListTypeTransferCache contactListTypeTransferCache;
    
    @Inject
    ContactListGroupDescriptionTransferCache contactListGroupDescriptionTransferCache;
    
    @Inject
    ContactListGroupTransferCache contactListGroupTransferCache;
    
    @Inject
    ContactListFrequencyDescriptionTransferCache contactListFrequencyDescriptionTransferCache;
    
    @Inject
    ContactListFrequencyTransferCache contactListFrequencyTransferCache;
    
    @Inject
    ContactListDescriptionTransferCache contactListDescriptionTransferCache;
    
    @Inject
    ContactListTransferCache contactListTransferCache;
    
    @Inject
    PartyContactListTransferCache partyContactListTransferCache;
    
    @Inject
    PartyTypeContactListTransferCache partyTypeContactListTransferCache;
    
    @Inject
    PartyTypeContactListGroupTransferCache partyTypeContactListGroupTransferCache;
    
    @Inject
    CustomerTypeContactListTransferCache customerTypeContactListTransferCache;
    
    @Inject
    CustomerTypeContactListGroupTransferCache customerTypeContactListGroupTransferCache;
    
    @Inject
    ContactListContactMechanismPurposeTransferCache contactListContactMechanismPurposeTransferCache;

    /** Creates a new instance of ContactListTransferCaches */
    protected ContactListTransferCaches() {
        super();
    }
    
    public ContactListTypeDescriptionTransferCache getContactListTypeDescriptionTransferCache() {
        return contactListTypeDescriptionTransferCache;
    }
    
    public ContactListTypeTransferCache getContactListTypeTransferCache() {
        return contactListTypeTransferCache;
    }
    
    public ContactListGroupDescriptionTransferCache getContactListGroupDescriptionTransferCache() {
        return contactListGroupDescriptionTransferCache;
    }

    public ContactListGroupTransferCache getContactListGroupTransferCache() {
        return contactListGroupTransferCache;
    }

    public ContactListFrequencyDescriptionTransferCache getContactListFrequencyDescriptionTransferCache() {
        return contactListFrequencyDescriptionTransferCache;
    }

    public ContactListFrequencyTransferCache getContactListFrequencyTransferCache() {
        return contactListFrequencyTransferCache;
    }

    public ContactListDescriptionTransferCache getContactListDescriptionTransferCache() {
        return contactListDescriptionTransferCache;
    }
    
    public ContactListTransferCache getContactListTransferCache() {
        return contactListTransferCache;
    }
    
    public PartyContactListTransferCache getPartyContactListTransferCache() {
        return partyContactListTransferCache;
    }
    
    public PartyTypeContactListTransferCache getPartyTypeContactListTransferCache() {
        return partyTypeContactListTransferCache;
    }

    public PartyTypeContactListGroupTransferCache getPartyTypeContactListGroupTransferCache() {
        return partyTypeContactListGroupTransferCache;
    }

    public CustomerTypeContactListTransferCache getCustomerTypeContactListTransferCache() {
        return customerTypeContactListTransferCache;
    }

    public CustomerTypeContactListGroupTransferCache getCustomerTypeContactListGroupTransferCache() {
        return customerTypeContactListGroupTransferCache;
    }

    public ContactListContactMechanismPurposeTransferCache getContactListContactMechanismPurposeTransferCache() {
        return contactListContactMechanismPurposeTransferCache;
    }
    
}
