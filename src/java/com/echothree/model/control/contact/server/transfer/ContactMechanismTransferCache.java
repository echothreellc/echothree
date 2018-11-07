// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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
import com.echothree.model.control.contact.common.ContactConstants;
import com.echothree.model.control.contact.common.ContactOptions;
import com.echothree.model.control.contact.common.transfer.ContactMechanismTransfer;
import com.echothree.model.control.contact.common.transfer.ContactMechanismTypeTransfer;
import com.echothree.model.control.contact.server.ContactControl;
import com.echothree.model.data.contact.server.entity.ContactMechanism;
import com.echothree.model.data.contact.server.entity.ContactMechanismDetail;
import com.echothree.model.data.user.server.entity.UserVisit;
import java.util.Set;

public class ContactMechanismTransferCache
        extends BaseContactTransferCache<ContactMechanism, ContactMechanismTransfer> {
    
    boolean includeComments;

    /** Creates a new instance of ContactMechanismTransferCache */
    public ContactMechanismTransferCache(UserVisit userVisit, ContactControl contactControl) {
        super(userVisit, contactControl);

        Set<String> options = session.getOptions();
        if(options != null) {
            setIncludeKey(options.contains(ContactOptions.ContactMechanismIncludeKey));
            setIncludeGuid(options.contains(ContactOptions.ContactMechanismIncludeGuid));
            includeComments = options.contains(ContactOptions.ContactMechanismIncludeComments);
            setIncludeEntityAttributeGroups(options.contains(ContactOptions.ContactMechanismIncludeEntityAttributeGroups));
            setIncludeTagScopes(options.contains(ContactOptions.ContactMechanismIncludeTagScopes));
        }
        
        setIncludeEntityInstance(true);
    }
    
    public ContactMechanismTransfer getContactMechanismTransfer(ContactMechanism contactMechanism) {
        ContactMechanismTransfer contactMechanismTransfer = get(contactMechanism);
        
        if(contactMechanismTransfer == null) {
            ContactMechanismDetail contactMechanismDetail = contactMechanism.getLastDetail();
            String contactMechanismName = contactMechanismDetail.getContactMechanismName();
            ContactMechanismTypeTransfer contactMechanismType = contactControl.getContactMechanismTypeTransfer(userVisit,
                    contactMechanismDetail.getContactMechanismType());
            Boolean allowSolicitation = contactMechanismDetail.getAllowSolicitation();

            contactMechanismTransfer = new ContactMechanismTransfer(contactMechanismName, contactMechanismType, allowSolicitation);
            put(contactMechanism, contactMechanismTransfer);

            String contactMechanismTypeName = contactMechanismType.getContactMechanismTypeName();
            
            if(contactMechanismTypeName.equals(ContactConstants.ContactMechanismType_POSTAL_ADDRESS)) {
                contactMechanismTransfer.setContactPostalAddress(contactControl.getContactPostalAddressTransfer(userVisit, contactControl.getContactPostalAddress(contactMechanism)) );
            } else if(contactMechanismTypeName.equals(ContactConstants.ContactMechanismType_EMAIL_ADDRESS)) {
                contactMechanismTransfer.setContactEmailAddress(contactControl.getContactEmailAddressTransfer(userVisit, contactControl.getContactEmailAddress(contactMechanism)));
            } else if(contactMechanismTypeName.equals(ContactConstants.ContactMechanismType_TELECOM_ADDRESS)) {
                contactMechanismTransfer.setContactTelephone(contactControl.getContactTelephoneTransfer(userVisit, contactControl.getContactTelephone(contactMechanism)));
            } else if(contactMechanismTypeName.equals(ContactConstants.ContactMechanismType_WEB_ADDRESS)) {
                contactMechanismTransfer.setContactWebAddress(contactControl.getContactWebAddressTransfer(userVisit, contactControl.getContactWebAddress(contactMechanism)));
            } else if(contactMechanismTypeName.equals(ContactConstants.ContactMechanismType_INET_4)) {
                contactMechanismTransfer.setContactInet4Address(contactControl.getContactInet4AddressTransfer(userVisit, contactControl.getContactInet4Address(contactMechanism)));
            }
            
            if(includeComments) {
                setupComments(contactMechanism, null, contactMechanismTransfer, CommentConstants.CommentType_CONTACT_MECHANISM);
            }
        }
        
        return contactMechanismTransfer;
    }
    
}
