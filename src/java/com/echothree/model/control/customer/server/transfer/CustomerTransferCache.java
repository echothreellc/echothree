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

package com.echothree.model.control.customer.server.transfer;

import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.cancellationpolicy.server.control.CancellationPolicyControl;
import com.echothree.model.control.carrier.server.control.CarrierControl;
import com.echothree.model.control.comment.common.CommentConstants;
import com.echothree.model.control.communication.server.control.CommunicationControl;
import com.echothree.model.control.contact.server.control.ContactControl;
import com.echothree.model.control.contactlist.server.control.ContactListControl;
import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.control.customer.common.CustomerOptions;
import com.echothree.model.control.customer.common.transfer.CustomerTransfer;
import com.echothree.model.control.customer.common.workflow.CustomerCreditStatusConstants;
import com.echothree.model.control.customer.common.workflow.CustomerStatusConstants;
import com.echothree.model.control.customer.server.control.CustomerControl;
import com.echothree.model.control.document.server.control.DocumentControl;
import com.echothree.model.control.invoice.server.control.InvoiceControl;
import com.echothree.model.control.offer.server.control.OfferUseControl;
import com.echothree.model.control.party.common.PartyOptions;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.payment.server.control.BillingControl;
import com.echothree.model.control.payment.server.control.PartyPaymentMethodControl;
import com.echothree.model.control.printer.server.control.PrinterControl;
import com.echothree.model.control.returnpolicy.server.control.ReturnPolicyControl;
import com.echothree.model.control.scale.server.control.ScaleControl;
import com.echothree.model.control.shipment.server.control.PartyFreeOnBoardControl;
import com.echothree.model.control.subscription.server.control.SubscriptionControl;
import com.echothree.model.control.term.server.control.TermControl;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.communication.server.factory.CommunicationEventFactory;
import com.echothree.model.data.customer.server.entity.Customer;
import com.echothree.model.data.invoice.server.factory.InvoiceFactory;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.transfer.ListWrapper;
import com.echothree.util.server.persistence.Session;

