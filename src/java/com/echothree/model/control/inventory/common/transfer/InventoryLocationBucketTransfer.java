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

public class InventoryLocationBucketTransfer
        extends BaseTransfer {

    private InventoryLocationTransfer inventoryLocation;
    private InventoryBucketTypeTransfer inventoryBucketType;
    private String quantity;

    public InventoryLocationBucketTransfer(InventoryLocationTransfer inventoryLocation,
            InventoryBucketTypeTransfer inventoryBucketType, String quantity) {
        this.inventoryLocation = inventoryLocation;
        this.inventoryBucketType = inventoryBucketType;
        this.quantity = quantity;
    }

    public InventoryLocationTransfer getInventoryLocation() {
        return inventoryLocation;
    }

    public void setInventoryLocation(InventoryLocationTransfer inventoryLocation) {
        this.inventoryLocation = inventoryLocation;
    }

    public InventoryBucketTypeTransfer getInventoryBucketType() {
        return inventoryBucketType;
    }

    public void setInventoryBucketType(InventoryBucketTypeTransfer inventoryBucketType) {
        this.inventoryBucketType = inventoryBucketType;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

}
