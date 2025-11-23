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

import com.echothree.model.control.order.common.exception.UnknownOrderLineTimeException;
import com.echothree.model.control.order.common.exception.UnknownOrderTimeTypeNameException;
import com.echothree.model.control.order.common.transfer.OrderLineTimeTransfer;
import com.echothree.model.control.order.server.control.OrderTimeControl;
import com.echothree.model.data.order.server.entity.OrderLine;
import com.echothree.model.data.order.server.entity.OrderLineTime;
import com.echothree.model.data.order.server.entity.OrderTimeType;
import com.echothree.model.data.order.server.entity.OrderType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class OrderLineTimeLogic
        extends BaseLogic {

    protected OrderLineTimeLogic() {
        super();
    }

    public static OrderLineTimeLogic getInstance() {
        return CDI.current().select(OrderLineTimeLogic.class).get();
    }

    private String getOrderTypeName(OrderType orderType) {
        return orderType.getLastDetail().getOrderTypeName();
    }

    public OrderTimeType getOrderTimeTypeByName(final ExecutionErrorAccumulator eea, final OrderType orderType, final String orderTimeTypeName) {
        var orderTimeControl = Session.getModelController(OrderTimeControl.class);
        var orderTimeType = orderTimeControl.getOrderTimeTypeByName(orderType, orderTimeTypeName);

        if(orderTimeType == null) {
            var orderTypeName = orderType.getLastDetail().getOrderTypeName();

            handleExecutionError(UnknownOrderTimeTypeNameException.class, eea, ExecutionErrors.UnknownOrderTimeTypeName.name(), orderTypeName, orderTimeTypeName);
        }

        return orderTimeType;
    }

    public void createOrderLineTime(final ExecutionErrorAccumulator eea, final OrderLine orderLine, final String orderTimeTypeName, final Long time, final BasePK partyPK) {
        var orderTimeControl = Session.getModelController(OrderTimeControl.class);
        var orderLineDetail = orderLine.getLastDetail();
        var orderDetail = orderLineDetail.getOrder().getLastDetail();
        var orderType = orderDetail.getOrderType();
        var orderTimeType = orderTimeControl.getOrderTimeTypeByName(orderType, orderTimeTypeName);

        if(eea == null || !eea.hasExecutionErrors()) {
            if(orderTimeControl.orderLineTimeExists(orderLine, orderTimeType)) {
                handleExecutionError(UnknownOrderLineTimeException.class, eea, ExecutionErrors.DuplicateOrderLineTime.name(), getOrderTypeName(orderType),
                        orderDetail.getOrderName(), orderLineDetail.getOrderLineSequence().toString(), orderTimeTypeName);
            } else {
                orderTimeControl.createOrderLineTime(orderLine, orderTimeType, time, partyPK);
            }
        }
    }

    public void updateOrderLineTime(final ExecutionErrorAccumulator eea, final OrderLine orderLine, final String orderTimeTypeName, final Long time, final BasePK partyPK) {
        var orderTimeControl = Session.getModelController(OrderTimeControl.class);
        var orderLineDetail = orderLine.getLastDetail();
        var orderDetail = orderLineDetail.getOrder().getLastDetail();
        var orderType = orderDetail.getOrderType();
        var orderTimeType = orderTimeControl.getOrderTimeTypeByName(orderType, orderTimeTypeName);

        if(eea == null || !eea.hasExecutionErrors()) {
            var orderLineTimeValue = orderTimeControl.getOrderLineTimeValueForUpdate(orderLine, orderTimeType);

            if(orderLineTimeValue == null) {
                handleExecutionError(UnknownOrderLineTimeException.class, eea, ExecutionErrors.UnknownOrderLineTime.name(), getOrderTypeName(orderType),
                        orderDetail.getOrderName(), orderLineDetail.getOrderLineSequence().toString(), orderTimeTypeName);
            } else {
                orderLineTimeValue.setTime(time);
                orderTimeControl.updateOrderLineTimeFromValue(orderLineTimeValue, partyPK);
            }
        }
    }

    public void createOrUpdateOrderLineTimeIfNotNull(final ExecutionErrorAccumulator eea, final OrderLine orderLine, final String orderTimeTypeName, final Long time,
            final BasePK partyPK) {
        if(time != null) {
            createOrUpdateOrderLineTime(eea, orderLine, orderTimeTypeName, time, partyPK);
        }
    }

    public void createOrUpdateOrderLineTime(final ExecutionErrorAccumulator eea, final OrderLine orderLine, final String orderTimeTypeName, final Long time,
            final BasePK partyPK) {
        var orderTimeControl = Session.getModelController(OrderTimeControl.class);
        var orderLineDetail = orderLine.getLastDetail();
        var orderDetail = orderLineDetail.getOrder().getLastDetail();
        var orderType = orderDetail.getOrderType();
        var orderTimeType = getOrderTimeTypeByName(eea, orderType, orderTimeTypeName);

        if(eea == null || !eea.hasExecutionErrors()) {
            var orderLineTimeValue = orderTimeControl.getOrderLineTimeValueForUpdate(orderLine, orderTimeType);

            if(orderLineTimeValue == null) {
                orderTimeControl.createOrderLineTime(orderLine, orderTimeType, time, partyPK);
            } else {
                orderLineTimeValue.setTime(time);
                orderTimeControl.updateOrderLineTimeFromValue(orderLineTimeValue, partyPK);
            }
        }
    }

    private OrderLineTime getOrderLineTimeEntity(final ExecutionErrorAccumulator eea, final OrderLine orderLine, final String orderTimeTypeName) {
        var orderTimeControl = Session.getModelController(OrderTimeControl.class);
        var orderLineDetail = orderLine.getLastDetail();
        var orderDetail = orderLineDetail.getOrder().getLastDetail();
        var orderType = orderDetail.getOrderType();
        var orderTimeType = getOrderTimeTypeByName(eea, orderType, orderTimeTypeName);
        OrderLineTime result = null;

        if(eea == null || !eea.hasExecutionErrors()) {
            result = orderTimeControl.getOrderLineTimeForUpdate(orderLine, orderTimeType);

            if(result == null) {
                handleExecutionError(UnknownOrderLineTimeException.class, eea, ExecutionErrors.UnknownOrderLineTime.name(), getOrderTypeName(orderType),
                        orderDetail.getOrderName(), orderLineDetail.getOrderLineSequence().toString(), orderTimeTypeName);
            }
        }

        return result;
    }

    public Long getOrderLineTime(final ExecutionErrorAccumulator eea, final OrderLine orderLine, final String orderTimeTypeName) {
        var orderLineTime = getOrderLineTimeEntity(eea, orderLine, orderTimeTypeName);
        
        return orderLineTime == null ? null : orderLineTime.getTime();
    }

    public OrderLineTimeTransfer getOrderLineTimeTransfer(final ExecutionErrorAccumulator eea, final UserVisit userVisit, final OrderLine orderLine,
            final String orderTimeTypeName) {
        var orderLineTime = getOrderLineTimeEntity(eea, orderLine, orderTimeTypeName);
        
        return orderLineTime == null ? null : ((OrderTimeControl)Session.getModelController(OrderTimeControl.class)).getOrderLineTimeTransfer(userVisit, orderLineTime);
    }

    public List<OrderLineTimeTransfer> getOrderLineTimeTransfersByOrder(final ExecutionErrorAccumulator eea, final UserVisit userVisit, final OrderLine orderLine) {
        return ((OrderTimeControl)Session.getModelController(OrderTimeControl.class)).getOrderLineTimeTransfersByOrderLine(userVisit, orderLine);
    }

    public void deleteOrderLineTime(final ExecutionErrorAccumulator eea, final OrderLine orderLine, final String orderTimeTypeName, final BasePK deletedBy) {
        var orderTimeControl = Session.getModelController(OrderTimeControl.class);
        var orderLineDetail = orderLine.getLastDetail();
        var orderDetail = orderLineDetail.getOrder().getLastDetail();
        var orderType = orderDetail.getOrderType();
        var orderTimeType = getOrderTimeTypeByName(eea, orderType, orderTimeTypeName);

        if(eea == null || !eea.hasExecutionErrors()) {
            var orderLineTime = orderTimeControl.getOrderLineTimeForUpdate(orderLine, orderTimeType);

            if(orderLineTime == null) {
                handleExecutionError(UnknownOrderLineTimeException.class, eea, ExecutionErrors.UnknownOrderLineTime.name(), getOrderTypeName(orderType),
                        orderDetail.getOrderName(), orderLineDetail.getOrderLineSequence().toString(), orderTimeTypeName);
            } else {
                orderTimeControl.deleteOrderLineTime(orderLineTime, deletedBy);
            }
        }
    }

}
