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

import com.echothree.model.control.contact.common.transfer.ContactWebAddressTransfer;
import com.echothree.model.control.contact.common.workflow.WebAddressStatusConstants;
import com.echothree.model.control.contact.server.control.ContactControl;
import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.contact.server.entity.ContactWebAddress;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class ContactWebAddressTransferCache
        extends BaseContactTransferCache<ContactWebAddress, ContactWebAddressTransfer> {

    EntityInstanceControl entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
    WorkflowControl workflowControl = Session.getModelController(WorkflowControl.class);

    /** Creates a new instance of ContactWebAddressTransferCache */
    public ContactWebAddressTransferCache(UserVisit userVisit, ContactControl contactControl) {
        super(userVisit, contactControl);
    }
    
    public ContactWebAddressTransfer getContactWebAddressTransfer(ContactWebAddress contactWebAddress) {
        var contactWebAddressTransfer = get(contactWebAddress);
        
        if(contactWebAddressTransfer == null) {
            var url = contactWebAddress.getUrl();

            var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(contactWebAddress.getContactMechanismPK());
            var webAddressStatusTransfer = workflowControl.getWorkflowEntityStatusTransferByEntityInstanceUsingNames(userVisit,
                    WebAddressStatusConstants.Workflow_WEB_ADDRESS_STATUS, entityInstance);
            
            contactWebAddressTransfer = new ContactWebAddressTransfer(url, webAddressStatusTransfer);
            put(userVisit, contactWebAddress, contactWebAddressTransfer);
        }
        
        return contactWebAddressTransfer;
    }
    
}
