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

import com.echothree.model.control.order.common.transfer.OrderAdjustmentTypeDescriptionTransfer;
import com.echothree.model.control.order.common.transfer.OrderAdjustmentTypeTransfer;
import com.echothree.model.control.order.server.OrderControl;
import com.echothree.model.control.party.common.transfer.LanguageTransfer;
import com.echothree.model.data.order.server.entity.OrderAdjustmentTypeDescription;
import com.echothree.model.data.user.server.entity.UserVisit;

public class OrderAdjustmentTypeDescriptionTransferCache
        extends BaseOrderDescriptionTransferCache<OrderAdjustmentTypeDescription, OrderAdjustmentTypeDescriptionTransfer> {
    
    /** Creates a new instance of OrderAdjustmentTypeDescriptionTransferCache */
    public OrderAdjustmentTypeDescriptionTransferCache(UserVisit userVisit, OrderControl orderControl) {
        super(userVisit, orderControl);
    }
    
    public OrderAdjustmentTypeDescriptionTransfer getOrderAdjustmentTypeDescriptionTransfer(OrderAdjustmentTypeDescription orderAdjustmentTypeDescription) {
        OrderAdjustmentTypeDescriptionTransfer orderAdjustmentTypeDescriptionTransfer = get(orderAdjustmentTypeDescription);
        
        if(orderAdjustmentTypeDescriptionTransfer == null) {
            OrderAdjustmentTypeTransfer orderAdjustmentTypeTransfer = orderControl.getOrderAdjustmentTypeTransfer(userVisit, orderAdjustmentTypeDescription.getOrderAdjustmentType());
            LanguageTransfer languageTransfer = partyControl.getLanguageTransfer(userVisit, orderAdjustmentTypeDescription.getLanguage());
            
            orderAdjustmentTypeDescriptionTransfer = new OrderAdjustmentTypeDescriptionTransfer(languageTransfer, orderAdjustmentTypeTransfer, orderAdjustmentTypeDescription.getDescription());
            put(orderAdjustmentTypeDescription, orderAdjustmentTypeDescriptionTransfer);
        }
        
        return orderAdjustmentTypeDescriptionTransfer;
    }
    
}
