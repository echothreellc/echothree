// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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


import com.echothree.model.control.inventory.common.transfer.InventoryConditionTransfer;
import com.echothree.model.control.inventory.server.control.InventoryControl;
import com.echothree.model.control.item.common.transfer.ItemTransfer;
import com.echothree.model.control.item.common.transfer.ItemUnitLimitTransfer;
import com.echothree.model.control.item.server.ItemControl;
import com.echothree.model.control.uom.common.transfer.UnitOfMeasureTypeTransfer;
import com.echothree.model.control.uom.server.UomControl;
import com.echothree.model.data.item.server.entity.ItemUnitLimit;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class ItemUnitLimitTransferCache
        extends BaseItemTransferCache<ItemUnitLimit, ItemUnitLimitTransfer> {
    
    InventoryControl inventoryControl = (InventoryControl)Session.getModelController(InventoryControl.class);
    UomControl uomControl = (UomControl)Session.getModelController(UomControl.class);
    
    /** Creates a new instance of ItemUnitLimitTransferCache */
    public ItemUnitLimitTransferCache(UserVisit userVisit, ItemControl itemControl) {
        super(userVisit, itemControl);
    }
    
    @Override
    public ItemUnitLimitTransfer getTransfer(ItemUnitLimit itemUnitLimit) {
        ItemUnitLimitTransfer itemUnitLimitTransfer = get(itemUnitLimit);
        
        if(itemUnitLimitTransfer == null) {
            ItemTransfer item = itemControl.getItemTransfer(userVisit, itemUnitLimit.getItem());
            InventoryConditionTransfer inventoryCondition = inventoryControl.getInventoryConditionTransfer(userVisit, itemUnitLimit.getInventoryCondition());
            UnitOfMeasureTypeTransfer unitOfMeasureType = uomControl.getUnitOfMeasureTypeTransfer(userVisit, itemUnitLimit.getUnitOfMeasureType());
            Long longMinimumQuantity = itemUnitLimit.getMinimumQuantity();
            String minimumQuantity = longMinimumQuantity == null ? null : longMinimumQuantity.toString();
            Long longMaximumQuantity = itemUnitLimit.getMaximumQuantity();
            String maximumQuantity = longMaximumQuantity == null ? null : longMaximumQuantity.toString();

            itemUnitLimitTransfer = new ItemUnitLimitTransfer(item, inventoryCondition, unitOfMeasureType, minimumQuantity, maximumQuantity);
            put(itemUnitLimit, itemUnitLimitTransfer);
        }
        
        return itemUnitLimitTransfer;
    }
    
}
