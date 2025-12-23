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
import com.echothree.model.control.payment.common.transfer.BillingAccountTransfer;
import com.echothree.model.control.term.common.transfer.TermTransfer;
import com.echothree.model.control.workflow.common.transfer.WorkflowEntityStatusTransfer;
import com.echothree.util.common.transfer.BaseTransfer;
import com.echothree.util.common.transfer.ListWrapper;
import com.echothree.util.common.transfer.MapWrapper;

public class InvoiceTransfer
        extends BaseTransfer {
    
    private InvoiceTypeTransfer invoiceType;
    private String invoiceName;
    private BillingAccountTransfer billingAccount;
    private GlAccountTransfer glAccount;
    private TermTransfer term;
    private String reference;
    private String description;
    private WorkflowEntityStatusTransfer invoiceStatus;
    
    private MapWrapper<InvoiceTimeTransfer> invoiceTimes;
    private MapWrapper<InvoiceRoleTransfer> invoiceRoles;
    private ListWrapper<InvoiceLineTransfer> invoiceLines;
    private ListWrapper<InvoiceAliasTransfer> invoiceAliases;
    
    /** Creates a new instance of InvoiceTransfer */
    public InvoiceTransfer(InvoiceTypeTransfer invoiceType, String invoiceName, BillingAccountTransfer billingAccount, GlAccountTransfer glAccount,
            TermTransfer term, String reference, String description, WorkflowEntityStatusTransfer invoiceStatus) {
        this.invoiceType = invoiceType;
        this.invoiceName = invoiceName;
        this.billingAccount = billingAccount;
        this.glAccount = glAccount;
        this.term = term;
        this.reference = reference;
        this.description = description;
        this.invoiceStatus = invoiceStatus;
    }

    /**
     * Returns the invoiceType.
     * @return the invoiceType
     */
    public InvoiceTypeTransfer getInvoiceType() {
        return invoiceType;
    }

    /**
     * Sets the invoiceType.
     * @param invoiceType the invoiceType to set
     */
    public void setInvoiceType(InvoiceTypeTransfer invoiceType) {
        this.invoiceType = invoiceType;
    }

    /**
     * Returns the invoiceName.
     * @return the invoiceName
     */
    public String getInvoiceName() {
        return invoiceName;
    }

    /**
     * Sets the invoiceName.
     * @param invoiceName the invoiceName to set
     */
    public void setInvoiceName(String invoiceName) {
        this.invoiceName = invoiceName;
    }

    /**
     * Returns the billingAccount.
     * @return the billingAccount
     */
    public BillingAccountTransfer getBillingAccount() {
        return billingAccount;
    }

    /**
     * Sets the billingAccount.
     * @param billingAccount the billingAccount to set
     */
    public void setBillingAccount(BillingAccountTransfer billingAccount) {
        this.billingAccount = billingAccount;
    }

    /**
     * Returns the glAccount.
     * @return the glAccount
     */
    public GlAccountTransfer getGlAccount() {
        return glAccount;
    }

    /**
     * Sets the glAccount.
     * @param glAccount the glAccount to set
     */
    public void setGlAccount(GlAccountTransfer glAccount) {
        this.glAccount = glAccount;
    }

    /**
     * Returns the term.
     * @return the term
     */
    public TermTransfer getTerm() {
        return term;
    }

    /**
     * Sets the term.
     * @param term the term to set
     */
    public void setTerm(TermTransfer term) {
        this.term = term;
    }

    /**
     * Returns the reference.
     * @return the reference
     */
    public String getReference() {
        return reference;
    }

    /**
     * Sets the reference.
     * @param reference the reference to set
     */
    public void setReference(String reference) {
        this.reference = reference;
    }

    /**
     * Returns the description.
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description.
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the invoiceStatus.
     * @return the invoiceStatus
     */
    public WorkflowEntityStatusTransfer getInvoiceStatus() {
        return invoiceStatus;
    }

    /**
     * Sets the invoiceStatus.
     * @param invoiceStatus the invoiceStatus to set
     */
    public void setInvoiceStatus(WorkflowEntityStatusTransfer invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
    }

    /**
     * Returns the invoiceTimes.
     * @return the invoiceTimes
     */
    public MapWrapper<InvoiceTimeTransfer> getInvoiceTimes() {
        return invoiceTimes;
    }

    /**
     * Sets the invoiceTimes.
     * @param invoiceTimes the invoiceTimes to set
     */
    public void setInvoiceTimes(MapWrapper<InvoiceTimeTransfer> invoiceTimes) {
        this.invoiceTimes = invoiceTimes;
    }

    /**
     * Returns the invoiceRoles.
     * @return the invoiceRoles
     */
    public MapWrapper<InvoiceRoleTransfer> getInvoiceRoles() {
        return invoiceRoles;
    }

    /**
     * Sets the invoiceRoles.
     * @param invoiceRoles the invoiceRoles to set
     */
    public void setInvoiceRoles(MapWrapper<InvoiceRoleTransfer> invoiceRoles) {
        this.invoiceRoles = invoiceRoles;
    }

    /**
     * Returns the invoiceLines.
     * @return the invoiceLines
     */
    public ListWrapper<InvoiceLineTransfer> getInvoiceLines() {
        return invoiceLines;
    }

    /**
     * Sets the invoiceLines.
     * @param invoiceLines the invoiceLines to set
     */
    public void setInvoiceLines(ListWrapper<InvoiceLineTransfer> invoiceLines) {
        this.invoiceLines = invoiceLines;
    }

    /**
     * Returns the invoiceAliases.
     * @return the invoiceAliases
     */
    public ListWrapper<InvoiceAliasTransfer> getInvoiceAliases() {
        return invoiceAliases;
    }

    /**
     * Sets the invoiceAliases.
     * @param invoiceAliases the invoiceAliases to set
     */
    public void setInvoiceAliases(ListWrapper<InvoiceAliasTransfer> invoiceAliases) {
        this.invoiceAliases = invoiceAliases;
    }

}
