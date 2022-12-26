// --------------------------------------------------------------------------------
// Copyright 2002-2023 Echo Three, LLC
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

import com.echothree.model.control.wishlist.common.transfer.WishlistTypeTransfer;
import com.echothree.model.control.wishlist.server.control.WishlistControl;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.wishlist.server.entity.WishlistType;
import com.echothree.model.data.wishlist.server.entity.WishlistTypeDetail;

public class WishlistTypeTransferCache
        extends BaseWishlistTransferCache<WishlistType, WishlistTypeTransfer> {
    
    /** Creates a new instance of WishlistTypeTransferCache */
    public WishlistTypeTransferCache(UserVisit userVisit, WishlistControl wishlistControl) {
        super(userVisit, wishlistControl);

        setIncludeEntityInstance(true);
    }
    
    public WishlistTypeTransfer getWishlistTypeTransfer(WishlistType wishlistType) {
        WishlistTypeTransfer wishlistTypeTransfer = get(wishlistType);
        
        if(wishlistTypeTransfer == null) {
            WishlistTypeDetail wishlistTypeDetail = wishlistType.getLastDetail();
            String wishlistTypeName = wishlistTypeDetail.getWishlistTypeName();
            Boolean isDefault = wishlistTypeDetail.getIsDefault();
            Integer sortOrder = wishlistTypeDetail.getSortOrder();
            String description = wishlistControl.getBestWishlistTypeDescription(wishlistType, getLanguage());
            
            wishlistTypeTransfer = new WishlistTypeTransfer(wishlistTypeName, isDefault, sortOrder, description);
            put(wishlistType, wishlistTypeTransfer);
        }
        
        return wishlistTypeTransfer;
    }
    
}
