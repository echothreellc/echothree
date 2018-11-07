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

package com.echothree.model.control.shipping.common.transfer;

import com.echothree.model.control.selector.common.transfer.SelectorTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class ShippingMethodTransfer
        extends BaseTransfer {
    
    private String shippingMethodName;
    private SelectorTransfer geoCodeSelector;
    private SelectorTransfer itemSelector;
    private Integer sortOrder;
    private String description;
    
    /** Creates a new instance of ShippingMethodTransfer */
    public ShippingMethodTransfer(String shippingMethodName, SelectorTransfer geoCodeSelector, SelectorTransfer itemSelector, Integer sortOrder,
            String description) {
        this.shippingMethodName = shippingMethodName;
        this.geoCodeSelector = geoCodeSelector;
        this.itemSelector = itemSelector;
        this.sortOrder = sortOrder;
        this.description = description;
    }

    /**
     * @return the shippingMethodName
     */
    public String getShippingMethodName() {
        return shippingMethodName;
    }

    /**
     * @param shippingMethodName the shippingMethodName to set
     */
    public void setShippingMethodName(String shippingMethodName) {
        this.shippingMethodName = shippingMethodName;
    }

    /**
     * @return the geoCodeSelector
     */
    public SelectorTransfer getGeoCodeSelector() {
        return geoCodeSelector;
    }

    /**
     * @param geoCodeSelector the geoCodeSelector to set
     */
    public void setGeoCodeSelector(SelectorTransfer geoCodeSelector) {
        this.geoCodeSelector = geoCodeSelector;
    }

    /**
     * @return the itemSelector
     */
    public SelectorTransfer getItemSelector() {
        return itemSelector;
    }

    /**
     * @param itemSelector the itemSelector to set
     */
    public void setItemSelector(SelectorTransfer itemSelector) {
        this.itemSelector = itemSelector;
    }

    /**
     * @return the sortOrder
     */
    public Integer getSortOrder() {
        return sortOrder;
    }

    /**
     * @param sortOrder the sortOrder to set
     */
    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }
    
}
