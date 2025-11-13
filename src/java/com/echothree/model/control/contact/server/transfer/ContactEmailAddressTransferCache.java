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

import com.echothree.model.control.contact.common.transfer.ContactEmailAddressTransfer;
import com.echothree.model.control.contact.common.workflow.EmailAddressStatusConstants;
import com.echothree.model.control.contact.common.workflow.EmailAddressVerificationConstants;
import com.echothree.model.control.contact.server.control.ContactControl;
import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.contact.server.entity.ContactEmailAddress;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class ContactEmailAddressTransferCache
        extends BaseContactTransferCache<ContactEmailAddress, ContactEmailAddressTransfer> {

    EntityInstanceControl entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
    WorkflowControl workflowControl = Session.getModelController(WorkflowControl.class);

    /** Creates a new instance of ContactEmailAddressTransferCache */
    public ContactEmailAddressTransferCache(ContactControl contactControl) {
        super(contactControl);
    }
    
    public ContactEmailAddressTransfer getContactEmailAddressTransfer(UserVisit userVisit, ContactEmailAddress contactEmailAddress) {
        var contactEmailAddressTransfer = get(contactEmailAddress);
        
        if(contactEmailAddressTransfer == null) {
            var emailAddress = contactEmailAddress.getEmailAddress();

            var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(contactEmailAddress.getContactMechanismPK());
            var emailAddressStatusTransfer = workflowControl.getWorkflowEntityStatusTransferByEntityInstanceUsingNames(userVisit,
                    EmailAddressStatusConstants.Workflow_EMAIL_ADDRESS_STATUS, entityInstance);
            var emailAddressVerificationTransfer = workflowControl.getWorkflowEntityStatusTransferByEntityInstanceUsingNames(userVisit,
                    EmailAddressVerificationConstants.Workflow_EMAIL_ADDRESS_VERIFICATION, entityInstance);
            
            contactEmailAddressTransfer = new ContactEmailAddressTransfer(emailAddress, emailAddressStatusTransfer, emailAddressVerificationTransfer);
            put(userVisit, contactEmailAddress, contactEmailAddressTransfer);
        }
        
        return contactEmailAddressTransfer;
    }
    
}
