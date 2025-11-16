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
import javax.enterprise.inject.spi.CDI;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class VendorTransferCaches
        extends BaseTransferCaches {
    
    protected VendorTypeTransferCache vendorTypeTransferCache;
    protected VendorTypeDescriptionTransferCache vendorTypeDescriptionTransferCache;
    protected VendorTransferCache vendorTransferCache;
    protected VendorItemTransferCache vendorItemTransferCache;
    protected VendorItemCostTransferCache vendorItemCostTransferCache;
    protected ItemPurchasingCategoryDescriptionTransferCache itemPurchasingCategoryDescriptionTransferCache;
    protected ItemPurchasingCategoryTransferCache itemPurchasingCategoryTransferCache;
    
    /** Creates a new instance of VendorTransferCaches */
    protected VendorTransferCaches() {
        super();
    }
    
    public VendorTypeTransferCache getVendorTypeTransferCache() {
        if(vendorTypeTransferCache == null)
            vendorTypeTransferCache = CDI.current().select(VendorTypeTransferCache.class).get();
        
        return vendorTypeTransferCache;
    }
    
    public VendorTypeDescriptionTransferCache getVendorTypeDescriptionTransferCache() {
        if(vendorTypeDescriptionTransferCache == null)
            vendorTypeDescriptionTransferCache = CDI.current().select(VendorTypeDescriptionTransferCache.class).get();
        
        return vendorTypeDescriptionTransferCache;
    }
    
    public VendorTransferCache getVendorTransferCache() {
        if(vendorTransferCache == null)
            vendorTransferCache = CDI.current().select(VendorTransferCache.class).get();
        
        return vendorTransferCache;
    }
    
    public VendorItemTransferCache getVendorItemTransferCache() {
        if(vendorItemTransferCache == null)
            vendorItemTransferCache = CDI.current().select(VendorItemTransferCache.class).get();
        
        return vendorItemTransferCache;
    }
    
    public VendorItemCostTransferCache getVendorItemCostTransferCache() {
        if(vendorItemCostTransferCache == null)
            vendorItemCostTransferCache = CDI.current().select(VendorItemCostTransferCache.class).get();
        
        return vendorItemCostTransferCache;
    }
    
    public ItemPurchasingCategoryDescriptionTransferCache getItemPurchasingCategoryDescriptionTransferCache() {
        if(itemPurchasingCategoryDescriptionTransferCache == null)
            itemPurchasingCategoryDescriptionTransferCache = CDI.current().select(ItemPurchasingCategoryDescriptionTransferCache.class).get();
        
        return itemPurchasingCategoryDescriptionTransferCache;
    }
    
    public ItemPurchasingCategoryTransferCache getItemPurchasingCategoryTransferCache() {
        if(itemPurchasingCategoryTransferCache == null)
            itemPurchasingCategoryTransferCache = CDI.current().select(ItemPurchasingCategoryTransferCache.class).get();
        
        return itemPurchasingCategoryTransferCache;
    }
    
}
