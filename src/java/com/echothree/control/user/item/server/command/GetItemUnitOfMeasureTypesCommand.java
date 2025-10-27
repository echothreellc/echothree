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

package com.echothree.control.user.item.server.command;

import com.echothree.control.user.item.common.form.GetItemUnitOfMeasureTypesForm;
import com.echothree.control.user.item.common.result.ItemResultFactory;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.item.server.logic.ItemLogic;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.item.server.entity.ItemUnitOfMeasureType;
import com.echothree.model.data.item.server.factory.ItemUnitOfMeasureTypeFactory;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseMultipleEntitiesCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class GetItemUnitOfMeasureTypesCommand
        extends BaseMultipleEntitiesCommand<ItemUnitOfMeasureType, GetItemUnitOfMeasureTypesForm> {

    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("ItemName", FieldType.ENTITY_NAME, true, null, null)
        );
    }
    
    /** Creates a new instance of GetItemUnitOfMeasureTypesCommand */
    public GetItemUnitOfMeasureTypesCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }

    Item item;

    @Override
    protected Collection<ItemUnitOfMeasureType> getEntities() {
        Collection<ItemUnitOfMeasureType> entities = null;

        item = ItemLogic.getInstance().getItemByName(this, form.getItemName());

        if(!hasExecutionErrors()) {
            var itemControl = Session.getModelController(ItemControl.class);

            entities = itemControl.getItemUnitOfMeasureTypesByItem(item);
        }

        return entities;
    }

    @Override
    protected BaseResult getResult(Collection<ItemUnitOfMeasureType> entities) {
        var result = ItemResultFactory.getGetItemUnitOfMeasureTypesResult();

        if(entities != null) {
            var itemControl = Session.getModelController(ItemControl.class);
            var userVisit = getUserVisit();

            if(session.hasLimit(ItemUnitOfMeasureTypeFactory.class)) {
                result.setItemUnitOfMeasureTypeCount(itemControl.countItemUnitOfMeasureTypesByItem(item));
            }

            result.setItem(itemControl.getItemTransfer(userVisit, item));
            result.setItemUnitOfMeasureTypes(itemControl.getItemUnitOfMeasureTypeTransfers(userVisit, entities));
        }

        return result;
    }

}
