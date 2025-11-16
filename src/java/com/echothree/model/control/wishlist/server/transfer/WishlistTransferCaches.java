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

package com.echothree.model.control.wishlist.server.transfer;

import com.echothree.util.server.transfer.BaseTransferCaches;
import javax.enterprise.inject.spi.CDI;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class WishlistTransferCaches
        extends BaseTransferCaches {
    
    protected WishlistTypeTransferCache wishlistTypeTransferCache;
    protected WishlistTypeDescriptionTransferCache wishlistTypeDescriptionTransferCache;
    protected WishlistPriorityTransferCache wishlistPriorityTransferCache;
    protected WishlistPriorityDescriptionTransferCache wishlistPriorityDescriptionTransferCache;
    protected WishlistTransferCache wishlistTransferCache;
    protected WishlistLineTransferCache wishlistLineTransferCache;
    
    /** Creates a new instance of WishlistTransferCaches */
    protected WishlistTransferCaches() {
        super();
    }
    
    public WishlistTypeTransferCache getWishlistTypeTransferCache() {
        if(wishlistTypeTransferCache == null)
            wishlistTypeTransferCache = CDI.current().select(WishlistTypeTransferCache.class).get();
        
        return wishlistTypeTransferCache;
    }
    
    public WishlistTypeDescriptionTransferCache getWishlistTypeDescriptionTransferCache() {
        if(wishlistTypeDescriptionTransferCache == null)
            wishlistTypeDescriptionTransferCache = CDI.current().select(WishlistTypeDescriptionTransferCache.class).get();
        
        return wishlistTypeDescriptionTransferCache;
    }
    
    public WishlistPriorityTransferCache getWishlistPriorityTransferCache() {
        if(wishlistPriorityTransferCache == null)
            wishlistPriorityTransferCache = CDI.current().select(WishlistPriorityTransferCache.class).get();
        
        return wishlistPriorityTransferCache;
    }
    
    public WishlistPriorityDescriptionTransferCache getWishlistPriorityDescriptionTransferCache() {
        if(wishlistPriorityDescriptionTransferCache == null)
            wishlistPriorityDescriptionTransferCache = CDI.current().select(WishlistPriorityDescriptionTransferCache.class).get();
        
        return wishlistPriorityDescriptionTransferCache;
    }
    
    public WishlistTransferCache getWishlistTransferCache() {
        if(wishlistTransferCache == null)
            wishlistTransferCache = CDI.current().select(WishlistTransferCache.class).get();
        
        return wishlistTransferCache;
    }
    
    public WishlistLineTransferCache getWishlistLineTransferCache() {
        if(wishlistLineTransferCache == null)
            wishlistLineTransferCache = CDI.current().select(WishlistLineTransferCache.class).get();
        
        return wishlistLineTransferCache;
    }
    
}
