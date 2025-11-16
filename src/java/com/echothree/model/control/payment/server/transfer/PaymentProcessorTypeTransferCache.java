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

import com.echothree.model.control.payment.common.transfer.PaymentProcessorTypeTransfer;
import com.echothree.model.control.payment.server.control.PaymentProcessorTypeControl;
import com.echothree.model.data.payment.server.entity.PaymentProcessorType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class PaymentProcessorTypeTransferCache
        extends BasePaymentTransferCache<PaymentProcessorType, PaymentProcessorTypeTransfer> {

    PaymentProcessorTypeControl paymentProcessorTypeControl = Session.getModelController(PaymentProcessorTypeControl.class);

    /** Creates a new instance of PaymentProcessorTypeTransferCache */
    public PaymentProcessorTypeTransferCache() {
        super();
        
        setIncludeEntityInstance(true);
    }
    
    @Override
    public PaymentProcessorTypeTransfer getTransfer(UserVisit userVisit, PaymentProcessorType paymentProcessorType) {
        var paymentProcessorTypeTransfer = get(paymentProcessorType);
        
        if(paymentProcessorTypeTransfer == null) {
            var paymentProcessorTypeDetail = paymentProcessorType.getLastDetail();
            var paymentProcessorTypeName = paymentProcessorTypeDetail.getPaymentProcessorTypeName();
            var isDefault = paymentProcessorTypeDetail.getIsDefault();
            var sortOrder = paymentProcessorTypeDetail.getSortOrder();
            var description = paymentProcessorTypeControl.getBestPaymentProcessorTypeDescription(paymentProcessorType, getLanguage(userVisit));
            
            paymentProcessorTypeTransfer = new PaymentProcessorTypeTransfer(paymentProcessorTypeName, isDefault, sortOrder, description);
            put(userVisit, paymentProcessorType, paymentProcessorTypeTransfer);
        }
        
        return paymentProcessorTypeTransfer;
    }
    
}
