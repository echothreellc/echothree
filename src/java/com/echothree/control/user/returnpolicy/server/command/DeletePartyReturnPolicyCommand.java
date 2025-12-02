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

package com.echothree.control.user.returnpolicy.server.command;

import com.echothree.control.user.returnpolicy.common.form.DeletePartyReturnPolicyForm;
import com.echothree.control.user.returnpolicy.common.result.ReturnPolicyResultFactory;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.returnpolicy.server.control.ReturnPolicyControl;
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
public class DeletePartyReturnPolicyCommand
        extends BaseSimpleCommand<DeletePartyReturnPolicyForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                    new SecurityRoleDefinition(SecurityRoleGroups.PartyReturnPolicy.name(), SecurityRoles.Delete.name())
                    )))
                )));

        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PartyName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ReturnKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ReturnPolicyName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }

    /** Creates a new instance of DeletePartyReturnPolicyCommand */
    public DeletePartyReturnPolicyCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    @Override
    protected BaseResult execute() {
        var partyControl = Session.getModelController(PartyControl.class);
        var result = ReturnPolicyResultFactory.getGetPartyReturnPolicyResult();
        var partyName = form.getPartyName();
        var party = partyControl.getPartyByName(partyName);

        if(party != null) {
            var returnPolicyControl = Session.getModelController(ReturnPolicyControl.class);
            var returnKindName = form.getReturnKindName();
            var returnKind = returnPolicyControl.getReturnKindByName(returnKindName);

            if(returnKind != null) {
                var returnPolicyName = form.getReturnPolicyName();
                var returnPolicy = returnPolicyControl.getReturnPolicyByName(returnKind, returnPolicyName);

                if(returnPolicy != null) {
                    var partyReturnPolicy = returnPolicyControl.getPartyReturnPolicyForUpdate(party, returnPolicy);

                    if(partyReturnPolicy != null) {
                        returnPolicyControl.deletePartyReturnPolicy(partyReturnPolicy, getPartyPK());
                    } else {
                        addExecutionError(ExecutionErrors.UnknownPartyReturnPolicy.name(), partyName, returnKindName, returnPolicyName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownReturnPolicyName.name(), returnKindName, returnPolicyName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownReturnKindName.name(), returnKindName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownPartyName.name(), partyName);
        }

        return result;
    }

}
