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

import com.echothree.model.control.order.common.transfer.OrderLineAdjustmentTypeTransfer;
import com.echothree.model.control.order.server.control.OrderControl;
import com.echothree.model.data.order.server.entity.OrderLineAdjustmentType;
import com.echothree.model.data.order.server.entity.OrderLineAdjustmentTypeDetail;
import com.echothree.model.data.user.server.entity.UserVisit;

public class OrderLineAdjustmentTypeTransferCache
        extends BaseOrderTransferCache<OrderLineAdjustmentType, OrderLineAdjustmentTypeTransfer> {
    
    /** Creates a new instance of OrderLineAdjustmentTypeTransferCache */
    public OrderLineAdjustmentTypeTransferCache(UserVisit userVisit, OrderControl orderControl) {
        super(userVisit, orderControl);
        
        setIncludeEntityInstance(true);
    }
    
    public OrderLineAdjustmentTypeTransfer getOrderLineAdjustmentTypeTransfer(OrderLineAdjustmentType orderLineAdjustmentType) {
        OrderLineAdjustmentTypeTransfer orderLineAdjustmentTypeTransfer = get(orderLineAdjustmentType);
        
        if(orderLineAdjustmentTypeTransfer == null) {
            OrderLineAdjustmentTypeDetail orderLineAdjustmentTypeDetail = orderLineAdjustmentType.getLastDetail();
            String orderLineAdjustmentTypeName = orderLineAdjustmentTypeDetail.getOrderLineAdjustmentTypeName();
            Boolean isDefault = orderLineAdjustmentTypeDetail.getIsDefault();
            Integer sortOrder = orderLineAdjustmentTypeDetail.getSortOrder();
            String description = orderControl.getBestOrderLineAdjustmentTypeDescription(orderLineAdjustmentType, getLanguage());
            
            orderLineAdjustmentTypeTransfer = new OrderLineAdjustmentTypeTransfer(orderLineAdjustmentTypeName, isDefault, sortOrder, description);
            put(orderLineAdjustmentType, orderLineAdjustmentTypeTransfer);
        }
        
        return orderLineAdjustmentTypeTransfer;
    }
    
}
