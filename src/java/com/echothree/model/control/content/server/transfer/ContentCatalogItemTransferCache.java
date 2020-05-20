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

package com.echothree.model.control.content.server.transfer;

import com.echothree.model.control.accounting.common.transfer.CurrencyTransfer;
import com.echothree.model.control.accounting.server.AccountingControl;
import com.echothree.model.control.content.common.ContentProperties;
import com.echothree.model.control.content.common.transfer.ContentCatalogItemTransfer;
import com.echothree.model.control.content.common.transfer.ContentCatalogTransfer;
import com.echothree.model.control.content.server.ContentControl;
import com.echothree.model.control.inventory.common.transfer.InventoryConditionTransfer;
import com.echothree.model.control.inventory.server.control.InventoryControl;
import com.echothree.model.control.item.common.ItemPriceTypes;
import com.echothree.model.control.item.common.transfer.ItemTransfer;
import com.echothree.model.control.item.server.ItemControl;
import com.echothree.model.control.uom.common.transfer.UnitOfMeasureTypeTransfer;
import com.echothree.model.control.uom.server.UomControl;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.content.server.entity.ContentCatalogItem;
import com.echothree.model.data.content.server.entity.ContentCatalogItemFixedPrice;
import com.echothree.model.data.content.server.entity.ContentCatalogItemVariablePrice;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.form.TransferProperties;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.string.AmountUtils;
import java.util.Set;

