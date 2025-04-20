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

package com.echothree.control.user.party.server.command;

import com.echothree.control.user.party.common.form.CreateEmployeeForm;
import com.echothree.control.user.party.common.result.PartyResultFactory;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.contact.common.ContactMechanismPurposes;
import com.echothree.model.control.contact.server.logic.ContactEmailAddressLogic;
import com.echothree.model.control.contactlist.server.logic.ContactListLogic;
import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.control.employee.common.workflow.EmployeeAvailabilityConstants;
import com.echothree.model.control.employee.common.workflow.EmployeeStatusConstants;
import com.echothree.model.control.employee.server.control.EmployeeControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.party.server.logic.PasswordStringPolicyLogic;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.security.server.control.SecurityControl;
import com.echothree.model.control.sequence.common.SequenceTypes;
import com.echothree.model.control.sequence.server.logic.SequenceGeneratorLogic;
import com.echothree.model.control.user.common.UserConstants;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.employee.server.entity.PartyEmployee;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.apache.commons.codec.language.Soundex;

public class CreateEmployeeCommand
        extends BaseSimpleCommand<CreateEmployeeForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.Employee.name(), SecurityRoles.Create.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("EmployeeTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("PersonalTitleId", FieldType.ID, false, null, null),
                new FieldDefinition("FirstName", FieldType.STRING, true, 1L, 20L),
                new FieldDefinition("MiddleName", FieldType.STRING, false, 1L, 20L),
                new FieldDefinition("LastName", FieldType.STRING, true, 1L, 20L),
                new FieldDefinition("NameSuffixId", FieldType.ID, false, null, null),
                new FieldDefinition("PreferredLanguageIsoName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("PreferredCurrencyIsoName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("PreferredJavaTimeZoneName", FieldType.TIME_ZONE_NAME, false, null, null),
                new FieldDefinition("PreferredDateTimeFormatName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EmailAddress", FieldType.EMAIL_ADDRESS, true, null, null),
                new FieldDefinition("AllowSolicitation", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("Username", FieldType.STRING, true, 1L, 80L),
                new FieldDefinition("Password1", FieldType.STRING, true, 1L, 40L),
                new FieldDefinition("Password2", FieldType.STRING, true, 1L, 40L),
                new FieldDefinition("PartySecurityRoleTemplateName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of CreateEmployeeCommand */
    public CreateEmployeeCommand(UserVisitPK userVisitPK, CreateEmployeeForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var employeeControl = Session.getModelController(EmployeeControl.class);
        var userControl = getUserControl();
        var result = PartyResultFactory.getCreateEmployeeResult();
        PartyEmployee partyEmployee = null;
        var username = form.getUsername();
        var userLogin = userControl.getUserLoginByUsername(username);
        
        if(userLogin == null) {
            var password1 = form.getPassword1();
            var password2 = form.getPassword2();
            
            if(password1.equals(password2)) {
                var partyControl = Session.getModelController(PartyControl.class);
                var partyType = partyControl.getPartyTypeByName(PartyTypes.EMPLOYEE.name());
                var partyTypePasswordStringPolicy = PasswordStringPolicyLogic.getInstance().checkStringPassword(session,
                        getUserVisit(), this, partyType, null, null, password1);
                
                if(!hasExecutionErrors()) {
                    var employeeTypeName = form.getEmployeeTypeName();
                    var employeeType = employeeTypeName == null? employeeControl.getDefaultEmployeeType(): employeeControl.getEmployeeTypeByName(employeeTypeName);
                    
                    if(employeeType != null) {
                        var preferredLanguageIsoName = form.getPreferredLanguageIsoName();
                        var preferredLanguage = preferredLanguageIsoName == null? null: partyControl.getLanguageByIsoName(preferredLanguageIsoName);
                        
                        if(preferredLanguageIsoName == null || (preferredLanguage != null)) {
                            var preferredJavaTimeZoneName = form.getPreferredJavaTimeZoneName();
                            var preferredTimeZone = preferredJavaTimeZoneName == null? null: partyControl.getTimeZoneByJavaName(preferredJavaTimeZoneName);
                            
                            if(preferredJavaTimeZoneName == null || (preferredTimeZone != null)) {
                                var preferredDateTimeFormatName = form.getPreferredDateTimeFormatName();
                                var preferredDateTimeFormat = preferredDateTimeFormatName == null? null: partyControl.getDateTimeFormatByName(preferredDateTimeFormatName);
                                
                                if(preferredDateTimeFormatName == null || (preferredDateTimeFormat != null)) {
                                    var preferredCurrencyIsoName = form.getPreferredCurrencyIsoName();
                                    Currency preferredCurrency;
                                    
                                    if(preferredCurrencyIsoName == null)
                                        preferredCurrency = null;
                                    else {
                                        var accountingControl = Session.getModelController(AccountingControl.class);
                                        preferredCurrency = accountingControl.getCurrencyByIsoName(preferredCurrencyIsoName);
                                    }
                                    
                                    if(preferredCurrencyIsoName == null || (preferredCurrency != null)) {
                                        var securityControl = Session.getModelController(SecurityControl.class);
                                        var partySecurityRoleTemplateName = form.getPartySecurityRoleTemplateName();
                                        var partySecurityRoleTemplate = securityControl.getPartySecurityRoleTemplateByName(partySecurityRoleTemplateName);
                                        
                                        if(partySecurityRoleTemplate != null) {
                                            var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
                                            var workflowControl = Session.getModelController(WorkflowControl.class);
                                            var soundex = new Soundex();
                                            BasePK createdBy = getPartyPK();
                                            var personalTitleId = form.getPersonalTitleId();
                                            var personalTitle = personalTitleId == null? null: partyControl.convertPersonalTitleIdToEntity(personalTitleId, EntityPermission.READ_ONLY);
                                            var firstName = form.getFirstName();
                                            var firstNameSdx = soundex.encode(firstName);
                                            var middleName = form.getMiddleName();
                                            var middleNameSdx = middleName == null? null: soundex.encode(middleName);
                                            var lastName = form.getLastName();
                                            var lastNameSdx = soundex.encode(lastName);
                                            var nameSuffixId = form.getNameSuffixId();
                                            var nameSuffix = nameSuffixId == null? null: partyControl.convertNameSuffixIdToEntity(nameSuffixId, EntityPermission.READ_ONLY);
                                            var emailAddress = form.getEmailAddress();
                                            var allowSolicitation = Boolean.valueOf(form.getAllowSolicitation());
                                            var partyEmployeeName = SequenceGeneratorLogic.getInstance().getNextSequenceValue(null, SequenceTypes.EMPLOYEE.name());

                                            var party = partyControl.createParty(null, partyType, preferredLanguage, preferredCurrency, preferredTimeZone, preferredDateTimeFormat, createdBy);
                                            partyControl.createPerson(party, personalTitle, firstName, firstNameSdx, middleName, middleNameSdx, lastName, lastNameSdx, nameSuffix, createdBy);
                                            partyEmployee = employeeControl.createPartyEmployee(party, partyEmployeeName, employeeType, createdBy);
                                            userControl.createUserLogin(party, username, createdBy);
                                            
                                            ContactEmailAddressLogic.getInstance().createContactEmailAddress(party,
                                                    emailAddress, allowSolicitation, null,
                                                    ContactMechanismPurposes.PRIMARY_EMAIL.name(), createdBy);

                                            var userLoginPasswordType = userControl.getUserLoginPasswordTypeByName(UserConstants.UserLoginPasswordType_STRING);
                                            var userLoginPassword = userControl.createUserLoginPassword(party, userLoginPasswordType, createdBy);
                                            userControl.createUserLoginPasswordString(userLoginPassword, password1, session.START_TIME_LONG, Boolean.FALSE, createdBy);

                                            if(partyTypePasswordStringPolicy != null && partyTypePasswordStringPolicy.getLastDetail().getForceChangeAfterCreate()) {
                                                var userLoginStatus = userControl.getUserLoginStatusForUpdate(party);

                                                userLoginStatus.setForceChange(Boolean.TRUE);
                                            }

                                            securityControl.createPartySecurityRoleTemplateUse(party, partySecurityRoleTemplate, createdBy);

                                            var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(party.getPrimaryKey());
                                            workflowControl.addEntityToWorkflowUsingNames(null, EmployeeStatusConstants.Workflow_EMPLOYEE_STATUS,
                                                    EmployeeStatusConstants.WorkflowEntrance_NEW_ACTIVE, entityInstance, null, null, createdBy);
                                            workflowControl.addEntityToWorkflowUsingNames(null, EmployeeAvailabilityConstants.Workflow_EMPLOYEE_AVAILABILITY,
                                                    EmployeeAvailabilityConstants.WorkflowEntrance_NEW_AVAILABLE, entityInstance, null, null, createdBy);
                                            
                                            ContactListLogic.getInstance().setupInitialContactLists(this, party, createdBy);
                                        } else {
                                            addExecutionError(ExecutionErrors.UnknownPartySecurityRoleTemplateName.name(), partySecurityRoleTemplateName);
                                        }
                                    } else {
                                        addExecutionError(ExecutionErrors.UnknownCurrencyIsoName.name(), preferredCurrencyIsoName);
                                    }
                                } else {
                                    addExecutionError(ExecutionErrors.UnknownDateTimeFormatName.name(), preferredDateTimeFormatName);
                                }
                            } else {
                                addExecutionError(ExecutionErrors.UnknownJavaTimeZoneName.name(), preferredJavaTimeZoneName);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), preferredLanguageIsoName);
                        }
                    } else {
                        if(employeeTypeName != null) {
                            addExecutionError(ExecutionErrors.UnknownEmployeeTypeName.name(), employeeTypeName);
                        } else {
                            addExecutionError(ExecutionErrors.UnknownDefaultEmployeeType.name());
                        }
                    }
                }
            } else {
                addExecutionError(ExecutionErrors.MismatchedPasswords.name());
            }
        } else {
            addExecutionError(ExecutionErrors.DuplicateUsername.name());

            var party = userLogin.getParty();
            partyEmployee = employeeControl.getPartyEmployee(party);
        }
        
        if(partyEmployee != null) {
            var party = partyEmployee.getParty();
            
            result.setEntityRef(party.getPrimaryKey().getEntityRef());
            result.setEmployeeName(partyEmployee.getPartyEmployeeName());
            result.setPartyName(party.getLastDetail().getPartyName());
        }
        
        return result;
    }
    
}
