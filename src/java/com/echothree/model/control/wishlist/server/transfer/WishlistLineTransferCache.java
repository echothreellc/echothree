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

package com.echothree.model.control.wishlist.server.transfer;

import com.echothree.model.control.associate.common.transfer.AssociateReferralTransfer;
import com.echothree.model.control.inventory.server.control.InventoryControl;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.offer.server.control.OfferUseControl;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.control.wishlist.common.transfer.WishlistLineTransfer;
import com.echothree.model.control.wishlist.server.control.WishlistControl;
import com.echothree.model.data.order.server.entity.OrderLine;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.string.AmountUtils;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class WishlistLineTransferCache
        extends BaseWishlistTransferCache<OrderLine, WishlistLineTransfer> {
    
    InventoryControl inventoryControl = Session.getModelController(InventoryControl.class);
    ItemControl itemControl = Session.getModelController(ItemControl.class);
    OfferUseControl offerUseControl = Session.getModelController(OfferUseControl.class);
    UomControl uomControl = Session.getModelController(UomControl.class);
    WishlistControl wishlistControl = Session.getModelController(WishlistControl.class);
    
    /** Creates a new instance of WishlistLineTransferCache */
    protected WishlistLineTransferCache() {
        super();
        
        setIncludeEntityInstance(true);
    }
    
    public WishlistLineTransfer getWishlistLineTransfer(UserVisit userVisit, OrderLine orderLine) {
        var wishlistLineTransfer = get(orderLine);
        
        if(wishlistLineTransfer == null) {
            var orderLineDetail = orderLine.getLastDetail();
            var wishlistLine = wishlistControl.getWishlistLine(orderLine);
            var order = orderLineDetail.getOrder();
            var wishlist = wishlistControl.getWishlistTransfer(userVisit, order);
            var orderLineSequence = orderLineDetail.getOrderLineSequence();
            var item = itemControl.getItemTransfer(userVisit, orderLineDetail.getItem());
            var inventoryCondition = inventoryControl.getInventoryConditionTransfer(userVisit, orderLineDetail.getInventoryCondition());
            var unitOfMeasureType = uomControl.getUnitOfMeasureTypeTransfer(userVisit, orderLineDetail.getUnitOfMeasureType());
            var quantity = orderLineDetail.getQuantity();
            var unformattedUnitAmount = orderLineDetail.getUnitAmount();
            var unitAmount = AmountUtils.getInstance().formatPriceLine(order.getLastDetail().getCurrency(), unformattedUnitAmount);
            var description = orderLineDetail.getDescription();
            var offerUse = offerUseControl.getOfferUseTransfer(userVisit, wishlistLine.getOfferUse());
            var wishlistPriority = wishlistControl.getWishlistPriorityTransfer(userVisit, wishlistLine.getWishlistPriority());
            AssociateReferralTransfer associateReferral = null; // TODO
            var comment = wishlistLine.getComment();
            
            wishlistLineTransfer = new WishlistLineTransfer(wishlist, orderLineSequence, item, inventoryCondition, unitOfMeasureType, quantity,
                    unformattedUnitAmount, unitAmount, description, offerUse, wishlistPriority, associateReferral, comment);
            put(userVisit, orderLine, wishlistLineTransfer);
        }
        
        return wishlistLineTransfer;
    }
    
}
