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

import com.echothree.model.control.payment.common.transfer.BillingAccountRoleTypeTransfer;
import com.echothree.model.control.payment.server.PaymentControl;
import com.echothree.model.data.payment.server.entity.BillingAccountRoleType;
import com.echothree.model.data.user.server.entity.UserVisit;

public class BillingAccountRoleTypeTransferCache
        extends BasePaymentTransferCache<BillingAccountRoleType, BillingAccountRoleTypeTransfer> {
    
    /** Creates a new instance of BillingAccountRoleTypeTransferCache */
    public BillingAccountRoleTypeTransferCache(UserVisit userVisit, PaymentControl paymentControl) {
        super(userVisit, paymentControl);
    }

    @Override
    public BillingAccountRoleTypeTransfer getTransfer(BillingAccountRoleType billingAccountRoleType) {
        BillingAccountRoleTypeTransfer billingAccountRoleTypeTransfer = get(billingAccountRoleType);
        
        if(billingAccountRoleTypeTransfer == null) {
            String billingAccountRoleTypeName = billingAccountRoleType.getBillingAccountRoleTypeName();
            Integer sortOrder = billingAccountRoleType.getSortOrder();
            String description = paymentControl.getBestBillingAccountRoleTypeDescription(billingAccountRoleType, getLanguage());
            
            billingAccountRoleTypeTransfer = new BillingAccountRoleTypeTransfer(billingAccountRoleTypeName, sortOrder, description);
            put(billingAccountRoleType, billingAccountRoleTypeTransfer);
        }
        
        return billingAccountRoleTypeTransfer;
    }
    
}
