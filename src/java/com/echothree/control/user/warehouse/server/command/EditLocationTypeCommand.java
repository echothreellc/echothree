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

import com.echothree.control.user.warehouse.common.edit.LocationTypeEdit;
import com.echothree.control.user.warehouse.common.edit.WarehouseEditFactory;
import com.echothree.control.user.warehouse.common.form.EditLocationTypeForm;
import com.echothree.control.user.warehouse.common.result.WarehouseResultFactory;
import com.echothree.control.user.warehouse.common.spec.LocationTypeSpec;
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
import javax.enterprise.context.Dependent;

@Dependent
public class EditLocationTypeCommand
        extends BaseEditCommand<LocationTypeSpec, LocationTypeEdit> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.LocationType.name(), SecurityRoles.Edit.name())
                ))
        ));

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
    public EditLocationTypeCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var warehouseControl = Session.getModelController(WarehouseControl.class);
        var result = WarehouseResultFactory.getEditLocationTypeResult();
        var warehouseName = spec.getWarehouseName();
        var warehouse = warehouseControl.getWarehouseByName(warehouseName);
        
        if(warehouse != null) {
            var warehouseParty = warehouse.getParty();
            
            if(editMode.equals(EditMode.LOCK)) {
                var locationTypeName = spec.getLocationTypeName();
                var locationType = warehouseControl.getLocationTypeByName(warehouseParty, locationTypeName);
                
                if(locationType != null) {
                    result.setLocationType(warehouseControl.getLocationTypeTransfer(getUserVisit(), locationType));
                    
                    if(lockEntity(locationType)) {
                        var locationTypeDescription = warehouseControl.getLocationTypeDescription(locationType, getPreferredLanguage());
                        var edit = WarehouseEditFactory.getLocationTypeEdit();
                        var locationTypeDetail = locationType.getLastDetail();
                        
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
                var locationTypeName = spec.getLocationTypeName();
                var locationType = warehouseControl.getLocationTypeByNameForUpdate(warehouseParty, locationTypeName);
                
                if(locationType != null) {
                    locationTypeName = edit.getLocationTypeName();
                    var duplicateLocationType = warehouseControl.getLocationTypeByName(warehouseParty, locationTypeName);
                    
                    if(duplicateLocationType == null || locationType.equals(duplicateLocationType)) {
                        if(lockEntityForUpdate(locationType)) {
                            try {
                                var partyPK = getPartyPK();
                                var locationTypeDetailValue = warehouseControl.getLocationTypeDetailValueForUpdate(locationType);
                                var locationTypeDescription = warehouseControl.getLocationTypeDescriptionForUpdate(locationType, getPreferredLanguage());
                                var description = edit.getDescription();
                                
                                locationTypeDetailValue.setLocationTypeName(edit.getLocationTypeName());
                                locationTypeDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
                                locationTypeDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));
                                
                                warehouseControl.updateLocationTypeFromValue(locationTypeDetailValue, partyPK);
                                
                                if(locationTypeDescription == null && description != null) {
                                    warehouseControl.createLocationTypeDescription(locationType, getPreferredLanguage(), description, partyPK);
                                } else if(locationTypeDescription != null && description == null) {
                                    warehouseControl.deleteLocationTypeDescription(locationTypeDescription, partyPK);
                                } else if(locationTypeDescription != null && description != null) {
                                    var locationTypeDescriptionValue = warehouseControl.getLocationTypeDescriptionValue(locationTypeDescription);
                                    
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
