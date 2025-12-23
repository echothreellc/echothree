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

import com.echothree.model.control.order.common.transfer.OrderPriorityDescriptionTransfer;
import com.echothree.model.control.order.server.control.OrderPriorityControl;
import com.echothree.model.data.order.server.entity.OrderPriorityDescription;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class OrderPriorityDescriptionTransferCache
        extends BaseOrderDescriptionTransferCache<OrderPriorityDescription, OrderPriorityDescriptionTransfer> {

    OrderPriorityControl orderPriorityControl = Session.getModelController(OrderPriorityControl.class);

    /** Creates a new instance of OrderPriorityDescriptionTransferCache */
    protected OrderPriorityDescriptionTransferCache() {
        super();
    }
    
    public OrderPriorityDescriptionTransfer getOrderPriorityDescriptionTransfer(UserVisit userVisit, OrderPriorityDescription orderPriorityDescription) {
        var orderPriorityDescriptionTransfer = get(orderPriorityDescription);
        
        if(orderPriorityDescriptionTransfer == null) {
            var orderPriorityTransfer = orderPriorityControl.getOrderPriorityTransfer(userVisit, orderPriorityDescription.getOrderPriority());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, orderPriorityDescription.getLanguage());
            
            orderPriorityDescriptionTransfer = new OrderPriorityDescriptionTransfer(languageTransfer, orderPriorityTransfer, orderPriorityDescription.getDescription());
            put(userVisit, orderPriorityDescription, orderPriorityDescriptionTransfer);
        }
        
        return orderPriorityDescriptionTransfer;
    }
    
}
