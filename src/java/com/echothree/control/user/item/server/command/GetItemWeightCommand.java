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

package com.echothree.control.user.item.server.command;

import com.echothree.control.user.item.common.form.GetItemWeightForm;
import com.echothree.control.user.item.common.result.ItemResultFactory;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.item.server.logic.ItemLogic;
import com.echothree.model.control.item.server.logic.ItemWeightTypeLogic;
import com.echothree.model.control.uom.server.logic.UnitOfMeasureTypeLogic;
import com.echothree.model.data.item.server.entity.ItemWeight;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetItemWeightCommand
        extends BaseSingleEntityCommand<ItemWeight, GetItemWeightForm> {

    // No COMMAND_SECURITY_DEFINITION, anyone may execute this command.
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("ItemName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("UnitOfMeasureTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ItemWeightTypeName", FieldType.ENTITY_NAME, true, null, null)
        );
    }

    @Inject
    ItemControl itemControl;

    @Inject
    ItemLogic itemLogic;

    @Inject
    ItemWeightTypeLogic itemWeightTypeLogic;

    @Inject
    UnitOfMeasureTypeLogic unitOfMeasureTypeLogic;

    /** Creates a new instance of GetItemWeightCommand */
    public GetItemWeightCommand() {
        super(null, FORM_FIELD_DEFINITIONS, false);
    }

    @Override
    protected ItemWeight getEntity() {
        ItemWeight itemWeight = null;
        var itemName = form.getItemName();
        var item = itemLogic.getItemByName(this, itemName);

        if(!hasExecutionErrors()) {
            var unitOfMeasureTypeName = form.getUnitOfMeasureTypeName();
            var unitOfMeasureKind = item.getLastDetail().getUnitOfMeasureKind();
            var unitOfMeasureType = unitOfMeasureTypeLogic.getUnitOfMeasureTypeByName(this, unitOfMeasureKind, unitOfMeasureTypeName);

            if(!hasExecutionErrors()) {
                var itemWeightType = itemWeightTypeLogic.getItemWeightTypeByName(this, form.getItemWeightTypeName());

                if(!hasExecutionErrors()) {
                    itemWeight = itemControl.getItemWeight(item, unitOfMeasureType, itemWeightType);

                    if(itemWeight == null) {
                        addExecutionError(ExecutionErrors.UnknownItemWeight.name(), item.getLastDetail().getItemName(),
                                unitOfMeasureType.getLastDetail().getUnitOfMeasureTypeName(),
                                itemWeightType.getLastDetail().getItemWeightTypeName());
                    }
                }
            }
        }

        return itemWeight;
    }

    @Override
    protected BaseResult getResult(ItemWeight itemWeight) {
        var result = ItemResultFactory.getGetItemWeightResult();

        if(itemWeight != null) {
            result.setItemWeight(itemControl.getItemWeightTransfer(getUserVisit(), itemWeight));
        }

        return result;
    }
    
}
