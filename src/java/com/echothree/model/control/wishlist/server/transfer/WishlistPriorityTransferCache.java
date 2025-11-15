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

import com.echothree.model.control.wishlist.common.transfer.WishlistPriorityTransfer;
import com.echothree.model.control.wishlist.server.control.WishlistControl;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.wishlist.server.entity.WishlistPriority;
import com.echothree.util.server.persistence.Session;

public class WishlistPriorityTransferCache
        extends BaseWishlistTransferCache<WishlistPriority, WishlistPriorityTransfer> {

    WishlistControl wishlistControl = Session.getModelController(WishlistControl.class);

    /** Creates a new instance of WishlistPriorityTransferCache */
    public WishlistPriorityTransferCache() {
        super();
        
        setIncludeEntityInstance(true);
    }
    
    public WishlistPriorityTransfer getWishlistPriorityTransfer(UserVisit userVisit, WishlistPriority wishlistPriority) {
        var wishlistPriorityTransfer = get(wishlistPriority);
        
        if(wishlistPriorityTransfer == null) {
            var wishlistPriorityDetail = wishlistPriority.getLastDetail();
            var wishlistType = wishlistControl.getWishlistTypeTransfer(userVisit, wishlistPriorityDetail.getWishlistType());
            var wishlistPriorityName = wishlistPriorityDetail.getWishlistPriorityName();
            var isDefault = wishlistPriorityDetail.getIsDefault();
            var sortOrder = wishlistPriorityDetail.getSortOrder();
            var description = wishlistControl.getBestWishlistPriorityDescription(wishlistPriority, getLanguage(userVisit));
            
            wishlistPriorityTransfer = new WishlistPriorityTransfer(wishlistType, wishlistPriorityName, isDefault,
                    sortOrder, description);
            put(userVisit, wishlistPriority, wishlistPriorityTransfer);
        }
        
        return wishlistPriorityTransfer;
    }
    
}