public class ContentCatalogItemTransferCache
        extends BaseContentTransferCache<ContentCatalogItem, ContentCatalogItemTransfer> {

    AccountingControl accountingControl = (AccountingControl)Session.getModelController(AccountingControl.class);
    InventoryControl inventoryControl = (InventoryControl)Session.getModelController(InventoryControl.class);
    ItemControl itemControl = (ItemControl)Session.getModelController(ItemControl.class);
    UomControl uomControl = (UomControl)Session.getModelController(UomControl.class);
    AmountUtils amountUtils = AmountUtils.getInstance();
    
    TransferProperties transferProperties;
    boolean filterContentCatalog;
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
    boolean filterEntityInstance;
    
    /** Creates a new instance of ContentCatalogItemTransferCache */
    public ContentCatalogItemTransferCache(UserVisit userVisit, ContentControl contentControl) {
        super(userVisit, contentControl);
        
        transferProperties = session.getTransferProperties();
        if(transferProperties != null) {
            Set<String> properties = transferProperties.getProperties(ContentCatalogItemTransfer.class);
            
            if(properties != null) {
                filterContentCatalog = !properties.contains(ContentProperties.CONTENT_CATALOG);
                filterItem = !properties.contains(ContentProperties.ITEM);
                filterInventoryCondition = !properties.contains(ContentProperties.INVENTORY_CONDITION);
                filterUnitOfMeasureType = !properties.contains(ContentProperties.UNIT_OF_MEASURE_TYPE);
                filterCurrency = !properties.contains(ContentProperties.CURRENCY);
                filterUnformattedUnitPrice = !properties.contains(ContentProperties.UNFORMATTED_UNIT_PRICE);
                filterUnitPrice = !properties.contains(ContentProperties.UNIT_PRICE);
                filterUnformattedMinimumUnitPrice = !properties.contains(ContentProperties.UNFORMATTED_MINIMUM_UNIT_PRICE);
                filterMinimumUnitPrice = !properties.contains(ContentProperties.MINIMUM_UNIT_PRICE);
                filterUnformattedMaximumUnitPrice = !properties.contains(ContentProperties.UNFORMATTED_MAXIMUM_UNIT_PRICE);
                filterMaximumUnitPrice = !properties.contains(ContentProperties.MAXIMUM_UNIT_PRICE);
                filterUnformattedUnitPriceIncrement = !properties.contains(ContentProperties.UNFORMATTED_UNIT_PRICE_INCREMENT);
                filterUnitPriceIncrement = !properties.contains(ContentProperties.UNIT_PRICE_INCREMENT);
                filterEntityInstance = !properties.contains(ContentProperties.ENTITY_INSTANCE);
            }
        }
        
        setIncludeEntityInstance(!filterEntityInstance);
    }
    
    public ContentCatalogItemTransfer getContentCatalogItemTransfer(ContentCatalogItem contentCatalogItem) {
        ContentCatalogItemTransfer contentCatalogItemTransfer = get(contentCatalogItem);
        
        if(contentCatalogItemTransfer == null) {
            ContentCatalogTransfer contentCatalog = filterContentCatalog ? null : contentControl.getContentCatalogTransfer(userVisit, contentCatalogItem.getContentCatalog());
            Item item = contentCatalogItem.getItem();
            ItemTransfer itemTransfer = filterItem ? null : itemControl.getItemTransfer(userVisit, item);
            InventoryConditionTransfer inventoryConditionTransfer = inventoryControl.getInventoryConditionTransfer(userVisit, contentCatalogItem.getInventoryCondition());
            UnitOfMeasureTypeTransfer unitOfMeasureTypeTransfer = filterUnitOfMeasureType ? null : uomControl.getUnitOfMeasureTypeTransfer(userVisit, contentCatalogItem.getUnitOfMeasureType());
            Currency currency = contentCatalogItem.getCurrency();
            CurrencyTransfer currencyTransfer = filterCurrency ? null : accountingControl.getCurrencyTransfer(userVisit, currency);
            Long unformattedUnitPrice = null;
            String unitPrice = null;
            Long unformattedMinimumUnitPrice = null;
            String minimumUnitPrice = null;
            Long unformattedMaximumUnitPrice = null;
            String maximumUnitPrice = null;
            Long unformattedUnitPriceIncrement = null;
            String unitPriceIncrement = null;

            String itemPriceTypeName = item.getLastDetail().getItemPriceType().getItemPriceTypeName();
            if(ItemPriceTypes.FIXED.name().equals(itemPriceTypeName)) {
                if(!filterUnformattedUnitPrice || !filterUnitPrice) {
                    ContentCatalogItemFixedPrice contentCatalogItemFixedPrice = contentControl.getContentCatalogItemFixedPrice(contentCatalogItem);

                    if(contentCatalogItemFixedPrice != null) {
                        Long rawUnitPrice = contentCatalogItemFixedPrice.getUnitPrice();
                        
                        unformattedUnitPrice = filterUnformattedUnitPrice ? null : rawUnitPrice;
                        unitPrice = filterUnitPrice ? null : amountUtils.formatPriceUnit(currency, rawUnitPrice);
                    }
                }
            } else if(ItemPriceTypes.VARIABLE.name().equals(itemPriceTypeName)) {
                if(!filterUnformattedMinimumUnitPrice || !filterMinimumUnitPrice || !filterUnformattedMaximumUnitPrice || !filterMaximumUnitPrice
                        || !filterUnformattedUnitPriceIncrement || !filterUnitPriceIncrement) {
                    ContentCatalogItemVariablePrice contentCatalogItemVariablePrice = contentControl.getContentCatalogItemVariablePrice(contentCatalogItem);

                    if(contentCatalogItemVariablePrice != null) {
                        Long rawMinimumUnitPrice = contentCatalogItemVariablePrice.getMinimumUnitPrice();
                        Long rawMaximumUnitPrice = contentCatalogItemVariablePrice.getMaximumUnitPrice();
                        Long rawUnitPriceIncrement = contentCatalogItemVariablePrice.getUnitPriceIncrement();

                        unformattedMinimumUnitPrice = filterUnformattedMinimumUnitPrice ? null : rawMinimumUnitPrice;
                        minimumUnitPrice = filterMinimumUnitPrice ? null : amountUtils.formatPriceUnit(currency, rawMinimumUnitPrice);
                        unformattedMaximumUnitPrice = filterUnformattedMaximumUnitPrice ? null : rawMaximumUnitPrice;
                        maximumUnitPrice = filterMaximumUnitPrice ? null : amountUtils.formatPriceUnit(currency, rawMaximumUnitPrice);
                        unformattedUnitPriceIncrement = filterUnformattedUnitPriceIncrement ? null : rawUnitPriceIncrement;
                        unitPriceIncrement = filterUnitPriceIncrement ? null : amountUtils.formatPriceUnit(currency, rawUnitPriceIncrement);
                    }
                }
            }

            contentCatalogItemTransfer = new ContentCatalogItemTransfer(contentCatalog, itemTransfer, inventoryConditionTransfer, unitOfMeasureTypeTransfer,
                    currencyTransfer, unformattedUnitPrice, unitPrice, unformattedMinimumUnitPrice, minimumUnitPrice, unformattedMaximumUnitPrice,
                    maximumUnitPrice, unformattedUnitPriceIncrement, unitPriceIncrement);
            put(contentCatalogItem, contentCatalogItemTransfer);
        }
        
        return contentCatalogItemTransfer;
    }
    
}
