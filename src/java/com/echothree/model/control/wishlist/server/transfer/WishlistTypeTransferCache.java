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

import com.echothree.model.control.wishlist.common.transfer.WishlistTypeTransfer;
import com.echothree.model.control.wishlist.server.control.WishlistControl;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.wishlist.server.entity.WishlistType;
import com.echothree.util.server.persistence.Session;

public class WishlistTypeTransferCache
        extends BaseWishlistTransferCache<WishlistType, WishlistTypeTransfer> {

    WishlistControl wishlistControl = Session.getModelController(WishlistControl.class);

    /** Creates a new instance of WishlistTypeTransferCache */
    public WishlistTypeTransferCache() {
        super();

        setIncludeEntityInstance(true);
    }
    
    public WishlistTypeTransfer getWishlistTypeTransfer(UserVisit userVisit, WishlistType wishlistType) {
        var wishlistTypeTransfer = get(wishlistType);
        
        if(wishlistTypeTransfer == null) {
            var wishlistTypeDetail = wishlistType.getLastDetail();
            var wishlistTypeName = wishlistTypeDetail.getWishlistTypeName();
            var isDefault = wishlistTypeDetail.getIsDefault();
            var sortOrder = wishlistTypeDetail.getSortOrder();
            var description = wishlistControl.getBestWishlistTypeDescription(wishlistType, getLanguage(userVisit));
            
            wishlistTypeTransfer = new WishlistTypeTransfer(wishlistTypeName, isDefault, sortOrder, description);
            put(userVisit, wishlistType, wishlistTypeTransfer);
        }
        
        return wishlistTypeTransfer;
    }
    
}
