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

import com.echothree.model.control.payment.common.transfer.PaymentProcessorTypeDescriptionTransfer;
import com.echothree.model.control.payment.server.control.PaymentProcessorTypeControl;
import com.echothree.model.data.payment.server.entity.PaymentProcessorTypeDescription;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class PaymentProcessorTypeDescriptionTransferCache
        extends BasePaymentDescriptionTransferCache<PaymentProcessorTypeDescription, PaymentProcessorTypeDescriptionTransfer> {

    PaymentProcessorTypeControl paymentProcessorTypeControl = Session.getModelController(PaymentProcessorTypeControl.class);

    /** Creates a new instance of PaymentProcessorTypeDescriptionTransferCache */
    public PaymentProcessorTypeDescriptionTransferCache() {
        super();
    }
    
    @Override
    public PaymentProcessorTypeDescriptionTransfer getTransfer(UserVisit userVisit, PaymentProcessorTypeDescription paymentProcessorTypeDescription) {
        var paymentProcessorTypeDescriptionTransfer = get(paymentProcessorTypeDescription);
        
        if(paymentProcessorTypeDescriptionTransfer == null) {
            var paymentProcessorTypeTransfer = paymentProcessorTypeControl.getPaymentProcessorTypeTransfer(userVisit, paymentProcessorTypeDescription.getPaymentProcessorType());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, paymentProcessorTypeDescription.getLanguage());
            
            paymentProcessorTypeDescriptionTransfer = new PaymentProcessorTypeDescriptionTransfer(languageTransfer, paymentProcessorTypeTransfer, paymentProcessorTypeDescription.getDescription());
            put(userVisit, paymentProcessorTypeDescription, paymentProcessorTypeDescriptionTransfer);
        }
        
        return paymentProcessorTypeDescriptionTransfer;
    }
    
}
