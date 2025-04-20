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

package com.echothree.model.control.vendor.server.transfer;

import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.cancellationpolicy.server.control.CancellationPolicyControl;
import com.echothree.model.control.carrier.server.control.CarrierControl;
import com.echothree.model.control.comment.common.CommentConstants;
import com.echothree.model.control.communication.server.control.CommunicationControl;
import com.echothree.model.control.contact.server.control.ContactControl;
import com.echothree.model.control.contactlist.server.control.ContactListControl;
import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.control.document.server.control.DocumentControl;
import com.echothree.model.control.invoice.server.control.InvoiceControl;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.party.common.PartyOptions;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.payment.server.control.BillingControl;
import com.echothree.model.control.printer.server.control.PrinterControl;
import com.echothree.model.control.returnpolicy.server.control.ReturnPolicyControl;
import com.echothree.model.control.scale.server.control.ScaleControl;
import com.echothree.model.control.shipment.server.control.PartyFreeOnBoardControl;
import com.echothree.model.control.subscription.server.control.SubscriptionControl;
import com.echothree.model.control.term.server.control.TermControl;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.control.vendor.common.VendorOptions;
import com.echothree.model.control.vendor.common.transfer.VendorTransfer;
import com.echothree.model.control.vendor.common.workflow.VendorStatusConstants;
import com.echothree.model.control.vendor.server.control.VendorControl;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.communication.server.factory.CommunicationEventFactory;
import com.echothree.model.data.invoice.server.factory.InvoiceFactory;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.vendor.server.entity.Vendor;
import com.echothree.model.data.vendor.server.factory.VendorItemFactory;
import com.echothree.util.common.transfer.ListWrapper;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.string.AmountUtils;

