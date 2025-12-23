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

package com.echothree.model.control.sales.common.transfer;

import com.echothree.model.control.accounting.common.transfer.CurrencyTransfer;
import com.echothree.model.control.batch.common.transfer.BatchTypeTransfer;
import com.echothree.model.control.order.common.transfer.OrderBatchTransfer;
import com.echothree.model.control.payment.common.transfer.PaymentMethodTransfer;
import com.echothree.model.control.workflow.common.transfer.WorkflowEntityStatusTransfer;

public class SalesOrderBatchTransfer
        extends OrderBatchTransfer {
    
    private PaymentMethodTransfer paymentMethod;
    
    /** Creates a new instance of SalesOrderBatchTransfer */
    public SalesOrderBatchTransfer(BatchTypeTransfer batchType, String batchName, CurrencyTransfer currency, PaymentMethodTransfer paymentMethod, Long count,
            String amount, WorkflowEntityStatusTransfer batchStatus) {
        super(batchType, batchName, currency, count, amount, batchStatus);

        this.paymentMethod = paymentMethod;
    }

    /**
     * Returns the paymentMethod.
     * @return the paymentMethod
     */
    public PaymentMethodTransfer getPaymentMethod() {
        return paymentMethod;
    }

    /**
     * Sets the paymentMethod.
     * @param paymentMethod the paymentMethod to set
     */
    public void setPaymentMethod(PaymentMethodTransfer paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
    
}
