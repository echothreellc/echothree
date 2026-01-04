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

package com.echothree.model.control.order.server.transfer;

import com.echothree.model.control.order.common.transfer.OrderAdjustmentTypeTransfer;
import com.echothree.model.control.order.server.control.OrderAdjustmentControl;
import com.echothree.model.data.order.server.entity.OrderAdjustmentType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class OrderAdjustmentTypeTransferCache
        extends BaseOrderTransferCache<OrderAdjustmentType, OrderAdjustmentTypeTransfer> {

    OrderAdjustmentControl orderAdjustmentControl = Session.getModelController(OrderAdjustmentControl.class);

    /** Creates a new instance of OrderAdjustmentTypeTransferCache */
    protected OrderAdjustmentTypeTransferCache() {
        super();
        
        setIncludeEntityInstance(true);
    }
    
    public OrderAdjustmentTypeTransfer getOrderAdjustmentTypeTransfer(UserVisit userVisit, OrderAdjustmentType orderAdjustmentType) {
        var orderAdjustmentTypeTransfer = get(orderAdjustmentType);
        
        if(orderAdjustmentTypeTransfer == null) {
            var orderAdjustmentTypeDetail = orderAdjustmentType.getLastDetail();
            var orderAdjustmentTypeName = orderAdjustmentTypeDetail.getOrderAdjustmentTypeName();
            var isDefault = orderAdjustmentTypeDetail.getIsDefault();
            var sortOrder = orderAdjustmentTypeDetail.getSortOrder();
            var description = orderAdjustmentControl.getBestOrderAdjustmentTypeDescription(orderAdjustmentType, getLanguage(userVisit));
            
            orderAdjustmentTypeTransfer = new OrderAdjustmentTypeTransfer(orderAdjustmentTypeName, isDefault, sortOrder, description);
            put(userVisit, orderAdjustmentType, orderAdjustmentTypeTransfer);
        }
        
        return orderAdjustmentTypeTransfer;
    }
    
}
