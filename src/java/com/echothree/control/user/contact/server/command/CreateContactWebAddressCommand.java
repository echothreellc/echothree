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

package com.echothree.control.user.contact.server.command;

import com.echothree.control.user.contact.remote.form.CreateContactWebAddressForm;
import com.echothree.control.user.contact.remote.result.ContactResultFactory;
import com.echothree.control.user.contact.remote.result.CreateContactWebAddressResult;
import com.echothree.model.control.contact.common.ContactConstants;
import com.echothree.model.control.contact.common.workflow.WebAddressStatusConstants;
import com.echothree.model.control.contact.server.ContactControl;
import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.sequence.common.SequenceConstants;
import com.echothree.model.control.sequence.server.logic.SequenceLogic;
import com.echothree.model.control.workflow.server.WorkflowControl;
import com.echothree.model.data.contact.server.entity.ContactMechanism;
import com.echothree.model.data.contact.server.entity.ContactMechanismType;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.user.remote.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.remote.command.BaseResult;
import com.echothree.util.remote.persistence.BasePK;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CreateContactWebAddressCommand
        extends BaseSimpleCommand<CreateContactWebAddressForm> {

    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PartyName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("Url", FieldType.URL, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of CreateContactWebAddressCommand */
    public CreateContactWebAddressCommand(UserVisitPK userVisitPK, CreateContactWebAddressForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        CreateContactWebAddressResult result = ContactResultFactory.getCreateContactWebAddressResult();
        PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);
        String partyName = form.getPartyName();
        Party party = partyControl.getPartyByName(partyName);
        
        if(party != null) {
            ContactControl contactControl = (ContactControl)Session.getModelController(ContactControl.class);
                            CoreControl coreControl = getCoreControl();
            WorkflowControl workflowControl = (WorkflowControl)Session.getModelController(WorkflowControl.class);
            BasePK createdBy = getPartyPK();
            String url = form.getUrl();
            String description = form.getDescription();
            String contactMechanismName = SequenceLogic.getInstance().getNextSequenceValue(null, SequenceConstants.SequenceType_CONTACT_MECHANISM);

            ContactMechanismType contactMechanismType = contactControl.getContactMechanismTypeByName(ContactConstants.ContactMechanismType_WEB_ADDRESS);
            ContactMechanism contactMechanism = contactControl.createContactMechanism(contactMechanismName, contactMechanismType,
                    Boolean.FALSE, createdBy);
            
            contactControl.createContactWebAddress(contactMechanism, url, createdBy);
            
            contactControl.createPartyContactMechanism(party, contactMechanism, description, Boolean.FALSE, 1,
                    createdBy);
            
            EntityInstance entityInstance = coreControl.getEntityInstanceByBasePK(contactMechanism.getPrimaryKey());
            workflowControl.addEntityToWorkflowUsingNames(null, WebAddressStatusConstants.Workflow_WEB_ADDRESS_STATUS,
                    WebAddressStatusConstants.WorkflowEntrance_NEW_WEB_ADDRESS, entityInstance, null, null, createdBy);
            
            result.setContactMechanismName(contactMechanism.getLastDetail().getContactMechanismName());
            result.setEntityRef(contactMechanism.getPrimaryKey().getEntityRef());
        } else {
            addExecutionError(ExecutionErrors.UnknownPartyName.name(), partyName);
        }
        
        return result;
    }
    
}
