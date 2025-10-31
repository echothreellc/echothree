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

package com.echothree.control.user.warehouse.server.command;

import com.echothree.control.user.warehouse.common.edit.LocationEdit;
import com.echothree.control.user.warehouse.common.edit.WarehouseEditFactory;
import com.echothree.control.user.warehouse.common.form.EditLocationForm;
import com.echothree.control.user.warehouse.common.result.WarehouseResultFactory;
import com.echothree.control.user.warehouse.common.spec.LocationSpec;
import com.echothree.model.control.inventory.server.control.InventoryControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.warehouse.server.control.WarehouseControl;
import com.echothree.model.control.warehouse.server.logic.LocationLogic;
import com.echothree.model.control.warehouse.server.logic.LocationUseTypeLogic;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class EditLocationCommand
        extends BaseEditCommand<LocationSpec, LocationEdit> {

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

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("WarehouseName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("LocationName", FieldType.ENTITY_NAME, true, null, null)
        ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("LocationName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("LocationTypeName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("LocationUseTypeName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("Velocity", FieldType.UNSIGNED_INTEGER, true, null, null),
            new FieldDefinition("InventoryLocationGroupName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
        ));
    }
    
    /** Creates a new instance of EditLocationCommand */
    public EditLocationCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var warehouseControl = Session.getModelController(WarehouseControl.class);
        var result = WarehouseResultFactory.getEditLocationResult();
        var warehouseName = spec.getWarehouseName();
        var warehouse = warehouseControl.getWarehouseByName(warehouseName);
        
        if(warehouse != null) {
            var warehouseParty = warehouse.getParty();
            if(editMode.equals(EditMode.LOCK)) {
                var locationName = spec.getLocationName();
                var location = warehouseControl.getLocationByName(warehouseParty, locationName);
                
                if(location != null) {
                    result.setLocation(warehouseControl.getLocationTransfer(getUserVisit(), location));
                    
                    if(lockEntity(location)) {
                        var locationDescription = warehouseControl.getLocationDescription(location, getPreferredLanguage());
                        var edit = WarehouseEditFactory.getLocationEdit();
                        var locationDetail = location.getLastDetail();
                        
                        result.setEdit(edit);
                        edit.setLocationName(locationDetail.getLocationName());
                        edit.setLocationTypeName(locationDetail.getLocationType().getLastDetail().getLocationTypeName());
                        edit.setLocationUseTypeName(locationDetail.getLocationUseType().getLocationUseTypeName());
                        edit.setVelocity(locationDetail.getVelocity().toString());
                        edit.setInventoryLocationGroupName(locationDetail.getInventoryLocationGroup().getLastDetail().getInventoryLocationGroupName());
                        
                        if(locationDescription != null) {
                            edit.setDescription(locationDescription.getDescription());
                        }
                    } else {
                        addExecutionError(ExecutionErrors.EntityLockFailed.name());
                    }
                    
                    result.setEntityLock(getEntityLockTransfer(location));
                } else {
                    addExecutionError(ExecutionErrors.UnknownLocationName.name(), locationName);
                }
            } else if(editMode.equals(EditMode.UPDATE)) {
                var locationName = spec.getLocationName();
                var location = warehouseControl.getLocationByNameForUpdate(warehouseParty, locationName);
                
                if(location != null) {
                    locationName = edit.getLocationName();
                    var duplicateLocation = warehouseControl.getLocationByName(warehouseParty, locationName);
                    
                    if(duplicateLocation == null || location.equals(duplicateLocation)) {
                        var locationTypeName = edit.getLocationTypeName();
                        var locationType = warehouseControl.getLocationTypeByName(warehouseParty, locationTypeName);
                        
                        if(locationType != null) {
                            LocationLogic.getInstance().validateLocationName(this, locationType, locationName);

                            if(!hasExecutionErrors()) {
                                var locationUseTypeName = edit.getLocationUseTypeName();
                                var locationUseType = LocationUseTypeLogic.getInstance().getLocationUseTypeByName(this, locationUseTypeName, null, false);
                                
                                if(!hasExecutionErrors()) {
                                    var multipleUseError = false;
                                    
                                    if(!locationUseType.getAllowMultiple()) {
                                        if(warehouseControl.countLocationsByLocationUseType(warehouseParty, locationUseType) != 0)
                                            multipleUseError = true;
                                    }
                                    
                                    if(!multipleUseError) {
                                        var inventoryControl = Session.getModelController(InventoryControl.class);
                                        var inventoryLocationGroupName = edit.getInventoryLocationGroupName();
                                        var inventoryLocationGroup = inventoryControl.getInventoryLocationGroupByName(warehouseParty, inventoryLocationGroupName);
                                        
                                        if(inventoryLocationGroup != null) {
                                            if(lockEntityForUpdate(location)) {
                                                try {
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
                                                } finally {
                                                    unlockEntity(location);
                                                }
                                            } else {
                                                addExecutionError(ExecutionErrors.EntityLockStale.name());
                                            }
                                        } else {
                                            addExecutionError(ExecutionErrors.UnknownInventoryLocationGroupName.name(), inventoryLocationGroupName);
                                        }
                                    } else {
                                        addExecutionError(ExecutionErrors.MultipleLocationUseTypesNotAllowed.name());
                                    }
                                }
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownLocationTypeName.name(), locationTypeName);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.DuplicateLocationName.name(), locationName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownLocationName.name(), locationName);
                }
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownWarehouseName.name(), warehouseName);
        }
        
        return result;
    }
    
}
