// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

import com.echothree.model.control.vendor.server.control.VendorControl;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.transfer.BaseTransferCaches;

public class VendorTransferCaches
        extends BaseTransferCaches {
    
    protected VendorControl vendorControl;
    
    protected VendorTypeTransferCache vendorTypeTransferCache;
    protected VendorTypeDescriptionTransferCache vendorTypeDescriptionTransferCache;
    protected VendorTransferCache vendorTransferCache;
    protected VendorItemTransferCache vendorItemTransferCache;
    protected VendorItemCostTransferCache vendorItemCostTransferCache;
    protected ItemPurchasingCategoryDescriptionTransferCache itemPurchasingCategoryDescriptionTransferCache;
    protected ItemPurchasingCategoryTransferCache itemPurchasingCategoryTransferCache;
    
    /** Creates a new instance of VendorTransferCaches */
    public VendorTransferCaches(UserVisit userVisit, VendorControl vendorControl) {
        super(userVisit);
        
        this.vendorControl = vendorControl;
    }
    
    public VendorTypeTransferCache getVendorTypeTransferCache() {
        if(vendorTypeTransferCache == null)
            vendorTypeTransferCache = new VendorTypeTransferCache(userVisit, vendorControl);
        
        return vendorTypeTransferCache;
    }
    
    public VendorTypeDescriptionTransferCache getVendorTypeDescriptionTransferCache() {
        if(vendorTypeDescriptionTransferCache == null)
            vendorTypeDescriptionTransferCache = new VendorTypeDescriptionTransferCache(userVisit, vendorControl);
        
        return vendorTypeDescriptionTransferCache;
    }
    
    public VendorTransferCache getVendorTransferCache() {
        if(vendorTransferCache == null)
            vendorTransferCache = new VendorTransferCache(userVisit, vendorControl);
        
        return vendorTransferCache;
    }
    
    public VendorItemTransferCache getVendorItemTransferCache() {
        if(vendorItemTransferCache == null)
            vendorItemTransferCache = new VendorItemTransferCache(userVisit, vendorControl);
        
        return vendorItemTransferCache;
    }
    
    public VendorItemCostTransferCache getVendorItemCostTransferCache() {
        if(vendorItemCostTransferCache == null)
            vendorItemCostTransferCache = new VendorItemCostTransferCache(userVisit, vendorControl);
        
        return vendorItemCostTransferCache;
    }
    
    public ItemPurchasingCategoryDescriptionTransferCache getItemPurchasingCategoryDescriptionTransferCache() {
        if(itemPurchasingCategoryDescriptionTransferCache == null)
            itemPurchasingCategoryDescriptionTransferCache = new ItemPurchasingCategoryDescriptionTransferCache(userVisit, vendorControl);
        
        return itemPurchasingCategoryDescriptionTransferCache;
    }
    
    public ItemPurchasingCategoryTransferCache getItemPurchasingCategoryTransferCache() {
        if(itemPurchasingCategoryTransferCache == null)
            itemPurchasingCategoryTransferCache = new ItemPurchasingCategoryTransferCache(userVisit, vendorControl);
        
        return itemPurchasingCategoryTransferCache;
    }
    
}
