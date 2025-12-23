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

package com.echothree.model.control.tax.common.transfer;

import com.echothree.model.control.geo.common.transfer.CountryTransfer;
import com.echothree.model.control.item.common.transfer.ItemTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class ItemTaxClassificationTransfer
        extends BaseTransfer {
    
    private ItemTransfer item;
    private CountryTransfer countryGeoCode;
    private TaxClassificationTransfer taxClassification;
    
    /** Creates a new instance of ItemTaxClassificationTransfer */
    public ItemTaxClassificationTransfer(ItemTransfer item, CountryTransfer countryGeoCode, TaxClassificationTransfer taxClassification) {
        this.item = item;
        this.countryGeoCode = countryGeoCode;
        this.taxClassification = taxClassification;
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
     * Returns the countryGeoCode.
     * @return the countryGeoCode
     */
    public CountryTransfer getCountryGeoCode() {
        return countryGeoCode;
    }

    /**
     * Sets the countryGeoCode.
     * @param countryGeoCode the countryGeoCode to set
     */
    public void setCountryGeoCode(CountryTransfer countryGeoCode) {
        this.countryGeoCode = countryGeoCode;
    }

    /**
     * Returns the taxClassification.
     * @return the taxClassification
     */
    public TaxClassificationTransfer getTaxClassification() {
        return taxClassification;
    }

    /**
     * Sets the taxClassification.
     * @param taxClassification the taxClassification to set
     */
    public void setTaxClassification(TaxClassificationTransfer taxClassification) {
        this.taxClassification = taxClassification;
    }

}
