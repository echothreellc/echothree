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

import com.echothree.model.control.contact.server.control.ContactControl;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.payment.common.transfer.BillingAccountRoleTransfer;
import com.echothree.model.control.payment.server.control.BillingControl;
import com.echothree.model.data.payment.server.entity.BillingAccountRole;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class BillingAccountRoleTransferCache
        extends BasePaymentTransferCache<BillingAccountRole, BillingAccountRoleTransfer> {

    BillingControl billingControl = Session.getModelController(BillingControl.class);
    ContactControl contactControl = Session.getModelController(ContactControl.class);
    PartyControl partyControl = Session.getModelController(PartyControl.class);

    /** Creates a new instance of BillingAccountRoleTransferCache */
    public BillingAccountRoleTransferCache() {
        super();
    }

    @Override
    public BillingAccountRoleTransfer getTransfer(UserVisit userVisit, BillingAccountRole billingAccountRole) {
        var billingAccountRoleTransfer = get(billingAccountRole);

        if(billingAccountRoleTransfer == null) {
            var billingAccount = billingControl.getBillingAccountTransfer(userVisit, billingAccountRole.getBillingAccount());
            var party = partyControl.getPartyTransfer(userVisit, billingAccountRole.getParty());
            var partyContactMechanism = contactControl.getPartyContactMechanismTransfer(userVisit, billingAccountRole.getPartyContactMechanism());
            var billingAccountRoleType = billingControl.getBillingAccountRoleTypeTransfer(userVisit, billingAccountRole.getBillingAccountRoleType());

            billingAccountRoleTransfer = new BillingAccountRoleTransfer(billingAccount, party, partyContactMechanism, billingAccountRoleType);
            put(userVisit, billingAccountRole, billingAccountRoleTransfer);
        }

        return billingAccountRoleTransfer;
    }
}