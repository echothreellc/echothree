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

import com.echothree.util.server.transfer.BaseTransferCaches;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
public class VendorTransferCaches
        extends BaseTransferCaches {
    
    @Inject
    VendorTypeTransferCache vendorTypeTransferCache;
    
    @Inject
    VendorTypeDescriptionTransferCache vendorTypeDescriptionTransferCache;
    
    @Inject
    VendorTransferCache vendorTransferCache;
    
    @Inject
    VendorItemTransferCache vendorItemTransferCache;
    
    @Inject
    VendorItemCostTransferCache vendorItemCostTransferCache;
    
    @Inject
    ItemPurchasingCategoryDescriptionTransferCache itemPurchasingCategoryDescriptionTransferCache;
    
    @Inject
    ItemPurchasingCategoryTransferCache itemPurchasingCategoryTransferCache;

    /** Creates a new instance of VendorTransferCaches */
    protected VendorTransferCaches() {
        super();
    }
    
    public VendorTypeTransferCache getVendorTypeTransferCache() {
        return vendorTypeTransferCache;
    }
    
    public VendorTypeDescriptionTransferCache getVendorTypeDescriptionTransferCache() {
        return vendorTypeDescriptionTransferCache;
    }
    
    public VendorTransferCache getVendorTransferCache() {
        return vendorTransferCache;
    }
    
    public VendorItemTransferCache getVendorItemTransferCache() {
        return vendorItemTransferCache;
    }
    
    public VendorItemCostTransferCache getVendorItemCostTransferCache() {
        return vendorItemCostTransferCache;
    }
    
    public ItemPurchasingCategoryDescriptionTransferCache getItemPurchasingCategoryDescriptionTransferCache() {
        return itemPurchasingCategoryDescriptionTransferCache;
    }
    
    public ItemPurchasingCategoryTransferCache getItemPurchasingCategoryTransferCache() {
        return itemPurchasingCategoryTransferCache;
    }
    
}
