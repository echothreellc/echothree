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

package com.echothree.model.control.invoice.common.transfer;

import com.echothree.model.control.contact.common.transfer.PartyContactMechanismTransfer;
import com.echothree.model.control.party.common.transfer.PartyTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class InvoiceRoleTransfer
        extends BaseTransfer {

    private InvoiceTransfer invoice;
    private PartyTransfer party;
    private PartyContactMechanismTransfer partyContactMechanism;
    private InvoiceRoleTypeTransfer invoiceRoleType;

    /** Creates a new instance of InvoiceRoleTransfer */
    public InvoiceRoleTransfer(InvoiceTransfer invoice, PartyTransfer party, PartyContactMechanismTransfer partyContactMechanism,
            InvoiceRoleTypeTransfer invoiceRoleType) {
        this.invoice = invoice;
        this.party = party;
        this.partyContactMechanism = partyContactMechanism;
        this.invoiceRoleType = invoiceRoleType;
    }

    public InvoiceTransfer getInvoice() {
        return invoice;
    }

    public void setInvoice(InvoiceTransfer invoice) {
        this.invoice = invoice;
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

    public InvoiceRoleTypeTransfer getInvoiceRoleType() {
        return invoiceRoleType;
    }

    public void setInvoiceRoleType(InvoiceRoleTypeTransfer invoiceRoleType) {
        this.invoiceRoleType = invoiceRoleType;
    }
}