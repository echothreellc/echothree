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

import com.echothree.control.user.warehouse.common.edit.LocationNameElementEdit;
import com.echothree.control.user.warehouse.common.edit.WarehouseEditFactory;
import com.echothree.control.user.warehouse.common.form.EditLocationNameElementForm;
import com.echothree.control.user.warehouse.common.result.WarehouseResultFactory;
import com.echothree.control.user.warehouse.common.spec.LocationNameElementSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.warehouse.server.control.WarehouseControl;
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

public class EditLocationNameElementCommand
        extends BaseEditCommand<LocationNameElementSpec, LocationNameElementEdit> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.LocationNameElement.name(), SecurityRoles.Edit.name())
                ))
        ));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("WarehouseName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("LocationTypeName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("LocationNameElementName", FieldType.ENTITY_NAME, true, null, null)
        ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("LocationNameElementName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("Offset", FieldType.UNSIGNED_INTEGER, true, null, null),
            new FieldDefinition("Length", FieldType.UNSIGNED_INTEGER, true, null, null),
            new FieldDefinition("ValidationPattern", FieldType.REGULAR_EXPRESSION, false, null, null),
            new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
        ));
    }

    /** Creates a new instance of EditLocationNameElementCommand */
    public EditLocationNameElementCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var warehouseControl = Session.getModelController(WarehouseControl.class);
        var result = WarehouseResultFactory.getEditLocationNameElementResult();
        var warehouseName = spec.getWarehouseName();
        var warehouse = warehouseControl.getWarehouseByName(warehouseName);
        
        if(warehouse != null) {
            var warehouseParty = warehouse.getParty();
            var locationTypeName = spec.getLocationTypeName();
            var locationType = warehouseControl.getLocationTypeByName(warehouseParty, locationTypeName);
            
            if(locationType != null) {
                if(editMode.equals(EditMode.LOCK)) {
                    var locationNameElementName = spec.getLocationNameElementName();
                    var locationNameElement = warehouseControl.getLocationNameElementByName(locationType, locationNameElementName);
                    
                    if(locationNameElement != null) {
                        result.setLocationNameElement(warehouseControl.getLocationNameElementTransfer(getUserVisit(), locationNameElement));
                        
                        if(lockEntity(locationNameElement)) {
                            var locationNameElementDescription = warehouseControl.getLocationNameElementDescription(locationNameElement, getPreferredLanguage());
                            var edit = WarehouseEditFactory.getLocationNameElementEdit();
                            var locationNameElementDetail = locationNameElement.getLastDetail();
                            
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
                    var locationNameElementName = spec.getLocationNameElementName();
                    var locationNameElement = warehouseControl.getLocationNameElementByNameForUpdate(locationType, locationNameElementName);
                    
                    if(locationNameElement != null) {
                        locationNameElementName = edit.getLocationNameElementName();
                        var duplicateLocationNameElement = warehouseControl.getLocationNameElementByName(locationType, locationNameElementName);
                        
                        if(duplicateLocationNameElement == null || locationNameElement.equals(duplicateLocationNameElement)) {
                            if(lockEntityForUpdate(locationNameElement)) {
                                try {
                                    var partyPK = getPartyPK();
                                    var locationNameElementDetailValue = warehouseControl.getLocationNameElementDetailValueForUpdate(locationNameElement);
                                    var locationNameElementDescription = warehouseControl.getLocationNameElementDescriptionForUpdate(locationNameElement, getPreferredLanguage());
                                    var description = edit.getDescription();
                                    
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
                                        var locationNameElementDescriptionValue = warehouseControl.getLocationNameElementDescriptionValue(locationNameElementDescription);
                                        
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
