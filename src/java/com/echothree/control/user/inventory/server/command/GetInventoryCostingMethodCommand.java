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

import com.echothree.control.user.inventory.common.form.GetInventoryCostingMethodForm;
import com.echothree.control.user.inventory.common.result.InventoryResultFactory;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.inventory.server.control.InventoryCostingMethodControl;
import com.echothree.model.control.inventory.server.logic.InventoryCostingMethodLogic;
import com.echothree.model.data.inventory.server.entity.InventoryCostingMethod;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import java.util.List;
import javax.inject.Inject;
import javax.enterprise.context.Dependent;

@Dependent
public class GetInventoryCostingMethodCommand
        extends BaseSingleEntityCommand<InventoryCostingMethod, GetInventoryCostingMethodForm> {

    // No COMMAND_SECURITY_DEFINITION, anyone may execute this command.
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("InventoryCostingMethodName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null)
        );
    }

    @Inject
    InventoryCostingMethodControl inventoryCostingMethodControl;

    @Inject
    InventoryCostingMethodLogic inventoryCostingMethodLogic;

    /** Creates a new instance of GetInventoryCostingMethodCommand */
    public GetInventoryCostingMethodCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }

    @Override
    protected InventoryCostingMethod getEntity() {
        var inventoryCostingMethod = inventoryCostingMethodLogic.getInventoryCostingMethodByUniversalSpec(this, form, true);

        if(inventoryCostingMethod != null) {
            sendEvent(inventoryCostingMethod.getPrimaryKey(), EventTypes.READ, null, null, getPartyPK());
        }

        return inventoryCostingMethod;
    }

    @Override
    protected BaseResult getResult(InventoryCostingMethod itemAliasType) {
        var result = InventoryResultFactory.getGetInventoryCostingMethodResult();

        if(itemAliasType != null) {
            result.setInventoryCostingMethod(inventoryCostingMethodControl.getInventoryCostingMethodTransfer(getUserVisit(), itemAliasType));
        }

        return result;
    }

}
