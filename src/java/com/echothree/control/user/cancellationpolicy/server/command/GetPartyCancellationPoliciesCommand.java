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

import com.echothree.control.user.cancellationpolicy.common.form.GetPartyCancellationPoliciesForm;
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
import javax.enterprise.context.Dependent;

@Dependent
public class GetPartyCancellationPoliciesCommand
        extends BaseSimpleCommand<GetPartyCancellationPoliciesForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.PartyCancellationPolicy.name(), SecurityRoles.List.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PartyName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("CancellationKindName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("CancellationPolicyName", FieldType.ENTITY_NAME, false, null, null)
                ));
    }

    /** Creates a new instance of GetPartyCancellationPoliciesCommand */
    public GetPartyCancellationPoliciesCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    @Override
    protected BaseResult execute() {
        var cancellationPolicyControl = Session.getModelController(CancellationPolicyControl.class);
        var result = CancellationPolicyResultFactory.getGetPartyCancellationPoliciesResult();
        var partyName = form.getPartyName();
        var cancellationKindName = form.getCancellationKindName();
        var cancellationPolicyName = form.getCancellationPolicyName();
        var parameterCount = (partyName != null ? 1 : 0) + (cancellationKindName != null && cancellationPolicyName != null ? 1 : 0);

        if(parameterCount == 1) {
            if(partyName != null) {
                var partyControl = Session.getModelController(PartyControl.class);
                var party = partyControl.getPartyByName(partyName);

                if(party != null) {
                    result.setPartyCancellationPolicies(cancellationPolicyControl.getPartyCancellationPolicyTransfersByParty(getUserVisit(), party));
                } else {
                    addExecutionError(ExecutionErrors.UnknownPartyName.name(), partyName);
                }
            } else {
                var cancellationKind = cancellationPolicyControl.getCancellationKindByName(cancellationKindName);

                if(cancellationKind != null) {
                    var cancellationPolicy = cancellationPolicyControl.getCancellationPolicyByName(cancellationKind, cancellationPolicyName);

                    if(cancellationPolicy != null) {
                        result.setPartyCancellationPolicies(cancellationPolicyControl.getPartyCancellationPolicyTransfersByCancellationPolicy(getUserVisit(), cancellationPolicy));
                    } else {
                        addExecutionError(ExecutionErrors.UnknownCancellationPolicyName.name(), cancellationKindName, cancellationPolicyName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownCancellationKindName.name(), cancellationKindName);
                }
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }

        return result;
    }

}
