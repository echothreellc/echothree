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

package com.echothree.model.control.shipment.common.transfer;

import com.echothree.model.control.shipping.common.transfer.ShippingMethodTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class ShipmentTypeShippingMethodTransfer
        extends BaseTransfer {
    
    private ShipmentTypeTransfer shipmentType;
    private ShippingMethodTransfer shippingMethod;
    private Boolean isDefault;
    private Integer sortOrder;
    
    /** Creates a new instance of ShipmentTypeShippingMethodTransfer */
    public ShipmentTypeShippingMethodTransfer(ShipmentTypeTransfer shipmentType, ShippingMethodTransfer shippingMethod, Boolean isDefault,
            Integer sortOrder) {
        this.shipmentType = shipmentType;
        this.shippingMethod = shippingMethod;
        this.isDefault = isDefault;
        this.sortOrder = sortOrder;
    }
    
    public ShipmentTypeTransfer getShipmentType() {
        return shipmentType;
    }
    
    public void setShipmentType(ShipmentTypeTransfer shipmentType) {
        this.shipmentType = shipmentType;
    }
    
    public ShippingMethodTransfer getShippingMethod() {
        return shippingMethod;
    }
    
    public void setShippingMethod(ShippingMethodTransfer shippingMethod) {
        this.shippingMethod = shippingMethod;
    }
    
    public Boolean getIsDefault() {
        return isDefault;
    }
    
    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }
    
    public Integer getSortOrder() {
        return sortOrder;
    }
    
    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }
    
}
