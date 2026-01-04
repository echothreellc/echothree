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

package com.echothree.model.control.order.server.trigger;

import com.echothree.model.control.order.common.OrderTypes;
import com.echothree.model.control.order.server.control.OrderControl;
import com.echothree.model.control.sales.server.trigger.SalesOrderTrigger;
import com.echothree.model.control.workflow.server.trigger.BaseTrigger;
import com.echothree.model.control.workflow.server.trigger.EntityTypeTrigger;
import com.echothree.model.data.order.server.entity.Order;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.workflow.server.entity.WorkflowEntityStatus;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;

public class OrderTrigger
        extends BaseTrigger
        implements EntityTypeTrigger {

    // TODO: configure using a property file.
    private OrderTypeTrigger locateTrigger(final ExecutionErrorAccumulator eea, final Order order) {
        OrderTypeTrigger result = null;
        var orderTypeName = order.getLastDetail().getOrderType().getLastDetail().getOrderTypeName();
        
        if(orderTypeName.equals(OrderTypes.SALES_ORDER.name())) {
            result = new SalesOrderTrigger();
        }
        
        if(result == null) {
            eea.addExecutionError(ExecutionErrors.UnknownOrderTypeTrigger.name(), orderTypeName);
        }
        
        return result;
    }
    
    @Override
    public void handleTrigger(final Session session, final ExecutionErrorAccumulator eea, final WorkflowEntityStatus workflowEntityStatus, final PartyPK triggeredBy) {
        var orderControl = Session.getModelController(OrderControl.class);
        var order = orderControl.convertEntityInstanceToOrderForUpdate(getEntityInstance(workflowEntityStatus));
        var orderTypeTrigger = locateTrigger(eea, order);
        
        if(!eea.hasExecutionErrors()) {
            orderTypeTrigger.handleTrigger(session, eea, workflowEntityStatus, order, triggeredBy);
        }
    }

}
