// --------------------------------------------------------------------------------
// Copyright 2002-2026 Echo Three, LLC
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

import com.echothree.model.control.payment.common.PaymentOptions;
import com.echothree.model.control.payment.common.transfer.PaymentProcessorTransactionTransfer;
import com.echothree.model.control.payment.server.control.PaymentProcessorActionTypeControl;
import com.echothree.model.control.payment.server.control.PaymentProcessorControl;
import com.echothree.model.control.payment.server.control.PaymentProcessorResultCodeControl;
import com.echothree.model.control.payment.server.control.PaymentProcessorTransactionCodeControl;
import com.echothree.model.data.payment.server.entity.PaymentProcessorTransaction;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.transfer.ListWrapper;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class PaymentProcessorTransactionTransferCache
        extends BasePaymentTransferCache<PaymentProcessorTransaction, PaymentProcessorTransactionTransfer> {

    PaymentProcessorControl paymentProcessorControl = Session.getModelController(PaymentProcessorControl.class);
    PaymentProcessorActionTypeControl paymentProcessorActionTypeControl = Session.getModelController(PaymentProcessorActionTypeControl.class);
    PaymentProcessorResultCodeControl paymentProcessorResultCodeControl = Session.getModelController(PaymentProcessorResultCodeControl.class);
    PaymentProcessorTransactionCodeControl paymentProcessorTransactionCodeControl = Session.getModelController(PaymentProcessorTransactionCodeControl.class);

    boolean includePaymentProcessorTransactionCodes;
    
    /** Creates a new instance of PaymentProcessorTransactionTransferCache */
    protected PaymentProcessorTransactionTransferCache() {
        super();

        var options = session.getOptions();
        if(options != null) {
            includePaymentProcessorTransactionCodes = options.contains(PaymentOptions.PaymentProcessorTransactionIncludePaymentProcessorTransactionCodes);
        }

        setIncludeEntityInstance(true);
    }
    
    @Override
    public PaymentProcessorTransactionTransfer getTransfer(UserVisit userVisit, PaymentProcessorTransaction paymentProcessorTransaction) {
        var paymentProcessorTransactionTransfer = get(paymentProcessorTransaction);
        
        if(paymentProcessorTransactionTransfer == null) {
            var paymentProcessorTransactionDetail = paymentProcessorTransaction.getLastDetail();
            var paymentProcessorTransactionName = paymentProcessorTransactionDetail.getPaymentProcessorTransactionName();
            var paymentProcessor = paymentProcessorControl.getPaymentProcessorTransfer(userVisit, paymentProcessorTransactionDetail.getPaymentProcessor());
            var paymentProcessorActionType = paymentProcessorActionTypeControl.getPaymentProcessorActionTypeTransfer(userVisit, paymentProcessorTransactionDetail.getPaymentProcessorActionType());
            var paymentProcessorResultCode = paymentProcessorResultCodeControl.getPaymentProcessorResultCodeTransfer(userVisit, paymentProcessorTransactionDetail.getPaymentProcessorResultCode());
            
            paymentProcessorTransactionTransfer = new PaymentProcessorTransactionTransfer(paymentProcessorTransactionName,
                    paymentProcessor, paymentProcessorActionType, paymentProcessorResultCode);
            put(userVisit, paymentProcessorTransaction, paymentProcessorTransactionTransfer);

            if(includePaymentProcessorTransactionCodes) {
                paymentProcessorTransactionTransfer.setPaymentProcessorTransactionCodes(new ListWrapper<>(paymentProcessorTransactionCodeControl.getPaymentProcessorTransactionCodeTransfersByPaymentProcessorTransaction(userVisit, paymentProcessorTransaction)));
            }
        }
        
        return paymentProcessorTransactionTransfer;
    }
    
}
