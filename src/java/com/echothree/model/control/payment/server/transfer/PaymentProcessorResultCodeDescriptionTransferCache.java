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

import com.echothree.model.control.party.common.transfer.LanguageTransfer;
import com.echothree.model.control.payment.common.transfer.PaymentProcessorResultCodeDescriptionTransfer;
import com.echothree.model.control.payment.common.transfer.PaymentProcessorResultCodeTransfer;
import com.echothree.model.control.payment.server.control.PaymentProcessorResultCodeControl;
import com.echothree.model.data.payment.server.entity.PaymentProcessorResultCodeDescription;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class PaymentProcessorResultCodeDescriptionTransferCache
        extends BasePaymentDescriptionTransferCache<PaymentProcessorResultCodeDescription, PaymentProcessorResultCodeDescriptionTransfer> {

    PaymentProcessorResultCodeControl paymentProcessorResultCodeControl = (PaymentProcessorResultCodeControl)Session.getModelController(PaymentProcessorResultCodeControl.class);

    /** Creates a new instance of PaymentProcessorResultCodeDescriptionTransferCache */
    public PaymentProcessorResultCodeDescriptionTransferCache(UserVisit userVisit) {
        super(userVisit);
    }
    
    @Override
    public PaymentProcessorResultCodeDescriptionTransfer getTransfer(PaymentProcessorResultCodeDescription paymentProcessorResultCodeDescription) {
        PaymentProcessorResultCodeDescriptionTransfer paymentProcessorResultCodeDescriptionTransfer = get(paymentProcessorResultCodeDescription);
        
        if(paymentProcessorResultCodeDescriptionTransfer == null) {
            PaymentProcessorResultCodeTransfer paymentProcessorResultCodeTransfer = paymentProcessorResultCodeControl.getPaymentProcessorResultCodeTransfer(userVisit, paymentProcessorResultCodeDescription.getPaymentProcessorResultCode());
            LanguageTransfer languageTransfer = partyControl.getLanguageTransfer(userVisit, paymentProcessorResultCodeDescription.getLanguage());
            
            paymentProcessorResultCodeDescriptionTransfer = new PaymentProcessorResultCodeDescriptionTransfer(languageTransfer, paymentProcessorResultCodeTransfer, paymentProcessorResultCodeDescription.getDescription());
            put(paymentProcessorResultCodeDescription, paymentProcessorResultCodeDescriptionTransfer);
        }
        
        return paymentProcessorResultCodeDescriptionTransfer;
    }
    
}
