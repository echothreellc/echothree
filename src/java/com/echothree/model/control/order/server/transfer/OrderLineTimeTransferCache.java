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

import com.echothree.model.control.order.common.transfer.OrderLineTimeTransfer;
import com.echothree.model.control.order.server.control.OrderTimeControl;
import com.echothree.model.data.order.server.entity.OrderLineTime;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class OrderLineTimeTransferCache
        extends BaseOrderTransferCache<OrderLineTime, OrderLineTimeTransfer> {

    OrderTimeControl orderTimeControl = Session.getModelController(OrderTimeControl.class);

    /** Creates a new instance of OrderLineTimeTransferCache */
    public OrderLineTimeTransferCache(UserVisit userVisit) {
        super(userVisit);
    }
    
    public OrderLineTimeTransfer getOrderLineTimeTransfer(OrderLineTime orderLineTime) {
        var orderLineTimeTransfer = get(orderLineTime);
        
        if(orderLineTimeTransfer == null) {
            var orderTimeType = orderTimeControl.getOrderTimeTypeTransfer(userVisit, orderLineTime.getOrderTimeType());
            var unformattedTime = orderLineTime.getTime();
            var time = formatTypicalDateTime(userVisit, unformattedTime);
            
            orderLineTimeTransfer = new OrderLineTimeTransfer(orderTimeType, unformattedTime, time);
            put(userVisit, orderLineTime, orderLineTimeTransfer);
        }
        
        return orderLineTimeTransfer;
    }
    
}
