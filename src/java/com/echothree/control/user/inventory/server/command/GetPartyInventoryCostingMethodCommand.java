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

import com.echothree.control.user.inventory.common.form.GetPartyInventoryCostingMethodForm;
import com.echothree.control.user.inventory.common.result.InventoryResultFactory;
import com.echothree.model.control.inventory.server.control.InventoryCostingMethodControl;
import com.echothree.model.control.inventory.server.logic.PartyInventoryCostingMethodLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.logic.PartyLogic;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.inventory.server.entity.PartyInventoryCostingMethod;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetPartyInventoryCostingMethodCommand
        extends BaseSingleEntityCommand<PartyInventoryCostingMethod, GetPartyInventoryCostingMethodForm> {

    private static final CommandSecurityDefinition COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
            new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
            new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                    new SecurityRoleDefinition(SecurityRoleGroups.InventoryCostingMethod.name(), SecurityRoles.Review.name())
            ))
    ));

    private static final List<FieldDefinition> FORM_FIELD_DEFINITIONS = List.of(
            new FieldDefinition("PartyName", FieldType.ENTITY_NAME, false, null, null),
            new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
            new FieldDefinition("Uuid", FieldType.UUID, false, null, null)
    );

    @Inject
    InventoryCostingMethodControl inventoryCostingMethodControl;

    @Inject
    PartyInventoryCostingMethodLogic partyInventoryCostingMethodLogic;

    @Inject
    PartyLogic partyLogic;

    public GetPartyInventoryCostingMethodCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    @Override
    protected PartyInventoryCostingMethod getEntity() {
        var party = partyLogic.getPartyByName(this, form.getPartyName(), form);
        PartyInventoryCostingMethod partyInventoryCostingMethod = null;

        if(!hasExecutionErrors()) {
            partyInventoryCostingMethod = partyInventoryCostingMethodLogic.getPartyInventoryCostingMethod(this, party,
                    false, getPartyPK());
        }

        return partyInventoryCostingMethod;
    }

    @Override
    protected BaseResult getResult(PartyInventoryCostingMethod partyInventoryCostingMethod) {
        var result = InventoryResultFactory.getGetPartyInventoryCostingMethodResult();

        if(partyInventoryCostingMethod != null) {
            result.setPartyInventoryCostingMethod(inventoryCostingMethodControl.getPartyInventoryCostingMethodTransfer(
                    getUserVisit(), partyInventoryCostingMethod));
        }

        return result;
    }
}
