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

package com.echothree.model.control.inventory.common.transfer;

import com.echothree.util.common.transfer.BaseTransfer;

public class InventoryDispositionAdjustmentTransfer
        extends BaseTransfer {

    private InventoryDispositionTransfer inventoryDisposition;
    private InventoryAdjustmentTypeTransfer inventoryAdjustmentType;
    private InventoryBucketTypeTransfer inventoryBucketType;
    private String inventoryDispositionAdjustmentName;
    private Boolean isDefault;
    private Integer sortOrder;
    private String description;
    
    /** Creates a new instance of InventoryDispositionAdjustmentTransfer */
    public InventoryDispositionAdjustmentTransfer(InventoryDispositionTransfer inventoryDisposition,
            InventoryAdjustmentTypeTransfer inventoryAdjustmentType, InventoryBucketTypeTransfer inventoryBucketType,
            String inventoryDispositionAdjustmentName,
            Boolean isDefault, Integer sortOrder, String description) {
        this.inventoryDisposition = inventoryDisposition;
        this.inventoryAdjustmentType = inventoryAdjustmentType;
        this.inventoryBucketType = inventoryBucketType;
        this.inventoryDispositionAdjustmentName = inventoryDispositionAdjustmentName;
        this.isDefault = isDefault;
        this.sortOrder = sortOrder;
        this.description = description;
    }

    public InventoryAdjustmentTypeTransfer getInventoryAdjustmentType() { return inventoryAdjustmentType; }
    public void setInventoryAdjustmentType(InventoryAdjustmentTypeTransfer inventoryAdjustmentType) {
        this.inventoryAdjustmentType = inventoryAdjustmentType;
    }

    public InventoryBucketTypeTransfer getInventoryBucketType() { return inventoryBucketType; }
    public void setInventoryBucketType(InventoryBucketTypeTransfer inventoryBucketType) { this.inventoryBucketType = inventoryBucketType; }

    public InventoryDispositionTransfer getInventoryDisposition() {
        return inventoryDisposition;
    }

    public void setInventoryDisposition(InventoryDispositionTransfer inventoryDisposition) {
        this.inventoryDisposition = inventoryDisposition;
    }

    public String getInventoryDispositionAdjustmentName() {
        return inventoryDispositionAdjustmentName;
    }

    public void setInventoryDispositionAdjustmentName(String inventoryDispositionAdjustmentName) {
        this.inventoryDispositionAdjustmentName = inventoryDispositionAdjustmentName;
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
