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

import com.echothree.control.user.contact.common.form.DeletePartyContactMechanismPurposeForm;
import com.echothree.model.control.contact.server.ContactControl;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.data.contact.server.entity.ContactMechanism;
import com.echothree.model.data.contact.server.entity.ContactMechanismPurpose;
import com.echothree.model.data.contact.server.entity.PartyContactMechanism;
import com.echothree.model.data.contact.server.entity.PartyContactMechanismPurpose;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DeletePartyContactMechanismPurposeCommand
        extends BaseSimpleCommand<DeletePartyContactMechanismPurposeForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PartyName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ContactMechanismName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ContactMechanismPurposeName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of DeletePartyContactMechanismPurposeCommand */
    public DeletePartyContactMechanismPurposeCommand(UserVisitPK userVisitPK, DeletePartyContactMechanismPurposeForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);
        String partyName = form.getPartyName();
        Party party = partyControl.getPartyByName(partyName);
        
        if(party != null) {
            ContactControl contactControl = (ContactControl)Session.getModelController(ContactControl.class);
            String contactMechanismName = form.getContactMechanismName();
            ContactMechanism contactMechanism = contactControl.getContactMechanismByName(contactMechanismName);
            
            if(contactMechanism != null) {
                PartyContactMechanism partyContactMechanism = contactControl.getPartyContactMechanism(party, contactMechanism);
                
                if(partyContactMechanism != null) {
                    String contactMechanismPurposeName = form.getContactMechanismPurposeName();
                    ContactMechanismPurpose contactMechanismPurpose = contactControl.getContactMechanismPurposeByName(contactMechanismPurposeName);
                    
                    if(contactMechanismPurpose != null) {
                        PartyContactMechanismPurpose partyContactMechanismPurpose = contactControl.getPartyContactMechanismPurposeForUpdate(partyContactMechanism,
                                contactMechanismPurpose);
                        
                        if(partyContactMechanismPurpose != null) {
                            contactControl.deletePartyContactMechanismPurpose(partyContactMechanismPurpose, getPartyPK());
                        } else {
                            addExecutionError(ExecutionErrors.UnknownPartyContactMechanismPurpose.name());
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownContactMechanismPurposeName.name(), contactMechanismPurposeName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownPartyContactMechanism.name());
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownContactMechanismName.name(), contactMechanismName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownPartyName.name(), partyName);
        }
        
        return null;
    }
    
}
