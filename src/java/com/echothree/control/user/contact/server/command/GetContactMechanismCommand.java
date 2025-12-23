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

import com.echothree.control.user.contact.common.form.GetContactMechanismForm;
import com.echothree.control.user.contact.common.result.ContactResultFactory;
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
public class GetContactMechanismCommand
        extends BaseSimpleCommand<GetContactMechanismForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PartyName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ContactMechanismName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of GetContactMechanismCommand */
    public GetContactMechanismCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected BaseResult execute() {
        var partyControl = Session.getModelController(PartyControl.class);
        var result = ContactResultFactory.getGetContactMechanismResult();
        var partyName = form.getPartyName();
        var party = partyName == null ? null : partyControl.getPartyByName(partyName);

        if(partyName == null || party != null) {
            var contactControl = Session.getModelController(ContactControl.class);
            var contactMechanismName = form.getContactMechanismName();
            var contactMechanism = contactControl.getContactMechanismByName(contactMechanismName);

            if(contactMechanism != null) {
                if(partyName == null) {
                    result.setContactMechanism(contactControl.getContactMechanismTransfer(getUserVisit(), contactMechanism));
                } else {
                    var partyContactMechanism = contactControl.getPartyContactMechanism(party, contactMechanism);

                    if(partyContactMechanism != null) {
                        result.setPartyContactMechanism(contactControl.getPartyContactMechanismTransfer(getUserVisit(), partyContactMechanism));
                    } else {
                        addExecutionError(ExecutionErrors.UnknownPartyContactMechanism.name(), partyName, contactMechanismName);
                    }
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownContactMechanismName.name(), contactMechanismName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownPartyName.name(), partyName);
        }

        return result;
    }
    
}
