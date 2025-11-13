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
import com.echothree.model.control.contact.server.control.ContactControl;
import com.echothree.model.control.document.server.control.DocumentControl;
import com.echothree.model.control.party.common.PartyOptions;
import com.echothree.model.control.party.common.transfer.DepartmentTransfer;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.printer.server.control.PrinterControl;
import com.echothree.model.control.scale.server.control.ScaleControl;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.party.server.entity.PartyDepartment;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.transfer.ListWrapper;
import com.echothree.util.server.persistence.Session;

public class DepartmentTransferCache
        extends BasePartyTransferCache<Party, DepartmentTransfer> {
    
    AccountingControl accountingControl = Session.getModelController(AccountingControl.class);
    ContactControl contactControl = Session.getModelController(ContactControl.class);
    DocumentControl documentControl = Session.getModelController(DocumentControl.class);
    PartyControl partyControl = Session.getModelController(PartyControl.class);
    PrinterControl printerControl = Session.getModelController(PrinterControl.class);
    ScaleControl scaleControl = Session.getModelController(ScaleControl.class);
    boolean includePartyContactMechanisms;
    boolean includePartyDocuments;
    boolean includePartyPrinterGroupUses;
    boolean includePartyScaleUses;
    
    /** Creates a new instance of DepartmentTransferCache */
    public DepartmentTransferCache() {
        super();
        
        var options = session.getOptions();
        if(options != null) {
            setIncludeUuid(options.contains(PartyOptions.PartyIncludeUuid) || options.contains(PartyOptions.DepartmentIncludeUuid));
            includePartyContactMechanisms = options.contains(PartyOptions.PartyIncludePartyContactMechanisms);
            includePartyDocuments = options.contains(PartyOptions.PartyIncludePartyDocuments);
            includePartyPrinterGroupUses = options.contains(PartyOptions.PartyIncludePartyPrinterGroupUses);
            includePartyScaleUses = options.contains(PartyOptions.PartyIncludePartyScaleUses);
            setIncludeEntityAttributeGroups(options.contains(PartyOptions.DepartmentIncludeEntityAttributeGroups));
            setIncludeTagScopes(options.contains(PartyOptions.DepartmentIncludeTagScopes));
        }
        
        setIncludeEntityInstance(true);
    }
    
    public DepartmentTransfer getTransfer(UserVisit userVisit, PartyDepartment partyDepartment) {
        return getTransfer(userVisit, partyDepartment.getParty());
    }

    @Override
    public DepartmentTransfer getTransfer(UserVisit userVisit, Party party) {
        var departmentTransfer = get(party);
        
        if(departmentTransfer == null) {
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
            var partyDepartment = partyControl.getPartyDepartment(party);
            var divisionParty = partyDepartment.getDivisionParty();
            var division = partyControl.getDivisionTransfer(userVisit, divisionParty);
            var departmentName = partyDepartment.getPartyDepartmentName();
            var isDefault = partyDepartment.getIsDefault().toString();
            var sortOrder = partyDepartment.getSortOrder().toString();
            
            departmentTransfer = new DepartmentTransfer(partyName, partyTypeTransfer, preferredLanguageTransfer, preferredCurrencyTransfer, preferredTimeZoneTransfer, preferredDateTimeFormatTransfer,
                    personTransfer, partyGroupTransfer, division, departmentName, isDefault, sortOrder);
            put(userVisit, party, departmentTransfer);
            
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
