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

package com.echothree.control.user.cancellationpolicy.server.command;

import com.echothree.control.user.cancellationpolicy.common.form.GetPartyCancellationPoliciesForm;
import com.echothree.control.user.cancellationpolicy.common.result.CancellationPolicyResultFactory;
import com.echothree.model.control.cancellationpolicy.server.control.CancellationPolicyControl;
import com.echothree.model.control.cancellationpolicy.server.logic.CancellationKindLogic;
import com.echothree.model.control.cancellationpolicy.server.logic.CancellationPolicyLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.party.server.logic.PartyLogic;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationPolicy;
import com.echothree.model.data.cancellationpolicy.server.entity.PartyCancellationPolicy;
import com.echothree.model.data.cancellationpolicy.server.factory.PartyCancellationPolicyFactory;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetPartyCancellationPoliciesCommand
        extends BasePaginatedMultipleEntitiesCommand<PartyCancellationPolicy, GetPartyCancellationPoliciesForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.PartyCancellationPolicy.name(), SecurityRoles.List.name())
                ))
        ));
        
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("PartyName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("CancellationKindName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("CancellationPolicyName", FieldType.ENTITY_NAME, false, null, null)
        );
    }

    @Inject
    CancellationPolicyControl cancellationPolicyControl;

    @Inject
    PartyControl partyControl;

    @Inject
    CancellationKindLogic cancellationKindLogic;

    @Inject
    CancellationPolicyLogic cancellationPolicyLogic;

    @Inject
    PartyLogic partyLogic;

    /** Creates a new instance of GetPartyCancellationPoliciesCommand */
    public GetPartyCancellationPoliciesCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    private Party party;
    private CancellationPolicy cancellationPolicy;

    @Override
    protected void handleForm() {
        var partyName = form.getPartyName();
        var cancellationKindName = form.getCancellationKindName();
        var cancellationPolicyName = form.getCancellationPolicyName();
        var parameterCount = (partyName != null ? 1 : 0) + (cancellationKindName != null && cancellationPolicyName != null ? 1 : 0);

        if(parameterCount == 1) {
            if(partyName != null) {
                party = partyLogic.getPartyByName(this, partyName);
            } else {
                var cancellationKind = cancellationKindLogic.getCancellationKindByName(this, cancellationKindName);

                if(!hasExecutionErrors()) {
                    cancellationPolicy = cancellationPolicyLogic.getCancellationPolicyByName(this, cancellationKind, cancellationPolicyName);
                }
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }
    }

    @Override
    protected Long getTotalEntities() {
        Long total = null;

        if(!hasExecutionErrors()) {
            if(party != null) {
                total = cancellationPolicyControl.countPartyCancellationPoliciesByParty(party);
            } else {
                total = cancellationPolicyControl.countPartyCancellationPoliciesByCancellationPolicy(cancellationPolicy);
            }
        }

        return total;
    }

    @Override
    protected Collection<PartyCancellationPolicy> getEntities() {
        Collection<PartyCancellationPolicy> entities = null;

        if(!hasExecutionErrors()) {
            if(party != null) {
                entities = cancellationPolicyControl.getPartyCancellationPoliciesByParty(party);
            } else {
                entities = cancellationPolicyControl.getPartyCancellationPoliciesByCancellationPolicy(cancellationPolicy);
            }
        }

        return entities;
    }

    @Override
    protected BaseResult getResult(Collection<PartyCancellationPolicy> entities) {
        var result = CancellationPolicyResultFactory.getGetPartyCancellationPoliciesResult();

        if(entities != null) {
            var userVisit = getUserVisit();

            if(party != null) {
                result.setParty(partyControl.getPartyTransfer(userVisit, party));
            }

            if(cancellationPolicy != null) {
                result.setCancellationPolicy(cancellationPolicyControl.getCancellationPolicyTransfer(userVisit, cancellationPolicy));
            }

            if(session.hasLimit(PartyCancellationPolicyFactory.class)) {
                result.setPartyCancellationPolicyCount(getTotalEntities());
            }

            result.setPartyCancellationPolicies(cancellationPolicyControl.getPartyCancellationPolicyTransfers(userVisit, entities));
        }

        return result;
    }

}
