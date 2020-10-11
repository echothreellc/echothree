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
import com.echothree.model.control.contact.server.ContactControl;
import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.control.document.server.DocumentControl;
import com.echothree.model.control.party.common.PartyOptions;
import com.echothree.model.control.party.common.transfer.DateTimeFormatTransfer;
import com.echothree.model.control.party.common.transfer.DepartmentTransfer;
import com.echothree.model.control.party.common.transfer.DivisionTransfer;
import com.echothree.model.control.party.common.transfer.LanguageTransfer;
import com.echothree.model.control.party.common.transfer.PartyGroupTransfer;
import com.echothree.model.control.party.common.transfer.PartyTypeTransfer;
import com.echothree.model.control.party.common.transfer.PersonTransfer;
import com.echothree.model.control.party.common.transfer.TimeZoneTransfer;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.printer.server.PrinterControl;
import com.echothree.model.control.scale.server.ScaleControl;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.party.server.entity.DateTimeFormat;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.party.server.entity.PartyDepartment;
import com.echothree.model.data.party.server.entity.PartyDetail;
import com.echothree.model.data.party.server.entity.PartyGroup;
import com.echothree.model.data.party.server.entity.Person;
import com.echothree.model.data.party.server.entity.TimeZone;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.transfer.ListWrapper;
import com.echothree.util.server.persistence.Session;
import java.util.Set;

public class DepartmentTransferCache
        extends BasePartyTransferCache<Party, DepartmentTransfer> {
    
    AccountingControl accountingControl = (AccountingControl)Session.getModelController(AccountingControl.class);
    ContactControl contactControl = (ContactControl)Session.getModelController(ContactControl.class);
    CoreControl coreControl = (CoreControl)Session.getModelController(CoreControl.class);
    DocumentControl documentControl = (DocumentControl)Session.getModelController(DocumentControl.class);
    PrinterControl printerControl = (PrinterControl)Session.getModelController(PrinterControl.class);
    ScaleControl scaleControl = (ScaleControl)Session.getModelController(ScaleControl.class);
    boolean includePartyContactMechanisms;
    boolean includePartyDocuments;
    boolean includePartyPrinterGroupUses;
    boolean includePartyScaleUses;
    
    /** Creates a new instance of DepartmentTransferCache */
    public DepartmentTransferCache(UserVisit userVisit, PartyControl partyControl) {
        super(userVisit, partyControl);
        
        Set<String> options = session.getOptions();
        if(options != null) {
            setIncludeKey(options.contains(PartyOptions.PartyIncludeKey) || options.contains(PartyOptions.DepartmentIncludeKey));
            setIncludeGuid(options.contains(PartyOptions.PartyIncludeGuid) || options.contains(PartyOptions.DepartmentIncludeGuid));
            includePartyContactMechanisms = options.contains(PartyOptions.PartyIncludePartyContactMechanisms);
            includePartyDocuments = options.contains(PartyOptions.PartyIncludePartyDocuments);
            includePartyPrinterGroupUses = options.contains(PartyOptions.PartyIncludePartyPrinterGroupUses);
            includePartyScaleUses = options.contains(PartyOptions.PartyIncludePartyScaleUses);
            setIncludeEntityAttributeGroups(options.contains(PartyOptions.DepartmentIncludeEntityAttributeGroups));
            setIncludeTagScopes(options.contains(PartyOptions.DepartmentIncludeTagScopes));
        }
        
        setIncludeEntityInstance(true);
    }
    
    public DepartmentTransfer getDepartmentTransfer(PartyDepartment partyDepartment) {
        return getDepartmentTransfer(partyDepartment.getParty());
    }
    
    public DepartmentTransfer getDepartmentTransfer(Party party) {
        DepartmentTransfer departmentTransfer = get(party);
        
        if(departmentTransfer == null) {
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
            PartyDepartment partyDepartment = partyControl.getPartyDepartment(party);
            Party divisionParty = partyDepartment.getDivisionParty();
            DivisionTransfer division = partyControl.getDivisionTransfer(userVisit, divisionParty);
            String departmentName = partyDepartment.getPartyDepartmentName();
            String isDefault = partyDepartment.getIsDefault().toString();
            String sortOrder = partyDepartment.getSortOrder().toString();
            
            departmentTransfer = new DepartmentTransfer(partyName, partyTypeTransfer, preferredLanguageTransfer, preferredCurrencyTransfer, preferredTimeZoneTransfer, preferredDateTimeFormatTransfer,
                    personTransfer, partyGroupTransfer, division, departmentName, isDefault, sortOrder);
            put(party, departmentTransfer);
            
            if(includePartyContactMechanisms) {
                departmentTransfer.setPartyContactMechanisms(new ListWrapper<>(contactControl.getPartyContactMechanismTransfersByParty(userVisit, party)));
            }

            if(includePartyDocuments) {
                departmentTransfer.setPartyDocuments(new ListWrapper<>(documentControl.getPartyDocumentTransfersByParty(userVisit, party)));
            }

            if(includePartyPrinterGroupUses) {
                departmentTransfer.setPartyPrinterGroupUses(new ListWrapper<>(printerControl.getPartyPrinterGroupUseTransfersByParty(userVisit, party)));
            }

            if(includePartyScaleUses) {
                departmentTransfer.setPartyScaleUses(new ListWrapper<>(scaleControl.getPartyScaleUseTransfersByParty(userVisit, party)));
            }
        }
        
        return departmentTransfer;
    }
    
}
