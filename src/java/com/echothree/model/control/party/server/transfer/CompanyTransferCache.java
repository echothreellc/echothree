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

package com.echothree.model.control.party.server.transfer;

import com.echothree.model.control.accounting.common.transfer.CurrencyTransfer;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.carrier.server.control.CarrierControl;
import com.echothree.model.control.contact.server.control.ContactControl;
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.control.document.server.control.DocumentControl;
import com.echothree.model.control.invoice.server.control.InvoiceControl;
import com.echothree.model.control.party.common.PartyOptions;
import com.echothree.model.control.party.common.transfer.CompanyTransfer;
import com.echothree.model.control.party.common.transfer.DateTimeFormatTransfer;
import com.echothree.model.control.party.common.transfer.LanguageTransfer;
import com.echothree.model.control.party.common.transfer.PartyGroupTransfer;
import com.echothree.model.control.party.common.transfer.PartyTypeTransfer;
import com.echothree.model.control.party.common.transfer.PersonTransfer;
import com.echothree.model.control.party.common.transfer.TimeZoneTransfer;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.printer.server.PrinterControl;
import com.echothree.model.control.scale.server.ScaleControl;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.invoice.server.factory.InvoiceFactory;
import com.echothree.model.data.party.server.entity.DateTimeFormat;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.party.server.entity.PartyCompany;
import com.echothree.model.data.party.server.entity.PartyDetail;
import com.echothree.model.data.party.server.entity.PartyGroup;
import com.echothree.model.data.party.server.entity.Person;
import com.echothree.model.data.party.server.entity.TimeZone;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.transfer.ListWrapper;
import com.echothree.util.server.persistence.Session;
import java.util.Set;

public class CompanyTransferCache
        extends BasePartyTransferCache<Party, CompanyTransfer> {
    
    AccountingControl accountingControl = (AccountingControl)Session.getModelController(AccountingControl.class);
    CarrierControl carrierControl = (CarrierControl)Session.getModelController(CarrierControl.class);
    ContactControl contactControl = (ContactControl)Session.getModelController(ContactControl.class);
    CoreControl coreControl = (CoreControl)Session.getModelController(CoreControl.class);
    DocumentControl documentControl = (DocumentControl)Session.getModelController(DocumentControl.class);
    InvoiceControl invoiceControl = (InvoiceControl)Session.getModelController(InvoiceControl.class);
    PrinterControl printerControl = (PrinterControl)Session.getModelController(PrinterControl.class);
    ScaleControl scaleControl = (ScaleControl)Session.getModelController(ScaleControl.class);
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
    public CompanyTransferCache(UserVisit userVisit, PartyControl partyControl) {
        super(userVisit, partyControl);
        
        Set<String> options = session.getOptions();
        if(options != null) {
            setIncludeKey(options.contains(PartyOptions.PartyIncludeKey) || options.contains(PartyOptions.CompanyIncludeKey));
            setIncludeGuid(options.contains(PartyOptions.PartyIncludeGuid) || options.contains(PartyOptions.CompanyIncludeGuid));
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
    
    public CompanyTransfer getCompanyTransfer(PartyCompany partyCompany) {
        return getCompanyTransfer(partyCompany.getParty());
    }
    
    public CompanyTransfer getCompanyTransfer(Party party) {
        CompanyTransfer companyTransfer = get(party);
        
        if(companyTransfer == null) {
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
            Person person = partyControl.getPerson(party);
            PersonTransfer personTransfer = person == null ? null : partyControl.getPersonTransfer(userVisit, person);
            PartyGroup partyGroup = partyControl.getPartyGroup(party);
            PartyGroupTransfer partyGroupTransfer = partyGroup == null ? null : partyControl.getPartyGroupTransfer(userVisit, partyGroup);
            PartyCompany partyCompany = partyControl.getPartyCompany(party);
            String companyName = partyCompany.getPartyCompanyName();
            String isDefault = partyCompany.getIsDefault().toString();
            String sortOrder = partyCompany.getSortOrder().toString();
            
            companyTransfer = new CompanyTransfer(partyName, partyTypeTransfer, preferredLanguageTransfer, preferredCurrencyTransfer, preferredTimeZoneTransfer, preferredDateTimeFormatTransfer,
                    personTransfer, partyGroupTransfer, companyName, isDefault, sortOrder);
            put(party, companyTransfer);
            
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
