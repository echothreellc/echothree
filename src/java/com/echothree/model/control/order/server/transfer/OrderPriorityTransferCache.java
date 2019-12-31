// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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
import com.echothree.model.control.order.server.OrderControl;
import com.echothree.model.data.order.server.entity.OrderPriority;
import com.echothree.model.data.order.server.entity.OrderPriorityDetail;
import com.echothree.model.data.user.server.entity.UserVisit;

public class OrderPriorityTransferCache
        extends BaseOrderTransferCache<OrderPriority, OrderPriorityTransfer> {
    
    /** Creates a new instance of OrderPriorityTransferCache */
    public OrderPriorityTransferCache(UserVisit userVisit, OrderControl orderControl) {
        super(userVisit, orderControl);
        
        setIncludeEntityInstance(true);
    }
    
    public OrderPriorityTransfer getOrderPriorityTransfer(OrderPriority orderPriority) {
        OrderPriorityTransfer orderPriorityTransfer = get(orderPriority);
        
        if(orderPriorityTransfer == null) {
            OrderPriorityDetail orderPriorityDetail = orderPriority.getLastDetail();
            String orderPriorityName = orderPriorityDetail.getOrderPriorityName();
            Integer priority = orderPriorityDetail.getPriority();
            Boolean isDefault = orderPriorityDetail.getIsDefault();
            Integer sortOrder = orderPriorityDetail.getSortOrder();
            String description = orderControl.getBestOrderPriorityDescription(orderPriority, getLanguage());
            
            orderPriorityTransfer = new OrderPriorityTransfer(orderPriorityName, priority, isDefault, sortOrder, description);
            put(orderPriority, orderPriorityTransfer);
        }
        
        return orderPriorityTransfer;
    }
    
}
