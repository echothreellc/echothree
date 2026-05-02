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

import com.echothree.control.user.inventory.common.form.GetInventoryConditionUseTypesForm;
import com.echothree.control.user.inventory.common.result.InventoryResultFactory;
import com.echothree.model.control.inventory.server.control.InventoryControl;
import com.echothree.model.data.inventory.server.entity.InventoryConditionUseType;
import com.echothree.model.data.inventory.server.factory.InventoryConditionUseTypeFactory;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetInventoryConditionUseTypesCommand
        extends BasePaginatedMultipleEntitiesCommand<InventoryConditionUseType, GetInventoryConditionUseTypesForm> {

    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        FORM_FIELD_DEFINITIONS = List.of();
    }

    @Inject
    InventoryControl inventoryControl;

    /** Creates a new instance of GetInventoryConditionUseTypesCommand */
    public GetInventoryConditionUseTypesCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }

    @Override
    protected void handleForm() {
        // No form fields.
    }

    @Override
    protected Long getTotalEntities() {
        return inventoryControl.countInventoryConditionUseTypes();
    }

    @Override
    protected Collection<InventoryConditionUseType> getEntities() {
        return inventoryControl.getInventoryConditionUseTypes();
    }

    @Override
    protected BaseResult getResult(Collection<InventoryConditionUseType> entities) {
        var result = InventoryResultFactory.getGetInventoryConditionUseTypesResult();

        if(entities != null) {
            if(session.hasLimit(InventoryConditionUseTypeFactory.class)) {
                result.setInventoryConditionUseTypeCount(getTotalEntities());
            }

            result.setInventoryConditionUseTypes(inventoryControl.getInventoryConditionUseTypeTransfers(getUserVisit(), entities));
        }

        return result;
    }

}
