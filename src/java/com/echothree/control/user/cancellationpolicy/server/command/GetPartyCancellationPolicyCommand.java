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

import com.echothree.control.user.cancellationpolicy.common.form.GetPartyCancellationPolicyForm;
import com.echothree.control.user.cancellationpolicy.common.result.CancellationPolicyResultFactory;
import com.echothree.model.control.cancellationpolicy.server.control.CancellationPolicyControl;
import com.echothree.model.control.cancellationpolicy.server.logic.CancellationKindLogic;
import com.echothree.model.control.cancellationpolicy.server.logic.CancellationPolicyLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.logic.PartyLogic;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetPartyCancellationPolicyCommand
        extends BaseSimpleCommand<GetPartyCancellationPolicyForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.PartyCancellationPolicy.name(), SecurityRoles.Review.name())
                ))
        ));
        
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("PartyName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("CancellationKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("CancellationPolicyName", FieldType.ENTITY_NAME, true, null, null)
        );
    }

    @Inject
    CancellationPolicyControl cancellationPolicyControl;

    @Inject
    CancellationKindLogic cancellationKindLogic;

    @Inject
    CancellationPolicyLogic cancellationPolicyLogic;

    @Inject
    PartyLogic partyLogic;

    /** Creates a new instance of GetPartyCancellationPolicyCommand */
    public GetPartyCancellationPolicyCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    @Override
    protected BaseResult execute() {
        var result = CancellationPolicyResultFactory.getGetPartyCancellationPolicyResult();
        var partyName = form.getPartyName();
        var party = partyLogic.getPartyByName(this, partyName);

        if(!hasExecutionErrors()) {
            var cancellationKindName = form.getCancellationKindName();
            var cancellationKind = cancellationKindLogic.getCancellationKindByName(this, cancellationKindName);

            if(!hasExecutionErrors()) {
                var cancellationPolicyName = form.getCancellationPolicyName();
                var cancellationPolicy = cancellationPolicyLogic.getCancellationPolicyByName(this, cancellationKind, cancellationPolicyName);

                if(!hasExecutionErrors()) {
                    var partyCancellationPolicy = cancellationPolicyControl.getPartyCancellationPolicy(party, cancellationPolicy);

                    if(partyCancellationPolicy != null) {
                        result.setPartyCancellationPolicy(cancellationPolicyControl.getPartyCancellationPolicyTransfer(getUserVisit(), partyCancellationPolicy));
                    } else {
                        addExecutionError(ExecutionErrors.UnknownPartyCancellationPolicy.name(), partyName, cancellationKindName, cancellationPolicyName);
                    }
                }
            }
        }

        return result;
    }

}
