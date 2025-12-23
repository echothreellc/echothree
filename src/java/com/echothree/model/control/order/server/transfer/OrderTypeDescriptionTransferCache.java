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

import com.echothree.model.control.order.common.transfer.OrderTypeDescriptionTransfer;
import com.echothree.model.control.order.server.control.OrderTypeControl;
import com.echothree.model.data.order.server.entity.OrderTypeDescription;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class OrderTypeDescriptionTransferCache
        extends BaseOrderDescriptionTransferCache<OrderTypeDescription, OrderTypeDescriptionTransfer> {

    OrderTypeControl orderTypeControl = Session.getModelController(OrderTypeControl.class);

    /** Creates a new instance of OrderTypeDescriptionTransferCache */
    protected OrderTypeDescriptionTransferCache() {
        super();
    }
    
    public OrderTypeDescriptionTransfer getOrderTypeDescriptionTransfer(UserVisit userVisit, OrderTypeDescription orderTypeDescription) {
        var orderTypeDescriptionTransfer = get(orderTypeDescription);
        
        if(orderTypeDescriptionTransfer == null) {
            var orderTypeTransfer = orderTypeControl.getOrderTypeTransfer(userVisit, orderTypeDescription.getOrderType());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, orderTypeDescription.getLanguage());
            
            orderTypeDescriptionTransfer = new OrderTypeDescriptionTransfer(languageTransfer, orderTypeTransfer, orderTypeDescription.getDescription());
            put(userVisit, orderTypeDescription, orderTypeDescriptionTransfer);
        }
        
        return orderTypeDescriptionTransfer;
    }
    
}
