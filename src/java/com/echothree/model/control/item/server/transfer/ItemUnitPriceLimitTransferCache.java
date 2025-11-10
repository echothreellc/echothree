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

import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.inventory.server.control.InventoryControl;
import com.echothree.model.control.item.common.transfer.ItemUnitPriceLimitTransfer;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.data.item.server.entity.ItemUnitPriceLimit;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.string.AmountUtils;

public class ItemUnitPriceLimitTransferCache
        extends BaseItemTransferCache<ItemUnitPriceLimit, ItemUnitPriceLimitTransfer> {
    
    AccountingControl accountingControl = Session.getModelController(AccountingControl.class);
    InventoryControl inventoryControl = Session.getModelController(InventoryControl.class);
    UomControl uomControl = Session.getModelController(UomControl.class);
    
    /** Creates a new instance of ItemUnitPriceLimitTransferCache */
    public ItemUnitPriceLimitTransferCache(UserVisit userVisit, ItemControl itemControl) {
        super(userVisit, itemControl);
    }
    
    @Override
    public ItemUnitPriceLimitTransfer getTransfer(ItemUnitPriceLimit itemUnitPriceLimit) {
        var itemUnitPriceLimitTransfer = get(itemUnitPriceLimit);
        
        if(itemUnitPriceLimitTransfer == null) {
            var item = itemControl.getItemTransfer(userVisit, itemUnitPriceLimit.getItem());
            var inventoryCondition = inventoryControl.getInventoryConditionTransfer(userVisit, itemUnitPriceLimit.getInventoryCondition());
            var unitOfMeasureType = uomControl.getUnitOfMeasureTypeTransfer(userVisit, itemUnitPriceLimit.getUnitOfMeasureType());
            var currency = itemUnitPriceLimit.getCurrency();
            var currencyTransfer = accountingControl.getCurrencyTransfer(userVisit, currency);
            var unformattedMinimumUnitPrice = itemUnitPriceLimit.getMinimumUnitPrice();
            var minimumUnitPrice = AmountUtils.getInstance().formatPriceLine(currency, unformattedMinimumUnitPrice);
            var unformattedMaximumUnitPrice = itemUnitPriceLimit.getMaximumUnitPrice();
            var maximumUnitPrice = AmountUtils.getInstance().formatPriceLine(currency, unformattedMaximumUnitPrice);
            
            itemUnitPriceLimitTransfer = new ItemUnitPriceLimitTransfer(item, inventoryCondition, unitOfMeasureType, currencyTransfer,
                    unformattedMinimumUnitPrice, minimumUnitPrice, unformattedMaximumUnitPrice, maximumUnitPrice);
            put(userVisit, itemUnitPriceLimit, itemUnitPriceLimitTransfer);
        }
        
        return itemUnitPriceLimitTransfer;
    }
    
}
