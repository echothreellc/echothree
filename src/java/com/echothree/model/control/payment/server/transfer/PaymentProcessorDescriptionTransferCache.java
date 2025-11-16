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

import com.echothree.model.control.payment.common.transfer.PaymentProcessorDescriptionTransfer;
import com.echothree.model.control.payment.server.control.PaymentProcessorControl;
import com.echothree.model.data.payment.server.entity.PaymentProcessorDescription;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class PaymentProcessorDescriptionTransferCache
        extends BasePaymentDescriptionTransferCache<PaymentProcessorDescription, PaymentProcessorDescriptionTransfer> {

    PaymentProcessorControl paymentProcessorControl = Session.getModelController(com.echothree.model.control.payment.server.control.PaymentProcessorControl.class);

    /** Creates a new instance of PaymentProcessorDescriptionTransferCache */
    protected PaymentProcessorDescriptionTransferCache() {
        super();
    }

    @Override
    public PaymentProcessorDescriptionTransfer getTransfer(UserVisit userVisit, PaymentProcessorDescription paymentProcessorDescription) {
        var paymentProcessorDescriptionTransfer = get(paymentProcessorDescription);
        
        if(paymentProcessorDescriptionTransfer == null) {
            var paymentProcessorTransfer = paymentProcessorControl.getPaymentProcessorTransfer(userVisit, paymentProcessorDescription.getPaymentProcessor());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, paymentProcessorDescription.getLanguage());
            
            paymentProcessorDescriptionTransfer = new PaymentProcessorDescriptionTransfer(languageTransfer, paymentProcessorTransfer, paymentProcessorDescription.getDescription());
            put(userVisit, paymentProcessorDescription, paymentProcessorDescriptionTransfer);
        }
        
        return paymentProcessorDescriptionTransfer;
    }
    
}
