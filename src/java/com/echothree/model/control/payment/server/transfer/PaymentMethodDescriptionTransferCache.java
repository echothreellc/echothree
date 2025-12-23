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

package com.echothree.model.control.payment.server.transfer;

import com.echothree.model.control.payment.common.transfer.PaymentMethodDescriptionTransfer;
import com.echothree.model.control.payment.server.control.PaymentMethodControl;
import com.echothree.model.data.payment.server.entity.PaymentMethodDescription;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class PaymentMethodDescriptionTransferCache
        extends BasePaymentDescriptionTransferCache<PaymentMethodDescription, PaymentMethodDescriptionTransfer> {

    PaymentMethodControl paymentMethodControl = Session.getModelController(PaymentMethodControl.class);

    /** Creates a new instance of PaymentMethodDescriptionTransferCache */
    protected PaymentMethodDescriptionTransferCache() {
        super();
    }

    @Override
    public PaymentMethodDescriptionTransfer getTransfer(UserVisit userVisit, PaymentMethodDescription paymentMethodDescription) {
        var paymentMethodDescriptionTransfer = get(paymentMethodDescription);
        
        if(paymentMethodDescriptionTransfer == null) {
            var paymentMethodTransfer = paymentMethodControl.getPaymentMethodTransfer(userVisit, paymentMethodDescription.getPaymentMethod());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, paymentMethodDescription.getLanguage());
            
            paymentMethodDescriptionTransfer = new PaymentMethodDescriptionTransfer(languageTransfer, paymentMethodTransfer, paymentMethodDescription.getDescription());
            put(userVisit, paymentMethodDescription, paymentMethodDescriptionTransfer);
        }
        
        return paymentMethodDescriptionTransfer;
    }
    
}
