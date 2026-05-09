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

import com.echothree.control.user.item.common.form.GetItemUnitPriceLimitsForm;
import com.echothree.control.user.item.common.result.ItemResultFactory;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.item.server.logic.ItemLogic;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.item.server.entity.ItemUnitPriceLimit;
import com.echothree.model.data.item.server.factory.ItemUnitPriceLimitFactory;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetItemUnitPriceLimitsCommand
        extends BasePaginatedMultipleEntitiesCommand<ItemUnitPriceLimit, GetItemUnitPriceLimitsForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("ItemName", FieldType.ENTITY_NAME, true, null, null)
        );
    }
    
    @Inject
    ItemControl itemControl;

    @Inject
    ItemLogic itemLogic;

    /** Creates a new instance of GetItemUnitPriceLimitsCommand */
    public GetItemUnitPriceLimitsCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }
    
    Item item;

    @Override
    protected void handleForm() {
        item = itemLogic.getItemByName(this, form.getItemName());
    }

    @Override
    protected Long getTotalEntities() {
        return hasExecutionErrors() ? null : itemControl.countItemUnitPriceLimitsByItem(item);
    }

    @Override
    protected Collection<ItemUnitPriceLimit> getEntities() {
        return hasExecutionErrors() ? null : itemControl.getItemUnitPriceLimitsByItem(item);
    }

    @Override
    protected BaseResult getResult(Collection<ItemUnitPriceLimit> entities) {
        var result = ItemResultFactory.getGetItemUnitPriceLimitsResult();

        if(entities != null) {
            var userVisit = getUserVisit();

            result.setItem(itemControl.getItemTransfer(userVisit, item));

            if(session.hasLimit(ItemUnitPriceLimitFactory.class)) {
                result.setItemUnitPriceLimitCount(getTotalEntities());
            }

            result.setItemUnitPriceLimits(itemControl.getItemUnitPriceLimitTransfers(userVisit, entities));
        }

        return result;
    }
    
}
