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
    public ContactTransferCaches() {
        super();
    }
    
    public PostalAddressFormatTransferCache getPostalAddressFormatTransferCache() {
        if(postalAddressFormatTransferCache == null)
            postalAddressFormatTransferCache = new PostalAddressFormatTransferCache();
        
        return postalAddressFormatTransferCache;
    }
    
    public PostalAddressElementTypeTransferCache getPostalAddressElementTypeTransferCache() {
        if(postalAddressElementTypeTransferCache == null)
            postalAddressElementTypeTransferCache = new PostalAddressElementTypeTransferCache();
        
        return postalAddressElementTypeTransferCache;
    }
    
    public ContactMechanismAliasTypeTransferCache getContactMechanismAliasTypeTransferCache() {
        if(contactMechanismAliasTypeTransferCache == null)
            contactMechanismAliasTypeTransferCache = new ContactMechanismAliasTypeTransferCache();

        return contactMechanismAliasTypeTransferCache;
    }

    public ContactMechanismAliasTypeDescriptionTransferCache getContactMechanismAliasTypeDescriptionTransferCache() {
        if(contactMechanismAliasTypeDescriptionTransferCache == null)
            contactMechanismAliasTypeDescriptionTransferCache = new ContactMechanismAliasTypeDescriptionTransferCache();

        return contactMechanismAliasTypeDescriptionTransferCache;
    }

    public ContactMechanismPurposeTransferCache getContactMechanismPurposeTransferCache() {
        if(contactMechanismPurposeTransferCache == null)
            contactMechanismPurposeTransferCache = new ContactMechanismPurposeTransferCache();
        
        return contactMechanismPurposeTransferCache;
    }
    
    public ContactMechanismTypeTransferCache getContactMechanismTypeTransferCache() {
        if(contactMechanismTypeTransferCache == null)
            contactMechanismTypeTransferCache = new ContactMechanismTypeTransferCache();
        
        return contactMechanismTypeTransferCache;
    }
    
    public ContactMechanismTransferCache getContactMechanismTransferCache() {
        if(contactMechanismTransferCache == null)
            contactMechanismTransferCache = new ContactMechanismTransferCache();
        
        return contactMechanismTransferCache;
    }
    
    public ContactEmailAddressTransferCache getContactEmailAddressTransferCache() {
        if(contactEmailAddressTransferCache == null)
            contactEmailAddressTransferCache = new ContactEmailAddressTransferCache();
        
        return contactEmailAddressTransferCache;
    }
    
    public ContactPostalAddressTransferCache getContactPostalAddressTransferCache() {
        if(contactPostalAddressTransferCache == null)
            contactPostalAddressTransferCache = new ContactPostalAddressTransferCache();
        
        return contactPostalAddressTransferCache;
    }
    
    public ContactTelephoneTransferCache getContactTelephoneTransferCache() {
        if(contactTelephoneTransferCache == null)
            contactTelephoneTransferCache = new ContactTelephoneTransferCache();
        
        return contactTelephoneTransferCache;
    }
    
    public PostalAddressFormatDescriptionTransferCache getPostalAddressFormatDescriptionTransferCache() {
        if(postalAddressFormatDescriptionTransferCache == null)
            postalAddressFormatDescriptionTransferCache = new PostalAddressFormatDescriptionTransferCache();
        
        return postalAddressFormatDescriptionTransferCache;
    }
    
    public PostalAddressLineTransferCache getPostalAddressLineTransferCache() {
        if(postalAddressLineTransferCache == null)
            postalAddressLineTransferCache = new PostalAddressLineTransferCache();
        
        return postalAddressLineTransferCache;
    }
    
    public PostalAddressLineElementTransferCache getPostalAddressLineElementTransferCache() {
        if(postalAddressLineElementTransferCache == null)
            postalAddressLineElementTransferCache = new PostalAddressLineElementTransferCache();
        
        return postalAddressLineElementTransferCache;
    }
    
    public ContactWebAddressTransferCache getContactWebAddressTransferCache() {
        if(contactWebAddressTransferCache == null)
            contactWebAddressTransferCache = new ContactWebAddressTransferCache();
        
        return contactWebAddressTransferCache;
    }
    
    public ContactMechanismAliasTransferCache getContactMechanismAliasTransferCache() {
        if(contactMechanismAliasTransferCache == null)
            contactMechanismAliasTransferCache = new ContactMechanismAliasTransferCache();
        
        return contactMechanismAliasTransferCache;
    }
    
    public PartyContactMechanismAliasTransferCache getPartyContactMechanismAliasTransferCache() {
        if(partyContactMechanismAliasTransferCache == null)
            partyContactMechanismAliasTransferCache = new PartyContactMechanismAliasTransferCache();
        
        return partyContactMechanismAliasTransferCache;
    }
    
    public PartyContactMechanismPurposeTransferCache getPartyContactMechanismPurposeTransferCache() {
        if(partyContactMechanismPurposeTransferCache == null)
            partyContactMechanismPurposeTransferCache = new PartyContactMechanismPurposeTransferCache();
        
        return partyContactMechanismPurposeTransferCache;
    }
    
    public PartyContactMechanismRelationshipTransferCache getPartyContactMechanismRelationshipTransferCache() {
        if(partyContactMechanismRelationshipTransferCache == null)
            partyContactMechanismRelationshipTransferCache = new PartyContactMechanismRelationshipTransferCache();
        
        return partyContactMechanismRelationshipTransferCache;
    }
    
    public PartyContactMechanismTransferCache getPartyContactMechanismTransferCache() {
        if(partyContactMechanismTransferCache == null)
            partyContactMechanismTransferCache = new PartyContactMechanismTransferCache();
        
        return partyContactMechanismTransferCache;
    }
    
    public ContactInet4AddressTransferCache getContactInet4AddressTransferCache() {
        if(contactInet4AddressTransferCache == null)
            contactInet4AddressTransferCache = new ContactInet4AddressTransferCache();
        
        return contactInet4AddressTransferCache;
    }
    
}
