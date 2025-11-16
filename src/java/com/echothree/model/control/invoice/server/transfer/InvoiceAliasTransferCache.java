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

import com.echothree.model.control.invoice.common.transfer.InvoiceAliasTransfer;
import com.echothree.model.control.invoice.server.control.InvoiceControl;
import com.echothree.model.data.invoice.server.entity.InvoiceAlias;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class InvoiceAliasTransferCache
        extends BaseInvoiceTransferCache<InvoiceAlias, InvoiceAliasTransfer> {

    InvoiceControl invoiceControl = Session.getModelController(InvoiceControl.class);

    /** Creates a new instance of InvoiceAliasTransferCache */
    public InvoiceAliasTransferCache() {
        super();
    }
    
    public InvoiceAliasTransfer getInvoiceAliasTransfer(UserVisit userVisit, InvoiceAlias invoiceAlias) {
        var invoiceAliasTransfer = get(invoiceAlias);
        
        if(invoiceAliasTransfer == null) {
            var invoice = invoiceControl.getInvoiceTransfer(userVisit, invoiceAlias.getInvoice());
            var invoiceAliasType = invoiceControl.getInvoiceAliasTypeTransfer(userVisit, invoiceAlias.getInvoiceAliasType());
            var alias = invoiceAlias.getAlias();
            
            invoiceAliasTransfer = new InvoiceAliasTransfer(invoice, invoiceAliasType, alias);
            put(userVisit, invoiceAlias, invoiceAliasTransfer);
        }
        
        return invoiceAliasTransfer;
    }
    
}
