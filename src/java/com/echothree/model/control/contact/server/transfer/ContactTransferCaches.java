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
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
public class ContactTransferCaches
        extends BaseTransferCaches {
    
    @Inject
    PostalAddressFormatTransferCache postalAddressFormatTransferCache;
    
    @Inject
    PostalAddressElementTypeTransferCache postalAddressElementTypeTransferCache;
    
    @Inject
    ContactMechanismAliasTypeTransferCache contactMechanismAliasTypeTransferCache;
    
    @Inject
    ContactMechanismAliasTypeDescriptionTransferCache contactMechanismAliasTypeDescriptionTransferCache;
    
    @Inject
    ContactMechanismPurposeTransferCache contactMechanismPurposeTransferCache;
    
    @Inject
    ContactMechanismTypeTransferCache contactMechanismTypeTransferCache;
    
    @Inject
    ContactMechanismTransferCache contactMechanismTransferCache;
    
    @Inject
    ContactEmailAddressTransferCache contactEmailAddressTransferCache;
    
    @Inject
    ContactPostalAddressTransferCache contactPostalAddressTransferCache;
    
    @Inject
    ContactTelephoneTransferCache contactTelephoneTransferCache;
    
    @Inject
    PostalAddressFormatDescriptionTransferCache postalAddressFormatDescriptionTransferCache;
    
    @Inject
    PostalAddressLineTransferCache postalAddressLineTransferCache;
    
    @Inject
    PostalAddressLineElementTransferCache postalAddressLineElementTransferCache;
    
    @Inject
    ContactWebAddressTransferCache contactWebAddressTransferCache;
    
    @Inject
    ContactMechanismAliasTransferCache contactMechanismAliasTransferCache;
    
    @Inject
    PartyContactMechanismAliasTransferCache partyContactMechanismAliasTransferCache;
    
    @Inject
    PartyContactMechanismPurposeTransferCache partyContactMechanismPurposeTransferCache;
    
    @Inject
    PartyContactMechanismRelationshipTransferCache partyContactMechanismRelationshipTransferCache;
    
    @Inject
    PartyContactMechanismTransferCache partyContactMechanismTransferCache;

    @Inject
    ContactInet4AddressTransferCache contactInet4AddressTransferCache;
    
    /** Creates a new instance of ContactTransferCaches */
    protected ContactTransferCaches() {
        super();
    }
    
    public PostalAddressFormatTransferCache getPostalAddressFormatTransferCache() {
        return postalAddressFormatTransferCache;
    }
    
    public PostalAddressElementTypeTransferCache getPostalAddressElementTypeTransferCache() {
        return postalAddressElementTypeTransferCache;
    }
    
    public ContactMechanismAliasTypeTransferCache getContactMechanismAliasTypeTransferCache() {
        return contactMechanismAliasTypeTransferCache;
    }

    public ContactMechanismAliasTypeDescriptionTransferCache getContactMechanismAliasTypeDescriptionTransferCache() {
        return contactMechanismAliasTypeDescriptionTransferCache;
    }

    public ContactMechanismPurposeTransferCache getContactMechanismPurposeTransferCache() {
        return contactMechanismPurposeTransferCache;
    }
    
    public ContactMechanismTypeTransferCache getContactMechanismTypeTransferCache() {
        return contactMechanismTypeTransferCache;
    }
    
    public ContactMechanismTransferCache getContactMechanismTransferCache() {
        return contactMechanismTransferCache;
    }
    
    public ContactEmailAddressTransferCache getContactEmailAddressTransferCache() {
        return contactEmailAddressTransferCache;
    }
    
    public ContactPostalAddressTransferCache getContactPostalAddressTransferCache() {
        return contactPostalAddressTransferCache;
    }
    
    public ContactTelephoneTransferCache getContactTelephoneTransferCache() {
        return contactTelephoneTransferCache;
    }
    
    public PostalAddressFormatDescriptionTransferCache getPostalAddressFormatDescriptionTransferCache() {
        return postalAddressFormatDescriptionTransferCache;
    }
    
    public PostalAddressLineTransferCache getPostalAddressLineTransferCache() {
        return postalAddressLineTransferCache;
    }
    
    public PostalAddressLineElementTransferCache getPostalAddressLineElementTransferCache() {
        return postalAddressLineElementTransferCache;
    }
    
    public ContactWebAddressTransferCache getContactWebAddressTransferCache() {
        return contactWebAddressTransferCache;
    }
    
    public ContactMechanismAliasTransferCache getContactMechanismAliasTransferCache() {
        return contactMechanismAliasTransferCache;
    }
    
    public PartyContactMechanismAliasTransferCache getPartyContactMechanismAliasTransferCache() {
        return partyContactMechanismAliasTransferCache;
    }
    
    public PartyContactMechanismPurposeTransferCache getPartyContactMechanismPurposeTransferCache() {
        return partyContactMechanismPurposeTransferCache;
    }
    
    public PartyContactMechanismRelationshipTransferCache getPartyContactMechanismRelationshipTransferCache() {
        return partyContactMechanismRelationshipTransferCache;
    }
    
    public PartyContactMechanismTransferCache getPartyContactMechanismTransferCache() {
        return partyContactMechanismTransferCache;
    }
    
    public ContactInet4AddressTransferCache getContactInet4AddressTransferCache() {
        return contactInet4AddressTransferCache;
    }
    
}
