// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

package com.echothree.model.control.inventory.server.transfer;

import com.echothree.model.control.inventory.common.transfer.InventoryConditionTransfer;
import com.echothree.model.control.inventory.common.transfer.PartyInventoryLevelTransfer;
import com.echothree.model.control.inventory.server.control.InventoryControl;
import com.echothree.model.control.item.common.transfer.ItemTransfer;
import com.echothree.model.control.item.server.ItemControl;
import com.echothree.model.control.party.common.transfer.PartyTransfer;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.data.inventory.server.entity.PartyInventoryLevel;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKind;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class PartyInventoryLevelTransferCache
        extends BaseInventoryTransferCache<PartyInventoryLevel, PartyInventoryLevelTransfer> {
    
    ItemControl itemControl = (ItemControl)Session.getModelController(ItemControl.class);
    PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);
    
    /** Creates a new instance of PartyInventoryLevelTransferCache */
    public PartyInventoryLevelTransferCache(UserVisit userVisit, InventoryControl inventoryControl) {
        super(userVisit, inventoryControl);
    }
    
    @Override
    public PartyInventoryLevelTransfer getTransfer(PartyInventoryLevel partyInventoryLevel) {
        PartyInventoryLevelTransfer partyInventoryLevelTransfer = get(partyInventoryLevel);
        
        if(partyInventoryLevelTransfer == null) {
            PartyTransfer partyTransfer = partyControl.getPartyTransfer(userVisit, partyInventoryLevel.getParty());
            Item item = partyInventoryLevel.getItem();
            ItemTransfer itemTransfer = itemControl.getItemTransfer(userVisit, item);
            InventoryConditionTransfer inventoryConditionTransfer = inventoryControl.getInventoryConditionTransfer(userVisit, partyInventoryLevel.getInventoryCondition());
            UnitOfMeasureKind unitOfMeasureKind = item.getLastDetail().getUnitOfMeasureKind();
            String minimumInventory = formatUnitOfMeasure(unitOfMeasureKind, partyInventoryLevel.getMinimumInventory());
            String maximumInventory = formatUnitOfMeasure(unitOfMeasureKind, partyInventoryLevel.getMaximumInventory());
            String reorderQuantity = formatUnitOfMeasure(unitOfMeasureKind, partyInventoryLevel.getReorderQuantity());
            
            partyInventoryLevelTransfer = new PartyInventoryLevelTransfer(partyTransfer, itemTransfer, inventoryConditionTransfer,
                    minimumInventory, maximumInventory, reorderQuantity);
            put(partyInventoryLevel, partyInventoryLevelTransfer);
        }
        
        return partyInventoryLevelTransfer;
    }
    
}
