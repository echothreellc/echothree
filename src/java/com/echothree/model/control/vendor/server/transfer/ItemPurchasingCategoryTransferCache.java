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

package com.echothree.model.control.vendor.server.transfer;

import com.echothree.model.control.vendor.common.transfer.ItemPurchasingCategoryTransfer;
import com.echothree.model.control.vendor.server.control.VendorControl;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.vendor.server.entity.ItemPurchasingCategory;

public class ItemPurchasingCategoryTransferCache
        extends BaseVendorTransferCache<ItemPurchasingCategory, ItemPurchasingCategoryTransfer> {
    
    /** Creates a new instance of ItemPurchasingCategoryTransferCache */
    public ItemPurchasingCategoryTransferCache(VendorControl vendorControl) {
        super(vendorControl);
        
        setIncludeEntityInstance(true);
    }
    
    public ItemPurchasingCategoryTransfer getItemPurchasingCategoryTransfer(UserVisit userVisit, ItemPurchasingCategory itemPurchasingCategory) {
        var itemPurchasingCategoryTransfer = get(itemPurchasingCategory);
        
        if(itemPurchasingCategoryTransfer == null) {
            var itemPurchasingCategoryDetail = itemPurchasingCategory.getLastDetail();
            var itemPurchasingCategoryName = itemPurchasingCategoryDetail.getItemPurchasingCategoryName();
            var parentItemPurchasingCategory = itemPurchasingCategoryDetail.getParentItemPurchasingCategory();
            var parentItemPurchasingCategoryTransfer = parentItemPurchasingCategory == null ? null : getItemPurchasingCategoryTransfer(userVisit, parentItemPurchasingCategory);
            var isDefault = itemPurchasingCategoryDetail.getIsDefault();
            var sortOrder = itemPurchasingCategoryDetail.getSortOrder();
            var description = vendorControl.getBestItemPurchasingCategoryDescription(itemPurchasingCategory, getLanguage(userVisit));
            
            itemPurchasingCategoryTransfer = new ItemPurchasingCategoryTransfer(itemPurchasingCategoryName,
                    parentItemPurchasingCategoryTransfer, isDefault, sortOrder, description);
            put(userVisit, itemPurchasingCategory, itemPurchasingCategoryTransfer);
        }
        
        return itemPurchasingCategoryTransfer;
    }
    
}
