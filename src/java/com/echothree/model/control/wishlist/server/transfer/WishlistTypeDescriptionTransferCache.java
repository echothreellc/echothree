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

import com.echothree.model.control.wishlist.common.transfer.WishlistTypeDescriptionTransfer;
import com.echothree.model.control.wishlist.server.control.WishlistControl;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.wishlist.server.entity.WishlistTypeDescription;
import com.echothree.util.server.persistence.Session;

public class WishlistTypeDescriptionTransferCache
        extends BaseWishlistDescriptionTransferCache<WishlistTypeDescription, WishlistTypeDescriptionTransfer> {

    WishlistControl wishlistControl = Session.getModelController(WishlistControl.class);

    /** Creates a new instance of WishlistTypeDescriptionTransferCache */
    public WishlistTypeDescriptionTransferCache() {
        super();
    }
    
    public WishlistTypeDescriptionTransfer getWishlistTypeDescriptionTransfer(UserVisit userVisit, WishlistTypeDescription wishlistTypeDescription) {
        var wishlistTypeDescriptionTransfer = get(wishlistTypeDescription);
        
        if(wishlistTypeDescriptionTransfer == null) {
            var wishlistTypeTransfer = wishlistControl.getWishlistTypeTransfer(userVisit, wishlistTypeDescription.getWishlistType());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, wishlistTypeDescription.getLanguage());
            
            wishlistTypeDescriptionTransfer = new WishlistTypeDescriptionTransfer(languageTransfer, wishlistTypeTransfer, wishlistTypeDescription.getDescription());
            put(userVisit, wishlistTypeDescription, wishlistTypeDescriptionTransfer);
        }
        
        return wishlistTypeDescriptionTransfer;
    }
    
}
