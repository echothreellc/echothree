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

package com.echothree.model.control.payment.common.transfer;

import com.echothree.model.control.contact.common.transfer.PartyContactMechanismTransfer;
import com.echothree.model.control.party.common.transfer.PartyTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class BillingAccountRoleTransfer
        extends BaseTransfer {

    private BillingAccountTransfer billingAccount;
    private PartyTransfer party;
    private PartyContactMechanismTransfer partyContactMechanism;
    private BillingAccountRoleTypeTransfer billingAccountRoleType;

    /** Creates a new instance of BillingAccountRoleTransfer */
    public BillingAccountRoleTransfer(BillingAccountTransfer billingAccount, PartyTransfer party,
            PartyContactMechanismTransfer partyContactMechanism, BillingAccountRoleTypeTransfer billingAccountRoleType) {
        this.billingAccount = billingAccount;
        this.party = party;
        this.partyContactMechanism = partyContactMechanism;
        this.billingAccountRoleType = billingAccountRoleType;
    }

    public BillingAccountTransfer getBillingAccount() {
        return billingAccount;
    }

    public void setBillingAccount(BillingAccountTransfer billingAccount) {
        this.billingAccount = billingAccount;
    }

    public PartyTransfer getParty() {
        return party;
    }

    public void setParty(PartyTransfer party) {
        this.party = party;
    }

    public PartyContactMechanismTransfer getPartyContactMechanism() {
        return partyContactMechanism;
    }

    public void setPartyContactMechanism(PartyContactMechanismTransfer partyContactMechanism) {
        this.partyContactMechanism = partyContactMechanism;
    }

    public BillingAccountRoleTypeTransfer getBillingAccountRoleType() {
        return billingAccountRoleType;
    }

    public void setBillingAccountRoleType(BillingAccountRoleTypeTransfer billingAccountRoleType) {
        this.billingAccountRoleType = billingAccountRoleType;
    }
}