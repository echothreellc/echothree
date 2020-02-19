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

import com.echothree.model.control.invoice.common.InvoiceLineUseTypes;
import com.echothree.model.control.invoice.common.transfer.InvoiceLineTransfer;
import com.echothree.model.control.invoice.common.transfer.InvoiceLineTypeTransfer;
import com.echothree.model.control.invoice.common.transfer.InvoiceLineUseTypeTransfer;
import com.echothree.model.control.invoice.common.transfer.InvoiceTransfer;
import com.echothree.model.control.invoice.server.InvoiceControl;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.invoice.server.entity.Invoice;
import com.echothree.model.data.invoice.server.entity.InvoiceLine;
import com.echothree.model.data.invoice.server.entity.InvoiceLineDetail;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.string.AmountUtils;

public class InvoiceLineTransferCache
        extends BaseInvoiceTransferCache<InvoiceLine, InvoiceLineTransfer> {
    
    /** Creates a new instance of InvoiceLineTransferCache */
    public InvoiceLineTransferCache(UserVisit userVisit, InvoiceControl invoiceControl) {
        super(userVisit, invoiceControl);
        
        setIncludeEntityInstance(true);
    }
    
    public InvoiceLineTransfer getInvoiceLineTransfer(InvoiceLine invoiceLine) {
        InvoiceLineTransfer invoiceLineTransfer = get(invoiceLine);
        
        if(invoiceLineTransfer == null) {
            InvoiceLineDetail invoiceLineDetail = invoiceLine.getLastDetail();
            Invoice invoice = invoiceLineDetail.getInvoice();
            InvoiceTransfer invoiceTransfer = invoiceControl.getInvoiceTransfer(userVisit, invoice);
            Integer invoiceLineSequence = invoiceLineDetail.getInvoiceLineSequence();
            InvoiceLine parentInvoiceLine = invoiceLineDetail.getParentInvoiceLine();
            InvoiceLineTransfer parentInvoiceLineTransfer = parentInvoiceLine == null? null: getInvoiceLineTransfer(parentInvoiceLine);
            InvoiceLineTypeTransfer invoiceLineTypeTransfer = invoiceControl.getInvoiceLineTypeTransfer(userVisit, invoiceLineDetail.getInvoiceLineType());
            InvoiceLineUseTypeTransfer invoiceLineUseTypeTransfer = invoiceControl.getInvoiceLineUseTypeTransfer(userVisit, invoiceLineDetail.getInvoiceLineUseType());
            String description = invoiceLineDetail.getDescription();
            
            Currency currency = invoice.getLastDetail().getBillingAccount().getLastDetail().getCurrency();
            Long unformattedAmount = invoiceLineDetail.getAmount();
            String amount = AmountUtils.getInstance().formatCostUnit(currency, unformattedAmount);
            
            invoiceLineTransfer = new InvoiceLineTransfer(invoiceTransfer, invoiceLineSequence, parentInvoiceLineTransfer, invoiceLineTypeTransfer, invoiceLineUseTypeTransfer, amount,
                    unformattedAmount, description);
            put(invoiceLine, invoiceLineTransfer);
            
            String invoiceLineUseTypeName = invoiceLineUseTypeTransfer.getInvoiceLineUseTypeName();
            if(invoiceLineUseTypeName.equals(InvoiceLineUseTypes.ITEM.name())) {
                invoiceLineTransfer.setInvoiceLineItem(invoiceControl.getInvoiceLineItemTransfer(userVisit, invoiceControl.getInvoiceLineItem(invoiceLine)));
            } else if(invoiceLineUseTypeName.equals(InvoiceLineUseTypes.GL_ACCOUNT.name())) {
                invoiceLineTransfer.setInvoiceLineGlAccount(invoiceControl.getInvoiceLineGlAccountTransfer(userVisit, invoiceControl.getInvoiceLineGlAccount(invoiceLine)));
            }
        }
        
        return invoiceLineTransfer;
    }
    
}
