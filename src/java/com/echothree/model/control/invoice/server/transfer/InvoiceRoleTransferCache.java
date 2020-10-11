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

package com.echothree.model.control.invoice.server.transfer;

import com.echothree.model.control.contact.common.transfer.PartyContactMechanismTransfer;
import com.echothree.model.control.contact.server.control.ContactControl;
import com.echothree.model.control.invoice.common.transfer.InvoiceRoleTransfer;
import com.echothree.model.control.invoice.common.transfer.InvoiceRoleTypeTransfer;
import com.echothree.model.control.invoice.common.transfer.InvoiceTransfer;
import com.echothree.model.control.invoice.server.InvoiceControl;
import com.echothree.model.control.party.common.transfer.PartyTransfer;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.data.invoice.server.entity.InvoiceRole;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class InvoiceRoleTransferCache
        extends BaseInvoiceTransferCache<InvoiceRole, InvoiceRoleTransfer> {

    ContactControl contactControl;
    PartyControl partyControl;

    /** Creates a new instance of InvoiceRoleTransferCache */
    public InvoiceRoleTransferCache(UserVisit userVisit, InvoiceControl invoiceControl) {
        super(userVisit, invoiceControl);

        contactControl = (ContactControl)Session.getModelController(ContactControl.class);
        partyControl = (PartyControl)Session.getModelController(PartyControl.class);
    }

    public InvoiceRoleTransfer getInvoiceRoleTransfer(InvoiceRole invoiceRole) {
        InvoiceRoleTransfer invoiceRoleTransfer = get(invoiceRole);

        if(invoiceRoleTransfer == null) {
            InvoiceTransfer invoice = invoiceControl.getInvoiceTransfer(userVisit, invoiceRole.getInvoice());
            PartyTransfer party = partyControl.getPartyTransfer(userVisit, invoiceRole.getParty());
            PartyContactMechanismTransfer partyContactMechanism = contactControl.getPartyContactMechanismTransfer(userVisit, invoiceRole.getPartyContactMechanism());
            InvoiceRoleTypeTransfer invoiceRoleType = invoiceControl.getInvoiceRoleTypeTransfer(userVisit, invoiceRole.getInvoiceRoleType());

            invoiceRoleTransfer = new InvoiceRoleTransfer(invoice, party, partyContactMechanism, invoiceRoleType);
            put(invoiceRole, invoiceRoleTransfer);
        }

        return invoiceRoleTransfer;
    }
}