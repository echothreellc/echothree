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

import com.echothree.model.control.order.common.transfer.OrderTimeTypeTransfer;
import com.echothree.model.control.order.server.control.OrderTimeControl;
import com.echothree.model.data.order.server.entity.OrderTimeType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class OrderTimeTypeTransferCache
        extends BaseOrderTransferCache<OrderTimeType, OrderTimeTypeTransfer> {

    OrderTimeControl orderTimeControl = Session.getModelController(OrderTimeControl.class);

    /** Creates a new instance of OrderTimeTypeTransferCache */
    public OrderTimeTypeTransferCache() {
        super();
        
        setIncludeEntityInstance(true);
    }
    
    public OrderTimeTypeTransfer getOrderTimeTypeTransfer(UserVisit userVisit, OrderTimeType orderTimeType) {
        var orderTimeTypeTransfer = get(orderTimeType);
        
        if(orderTimeTypeTransfer == null) {
            var orderTimeTypeDetail = orderTimeType.getLastDetail();
            var orderTimeTypeName = orderTimeTypeDetail.getOrderTimeTypeName();
            var isDefault = orderTimeTypeDetail.getIsDefault();
            var sortOrder = orderTimeTypeDetail.getSortOrder();
            var description = orderTimeControl.getBestOrderTimeTypeDescription(orderTimeType, getLanguage(userVisit));
            
            orderTimeTypeTransfer = new OrderTimeTypeTransfer(orderTimeTypeName, isDefault, sortOrder, description);
            put(userVisit, orderTimeType, orderTimeTypeTransfer);
        }
        
        return orderTimeTypeTransfer;
    }
    
}
