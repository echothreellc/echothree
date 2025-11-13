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

import com.echothree.model.control.vendor.common.transfer.ItemPurchasingCategoryDescriptionTransfer;
import com.echothree.model.control.vendor.server.control.VendorControl;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.vendor.server.entity.ItemPurchasingCategoryDescription;

public class ItemPurchasingCategoryDescriptionTransferCache
        extends BaseVendorDescriptionTransferCache<ItemPurchasingCategoryDescription, ItemPurchasingCategoryDescriptionTransfer> {
    
    /** Creates a new instance of ItemPurchasingCategoryDescriptionTransferCache */
    public ItemPurchasingCategoryDescriptionTransferCache(VendorControl vendorControl) {
        super(vendorControl);
    }
    
    public ItemPurchasingCategoryDescriptionTransfer getItemPurchasingCategoryDescriptionTransfer(ItemPurchasingCategoryDescription itemPurchasingCategoryDescription) {
        var itemPurchasingCategoryDescriptionTransfer = get(itemPurchasingCategoryDescription);
        
        if(itemPurchasingCategoryDescriptionTransfer == null) {
            var itemPurchasingCategoryTransferCache = vendorControl.getVendorTransferCaches(userVisit).getItemPurchasingCategoryTransferCache();
            var itemPurchasingCategoryTransfer = itemPurchasingCategoryTransferCache.getItemPurchasingCategoryTransfer(itemPurchasingCategoryDescription.getItemPurchasingCategory());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, itemPurchasingCategoryDescription.getLanguage());
            
            itemPurchasingCategoryDescriptionTransfer = new ItemPurchasingCategoryDescriptionTransfer(languageTransfer, itemPurchasingCategoryTransfer, itemPurchasingCategoryDescription.getDescription());
            put(userVisit, itemPurchasingCategoryDescription, itemPurchasingCategoryDescriptionTransfer);
        }
        
        return itemPurchasingCategoryDescriptionTransfer;
    }
    
}
