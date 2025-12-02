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

package com.echothree.control.user.party.server.command;

import com.echothree.control.user.party.common.form.GetPartyAliasForm;
import com.echothree.control.user.party.common.result.PartyResultFactory;
import com.echothree.control.user.party.server.command.util.PartyAliasUtil;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.party.server.logic.PartyAliasTypeLogic;
import com.echothree.model.control.party.server.logic.PartyLogic;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.party.server.entity.PartyAlias;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class GetPartyAliasCommand
        extends BaseSingleEntityCommand<PartyAlias, GetPartyAliasForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("PartyName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("PartyTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("PartyAliasTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("Alias", FieldType.ENTITY_NAME, false, null, null)
        );
    }
    
    /** Creates a new instance of GetPartyAliasCommand */
    public GetPartyAliasCommand() {
        super(null, FORM_FIELD_DEFINITIONS, false);
    }

    @Override
    protected CommandSecurityDefinition getCommandSecurityDefinition() {
        return new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(PartyAliasUtil.getInstance().getSecurityRoleGroupNameBySpecs(form, form), SecurityRoles.Review.name())
                )))
        )));
    }

    @Override
    protected PartyAlias getEntity() {
        var partyName = form.getPartyName();
        var partyTypeName = form.getPartyTypeName();
        var partyAliasTypeName = form.getPartyAliasTypeName();
        var alias = form.getAlias();
        // Must specify either PartyName + PartyAliasTypeName or PartyTypeName + PartyAliasTypeName + Alias
        var parameterOption1 = (partyName != null) && (partyTypeName == null) && (partyAliasTypeName != null ) && (alias == null);
        var parameterOption2 = (partyName == null) && (partyTypeName != null) && (partyAliasTypeName != null ) && (alias != null);
        var parameterCount = (parameterOption1 ? 1 : 0) + (parameterOption2 ? 1 : 0);
        PartyAlias partyAlias = null;

        if(parameterCount == 1) {
            var partyControl = Session.getModelController(PartyControl.class);

            if(parameterOption1) {
                var party = PartyLogic.getInstance().getPartyByName(this, partyName);

                if(!hasExecutionErrors()) {
                    var partyType = party.getLastDetail().getPartyType();
                    var partyAliasType = PartyAliasTypeLogic.getInstance().getPartyAliasTypeByName(this, partyType, partyAliasTypeName);

                    if(!hasExecutionErrors()) {
                        partyAlias = partyControl.getPartyAlias(party, partyAliasType);

                        if(partyAlias == null) {
                            addExecutionError(ExecutionErrors.UnknownPartyAlias.name(), party.getLastDetail().getPartyName(),
                                    partyAliasType.getLastDetail().getPartyAliasTypeName());
                        }
                    }
                }
            } else {
                var partyAliasType = PartyAliasTypeLogic.getInstance().getPartyAliasTypeByName(this, partyTypeName, partyAliasTypeName);

                if(!hasExecutionErrors()) {
                    partyAlias = partyControl.getPartyAliasByAlias(partyAliasType, alias);

                    if(partyAlias == null) {
                        var partyAliasTypeDetail = partyAliasType.getLastDetail();
                        addExecutionError(ExecutionErrors.UnknownPartyAliasByAlias.name(), partyAliasTypeDetail.getPartyType().getPartyTypeName(),
                                partyAliasTypeDetail.getPartyAliasTypeName(), partyAliasType.getLastDetail().getPartyAliasTypeName(), alias);
                    }
                }
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }

        return partyAlias;
    }

    @Override
    protected BaseResult getResult(PartyAlias partyAlias) {
        var result = PartyResultFactory.getGetPartyAliasResult();

        if(partyAlias != null) {
            var partyControl = Session.getModelController(PartyControl.class);

            result.setPartyAlias(partyControl.getPartyAliasTransfer(getUserVisit(), partyAlias));
        }

        return result;
    }

}
