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

import com.echothree.model.control.customer.server.control.CustomerControl;
import com.echothree.model.control.inventory.server.control.InventoryControl;
import com.echothree.model.control.item.common.transfer.ItemUnitCustomerTypeLimitTransfer;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.data.item.server.entity.ItemUnitCustomerTypeLimit;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class ItemUnitCustomerTypeLimitTransferCache
        extends BaseItemTransferCache<ItemUnitCustomerTypeLimit, ItemUnitCustomerTypeLimitTransfer> {
    
    InventoryControl inventoryControl = Session.getModelController(InventoryControl.class);
    ItemControl itemControl = Session.getModelController(ItemControl.class);
    UomControl uomControl = Session.getModelController(UomControl.class);
    CustomerControl customerControl = Session.getModelController(CustomerControl.class);
    
    /** Creates a new instance of ItemUnitCustomerTypeLimitTransferCache */
    public ItemUnitCustomerTypeLimitTransferCache() {
        super();
    }
    
    @Override
    public ItemUnitCustomerTypeLimitTransfer getTransfer(UserVisit userVisit, ItemUnitCustomerTypeLimit itemUnitCustomerTypeLimit) {
        var itemUnitCustomerTypeLimitTransfer = get(itemUnitCustomerTypeLimit);
        
        if(itemUnitCustomerTypeLimitTransfer == null) {
            var item = itemControl.getItemTransfer(userVisit, itemUnitCustomerTypeLimit.getItem());
            var inventoryCondition = inventoryControl.getInventoryConditionTransfer(userVisit, itemUnitCustomerTypeLimit.getInventoryCondition());
            var unitOfMeasureType = uomControl.getUnitOfMeasureTypeTransfer(userVisit, itemUnitCustomerTypeLimit.getUnitOfMeasureType());
            var customerType = customerControl.getCustomerTypeTransfer(userVisit, itemUnitCustomerTypeLimit.getCustomerType());
            var longMinimumQuantity = itemUnitCustomerTypeLimit.getMinimumQuantity();
            var minimumQuantity = longMinimumQuantity == null ? null : longMinimumQuantity.toString();
            var longMaximumQuantity = itemUnitCustomerTypeLimit.getMaximumQuantity();
            var maximumQuantity = longMaximumQuantity == null ? null : longMaximumQuantity.toString();
            
            itemUnitCustomerTypeLimitTransfer = new ItemUnitCustomerTypeLimitTransfer(item, inventoryCondition, unitOfMeasureType, customerType,
                    minimumQuantity, maximumQuantity);
            put(userVisit, itemUnitCustomerTypeLimit, itemUnitCustomerTypeLimitTransfer);
        }
        
        return itemUnitCustomerTypeLimitTransfer;
    }
    
}
