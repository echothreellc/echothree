// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

import com.echothree.control.user.warehouse.remote.edit.LocationDescriptionEdit;
import com.echothree.control.user.warehouse.remote.edit.WarehouseEditFactory;
import com.echothree.control.user.warehouse.remote.form.EditLocationDescriptionForm;
import com.echothree.control.user.warehouse.remote.result.EditLocationDescriptionResult;
import com.echothree.control.user.warehouse.remote.result.WarehouseResultFactory;
import com.echothree.control.user.warehouse.remote.spec.LocationDescriptionSpec;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.warehouse.server.WarehouseControl;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.user.remote.pk.UserVisitPK;
import com.echothree.model.data.warehouse.server.entity.Location;
import com.echothree.model.data.warehouse.server.entity.LocationDescription;
import com.echothree.model.data.warehouse.server.entity.Warehouse;
import com.echothree.model.data.warehouse.server.value.LocationDescriptionValue;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.remote.command.BaseResult;
import com.echothree.util.remote.command.EditMode;
import com.echothree.util.server.control.BaseEditCommand;
import com.echothree.util.server.persistence.Session;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EditLocationDescriptionCommand
        extends BaseEditCommand<LocationDescriptionSpec, LocationDescriptionEdit> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        List<FieldDefinition> temp = new ArrayList<>(3);
        temp.add(new FieldDefinition("WarehouseName", FieldType.ENTITY_NAME, true, null, null));
        temp.add(new FieldDefinition("LocationName", FieldType.ENTITY_NAME, true, null, null));
        temp.add(new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null));
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(temp);
        
        temp = new ArrayList<>(1);
        temp.add(new FieldDefinition("Description", FieldType.STRING, true, 1L, 80L));
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(temp);
    }
    
    /** Creates a new instance of EditLocationDescriptionCommand */
    public EditLocationDescriptionCommand(UserVisitPK userVisitPK, EditLocationDescriptionForm form) {
        super(userVisitPK, form, null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        WarehouseControl warehouseControl = (WarehouseControl)Session.getModelController(WarehouseControl.class);
        EditLocationDescriptionResult result = WarehouseResultFactory.getEditLocationDescriptionResult();
        String warehouseName = spec.getWarehouseName();
        Warehouse warehouse = warehouseControl.getWarehouseByName(warehouseName);
        
        if(warehouse != null) {
            Party warehouseParty = warehouse.getParty();
            String locationName = spec.getLocationName();
            Location location = warehouseControl.getLocationByName(warehouseParty, locationName);
            
            if(location != null) {
                PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);
                String languageIsoName = spec.getLanguageIsoName();
                Language language = partyControl.getLanguageByIsoName(languageIsoName);
                
                if(language != null) {
                    if(editMode.equals(EditMode.LOCK)) {
                        LocationDescription locationDescription = warehouseControl.getLocationDescription(location, language);
                        
                        if(locationDescription != null) {
                            result.setLocationDescription(warehouseControl.getLocationDescriptionTransfer(getUserVisit(), locationDescription));
                            
                            if(lockEntity(location)) {
                                LocationDescriptionEdit edit = WarehouseEditFactory.getLocationDescriptionEdit();
                                
                                result.setEdit(edit);
                                edit.setDescription(locationDescription.getDescription());
                            } else {
                                addExecutionError(ExecutionErrors.EntityLockFailed.name());
                            }
                            
                            result.setEntityLock(getEntityLockTransfer(location));
                        } else {
                            addExecutionError(ExecutionErrors.UnknownLocationDescription.name());
                        }
                    } else if(editMode.equals(EditMode.UPDATE)) {
                        LocationDescriptionValue locationDescriptionValue = warehouseControl.getLocationDescriptionValueForUpdate(location, language);
                        
                        if(locationDescriptionValue != null) {
                            if(lockEntityForUpdate(location)) {
                                try {
                                    String description = edit.getDescription();
                                    
                                    locationDescriptionValue.setDescription(description);
                                    
                                    warehouseControl.updateLocationDescriptionFromValue(locationDescriptionValue, getPartyPK());
                                } finally {
                                    unlockEntity(location);
                                }
                            } else {
                                addExecutionError(ExecutionErrors.EntityLockStale.name());
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownLocationDescription.name());
                        }
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLocationName.name(), locationName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownWarehouseName.name(), warehouseName);
        }
        
        return result;
    }
    
}
