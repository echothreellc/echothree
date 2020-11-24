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

package com.echothree.control.user.cancellationpolicy.server.command;

import com.echothree.control.user.cancellationpolicy.common.form.SetPartyCancellationPolicyStatusForm;
import com.echothree.model.control.cancellationpolicy.server.control.CancellationPolicyControl;
import com.echothree.model.control.cancellationpolicy.server.logic.PartyCancellationPolicyLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationKind;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationPolicy;
import com.echothree.model.data.cancellationpolicy.server.entity.PartyCancellationPolicy;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SetPartyCancellationPolicyStatusCommand
        extends BaseSimpleCommand<SetPartyCancellationPolicyStatusForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                    new SecurityRoleDefinition(SecurityRoleGroups.PartyCancellationPolicyStatus.name(), SecurityRoles.Choices.name())
                    )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PartyName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("CancellationKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("CancellationPolicyName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("PartyCancellationPolicyStatusChoice", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of SetPartyCancellationPolicyStatusCommand */
    public SetPartyCancellationPolicyStatusCommand(UserVisitPK userVisitPK, SetPartyCancellationPolicyStatusForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var partyControl = Session.getModelController(PartyControl.class);
        String partyName = form.getPartyName();
        Party party = partyControl.getPartyByName(partyName);

        if(party != null) {
            var cancellationPolicyControl = Session.getModelController(CancellationPolicyControl.class);
            String cancellationKindName = form.getCancellationKindName();
            CancellationKind cancellationKind = cancellationPolicyControl.getCancellationKindByName(cancellationKindName);

            if(cancellationKind != null) {
                String cancellationPolicyName = form.getCancellationPolicyName();
                CancellationPolicy cancellationPolicy = cancellationPolicyControl.getCancellationPolicyByName(cancellationKind, cancellationPolicyName);

                if(cancellationPolicy != null) {
                    PartyCancellationPolicy partyCancellationPolicy = cancellationPolicyControl.createPartyCancellationPolicy(party, cancellationPolicy, getPartyPK());

                    if(partyCancellationPolicy != null) {
                        String partyCancellationPolicyStatusChoice = form.getPartyCancellationPolicyStatusChoice();

                        PartyCancellationPolicyLogic.getInstance().setPartyCancellationPolicyStatus(this, partyCancellationPolicy, partyCancellationPolicyStatusChoice, getPartyPK());
                    } else {
                        addExecutionError(ExecutionErrors.UnknownPartyCancellationPolicy.name(), partyName, cancellationKindName, cancellationPolicyName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownCancellationPolicyName.name(), cancellationKindName, cancellationPolicyName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownCancellationKindName.name(), cancellationKindName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownPartyName.name(), partyName);
        }

        return null;
    }
    
}
