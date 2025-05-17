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

package com.echothree.model.control.invoice.server.logic;

import com.echothree.model.control.accounting.common.AccountingConstants;
import com.echothree.model.control.accounting.server.logic.GlAccountLogic;
import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.control.invoice.common.InvoiceTypes;
import com.echothree.model.control.invoice.common.choice.PurchaseInvoiceStatusChoicesBean;
import com.echothree.model.control.invoice.common.workflow.PurchaseInvoiceStatusConstants;
import com.echothree.model.control.invoice.server.control.InvoiceControl;
import com.echothree.model.control.vendor.server.control.VendorControl;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.accounting.server.entity.GlAccount;
import com.echothree.model.data.contact.server.entity.PartyContactMechanism;
import com.echothree.model.data.invoice.server.entity.Invoice;
import com.echothree.model.data.invoice.server.entity.InvoiceLine;
import com.echothree.model.data.invoice.server.entity.InvoiceLineType;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.shipment.server.entity.FreeOnBoard;
import com.echothree.model.data.term.server.entity.Term;
import com.echothree.model.data.vendor.server.entity.Vendor;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;

public class PurchaseInvoiceLogic {

    private PurchaseInvoiceLogic() {
        super();
    }

    private static class PurchaseInvoiceLogicHolder {
        static PurchaseInvoiceLogic instance = new PurchaseInvoiceLogic();
    }

    public static PurchaseInvoiceLogic getInstance() {
        return PurchaseInvoiceLogicHolder.instance;
    }
    
    public Invoice getInvoiceByName(String invoiceName) {
        var invoiceControl = Session.getModelController(InvoiceControl.class);
        
        return invoiceControl.getInvoiceByNameUsingNames(InvoiceTypes.PURCHASE_INVOICE.name(), invoiceName);
    }
    
    protected void validateReference(final ExecutionErrorAccumulator eea, final Party billFrom, final String reference, final Vendor vendor) {
        if(vendor.getRequireReference() && reference == null) {
            eea.addExecutionError(ExecutionErrors.PurchaseInvoiceReferenceRequired.name());
        } else if(reference != null) {
            var invoiceControl = Session.getModelController(InvoiceControl.class);
            
            if(!vendor.getAllowReferenceDuplicates() && invoiceControl.countInvoicesByInvoiceFromAndReference(billFrom, reference) != 0) {
                eea.addExecutionError(ExecutionErrors.PurchaseInvoiceDuplicateReference.name());
            } else {
                var referenceValidationPattern = vendor.getReferenceValidationPattern();
                
                if(referenceValidationPattern != null && !reference.matches(referenceValidationPattern)) {
                    eea.addExecutionError(ExecutionErrors.InvalidPurchaseInvoiceReference.name());
                }
            }
        }
    }

    protected GlAccount getApGlAccount(final ExecutionErrorAccumulator eea, final Vendor vendor) {
        var vendorApGlAccount = vendor.getApGlAccount();
        var vendorTypeDefaultApGlAccount = vendor.getVendorType().getLastDetail().getDefaultApGlAccount();
        var glAccounts = new GlAccount[]{vendorApGlAccount, vendorTypeDefaultApGlAccount};

        return GlAccountLogic.getInstance().getDefaultGlAccountByCategory(eea, new GlAccount[]{
                    vendor.getApGlAccount(),
                    vendor.getVendorType().getLastDetail().getDefaultApGlAccount()
                }, AccountingConstants.GlAccountCategory_ACCOUNTS_PAYABLE, ExecutionErrors.UnknownDefaultApGlAccount.name());
    }
    
    public Invoice createInvoice(final Session session, final ExecutionErrorAccumulator eea, final Party billFrom,
            final PartyContactMechanism billFromPartyContactMechanism, final Party billTo, final PartyContactMechanism billToPartyContactMechanism,
            final Currency currency, final Term term, final FreeOnBoard freeOnBoard, final String reference,
            final String description, final Long invoicedTime, final Long dueTime,
            final Long paidTime, final String workflowEntranceName, final BasePK createdBy) {
        Invoice invoice = null;
        var vendorControl = Session.getModelController(VendorControl.class);
        var vendor = vendorControl.getVendor(billFrom);
        var glAccount = getApGlAccount(eea, vendor);
        
        validateReference(eea, billFrom, reference, vendor);
        
        if(eea == null || !eea.hasExecutionErrors()) {
            invoice = InvoiceLogic.getInstance().createInvoice(session, eea, InvoiceTypes.PURCHASE_INVOICE.name(), billFrom,
                    billFromPartyContactMechanism, billTo, billToPartyContactMechanism, currency, glAccount, term,
                    freeOnBoard, reference, description, invoicedTime, dueTime, paidTime, createdBy);

            if(!eea.hasExecutionErrors() && workflowEntranceName != null) {
                var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
                var workflowControl = Session.getModelController(WorkflowControl.class);
                var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(invoice.getPrimaryKey());

                workflowControl.addEntityToWorkflowUsingNames(null, PurchaseInvoiceStatusConstants.Workflow_PURCHASE_INVOICE_STATUS, workflowEntranceName,
                        entityInstance, null, null, createdBy);
            }
        }
        
        return invoice;
    }
    
