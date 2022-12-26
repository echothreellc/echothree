// --------------------------------------------------------------------------------
// Copyright 2002-2023 Echo Three, LLC
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
import com.echothree.model.control.contactlist.server.ContactListControl;
import com.echothree.model.control.document.server.control.DocumentControl;
import com.echothree.model.control.employee.server.control.EmployeeControl;
import com.echothree.model.control.party.common.PartyOptions;
import com.echothree.model.control.party.common.transfer.DateTimeFormatTransfer;
import com.echothree.model.control.party.common.transfer.LanguageTransfer;
import com.echothree.model.control.party.common.transfer.PartyGroupTransfer;
import com.echothree.model.control.party.common.transfer.PartyRelationshipTransfer;
import com.echothree.model.control.party.common.transfer.PartyTransfer;
import com.echothree.model.control.party.common.transfer.PartyTypeTransfer;
import com.echothree.model.control.party.common.transfer.PersonTransfer;
import com.echothree.model.control.party.common.transfer.ProfileTransfer;
import com.echothree.model.control.party.common.transfer.TimeZoneTransfer;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.printer.server.control.PrinterControl;
import com.echothree.model.control.scale.server.control.ScaleControl;
import com.echothree.model.control.training.server.control.TrainingControl;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.party.server.entity.DateTimeFormat;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.party.server.entity.PartyDetail;
import com.echothree.model.data.party.server.entity.PartyGroup;
import com.echothree.model.data.party.server.entity.Person;
import com.echothree.model.data.party.server.entity.Profile;
import com.echothree.model.data.party.server.entity.TimeZone;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.transfer.ListWrapper;
import com.echothree.util.server.persistence.Session;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class PartyTransferCache
        extends BasePartyTransferCache<Party, PartyTransfer> {
    
    AccountingControl accountingControl = Session.getModelController(AccountingControl.class);
    CarrierControl carrierControl = Session.getModelController(CarrierControl.class);
    ContactControl contactControl = Session.getModelController(ContactControl.class);
    ContactListControl contactListControl = Session.getModelController(ContactListControl.class);
    DocumentControl documentControl = Session.getModelController(DocumentControl.class);
    EmployeeControl employeeControl = Session.getModelController(EmployeeControl.class);
    PrinterControl printerControl = Session.getModelController(PrinterControl.class);
    ScaleControl scaleControl = Session.getModelController(ScaleControl.class);
    TrainingControl trainingControl = Session.getModelController(TrainingControl.class);
    UserControl userControl = Session.getModelController(UserControl.class);

    boolean includeUserLogin;
    boolean includeRecoveryAnswer;
    boolean includeDescription;
    boolean includePartyAliases;
    boolean includePartyContactMechanisms;
    boolean includePartyContactLists;
    boolean includePartyDocuments;
    boolean includePartyPrinterGroupUses;
    boolean includePartyScaleUses;
    boolean includePartyCarriers;
    boolean includePartyCarrierAccounts;
    boolean includePartyRelationships;
    boolean includePartyRelationshipsByFromParty;
    boolean includePartyRelationshipsByToParty;
    boolean includeEmployments;
    boolean includeLeaves;
    boolean includePartyResponsibilities;
    boolean includePartyTrainingClasses;
    boolean includePartySkills;
    
    /** Creates a new instance of PartyTransferCache */
    public PartyTransferCache(UserVisit userVisit, PartyControl partyControl) {
        super(userVisit, partyControl);
        
        var options = session.getOptions();
        if(options != null) {
            setIncludeKey(options.contains(PartyOptions.PartyIncludeKey));
            setIncludeGuid(options.contains(PartyOptions.PartyIncludeGuid));
            includeUserLogin = options.contains(PartyOptions.PartyIncludeUserLogin);
            includeRecoveryAnswer = options.contains(PartyOptions.PartyIncludeRecoveryAnswer);
            includeDescription = options.contains(PartyOptions.PartyIncludeDescription);
            includePartyAliases = options.contains(PartyOptions.PartyIncludePartyAliases);
            includePartyContactMechanisms = options.contains(PartyOptions.PartyIncludePartyContactMechanisms);
            includePartyContactLists = options.contains(PartyOptions.PartyIncludePartyContactLists);
            includePartyDocuments = options.contains(PartyOptions.PartyIncludePartyDocuments);
            includePartyPrinterGroupUses = options.contains(PartyOptions.PartyIncludePartyPrinterGroupUses);
            includePartyScaleUses = options.contains(PartyOptions.PartyIncludePartyScaleUses);
            includePartyCarriers = options.contains(PartyOptions.PartyIncludePartyCarriers);
            includePartyCarrierAccounts = options.contains(PartyOptions.PartyIncludePartyCarrierAccounts);
            includePartyRelationships = options.contains(PartyOptions.PartyIncludePartyRelationships);
            includePartyRelationshipsByFromParty = options.contains(PartyOptions.PartyIncludePartyRelationshipsByFromParty);
            includePartyRelationshipsByToParty = options.contains(PartyOptions.PartyIncludePartyRelationshipsByToParty);
            includeEmployments = options.contains(PartyOptions.PartyIncludeEmployments);
            includeLeaves = options.contains(PartyOptions.PartyIncludeLeaves);
            includePartyResponsibilities = options.contains(PartyOptions.PartyIncludePartyResponsibilities);
            includePartyTrainingClasses = options.contains(PartyOptions.PartyIncludePartyTrainingClasses);
            includePartySkills = options.contains(PartyOptions.PartyIncludePartySkills);
        }
        
        setIncludeEntityInstance(true);
    }
    
    public PartyTransfer getTransfer(Party party) {
        PartyTransfer partyTransfer = get(party);
        
        if(partyTransfer == null) {
            PartyDetail partyDetail = party.getLastDetail();
            String partyName = partyDetail.getPartyName();
            PartyTypeTransfer partyTypeTransfer = partyControl.getPartyTypeTransfer(userVisit, partyDetail.getPartyType());
            Language preferredLanguage = partyDetail.getPreferredLanguage();
            LanguageTransfer preferredLanguageTransfer = preferredLanguage == null? null: partyControl.getLanguageTransfer(userVisit, preferredLanguage);
            Currency preferredCurrency = partyDetail.getPreferredCurrency();
            CurrencyTransfer preferredCurrencyTransfer = preferredCurrency == null? null: accountingControl.getCurrencyTransfer(userVisit, preferredCurrency);
            TimeZone preferredTimeZone = partyDetail.getPreferredTimeZone();
            TimeZoneTransfer preferredTimeZoneTransfer = preferredTimeZone == null? null: partyControl.getTimeZoneTransfer(userVisit, preferredTimeZone);
            DateTimeFormat preferredDateTimeFormat = partyDetail.getPreferredDateTimeFormat();
            DateTimeFormatTransfer preferredDateTimeFormatTransfer = preferredDateTimeFormat == null? null: partyControl.getDateTimeFormatTransfer(userVisit, preferredDateTimeFormat);
            Person person = partyControl.getPerson(party);
            PersonTransfer personTransfer = person == null? null: partyControl.getPersonTransfer(userVisit, person);
            PartyGroup partyGroup = partyControl.getPartyGroup(party);
            PartyGroupTransfer partyGroupTransfer = partyGroup == null? null: partyControl.getPartyGroupTransfer(userVisit, partyGroup);
            Profile profile = partyControl.getProfile(party);
            ProfileTransfer profileTransfer = profile == null? null: partyControl.getProfileTransfer(userVisit, profile);
            
            partyTransfer = new PartyTransfer(partyName, partyTypeTransfer, preferredLanguageTransfer, preferredCurrencyTransfer, preferredTimeZoneTransfer, preferredDateTimeFormatTransfer,
                    personTransfer, partyGroupTransfer, profileTransfer);
            put(party, partyTransfer);
            
            if(includeUserLogin) {
                partyTransfer.setUserLogin(userControl.getUserLoginTransfer(userVisit, party));
            }
            
            if(includeRecoveryAnswer) {
                partyTransfer.setRecoveryAnswer(userControl.getRecoveryAnswerTransfer(userVisit, party));
            }
            
            if(includeDescription) {
                partyTransfer.setDescription(partyControl.getBestPartyDescription(party, getLanguage()));
            }
            
            if(includePartyContactMechanisms) {
                partyTransfer.setPartyContactMechanisms(new ListWrapper<>(contactControl.getPartyContactMechanismTransfersByParty(userVisit, party)));
            }
            
            if(includePartyContactLists) {
                partyTransfer.setPartyContactLists(new ListWrapper<>(contactListControl.getPartyContactListTransfersByParty(userVisit, party)));
            }

            if(includePartyAliases) {
                partyTransfer.setPartyAliases(new ListWrapper<>(partyControl.getPartyAliasTransfersByParty(userVisit, party)));
            }

            if(includePartyDocuments) {
                partyTransfer.setPartyDocuments(new ListWrapper<>(documentControl.getPartyDocumentTransfersByParty(userVisit, party)));
            }

            if(includePartyPrinterGroupUses) {
                partyTransfer.setPartyPrinterGroupUses(new ListWrapper<>(printerControl.getPartyPrinterGroupUseTransfersByParty(userVisit, party)));
            }

            if(includePartyScaleUses) {
                partyTransfer.setPartyScaleUses(new ListWrapper<>(scaleControl.getPartyScaleUseTransfersByParty(userVisit, party)));
            }

            if(includePartyCarriers) {
                partyTransfer.setPartyCarriers(new ListWrapper<>(carrierControl.getPartyCarrierTransfersByParty(userVisit, party)));
            }

            if(includePartyCarrierAccounts) {
                partyTransfer.setPartyCarrierAccounts(new ListWrapper<>(carrierControl.getPartyCarrierAccountTransfersByParty(userVisit, party)));
            }

            if(includePartyRelationships || includePartyRelationshipsByFromParty || includePartyRelationshipsByToParty) {
                Set<PartyRelationshipTransfer> partyRelationships = new HashSet<>();

                if(includePartyRelationships || includePartyRelationshipsByFromParty) {
                    partyRelationships.addAll(partyControl.getPartyRelationshipTransfersByFromParty(userVisit, party));
                }

                if(includePartyRelationships || includePartyRelationshipsByToParty) {
                    partyRelationships.addAll(partyControl.getPartyRelationshipTransfersByToParty(userVisit, party));
                }

                partyTransfer.setPartyRelationships(new ListWrapper<>(new ArrayList<>(partyRelationships)));
            }

            if(includeEmployments) {
                partyTransfer.setEmployments(new ListWrapper<>(employeeControl.getEmploymentTransfersByParty(userVisit, party)));
            }

            if(includeLeaves) {
                partyTransfer.setLeaves(new ListWrapper<>(employeeControl.getLeaveTransfersByParty(userVisit, party)));
            }

            if(includePartyResponsibilities) {
                partyTransfer.setPartyResponsibilities(new ListWrapper<>(employeeControl.getPartyResponsibilityTransfersByParty(userVisit, party)));
            }

            if(includePartyTrainingClasses) {
                partyTransfer.setPartyTrainingClasses(new ListWrapper<>(trainingControl.getPartyTrainingClassTransfersByParty(userVisit, party)));
            }

            if(includePartySkills) {
                partyTransfer.setPartySkills(new ListWrapper<>(employeeControl.getPartySkillTransfersByParty(userVisit, party)));
            }
        }
        
        return partyTransfer;
    }
    
}
