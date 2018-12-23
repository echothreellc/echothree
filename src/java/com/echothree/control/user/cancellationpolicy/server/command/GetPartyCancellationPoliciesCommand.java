// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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
import com.echothree.control.user.cancellationpolicy.common.result.GetPartyCancellationPoliciesResult;
import com.echothree.model.control.cancellationpolicy.server.CancellationPolicyControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationKind;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationPolicy;
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

public class GetPartyCancellationPoliciesCommand
        extends BaseSimpleCommand<GetPartyCancellationPoliciesForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
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
    public GetPartyCancellationPoliciesCommand(UserVisitPK userVisitPK, GetPartyCancellationPoliciesForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    @Override
    protected BaseResult execute() {
        CancellationPolicyControl cancellationPolicyControl = (CancellationPolicyControl)Session.getModelController(CancellationPolicyControl.class);
        GetPartyCancellationPoliciesResult result = CancellationPolicyResultFactory.getGetPartyCancellationPoliciesResult();
        String partyName = form.getPartyName();
        String cancellationKindName = form.getCancellationKindName();
        String cancellationPolicyName = form.getCancellationPolicyName();
        int parameterCount = (partyName != null ? 1 : 0) + (cancellationKindName != null && cancellationPolicyName != null ? 1 : 0);

        if(parameterCount == 1) {
            if(partyName != null) {
                PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);
                Party party = partyControl.getPartyByName(partyName);

                if(party != null) {
                    result.setPartyCancellationPolicies(cancellationPolicyControl.getPartyCancellationPolicyTransfersByParty(getUserVisit(), party));
                } else {
                    addExecutionError(ExecutionErrors.UnknownPartyName.name(), partyName);
                }
            } else {
                CancellationKind cancellationKind = cancellationPolicyControl.getCancellationKindByName(cancellationKindName);

                if(cancellationKind != null) {
                    CancellationPolicy cancellationPolicy = cancellationPolicyControl.getCancellationPolicyByName(cancellationKind, cancellationPolicyName);

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
