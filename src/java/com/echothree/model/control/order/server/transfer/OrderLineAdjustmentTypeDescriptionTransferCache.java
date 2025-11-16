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

import com.echothree.model.control.order.common.transfer.OrderLineAdjustmentTypeDescriptionTransfer;
import com.echothree.model.control.order.server.control.OrderLineAdjustmentControl;
import com.echothree.model.data.order.server.entity.OrderLineAdjustmentTypeDescription;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class OrderLineAdjustmentTypeDescriptionTransferCache
        extends BaseOrderDescriptionTransferCache<OrderLineAdjustmentTypeDescription, OrderLineAdjustmentTypeDescriptionTransfer> {

    OrderLineAdjustmentControl orderLineAdjustmentControl = Session.getModelController(OrderLineAdjustmentControl.class);

    /** Creates a new instance of OrderLineAdjustmentTypeDescriptionTransferCache */
    protected OrderLineAdjustmentTypeDescriptionTransferCache() {
        super();
    }
    
    public OrderLineAdjustmentTypeDescriptionTransfer getOrderLineAdjustmentTypeDescriptionTransfer(UserVisit userVisit, OrderLineAdjustmentTypeDescription orderLineAdjustmentTypeDescription) {
        var orderLineAdjustmentTypeDescriptionTransfer = get(orderLineAdjustmentTypeDescription);
        
        if(orderLineAdjustmentTypeDescriptionTransfer == null) {
            var orderLineAdjustmentTypeTransfer = orderLineAdjustmentControl.getOrderLineAdjustmentTypeTransfer(userVisit, orderLineAdjustmentTypeDescription.getOrderLineAdjustmentType());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, orderLineAdjustmentTypeDescription.getLanguage());
            
            orderLineAdjustmentTypeDescriptionTransfer = new OrderLineAdjustmentTypeDescriptionTransfer(languageTransfer, orderLineAdjustmentTypeTransfer, orderLineAdjustmentTypeDescription.getDescription());
            put(userVisit, orderLineAdjustmentTypeDescription, orderLineAdjustmentTypeDescriptionTransfer);
        }
        
        return orderLineAdjustmentTypeDescriptionTransfer;
    }
    
}
