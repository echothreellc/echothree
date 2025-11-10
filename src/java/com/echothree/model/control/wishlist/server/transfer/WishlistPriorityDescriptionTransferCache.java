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

import com.echothree.model.control.wishlist.common.transfer.WishlistPriorityDescriptionTransfer;
import com.echothree.model.control.wishlist.server.control.WishlistControl;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.wishlist.server.entity.WishlistPriorityDescription;

public class WishlistPriorityDescriptionTransferCache
        extends BaseWishlistDescriptionTransferCache<WishlistPriorityDescription, WishlistPriorityDescriptionTransfer> {
    
    /** Creates a new instance of WishlistPriorityDescriptionTransferCache */
    public WishlistPriorityDescriptionTransferCache(UserVisit userVisit, WishlistControl wishlistControl) {
        super(userVisit, wishlistControl);
    }
    
    public WishlistPriorityDescriptionTransfer getWishlistPriorityDescriptionTransfer(WishlistPriorityDescription wishlistPriorityDescription) {
        var wishlistPriorityDescriptionTransfer = get(wishlistPriorityDescription);
        
        if(wishlistPriorityDescriptionTransfer == null) {
            var wishlistPriorityTransfer = wishlistControl.getWishlistPriorityTransfer(userVisit, wishlistPriorityDescription.getWishlistPriority());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, wishlistPriorityDescription.getLanguage());
            
            wishlistPriorityDescriptionTransfer = new WishlistPriorityDescriptionTransfer(languageTransfer, wishlistPriorityTransfer, wishlistPriorityDescription.getDescription());
            put(userVisit, wishlistPriorityDescription, wishlistPriorityDescriptionTransfer);
        }
        
        return wishlistPriorityDescriptionTransfer;
    }
    
}
