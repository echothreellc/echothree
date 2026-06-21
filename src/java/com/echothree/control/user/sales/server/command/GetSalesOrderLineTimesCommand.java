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

import com.echothree.control.user.sales.common.form.GetSalesOrderLineTimesForm;
import com.echothree.control.user.sales.common.result.SalesResultFactory;
import com.echothree.model.control.order.server.control.OrderTimeControl;
import com.echothree.model.control.sales.server.logic.SalesOrderLineLogic;
import com.echothree.model.data.order.server.entity.OrderLine;
import com.echothree.model.data.order.server.entity.OrderLineTime;
import com.echothree.model.data.order.server.factory.OrderLineTimeFactory;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetSalesOrderLineTimesCommand
        extends BasePaginatedMultipleEntitiesCommand<OrderLineTime, GetSalesOrderLineTimesForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("OrderName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("OrderLineSequence", FieldType.UNSIGNED_INTEGER, true, null, null)
        );
    }
    
    @Inject
    OrderTimeControl orderTimeControl;

    @Inject
    SalesOrderLineLogic salesOrderLineLogic;

    /** Creates a new instance of GetSalesOrderLineTimesCommand */
    public GetSalesOrderLineTimesCommand() {
        super(null, FORM_FIELD_DEFINITIONS, false);
    }

    OrderLine orderLine;

    @Override
    protected void handleForm() {
        var orderName = form.getOrderName();
        var orderLineSequence = form.getOrderLineSequence();

        orderLine = salesOrderLineLogic.getOrderLineByName(this, orderName, orderLineSequence);
    }

    @Override
    protected Long getTotalEntities() {
        return hasExecutionErrors() ? null : orderTimeControl.countOrderLineTimesByOrderLine(orderLine);
    }

    @Override
    protected Collection<OrderLineTime> getEntities() {
        return hasExecutionErrors() ? null : orderTimeControl.getOrderLineTimesByOrderLine(orderLine);
    }

    @Override
    protected BaseResult getResult(Collection<OrderLineTime> entities) {
        var result = SalesResultFactory.getGetSalesOrderLineTimesResult();

        if(entities != null) {
            var userVisit = getUserVisit();

            if(session.hasLimit(OrderLineTimeFactory.class)) {
                result.setOrderLineTimeCount(getTotalEntities());
            }

            result.setOrderLineTimes(orderTimeControl.getOrderLineTimeTransfers(userVisit, entities));
        }

        return result;
    }

}