    public InvoiceLine createInvoiceLine(final ExecutionErrorAccumulator eea, final Invoice invoice, final Integer invoiceLineSequence, final InvoiceLine parentInvoiceLine,
            final Long amount, final InvoiceLineType invoiceLineType, final GlAccount glAccount, final String description, final BasePK createdBy) {
        var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
        var workflowControl = Session.getModelController(WorkflowControl.class);
        InvoiceLine invoiceLine = null;
        var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(invoice.getPrimaryKey());
        var workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceUsingNames(PurchaseInvoiceStatusConstants.Workflow_PURCHASE_INVOICE_STATUS, entityInstance);
        var workflowStepName = workflowEntityStatus.getWorkflowStep().getLastDetail().getWorkflowStepName();

        if(workflowStepName.equals(PurchaseInvoiceStatusConstants.WorkflowStep_ENTRY)) {
            invoiceLine = InvoiceLogic.getInstance().createInvoiceLine(eea, invoice, invoiceLineSequence, parentInvoiceLine, amount, invoiceLineType, glAccount, description, createdBy);
        } else {
            eea.addExecutionError(ExecutionErrors.InvalidPurchaseInvoiceStatus.name(), invoice.getLastDetail().getInvoiceName(), workflowStepName);
        }
        
        return invoiceLine;
    }
    
    public PurchaseInvoiceStatusChoicesBean getPurchaseInvoiceStatusChoices(final String defaultInvoiceStatusChoice, final Language language, final boolean allowNullChoice,
            final Invoice invoice, final PartyPK partyPK) {
        var workflowControl = Session.getModelController(WorkflowControl.class);
        var purchaseInvoiceStatusChoicesBean = new PurchaseInvoiceStatusChoicesBean();
        
        if(invoice == null) {
            workflowControl.getWorkflowEntranceChoices(purchaseInvoiceStatusChoicesBean, defaultInvoiceStatusChoice, language, allowNullChoice,
                    workflowControl.getWorkflowByName(PurchaseInvoiceStatusConstants.Workflow_PURCHASE_INVOICE_STATUS), partyPK);
        } else {
            var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
            var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(invoice.getPrimaryKey());
            var workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceUsingNames(PurchaseInvoiceStatusConstants.Workflow_PURCHASE_INVOICE_STATUS, entityInstance);
            
            workflowControl.getWorkflowDestinationChoices(purchaseInvoiceStatusChoicesBean, defaultInvoiceStatusChoice, language, allowNullChoice, workflowEntityStatus.getWorkflowStep(), partyPK);
        }
        
        return purchaseInvoiceStatusChoicesBean;
    }
    
    public void setPurchaseInvoiceStatus(final ExecutionErrorAccumulator eea, final Invoice invoice, final String invoiceStatusChoice, final PartyPK modifiedBy) {
        var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
        var workflowControl = Session.getModelController(WorkflowControl.class);
        var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(invoice.getPrimaryKey());
        var workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceForUpdateUsingNames(PurchaseInvoiceStatusConstants.Workflow_PURCHASE_INVOICE_STATUS,
                entityInstance);
        var workflowDestination = invoiceStatusChoice == null? null: workflowControl.getWorkflowDestinationByName(workflowEntityStatus.getWorkflowStep(), invoiceStatusChoice);
        
        if(workflowDestination != null || invoiceStatusChoice == null) {
            workflowControl.transitionEntityInWorkflow(eea, workflowEntityStatus, workflowDestination, null, modifiedBy);
        } else {
            eea.addExecutionError(ExecutionErrors.UnknownPurchaseInvoiceStatusChoice.name(), invoiceStatusChoice);
        }
    }
    
}
