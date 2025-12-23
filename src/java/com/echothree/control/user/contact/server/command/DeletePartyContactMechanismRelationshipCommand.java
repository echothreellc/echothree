// --------------------------------------------------------------------------------
// Copyright 2002-2026 Echo Three, LLC
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

import com.echothree.control.user.contact.common.form.DeletePartyContactMechanismRelationshipForm;
import com.echothree.model.control.contact.server.control.ContactControl;
import com.echothree.model.control.party.server.control.PartyControl;
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
import javax.enterprise.context.Dependent;

@Dependent
public class DeletePartyContactMechanismRelationshipCommand
        extends BaseSimpleCommand<DeletePartyContactMechanismRelationshipForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PartyName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("FromContactMechanismName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ToContactMechanismName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of DeletePartyContactMechanismRelationshipCommand */
    public DeletePartyContactMechanismRelationshipCommand() {
        super(null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var partyControl = Session.getModelController(PartyControl.class);
        var partyName = form.getPartyName();
        var party = partyControl.getPartyByName(partyName);
        
        if(party != null) {
            var contactControl = Session.getModelController(ContactControl.class);
            var fromContactMechanismName = form.getFromContactMechanismName();
            var fromContactMechanism = contactControl.getContactMechanismByName(fromContactMechanismName);

            if(fromContactMechanism != null) {
                var fromPartyContactMechanism = contactControl.getPartyContactMechanism(party, fromContactMechanism);
                
                if(fromPartyContactMechanism != null) {
                    var toContactMechanismName = form.getToContactMechanismName();
                    var toContactMechanism = contactControl.getContactMechanismByName(toContactMechanismName);

                    if(toContactMechanism != null) {
                        var toPartyContactMechanism = contactControl.getPartyContactMechanism(party, toContactMechanism);
                        
                        if(toPartyContactMechanism != null) {
                            var partyContactMechanismRelationship = contactControl.getPartyContactMechanismRelationshipForUpdate(fromPartyContactMechanism,
                                    toPartyContactMechanism);

                            if(partyContactMechanismRelationship != null) {
                                contactControl.deletePartyContactMechanismRelationship(partyContactMechanismRelationship, getPartyPK());
                            } else {
                                addExecutionError(ExecutionErrors.UnknownPartyContactMechanismRelationship.name(), partyName, fromContactMechanismName, toContactMechanismName);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownToPartyContactMechanism.name(), partyName, toContactMechanismName);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownToContactMechanismName.name(), toContactMechanismName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownFromPartyContactMechanism.name(), partyName, fromContactMechanismName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownFromContactMechanismName.name(), fromContactMechanismName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownPartyName.name(), partyName);
        }
        
        return null;
    }
    
}
