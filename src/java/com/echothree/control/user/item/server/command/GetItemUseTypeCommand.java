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

import com.echothree.control.user.item.common.form.GetItemUseTypeForm;
import com.echothree.control.user.item.common.result.ItemResultFactory;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.item.server.logic.ItemUseTypeLogic;
import com.echothree.model.data.item.server.entity.ItemUseType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import com.echothree.util.server.persistence.Session;
import java.util.List;

public class GetItemUseTypeCommand
        extends BaseSingleEntityCommand<ItemUseType, GetItemUseTypeForm> {

    // No COMMAND_SECURITY_DEFINITION, anyone may execute this command.
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("ItemUseTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null)
        );
    }

    /** Creates a new instance of GetItemUseTypeCommand */
    public GetItemUseTypeCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }

    @Override
    protected ItemUseType getEntity() {
        var itemControl = Session.getModelController(ItemControl.class);
        ItemUseType itemUseType = null;
        var itemUseTypeName = form.getItemUseTypeName();
        var parameterCount = (itemUseTypeName == null ? 0 : 1) + EntityInstanceLogic.getInstance().countPossibleEntitySpecs(form);

        if(parameterCount == 1) {
            if(itemUseTypeName == null) {
                var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(this, form,
                        ComponentVendors.ECHO_THREE.name(), EntityTypes.ItemUseType.name());

                if(!hasExecutionErrors()) {
                    itemUseType = itemControl.getItemUseTypeByEntityInstance(entityInstance);
                }
            } else {
                itemUseType = ItemUseTypeLogic.getInstance().getItemUseTypeByName(this, itemUseTypeName);
            }

            if(itemUseType != null) {
                sendEvent(itemUseType.getPrimaryKey(), EventTypes.READ, null, null, getPartyPK());
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }

        return itemUseType;
    }

    @Override
    protected BaseResult getResult(ItemUseType itemUseType) {
        var itemControl = Session.getModelController(ItemControl.class);
        var result = ItemResultFactory.getGetItemUseTypeResult();

        if(itemUseType != null) {
            result.setItemUseType(itemControl.getItemUseTypeTransfer(getUserVisit(), itemUseType));
        }

        return result;
    }

}
