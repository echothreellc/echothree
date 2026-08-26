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

import com.echothree.model.control.item.common.transfer.ItemTransfer;
import com.echothree.model.control.party.common.transfer.PartyTransfer;
import com.echothree.model.control.uom.common.transfer.UnitOfMeasureTypeTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class PartyBucketTransfer
        extends BaseTransfer {

    private PartyTransfer party;
    private ItemTransfer item;
    private UnitOfMeasureTypeTransfer unitOfMeasureType;
    private InventoryConditionTransfer inventoryCondition;
    private InventoryBucketTypeTransfer inventoryBucketType;
    private String quantity;

    public PartyBucketTransfer(PartyTransfer party, ItemTransfer item, UnitOfMeasureTypeTransfer unitOfMeasureType,
            InventoryConditionTransfer inventoryCondition, InventoryBucketTypeTransfer inventoryBucketType, String quantity) {
        this.party = party;
        this.item = item;
        this.unitOfMeasureType = unitOfMeasureType;
        this.inventoryCondition = inventoryCondition;
        this.inventoryBucketType = inventoryBucketType;
        this.quantity = quantity;
    }

    public PartyTransfer getParty() {
        return party;
    }

    public void setParty(PartyTransfer party) {
        this.party = party;
    }

    public ItemTransfer getItem() {
        return item;
    }

    public void setItem(ItemTransfer item) {
        this.item = item;
    }

    public UnitOfMeasureTypeTransfer getUnitOfMeasureType() {
        return unitOfMeasureType;
    }

    public void setUnitOfMeasureType(UnitOfMeasureTypeTransfer unitOfMeasureType) {
        this.unitOfMeasureType = unitOfMeasureType;
    }

    public InventoryConditionTransfer getInventoryCondition() {
        return inventoryCondition;
    }

    public void setInventoryCondition(InventoryConditionTransfer inventoryCondition) {
        this.inventoryCondition = inventoryCondition;
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
