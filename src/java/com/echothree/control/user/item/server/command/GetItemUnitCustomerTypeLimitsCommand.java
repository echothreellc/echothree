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

import com.echothree.control.user.item.common.form.GetItemUnitCustomerTypeLimitsForm;
import com.echothree.control.user.item.common.result.ItemResultFactory;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.item.server.logic.ItemLogic;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.item.server.entity.ItemUnitCustomerTypeLimit;
import com.echothree.model.data.item.server.factory.ItemUnitCustomerTypeLimitFactory;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetItemUnitCustomerTypeLimitsCommand
        extends BasePaginatedMultipleEntitiesCommand<ItemUnitCustomerTypeLimit, GetItemUnitCustomerTypeLimitsForm> {
    
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

    /** Creates a new instance of GetItemUnitCustomerTypeLimitsCommand */
    public GetItemUnitCustomerTypeLimitsCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }
    
    Item item;

    @Override
    protected void handleForm() {
        item = itemLogic.getItemByName(this, form.getItemName());
    }

    @Override
    protected Long getTotalEntities() {
        return hasExecutionErrors() ? null : itemControl.countItemUnitCustomerTypeLimitsByItem(item);
    }

    @Override
    protected Collection<ItemUnitCustomerTypeLimit> getEntities() {
        return hasExecutionErrors() ? null : itemControl.getItemUnitCustomerTypeLimitsByItem(item);
    }

    @Override
    protected BaseResult getResult(Collection<ItemUnitCustomerTypeLimit> entities) {
        var result = ItemResultFactory.getGetItemUnitCustomerTypeLimitsResult();

        if(entities != null) {
            var userVisit = getUserVisit();

            if(session.hasLimit(ItemUnitCustomerTypeLimitFactory.class)) {
                result.setItemUnitCustomerTypeLimitCount(getTotalEntities());
            }

            result.setItem(itemControl.getItemTransfer(userVisit, item));
            result.setItemUnitCustomerTypeLimits(itemControl.getItemUnitCustomerTypeLimitTransfers(userVisit, entities));
        }

        return result;
    }

}
