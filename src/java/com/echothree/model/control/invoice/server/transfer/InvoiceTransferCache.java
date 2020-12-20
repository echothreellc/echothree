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

import com.echothree.model.control.accounting.common.transfer.GlAccountTransfer;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.control.invoice.common.InvoiceOptions;
import com.echothree.model.control.invoice.common.InvoiceTypes;
import com.echothree.model.control.invoice.common.transfer.InvoiceRoleTransfer;
import com.echothree.model.control.invoice.common.transfer.InvoiceTimeTransfer;
import com.echothree.model.control.invoice.common.transfer.InvoiceTransfer;
import com.echothree.model.control.invoice.common.transfer.InvoiceTypeTransfer;
import com.echothree.model.control.invoice.common.workflow.PurchaseInvoiceStatusConstants;
import com.echothree.model.control.invoice.server.control.InvoiceControl;
import com.echothree.model.control.payment.common.transfer.BillingAccountTransfer;
import com.echothree.model.control.payment.server.control.BillingControl;
import com.echothree.model.control.term.common.transfer.TermTransfer;
import com.echothree.model.control.term.server.control.TermControl;
import com.echothree.model.control.workflow.common.transfer.WorkflowEntityStatusTransfer;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.invoice.server.entity.Invoice;
import com.echothree.model.data.invoice.server.entity.InvoiceDetail;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.string.DateTimeFormatType;
import com.echothree.util.common.string.DateTimeFormatter;
import com.echothree.util.common.transfer.ListWrapper;
import com.echothree.util.common.transfer.MapWrapper;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.string.DateUtils;
import java.util.List;
import java.util.Set;

public class InvoiceTransferCache
        extends BaseInvoiceTransferCache<Invoice, InvoiceTransfer> {
    
    AccountingControl accountingControl = Session.getModelController(AccountingControl.class);
    BillingControl billingControl = Session.getModelController(BillingControl.class);
    CoreControl coreControl = Session.getModelController(CoreControl.class);
    TermControl termControl = Session.getModelController(TermControl.class);
    WorkflowControl workflowControl = Session.getModelController(WorkflowControl.class);
    boolean includeLines;
    boolean includeRoles;
    DateTimeFormatter shortDateFormatter;
    
    /** Creates a new instance of InvoiceTransferCache */
    public InvoiceTransferCache(UserVisit userVisit, InvoiceControl invoiceControl) {
        super(userVisit, invoiceControl);

        var options = session.getOptions();
        if(options != null) {
            includeLines = options.contains(InvoiceOptions.InvoiceIncludeLines);
            includeRoles = options.contains(InvoiceOptions.InvoiceIncludeRoles);
        }

        setIncludeEntityInstance(true);
        
        shortDateFormatter = DateUtils.getInstance().getDateTimeFormatter(userVisit, DateTimeFormatType.SHORT_DATE);
    }

    private InvoiceTransfer setInvoiceTimes(Invoice invoice, InvoiceTransfer invoiceTransfer) {
        List<InvoiceTimeTransfer> invoiceTimeTransfers = invoiceControl.getInvoiceTimeTransfersByInvoice(userVisit, invoice);
        MapWrapper<InvoiceTimeTransfer> invoiceTimes = new MapWrapper<>(invoiceTimeTransfers.size());

        invoiceTimeTransfers.forEach((invoiceTimeTransfer) -> {
            invoiceTimes.put(invoiceTimeTransfer.getInvoiceTimeType().getInvoiceTimeTypeName(), invoiceTimeTransfer);
        });

        invoiceTransfer.setInvoiceTimes(invoiceTimes);

        return invoiceTransfer;
    }

    private InvoiceTransfer setInvoiceRoles(Invoice invoice, InvoiceTransfer invoiceTransfer) {
        List<InvoiceRoleTransfer> invoiceRoleTransfers = invoiceControl.getInvoiceRoleTransfersByInvoice(userVisit, invoice);
        MapWrapper<InvoiceRoleTransfer> invoiceRoles = new MapWrapper<>(invoiceRoleTransfers.size());

        invoiceRoleTransfers.forEach((invoiceRoleTransfer) -> {
            invoiceRoles.put(invoiceRoleTransfer.getInvoiceRoleType().getInvoiceRoleTypeUseTypeName(), invoiceRoleTransfer);
        });

        invoiceTransfer.setInvoiceRoles(invoiceRoles);

        return invoiceTransfer;
    }

    public InvoiceTransfer getInvoiceTransfer(Invoice invoice) {
        InvoiceTransfer invoiceTransfer = get(invoice);
        
        if(invoiceTransfer == null) {
            InvoiceDetail invoiceDetail = invoice.getLastDetail();
            InvoiceTypeTransfer invoiceType = invoiceControl.getInvoiceTypeTransfer(userVisit, invoiceDetail.getInvoiceType());
            String invoiceName = invoiceDetail.getInvoiceName();
            BillingAccountTransfer billingAccount = billingControl.getBillingAccountTransfer(userVisit, invoiceDetail.getBillingAccount());
            GlAccountTransfer glAccount = accountingControl.getGlAccountTransfer(userVisit, invoiceDetail.getGlAccount());
            TermTransfer term = termControl.getTermTransfer(userVisit, invoiceDetail.getTerm()); 
            String reference = invoiceDetail.getReference();
            String description = invoiceDetail.getDescription();
            WorkflowEntityStatusTransfer invoiceStatus = null;
            
            String invoiceTypeName = invoiceType.getInvoiceTypeName();
            EntityInstance entityInstance = coreControl.getEntityInstanceByBasePK(invoice.getPrimaryKey());
            if(invoiceTypeName.equals(InvoiceTypes.PURCHASE_INVOICE.name())) {
                invoiceStatus = workflowControl.getWorkflowEntityStatusTransferByEntityInstanceUsingNames(userVisit, PurchaseInvoiceStatusConstants.Workflow_PURCHASE_INVOICE_STATUS, entityInstance);
            }
            
            invoiceTransfer = setInvoiceTimes(invoice, new InvoiceTransfer(invoiceType, invoiceName, billingAccount, glAccount, term, reference, description, invoiceStatus));
            put(invoice, invoiceTransfer, entityInstance);
            

            if(includeLines) {
                invoiceTransfer.setInvoiceLines(new ListWrapper<>(invoiceControl.getInvoiceLineTransfersByInvoice(userVisit, invoice)));
            }

            if(includeRoles) {
                setInvoiceRoles(invoice, invoiceTransfer);
            }
        }
        
        return invoiceTransfer;
    }
    
}
