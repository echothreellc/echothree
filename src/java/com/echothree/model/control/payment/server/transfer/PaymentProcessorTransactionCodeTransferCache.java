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

import com.echothree.model.control.payment.common.transfer.PaymentProcessorTransactionCodeTransfer;
import com.echothree.model.control.payment.server.control.PaymentProcessorTransactionControl;
import com.echothree.model.control.payment.server.control.PaymentProcessorTypeCodeControl;
import com.echothree.model.data.payment.server.entity.PaymentProcessorTransactionCode;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class PaymentProcessorTransactionCodeTransferCache
        extends BasePaymentTransferCache<PaymentProcessorTransactionCode, PaymentProcessorTransactionCodeTransfer> {

    PaymentProcessorTransactionControl paymentProcessorTypeControl = Session.getModelController(PaymentProcessorTransactionControl.class);
    PaymentProcessorTypeCodeControl paymentProcessorTypeCodeControl = Session.getModelController(PaymentProcessorTypeCodeControl.class);

    /** Creates a new instance of PaymentProcessorTypeTransferCache */
    public PaymentProcessorTransactionCodeTransferCache(UserVisit userVisit) {
        super(userVisit);
        
        setIncludeEntityInstance(true);
    }
    
    @Override
    public PaymentProcessorTransactionCodeTransfer getTransfer(PaymentProcessorTransactionCode paymentProcessorTransactionCode) {
        var paymentProcessorTransactionCodeTransfer = get(paymentProcessorTransactionCode);
        
        if(paymentProcessorTransactionCodeTransfer == null) {
            var paymentProcessorTransactionTransfer = paymentProcessorTypeControl.getPaymentProcessorTransactionTransfer(userVisit, paymentProcessorTransactionCode.getPaymentProcessorTransaction());
            var paymentProcessorActionTypeTransfer = paymentProcessorTypeCodeControl.getPaymentProcessorTypeCodeTransfer(userVisit, paymentProcessorTransactionCode.getPaymentProcessorTypeCode());

            paymentProcessorTransactionCodeTransfer = new PaymentProcessorTransactionCodeTransfer(paymentProcessorTransactionTransfer,
                    paymentProcessorActionTypeTransfer);
            put(userVisit, paymentProcessorTransactionCode, paymentProcessorTransactionCodeTransfer);
        }
        
        return paymentProcessorTransactionCodeTransfer;
    }
    
}
