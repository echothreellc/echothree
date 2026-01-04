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

package com.echothree.model.control.order.server.logic;

import com.echothree.model.control.order.common.exception.DuplicateOrderTimeException;
import com.echothree.model.control.order.common.exception.UnknownOrderTimeException;
import com.echothree.model.control.order.common.exception.UnknownOrderTimeTypeNameException;
import com.echothree.model.control.order.common.transfer.OrderTimeTransfer;
import com.echothree.model.control.order.server.control.OrderTimeControl;
import com.echothree.model.data.order.server.entity.Order;
import com.echothree.model.data.order.server.entity.OrderTime;
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
public class OrderTimeLogic
        extends BaseLogic {

    protected OrderTimeLogic() {
        super();
    }

    public static OrderTimeLogic getInstance() {
        return CDI.current().select(OrderTimeLogic.class).get();
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

    public void createOrderTime(final ExecutionErrorAccumulator eea, final Order order, final String orderTimeTypeName, final Long time, final BasePK partyPK) {
        var orderTimeControl = Session.getModelController(OrderTimeControl.class);
        var orderDetail = order.getLastDetail().getOrder().getLastDetail();
        var orderType = orderDetail.getOrderType();
        var orderTimeType = orderTimeControl.getOrderTimeTypeByName(orderType, orderTimeTypeName);

        if(eea == null || !eea.hasExecutionErrors()) {
            if(orderTimeControl.orderTimeExists(order, orderTimeType)) {
                handleExecutionError(DuplicateOrderTimeException.class, eea, ExecutionErrors.DuplicateOrderTime.name(), getOrderTypeName(orderType),
                        orderDetail.getOrderName(), orderTimeTypeName);
            } else {
                orderTimeControl.createOrderTime(order, orderTimeType, time, partyPK);
            }
        }
    }

    public void updateOrderTime(final ExecutionErrorAccumulator eea, final Order order, final String orderTimeTypeName, final Long time, final BasePK partyPK) {
        var orderTimeControl = Session.getModelController(OrderTimeControl.class);
        var orderDetail = order.getLastDetail().getOrder().getLastDetail();
        var orderType = orderDetail.getOrderType();
        var orderTimeType = orderTimeControl.getOrderTimeTypeByName(orderType, orderTimeTypeName);

        if(eea == null || !eea.hasExecutionErrors()) {
            var orderTimeValue = orderTimeControl.getOrderTimeValueForUpdate(order, orderTimeType);

            if(orderTimeValue == null) {
                handleExecutionError(UnknownOrderTimeException.class, eea, ExecutionErrors.UnknownOrderTime.name(), getOrderTypeName(orderType),
                        orderDetail.getOrderName(), orderTimeTypeName);
            } else {
                orderTimeValue.setTime(time);
                orderTimeControl.updateOrderTimeFromValue(orderTimeValue, partyPK);
            }
        }
    }

    public void createOrUpdateOrderTimeIfNotNull(final ExecutionErrorAccumulator eea, final Order order, final String orderTimeTypeName, final Long time,
            final BasePK partyPK) {
        if(time != null) {
            createOrUpdateOrderTime(eea, order, orderTimeTypeName, time, partyPK);
        }
    }

    public void createOrUpdateOrderTime(final ExecutionErrorAccumulator eea, final Order order, final String orderTimeTypeName, final Long time,
            final BasePK partyPK) {
        var orderTimeControl = Session.getModelController(OrderTimeControl.class);
        var orderDetail = order.getLastDetail();
        var orderType = orderDetail.getOrderType();
        var orderTimeType = getOrderTimeTypeByName(eea, orderType, orderTimeTypeName);

        if(eea == null || !eea.hasExecutionErrors()) {
            var orderTimeValue = orderTimeControl.getOrderTimeValueForUpdate(order, orderTimeType);

            if(orderTimeValue == null) {
                orderTimeControl.createOrderTime(order, orderTimeType, time, partyPK);
            } else {
                orderTimeValue.setTime(time);
                orderTimeControl.updateOrderTimeFromValue(orderTimeValue, partyPK);
            }
        }
    }

    private OrderTime getOrderTimeEntity(final ExecutionErrorAccumulator eea, final Order order, final String orderTimeTypeName) {
        var orderTimeControl = Session.getModelController(OrderTimeControl.class);
        var orderDetail = order.getLastDetail();
        var orderType = orderDetail.getOrderType();
        var orderTimeType = getOrderTimeTypeByName(eea, orderType, orderTimeTypeName);
        OrderTime result = null;

        if(eea == null || !eea.hasExecutionErrors()) {
            result = orderTimeControl.getOrderTimeForUpdate(order, orderTimeType);

            if(result == null) {
                handleExecutionError(UnknownOrderTimeException.class, eea, ExecutionErrors.UnknownOrderTime.name(), getOrderTypeName(orderType), orderDetail.getOrderName(),
                        orderTimeTypeName);
            }
        }

        return result;
    }

    public Long getOrderTime(final ExecutionErrorAccumulator eea, final Order order, final String orderTimeTypeName) {
        var orderTime = getOrderTimeEntity(eea, order, orderTimeTypeName);
        
        return orderTime == null ? null : orderTime.getTime();
    }

    public OrderTimeTransfer getOrderTimeTransfer(final ExecutionErrorAccumulator eea, final UserVisit userVisit, final Order order, final String orderTimeTypeName) {
        var orderTime = getOrderTimeEntity(eea, order, orderTimeTypeName);
        
        return orderTime == null ? null : ((OrderTimeControl)Session.getModelController(OrderTimeControl.class)).getOrderTimeTransfer(userVisit, orderTime);
    }

    public List<OrderTimeTransfer> getOrderTimeTransfersByOrder(final ExecutionErrorAccumulator eea, final UserVisit userVisit, final Order order) {
        return ((OrderTimeControl)Session.getModelController(OrderTimeControl.class)).getOrderTimeTransfersByOrder(userVisit, order);
    }

    public void deleteOrderTime(final ExecutionErrorAccumulator eea, final Order order, final String orderTimeTypeName, final BasePK deletedBy) {
        var orderTimeControl = Session.getModelController(OrderTimeControl.class);
        var orderDetail = order.getLastDetail();
        var orderType = orderDetail.getOrderType();
        var orderTimeType = getOrderTimeTypeByName(eea, orderType, orderTimeTypeName);

        if(eea == null || !eea.hasExecutionErrors()) {
            var orderTime = orderTimeControl.getOrderTimeForUpdate(order, orderTimeType);

            if(orderTime == null) {
                handleExecutionError(UnknownOrderTimeException.class, eea, ExecutionErrors.UnknownOrderTime.name(), getOrderTypeName(orderType), orderDetail.getOrderName(), orderTimeTypeName);
            } else {
                orderTimeControl.deleteOrderTime(orderTime, deletedBy);
            }
        }
    }

}
