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

package com.echothree.model.control.party.server.transfer;

import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.carrier.server.control.CarrierControl;
import com.echothree.model.control.contact.server.control.ContactControl;
import com.echothree.model.control.document.server.control.DocumentControl;
import com.echothree.model.control.invoice.server.control.InvoiceControl;
import com.echothree.model.control.party.common.PartyOptions;
import com.echothree.model.control.party.common.transfer.CompanyTransfer;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.printer.server.control.PrinterControl;
import com.echothree.model.control.scale.server.control.ScaleControl;
import com.echothree.model.data.invoice.server.factory.InvoiceFactory;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.party.server.entity.PartyCompany;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.transfer.ListWrapper;
import com.echothree.util.server.persistence.Session;

public class CompanyTransferCache
        extends BasePartyTransferCache<Party, CompanyTransfer> {
    
    AccountingControl accountingControl = Session.getModelController(AccountingControl.class);
    CarrierControl carrierControl = Session.getModelController(CarrierControl.class);
    ContactControl contactControl = Session.getModelController(ContactControl.class);
    DocumentControl documentControl = Session.getModelController(DocumentControl.class);
    InvoiceControl invoiceControl = Session.getModelController(InvoiceControl.class);
    PartyControl partyControl = Session.getModelController(PartyControl.class);
    PrinterControl printerControl = Session.getModelController(PrinterControl.class);
    ScaleControl scaleControl = Session.getModelController(ScaleControl.class);
    boolean includePartyContactMechanisms;
    boolean includePartyDocuments;
    boolean includePartyPrinterGroupUses;
    boolean includePartyScaleUses;
    boolean includePartyCarriers;
    boolean includePartyCarrierAccounts;
    boolean includeBillingAccounts;
    boolean includeInvoicesFrom;
    boolean includeInvoicesTo;
    boolean hasInvoiceLimits;
    
    /** Creates a new instance of CompanyTransferCache */
    public CompanyTransferCache(UserVisit userVisit) {
        super(userVisit);
        
        var options = session.getOptions();
        if(options != null) {
            setIncludeUuid(options.contains(PartyOptions.PartyIncludeUuid) || options.contains(PartyOptions.CompanyIncludeUuid));
            includePartyContactMechanisms = options.contains(PartyOptions.PartyIncludePartyContactMechanisms);
            includePartyDocuments = options.contains(PartyOptions.PartyIncludePartyDocuments);
            includePartyPrinterGroupUses = options.contains(PartyOptions.PartyIncludePartyPrinterGroupUses);
            includePartyScaleUses = options.contains(PartyOptions.PartyIncludePartyScaleUses);
            includePartyCarriers = options.contains(PartyOptions.PartyIncludePartyCarriers);
            includePartyCarrierAccounts = options.contains(PartyOptions.PartyIncludePartyCarrierAccounts);
            includeBillingAccounts = options.contains(PartyOptions.CompanyIncludeBillingAccounts);
            includeInvoicesFrom = options.contains(PartyOptions.CompanyIncludeInvoicesFrom);
            includeInvoicesTo = options.contains(PartyOptions.CompanyIncludeInvoicesTo);
            setIncludeEntityAttributeGroups(options.contains(PartyOptions.CompanyIncludeEntityAttributeGroups));
            setIncludeTagScopes(options.contains(PartyOptions.CompanyIncludeTagScopes));
        }
        
        setIncludeEntityInstance(true);
        
        hasInvoiceLimits = session.hasLimit(InvoiceFactory.class);
    }
    
    public CompanyTransfer getTransfer(PartyCompany partyCompany) {
        return getTransfer(partyCompany.getParty());
    }

    @Override
    public CompanyTransfer getTransfer(Party party) {
        var companyTransfer = get(party);
        
        if(companyTransfer == null) {
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
            var person = partyControl.getPerson(party);
            var personTransfer = person == null ? null : partyControl.getPersonTransfer(userVisit, person);
            var partyGroup = partyControl.getPartyGroup(party);
            var partyGroupTransfer = partyGroup == null ? null : partyControl.getPartyGroupTransfer(userVisit, partyGroup);
            var partyCompany = partyControl.getPartyCompany(party);
            var companyName = partyCompany.getPartyCompanyName();
            var isDefault = partyCompany.getIsDefault().toString();
            var sortOrder = partyCompany.getSortOrder().toString();
            
            companyTransfer = new CompanyTransfer(partyName, partyTypeTransfer, preferredLanguageTransfer, preferredCurrencyTransfer, preferredTimeZoneTransfer, preferredDateTimeFormatTransfer,
                    personTransfer, partyGroupTransfer, companyName, isDefault, sortOrder);
            put(userVisit, party, companyTransfer);
            
            if(includePartyContactMechanisms) {
                companyTransfer.setPartyContactMechanisms(new ListWrapper<>(contactControl.getPartyContactMechanismTransfersByParty(userVisit, party)));
            }

            if(includePartyDocuments) {
                companyTransfer.setPartyDocuments(new ListWrapper<>(documentControl.getPartyDocumentTransfersByParty(userVisit, party)));
            }

            if(includePartyPrinterGroupUses) {
                companyTransfer.setPartyPrinterGroupUses(new ListWrapper<>(printerControl.getPartyPrinterGroupUseTransfersByParty(userVisit, party)));
            }

            if(includePartyScaleUses) {
                companyTransfer.setPartyScaleUses(new ListWrapper<>(scaleControl.getPartyScaleUseTransfersByParty(userVisit, party)));
            }

            if(includePartyCarriers) {
                companyTransfer.setPartyCarriers(new ListWrapper<>(carrierControl.getPartyCarrierTransfersByParty(userVisit, party)));
            }

            if(includePartyCarrierAccounts) {
                companyTransfer.setPartyCarrierAccounts(new ListWrapper<>(carrierControl.getPartyCarrierAccountTransfersByParty(userVisit, party)));
            }

            if(includeInvoicesFrom) {
                companyTransfer.setInvoicesFrom(new ListWrapper<>(invoiceControl.getInvoiceTransfersByInvoiceFrom(userVisit, party)));
                
                if(hasInvoiceLimits) {
                    companyTransfer.setInvoicesFromCount(invoiceControl.countInvoicesByInvoiceFrom(party));
                }
            }
            
            if(includeInvoicesTo) {
                companyTransfer.setInvoicesTo(new ListWrapper<>(invoiceControl.getInvoiceTransfersByInvoiceTo(userVisit, party)));
                
                if(hasInvoiceLimits) {
                    companyTransfer.setInvoicesToCount(invoiceControl.countInvoicesByInvoiceTo(party));
                }
            }
        }
        
        return companyTransfer;
    }
    
}
