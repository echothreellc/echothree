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

package com.echothree.control.user.warehouse.server.command;

import com.echothree.control.user.warehouse.common.edit.LocationTypeEdit;
import com.echothree.control.user.warehouse.common.edit.WarehouseEditFactory;
import com.echothree.control.user.warehouse.common.form.EditLocationTypeForm;
import com.echothree.control.user.warehouse.common.result.EditLocationTypeResult;
import com.echothree.control.user.warehouse.common.result.WarehouseResultFactory;
import com.echothree.control.user.warehouse.common.spec.LocationTypeSpec;
import com.echothree.model.control.warehouse.server.control.WarehouseControl;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.warehouse.server.entity.LocationType;
import com.echothree.model.data.warehouse.server.entity.LocationTypeDescription;
import com.echothree.model.data.warehouse.server.entity.LocationTypeDetail;
import com.echothree.model.data.warehouse.server.entity.Warehouse;
import com.echothree.model.data.warehouse.server.value.LocationTypeDescriptionValue;
import com.echothree.model.data.warehouse.server.value.LocationTypeDetailValue;
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

public class EditLocationTypeCommand
        extends BaseEditCommand<LocationTypeSpec, LocationTypeEdit> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("WarehouseName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("LocationTypeName", FieldType.ENTITY_NAME, true, null, null)
        ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("LocationTypeName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
            new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
            new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
        ));
    }
    
    /** Creates a new instance of EditLocationTypeCommand */
    public EditLocationTypeCommand(UserVisitPK userVisitPK, EditLocationTypeForm form) {
        super(userVisitPK, form, null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var warehouseControl = Session.getModelController(WarehouseControl.class);
        EditLocationTypeResult result = WarehouseResultFactory.getEditLocationTypeResult();
        String warehouseName = spec.getWarehouseName();
        Warehouse warehouse = warehouseControl.getWarehouseByName(warehouseName);
        
        if(warehouse != null) {
            Party warehouseParty = warehouse.getParty();
            
            if(editMode.equals(EditMode.LOCK)) {
                String locationTypeName = spec.getLocationTypeName();
                LocationType locationType = warehouseControl.getLocationTypeByName(warehouseParty, locationTypeName);
                
                if(locationType != null) {
                    result.setLocationType(warehouseControl.getLocationTypeTransfer(getUserVisit(), locationType));
                    
                    if(lockEntity(locationType)) {
                        LocationTypeDescription locationTypeDescription = warehouseControl.getLocationTypeDescription(locationType, getPreferredLanguage());
                        LocationTypeEdit edit = WarehouseEditFactory.getLocationTypeEdit();
                        LocationTypeDetail locationTypeDetail = locationType.getLastDetail();
                        
                        result.setEdit(edit);
                        edit.setLocationTypeName(locationTypeDetail.getLocationTypeName());
                        edit.setIsDefault(locationTypeDetail.getIsDefault().toString());
                        edit.setSortOrder(locationTypeDetail.getSortOrder().toString());
                        
                        if(locationTypeDescription != null) {
                            edit.setDescription(locationTypeDescription.getDescription());
                        }
                    } else {
                        addExecutionError(ExecutionErrors.EntityLockFailed.name());
                    }
                    
                    result.setEntityLock(getEntityLockTransfer(locationType));
                } else {
                    addExecutionError(ExecutionErrors.UnknownLocationTypeName.name(), locationTypeName);
                }
            } else if(editMode.equals(EditMode.UPDATE)) {
                String locationTypeName = spec.getLocationTypeName();
                LocationType locationType = warehouseControl.getLocationTypeByNameForUpdate(warehouseParty, locationTypeName);
                
                if(locationType != null) {
                    locationTypeName = edit.getLocationTypeName();
                    LocationType duplicateLocationType = warehouseControl.getLocationTypeByName(warehouseParty, locationTypeName);
                    
                    if(duplicateLocationType == null || locationType.equals(duplicateLocationType)) {
                        if(lockEntityForUpdate(locationType)) {
                            try {
                                var partyPK = getPartyPK();
                                LocationTypeDetailValue locationTypeDetailValue = warehouseControl.getLocationTypeDetailValueForUpdate(locationType);
                                LocationTypeDescription locationTypeDescription = warehouseControl.getLocationTypeDescriptionForUpdate(locationType, getPreferredLanguage());
                                String description = edit.getDescription();
                                
                                locationTypeDetailValue.setLocationTypeName(edit.getLocationTypeName());
                                locationTypeDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
                                locationTypeDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));
                                
                                warehouseControl.updateLocationTypeFromValue(locationTypeDetailValue, partyPK);
                                
                                if(locationTypeDescription == null && description != null) {
                                    warehouseControl.createLocationTypeDescription(locationType, getPreferredLanguage(), description, partyPK);
                                } else if(locationTypeDescription != null && description == null) {
                                    warehouseControl.deleteLocationTypeDescription(locationTypeDescription, partyPK);
                                } else if(locationTypeDescription != null && description != null) {
                                    LocationTypeDescriptionValue locationTypeDescriptionValue = warehouseControl.getLocationTypeDescriptionValue(locationTypeDescription);
                                    
                                    locationTypeDescriptionValue.setDescription(description);
                                    warehouseControl.updateLocationTypeDescriptionFromValue(locationTypeDescriptionValue, partyPK);
                                }
                            } finally {
                                unlockEntity(locationType);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.EntityLockStale.name());
                        }
                    } else {
                        addExecutionError(ExecutionErrors.DuplicateLocationTypeName.name(), locationTypeName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownLocationTypeName.name(), locationTypeName);
                }
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownWarehouseName.name(), warehouseName);
        }
        
        return result;
    }
    
}
