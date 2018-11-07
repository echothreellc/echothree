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

import com.echothree.model.control.order.common.transfer.OrderAliasTransfer;
import com.echothree.model.control.order.common.transfer.OrderAliasTypeTransfer;
import com.echothree.model.control.order.server.OrderControl;
import com.echothree.model.data.order.server.entity.OrderAlias;
import com.echothree.model.data.user.server.entity.UserVisit;

public class OrderAliasTransferCache
        extends BaseOrderTransferCache<OrderAlias, OrderAliasTransfer> {
    
    /** Creates a new instance of OrderAliasTransferCache */
    public OrderAliasTransferCache(UserVisit userVisit, OrderControl orderControl) {
        super(userVisit, orderControl);
        
        setIncludeEntityInstance(true);
    }
    
    public OrderAliasTransfer getOrderAliasTransfer(OrderAlias orderAlias) {
        OrderAliasTransfer orderAliasTransfer = get(orderAlias);
        
        if(orderAliasTransfer == null) {
            OrderAliasTypeTransfer orderAliasType = orderControl.getOrderAliasTypeTransfer(userVisit, orderAlias.getOrderAliasType());
            String alias = orderAlias.getAlias();
            
            orderAliasTransfer = new OrderAliasTransfer(orderAliasType, alias);
            put(orderAlias, orderAliasTransfer);
        }
        
        return orderAliasTransfer;
    }
    
}
