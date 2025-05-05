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

package com.echothree.control.user.cancellationpolicy.server.command;

import com.echothree.control.user.cancellationpolicy.common.form.DeletePartyCancellationPolicyForm;
import com.echothree.control.user.cancellationpolicy.common.result.CancellationPolicyResultFactory;
import com.echothree.model.control.cancellationpolicy.server.control.CancellationPolicyControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
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

public class DeletePartyCancellationPolicyCommand
        extends BaseSimpleCommand<DeletePartyCancellationPolicyForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.PartyCancellationPolicy.name(), SecurityRoles.Delete.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PartyName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("CancellationKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("CancellationPolicyName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }

    /** Creates a new instance of DeletePartyCancellationPolicyCommand */
    public DeletePartyCancellationPolicyCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    @Override
    protected BaseResult execute() {
        var partyControl = Session.getModelController(PartyControl.class);
        var result = CancellationPolicyResultFactory.getGetPartyCancellationPolicyResult();
        var partyName = form.getPartyName();
        var party = partyControl.getPartyByName(partyName);

        if(party != null) {
            var cancellationPolicyControl = Session.getModelController(CancellationPolicyControl.class);
            var cancellationKindName = form.getCancellationKindName();
            var cancellationKind = cancellationPolicyControl.getCancellationKindByName(cancellationKindName);

            if(cancellationKind != null) {
                var cancellationPolicyName = form.getCancellationPolicyName();
                var cancellationPolicy = cancellationPolicyControl.getCancellationPolicyByName(cancellationKind, cancellationPolicyName);

                if(cancellationPolicy != null) {
                    var partyCancellationPolicy = cancellationPolicyControl.getPartyCancellationPolicyForUpdate(party, cancellationPolicy);

                    if(partyCancellationPolicy != null) {
                        cancellationPolicyControl.deletePartyCancellationPolicy(partyCancellationPolicy, getPartyPK());
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

        return result;
    }

}
