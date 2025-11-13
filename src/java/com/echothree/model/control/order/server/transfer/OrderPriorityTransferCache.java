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

import com.echothree.model.control.order.common.transfer.OrderPriorityTransfer;
import com.echothree.model.control.order.server.control.OrderPriorityControl;
import com.echothree.model.data.order.server.entity.OrderPriority;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class OrderPriorityTransferCache
        extends BaseOrderTransferCache<OrderPriority, OrderPriorityTransfer> {

    OrderPriorityControl orderPriorityControl = Session.getModelController(OrderPriorityControl.class);

    /** Creates a new instance of OrderPriorityTransferCache */
    public OrderPriorityTransferCache() {
        super();
        
        setIncludeEntityInstance(true);
    }
    
    public OrderPriorityTransfer getOrderPriorityTransfer(OrderPriority orderPriority) {
        var orderPriorityTransfer = get(orderPriority);
        
        if(orderPriorityTransfer == null) {
            var orderPriorityDetail = orderPriority.getLastDetail();
            var orderPriorityName = orderPriorityDetail.getOrderPriorityName();
            var priority = orderPriorityDetail.getPriority();
            var isDefault = orderPriorityDetail.getIsDefault();
            var sortOrder = orderPriorityDetail.getSortOrder();
            var description = orderPriorityControl.getBestOrderPriorityDescription(orderPriority, getLanguage(userVisit));
            
            orderPriorityTransfer = new OrderPriorityTransfer(orderPriorityName, priority, isDefault, sortOrder, description);
            put(userVisit, orderPriority, orderPriorityTransfer);
        }
        
        return orderPriorityTransfer;
    }
    
}
