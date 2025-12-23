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

import com.echothree.model.control.order.common.transfer.OrderAliasTransfer;
import com.echothree.model.control.order.server.control.OrderAliasControl;
import com.echothree.model.data.order.server.entity.OrderAlias;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class OrderAliasTransferCache
        extends BaseOrderTransferCache<OrderAlias, OrderAliasTransfer> {

    OrderAliasControl orderAliasControl = Session.getModelController(OrderAliasControl.class);

    /** Creates a new instance of OrderAliasTransferCache */
    protected OrderAliasTransferCache() {
        super();
        
        setIncludeEntityInstance(true);
    }
    
    public OrderAliasTransfer getOrderAliasTransfer(UserVisit userVisit, OrderAlias orderAlias) {
        var orderAliasTransfer = get(orderAlias);
        
        if(orderAliasTransfer == null) {
            var orderAliasType = orderAliasControl.getOrderAliasTypeTransfer(userVisit, orderAlias.getOrderAliasType());
            var alias = orderAlias.getAlias();
            
            orderAliasTransfer = new OrderAliasTransfer(orderAliasType, alias);
            put(userVisit, orderAlias, orderAliasTransfer);
        }
        
        return orderAliasTransfer;
    }
    
}
