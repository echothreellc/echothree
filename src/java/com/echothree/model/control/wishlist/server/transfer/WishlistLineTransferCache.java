// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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
import com.echothree.model.control.inventory.common.transfer.InventoryConditionTransfer;
import com.echothree.model.control.inventory.server.control.InventoryControl;
import com.echothree.model.control.item.common.transfer.ItemTransfer;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.offer.common.transfer.OfferUseTransfer;
import com.echothree.model.control.offer.server.control.OfferUseControl;
import com.echothree.model.control.uom.common.transfer.UnitOfMeasureTypeTransfer;
import com.echothree.model.control.uom.server.UomControl;
import com.echothree.model.control.wishlist.common.transfer.WishlistLineTransfer;
import com.echothree.model.control.wishlist.common.transfer.WishlistTransfer;
import com.echothree.model.control.wishlist.common.transfer.WishlistTypePriorityTransfer;
import com.echothree.model.control.wishlist.server.WishlistControl;
import com.echothree.model.data.order.server.entity.Order;
import com.echothree.model.data.order.server.entity.OrderLine;
import com.echothree.model.data.order.server.entity.OrderLineDetail;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.wishlist.server.entity.WishlistLine;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.string.AmountUtils;

public class WishlistLineTransferCache
        extends BaseWishlistTransferCache<OrderLine, WishlistLineTransfer> {
    
    InventoryControl inventoryControl = (InventoryControl)Session.getModelController(InventoryControl.class);
    ItemControl itemControl = (ItemControl)Session.getModelController(ItemControl.class);
    OfferUseControl offerUseControl = (OfferUseControl)Session.getModelController(OfferUseControl.class);
    UomControl uomControl = (UomControl)Session.getModelController(UomControl.class);
    
    /** Creates a new instance of WishlistLineTransferCache */
    public WishlistLineTransferCache(UserVisit userVisit, WishlistControl wishlistControl) {
        super(userVisit, wishlistControl);
        
        setIncludeEntityInstance(true);
    }
    
    public WishlistLineTransfer getWishlistLineTransfer(OrderLine orderLine) {
        WishlistLineTransfer wishlistLineTransfer = get(orderLine);
        
        if(wishlistLineTransfer == null) {
            OrderLineDetail orderLineDetail = orderLine.getLastDetail();
            WishlistLine wishlistLine = wishlistControl.getWishlistLine(orderLine);
            Order order = orderLineDetail.getOrder();
            WishlistTransfer wishlist = wishlistControl.getWishlistTransfer(userVisit, order);
            Integer orderLineSequence = orderLineDetail.getOrderLineSequence();
            ItemTransfer item = itemControl.getItemTransfer(userVisit, orderLineDetail.getItem());
            InventoryConditionTransfer inventoryCondition = inventoryControl.getInventoryConditionTransfer(userVisit, orderLineDetail.getInventoryCondition());
            UnitOfMeasureTypeTransfer unitOfMeasureType = uomControl.getUnitOfMeasureTypeTransfer(userVisit, orderLineDetail.getUnitOfMeasureType());
            Long quantity = orderLineDetail.getQuantity();
            Long unformattedUnitAmount = orderLineDetail.getUnitAmount();
            String unitAmount = AmountUtils.getInstance().formatPriceLine(order.getLastDetail().getCurrency(), unformattedUnitAmount);
            String description = orderLineDetail.getDescription();
            OfferUseTransfer offerUse = offerUseControl.getOfferUseTransfer(userVisit, wishlistLine.getOfferUse());
            WishlistTypePriorityTransfer wishlistTypePriority = wishlistControl.getWishlistTypePriorityTransfer(userVisit, wishlistLine.getWishlistTypePriority());
            AssociateReferralTransfer associateReferral = null; // TODO
            String comment = wishlistLine.getComment();
            
            wishlistLineTransfer = new WishlistLineTransfer(wishlist, orderLineSequence, item, inventoryCondition, unitOfMeasureType, quantity,
                    unformattedUnitAmount, unitAmount, description, offerUse, wishlistTypePriority, associateReferral, comment);
            put(orderLine, wishlistLineTransfer);
        }
        
        return wishlistLineTransfer;
    }
    
}
