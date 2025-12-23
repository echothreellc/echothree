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

import com.echothree.model.control.payment.common.transfer.BillingAccountRoleTypeTransfer;
import com.echothree.model.control.payment.server.control.BillingControl;
import com.echothree.model.data.payment.server.entity.BillingAccountRoleType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class BillingAccountRoleTypeTransferCache
        extends BasePaymentTransferCache<BillingAccountRoleType, BillingAccountRoleTypeTransfer> {

    BillingControl billingControl = Session.getModelController(BillingControl.class);

    /** Creates a new instance of BillingAccountRoleTypeTransferCache */
    protected BillingAccountRoleTypeTransferCache() {
        super();
    }

    @Override
    public BillingAccountRoleTypeTransfer getTransfer(UserVisit userVisit, BillingAccountRoleType billingAccountRoleType) {
        var billingAccountRoleTypeTransfer = get(billingAccountRoleType);
        
        if(billingAccountRoleTypeTransfer == null) {
            var billingAccountRoleTypeName = billingAccountRoleType.getBillingAccountRoleTypeName();
            var sortOrder = billingAccountRoleType.getSortOrder();
            var description = billingControl.getBestBillingAccountRoleTypeDescription(billingAccountRoleType, getLanguage(userVisit));
            
            billingAccountRoleTypeTransfer = new BillingAccountRoleTypeTransfer(billingAccountRoleTypeName, sortOrder, description);
            put(userVisit, billingAccountRoleType, billingAccountRoleTypeTransfer);
        }
        
        return billingAccountRoleTypeTransfer;
    }
    
}
