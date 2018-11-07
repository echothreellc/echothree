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

import com.echothree.control.user.inventory.common.edit.InventoryConditionEdit;
import com.echothree.control.user.inventory.common.edit.InventoryEditFactory;
import com.echothree.control.user.inventory.common.form.EditInventoryConditionForm;
import com.echothree.control.user.inventory.common.result.EditInventoryConditionResult;
import com.echothree.control.user.inventory.common.result.InventoryResultFactory;
import com.echothree.control.user.inventory.common.spec.InventoryConditionSpec;
import com.echothree.model.control.inventory.server.InventoryControl;
import com.echothree.model.data.inventory.server.entity.InventoryCondition;
import com.echothree.model.data.inventory.server.entity.InventoryConditionDescription;
import com.echothree.model.data.inventory.server.entity.InventoryConditionDetail;
import com.echothree.model.data.inventory.server.value.InventoryConditionDescriptionValue;
import com.echothree.model.data.inventory.server.value.InventoryConditionDetailValue;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.user.common.pk.UserVisitPK;
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

public class EditInventoryConditionCommand
        extends BaseEditCommand<InventoryConditionSpec, InventoryConditionEdit> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        List<FieldDefinition> temp = new ArrayList<>(1);
        temp.add(new FieldDefinition("InventoryConditionName", FieldType.ENTITY_NAME, true, null, null));
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(temp);
        
        temp = new ArrayList<>(4);
        temp.add(new FieldDefinition("InventoryConditionName", FieldType.ENTITY_NAME, true, null, null));
        temp.add(new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null));
        temp.add(new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null));
        temp.add(new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L));
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(temp);
    }
    
    /** Creates a new instance of EditInventoryConditionCommand */
    public EditInventoryConditionCommand(UserVisitPK userVisitPK, EditInventoryConditionForm form) {
        super(userVisitPK, form, null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        InventoryControl inventoryControl = (InventoryControl)Session.getModelController(InventoryControl.class);
        EditInventoryConditionResult result = InventoryResultFactory.getEditInventoryConditionResult();
        
        if(editMode.equals(EditMode.LOCK)) {
            String inventoryConditionName = spec.getInventoryConditionName();
            InventoryCondition inventoryCondition = inventoryControl.getInventoryConditionByName(inventoryConditionName);
            
            if(inventoryCondition != null) {
                if(lockEntity(inventoryCondition)) {
                    InventoryConditionDescription inventoryConditionDescription = inventoryControl.getInventoryConditionDescription(inventoryCondition, getPreferredLanguage());
                    InventoryConditionEdit edit = InventoryEditFactory.getInventoryConditionEdit();
                    InventoryConditionDetail inventoryConditionDetail = inventoryCondition.getLastDetail();
                    
                    result.setEdit(edit);
                    edit.setInventoryConditionName(inventoryConditionDetail.getInventoryConditionName());
                    edit.setIsDefault(inventoryConditionDetail.getIsDefault().toString());
                    edit.setSortOrder(inventoryConditionDetail.getSortOrder().toString());
                    
                    if(inventoryConditionDescription != null)
                        edit.setDescription(inventoryConditionDescription.getDescription());
                } else {
                    addExecutionError(ExecutionErrors.EntityLockFailed.name());
                }
                
                result.setEntityLock(getEntityLockTransfer(inventoryCondition));
            } else {
                addExecutionError(ExecutionErrors.UnknownInventoryConditionName.name(), inventoryConditionName);
            }
        } else if(editMode.equals(EditMode.UPDATE)) {
            String inventoryConditionName = spec.getInventoryConditionName();
            InventoryCondition inventoryCondition = inventoryControl.getInventoryConditionByNameForUpdate(inventoryConditionName);
            
            if(inventoryCondition != null) {
                if(lockEntityForUpdate(inventoryCondition)) {
                    try {
                        PartyPK partyPK = getPartyPK();
                        InventoryConditionDetailValue inventoryConditionDetailValue = inventoryControl.getInventoryConditionDetailValueForUpdate(inventoryCondition);
                        InventoryConditionDescription inventoryConditionDescription = inventoryControl.getInventoryConditionDescriptionForUpdate(inventoryCondition, getPreferredLanguage());
                        String description = edit.getDescription();
                        
                        inventoryConditionDetailValue.setInventoryConditionName(edit.getInventoryConditionName());
                        inventoryConditionDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
                        inventoryConditionDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));
                        
                        inventoryControl.updateInventoryConditionFromValue(inventoryConditionDetailValue, partyPK);
                        
                        if(inventoryConditionDescription == null && description != null) {
                            inventoryControl.createInventoryConditionDescription(inventoryCondition, getPreferredLanguage(), description, partyPK);
                        } else if(inventoryConditionDescription != null && description == null) {
                            inventoryControl.deleteInventoryConditionDescription(inventoryConditionDescription, partyPK);
                        } else if(inventoryConditionDescription != null && description != null) {
                            InventoryConditionDescriptionValue inventoryConditionDescriptionValue = inventoryControl.getInventoryConditionDescriptionValue(inventoryConditionDescription);
                            
                            inventoryConditionDescriptionValue.setDescription(description);
                            inventoryControl.updateInventoryConditionDescriptionFromValue(inventoryConditionDescriptionValue, partyPK);
                        }
                    } finally {
                        unlockEntity(inventoryCondition);
                    }
                } else {
                    addExecutionError(ExecutionErrors.EntityLockStale.name());
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownInventoryConditionName.name(), inventoryConditionName);
            }
        }
        
        return result;
    }
    
}
