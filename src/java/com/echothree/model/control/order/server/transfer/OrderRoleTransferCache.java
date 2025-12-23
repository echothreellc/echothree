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

import com.echothree.model.control.order.common.transfer.OrderRoleTransfer;
import com.echothree.model.control.order.server.control.OrderRoleControl;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.order.server.entity.OrderRole;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class OrderRoleTransferCache
        extends BaseOrderTransferCache<OrderRole, OrderRoleTransfer> {

    OrderRoleControl orderRoleControl = Session.getModelController(OrderRoleControl.class);
    PartyControl partyControl = Session.getModelController(PartyControl.class);

    /** Creates a new instance of OrderRoleTransferCache */
    protected OrderRoleTransferCache() {
        super();

        setIncludeEntityInstance(true);
    }
    
    public OrderRoleTransfer getOrderRoleTransfer(UserVisit userVisit, OrderRole orderRole) {
        var orderRoleTransfer = get(orderRole);
        
        if(orderRoleTransfer == null) {
            var party = partyControl.getPartyTransfer(userVisit, orderRole.getParty());
            var orderRoleType = orderRoleControl.getOrderRoleTypeTransfer(userVisit, orderRole.getOrderRoleType());

            orderRoleTransfer = new OrderRoleTransfer(party, orderRoleType);
            put(userVisit, orderRole, orderRoleTransfer);
        }
        
        return orderRoleTransfer;
    }
    
}
