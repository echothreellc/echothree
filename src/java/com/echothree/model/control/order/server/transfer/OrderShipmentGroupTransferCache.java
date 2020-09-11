// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

import com.echothree.model.control.contact.common.transfer.PartyContactMechanismTransfer;
import com.echothree.model.control.contact.server.ContactControl;
import com.echothree.model.control.item.common.transfer.ItemDeliveryTypeTransfer;
import com.echothree.model.control.item.server.ItemControl;
import com.echothree.model.control.order.common.transfer.OrderShipmentGroupTransfer;
import com.echothree.model.control.shipping.common.transfer.ShippingMethodTransfer;
import com.echothree.model.control.shipping.server.ShippingControl;
import com.echothree.model.data.contact.server.entity.PartyContactMechanism;
import com.echothree.model.data.order.server.entity.OrderShipmentGroup;
import com.echothree.model.data.order.server.entity.OrderShipmentGroupDetail;
import com.echothree.model.data.shipping.server.entity.ShippingMethod;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class OrderShipmentGroupTransferCache
        extends BaseOrderTransferCache<OrderShipmentGroup, OrderShipmentGroupTransfer> {
    
    ContactControl contactControl = (ContactControl)Session.getModelController(ContactControl.class);;
    ItemControl itemControl = (ItemControl)Session.getModelController(ItemControl.class);;
    ShippingControl shippingControl = (ShippingControl)Session.getModelController(ShippingControl.class);;
    
    /** Creates a new instance of OrderShipmentGroupTransferCache */
    public OrderShipmentGroupTransferCache(UserVisit userVisit) {
        super(userVisit);
        
        setIncludeEntityInstance(true);
    }
    
    public OrderShipmentGroupTransfer getOrderShipmentGroupTransfer(OrderShipmentGroup orderShipmentGroup) {
        OrderShipmentGroupTransfer orderShipmentGroupTransfer = get(orderShipmentGroup);
        
        if(orderShipmentGroupTransfer == null) {
            OrderShipmentGroupDetail orderShipmentGroupDetail = orderShipmentGroup.getLastDetail();
            Integer orderShipmentGroupSequence = orderShipmentGroupDetail.getOrderShipmentGroupSequence();
            ItemDeliveryTypeTransfer itemDeliveryTypeTransfer = itemControl.getItemDeliveryTypeTransfer(userVisit, orderShipmentGroupDetail.getItemDeliveryType());
            Boolean isDefault = orderShipmentGroupDetail.getIsDefault();
            PartyContactMechanism partyContactMechanism = orderShipmentGroupDetail.getPartyContactMechanism();
            PartyContactMechanismTransfer partyContactMechanismTransfer = partyContactMechanism == null ? null : contactControl.getPartyContactMechanismTransfer(userVisit, partyContactMechanism);
            ShippingMethod shippingMethod = orderShipmentGroupDetail.getShippingMethod();
            ShippingMethodTransfer shippingMethodTransfer = shippingMethod == null ? null : shippingControl.getShippingMethodTransfer(userVisit, shippingMethod);
            Boolean holdUntilComplete = orderShipmentGroupDetail.getHoldUntilComplete();
            
            orderShipmentGroupTransfer = new OrderShipmentGroupTransfer(orderShipmentGroupSequence, itemDeliveryTypeTransfer, isDefault,
                    partyContactMechanismTransfer, shippingMethodTransfer, holdUntilComplete);
            put(orderShipmentGroup, orderShipmentGroupTransfer);
        }
        
        return orderShipmentGroupTransfer;
    }
    
}
