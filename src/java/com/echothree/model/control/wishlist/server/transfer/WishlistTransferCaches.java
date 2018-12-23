// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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

package com.echothree.model.control.wishlist.server.transfer;

import com.echothree.model.control.wishlist.server.WishlistControl;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.transfer.BaseTransferCaches;

public class WishlistTransferCaches
        extends BaseTransferCaches {
    
    protected WishlistControl wishlistControl;
    
    protected WishlistTypeTransferCache wishlistTypeTransferCache;
    protected WishlistTypeDescriptionTransferCache wishlistTypeDescriptionTransferCache;
    protected WishlistTypePriorityTransferCache wishlistTypePriorityTransferCache;
    protected WishlistTypePriorityDescriptionTransferCache wishlistTypePriorityDescriptionTransferCache;
    protected WishlistTransferCache wishlistTransferCache;
    protected WishlistLineTransferCache wishlistLineTransferCache;
    
    /** Creates a new instance of WishlistTransferCaches */
    public WishlistTransferCaches(UserVisit userVisit, WishlistControl wishlistControl) {
        super(userVisit);
        
        this.wishlistControl = wishlistControl;
    }
    
    public WishlistTypeTransferCache getWishlistTypeTransferCache() {
        if(wishlistTypeTransferCache == null)
            wishlistTypeTransferCache = new WishlistTypeTransferCache(userVisit, wishlistControl);
        
        return wishlistTypeTransferCache;
    }
    
    public WishlistTypeDescriptionTransferCache getWishlistTypeDescriptionTransferCache() {
        if(wishlistTypeDescriptionTransferCache == null)
            wishlistTypeDescriptionTransferCache = new WishlistTypeDescriptionTransferCache(userVisit, wishlistControl);
        
        return wishlistTypeDescriptionTransferCache;
    }
    
    public WishlistTypePriorityTransferCache getWishlistTypePriorityTransferCache() {
        if(wishlistTypePriorityTransferCache == null)
            wishlistTypePriorityTransferCache = new WishlistTypePriorityTransferCache(userVisit, wishlistControl);
        
        return wishlistTypePriorityTransferCache;
    }
    
    public WishlistTypePriorityDescriptionTransferCache getWishlistTypePriorityDescriptionTransferCache() {
        if(wishlistTypePriorityDescriptionTransferCache == null)
            wishlistTypePriorityDescriptionTransferCache = new WishlistTypePriorityDescriptionTransferCache(userVisit, wishlistControl);
        
        return wishlistTypePriorityDescriptionTransferCache;
    }
    
    public WishlistTransferCache getWishlistTransferCache() {
        if(wishlistTransferCache == null)
            wishlistTransferCache = new WishlistTransferCache(userVisit, wishlistControl);
        
        return wishlistTransferCache;
    }
    
    public WishlistLineTransferCache getWishlistLineTransferCache() {
        if(wishlistLineTransferCache == null)
            wishlistLineTransferCache = new WishlistLineTransferCache(userVisit, wishlistControl);
        
        return wishlistLineTransferCache;
    }
    
}
