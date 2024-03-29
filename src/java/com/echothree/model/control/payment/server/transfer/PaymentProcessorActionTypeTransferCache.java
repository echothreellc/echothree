// --------------------------------------------------------------------------------
// Copyright 2002-2024 Echo Three, LLC
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

import com.echothree.model.control.payment.common.transfer.PaymentProcessorActionTypeTransfer;
import com.echothree.model.control.payment.server.control.PaymentProcessorActionTypeControl;
import com.echothree.model.data.payment.server.entity.PaymentProcessorActionType;
import com.echothree.model.data.payment.server.entity.PaymentProcessorActionTypeDetail;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class PaymentProcessorActionTypeTransferCache
        extends BasePaymentTransferCache<PaymentProcessorActionType, PaymentProcessorActionTypeTransfer> {

    PaymentProcessorActionTypeControl paymentProcessorActionTypeControl = Session.getModelController(PaymentProcessorActionTypeControl.class);

    /** Creates a new instance of PaymentProcessorActionTypeTransferCache */
    public PaymentProcessorActionTypeTransferCache(UserVisit userVisit) {
        super(userVisit);
        
        setIncludeEntityInstance(true);
    }
    
    @Override
    public PaymentProcessorActionTypeTransfer getTransfer(PaymentProcessorActionType paymentProcessorActionType) {
        PaymentProcessorActionTypeTransfer paymentProcessorActionTypeTransfer = get(paymentProcessorActionType);
        
        if(paymentProcessorActionTypeTransfer == null) {
            PaymentProcessorActionTypeDetail paymentProcessorActionTypeDetail = paymentProcessorActionType.getLastDetail();
            String paymentProcessorActionTypeName = paymentProcessorActionTypeDetail.getPaymentProcessorActionTypeName();
            Boolean isDefault = paymentProcessorActionTypeDetail.getIsDefault();
            Integer sortOrder = paymentProcessorActionTypeDetail.getSortOrder();
            String description = paymentProcessorActionTypeControl.getBestPaymentProcessorActionTypeDescription(paymentProcessorActionType, getLanguage());
            
            paymentProcessorActionTypeTransfer = new PaymentProcessorActionTypeTransfer(paymentProcessorActionTypeName, isDefault, sortOrder, description);
            put(paymentProcessorActionType, paymentProcessorActionTypeTransfer);
        }
        
        return paymentProcessorActionTypeTransfer;
    }
    
}
