// --------------------------------------------------------------------------------
// Copyright 2002-2024 Echo Three, LLC
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

import com.echothree.model.control.item.common.transfer.ItemAliasChecksumTypeTransfer;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.data.item.server.entity.ItemAliasChecksumType;
import com.echothree.model.data.user.server.entity.UserVisit;

public class ItemAliasChecksumTypeTransferCache
        extends BaseItemTransferCache<ItemAliasChecksumType, ItemAliasChecksumTypeTransfer> {
    
    /** Creates a new instance of ItemAliasChecksumTypeTransferCache */
    public ItemAliasChecksumTypeTransferCache(UserVisit userVisit, ItemControl itemControl) {
        super(userVisit, itemControl);
    }
    
    @Override
    public ItemAliasChecksumTypeTransfer getTransfer(ItemAliasChecksumType itemAliasChecksumType) {
        ItemAliasChecksumTypeTransfer itemAliasChecksumTypeTransfer = get(itemAliasChecksumType);
        
        if(itemAliasChecksumTypeTransfer == null) {
            String itemAliasChecksumTypeName = itemAliasChecksumType.getItemAliasChecksumTypeName();
            Boolean isDefault = itemAliasChecksumType.getIsDefault();
            Integer sortOrder = itemAliasChecksumType.getSortOrder();
            String description = itemControl.getBestItemAliasChecksumTypeDescription(itemAliasChecksumType, getLanguage());
            
            itemAliasChecksumTypeTransfer = new ItemAliasChecksumTypeTransfer(itemAliasChecksumTypeName, isDefault, sortOrder, description);
            put(itemAliasChecksumType, itemAliasChecksumTypeTransfer);
        }
        
        return itemAliasChecksumTypeTransfer;
    }
    
}
