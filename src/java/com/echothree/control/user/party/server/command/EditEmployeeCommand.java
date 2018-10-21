// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

import com.echothree.control.user.party.remote.edit.EmployeeEdit;
import com.echothree.control.user.party.remote.edit.PartyEditFactory;
import com.echothree.control.user.party.remote.form.EditEmployeeForm;
import com.echothree.control.user.party.remote.result.EditEmployeeResult;
import com.echothree.control.user.party.remote.result.PartyResultFactory;
import com.echothree.control.user.party.remote.spec.EmployeeSpec;
import com.echothree.model.control.accounting.server.AccountingControl;
import com.echothree.model.control.employee.server.EmployeeControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.security.server.SecurityControl;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.employee.server.entity.EmployeeType;
import com.echothree.model.data.employee.server.entity.PartyEmployee;
import com.echothree.model.data.employee.server.value.PartyEmployeeValue;
import com.echothree.model.data.party.remote.pk.PartyPK;
import com.echothree.model.data.party.server.entity.DateTimeFormat;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.NameSuffix;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.party.server.entity.PartyDetail;
import com.echothree.model.data.party.server.entity.Person;
import com.echothree.model.data.party.server.entity.PersonalTitle;
import com.echothree.model.data.party.server.entity.TimeZone;
import com.echothree.model.data.party.server.value.PartyDetailValue;
import com.echothree.model.data.party.server.value.PersonValue;
import com.echothree.model.data.security.server.entity.PartySecurityRoleTemplate;
import com.echothree.model.data.security.server.entity.PartySecurityRoleTemplateUse;
import com.echothree.model.data.security.server.value.PartySecurityRoleTemplateUseValue;
import com.echothree.model.data.user.remote.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.remote.command.BaseResult;
import com.echothree.util.remote.command.EditMode;
import com.echothree.util.server.control.BaseEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.apache.commons.codec.language.Soundex;

