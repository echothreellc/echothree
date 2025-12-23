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

package com.echothree.model.control.item.server.transfer;


import com.echothree.model.control.inventory.server.control.InventoryControl;
import com.echothree.model.control.item.common.transfer.ItemKitMemberTransfer;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.data.item.server.entity.ItemKitMember;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class ItemKitMemberTransferCache
        extends BaseItemTransferCache<ItemKitMember, ItemKitMemberTransfer> {
    
    InventoryControl inventoryControl = Session.getModelController(InventoryControl.class);
    ItemControl itemControl = Session.getModelController(ItemControl.class);
    UomControl uomControl = Session.getModelController(UomControl.class);
    
    /** Creates a new instance of ItemKitMemberTransferCache */
    protected ItemKitMemberTransferCache() {
        super();
    }
    
    @Override
    public ItemKitMemberTransfer getTransfer(UserVisit userVisit, ItemKitMember itemKitMember) {
        var itemKitMemberTransfer = get(itemKitMember);
        
        if(itemKitMemberTransfer == null) {
            var item = itemControl.getItemTransfer(userVisit, itemKitMember.getItem());
            var inventoryCondition = inventoryControl.getInventoryConditionTransfer(userVisit, itemKitMember.getInventoryCondition());
            var unitOfMeasureType = uomControl.getUnitOfMeasureTypeTransfer(userVisit, itemKitMember.getUnitOfMeasureType());
            var memberItem = itemControl.getItemTransfer(userVisit, itemKitMember.getMemberItem());
            var memberInventoryCondition = inventoryControl.getInventoryConditionTransfer(userVisit, itemKitMember.getMemberInventoryCondition());
            var memberUnitOfMeasureType = uomControl.getUnitOfMeasureTypeTransfer(userVisit, itemKitMember.getMemberUnitOfMeasureType());
            var quantity = itemKitMember.getQuantity().toString();
            
            itemKitMemberTransfer = new ItemKitMemberTransfer(item, inventoryCondition, unitOfMeasureType, memberItem, memberInventoryCondition,
                    memberUnitOfMeasureType, quantity);
            put(userVisit, itemKitMember, itemKitMemberTransfer);
        }
        
        return itemKitMemberTransfer;
    }
    
}
