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

import com.echothree.util.server.transfer.BaseTransferCaches;
import javax.enterprise.inject.spi.CDI;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class ContactTransferCaches
        extends BaseTransferCaches {
    
    protected PostalAddressFormatTransferCache postalAddressFormatTransferCache;
    protected PostalAddressElementTypeTransferCache postalAddressElementTypeTransferCache;
    protected ContactMechanismAliasTypeTransferCache contactMechanismAliasTypeTransferCache;
    protected ContactMechanismAliasTypeDescriptionTransferCache contactMechanismAliasTypeDescriptionTransferCache;
    protected ContactMechanismPurposeTransferCache contactMechanismPurposeTransferCache;
    protected ContactMechanismTypeTransferCache contactMechanismTypeTransferCache;
    protected ContactMechanismTransferCache contactMechanismTransferCache;
    protected ContactEmailAddressTransferCache contactEmailAddressTransferCache;
    protected ContactPostalAddressTransferCache contactPostalAddressTransferCache;
    protected ContactTelephoneTransferCache contactTelephoneTransferCache;
    protected PostalAddressFormatDescriptionTransferCache postalAddressFormatDescriptionTransferCache;
    protected PostalAddressLineTransferCache postalAddressLineTransferCache;
    protected PostalAddressLineElementTransferCache postalAddressLineElementTransferCache;
    protected ContactWebAddressTransferCache contactWebAddressTransferCache;
    protected ContactMechanismAliasTransferCache contactMechanismAliasTransferCache;
    protected PartyContactMechanismAliasTransferCache partyContactMechanismAliasTransferCache;
    protected PartyContactMechanismPurposeTransferCache partyContactMechanismPurposeTransferCache;
    protected PartyContactMechanismRelationshipTransferCache partyContactMechanismRelationshipTransferCache;
    protected PartyContactMechanismTransferCache partyContactMechanismTransferCache;
    protected ContactInet4AddressTransferCache contactInet4AddressTransferCache;
    
    /** Creates a new instance of ContactTransferCaches */
    protected ContactTransferCaches() {
        super();
    }
    
    public PostalAddressFormatTransferCache getPostalAddressFormatTransferCache() {
        if(postalAddressFormatTransferCache == null)
            postalAddressFormatTransferCache = CDI.current().select(PostalAddressFormatTransferCache.class).get();
        
        return postalAddressFormatTransferCache;
    }
    
    public PostalAddressElementTypeTransferCache getPostalAddressElementTypeTransferCache() {
        if(postalAddressElementTypeTransferCache == null)
            postalAddressElementTypeTransferCache = CDI.current().select(PostalAddressElementTypeTransferCache.class).get();
        
        return postalAddressElementTypeTransferCache;
    }
    
    public ContactMechanismAliasTypeTransferCache getContactMechanismAliasTypeTransferCache() {
        if(contactMechanismAliasTypeTransferCache == null)
            contactMechanismAliasTypeTransferCache = CDI.current().select(ContactMechanismAliasTypeTransferCache.class).get();

        return contactMechanismAliasTypeTransferCache;
    }

    public ContactMechanismAliasTypeDescriptionTransferCache getContactMechanismAliasTypeDescriptionTransferCache() {
        if(contactMechanismAliasTypeDescriptionTransferCache == null)
            contactMechanismAliasTypeDescriptionTransferCache = CDI.current().select(ContactMechanismAliasTypeDescriptionTransferCache.class).get();

        return contactMechanismAliasTypeDescriptionTransferCache;
    }

    public ContactMechanismPurposeTransferCache getContactMechanismPurposeTransferCache() {
        if(contactMechanismPurposeTransferCache == null)
            contactMechanismPurposeTransferCache = CDI.current().select(ContactMechanismPurposeTransferCache.class).get();
        
        return contactMechanismPurposeTransferCache;
    }
    
    public ContactMechanismTypeTransferCache getContactMechanismTypeTransferCache() {
        if(contactMechanismTypeTransferCache == null)
            contactMechanismTypeTransferCache = CDI.current().select(ContactMechanismTypeTransferCache.class).get();
        
        return contactMechanismTypeTransferCache;
    }
    
    public ContactMechanismTransferCache getContactMechanismTransferCache() {
        if(contactMechanismTransferCache == null)
            contactMechanismTransferCache = CDI.current().select(ContactMechanismTransferCache.class).get();
        
        return contactMechanismTransferCache;
    }
    
    public ContactEmailAddressTransferCache getContactEmailAddressTransferCache() {
        if(contactEmailAddressTransferCache == null)
            contactEmailAddressTransferCache = CDI.current().select(ContactEmailAddressTransferCache.class).get();
        
        return contactEmailAddressTransferCache;
    }
    
    public ContactPostalAddressTransferCache getContactPostalAddressTransferCache() {
        if(contactPostalAddressTransferCache == null)
            contactPostalAddressTransferCache = CDI.current().select(ContactPostalAddressTransferCache.class).get();
        
        return contactPostalAddressTransferCache;
    }
    
    public ContactTelephoneTransferCache getContactTelephoneTransferCache() {
        if(contactTelephoneTransferCache == null)
            contactTelephoneTransferCache = CDI.current().select(ContactTelephoneTransferCache.class).get();
        
        return contactTelephoneTransferCache;
    }
    
    public PostalAddressFormatDescriptionTransferCache getPostalAddressFormatDescriptionTransferCache() {
        if(postalAddressFormatDescriptionTransferCache == null)
            postalAddressFormatDescriptionTransferCache = CDI.current().select(PostalAddressFormatDescriptionTransferCache.class).get();
        
        return postalAddressFormatDescriptionTransferCache;
    }
    
    public PostalAddressLineTransferCache getPostalAddressLineTransferCache() {
        if(postalAddressLineTransferCache == null)
            postalAddressLineTransferCache = CDI.current().select(PostalAddressLineTransferCache.class).get();
        
        return postalAddressLineTransferCache;
    }
    
    public PostalAddressLineElementTransferCache getPostalAddressLineElementTransferCache() {
        if(postalAddressLineElementTransferCache == null)
            postalAddressLineElementTransferCache = CDI.current().select(PostalAddressLineElementTransferCache.class).get();
        
        return postalAddressLineElementTransferCache;
    }
    
    public ContactWebAddressTransferCache getContactWebAddressTransferCache() {
        if(contactWebAddressTransferCache == null)
            contactWebAddressTransferCache = CDI.current().select(ContactWebAddressTransferCache.class).get();
        
        return contactWebAddressTransferCache;
    }
    
    public ContactMechanismAliasTransferCache getContactMechanismAliasTransferCache() {
        if(contactMechanismAliasTransferCache == null)
            contactMechanismAliasTransferCache = CDI.current().select(ContactMechanismAliasTransferCache.class).get();
        
        return contactMechanismAliasTransferCache;
    }
    
    public PartyContactMechanismAliasTransferCache getPartyContactMechanismAliasTransferCache() {
        if(partyContactMechanismAliasTransferCache == null)
            partyContactMechanismAliasTransferCache = CDI.current().select(PartyContactMechanismAliasTransferCache.class).get();
        
        return partyContactMechanismAliasTransferCache;
    }
    
    public PartyContactMechanismPurposeTransferCache getPartyContactMechanismPurposeTransferCache() {
        if(partyContactMechanismPurposeTransferCache == null)
            partyContactMechanismPurposeTransferCache = CDI.current().select(PartyContactMechanismPurposeTransferCache.class).get();
        
        return partyContactMechanismPurposeTransferCache;
    }
    
    public PartyContactMechanismRelationshipTransferCache getPartyContactMechanismRelationshipTransferCache() {
        if(partyContactMechanismRelationshipTransferCache == null)
            partyContactMechanismRelationshipTransferCache = CDI.current().select(PartyContactMechanismRelationshipTransferCache.class).get();
        
        return partyContactMechanismRelationshipTransferCache;
    }
    
    public PartyContactMechanismTransferCache getPartyContactMechanismTransferCache() {
        if(partyContactMechanismTransferCache == null)
            partyContactMechanismTransferCache = CDI.current().select(PartyContactMechanismTransferCache.class).get();
        
        return partyContactMechanismTransferCache;
    }
    
    public ContactInet4AddressTransferCache getContactInet4AddressTransferCache() {
        if(contactInet4AddressTransferCache == null)
            contactInet4AddressTransferCache = new ContactInet4AddressTransferCache();
        
        return contactInet4AddressTransferCache;
    }
    
}
