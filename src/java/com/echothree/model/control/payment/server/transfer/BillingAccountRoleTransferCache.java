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

import com.echothree.model.control.contact.common.transfer.PartyContactMechanismTransfer;
import com.echothree.model.control.contact.server.ContactControl;
import com.echothree.model.control.party.common.transfer.PartyTransfer;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.payment.common.transfer.BillingAccountRoleTransfer;
import com.echothree.model.control.payment.common.transfer.BillingAccountRoleTypeTransfer;
import com.echothree.model.control.payment.common.transfer.BillingAccountTransfer;
import com.echothree.model.control.payment.server.PaymentControl;
import com.echothree.model.data.payment.server.entity.BillingAccountRole;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class BillingAccountRoleTransferCache
        extends BasePaymentTransferCache<BillingAccountRole, BillingAccountRoleTransfer> {

    ContactControl contactControl;
    PartyControl partyControl;

    /** Creates a new instance of BillingAccountRoleTransferCache */
    public BillingAccountRoleTransferCache(UserVisit userVisit, PaymentControl paymentControl) {
        super(userVisit, paymentControl);

        contactControl = (ContactControl)Session.getModelController(ContactControl.class);
        partyControl = (PartyControl)Session.getModelController(PartyControl.class);
    }

    public BillingAccountRoleTransfer getTransfer(BillingAccountRole billingAccountRole) {
        BillingAccountRoleTransfer billingAccountRoleTransfer = get(billingAccountRole);

        if(billingAccountRoleTransfer == null) {
            BillingAccountTransfer billingAccount = paymentControl.getBillingAccountTransfer(userVisit, billingAccountRole.getBillingAccount());
            PartyTransfer party = partyControl.getPartyTransfer(userVisit, billingAccountRole.getParty());
            PartyContactMechanismTransfer partyContactMechanism = contactControl.getPartyContactMechanismTransfer(userVisit, billingAccountRole.getPartyContactMechanism());
            BillingAccountRoleTypeTransfer billingAccountRoleType = paymentControl.getBillingAccountRoleTypeTransfer(userVisit, billingAccountRole.getBillingAccountRoleType());

            billingAccountRoleTransfer = new BillingAccountRoleTransfer(billingAccount, party, partyContactMechanism, billingAccountRoleType);
            put(billingAccountRole, billingAccountRoleTransfer);
        }

        return billingAccountRoleTransfer;
    }
}