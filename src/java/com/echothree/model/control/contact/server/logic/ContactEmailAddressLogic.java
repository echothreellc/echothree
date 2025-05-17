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

package com.echothree.model.control.contact.server.logic;

import com.echothree.model.control.contact.common.ContactMechanismPurposes;
import com.echothree.model.control.contact.common.ContactMechanismTypes;
import com.echothree.model.control.contact.common.workflow.EmailAddressStatusConstants;
import com.echothree.model.control.contact.common.workflow.EmailAddressVerificationConstants;
import com.echothree.model.control.contact.server.control.ContactControl;
import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.control.sequence.common.SequenceTypes;
import com.echothree.model.control.sequence.server.logic.SequenceGeneratorLogic;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.contact.server.entity.PartyContactMechanism;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.persistence.Session;

public class ContactEmailAddressLogic
    extends BaseLogic {
    
    private ContactEmailAddressLogic() {
        super();
    }
    
    private static class ContactEmailAddressLogicHolder {
        static ContactEmailAddressLogic instance = new ContactEmailAddressLogic();
    }
    
    public static ContactEmailAddressLogic getInstance() {
        return ContactEmailAddressLogicHolder.instance;
    }
    
    public PartyContactMechanism createContactEmailAddress(Party party, String emailAddress, Boolean allowSolicitation,
            String description, String contactMechanismPurposeName, BasePK createdBy) {
        var contactControl = Session.getModelController(ContactControl.class);
        var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
        var workflowControl = Session.getModelController(WorkflowControl.class);
        var contactMechanismName = SequenceGeneratorLogic.getInstance().getNextSequenceValue(null, SequenceTypes.CONTACT_MECHANISM.name());
        var contactMechanismType = contactControl.getContactMechanismTypeByName(ContactMechanismTypes.EMAIL_ADDRESS.name());

        var contactMechanism = contactControl.createContactMechanism(contactMechanismName, contactMechanismType,
                allowSolicitation, createdBy);

        contactControl.createContactEmailAddress(contactMechanism, emailAddress, createdBy);

        var partyContactMechanism = contactControl.createPartyContactMechanism(party, contactMechanism, description, Boolean.FALSE, 1, createdBy);

        var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(contactMechanism.getPrimaryKey());
        workflowControl.addEntityToWorkflowUsingNames(null, EmailAddressStatusConstants.Workflow_EMAIL_ADDRESS_STATUS,
                EmailAddressStatusConstants.WorkflowEntrance_NEW_EMAIL_ADDRESS, entityInstance, null, null, createdBy);
        workflowControl.addEntityToWorkflowUsingNames(null, EmailAddressVerificationConstants.Workflow_EMAIL_ADDRESS_VERIFICATION,
                EmailAddressVerificationConstants.WorkflowEntrance_NEW_EMAIL_ADDRESS, entityInstance, null, null, createdBy);

        if(contactMechanismPurposeName != null) {
            var contactMechanismPurpose = contactControl.getContactMechanismPurposeByName(ContactMechanismPurposes.PRIMARY_EMAIL.name());

            contactControl.createPartyContactMechanismPurpose(partyContactMechanism, contactMechanismPurpose, Boolean.FALSE, 1, createdBy);
        }
        
        return partyContactMechanism;
    }
    
}