public class VendorTransferCache
        extends BaseVendorTransferCache<Party, VendorTransfer> {

    AccountingControl accountingControl = Session.getModelController(AccountingControl.class);
    BillingControl billingControl = Session.getModelController(BillingControl.class);
    CancellationPolicyControl cancellationPolicyControl = Session.getModelController(CancellationPolicyControl.class);
    CarrierControl carrierControl = Session.getModelController(CarrierControl.class);
    CommunicationControl communicationControl = Session.getModelController(CommunicationControl.class);
    ContactControl contactControl = Session.getModelController(ContactControl.class);
    ContactListControl contactListControl = Session.getModelController(ContactListControl.class);
    DocumentControl documentControl = Session.getModelController(DocumentControl.class);
    EntityInstanceControl entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
    ItemControl itemControl = Session.getModelController(ItemControl.class);
    InvoiceControl invoiceControl = Session.getModelController(InvoiceControl.class);
    PartyControl partyControl = Session.getModelController(PartyControl.class);
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
    boolean includeVendorItems;
    boolean includeBillingAccounts;
    boolean includeInvoicesFrom;
    boolean includeInvoicesTo;
    boolean includePurchasingComments;
    boolean includePartyCreditLimits;
    boolean includePartyTerm;
    boolean includePartyFreeOnBoard;
    boolean includeSubscriptions;
    boolean includeCommunicationEvents;
    boolean hasVendorItemsLimits;
    boolean hasInvoiceLimits;
    boolean hasCommunicationEventLimits;

    /** Creates a new instance of VendorTransferCache */
    public VendorTransferCache(UserVisit userVisit, VendorControl vendorControl) {
        super(userVisit, vendorControl);

        var options = session.getOptions();
        if(options != null) {
            setIncludeUuid(options.contains(PartyOptions.PartyIncludeUuid) || options.contains(VendorOptions.VendorIncludeUuid));
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
            includeBillingAccounts = options.contains(VendorOptions.VendorIncludeBillingAccounts);
            includeVendorItems = options.contains(VendorOptions.VendorIncludeVendorItems);
            includeInvoicesFrom = options.contains(VendorOptions.VendorIncludeInvoicesFrom);
            includeInvoicesTo = options.contains(VendorOptions.VendorIncludeInvoicesTo);
            includePurchasingComments = options.contains(VendorOptions.VendorIncludePurchasingComments);
            includePartyCreditLimits = options.contains(VendorOptions.VendorIncludePartyCreditLimits);
            includePartyTerm = options.contains(VendorOptions.VendorIncludePartyTerm);
            includePartyFreeOnBoard = options.contains(VendorOptions.VendorIncludePartyFreeOnBoard);
            includeSubscriptions = options.contains(VendorOptions.VendorIncludeSubscriptions);
            includeCommunicationEvents = options.contains(VendorOptions.VendorIncludeCommunicationEvents);
            setIncludeEntityAttributeGroups(options.contains(VendorOptions.VendorIncludeEntityAttributeGroups));
            setIncludeTagScopes(options.contains(VendorOptions.VendorIncludeTagScopes));
        }
        
        setIncludeEntityInstance(true);
        
        hasVendorItemsLimits = session.hasLimit(VendorItemFactory.class);
        hasInvoiceLimits = session.hasLimit(InvoiceFactory.class);
        hasCommunicationEventLimits = session.hasLimit(CommunicationEventFactory.class);
    }

    public VendorTransfer getTransfer(Vendor vendor) {
        return getTransfer(vendor.getParty());
    }

    public VendorTransfer getTransfer(Party party) {
        var vendorTransfer = get(party);

        if(vendorTransfer == null) {
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
            var vendor = vendorControl.getVendor(party);
            var vendorName = vendor.getVendorName();
            var vendorType = vendorControl.getVendorTypeTransfer(userVisit, vendor.getVendorType());
            var minimumPurchaseOrderLines = vendor.getMinimumPurchaseOrderLines();
            var maximumPurchaseOrderLines = vendor.getMaximumPurchaseOrderLines();
            var currency = partyControl.getPreferredCurrency(party);
            var unformattedMinimumPurchaseOrderAmount = vendor.getMinimumPurchaseOrderAmount();
            var minimumPurchaseOrderAmount = AmountUtils.getInstance().formatCostLine(currency, unformattedMinimumPurchaseOrderAmount);
            var unformattedMaximumPurchaseOrderAmount = vendor.getMaximumPurchaseOrderAmount();
            var maximumPurchaseOrderAmount = AmountUtils.getInstance().formatCostLine(currency, unformattedMaximumPurchaseOrderAmount);
            var useItemPurchasingCategories = vendor.getUseItemPurchasingCategories();
            var defaultItemAliasType = vendor.getDefaultItemAliasType();
            var defaultItemAliasTypeTransfer = defaultItemAliasType == null ? null : itemControl.getItemAliasTypeTransfer(userVisit, defaultItemAliasType);
            var cancellationPolicy = vendor.getCancellationPolicy();
            var cancellationPolicyTransfer = cancellationPolicy == null ? null : cancellationPolicyControl.getCancellationPolicyTransfer(userVisit, cancellationPolicy);
            var returnPolicy = vendor.getReturnPolicy();
            var returnPolicyTransfer = returnPolicy == null ? null : returnPolicyControl.getReturnPolicyTransfer(userVisit, returnPolicy);
            var apGlAccount = vendor.getApGlAccount();
            var apGlAccountTransfer = apGlAccount == null ? null : accountingControl.getGlAccountTransfer(userVisit, apGlAccount);
            var holdUntilComplete = vendor.getHoldUntilComplete();
            var allowBackorders = vendor.getAllowBackorders();
            var allowSubstitutions = vendor.getAllowSubstitutions();
            var allowCombiningShipments = vendor.getAllowCombiningShipments();
            var requireReference = vendor.getRequireReference();
            var allowReferenceDuplicates = vendor.getAllowReferenceDuplicates();
            var referenceValidationPattern = vendor.getReferenceValidationPattern();
            var person = partyControl.getPerson(party);
            var personTransfer = person == null ? null : partyControl.getPersonTransfer(userVisit, person);
            var partyGroup = partyControl.getPartyGroup(party);
            var partyGroupTransfer = partyGroup == null ? null : partyControl.getPartyGroupTransfer(userVisit, partyGroup);

            var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(party.getPrimaryKey());
            var vendorStatusTransfer = workflowControl.getWorkflowEntityStatusTransferByEntityInstanceUsingNames(userVisit,
                    VendorStatusConstants.Workflow_VENDOR_STATUS, entityInstance);

            vendorTransfer = new VendorTransfer(partyName, partyTypeTransfer, preferredLanguageTransfer, preferredCurrencyTransfer, preferredTimeZoneTransfer,
                    preferredDateTimeFormatTransfer, personTransfer, partyGroupTransfer, vendorName, vendorType, minimumPurchaseOrderLines,
                    maximumPurchaseOrderLines, unformattedMinimumPurchaseOrderAmount, minimumPurchaseOrderAmount, unformattedMaximumPurchaseOrderAmount,
                    maximumPurchaseOrderAmount, useItemPurchasingCategories, defaultItemAliasTypeTransfer, cancellationPolicyTransfer, returnPolicyTransfer,
                    apGlAccountTransfer, holdUntilComplete, allowBackorders, allowSubstitutions, allowCombiningShipments, requireReference,
                    allowReferenceDuplicates, referenceValidationPattern, vendorStatusTransfer);
            put(party, vendorTransfer);

            if(includeUserLogin) {
                vendorTransfer.setUserLogin(userControl.getUserLoginTransfer(userVisit, party));
            }

            if(includeRecoveryAnswer) {
                vendorTransfer.setRecoveryAnswer(userControl.getRecoveryAnswerTransfer(userVisit, party));
            }

            if(includePartyAliases) {
                vendorTransfer.setPartyAliases(new ListWrapper<>(partyControl.getPartyAliasTransfersByParty(userVisit, party)));
            }

            if(includePartyContactMechanisms) {
                vendorTransfer.setPartyContactMechanisms(new ListWrapper<>(contactControl.getPartyContactMechanismTransfersByParty(userVisit, party)));
            }
            
            if(includePartyContactLists) {
                vendorTransfer.setPartyContactLists(new ListWrapper<>(contactListControl.getPartyContactListTransfersByParty(userVisit, party)));
            }

            if(includePartyDocuments) {
                vendorTransfer.setPartyDocuments(new ListWrapper<>(documentControl.getPartyDocumentTransfersByParty(userVisit, party)));
            }

            if(includePartyPrinterGroupUses) {
                vendorTransfer.setPartyPrinterGroupUses(new ListWrapper<>(printerControl.getPartyPrinterGroupUseTransfersByParty(userVisit, party)));
            }

            if(includePartyScaleUses) {
                vendorTransfer.setPartyScaleUses(new ListWrapper<>(scaleControl.getPartyScaleUseTransfersByParty(userVisit, party)));
            }

            if(includePartyCarriers) {
                vendorTransfer.setPartyCarriers(new ListWrapper<>(carrierControl.getPartyCarrierTransfersByParty(userVisit, party)));
            }

            if(includePartyCarrierAccounts) {
                vendorTransfer.setPartyCarrierAccounts(new ListWrapper<>(carrierControl.getPartyCarrierAccountTransfersByParty(userVisit, party)));
            }
            
            if(includeBillingAccounts) {
                vendorTransfer.setBillingAccounts(new ListWrapper<>(billingControl.getBillingAccountTransfersByBillFrom(userVisit, party)));
            }
            
            if(includeVendorItems) {
                vendorTransfer.setVendorItems(new ListWrapper<>(vendorControl.getVendorItemTransfersByVendorParty(userVisit, party)));
                
                if(hasVendorItemsLimits) {
                    vendorTransfer.setVendorItemsCount(vendorControl.countVendorItemsByVendorParty(party));
                }
            }
            
            if(includeInvoicesFrom) {
                vendorTransfer.setInvoicesFrom(new ListWrapper<>(invoiceControl.getInvoiceTransfersByInvoiceFrom(userVisit, party)));
                
                if(hasInvoiceLimits) {
                    vendorTransfer.setInvoicesFromCount(invoiceControl.countInvoicesByInvoiceFrom(party));
                }
            }
            
            if(includeInvoicesTo) {
                vendorTransfer.setInvoicesTo(new ListWrapper<>(invoiceControl.getInvoiceTransfersByInvoiceTo(userVisit, party)));
                
                if(hasInvoiceLimits) {
                    vendorTransfer.setInvoicesToCount(invoiceControl.countInvoicesByInvoiceTo(party));
                }
            }

            if(includePurchasingComments) {
                setupComments(null, entityInstance, vendorTransfer, CommentConstants.CommentType_VENDOR_PURCHASING);
            }

            if(includePartyCreditLimits) {
                vendorTransfer.setPartyCreditLimits(new ListWrapper<>(termControl.getPartyCreditLimitTransfersByParty(userVisit, party)));
            }

            if(includePartyTerm) {
                vendorTransfer.setPartyTerm(termControl.getPartyTermTransfer(userVisit, party));
            }

            if(includePartyFreeOnBoard) {
                vendorTransfer.setPartyFreeOnBoard(partyFreeOnBoardControl.getPartyFreeOnBoardTransfer(userVisit, party));
            }

            if(includeSubscriptions) {
                vendorTransfer.setSubscriptions(new ListWrapper<>(subscriptionControl.getSubscriptionTransfersByParty(userVisit, party)));
            }

            if(includeCommunicationEvents) {
                vendorTransfer.setCommunicationEvents(new ListWrapper<>(communicationControl.getCommunicationEventTransfersByParty(userVisit, party)));

                if(hasCommunicationEventLimits) {
                    vendorTransfer.setCommunicationEventsCount(communicationControl.countCommunicationEventsByParty(party));
                }
            }
        }

        return vendorTransfer;
    }
}
