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

package com.echothree.model.control.carrier.server.transfer;

import com.echothree.model.control.accounting.common.transfer.CurrencyTransfer;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.carrier.common.CarrierOptions;
import com.echothree.model.control.carrier.common.transfer.CarrierTransfer;
import com.echothree.model.control.carrier.common.transfer.CarrierTypeTransfer;
import com.echothree.model.control.carrier.server.control.CarrierControl;
import com.echothree.model.control.contact.server.control.ContactControl;
import com.echothree.model.control.document.server.control.DocumentControl;
import com.echothree.model.control.party.common.PartyOptions;
import com.echothree.model.control.party.common.transfer.DateTimeFormatTransfer;
import com.echothree.model.control.party.common.transfer.LanguageTransfer;
import com.echothree.model.control.party.common.transfer.PartyGroupTransfer;
import com.echothree.model.control.party.common.transfer.PartyTypeTransfer;
import com.echothree.model.control.party.common.transfer.PersonTransfer;
import com.echothree.model.control.party.common.transfer.TimeZoneTransfer;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.printer.server.control.PrinterControl;
import com.echothree.model.control.scale.server.control.ScaleControl;
import com.echothree.model.control.selector.common.transfer.SelectorTransfer;
import com.echothree.model.control.selector.server.control.SelectorControl;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.carrier.server.entity.Carrier;
import com.echothree.model.data.party.server.entity.DateTimeFormat;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.party.server.entity.PartyDetail;
import com.echothree.model.data.party.server.entity.PartyGroup;
import com.echothree.model.data.party.server.entity.Person;
import com.echothree.model.data.party.server.entity.TimeZone;
import com.echothree.model.data.selector.server.entity.Selector;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.transfer.ListWrapper;
import com.echothree.util.server.persistence.Session;
import java.util.Set;

public class CarrierTransferCache
        extends BaseCarrierTransferCache<Party, CarrierTransfer> {
    
    AccountingControl accountingControl = Session.getModelController(AccountingControl.class);
    ContactControl contactControl = Session.getModelController(ContactControl.class);
    DocumentControl documentControl = Session.getModelController(DocumentControl.class);
    PartyControl partyControl = Session.getModelController(PartyControl.class);
    PrinterControl printerControl = Session.getModelController(PrinterControl.class);
    ScaleControl scaleControl = Session.getModelController(ScaleControl.class);
    SelectorControl selectorControl = Session.getModelController(SelectorControl.class);
    boolean includePartyContactMechanisms;
    boolean includePartyDocuments;
    boolean includePartyPrinterGroupUses;
    boolean includePartyScaleUses;
    
    /** Creates a new instance of CarrierTransferCache */
    public CarrierTransferCache(UserVisit userVisit, CarrierControl carrierControl) {
        super(userVisit, carrierControl);
        
        Set<String> options = session.getOptions();
        if(options != null) {
            setIncludeKey(options.contains(PartyOptions.PartyIncludeKey) || options.contains(CarrierOptions.CarrierIncludeKey));
            setIncludeGuid(options.contains(PartyOptions.PartyIncludeGuid) || options.contains(CarrierOptions.CarrierIncludeGuid));
            includePartyContactMechanisms = options.contains(PartyOptions.PartyIncludePartyContactMechanisms);
            includePartyDocuments = options.contains(PartyOptions.PartyIncludePartyDocuments);
            includePartyPrinterGroupUses = options.contains(PartyOptions.PartyIncludePartyPrinterGroupUses);
            includePartyScaleUses = options.contains(PartyOptions.PartyIncludePartyScaleUses);
            setIncludeEntityAttributeGroups(options.contains(CarrierOptions.CarrierIncludeEntityAttributeGroups));
            setIncludeTagScopes(options.contains(CarrierOptions.CarrierIncludeTagScopes));
        }
        
        setIncludeEntityInstance(true);
    }
    
    public CarrierTransfer getCarrierTransfer(Carrier carrier) {
        return getCarrierTransfer(carrier.getParty());
    }

    public CarrierTransfer getCarrierTransfer(Party party) {
        CarrierTransfer carrierTransfer = get(party);
        
        if(carrierTransfer == null) {
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
            Carrier carrier = carrierControl.getCarrier(party);
            String carrierName = carrier.getCarrierName();
            CarrierTypeTransfer carrierType = carrierControl.getCarrierTypeTransfer(userVisit, carrier.getCarrierType());
            Selector geoCodeSelector = carrier.getGeoCodeSelector();
            SelectorTransfer geoCodeSelectorTransfer = geoCodeSelector == null? null: selectorControl.getSelectorTransfer(userVisit, geoCodeSelector);
            Selector itemSelector = carrier.getItemSelector();
            SelectorTransfer itemSelectorTransfer = itemSelector == null? null: selectorControl.getSelectorTransfer(userVisit, itemSelector);
            String accountValidationPattern = carrier.getAccountValidationPattern();
            Boolean isDefault = carrier.getIsDefault();
            Integer sortOrder = carrier.getSortOrder();
            
            carrierTransfer = new CarrierTransfer(partyName, partyTypeTransfer, preferredLanguageTransfer, preferredCurrencyTransfer, preferredTimeZoneTransfer,
                    preferredDateTimeFormatTransfer, personTransfer, partyGroupTransfer, carrierName, carrierType, geoCodeSelectorTransfer, itemSelectorTransfer,
                    accountValidationPattern, isDefault, sortOrder);
            put(party, carrierTransfer);
            
            if(includePartyContactMechanisms) {
                carrierTransfer.setPartyContactMechanisms(new ListWrapper<>(contactControl.getPartyContactMechanismTransfersByParty(userVisit, party)));
            }

            if(includePartyDocuments) {
                carrierTransfer.setPartyDocuments(new ListWrapper<>(documentControl.getPartyDocumentTransfersByParty(userVisit, party)));
            }

            if(includePartyPrinterGroupUses) {
                carrierTransfer.setPartyPrinterGroupUses(new ListWrapper<>(printerControl.getPartyPrinterGroupUseTransfersByParty(userVisit, party)));
            }

            if(includePartyScaleUses) {
                carrierTransfer.setPartyScaleUses(new ListWrapper<>(scaleControl.getPartyScaleUseTransfersByParty(userVisit, party)));
            }
        }
        
        return carrierTransfer;
    }
    
}
