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

import com.echothree.model.control.accounting.common.transfer.CurrencyTransfer;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.offer.common.transfer.OfferUseTransfer;
import com.echothree.model.control.offer.server.control.OfferUseControl;
import com.echothree.model.control.order.common.transfer.OrderTypeTransfer;
import com.echothree.model.control.order.server.control.OrderTypeControl;
import com.echothree.model.control.wishlist.common.transfer.WishlistTransfer;
import com.echothree.model.control.wishlist.common.transfer.WishlistTypeTransfer;
import com.echothree.model.control.wishlist.server.control.WishlistControl;
import com.echothree.model.data.order.server.entity.Order;
import com.echothree.model.data.order.server.entity.OrderDetail;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.wishlist.server.entity.Wishlist;
import com.echothree.util.server.persistence.Session;

public class WishlistTransferCache
        extends BaseWishlistTransferCache<Order, WishlistTransfer> {
    
    AccountingControl accountingControl = Session.getModelController(AccountingControl.class);
    OfferUseControl offerUseControl = Session.getModelController(OfferUseControl.class);
    OrderTypeControl orderTypeControl = Session.getModelController(OrderTypeControl.class);
    
    /** Creates a new instance of WishlistTransferCache */
    public WishlistTransferCache(UserVisit userVisit, WishlistControl wishlistControl) {
        super(userVisit, wishlistControl);
        
        setIncludeEntityInstance(true);
    }
    
    public WishlistTransfer getWishlistTransfer(Order order) {
        WishlistTransfer wishlistTransfer = get(order);
        
        if(wishlistTransfer == null) {
            OrderDetail orderDetail = order.getActiveDetail();
            Wishlist wishlist = wishlistControl.getWishlist(order);
            OrderTypeTransfer orderType = orderTypeControl.getOrderTypeTransfer(userVisit, orderDetail.getOrderType());
            String orderName = orderDetail.getOrderName();
            CurrencyTransfer currency = accountingControl.getCurrencyTransfer(userVisit, orderDetail.getCurrency());
            OfferUseTransfer offerUse = offerUseControl.getOfferUseTransfer(userVisit, wishlist.getOfferUse());
            WishlistTypeTransfer wishlistType = wishlistControl.getWishlistTypeTransfer(userVisit, wishlist.getWishlistType());
            
            wishlistTransfer = new WishlistTransfer(orderType, orderName, currency, offerUse, wishlistType);
            put(order, wishlistTransfer);
        }
        
        return wishlistTransfer;
    }
    
}
