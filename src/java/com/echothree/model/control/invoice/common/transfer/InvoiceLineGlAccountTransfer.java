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

import com.echothree.model.control.accounting.common.transfer.GlAccountTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class InvoiceLineGlAccountTransfer
        extends BaseTransfer {
    
    private InvoiceLineTransfer invoiceLine;
    private GlAccountTransfer glAccount;
    
    /** Creates a new instance of InvoiceLineGlAccountTransfer */
    public InvoiceLineGlAccountTransfer(InvoiceLineTransfer invoiceLine, GlAccountTransfer glAccount) {
        this.invoiceLine = invoiceLine;
        this.glAccount = glAccount;
    }

    public InvoiceLineTransfer getInvoiceLine() {
        return invoiceLine;
    }

    public void setInvoiceLine(InvoiceLineTransfer invoiceLine) {
        this.invoiceLine = invoiceLine;
    }

    public GlAccountTransfer getGlAccount() {
        return glAccount;
    }

    public void setGlAccount(GlAccountTransfer glAccount) {
        this.glAccount = glAccount;
    }
    
}
