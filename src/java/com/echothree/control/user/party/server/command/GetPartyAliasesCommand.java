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
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetPartyAliasesCommand
        extends BasePaginatedMultipleEntitiesCommand<PartyAlias, GetPartyAliasesForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("PartyName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("PartyTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("PartyAliasTypeName", FieldType.ENTITY_NAME, false, null, null)
        );
    }

    @Inject
    PartyControl partyControl;

    @Inject
    PartyAliasTypeLogic partyAliasTypeLogic;

    @Inject
    PartyLogic partyLogic;
    
    /** Creates a new instance of GetPartyAliasesCommand */
    public GetPartyAliasesCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }

    @Override
    protected CommandSecurityDefinition getCommandSecurityDefinition() {
        return new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(PartyAliasUtil.getInstance().getSecurityRoleGroupNameBySpecs(form, form), SecurityRoles.List.name())
                ))
        ));
    }

    private Party party;
    private PartyAliasType partyAliasType;

    @Override
    protected void handleForm() {
        var partyName = form.getPartyName();
        var partyTypeName = form.getPartyTypeName();
        var partyAliasTypeName = form.getPartyAliasTypeName();
        // Must specify either PartyName or PartyTypeName + PartyAliasTypeName
        var parameterOption1 = (partyName != null) && (partyTypeName == null) && (partyAliasTypeName == null );
        var parameterOption2 = (partyName == null) && (partyTypeName != null) && (partyAliasTypeName != null );
        var parameterCount = (parameterOption1 ? 1 : 0) + (parameterOption2 ? 1 : 0);

        if(parameterCount == 1) {
            if(parameterOption1) {
                party = partyLogic.getPartyByName(this, form.getPartyName());
            } else {
                partyAliasType = partyAliasTypeLogic.getPartyAliasTypeByName(this, partyTypeName, partyAliasTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }
    }

    @Override
    protected Long getTotalEntities() {
        Long totalEntities = null;

        if(!hasExecutionErrors()) {
            if(party != null) {
                totalEntities = partyControl.countPartyAliasesByParty(party);
            } else {
                totalEntities = partyControl.countPartyAliasesByPartyAliasType(partyAliasType);
            }
        }

        return totalEntities;
    }

    @Override
    protected Collection<PartyAlias> getEntities() {
        Collection<PartyAlias> partyAliases = null;

        if(!hasExecutionErrors()) {
            if(party != null) {
                partyAliases = partyControl.getPartyAliasesByParty(party);
            } else {
                partyAliases = partyControl.getPartyAliasesByPartyAliasType(partyAliasType);
            }
        }

        return partyAliases;
    }

    @Override
    protected BaseResult getResult(Collection<PartyAlias> entities) {
        var result = PartyResultFactory.getGetPartyAliasesResult();

        if(entities != null) {
            if(session.hasLimit(PartyAliasFactory.class)) {
                result.setPartyAliasCount(getTotalEntities());
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
