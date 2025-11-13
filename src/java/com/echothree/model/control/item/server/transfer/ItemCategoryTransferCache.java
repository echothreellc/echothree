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

import com.echothree.model.control.item.common.transfer.ItemCategoryTransfer;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.data.item.server.entity.ItemCategory;
import com.echothree.model.data.user.server.entity.UserVisit;

public class ItemCategoryTransferCache
        extends BaseItemTransferCache<ItemCategory, ItemCategoryTransfer> {
    
    /** Creates a new instance of ItemCategoryTransferCache */
    public ItemCategoryTransferCache(ItemControl itemControl) {
        super(itemControl);
        
        setIncludeEntityInstance(true);
    }
    
    @Override
    public ItemCategoryTransfer getTransfer(UserVisit userVisit, ItemCategory itemCategory) {
        var itemCategoryTransfer = get(itemCategory);
        
        if(itemCategoryTransfer == null) {
            var itemCategoryDetail = itemCategory.getLastDetail();
            var itemCategoryName = itemCategoryDetail.getItemCategoryName();
            var parentItemCategory = itemCategoryDetail.getParentItemCategory();
            var parentItemCategoryTransfer = parentItemCategory == null ? null : getTransfer(userVisit, parentItemCategory);
            var isDefault = itemCategoryDetail.getIsDefault();
            var sortOrder = itemCategoryDetail.getSortOrder();
            var description = itemControl.getBestItemCategoryDescription(itemCategory, getLanguage(userVisit));
            
            itemCategoryTransfer = new ItemCategoryTransfer(itemCategoryName, parentItemCategoryTransfer, isDefault, sortOrder, description);
            put(userVisit, itemCategory, itemCategoryTransfer);
        }
        
        return itemCategoryTransfer;
    }
    
}
