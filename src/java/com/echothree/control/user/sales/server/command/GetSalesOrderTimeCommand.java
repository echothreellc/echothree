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

import com.echothree.control.user.sales.common.form.GetSalesOrderTimeForm;
import com.echothree.control.user.sales.common.result.SalesResultFactory;
import com.echothree.model.control.order.server.control.OrderTimeControl;
import com.echothree.model.control.order.server.logic.OrderTimeLogic;
import com.echothree.model.control.sales.server.logic.SalesOrderLogic;
import com.echothree.model.data.order.server.entity.OrderTime;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetSalesOrderTimeCommand
        extends BaseSingleEntityCommand<OrderTime, GetSalesOrderTimeForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("OrderName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("OrderTimeTypeName", FieldType.ENTITY_NAME, true, null, null)
        );
    }
    
    @Inject
    OrderTimeControl orderTimeControl;

    @Inject
    OrderTimeLogic orderTimeLogic;

    @Inject
    SalesOrderLogic salesOrderLogic;

    /** Creates a new instance of GetSalesOrderTimeCommand */
    public GetSalesOrderTimeCommand() {
        super(null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected OrderTime getEntity() {
        var orderName = form.getOrderName();
        var order = salesOrderLogic.getOrderByName(this, orderName);
        OrderTime orderTime = null;

        if(!hasExecutionErrors()) {
            var orderTimeTypeName = form.getOrderTimeTypeName();

            orderTime = orderTimeLogic.getOrderTimeEntity(this, order, orderTimeTypeName);
        }

        return orderTime;
    }

    @Override
    protected BaseResult getResult(OrderTime orderTime) {
        var result = SalesResultFactory.getGetSalesOrderTimeResult();

        if(orderTime != null) {
            result.setOrderTime(orderTimeControl.getOrderTimeTransfer(getUserVisit(), orderTime));
        }

        return result;
    }
    
}
