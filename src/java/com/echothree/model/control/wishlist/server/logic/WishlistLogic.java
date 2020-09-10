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

package com.echothree.model.control.wishlist.server.logic;

import com.echothree.model.control.item.common.ItemPriceTypes;
import com.echothree.model.control.offer.server.OfferControl;
import com.echothree.model.control.order.common.OrderRoleTypes;
import com.echothree.model.control.order.common.OrderTypes;
import com.echothree.model.control.order.server.control.OrderControl;
import com.echothree.model.control.order.server.logic.OrderLogic;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.sales.server.logic.SalesOrderLineLogic;
import com.echothree.model.control.wishlist.server.WishlistControl;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.associate.server.entity.AssociateReferral;
import com.echothree.model.data.inventory.server.entity.InventoryCondition;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.offer.server.entity.OfferItemFixedPrice;
import com.echothree.model.data.offer.server.entity.OfferItemPrice;
import com.echothree.model.data.offer.server.entity.OfferUse;
import com.echothree.model.data.offer.server.entity.Source;
import com.echothree.model.data.order.server.entity.Order;
import com.echothree.model.data.order.server.entity.OrderLine;
import com.echothree.model.data.order.server.entity.OrderType;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.party.server.entity.PartyDepartment;
import com.echothree.model.data.party.server.entity.PartyDivision;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.wishlist.server.entity.WishlistType;
import com.echothree.model.data.wishlist.server.entity.WishlistTypePriority;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;

public class WishlistLogic
        extends BaseLogic {
    
    private WishlistLogic() {
        super();
    }
    
    private static class WishlistLogicHolder {
        static WishlistLogic instance = new WishlistLogic();
    }
    
    public static WishlistLogic getInstance() {
        return WishlistLogicHolder.instance;
    }

    private OfferUse getOrderOfferUse(final UserVisit userVisit, final OfferUse offerUse, final Party companyParty) {
            var partyControl = (PartyControl)Session.getModelController(PartyControl.class);
        OfferUse orderOfferUse = userVisit.getOfferUse();

        if(orderOfferUse == null) {
            orderOfferUse = offerUse;
        } else {
            Party orderDepartmentParty = offerUse.getLastDetail().getOffer().getLastDetail().getDepartmentParty();
            PartyDepartment orderPartyDepartment = partyControl.getPartyDepartment(orderDepartmentParty);
            Party orderDivisionParty = orderPartyDepartment.getDivisionParty();
            PartyDivision orderPartyDivision = partyControl.getPartyDivision(orderDivisionParty);
            Party orderCompanyParty = orderPartyDivision.getCompanyParty();

            if(!orderCompanyParty.equals(companyParty)) {
                orderOfferUse = offerUse;
            }
        }

        return orderOfferUse;
    }

    public void createWishlistLine(final Session session, final ExecutionErrorAccumulator ema, final UserVisit userVisit, final Party party, final Source source,
            final OfferItemPrice offerItemPrice, final WishlistType wishlistType, final WishlistTypePriority wishlistTypePriority, final Long quantity,
            final String comment, final PartyPK createdBy) {
        Item item = offerItemPrice.getOfferItem().getItem();
        String itemPriceTypeName = item.getLastDetail().getItemPriceType().getItemPriceTypeName();

        if(itemPriceTypeName.equals(ItemPriceTypes.FIXED.name())) {
            var orderControl = (OrderControl)Session.getModelController(OrderControl.class);
            var partyControl = (PartyControl)Session.getModelController(PartyControl.class);
            var wishlistControl = (WishlistControl)Session.getModelController(WishlistControl.class);
            OrderLogic orderLogic = OrderLogic.getInstance();
            Currency currency = offerItemPrice.getCurrency();
            OfferUse offerUse = source.getLastDetail().getOfferUse();
            Party departmentParty = offerUse.getLastDetail().getOffer().getLastDetail().getDepartmentParty();
            PartyDepartment partyDepartment = partyControl.getPartyDepartment(departmentParty);
            Party divisionParty = partyDepartment.getDivisionParty();
            PartyDivision partyDivision = partyControl.getPartyDivision(divisionParty);
            Party companyParty = partyDivision.getCompanyParty();
            Order order = wishlistControl.getWishlist(companyParty, party, wishlistType, currency);

            if(order == null) {
                OrderType orderType = orderLogic.getOrderTypeByName(ema, OrderTypes.WISHLIST.name());

                if(!ema.hasExecutionErrors()) {
                    order = orderLogic.createOrder(ema, orderType, null, null, currency, null, null, null, null, null, null, null, null, null, null, null, createdBy);

                    if(!ema.hasExecutionErrors()) {
                        wishlistControl.createWishlist(order, getOrderOfferUse(userVisit, offerUse, companyParty), wishlistType, createdBy);

                        orderControl.createOrderRoleUsingNames(order, companyParty, OrderRoleTypes.BILL_FROM.name(), createdBy);
                        orderControl.createOrderRoleUsingNames(order, party, OrderRoleTypes.BILL_TO.name(), createdBy);
                    }
                }
            }

            if(!ema.hasExecutionErrors()) {
                InventoryCondition inventoryCondition = offerItemPrice.getInventoryCondition();
                UnitOfMeasureType unitOfMeasureType = offerItemPrice.getUnitOfMeasureType();
                OrderLine orderLine = wishlistControl.getWishlistLineByItemForUpdate(order, item, inventoryCondition, unitOfMeasureType);

                if(orderLine == null) {
                    var offerControl = (OfferControl)Session.getModelController(OfferControl.class);
                    OfferItemFixedPrice offerItemFixedPrice = offerControl.getOfferItemFixedPrice(offerItemPrice);
                    Long unitAmount = offerItemFixedPrice.getUnitPrice();
                    AssociateReferral associateReferral = userVisit.getAssociateReferral();

                    orderLine = SalesOrderLineLogic.getInstance().createOrderLine(session, ema, order, null, null, null, item, inventoryCondition, unitOfMeasureType,
                            quantity, unitAmount, null, null, null, null, createdBy);

                    if(!ema.hasExecutionErrors()) {
                        wishlistControl.createWishlistLine(orderLine, offerUse, wishlistTypePriority, associateReferral, comment, createdBy);
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
