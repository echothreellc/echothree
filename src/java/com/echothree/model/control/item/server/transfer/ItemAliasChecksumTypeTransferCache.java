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

import com.echothree.model.control.item.common.transfer.ItemAliasChecksumTypeTransfer;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.data.item.server.entity.ItemAliasChecksumType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class ItemAliasChecksumTypeTransferCache
        extends BaseItemTransferCache<ItemAliasChecksumType, ItemAliasChecksumTypeTransfer> {

    ItemControl itemControl = Session.getModelController(ItemControl.class);

    /** Creates a new instance of ItemAliasChecksumTypeTransferCache */
    public ItemAliasChecksumTypeTransferCache() {
        super();
    }
    
    @Override
    public ItemAliasChecksumTypeTransfer getTransfer(UserVisit userVisit, ItemAliasChecksumType itemAliasChecksumType) {
        var itemAliasChecksumTypeTransfer = get(itemAliasChecksumType);
        
        if(itemAliasChecksumTypeTransfer == null) {
            var itemAliasChecksumTypeName = itemAliasChecksumType.getItemAliasChecksumTypeName();
            var isDefault = itemAliasChecksumType.getIsDefault();
            var sortOrder = itemAliasChecksumType.getSortOrder();
            var description = itemControl.getBestItemAliasChecksumTypeDescription(itemAliasChecksumType, getLanguage(userVisit));
            
            itemAliasChecksumTypeTransfer = new ItemAliasChecksumTypeTransfer(itemAliasChecksumTypeName, isDefault, sortOrder, description);
            put(userVisit, itemAliasChecksumType, itemAliasChecksumTypeTransfer);
        }
        
        return itemAliasChecksumTypeTransfer;
    }
    
}
