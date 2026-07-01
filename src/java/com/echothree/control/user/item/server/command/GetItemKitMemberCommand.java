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

import com.echothree.control.user.item.common.form.GetItemKitMemberForm;
import com.echothree.control.user.item.common.result.ItemResultFactory;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.item.server.logic.ItemKitMemberLogic;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSimpleCommand;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetItemKitMemberCommand
        extends BaseSimpleCommand<GetItemKitMemberForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("ItemName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("InventoryConditionName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("UnitOfMeasureTypeName", FieldType.PERCENT, true, null, null),
                new FieldDefinition("MemberItemName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("MemberInventoryConditionName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("MemberUnitOfMeasureTypeName", FieldType.PERCENT, true, null, null)
        );
    }
    
    /** Creates a new instance of GetItemKitMemberCommand */
    public GetItemKitMemberCommand() {
        super(null, FORM_FIELD_DEFINITIONS, false);
    }

    @Inject
    ItemControl itemControl;

    @Inject
    ItemKitMemberLogic itemKitMemberLogic;
    
    @Override
    protected BaseResult execute() {
        var result = ItemResultFactory.getGetItemKitMemberResult();
        var itemName = form.getItemName();
        var inventoryConditionName = form.getInventoryConditionName();
        var unitOfMeasureTypeName = form.getUnitOfMeasureTypeName();
        var memberItemName = form.getMemberItemName();
        var memberInventoryConditionName = form.getMemberInventoryConditionName();
        var memberUnitOfMeasureTypeName = form.getMemberUnitOfMeasureTypeName();
        var itemKitMember = itemKitMemberLogic.getItemKitMember(this, itemName, inventoryConditionName,
                unitOfMeasureTypeName, memberItemName, memberInventoryConditionName,
                memberUnitOfMeasureTypeName);

        if(!hasExecutionErrors()) {
            result.setItemKitMember(itemControl.getItemKitMemberTransfer(getUserVisit(), itemKitMember));
        }

        return result;
    }
    
}
