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

import com.echothree.model.control.payment.common.transfer.PaymentProcessorTypeCodeTypeTransfer;
import com.echothree.model.control.payment.server.control.PaymentProcessorTypeCodeTypeControl;
import com.echothree.model.control.payment.server.control.PaymentProcessorTypeControl;
import com.echothree.model.data.payment.server.entity.PaymentProcessorTypeCodeType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class PaymentProcessorTypeCodeTypeTransferCache
        extends BasePaymentTransferCache<PaymentProcessorTypeCodeType, PaymentProcessorTypeCodeTypeTransfer> {

    PaymentProcessorTypeControl paymentProcessorTypeControl = Session.getModelController(PaymentProcessorTypeControl.class);
    PaymentProcessorTypeCodeTypeControl paymentProcessorTypeCodeTypeControl = Session.getModelController(PaymentProcessorTypeCodeTypeControl.class);

    /** Creates a new instance of PaymentProcessorTypeTransferCache */
    public PaymentProcessorTypeCodeTypeTransferCache() {
        super();
        
        setIncludeEntityInstance(true);
    }
    
    @Override
    public PaymentProcessorTypeCodeTypeTransfer getTransfer(PaymentProcessorTypeCodeType paymentProcessorTypeCodeType) {
        var paymentProcessorTypeCodeTypeTransfer = get(paymentProcessorTypeCodeType);
        
        if(paymentProcessorTypeCodeTypeTransfer == null) {
            var paymentProcessorTypeCodeTypeDetail = paymentProcessorTypeCodeType.getLastDetail();
            var paymentProcessorTypeTransfer = paymentProcessorTypeControl.getPaymentProcessorTypeTransfer(userVisit, paymentProcessorTypeCodeTypeDetail.getPaymentProcessorType());
            var paymentProcessorTypeCodeTypeName = paymentProcessorTypeCodeTypeDetail.getPaymentProcessorTypeCodeTypeName();
            var isDefault = paymentProcessorTypeCodeTypeDetail.getIsDefault();
            var sortOrder = paymentProcessorTypeCodeTypeDetail.getSortOrder();
            var description = paymentProcessorTypeCodeTypeControl.getBestPaymentProcessorTypeCodeTypeDescription(paymentProcessorTypeCodeType, getLanguage(userVisit));
            
            paymentProcessorTypeCodeTypeTransfer = new PaymentProcessorTypeCodeTypeTransfer(paymentProcessorTypeTransfer,
                    paymentProcessorTypeCodeTypeName, isDefault, sortOrder, description);
            put(userVisit, paymentProcessorTypeCodeType, paymentProcessorTypeCodeTypeTransfer);
        }
        
        return paymentProcessorTypeCodeTypeTransfer;
    }
    
}
