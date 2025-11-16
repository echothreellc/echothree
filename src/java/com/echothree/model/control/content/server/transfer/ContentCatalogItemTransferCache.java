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

package com.echothree.model.control.content.server.transfer;

import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.content.common.ContentOptions;
import com.echothree.model.control.content.common.ContentProperties;
import com.echothree.model.control.content.common.transfer.ContentCatalogItemTransfer;
import com.echothree.model.control.content.server.control.ContentControl;
import com.echothree.model.control.inventory.server.control.InventoryControl;
import com.echothree.model.control.item.common.ItemPriceTypes;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.data.content.server.entity.ContentCatalogItem;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.form.TransferProperties;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.string.AmountUtils;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class ContentCatalogItemTransferCache
        extends BaseContentTransferCache<ContentCatalogItem, ContentCatalogItemTransfer> {

    AccountingControl accountingControl = Session.getModelController(AccountingControl.class);
    ContentControl contentControl = Session.getModelController(ContentControl.class);
    InventoryControl inventoryControl = Session.getModelController(InventoryControl.class);
    ItemControl itemControl = Session.getModelController(ItemControl.class);
    UomControl uomControl = Session.getModelController(UomControl.class);
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
    protected ContentCatalogItemTransferCache() {
        super();

        var options = session.getOptions();
        if(options != null) {
            setIncludeEntityAttributeGroups(options.contains(ContentOptions.ContentCatalogItemIncludeEntityAttributeGroups));
            setIncludeTagScopes(options.contains(ContentOptions.ContentCatalogItemIncludeTagScopes));
        }

        transferProperties = session.getTransferProperties();
        if(transferProperties != null) {
            var properties = transferProperties.getProperties(ContentCatalogItemTransfer.class);
            
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
    
    public ContentCatalogItemTransfer getContentCatalogItemTransfer(UserVisit userVisit, ContentCatalogItem contentCatalogItem) {
        var contentCatalogItemTransfer = get(contentCatalogItem);
        
        if(contentCatalogItemTransfer == null) {
            var contentCatalog = filterContentCatalog ? null : contentControl.getContentCatalogTransfer(userVisit, contentCatalogItem.getContentCatalog());
            var item = contentCatalogItem.getItem();
            var itemTransfer = filterItem ? null : itemControl.getItemTransfer(userVisit, item);
            var inventoryConditionTransfer = inventoryControl.getInventoryConditionTransfer(userVisit, contentCatalogItem.getInventoryCondition());
            var unitOfMeasureTypeTransfer = filterUnitOfMeasureType ? null : uomControl.getUnitOfMeasureTypeTransfer(userVisit, contentCatalogItem.getUnitOfMeasureType());
            var currency = contentCatalogItem.getCurrency();
            var currencyTransfer = filterCurrency ? null : accountingControl.getCurrencyTransfer(userVisit, currency);
            Long unformattedUnitPrice = null;
            String unitPrice = null;
            Long unformattedMinimumUnitPrice = null;
            String minimumUnitPrice = null;
            Long unformattedMaximumUnitPrice = null;
            String maximumUnitPrice = null;
            Long unformattedUnitPriceIncrement = null;
            String unitPriceIncrement = null;

            var itemPriceTypeName = item.getLastDetail().getItemPriceType().getItemPriceTypeName();
            if(ItemPriceTypes.FIXED.name().equals(itemPriceTypeName)) {
                if(!filterUnformattedUnitPrice || !filterUnitPrice) {
                    var contentCatalogItemFixedPrice = contentControl.getContentCatalogItemFixedPrice(contentCatalogItem);

                    if(contentCatalogItemFixedPrice != null) {
                        var rawUnitPrice = contentCatalogItemFixedPrice.getUnitPrice();
                        
                        unformattedUnitPrice = filterUnformattedUnitPrice ? null : rawUnitPrice;
                        unitPrice = filterUnitPrice ? null : amountUtils.formatPriceUnit(currency, rawUnitPrice);
                    }
                }
            } else if(ItemPriceTypes.VARIABLE.name().equals(itemPriceTypeName)) {
                if(!filterUnformattedMinimumUnitPrice || !filterMinimumUnitPrice || !filterUnformattedMaximumUnitPrice || !filterMaximumUnitPrice
                        || !filterUnformattedUnitPriceIncrement || !filterUnitPriceIncrement) {
                    var contentCatalogItemVariablePrice = contentControl.getContentCatalogItemVariablePrice(contentCatalogItem);

                    if(contentCatalogItemVariablePrice != null) {
                        var rawMinimumUnitPrice = contentCatalogItemVariablePrice.getMinimumUnitPrice();
                        var rawMaximumUnitPrice = contentCatalogItemVariablePrice.getMaximumUnitPrice();
                        var rawUnitPriceIncrement = contentCatalogItemVariablePrice.getUnitPriceIncrement();

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
            put(userVisit, contentCatalogItem, contentCatalogItemTransfer);
        }
        
        return contentCatalogItemTransfer;
    }
    
}
