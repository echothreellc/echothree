// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

import com.echothree.model.control.contact.common.workflow.EmailAddressStatusConstants;
import com.echothree.model.control.contact.common.workflow.EmailAddressVerificationConstants;
import com.echothree.model.control.contact.common.transfer.ContactEmailAddressTransfer;
import com.echothree.model.control.contact.server.control.ContactControl;
import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.control.workflow.common.transfer.WorkflowEntityStatusTransfer;
import com.echothree.model.control.workflow.server.WorkflowControl;
import com.echothree.model.data.contact.server.entity.ContactEmailAddress;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class ContactEmailAddressTransferCache
        extends BaseContactTransferCache<ContactEmailAddress, ContactEmailAddressTransfer> {
    
    CoreControl coreControl = (CoreControl)Session.getModelController(CoreControl.class);
    WorkflowControl workflowControl = (WorkflowControl)Session.getModelController(WorkflowControl.class);

    /** Creates a new instance of ContactEmailAddressTransferCache */
    public ContactEmailAddressTransferCache(UserVisit userVisit, ContactControl contactControl) {
        super(userVisit, contactControl);
    }
    
    public ContactEmailAddressTransfer getContactEmailAddressTransfer(ContactEmailAddress contactEmailAddress) {
        ContactEmailAddressTransfer contactEmailAddressTransfer = get(contactEmailAddress);
        
        if(contactEmailAddressTransfer == null) {
            String emailAddress = contactEmailAddress.getEmailAddress();
            
            EntityInstance entityInstance = coreControl.getEntityInstanceByBasePK(contactEmailAddress.getContactMechanismPK());
            WorkflowEntityStatusTransfer emailAddressStatusTransfer = workflowControl.getWorkflowEntityStatusTransferByEntityInstanceUsingNames(userVisit,
                    EmailAddressStatusConstants.Workflow_EMAIL_ADDRESS_STATUS, entityInstance);
            WorkflowEntityStatusTransfer emailAddressVerificationTransfer = workflowControl.getWorkflowEntityStatusTransferByEntityInstanceUsingNames(userVisit,
                    EmailAddressVerificationConstants.Workflow_EMAIL_ADDRESS_VERIFICATION, entityInstance);
            
            contactEmailAddressTransfer = new ContactEmailAddressTransfer(emailAddress, emailAddressStatusTransfer, emailAddressVerificationTransfer);
            put(contactEmailAddress, contactEmailAddressTransfer);
        }
        
        return contactEmailAddressTransfer;
    }
    
}
