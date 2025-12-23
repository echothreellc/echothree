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

import com.echothree.control.user.item.common.form.GetItemInventoryTypeForm;
import com.echothree.control.user.item.common.result.ItemResultFactory;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.item.server.logic.ItemInventoryTypeLogic;
import com.echothree.model.data.item.server.entity.ItemInventoryType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import com.echothree.util.server.persistence.Session;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class GetItemInventoryTypeCommand
        extends BaseSingleEntityCommand<ItemInventoryType, GetItemInventoryTypeForm> {

    // No COMMAND_SECURITY_DEFINITION, anyone may execute this command.
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("ItemInventoryTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null)
        );
    }

    /** Creates a new instance of GetItemInventoryTypeCommand */
    public GetItemInventoryTypeCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }

    @Override
    protected ItemInventoryType getEntity() {
        var itemControl = Session.getModelController(ItemControl.class);
        ItemInventoryType itemInventoryType = null;
        var itemInventoryTypeName = form.getItemInventoryTypeName();
        var parameterCount = (itemInventoryTypeName == null ? 0 : 1) + EntityInstanceLogic.getInstance().countPossibleEntitySpecs(form);

        if(parameterCount == 1) {
            if(itemInventoryTypeName == null) {
                var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(this, form,
                        ComponentVendors.ECHO_THREE.name(), EntityTypes.ItemInventoryType.name());

                if(!hasExecutionErrors()) {
                    itemInventoryType = itemControl.getItemInventoryTypeByEntityInstance(entityInstance);
                }
            } else {
                itemInventoryType = ItemInventoryTypeLogic.getInstance().getItemInventoryTypeByName(this, itemInventoryTypeName);
            }

            if(itemInventoryType != null) {
                sendEvent(itemInventoryType.getPrimaryKey(), EventTypes.READ, null, null, getPartyPK());
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }

        return itemInventoryType;
    }

    @Override
    protected BaseResult getResult(ItemInventoryType itemInventoryType) {
        var itemControl = Session.getModelController(ItemControl.class);
        var result = ItemResultFactory.getGetItemInventoryTypeResult();

        if(itemInventoryType != null) {
            result.setItemInventoryType(itemControl.getItemInventoryTypeTransfer(getUserVisit(), itemInventoryType));
        }

        return result;
    }

}
