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

import com.echothree.model.control.payment.common.transfer.PaymentProcessorActionTypeDescriptionTransfer;
import com.echothree.model.control.payment.server.control.PaymentProcessorActionTypeControl;
import com.echothree.model.data.payment.server.entity.PaymentProcessorActionTypeDescription;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class PaymentProcessorActionTypeDescriptionTransferCache
        extends BasePaymentDescriptionTransferCache<PaymentProcessorActionTypeDescription, PaymentProcessorActionTypeDescriptionTransfer> {

    PaymentProcessorActionTypeControl paymentProcessorActionTypeControl = Session.getModelController(PaymentProcessorActionTypeControl.class);

    /** Creates a new instance of PaymentProcessorActionTypeDescriptionTransferCache */
    public PaymentProcessorActionTypeDescriptionTransferCache() {
        super();
    }
    
    @Override
    public PaymentProcessorActionTypeDescriptionTransfer getTransfer(UserVisit userVisit, PaymentProcessorActionTypeDescription paymentProcessorActionTypeDescription) {
        var paymentProcessorActionTypeDescriptionTransfer = get(paymentProcessorActionTypeDescription);
        
        if(paymentProcessorActionTypeDescriptionTransfer == null) {
            var paymentProcessorActionTypeTransfer = paymentProcessorActionTypeControl.getPaymentProcessorActionTypeTransfer(userVisit, paymentProcessorActionTypeDescription.getPaymentProcessorActionType());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, paymentProcessorActionTypeDescription.getLanguage());
            
            paymentProcessorActionTypeDescriptionTransfer = new PaymentProcessorActionTypeDescriptionTransfer(languageTransfer, paymentProcessorActionTypeTransfer, paymentProcessorActionTypeDescription.getDescription());
            put(userVisit, paymentProcessorActionTypeDescription, paymentProcessorActionTypeDescriptionTransfer);
        }
        
        return paymentProcessorActionTypeDescriptionTransfer;
    }
    
}
