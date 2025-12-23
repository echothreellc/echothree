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
import com.echothree.util.common.transfer.BaseTransfer;

public class PartyInventoryLevelTransfer
        extends BaseTransfer {
    
    private PartyTransfer party;
    private ItemTransfer item;
    private InventoryConditionTransfer inventoryCondition;
    private String minimumInventory;
    private String maximumInventory;
    private String reorderQuantity;
    
    /** Creates a new instance of PartyInventoryLevelTransfer */
    public PartyInventoryLevelTransfer(PartyTransfer party, ItemTransfer item, InventoryConditionTransfer inventoryCondition,
            String minimumInventory, String maximumInventory, String reorderQuantity) {
        this.party = party;
        this.item = item;
        this.inventoryCondition = inventoryCondition;
        this.minimumInventory = minimumInventory;
        this.maximumInventory = maximumInventory;
        this.reorderQuantity = reorderQuantity;
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
    
    public InventoryConditionTransfer getInventoryCondition() {
        return inventoryCondition;
    }
    
    public void setInventoryCondition(InventoryConditionTransfer inventoryCondition) {
        this.inventoryCondition = inventoryCondition;
    }
    
    public String getMinimumInventory() {
        return minimumInventory;
    }
    
    public void setMinimumInventory(String minimumInventory) {
        this.minimumInventory = minimumInventory;
    }
    
    public String getMaximumInventory() {
        return maximumInventory;
    }
    
    public void setMaximumInventory(String maximumInventory) {
        this.maximumInventory = maximumInventory;
    }
    
    public String getReorderQuantity() {
        return reorderQuantity;
    }
    
    public void setReorderQuantity(String reorderQuantity) {
        this.reorderQuantity = reorderQuantity;
    }
    
}
