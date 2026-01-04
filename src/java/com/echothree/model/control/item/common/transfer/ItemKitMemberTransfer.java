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

package com.echothree.model.control.item.common.transfer;

import com.echothree.model.control.inventory.common.transfer.InventoryConditionTransfer;
import com.echothree.model.control.uom.common.transfer.UnitOfMeasureTypeTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class ItemKitMemberTransfer
        extends BaseTransfer {
    
    private ItemTransfer item;
    private InventoryConditionTransfer inventoryCondition;
    private UnitOfMeasureTypeTransfer unitOfMeasureType;
    private ItemTransfer memberItem;
    private InventoryConditionTransfer memberInventoryCondition;
    private UnitOfMeasureTypeTransfer memberUnitOfMeasureType;
    private String quantity;
    
    /** Creates a new instance of ItemKitMemberTransfer */
    public ItemKitMemberTransfer(ItemTransfer item, InventoryConditionTransfer inventoryCondition,
            UnitOfMeasureTypeTransfer unitOfMeasureType, ItemTransfer memberItem,
            InventoryConditionTransfer memberInventoryCondition, UnitOfMeasureTypeTransfer memberUnitOfMeasureType,
            String quantity) {
        this.item = item;
        this.inventoryCondition = inventoryCondition;
        this.unitOfMeasureType = unitOfMeasureType;
        this.memberItem = memberItem;
        this.memberInventoryCondition = memberInventoryCondition;
        this.memberUnitOfMeasureType = memberUnitOfMeasureType;
        this.quantity = quantity;
    }
    
    public ItemTransfer getItem() {
        return item;
    }
    
    public void setItem(ItemTransfer item) {
        this.item = item;
    }
    
    public InventoryConditionTransfer getInventoryCondition() {
        return inventoryCondition;
    }
    
    public void setInventoryCondition(InventoryConditionTransfer inventoryCondition) {
        this.inventoryCondition = inventoryCondition;
    }
    
    public UnitOfMeasureTypeTransfer getUnitOfMeasureType() {
        return unitOfMeasureType;
    }
    
    public void setUnitOfMeasureType(UnitOfMeasureTypeTransfer unitOfMeasureType) {
        this.unitOfMeasureType = unitOfMeasureType;
    }
    
    public ItemTransfer getMemberItem() {
        return memberItem;
    }
    
    public void setMemberItem(ItemTransfer memberItem) {
        this.memberItem = memberItem;
    }
    
    public InventoryConditionTransfer getMemberInventoryCondition() {
        return memberInventoryCondition;
    }
    
    public void setMemberInventoryCondition(InventoryConditionTransfer memberInventoryCondition) {
        this.memberInventoryCondition = memberInventoryCondition;
    }
    
    public UnitOfMeasureTypeTransfer getMemberUnitOfMeasureType() {
        return memberUnitOfMeasureType;
    }
    
    public void setMemberUnitOfMeasureType(UnitOfMeasureTypeTransfer memberUnitOfMeasureType) {
        this.memberUnitOfMeasureType = memberUnitOfMeasureType;
    }
    
    public String getQuantity() {
        return quantity;
    }
    
    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
    
}
