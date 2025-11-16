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

import com.echothree.model.control.comment.common.CommentConstants;
import com.echothree.model.control.payment.common.PaymentOptions;
import com.echothree.model.control.payment.common.transfer.PaymentProcessorTransfer;
import com.echothree.model.control.payment.server.control.PaymentProcessorControl;
import com.echothree.model.control.payment.server.control.PaymentProcessorTransactionControl;
import com.echothree.model.control.payment.server.control.PaymentProcessorTypeControl;
import com.echothree.model.data.payment.server.entity.PaymentProcessor;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.transfer.ListWrapper;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class PaymentProcessorTransferCache
        extends BasePaymentTransferCache<PaymentProcessor, PaymentProcessorTransfer> {

    PaymentProcessorControl paymentProcessorControl = Session.getModelController(PaymentProcessorControl.class);
    PaymentProcessorTypeControl paymentProcessorTypeControl = Session.getModelController(PaymentProcessorTypeControl.class);
    PaymentProcessorTransactionControl paymentProcessorTransactionControl = Session.getModelController(PaymentProcessorTransactionControl.class);

    boolean includeComments;
    boolean includePaymentProcessorTransactions;

    /** Creates a new instance of PaymentProcessorTransferCache */
    protected PaymentProcessorTransferCache() {
        super();

        var options = session.getOptions();
        if(options != null) {
            setIncludeUuid(options.contains(PaymentOptions.PaymentProcessorIncludeUuid));
            includeComments = options.contains(PaymentOptions.PaymentProcessorIncludeComments);
            includePaymentProcessorTransactions = options.contains(PaymentOptions.PaymentProcessorIncludePaymentProcessorTransactions);
        }
        
        setIncludeEntityInstance(true);
    }

    @Override
    public PaymentProcessorTransfer getTransfer(UserVisit userVisit, PaymentProcessor paymentProcessor) {
        var paymentProcessorTransfer = get(paymentProcessor);
        
        if(paymentProcessorTransfer == null) {
            var paymentProcessorDetail = paymentProcessor.getLastDetail();
            var paymentProcessorName = paymentProcessorDetail.getPaymentProcessorName();
            var paymentProcessorType = paymentProcessorTypeControl.getPaymentProcessorTypeTransfer(userVisit, paymentProcessorDetail.getPaymentProcessorType());
            var isDefault = paymentProcessorDetail.getIsDefault();
            var sortOrder = paymentProcessorDetail.getSortOrder();
            var description = paymentProcessorControl.getBestPaymentProcessorDescription(paymentProcessor, getLanguage(userVisit));
            
            paymentProcessorTransfer = new PaymentProcessorTransfer(paymentProcessorName, paymentProcessorType, isDefault, sortOrder, description);
            put(userVisit, paymentProcessor, paymentProcessorTransfer);

            if(includeComments) {
                setupComments(userVisit, paymentProcessor, null, paymentProcessorTransfer, CommentConstants.CommentType_PAYMENT_PROCESSOR);
            }
            
            if(includePaymentProcessorTransactions) {
                paymentProcessorTransfer.setPaymentProcessorTransactions(new ListWrapper<>(paymentProcessorTransactionControl.getPaymentProcessorTransactionTransfersByPaymentProcessor(userVisit, paymentProcessor)));
            }
        }

        return paymentProcessorTransfer;
    }
    
}
