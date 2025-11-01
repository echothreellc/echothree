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

import com.echothree.model.control.order.common.exception.DuplicateOrderShipmentGroupSequenceException;
import com.echothree.model.control.order.common.exception.UnknownDefaultOrderShipmentGroupException;
import com.echothree.model.control.order.common.exception.UnknownOrderShipmentGroupException;
import com.echothree.model.control.order.server.control.OrderControl;
import com.echothree.model.control.order.server.control.OrderShipmentGroupControl;
import com.echothree.model.data.contact.server.entity.PartyContactMechanism;
import com.echothree.model.data.item.server.entity.ItemDeliveryType;
import com.echothree.model.data.order.server.entity.Order;
import com.echothree.model.data.order.server.entity.OrderShipmentGroup;
import com.echothree.model.data.shipping.server.entity.ShippingMethod;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

public class OrderShipmentGroupLogic
        extends BaseLogic {

    protected OrderShipmentGroupLogic() {
        super();
    }

    public OrderShipmentGroup createOrderShipmentGroup(final ExecutionErrorAccumulator eea, final Order order, Integer orderShipmentGroupSequence,
            final ItemDeliveryType itemDeliveryType, final Boolean isDefault, final PartyContactMechanism partyContactMechanism,
            final ShippingMethod shippingMethod, final Boolean holdUntilComplete, final BasePK createdBy) {
        var orderControl = Session.getModelController(OrderControl.class);
        var orderShipmentGroupControl = Session.getModelController(OrderShipmentGroupControl.class);
        var orderStatus = orderControl.getOrderStatusForUpdate(order);
        OrderShipmentGroup orderShipmentGroup = null;

        if(orderShipmentGroupSequence == null) {
            orderShipmentGroupSequence = orderStatus.getOrderShipmentGroupSequence() + 1;
            orderStatus.setOrderShipmentGroupSequence(orderShipmentGroupSequence);
        } else {
            orderShipmentGroup = orderShipmentGroupControl.getOrderShipmentGroupBySequence(order, orderShipmentGroupSequence);

            if(orderShipmentGroup == null) {
                // If the orderShipmentGroupSequence is > the last one that was recorded in the OrderStatus, jump the
                // one in OrderStatus forward - it should always record the greatest orderShipmentGroupSequence used.
                if(orderShipmentGroupSequence > orderStatus.getOrderShipmentGroupSequence()) {
                    orderStatus.setOrderShipmentGroupSequence(orderShipmentGroupSequence);
                }
            } else {
                handleExecutionError(DuplicateOrderShipmentGroupSequenceException.class, eea, ExecutionErrors.DuplicateOrderShipmentGroupSequence.name(),
                        order.getLastDetail().getOrderName(), orderShipmentGroupSequence.toString());
            }
        }

        if(orderShipmentGroup == null) {
            orderShipmentGroup = orderShipmentGroupControl.createOrderShipmentGroup(order, orderShipmentGroupSequence, itemDeliveryType, isDefault, partyContactMechanism,
                    shippingMethod, holdUntilComplete, createdBy);
        }

        return orderShipmentGroup;
    }

    public OrderShipmentGroup getDefaultOrderShipmentGroup(final ExecutionErrorAccumulator eea, final Order order,
            final ItemDeliveryType itemDeliveryType, final EntityPermission entityPermission) {
        var orderShipmentGroupControl = Session.getModelController(OrderShipmentGroupControl.class);
        var orderShipmentGroup = orderShipmentGroupControl.getDefaultOrderShipmentGroup(order, itemDeliveryType, entityPermission);

        if(orderShipmentGroup == null) {
            handleExecutionError(UnknownDefaultOrderShipmentGroupException.class, eea, ExecutionErrors.UnknownDefaultOrderShipmentGroup.name(),
                    order.getLastDetail().getOrderName(), itemDeliveryType.getItemDeliveryTypeName());
        }

        return orderShipmentGroup;
    }

    public OrderShipmentGroup getDefaultOrderShipmentGroup(final ExecutionErrorAccumulator eea, final Order order,
            final ItemDeliveryType itemDeliveryType) {
        return getDefaultOrderShipmentGroup(eea, order, itemDeliveryType, EntityPermission.READ_ONLY);
    }

    public OrderShipmentGroup getDefaultOrderShipmentGroupForUpdate(final ExecutionErrorAccumulator eea, final Order order,
            final ItemDeliveryType itemDeliveryType) {
        return getDefaultOrderShipmentGroup(eea, order, itemDeliveryType, EntityPermission.READ_WRITE);
    }

    public OrderShipmentGroup getOrderShipmentGroupBySequence(final ExecutionErrorAccumulator eea, final Order order,
            final Integer orderShipmentGroupSequence, final EntityPermission entityPermission) {
        var orderShipmentGroupControl = Session.getModelController(OrderShipmentGroupControl.class);
        var orderShipmentGroup = orderShipmentGroupControl.getOrderShipmentGroupBySequence(order, orderShipmentGroupSequence);

        if(orderShipmentGroup == null) {
            handleExecutionError(UnknownOrderShipmentGroupException.class, eea, ExecutionErrors.UnknownOrderShipmentGroup.name(),
                    order.getLastDetail().getOrderName(), orderShipmentGroupSequence);
        }

        return orderShipmentGroup;
    }

    public OrderShipmentGroup getOrderShipmentGroup(final ExecutionErrorAccumulator eea, final Order order, final Integer orderShipmentGroupSequence) {
        return getOrderShipmentGroupBySequence(eea, order, orderShipmentGroupSequence, EntityPermission.READ_ONLY);
    }

    public OrderShipmentGroup getOrderShipmentGroupForUpdate(final ExecutionErrorAccumulator eea, final Order order, final Integer orderShipmentGroupSequence) {
        return getOrderShipmentGroupBySequence(eea, order, orderShipmentGroupSequence, EntityPermission.READ_WRITE);
    }

}
