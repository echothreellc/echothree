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

package com.echothree.model.control.item.common.transfer;

import com.echothree.model.control.accounting.common.transfer.CurrencyTransfer;
import com.echothree.model.control.inventory.common.transfer.InventoryConditionTransfer;
import com.echothree.model.control.uom.common.transfer.UnitOfMeasureTypeTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class ItemPriceTransfer
        extends BaseTransfer {
    
    private ItemTransfer item;
    private InventoryConditionTransfer inventoryCondition;
    private UnitOfMeasureTypeTransfer unitOfMeasureType;
    private CurrencyTransfer currency;
    private Long unformattedUnitPrice;
    private String unitPrice;
    private Long unformattedMinimumUnitPrice;
    private String minimumUnitPrice;
    private Long unformattedMaximumUnitPrice;
    private String maximumUnitPrice;
    private Long unformattedUnitPriceIncrement;
    private String unitPriceIncrement;
    
    /** Creates a new instance of ItemPriceTransfer */
    public ItemPriceTransfer(ItemTransfer item, InventoryConditionTransfer inventoryCondition, UnitOfMeasureTypeTransfer unitOfMeasureType,
            CurrencyTransfer currency, Long unformattedUnitPrice, String unitPrice, Long unformattedMinimumUnitPrice, String minimumUnitPrice,
            Long unformattedMaximumUnitPrice, String maximumUnitPrice, Long unformattedUnitPriceIncrement, String unitPriceIncrement) {
        this.item = item;
        this.inventoryCondition = inventoryCondition;
        this.unitOfMeasureType = unitOfMeasureType;
        this.currency = currency;
        this.unformattedUnitPrice = unformattedUnitPrice;
        this.unitPrice = unitPrice;
        this.unformattedMinimumUnitPrice = unformattedMinimumUnitPrice;
        this.minimumUnitPrice = minimumUnitPrice;
        this.unformattedMaximumUnitPrice = unformattedMaximumUnitPrice;
        this.maximumUnitPrice = maximumUnitPrice;
        this.unformattedUnitPriceIncrement = unformattedUnitPriceIncrement;
        this.unitPriceIncrement = unitPriceIncrement;
    }

    /**
     * Returns the item.
     * @return the item
     */
    public ItemTransfer getItem() {
        return item;
    }

    /**
     * Sets the item.
     * @param item the item to set
     */
    public void setItem(ItemTransfer item) {
        this.item = item;
    }

    /**
     * Returns the inventoryCondition.
     * @return the inventoryCondition
     */
    public InventoryConditionTransfer getInventoryCondition() {
        return inventoryCondition;
    }

    /**
     * Sets the inventoryCondition.
     * @param inventoryCondition the inventoryCondition to set
     */
    public void setInventoryCondition(InventoryConditionTransfer inventoryCondition) {
        this.inventoryCondition = inventoryCondition;
    }

    /**
     * Returns the unitOfMeasureType.
     * @return the unitOfMeasureType
     */
    public UnitOfMeasureTypeTransfer getUnitOfMeasureType() {
        return unitOfMeasureType;
    }

    /**
     * Sets the unitOfMeasureType.
     * @param unitOfMeasureType the unitOfMeasureType to set
     */
    public void setUnitOfMeasureType(UnitOfMeasureTypeTransfer unitOfMeasureType) {
        this.unitOfMeasureType = unitOfMeasureType;
    }

    /**
     * Returns the currency.
     * @return the currency
     */
    public CurrencyTransfer getCurrency() {
        return currency;
    }

    /**
     * Sets the currency.
     * @param currency the currency to set
     */
    public void setCurrency(CurrencyTransfer currency) {
        this.currency = currency;
    }

    /**
     * Returns the unformattedUnitPrice.
     * @return the unformattedUnitPrice
     */
    public Long getUnformattedUnitPrice() {
        return unformattedUnitPrice;
    }

    /**
     * Sets the unformattedUnitPrice.
     * @param unformattedUnitPrice the unformattedUnitPrice to set
     */
    public void setUnformattedUnitPrice(Long unformattedUnitPrice) {
        this.unformattedUnitPrice = unformattedUnitPrice;
    }

    /**
     * Returns the unitPrice.
     * @return the unitPrice
     */
    public String getUnitPrice() {
        return unitPrice;
    }

    /**
     * Sets the unitPrice.
     * @param unitPrice the unitPrice to set
     */
    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    /**
     * Returns the unformattedMinimumUnitPrice.
     * @return the unformattedMinimumUnitPrice
     */
    public Long getUnformattedMinimumUnitPrice() {
        return unformattedMinimumUnitPrice;
    }

    /**
     * Sets the unformattedMinimumUnitPrice.
     * @param unformattedMinimumUnitPrice the unformattedMinimumUnitPrice to set
     */
    public void setUnformattedMinimumUnitPrice(Long unformattedMinimumUnitPrice) {
        this.unformattedMinimumUnitPrice = unformattedMinimumUnitPrice;
    }

    /**
     * Returns the minimumUnitPrice.
     * @return the minimumUnitPrice
     */
    public String getMinimumUnitPrice() {
        return minimumUnitPrice;
    }

    /**
     * Sets the minimumUnitPrice.
     * @param minimumUnitPrice the minimumUnitPrice to set
     */
    public void setMinimumUnitPrice(String minimumUnitPrice) {
        this.minimumUnitPrice = minimumUnitPrice;
    }

    /**
     * Returns the unformattedMaximumUnitPrice.
     * @return the unformattedMaximumUnitPrice
     */
    public Long getUnformattedMaximumUnitPrice() {
        return unformattedMaximumUnitPrice;
    }

    /**
     * Sets the unformattedMaximumUnitPrice.
     * @param unformattedMaximumUnitPrice the unformattedMaximumUnitPrice to set
     */
    public void setUnformattedMaximumUnitPrice(Long unformattedMaximumUnitPrice) {
        this.unformattedMaximumUnitPrice = unformattedMaximumUnitPrice;
    }

    /**
     * Returns the maximumUnitPrice.
     * @return the maximumUnitPrice
     */
    public String getMaximumUnitPrice() {
        return maximumUnitPrice;
    }

    /**
     * Sets the maximumUnitPrice.
     * @param maximumUnitPrice the maximumUnitPrice to set
     */
    public void setMaximumUnitPrice(String maximumUnitPrice) {
        this.maximumUnitPrice = maximumUnitPrice;
    }

    /**
     * Returns the unformattedUnitPriceIncrement.
     * @return the unformattedUnitPriceIncrement
     */
    public Long getUnformattedUnitPriceIncrement() {
        return unformattedUnitPriceIncrement;
    }

    /**
     * Sets the unformattedUnitPriceIncrement.
     * @param unformattedUnitPriceIncrement the unformattedUnitPriceIncrement to set
     */
    public void setUnformattedUnitPriceIncrement(Long unformattedUnitPriceIncrement) {
        this.unformattedUnitPriceIncrement = unformattedUnitPriceIncrement;
    }

    /**
     * Returns the unitPriceIncrement.
     * @return the unitPriceIncrement
     */
    public String getUnitPriceIncrement() {
        return unitPriceIncrement;
    }

    /**
     * Sets the unitPriceIncrement.
     * @param unitPriceIncrement the unitPriceIncrement to set
     */
    public void setUnitPriceIncrement(String unitPriceIncrement) {
        this.unitPriceIncrement = unitPriceIncrement;
    }
    
}
