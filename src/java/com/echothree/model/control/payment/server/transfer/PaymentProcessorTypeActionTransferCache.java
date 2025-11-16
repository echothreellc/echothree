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

package com.echothree.model.control.payment.server.transfer;

import com.echothree.model.control.payment.common.transfer.PaymentProcessorTypeActionTransfer;
import com.echothree.model.control.payment.server.control.PaymentProcessorActionTypeControl;
import com.echothree.model.control.payment.server.control.PaymentProcessorTypeControl;
import com.echothree.model.data.payment.server.entity.PaymentProcessorTypeAction;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class PaymentProcessorTypeActionTransferCache
        extends BasePaymentTransferCache<PaymentProcessorTypeAction, PaymentProcessorTypeActionTransfer> {

    PaymentProcessorTypeControl paymentProcessorTypeControl = Session.getModelController(PaymentProcessorTypeControl.class);
    PaymentProcessorActionTypeControl paymentProcessorActionTypeControl = Session.getModelController(PaymentProcessorActionTypeControl.class);

    /** Creates a new instance of PaymentProcessorTypeTransferCache */
    protected PaymentProcessorTypeActionTransferCache() {
        super();
        
        setIncludeEntityInstance(true);
    }
    
    @Override
    public PaymentProcessorTypeActionTransfer getTransfer(UserVisit userVisit, PaymentProcessorTypeAction paymentProcessorTypeAction) {
        var paymentProcessorTypeActionTransfer = get(paymentProcessorTypeAction);
        
        if(paymentProcessorTypeActionTransfer == null) {
            var paymentProcessorTypeActionDetail = paymentProcessorTypeAction.getLastDetail();
            var paymentProcessorTypeTransfer = paymentProcessorTypeControl.getPaymentProcessorTypeTransfer(userVisit, paymentProcessorTypeActionDetail.getPaymentProcessorType());
            var paymentProcessorActionTypeTransfer = paymentProcessorActionTypeControl.getPaymentProcessorActionTypeTransfer(userVisit, paymentProcessorTypeActionDetail.getPaymentProcessorActionType());
            var isDefault = paymentProcessorTypeActionDetail.getIsDefault();
            var sortOrder = paymentProcessorTypeActionDetail.getSortOrder();
            
            paymentProcessorTypeActionTransfer = new PaymentProcessorTypeActionTransfer(paymentProcessorTypeTransfer,
                    paymentProcessorActionTypeTransfer, isDefault, sortOrder);
            put(userVisit, paymentProcessorTypeAction, paymentProcessorTypeActionTransfer);
        }
        
        return paymentProcessorTypeActionTransfer;
    }
    
}
