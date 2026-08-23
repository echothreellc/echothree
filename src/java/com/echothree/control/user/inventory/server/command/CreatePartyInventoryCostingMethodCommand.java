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

package com.echothree.control.user.inventory.server.command;

import com.echothree.control.user.inventory.common.form.CreatePartyInventoryCostingMethodForm;
import com.echothree.model.control.inventory.server.logic.InventoryCostingMethodLogic;
import com.echothree.model.control.inventory.server.logic.PartyInventoryCostingMethodLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.logic.PartyLogic;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.util.common.command.BaseResult;
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
public class CreatePartyInventoryCostingMethodCommand
        extends BaseSimpleCommand<CreatePartyInventoryCostingMethodForm> {

    private static final CommandSecurityDefinition COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
            new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
            new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                    new SecurityRoleDefinition(SecurityRoleGroups.InventoryCostingMethod.name(), SecurityRoles.Create.name())
            ))
    ));

    private static final List<FieldDefinition> FORM_FIELD_DEFINITIONS = List.of(
            new FieldDefinition("PartyName", FieldType.ENTITY_NAME, false, null, null),
            new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
            new FieldDefinition("Uuid", FieldType.UUID, false, null, null),
            new FieldDefinition("InventoryCostingMethodName", FieldType.ENTITY_NAME, true, null, null)
    );

    @Inject
    InventoryCostingMethodLogic inventoryCostingMethodLogic;

    @Inject
    PartyInventoryCostingMethodLogic partyInventoryCostingMethodLogic;

    @Inject
    PartyLogic partyLogic;

    public CreatePartyInventoryCostingMethodCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }

    @Override
    protected BaseResult execute() {
        var party = partyLogic.getPartyByName(this, form.getPartyName(), form);
        var inventoryCostingMethod = inventoryCostingMethodLogic.getInventoryCostingMethodByName(this,
                form.getInventoryCostingMethodName());

        if(!hasExecutionErrors()) {
            partyInventoryCostingMethodLogic.createPartyInventoryCostingMethod(this, party,
                    inventoryCostingMethod, getPartyPK());
        }

        return null;
    }
}
