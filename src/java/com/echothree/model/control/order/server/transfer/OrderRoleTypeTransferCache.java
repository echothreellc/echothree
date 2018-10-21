// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

import com.echothree.model.control.order.remote.transfer.OrderRoleTypeTransfer;
import com.echothree.model.control.order.server.OrderControl;
import com.echothree.model.data.order.server.entity.OrderRoleType;
import com.echothree.model.data.user.server.entity.UserVisit;

public class OrderRoleTypeTransferCache
        extends BaseOrderTransferCache<OrderRoleType, OrderRoleTypeTransfer> {
    
    /** Creates a new instance of OrderRoleTypeTransferCache */
    public OrderRoleTypeTransferCache(UserVisit userVisit, OrderControl orderControl) {
        super(userVisit, orderControl);
    }
    
    public OrderRoleTypeTransfer getOrderRoleTypeTransfer(OrderRoleType orderRoleType) {
        OrderRoleTypeTransfer orderRoleTypeTransfer = get(orderRoleType);
        
        if(orderRoleTypeTransfer == null) {
            String orderRoleTypeName = orderRoleType.getOrderRoleTypeName();
            Integer sortOrder = orderRoleType.getSortOrder();
            String description = orderControl.getBestOrderRoleTypeDescription(orderRoleType, getLanguage());
            
            orderRoleTypeTransfer = new OrderRoleTypeTransfer(orderRoleTypeName, sortOrder, description);
            put(orderRoleType, orderRoleTypeTransfer);
        }
        
        return orderRoleTypeTransfer;
    }
    
}
