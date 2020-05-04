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

import com.echothree.model.control.payment.common.PaymentOptions;
import com.echothree.model.control.payment.common.transfer.PaymentMethodTypeTransfer;
import com.echothree.model.control.payment.server.PaymentControl;
import com.echothree.model.data.payment.server.entity.PaymentMethodType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.transfer.ListWrapper;
import java.util.Set;

public class PaymentMethodTypeTransferCache
        extends BasePaymentTransferCache<PaymentMethodType, PaymentMethodTypeTransfer> {
    
    boolean includePaymentMethodTypePartyTypes;

    /** Creates a new instance of PaymentMethodTypeTransferCache */
    public PaymentMethodTypeTransferCache(UserVisit userVisit, PaymentControl paymentControl) {
        super(userVisit, paymentControl);

        Set<String> options = session.getOptions();
        if(options != null) {
            includePaymentMethodTypePartyTypes = options.contains(PaymentOptions.PaymentMethodTypeIncludePaymentMethodTypePartyTypes);
        }
    }

    @Override
    public PaymentMethodTypeTransfer getTransfer(PaymentMethodType paymentMethodType) {
        PaymentMethodTypeTransfer paymentMethodTypeTransfer = get(paymentMethodType);
        
        if(paymentMethodTypeTransfer == null) {
            String paymentMethodTypeName = paymentMethodType.getPaymentMethodTypeName();
            Boolean isDefault = paymentMethodType.getIsDefault();
            Integer sortOrder = paymentMethodType.getSortOrder();
            String description = paymentControl.getBestPaymentMethodTypeDescription(paymentMethodType, getLanguage());
            
            paymentMethodTypeTransfer = new PaymentMethodTypeTransfer(paymentMethodTypeName, isDefault, sortOrder, description);
            put(paymentMethodType, paymentMethodTypeTransfer);

            if(includePaymentMethodTypePartyTypes) {
                paymentMethodTypeTransfer.setPaymentMethodTypePartyTypes(new ListWrapper<>(paymentControl.getPaymentMethodTypePartyTypeTransfersByPaymentMethodType(userVisit, paymentMethodType)));
            }
        }
        return paymentMethodTypeTransfer;
    }
    
}
