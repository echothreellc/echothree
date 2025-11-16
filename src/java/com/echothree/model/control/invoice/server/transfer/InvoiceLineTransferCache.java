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

import com.echothree.model.control.invoice.common.InvoiceLineUseTypes;
import com.echothree.model.control.invoice.common.transfer.InvoiceLineTransfer;
import com.echothree.model.control.invoice.server.control.InvoiceControl;
import com.echothree.model.data.invoice.server.entity.InvoiceLine;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.string.AmountUtils;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class InvoiceLineTransferCache
        extends BaseInvoiceTransferCache<InvoiceLine, InvoiceLineTransfer> {

    InvoiceControl invoiceControl = Session.getModelController(InvoiceControl.class);

    /** Creates a new instance of InvoiceLineTransferCache */
    protected InvoiceLineTransferCache() {
        super();
        
        setIncludeEntityInstance(true);
    }
    
    public InvoiceLineTransfer getInvoiceLineTransfer(UserVisit userVisit, InvoiceLine invoiceLine) {
        var invoiceLineTransfer = get(invoiceLine);
        
        if(invoiceLineTransfer == null) {
            var invoiceLineDetail = invoiceLine.getLastDetail();
            var invoice = invoiceLineDetail.getInvoice();
            var invoiceTransfer = invoiceControl.getInvoiceTransfer(userVisit, invoice);
            var invoiceLineSequence = invoiceLineDetail.getInvoiceLineSequence();
            var parentInvoiceLine = invoiceLineDetail.getParentInvoiceLine();
            var parentInvoiceLineTransfer = parentInvoiceLine == null ? null : getInvoiceLineTransfer(userVisit, parentInvoiceLine);
            var invoiceLineTypeTransfer = invoiceControl.getInvoiceLineTypeTransfer(userVisit, invoiceLineDetail.getInvoiceLineType());
            var invoiceLineUseTypeTransfer = invoiceControl.getInvoiceLineUseTypeTransfer(userVisit, invoiceLineDetail.getInvoiceLineUseType());
            var description = invoiceLineDetail.getDescription();

            var currency = invoice.getLastDetail().getBillingAccount().getLastDetail().getCurrency();
            var unformattedAmount = invoiceLineDetail.getAmount();
            var amount = AmountUtils.getInstance().formatCostUnit(currency, unformattedAmount);
            
            invoiceLineTransfer = new InvoiceLineTransfer(invoiceTransfer, invoiceLineSequence, parentInvoiceLineTransfer, invoiceLineTypeTransfer, invoiceLineUseTypeTransfer, amount,
                    unformattedAmount, description);
            put(userVisit, invoiceLine, invoiceLineTransfer);

            var invoiceLineUseTypeName = invoiceLineUseTypeTransfer.getInvoiceLineUseTypeName();
            if(invoiceLineUseTypeName.equals(InvoiceLineUseTypes.ITEM.name())) {
                invoiceLineTransfer.setInvoiceLineItem(invoiceControl.getInvoiceLineItemTransfer(userVisit, invoiceControl.getInvoiceLineItem(invoiceLine)));
            } else if(invoiceLineUseTypeName.equals(InvoiceLineUseTypes.GL_ACCOUNT.name())) {
                invoiceLineTransfer.setInvoiceLineGlAccount(invoiceControl.getInvoiceLineGlAccountTransfer(userVisit, invoiceControl.getInvoiceLineGlAccount(invoiceLine)));
            }
        }
        
        return invoiceLineTransfer;
    }
    
}
