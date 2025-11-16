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

package com.echothree.model.control.invoice.server.transfer;

import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.invoice.common.transfer.InvoiceLineGlAccountTransfer;
import com.echothree.model.control.invoice.server.control.InvoiceControl;
import com.echothree.model.data.invoice.server.entity.InvoiceLineGlAccount;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class InvoiceLineGlAccountTransferCache
        extends BaseInvoiceTransferCache<InvoiceLineGlAccount, InvoiceLineGlAccountTransfer> {

    AccountingControl accountingControl = Session.getModelController(AccountingControl.class);
    InvoiceControl invoiceControl = Session.getModelController(InvoiceControl.class);

    /** Creates a new instance of InvoiceLineGlAccountTransferCache */
    public InvoiceLineGlAccountTransferCache() {
        super();
    }

    public InvoiceLineGlAccountTransfer getInvoiceLineGlAccountTransfer(UserVisit userVisit, InvoiceLineGlAccount invoiceLineGlAccount) {
        var invoiceLineGlAccountTransfer = get(invoiceLineGlAccount);

        if(invoiceLineGlAccountTransfer == null) {
            var invoiceLine = invoiceControl.getInvoiceLineTransfer(userVisit, invoiceLineGlAccount.getInvoiceLine());
            var glAccount = accountingControl.getGlAccountTransfer(userVisit, invoiceLineGlAccount.getGlAccount());

            invoiceLineGlAccountTransfer = new InvoiceLineGlAccountTransfer(invoiceLine, glAccount);
            put(userVisit, invoiceLineGlAccount, invoiceLineGlAccountTransfer);
        }

        return invoiceLineGlAccountTransfer;
    }
}