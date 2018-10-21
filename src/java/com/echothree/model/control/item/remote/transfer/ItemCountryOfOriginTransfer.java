// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

package com.echothree.model.control.item.remote.transfer;

import com.echothree.model.control.geo.remote.transfer.CountryTransfer;
import com.echothree.util.remote.transfer.BaseTransfer;

public class ItemCountryOfOriginTransfer
        extends BaseTransfer {
    
    private ItemTransfer item;
    private CountryTransfer countryGeoCode;
    private Integer unformattedPercent;
    private String percent;
    
    /** Creates a new instance of ItemCountryOfOriginTransfer */
    public ItemCountryOfOriginTransfer(ItemTransfer item, CountryTransfer countryGeoCode, Integer unformattedPercent, String percent) {
        this.item = item;
        this.countryGeoCode = countryGeoCode;
        this.unformattedPercent = unformattedPercent;
        this.percent = percent;
    }

    /**
     * @return the item
     */
    public ItemTransfer getItem() {
        return item;
    }

    /**
     * @param item the item to set
     */
    public void setItem(ItemTransfer item) {
        this.item = item;
    }

    /**
     * @return the countryGeoCode
     */
    public CountryTransfer getCountryGeoCode() {
        return countryGeoCode;
    }

    /**
     * @param countryGeoCode the countryGeoCode to set
     */
    public void setCountryGeoCode(CountryTransfer countryGeoCode) {
        this.countryGeoCode = countryGeoCode;
    }

    /**
     * @return the unformattedPercent
     */
    public Integer getUnformattedPercent() {
        return unformattedPercent;
    }

    /**
     * @param unformattedPercent the unformattedPercent to set
     */
    public void setUnformattedPercent(Integer unformattedPercent) {
        this.unformattedPercent = unformattedPercent;
    }

    /**
     * @return the percent
     */
    public String getPercent() {
        return percent;
    }

    /**
     * @param percent the percent to set
     */
    public void setPercent(String percent) {
        this.percent = percent;
    }
    
}
