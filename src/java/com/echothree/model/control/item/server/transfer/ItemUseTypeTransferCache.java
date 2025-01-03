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

import com.echothree.model.control.item.common.transfer.ItemUseTypeTransfer;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.data.item.server.entity.ItemUseType;
import com.echothree.model.data.user.server.entity.UserVisit;

public class ItemUseTypeTransferCache
        extends BaseItemTransferCache<ItemUseType, ItemUseTypeTransfer> {
    
    /** Creates a new instance of ItemUseTypeTransferCache */
    public ItemUseTypeTransferCache(UserVisit userVisit, ItemControl itemControl) {
        super(userVisit, itemControl);
    }
    
    @Override
    public ItemUseTypeTransfer getTransfer(ItemUseType itemUseType) {
        var itemUseTypeTransfer = get(itemUseType);
        
        if(itemUseTypeTransfer == null) {
            var itemUseTypeName = itemUseType.getItemUseTypeName();
            var isDefault = itemUseType.getIsDefault();
            var sortOrder = itemUseType.getSortOrder();
            var description = itemControl.getBestItemUseTypeDescription(itemUseType, getLanguage());
            
            itemUseTypeTransfer = new ItemUseTypeTransfer(itemUseTypeName, isDefault, sortOrder, description);
            put(itemUseType, itemUseTypeTransfer);
        }
        
        return itemUseTypeTransfer;
    }
    
}
