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

package com.echothree.model.control.item.server.transfer;


import com.echothree.model.control.inventory.remote.transfer.InventoryConditionTransfer;
import com.echothree.model.control.inventory.server.InventoryControl;
import com.echothree.model.control.item.remote.transfer.ItemKitMemberTransfer;
import com.echothree.model.control.item.remote.transfer.ItemTransfer;
import com.echothree.model.control.item.server.ItemControl;
import com.echothree.model.control.uom.remote.transfer.UnitOfMeasureTypeTransfer;
import com.echothree.model.control.uom.server.UomControl;
import com.echothree.model.data.item.server.entity.ItemKitMember;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class ItemKitMemberTransferCache
        extends BaseItemTransferCache<ItemKitMember, ItemKitMemberTransfer> {
    
    InventoryControl inventoryControl = (InventoryControl)Session.getModelController(InventoryControl.class);
    UomControl uomControl = (UomControl)Session.getModelController(UomControl.class);
    
    /** Creates a new instance of ItemKitMemberTransferCache */
    public ItemKitMemberTransferCache(UserVisit userVisit, ItemControl itemControl) {
        super(userVisit, itemControl);
    }
    
    @Override
    public ItemKitMemberTransfer getTransfer(ItemKitMember itemKitMember) {
        ItemKitMemberTransfer itemKitMemberTransfer = get(itemKitMember);
        
        if(itemKitMemberTransfer == null) {
            ItemTransfer item = itemControl.getItemTransfer(userVisit, itemKitMember.getItem());
            InventoryConditionTransfer inventoryCondition = inventoryControl.getInventoryConditionTransfer(userVisit, itemKitMember.getInventoryCondition());
            UnitOfMeasureTypeTransfer unitOfMeasureType = uomControl.getUnitOfMeasureTypeTransfer(userVisit, itemKitMember.getUnitOfMeasureType());
            ItemTransfer memberItem = itemControl.getItemTransfer(userVisit, itemKitMember.getMemberItem());
            InventoryConditionTransfer memberInventoryCondition = inventoryControl.getInventoryConditionTransfer(userVisit, itemKitMember.getMemberInventoryCondition());
            UnitOfMeasureTypeTransfer memberUnitOfMeasureType = uomControl.getUnitOfMeasureTypeTransfer(userVisit, itemKitMember.getMemberUnitOfMeasureType());
            String quantity = itemKitMember.getQuantity().toString();
            
            itemKitMemberTransfer = new ItemKitMemberTransfer(item, inventoryCondition, unitOfMeasureType, memberItem, memberInventoryCondition,
                    memberUnitOfMeasureType, quantity);
            put(itemKitMember, itemKitMemberTransfer);
        }
        
        return itemKitMemberTransfer;
    }
    
}
