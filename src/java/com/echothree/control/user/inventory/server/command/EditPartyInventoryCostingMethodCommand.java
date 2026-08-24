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

import com.echothree.control.user.inventory.common.edit.InventoryEditFactory;
import com.echothree.control.user.inventory.common.edit.PartyInventoryCostingMethodEdit;
import com.echothree.control.user.inventory.common.result.EditPartyInventoryCostingMethodResult;
import com.echothree.control.user.inventory.common.result.InventoryResultFactory;
import com.echothree.control.user.party.common.spec.PartyUniversalSpec;
import com.echothree.model.control.inventory.server.control.InventoryCostingMethodControl;
import com.echothree.model.control.inventory.server.logic.InventoryCostingMethodLogic;
import com.echothree.model.control.inventory.server.logic.PartyInventoryCostingMethodLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.logic.PartyLogic;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.inventory.server.entity.InventoryCostingMethod;
import com.echothree.model.data.inventory.server.entity.PartyInventoryCostingMethod;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class EditPartyInventoryCostingMethodCommand
        extends BaseAbstractEditCommand<PartyUniversalSpec, PartyInventoryCostingMethodEdit,
        EditPartyInventoryCostingMethodResult, PartyInventoryCostingMethod, Party> {

    private static final CommandSecurityDefinition COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
            new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
            new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                    new SecurityRoleDefinition(SecurityRoleGroups.InventoryCostingMethod.name(), SecurityRoles.Edit.name())
            ))
    ));

    private static final List<FieldDefinition> SPEC_FIELD_DEFINITIONS = List.of(
            new FieldDefinition("PartyName", FieldType.ENTITY_NAME, false, null, null),
            new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
            new FieldDefinition("Uuid", FieldType.UUID, false, null, null)
    );

    private static final List<FieldDefinition> EDIT_FIELD_DEFINITIONS = List.of(
            new FieldDefinition("InventoryCostingMethodName", FieldType.ENTITY_NAME, true, null, null)
    );

    @Inject
    InventoryCostingMethodControl inventoryCostingMethodControl;

    @Inject
    InventoryCostingMethodLogic inventoryCostingMethodLogic;

    @Inject
    PartyInventoryCostingMethodLogic partyInventoryCostingMethodLogic;

    @Inject
    PartyLogic partyLogic;

    private Party party;
    private InventoryCostingMethod inventoryCostingMethod;

    public EditPartyInventoryCostingMethodCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditPartyInventoryCostingMethodResult getResult() {
        return InventoryResultFactory.getEditPartyInventoryCostingMethodResult();
    }

    @Override
    public PartyInventoryCostingMethodEdit getEdit() {
        return InventoryEditFactory.getPartyInventoryCostingMethodEdit();
    }

    @Override
    public PartyInventoryCostingMethod getEntity(EditPartyInventoryCostingMethodResult result) {
        PartyInventoryCostingMethod partyInventoryCostingMethod = null;

        party = partyLogic.getPartyByName(this, spec.getPartyName(), spec);

        if(!hasExecutionErrors()) {
            partyInventoryCostingMethod = partyInventoryCostingMethodLogic.getPartyInventoryCostingMethod(this, party,
                    false, getPartyPK(), editModeToEntityPermission(editMode));
        }

        return partyInventoryCostingMethod;
    }

    @Override
    public Party getLockEntity(PartyInventoryCostingMethod partyInventoryCostingMethod) {
        return party;
    }

    @Override
    public void fillInResult(EditPartyInventoryCostingMethodResult result,
            PartyInventoryCostingMethod partyInventoryCostingMethod) {
        result.setPartyInventoryCostingMethod(inventoryCostingMethodControl.getPartyInventoryCostingMethodTransfer(
                getUserVisit(), partyInventoryCostingMethod));
    }

    @Override
    public void doLock(PartyInventoryCostingMethodEdit edit, PartyInventoryCostingMethod partyInventoryCostingMethod) {
        edit.setInventoryCostingMethodName(partyInventoryCostingMethod.getInventoryCostingMethod().getLastDetail()
                .getInventoryCostingMethodName());
    }

    @Override
    public void canUpdate(PartyInventoryCostingMethod partyInventoryCostingMethod) {
        inventoryCostingMethod = inventoryCostingMethodLogic.getInventoryCostingMethodByName(this,
                edit.getInventoryCostingMethodName());
    }

    @Override
    public void doUpdate(PartyInventoryCostingMethod partyInventoryCostingMethod) {
        var partyInventoryCostingMethodValue = inventoryCostingMethodControl
                .getPartyInventoryCostingMethodValue(partyInventoryCostingMethod);

        partyInventoryCostingMethodValue.setInventoryCostingMethodPK(inventoryCostingMethod.getPrimaryKey());
        partyInventoryCostingMethodLogic.updatePartyInventoryCostingMethodFromValue(partyInventoryCostingMethodValue,
                getPartyPK());
    }
}
