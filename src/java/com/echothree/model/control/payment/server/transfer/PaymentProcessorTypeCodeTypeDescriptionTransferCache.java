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

import com.echothree.model.control.payment.common.transfer.PaymentProcessorTypeCodeTypeDescriptionTransfer;
import com.echothree.model.control.payment.server.control.PaymentProcessorTypeCodeTypeControl;
import com.echothree.model.data.payment.server.entity.PaymentProcessorTypeCodeTypeDescription;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class PaymentProcessorTypeCodeTypeDescriptionTransferCache
        extends BasePaymentDescriptionTransferCache<PaymentProcessorTypeCodeTypeDescription, PaymentProcessorTypeCodeTypeDescriptionTransfer> {

    PaymentProcessorTypeCodeTypeControl paymentProcessorTypeCodeType = Session.getModelController(PaymentProcessorTypeCodeTypeControl.class);

    /** Creates a new instance of PaymentProcessorTypeDescriptionTransferCache */
    public PaymentProcessorTypeCodeTypeDescriptionTransferCache(UserVisit userVisit) {
        super(userVisit);
    }
    
    @Override
    public PaymentProcessorTypeCodeTypeDescriptionTransfer getTransfer(PaymentProcessorTypeCodeTypeDescription paymentProcessorTypeCodeTypeDescription) {
        var paymentProcessorTypeCodeTypeDescriptionTransfer = get(paymentProcessorTypeCodeTypeDescription);
        
        if(paymentProcessorTypeCodeTypeDescriptionTransfer == null) {
            var paymentProcessorTypeCodeTypeTransfer = paymentProcessorTypeCodeType.getPaymentProcessorTypeCodeTypeTransfer(userVisit, paymentProcessorTypeCodeTypeDescription.getPaymentProcessorTypeCodeType());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, paymentProcessorTypeCodeTypeDescription.getLanguage());
            
            paymentProcessorTypeCodeTypeDescriptionTransfer = new PaymentProcessorTypeCodeTypeDescriptionTransfer(languageTransfer, paymentProcessorTypeCodeTypeTransfer, paymentProcessorTypeCodeTypeDescription.getDescription());
            put(userVisit, paymentProcessorTypeCodeTypeDescription, paymentProcessorTypeCodeTypeDescriptionTransfer);
        }
        
        return paymentProcessorTypeCodeTypeDescriptionTransfer;
    }
    
}
