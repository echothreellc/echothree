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

package com.echothree.model.control.warehouse.server.transfer;

import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.contact.server.control.ContactControl;
import com.echothree.model.control.document.server.control.DocumentControl;
import com.echothree.model.control.party.common.PartyOptions;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.printer.server.control.PrinterControl;
import com.echothree.model.control.scale.server.control.ScaleControl;
import com.echothree.model.control.warehouse.common.WarehouseOptions;
import com.echothree.model.control.warehouse.common.transfer.WarehouseTransfer;
import com.echothree.model.control.warehouse.server.control.WarehouseControl;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.warehouse.server.entity.Warehouse;
import com.echothree.util.common.transfer.ListWrapper;
import com.echothree.util.server.persistence.Session;

public class WarehouseTransferCache
        extends BaseWarehouseTransferCache<Party, WarehouseTransfer> {
    
    AccountingControl accountingControl = Session.getModelController(AccountingControl.class);
    ContactControl contactControl = Session.getModelController(ContactControl.class);
    DocumentControl documentControl = Session.getModelController(DocumentControl.class);
    PartyControl partyControl = Session.getModelController(PartyControl.class);
    PrinterControl printerControl = Session.getModelController(PrinterControl.class);
    ScaleControl scaleControl = Session.getModelController(ScaleControl.class);

    boolean includeLocationsCount;
    boolean includeLocations;
    boolean includePartyAliases;
    boolean includePartyContactMechanisms;
    boolean includePartyDocuments;
    boolean includePartyPrinterGroupUses;
    boolean includePartyScaleUses;
    
    /** Creates a new instance of WarehouseTransferCache */
    public WarehouseTransferCache(WarehouseControl warehouseControl) {
        super(warehouseControl);
        
        var options = session.getOptions();
        if(options != null) {
            includeLocationsCount = options.contains(WarehouseOptions.WarehouseIncludeLocationsCount);
            includeLocations = options.contains(WarehouseOptions.WarehouseIncludeLocations);
            setIncludeUuid(options.contains(PartyOptions.PartyIncludeUuid) || options.contains(WarehouseOptions.WarehouseIncludeUuid));
            includePartyAliases = options.contains(PartyOptions.PartyIncludePartyAliases);
            includePartyContactMechanisms = options.contains(PartyOptions.PartyIncludePartyContactMechanisms);
            includePartyDocuments = options.contains(PartyOptions.PartyIncludePartyDocuments);
            includePartyPrinterGroupUses = options.contains(PartyOptions.PartyIncludePartyPrinterGroupUses);
            includePartyScaleUses = options.contains(PartyOptions.PartyIncludePartyScaleUses);
            setIncludeEntityAttributeGroups(options.contains(WarehouseOptions.WarehouseIncludeEntityAttributeGroups));
            setIncludeTagScopes(options.contains(WarehouseOptions.WarehouseIncludeTagScopes));
        }
        
        setIncludeEntityInstance(true);
    }
    
    public WarehouseTransfer getWarehouseTransfer(Warehouse warehouse) {
        return getWarehouseTransfer(warehouse.getParty());
    }
    
    public WarehouseTransfer getWarehouseTransfer(Party party) {
        var warehouseTransfer = get(party);
        
        if(warehouseTransfer == null) {
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
            var warehouse = warehouseControl.getWarehouse(party);
            var warehouseName = warehouse.getWarehouseName();
            var warehouseTypeTransfer = warehouseControl.getWarehouseTypeTransfer(userVisit, warehouse.getWarehouseType());
            var isDefault = warehouse.getIsDefault();
            var sortOrder = warehouse.getSortOrder();
            
            warehouseTransfer = new WarehouseTransfer(partyName, partyTypeTransfer, preferredLanguageTransfer, preferredCurrencyTransfer,
                    preferredTimeZoneTransfer, preferredDateTimeFormatTransfer, personTransfer, partyGroupTransfer, warehouseName,
                    warehouseTypeTransfer, isDefault, sortOrder);
            put(userVisit, party, warehouseTransfer);

            if(includeLocationsCount) {
                warehouseTransfer.setLocationsCount(warehouseControl.countLocationsByWarehouseParty(party));
            }

            if(includeLocations) {
                warehouseTransfer.setLocations(new ListWrapper<>(warehouseControl.getLocationTransfersByWarehouseParty(userVisit, party)));
            }

            if(includePartyAliases) {
                warehouseTransfer.setPartyAliases(new ListWrapper<>(partyControl.getPartyAliasTransfersByParty(userVisit, party)));
            }

            if(includePartyContactMechanisms) {
                warehouseTransfer.setPartyContactMechanisms(new ListWrapper<>(contactControl.getPartyContactMechanismTransfersByParty(userVisit, party)));
            }

            if(includePartyDocuments) {
                warehouseTransfer.setPartyDocuments(new ListWrapper<>(documentControl.getPartyDocumentTransfersByParty(userVisit, party)));
            }

            if(includePartyPrinterGroupUses) {
                warehouseTransfer.setPartyPrinterGroupUses(new ListWrapper<>(printerControl.getPartyPrinterGroupUseTransfersByParty(userVisit, party)));
            }

            if(includePartyScaleUses) {
                warehouseTransfer.setPartyScaleUses(new ListWrapper<>(scaleControl.getPartyScaleUseTransfersByParty(userVisit, party)));
            }
        }
        
        return warehouseTransfer;
    }
    
}
