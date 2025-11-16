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
import javax.enterprise.inject.spi.CDI;

public class ContactListTransferCaches
        extends BaseTransferCaches {
    
    protected ContactListTypeDescriptionTransferCache contactListTypeDescriptionTransferCache;
    protected ContactListTypeTransferCache contactListTypeTransferCache;
    protected ContactListGroupDescriptionTransferCache contactListGroupDescriptionTransferCache;
    protected ContactListGroupTransferCache contactListGroupTransferCache;
    protected ContactListFrequencyDescriptionTransferCache contactListFrequencyDescriptionTransferCache;
    protected ContactListFrequencyTransferCache contactListFrequencyTransferCache;
    protected ContactListDescriptionTransferCache contactListDescriptionTransferCache;
    protected ContactListTransferCache contactListTransferCache;
    protected PartyContactListTransferCache partyContactListTransferCache;
    protected PartyTypeContactListTransferCache partyTypeContactListTransferCache;
    protected PartyTypeContactListGroupTransferCache partyTypeContactListGroupTransferCache;
    protected CustomerTypeContactListTransferCache customerTypeContactListTransferCache;
    protected CustomerTypeContactListGroupTransferCache customerTypeContactListGroupTransferCache;
    protected ContactListContactMechanismPurposeTransferCache contactListContactMechanismPurposeTransferCache;
    
    /** Creates a new instance of ContactListTransferCaches */
    public ContactListTransferCaches() {
        super();
    }
    
    public ContactListTypeDescriptionTransferCache getContactListTypeDescriptionTransferCache() {
        if(contactListTypeDescriptionTransferCache == null)
            contactListTypeDescriptionTransferCache = CDI.current().select(ContactListTypeDescriptionTransferCache.class).get();
        
        return contactListTypeDescriptionTransferCache;
    }
    
    public ContactListTypeTransferCache getContactListTypeTransferCache() {
        if(contactListTypeTransferCache == null)
            contactListTypeTransferCache = CDI.current().select(ContactListTypeTransferCache.class).get();
        
        return contactListTypeTransferCache;
    }
    
    public ContactListGroupDescriptionTransferCache getContactListGroupDescriptionTransferCache() {
        if(contactListGroupDescriptionTransferCache == null)
            contactListGroupDescriptionTransferCache = CDI.current().select(ContactListGroupDescriptionTransferCache.class).get();

        return contactListGroupDescriptionTransferCache;
    }

    public ContactListGroupTransferCache getContactListGroupTransferCache() {
        if(contactListGroupTransferCache == null)
            contactListGroupTransferCache = CDI.current().select(ContactListGroupTransferCache.class).get();

        return contactListGroupTransferCache;
    }

    public ContactListFrequencyDescriptionTransferCache getContactListFrequencyDescriptionTransferCache() {
        if(contactListFrequencyDescriptionTransferCache == null)
            contactListFrequencyDescriptionTransferCache = CDI.current().select(ContactListFrequencyDescriptionTransferCache.class).get();

        return contactListFrequencyDescriptionTransferCache;
    }

    public ContactListFrequencyTransferCache getContactListFrequencyTransferCache() {
        if(contactListFrequencyTransferCache == null)
            contactListFrequencyTransferCache = CDI.current().select(ContactListFrequencyTransferCache.class).get();

        return contactListFrequencyTransferCache;
    }

    public ContactListDescriptionTransferCache getContactListDescriptionTransferCache() {
        if(contactListDescriptionTransferCache == null)
            contactListDescriptionTransferCache = CDI.current().select(ContactListDescriptionTransferCache.class).get();
        
        return contactListDescriptionTransferCache;
    }
    
    public ContactListTransferCache getContactListTransferCache() {
        if(contactListTransferCache == null)
            contactListTransferCache = CDI.current().select(ContactListTransferCache.class).get();
        
        return contactListTransferCache;
    }
    
    public PartyContactListTransferCache getPartyContactListTransferCache() {
        if(partyContactListTransferCache == null)
            partyContactListTransferCache = CDI.current().select(PartyContactListTransferCache.class).get();
        
        return partyContactListTransferCache;
    }
    
    public PartyTypeContactListTransferCache getPartyTypeContactListTransferCache() {
        if(partyTypeContactListTransferCache == null)
            partyTypeContactListTransferCache = CDI.current().select(PartyTypeContactListTransferCache.class).get();

        return partyTypeContactListTransferCache;
    }

    public PartyTypeContactListGroupTransferCache getPartyTypeContactListGroupTransferCache() {
        if(partyTypeContactListGroupTransferCache == null)
            partyTypeContactListGroupTransferCache = CDI.current().select(PartyTypeContactListGroupTransferCache.class).get();

        return partyTypeContactListGroupTransferCache;
    }

    public CustomerTypeContactListTransferCache getCustomerTypeContactListTransferCache() {
        if(customerTypeContactListTransferCache == null)
            customerTypeContactListTransferCache = CDI.current().select(CustomerTypeContactListTransferCache.class).get();

        return customerTypeContactListTransferCache;
    }

    public CustomerTypeContactListGroupTransferCache getCustomerTypeContactListGroupTransferCache() {
        if(customerTypeContactListGroupTransferCache == null)
            customerTypeContactListGroupTransferCache = CDI.current().select(CustomerTypeContactListGroupTransferCache.class).get();

        return customerTypeContactListGroupTransferCache;
    }

    public ContactListContactMechanismPurposeTransferCache getContactListContactMechanismPurposeTransferCache() {
        if(contactListContactMechanismPurposeTransferCache == null)
            contactListContactMechanismPurposeTransferCache = CDI.current().select(ContactListContactMechanismPurposeTransferCache.class).get();
        
        return contactListContactMechanismPurposeTransferCache;
    }
    
}
