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

import com.echothree.model.control.item.common.transfer.ItemAliasTypeTransfer;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.data.item.server.entity.ItemAliasType;
import com.echothree.model.data.user.server.entity.UserVisit;

public class ItemAliasTypeTransferCache
        extends BaseItemTransferCache<ItemAliasType, ItemAliasTypeTransfer> {
    
    /** Creates a new instance of ItemAliasTypeTransferCache */
    public ItemAliasTypeTransferCache(UserVisit userVisit, ItemControl itemControl) {
        super(userVisit, itemControl);
        
        setIncludeEntityInstance(true);
    }
    
    @Override
    public ItemAliasTypeTransfer getTransfer(ItemAliasType itemAliasType) {
        var itemAliasTypeTransfer = get(itemAliasType);
        
        if(itemAliasTypeTransfer == null) {
            var itemAliasTypeDetail = itemAliasType.getLastDetail();
            var itemAliasTypeName = itemAliasTypeDetail.getItemAliasTypeName();
            var validationPattern = itemAliasTypeDetail.getValidationPattern();
            var itemAliasChecksumType = itemControl.getItemAliasChecksumTypeTransfer(userVisit, itemAliasTypeDetail.getItemAliasChecksumType());
            var allowMultiple = itemAliasTypeDetail.getAllowMultiple();
            var isDefault = itemAliasTypeDetail.getIsDefault();
            var sortOrder = itemAliasTypeDetail.getSortOrder();
            var description = itemControl.getBestItemAliasTypeDescription(itemAliasType, getLanguage());
            
            itemAliasTypeTransfer = new ItemAliasTypeTransfer(itemAliasTypeName, validationPattern, itemAliasChecksumType, allowMultiple, isDefault, sortOrder,
                    description);
            put(itemAliasType, itemAliasTypeTransfer);
        }
        
        return itemAliasTypeTransfer;
    }
    
}
