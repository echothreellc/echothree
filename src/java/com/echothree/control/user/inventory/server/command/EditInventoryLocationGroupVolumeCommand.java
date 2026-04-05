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
import com.echothree.control.user.inventory.common.edit.InventoryLocationGroupVolumeEdit;
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
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKind;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureType;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class EditInventoryLocationGroupVolumeCommand
        extends BaseAbstractEditCommand<InventoryLocationGroupSpec, InventoryLocationGroupVolumeEdit, EditInventoryLocationGroupVolumeResult, InventoryLocationGroupVolume, InventoryLocationGroup> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("WarehouseName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("InventoryLocationGroupName", FieldType.ENTITY_NAME, true, null, null)
                );
        
        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("HeightUnitOfMeasureTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("Height", FieldType.UNSIGNED_LONG, true, null, null),
                new FieldDefinition("WidthUnitOfMeasureTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("Width", FieldType.UNSIGNED_LONG, true, null, null),
                new FieldDefinition("DepthUnitOfMeasureTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("Depth", FieldType.UNSIGNED_LONG, true, null, null)
                );
    }
    
    /** Creates a new instance of EditInventoryLocationGroupVolumeCommand */
    public EditInventoryLocationGroupVolumeCommand() {
        super(null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Inject
    InventoryControl inventoryControl;

    @Inject
    UomControl uomControl;

    @Inject
    WarehouseControl warehouseControl;

    @Override
    public EditInventoryLocationGroupVolumeResult getResult() {
        return InventoryResultFactory.getEditInventoryLocationGroupVolumeResult();
    }

    @Override
    public InventoryLocationGroupVolumeEdit getEdit() {
        return InventoryEditFactory.getInventoryLocationGroupVolumeEdit();
    }

    @Override
    public InventoryLocationGroupVolume getEntity(EditInventoryLocationGroupVolumeResult result) {
        InventoryLocationGroupVolume inventoryLocationGroupVolume = null;
        var warehouseName = spec.getWarehouseName();
        var warehouse = warehouseControl.getWarehouseByName(warehouseName);

        if(warehouse != null) {
            var inventoryLocationGroupName = spec.getInventoryLocationGroupName();
            var inventoryLocationGroup = inventoryControl.getInventoryLocationGroupByName(warehouse.getParty(), inventoryLocationGroupName);

            if(inventoryLocationGroup != null) {
                if(editMode.equals(EditMode.UPDATE)) {
                    inventoryLocationGroupVolume = inventoryControl.getInventoryLocationGroupVolumeForUpdate(inventoryLocationGroup);
                } else {
                    inventoryLocationGroupVolume = inventoryControl.getInventoryLocationGroupVolume(inventoryLocationGroup);
                }

                if(inventoryLocationGroupVolume == null) {
                    addExecutionError(ExecutionErrors.UnknownInventoryLocationGroupVolume.name(), warehouseName, inventoryLocationGroupName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownInventoryLocationGroupName.name(), warehouseName, inventoryLocationGroupName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownWarehouseName.name(), warehouseName);
        }

        return inventoryLocationGroupVolume;
    }

    @Override
    public InventoryLocationGroup getLockEntity(InventoryLocationGroupVolume inventoryLocationGroupVolume) {
        return inventoryLocationGroupVolume.getInventoryLocationGroup();
    }

    @Override
    public void fillInResult(EditInventoryLocationGroupVolumeResult result, InventoryLocationGroupVolume inventoryLocationGroupVolume) {
        result.setInventoryLocationGroupVolume(inventoryControl.getInventoryLocationGroupVolumeTransfer(getUserVisit(), inventoryLocationGroupVolume));
    }

    UnitOfMeasureKind volumeUnitOfMeasureKind;

    private UnitOfMeasureKind getVolumeUnitOfMeasureKind() {
        if(volumeUnitOfMeasureKind == null) {
            volumeUnitOfMeasureKind = uomControl.getUnitOfMeasureKindByUnitOfMeasureKindUseTypeUsingNames(UomConstants.UnitOfMeasureKindUseType_VOLUME);
        }

        return volumeUnitOfMeasureKind;
    }

    @Override
    public void doLock(InventoryLocationGroupVolumeEdit edit, InventoryLocationGroupVolume inventoryLocationGroupVolume) {
        var volumeUnitOfMeasureKind = getVolumeUnitOfMeasureKind();
        var height = inventoryLocationGroupVolume.getHeight();
        var heightConversion = height == null? null: new Conversion(uomControl, volumeUnitOfMeasureKind, height).convertToHighestUnitOfMeasureType();
        var width = inventoryLocationGroupVolume.getWidth();
        var widthConversion = width == null? null: new Conversion(uomControl, volumeUnitOfMeasureKind, width).convertToHighestUnitOfMeasureType();
        var depth = inventoryLocationGroupVolume.getDepth();
        var depthConversion = depth == null? null: new Conversion(uomControl, volumeUnitOfMeasureKind, depth).convertToHighestUnitOfMeasureType();

        if(heightConversion != null) {
            edit.setHeight(heightConversion.getQuantity().toString());
            edit.setHeightUnitOfMeasureTypeName(heightConversion.getUnitOfMeasureType().getLastDetail().getUnitOfMeasureTypeName());
        }

        if(widthConversion != null) {
            edit.setWidth(widthConversion.getQuantity().toString());
            edit.setWidthUnitOfMeasureTypeName(widthConversion.getUnitOfMeasureType().getLastDetail().getUnitOfMeasureTypeName());
        }

        if(depthConversion != null) {
            edit.setDepth(depthConversion.getQuantity().toString());
            edit.setDepthUnitOfMeasureTypeName(depthConversion.getUnitOfMeasureType().getLastDetail().getUnitOfMeasureTypeName());
        }
    }

    Long height;
    Long width;
    Long depth;

    @Override
    public void canUpdate(InventoryLocationGroupVolume inventoryLocationGroupVolume) {
        var volumeUnitOfMeasureKind = getVolumeUnitOfMeasureKind();
        var heightUnitOfMeasureTypeName = edit.getHeightUnitOfMeasureTypeName();
        var heightUnitOfMeasureType = uomControl.getUnitOfMeasureTypeByName(volumeUnitOfMeasureKind, heightUnitOfMeasureTypeName);

        if(heightUnitOfMeasureType != null) {
            var heightValue = Long.valueOf(edit.getHeight());

            if(heightValue > 0) {
                var widthUnitOfMeasureTypeName = edit.getWidthUnitOfMeasureTypeName();
                var widthUnitOfMeasureType = uomControl.getUnitOfMeasureTypeByName(volumeUnitOfMeasureKind, widthUnitOfMeasureTypeName);

                if(widthUnitOfMeasureType != null) {
                    var widthValue = Long.valueOf(edit.getWidth());

                    if(widthValue > 0) {
                        var depthUnitOfMeasureTypeName = edit.getDepthUnitOfMeasureTypeName();
                        var depthUnitOfMeasureType = uomControl.getUnitOfMeasureTypeByName(volumeUnitOfMeasureKind, depthUnitOfMeasureTypeName);

                        if(depthUnitOfMeasureType != null) {
                            var depthValue = Long.valueOf(edit.getDepth());

                            if(depthValue > 0) {
                                height = new Conversion(uomControl, heightUnitOfMeasureType, heightValue).convertToLowestUnitOfMeasureType().getQuantity();
                                width = new Conversion(uomControl, widthUnitOfMeasureType, widthValue).convertToLowestUnitOfMeasureType().getQuantity();
                                depth = new Conversion(uomControl, depthUnitOfMeasureType, depthValue).convertToLowestUnitOfMeasureType().getQuantity();
                            } else {
                                addExecutionError(ExecutionErrors.InvalidDepth.name(), depthValue);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownDepthUnitOfMeasureTypeName.name(), depthUnitOfMeasureTypeName);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.InvalidWidth.name(), widthValue);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownWidthUnitOfMeasureTypeName.name(), widthUnitOfMeasureTypeName);
                }
            } else {
                addExecutionError(ExecutionErrors.InvalidHeight.name(), heightValue);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownHeightUnitOfMeasureTypeName.name(), heightUnitOfMeasureTypeName);
        }
    }

    @Override
    public void doUpdate(InventoryLocationGroupVolume inventoryLocationGroupVolume) {
        var inventoryLocationGroupVolumeValue = inventoryControl.getInventoryLocationGroupVolumeValueForUpdate(inventoryLocationGroupVolume);

        inventoryLocationGroupVolumeValue.setHeight(height);
        inventoryLocationGroupVolumeValue.setWidth(width);
        inventoryLocationGroupVolumeValue.setDepth(depth);

        inventoryControl.updateInventoryLocationGroupVolumeFromValue(inventoryLocationGroupVolumeValue, getPartyPK());
    }

}
