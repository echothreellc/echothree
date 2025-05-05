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

import com.echothree.control.user.item.common.form.GetItemAliasesForm;
import com.echothree.control.user.item.common.result.ItemResultFactory;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.item.server.logic.ItemLogic;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.item.server.entity.ItemAlias;
import com.echothree.model.data.item.server.factory.ItemAliasFactory;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseMultipleEntitiesCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class GetItemAliasesCommand
        extends BaseMultipleEntitiesCommand<ItemAlias, GetItemAliasesForm> {

    // No COMMAND_SECURITY_DEFINITION, anyone may execute this command.
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ItemName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of GetItemAliasesCommand */
    public GetItemAliasesCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }

    Item item;

    @Override
    protected Collection<ItemAlias> getEntities() {
        var itemControl = Session.getModelController(ItemControl.class);
        Collection<ItemAlias> itemAliases = null;

        item = ItemLogic.getInstance().getItemByName(this, form.getItemName());

        if(!hasExecutionErrors()) {
            itemAliases = itemControl.getItemAliasesByItem(item);
        }

        return itemAliases;
    }

    @Override
    protected BaseResult getResult(Collection<ItemAlias> entities) {
        var result = ItemResultFactory.getGetItemAliasesResult();

        if(entities != null) {
            var itemControl = Session.getModelController(ItemControl.class);
            var userVisit = getUserVisit();

            if(session.hasLimit(ItemAliasFactory.class)) {
                result.setItemAliasCount(itemControl.countItemAliasesByItem(item));
            }

            result.setItem(itemControl.getItemTransfer(userVisit, item));
            result.setItemAliases(itemControl.getItemAliasTransfers(userVisit, entities));
        }

        return result;
    }

}
