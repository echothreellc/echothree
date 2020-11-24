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

import com.echothree.control.user.warehouse.common.edit.LocationNameElementEdit;
import com.echothree.control.user.warehouse.common.edit.WarehouseEditFactory;
import com.echothree.control.user.warehouse.common.form.EditLocationNameElementForm;
import com.echothree.control.user.warehouse.common.result.EditLocationNameElementResult;
import com.echothree.control.user.warehouse.common.result.WarehouseResultFactory;
import com.echothree.control.user.warehouse.common.spec.LocationNameElementSpec;
import com.echothree.model.control.warehouse.server.control.WarehouseControl;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.warehouse.server.entity.LocationNameElement;
import com.echothree.model.data.warehouse.server.entity.LocationNameElementDescription;
import com.echothree.model.data.warehouse.server.entity.LocationNameElementDetail;
import com.echothree.model.data.warehouse.server.entity.LocationType;
import com.echothree.model.data.warehouse.server.entity.Warehouse;
import com.echothree.model.data.warehouse.server.value.LocationNameElementDescriptionValue;
import com.echothree.model.data.warehouse.server.value.LocationNameElementDetailValue;
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

public class EditLocationNameElementCommand
        extends BaseEditCommand<LocationNameElementSpec, LocationNameElementEdit> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("WarehouseName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("LocationType", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("LocationNameElementName", FieldType.ENTITY_NAME, true, null, null)
        ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("LocationNameElementName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("Offset", FieldType.UNSIGNED_INTEGER, true, null, null),
            new FieldDefinition("Length", FieldType.UNSIGNED_INTEGER, true, null, null),
            new FieldDefinition("ValidationPattern", FieldType.REGULAR_EXPRESSION, false, null, null),
            new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L)
        ));
    }

    /** Creates a new instance of EditLocationNameElementCommand */
    public EditLocationNameElementCommand(UserVisitPK userVisitPK, EditLocationNameElementForm form) {
        super(userVisitPK, form, null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var warehouseControl = Session.getModelController(WarehouseControl.class);
        EditLocationNameElementResult result = WarehouseResultFactory.getEditLocationNameElementResult();
        String warehouseName = spec.getWarehouseName();
        Warehouse warehouse = warehouseControl.getWarehouseByName(warehouseName);
        
        if(warehouse != null) {
            Party warehouseParty = warehouse.getParty();
            String locationTypeName = spec.getLocationTypeName();
            LocationType locationType = warehouseControl.getLocationTypeByName(warehouseParty, locationTypeName);
            
            if(locationType != null) {
                if(editMode.equals(EditMode.LOCK)) {
                    String locationNameElementName = spec.getLocationNameElementName();
                    LocationNameElement locationNameElement = warehouseControl.getLocationNameElementByName(locationType, locationNameElementName);
                    
                    if(locationNameElement != null) {
                        result.setLocationNameElement(warehouseControl.getLocationNameElementTransfer(getUserVisit(), locationNameElement));
                        
                        if(lockEntity(locationNameElement)) {
                            LocationNameElementDescription locationNameElementDescription = warehouseControl.getLocationNameElementDescription(locationNameElement, getPreferredLanguage());
                            LocationNameElementEdit edit = WarehouseEditFactory.getLocationNameElementEdit();
                            LocationNameElementDetail locationNameElementDetail = locationNameElement.getLastDetail();
                            
                            result.setEdit(edit);
                            edit.setLocationNameElementName(locationNameElementDetail.getLocationNameElementName());
                            edit.setOffset(locationNameElementDetail.getOffset().toString());
                            edit.setLength(locationNameElementDetail.getLength().toString());
                            edit.setValidationPattern(locationNameElementDetail.getValidationPattern());
                            
                            if(locationNameElementDescription != null)
                                edit.setDescription(locationNameElementDescription.getDescription());
                        } else {
                            addExecutionError(ExecutionErrors.EntityLockFailed.name());
                        }
                        
                        result.setEntityLock(getEntityLockTransfer(locationNameElement));
                    } else {
                        addExecutionError(ExecutionErrors.UnknownLocationNameElementName.name(), locationNameElementName);
                    }
                } else if(editMode.equals(EditMode.UPDATE)) {
                    String locationNameElementName = spec.getLocationNameElementName();
                    LocationNameElement locationNameElement = warehouseControl.getLocationNameElementByNameForUpdate(locationType, locationNameElementName);
                    
                    if(locationNameElement != null) {
                        locationNameElementName = edit.getLocationNameElementName();
                        LocationNameElement duplicateLocationNameElement = warehouseControl.getLocationNameElementByName(locationType, locationNameElementName);
                        
                        if(duplicateLocationNameElement == null || locationNameElement.equals(duplicateLocationNameElement)) {
                            if(lockEntityForUpdate(locationNameElement)) {
                                try {
                                    var partyPK = getPartyPK();
                                    LocationNameElementDetailValue locationNameElementDetailValue = warehouseControl.getLocationNameElementDetailValueForUpdate(locationNameElement);
                                    LocationNameElementDescription locationNameElementDescription = warehouseControl.getLocationNameElementDescriptionForUpdate(locationNameElement, getPreferredLanguage());
                                    String description = edit.getDescription();
                                    
                                    locationNameElementDetailValue.setLocationNameElementName(edit.getLocationNameElementName());
                                    locationNameElementDetailValue.setOffset(Integer.valueOf(edit.getOffset()));
                                    locationNameElementDetailValue.setLength(Integer.valueOf(edit.getLength()));
                                    locationNameElementDetailValue.setValidationPattern(edit.getValidationPattern());
                                    
                                    warehouseControl.updateLocationNameElementFromValue(locationNameElementDetailValue, partyPK);
                                    
                                    if(locationNameElementDescription == null && description != null) {
                                        warehouseControl.createLocationNameElementDescription(locationNameElement, getPreferredLanguage(), description, partyPK);
                                    } else if(locationNameElementDescription != null && description == null) {
                                        warehouseControl.deleteLocationNameElementDescription(locationNameElementDescription, partyPK);
                                    } else if(locationNameElementDescription != null && description != null) {
                                        LocationNameElementDescriptionValue locationNameElementDescriptionValue = warehouseControl.getLocationNameElementDescriptionValue(locationNameElementDescription);
                                        
                                        locationNameElementDescriptionValue.setDescription(description);
                                        warehouseControl.updateLocationNameElementDescriptionFromValue(locationNameElementDescriptionValue, partyPK);
                                    }
                                } finally {
                                    unlockEntity(locationNameElement);
                                }
                            } else {
                                addExecutionError(ExecutionErrors.EntityLockStale.name());
                            }
                        } else {
                            addExecutionError(ExecutionErrors.DuplicateLocationNameElementName.name(), locationNameElementName);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownLocationNameElementName.name(), locationNameElementName);
                    }
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLocationTypeName.name(), locationTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownWarehouseName.name(), warehouseName);
        }
        
        return result;
    }
    
}
