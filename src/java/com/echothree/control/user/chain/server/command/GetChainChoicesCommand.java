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

package com.echothree.control.user.chain.server.command;

import com.echothree.control.user.chain.common.form.GetChainChoicesForm;
import com.echothree.control.user.chain.common.result.ChainResultFactory;
import com.echothree.control.user.chain.common.result.GetChainChoicesResult;
import com.echothree.model.control.chain.server.ChainControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.chain.server.entity.ChainKind;
import com.echothree.model.data.chain.server.entity.ChainType;
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

public class GetChainChoicesCommand
        extends BaseSimpleCommand<GetChainChoicesForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                    new SecurityRoleDefinition(SecurityRoleGroups.Chain.name(), SecurityRoles.Choices.name())
                    )))
                )));

        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ChainKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ChainTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("DefaultChainChoice", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("AllowNullChoice", FieldType.BOOLEAN, true, null, null)
                ));
    }
    
    /** Creates a new instance of GetChainChoicesCommand */
    public GetChainChoicesCommand(UserVisitPK userVisitPK, GetChainChoicesForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        ChainControl chainControl = (ChainControl)Session.getModelController(ChainControl.class);
        GetChainChoicesResult result = ChainResultFactory.getGetChainChoicesResult();
        String chainKindName = form.getChainKindName();
        ChainKind chainKind = chainControl.getChainKindByName(chainKindName);
        
        if(chainKind != null) {
            String chainTypeName = form.getChainTypeName();
            ChainType chainType = chainControl.getChainTypeByName(chainKind, chainTypeName);

            if(chainType != null) {
                String defaultChainChoice = form.getDefaultChainChoice();
                boolean allowNullChoice = Boolean.parseBoolean(form.getAllowNullChoice());

                result.setChainChoices(chainControl.getChainChoices(defaultChainChoice, getPreferredLanguage(), allowNullChoice, chainType));
            } else {
                addExecutionError(ExecutionErrors.UnknownChainTypeName.name(), chainKindName, chainTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownChainKindName.name(), chainKindName);
        }
        
        return result;
    }
    
}
