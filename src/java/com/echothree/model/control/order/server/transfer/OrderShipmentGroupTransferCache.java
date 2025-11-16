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

import com.echothree.model.control.contact.server.control.ContactControl;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.order.common.transfer.OrderShipmentGroupTransfer;
import com.echothree.model.control.shipping.server.control.ShippingControl;
import com.echothree.model.data.order.server.entity.OrderShipmentGroup;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class OrderShipmentGroupTransferCache
        extends BaseOrderTransferCache<OrderShipmentGroup, OrderShipmentGroupTransfer> {
    
    ContactControl contactControl = Session.getModelController(ContactControl.class);;
    ItemControl itemControl = Session.getModelController(ItemControl.class);;
    ShippingControl shippingControl = Session.getModelController(ShippingControl.class);;
    
    /** Creates a new instance of OrderShipmentGroupTransferCache */
    public OrderShipmentGroupTransferCache() {
        super();
        
        setIncludeEntityInstance(true);
    }
    
    public OrderShipmentGroupTransfer getOrderShipmentGroupTransfer(UserVisit userVisit, OrderShipmentGroup orderShipmentGroup) {
        var orderShipmentGroupTransfer = get(orderShipmentGroup);
        
        if(orderShipmentGroupTransfer == null) {
            var orderShipmentGroupDetail = orderShipmentGroup.getLastDetail();
            var orderShipmentGroupSequence = orderShipmentGroupDetail.getOrderShipmentGroupSequence();
            var itemDeliveryTypeTransfer = itemControl.getItemDeliveryTypeTransfer(userVisit, orderShipmentGroupDetail.getItemDeliveryType());
            var isDefault = orderShipmentGroupDetail.getIsDefault();
            var partyContactMechanism = orderShipmentGroupDetail.getPartyContactMechanism();
            var partyContactMechanismTransfer = partyContactMechanism == null ? null : contactControl.getPartyContactMechanismTransfer(userVisit, partyContactMechanism);
            var shippingMethod = orderShipmentGroupDetail.getShippingMethod();
            var shippingMethodTransfer = shippingMethod == null ? null : shippingControl.getShippingMethodTransfer(userVisit, shippingMethod);
            var holdUntilComplete = orderShipmentGroupDetail.getHoldUntilComplete();
            
            orderShipmentGroupTransfer = new OrderShipmentGroupTransfer(orderShipmentGroupSequence, itemDeliveryTypeTransfer, isDefault,
                    partyContactMechanismTransfer, shippingMethodTransfer, holdUntilComplete);
            put(userVisit, orderShipmentGroup, orderShipmentGroupTransfer);
        }
        
        return orderShipmentGroupTransfer;
    }
    
}
