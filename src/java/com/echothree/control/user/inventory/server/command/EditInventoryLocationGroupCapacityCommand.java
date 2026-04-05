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
import com.echothree.control.user.inventory.common.edit.InventoryLocationGroupCapacityEdit;
import com.echothree.control.user.inventory.common.result.EditInventoryLocationGroupCapacityResult;
import com.echothree.control.user.inventory.common.result.InventoryResultFactory;
import com.echothree.control.user.inventory.common.spec.InventoryLocationGroupCapacitySpec;
import com.echothree.model.control.inventory.server.control.InventoryControl;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.control.warehouse.server.control.WarehouseControl;
import com.echothree.model.data.inventory.server.entity.InventoryLocationGroup;
import com.echothree.model.data.inventory.server.entity.InventoryLocationGroupCapacity;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class EditInventoryLocationGroupCapacityCommand
        extends BaseAbstractEditCommand<InventoryLocationGroupCapacitySpec, InventoryLocationGroupCapacityEdit, EditInventoryLocationGroupCapacityResult, InventoryLocationGroupCapacity, InventoryLocationGroup> {

    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("WarehouseName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("InventoryLocationGroupName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("UnitOfMeasureKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("UnitOfMeasureTypeName", FieldType.ENTITY_NAME, true, null, null)
        );

        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("Capacity", FieldType.UNSIGNED_LONG, true, null, null)
        );
    }

    /** Creates a new instance of EditInventoryLocationGroupCapacityCommand */
    public EditInventoryLocationGroupCapacityCommand() {
        super(null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Inject
    InventoryControl inventoryControl;

    @Inject
    UomControl uomControl;

    @Inject
    WarehouseControl warehouseControl;

    @Override
    public EditInventoryLocationGroupCapacityResult getResult() {
        return InventoryResultFactory.getEditInventoryLocationGroupCapacityResult();
    }

    @Override
    public InventoryLocationGroupCapacityEdit getEdit() {
        return InventoryEditFactory.getInventoryLocationGroupCapacityEdit();
    }

    @Override
    public InventoryLocationGroupCapacity getEntity(EditInventoryLocationGroupCapacityResult result) {
        InventoryLocationGroupCapacity inventoryLocationGroupCapacity = null;
        var warehouseName = spec.getWarehouseName();
        var warehouse = warehouseControl.getWarehouseByName(warehouseName);

        if(warehouse != null) {
            var inventoryLocationGroupName = spec.getInventoryLocationGroupName();
            var inventoryLocationGroup = inventoryControl.getInventoryLocationGroupByName(warehouse.getParty(), inventoryLocationGroupName);

            if(inventoryLocationGroup != null) {
                var unitOfMeasureKindName = spec.getUnitOfMeasureKindName();
                var unitOfMeasureKind = uomControl.getUnitOfMeasureKindByName(unitOfMeasureKindName);

                if(unitOfMeasureKind != null) {
                    var unitOfMeasureTypeName = spec.getUnitOfMeasureTypeName();
                    var unitOfMeasureType = uomControl.getUnitOfMeasureTypeByName(unitOfMeasureKind, unitOfMeasureTypeName);

                    if(unitOfMeasureType != null) {
                        inventoryLocationGroupCapacity = inventoryControl.getInventoryLocationGroupCapacity(inventoryLocationGroup,
                                unitOfMeasureType, editModeToEntityPermission(editMode));

                        if(inventoryLocationGroupCapacity == null) {
                            addExecutionError(ExecutionErrors.UnknownInventoryLocationGroupCapacity.name(),
                                    warehouseName, inventoryLocationGroupName, unitOfMeasureKindName, unitOfMeasureTypeName);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownUnitOfMeasureTypeName.name(),
                                unitOfMeasureKindName, unitOfMeasureTypeName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownUnitOfMeasureKindName.name(), unitOfMeasureKindName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownInventoryLocationGroupName.name(), warehouseName, inventoryLocationGroupName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownWarehouseName.name(), warehouseName);
        }

        return inventoryLocationGroupCapacity;
    }

    @Override
    public InventoryLocationGroup getLockEntity(InventoryLocationGroupCapacity inventoryLocationGroupCapacity) {
        return inventoryLocationGroupCapacity.getInventoryLocationGroup();
    }

    @Override
    public void fillInResult(EditInventoryLocationGroupCapacityResult result, InventoryLocationGroupCapacity inventoryLocationGroupCapacity) {
        result.setInventoryLocationGroupCapacity(inventoryControl.getInventoryLocationGroupCapacityTransfer(getUserVisit(), inventoryLocationGroupCapacity));
    }

    @Override
    public void doLock(InventoryLocationGroupCapacityEdit edit, InventoryLocationGroupCapacity inventoryLocationGroupCapacity) {
        edit.setCapacity(inventoryLocationGroupCapacity.getCapacity().toString());
    }

    @Override
    public void doUpdate(InventoryLocationGroupCapacity inventoryLocationGroupCapacity) {
        var inventoryLocationGroupCapacityValue = inventoryLocationGroupCapacity.getInventoryLocationGroupCapacityValue().clone();

        inventoryLocationGroupCapacityValue.setCapacity(Long.valueOf(edit.getCapacity()));

        inventoryControl.updateInventoryLocationGroupCapacityFromValue(inventoryLocationGroupCapacityValue, getPartyPK());
    }

}
