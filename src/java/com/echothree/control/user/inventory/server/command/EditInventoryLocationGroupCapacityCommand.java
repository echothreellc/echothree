// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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
import com.echothree.control.user.inventory.common.form.EditInventoryLocationGroupCapacityForm;
import com.echothree.control.user.inventory.common.result.EditInventoryLocationGroupCapacityResult;
import com.echothree.control.user.inventory.common.result.InventoryResultFactory;
import com.echothree.control.user.inventory.common.spec.InventoryLocationGroupCapacitySpec;
import com.echothree.model.control.inventory.server.control.InventoryControl;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.control.warehouse.server.control.WarehouseControl;
import com.echothree.model.data.inventory.server.entity.InventoryLocationGroup;
import com.echothree.model.data.inventory.server.entity.InventoryLocationGroupCapacity;
import com.echothree.model.data.inventory.server.value.InventoryLocationGroupCapacityValue;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKind;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.warehouse.server.entity.Warehouse;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.server.control.BaseEditCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditInventoryLocationGroupCapacityCommand
        extends BaseEditCommand<InventoryLocationGroupCapacitySpec, InventoryLocationGroupCapacityEdit> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("WarehouseName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("InventoryLocationGroupName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("UnitOfMeasureKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("UnitOfMeasureTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Capacity", FieldType.UNSIGNED_LONG, true, null, null)
                ));
    }
    
    /** Creates a new instance of EditInventoryLocationGroupCapacityCommand */
    public EditInventoryLocationGroupCapacityCommand(UserVisitPK userVisitPK, EditInventoryLocationGroupCapacityForm form) {
        super(userVisitPK, form, null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var warehouseControl = Session.getModelController(WarehouseControl.class);
        EditInventoryLocationGroupCapacityResult result = InventoryResultFactory.getEditInventoryLocationGroupCapacityResult();
        String warehouseName = spec.getWarehouseName();
        Warehouse warehouse = warehouseControl.getWarehouseByName(warehouseName);
        
        if(warehouse != null) {
            var inventoryControl = Session.getModelController(InventoryControl.class);
            String inventoryLocationGroupName = spec.getInventoryLocationGroupName();
            InventoryLocationGroup inventoryLocationGroup = inventoryControl.getInventoryLocationGroupByName(warehouse.getParty(), inventoryLocationGroupName);
            
            if(inventoryLocationGroup != null) {
                var uomControl = Session.getModelController(UomControl.class);
                String unitOfMeasureKindName = spec.getUnitOfMeasureKindName();
                UnitOfMeasureKind unitOfMeasureKind = uomControl.getUnitOfMeasureKindByName(unitOfMeasureKindName);
                
                if(unitOfMeasureKind != null) {
                    String unitOfMeasureTypeName = spec.getUnitOfMeasureTypeName();
                    UnitOfMeasureType unitOfMeasureType = uomControl.getUnitOfMeasureTypeByName(unitOfMeasureKind, unitOfMeasureTypeName);
                    
                    if(unitOfMeasureType != null) {
                        if(editMode.equals(EditMode.LOCK)) {
                            InventoryLocationGroupCapacity inventoryLocationGroupCapacity = inventoryControl.getInventoryLocationGroupCapacity(inventoryLocationGroup, unitOfMeasureType);
                            
                            if(inventoryLocationGroupCapacity != null) {
                                result.setInventoryLocationGroupCapacity(inventoryControl.getInventoryLocationGroupCapacityTransfer(getUserVisit(), inventoryLocationGroupCapacity));
                                
                                if(lockEntity(inventoryLocationGroup)) {
                                    InventoryLocationGroupCapacityEdit edit = InventoryEditFactory.getInventoryLocationGroupCapacityEdit();
                                    
                                    result.setEdit(edit);
                                    edit.setCapacity(inventoryLocationGroupCapacity.getCapacity().toString());
                                } else {
                                    addExecutionError(ExecutionErrors.EntityLockFailed.name());
                                }
                                
                                result.setEntityLock(getEntityLockTransfer(inventoryLocationGroup));
                            } else {
                                addExecutionError(ExecutionErrors.UnknownInventoryLocationGroupCapacity.name());
                            }
                        } else if(editMode.equals(EditMode.UPDATE)) {
                            InventoryLocationGroupCapacityValue inventoryLocationGroupCapacityValue = inventoryControl.getInventoryLocationGroupCapacityValueForUpdate(inventoryLocationGroup, unitOfMeasureType);
                            
                            if(inventoryLocationGroupCapacityValue != null) {
                                if(lockEntityForUpdate(inventoryLocationGroup)) {
                                    try {
                                        inventoryLocationGroupCapacityValue.setCapacity(Long.valueOf(edit.getCapacity()));
                                        
                                        inventoryControl.updateInventoryLocationGroupCapacityFromValue(inventoryLocationGroupCapacityValue, getPartyPK());
                                    } finally {
                                        unlockEntity(inventoryLocationGroup);
                                    }
                                } else {
                                    addExecutionError(ExecutionErrors.EntityLockStale.name());
                                }
                            } else {
                                addExecutionError(ExecutionErrors.UnknownInventoryLocationGroupCapacity.name());
                            }
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownUnitOfMeasureTypeName.name(), unitOfMeasureTypeName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownUnitOfMeasureKindName.name(), unitOfMeasureKindName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownInventoryLocationGroupName.name(), inventoryLocationGroupName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownWarehouseName.name(), warehouseName);
        }
        
        return result;
    }
    
}
