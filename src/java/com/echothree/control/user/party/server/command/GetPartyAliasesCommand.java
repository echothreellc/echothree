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

import com.echothree.control.user.party.common.form.GetPartyAliasesForm;
import com.echothree.control.user.party.common.result.PartyResultFactory;
import com.echothree.control.user.party.server.command.util.PartyAliasUtil;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.party.server.logic.PartyAliasTypeLogic;
import com.echothree.model.control.party.server.logic.PartyLogic;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.party.server.entity.PartyAlias;
import com.echothree.model.data.party.server.entity.PartyAliasType;
import com.echothree.model.data.party.server.factory.PartyAliasFactory;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseMultipleEntitiesCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class GetPartyAliasesCommand
        extends BaseMultipleEntitiesCommand<PartyAlias, GetPartyAliasesForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("PartyName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("PartyTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("PartyAliasTypeName", FieldType.ENTITY_NAME, false, null, null)
        );
    }
    
    /** Creates a new instance of GetPartyAliasesCommand */
    public GetPartyAliasesCommand() {
        super(null, FORM_FIELD_DEFINITIONS, false);
    }

    @Override
    protected CommandSecurityDefinition getCommandSecurityDefinition() {
        return new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(PartyAliasUtil.getInstance().getSecurityRoleGroupNameBySpecs(form, form), SecurityRoles.List.name())
                )))
        )));
    }

    Party party;
    PartyAliasType partyAliasType;

    @Override
    protected Collection<PartyAlias> getEntities() {
        var partyName = form.getPartyName();
        var partyTypeName = form.getPartyTypeName();
        var partyAliasTypeName = form.getPartyAliasTypeName();
        // Must specify either PartyName or PartyTypeName + PartyAliasTypeName
        var parameterOption1 = (partyName != null) && (partyTypeName == null) && (partyAliasTypeName == null );
        var parameterOption2 = (partyName == null) && (partyTypeName != null) && (partyAliasTypeName != null );
        var parameterCount = (parameterOption1 ? 1 : 0) + (parameterOption2 ? 1 : 0);
        Collection<PartyAlias> partyAliases = null;

        if(parameterCount == 1) {
            var partyControl = Session.getModelController(PartyControl.class);

            if(parameterOption1) {
                party = PartyLogic.getInstance().getPartyByName(this, form.getPartyName());

                if(!hasExecutionErrors()) {
                    partyAliases = partyControl.getPartyAliasesByParty(party);
                }
            } else {
                partyAliasType = PartyAliasTypeLogic.getInstance().getPartyAliasTypeByName(this, partyTypeName, partyAliasTypeName);

                if(!hasExecutionErrors()) {
                    partyAliases = partyControl.getPartyAliasesByPartyAliasType(partyAliasType);
                }
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }

        return partyAliases;
    }

    @Override
    protected BaseResult getResult(Collection<PartyAlias> entities) {
        var result = PartyResultFactory.getGetPartyAliasesResult();

        if(entities != null) {
            var partyControl = Session.getModelController(PartyControl.class);

            if(session.hasLimit(PartyAliasFactory.class)) {
                if(party != null) {
                    result.setPartyAliasCount(partyControl.countPartyAliasesByParty(party));
                } else {
                    result.setPartyAliasCount(partyControl.countPartyAliasesByPartyAliasType(partyAliasType));
                }
            }

            if(party != null) {
                result.setParty(partyControl.getPartyTransfer(getUserVisit(), party));
            }

            if(partyAliasType != null) {
                result.setPartyAliasType(partyControl.getPartyAliasTypeTransfer(getUserVisit(), partyAliasType));
            }

            result.setPartyAliases(partyControl.getPartyAliasTransfers(getUserVisit(), entities));
        }

        return result;
    }

}
