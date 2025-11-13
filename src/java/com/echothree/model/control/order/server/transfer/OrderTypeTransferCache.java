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

package com.echothree.model.control.order.server.transfer;

import com.echothree.model.control.order.common.transfer.OrderTypeTransfer;
import com.echothree.model.control.order.server.control.OrderTypeControl;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.order.server.entity.OrderType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class OrderTypeTransferCache
        extends BaseOrderTransferCache<OrderType, OrderTypeTransfer> {

    OrderTypeControl orderTypeControl = Session.getModelController(OrderTypeControl.class);
    SequenceControl sequenceControl = Session.getModelController(SequenceControl.class);
    WorkflowControl workflowControl = Session.getModelController(WorkflowControl.class);
    
    /** Creates a new instance of OrderTypeTransferCache */
    public OrderTypeTransferCache() {
        super();
        
        setIncludeEntityInstance(true);
    }
    
    public OrderTypeTransfer getOrderTypeTransfer(OrderType orderType) {
        var orderTypeTransfer = get(orderType);
        
        if(orderTypeTransfer == null) {
            var orderTypeDetail = orderType.getLastDetail();
            var orderTypeName = orderTypeDetail.getOrderTypeName();
            var orderSequenceType = orderTypeDetail.getOrderSequenceType();
            var orderSequenceTypeTransfer = orderSequenceType == null? null: sequenceControl.getSequenceTypeTransfer(userVisit, orderSequenceType);
            var orderWorkflow = orderTypeDetail.getOrderWorkflow();
            var orderWorkflowTransfer = orderWorkflow == null? null: workflowControl.getWorkflowTransfer(userVisit, orderWorkflow);
            var orderWorkflowEntrance = orderTypeDetail.getOrderWorkflowEntrance();
            var orderWorkflowEntranceTransfer = orderWorkflowEntrance == null? null: workflowControl.getWorkflowEntranceTransfer(userVisit, orderWorkflowEntrance);
            var isDefault = orderTypeDetail.getIsDefault();
            var sortOrder = orderTypeDetail.getSortOrder();
            var description = orderTypeControl.getBestOrderTypeDescription(orderType, getLanguage(userVisit));
            
            orderTypeTransfer = new OrderTypeTransfer(orderTypeName, orderSequenceTypeTransfer, orderWorkflowTransfer,
                    orderWorkflowEntranceTransfer, isDefault, sortOrder, description);
            put(userVisit, orderType, orderTypeTransfer);
        }
        
        return orderTypeTransfer;
    }
    
}
