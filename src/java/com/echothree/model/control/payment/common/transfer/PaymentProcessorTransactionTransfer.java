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
import com.echothree.util.common.transfer.ListWrapper;

public class PaymentProcessorTransactionTransfer
        extends BaseTransfer {

    private String paymentProcessorActionTypeName;
    private PaymentProcessorTransfer paymentProcessor;
    private PaymentProcessorActionTypeTransfer paymentProcessorActionType;
    private PaymentProcessorResultCodeTransfer paymentProcessorResultCode;

    private ListWrapper<PaymentProcessorTransactionCodeTransfer> paymentProcessorTransactionCodes;

    /** Creates a new instance of PaymentProcessorTransactionTransfer */
    public PaymentProcessorTransactionTransfer(String paymentProcessorActionTypeName, PaymentProcessorTransfer paymentProcessor,
            PaymentProcessorActionTypeTransfer paymentProcessorActionType, PaymentProcessorResultCodeTransfer paymentProcessorResultCode) {
        this.paymentProcessorActionTypeName = paymentProcessorActionTypeName;
        this.paymentProcessor = paymentProcessor;
        this.paymentProcessorActionType = paymentProcessorActionType;
        this.paymentProcessorResultCode = paymentProcessorResultCode;
    }

    public String getPaymentProcessorActionTypeName() {
        return paymentProcessorActionTypeName;
    }

    public void setPaymentProcessorActionTypeName(String paymentProcessorActionTypeName) {
        this.paymentProcessorActionTypeName = paymentProcessorActionTypeName;
    }

    public PaymentProcessorTransfer getPaymentProcessor() {
        return paymentProcessor;
    }

    public void setPaymentProcessor(PaymentProcessorTransfer paymentProcessor) {
        this.paymentProcessor = paymentProcessor;
    }

    public PaymentProcessorActionTypeTransfer getPaymentProcessorActionType() {
        return paymentProcessorActionType;
    }

    public void setPaymentProcessorActionType(PaymentProcessorActionTypeTransfer paymentProcessorActionType) {
        this.paymentProcessorActionType = paymentProcessorActionType;
    }

    public PaymentProcessorResultCodeTransfer getPaymentProcessorResultCode() {
        return paymentProcessorResultCode;
    }

    public void setPaymentProcessorResultCode(PaymentProcessorResultCodeTransfer paymentProcessorResultCode) {
        this.paymentProcessorResultCode = paymentProcessorResultCode;
    }

    public ListWrapper<PaymentProcessorTransactionCodeTransfer> getPaymentProcessorTransactionCodes() {
        return paymentProcessorTransactionCodes;
    }

    public void setPaymentProcessorTransactionCodes(final ListWrapper<PaymentProcessorTransactionCodeTransfer> paymentProcessorTransactionCodes) {
        this.paymentProcessorTransactionCodes = paymentProcessorTransactionCodes;
    }

}
