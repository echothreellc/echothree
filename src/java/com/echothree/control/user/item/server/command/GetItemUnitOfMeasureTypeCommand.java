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
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetItemUnitOfMeasureTypeCommand
        extends BaseSingleEntityCommand<ItemUnitOfMeasureType, GetItemUnitOfMeasureTypeForm> {

    // No COMMAND_SECURITY_DEFINITION, anyone may execute this command.
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("ItemName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("UnitOfMeasureTypeName", FieldType.ENTITY_NAME, true, null, null)
        );
    }

    @Inject
    ItemControl itemControl;

    @Inject
    ItemLogic itemLogic;

    @Inject
    UnitOfMeasureTypeLogic unitOfMeasureTypeLogic;

    
    /** Creates a new instance of GetItemUnitOfMeasureTypeCommand */
    public GetItemUnitOfMeasureTypeCommand() {
        super(null, FORM_FIELD_DEFINITIONS, false);
    }


    @Override
    protected ItemUnitOfMeasureType getEntity() {
        var item = itemLogic.getItemByName(this, form.getItemName());
        var unitOfMeasureType = unitOfMeasureTypeLogic.getUnitOfMeasureTypeByName(this,
                item.getLastDetail().getUnitOfMeasureKind(), form.getUnitOfMeasureTypeName());
        ItemUnitOfMeasureType itemUnitOfMeasureType = null;

        if(!hasExecutionErrors()) {
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
            result.setItemUnitOfMeasureType(itemControl.getItemUnitOfMeasureTypeTransfer(getUserVisit(), entity));
        }

        return result;
    }

}
