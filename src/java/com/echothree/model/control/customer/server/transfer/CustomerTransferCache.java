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

package com.echothree.model.control.customer.server.transfer;

import com.echothree.model.control.accounting.common.transfer.CurrencyTransfer;
import com.echothree.model.control.accounting.common.transfer.GlAccountTransfer;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.cancellationpolicy.common.transfer.CancellationPolicyTransfer;
import com.echothree.model.control.cancellationpolicy.server.control.CancellationPolicyControl;
import com.echothree.model.control.carrier.server.control.CarrierControl;
import com.echothree.model.control.comment.common.CommentConstants;
import com.echothree.model.control.communication.server.control.CommunicationControl;
import com.echothree.model.control.contact.server.control.ContactControl;
import com.echothree.model.control.contactlist.server.ContactListControl;
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.control.customer.common.CustomerOptions;
import com.echothree.model.control.customer.common.transfer.CustomerTransfer;
import com.echothree.model.control.customer.common.transfer.CustomerTypeTransfer;
import com.echothree.model.control.customer.common.workflow.CustomerCreditStatusConstants;
import com.echothree.model.control.customer.common.workflow.CustomerStatusConstants;
import com.echothree.model.control.customer.server.control.CustomerControl;
import com.echothree.model.control.document.server.DocumentControl;
import com.echothree.model.control.invoice.server.InvoiceControl;
import com.echothree.model.control.offer.common.transfer.OfferUseTransfer;
import com.echothree.model.control.offer.server.control.OfferUseControl;
import com.echothree.model.control.party.common.PartyOptions;
import com.echothree.model.control.party.common.transfer.DateTimeFormatTransfer;
import com.echothree.model.control.party.common.transfer.LanguageTransfer;
import com.echothree.model.control.party.common.transfer.PartyGroupTransfer;
import com.echothree.model.control.party.common.transfer.PartyTypeTransfer;
import com.echothree.model.control.party.common.transfer.PersonTransfer;
import com.echothree.model.control.party.common.transfer.ProfileTransfer;
import com.echothree.model.control.party.common.transfer.TimeZoneTransfer;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.payment.server.control.BillingControl;
import com.echothree.model.control.payment.server.control.PartyPaymentMethodControl;
import com.echothree.model.control.printer.server.PrinterControl;
import com.echothree.model.control.returnpolicy.common.transfer.ReturnPolicyTransfer;
import com.echothree.model.control.returnpolicy.server.ReturnPolicyControl;
import com.echothree.model.control.scale.server.ScaleControl;
import com.echothree.model.control.shipment.server.control.PartyFreeOnBoardControl;
import com.echothree.model.control.subscription.server.SubscriptionControl;
import com.echothree.model.control.term.server.TermControl;
import com.echothree.model.control.user.server.UserControl;
import com.echothree.model.control.workflow.common.transfer.WorkflowEntityStatusTransfer;
import com.echothree.model.control.workflow.server.WorkflowControl;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.accounting.server.entity.GlAccount;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationPolicy;
import com.echothree.model.data.communication.server.factory.CommunicationEventFactory;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.customer.server.entity.Customer;
import com.echothree.model.data.invoice.server.factory.InvoiceFactory;
import com.echothree.model.data.party.server.entity.DateTimeFormat;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.party.server.entity.PartyDetail;
import com.echothree.model.data.party.server.entity.PartyGroup;
import com.echothree.model.data.party.server.entity.Person;
import com.echothree.model.data.party.server.entity.Profile;
import com.echothree.model.data.party.server.entity.TimeZone;
import com.echothree.model.data.returnpolicy.server.entity.ReturnPolicy;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.transfer.ListWrapper;
import com.echothree.util.server.persistence.Session;
import java.util.Set;

