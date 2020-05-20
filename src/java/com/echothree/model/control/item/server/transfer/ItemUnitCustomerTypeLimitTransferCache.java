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

import com.echothree.model.control.customer.common.transfer.CustomerTypeTransfer;
import com.echothree.model.control.customer.server.CustomerControl;
import com.echothree.model.control.inventory.common.transfer.InventoryConditionTransfer;
import com.echothree.model.control.inventory.server.control.InventoryControl;
import com.echothree.model.control.item.common.transfer.ItemTransfer;
import com.echothree.model.control.item.common.transfer.ItemUnitCustomerTypeLimitTransfer;
import com.echothree.model.control.item.server.ItemControl;
import com.echothree.model.control.uom.common.transfer.UnitOfMeasureTypeTransfer;
import com.echothree.model.control.uom.server.UomControl;
import com.echothree.model.data.item.server.entity.ItemUnitCustomerTypeLimit;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class ItemUnitCustomerTypeLimitTransferCache
        extends BaseItemTransferCache<ItemUnitCustomerTypeLimit, ItemUnitCustomerTypeLimitTransfer> {
    
    InventoryControl inventoryControl = (InventoryControl)Session.getModelController(InventoryControl.class);
    UomControl uomControl = (UomControl)Session.getModelController(UomControl.class);
    CustomerControl customerControl = (CustomerControl)Session.getModelController(CustomerControl.class);
    
    /** Creates a new instance of ItemUnitCustomerTypeLimitTransferCache */
    public ItemUnitCustomerTypeLimitTransferCache(UserVisit userVisit, ItemControl itemControl) {
        super(userVisit, itemControl);
    }
    
    @Override
    public ItemUnitCustomerTypeLimitTransfer getTransfer(ItemUnitCustomerTypeLimit itemUnitCustomerTypeLimit) {
        ItemUnitCustomerTypeLimitTransfer itemUnitCustomerTypeLimitTransfer = get(itemUnitCustomerTypeLimit);
        
        if(itemUnitCustomerTypeLimitTransfer == null) {
            ItemTransfer item = itemControl.getItemTransfer(userVisit, itemUnitCustomerTypeLimit.getItem());
            InventoryConditionTransfer inventoryCondition = inventoryControl.getInventoryConditionTransfer(userVisit, itemUnitCustomerTypeLimit.getInventoryCondition());
            UnitOfMeasureTypeTransfer unitOfMeasureType = uomControl.getUnitOfMeasureTypeTransfer(userVisit, itemUnitCustomerTypeLimit.getUnitOfMeasureType());
            CustomerTypeTransfer customerType = customerControl.getCustomerTypeTransfer(userVisit, itemUnitCustomerTypeLimit.getCustomerType());
            Long longMinimumQuantity = itemUnitCustomerTypeLimit.getMinimumQuantity();
            String minimumQuantity = longMinimumQuantity == null ? null : longMinimumQuantity.toString();
            Long longMaximumQuantity = itemUnitCustomerTypeLimit.getMaximumQuantity();
            String maximumQuantity = longMaximumQuantity == null ? null : longMaximumQuantity.toString();
            
            itemUnitCustomerTypeLimitTransfer = new ItemUnitCustomerTypeLimitTransfer(item, inventoryCondition, unitOfMeasureType, customerType,
                    minimumQuantity, maximumQuantity);
            put(itemUnitCustomerTypeLimit, itemUnitCustomerTypeLimitTransfer);
        }
        
        return itemUnitCustomerTypeLimitTransfer;
    }
    
}
