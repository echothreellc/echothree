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

package com.echothree.control.user.warehouse.server.command;

import com.echothree.control.user.warehouse.common.edit.LocationVolumeEdit;
import com.echothree.control.user.warehouse.common.edit.WarehouseEditFactory;
import com.echothree.control.user.warehouse.common.result.EditLocationVolumeResult;
import com.echothree.control.user.warehouse.common.result.WarehouseResultFactory;
import com.echothree.control.user.warehouse.common.spec.LocationSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.uom.common.UomConstants;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.control.uom.server.util.Conversion;
import com.echothree.model.control.warehouse.server.control.WarehouseControl;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKind;
import com.echothree.model.data.warehouse.server.entity.Location;
import com.echothree.model.data.warehouse.server.entity.LocationVolume;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.message.ExecutionErrors;
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
public class EditLocationVolumeCommand
        extends BaseAbstractEditCommand<LocationSpec, LocationVolumeEdit, EditLocationVolumeResult, LocationVolume, Location> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.LocationVolume.name(), SecurityRoles.Delete.name())
                ))
        ));

        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("WarehouseName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LocationName", FieldType.ENTITY_NAME, true, null, null)
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
    
    /** Creates a new instance of EditLocationVolumeCommand */
    public EditLocationVolumeCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Inject
    UomControl uomControl;

    @Inject
    WarehouseControl warehouseControl;

    @Override
    protected EditLocationVolumeResult getResult() {
        return WarehouseResultFactory.getEditLocationVolumeResult();
    }

    @Override
    protected LocationVolumeEdit getEdit() {
        return WarehouseEditFactory.getLocationVolumeEdit();
    }

    @Override
    protected LocationVolume getEntity(EditLocationVolumeResult result) {
        LocationVolume locationVolume = null;
        var warehouseName = spec.getWarehouseName();
        var warehouse = warehouseControl.getWarehouseByName(warehouseName);

        if(warehouse != null) {
            var locationName = spec.getLocationName();
            var location = warehouseControl.getLocationByName(warehouse.getParty(), locationName);

            if(location != null) {
                locationVolume = warehouseControl.getLocationVolume(location, editModeToEntityPermission(editMode));

                if(locationVolume == null) {
                    addExecutionError(ExecutionErrors.UnknownLocationVolume.name(),
                            warehouse.getWarehouseName(), location.getLastDetail().getLocationName());
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLocationName.name(), warehouse.getWarehouseName(), locationName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownWarehouseName.name(), warehouseName);
        }

        return locationVolume;
    }

    @Override
    protected Location getLockEntity(LocationVolume locationVolume) {
        return locationVolume.getLocation();
    }

    @Override
    protected void fillInResult(EditLocationVolumeResult result, LocationVolume locationVolume) {
        result.setLocationVolume(warehouseControl.getLocationVolumeTransfer(getUserVisit(), locationVolume));
    }

    UnitOfMeasureKind volumeUnitOfMeasureKind;

    private UnitOfMeasureKind getVolumeUnitOfMeasureKind() {
        if(volumeUnitOfMeasureKind == null) {
            volumeUnitOfMeasureKind = uomControl.getUnitOfMeasureKindByUnitOfMeasureKindUseTypeUsingNames(UomConstants.UnitOfMeasureKindUseType_VOLUME);
        }

        return volumeUnitOfMeasureKind;
    }

    @Override
    protected void doLock(LocationVolumeEdit edit, LocationVolume locationVolume) {
        var volumeUnitOfMeasureKind = getVolumeUnitOfMeasureKind();
        var height = locationVolume.getHeight();
        var heightConversion = height == null? null: new Conversion(uomControl, volumeUnitOfMeasureKind, height).convertToHighestUnitOfMeasureType();
        var width = locationVolume.getWidth();
        var widthConversion = width == null? null: new Conversion(uomControl, volumeUnitOfMeasureKind, width).convertToHighestUnitOfMeasureType();
        var depth = locationVolume.getDepth();
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
    protected void canUpdate(LocationVolume locationVolume) {
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
    protected void doUpdate(LocationVolume locationVolume) {
        var locationVolumeValue = locationVolume.getLocationVolumeValue().clone();

        locationVolumeValue.setHeight(height);
        locationVolumeValue.setWidth(width);
        locationVolumeValue.setDepth(depth);

        warehouseControl.updateLocationVolumeFromValue(locationVolumeValue, getPartyPK());
    }

}