public class CustomerTransferCache
        extends BaseCustomerTransferCache<Party, CustomerTransfer> {

    AccountingControl accountingControl = (AccountingControl)Session.getModelController(AccountingControl.class);
    BillingControl billingControl = (BillingControl)Session.getModelController(BillingControl.class);
    CancellationPolicyControl cancellationPolicyControl = (CancellationPolicyControl)Session.getModelController(CancellationPolicyControl.class);
    CarrierControl carrierControl = (CarrierControl)Session.getModelController(CarrierControl.class);
    CommunicationControl communicationControl = (CommunicationControl)Session.getModelController(CommunicationControl.class);
    ContactControl contactControl = (ContactControl)Session.getModelController(ContactControl.class);
    ContactListControl contactListControl = (ContactListControl)Session.getModelController(ContactListControl.class);
    CoreControl coreControl = (CoreControl)Session.getModelController(CoreControl.class);
    DocumentControl documentControl = (DocumentControl)Session.getModelController(DocumentControl.class);
    InvoiceControl invoiceControl = (InvoiceControl)Session.getModelController(InvoiceControl.class);
    OfferUseControl offerUseControl = (OfferUseControl)Session.getModelController(OfferUseControl.class);
    PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);
    PartyPaymentMethodControl partyPaymentMethodControl = (PartyPaymentMethodControl)Session.getModelController(PartyPaymentMethodControl.class);
    PartyFreeOnBoardControl partyFreeOnBoardControl = (PartyFreeOnBoardControl)Session.getModelController(PartyFreeOnBoardControl.class);
    PrinterControl printerControl = (PrinterControl)Session.getModelController(PrinterControl.class);
    ReturnPolicyControl returnPolicyControl = (ReturnPolicyControl)Session.getModelController(ReturnPolicyControl.class);
    ScaleControl scaleControl = (ScaleControl)Session.getModelController(ScaleControl.class);
    SubscriptionControl subscriptionControl = (SubscriptionControl)Session.getModelController(SubscriptionControl.class);
    TermControl termControl = (TermControl)Session.getModelController(TermControl.class);
    UserControl userControl = (UserControl)Session.getModelController(UserControl.class);
    WorkflowControl workflowControl = (WorkflowControl)Session.getModelController(WorkflowControl.class);

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
    public CustomerTransferCache(UserVisit userVisit, CustomerControl customerControl) {
        super(userVisit, customerControl);

        Set<String> options = session.getOptions();
        if(options != null) {
            setIncludeKey(options.contains(PartyOptions.PartyIncludeKey) || options.contains(CustomerOptions.CustomerIncludeKey));
            setIncludeGuid(options.contains(PartyOptions.PartyIncludeGuid) || options.contains(CustomerOptions.CustomerIncludeGuid));
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

    public CustomerTransfer getCustomerTransfer(Customer customer) {
        return getCustomerTransfer(customer.getParty());
    }

    public CustomerTransfer getCustomerTransfer(Party party) {
        CustomerTransfer customerTransfer = get(party);

        if(customerTransfer == null) {
            PartyDetail partyDetail = party.getLastDetail();
            String partyName = partyDetail.getPartyName();
            PartyTypeTransfer partyTypeTransfer = partyControl.getPartyTypeTransfer(userVisit, partyDetail.getPartyType());
            Language preferredLanguage = partyDetail.getPreferredLanguage();
            LanguageTransfer preferredLanguageTransfer = preferredLanguage == null ? null : partyControl.getLanguageTransfer(userVisit, preferredLanguage);
            Currency preferredCurrency = partyDetail.getPreferredCurrency();
            CurrencyTransfer preferredCurrencyTransfer = preferredCurrency == null ? null : accountingControl.getCurrencyTransfer(userVisit, preferredCurrency);
            TimeZone preferredTimeZone = partyDetail.getPreferredTimeZone();
            TimeZoneTransfer preferredTimeZoneTransfer = preferredTimeZone == null ? null : partyControl.getTimeZoneTransfer(userVisit, preferredTimeZone);
            DateTimeFormat preferredDateTimeFormat = partyDetail.getPreferredDateTimeFormat();
            DateTimeFormatTransfer preferredDateTimeFormatTransfer = preferredDateTimeFormat == null ? null : partyControl.getDateTimeFormatTransfer(userVisit, preferredDateTimeFormat);
            Customer customer = customerControl.getCustomer(party);
            String customerName = customer.getCustomerName();
            CustomerTypeTransfer customerTypeTransfer = customerControl.getCustomerTypeTransfer(userVisit, customer.getCustomerType());
            OfferUseTransfer initialOfferUse = offerUseControl.getOfferUseTransfer(userVisit, customer.getInitialOfferUse());
            CancellationPolicy cancellationPolicy = customer.getCancellationPolicy();
            CancellationPolicyTransfer cancellationPolicyTransfer = cancellationPolicy == null ? null : cancellationPolicyControl.getCancellationPolicyTransfer(userVisit, cancellationPolicy);
            ReturnPolicy returnPolicy = customer.getReturnPolicy();
            ReturnPolicyTransfer returnPolicyTransfer = returnPolicy == null ? null : returnPolicyControl.getReturnPolicyTransfer(userVisit, returnPolicy);
            GlAccount arGlAccount = customer.getArGlAccount();
            GlAccountTransfer arGlAccountTransfer = arGlAccount == null ? null : accountingControl.getGlAccountTransfer(userVisit, arGlAccount);
            Boolean holdUntilComplete = customer.getHoldUntilComplete();
            Boolean allowBackorders = customer.getAllowBackorders();
            Boolean allowSubstitutions = customer.getAllowSubstitutions();
            Boolean allowCombiningShipments = customer.getAllowCombiningShipments();
            Boolean requireReference = customer.getRequireReference();
            Boolean allowReferenceDuplicates = customer.getAllowReferenceDuplicates();
            String referenceValidationPattern = customer.getReferenceValidationPattern();
            Person person = partyControl.getPerson(party);
            PersonTransfer personTransfer = person == null ? null : partyControl.getPersonTransfer(userVisit, person);
            PartyGroup partyGroup = partyControl.getPartyGroup(party);
            PartyGroupTransfer partyGroupTransfer = partyGroup == null ? null : partyControl.getPartyGroupTransfer(userVisit, partyGroup);
            Profile profile = partyControl.getProfile(party);
            ProfileTransfer profileTransfer = profile == null ? null : partyControl.getProfileTransfer(userVisit, profile);

            EntityInstance entityInstance = coreControl.getEntityInstanceByBasePK(party.getPrimaryKey());
            WorkflowEntityStatusTransfer customerStatusTransfer = workflowControl.getWorkflowEntityStatusTransferByEntityInstanceUsingNames(userVisit,
                    CustomerStatusConstants.Workflow_CUSTOMER_STATUS, entityInstance);

            WorkflowEntityStatusTransfer customerCreditStatusTransfer = workflowControl.getWorkflowEntityStatusTransferByEntityInstanceUsingNames(userVisit,
                    CustomerCreditStatusConstants.Workflow_CUSTOMER_CREDIT_STATUS, entityInstance);

            customerTransfer = new CustomerTransfer(partyName, partyTypeTransfer, preferredLanguageTransfer, preferredCurrencyTransfer,
                    preferredTimeZoneTransfer, preferredDateTimeFormatTransfer, personTransfer, partyGroupTransfer, profileTransfer, customerName,
                    customerTypeTransfer, initialOfferUse, cancellationPolicyTransfer, returnPolicyTransfer, arGlAccountTransfer, holdUntilComplete,
                    allowBackorders, allowSubstitutions, allowCombiningShipments, requireReference, allowReferenceDuplicates, referenceValidationPattern,
                    customerStatusTransfer, customerCreditStatusTransfer);
            put(party, customerTransfer, entityInstance);

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
                setupComments(null, entityInstance, customerTransfer, CommentConstants.CommentType_CUSTOMER_CUSTOMER_SERVICE);
            }

            if(includeOrderEntryComments) {
                setupComments(null, entityInstance, customerTransfer, CommentConstants.CommentType_CUSTOMER_ORDER_ENTRY);
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
