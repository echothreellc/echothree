// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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

package com.echothree.control.user.warehouse.server.command;

import com.echothree.control.user.warehouse.common.edit.LocationCapacityEdit;
import com.echothree.control.user.warehouse.common.edit.WarehouseEditFactory;
import com.echothree.control.user.warehouse.common.form.EditLocationCapacityForm;
import com.echothree.control.user.warehouse.common.result.EditLocationCapacityResult;
import com.echothree.control.user.warehouse.common.result.WarehouseResultFactory;
import com.echothree.control.user.warehouse.common.spec.LocationCapacitySpec;
import com.echothree.model.control.uom.server.UomControl;
import com.echothree.model.control.warehouse.server.WarehouseControl;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKind;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.warehouse.server.entity.Location;
import com.echothree.model.data.warehouse.server.entity.LocationCapacity;
import com.echothree.model.data.warehouse.server.entity.Warehouse;
import com.echothree.model.data.warehouse.server.value.LocationCapacityValue;
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

public class EditLocationCapacityCommand
        extends BaseEditCommand<LocationCapacitySpec, LocationCapacityEdit> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("WarehouseName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LocationName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("UnitOfMeasureKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("UnitOfMeasureTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Capacity", FieldType.UNSIGNED_LONG, true, null, null)
                ));
    }
    
    /** Creates a new instance of EditLocationCapacityCommand */
    public EditLocationCapacityCommand(UserVisitPK userVisitPK, EditLocationCapacityForm form) {
        super(userVisitPK, form, null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var warehouseControl = (WarehouseControl)Session.getModelController(WarehouseControl.class);
        EditLocationCapacityResult result = WarehouseResultFactory.getEditLocationCapacityResult();
        String warehouseName = spec.getWarehouseName();
        Warehouse warehouse = warehouseControl.getWarehouseByName(warehouseName);
        
        if(warehouse != null) {
            String locationName = spec.getLocationName();
            Location location = warehouseControl.getLocationByName(warehouse.getParty(), locationName);
            
            if(location != null) {
                var uomControl = (UomControl)Session.getModelController(UomControl.class);
                String unitOfMeasureKindName = spec.getUnitOfMeasureKindName();
                UnitOfMeasureKind unitOfMeasureKind = uomControl.getUnitOfMeasureKindByName(unitOfMeasureKindName);
                
                if(unitOfMeasureKind != null) {
                    String unitOfMeasureTypeName = spec.getUnitOfMeasureTypeName();
                    UnitOfMeasureType unitOfMeasureType = uomControl.getUnitOfMeasureTypeByName(unitOfMeasureKind, unitOfMeasureTypeName);
                    
                    if(unitOfMeasureType != null) {
                        if(editMode.equals(EditMode.LOCK)) {
                            LocationCapacity locationCapacity = warehouseControl.getLocationCapacity(location, unitOfMeasureType);
                            
                            if(locationCapacity != null) {
                                result.setLocationCapacity(warehouseControl.getLocationCapacityTransfer(getUserVisit(), locationCapacity));
                                
                                if(lockEntity(location)) {
                                    LocationCapacityEdit edit = WarehouseEditFactory.getLocationCapacityEdit();
                                    
                                    result.setEdit(edit);
                                    edit.setCapacity(locationCapacity.getCapacity().toString());
                                } else {
                                    addExecutionError(ExecutionErrors.EntityLockFailed.name());
                                }
                                
                                result.setEntityLock(getEntityLockTransfer(location));
                            } else {
                                addExecutionError(ExecutionErrors.UnknownLocationCapacity.name());
                            }
                        } else if(editMode.equals(EditMode.UPDATE)) {
                            LocationCapacityValue locationCapacityValue = warehouseControl.getLocationCapacityValueForUpdate(location, unitOfMeasureType);
                            
                            if(locationCapacityValue != null) {
                                if(lockEntityForUpdate(location)) {
                                    try {
                                        locationCapacityValue.setCapacity(Long.valueOf(edit.getCapacity()));
                                        
                                        warehouseControl.updateLocationCapacityFromValue(locationCapacityValue, getPartyPK());
                                    } finally {
                                        unlockEntity(location);
                                    }
                                } else {
                                    addExecutionError(ExecutionErrors.EntityLockStale.name());
                                }
                            } else {
                                addExecutionError(ExecutionErrors.UnknownLocationCapacity.name());
                            }
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownUnitOfMeasureTypeName.name(), unitOfMeasureTypeName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownUnitOfMeasureKindName.name(), unitOfMeasureKindName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLocationName.name(), locationName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownWarehouseName.name(), warehouseName);
        }
        
        return result;
    }
    
}
