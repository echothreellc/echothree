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

import com.echothree.model.control.payment.common.transfer.PaymentProcessorActionTransfer;
import com.echothree.model.control.payment.server.control.PaymentProcessorControl;
import com.echothree.model.control.payment.server.control.PaymentProcessorTypeActionControl;
import com.echothree.model.data.payment.server.entity.PaymentProcessorAction;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class PaymentProcessorActionTransferCache
        extends BasePaymentTransferCache<PaymentProcessorAction, PaymentProcessorActionTransfer> {

    PaymentProcessorControl paymentProcessorControl = Session.getModelController(PaymentProcessorControl.class);
    PaymentProcessorTypeActionControl paymentProcessorTypeActionControl = Session.getModelController(PaymentProcessorTypeActionControl.class);

    /** Creates a new instance of PaymentProcessorTypeTransferCache */
    public PaymentProcessorActionTransferCache() {
        super();
        
        setIncludeEntityInstance(true);
    }
    
    @Override
    public PaymentProcessorActionTransfer getTransfer(PaymentProcessorAction paymentProcessorAction) {
        var paymentProcessorActionTransfer = get(paymentProcessorAction);
        
        if(paymentProcessorActionTransfer == null) {
            var paymentProcessorTransfer = paymentProcessorControl.getPaymentProcessorTransfer(userVisit, paymentProcessorAction.getPaymentProcessor());
            var paymentProcessorTypeActionTransfer = paymentProcessorTypeActionControl.getPaymentProcessorTypeActionTransfer(userVisit, paymentProcessorAction.getPaymentProcessorTypeAction());

            paymentProcessorActionTransfer = new PaymentProcessorActionTransfer(paymentProcessorTransfer,
                    paymentProcessorTypeActionTransfer);
            put(userVisit, paymentProcessorAction, paymentProcessorActionTransfer);
        }
        
        return paymentProcessorActionTransfer;
    }
    
}
