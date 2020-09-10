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

import com.echothree.model.control.order.common.transfer.OrderTimeTypeTransfer;
import com.echothree.model.control.order.server.control.OrderControl;
import com.echothree.model.data.order.server.entity.OrderTimeType;
import com.echothree.model.data.order.server.entity.OrderTimeTypeDetail;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class OrderTimeTypeTransferCache
        extends BaseOrderTransferCache<OrderTimeType, OrderTimeTypeTransfer> {

    OrderControl orderControl = (OrderControl) Session.getModelController(OrderControl.class);

    /** Creates a new instance of OrderTimeTypeTransferCache */
    public OrderTimeTypeTransferCache(UserVisit userVisit) {
        super(userVisit);
        
        setIncludeEntityInstance(true);
    }
    
    public OrderTimeTypeTransfer getOrderTimeTypeTransfer(OrderTimeType orderTimeType) {
        OrderTimeTypeTransfer orderTimeTypeTransfer = get(orderTimeType);
        
        if(orderTimeTypeTransfer == null) {
            OrderTimeTypeDetail orderTimeTypeDetail = orderTimeType.getLastDetail();
            String orderTimeTypeName = orderTimeTypeDetail.getOrderTimeTypeName();
            Boolean isDefault = orderTimeTypeDetail.getIsDefault();
            Integer sortOrder = orderTimeTypeDetail.getSortOrder();
            String description = orderControl.getBestOrderTimeTypeDescription(orderTimeType, getLanguage());
            
            orderTimeTypeTransfer = new OrderTimeTypeTransfer(orderTimeTypeName, isDefault, sortOrder, description);
            put(orderTimeType, orderTimeTypeTransfer);
        }
        
        return orderTimeTypeTransfer;
    }
    
}
