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

import com.echothree.model.control.payment.common.transfer.PaymentMethodTypeDescriptionTransfer;
import com.echothree.model.control.payment.common.transfer.PaymentMethodTypeTransfer;
import com.echothree.model.control.payment.server.PaymentControl;
import com.echothree.model.control.party.common.transfer.LanguageTransfer;
import com.echothree.model.data.payment.server.entity.PaymentMethodTypeDescription;
import com.echothree.model.data.user.server.entity.UserVisit;

public class PaymentMethodTypeDescriptionTransferCache
        extends BasePaymentDescriptionTransferCache<PaymentMethodTypeDescription, PaymentMethodTypeDescriptionTransfer> {
    
    /** Creates a new instance of PaymentMethodTypeDescriptionTransferCache */
    public PaymentMethodTypeDescriptionTransferCache(UserVisit userVisit, PaymentControl paymentControl) {
        super(userVisit, paymentControl);
    }
    
    @Override
    public PaymentMethodTypeDescriptionTransfer getTransfer(PaymentMethodTypeDescription paymentMethodTypeDescription) {
        PaymentMethodTypeDescriptionTransfer paymentMethodTypeDescriptionTransfer = get(paymentMethodTypeDescription);
        
        if(paymentMethodTypeDescriptionTransfer == null) {
            PaymentMethodTypeTransferCache paymentMethodTypeTransferCache = paymentControl.getPaymentTransferCaches(userVisit).getPaymentMethodTypeTransferCache();
            PaymentMethodTypeTransfer paymentMethodTypeTransfer = paymentMethodTypeTransferCache.getTransfer(paymentMethodTypeDescription.getPaymentMethodType());
            LanguageTransfer languageTransfer = partyControl.getLanguageTransfer(userVisit, paymentMethodTypeDescription.getLanguage());
            
            paymentMethodTypeDescriptionTransfer = new PaymentMethodTypeDescriptionTransfer(languageTransfer, paymentMethodTypeTransfer, paymentMethodTypeDescription.getDescription());
            put(paymentMethodTypeDescription, paymentMethodTypeDescriptionTransfer);
        }
        
        return paymentMethodTypeDescriptionTransfer;
    }
    
}