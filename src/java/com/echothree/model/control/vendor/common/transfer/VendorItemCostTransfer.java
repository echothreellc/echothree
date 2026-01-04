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

package com.echothree.model.control.vendor.common.transfer;

import com.echothree.model.control.inventory.common.transfer.InventoryConditionTransfer;
import com.echothree.model.control.uom.common.transfer.UnitOfMeasureTypeTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class VendorItemCostTransfer
        extends BaseTransfer {
    
    private VendorItemTransfer vendorItem;
    private InventoryConditionTransfer inventoryCondition;
    private UnitOfMeasureTypeTransfer unitOfMeasureType;
    private Long unformattedUnitCost;
    private String unitCost;
    
    /** Creates a new instance of VendorItemCostTransfer */
    public VendorItemCostTransfer(VendorItemTransfer vendorItem, InventoryConditionTransfer inventoryCondition, UnitOfMeasureTypeTransfer unitOfMeasureType,
            Long unformattedUnitCost, String unitCost) {
        this.vendorItem = vendorItem;
        this.inventoryCondition = inventoryCondition;
        this.unitOfMeasureType = unitOfMeasureType;
        this.unformattedUnitCost = unformattedUnitCost;
        this.unitCost = unitCost;
    }

    /**
     * Returns the vendorItem.
     * @return the vendorItem
     */
    public VendorItemTransfer getVendorItem() {
        return vendorItem;
    }

    /**
     * Sets the vendorItem.
     * @param vendorItem the vendorItem to set
     */
    public void setVendorItem(VendorItemTransfer vendorItem) {
        this.vendorItem = vendorItem;
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
     * Returns the unformattedUnitCost.
     * @return the unformattedUnitCost
     */
    public Long getUnformattedUnitCost() {
        return unformattedUnitCost;
    }

    /**
     * Sets the unformattedUnitCost.
     * @param unformattedUnitCost the unformattedUnitCost to set
     */
    public void setUnformattedUnitCost(Long unformattedUnitCost) {
        this.unformattedUnitCost = unformattedUnitCost;
    }

    /**
     * Returns the unitCost.
     * @return the unitCost
     */
    public String getUnitCost() {
        return unitCost;
    }

    /**
     * Sets the unitCost.
     * @param unitCost the unitCost to set
     */
    public void setUnitCost(String unitCost) {
        this.unitCost = unitCost;
    }
    
}
