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

public class PaymentProcessorActionTransfer
        extends BaseTransfer {

    private PaymentProcessorTransfer paymentProcessorTransfer;
    private PaymentProcessorTypeActionTransfer paymentProcessorTypeActionTransfer;

    /** Creates a new instance of PaymentProcessorActionTransfer */
    public PaymentProcessorActionTransfer(PaymentProcessorTransfer paymentProcessorTransfer,
            PaymentProcessorTypeActionTransfer paymentProcessorTypeActionTransfer) {
        this.paymentProcessorTransfer = paymentProcessorTransfer;
        this.paymentProcessorTypeActionTransfer = paymentProcessorTypeActionTransfer;
    }

    public PaymentProcessorTransfer getPaymentProcessorTransfer() {
        return paymentProcessorTransfer;
    }

    public void setPaymentProcessorTransfer(PaymentProcessorTransfer paymentProcessorTransfer) {
        this.paymentProcessorTransfer = paymentProcessorTransfer;
    }

    public PaymentProcessorTypeActionTransfer getPaymentProcessorTypeActionTransfer() {
        return paymentProcessorTypeActionTransfer;
    }

    public void setPaymentProcessorTypeActionTransfer(PaymentProcessorTypeActionTransfer paymentProcessorTypeActionTransfer) {
        this.paymentProcessorTypeActionTransfer = paymentProcessorTypeActionTransfer;
    }

}
