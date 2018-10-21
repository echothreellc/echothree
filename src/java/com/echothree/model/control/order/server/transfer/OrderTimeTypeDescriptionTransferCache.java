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

import com.echothree.model.control.order.remote.transfer.OrderTimeTypeDescriptionTransfer;
import com.echothree.model.control.order.remote.transfer.OrderTimeTypeTransfer;
import com.echothree.model.control.order.server.OrderControl;
import com.echothree.model.control.party.remote.transfer.LanguageTransfer;
import com.echothree.model.data.order.server.entity.OrderTimeTypeDescription;
import com.echothree.model.data.user.server.entity.UserVisit;

public class OrderTimeTypeDescriptionTransferCache
        extends BaseOrderDescriptionTransferCache<OrderTimeTypeDescription, OrderTimeTypeDescriptionTransfer> {
    
    /** Creates a new instance of OrderTimeTypeDescriptionTransferCache */
    public OrderTimeTypeDescriptionTransferCache(UserVisit userVisit, OrderControl orderControl) {
        super(userVisit, orderControl);
    }
    
    public OrderTimeTypeDescriptionTransfer getOrderTimeTypeDescriptionTransfer(OrderTimeTypeDescription orderTimeTypeDescription) {
        OrderTimeTypeDescriptionTransfer orderTimeTypeDescriptionTransfer = get(orderTimeTypeDescription);
        
        if(orderTimeTypeDescriptionTransfer == null) {
            OrderTimeTypeTransfer orderTimeTypeTransfer = orderControl.getOrderTimeTypeTransfer(userVisit, orderTimeTypeDescription.getOrderTimeType());
            LanguageTransfer languageTransfer = partyControl.getLanguageTransfer(userVisit, orderTimeTypeDescription.getLanguage());
            
            orderTimeTypeDescriptionTransfer = new OrderTimeTypeDescriptionTransfer(languageTransfer, orderTimeTypeTransfer, orderTimeTypeDescription.getDescription());
            put(orderTimeTypeDescription, orderTimeTypeDescriptionTransfer);
        }
        
        return orderTimeTypeDescriptionTransfer;
    }
    
}
