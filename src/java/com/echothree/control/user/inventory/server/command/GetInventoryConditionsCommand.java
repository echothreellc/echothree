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

import com.echothree.control.user.inventory.common.form.GetInventoryConditionsForm;
import com.echothree.control.user.inventory.common.result.InventoryResultFactory;
import com.echothree.model.control.inventory.server.control.InventoryControl;
import com.echothree.model.data.inventory.server.entity.InventoryCondition;
import com.echothree.model.data.inventory.server.factory.InventoryConditionFactory;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class GetInventoryConditionsCommand
        extends BasePaginatedMultipleEntitiesCommand<InventoryCondition, GetInventoryConditionsForm> {
    
    // No COMMAND_SECURITY_DEFINITION, anyone may execute this command.
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        FORM_FIELD_DEFINITIONS = List.of();
    }
    
    /** Creates a new instance of GetInventoryConditionsCommand */
    public GetInventoryConditionsCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }

    @Override
    protected void handleForm() {
        // No form fields.
    }

    @Override
    protected Long getTotalEntities() {
        var inventoryControl = Session.getModelController(InventoryControl.class);

        return inventoryControl.countInventoryConditions();
    }
    
    @Override
    protected Collection<InventoryCondition> getEntities() {
        var inventoryControl = Session.getModelController(InventoryControl.class);
        
        return inventoryControl.getInventoryConditions();
    }
    
    @Override
    protected BaseResult getResult(Collection<InventoryCondition> entities) {
        var result = InventoryResultFactory.getGetInventoryConditionsResult();

        if(entities != null) {
            var inventoryControl = Session.getModelController(InventoryControl.class);

            if(session.hasLimit(InventoryConditionFactory.class)) {
                result.setInventoryConditionCount(getTotalEntities());
            }

            result.setInventoryConditions(inventoryControl.getInventoryConditionTransfers(getUserVisit(), entities));
        }

        return result;
    }
    
}
