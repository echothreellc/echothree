// --------------------------------------------------------------------------------
// Copyright 2002-2026 Echo Three, LLC
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

package com.echothree.model.control.wishlist.common.transfer;

import com.echothree.model.control.accounting.common.transfer.CurrencyTransfer;
import com.echothree.model.control.offer.common.transfer.OfferUseTransfer;
import com.echothree.model.control.order.common.transfer.OrderTransfer;
import com.echothree.model.control.order.common.transfer.OrderTypeTransfer;

public class WishlistTransfer
        extends OrderTransfer {

    private OfferUseTransfer offerUse;
    private WishlistTypeTransfer wishlistType;

    /** Creates a new instance of WishlistTransfer */
    public WishlistTransfer(OrderTypeTransfer orderType, String orderName, CurrencyTransfer currency, OfferUseTransfer offerUse,
            WishlistTypeTransfer wishlistType) {
        super(orderType, orderName, null, currency, null, null, null, null, null, null, null, null, null, null);

        this.offerUse = offerUse;
        this.wishlistType = wishlistType;
    }

    public String getWishlistName() {
        return this.orderName;
    }

    public void setWishlistName(String wishlistName) {
        this.orderName = wishlistName;
    }

    public OfferUseTransfer getOfferUse() {
        return offerUse;
    }

    public void setOfferUse(OfferUseTransfer offerUse) {
        this.offerUse = offerUse;
    }

    public WishlistTypeTransfer getWishlistType() {
        return wishlistType;
    }

    public void setWishlistType(WishlistTypeTransfer wishlistType) {
        this.wishlistType = wishlistType;
    }
}