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

import com.echothree.model.control.associate.common.transfer.AssociateReferralTransfer;
import com.echothree.model.control.inventory.common.transfer.InventoryConditionTransfer;
import com.echothree.model.control.item.common.transfer.ItemTransfer;
import com.echothree.model.control.offer.common.transfer.OfferUseTransfer;
import com.echothree.model.control.order.common.transfer.OrderLineTransfer;
import com.echothree.model.control.uom.common.transfer.UnitOfMeasureTypeTransfer;

public class WishlistLineTransfer
        extends OrderLineTransfer<WishlistTransfer> {

    private OfferUseTransfer offerUse;
    private WishlistPriorityTransfer wishlistPriority;
    private AssociateReferralTransfer associateReferral;
    private String comment;

    /** Creates a new instance of WishlistLineTransfer */
    public WishlistLineTransfer(WishlistTransfer wishlist, Integer orderLineSequence, ItemTransfer item, InventoryConditionTransfer inventoryCondition,
            UnitOfMeasureTypeTransfer unitOfMeasureType, Long quantity, Long unformattedUnitAmount, String unitAmount, String description,
            OfferUseTransfer offerUse, WishlistPriorityTransfer wishlistPriority, AssociateReferralTransfer associateReferral, String comment) {
        super(wishlist, orderLineSequence, item, inventoryCondition, unitOfMeasureType, quantity, unformattedUnitAmount, unitAmount, description, null, null,
                null);

        this.offerUse = offerUse;
        this.wishlistPriority = wishlistPriority;
        this.associateReferral = associateReferral;
        this.comment = comment;
    }

    public WishlistTransfer getWishlist() {
        return this.order;
    }

    public void setWishlist(WishlistTransfer wishlist) {
        this.order = wishlist;
    }

    public Integer getWishlistLineSequence() {
        return getOrderLineSequence();
    }

    public void setWishlistLineSequence(Integer orderLineSequence) {
        this.orderLineSequence = orderLineSequence;
    }

    public OfferUseTransfer getOfferUse() {
        return offerUse;
    }

    public void setOfferUse(OfferUseTransfer offerUse) {
        this.offerUse = offerUse;
    }

    public WishlistPriorityTransfer getWishlistPriority() {
        return wishlistPriority;
    }

    public void setWishlistPriority(WishlistPriorityTransfer wishlistPriority) {
        this.wishlistPriority = wishlistPriority;
    }

    public AssociateReferralTransfer getAssociateReferral() {
        return associateReferral;
    }

    public void setAssociateReferral(AssociateReferralTransfer associateReferral) {
        this.associateReferral = associateReferral;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}