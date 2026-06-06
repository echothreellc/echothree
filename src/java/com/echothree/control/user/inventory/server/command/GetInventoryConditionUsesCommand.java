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

package com.echothree.control.user.inventory.server.command;

import com.echothree.control.user.inventory.common.form.GetInventoryConditionUsesForm;
import com.echothree.control.user.inventory.common.result.InventoryResultFactory;
import com.echothree.model.control.inventory.server.control.InventoryControl;
import com.echothree.model.control.inventory.server.logic.InventoryConditionLogic;
import com.echothree.model.data.inventory.server.entity.InventoryCondition;
import com.echothree.model.data.inventory.server.entity.InventoryConditionUse;
import com.echothree.model.data.inventory.server.entity.InventoryConditionUseType;
import com.echothree.model.data.inventory.server.factory.InventoryConditionUseFactory;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetInventoryConditionUsesCommand
        extends BasePaginatedMultipleEntitiesCommand<InventoryConditionUse, GetInventoryConditionUsesForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("InventoryConditionName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("InventoryConditionUseTypeName", FieldType.ENTITY_NAME, false, null, null)
        );
    }
    
    @Inject
    InventoryControl inventoryControl;

    @Inject
    InventoryConditionLogic inventoryConditionLogic;

    /** Creates a new instance of GetInventoryConditionUsesCommand */
    public GetInventoryConditionUsesCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }

    private InventoryCondition inventoryCondition;
    private InventoryConditionUseType inventoryConditionUseType;

    @Override
    protected void handleForm() {
        var inventoryConditionName = form.getInventoryConditionName();
        var inventoryConditionUseTypeName = form.getInventoryConditionUseTypeName();
        var parameterCount = (inventoryConditionName == null ? 0 : 1) + (inventoryConditionUseTypeName == null ? 0 : 1);

        if(parameterCount == 1) {
            if(inventoryConditionName != null) {
                inventoryCondition = inventoryConditionLogic.getInventoryConditionByName(this, inventoryConditionName);
            } else {
                inventoryConditionUseType = inventoryControl.getInventoryConditionUseTypeByName(inventoryConditionUseTypeName);

                if(inventoryConditionUseType == null) {
                    addExecutionError(ExecutionErrors.UnknownInventoryConditionUseTypeName.name(), inventoryConditionUseTypeName);
                }
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }
    }

    @Override
    protected Long getTotalEntities() {
        Long total = null;

        if(!hasExecutionErrors()) {
            if(inventoryCondition != null) {
                total = inventoryControl.countInventoryConditionUsesByInventoryCondition(inventoryCondition);
            } else {
                total = inventoryControl.countInventoryConditionUsesByInventoryConditionUseType(inventoryConditionUseType);
            }
        }

        return total;
    }

    @Override
    protected Collection<InventoryConditionUse> getEntities() {
        Collection<InventoryConditionUse> entities = null;

        if(!hasExecutionErrors()) {
            if(inventoryCondition != null) {
                entities = inventoryControl.getInventoryConditionUsesByInventoryCondition(inventoryCondition);
            } else {
                entities = inventoryControl.getInventoryConditionUsesByInventoryConditionUseType(inventoryConditionUseType);
            }
        }

        return entities;
    }

    @Override
    protected BaseResult getResult(Collection<InventoryConditionUse> entities) {
        var result = InventoryResultFactory.getGetInventoryConditionUsesResult();

        if(entities != null) {
            var userVisit = getUserVisit();

            if(inventoryCondition != null) {
                result.setInventoryCondition(inventoryControl.getInventoryConditionTransfer(userVisit, inventoryCondition));
            } else {
                result.setInventoryConditionUseType(inventoryControl.getInventoryConditionUseTypeTransfer(userVisit, inventoryConditionUseType));
            }

            if(session.hasLimit(InventoryConditionUseFactory.class)) {
                result.setInventoryConditionUseCount(getTotalEntities());
            }

            result.setInventoryConditionUses(inventoryControl.getInventoryConditionUseTransfers(userVisit, entities));
        }

        return result;
    }
    
}
