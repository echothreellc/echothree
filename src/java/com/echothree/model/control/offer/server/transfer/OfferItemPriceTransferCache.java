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

package com.echothree.model.control.offer.server.transfer;

import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.inventory.server.control.InventoryControl;
import com.echothree.model.control.item.common.ItemPriceTypes;
import com.echothree.model.control.offer.common.OfferProperties;
import com.echothree.model.control.offer.common.transfer.OfferItemPriceTransfer;
import com.echothree.model.control.offer.server.control.OfferControl;
import com.echothree.model.control.offer.server.control.OfferItemControl;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.data.offer.server.entity.OfferItemFixedPrice;
import com.echothree.model.data.offer.server.entity.OfferItemPrice;
import com.echothree.model.data.offer.server.entity.OfferItemVariablePrice;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.form.TransferProperties;
import com.echothree.util.common.transfer.HistoryTransfer;
import com.echothree.util.common.transfer.ListWrapper;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.string.AmountUtils;
import com.echothree.util.server.transfer.HistoryCache;
import java.util.ArrayList;
import java.util.List;

public class OfferItemPriceTransferCache
        extends BaseOfferTransferCache<OfferItemPrice, OfferItemPriceTransfer>
        implements HistoryCache<OfferItemPrice, OfferItemPriceTransfer> {
    
    AccountingControl accountingControl = Session.getModelController(AccountingControl.class);
    InventoryControl inventoryControl = Session.getModelController(InventoryControl.class);
    OfferControl offerControl = Session.getModelController(OfferControl.class);
    OfferItemControl offerItemControl = Session.getModelController(OfferItemControl.class);
    UomControl uomControl = Session.getModelController(UomControl.class);
    
    TransferProperties transferProperties;
    boolean filterOfferItem;
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
    
    /** Creates a new instance of OfferItemPriceTransferCache */
    public OfferItemPriceTransferCache() {
        super();
        
        transferProperties = session.getTransferProperties();
        if(transferProperties != null) {
            var properties = transferProperties.getProperties(OfferItemPriceTransfer.class);
            
            if(properties != null) {
                filterOfferItem = !properties.contains(OfferProperties.OFFER_ITEM);
                filterInventoryCondition = !properties.contains(OfferProperties.INVENTORY_CONDITION);
                filterUnitOfMeasureType = !properties.contains(OfferProperties.UNIT_OF_MEASURE_TYPE);
                filterCurrency = !properties.contains(OfferProperties.CURRENCY);
                filterUnformattedUnitPrice = !properties.contains(OfferProperties.UNFORMATTED_UNIT_PRICE);
                filterUnitPrice = !properties.contains(OfferProperties.UNIT_PRICE);
                filterUnformattedMinimumUnitPrice = !properties.contains(OfferProperties.UNFORMATTED_MINIMUM_UNIT_PRICE);
                filterMinimumUnitPrice = !properties.contains(OfferProperties.MINIMUM_UNIT_PRICE);
                filterUnformattedMaximumUnitPrice = !properties.contains(OfferProperties.UNFORMATTED_MAXIMUM_UNIT_PRICE);
                filterMaximumUnitPrice = !properties.contains(OfferProperties.MAXIMUM_UNIT_PRICE);
                filterUnformattedUnitPriceIncrement = !properties.contains(OfferProperties.UNFORMATTED_UNIT_PRICE_INCREMENT);
                filterUnitPriceIncrement = !properties.contains(OfferProperties.UNIT_PRICE_INCREMENT);
                filterUnformattedFromTime = !properties.contains(OfferProperties.UNFORMATTED_FROM_TIME);
                filterFromTime = !properties.contains(OfferProperties.FROM_TIME);
                filterUnformattedThruTime = !properties.contains(OfferProperties.UNFORMATTED_THRU_TIME);
                filterThruTime = !properties.contains(OfferProperties.THRU_TIME);
            }
        }
    }
    
    private OfferItemPriceTransfer getOfferItemPriceTransfer(OfferItemPrice offerItemPrice, OfferItemFixedPrice offerItemFixedPrice,
            OfferItemVariablePrice offerItemVariablePrice) {
        var offerItem = filterOfferItem ? null : offerItemPrice.getOfferItem();
        var offerItemTransfer = offerItem == null ? null : offerItemControl.getOfferItemTransfer(userVisit, offerItem);
        var inventoryCondition = filterInventoryCondition ? null : offerItemPrice.getInventoryCondition();
        var inventoryConditionTransfer = inventoryCondition == null ? null : inventoryControl.getInventoryConditionTransfer(userVisit, inventoryCondition);
        var unitOfMeasureType = filterUnitOfMeasureType ? null : offerItemPrice.getUnitOfMeasureType();
        var unitOfMeasureTypeTransfer = unitOfMeasureType == null ? null : uomControl.getUnitOfMeasureTypeTransfer(userVisit, unitOfMeasureType);
        var currency = offerItemPrice.getCurrency();
        var currencyTransfer = filterCurrency ? null : accountingControl.getCurrencyTransfer(userVisit, currency);
        Long unformattedUnitPrice = null;
        String unitPrice = null;
        Long unformattedMinimumUnitPrice = null;
        String minimumUnitPrice = null;
        Long unformattedMaximumUnitPrice = null;
        String maximumUnitPrice = null;
        Long unformattedUnitPriceIncrement = null;
        String unitPriceIncrement = null;

        if(offerItemFixedPrice != null) {
            unformattedUnitPrice = filterUnformattedUnitPrice ? null : offerItemFixedPrice.getUnitPrice();
            unitPrice = filterUnitPrice ? null : AmountUtils.getInstance().formatPriceUnit(currency, offerItemFixedPrice.getUnitPrice());
        }
        
        if(offerItemVariablePrice != null) {
            unformattedMinimumUnitPrice = filterUnformattedMinimumUnitPrice ? null : offerItemVariablePrice.getMinimumUnitPrice();
            minimumUnitPrice = filterMinimumUnitPrice ? null : AmountUtils.getInstance().formatPriceUnit(currency, offerItemVariablePrice.getMinimumUnitPrice());
            unformattedMaximumUnitPrice = filterUnformattedMaximumUnitPrice ? null : offerItemVariablePrice.getMaximumUnitPrice();
            maximumUnitPrice = filterMaximumUnitPrice ? null : AmountUtils.getInstance().formatPriceUnit(currency, offerItemVariablePrice.getMaximumUnitPrice());
            unformattedUnitPriceIncrement = filterUnformattedUnitPriceIncrement ? null : offerItemVariablePrice.getUnitPriceIncrement();
            unitPriceIncrement = filterUnitPriceIncrement ? null : AmountUtils.getInstance().formatPriceUnit(currency, offerItemVariablePrice.getUnitPriceIncrement());
        }

        return new OfferItemPriceTransfer(offerItemTransfer, inventoryConditionTransfer, unitOfMeasureTypeTransfer, currencyTransfer, unformattedUnitPrice,
                unitPrice, unformattedMinimumUnitPrice, minimumUnitPrice, unformattedMaximumUnitPrice, maximumUnitPrice, unformattedUnitPriceIncrement,
                unitPriceIncrement);
    }
    
    @Override
    public ListWrapper<HistoryTransfer<OfferItemPriceTransfer>> getHistory(OfferItemPrice offerItemPrice) {
        List<HistoryTransfer<OfferItemPriceTransfer>> historyTransfers = null;
        var itemPriceTypeName = offerItemPrice.getOfferItem().getItem().getLastDetail().getItemPriceType().getItemPriceTypeName();
        
        if(ItemPriceTypes.FIXED.name().equals(itemPriceTypeName)) {
            var offerItemFixedPriceHistory = offerItemControl.getOfferItemFixedPriceHistory(offerItemPrice);
            
            historyTransfers = new ArrayList<>(offerItemFixedPriceHistory.size());
            
            for(var offerItemFixedPrice : offerItemFixedPriceHistory) {
                var unformattedFromTime = filterUnformattedFromTime ? null : offerItemFixedPrice.getFromTime();
                var fromTime = filterFromTime ? null : formatTypicalDateTime(userVisit, offerItemFixedPrice.getFromTime());
                var unformattedThruTime = filterUnformattedThruTime ? null : offerItemFixedPrice.getThruTime();
                var thruTime = filterThruTime ? null : formatTypicalDateTime(userVisit, offerItemFixedPrice.getThruTime());
                
                historyTransfers.add(new HistoryTransfer<>(getOfferItemPriceTransfer(offerItemPrice, offerItemFixedPrice, null),
                        unformattedFromTime, fromTime, unformattedThruTime, thruTime));
            }
        } else if(ItemPriceTypes.VARIABLE.name().equals(itemPriceTypeName)) {
            var offerItemVariablePriceHistory = offerItemControl.getOfferItemVariablePriceHistory(offerItemPrice);
            
            historyTransfers = new ArrayList<>(offerItemVariablePriceHistory.size());
            
            for(var offerItemVariablePrice : offerItemVariablePriceHistory) {
                var unformattedFromTime = filterUnformattedFromTime ? null : offerItemVariablePrice.getFromTime();
                var fromTime = filterFromTime ? null : formatTypicalDateTime(userVisit, offerItemVariablePrice.getFromTime());
                var unformattedThruTime = filterUnformattedThruTime ? null : offerItemVariablePrice.getThruTime();
                var thruTime = filterThruTime ? null : formatTypicalDateTime(userVisit, offerItemVariablePrice.getThruTime());
                
                historyTransfers.add(new HistoryTransfer<>(getOfferItemPriceTransfer(offerItemPrice, null, offerItemVariablePrice),
                        unformattedFromTime, fromTime, unformattedThruTime, thruTime));
            }
        }
        
        return new ListWrapper<>(historyTransfers);
    }
    
    public OfferItemPriceTransfer getTransfer(OfferItemPrice offerItemPrice) {
        var offerItemPriceTransfer = get(offerItemPrice);
        
        if(offerItemPriceTransfer == null) {
            OfferItemFixedPrice offerItemFixedPrice = null;
            OfferItemVariablePrice offerItemVariablePrice = null;
            var itemPriceTypeName = offerItemPrice.getOfferItem().getItem().getLastDetail().getItemPriceType().getItemPriceTypeName();

            if(ItemPriceTypes.FIXED.name().equals(itemPriceTypeName)) {
                offerItemFixedPrice = offerItemControl.getOfferItemFixedPrice(offerItemPrice);
            } else if(ItemPriceTypes.VARIABLE.name().equals(itemPriceTypeName)) {
                offerItemVariablePrice = offerItemControl.getOfferItemVariablePrice(offerItemPrice);
            }
            
            offerItemPriceTransfer = getOfferItemPriceTransfer(offerItemPrice, offerItemFixedPrice, offerItemVariablePrice);
            
            put(userVisit, offerItemPrice, offerItemPriceTransfer);
        }
        
        return offerItemPriceTransfer;
    }
    
}
