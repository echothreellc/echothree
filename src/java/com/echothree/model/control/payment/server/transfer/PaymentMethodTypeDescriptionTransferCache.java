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

import com.echothree.model.control.payment.common.transfer.PaymentMethodTypeDescriptionTransfer;
import com.echothree.model.control.payment.server.control.PaymentMethodTypeControl;
import com.echothree.model.data.payment.server.entity.PaymentMethodTypeDescription;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class PaymentMethodTypeDescriptionTransferCache
        extends BasePaymentDescriptionTransferCache<PaymentMethodTypeDescription, PaymentMethodTypeDescriptionTransfer> {

    PaymentMethodTypeControl paymentMethodTypeControl = Session.getModelController(PaymentMethodTypeControl.class);

    /** Creates a new instance of PaymentMethodTypeDescriptionTransferCache */
    protected PaymentMethodTypeDescriptionTransferCache() {
        super();
    }
    
    @Override
    public PaymentMethodTypeDescriptionTransfer getTransfer(UserVisit userVisit, PaymentMethodTypeDescription paymentMethodTypeDescription) {
        var paymentMethodTypeDescriptionTransfer = get(paymentMethodTypeDescription);
        
        if(paymentMethodTypeDescriptionTransfer == null) {
            var paymentMethodTypeTransfer = paymentMethodTypeControl.getPaymentMethodTypeTransfer(userVisit, paymentMethodTypeDescription.getPaymentMethodType());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, paymentMethodTypeDescription.getLanguage());
            
            paymentMethodTypeDescriptionTransfer = new PaymentMethodTypeDescriptionTransfer(languageTransfer, paymentMethodTypeTransfer, paymentMethodTypeDescription.getDescription());
            put(userVisit, paymentMethodTypeDescription, paymentMethodTypeDescriptionTransfer);
        }
        
        return paymentMethodTypeDescriptionTransfer;
    }
    
}
