// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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

import com.echothree.model.control.item.common.transfer.ItemInventoryTypeTransfer;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.data.item.server.entity.ItemInventoryType;
import com.echothree.model.data.user.server.entity.UserVisit;

public class ItemInventoryTypeTransferCache
        extends BaseItemTransferCache<ItemInventoryType, ItemInventoryTypeTransfer> {
    
    /** Creates a new instance of ItemInventoryTypeTransferCache */
    public ItemInventoryTypeTransferCache(ItemControl itemControl) {
        super(itemControl);
    }
    
    @Override
    public ItemInventoryTypeTransfer getTransfer(UserVisit userVisit, ItemInventoryType itemInventoryType) {
        var itemInventoryTypeTransfer = get(itemInventoryType);
        
        if(itemInventoryTypeTransfer == null) {
            var itemInventoryTypeName = itemInventoryType.getItemInventoryTypeName();
            var isDefault = itemInventoryType.getIsDefault();
            var sortOrder = itemInventoryType.getSortOrder();
            var description = itemControl.getBestItemInventoryTypeDescription(itemInventoryType, getLanguage(userVisit));
            
            itemInventoryTypeTransfer = new ItemInventoryTypeTransfer(itemInventoryTypeName, isDefault, sortOrder, description);
            put(userVisit, itemInventoryType, itemInventoryTypeTransfer);
        }
        
        return itemInventoryTypeTransfer;
    }
    
}
