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

package com.echothree.model.control.sales.server.logic;

import com.echothree.model.control.order.common.transfer.OrderTimeTransfer;
import com.echothree.model.control.order.server.logic.OrderTimeLogic;
import com.echothree.model.data.order.server.entity.Order;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class SalesOrderTimeLogic
        extends BaseLogic {

    protected SalesOrderTimeLogic() {
        super();
    }

    public static SalesOrderTimeLogic getInstance() {
        return CDI.current().select(SalesOrderTimeLogic.class).get();
    }
    
    public void createOrderTime(final ExecutionErrorAccumulator eea, final String orderName, final String orderTimeTypeName, final Long time, final BasePK createdBy) {
        var order = SalesOrderLogic.getInstance().getOrderByName(eea, orderName);
        
        if(eea == null || !eea.hasExecutionErrors()) {
            createOrderTime(eea, order, orderTimeTypeName, time, createdBy);
        }
    }

    public void createOrderTime(final ExecutionErrorAccumulator eea, final Order order, final String orderTimeTypeName, final Long time, final BasePK createdBy) {
        // TODO: Check Order's status.

        OrderTimeLogic.getInstance().createOrderTime(eea, order, orderTimeTypeName, time, createdBy);
    }

    public void updateOrderTime(final ExecutionErrorAccumulator eea, final String orderName, final String orderTimeTypeName, final Long time, final BasePK updatedBy) {
        var order = SalesOrderLogic.getInstance().getOrderByName(eea, orderName);
        
        if(eea == null || !eea.hasExecutionErrors()) {
            updateOrderTime(eea, order, orderTimeTypeName, time, updatedBy);
        }
    }

    public void updateOrderTime(final ExecutionErrorAccumulator eea, final Order order, final String orderTimeTypeName, final Long time, final BasePK updatedBy) {
        // TODO: Check Order's status.

        OrderTimeLogic.getInstance().updateOrderTime(eea, order, orderTimeTypeName, time, updatedBy);
    }

    public OrderTimeTransfer getOrderTimeTransfer(final ExecutionErrorAccumulator eea, final UserVisit userVisit, final String orderName, final String orderTimeTypeName) {
        var order = SalesOrderLogic.getInstance().getOrderByName(eea, orderName);
        OrderTimeTransfer result = null;
        
        if(eea == null || !eea.hasExecutionErrors()) {
            result = getOrderTimeTransfer(eea, userVisit, order, orderTimeTypeName);
        }
        
        return result;
    }

    public OrderTimeTransfer getOrderTimeTransfer(final ExecutionErrorAccumulator eea, final UserVisit userVisit, final Order order, final String orderTimeTypeName) {
        return OrderTimeLogic.getInstance().getOrderTimeTransfer(eea, userVisit, order, orderTimeTypeName);
    }

    public List<OrderTimeTransfer> getOrderTimeTransfersByOrder(final ExecutionErrorAccumulator eea, final UserVisit userVisit, final String orderName) {
        var order = SalesOrderLogic.getInstance().getOrderByName(eea, orderName);
        
        return OrderTimeLogic.getInstance().getOrderTimeTransfersByOrder(eea, userVisit, order);
    }

    public void deleteOrderTime(final ExecutionErrorAccumulator eea, final String orderName, final String orderTimeTypeName, final BasePK deletedBy) {
        var order = SalesOrderLogic.getInstance().getOrderByName(eea, orderName);
        
        if(eea == null || !eea.hasExecutionErrors()) {
            deleteOrderTime(eea, order, orderTimeTypeName, deletedBy);
        }
    }

    public void deleteOrderTime(final ExecutionErrorAccumulator eea, final Order order, final String orderTimeTypeName, final BasePK deletedBy) {
        // TODO: Check Order's status.

        OrderTimeLogic.getInstance().deleteOrderTime(eea, order, orderTimeTypeName, deletedBy);
    }

}
