// --------------------------------------------------------------------------------
// Copyright 2002-2022 Echo Three, LLC
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

import com.echothree.model.control.accounting.common.transfer.CurrencyTransfer;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.inventory.common.transfer.InventoryConditionTransfer;
import com.echothree.model.control.inventory.server.control.InventoryControl;
import com.echothree.model.control.item.common.ItemPriceTypes;
import com.echothree.model.control.item.common.ItemProperties;
import com.echothree.model.control.item.common.transfer.ItemPriceTransfer;
import com.echothree.model.control.item.common.transfer.ItemTransfer;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.uom.common.transfer.UnitOfMeasureTypeTransfer;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.inventory.server.entity.InventoryCondition;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.item.server.entity.ItemFixedPrice;
import com.echothree.model.data.item.server.entity.ItemPrice;
import com.echothree.model.data.item.server.entity.ItemVariablePrice;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.form.TransferProperties;
import com.echothree.util.common.transfer.HistoryTransfer;
import com.echothree.util.common.transfer.ListWrapper;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.string.AmountUtils;
import com.echothree.util.server.transfer.HistoryCache;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ItemPriceTransferCache
        extends BaseItemTransferCache<ItemPrice, ItemPriceTransfer>
        implements HistoryCache<ItemPrice, ItemPriceTransfer> {
    
    AccountingControl accountingControl = Session.getModelController(AccountingControl.class);
    InventoryControl inventoryControl = Session.getModelController(InventoryControl.class);
    UomControl uomControl = Session.getModelController(UomControl.class);
    
    TransferProperties transferProperties;
    boolean filterItem;
    boolean filterInventoryCondition;
    boolean filterUnitOfMeasureType;
    boolean filterCurrency;
    boolean filterUnformattedUnitPrice;
    boolean filterUnitPrice;
    boolean filterUnformattedMinimumUnitPrice;
    boolean filterMinimumUnitPrice;
    boolean filterUnformattedMaximumUnitPrice;
    boolean filterMaximumUnitPrice;
    boolean filterUnformattedUnitPriceIncrement;
    boolean filterUnitPriceIncrement;
    boolean filterUnformattedFromTime;
    boolean filterFromTime;
    boolean filterUnformattedThruTime;
    boolean filterThruTime;
    
    /** Creates a new instance of ItemPriceTransferCache */
    public ItemPriceTransferCache(UserVisit userVisit, ItemControl itemControl) {
        super(userVisit, itemControl);
        
        transferProperties = session.getTransferProperties();
        if(transferProperties != null) {
            Set<String> properties = transferProperties.getProperties(ItemPriceTransfer.class);
            
            if(properties != null) {
                filterItem = !properties.contains(ItemProperties.ITEM);
                filterInventoryCondition = !properties.contains(ItemProperties.INVENTORY_CONDITION);
                filterUnitOfMeasureType = !properties.contains(ItemProperties.UNIT_OF_MEASURE_TYPE);
                filterCurrency = !properties.contains(ItemProperties.CURRENCY);
                filterUnformattedUnitPrice = !properties.contains(ItemProperties.UNFORMATTED_UNIT_PRICE);
                filterUnitPrice = !properties.contains(ItemProperties.UNIT_PRICE);
                filterUnformattedMinimumUnitPrice = !properties.contains(ItemProperties.UNFORMATTED_MINIMUM_UNIT_PRICE);
                filterMinimumUnitPrice = !properties.contains(ItemProperties.MINIMUM_UNIT_PRICE);
                filterUnformattedMaximumUnitPrice = !properties.contains(ItemProperties.UNFORMATTED_MAXIMUM_UNIT_PRICE);
                filterMaximumUnitPrice = !properties.contains(ItemProperties.MAXIMUM_UNIT_PRICE);
                filterUnformattedUnitPriceIncrement = !properties.contains(ItemProperties.UNFORMATTED_UNIT_PRICE_INCREMENT);
                filterUnitPriceIncrement = !properties.contains(ItemProperties.UNIT_PRICE_INCREMENT);
                filterUnformattedFromTime = !properties.contains(ItemProperties.UNFORMATTED_FROM_TIME);
                filterFromTime = !properties.contains(ItemProperties.FROM_TIME);
                filterUnformattedThruTime = !properties.contains(ItemProperties.UNFORMATTED_THRU_TIME);
                filterThruTime = !properties.contains(ItemProperties.THRU_TIME);
            }
        }
    }

    private ItemPriceTransfer getItemPriceTransfer(ItemPrice itemPrice, ItemFixedPrice itemFixedPrice, ItemVariablePrice itemVariablePrice) {
        Item item = itemPrice.getItem();
        ItemTransfer itemTransfer = filterItem ? null : itemControl.getItemTransfer(userVisit, item);
        InventoryCondition inventoryCondition = filterInventoryCondition ? null : itemPrice.getInventoryCondition();
        InventoryConditionTransfer inventoryConditionTransfer = inventoryCondition == null ? null : inventoryControl.getInventoryConditionTransfer(userVisit, inventoryCondition);
        UnitOfMeasureType unitOfMeasureType = filterUnitOfMeasureType ? null : itemPrice.getUnitOfMeasureType();
        UnitOfMeasureTypeTransfer unitOfMeasureTypeTransfer = unitOfMeasureType == null ? null : uomControl.getUnitOfMeasureTypeTransfer(userVisit, unitOfMeasureType);
        Currency currency = itemPrice.getCurrency();
        CurrencyTransfer currencyTransfer = filterCurrency ? null : accountingControl.getCurrencyTransfer(userVisit, currency);
        Long unformattedUnitPrice = null;
        String unitPrice = null;
        Long unformattedMinimumUnitPrice = null;
        String minimumUnitPrice = null;
        Long unformattedMaximumUnitPrice = null;
        String maximumUnitPrice = null;
        Long unformattedUnitPriceIncrement = null;
        String unitPriceIncrement = null;

        if(itemFixedPrice != null) {
            unformattedUnitPrice = filterUnformattedUnitPrice ? null : itemFixedPrice.getUnitPrice();
            unitPrice = filterUnitPrice ? null : AmountUtils.getInstance().formatPriceUnit(currency, itemFixedPrice.getUnitPrice());
        }
        
        if(itemVariablePrice != null) {
            unformattedMinimumUnitPrice = filterUnformattedMinimumUnitPrice ? null : itemVariablePrice.getMinimumUnitPrice();
            minimumUnitPrice = filterMinimumUnitPrice ? null : AmountUtils.getInstance().formatPriceUnit(currency, itemVariablePrice.getMinimumUnitPrice());
            unformattedMaximumUnitPrice = filterUnformattedMinimumUnitPrice ? null : itemVariablePrice.getMaximumUnitPrice();
            maximumUnitPrice = filterMaximumUnitPrice ? null : AmountUtils.getInstance().formatPriceUnit(currency, itemVariablePrice.getMaximumUnitPrice());
            unformattedUnitPriceIncrement = filterUnformattedUnitPriceIncrement ? null : itemVariablePrice.getUnitPriceIncrement();
            unitPriceIncrement = filterUnitPriceIncrement ? null : AmountUtils.getInstance().formatPriceUnit(currency, itemVariablePrice.getUnitPriceIncrement());
        }

        return new ItemPriceTransfer(itemTransfer, inventoryConditionTransfer, unitOfMeasureTypeTransfer, currencyTransfer,
                unformattedUnitPrice, unitPrice, unformattedMinimumUnitPrice, minimumUnitPrice, unformattedMaximumUnitPrice, maximumUnitPrice,
                unformattedUnitPriceIncrement, unitPriceIncrement);
    }
    
    @Override
    public ListWrapper<HistoryTransfer<ItemPriceTransfer>> getHistory(ItemPrice itemPrice) {
        List<HistoryTransfer<ItemPriceTransfer>> historyTransfers = null;
        String itemPriceTypeName = itemPrice.getItem().getLastDetail().getItemPriceType().getItemPriceTypeName();
        
        if(ItemPriceTypes.FIXED.name().equals(itemPriceTypeName)) {
            List<ItemFixedPrice> itemFixedPriceHistory = itemControl.getItemFixedPriceHistory(itemPrice);
            
            historyTransfers = new ArrayList<>(itemFixedPriceHistory.size());
            
            for(var itemFixedPrice : itemFixedPriceHistory) {
                Long unformattedFromTime = filterUnformattedFromTime ? null : itemFixedPrice.getFromTime();
                String fromTime = filterFromTime ? null : formatTypicalDateTime(itemFixedPrice.getFromTime());
                Long unformattedThruTime = filterUnformattedThruTime ? null : itemFixedPrice.getThruTime();
                String thruTime = filterThruTime ? null : formatTypicalDateTime(itemFixedPrice.getThruTime());
                
                historyTransfers.add(new HistoryTransfer<>(getItemPriceTransfer(itemPrice, itemFixedPrice, null),
                        unformattedFromTime, fromTime, unformattedThruTime, thruTime));
            }
        } else if(ItemPriceTypes.VARIABLE.name().equals(itemPriceTypeName)) {
            List<ItemVariablePrice> itemVariablePriceHistory = itemControl.getItemVariablePriceHistory(itemPrice);
            
            historyTransfers = new ArrayList<>(itemVariablePriceHistory.size());
            
            for(var itemVariablePrice : itemVariablePriceHistory) {
                Long unformattedFromTime = filterUnformattedFromTime ? null : itemVariablePrice.getFromTime();
                String fromTime = filterFromTime ? null : formatTypicalDateTime(itemVariablePrice.getFromTime());
                Long unformattedThruTime = filterUnformattedThruTime ? null : itemVariablePrice.getThruTime();
                String thruTime = filterThruTime ? null : formatTypicalDateTime(itemVariablePrice.getThruTime());
                
                historyTransfers.add(new HistoryTransfer<>(getItemPriceTransfer(itemPrice, null, itemVariablePrice),
                        unformattedFromTime, fromTime, unformattedThruTime, thruTime));
            }
        }
        
        return new ListWrapper<>(historyTransfers);
    }
    
    @Override
    public ItemPriceTransfer getTransfer(ItemPrice itemPrice) {
        ItemPriceTransfer itemPriceTransfer = get(itemPrice);
        
        if(itemPriceTransfer == null) {
            ItemFixedPrice itemFixedPrice = null;
            ItemVariablePrice itemVariablePrice = null;
            String itemPriceTypeName = itemPrice.getItem().getLastDetail().getItemPriceType().getItemPriceTypeName();

            if(ItemPriceTypes.FIXED.name().equals(itemPriceTypeName)) {
                itemFixedPrice = itemControl.getItemFixedPrice(itemPrice);
            } else if(ItemPriceTypes.VARIABLE.name().equals(itemPriceTypeName)) {
                itemVariablePrice = itemControl.getItemVariablePrice(itemPrice);
            }
            
            itemPriceTransfer = getItemPriceTransfer(itemPrice, itemFixedPrice, itemVariablePrice);
            
            put(itemPrice, itemPriceTransfer);
        }
        
        return itemPriceTransfer;
    }
    
}
