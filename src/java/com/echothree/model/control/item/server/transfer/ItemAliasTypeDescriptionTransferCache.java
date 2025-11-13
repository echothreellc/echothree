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

import com.echothree.model.control.item.common.transfer.ItemAliasTypeDescriptionTransfer;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.data.item.server.entity.ItemAliasTypeDescription;
import com.echothree.model.data.user.server.entity.UserVisit;

public class ItemAliasTypeDescriptionTransferCache
        extends BaseItemDescriptionTransferCache<ItemAliasTypeDescription, ItemAliasTypeDescriptionTransfer> {
    
    /** Creates a new instance of ItemAliasTypeDescriptionTransferCache */
    public ItemAliasTypeDescriptionTransferCache(ItemControl itemControl) {
        super(itemControl);
    }
    
    @Override
    public ItemAliasTypeDescriptionTransfer getTransfer(ItemAliasTypeDescription itemAliasTypeDescription) {
        var itemAliasTypeDescriptionTransfer = get(itemAliasTypeDescription);
        
        if(itemAliasTypeDescriptionTransfer == null) {
            var itemAliasTypeTransferCache = itemControl.getItemTransferCaches(userVisit).getItemAliasTypeTransferCache();
            var itemAliasTypeTransfer = itemAliasTypeTransferCache.getTransfer(itemAliasTypeDescription.getItemAliasType());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, itemAliasTypeDescription.getLanguage());
            
            itemAliasTypeDescriptionTransfer = new ItemAliasTypeDescriptionTransfer(languageTransfer, itemAliasTypeTransfer, itemAliasTypeDescription.getDescription());
            put(userVisit, itemAliasTypeDescription, itemAliasTypeDescriptionTransfer);
        }
        
        return itemAliasTypeDescriptionTransfer;
    }
    
}
