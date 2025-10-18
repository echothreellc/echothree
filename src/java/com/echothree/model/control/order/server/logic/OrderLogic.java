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

package com.echothree.model.control.order.server.logic;

import com.echothree.model.control.order.common.exception.CannotSpecifyPartyPaymentMethodException;
import com.echothree.model.control.order.common.exception.CannotSpecifyPaymentMethodAndPartyPaymentMethodException;
import com.echothree.model.control.order.common.exception.CannotSpecifyWasPresentException;
import com.echothree.model.control.order.common.exception.DuplicateOrderPaymentPreferenceSequenceException;
import com.echothree.model.control.order.common.exception.MustSpecifyPartyPaymentMethodException;
import com.echothree.model.control.order.common.exception.MustSpecifyPaymentMethodOrPartyPaymentMethodException;
import com.echothree.model.control.order.common.exception.MustSpecifyWasPresentException;
import com.echothree.model.control.order.common.exception.UnknownOrderAliasTypeNameException;
import com.echothree.model.control.order.common.exception.UnknownOrderNameException;
import com.echothree.model.control.order.common.exception.UnknownOrderPaymentPreferenceSequenceException;
import com.echothree.model.control.order.common.exception.UnknownOrderRoleTypeNameException;
import com.echothree.model.control.order.common.exception.UnknownOrderSequenceException;
import com.echothree.model.control.order.common.exception.UnknownOrderSequenceTypeException;
import com.echothree.model.control.order.common.exception.UnknownOrderTypeNameException;
import com.echothree.model.control.order.server.control.OrderAliasControl;
import com.echothree.model.control.order.server.control.OrderControl;
import com.echothree.model.control.order.server.control.OrderLineControl;
import com.echothree.model.control.order.server.control.OrderPaymentPreferenceControl;
import com.echothree.model.control.order.server.control.OrderRoleControl;
import com.echothree.model.control.order.server.control.OrderTypeControl;
import com.echothree.model.control.payment.common.PaymentMethodTypes;
import com.echothree.model.control.payment.server.logic.PaymentMethodLogic;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.control.sequence.server.logic.SequenceGeneratorLogic;
import com.echothree.model.control.shipping.server.logic.ShippingMethodLogic;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationPolicy;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.order.server.entity.Order;
import com.echothree.model.data.order.server.entity.OrderAliasType;
import com.echothree.model.data.order.server.entity.OrderPaymentPreference;
import com.echothree.model.data.order.server.entity.OrderPriority;
import com.echothree.model.data.order.server.entity.OrderRoleType;
import com.echothree.model.data.order.server.entity.OrderShipmentGroup;
import com.echothree.model.data.order.server.entity.OrderType;
import com.echothree.model.data.payment.server.entity.PartyPaymentMethod;
import com.echothree.model.data.payment.server.entity.PaymentMethod;
import com.echothree.model.data.returnpolicy.server.entity.ReturnPolicy;
import com.echothree.model.data.sequence.server.entity.Sequence;
import com.echothree.model.data.sequence.server.entity.SequenceType;
import com.echothree.model.data.shipment.server.entity.FreeOnBoard;
import com.echothree.model.data.term.server.entity.Term;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.HashSet;
import java.util.Set;

