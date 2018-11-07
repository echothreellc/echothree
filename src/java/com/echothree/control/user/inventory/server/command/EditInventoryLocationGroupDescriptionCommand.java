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

package com.echothree.control.user.inventory.server.command;

import com.echothree.control.user.inventory.common.edit.InventoryEditFactory;
import com.echothree.control.user.inventory.common.edit.InventoryLocationGroupDescriptionEdit;
import com.echothree.control.user.inventory.common.form.EditInventoryLocationGroupDescriptionForm;
import com.echothree.control.user.inventory.common.result.EditInventoryLocationGroupDescriptionResult;
import com.echothree.control.user.inventory.common.result.InventoryResultFactory;
import com.echothree.control.user.inventory.common.spec.InventoryLocationGroupDescriptionSpec;
import com.echothree.model.control.inventory.server.InventoryControl;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.warehouse.server.WarehouseControl;
import com.echothree.model.data.inventory.server.entity.InventoryLocationGroup;
import com.echothree.model.data.inventory.server.entity.InventoryLocationGroupDescription;
import com.echothree.model.data.inventory.server.value.InventoryLocationGroupDescriptionValue;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.warehouse.server.entity.Warehouse;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.server.control.BaseEditCommand;
import com.echothree.util.server.persistence.Session;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EditInventoryLocationGroupDescriptionCommand
        extends BaseEditCommand<InventoryLocationGroupDescriptionSpec, InventoryLocationGroupDescriptionEdit> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        List<FieldDefinition> temp = new ArrayList<>(4);
        temp.add(new FieldDefinition("WarehouseName", FieldType.ENTITY_NAME, true, null, null));
        temp.add(new FieldDefinition("InventoryLocationGroupName", FieldType.ENTITY_NAME, true, null, null));
        temp.add(new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null));
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(temp);
        
        temp = new ArrayList<>(1);
        temp.add(new FieldDefinition("Description", FieldType.STRING, true, 1L, 80L));
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(temp);
    }
    
    /** Creates a new instance of EditInventoryLocationGroupDescriptionCommand */
    public EditInventoryLocationGroupDescriptionCommand(UserVisitPK userVisitPK, EditInventoryLocationGroupDescriptionForm form) {
        super(userVisitPK, form, null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        WarehouseControl warehouseControl = (WarehouseControl)Session.getModelController(WarehouseControl.class);
        EditInventoryLocationGroupDescriptionResult result = InventoryResultFactory.getEditInventoryLocationGroupDescriptionResult();
        String warehouseName = spec.getWarehouseName();
        Warehouse warehouse = warehouseControl.getWarehouseByName(warehouseName);
        
        if(warehouse != null) {
            InventoryControl inventoryControl = (InventoryControl)Session.getModelController(InventoryControl.class);
            Party warehouseParty = warehouse.getParty();
            String inventoryLocationGroupName = spec.getInventoryLocationGroupName();
            InventoryLocationGroup inventoryLocationGroup = inventoryControl.getInventoryLocationGroupByName(warehouseParty, inventoryLocationGroupName);
            
            if(inventoryLocationGroup != null) {
                PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);
                String languageIsoName = spec.getLanguageIsoName();
                Language language = partyControl.getLanguageByIsoName(languageIsoName);
                
                if(language != null) {
                    if(editMode.equals(EditMode.LOCK)) {
                        InventoryLocationGroupDescription inventoryLocationGroupDescription = inventoryControl.getInventoryLocationGroupDescription(inventoryLocationGroup, language);
                        
                        if(inventoryLocationGroupDescription != null) {
                            result.setInventoryLocationGroupDescription(inventoryControl.getInventoryLocationGroupDescriptionTransfer(getUserVisit(), inventoryLocationGroupDescription));
                            
                            if(lockEntity(inventoryLocationGroup)) {
                                InventoryLocationGroupDescriptionEdit edit = InventoryEditFactory.getInventoryLocationGroupDescriptionEdit();
                                
                                result.setEdit(edit);
                                edit.setDescription(inventoryLocationGroupDescription.getDescription());
                            } else {
                                addExecutionError(ExecutionErrors.EntityLockFailed.name());
                            }
                            
                            result.setEntityLock(getEntityLockTransfer(inventoryLocationGroup));
                        } else {
                            addExecutionError(ExecutionErrors.UnknownInventoryLocationGroupDescription.name());
                        }
                    } else if(editMode.equals(EditMode.UPDATE)) {
                        InventoryLocationGroupDescriptionValue inventoryLocationGroupDescriptionValue = inventoryControl.getInventoryLocationGroupDescriptionValueForUpdate(inventoryLocationGroup, language);
                        
                        if(inventoryLocationGroupDescriptionValue != null) {
                            if(lockEntityForUpdate(inventoryLocationGroup)) {
                                try {
                                    String description = edit.getDescription();
                                    
                                    inventoryLocationGroupDescriptionValue.setDescription(description);
                                    
                                    inventoryControl.updateInventoryLocationGroupDescriptionFromValue(inventoryLocationGroupDescriptionValue, getPartyPK());
                                } finally {
                                    unlockEntity(inventoryLocationGroup);
                                }
                            } else {
                                addExecutionError(ExecutionErrors.EntityLockStale.name());
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownInventoryLocationGroupDescription.name());
                        }
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownInventoryLocationGroupName.name(), inventoryLocationGroupName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownWarehouseName.name(), warehouseName);
        }
        
        return result;
    }
    
}