public class EditEmployeeCommand
        extends BaseEditCommand<EmployeeSpec, EmployeeEdit> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                    new SecurityRoleDefinition(SecurityRoleGroups.Employee.name(), SecurityRoles.Edit.name())
                    )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("EmployeeName", FieldType.ENTITY_NAME, true, null, null)
        ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("EmployeeTypeName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("PersonalTitleId", FieldType.ID, false, null, null),
            new FieldDefinition("FirstName", FieldType.STRING, true, 1L, 20L),
            new FieldDefinition("MiddleName", FieldType.STRING, false, 1L, 20L),
            new FieldDefinition("LastName", FieldType.STRING, true, 1L, 20L),
            new FieldDefinition("NameSuffixId", FieldType.ID, false, null, null),
            new FieldDefinition("PreferredLanguageIsoName", FieldType.ENTITY_NAME, false, null, null),
            new FieldDefinition("PreferredCurrencyIsoName", FieldType.ENTITY_NAME, false, null, null),
            new FieldDefinition("PreferredJavaTimeZoneName", FieldType.TIME_ZONE_NAME, false, null, null),
            new FieldDefinition("PreferredDateTimeFormatName", FieldType.ENTITY_NAME, false, null, null),
            new FieldDefinition("PartySecurityRoleTemplateName", FieldType.ENTITY_NAME, true, null, null)
        ));
    }
    
    /** Creates a new instance of EditEmployeeCommand */
    public EditEmployeeCommand(UserVisitPK userVisitPK, EditEmployeeForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        EmployeeControl employeeControl = (EmployeeControl)Session.getModelController(EmployeeControl.class);
        EditEmployeeResult result = PartyResultFactory.getEditEmployeeResult();
        String employeeName = spec.getEmployeeName();
        PartyEmployee partyEmployee = employeeControl.getPartyEmployeeByNameForUpdate(employeeName);
        
        if(partyEmployee != null) {
            PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);
            SecurityControl securityControl = (SecurityControl)Session.getModelController(SecurityControl.class);
            Party party = partyEmployee.getParty();
            
            if(editMode.equals(EditMode.LOCK)) {
                result.setEmployee(employeeControl.getEmployeeTransfer(getUserVisit(), party));
                
                if(lockEntity(party)) {
                    EmployeeEdit edit = PartyEditFactory.getEmployeeEdit();
                    PartyDetail partyDetail = party.getLastDetail();
                    Language preferredLanguage = partyDetail.getPreferredLanguage();
                    Currency preferredCurrency = partyDetail.getPreferredCurrency();
                    TimeZone preferredTimeZone = partyDetail.getPreferredTimeZone();
                    DateTimeFormat dateTimeFormat = partyDetail.getPreferredDateTimeFormat();
                    Person person = partyControl.getPerson(party);
                    PersonalTitle personalTitle = person.getPersonalTitle();
                    NameSuffix nameSuffix = person.getNameSuffix();
                    PartySecurityRoleTemplateUse partySecurityRoleTemplateUse = securityControl.getPartySecurityRoleTemplateUse(party);
                    
                    result.setEdit(edit);
                    edit.setEmployeeTypeName(partyEmployee.getEmployeeType().getLastDetail().getEmployeeTypeName());
                    edit.setPersonalTitleId(personalTitle == null? null: personalTitle.getPrimaryKey().getEntityId().toString());
                    edit.setFirstName(person.getFirstName());
                    edit.setMiddleName(person.getMiddleName());
                    edit.setLastName(person.getLastName());
                    edit.setNameSuffixId(nameSuffix == null? null: nameSuffix.getPrimaryKey().getEntityId().toString());
                    edit.setPreferredLanguageIsoName(preferredLanguage == null? null: preferredLanguage.getLanguageIsoName());
                    edit.setPreferredCurrencyIsoName(preferredCurrency == null? null: preferredCurrency.getCurrencyIsoName());
                    edit.setPreferredJavaTimeZoneName(preferredTimeZone == null? null: preferredTimeZone.getLastDetail().getJavaTimeZoneName());
                    edit.setPreferredDateTimeFormatName(dateTimeFormat == null? null: dateTimeFormat.getLastDetail().getDateTimeFormatName());
                    edit.setPartySecurityRoleTemplateName(partySecurityRoleTemplateUse.getPartySecurityRoleTemplate().getLastDetail().getPartySecurityRoleTemplateName());
                } else {
                    addExecutionError(ExecutionErrors.EntityLockFailed.name());
                }
                
                result.setEntityLock(getEntityLockTransfer(party));
            } else if(editMode.equals(EditMode.ABANDON)) {
                unlockEntity(party);
            } else if(editMode.equals(EditMode.UPDATE)) {
                PartyEmployeeValue partyEmployeeValue = employeeControl.getPartyEmployeeValue(partyEmployee);

                String employeeTypeName = edit.getEmployeeTypeName();
                EmployeeType employeeType = employeeControl.getEmployeeTypeByName(employeeTypeName);

                if(employeeType != null) {
                    String preferredLanguageIsoName = edit.getPreferredLanguageIsoName();
                    Language preferredLanguage = preferredLanguageIsoName == null ? null : partyControl.getLanguageByIsoName(preferredLanguageIsoName);

                    if(preferredLanguageIsoName == null || (preferredLanguage != null)) {
                        String preferredJavaTimeZoneName = edit.getPreferredJavaTimeZoneName();
                        TimeZone preferredTimeZone = preferredJavaTimeZoneName == null ? null : partyControl.getTimeZoneByJavaName(preferredJavaTimeZoneName);

                        if(preferredJavaTimeZoneName == null || (preferredTimeZone != null)) {
                            String preferredDateTimeFormatName = edit.getPreferredDateTimeFormatName();
                            DateTimeFormat preferredDateTimeFormat = preferredDateTimeFormatName == null ? null : partyControl.getDateTimeFormatByName(preferredDateTimeFormatName);

                            if(preferredDateTimeFormatName == null || (preferredDateTimeFormat != null)) {
                                String preferredCurrencyIsoName = edit.getPreferredCurrencyIsoName();
                                Currency preferredCurrency;

                                if(preferredCurrencyIsoName == null) {
                                    preferredCurrency = null;
                                } else {
                                    AccountingControl accountingControl = (AccountingControl)Session.getModelController(AccountingControl.class);
                                    preferredCurrency = accountingControl.getCurrencyByIsoName(preferredCurrencyIsoName);
                                }

                                if(preferredCurrencyIsoName == null || (preferredCurrency != null)) {
                                    String partySecurityRoleTemplateName = edit.getPartySecurityRoleTemplateName();
                                    PartySecurityRoleTemplate partySecurityRoleTemplate = securityControl.getPartySecurityRoleTemplateByName(partySecurityRoleTemplateName);

                                    if(partySecurityRoleTemplate != null) {
                                        if(lockEntityForUpdate(party)) {
                                            try {
                                                Soundex soundex = new Soundex();
                                                PartyDetailValue partyDetailValue = partyControl.getPartyDetailValueForUpdate(party);
                                                PersonValue personValue = partyControl.getPersonValueForUpdate(party);
                                                String personalTitleId = edit.getPersonalTitleId();
                                                PersonalTitle personalTitle = personalTitleId == null ? null : partyControl.convertPersonalTitleIdToEntity(personalTitleId, EntityPermission.READ_ONLY);
                                                String firstName = edit.getFirstName();
                                                String firstNameSdx = soundex.encode(firstName);
                                                String middleName = edit.getMiddleName();
                                                String middleNameSdx = middleName == null ? null : soundex.encode(middleName);
                                                String lastName = edit.getLastName();
                                                String lastNameSdx = soundex.encode(lastName);
                                                String nameSuffixId = edit.getNameSuffixId();
                                                NameSuffix nameSuffix = nameSuffixId == null ? null
                                                        : partyControl.convertNameSuffixIdToEntity(nameSuffixId, EntityPermission.READ_ONLY);
                                                PartySecurityRoleTemplateUseValue partySecurityRoleTemplateUseValue = securityControl.getPartySecurityRoleTemplateUseValueForUpdate(party);

                                                partyEmployeeValue.setEmployeeTypePK(employeeType.getPrimaryKey());

                                                partyDetailValue.setPreferredLanguagePK(preferredLanguage == null? null: preferredLanguage.getPrimaryKey());
                                                partyDetailValue.setPreferredTimeZonePK(preferredTimeZone == null? null: preferredTimeZone.getPrimaryKey());
                                                partyDetailValue.setPreferredDateTimeFormatPK(preferredDateTimeFormat == null? null: preferredDateTimeFormat.getPrimaryKey());
                                                partyDetailValue.setPreferredCurrencyPK(preferredCurrency == null? null: preferredCurrency.getPrimaryKey());
                                                
                                                personValue.setPersonalTitlePK(personalTitle == null? null: personalTitle.getPrimaryKey());
                                                personValue.setFirstName(firstName);
                                                personValue.setFirstNameSdx(firstNameSdx);
                                                personValue.setMiddleName(middleName);
                                                personValue.setMiddleNameSdx(middleNameSdx);
                                                personValue.setLastName(lastName);
                                                personValue.setLastNameSdx(lastNameSdx);
                                                personValue.setNameSuffixPK(nameSuffix == null? null: nameSuffix.getPrimaryKey());

                                                partySecurityRoleTemplateUseValue.setPartySecurityRoleTemplatePK(partySecurityRoleTemplate.getPrimaryKey());

                                                PartyPK updatedBy = getPartyPK();
                                                employeeControl.updatePartyEmployeeFromValue(partyEmployeeValue, updatedBy);
                                                partyControl.updatePartyFromValue(partyDetailValue, updatedBy);
                                                partyControl.updatePersonFromValue(personValue, updatedBy);
                                                securityControl.updatePartySecurityRoleTemplateUseFromValue(partySecurityRoleTemplateUseValue, updatedBy);
                                            } finally {
                                                unlockEntity(party);
                                            }
                                        } else {
                                            addExecutionError(ExecutionErrors.EntityLockStale.name());
                                        }
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
                    addExecutionError(ExecutionErrors.UnknownEmployeeTypeName.name(), employeeTypeName);
                }

                if(hasExecutionErrors()) {
                    result.setEmployee(employeeControl.getEmployeeTransfer(getUserVisit(), party));
                    result.setEntityLock(getEntityLockTransfer(party));
                }
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownEmployeeName.name(), employeeName);
        }
        
        return result;
    }
    
}