public class CustomerTransferCache
        extends BaseCustomerTransferCache<Party, CustomerTransfer> {

    AccountingControl accountingControl = Session.getModelController(AccountingControl.class);
    BillingControl billingControl = Session.getModelController(BillingControl.class);
    CancellationPolicyControl cancellationPolicyControl = Session.getModelController(CancellationPolicyControl.class);
    CarrierControl carrierControl = Session.getModelController(CarrierControl.class);
    CommunicationControl communicationControl = Session.getModelController(CommunicationControl.class);
    ContactControl contactControl = Session.getModelController(ContactControl.class);
    ContactListControl contactListControl = Session.getModelController(ContactListControl.class);
    DocumentControl documentControl = Session.getModelController(DocumentControl.class);
    EntityInstanceControl entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
    InvoiceControl invoiceControl = Session.getModelController(InvoiceControl.class);
    OfferUseControl offerUseControl = Session.getModelController(OfferUseControl.class);
    PartyControl partyControl = Session.getModelController(PartyControl.class);
    PartyPaymentMethodControl partyPaymentMethodControl = Session.getModelController(PartyPaymentMethodControl.class);
    PartyFreeOnBoardControl partyFreeOnBoardControl = Session.getModelController(PartyFreeOnBoardControl.class);
    PrinterControl printerControl = Session.getModelController(PrinterControl.class);
    ReturnPolicyControl returnPolicyControl = Session.getModelController(ReturnPolicyControl.class);
    ScaleControl scaleControl = Session.getModelController(ScaleControl.class);
    SubscriptionControl subscriptionControl = Session.getModelController(SubscriptionControl.class);
    TermControl termControl = Session.getModelController(TermControl.class);
    UserControl userControl = Session.getModelController(UserControl.class);
    WorkflowControl workflowControl = Session.getModelController(WorkflowControl.class);

    boolean includeUserLogin;
    boolean includeRecoveryAnswer;
    boolean includePartyAliases;
    boolean includePartyContactMechanisms;
    boolean includePartyContactLists;
    boolean includePartyDocuments;
    boolean includePartyPrinterGroupUses;
    boolean includePartyScaleUses;
    boolean includePartyCarriers;
    boolean includePartyCarrierAccounts;
    boolean includeCustomerServiceComments;
    boolean includeOrderEntryComments;
    boolean includeBillingAccounts;
    boolean includeInvoicesFrom;
    boolean includeInvoicesTo;
    boolean hasInvoiceLimits;
    boolean includePartyCreditLimits;
    boolean includePartyTerm;
    boolean includePartyFreeOnBoard;
    boolean includePartyPaymentMethods;
    boolean includePartyCancellationPolicies;
    boolean includePartyReturnPolicies;
    boolean includeSubscriptions;
    boolean includeCommunicationEvents;
    boolean hasCommunicationEventLimits;

    /** Creates a new instance of CustomerTransferCache */
    public CustomerTransferCache(CustomerControl customerControl) {
        super(customerControl);

        var options = session.getOptions();
        if(options != null) {
            setIncludeUuid(options.contains(PartyOptions.PartyIncludeUuid) || options.contains(CustomerOptions.CustomerIncludeUuid));
            includeUserLogin = options.contains(PartyOptions.PartyIncludeUserLogin);
            includeRecoveryAnswer = options.contains(PartyOptions.PartyIncludeRecoveryAnswer);
            includePartyAliases = options.contains(PartyOptions.PartyIncludePartyAliases);
            includePartyContactMechanisms = options.contains(PartyOptions.PartyIncludePartyContactMechanisms);
            includePartyContactLists = options.contains(PartyOptions.PartyIncludePartyContactLists);
            includePartyDocuments = options.contains(PartyOptions.PartyIncludePartyDocuments);
            includePartyPrinterGroupUses = options.contains(PartyOptions.PartyIncludePartyPrinterGroupUses);
            includePartyScaleUses = options.contains(PartyOptions.PartyIncludePartyScaleUses);
            includePartyCarriers = options.contains(PartyOptions.PartyIncludePartyCarriers);
            includePartyCarrierAccounts = options.contains(PartyOptions.PartyIncludePartyCarrierAccounts);
            includeCustomerServiceComments = options.contains(CustomerOptions.CustomerIncludeCustomerServiceComments);
            includeOrderEntryComments = options.contains(CustomerOptions.CustomerIncludeOrderEntryComments);
            includeBillingAccounts = options.contains(CustomerOptions.CustomerIncludeBillingAccounts);
            includeInvoicesFrom = options.contains(CustomerOptions.CustomerIncludeInvoicesFrom);
            includeInvoicesTo = options.contains(CustomerOptions.CustomerIncludeInvoicesTo);
            includePartyCreditLimits = options.contains(CustomerOptions.CustomerIncludePartyCreditLimits);
            includePartyTerm = options.contains(CustomerOptions.CustomerIncludePartyTerm);
            includePartyFreeOnBoard = options.contains(CustomerOptions.CustomerIncludePartyFreeOnBoard);
            includePartyPaymentMethods = options.contains(CustomerOptions.CustomerIncludePartyPaymentMethods);
            includePartyCancellationPolicies = options.contains(CustomerOptions.CustomerIncludePartyCancellationPolicies);
            includePartyReturnPolicies = options.contains(CustomerOptions.CustomerIncludePartyReturnPolicies);
            includeSubscriptions = options.contains(CustomerOptions.CustomerIncludeSubscriptions);
            includeCommunicationEvents = options.contains(CustomerOptions.CustomerIncludeCommunicationEvents);
            setIncludeEntityAttributeGroups(options.contains(CustomerOptions.CustomerIncludeEntityAttributeGroups));
            setIncludeTagScopes(options.contains(CustomerOptions.CustomerIncludeTagScopes));
        }
        
        setIncludeEntityInstance(true);
        
        hasInvoiceLimits = session.hasLimit(InvoiceFactory.class);
        hasCommunicationEventLimits = session.hasLimit(CommunicationEventFactory.class);
    }

    public CustomerTransfer getTransfer(Customer customer) {
        return getTransfer(customer.getParty());
    }

    public CustomerTransfer getTransfer(Party party) {
        var customerTransfer = get(party);

        if(customerTransfer == null) {
            var partyDetail = party.getLastDetail();
            var partyName = partyDetail.getPartyName();
            var partyTypeTransfer = partyControl.getPartyTypeTransfer(userVisit, partyDetail.getPartyType());
            var preferredLanguage = partyDetail.getPreferredLanguage();
            var preferredLanguageTransfer = preferredLanguage == null ? null : partyControl.getLanguageTransfer(userVisit, preferredLanguage);
            var preferredCurrency = partyDetail.getPreferredCurrency();
            var preferredCurrencyTransfer = preferredCurrency == null ? null : accountingControl.getCurrencyTransfer(userVisit, preferredCurrency);
            var preferredTimeZone = partyDetail.getPreferredTimeZone();
            var preferredTimeZoneTransfer = preferredTimeZone == null ? null : partyControl.getTimeZoneTransfer(userVisit, preferredTimeZone);
            var preferredDateTimeFormat = partyDetail.getPreferredDateTimeFormat();
            var preferredDateTimeFormatTransfer = preferredDateTimeFormat == null ? null : partyControl.getDateTimeFormatTransfer(userVisit, preferredDateTimeFormat);
            var customer = customerControl.getCustomer(party);
            var customerName = customer.getCustomerName();
            var customerTypeTransfer = customerControl.getCustomerTypeTransfer(userVisit, customer.getCustomerType());
            var initialOfferUse = offerUseControl.getOfferUseTransfer(userVisit, customer.getInitialOfferUse());
            var cancellationPolicy = customer.getCancellationPolicy();
            var cancellationPolicyTransfer = cancellationPolicy == null ? null : cancellationPolicyControl.getCancellationPolicyTransfer(userVisit, cancellationPolicy);
            var returnPolicy = customer.getReturnPolicy();
            var returnPolicyTransfer = returnPolicy == null ? null : returnPolicyControl.getReturnPolicyTransfer(userVisit, returnPolicy);
            var arGlAccount = customer.getArGlAccount();
            var arGlAccountTransfer = arGlAccount == null ? null : accountingControl.getGlAccountTransfer(userVisit, arGlAccount);
            var holdUntilComplete = customer.getHoldUntilComplete();
            var allowBackorders = customer.getAllowBackorders();
            var allowSubstitutions = customer.getAllowSubstitutions();
            var allowCombiningShipments = customer.getAllowCombiningShipments();
            var requireReference = customer.getRequireReference();
            var allowReferenceDuplicates = customer.getAllowReferenceDuplicates();
            var referenceValidationPattern = customer.getReferenceValidationPattern();
            var person = partyControl.getPerson(party);
            var personTransfer = person == null ? null : partyControl.getPersonTransfer(userVisit, person);
            var partyGroup = partyControl.getPartyGroup(party);
            var partyGroupTransfer = partyGroup == null ? null : partyControl.getPartyGroupTransfer(userVisit, partyGroup);
            var profile = partyControl.getProfile(party);
            var profileTransfer = profile == null ? null : partyControl.getProfileTransfer(userVisit, profile);

            var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(party.getPrimaryKey());
            var customerStatusTransfer = workflowControl.getWorkflowEntityStatusTransferByEntityInstanceUsingNames(userVisit,
                    CustomerStatusConstants.Workflow_CUSTOMER_STATUS, entityInstance);

            var customerCreditStatusTransfer = workflowControl.getWorkflowEntityStatusTransferByEntityInstanceUsingNames(userVisit,
                    CustomerCreditStatusConstants.Workflow_CUSTOMER_CREDIT_STATUS, entityInstance);

            customerTransfer = new CustomerTransfer(partyName, partyTypeTransfer, preferredLanguageTransfer, preferredCurrencyTransfer,
                    preferredTimeZoneTransfer, preferredDateTimeFormatTransfer, personTransfer, partyGroupTransfer, profileTransfer, customerName,
                    customerTypeTransfer, initialOfferUse, cancellationPolicyTransfer, returnPolicyTransfer, arGlAccountTransfer, holdUntilComplete,
                    allowBackorders, allowSubstitutions, allowCombiningShipments, requireReference, allowReferenceDuplicates, referenceValidationPattern,
                    customerStatusTransfer, customerCreditStatusTransfer);
            put(userVisit, party, customerTransfer, entityInstance);

            if(includeUserLogin) {
                customerTransfer.setUserLogin(userControl.getUserLoginTransfer(userVisit, party));
            }

            if(includeRecoveryAnswer) {
                customerTransfer.setRecoveryAnswer(userControl.getRecoveryAnswerTransfer(userVisit, party));
            }

            if(includePartyAliases) {
                customerTransfer.setPartyAliases(new ListWrapper<>(partyControl.getPartyAliasTransfersByParty(userVisit, party)));
            }

            if(includePartyContactMechanisms) {
                customerTransfer.setPartyContactMechanisms(new ListWrapper<>(contactControl.getPartyContactMechanismTransfersByParty(userVisit, party)));
            }
            
            if(includePartyContactLists) {
                customerTransfer.setPartyContactLists(new ListWrapper<>(contactListControl.getPartyContactListTransfersByParty(userVisit, party)));
            }
            
            if(includePartyDocuments) {
                customerTransfer.setPartyDocuments(new ListWrapper<>(documentControl.getPartyDocumentTransfersByParty(userVisit, party)));
            }

            if(includePartyPrinterGroupUses) {
                customerTransfer.setPartyPrinterGroupUses(new ListWrapper<>(printerControl.getPartyPrinterGroupUseTransfersByParty(userVisit, party)));
            }

            if(includePartyScaleUses) {
                customerTransfer.setPartyScaleUses(new ListWrapper<>(scaleControl.getPartyScaleUseTransfersByParty(userVisit, party)));
            }

            if(includePartyCarriers) {
                customerTransfer.setPartyCarriers(new ListWrapper<>(carrierControl.getPartyCarrierTransfersByParty(userVisit, party)));
            }

            if(includePartyCarrierAccounts) {
                customerTransfer.setPartyCarrierAccounts(new ListWrapper<>(carrierControl.getPartyCarrierAccountTransfersByParty(userVisit, party)));
            }

            if(includeCustomerServiceComments) {
                setupComments(userVisit, null, entityInstance, customerTransfer, CommentConstants.CommentType_CUSTOMER_CUSTOMER_SERVICE);
            }

            if(includeOrderEntryComments) {
                setupComments(userVisit, null, entityInstance, customerTransfer, CommentConstants.CommentType_CUSTOMER_ORDER_ENTRY);
            }
            
            if(includeBillingAccounts) {
                customerTransfer.setBillingAccounts(new ListWrapper<>(billingControl.getBillingAccountTransfersByBillFrom(userVisit, party)));
            }
            
            if(includeInvoicesFrom) {
                customerTransfer.setInvoicesFrom(new ListWrapper<>(invoiceControl.getInvoiceTransfersByInvoiceFrom(userVisit, party)));
                
                if(hasInvoiceLimits) {
                    customerTransfer.setInvoicesFromCount(invoiceControl.countInvoicesByInvoiceFrom(party));
                }
            }
            
            if(includeInvoicesTo) {
                customerTransfer.setInvoicesTo(new ListWrapper<>(invoiceControl.getInvoiceTransfersByInvoiceTo(userVisit, party)));
                
                if(hasInvoiceLimits) {
                    customerTransfer.setInvoicesToCount(invoiceControl.countInvoicesByInvoiceTo(party));
                }
            }

            if(includePartyCreditLimits) {
                customerTransfer.setPartyCreditLimits(new ListWrapper<>(termControl.getPartyCreditLimitTransfersByParty(userVisit, party)));
            }

            if(includePartyTerm) {
                customerTransfer.setPartyTerm(termControl.getPartyTermTransfer(userVisit, party));
            }

            if(includePartyFreeOnBoard) {
                customerTransfer.setPartyFreeOnBoard(partyFreeOnBoardControl.getPartyFreeOnBoardTransfer(userVisit, party));
            }

            if(includePartyPaymentMethods) {
                customerTransfer.setPartyPaymentMethods(new ListWrapper<>(partyPaymentMethodControl.getPartyPaymentMethodTransfersByParty(userVisit, party)));
            }

            if(includePartyCancellationPolicies) {
                customerTransfer.setPartyCancellationPolicies(new ListWrapper<>(cancellationPolicyControl.getPartyCancellationPolicyTransfersByParty(userVisit, party)));
            }

            if(includePartyReturnPolicies) {
                customerTransfer.setPartyReturnPolicies(new ListWrapper<>(returnPolicyControl.getPartyReturnPolicyTransfersByParty(userVisit, party)));
            }

            if(includeSubscriptions) {
                customerTransfer.setSubscriptions(new ListWrapper<>(subscriptionControl.getSubscriptionTransfersByParty(userVisit, party)));
            }

            if(includeCommunicationEvents) {
                customerTransfer.setCommunicationEvents(new ListWrapper<>(communicationControl.getCommunicationEventTransfersByParty(userVisit, party)));

                if(hasCommunicationEventLimits) {
                    customerTransfer.setCommunicationEventsCount(communicationControl.countCommunicationEventsByParty(party));
                }
            }
        }

        return customerTransfer;
    }
}
