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

package com.echothree.model.control.item.server.logic;

import com.echothree.model.control.inventory.server.logic.InventoryConditionLogic;
import com.echothree.model.control.item.common.ItemTypes;
import com.echothree.model.control.item.common.exception.InvalidItemTypeException;
import com.echothree.model.control.item.common.exception.UnknownItemKitMemberException;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.uom.server.logic.UnitOfMeasureTypeLogic;
import com.echothree.model.data.item.server.entity.ItemKitMember;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.spi.CDI;
import javax.inject.Inject;

@Dependent
public class ItemKitMemberLogic
        extends BaseLogic {

    @Inject
    ItemControl itemControl;

    @Inject
    InventoryConditionLogic inventoryConditionLogic;

    @Inject
    ItemLogic itemLogic;

    @Inject
    UnitOfMeasureTypeLogic unitOfMeasureTypeLogic;

    private ItemKitMemberLogic() {
        super();
    }

    public static ItemKitMemberLogic getInstance() {
        return CDI.current().select(ItemKitMemberLogic.class).get();
    }

    public ItemKitMember getItemKitMember(final ExecutionErrorAccumulator eea, final String itemName, final String inventoryConditionName,
            final String unitOfMeasureTypeName, final String memberItemName, final String memberInventoryConditionName,
            final String memberUnitOfMeasureTypeName) {
        var item = itemLogic.getItemByName(eea, itemName);
        ItemKitMember itemKitMember = null;

        if(eea == null || !eea.hasExecutionErrors()) {
            var itemDetail = item.getLastDetail();
            var itemTypeName = itemDetail.getItemType().getItemTypeName();

            if(itemTypeName.equals(ItemTypes.KIT.name())) {
                var inventoryCondition = inventoryConditionLogic.getInventoryConditionByName(eea, inventoryConditionName);

                if(eea == null || !eea.hasExecutionErrors()) {
                    var unitOfMeasureKind = itemDetail.getUnitOfMeasureKind();
                    var unitOfMeasureType = unitOfMeasureTypeLogic.getUnitOfMeasureTypeByName(eea, unitOfMeasureKind, unitOfMeasureTypeName);

                    if(eea == null || !eea.hasExecutionErrors()) {
                        var memberItem = itemLogic.getItemByName(eea, memberItemName);

                        if(eea == null || !eea.hasExecutionErrors()) {
                            var memberInventoryCondition = inventoryConditionLogic.getInventoryConditionByName(eea, memberInventoryConditionName);

                            if(eea == null || !eea.hasExecutionErrors()) {
                                var memberUnitOfMeasureKind = memberItem.getLastDetail().getUnitOfMeasureKind();
                                var memberUnitOfMeasureType = unitOfMeasureTypeLogic.getUnitOfMeasureTypeByName(eea, memberUnitOfMeasureKind, memberUnitOfMeasureTypeName);

                                if(eea == null || !eea.hasExecutionErrors()) {
                                    itemKitMember = itemControl.getItemKitMember(item, inventoryCondition, unitOfMeasureType, memberItem,
                                            memberInventoryCondition, memberUnitOfMeasureType);

                                    if(itemKitMember == null) {
                                        handleExecutionError(UnknownItemKitMemberException.class, eea, ExecutionErrors.UnknownItemKitMember.name(),
                                                itemName, inventoryConditionName, unitOfMeasureTypeName, memberItemName, memberInventoryConditionName,
                                                memberUnitOfMeasureTypeName);
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                handleExecutionError(InvalidItemTypeException.class, eea, ExecutionErrors.InvalidItemType.name(), itemTypeName);
            }
        }

        return itemKitMember;
    }

}
