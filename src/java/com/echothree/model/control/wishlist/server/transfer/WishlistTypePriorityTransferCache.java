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

import com.echothree.model.control.wishlist.common.transfer.WishlistTypePriorityTransfer;
import com.echothree.model.control.wishlist.common.transfer.WishlistTypeTransfer;
import com.echothree.model.control.wishlist.server.WishlistControl;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.wishlist.server.entity.WishlistTypePriority;
import com.echothree.model.data.wishlist.server.entity.WishlistTypePriorityDetail;

public class WishlistTypePriorityTransferCache
        extends BaseWishlistTransferCache<WishlistTypePriority, WishlistTypePriorityTransfer> {
    
    /** Creates a new instance of WishlistTypePriorityTransferCache */
    public WishlistTypePriorityTransferCache(UserVisit userVisit, WishlistControl wishlistControl) {
        super(userVisit, wishlistControl);
        
        setIncludeEntityInstance(true);
    }
    
    public WishlistTypePriorityTransfer getWishlistTypePriorityTransfer(WishlistTypePriority wishlistTypePriority) {
        WishlistTypePriorityTransfer wishlistTypePriorityTransfer = get(wishlistTypePriority);
        
        if(wishlistTypePriorityTransfer == null) {
            WishlistTypePriorityDetail wishlistTypePriorityDetail = wishlistTypePriority.getLastDetail();
            WishlistTypeTransfer wishlistType = wishlistControl.getWishlistTypeTransfer(userVisit, wishlistTypePriorityDetail.getWishlistType());
            String wishlistTypePriorityName = wishlistTypePriorityDetail.getWishlistTypePriorityName();
            Boolean isDefault = wishlistTypePriorityDetail.getIsDefault();
            Integer sortOrder = wishlistTypePriorityDetail.getSortOrder();
            String description = wishlistControl.getBestWishlistTypePriorityDescription(wishlistTypePriority, getLanguage());
            
            wishlistTypePriorityTransfer = new WishlistTypePriorityTransfer(wishlistType, wishlistTypePriorityName, isDefault,
                    sortOrder, description);
            put(wishlistTypePriority, wishlistTypePriorityTransfer);
        }
        
        return wishlistTypePriorityTransfer;
    }
    
}
