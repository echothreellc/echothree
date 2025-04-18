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
package com.echothree.model.control.employee.server.transfer;

import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.contact.server.control.ContactControl;
import com.echothree.model.control.contactlist.server.control.ContactListControl;
import com.echothree.model.control.core.server.control.ApplicationControl;
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.control.document.server.control.DocumentControl;
import com.echothree.model.control.employee.common.EmployeeOptions;
import com.echothree.model.control.employee.common.transfer.EmployeeTransfer;
import com.echothree.model.control.employee.common.workflow.EmployeeAvailabilityConstants;
import com.echothree.model.control.employee.common.workflow.EmployeeStatusConstants;
import com.echothree.model.control.employee.server.control.EmployeeControl;
import com.echothree.model.control.party.common.PartyOptions;
import com.echothree.model.control.party.common.transfer.PartyRelationshipTransfer;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.printer.server.control.PrinterControl;
import com.echothree.model.control.scale.server.control.ScaleControl;
import com.echothree.model.control.training.server.control.TrainingControl;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.employee.server.entity.PartyEmployee;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.transfer.ListWrapper;
import com.echothree.util.server.persistence.Session;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class EmployeeTransferCache
        extends BaseEmployeeTransferCache<Party, EmployeeTransfer> {

    AccountingControl accountingControl = Session.getModelController(AccountingControl.class);
    ApplicationControl applicationControl = Session.getModelController(ApplicationControl.class);
    ContactControl contactControl = Session.getModelController(ContactControl.class);
    ContactListControl contactListControl = Session.getModelController(ContactListControl.class);
    CoreControl coreControl = Session.getModelController(CoreControl.class);
    DocumentControl documentControl = Session.getModelController(DocumentControl.class);
    PartyControl partyControl = Session.getModelController(PartyControl.class);
    PrinterControl printerControl = Session.getModelController(PrinterControl.class);
    ScaleControl scaleControl = Session.getModelController(ScaleControl.class);
    TrainingControl trainingControl = Session.getModelController(TrainingControl.class);
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

        var options = session.getOptions();
        if(options != null) {
            setIncludeUuid(options.contains(PartyOptions.PartyIncludeUuid) || options.contains(EmployeeOptions.EmployeeIncludeUuid));
            includeUserLogin = options.contains(PartyOptions.PartyIncludeUserLogin);
            includeRecoveryAnswer = options.contains(PartyOptions.PartyIncludeRecoveryAnswer);
            includePartyAliases = options.contains(PartyOptions.PartyIncludePartyAliases);
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

    public EmployeeTransfer getTransfer(PartyEmployee partyEmployee) {
        return getTransfer(partyEmployee.getParty());
    }

    public EmployeeTransfer getTransfer(Party party) {
        var employeeTransfer = get(party);

        if(employeeTransfer == null) {
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
            var partyEmployee = employeeControl.getPartyEmployee(party);
            var person = partyControl.getPerson(party);
            var personTransfer = person == null ? null : partyControl.getPersonTransfer(userVisit, person);
            var profile = partyControl.getProfile(party);
            var profileTransfer = profile == null ? null : partyControl.getProfileTransfer(userVisit, profile);

            var employeeName = partyEmployee.getPartyEmployeeName();
            var employeeType = employeeControl.getEmployeeTypeTransfer(userVisit, partyEmployee.getEmployeeType());

            var entityInstance = coreControl.getEntityInstanceByBasePK(party.getPrimaryKey());
            var employeeStatusTransfer = workflowControl.getWorkflowEntityStatusTransferByEntityInstanceUsingNames(userVisit,
                    EmployeeStatusConstants.Workflow_EMPLOYEE_STATUS, entityInstance);
            var employeeAvailabilityTransfer = workflowControl.getWorkflowEntityStatusTransferByEntityInstanceUsingNames(userVisit,
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

            if(includePartyAliases) {
                employeeTransfer.setPartyAliases(new ListWrapper<>(partyControl.getPartyAliasTransfersByParty(userVisit, party)));
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
                employeeTransfer.setPartyApplicationEditorUses(new ListWrapper<>(applicationControl.getPartyApplicationEditorUseTransfersByParty(userVisit, party)));
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
