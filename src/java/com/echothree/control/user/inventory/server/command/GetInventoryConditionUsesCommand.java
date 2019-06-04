// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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
import com.echothree.control.user.inventory.common.result.GetInventoryConditionUsesResult;
import com.echothree.control.user.inventory.common.result.InventoryResultFactory;
import com.echothree.model.control.inventory.server.InventoryControl;
import com.echothree.model.data.inventory.server.entity.InventoryCondition;
import com.echothree.model.data.inventory.server.entity.InventoryConditionUseType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
    public GetInventoryConditionUsesCommand(UserVisitPK userVisitPK, GetInventoryConditionUsesForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected BaseResult execute() {
        var inventoryControl = (InventoryControl)Session.getModelController(InventoryControl.class);
        GetInventoryConditionUsesResult result = InventoryResultFactory.getGetInventoryConditionUsesResult();
        String inventoryConditionName = form.getInventoryConditionName();
        String inventoryConditionUseTypeName = form.getInventoryConditionUseTypeName();
        int parameterCount = (inventoryConditionName == null? 0: 1) + (inventoryConditionUseTypeName == null? 0: 1);
        
        if(parameterCount == 1) {
            UserVisit userVisit = getUserVisit();
            
            if(inventoryConditionName != null) {
                InventoryCondition inventoryCondition = inventoryControl.getInventoryConditionByName(inventoryConditionName);
                
                if(inventoryCondition != null) {
                    result.setInventoryCondition(inventoryControl.getInventoryConditionTransfer(userVisit, inventoryCondition));
                    result.setInventoryConditionUses(inventoryControl.getInventoryConditionUseTransfersByInventoryCondition(userVisit, inventoryCondition));
                } else {
                    addExecutionError(ExecutionErrors.UnknownInventoryConditionName.name(), inventoryConditionName);
                }
            } else if(inventoryConditionUseTypeName != null) {
                InventoryConditionUseType inventoryConditionUseType = inventoryControl.getInventoryConditionUseTypeByName(inventoryConditionUseTypeName);
                
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
