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

import com.echothree.control.user.item.common.form.GetItemUnitOfMeasureTypeForm;
import com.echothree.control.user.item.common.result.ItemResultFactory;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.item.server.logic.ItemLogic;
import com.echothree.model.control.uom.server.logic.UnitOfMeasureTypeLogic;
import com.echothree.model.data.item.server.entity.ItemUnitOfMeasureType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class GetItemUnitOfMeasureTypeCommand
        extends BaseSingleEntityCommand<ItemUnitOfMeasureType, GetItemUnitOfMeasureTypeForm> {

    // No COMMAND_SECURITY_DEFINITION, anyone may execute this command.
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ItemName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("UnitOfMeasureTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of GetItemUnitOfMeasureTypeCommand */
    public GetItemUnitOfMeasureTypeCommand() {
        super(null, FORM_FIELD_DEFINITIONS, false);
    }


    @Override
    protected ItemUnitOfMeasureType getEntity() {
        var item = ItemLogic.getInstance().getItemByName(this, form.getItemName());
        var unitOfMeasureType = UnitOfMeasureTypeLogic.getInstance().getUnitOfMeasureTypeByName(this,
                item.getLastDetail().getUnitOfMeasureKind(), form.getUnitOfMeasureTypeName());
        ItemUnitOfMeasureType itemUnitOfMeasureType = null;

        if(!hasExecutionErrors()) {
            var itemControl = Session.getModelController(ItemControl.class);

            itemUnitOfMeasureType = itemControl.getItemUnitOfMeasureType(item, unitOfMeasureType);

            if(itemUnitOfMeasureType == null) {
                addExecutionError(ExecutionErrors.UnknownItemUnitOfMeasureType.name(),
                        item.getLastDetail().getItemName(), unitOfMeasureType.getLastDetail().getUnitOfMeasureTypeName());
            }
        }

        return itemUnitOfMeasureType;
    }

    @Override
    protected BaseResult getResult(ItemUnitOfMeasureType entity) {
        var result = ItemResultFactory.getGetItemUnitOfMeasureTypeResult();

        if(entity != null) {
            var itemControl = Session.getModelController(ItemControl.class);

            result.setItemUnitOfMeasureType(itemControl.getItemUnitOfMeasureTypeTransfer(getUserVisit(), entity));
        }

        return result;
    }

}
