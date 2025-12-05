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

import com.echothree.model.control.invoice.common.InvoiceLineUseTypes;
import com.echothree.model.control.invoice.common.InvoiceRoleTypes;
import com.echothree.model.control.invoice.common.InvoiceTimeTypes;
import com.echothree.model.control.invoice.common.InvoiceTypes;
import com.echothree.model.control.invoice.common.exception.UnknownInvoiceRoleTypeNameException;
import com.echothree.model.control.invoice.common.exception.UnknownInvoiceSequenceException;
import com.echothree.model.control.invoice.common.exception.UnknownInvoiceSequenceTypeException;
import com.echothree.model.control.invoice.common.exception.UnknownInvoiceTypeNameException;
import com.echothree.model.control.invoice.server.control.InvoiceControl;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.payment.common.BillingAccountRoleTypes;
import com.echothree.model.control.payment.server.control.BillingControl;
import com.echothree.model.control.payment.server.logic.BillingAccountLogic;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.control.sequence.server.logic.SequenceGeneratorLogic;
import com.echothree.model.control.shipment.server.control.PartyFreeOnBoardControl;
import com.echothree.model.control.term.common.TermTypes;
import com.echothree.model.control.term.server.control.TermControl;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.accounting.server.entity.GlAccount;
import com.echothree.model.data.contact.server.entity.PartyContactMechanism;
import com.echothree.model.data.invoice.server.entity.Invoice;
import com.echothree.model.data.invoice.server.entity.InvoiceLine;
import com.echothree.model.data.invoice.server.entity.InvoiceLineType;
import com.echothree.model.data.invoice.server.entity.InvoiceRoleType;
import com.echothree.model.data.invoice.server.entity.InvoiceType;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.sequence.server.entity.Sequence;
import com.echothree.model.data.sequence.server.entity.SequenceType;
import com.echothree.model.data.shipment.server.entity.FreeOnBoard;
import com.echothree.model.data.term.server.entity.Term;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class InvoiceLogic
        extends BaseLogic {

    protected InvoiceLogic() {
        super();
    }

    public static InvoiceLogic getInstance() {
        return CDI.current().select(InvoiceLogic.class).get();
    }
    
    public Currency getInvoiceCurrency(final Invoice invoice) {
        return invoice.getLastDetail().getBillingAccount().getLastDetail().getCurrency();
    }
    
    public InvoiceType getInvoiceTypeByName(final ExecutionErrorAccumulator eea, final String invoiceTypeName) {
        var invoiceControl = Session.getModelController(InvoiceControl.class);
        var invoiceType = invoiceControl.getInvoiceTypeByName(invoiceTypeName);

        if(invoiceType == null) {
            handleExecutionError(UnknownInvoiceTypeNameException.class, eea, ExecutionErrors.UnknownInvoiceTypeName.name(), invoiceTypeName);
        }

        return invoiceType;
    }

    public InvoiceRoleType getInvoiceRoleTypeByName(final ExecutionErrorAccumulator eea, final String invoiceRoleTypeName) {
        var invoiceControl = Session.getModelController(InvoiceControl.class);
        var invoiceRoleType = invoiceControl.getInvoiceRoleTypeByName(invoiceRoleTypeName);

        if(invoiceRoleType == null) {
            handleExecutionError(UnknownInvoiceRoleTypeNameException.class, eea, ExecutionErrors.UnknownInvoiceRoleTypeName.name(), invoiceRoleTypeName);
        }

        return invoiceRoleType;
    }

    public SequenceType getInvoiceSequenceType(final ExecutionErrorAccumulator eea, final InvoiceType invoiceType) {
        SequenceType sequenceType;
        var parentInvoiceType = invoiceType;
        
        do {
            var invoiceTypeDetail = parentInvoiceType.getLastDetail();
            
            sequenceType = invoiceTypeDetail.getInvoiceSequenceType();
            
            if(sequenceType == null) {
                parentInvoiceType = invoiceTypeDetail.getParentInvoiceType();
            } else {
                break;
            }
        } while(parentInvoiceType != null);
        
        if(sequenceType == null) {
            var sequenceControl = Session.getModelController(SequenceControl.class);
            
            sequenceType = sequenceControl.getDefaultSequenceType();
        }
        
        if(sequenceType == null) {
            handleExecutionError(UnknownInvoiceSequenceTypeException.class, eea, ExecutionErrors.UnknownInvoiceSequenceType.name(), invoiceType.getLastDetail().getInvoiceTypeName());
        }
        
        return sequenceType;
    }
    
    public Sequence getInvoiceSequence(final ExecutionErrorAccumulator eea, final InvoiceType invoiceType) {
        Sequence sequence = null;
        var sequenceType = getInvoiceSequenceType(eea, invoiceType);
        
        if(eea == null || !eea.hasExecutionErrors()) {
            var sequenceControl = Session.getModelController(SequenceControl.class);
            
            sequence = sequenceControl.getDefaultSequence(sequenceType);
        }
        
        if(sequence == null) {
            handleExecutionError(UnknownInvoiceSequenceException.class, eea, ExecutionErrors.UnknownInvoiceSequence.name(), invoiceType.getLastDetail().getInvoiceTypeName());
        }
        
        return sequence;
    }
    
    public String getInvoiceName(final ExecutionErrorAccumulator eea, final InvoiceType invoiceType) {
        String invoiceName = null;
        var sequence = getInvoiceSequence(eea, invoiceType);
        
        if(eea == null || !eea.hasExecutionErrors()) {
            invoiceName = SequenceGeneratorLogic.getInstance().getNextSequenceValue(sequence);
        }
        
        return invoiceName;
    }

    public Term getInvoiceTerm(final ExecutionErrorAccumulator eea, final Party billFrom, Term term) {
        if(term == null) {
            var termControl = Session.getModelController(TermControl.class);

            term = termControl.getPartyTerm(billFrom).getTerm();
        }

        if(term == null) {
            eea.addExecutionError(ExecutionErrors.UnknownPartyTerm.name(), billFrom.getLastDetail().getPartyName());
        }

        return term;
    }

    public FreeOnBoard getInvoiceFreeOnBoard(final ExecutionErrorAccumulator eea, final Party billFrom, FreeOnBoard freeOnBoard) {
        if(freeOnBoard == null) {
            var partyFreeOnBoardControl = Session.getModelController(PartyFreeOnBoardControl.class);

            freeOnBoard = partyFreeOnBoardControl.getPartyFreeOnBoard(billFrom).getFreeOnBoard();
        }

        if(freeOnBoard == null) {
            eea.addExecutionError(ExecutionErrors.UnknownPartyFreeOnBoard.name(), billFrom.getLastDetail().getPartyName());
        }

        return freeOnBoard;
    }

    public String getTermTypeName(final Term term) {
        var termDetail = term.getLastDetail();
        
        return termDetail.getTermType().getTermTypeName();
    }
    
    public Long getDueTime(final Session session, final Term term, final String termTypeName, Long invoicedTime) {
        var termControl = Session.getModelController(TermControl.class);
        Long dueTime;
        
        if(termTypeName.equals(TermTypes.STANDARD.name())) {
            dueTime = invoicedTime + termControl.getStandardTerm(term).getNetDueDays() * (1000 * 60 * 60 * 24);
        } else if(termTypeName.equals(TermTypes.PREPAID.name())) {
            dueTime = null;
        } else { // TODO: TermTypes.DATE_DRIVEN.name()
            dueTime = session.getStartTimeLong();
        }
        
        return dueTime;
    }
    
    public Long getPaidTime(final Session session, final String termTypeName) {
        Long paidTime;
        
        if(termTypeName.equals(TermTypes.PREPAID.name())) {
            paidTime = session.getStartTimeLong();
        } else {
            paidTime = null;
        }
        
        return paidTime;
    }
    
    public Invoice createInvoice(final Session session, final ExecutionErrorAccumulator eea, String invoiceTypeName, final Party billFrom,
            final PartyContactMechanism billFromPartyContactMechanism, final Party billTo, final PartyContactMechanism billToPartyContactMechanism, Currency currency, final GlAccount glAccount,
            Term term, FreeOnBoard freeOnBoard, final String reference, final String description, Long invoicedTime, Long dueTime, Long paidTime, final BasePK createdBy) {
        var partyControl = Session.getModelController(PartyControl.class);
        Invoice invoice = null;
        
        currency = currency == null ? partyControl.getPreferredCurrency(billFrom) : currency;
        var billingAccount = BillingAccountLogic.getInstance().getBillingAccount(eea, billFrom, billFromPartyContactMechanism, billTo, billToPartyContactMechanism, currency, null,
                null, createdBy);
        
        if(eea == null || !eea.hasExecutionErrors()) {
            var invoiceControl = Session.getModelController(InvoiceControl.class);
            var invoiceType = invoiceControl.getInvoiceTypeByName(invoiceTypeName);
            
            if(invoiceType != null) {
                var invoiceName = getInvoiceName(eea, invoiceType);

                invoiceTypeName = invoiceType.getLastDetail().getInvoiceTypeName(); // Clean-up capitalization.

                if(eea == null || !eea.hasExecutionErrors()) {
                    term = getInvoiceTerm(eea, billFrom, term);

                    // FreeOnBoard is only allowed for SALES_INVOICEs and PURCHASE_INVOICEs.
                    if(invoiceTypeName.equals(InvoiceTypes.SALES_INVOICE.name()) || invoiceTypeName.equals(InvoiceTypes.PURCHASE_INVOICE.name())) {
                        freeOnBoard = getInvoiceFreeOnBoard(eea, billFrom, freeOnBoard);
                    } else if(freeOnBoard != null) {
                        eea.addExecutionError(ExecutionErrors.FreeOnBoardNotAllowed.name(), freeOnBoard.getLastDetail().getFreeOnBoardName());
                    }

                    if(eea == null || !eea.hasExecutionErrors()) {
                        var billingControl = Session.getModelController(BillingControl.class);
                        var invoicedTimeLogic = InvoiceTimeLogic.getInstance();
                        var billFromContactMechanism = billingControl.getBillingAccountRoleUsingNames(billingAccount, BillingAccountRoleTypes.BILL_FROM.name()).getPartyContactMechanism();
                        var billToContactMechanism = billingControl.getBillingAccountRoleUsingNames(billingAccount, BillingAccountRoleTypes.BILL_TO.name()).getPartyContactMechanism();
                        var termTypeName = getTermTypeName(term);

                        invoicedTime = invoicedTime == null ? session.getStartTimeLong() : invoicedTime;
                        dueTime = dueTime == null ? getDueTime(session, term, termTypeName, invoicedTime) : dueTime;
                        paidTime = paidTime == null ? getPaidTime(session, termTypeName) : paidTime;

                        invoice = invoiceControl.createInvoice(invoiceType, invoiceName, billingAccount, glAccount, term, freeOnBoard, reference, description, createdBy);
                        invoicedTimeLogic.createOrUpdateInvoiceTimeIfNotNull(null, invoice, InvoiceTimeTypes.INVOICED.name(), invoicedTime, createdBy);
                        invoicedTimeLogic.createOrUpdateInvoiceTimeIfNotNull(null, invoice, InvoiceTimeTypes.DUE.name(), dueTime, createdBy);
                        invoicedTimeLogic.createOrUpdateInvoiceTimeIfNotNull(null, invoice, InvoiceTimeTypes.PAID.name(), paidTime, createdBy);

                        invoiceControl.createInvoiceRoleUsingNames(invoice, billFrom, billFromContactMechanism, InvoiceRoleTypes.INVOICE_FROM.name(), createdBy);
                        invoiceControl.createInvoiceRoleUsingNames(invoice, billTo, billToContactMechanism, InvoiceRoleTypes.INVOICE_TO.name(), createdBy);
                    }
                }
            } else {
                eea.addExecutionError(ExecutionErrors.UnknownInvoiceTypeName.name(), invoiceTypeName);
            }
        }
        
        return invoice;
    }
    
    public InvoiceLine createInvoiceLine(final ExecutionErrorAccumulator eea, final Invoice invoice, final Integer invoiceLineSequence, final InvoiceLine parentInvoiceLine,
            final Long amount, final InvoiceLineType invoiceLineType, GlAccount glAccount, final String description, final BasePK createdBy) {
        InvoiceLine invoiceLine = null;
        var invoiceControl = Session.getModelController(InvoiceControl.class);
        var invoiceLineUseType = invoiceControl.getInvoiceLineUseTypeByName(InvoiceLineUseTypes.GL_ACCOUNT.name());
        
        if(glAccount == null) {
            glAccount = invoiceLineType.getLastDetail().getDefaultGlAccount();
        }
        
        if(glAccount != null) {
            invoiceLine = invoiceControl.createInvoiceLine(invoice, invoiceLineSequence, parentInvoiceLine, invoiceLineType, invoiceLineUseType, amount, description, createdBy);

            invoiceControl.createInvoiceLineGlAccount(invoiceLine, glAccount, createdBy);
        } else {
            eea.addExecutionError(ExecutionErrors.MissingRequiredGlAccount.name());
        }
        
        return invoiceLine;
    }
    
}
