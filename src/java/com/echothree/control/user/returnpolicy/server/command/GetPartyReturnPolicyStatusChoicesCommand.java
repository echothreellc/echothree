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

package com.echothree.control.user.returnpolicy.server.command;

import com.echothree.control.user.returnpolicy.common.form.GetPartyReturnPolicyStatusChoicesForm;
import com.echothree.control.user.returnpolicy.common.result.GetPartyReturnPolicyStatusChoicesResult;
import com.echothree.control.user.returnpolicy.common.result.ReturnPolicyResultFactory;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.returnpolicy.server.ReturnPolicyControl;
import com.echothree.model.control.returnpolicy.server.logic.PartyReturnPolicyLogic;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.returnpolicy.server.entity.PartyReturnPolicy;
import com.echothree.model.data.returnpolicy.server.entity.ReturnKind;
import com.echothree.model.data.returnpolicy.server.entity.ReturnPolicy;
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

public class GetPartyReturnPolicyStatusChoicesCommand
        extends BaseSimpleCommand<GetPartyReturnPolicyStatusChoicesForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                    new SecurityRoleDefinition(SecurityRoleGroups.PartyReturnPolicyStatus.name(), SecurityRoles.Choices.name())
                    )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PartyName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ReturnKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ReturnPolicyName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("DefaultPartyReturnPolicyStatusChoice", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("AllowNullChoice", FieldType.BOOLEAN, true, null, null)
                ));
    }
    
    /** Creates a new instance of GetPartyReturnPolicyStatusChoicesCommand */
    public GetPartyReturnPolicyStatusChoicesCommand(UserVisitPK userVisitPK, GetPartyReturnPolicyStatusChoicesForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);
        GetPartyReturnPolicyStatusChoicesResult result = ReturnPolicyResultFactory.getGetPartyReturnPolicyStatusChoicesResult();
        String partyName = form.getPartyName();
        Party party = partyControl.getPartyByName(partyName);

        if(party != null) {
            ReturnPolicyControl returnPolicyControl = (ReturnPolicyControl)Session.getModelController(ReturnPolicyControl.class);
            String returnKindName = form.getReturnKindName();
            ReturnKind returnKind = returnPolicyControl.getReturnKindByName(returnKindName);

            if(returnKind != null) {
                String returnPolicyName = form.getReturnPolicyName();
                ReturnPolicy returnPolicy = returnPolicyControl.getReturnPolicyByName(returnKind, returnPolicyName);

                if(returnPolicy != null) {
                    PartyReturnPolicy partyReturnPolicy = returnPolicyControl.createPartyReturnPolicy(party, returnPolicy, getPartyPK());

                    if(partyReturnPolicy != null) {
                        String defaultPartyReturnPolicyStatusChoice = form.getDefaultPartyReturnPolicyStatusChoice();
                        boolean allowNullChoice = Boolean.parseBoolean(form.getAllowNullChoice());

                        result.setPartyReturnPolicyStatusChoices(PartyReturnPolicyLogic.getInstance().getPartyReturnPolicyStatusChoices(defaultPartyReturnPolicyStatusChoice,
                                getPreferredLanguage(), allowNullChoice, partyReturnPolicy, getPartyPK()));
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
