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

package com.echothree.control.user.inventory.server.command;

import com.echothree.control.user.inventory.common.form.CreateInventoryConditionUseForm;
import com.echothree.model.control.inventory.server.control.InventoryControl;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class CreateInventoryConditionUseCommand
        extends BaseSimpleCommand<CreateInventoryConditionUseForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("InventoryConditionName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("InventoryConditionUseTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null)
                ));
    }
    
    /** Creates a new instance of CreateInventoryConditionUseCommand */
    public CreateInventoryConditionUseCommand() {
        super(null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var inventoryControl = Session.getModelController(InventoryControl.class);
        var inventoryConditionUseTypeName = form.getInventoryConditionUseTypeName();
        var inventoryConditionUseType = inventoryControl.getInventoryConditionUseTypeByName(inventoryConditionUseTypeName);
        
        if(inventoryConditionUseType != null) {
            var inventoryConditionName = form.getInventoryConditionName();
            var inventoryCondition = inventoryControl.getInventoryConditionByName(inventoryConditionName);
            
            if(inventoryCondition != null) {
                var inventoryConditionUse = inventoryControl.getInventoryConditionUse(inventoryConditionUseType, inventoryCondition);
                
                if(inventoryConditionUse == null) {
                    var isDefault = Boolean.valueOf(form.getIsDefault());
                    
                    inventoryControl.createInventoryConditionUse(inventoryConditionUseType, inventoryCondition, isDefault, getPartyPK());
                } else {
                    addExecutionError(ExecutionErrors.DuplicateInventoryConditionUse.name());
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownInventoryConditionName.name(), inventoryConditionName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownInventoryConditionUseTypeName.name(), inventoryConditionUseTypeName);
        }
        
        return null;
    }
    
}
