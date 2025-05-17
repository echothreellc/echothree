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

import com.echothree.control.user.warehouse.common.edit.LocationNameElementDescriptionEdit;
import com.echothree.control.user.warehouse.common.edit.WarehouseEditFactory;
import com.echothree.control.user.warehouse.common.form.EditLocationNameElementDescriptionForm;
import com.echothree.control.user.warehouse.common.result.WarehouseResultFactory;
import com.echothree.control.user.warehouse.common.spec.LocationNameElementDescriptionSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EditLocationNameElementDescriptionCommand
        extends BaseEditCommand<LocationNameElementDescriptionSpec, LocationNameElementDescriptionEdit> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.LocationNameElement.name(), SecurityRoles.Description.name())
                ))
        ));

        List<FieldDefinition> temp = new ArrayList<>(4);
        temp.add(new FieldDefinition("WarehouseName", FieldType.ENTITY_NAME, true, null, null));
        temp.add(new FieldDefinition("LocationTypeName", FieldType.ENTITY_NAME, true, null, null));
        temp.add(new FieldDefinition("LocationNameElementName", FieldType.ENTITY_NAME, true, null, null));
        temp.add(new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null));
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(temp);
        
        temp = new ArrayList<>(1);
        temp.add(new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L));
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(temp);
    }
    
    /** Creates a new instance of EditLocationNameElementDescriptionCommand */
    public EditLocationNameElementDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var warehouseControl = Session.getModelController(WarehouseControl.class);
        var result = WarehouseResultFactory.getEditLocationNameElementDescriptionResult();
        var warehouseName = spec.getWarehouseName();
        var warehouse = warehouseControl.getWarehouseByName(warehouseName);
        
        if(warehouse != null) {
            var warehouseParty = warehouse.getParty();
            var locationTypeName = spec.getLocationTypeName();
            var locationType = warehouseControl.getLocationTypeByName(warehouseParty, locationTypeName);
            
            if(locationType != null) {
                var locationNameElementName = spec.getLocationNameElementName();
                var locationNameElement = warehouseControl.getLocationNameElementByName(locationType, locationNameElementName);
                
                if(locationNameElement != null) {
                    var partyControl = Session.getModelController(PartyControl.class);
                    var languageIsoName = spec.getLanguageIsoName();
                    var language = partyControl.getLanguageByIsoName(languageIsoName);
                    
                    if(language != null) {
                        if(editMode.equals(EditMode.LOCK)) {
                            var locationTypeDescription = warehouseControl.getLocationNameElementDescription(locationNameElement, language);
                            
                            if(locationTypeDescription != null) {
                                result.setLocationNameElementDescription(warehouseControl.getLocationNameElementDescriptionTransfer(getUserVisit(), locationTypeDescription));
                                
                                if(lockEntity(locationType)) {
                                    var edit = WarehouseEditFactory.getLocationNameElementDescriptionEdit();
                                    
                                    result.setEdit(edit);
                                    edit.setDescription(locationTypeDescription.getDescription());
                                } else {
                                    addExecutionError(ExecutionErrors.EntityLockFailed.name());
                                }
                                
                                result.setEntityLock(getEntityLockTransfer(locationType));
                            } else {
                                addExecutionError(ExecutionErrors.UnknownLocationNameElementDescription.name());
                            }
                        } else if(editMode.equals(EditMode.UPDATE)) {
                            var locationTypeDescriptionValue = warehouseControl.getLocationNameElementDescriptionValueForUpdate(locationNameElement, language);
                            
                            if(locationTypeDescriptionValue != null) {
                                if(lockEntityForUpdate(locationType)) {
                                    try {
                                        var description = edit.getDescription();
                                        
                                        locationTypeDescriptionValue.setDescription(description);
                                        
                                        warehouseControl.updateLocationNameElementDescriptionFromValue(locationTypeDescriptionValue, getPartyPK());
                                    } finally {
                                        unlockEntity(locationType);
                                    }
                                } else {
                                    addExecutionError(ExecutionErrors.EntityLockStale.name());
                                }
                            } else {
                                addExecutionError(ExecutionErrors.UnknownLocationNameElementDescription.name());
                            }
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownLocationNameElementName.name(), locationNameElementName);
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
