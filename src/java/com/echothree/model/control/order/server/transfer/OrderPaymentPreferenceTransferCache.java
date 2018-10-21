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

import com.echothree.model.control.order.remote.transfer.OrderPaymentPreferenceTransfer;
import com.echothree.model.control.order.server.OrderControl;
import com.echothree.model.control.payment.remote.transfer.PartyPaymentMethodTransfer;
import com.echothree.model.control.payment.remote.transfer.PaymentMethodTransfer;
import com.echothree.model.control.payment.server.PaymentControl;
import com.echothree.model.data.order.server.entity.OrderPaymentPreference;
import com.echothree.model.data.order.server.entity.OrderPaymentPreferenceDetail;
import com.echothree.model.data.payment.server.entity.PartyPaymentMethod;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.string.AmountUtils;

public class OrderPaymentPreferenceTransferCache
        extends BaseOrderTransferCache<OrderPaymentPreference, OrderPaymentPreferenceTransfer> {
    
    PaymentControl paymentControl = (PaymentControl)Session.getModelController(PaymentControl.class);;
    
    /** Creates a new instance of OrderPaymentPreferenceTransferCache */
    public OrderPaymentPreferenceTransferCache(UserVisit userVisit, OrderControl orderControl) {
        super(userVisit, orderControl);
        
        setIncludeEntityInstance(true);
    }
    
    public OrderPaymentPreferenceTransfer getOrderPaymentPreferenceTransfer(OrderPaymentPreference orderPaymentPreference) {
        OrderPaymentPreferenceTransfer orderPaymentPreferenceTransfer = get(orderPaymentPreference);
        
        if(orderPaymentPreferenceTransfer == null) {
            OrderPaymentPreferenceDetail orderPaymentPreferenceDetail = orderPaymentPreference.getLastDetail();
            Integer orderPaymentPreferenceSequence = orderPaymentPreferenceDetail.getOrderPaymentPreferenceSequence();
            PaymentMethodTransfer paymentMethodTransfer = paymentControl.getPaymentMethodTransfer(userVisit, orderPaymentPreferenceDetail.getPaymentMethod());
            PartyPaymentMethod partyPaymentMethod = orderPaymentPreferenceDetail.getPartyPaymentMethod();
            PartyPaymentMethodTransfer partyPaymentMethodTransfer = partyPaymentMethod == null ? null : paymentControl.getPartyPaymentMethodTransfer(userVisit, partyPaymentMethod);
            Boolean wasPresent = orderPaymentPreferenceDetail.getWasPresent();
            Long unformattedMaximumAmount = orderPaymentPreferenceDetail.getMaximumAmount();
            String maximumAmount = AmountUtils.getInstance().formatPriceUnit(orderPaymentPreferenceDetail.getOrder().getLastDetail().getCurrency(), unformattedMaximumAmount);
            Integer sortOrder = orderPaymentPreferenceDetail.getSortOrder();
            
            orderPaymentPreferenceTransfer = new OrderPaymentPreferenceTransfer(orderPaymentPreferenceSequence, paymentMethodTransfer,
                    partyPaymentMethodTransfer, wasPresent, unformattedMaximumAmount, maximumAmount,sortOrder);
            put(orderPaymentPreference, orderPaymentPreferenceTransfer);
        }
        
        return orderPaymentPreferenceTransfer;
    }
    
}
