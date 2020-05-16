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
import com.echothree.model.control.payment.common.transfer.PaymentProcessorTypeCodeDescriptionTransfer;
import com.echothree.model.control.payment.common.transfer.PaymentProcessorTypeCodeTransfer;
import com.echothree.model.control.payment.server.control.PaymentControl;
import com.echothree.model.data.payment.server.entity.PaymentProcessorTypeCodeDescription;
import com.echothree.model.data.user.server.entity.UserVisit;

public class PaymentProcessorTypeCodeDescriptionTransferCache
        extends BasePaymentDescriptionTransferCache<PaymentProcessorTypeCodeDescription, PaymentProcessorTypeCodeDescriptionTransfer> {
    
    /** Creates a new instance of PaymentProcessorTypeDescriptionTransferCache */
    public PaymentProcessorTypeCodeDescriptionTransferCache(UserVisit userVisit, PaymentControl paymentControl) {
        super(userVisit, paymentControl);
    }
    
    @Override
    public PaymentProcessorTypeCodeDescriptionTransfer getTransfer(PaymentProcessorTypeCodeDescription paymentProcessorTypeCodeDescription) {
        PaymentProcessorTypeCodeDescriptionTransfer paymentProcessorTypeCodeDescriptionTransfer = get(paymentProcessorTypeCodeDescription);
        
        if(paymentProcessorTypeCodeDescriptionTransfer == null) {
            PaymentProcessorTypeCodeTransferCache paymentProcessorTypeCodeTransferCache = paymentControl.getPaymentTransferCaches(userVisit).getPaymentProcessorTypeCodeTransferCache();
            PaymentProcessorTypeCodeTransfer paymentProcessorTypeCodeTransfer = paymentProcessorTypeCodeTransferCache.getTransfer(paymentProcessorTypeCodeDescription.getPaymentProcessorTypeCode());
            LanguageTransfer languageTransfer = partyControl.getLanguageTransfer(userVisit, paymentProcessorTypeCodeDescription.getLanguage());
            
            paymentProcessorTypeCodeDescriptionTransfer = new PaymentProcessorTypeCodeDescriptionTransfer(languageTransfer, paymentProcessorTypeCodeTransfer, paymentProcessorTypeCodeDescription.getDescription());
            put(paymentProcessorTypeCodeDescription, paymentProcessorTypeCodeDescriptionTransfer);
        }
        
        return paymentProcessorTypeCodeDescriptionTransfer;
    }
    
}
