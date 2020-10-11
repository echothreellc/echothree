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

package com.echothree.control.user.party.server.command;

import com.echothree.control.user.party.common.form.CreateEmployeeForm;
import com.echothree.control.user.party.common.result.CreateEmployeeResult;
import com.echothree.control.user.party.common.result.PartyResultFactory;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.contact.common.ContactMechanismPurposes;
import com.echothree.model.control.contact.server.logic.ContactEmailAddressLogic;
import com.echothree.model.control.contactlist.server.logic.ContactListLogic;
import com.echothree.model.control.employee.common.workflow.EmployeeAvailabilityConstants;
import com.echothree.model.control.employee.common.workflow.EmployeeStatusConstants;
import com.echothree.model.control.employee.server.EmployeeControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.party.server.logic.PasswordStringPolicyLogic;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.security.server.SecurityControl;
import com.echothree.model.control.sequence.common.SequenceTypes;
import com.echothree.model.control.sequence.server.logic.SequenceGeneratorLogic;
import com.echothree.model.control.user.common.UserConstants;
import com.echothree.model.control.user.server.UserControl;
import com.echothree.model.control.workflow.server.WorkflowControl;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.employee.server.entity.EmployeeType;
import com.echothree.model.data.employee.server.entity.PartyEmployee;
import com.echothree.model.data.party.server.entity.DateTimeFormat;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.NameSuffix;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.party.server.entity.PartyType;
import com.echothree.model.data.party.server.entity.PartyTypePasswordStringPolicy;
import com.echothree.model.data.party.server.entity.PersonalTitle;
import com.echothree.model.data.party.server.entity.TimeZone;
import com.echothree.model.data.security.server.entity.PartySecurityRoleTemplate;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.user.server.entity.UserLogin;
import com.echothree.model.data.user.server.entity.UserLoginPassword;
import com.echothree.model.data.user.server.entity.UserLoginPasswordType;
import com.echothree.model.data.user.server.entity.UserLoginStatus;
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
        var employeeControl = (EmployeeControl)Session.getModelController(EmployeeControl.class);
        UserControl userControl = getUserControl();
        CreateEmployeeResult result = PartyResultFactory.getCreateEmployeeResult();
        PartyEmployee partyEmployee = null;
        String username = form.getUsername();
        UserLogin userLogin = userControl.getUserLoginByUsername(username);
        
        if(userLogin == null) {
            String password1 = form.getPassword1();
            String password2 = form.getPassword2();
            
            if(password1.equals(password2)) {
                var partyControl = (PartyControl)Session.getModelController(PartyControl.class);
                PartyType partyType = partyControl.getPartyTypeByName(PartyTypes.EMPLOYEE.name());
                PartyTypePasswordStringPolicy partyTypePasswordStringPolicy = PasswordStringPolicyLogic.getInstance().checkStringPassword(session,
                        getUserVisit(), this, partyType, null, null, password1);
                
                if(!hasExecutionErrors()) {
                    String employeeTypeName = form.getEmployeeTypeName();
                    EmployeeType employeeType = employeeTypeName == null? employeeControl.getDefaultEmployeeType(): employeeControl.getEmployeeTypeByName(employeeTypeName);
                    
                    if(employeeType != null) {
                        String preferredLanguageIsoName = form.getPreferredLanguageIsoName();
                        Language preferredLanguage = preferredLanguageIsoName == null? null: partyControl.getLanguageByIsoName(preferredLanguageIsoName);
                        
                        if(preferredLanguageIsoName == null || (preferredLanguage != null)) {
                            String preferredJavaTimeZoneName = form.getPreferredJavaTimeZoneName();
                            TimeZone preferredTimeZone = preferredJavaTimeZoneName == null? null: partyControl.getTimeZoneByJavaName(preferredJavaTimeZoneName);
                            
                            if(preferredJavaTimeZoneName == null || (preferredTimeZone != null)) {
                                String preferredDateTimeFormatName = form.getPreferredDateTimeFormatName();
                                DateTimeFormat preferredDateTimeFormat = preferredDateTimeFormatName == null? null: partyControl.getDateTimeFormatByName(preferredDateTimeFormatName);
                                
                                if(preferredDateTimeFormatName == null || (preferredDateTimeFormat != null)) {
                                    String preferredCurrencyIsoName = form.getPreferredCurrencyIsoName();
                                    Currency preferredCurrency;
                                    
                                    if(preferredCurrencyIsoName == null)
                                        preferredCurrency = null;
                                    else {
                                        var accountingControl = (AccountingControl)Session.getModelController(AccountingControl.class);
                                        preferredCurrency = accountingControl.getCurrencyByIsoName(preferredCurrencyIsoName);
                                    }
                                    
                                    if(preferredCurrencyIsoName == null || (preferredCurrency != null)) {
                                        var securityControl = (SecurityControl)Session.getModelController(SecurityControl.class);
                                        String partySecurityRoleTemplateName = form.getPartySecurityRoleTemplateName();
                                        PartySecurityRoleTemplate partySecurityRoleTemplate = securityControl.getPartySecurityRoleTemplateByName(partySecurityRoleTemplateName);
                                        
                                        if(partySecurityRoleTemplate != null) {
                                            var coreControl = getCoreControl();
                                            var workflowControl = (WorkflowControl)Session.getModelController(WorkflowControl.class);
                                            Soundex soundex = new Soundex();
                                            BasePK createdBy = getPartyPK();
                                            String personalTitleId = form.getPersonalTitleId();
                                            PersonalTitle personalTitle = personalTitleId == null? null: partyControl.convertPersonalTitleIdToEntity(personalTitleId, EntityPermission.READ_ONLY);
                                            String firstName = form.getFirstName();
                                            String firstNameSdx = soundex.encode(firstName);
                                            String middleName = form.getMiddleName();
                                            String middleNameSdx = middleName == null? null: soundex.encode(middleName);
                                            String lastName = form.getLastName();
                                            String lastNameSdx = soundex.encode(lastName);
                                            String nameSuffixId = form.getNameSuffixId();
                                            NameSuffix nameSuffix = nameSuffixId == null? null: partyControl.convertNameSuffixIdToEntity(nameSuffixId, EntityPermission.READ_ONLY);
                                            String emailAddress = form.getEmailAddress();
                                            Boolean allowSolicitation = Boolean.valueOf(form.getAllowSolicitation());
                                            String partyEmployeeName = SequenceGeneratorLogic.getInstance().getNextSequenceValue(null, SequenceTypes.EMPLOYEE.name());
                                            
                                            Party party = partyControl.createParty(null, partyType, preferredLanguage, preferredCurrency, preferredTimeZone, preferredDateTimeFormat, createdBy);
                                            partyControl.createPerson(party, personalTitle, firstName, firstNameSdx, middleName, middleNameSdx, lastName, lastNameSdx, nameSuffix, createdBy);
                                            partyEmployee = employeeControl.createPartyEmployee(party, partyEmployeeName, employeeType, createdBy);
                                            userControl.createUserLogin(party, username, createdBy);
                                            
                                            ContactEmailAddressLogic.getInstance().createContactEmailAddress(party,
                                                    emailAddress, allowSolicitation, null,
                                                    ContactMechanismPurposes.PRIMARY_EMAIL.name(), createdBy);
                                            
                                            UserLoginPasswordType userLoginPasswordType = userControl.getUserLoginPasswordTypeByName(UserConstants.UserLoginPasswordType_STRING);
                                            UserLoginPassword userLoginPassword = userControl.createUserLoginPassword(party, userLoginPasswordType, createdBy);
                                            userControl.createUserLoginPasswordString(userLoginPassword, password1, session.START_TIME_LONG, Boolean.FALSE, createdBy);

                                            if(partyTypePasswordStringPolicy != null && partyTypePasswordStringPolicy.getLastDetail().getForceChangeAfterCreate()) {
                                                UserLoginStatus userLoginStatus = userControl.getUserLoginStatusForUpdate(party);

                                                userLoginStatus.setForceChange(Boolean.TRUE);
                                            }

                                            securityControl.createPartySecurityRoleTemplateUse(party, partySecurityRoleTemplate, createdBy);
                                            
                                            EntityInstance entityInstance = coreControl.getEntityInstanceByBasePK(party.getPrimaryKey());
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
            
            Party party = userLogin.getParty();
            partyEmployee = employeeControl.getPartyEmployee(party);
        }
        
        if(partyEmployee != null) {
            Party party = partyEmployee.getParty();
            
            result.setEntityRef(party.getPrimaryKey().getEntityRef());
            result.setEmployeeName(partyEmployee.getPartyEmployeeName());
            result.setPartyName(party.getLastDetail().getPartyName());
        }
        
        return result;
    }
    
}
