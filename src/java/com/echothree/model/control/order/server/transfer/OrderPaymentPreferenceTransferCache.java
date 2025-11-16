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

import com.echothree.model.control.order.common.transfer.OrderPaymentPreferenceTransfer;
import com.echothree.model.control.payment.server.control.PartyPaymentMethodControl;
import com.echothree.model.control.payment.server.control.PaymentMethodControl;
import com.echothree.model.data.order.server.entity.OrderPaymentPreference;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.string.AmountUtils;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class OrderPaymentPreferenceTransferCache
        extends BaseOrderTransferCache<OrderPaymentPreference, OrderPaymentPreferenceTransfer> {

    PartyPaymentMethodControl partyPaymentMethodControl = Session.getModelController(PartyPaymentMethodControl.class);
    PaymentMethodControl paymentMethodControl = Session.getModelController(PaymentMethodControl.class);

    /** Creates a new instance of OrderPaymentPreferenceTransferCache */
    protected OrderPaymentPreferenceTransferCache() {
        super();
        
        setIncludeEntityInstance(true);
    }
    
    public OrderPaymentPreferenceTransfer getOrderPaymentPreferenceTransfer(UserVisit userVisit, OrderPaymentPreference orderPaymentPreference) {
        var orderPaymentPreferenceTransfer = get(orderPaymentPreference);
        
        if(orderPaymentPreferenceTransfer == null) {
            var orderPaymentPreferenceDetail = orderPaymentPreference.getLastDetail();
            var orderPaymentPreferenceSequence = orderPaymentPreferenceDetail.getOrderPaymentPreferenceSequence();
            var paymentMethodTransfer = paymentMethodControl.getPaymentMethodTransfer(userVisit, orderPaymentPreferenceDetail.getPaymentMethod());
            var partyPaymentMethod = orderPaymentPreferenceDetail.getPartyPaymentMethod();
            var partyPaymentMethodTransfer = partyPaymentMethod == null ? null : partyPaymentMethodControl.getPartyPaymentMethodTransfer(userVisit, partyPaymentMethod);
            var wasPresent = orderPaymentPreferenceDetail.getWasPresent();
            var unformattedMaximumAmount = orderPaymentPreferenceDetail.getMaximumAmount();
            var maximumAmount = AmountUtils.getInstance().formatPriceUnit(orderPaymentPreferenceDetail.getOrder().getLastDetail().getCurrency(), unformattedMaximumAmount);
            var sortOrder = orderPaymentPreferenceDetail.getSortOrder();
            
            orderPaymentPreferenceTransfer = new OrderPaymentPreferenceTransfer(orderPaymentPreferenceSequence, paymentMethodTransfer,
                    partyPaymentMethodTransfer, wasPresent, unformattedMaximumAmount, maximumAmount,sortOrder);
            put(userVisit, orderPaymentPreference, orderPaymentPreferenceTransfer);
        }
        
        return orderPaymentPreferenceTransfer;
    }
    
}
