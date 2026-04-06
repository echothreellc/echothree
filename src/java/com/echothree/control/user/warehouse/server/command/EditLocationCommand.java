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

import com.echothree.control.user.warehouse.common.edit.LocationEdit;
import com.echothree.control.user.warehouse.common.edit.WarehouseEditFactory;
import com.echothree.control.user.warehouse.common.result.EditLocationResult;
import com.echothree.control.user.warehouse.common.result.WarehouseResultFactory;
import com.echothree.control.user.warehouse.common.spec.LocationSpec;
import com.echothree.model.control.inventory.server.control.InventoryControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.warehouse.server.control.WarehouseControl;
import com.echothree.model.control.warehouse.server.logic.LocationLogic;
import com.echothree.model.control.warehouse.server.logic.LocationUseTypeLogic;
import com.echothree.model.data.inventory.server.entity.InventoryLocationGroup;
import com.echothree.model.data.warehouse.server.entity.Location;
import com.echothree.model.data.warehouse.server.entity.LocationType;
import com.echothree.model.data.warehouse.server.entity.LocationUseType;
import com.echothree.model.data.warehouse.server.entity.Warehouse;
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
public class EditLocationCommand
        extends BaseAbstractEditCommand<LocationSpec, LocationEdit, EditLocationResult, Location, Location> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.Location.name(), SecurityRoles.Edit.name())
                ))
        ));

        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("WarehouseName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LocationName", FieldType.ENTITY_NAME, true, null, null)
        );
        
        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("LocationName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LocationTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LocationUseTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("Velocity", FieldType.UNSIGNED_INTEGER, true, null, null),
                new FieldDefinition("InventoryLocationGroupName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
        );
    }

    @Inject
    InventoryControl inventoryControl;

    @Inject
    WarehouseControl warehouseControl;

    @Inject
    LocationLogic locationLogic;

    @Inject
    LocationUseTypeLogic locationUseTypeLogic;

    /** Creates a new instance of EditLocationCommand */
    public EditLocationCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditLocationResult getResult() {
        return WarehouseResultFactory.getEditLocationResult();
    }

    @Override
    public LocationEdit getEdit() {
        return WarehouseEditFactory.getLocationEdit();
    }

    Warehouse warehouse;

    @Override
    public Location getEntity(EditLocationResult result) {
        Location location = null;
        var warehouseName = spec.getWarehouseName();

        warehouse = warehouseControl.getWarehouseByName(warehouseName);

        if(warehouse != null) {
            var warehouseParty = warehouse.getParty();
            var locationName = spec.getLocationName();

            location = warehouseControl.getLocationByName(warehouseParty, locationName, editModeToEntityPermission(editMode));

            if(location == null) {
                addExecutionError(ExecutionErrors.UnknownLocationName.name(), warehouse.getWarehouseName(), locationName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownWarehouseName.name(), warehouseName);
        }

        return location;
    }

    @Override
    public Location getLockEntity(Location location) {
        return location;
    }

    @Override
    public void fillInResult(EditLocationResult result, Location location) {
        result.setLocation(warehouseControl.getLocationTransfer(getUserVisit(), location));
    }

    @Override
    public void doLock(LocationEdit edit, Location location) {
        var locationDescription = warehouseControl.getLocationDescription(location, getPreferredLanguage());
        var locationDetail = location.getLastDetail();

        edit.setLocationName(locationDetail.getLocationName());
        edit.setLocationTypeName(locationDetail.getLocationType().getLastDetail().getLocationTypeName());
        edit.setLocationUseTypeName(locationDetail.getLocationUseType().getLocationUseTypeName());
        edit.setVelocity(locationDetail.getVelocity().toString());
        edit.setInventoryLocationGroupName(locationDetail.getInventoryLocationGroup().getLastDetail().getInventoryLocationGroupName());

        if(locationDescription != null) {
            edit.setDescription(locationDescription.getDescription());
        }
    }

    LocationType locationType;
    LocationUseType locationUseType;
    InventoryLocationGroup inventoryLocationGroup;

    @Override
    public void canUpdate(Location location) {
        var warehouseParty = warehouse.getParty();
        var locationName = edit.getLocationName();
        var duplicateLocation = warehouseControl.getLocationByName(warehouseParty, locationName);

        if(duplicateLocation == null || location.equals(duplicateLocation)) {
            var locationTypeName = edit.getLocationTypeName();

            locationType = warehouseControl.getLocationTypeByName(warehouseParty, locationTypeName);

            if(locationType != null) {
                locationLogic.validateLocationName(this, locationType, locationName);

                if(!hasExecutionErrors()) {
                    var locationUseTypeName = edit.getLocationUseTypeName();

                    locationUseType = locationUseTypeLogic.getLocationUseTypeByName(this, locationUseTypeName, null, false);

                    if(!hasExecutionErrors()) {
                        var multipleUseError = false;

                        if(!locationUseType.getAllowMultiple()) {
                            if(warehouseControl.countLocationsByLocationUseType(warehouseParty, locationUseType) != 0) {
                                multipleUseError = true;
                            }
                        }

                        if(!multipleUseError) {
                            var inventoryLocationGroupName = edit.getInventoryLocationGroupName();

                            inventoryLocationGroup = inventoryControl.getInventoryLocationGroupByName(warehouseParty, inventoryLocationGroupName);

                            if(inventoryLocationGroup == null) {
                                addExecutionError(ExecutionErrors.UnknownInventoryLocationGroupName.name(), inventoryLocationGroupName);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.MultipleLocationUseTypesNotAllowed.name());
                        }
                    }
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLocationTypeName.name(), warehouse.getWarehouseName(), locationTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.DuplicateLocationName.name(), warehouse.getWarehouseName(), locationName);
        }
    }

    @Override
    public void doUpdate(Location location) {
        var partyPK = getPartyPK();
        var locationDetailValue = warehouseControl.getLocationDetailValueForUpdate(location);
        var locationDescription = warehouseControl.getLocationDescriptionForUpdate(location, getPreferredLanguage());
        var description = edit.getDescription();

        locationDetailValue.setLocationName(edit.getLocationName());
        locationDetailValue.setLocationTypePK(locationType.getPrimaryKey());
        locationDetailValue.setLocationUseTypePK(locationUseType.getPrimaryKey());
        locationDetailValue.setVelocity(Integer.valueOf(edit.getVelocity()));
        locationDetailValue.setInventoryLocationGroupPK(inventoryLocationGroup.getPrimaryKey());

        warehouseControl.updateLocationFromValue(locationDetailValue, partyPK);

        if(locationDescription == null && description != null) {
            warehouseControl.createLocationDescription(location, getPreferredLanguage(), description, partyPK);
        } else if(locationDescription != null && description == null) {
            warehouseControl.deleteLocationDescription(locationDescription, partyPK);
        } else if(locationDescription != null && description != null) {
            var locationDescriptionValue = warehouseControl.getLocationDescriptionValue(locationDescription);

            locationDescriptionValue.setDescription(description);
            warehouseControl.updateLocationDescriptionFromValue(locationDescriptionValue, partyPK);
        }
    }

}
