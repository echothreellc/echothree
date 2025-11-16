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

import com.echothree.model.control.order.common.transfer.OrderAliasTypeTransfer;
import com.echothree.model.control.order.server.control.OrderAliasControl;
import com.echothree.model.control.order.server.control.OrderTypeControl;
import com.echothree.model.data.order.server.entity.OrderAliasType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class OrderAliasTypeTransferCache
        extends BaseOrderTransferCache<OrderAliasType, OrderAliasTypeTransfer> {

    OrderTypeControl orderTypeControl = Session.getModelController(OrderTypeControl.class);
    OrderAliasControl orderAliasControl = Session.getModelController(OrderAliasControl.class);

    /** Creates a new instance of OrderAliasTypeTransferCache */
    protected OrderAliasTypeTransferCache() {
        super();
        
        setIncludeEntityInstance(true);
    }
    
    public OrderAliasTypeTransfer getOrderAliasTypeTransfer(UserVisit userVisit, OrderAliasType orderAliasType) {
        var orderAliasTypeTransfer = get(orderAliasType);
        
        if(orderAliasTypeTransfer == null) {
            var orderAliasTypeDetail = orderAliasType.getLastDetail();
            var orderType = orderTypeControl.getOrderTypeTransfer(userVisit, orderAliasTypeDetail.getOrderType());
            var orderAliasTypeName = orderAliasTypeDetail.getOrderAliasTypeName();
            var validationPattern = orderAliasTypeDetail.getValidationPattern();
            var isDefault = orderAliasTypeDetail.getIsDefault();
            var sortOrder = orderAliasTypeDetail.getSortOrder();
            var description = orderAliasControl.getBestOrderAliasTypeDescription(orderAliasType, getLanguage(userVisit));
            
            orderAliasTypeTransfer = new OrderAliasTypeTransfer(orderType, orderAliasTypeName, validationPattern, isDefault, sortOrder, description);
            put(userVisit, orderAliasType, orderAliasTypeTransfer);
        }
        
        return orderAliasTypeTransfer;
    }
    
}
