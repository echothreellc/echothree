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

import com.echothree.util.common.transfer.BaseTransfer;

public class InvoiceLineTransfer
        extends BaseTransfer {
    
    private InvoiceTransfer invoice;
    private Integer invoiceLineSequence;
    private InvoiceLineTransfer parentInvoiceLine;
    private InvoiceLineTypeTransfer invoiceLineType;
    private InvoiceLineUseTypeTransfer invoiceLineUseType;
    private String amount;
    private Long unformattedAmount;
    private String description;
    private InvoiceLineItemTransfer invoiceLineItem;
    private InvoiceLineGlAccountTransfer invoiceLineGlAccount;
    
    /** Creates a new instance of InvoiceLineTransfer */
    public InvoiceLineTransfer(InvoiceTransfer invoice, Integer invoiceLineSequence, InvoiceLineTransfer parentInvoiceLine, InvoiceLineTypeTransfer invoiceLineType,
            InvoiceLineUseTypeTransfer invoiceLineUseType, String amount, Long unformattedAmount, String description) {
        this.invoice = invoice;
        this.invoiceLineSequence = invoiceLineSequence;
        this.parentInvoiceLine = parentInvoiceLine;
        this.invoiceLineType = invoiceLineType;
        this.invoiceLineUseType = invoiceLineUseType;
        this.amount = amount;
        this.unformattedAmount = unformattedAmount;
        this.description = description;
    }

    public InvoiceTransfer getInvoice() {
        return invoice;
    }

    public void setInvoice(InvoiceTransfer invoice) {
        this.invoice = invoice;
    }

    public Integer getInvoiceLineSequence() {
        return invoiceLineSequence;
    }

    public void setInvoiceLineSequence(Integer invoiceLineSequence) {
        this.invoiceLineSequence = invoiceLineSequence;
    }

    public InvoiceLineTransfer getParentInvoiceLine() {
        return parentInvoiceLine;
    }

    public void setParentInvoiceLine(InvoiceLineTransfer parentInvoiceLine) {
        this.parentInvoiceLine = parentInvoiceLine;
    }

    public InvoiceLineTypeTransfer getInvoiceLineType() {
        return invoiceLineType;
    }

    public void setInvoiceLineType(InvoiceLineTypeTransfer invoiceLineType) {
        this.invoiceLineType = invoiceLineType;
    }

    public InvoiceLineUseTypeTransfer getInvoiceLineUseType() {
        return invoiceLineUseType;
    }

    public void setInvoiceLineUseType(InvoiceLineUseTypeTransfer invoiceLineUseType) {
        this.invoiceLineUseType = invoiceLineUseType;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public Long getUnformattedAmount() {
        return unformattedAmount;
    }

    public void setUnformattedAmount(Long unformattedAmount) {
        this.unformattedAmount = unformattedAmount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public InvoiceLineItemTransfer getInvoiceLineItem() {
        return invoiceLineItem;
    }

    public void setInvoiceLineItem(InvoiceLineItemTransfer invoiceLineItem) {
        this.invoiceLineItem = invoiceLineItem;
    }

    public InvoiceLineGlAccountTransfer getInvoiceLineGlAccount() {
        return invoiceLineGlAccount;
    }

    public void setInvoiceLineGlAccount(InvoiceLineGlAccountTransfer invoiceLineGlAccount) {
        this.invoiceLineGlAccount = invoiceLineGlAccount;
    }
    
}
