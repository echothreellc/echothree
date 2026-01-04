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

package com.echothree.ui.web.main.action.advertising.offeritemprice;

import com.echothree.ui.web.main.framework.MainBaseDeleteActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;

@SproutForm(name="OfferItemPriceDelete")
public class DeleteActionForm
        extends MainBaseDeleteActionForm {

    private String offerName;
    private String itemName;
    private String inventoryConditionName;
    private String unitOfMeasureTypeName;
    private String currencyIsoName;

    /**
     * Returns the offerName.
     * @return the offerName
     */
    public String getOfferName() {
        return offerName;
    }

    /**
     * Sets the offerName.
     * @param offerName the offerName to set
     */
    public void setOfferName(String offerName) {
        this.offerName = offerName;
    }

    /**
     * Returns the itemName.
     * @return the itemName
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * Sets the itemName.
     * @param itemName the itemName to set
     */
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    /**
     * Returns the inventoryConditionName.
     * @return the inventoryConditionName
     */
    public String getInventoryConditionName() {
        return inventoryConditionName;
    }

    /**
     * Sets the inventoryConditionName.
     * @param inventoryConditionName the inventoryConditionName to set
     */
    public void setInventoryConditionName(String inventoryConditionName) {
        this.inventoryConditionName = inventoryConditionName;
    }

    /**
     * Returns the unitOfMeasureTypeName.
     * @return the unitOfMeasureTypeName
     */
    public String getUnitOfMeasureTypeName() {
        return unitOfMeasureTypeName;
    }

    /**
     * Sets the unitOfMeasureTypeName.
     * @param unitOfMeasureTypeName the unitOfMeasureTypeName to set
     */
    public void setUnitOfMeasureTypeName(String unitOfMeasureTypeName) {
        this.unitOfMeasureTypeName = unitOfMeasureTypeName;
    }

    /**
     * Returns the currencyIsoName.
     * @return the currencyIsoName
     */
    public String getCurrencyIsoName() {
        return currencyIsoName;
    }

    /**
     * Sets the currencyIsoName.
     * @param currencyIsoName the currencyIsoName to set
     */
    public void setCurrencyIsoName(String currencyIsoName) {
        this.currencyIsoName = currencyIsoName;
    }
    
}
