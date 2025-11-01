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

package com.echothree.model.control.sales.server.logic;

import com.echothree.model.control.associate.server.logic.AssociateReferralLogic;
import com.echothree.model.control.cancellationpolicy.common.CancellationKinds;
import com.echothree.model.control.cancellationpolicy.server.logic.CancellationPolicyLogic;
import com.echothree.model.control.inventory.common.exception.UnknownDefaultInventoryConditionException;
import com.echothree.model.control.inventory.server.control.InventoryControl;
import com.echothree.model.control.inventory.server.logic.InventoryConditionLogic;
import com.echothree.model.control.item.common.ItemPriceTypes;
import com.echothree.model.control.item.common.exception.UnknownDefaultItemUnitOfMeasureTypeException;
import com.echothree.model.control.item.common.workflow.ItemStatusConstants;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.item.server.logic.ItemLogic;
import com.echothree.model.control.offer.common.exception.UnknownOfferItemPriceException;
import com.echothree.model.control.offer.server.control.OfferItemControl;
import com.echothree.model.control.offer.server.logic.OfferItemLogic;
import com.echothree.model.control.offer.server.logic.SourceLogic;
import com.echothree.model.control.order.common.OrderTypes;
import com.echothree.model.control.order.server.logic.OrderLineLogic;
import com.echothree.model.control.returnpolicy.common.ReturnKinds;
import com.echothree.model.control.returnpolicy.server.logic.ReturnPolicyLogic;
import com.echothree.model.control.sales.common.exception.CurrentTimeAfterSalesOrderEndTimeException;
import com.echothree.model.control.sales.common.exception.CurrentTimeBeforeSalesOrderStartTimeException;
import com.echothree.model.control.sales.common.exception.ItemDiscontinuedException;
import com.echothree.model.control.sales.common.exception.QuantityAboveMaximumItemUnitCustomerTypeLimitException;
import com.echothree.model.control.sales.common.exception.QuantityAboveMaximumItemUnitLimitException;
import com.echothree.model.control.sales.common.exception.QuantityBelowMinimumItemUnitCustomerTypeLimitException;
import com.echothree.model.control.sales.common.exception.QuantityBelowMinimumItemUnitLimitException;
import com.echothree.model.control.sales.common.exception.UnitAmountAboveMaximumItemUnitPriceLimitException;
import com.echothree.model.control.sales.common.exception.UnitAmountAboveMaximumUnitPriceException;
import com.echothree.model.control.sales.common.exception.UnitAmountBelowMinimumItemUnitPriceLimitException;
import com.echothree.model.control.sales.common.exception.UnitAmountBelowMinimumUnitPriceException;
import com.echothree.model.control.sales.common.exception.UnitAmountNotMultipleOfUnitPriceIncrementException;
import com.echothree.model.control.sales.common.exception.UnitAmountRequiredException;
import com.echothree.model.control.sales.server.control.SalesOrderControl;
import com.echothree.model.control.uom.server.logic.UnitOfMeasureTypeLogic;
import com.echothree.model.control.workflow.server.logic.WorkflowStepLogic;
import com.echothree.model.data.associate.server.entity.AssociateReferral;
import com.echothree.model.data.batch.server.entity.Batch;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationPolicy;
import com.echothree.model.data.contact.server.entity.PartyContactMechanism;
import com.echothree.model.data.inventory.server.entity.InventoryCondition;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.offer.server.entity.Source;
import com.echothree.model.data.order.server.entity.Order;
import com.echothree.model.data.order.server.entity.OrderLine;
import com.echothree.model.data.order.server.entity.OrderShipmentGroup;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.returnpolicy.server.entity.ReturnPolicy;
import com.echothree.model.data.shipping.server.entity.ShippingMethod;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.message.DummyExecutionErrorAccumulator;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class SalesOrderLineLogic
        extends OrderLineLogic {

    protected SalesOrderLineLogic() {
        super();
    }

    public static SalesOrderLineLogic getInstance() {
        return CDI.current().select(SalesOrderLineLogic.class).get();
    }

    /**
     * Create a new Sales Order Line using appropriate defaults for Optional values when possible.
     * 
     * @param session Required.
     * @param eea Optional.
     * @param userVisit Required.
     * @param order Optional.
     * @param orderShipmentGroup Optional.
     * @param orderShipmentGroupSequence Optional.
     * @param orderLineSequence Optional.
     * @param parentOrderLine Optional.
     * @param partyContactMechanism Optional.
     * @param shippingMethod Optional.
     * @param item Required.
     * @param inventoryCondition Optional.
     * @param unitOfMeasureType Optional.
     * @param quantity Required.
     * @param unitAmount Optional for Items with a FIXED ItemPriceType, Required for VARIABLE.
     * @param description Optional.
     * @param taxable Optional.
     * @param source Optional.
     * @param associateReferral Optional.
     * @param createdByParty Required.
     * @return The newly created OrderLine, otherwise null if there was an error.
     */
    public OrderLine createSalesOrderLine(final Session session, final ExecutionErrorAccumulator eea, final UserVisit userVisit,
            Order order, OrderShipmentGroup orderShipmentGroup, final Integer orderShipmentGroupSequence, Integer orderLineSequence,
            final OrderLine parentOrderLine, PartyContactMechanism partyContactMechanism, ShippingMethod shippingMethod, final Item item,
            InventoryCondition inventoryCondition, UnitOfMeasureType unitOfMeasureType, final Long quantity, Long unitAmount,
            final String description, CancellationPolicy cancellationPolicy, ReturnPolicy returnPolicy, Boolean taxable, final Source source,
            final AssociateReferral associateReferral, final Party createdByParty) {
        var salesOrderLogic = SalesOrderLogic.getInstance();
        var createdByPartyPK = createdByParty.getPrimaryKey();
        OrderLine orderLine = null;

        // Create a new Sales Order if there was not one supplied. Defaults will be used for nearly all options.
        if(order == null) {
            order = SalesOrderLogic.getInstance().createSalesOrder(session, eea, userVisit, (Batch)null, null, null,
                    null, null, null, null, null, null, null, null, null, null, null, createdByParty);
        }

        salesOrderLogic.checkOrderAvailableForModification(session, eea, order, createdByPartyPK);

        if(eea == null || !eea.hasExecutionErrors()) {
            var salesOrderShipmentGroupLogic = SalesOrderShipmentGroupLogic.getInstance();
            var itemControl = Session.getModelController(ItemControl.class);
            var salesOrderControl = Session.getModelController(SalesOrderControl.class);
            var orderDetail = order.getLastDetail();
            var itemDetail = item.getLastDetail();
            var itemDeliveryType = itemDetail.getItemDeliveryType();
            var currency = orderDetail.getCurrency();
            var customerType = salesOrderLogic.getOrderBillToCustomerType(order);

            if(customerType != null && shippingMethod != null) {
                salesOrderShipmentGroupLogic.checkCustomerTypeShippingMethod(eea, customerType, shippingMethod);
            }

            if(eea == null || !eea.hasExecutionErrors()) {
                // ItemDeliveryType must be checked against the ContactMechanismType for the partyContactMechanism.

                // Check to see if an orderShipmentGroup was supplied. If it wasn't, try to get a default one for this order and itemDeliveryType.
                // If a default doesn't exist, then create one.
                if(orderShipmentGroup == null) {
                    var dummyExecutionErrorAccumulator = new DummyExecutionErrorAccumulator(); // No Execution Errors, don't throw Exceptions
                    orderShipmentGroup = SalesOrderShipmentGroupLogic.getInstance().getDefaultOrderShipmentGroup(dummyExecutionErrorAccumulator,
                            order, itemDeliveryType);

                    if(orderShipmentGroup == null) {
                        var holdUntilComplete = order.getLastDetail().getHoldUntilComplete();
                        var orderShipToParty = salesOrderLogic.getOrderShipToParty(order, true, createdByPartyPK);

                        // If partyContactMechanism is null, attempt to get from SHIP_TO party for the order.
                        // If no SHIP_TO party exists, try to copy from the BILL_TO party.

                        // TODO.

                        // Select an appropriate partyContactMechanism for the itemDeliveryType.

                        // TODO.

                        orderShipmentGroup = salesOrderShipmentGroupLogic.createSalesOrderShipmentGroup(session, eea, order,
                                orderShipmentGroupSequence, itemDeliveryType, true, partyContactMechanism,
                                shippingMethod, holdUntilComplete, createdByPartyPK);
                    } else {
                        var orderShipmentGroupDetail = orderShipmentGroup.getLastDetail();

                        partyContactMechanism = orderShipmentGroupDetail.getPartyContactMechanism();
                        shippingMethod = orderShipmentGroupDetail.getShippingMethod();
                    }
                }

                // If shippingMethod was specified, check to see if it can be used for this item and partyContactMechanism.

                // Check InventoryCondition.
                if(inventoryCondition == null) {
                    var inventoryControl = Session.getModelController(InventoryControl.class);

                    inventoryCondition = inventoryControl.getDefaultInventoryCondition();

                    if(inventoryControl == null) {
                        handleExecutionError(UnknownDefaultInventoryConditionException.class, eea, ExecutionErrors.UnknownDefaultInventoryCondition.name());
                    }
                }

                // Check UnitOfMeasureType.
                if(unitOfMeasureType == null) {
                    var itemUnitOfMeasureType = itemControl.getDefaultItemUnitOfMeasureType(item);

                    if(itemUnitOfMeasureType == null) {
                        handleExecutionError(UnknownDefaultItemUnitOfMeasureTypeException.class, eea, ExecutionErrors.UnknownDefaultItemUnitOfMeasureType.name());
                    } else {
                        unitOfMeasureType = itemUnitOfMeasureType.getUnitOfMeasureType();
                    }
                }

                // Verify the OfferItem exists.
                var offerUse = source == null ? null : source.getLastDetail().getOfferUse();
                if(offerUse == null) {
                    var salesOrder = salesOrderControl.getSalesOrder(order);

                    offerUse = salesOrder.getOfferUse();
                }

                var offerItem = OfferItemLogic.getInstance().getOfferItem(eea, offerUse.getLastDetail().getOffer(), item);

                // Verify unitAmount.
                if(offerItem != null) {
                    var offerItemControl = Session.getModelController(OfferItemControl.class);
                    var offerItemPrice = offerItemControl.getOfferItemPrice(offerItem, inventoryCondition, unitOfMeasureType, currency);

                    if(offerItemPrice == null) {
                        handleExecutionError(UnknownOfferItemPriceException.class, eea, ExecutionErrors.UnknownOfferItemPrice.name());
                    } else {
                        var itemPriceTypeName = itemDetail.getItemPriceType().getItemPriceTypeName();

                        if(itemPriceTypeName.equals(ItemPriceTypes.FIXED.name())) {
                            // We'll accept the supplied unitAmount as long as it passes the limit checks later on. Any enforcement of
                            // security should come in the UC.
                            if(unitAmount == null) {
                                var offerItemFixedPrice = offerItemControl.getOfferItemFixedPrice(offerItemPrice);

                                unitAmount = offerItemFixedPrice.getUnitPrice();
                            }
                        } else if(itemPriceTypeName.equals(ItemPriceTypes.VARIABLE.name())) {
                            if(unitAmount == null) {
                                handleExecutionError(UnitAmountRequiredException.class, eea, ExecutionErrors.UnitAmountRequired.name());
                            } else {
                                var offerItemVariablePrice = offerItemControl.getOfferItemVariablePrice(offerItemPrice);

                                if(unitAmount < offerItemVariablePrice.getMinimumUnitPrice()) {
                                    handleExecutionError(UnitAmountBelowMinimumUnitPriceException.class, eea, ExecutionErrors.UnitAmountBelowMinimumUnitPrice.name());
                                }

                                if(unitAmount > offerItemVariablePrice.getMaximumUnitPrice()) {
                                    handleExecutionError(UnitAmountAboveMaximumUnitPriceException.class, eea, ExecutionErrors.UnitAmountAboveMaximumUnitPrice.name());
                                }

                                if(unitAmount % offerItemVariablePrice.getUnitPriceIncrement() != 0) {
                                    handleExecutionError(UnitAmountNotMultipleOfUnitPriceIncrementException.class, eea, ExecutionErrors.UnitAmountNotMultipleOfUnitPriceIncrement.name());
                                }
                            }
                        } else {
                            handleExecutionError(UnknownOfferItemPriceException.class, eea, ExecutionErrors.UnknownItemPriceType.name(), itemPriceTypeName);
                        }
                    }
                }

                // Check ItemUnitPriceLimits.
                if(unitAmount != null) {
                    var itemUnitPriceLimit = itemControl.getItemUnitPriceLimit(item, inventoryCondition, unitOfMeasureType, currency);

                    // This isn't required. If it is missing, no check is performed.
                    if(itemUnitPriceLimit != null) {
                        var minimumUnitPrice = itemUnitPriceLimit.getMinimumUnitPrice();
                        var maximumUnitPrice = itemUnitPriceLimit.getMaximumUnitPrice();

                        if(minimumUnitPrice != null && unitAmount < minimumUnitPrice) {
                            handleExecutionError(UnitAmountBelowMinimumItemUnitPriceLimitException.class, eea, ExecutionErrors.UnitAmountBelowMinimumItemUnitPriceLimit.name());
                        }

                        if(maximumUnitPrice != null && unitAmount > maximumUnitPrice) {
                            handleExecutionError(UnitAmountAboveMaximumItemUnitPriceLimitException.class, eea, ExecutionErrors.UnitAmountAboveMaximumItemUnitPriceLimit.name());
                        }
                    }
                }

                // Check quantity being ordered and make sure that it's within acceptible limits. Both ItemUnitLimits and ItemUnitCustomerTypeLimits.
                if(inventoryCondition != null && unitOfMeasureType != null) {
                    var itemUnitLimit = itemControl.getItemUnitLimit(item, inventoryCondition, unitOfMeasureType);

                    if(itemUnitLimit != null) {
                        var minimumQuantity = itemUnitLimit.getMinimumQuantity();
                        var maximumQuantity = itemUnitLimit.getMaximumQuantity();

                        if(minimumQuantity != null && quantity < minimumQuantity) {
                            handleExecutionError(QuantityBelowMinimumItemUnitLimitException.class, eea, ExecutionErrors.QuantityBelowMinimumItemUnitLimit.name());
                        }

                        if(maximumQuantity != null && quantity > maximumQuantity) {
                            handleExecutionError(QuantityAboveMaximumItemUnitLimitException.class, eea, ExecutionErrors.QuantityAboveMaximumItemUnitLimit.name());
                        }
                    }

                    if(customerType != null) {
                        var itemUnitCustomerTypeLimit = itemControl.getItemUnitCustomerTypeLimit(item, inventoryCondition, unitOfMeasureType, customerType);

                        if(itemUnitCustomerTypeLimit != null) {
                            var minimumQuantity = itemUnitCustomerTypeLimit.getMinimumQuantity();
                            var maximumQuantity = itemUnitCustomerTypeLimit.getMaximumQuantity();

                            if(minimumQuantity != null && quantity < minimumQuantity) {
                                handleExecutionError(QuantityBelowMinimumItemUnitCustomerTypeLimitException.class, eea, ExecutionErrors.QuantityBelowMinimumItemUnitCustomerTypeLimit.name());
                            }

                            if(maximumQuantity != null && quantity > maximumQuantity) {
                                handleExecutionError(QuantityAboveMaximumItemUnitCustomerTypeLimitException.class, eea, ExecutionErrors.QuantityAboveMaximumItemUnitCustomerTypeLimit.name());
                            }
                        }
                    }
                }

                // Check Item's SalesOrderStartTime and SalesOrderEndTime.
                var salesOrderStartTime = itemDetail.getSalesOrderStartTime();
                if(salesOrderStartTime != null && session.START_TIME < salesOrderStartTime) {
                    handleExecutionError(CurrentTimeBeforeSalesOrderStartTimeException.class, eea, ExecutionErrors.CurrentTimeBeforeSalesOrderStartTime.name());
                }

                var salesOrderEndTime = itemDetail.getSalesOrderEndTime();
                if(salesOrderEndTime != null && session.START_TIME > salesOrderEndTime) {
                    handleExecutionError(CurrentTimeAfterSalesOrderEndTimeException.class, eea, ExecutionErrors.CurrentTimeAfterSalesOrderEndTime.name());
                }

                // Check Item's status.
                if(!WorkflowStepLogic.getInstance().isEntityInWorkflowSteps(eea, ItemStatusConstants.Workflow_ITEM_STATUS, item,
                        ItemStatusConstants.WorkflowStep_ITEM_STATUS_DISCONTINUED).isEmpty()) {
                    handleExecutionError(ItemDiscontinuedException.class, eea, ExecutionErrors.ItemDiscontinued.name(), item.getLastDetail().getItemName());
                }

                // Create the line.
                if(eea == null || !eea.hasExecutionErrors()) {
                    // If a specific CancellationPolicy was specified, use that. Otherwise, try to use the one for the Item.
                    // If that's null, we'll leave it null for the OrderLine, which indicates that we should fall back to the
                    // one on the Order if it's ever needed.
                    if(cancellationPolicy == null) {
                        cancellationPolicy = itemDetail.getCancellationPolicy();
                    }

                    // If a specific ReturnPolicy was specified, use that. Otherwise, try to use the one for the Item.
                    // If that's null, we'll leave it null for the OrderLine, which indicates that we should fall back to the
                    // one on the Order if it's ever needed.
                    if(returnPolicy == null) {
                        returnPolicy = itemDetail.getReturnPolicy();
                    }

                    // If there was no taxable flag passed in, then get the taxable value from the Item. If that is true,
                    // the taxable flag from the Order will override it.
                    if(taxable == null) {
                        // taxable = itemDetail.getTaxable();
                        taxable = true; // TODO: This needs to consider the GeoCode-aware taxing system.
                        
                        if(taxable) {
                            taxable = orderDetail.getTaxable();
                        }
                    }

                    orderLine = createOrderLine(session, eea, order, orderLineSequence, parentOrderLine, orderShipmentGroup, item, inventoryCondition,
                            unitOfMeasureType, quantity, unitAmount, description, cancellationPolicy, returnPolicy, taxable, createdByPartyPK);

                    if(eea == null || !eea.hasExecutionErrors()) {
                        salesOrderControl.createSalesOrderLine(orderLine, offerUse, associateReferral, createdByPartyPK);
                    }
                }
            }
        }
        
        return orderLine;
    }

    public OrderLine createOrderLine(final Session session, final ExecutionErrorAccumulator eea, final UserVisit userVisit,
            final String orderName, final String itemName, final String inventoryConditionName, final String cancellationPolicyName,
            final String returnPolicyName, final String unitOfMeasureTypeName, final String sourceName,
            final String strOrderLineSequence, final String strQuantity, final String strUnitAmount, final String description,
            final String strTaxable, final Party createdByParty) {
        var order = orderName == null ? null : SalesOrderLogic.getInstance().getOrderByName(eea, orderName);
        var item = ItemLogic.getInstance().getItemByNameThenAlias(eea, itemName);
        var inventoryCondition = inventoryConditionName == null ? null : InventoryConditionLogic.getInstance().getInventoryConditionByName(eea, inventoryConditionName);
        var cancellationPolicy = cancellationPolicyName == null ? null : CancellationPolicyLogic.getInstance().getCancellationPolicyByName(eea, CancellationKinds.CUSTOMER_CANCELLATION.name(), cancellationPolicyName);
        var returnPolicy = returnPolicyName == null ? null : ReturnPolicyLogic.getInstance().getReturnPolicyByName(eea, ReturnKinds.CUSTOMER_RETURN.name(), returnPolicyName);
        var source = sourceName == null ? null : SourceLogic.getInstance().getSourceByName(eea, sourceName);
        OrderLine orderLine = null;

        if(!eea.hasExecutionErrors()) {
            var itemDetail = item.getLastDetail();
            var unitOfMeasureKind = itemDetail.getUnitOfMeasureKind();
            var unitOfMeasureType = unitOfMeasureTypeName == null ? null : UnitOfMeasureTypeLogic.getInstance().getUnitOfMeasureTypeByName(eea, unitOfMeasureKind, unitOfMeasureTypeName);

            if(!eea.hasExecutionErrors()) {
                var orderLineSequence = strOrderLineSequence == null ? null : Integer.valueOf(strOrderLineSequence);
                var quantity = Long.valueOf(strQuantity);
                var unitAmount = strUnitAmount == null ? null : Long.valueOf(strUnitAmount);
                var taxable = strTaxable == null ? null : Boolean.valueOf(strTaxable);
                var associateReferral = AssociateReferralLogic.getInstance().getAssociateReferral(session, userVisit);

                orderLine = createSalesOrderLine(session, eea, userVisit, order, null,
                        null, orderLineSequence, null, null, null, item, inventoryCondition, unitOfMeasureType, quantity,
                        unitAmount, description, cancellationPolicy, returnPolicy, taxable, source, associateReferral,
                        createdByParty);
            }
        }

        return orderLine;
    }

    public OrderLine getOrderLineByName(final ExecutionErrorAccumulator eea, final String orderName, final String orderLineSequence) {
        return getOrderLineByName(eea, OrderTypes.SALES_ORDER.name(), orderName, orderLineSequence);
    }

    public OrderLine getOrderLineByNameForUpdate(final ExecutionErrorAccumulator eea, final String orderName, final String orderLineSequence) {
        return getOrderLineByNameForUpdate(eea, OrderTypes.SALES_ORDER.name(), orderName, orderLineSequence);
    }

}
