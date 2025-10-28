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

package com.echothree.control.user.inventory.server.command;

import com.echothree.control.user.inventory.common.form.GetInventoryAdjustmentTypeForm;
import com.echothree.control.user.inventory.common.result.InventoryResultFactory;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.inventory.server.control.InventoryAdjustmentTypeControl;
import com.echothree.model.control.inventory.server.logic.InventoryAdjustmentTypeLogic;
import com.echothree.model.data.inventory.server.entity.InventoryAdjustmentType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import com.echothree.util.server.persistence.Session;
import java.util.List;

public class GetInventoryAdjustmentTypeCommand
        extends BaseSingleEntityCommand<InventoryAdjustmentType, GetInventoryAdjustmentTypeForm> {

    // No COMMAND_SECURITY_DEFINITION, anyone may execute this command.
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("InventoryAdjustmentTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null)
        );
    }
    
    /** Creates a new instance of GetInventoryAdjustmentTypeCommand */
    public GetInventoryAdjustmentTypeCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }

    @Override
    protected InventoryAdjustmentType getEntity() {
        var inventoryAdjustmentType = InventoryAdjustmentTypeLogic.getInstance().getInventoryAdjustmentTypeByUniversalSpec(this, form, true);

        if(inventoryAdjustmentType != null) {
            sendEvent(inventoryAdjustmentType.getPrimaryKey(), EventTypes.READ, null, null, getPartyPK());
        }

        return inventoryAdjustmentType;
    }

    @Override
    protected BaseResult getResult(InventoryAdjustmentType itemAliasType) {
        var inventoryAdjustmentTypeControl = Session.getModelController(InventoryAdjustmentTypeControl.class);
        var result = InventoryResultFactory.getGetInventoryAdjustmentTypeResult();

        if(itemAliasType != null) {
            result.setInventoryAdjustmentType(inventoryAdjustmentTypeControl.getInventoryAdjustmentTypeTransfer(getUserVisit(), itemAliasType));
        }

        return result;
    }
    
}
