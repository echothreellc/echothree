// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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
import com.echothree.model.control.payment.server.control.PaymentControl;
import com.echothree.model.control.payment.server.control.PaymentProcessorTypeControl;
import com.echothree.model.data.payment.server.entity.PaymentProcessor;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class PaymentProcessorTransferCache
        extends BasePaymentTransferCache<PaymentProcessor, PaymentProcessorTransfer> {

    PaymentProcessorTypeControl paymentProcessorTypeControl = (PaymentProcessorTypeControl) Session.getModelController(PaymentProcessorTypeControl.class);

    boolean includeComments;

    /** Creates a new instance of PaymentProcessorTransferCache */
    public PaymentProcessorTransferCache(UserVisit userVisit, PaymentControl paymentControl) {
        super(userVisit, paymentControl);

        var options = session.getOptions();
        if(options != null) {
            setIncludeKey(options.contains(PaymentOptions.PaymentProcessorIncludeKey));
            setIncludeGuid(options.contains(PaymentOptions.PaymentProcessorIncludeGuid));
            includeComments = options.contains(PaymentOptions.PaymentProcessorIncludeComments);
        }
        
        setIncludeEntityInstance(true);
    }

    @Override
    public PaymentProcessorTransfer getTransfer(PaymentProcessor paymentProcessor) {
        PaymentProcessorTransfer paymentProcessorTransfer = get(paymentProcessor);
        
        if(paymentProcessorTransfer == null) {
            var paymentProcessorDetail = paymentProcessor.getLastDetail();
            var paymentProcessorName = paymentProcessorDetail.getPaymentProcessorName();
            var paymentProcessorType = paymentProcessorTypeControl.getPaymentProcessorTypeTransfer(userVisit, paymentProcessorDetail.getPaymentProcessorType());
            var isDefault = paymentProcessorDetail.getIsDefault();
            var sortOrder = paymentProcessorDetail.getSortOrder();
            var description = paymentControl.getBestPaymentProcessorDescription(paymentProcessor, getLanguage());
            
            paymentProcessorTransfer = new PaymentProcessorTransfer(paymentProcessorName, paymentProcessorType, isDefault, sortOrder, description);
            put(paymentProcessor, paymentProcessorTransfer);

            if(includeComments) {
                setupComments(paymentProcessor, null, paymentProcessorTransfer, CommentConstants.CommentType_PAYMENT_PROCESSOR);
            }
        }

        return paymentProcessorTransfer;
    }
    
}
