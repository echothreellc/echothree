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

import com.echothree.control.user.inventory.common.edit.InventoryConditionDescriptionEdit;
import com.echothree.control.user.inventory.common.edit.InventoryEditFactory;
import com.echothree.control.user.inventory.common.form.EditInventoryConditionDescriptionForm;
import com.echothree.control.user.inventory.common.result.EditInventoryConditionDescriptionResult;
import com.echothree.control.user.inventory.common.result.InventoryResultFactory;
import com.echothree.control.user.inventory.common.spec.InventoryConditionDescriptionSpec;
import com.echothree.model.control.inventory.server.InventoryControl;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.data.inventory.server.entity.InventoryCondition;
import com.echothree.model.data.inventory.server.entity.InventoryConditionDescription;
import com.echothree.model.data.inventory.server.value.InventoryConditionDescriptionValue;
import com.echothree.model.data.party.server.entity.Language;
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

public class EditInventoryConditionDescriptionCommand
        extends BaseEditCommand<InventoryConditionDescriptionSpec, InventoryConditionDescriptionEdit> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        List<FieldDefinition> temp = new ArrayList<>(2);
        temp.add(new FieldDefinition("InventoryConditionName", FieldType.ENTITY_NAME, true, null, null));
        temp.add(new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null));
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(temp);
        
        temp = new ArrayList<>(1);
        temp.add(new FieldDefinition("Description", FieldType.STRING, true, 1L, 80L));
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(temp);
    }
    
    /** Creates a new instance of EditInventoryConditionDescriptionCommand */
    public EditInventoryConditionDescriptionCommand(UserVisitPK userVisitPK, EditInventoryConditionDescriptionForm form) {
        super(userVisitPK, form, null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        InventoryControl inventoryControl = (InventoryControl)Session.getModelController(InventoryControl.class);
        EditInventoryConditionDescriptionResult result = InventoryResultFactory.getEditInventoryConditionDescriptionResult();
        String inventoryConditionName = spec.getInventoryConditionName();
        InventoryCondition inventoryCondition = inventoryControl.getInventoryConditionByName(inventoryConditionName);
        
        if(inventoryCondition != null) {
            PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);
            String languageIsoName = spec.getLanguageIsoName();
            Language language = partyControl.getLanguageByIsoName(languageIsoName);
            
            if(language != null) {
                if(editMode.equals(EditMode.LOCK)) {
                    InventoryConditionDescription inventoryConditionDescription = inventoryControl.getInventoryConditionDescription(inventoryCondition, language);
                    
                    if(inventoryConditionDescription != null) {
                        result.setInventoryConditionDescription(inventoryControl.getInventoryConditionDescriptionTransfer(getUserVisit(), inventoryConditionDescription));
                        
                        if(lockEntity(inventoryCondition)) {
                            InventoryConditionDescriptionEdit edit = InventoryEditFactory.getInventoryConditionDescriptionEdit();
                            
                            result.setEdit(edit);
                            edit.setDescription(inventoryConditionDescription.getDescription());
                        } else {
                            addExecutionError(ExecutionErrors.EntityLockFailed.name());
                        }
                        
                        result.setEntityLock(getEntityLockTransfer(inventoryCondition));
                    } else {
                        addExecutionError(ExecutionErrors.UnknownInventoryConditionDescription.name());
                    }
                } else if(editMode.equals(EditMode.UPDATE)) {
                    InventoryConditionDescriptionValue inventoryConditionDescriptionValue = inventoryControl.getInventoryConditionDescriptionValueForUpdate(inventoryCondition, language);
                    
                    if(inventoryConditionDescriptionValue != null) {
                        if(lockEntityForUpdate(inventoryCondition)) {
                            try {
                                String description = edit.getDescription();
                                
                                inventoryConditionDescriptionValue.setDescription(description);
                                
                                inventoryControl.updateInventoryConditionDescriptionFromValue(inventoryConditionDescriptionValue, getPartyPK());
                            } finally {
                                unlockEntity(inventoryCondition);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.EntityLockStale.name());
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownInventoryConditionDescription.name());
                    }
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownInventoryConditionName.name(), inventoryConditionName);
        }
        
        return result;
    }
    
}
