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

import com.echothree.model.control.order.common.transfer.OrderLineTimeTransfer;
import com.echothree.model.control.order.server.logic.OrderLineTimeLogic;
import com.echothree.model.data.order.server.entity.OrderLine;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class SalesOrderLineTimeLogic
        extends BaseLogic {

    protected SalesOrderLineTimeLogic() {
        super();
    }

    public static SalesOrderLineTimeLogic getInstance() {
        return CDI.current().select(SalesOrderLineTimeLogic.class).get();
    }
    
    public void createOrderLineTime(final ExecutionErrorAccumulator eea, final String orderName, final String orderLineSequence, final String orderTimeTypeName, final Long time, final BasePK createdBy) {
        var orderLine = SalesOrderLineLogic.getInstance().getOrderLineByName(eea, orderName, orderLineSequence);
        
        if(eea == null || !eea.hasExecutionErrors()) {
            createOrderLineTime(eea, orderLine, orderTimeTypeName, time, createdBy);
        }
    }

    public void createOrderLineTime(final ExecutionErrorAccumulator eea, final OrderLine orderLine, final String orderTimeTypeName, final Long time, final BasePK createdBy) {
        // TODO: Check Order's status.

        OrderLineTimeLogic.getInstance().createOrderLineTime(eea, orderLine, orderTimeTypeName, time, createdBy);
    }

    public void updateOrderLineTime(final ExecutionErrorAccumulator eea, final String orderName, final String orderLineSequence, final String orderTimeTypeName, final Long time, final BasePK updatedBy) {
        var orderLine = SalesOrderLineLogic.getInstance().getOrderLineByName(eea, orderName, orderLineSequence);
        
        if(eea == null || !eea.hasExecutionErrors()) {
            updateOrderLineTime(eea, orderLine, orderTimeTypeName, time, updatedBy);
        }
    }

    public void updateOrderLineTime(final ExecutionErrorAccumulator eea, final OrderLine orderLine, final String orderTimeTypeName, final Long time, final BasePK updatedBy) {
        // TODO: Check Order's status.

        OrderLineTimeLogic.getInstance().updateOrderLineTime(eea, orderLine, orderTimeTypeName, time, updatedBy);
    }

    public OrderLineTimeTransfer getOrderLineTimeTransfer(final ExecutionErrorAccumulator eea, final UserVisit userVisit, final String orderName, final String orderLineSequence, final String orderTimeTypeName) {
        var orderLine = SalesOrderLineLogic.getInstance().getOrderLineByName(eea, orderName, orderLineSequence);
        OrderLineTimeTransfer result = null;
        
        if(eea == null || !eea.hasExecutionErrors()) {
            result = getOrderLineTimeTransfer(eea, userVisit, orderLine, orderTimeTypeName);
        }
        
        return result;
    }

    public OrderLineTimeTransfer getOrderLineTimeTransfer(final ExecutionErrorAccumulator eea, final UserVisit userVisit, final OrderLine orderLine, final String orderTimeTypeName) {
        return OrderLineTimeLogic.getInstance().getOrderLineTimeTransfer(eea, userVisit, orderLine, orderTimeTypeName);
    }

    public List<OrderLineTimeTransfer> getOrderLineTimeTransfersByOrder(final ExecutionErrorAccumulator eea, final UserVisit userVisit, final String orderName, final String orderLineSequence) {
        var orderLine = SalesOrderLineLogic.getInstance().getOrderLineByName(eea, orderName, orderLineSequence);
        
        return OrderLineTimeLogic.getInstance().getOrderLineTimeTransfersByOrder(eea, userVisit, orderLine);
    }

    public void deleteOrderLineTime(final ExecutionErrorAccumulator eea, final String orderName, final String orderLineSequence, final String orderTimeTypeName, final BasePK deletedBy) {
        var orderLine = SalesOrderLineLogic.getInstance().getOrderLineByName(eea, orderName, orderLineSequence);
        
        if(eea == null || !eea.hasExecutionErrors()) {
            deleteOrderLineTime(eea, orderLine, orderTimeTypeName, deletedBy);
        }
    }

    public void deleteOrderLineTime(final ExecutionErrorAccumulator eea, final OrderLine orderLine, final String orderTimeTypeName, final BasePK deletedBy) {
        // TODO: Check Order's status.

        OrderLineTimeLogic.getInstance().deleteOrderLineTime(eea, orderLine, orderTimeTypeName, deletedBy);
    }

}
