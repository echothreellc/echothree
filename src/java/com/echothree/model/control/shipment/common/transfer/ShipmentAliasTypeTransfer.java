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

import com.echothree.util.common.transfer.BaseTransfer;

public class ShipmentAliasTypeTransfer
        extends BaseTransfer {
    
    private ShipmentTypeTransfer shipmentType;
    private String shipmentAliasTypeName;
    private String validationPattern;
    private Boolean isDefault;
    private Integer sortOrder;
    private String description;
    
    /** Creates a new instance of ShipmentAliasTypeTransfer */
    public ShipmentAliasTypeTransfer(ShipmentTypeTransfer shipmentType, String shipmentAliasTypeName, String validationPattern,
            Boolean isDefault, Integer sortOrder, String description) {
        this.shipmentType = shipmentType;
        this.shipmentAliasTypeName = shipmentAliasTypeName;
        this.validationPattern = validationPattern;
        this.isDefault = isDefault;
        this.sortOrder = sortOrder;
        this.description = description;
    }

    public ShipmentTypeTransfer getShipmentType() {
        return shipmentType;
    }

    public void setShipmentType(ShipmentTypeTransfer shipmentType) {
        this.shipmentType = shipmentType;
    }

    public String getShipmentAliasTypeName() {
        return shipmentAliasTypeName;
    }

    public void setShipmentAliasTypeName(String shipmentAliasTypeName) {
        this.shipmentAliasTypeName = shipmentAliasTypeName;
    }

    public String getValidationPattern() {
        return validationPattern;
    }

    public void setValidationPattern(String validationPattern) {
        this.validationPattern = validationPattern;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
}