public class OrderLogic
        extends BaseLogic {

    protected OrderLogic() {
        super();
    }

    private static class OrderLogicHolder {
        static OrderLogic instance = new OrderLogic();
    }

    public static OrderLogic getInstance() {
        return OrderLogicHolder.instance;
    }

    public Currency getOrderCurrency(final Order order) {
        return order.getLastDetail().getCurrency();
    }
    
    public OrderType getOrderTypeByName(final ExecutionErrorAccumulator eea, final String orderTypeName) {
        var orderTypeControl = Session.getModelController(OrderTypeControl.class);
        var orderType = orderTypeControl.getOrderTypeByName(orderTypeName);

        if(orderType == null) {
            handleExecutionError(UnknownOrderTypeNameException.class, eea, ExecutionErrors.UnknownOrderTypeName.name(), orderTypeName);
        }

        return orderType;
    }

    public OrderRoleType getOrderRoleTypeByName(final ExecutionErrorAccumulator eea, final String orderRoleTypeName) {
        var orderRoleControl = Session.getModelController(OrderRoleControl.class);
        var orderRoleType = orderRoleControl.getOrderRoleTypeByName(orderRoleTypeName);

        if(orderRoleType == null) {
            handleExecutionError(UnknownOrderRoleTypeNameException.class, eea, ExecutionErrors.UnknownOrderRoleTypeName.name(), orderRoleTypeName);
        }

        return orderRoleType;
    }

    public SequenceType getOrderSequenceType(final ExecutionErrorAccumulator eea, final OrderType orderType) {
        var orderTypeDetail = orderType.getLastDetail();
        var sequenceType = orderTypeDetail.getOrderSequenceType();

        if(sequenceType == null) {
            var sequenceControl = Session.getModelController(SequenceControl.class);

            sequenceType = sequenceControl.getDefaultSequenceType();
        }

        if(sequenceType == null) {
            handleExecutionError(UnknownOrderSequenceTypeException.class, eea, ExecutionErrors.UnknownOrderSequenceType.name(), orderType.getLastDetail().getOrderTypeName());
        }

        return sequenceType;
    }

    public Sequence getOrderSequence(final ExecutionErrorAccumulator eea, final OrderType orderType) {
        var sequenceType = getOrderSequenceType(eea, orderType);
        Sequence sequence = null;

        if(eea == null || !eea.hasExecutionErrors()) {
            var sequenceControl = Session.getModelController(SequenceControl.class);

            sequence = sequenceControl.getDefaultSequence(sequenceType);
        }

        if(sequence == null) {
            handleExecutionError(UnknownOrderSequenceException.class, eea, ExecutionErrors.UnknownOrderSequence.name(), orderType.getLastDetail().getOrderTypeName());
        }

        return sequence;
    }

    public String getOrderName(final ExecutionErrorAccumulator eea, final OrderType orderType, Sequence sequence) {
        String orderName = null;

        if(sequence == null) {
            sequence = getOrderSequence(eea, orderType);
        }

        if(eea == null || !eea.hasExecutionErrors()) {
            orderName = SequenceGeneratorLogic.getInstance().getNextSequenceValue(sequence);
        }

        return orderName;
    }

    public Order createOrder(final ExecutionErrorAccumulator eea, OrderType orderType, Sequence sequence, final OrderPriority orderPriority,
            final Currency currency, final Boolean holdUntilComplete, final Boolean allowBackorders, final Boolean allowSubstitutions,
            final Boolean allowCombiningShipments, final Term term, final FreeOnBoard freeOnBoard, final String reference, final String description,
            final CancellationPolicy cancellationPolicy, final ReturnPolicy returnPolicy, final Boolean taxable, final BasePK createdBy) {
        var orderName = getOrderName(eea, orderType, sequence);
        Order order = null;

        if(eea == null || !eea.hasExecutionErrors()) {
            var orderControl = Session.getModelController(OrderControl.class);
            
            order = orderControl.createOrder(orderType, orderName, orderPriority, currency, holdUntilComplete, allowBackorders, allowSubstitutions,
                    allowCombiningShipments, term, freeOnBoard, reference, description, cancellationPolicy, returnPolicy, taxable, createdBy);
        }

        return order;
    }

    public OrderAliasType getOrderAliasTypeByName(final ExecutionErrorAccumulator eea, final OrderType orderType, final String orderAliasTypeName) {
        var orderAliasControl = Session.getModelController(OrderAliasControl.class);
        var orderAliasType = orderAliasControl.getOrderAliasTypeByName(orderType, orderAliasTypeName);

        if(orderAliasType == null) {
            handleExecutionError(UnknownOrderAliasTypeNameException.class, eea, ExecutionErrors.UnknownOrderAliasTypeName.name(),
                    orderType.getLastDetail().getOrderTypeName(), orderAliasTypeName);
        }

        return orderAliasType;
    }

    private Order getOrderByName(final ExecutionErrorAccumulator eea, final String orderTypeName, final String orderName,
            final EntityPermission entityPermission) {
        var orderControl = Session.getModelController(OrderControl.class);
        var orderType = getOrderTypeByName(eea, orderTypeName);
        Order order = null;

        if(eea == null || !eea.hasExecutionErrors()) {
            order = orderControl.getOrderByName(orderType, orderName, entityPermission);

            if(order == null) {
                handleExecutionError(UnknownOrderNameException.class, eea, ExecutionErrors.UnknownOrderName.name(), orderTypeName, orderName);
            }
        }

        return order;
    }

    public Order getOrderByName(final ExecutionErrorAccumulator eea, final String orderTypeName, final String orderName) {
        return getOrderByName(eea, orderTypeName, orderName, EntityPermission.READ_ONLY);
    }

    public Order getOrderByNameForUpdate(final ExecutionErrorAccumulator eea, final String orderTypeName, final String orderName) {
        return getOrderByName(eea, orderTypeName, orderName, EntityPermission.READ_WRITE);
    }
    
    public Set<Item> getItemsFromOrder(final Order order) {
        var orderLineControl = Session.getModelController(OrderLineControl.class);
        var orderLines = orderLineControl.getOrderLinesByOrder(order);
        var items = new HashSet<Item>(orderLines.size());

        orderLines.forEach((orderLine) -> {
            items.add(orderLine.getLastDetail().getItem());
        });
        
        return items;
    }
    
    public OrderPaymentPreference createOrderPaymentPreference(final Session session, final ExecutionErrorAccumulator eea, final Order order,
            Integer orderPaymentPreferenceSequence, PaymentMethod paymentMethod, final PartyPaymentMethod partyPaymentMethod,
            final Boolean wasPresent, final Long maximumAmount, final Integer sortOrder, final BasePK createdBy) {
        var parameterCount = (paymentMethod == null ? 0 : 1) + (partyPaymentMethod == null ? 0 : 1);
        OrderPaymentPreference orderPaymentPreference = null;

        // Either the paymentMethod or the partyPaymentMethod must be specified.
        if(parameterCount == 1) {
            String paymentMethodTypeName;

            if(paymentMethod == null) {
                paymentMethod = partyPaymentMethod.getLastDetail().getPaymentMethod();
            }
            
            paymentMethodTypeName = paymentMethod.getLastDetail().getPaymentMethodType().getLastDetail().getPaymentMethodTypeName();
            
            // If the type is CREDIT_CARD, GIFT_CARD, or GIFT_CERTIFICATE, then the partyPaymentMethod must be specified.
            if((paymentMethodTypeName.equals(PaymentMethodTypes.CREDIT_CARD.name())
                    || paymentMethodTypeName.equals(PaymentMethodTypes.GIFT_CARD.name())
                    || paymentMethodTypeName.equals(PaymentMethodTypes.GIFT_CERTIFICATE.name())) && partyPaymentMethod == null) {
                handleExecutionError(MustSpecifyPartyPaymentMethodException.class, eea, ExecutionErrors.MustSpecifyPartyPaymentMethod.name());
            } else if(partyPaymentMethod != null) {
                // Otherwise, the partyPaymentMethod should always be null.
                handleExecutionError(CannotSpecifyPartyPaymentMethodException.class, eea, ExecutionErrors.CannotSpecifyPartyPaymentMethod.name());
            }
            
            // If the type is CREDIT_CARD then wasPresent must be specified.
            if(paymentMethodTypeName.equals(PaymentMethodTypes.CREDIT_CARD.name()) && wasPresent == null) {
                handleExecutionError(MustSpecifyWasPresentException.class, eea, ExecutionErrors.MustSpecifyWasPresent.name());
            } else if(wasPresent != null) {
                // Otherwise, the partyPaymentMethod should always be null.
                handleExecutionError(CannotSpecifyWasPresentException.class, eea, ExecutionErrors.CannotSpecifyWasPresent.name());
            }
            
            if(eea == null || !eea.hasExecutionErrors()) {
                PaymentMethodLogic.getInstance().checkAcceptanceOfItems(session, eea, paymentMethod, getItemsFromOrder(order), createdBy);
                
                if(eea == null || !eea.hasExecutionErrors()) {
                    var orderPaymentPreferenceControl = Session.getModelController(OrderPaymentPreferenceControl.class);
                    var orderControl = Session.getModelController(OrderControl.class);
                    var orderStatus = orderControl.getOrderStatusForUpdate(order);

                    if(orderPaymentPreferenceSequence == null) {
                        orderPaymentPreferenceSequence = orderStatus.getOrderPaymentPreferenceSequence() + 1;
                        orderStatus.setOrderPaymentPreferenceSequence(orderPaymentPreferenceSequence);
                    } else {
                        orderPaymentPreference = orderPaymentPreferenceControl.getOrderPaymentPreferenceBySequence(order, orderPaymentPreferenceSequence);

                        if(orderPaymentPreference == null) {
                            // If the orderPaymentPreferenceSequence is > the last one that was recorded in the OrderStatus, jump the
                            // one in OrderStatus forward - it should always record the greatest orderPaymentPreferenceSequence used.
                            if(orderPaymentPreferenceSequence > orderStatus.getOrderPaymentPreferenceSequence()) {
                                orderStatus.setOrderPaymentPreferenceSequence(orderPaymentPreferenceSequence);
                            }
                        } else {
                            handleExecutionError(DuplicateOrderPaymentPreferenceSequenceException.class, eea, ExecutionErrors.DuplicateOrderPaymentPreferenceSequence.name(),
                                    order.getLastDetail().getOrderName(), orderPaymentPreferenceSequence.toString());
                        }
                    }

                    if(orderPaymentPreference == null) {
                        orderPaymentPreference = orderPaymentPreferenceControl.createOrderPaymentPreference(order, orderPaymentPreferenceSequence, paymentMethod, partyPaymentMethod,
                                wasPresent, maximumAmount, sortOrder, createdBy);
                    }
                }
            }
        } else {
            if(parameterCount == 0) {
                handleExecutionError(MustSpecifyPaymentMethodOrPartyPaymentMethodException.class, eea, ExecutionErrors.MustSpecifyPaymentMethodOrPartyPaymentMethod.name());
            } else {
                handleExecutionError(CannotSpecifyPaymentMethodAndPartyPaymentMethodException.class, eea, ExecutionErrors.CannotSpecifyPaymentMethodAndPartyPaymentMethod.name());
            }
        }

        return orderPaymentPreference;
    }

    private OrderPaymentPreference getOrderPaymentPreferenceByName(final ExecutionErrorAccumulator eea, final String orderTypeName, final String orderName,
            final String orderPaymentPreferenceSequence, final EntityPermission entityPermission) {
        var orderPaymentPreferenceControl = Session.getModelController(OrderPaymentPreferenceControl.class);
        var order = getOrderByName(eea, orderTypeName, orderName);
        OrderPaymentPreference orderPaymentPreference = null;
        
        if(eea == null || !eea.hasExecutionErrors()) {
            orderPaymentPreference = orderPaymentPreferenceControl.getOrderPaymentPreferenceBySequence(order, Integer.valueOf(orderPaymentPreferenceSequence), entityPermission);
            
            if(orderPaymentPreference == null) {
                handleExecutionError(UnknownOrderPaymentPreferenceSequenceException.class, eea, ExecutionErrors.UnknownOrderPaymentPreferenceSequence.name(), orderTypeName,
                        orderName, orderPaymentPreferenceSequence);
            }
        }

        return orderPaymentPreference;
    }

    public OrderPaymentPreference getOrderPaymentPreferenceByName(final ExecutionErrorAccumulator eea, final String orderTypeName, final String orderName,
            final String orderPaymentPreferenceSequence) {
        return getOrderPaymentPreferenceByName(eea, orderTypeName, orderName, orderPaymentPreferenceSequence, EntityPermission.READ_ONLY);
    }

    public OrderPaymentPreference getOrderPaymentPreferenceByNameForUpdate(final ExecutionErrorAccumulator eea, final String orderTypeName, final String orderName,
            final String orderPaymentPreferenceSequence) {
        return getOrderPaymentPreferenceByName(eea, orderTypeName, orderName, orderPaymentPreferenceSequence, EntityPermission.READ_WRITE);
    }
    
    public void checkItemAgainstOrderPaymentPreferences(final Session session, final ExecutionErrorAccumulator eea, final Order order, final Item item,
            final BasePK evaluatedBy) {
        var orderPaymentPreferenceControl = Session.getModelController(OrderPaymentPreferenceControl.class);
        var orderPaymentPreferences = orderPaymentPreferenceControl.getOrderPaymentPreferencesByOrder(order);
        
        orderPaymentPreferences.forEach((orderPaymentPreference) -> {
            PaymentMethodLogic.getInstance().checkAcceptanceOfItem(session, eea, orderPaymentPreference.getLastDetail().getPaymentMethod(), item, evaluatedBy);
        });
    }
    
    public void checkItemAgainstShippingMethod(final Session session, final ExecutionErrorAccumulator eea, final OrderShipmentGroup orderShipmentGroup,
            final Item item, final BasePK evaluatedBy) {
        var shippingMethod = orderShipmentGroup.getLastDetail().getShippingMethod();
        
        if(shippingMethod != null) {
            ShippingMethodLogic.getInstance().checkAcceptanceOfItem(session, eea, shippingMethod, item, evaluatedBy);
        }
    }

}
