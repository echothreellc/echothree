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

import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;

@SproutForm(name="OfferItemPriceEdit")
public class EditActionForm
        extends BaseActionForm {
    
    private String offerName;
    private String itemName;
    private String inventoryConditionName;
    private String unitOfMeasureTypeName;
    private String currencyIsoName;
    private String unitPrice;
    private String maximumUnitPrice;
    private String minimumUnitPrice;
    private String unitPriceIncrement;
    
    public String getOfferName() {
        return offerName;
    }
    
    public void setOfferName(String offerName) {
        this.offerName = offerName;
    }
    
    public String getItemName() {
        return itemName;
    }
    
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
    
    public String getInventoryConditionName() {
        return inventoryConditionName;
    }
    
    public void setInventoryConditionName(String inventoryConditionName) {
        this.inventoryConditionName = inventoryConditionName;
    }
    
    public String getUnitOfMeasureTypeName() {
        return unitOfMeasureTypeName;
    }
    
    public void setUnitOfMeasureTypeName(String unitOfMeasureTypeName) {
        this.unitOfMeasureTypeName = unitOfMeasureTypeName;
    }
    
    public String getCurrencyIsoName() {
        return currencyIsoName;
    }
    
    public void setCurrencyIsoName(String currencyIsoName) {
        this.currencyIsoName = currencyIsoName;
    }
    
    public String getUnitPrice() {
        return unitPrice;
    }
    
    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }
    
    public String getMaximumUnitPrice() {
        return maximumUnitPrice;
    }
    
    public void setMaximumUnitPrice(String maximumUnitPrice) {
        this.maximumUnitPrice = maximumUnitPrice;
    }
    
    public String getMinimumUnitPrice() {
        return minimumUnitPrice;
    }
    
    public void setMinimumUnitPrice(String minimumUnitPrice) {
        this.minimumUnitPrice = minimumUnitPrice;
    }
    
    public String getUnitPriceIncrement() {
        return unitPriceIncrement;
    }
    
    public void setUnitPriceIncrement(String unitPriceIncrement) {
        this.unitPriceIncrement = unitPriceIncrement;
    }
    
}
