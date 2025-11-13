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

package com.echothree.model.control.item.server.transfer;


import com.echothree.model.control.inventory.server.control.InventoryControl;
import com.echothree.model.control.item.common.transfer.ItemUnitLimitTransfer;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.data.item.server.entity.ItemUnitLimit;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class ItemUnitLimitTransferCache
        extends BaseItemTransferCache<ItemUnitLimit, ItemUnitLimitTransfer> {
    
    InventoryControl inventoryControl = Session.getModelController(InventoryControl.class);
    UomControl uomControl = Session.getModelController(UomControl.class);
    
    /** Creates a new instance of ItemUnitLimitTransferCache */
    public ItemUnitLimitTransferCache(ItemControl itemControl) {
        super(itemControl);
    }
    
    @Override
    public ItemUnitLimitTransfer getTransfer(UserVisit userVisit, ItemUnitLimit itemUnitLimit) {
        var itemUnitLimitTransfer = get(itemUnitLimit);
        
        if(itemUnitLimitTransfer == null) {
            var item = itemControl.getItemTransfer(userVisit, itemUnitLimit.getItem());
            var inventoryCondition = inventoryControl.getInventoryConditionTransfer(userVisit, itemUnitLimit.getInventoryCondition());
            var unitOfMeasureType = uomControl.getUnitOfMeasureTypeTransfer(userVisit, itemUnitLimit.getUnitOfMeasureType());
            var longMinimumQuantity = itemUnitLimit.getMinimumQuantity();
            var minimumQuantity = longMinimumQuantity == null ? null : longMinimumQuantity.toString();
            var longMaximumQuantity = itemUnitLimit.getMaximumQuantity();
            var maximumQuantity = longMaximumQuantity == null ? null : longMaximumQuantity.toString();

            itemUnitLimitTransfer = new ItemUnitLimitTransfer(item, inventoryCondition, unitOfMeasureType, minimumQuantity, maximumQuantity);
            put(userVisit, itemUnitLimit, itemUnitLimitTransfer);
        }
        
        return itemUnitLimitTransfer;
    }
    
}
