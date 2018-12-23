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

package com.echothree.control.user.item.server.command;

import com.echothree.control.user.item.common.form.GetItemForm;
import com.echothree.control.user.item.common.result.GetItemResult;
import com.echothree.control.user.item.common.result.ItemResultFactory;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.item.server.ItemControl;
import com.echothree.model.control.item.server.logic.ItemLogic;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GetItemCommand
        extends BaseSingleEntityCommand<Item, GetItemForm> {
    
    // No COMMAND_SECURITY_DEFINITION, anyone may execute this command.
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ItemName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ItemNameOrAlias", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Key", FieldType.KEY, false, null, null),
                new FieldDefinition("Guid", FieldType.GUID, false, null, null),
                new FieldDefinition("Ulid", FieldType.ULID, false, null, null)
                ));
    }
    
    /** Creates a new instance of GetItemCommand */
    public GetItemCommand(UserVisitPK userVisitPK, GetItemForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected Item getEntity() {
        ItemControl itemControl = (ItemControl)Session.getModelController(ItemControl.class);
        Item item = null;
        String itemName = form.getItemName();
        String itemNameOrAlias = form.getItemNameOrAlias();
        int parameterCount = (itemName == null ? 0 : 1) + (itemNameOrAlias == null ? 0 : 1) + EntityInstanceLogic.getInstance().countPossibleEntitySpecs(form);

        if(parameterCount == 1) {
            if(itemName == null && itemNameOrAlias == null) {
                EntityInstance entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(this, form,
                        ComponentVendors.ECHOTHREE.name(), EntityTypes.Item.name());

                if(!hasExecutionErrors()) {
                    item = itemControl.getItemByEntityInstance(entityInstance);
                }
            } else {
                item = itemNameOrAlias == null ? ItemLogic.getInstance().getItemByName(this, itemName) : ItemLogic.getInstance().getItemByNameThenAlias(this, itemNameOrAlias);
            }

            if(item != null) {
                sendEventUsingNames(item.getPrimaryKey(), EventTypes.READ.name(), null, null, getPartyPK());
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }
        
        return item;
    }
    
    @Override
    protected BaseResult getTransfer(Item item) {
        ItemControl itemControl = (ItemControl)Session.getModelController(ItemControl.class);
        GetItemResult result = ItemResultFactory.getGetItemResult();

        if(item != null) {
            result.setItem(itemControl.getItemTransfer(getUserVisit(), item));
        }

        return result;
    }
    
}
