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
import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.control.invoice.common.InvoiceOptions;
import com.echothree.model.control.invoice.common.InvoiceTypes;
import com.echothree.model.control.invoice.common.transfer.InvoiceRoleTransfer;
import com.echothree.model.control.invoice.common.transfer.InvoiceTimeTransfer;
import com.echothree.model.control.invoice.common.transfer.InvoiceTransfer;
import com.echothree.model.control.invoice.common.workflow.PurchaseInvoiceStatusConstants;
import com.echothree.model.control.invoice.server.control.InvoiceControl;
import com.echothree.model.control.payment.server.control.BillingControl;
import com.echothree.model.control.term.server.control.TermControl;
import com.echothree.model.control.workflow.common.transfer.WorkflowEntityStatusTransfer;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.invoice.server.entity.Invoice;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.transfer.ListWrapper;
import com.echothree.util.common.transfer.MapWrapper;
import com.echothree.util.server.persistence.Session;

public class InvoiceTransferCache
        extends BaseInvoiceTransferCache<Invoice, InvoiceTransfer> {
    
    AccountingControl accountingControl = Session.getModelController(AccountingControl.class);
    BillingControl billingControl = Session.getModelController(BillingControl.class);
    EntityInstanceControl entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
    TermControl termControl = Session.getModelController(TermControl.class);
    WorkflowControl workflowControl = Session.getModelController(WorkflowControl.class);
    boolean includeLines;
    boolean includeRoles;

    /** Creates a new instance of InvoiceTransferCache */
    public InvoiceTransferCache(InvoiceControl invoiceControl) {
        super(invoiceControl);

        var options = session.getOptions();
        if(options != null) {
            includeLines = options.contains(InvoiceOptions.InvoiceIncludeLines);
            includeRoles = options.contains(InvoiceOptions.InvoiceIncludeRoles);
        }

        setIncludeEntityInstance(true);
    }

    private InvoiceTransfer setInvoiceTimes(UserVisit userVisit, Invoice invoice, InvoiceTransfer invoiceTransfer) {
        var invoiceTimeTransfers = invoiceControl.getInvoiceTimeTransfersByInvoice(userVisit, invoice);
        var invoiceTimes = new MapWrapper<InvoiceTimeTransfer>(invoiceTimeTransfers.size());

        invoiceTimeTransfers.forEach((invoiceTimeTransfer) -> {
            invoiceTimes.put(invoiceTimeTransfer.getInvoiceTimeType().getInvoiceTimeTypeName(), invoiceTimeTransfer);
        });

        invoiceTransfer.setInvoiceTimes(invoiceTimes);

        return invoiceTransfer;
    }

    private InvoiceTransfer setInvoiceRoles(UserVisit userVisit, Invoice invoice, InvoiceTransfer invoiceTransfer) {
        var invoiceRoleTransfers = invoiceControl.getInvoiceRoleTransfersByInvoice(userVisit, invoice);
        var invoiceRoles = new MapWrapper<InvoiceRoleTransfer>(invoiceRoleTransfers.size());

        invoiceRoleTransfers.forEach((invoiceRoleTransfer) -> {
            invoiceRoles.put(invoiceRoleTransfer.getInvoiceRoleType().getInvoiceRoleTypeUseTypeName(), invoiceRoleTransfer);
        });

        invoiceTransfer.setInvoiceRoles(invoiceRoles);

        return invoiceTransfer;
    }

    public InvoiceTransfer getInvoiceTransfer(UserVisit userVisit, Invoice invoice) {
        var invoiceTransfer = get(invoice);
        
        if(invoiceTransfer == null) {
            var invoiceDetail = invoice.getLastDetail();
            var invoiceType = invoiceControl.getInvoiceTypeTransfer(userVisit, invoiceDetail.getInvoiceType());
            var invoiceName = invoiceDetail.getInvoiceName();
            var billingAccount = billingControl.getBillingAccountTransfer(userVisit, invoiceDetail.getBillingAccount());
            var glAccount = accountingControl.getGlAccountTransfer(userVisit, invoiceDetail.getGlAccount());
            var term = termControl.getTermTransfer(userVisit, invoiceDetail.getTerm());
            var reference = invoiceDetail.getReference();
            var description = invoiceDetail.getDescription();
            WorkflowEntityStatusTransfer invoiceStatus = null;

            var invoiceTypeName = invoiceType.getInvoiceTypeName();
            var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(invoice.getPrimaryKey());
            if(invoiceTypeName.equals(InvoiceTypes.PURCHASE_INVOICE.name())) {
                invoiceStatus = workflowControl.getWorkflowEntityStatusTransferByEntityInstanceUsingNames(userVisit, PurchaseInvoiceStatusConstants.Workflow_PURCHASE_INVOICE_STATUS, entityInstance);
            }
            
            invoiceTransfer = setInvoiceTimes(userVisit, invoice, new InvoiceTransfer(invoiceType, invoiceName, billingAccount, glAccount, term, reference, description, invoiceStatus));
            put(userVisit, invoice, invoiceTransfer, entityInstance);

            if(includeLines) {
                invoiceTransfer.setInvoiceLines(new ListWrapper<>(invoiceControl.getInvoiceLineTransfersByInvoice(userVisit, invoice)));
            }

            if(includeRoles) {
                setInvoiceRoles(userVisit, invoice, invoiceTransfer);
            }
        }
        
        return invoiceTransfer;
    }
    
}
