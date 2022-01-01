// --------------------------------------------------------------------------------
// Copyright 2002-2022 Echo Three, LLC
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
import com.echothree.control.user.inventory.common.edit.InventoryLocationGroupVolumeEdit;
import com.echothree.control.user.inventory.common.form.EditInventoryLocationGroupVolumeForm;
import com.echothree.control.user.inventory.common.result.EditInventoryLocationGroupVolumeResult;
import com.echothree.control.user.inventory.common.result.InventoryResultFactory;
import com.echothree.control.user.inventory.common.spec.InventoryLocationGroupSpec;
import com.echothree.model.control.inventory.server.control.InventoryControl;
import com.echothree.model.control.uom.common.UomConstants;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.control.uom.server.util.Conversion;
import com.echothree.model.control.warehouse.server.control.WarehouseControl;
import com.echothree.model.data.inventory.server.entity.InventoryLocationGroup;
import com.echothree.model.data.inventory.server.entity.InventoryLocationGroupVolume;
import com.echothree.model.data.inventory.server.value.InventoryLocationGroupVolumeValue;
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

public class EditInventoryLocationGroupVolumeCommand
        extends BaseEditCommand<InventoryLocationGroupSpec, InventoryLocationGroupVolumeEdit> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("WarehouseName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("InventoryLocationGroupName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("HeightUnitOfMeasureTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("Height", FieldType.UNSIGNED_LONG, true, null, null),
                new FieldDefinition("WidthUnitOfMeasureTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("Width", FieldType.UNSIGNED_LONG, true, null, null),
                new FieldDefinition("DepthUnitOfMeasureTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("Depth", FieldType.UNSIGNED_LONG, true, null, null)
                ));
    }
    
    /** Creates a new instance of EditInventoryLocationGroupVolumeCommand */
    public EditInventoryLocationGroupVolumeCommand(UserVisitPK userVisitPK, EditInventoryLocationGroupVolumeForm form) {
        super(userVisitPK, form, null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var warehouseControl = Session.getModelController(WarehouseControl.class);
        EditInventoryLocationGroupVolumeResult result = InventoryResultFactory.getEditInventoryLocationGroupVolumeResult();
        String warehouseName = spec.getWarehouseName();
        Warehouse warehouse = warehouseControl.getWarehouseByName(warehouseName);
        
        if(warehouse != null) {
            var inventoryControl = Session.getModelController(InventoryControl.class);
            String inventoryLocationGroupName = spec.getInventoryLocationGroupName();
            InventoryLocationGroup inventoryLocationGroup = inventoryControl.getInventoryLocationGroupByName(warehouse.getParty(), inventoryLocationGroupName);
            
            if(inventoryLocationGroup != null) {
                var uomControl = Session.getModelController(UomControl.class);
                UnitOfMeasureKind volumeUnitOfMeasureKind = uomControl.getUnitOfMeasureKindByUnitOfMeasureKindUseTypeUsingNames(UomConstants.UnitOfMeasureKindUseType_VOLUME);
                
                if(volumeUnitOfMeasureKind != null) {
                    if(editMode.equals(EditMode.LOCK)) {
                        InventoryLocationGroupVolume inventoryLocationGroupVolume = inventoryControl.getInventoryLocationGroupVolume(inventoryLocationGroup);
                        
                        if(inventoryLocationGroupVolume != null) {
                            result.setInventoryLocationGroupVolume(inventoryControl.getInventoryLocationGroupVolumeTransfer(getUserVisit(), inventoryLocationGroupVolume));
                            
                            if(lockEntity(inventoryLocationGroup)) {
                                InventoryLocationGroupVolumeEdit edit = InventoryEditFactory.getInventoryLocationGroupVolumeEdit();
                                Long height = inventoryLocationGroupVolume.getHeight();
                                Conversion heightConversion = height == null? null: new Conversion(uomControl, volumeUnitOfMeasureKind, height).convertToHighestUnitOfMeasureType();
                                Long width = inventoryLocationGroupVolume.getWidth();
                                Conversion widthConversion = width == null? null: new Conversion(uomControl, volumeUnitOfMeasureKind, width).convertToHighestUnitOfMeasureType();
                                Long depth = inventoryLocationGroupVolume.getDepth();
                                Conversion depthConversion = depth == null? null: new Conversion(uomControl, volumeUnitOfMeasureKind, depth).convertToHighestUnitOfMeasureType();
                                
                                result.setEdit(edit);
                                edit.setHeight(heightConversion.getQuantity().toString());
                                edit.setHeightUnitOfMeasureTypeName(heightConversion.getUnitOfMeasureType().getLastDetail().getUnitOfMeasureTypeName());
                                edit.setWidth(widthConversion.getQuantity().toString());
                                edit.setWidthUnitOfMeasureTypeName(widthConversion.getUnitOfMeasureType().getLastDetail().getUnitOfMeasureTypeName());
                                edit.setDepth(depthConversion.getQuantity().toString());
                                edit.setDepthUnitOfMeasureTypeName(depthConversion.getUnitOfMeasureType().getLastDetail().getUnitOfMeasureTypeName());
                            } else {
                                addExecutionError(ExecutionErrors.EntityLockFailed.name());
                            }
                            
                            result.setEntityLock(getEntityLockTransfer(inventoryLocationGroup));
                        } else {
                            addExecutionError(ExecutionErrors.UnknownInventoryLocationGroupVolume.name());
                        }
                    } else if(editMode.equals(EditMode.UPDATE)) {
                        InventoryLocationGroupVolumeValue inventoryLocationGroupVolumeValue = inventoryControl.getInventoryLocationGroupVolumeValueForUpdate(inventoryLocationGroup);
                        
                        if(inventoryLocationGroupVolumeValue != null) {
                            String heightUnitOfMeasureTypeName = edit.getHeightUnitOfMeasureTypeName();
                            UnitOfMeasureType heightUnitOfMeasureType = uomControl.getUnitOfMeasureTypeByName(volumeUnitOfMeasureKind,
                                    heightUnitOfMeasureTypeName);
                            
                            if(heightUnitOfMeasureType != null) {
                                Long height = Long.valueOf(edit.getHeight());
                                
                                if(height > 0) {
                                    String widthUnitOfMeasureTypeName = edit.getWidthUnitOfMeasureTypeName();
                                    UnitOfMeasureType widthUnitOfMeasureType = uomControl.getUnitOfMeasureTypeByName(volumeUnitOfMeasureKind,
                                            widthUnitOfMeasureTypeName);
                                    
                                    if(widthUnitOfMeasureType != null) {
                                        Long width = Long.valueOf(edit.getWidth());
                                        
                                        if(width > 0) {
                                            String depthUnitOfMeasureTypeName = edit.getDepthUnitOfMeasureTypeName();
                                            UnitOfMeasureType depthUnitOfMeasureType = uomControl.getUnitOfMeasureTypeByName(volumeUnitOfMeasureKind,
                                                    depthUnitOfMeasureTypeName);
                                            
                                            if(depthUnitOfMeasureType != null) {
                                                Long depth = Long.valueOf(edit.getDepth());
                                                
                                                if(depth > 0) {
                                                    if(lockEntityForUpdate(inventoryLocationGroup)) {
                                                        try {
                                                            Conversion heightConversion = new Conversion(uomControl, heightUnitOfMeasureType, height).convertToLowestUnitOfMeasureType();
                                                            Conversion widthConversion = new Conversion(uomControl, widthUnitOfMeasureType, width).convertToLowestUnitOfMeasureType();
                                                            Conversion depthConversion = new Conversion(uomControl, depthUnitOfMeasureType, depth).convertToLowestUnitOfMeasureType();
                                                            
                                                            inventoryLocationGroupVolumeValue.setHeight(heightConversion.getQuantity());
                                                            inventoryLocationGroupVolumeValue.setWidth(widthConversion.getQuantity());
                                                            inventoryLocationGroupVolumeValue.setDepth(depthConversion.getQuantity());
                                                            
                                                            inventoryControl.updateInventoryLocationGroupVolumeFromValue(inventoryLocationGroupVolumeValue, getPartyPK());
                                                        } finally {
                                                            unlockEntity(inventoryLocationGroup);
                                                        }
                                                    } else {
                                                        addExecutionError(ExecutionErrors.EntityLockStale.name());
                                                    }
                                                } else {
                                                    addExecutionError(ExecutionErrors.InvalidDepth.name(), depth);
                                                }
                                            } else {
                                                addExecutionError(ExecutionErrors.UnknownDepthUnitOfMeasureTypeName.name(), depthUnitOfMeasureTypeName);
                                            }
                                        } else {
                                            addExecutionError(ExecutionErrors.InvalidWidth.name(), width);
                                        }
                                    } else {
                                        addExecutionError(ExecutionErrors.UnknownWidthUnitOfMeasureTypeName.name(), widthUnitOfMeasureTypeName);
                                    }
                                } else {
                                    addExecutionError(ExecutionErrors.InvalidHeight.name(), height);
                                }
                            } else {
                                addExecutionError(ExecutionErrors.UnknownHeightUnitOfMeasureTypeName.name(), heightUnitOfMeasureTypeName);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownUnitOfMeasureTypeVolume.name());
                        }
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownVolumeUnitOfMeasureKind.name());
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
