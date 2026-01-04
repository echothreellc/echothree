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

import com.echothree.control.user.sales.common.edit.SalesEditFactory;
import com.echothree.control.user.sales.common.edit.SalesOrderTimeEdit;
import com.echothree.control.user.sales.common.form.EditSalesOrderTimeForm;
import com.echothree.control.user.sales.common.result.EditSalesOrderTimeResult;
import com.echothree.control.user.sales.common.result.SalesResultFactory;
import com.echothree.control.user.sales.common.spec.SalesOrderTimeSpec;
import com.echothree.model.control.order.server.control.OrderTimeControl;
import com.echothree.model.control.sales.server.logic.SalesOrderLogic;
import com.echothree.model.data.order.server.entity.Order;
import com.echothree.model.data.order.server.entity.OrderTime;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.string.DateUtils;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class EditSalesOrderTimeCommand
        extends BaseAbstractEditCommand<SalesOrderTimeSpec, SalesOrderTimeEdit, EditSalesOrderTimeResult, OrderTime, Order> {

    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("OrderName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("OrderTimeTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Time", FieldType.DATE_TIME, true, null, null)
                ));
    }

    /** Creates a new instance of EditSalesOrderTimeCommand */
    public EditSalesOrderTimeCommand() {
        super(null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditSalesOrderTimeResult getResult() {
        return SalesResultFactory.getEditSalesOrderTimeResult();
    }

    @Override
    public SalesOrderTimeEdit getEdit() {
        return SalesEditFactory.getSalesOrderTimeEdit();
    }

    @Override
    public OrderTime getEntity(EditSalesOrderTimeResult result) {
        var orderName = spec.getOrderName();
        var order = SalesOrderLogic.getInstance().getOrderByName(this, orderName);
        OrderTime orderTime = null;
        
        if(!hasExecutionErrors()) {
            var orderTimeControl = Session.getModelController(OrderTimeControl.class);
            var orderType = order.getLastDetail().getOrderType();
            var orderTimeTypeName = spec.getOrderTimeTypeName();
            var orderTimeType = orderTimeControl.getOrderTimeTypeByName(orderType, orderTimeTypeName);

            if(orderTimeType != null) {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    orderTime = orderTimeControl.getOrderTime(order, orderTimeType);
                } else { // EditMode.UPDATE
                    orderTime = orderTimeControl.getOrderTimeForUpdate(order, orderTimeType);
                }

                if(orderTime == null) {
                    addExecutionError(ExecutionErrors.UnknownOrderTime.name(), orderType.getLastDetail().getOrderTypeName(), orderName, orderTimeTypeName);
                }
            }
        }
        
        return orderTime;
    }

    @Override
    public Order getLockEntity(OrderTime orderTime) {
        return orderTime.getOrder();
    }

    @Override
    public void fillInResult(EditSalesOrderTimeResult result, OrderTime orderTime) {
        var orderTimeControl = Session.getModelController(OrderTimeControl.class);

        result.setOrderTime(orderTimeControl.getOrderTimeTransfer(getUserVisit(), orderTime));
    }

    @Override
    public void doLock(SalesOrderTimeEdit edit, OrderTime orderTime) {
        edit.setTime(DateUtils.getInstance().formatTypicalDateTime(getUserVisit(), getPreferredDateTimeFormat(), orderTime.getTime()));
    }

    @Override
    public void doUpdate(OrderTime orderTime) {
        var orderTimeControl = Session.getModelController(OrderTimeControl.class);
        var orderTimeValue = orderTimeControl.getOrderTimeValue(orderTime);
        var time = Long.valueOf(edit.getTime());
        
        orderTimeValue.setTime(time);

        orderTimeControl.updateOrderTimeFromValue(orderTimeValue, getPartyPK());
    }

}
