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

import com.echothree.model.control.order.common.exception.DuplicateOrderLineSequenceException;
import com.echothree.model.control.order.common.exception.UnknownOrderLineSequenceException;
import com.echothree.model.control.order.server.control.OrderAdjustmentControl;
import com.echothree.model.control.order.server.control.OrderControl;
import com.echothree.model.control.order.server.control.OrderLineAdjustmentControl;
import com.echothree.model.control.order.server.control.OrderLineControl;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationPolicy;
import com.echothree.model.data.inventory.server.entity.InventoryCondition;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.order.server.entity.Order;
import com.echothree.model.data.order.server.entity.OrderLine;
import com.echothree.model.data.order.server.entity.OrderShipmentGroup;
import com.echothree.model.data.returnpolicy.server.entity.ReturnPolicy;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureType;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class OrderLineLogic
        extends BaseLogic {

    protected OrderLineLogic() {
        super();
    }

    public static OrderLineLogic getInstance() {
        return CDI.current().select(OrderLineLogic.class).get();
    }

    public OrderLine createOrderLine(final Session session, final ExecutionErrorAccumulator eea, final Order order, Integer orderLineSequence,
            final OrderLine parentOrderLine, final OrderShipmentGroup orderShipmentGroup, final Item item, final InventoryCondition inventoryCondition,
            final UnitOfMeasureType unitOfMeasureType, final Long quantity, final Long unitAmount, final String description,
            final CancellationPolicy cancellationPolicy, final ReturnPolicy returnPolicy, final Boolean taxable, final BasePK createdBy) {
        var orderLogic = OrderLogic.getInstance();
        OrderLine orderLine = null;

        // Make sure any OrderPaymentPreferences associated with this order are OK with this Item.
        orderLogic.checkItemAgainstOrderPaymentPreferences(session, eea, order, item, createdBy);
        
        if(orderShipmentGroup != null) {
            // Make sure the ShippingMethod associated with the OrderShipmentGroup is OK with this Item.
            orderLogic.checkItemAgainstShippingMethod(session, eea, orderShipmentGroup, item, createdBy);
        }
        
        if(eea == null || !eea.hasExecutionErrors()) {
            var orderControl = Session.getModelController(OrderControl.class);
            var orderLineControl = Session.getModelController(OrderLineControl.class);
            var orderStatus = orderControl.getOrderStatusForUpdate(order);

            if(orderLineSequence == null) {
                orderLineSequence = orderStatus.getOrderLineSequence() + 1;
                orderStatus.setOrderLineSequence(orderLineSequence);
            } else {
                orderLine = orderLineControl.getOrderLineBySequence(order, orderLineSequence);

                if(orderLine == null) {
                    // If the orderLineSequence is > the last one that was recorded in the OrderStatus, jump the
                    // one in OrderStatus forward - it should always record the greatest orderLineSequence used.
                    if(orderLineSequence > orderStatus.getOrderLineSequence()) {
                        orderStatus.setOrderLineSequence(orderLineSequence);
                    }
                } else {
                    handleExecutionError(DuplicateOrderLineSequenceException.class, eea, ExecutionErrors.DuplicateOrderLineSequence.name(),
                            order.getLastDetail().getOrderName(), orderLineSequence.toString());
                }
            }

            if(orderLine == null) {
                orderLine = orderLineControl.createOrderLine(order, orderLineSequence, parentOrderLine, orderShipmentGroup, item, inventoryCondition,
                        unitOfMeasureType, quantity, unitAmount, description, cancellationPolicy, returnPolicy, taxable, createdBy);
            }
        }

        return orderLine;
    }

    private OrderLine getOrderLineByName(final ExecutionErrorAccumulator eea, final String orderTypeName, final String orderName, final String orderLineSequence,
            final EntityPermission entityPermission) {
        var order = OrderLogic.getInstance().getOrderByName(eea, orderTypeName, orderName);
        OrderLine orderLine = null;
        
        if(eea == null || !eea.hasExecutionErrors()) {
            var orderLineControl = Session.getModelController(OrderLineControl.class);

            orderLine = orderLineControl.getOrderLineBySequence(order, Integer.valueOf(orderLineSequence), entityPermission);
            
            if(orderLine == null) {
                handleExecutionError(UnknownOrderLineSequenceException.class, eea, ExecutionErrors.UnknownOrderLineSequence.name(), orderTypeName, orderName, orderLineSequence);
            }
        }

        return orderLine;
    }

    public OrderLine getOrderLineByName(final ExecutionErrorAccumulator eea, final String orderTypeName, final String orderName, final String orderLineSequence) {
        return getOrderLineByName(eea, orderTypeName, orderName, orderLineSequence, EntityPermission.READ_ONLY);
    }

    public OrderLine getOrderLineByNameForUpdate(final ExecutionErrorAccumulator eea, final String orderTypeName, final String orderName, final String orderLineSequence) {
        return getOrderLineByName(eea, orderTypeName, orderName, orderLineSequence, EntityPermission.READ_WRITE);
    }
    
    public Long getOrderTotalWithAdjustments(final Order order) {
        var orderAdjustmentControl = Session.getModelController(OrderAdjustmentControl.class);
        long total = 0;
        var orderAdjustments = orderAdjustmentControl.getOrderAdjustmentsByOrder(order);

        total = orderAdjustments.stream().map((orderAdjustment) -> orderAdjustment.getLastDetail().getAmount()).reduce(total, (accumulator, _item) -> accumulator + _item);

        return total + getOrderLineTotalsWithAdjustments(order);
    }

    public Long getOrderLineTotalsWithAdjustments(final Order order) {
        var orderLineControl = Session.getModelController(OrderLineControl.class);
        var orderLines = orderLineControl.getOrderLinesByOrder(order);
        long total = 0;

        total = orderLines.stream().map((orderLine) -> getOrderLineTotalWithAdjustments(orderLine)).reduce(total, (accumulator, _item) -> accumulator + _item);

        return total;
    }

    public Long getOrderLineTotalWithAdjustments(final OrderLine orderLine) {
        var orderLineAdjustmentControl = Session.getModelController(OrderLineAdjustmentControl.class);
        var orderLineDetail = orderLine.getLastDetail();
        var total = orderLineDetail.getQuantity() * orderLineDetail.getUnitAmount();
        var orderLineAdjustments = orderLineAdjustmentControl.getOrderLineAdjustmentsByOrderLine(orderLine);

        total = orderLineAdjustments.stream().map((orderLineAdjustment) -> orderLineAdjustment.getLastDetail().getAmount()).reduce(total, (accumulator, _item) -> accumulator + _item);

        return total;
    }

}
