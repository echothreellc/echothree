// --------------------------------------------------------------------------------
// Copyright 2002-2024 Echo Three, LLC
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
import com.echothree.model.control.sequence.common.transfer.SequenceTypeTransfer;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.control.workflow.common.transfer.WorkflowEntranceTransfer;
import com.echothree.model.control.workflow.common.transfer.WorkflowTransfer;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.order.server.entity.OrderType;
import com.echothree.model.data.order.server.entity.OrderTypeDetail;
import com.echothree.model.data.sequence.server.entity.SequenceType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.workflow.server.entity.Workflow;
import com.echothree.model.data.workflow.server.entity.WorkflowEntrance;
import com.echothree.util.server.persistence.Session;

public class OrderTypeTransferCache
        extends BaseOrderTransferCache<OrderType, OrderTypeTransfer> {

    OrderTypeControl orderTypeControl = Session.getModelController(OrderTypeControl.class);
    SequenceControl sequenceControl = Session.getModelController(SequenceControl.class);
    WorkflowControl workflowControl = Session.getModelController(WorkflowControl.class);
    
    /** Creates a new instance of OrderTypeTransferCache */
    public OrderTypeTransferCache(UserVisit userVisit) {
        super(userVisit);
        
        setIncludeEntityInstance(true);
    }
    
    public OrderTypeTransfer getOrderTypeTransfer(OrderType orderType) {
        OrderTypeTransfer orderTypeTransfer = get(orderType);
        
        if(orderTypeTransfer == null) {
            OrderTypeDetail orderTypeDetail = orderType.getLastDetail();
            String orderTypeName = orderTypeDetail.getOrderTypeName();
            OrderType parentOrderType = orderTypeDetail.getParentOrderType();
            OrderTypeTransfer parentOrderTypeTransfer = parentOrderType == null? null: getOrderTypeTransfer(parentOrderType);
            SequenceType orderSequenceType = orderTypeDetail.getOrderSequenceType();
            SequenceTypeTransfer orderSequenceTypeTransfer = orderSequenceType == null? null: sequenceControl.getSequenceTypeTransfer(userVisit, orderSequenceType);
            Workflow orderWorkflow = orderTypeDetail.getOrderWorkflow();
            WorkflowTransfer orderWorkflowTransfer = orderWorkflow == null? null: workflowControl.getWorkflowTransfer(userVisit, orderWorkflow);
            WorkflowEntrance orderWorkflowEntrance = orderTypeDetail.getOrderWorkflowEntrance();
            WorkflowEntranceTransfer orderWorkflowEntranceTransfer = orderWorkflowEntrance == null? null: workflowControl.getWorkflowEntranceTransfer(userVisit, orderWorkflowEntrance);
            Boolean isDefault = orderTypeDetail.getIsDefault();
            Integer sortOrder = orderTypeDetail.getSortOrder();
            String description = orderTypeControl.getBestOrderTypeDescription(orderType, getLanguage());
            
            orderTypeTransfer = new OrderTypeTransfer(orderTypeName, parentOrderTypeTransfer, orderSequenceTypeTransfer, orderWorkflowTransfer,
                    orderWorkflowEntranceTransfer, isDefault, sortOrder, description);
            put(orderType, orderTypeTransfer);
        }
        
        return orderTypeTransfer;
    }
    
}
