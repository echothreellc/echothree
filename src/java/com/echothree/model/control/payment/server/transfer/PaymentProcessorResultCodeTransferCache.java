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

import com.echothree.model.control.payment.common.transfer.PaymentProcessorResultCodeTransfer;
import com.echothree.model.control.payment.server.PaymentControl;
import com.echothree.model.control.payment.server.PaymentProcessorResultCodeControl;
import com.echothree.model.data.payment.server.entity.PaymentProcessorResultCode;
import com.echothree.model.data.payment.server.entity.PaymentProcessorResultCodeDetail;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class PaymentProcessorResultCodeTransferCache
        extends BasePaymentTransferCache<PaymentProcessorResultCode, PaymentProcessorResultCodeTransfer> {

    PaymentProcessorResultCodeControl paymentProcessorResultCodeControl = (PaymentProcessorResultCodeControl) Session.getModelController(PaymentProcessorResultCodeControl.class);

    /** Creates a new instance of PaymentProcessorResultCodeTransferCache */
    public PaymentProcessorResultCodeTransferCache(UserVisit userVisit, PaymentControl paymentControl) {
        super(userVisit, paymentControl);
        
        setIncludeEntityInstance(true);
    }
    
    @Override
    public PaymentProcessorResultCodeTransfer getTransfer(PaymentProcessorResultCode paymentProcessorResultCode) {
        PaymentProcessorResultCodeTransfer paymentProcessorResultCodeTransfer = get(paymentProcessorResultCode);
        
        if(paymentProcessorResultCodeTransfer == null) {
            PaymentProcessorResultCodeDetail paymentProcessorResultCodeDetail = paymentProcessorResultCode.getLastDetail();
            String paymentProcessorResultCodeName = paymentProcessorResultCodeDetail.getPaymentProcessorResultCodeName();
            Boolean isDefault = paymentProcessorResultCodeDetail.getIsDefault();
            Integer sortOrder = paymentProcessorResultCodeDetail.getSortOrder();
            String description = paymentProcessorResultCodeControl.getBestPaymentProcessorResultCodeDescription(paymentProcessorResultCode, getLanguage());
            
            paymentProcessorResultCodeTransfer = new PaymentProcessorResultCodeTransfer(paymentProcessorResultCodeName, isDefault, sortOrder, description);
            put(paymentProcessorResultCode, paymentProcessorResultCodeTransfer);
        }
        
        return paymentProcessorResultCodeTransfer;
    }
    
}
