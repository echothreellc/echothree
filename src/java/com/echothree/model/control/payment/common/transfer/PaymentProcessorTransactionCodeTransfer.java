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

package com.echothree.model.control.payment.common.transfer;

import com.echothree.util.common.transfer.BaseTransfer;

public class PaymentProcessorTransactionCodeTransfer
        extends BaseTransfer {
    
    private PaymentProcessorTransactionTransfer paymentProcessorTransaction;
    private PaymentProcessorTypeCodeTransfer paymentProcessorTypeCode;

    /** Creates a new instance of PaymentProcessorTransactionCodeTransfer */
    public PaymentProcessorTransactionCodeTransfer(PaymentProcessorTransactionTransfer paymentProcessorTransaction,
            PaymentProcessorTypeCodeTransfer paymentProcessorTypeCode) {
        this.paymentProcessorTransaction = paymentProcessorTransaction;
        this.paymentProcessorTypeCode = paymentProcessorTypeCode;
    }

    public PaymentProcessorTransactionTransfer getPaymentProcessorTransaction() {
        return paymentProcessorTransaction;
    }

    public void setPaymentProcessorTransaction(PaymentProcessorTransactionTransfer paymentProcessorTransaction) {
        this.paymentProcessorTransaction = paymentProcessorTransaction;
    }

    public PaymentProcessorTypeCodeTransfer getPaymentProcessorTypeCode() {
        return paymentProcessorTypeCode;
    }

    public void setPaymentProcessorTypeCode(PaymentProcessorTypeCodeTransfer paymentProcessorTypeCode) {
        this.paymentProcessorTypeCode = paymentProcessorTypeCode;
    }

}
