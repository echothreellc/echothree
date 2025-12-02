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

import com.echothree.control.user.inventory.common.form.GetInventoryConditionUsesForm;
import com.echothree.control.user.inventory.common.result.InventoryResultFactory;
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
import javax.enterprise.context.Dependent;

@Dependent
public class GetInventoryConditionUsesCommand
        extends BaseSimpleCommand<GetInventoryConditionUsesForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("InventoryConditionName", FieldType.ENTITY_NAME, false, null, null),
            new FieldDefinition("InventoryConditionUseTypeName", FieldType.ENTITY_NAME, false, null, null)
        ));
    }
    
    /** Creates a new instance of GetInventoryConditionUsesCommand */
    public GetInventoryConditionUsesCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected BaseResult execute() {
        var inventoryControl = Session.getModelController(InventoryControl.class);
        var result = InventoryResultFactory.getGetInventoryConditionUsesResult();
        var inventoryConditionName = form.getInventoryConditionName();
        var inventoryConditionUseTypeName = form.getInventoryConditionUseTypeName();
        var parameterCount = (inventoryConditionName == null ? 0 : 1) + (inventoryConditionUseTypeName == null ? 0 : 1);
        
        if(parameterCount == 1) {
            var userVisit = getUserVisit();
            
            if(inventoryConditionName != null) {
                var inventoryCondition = inventoryControl.getInventoryConditionByName(inventoryConditionName);
                
                if(inventoryCondition != null) {
                    result.setInventoryCondition(inventoryControl.getInventoryConditionTransfer(userVisit, inventoryCondition));
                    result.setInventoryConditionUses(inventoryControl.getInventoryConditionUseTransfersByInventoryCondition(userVisit, inventoryCondition));
                } else {
                    addExecutionError(ExecutionErrors.UnknownInventoryConditionName.name(), inventoryConditionName);
                }
            } else if(inventoryConditionUseTypeName != null) {
                var inventoryConditionUseType = inventoryControl.getInventoryConditionUseTypeByName(inventoryConditionUseTypeName);
                
                if(inventoryConditionUseType != null) {
                    result.setInventoryConditionUseType(inventoryControl.getInventoryConditionUseTypeTransfer(userVisit, inventoryConditionUseType));
                    result.setInventoryConditionUses(inventoryControl.getInventoryConditionUseTransfersByInventoryConditionUseType(userVisit, inventoryConditionUseType));
                } else {
                    addExecutionError(ExecutionErrors.UnknownInventoryConditionUseTypeName.name(), inventoryConditionUseTypeName);
                }
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }
        
        return result;
    }
    
}
