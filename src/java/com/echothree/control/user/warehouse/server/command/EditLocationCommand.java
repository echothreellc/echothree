// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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
import com.echothree.control.user.warehouse.common.result.EditLocationResult;
import com.echothree.control.user.warehouse.common.result.WarehouseResultFactory;
import com.echothree.control.user.warehouse.common.spec.LocationSpec;
import com.echothree.model.control.inventory.server.control.InventoryControl;
import com.echothree.model.control.warehouse.server.control.WarehouseControl;
import com.echothree.model.data.inventory.server.entity.InventoryLocationGroup;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.warehouse.server.entity.Location;
import com.echothree.model.data.warehouse.server.entity.LocationDescription;
import com.echothree.model.data.warehouse.server.entity.LocationDetail;
import com.echothree.model.data.warehouse.server.entity.LocationNameElement;
import com.echothree.model.data.warehouse.server.entity.LocationNameElementDetail;
import com.echothree.model.data.warehouse.server.entity.LocationType;
import com.echothree.model.data.warehouse.server.entity.LocationUseType;
import com.echothree.model.data.warehouse.server.entity.Warehouse;
import com.echothree.model.data.warehouse.server.value.LocationDescriptionValue;
import com.echothree.model.data.warehouse.server.value.LocationDetailValue;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.server.control.BaseEditCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditLocationCommand
        extends BaseEditCommand<LocationSpec, LocationEdit> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
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
            new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L)
        ));
    }
    
    /** Creates a new instance of EditLocationCommand */
    public EditLocationCommand(UserVisitPK userVisitPK, EditLocationForm form) {
        super(userVisitPK, form, null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var warehouseControl = Session.getModelController(WarehouseControl.class);
        EditLocationResult result = WarehouseResultFactory.getEditLocationResult();
        String warehouseName = spec.getWarehouseName();
        Warehouse warehouse = warehouseControl.getWarehouseByName(warehouseName);
        
        if(warehouse != null) {
            Party warehouseParty = warehouse.getParty();
            if(editMode.equals(EditMode.LOCK)) {
                String locationName = spec.getLocationName();
                Location location = warehouseControl.getLocationByName(warehouseParty, locationName);
                
                if(location != null) {
                    result.setLocation(warehouseControl.getLocationTransfer(getUserVisit(), location));
                    
                    if(lockEntity(location)) {
                        LocationDescription locationDescription = warehouseControl.getLocationDescription(location, getPreferredLanguage());
                        LocationEdit edit = WarehouseEditFactory.getLocationEdit();
                        LocationDetail locationDetail = location.getLastDetail();
                        
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
                String locationName = spec.getLocationName();
                Location location = warehouseControl.getLocationByNameForUpdate(warehouseParty, locationName);
                
                if(location != null) {
                    locationName = edit.getLocationName();
                    Location duplicateLocation = warehouseControl.getLocationByName(warehouseParty, locationName);
                    
                    if(duplicateLocation == null || location.equals(duplicateLocation)) {
                        String locationTypeName = edit.getLocationTypeName();
                        LocationType locationType = warehouseControl.getLocationTypeByName(warehouseParty, locationTypeName);
                        
                        if(locationType != null) {
                            Collection locationNameElements = warehouseControl.getLocationNameElementsByLocationType(locationType);
                            int endIndex = 0;
                            boolean validLocationName = true;
                            
                            for(Iterator iter = locationNameElements.iterator(); iter.hasNext() && validLocationName;) {
                                LocationNameElement locationNameElement = (LocationNameElement)iter.next();
                                LocationNameElementDetail locationNameElementDetail = locationNameElement.getLastDetail();
                                String validationPattern = locationNameElementDetail.getValidationPattern();
                                
                                if(validationPattern != null) {
                                    try {
                                        Pattern pattern = Pattern.compile(validationPattern);
                                        int beginIndex = locationNameElementDetail.getOffset();
                                        
                                        endIndex = beginIndex + locationNameElementDetail.getLength();
                                        String substr = locationName.substring(beginIndex, endIndex);
                                        Matcher m = pattern.matcher(substr);
                                        
                                        if(!m.matches()) {
                                            validLocationName = false;
                                        }
                                    } catch (IndexOutOfBoundsException ioobe) {
                                        validLocationName = false;
                                    }
                                }
                            }
                            
                            if(locationName.length() > endIndex)
                                validLocationName = false;
                            
                            if(validLocationName) {
                                String locationUseTypeName = edit.getLocationUseTypeName();
                                LocationUseType locationUseType = warehouseControl.getLocationUseTypeByName(locationUseTypeName);
                                
                                if(locationUseType != null) {
                                    boolean multipleUseError = false;
                                    
                                    if(!locationUseType.getAllowMultiple()) {
                                        if(warehouseControl.countLocationsByLocationUseType(warehouseParty, locationUseType) != 0)
                                            multipleUseError = true;
                                    }
                                    
                                    if(!multipleUseError) {
                                        var inventoryControl = Session.getModelController(InventoryControl.class);
                                        String inventoryLocationGroupName = edit.getInventoryLocationGroupName();
                                        InventoryLocationGroup inventoryLocationGroup = inventoryControl.getInventoryLocationGroupByName(warehouseParty, inventoryLocationGroupName);
                                        
                                        if(inventoryLocationGroup != null) {
                                            if(lockEntityForUpdate(location)) {
                                                try {
                                                    var partyPK = getPartyPK();
                                                    LocationDetailValue locationDetailValue = warehouseControl.getLocationDetailValueForUpdate(location);
                                                    LocationDescription locationDescription = warehouseControl.getLocationDescriptionForUpdate(location, getPreferredLanguage());
                                                    String description = edit.getDescription();
                                                    
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
                                                        LocationDescriptionValue locationDescriptionValue = warehouseControl.getLocationDescriptionValue(locationDescription);
                                                        
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
                                } else {
                                    addExecutionError(ExecutionErrors.UnknownLocationUseTypeName.name(), locationUseTypeName);
                                }
                            } else {
                                addExecutionError(ExecutionErrors.InvalidLocationName.name(), locationName);
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
