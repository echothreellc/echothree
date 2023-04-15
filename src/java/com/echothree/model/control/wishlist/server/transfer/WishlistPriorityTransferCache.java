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

import com.echothree.model.control.wishlist.common.transfer.WishlistPriorityTransfer;
import com.echothree.model.control.wishlist.common.transfer.WishlistTypeTransfer;
import com.echothree.model.control.wishlist.server.control.WishlistControl;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.wishlist.server.entity.WishlistPriority;
import com.echothree.model.data.wishlist.server.entity.WishlistPriorityDetail;

public class WishlistPriorityTransferCache
        extends BaseWishlistTransferCache<WishlistPriority, WishlistPriorityTransfer> {
    
    /** Creates a new instance of WishlistPriorityTransferCache */
    public WishlistPriorityTransferCache(UserVisit userVisit, WishlistControl wishlistControl) {
        super(userVisit, wishlistControl);
        
        setIncludeEntityInstance(true);
    }
    
    public WishlistPriorityTransfer getWishlistPriorityTransfer(WishlistPriority wishlistPriority) {
        WishlistPriorityTransfer wishlistPriorityTransfer = get(wishlistPriority);
        
        if(wishlistPriorityTransfer == null) {
            WishlistPriorityDetail wishlistPriorityDetail = wishlistPriority.getLastDetail();
            WishlistTypeTransfer wishlistType = wishlistControl.getWishlistTypeTransfer(userVisit, wishlistPriorityDetail.getWishlistType());
            String wishlistPriorityName = wishlistPriorityDetail.getWishlistPriorityName();
            Boolean isDefault = wishlistPriorityDetail.getIsDefault();
            Integer sortOrder = wishlistPriorityDetail.getSortOrder();
            String description = wishlistControl.getBestWishlistPriorityDescription(wishlistPriority, getLanguage());
            
            wishlistPriorityTransfer = new WishlistPriorityTransfer(wishlistType, wishlistPriorityName, isDefault,
                    sortOrder, description);
            put(wishlistPriority, wishlistPriorityTransfer);
        }
        
        return wishlistPriorityTransfer;
    }
    
}
