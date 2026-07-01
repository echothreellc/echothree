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

package com.echothree.control.user.sales.server.command;

import com.echothree.control.user.sales.common.form.GetSalesOrderTimesForm;
import com.echothree.control.user.sales.common.result.SalesResultFactory;
import com.echothree.model.control.order.server.control.OrderControl;
import com.echothree.model.control.order.server.control.OrderTimeControl;
import com.echothree.model.control.sales.server.logic.SalesOrderLogic;
import com.echothree.model.data.order.server.entity.Order;
import com.echothree.model.data.order.server.entity.OrderTime;
import com.echothree.model.data.order.server.factory.OrderTimeFactory;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetSalesOrderTimesCommand
        extends BasePaginatedMultipleEntitiesCommand<OrderTime, GetSalesOrderTimesForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("OrderName", FieldType.ENTITY_NAME, true, null, null)
        );
    }
    
    @Inject
    OrderControl orderControl;

    @Inject
    OrderTimeControl orderTimeControl;

    @Inject
    SalesOrderLogic salesOrderLogic;
    
    /** Creates a new instance of GetSalesOrderTimesCommand */
    public GetSalesOrderTimesCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }

    Order order;

    @Override
    protected void handleForm() {
        order = salesOrderLogic.getOrderByName(this, form.getOrderName());
    }

    @Override
    protected Long getTotalEntities() {
        return hasExecutionErrors() ? null : orderTimeControl.countOrderTimesByOrder(order);
    }

    @Override
    protected Collection<OrderTime> getEntities() {
        return hasExecutionErrors() ? null : orderTimeControl.getOrderTimesByOrder(order);
    }

    @Override
    protected BaseResult getResult(Collection<OrderTime> entities) {
        var result = SalesResultFactory.getGetSalesOrderTimesResult();

        if(entities != null) {
            var userVisit = getUserVisit();

            result.setOrder(orderControl.getOrderTransfer(userVisit, order));

            if(session.hasLimit(OrderTimeFactory.class)) {
                result.setOrderTimeCount(getTotalEntities());
            }

            result.setOrderTimes(orderTimeControl.getOrderTimeTransfers(userVisit, entities));
        }

        return result;
    }
    
}
