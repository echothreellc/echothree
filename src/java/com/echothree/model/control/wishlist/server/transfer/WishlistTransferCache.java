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

import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.offer.server.control.OfferUseControl;
import com.echothree.model.control.order.server.control.OrderTypeControl;
import com.echothree.model.control.wishlist.common.transfer.WishlistTransfer;
import com.echothree.model.control.wishlist.server.control.WishlistControl;
import com.echothree.model.data.order.server.entity.Order;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class WishlistTransferCache
        extends BaseWishlistTransferCache<Order, WishlistTransfer> {
    
    AccountingControl accountingControl = Session.getModelController(AccountingControl.class);
    OfferUseControl offerUseControl = Session.getModelController(OfferUseControl.class);
    OrderTypeControl orderTypeControl = Session.getModelController(OrderTypeControl.class);
    
    /** Creates a new instance of WishlistTransferCache */
    public WishlistTransferCache(WishlistControl wishlistControl) {
        super(wishlistControl);
        
        setIncludeEntityInstance(true);
    }
    
    public WishlistTransfer getWishlistTransfer(Order order) {
        var wishlistTransfer = get(order);
        
        if(wishlistTransfer == null) {
            var orderDetail = order.getActiveDetail();
            var wishlist = wishlistControl.getWishlist(order);
            var orderType = orderTypeControl.getOrderTypeTransfer(userVisit, orderDetail.getOrderType());
            var orderName = orderDetail.getOrderName();
            var currency = accountingControl.getCurrencyTransfer(userVisit, orderDetail.getCurrency());
            var offerUse = offerUseControl.getOfferUseTransfer(userVisit, wishlist.getOfferUse());
            var wishlistType = wishlistControl.getWishlistTypeTransfer(userVisit, wishlist.getWishlistType());
            
            wishlistTransfer = new WishlistTransfer(orderType, orderName, currency, offerUse, wishlistType);
            put(userVisit, order, wishlistTransfer);
        }
        
        return wishlistTransfer;
    }
    
}
