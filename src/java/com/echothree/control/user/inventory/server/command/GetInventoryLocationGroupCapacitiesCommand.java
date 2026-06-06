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

import com.echothree.control.user.inventory.common.form.GetInventoryLocationGroupCapacitiesForm;
import com.echothree.control.user.inventory.common.result.InventoryResultFactory;
import com.echothree.model.control.inventory.server.control.InventoryControl;
import com.echothree.model.control.warehouse.server.logic.WarehouseLogic;
import com.echothree.model.data.inventory.server.entity.InventoryLocationGroup;
import com.echothree.model.data.inventory.server.entity.InventoryLocationGroupCapacity;
import com.echothree.model.data.inventory.server.factory.InventoryLocationGroupCapacityFactory;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetInventoryLocationGroupCapacitiesCommand
        extends BasePaginatedMultipleEntitiesCommand<InventoryLocationGroupCapacity, GetInventoryLocationGroupCapacitiesForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("WarehouseName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("InventoryLocationGroupName", FieldType.ENTITY_NAME, true, null, null)
        );
    }
    
    /** Creates a new instance of GetInventoryLocationGroupCapacitiesCommand */
    public GetInventoryLocationGroupCapacitiesCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }

    @Inject
    InventoryControl inventoryControl;

    @Inject
    WarehouseLogic warehouseLogic;

    private InventoryLocationGroup inventoryLocationGroup;

    @Override
    protected void handleForm() {
        var warehouse = warehouseLogic.getWarehouseByName(this, form.getWarehouseName(), null, null, false);

        if(!hasExecutionErrors()) {
            var inventoryLocationGroupName = form.getInventoryLocationGroupName();

            inventoryLocationGroup = inventoryControl.getInventoryLocationGroupByName(warehouse.getParty(), inventoryLocationGroupName);

            if(inventoryLocationGroup == null) {
                addExecutionError(ExecutionErrors.UnknownInventoryLocationGroupName.name(), warehouse.getWarehouseName(), inventoryLocationGroupName);
            }
        }
    }

    @Override
    protected Long getTotalEntities() {
        return hasExecutionErrors() ? null : inventoryControl.countInventoryLocationGroupCapacitiesByInventoryLocationGroup(inventoryLocationGroup);
    }

    @Override
    protected Collection<InventoryLocationGroupCapacity> getEntities() {
        return hasExecutionErrors() ? null : inventoryControl.getInventoryLocationGroupCapacitiesByInventoryLocationGroup(inventoryLocationGroup);
    }

    @Override
    protected BaseResult getResult(Collection<InventoryLocationGroupCapacity> entities) {
        var result = InventoryResultFactory.getGetInventoryLocationGroupCapacitiesResult();

        if(entities != null) {
            var userVisit = getUserVisit();

            result.setInventoryLocationGroup(inventoryControl.getInventoryLocationGroupTransfer(userVisit, inventoryLocationGroup));

            if(session.hasLimit(InventoryLocationGroupCapacityFactory.class)) {
                result.setInventoryLocationGroupCapacityCount(getTotalEntities());
            }

            result.setInventoryLocationGroupCapacities(inventoryControl.getInventoryLocationGroupCapacityTransfersByInventoryLocationGroup(userVisit, inventoryLocationGroup));
        }

        return result;
    }
    
}
