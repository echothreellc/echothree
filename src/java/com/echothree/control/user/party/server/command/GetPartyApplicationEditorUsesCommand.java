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

import com.echothree.control.user.party.common.form.GetPartyApplicationEditorUsesForm;
import com.echothree.control.user.party.common.result.PartyResultFactory;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyApplicationEditorUseControl;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.party.server.logic.PartyLogic;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.party.server.entity.PartyApplicationEditorUse;
import com.echothree.model.data.party.server.factory.PartyApplicationEditorUseFactory;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetPartyApplicationEditorUsesCommand
        extends BasePaginatedMultipleEntitiesCommand<PartyApplicationEditorUse, GetPartyApplicationEditorUsesForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.PartyApplicationEditorUse.name(), SecurityRoles.List.name())
                ))
        ));
        
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("PartyName", FieldType.ENTITY_NAME, false, null, null)
        );
    }
    
    @Inject
    PartyApplicationEditorUseControl partyApplicationEditorUseControl;

    @Inject
    PartyControl partyControl;

    @Inject
    PartyLogic partyLogic;

    /** Creates a new instance of GetPartyApplicationEditorUsesCommand */
    public GetPartyApplicationEditorUsesCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }
    
    Party party;

    @Override
    protected void handleForm() {
        var partyName = form.getPartyName();

        party = partyName == null ? getParty() : partyLogic.getPartyByName(this, partyName);
    }

    @Override
    protected Long getTotalEntities() {
        return hasExecutionErrors() ? null : partyApplicationEditorUseControl.countPartyApplicationEditorUsesByParty(party);
    }

    @Override
    protected Collection<PartyApplicationEditorUse> getEntities() {
        return hasExecutionErrors() ? null : partyApplicationEditorUseControl.getPartyApplicationEditorUsesByParty(party);
    }

    @Override
    protected BaseResult getResult(Collection<PartyApplicationEditorUse> entities) {
        var result = PartyResultFactory.getGetPartyApplicationEditorUsesResult();

        if(entities != null) {
            result.setParty(partyControl.getPartyTransfer(getUserVisit(), party));

            if(session.hasLimit(PartyApplicationEditorUseFactory.class)) {
                result.setPartyApplicationEditorUseCount(getTotalEntities());
            }

            result.setPartyApplicationEditorUses(partyApplicationEditorUseControl.getPartyApplicationEditorUseTransfers(new ArrayList<>(entities), getUserVisit()));
        }

        return result;
    }

}
