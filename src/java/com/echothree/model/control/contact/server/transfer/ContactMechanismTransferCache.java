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

import com.echothree.model.control.comment.common.CommentConstants;
import com.echothree.model.control.contact.common.ContactMechanismTypes;
import com.echothree.model.control.contact.common.ContactOptions;
import com.echothree.model.control.contact.common.transfer.ContactMechanismTransfer;
import com.echothree.model.control.contact.server.control.ContactControl;
import com.echothree.model.data.contact.server.entity.ContactMechanism;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class ContactMechanismTransferCache
        extends BaseContactTransferCache<ContactMechanism, ContactMechanismTransfer> {

    ContactControl contactControl = Session.getModelController(ContactControl.class);

    boolean includeComments;

    /** Creates a new instance of ContactMechanismTransferCache */
    public ContactMechanismTransferCache() {
        super();

        var options = session.getOptions();
        if(options != null) {
            setIncludeUuid(options.contains(ContactOptions.ContactMechanismIncludeUuid));
            includeComments = options.contains(ContactOptions.ContactMechanismIncludeComments);
            setIncludeEntityAttributeGroups(options.contains(ContactOptions.ContactMechanismIncludeEntityAttributeGroups));
            setIncludeTagScopes(options.contains(ContactOptions.ContactMechanismIncludeTagScopes));
        }
        
        setIncludeEntityInstance(true);
    }
    
    public ContactMechanismTransfer getContactMechanismTransfer(UserVisit userVisit, ContactMechanism contactMechanism) {
        var contactMechanismTransfer = get(contactMechanism);
        
        if(contactMechanismTransfer == null) {
            var contactMechanismDetail = contactMechanism.getLastDetail();
            var contactMechanismName = contactMechanismDetail.getContactMechanismName();
            var contactMechanismType = contactControl.getContactMechanismTypeTransfer(userVisit,
                    contactMechanismDetail.getContactMechanismType());
            var allowSolicitation = contactMechanismDetail.getAllowSolicitation();

            contactMechanismTransfer = new ContactMechanismTransfer(contactMechanismName, contactMechanismType, allowSolicitation);
            put(userVisit, contactMechanism, contactMechanismTransfer);

            var contactMechanismTypeName = contactMechanismType.getContactMechanismTypeName();
            
            if(contactMechanismTypeName.equals(ContactMechanismTypes.POSTAL_ADDRESS.name())) {
                contactMechanismTransfer.setContactPostalAddress(contactControl.getContactPostalAddressTransfer(userVisit, contactControl.getContactPostalAddress(contactMechanism)) );
            } else if(contactMechanismTypeName.equals(ContactMechanismTypes.EMAIL_ADDRESS.name())) {
                contactMechanismTransfer.setContactEmailAddress(contactControl.getContactEmailAddressTransfer(userVisit, contactControl.getContactEmailAddress(contactMechanism)));
            } else if(contactMechanismTypeName.equals(ContactMechanismTypes.TELECOM_ADDRESS.name())) {
                contactMechanismTransfer.setContactTelephone(contactControl.getContactTelephoneTransfer(userVisit, contactControl.getContactTelephone(contactMechanism)));
            } else if(contactMechanismTypeName.equals(ContactMechanismTypes.WEB_ADDRESS.name())) {
                contactMechanismTransfer.setContactWebAddress(contactControl.getContactWebAddressTransfer(userVisit, contactControl.getContactWebAddress(contactMechanism)));
            } else if(contactMechanismTypeName.equals(ContactMechanismTypes.INET_4.name())) {
                contactMechanismTransfer.setContactInet4Address(contactControl.getContactInet4AddressTransfer(userVisit, contactControl.getContactInet4Address(contactMechanism)));
            }
            
            if(includeComments) {
                setupComments(userVisit, contactMechanism, null, contactMechanismTransfer, CommentConstants.CommentType_CONTACT_MECHANISM);
            }
        }
        
        return contactMechanismTransfer;
    }
    
}
