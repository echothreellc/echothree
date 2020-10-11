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
package com.echothree.model.control.employee.server.transfer;

import com.echothree.model.control.accounting.common.transfer.CurrencyTransfer;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.contact.server.control.ContactControl;
import com.echothree.model.control.contactlist.server.ContactListControl;
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.control.document.server.control.DocumentControl;
import com.echothree.model.control.employee.common.EmployeeOptions;
import com.echothree.model.control.employee.common.transfer.EmployeeTransfer;
import com.echothree.model.control.employee.common.transfer.EmployeeTypeTransfer;
import com.echothree.model.control.employee.server.control.EmployeeControl;
import com.echothree.model.control.party.common.PartyOptions;
import com.echothree.model.control.party.common.transfer.DateTimeFormatTransfer;
import com.echothree.model.control.party.common.transfer.LanguageTransfer;
import com.echothree.model.control.party.common.transfer.PartyRelationshipTransfer;
import com.echothree.model.control.party.common.transfer.PartyTypeTransfer;
import com.echothree.model.control.party.common.transfer.PersonTransfer;
import com.echothree.model.control.party.common.transfer.ProfileTransfer;
import com.echothree.model.control.party.common.transfer.TimeZoneTransfer;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.printer.server.control.PrinterControl;
import com.echothree.model.control.scale.server.control.ScaleControl;
import com.echothree.model.control.training.server.TrainingControl;
import com.echothree.model.control.user.server.UserControl;
import com.echothree.model.control.employee.common.workflow.EmployeeAvailabilityConstants;
import com.echothree.model.control.employee.common.workflow.EmployeeStatusConstants;
import com.echothree.model.control.workflow.common.transfer.WorkflowEntityStatusTransfer;
import com.echothree.model.control.workflow.server.WorkflowControl;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.employee.server.entity.PartyEmployee;
import com.echothree.model.data.party.server.entity.DateTimeFormat;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.party.server.entity.PartyDetail;
import com.echothree.model.data.party.server.entity.Person;
import com.echothree.model.data.party.server.entity.Profile;
import com.echothree.model.data.party.server.entity.TimeZone;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.transfer.ListWrapper;
import com.echothree.util.server.persistence.Session;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class EmployeeTransferCache
        extends BaseEmployeeTransferCache<Party, EmployeeTransfer> {

    AccountingControl accountingControl = (AccountingControl)Session.getModelController(AccountingControl.class);
    ContactControl contactControl = (ContactControl)Session.getModelController(ContactControl.class);
    ContactListControl contactListControl = (ContactListControl)Session.getModelController(ContactListControl.class);
    CoreControl coreControl = (CoreControl)Session.getModelController(CoreControl.class);
    DocumentControl documentControl = (DocumentControl)Session.getModelController(DocumentControl.class);
    PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);
    PrinterControl printerControl = (PrinterControl)Session.getModelController(PrinterControl.class);
    ScaleControl scaleControl = (ScaleControl)Session.getModelController(ScaleControl.class);
    TrainingControl trainingControl = (TrainingControl)Session.getModelController(TrainingControl.class);
    UserControl userControl = (UserControl)Session.getModelController(UserControl.class);
    WorkflowControl workflowControl = (WorkflowControl)Session.getModelController(WorkflowControl.class);
    boolean includeUserLogin;
    boolean includeRecoveryAnswer;
    boolean includePartyContactMechanisms;
    boolean includePartyContactLists;
    boolean includePartyDocuments;
    boolean includePartyPrinterGroupUses;
    boolean includePartyScaleUses;
    boolean includePartyEntityTypes;
    boolean includePartyApplicationEditorUses;
    boolean includePartyRelationships;
    boolean includePartyRelationshipsByFromParty;
    boolean includePartyRelationshipsByToParty;
    boolean includeEmployments;
    boolean includeLeaves;
    boolean includePartyResponsibilities;
    boolean includePartyTrainingClasses;
    boolean includePartySkills;

    /** Creates a new instance of EmployeeTransferCache */
    public EmployeeTransferCache(UserVisit userVisit, EmployeeControl employeeControl) {
        super(userVisit, employeeControl);

        Set<String> options = session.getOptions();
        if(options != null) {
            setIncludeKey(options.contains(PartyOptions.PartyIncludeKey) || options.contains(EmployeeOptions.EmployeeIncludeKey));
            setIncludeGuid(options.contains(PartyOptions.PartyIncludeGuid) || options.contains(EmployeeOptions.EmployeeIncludeGuid));
            includeUserLogin = options.contains(PartyOptions.PartyIncludeUserLogin);
            includeRecoveryAnswer = options.contains(PartyOptions.PartyIncludeRecoveryAnswer);
            includePartyContactMechanisms = options.contains(PartyOptions.PartyIncludePartyContactMechanisms);
            includePartyContactLists = options.contains(PartyOptions.PartyIncludePartyContactLists);
            includePartyDocuments = options.contains(PartyOptions.PartyIncludePartyDocuments);
            includePartyPrinterGroupUses = options.contains(PartyOptions.PartyIncludePartyPrinterGroupUses);
            includePartyScaleUses = options.contains(PartyOptions.PartyIncludePartyScaleUses);
            includePartyEntityTypes = options.contains(PartyOptions.PartyIncludePartyEntityTypes);
            includePartyApplicationEditorUses = options.contains(PartyOptions.PartyIncludePartyApplicationEditorUses);
            includePartyRelationships = options.contains(EmployeeOptions.EmployeeIncludePartyRelationships);
            includePartyRelationshipsByFromParty = options.contains(EmployeeOptions.EmployeeIncludePartyRelationshipsByFromParty);
            includePartyRelationshipsByToParty = options.contains(EmployeeOptions.EmployeeIncludePartyRelationshipsByToParty);
            includeEmployments = options.contains(EmployeeOptions.EmployeeIncludeEmployments);
            includeLeaves = options.contains(EmployeeOptions.EmployeeIncludeLeaves);
            includePartyResponsibilities = options.contains(EmployeeOptions.EmployeeIncludePartyResponsibilities);
            includePartyTrainingClasses = options.contains(EmployeeOptions.EmployeeIncludePartyTrainingClasses);
            includePartySkills = options.contains(EmployeeOptions.EmployeeIncludePartySkills);
            setIncludeEntityAttributeGroups(options.contains(EmployeeOptions.EmployeeIncludeEntityAttributeGroups));
            setIncludeTagScopes(options.contains(EmployeeOptions.EmployeeIncludeTagScopes));
        }
        
        setIncludeEntityInstance(true);
    }

    public EmployeeTransfer getEmployeeTransfer(Party party) {
        EmployeeTransfer employeeTransfer = get(party);

        if(employeeTransfer == null) {
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
            PartyEmployee partyEmployee = employeeControl.getPartyEmployee(party);
            Person person = partyControl.getPerson(party);
            PersonTransfer personTransfer = person == null ? null : partyControl.getPersonTransfer(userVisit, person);
            Profile profile = partyControl.getProfile(party);
            ProfileTransfer profileTransfer = profile == null ? null : partyControl.getProfileTransfer(userVisit, profile);

            String employeeName = partyEmployee.getPartyEmployeeName();
            EmployeeTypeTransfer employeeType = employeeControl.getEmployeeTypeTransfer(userVisit, partyEmployee.getEmployeeType());

            EntityInstance entityInstance = coreControl.getEntityInstanceByBasePK(party.getPrimaryKey());
            WorkflowEntityStatusTransfer employeeStatusTransfer = workflowControl.getWorkflowEntityStatusTransferByEntityInstanceUsingNames(userVisit,
                    EmployeeStatusConstants.Workflow_EMPLOYEE_STATUS, entityInstance);
            WorkflowEntityStatusTransfer employeeAvailabilityTransfer = workflowControl.getWorkflowEntityStatusTransferByEntityInstanceUsingNames(userVisit,
                    EmployeeAvailabilityConstants.Workflow_EMPLOYEE_AVAILABILITY, entityInstance);

            employeeTransfer = new EmployeeTransfer(partyName, partyTypeTransfer, preferredLanguageTransfer, preferredCurrencyTransfer, preferredTimeZoneTransfer, preferredDateTimeFormatTransfer,
                    personTransfer, profileTransfer, employeeName, employeeType, employeeStatusTransfer, employeeAvailabilityTransfer);
            put(party, employeeTransfer, entityInstance);

            if(includeUserLogin) {
                employeeTransfer.setUserLogin(userControl.getUserLoginTransfer(userVisit, party));
            }

            if(includeRecoveryAnswer) {
                employeeTransfer.setRecoveryAnswer(userControl.getRecoveryAnswerTransfer(userVisit, party));
            }

            if(includePartyContactMechanisms) {
                employeeTransfer.setPartyContactMechanisms(new ListWrapper<>(contactControl.getPartyContactMechanismTransfersByParty(userVisit, party)));
            }

            if(includePartyContactLists) {
                employeeTransfer.setPartyContactLists(new ListWrapper<>(contactListControl.getPartyContactListTransfersByParty(userVisit, party)));
            }

            if(includePartyDocuments) {
                employeeTransfer.setPartyDocuments(new ListWrapper<>(documentControl.getPartyDocumentTransfersByParty(userVisit, party)));
            }

            if(includePartyPrinterGroupUses) {
                employeeTransfer.setPartyPrinterGroupUses(new ListWrapper<>(printerControl.getPartyPrinterGroupUseTransfersByParty(userVisit, party)));
            }

            if(includePartyScaleUses) {
                employeeTransfer.setPartyScaleUses(new ListWrapper<>(scaleControl.getPartyScaleUseTransfersByParty(userVisit, party)));
            }
            
            if(includePartyEntityTypes) {
                employeeTransfer.setPartyEntityTypes(new ListWrapper<>(coreControl.getPartyEntityTypeTransfersByParty(userVisit, party)));
            }
            
            if(includePartyApplicationEditorUses) {
                employeeTransfer.setPartyApplicationEditorUses(new ListWrapper<>(coreControl.getPartyApplicationEditorUseTransfersByParty(userVisit, party)));
            }

            if(includePartyRelationships || includePartyRelationshipsByFromParty || includePartyRelationshipsByToParty) {
                Set<PartyRelationshipTransfer> partyRelationships = new HashSet<>();

                if(includePartyRelationships || includePartyRelationshipsByFromParty) {
                    partyRelationships.addAll(partyControl.getPartyRelationshipTransfersByFromParty(userVisit, party));
                }

                if(includePartyRelationships || includePartyRelationshipsByToParty) {
                    partyRelationships.addAll(partyControl.getPartyRelationshipTransfersByToParty(userVisit, party));
                }

                employeeTransfer.setPartyRelationships(new ListWrapper<>(new ArrayList<>(partyRelationships)));
            }

            if(includeEmployments) {
                employeeTransfer.setEmployments(new ListWrapper<>(employeeControl.getEmploymentTransfersByParty(userVisit, party)));
            }

            if(includeLeaves) {
                employeeTransfer.setLeaves(new ListWrapper<>(employeeControl.getLeaveTransfersByParty(userVisit, party)));
            }

            if(includePartyResponsibilities) {
                employeeTransfer.setPartyResponsibilities(new ListWrapper<>(employeeControl.getPartyResponsibilityTransfersByParty(userVisit, party)));
            }

            if(includePartyTrainingClasses) {
                employeeTransfer.setPartyTrainingClasses(new ListWrapper<>(trainingControl.getPartyTrainingClassTransfersByParty(userVisit, party)));
            }

            if(includePartySkills) {
                employeeTransfer.setPartySkills(new ListWrapper<>(employeeControl.getPartySkillTransfersByParty(userVisit, party)));
            }
        }

        return employeeTransfer;
    }

}
