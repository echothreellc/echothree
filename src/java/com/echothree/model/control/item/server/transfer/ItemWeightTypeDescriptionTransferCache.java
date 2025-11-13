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

import com.echothree.model.control.item.common.transfer.ItemWeightTypeDescriptionTransfer;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.data.item.server.entity.ItemWeightTypeDescription;
import com.echothree.model.data.user.server.entity.UserVisit;

public class ItemWeightTypeDescriptionTransferCache
        extends BaseItemDescriptionTransferCache<ItemWeightTypeDescription, ItemWeightTypeDescriptionTransfer> {
    
    /** Creates a new instance of ItemWeightTypeDescriptionTransferCache */
    public ItemWeightTypeDescriptionTransferCache(ItemControl itemControl) {
        super(itemControl);
    }
    
    @Override
    public ItemWeightTypeDescriptionTransfer getTransfer(UserVisit userVisit, ItemWeightTypeDescription itemWeightTypeDescription) {
        var itemWeightTypeDescriptionTransfer = get(itemWeightTypeDescription);
        
        if(itemWeightTypeDescriptionTransfer == null) {
            var itemWeightTypeTransferCache = itemControl.getItemTransferCaches().getItemWeightTypeTransferCache();
            var itemWeightTypeTransfer = itemWeightTypeTransferCache.getTransfer(userVisit, itemWeightTypeDescription.getItemWeightType());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, itemWeightTypeDescription.getLanguage());
            
            itemWeightTypeDescriptionTransfer = new ItemWeightTypeDescriptionTransfer(languageTransfer, itemWeightTypeTransfer, itemWeightTypeDescription.getDescription());
            put(userVisit, itemWeightTypeDescription, itemWeightTypeDescriptionTransfer);
        }
        
        return itemWeightTypeDescriptionTransfer;
    }
    
}
