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

package com.echothree.model.control.wishlist.server.logic;

import com.echothree.model.control.item.common.ItemPriceTypes;
import com.echothree.model.control.offer.server.control.OfferItemControl;
import com.echothree.model.control.order.common.OrderRoleTypes;
import com.echothree.model.control.order.common.OrderTypes;
import com.echothree.model.control.order.server.control.OrderRoleControl;
import com.echothree.model.control.order.server.logic.OrderLogic;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.sales.server.logic.SalesOrderLineLogic;
import com.echothree.model.control.wishlist.server.control.WishlistControl;
import com.echothree.model.data.offer.server.entity.OfferItemPrice;
import com.echothree.model.data.offer.server.entity.OfferUse;
import com.echothree.model.data.offer.server.entity.Source;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.wishlist.server.entity.WishlistPriority;
import com.echothree.model.data.wishlist.server.entity.WishlistType;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class WishlistLogic
        extends BaseLogic {

    protected WishlistLogic() {
        super();
    }

    public static WishlistLogic getInstance() {
        return CDI.current().select(WishlistLogic.class).get();
    }

    private OfferUse getOrderOfferUse(final UserVisit userVisit, final OfferUse offerUse, final Party companyParty) {
            var partyControl = Session.getModelController(PartyControl.class);
        var orderOfferUse = userVisit.getOfferUse();

        if(orderOfferUse == null) {
            orderOfferUse = offerUse;
        } else {
            var orderDepartmentParty = offerUse.getLastDetail().getOffer().getLastDetail().getDepartmentParty();
            var orderPartyDepartment = partyControl.getPartyDepartment(orderDepartmentParty);
            var orderDivisionParty = orderPartyDepartment.getDivisionParty();
            var orderPartyDivision = partyControl.getPartyDivision(orderDivisionParty);
            var orderCompanyParty = orderPartyDivision.getCompanyParty();

            if(!orderCompanyParty.equals(companyParty)) {
                orderOfferUse = offerUse;
            }
        }

        return orderOfferUse;
    }

    public void createWishlistLine(final Session session, final ExecutionErrorAccumulator ema, final UserVisit userVisit, final Party party, final Source source,
            final OfferItemPrice offerItemPrice, final WishlistType wishlistType, final WishlistPriority wishlistPriority, final Long quantity,
            final String comment, final PartyPK createdBy) {
        var item = offerItemPrice.getOfferItem().getItem();
        var itemPriceTypeName = item.getLastDetail().getItemPriceType().getItemPriceTypeName();

        if(itemPriceTypeName.equals(ItemPriceTypes.FIXED.name())) {
            var partyControl = Session.getModelController(PartyControl.class);
            var wishlistControl = Session.getModelController(WishlistControl.class);
            var orderLogic = OrderLogic.getInstance();
            var currency = offerItemPrice.getCurrency();
            var offerUse = source.getLastDetail().getOfferUse();
            var departmentParty = offerUse.getLastDetail().getOffer().getLastDetail().getDepartmentParty();
            var partyDepartment = partyControl.getPartyDepartment(departmentParty);
            var divisionParty = partyDepartment.getDivisionParty();
            var partyDivision = partyControl.getPartyDivision(divisionParty);
            var companyParty = partyDivision.getCompanyParty();
            var order = wishlistControl.getWishlist(companyParty, party, wishlistType, currency);

            if(order == null) {
                var orderType = orderLogic.getOrderTypeByName(ema, OrderTypes.WISHLIST.name());

                if(!ema.hasExecutionErrors()) {
                    order = orderLogic.createOrder(ema, orderType, null, null, currency, null, null, null, null, null, null, null, null, null, null, null, createdBy);

                    if(!ema.hasExecutionErrors()) {
                        var orderRoleControl = Session.getModelController(OrderRoleControl.class);

                        wishlistControl.createWishlist(order, getOrderOfferUse(userVisit, offerUse, companyParty), wishlistType, createdBy);

                        orderRoleControl.createOrderRoleUsingNames(order, companyParty, OrderRoleTypes.BILL_FROM.name(), createdBy);
                        orderRoleControl.createOrderRoleUsingNames(order, party, OrderRoleTypes.BILL_TO.name(), createdBy);
                    }
                }
            }

            if(!ema.hasExecutionErrors()) {
                var inventoryCondition = offerItemPrice.getInventoryCondition();
                var unitOfMeasureType = offerItemPrice.getUnitOfMeasureType();
                var orderLine = wishlistControl.getWishlistLineByItemForUpdate(order, item, inventoryCondition, unitOfMeasureType);

                if(orderLine == null) {
                    var offerItemControl = Session.getModelController(OfferItemControl.class);
                    var offerItemFixedPrice = offerItemControl.getOfferItemFixedPrice(offerItemPrice);
                    var unitAmount = offerItemFixedPrice.getUnitPrice();
                    var associateReferral = userVisit.getAssociateReferral();

                    orderLine = SalesOrderLineLogic.getInstance().createOrderLine(session, ema, order, null, null, null, item, inventoryCondition, unitOfMeasureType,
                            quantity, unitAmount, null, null, null, null, createdBy);

                    if(!ema.hasExecutionErrors()) {
                        wishlistControl.createWishlistLine(orderLine, offerUse, wishlistPriority, associateReferral, comment, createdBy);
                    }
                } else {
                    // TODO: add to wishlist quantity?
                }
            }
        } else {
            addExecutionError(ema, ExecutionErrors.InvalidItemPriceType.name(), itemPriceTypeName);
        }
    }

    public void deleteWishlistLine() {
        // TODO
        // deleteWishlistLine(orderLine);
        // deleteOrderLine(orderLine);
    }

}
