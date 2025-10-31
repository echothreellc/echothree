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

import com.echothree.control.user.party.common.edit.EmployeeEdit;
import com.echothree.control.user.party.common.edit.PartyEditFactory;
import com.echothree.control.user.party.common.form.EditEmployeeForm;
import com.echothree.control.user.party.common.result.EditEmployeeResult;
import com.echothree.control.user.party.common.result.PartyResultFactory;
import com.echothree.control.user.party.common.spec.EmployeeSpec;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.employee.server.control.EmployeeControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.security.server.control.SecurityControl;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.employee.server.entity.EmployeeType;
import com.echothree.model.data.employee.server.entity.PartyEmployee;
import com.echothree.model.data.party.server.entity.DateTimeFormat;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.party.server.entity.TimeZone;
import com.echothree.model.data.security.server.entity.PartySecurityRoleTemplate;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.apache.commons.codec.language.Soundex;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class EditEmployeeCommand
        extends BaseAbstractEditCommand<EmployeeSpec, EmployeeEdit, EditEmployeeResult, Party, Party> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
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
    public EditEmployeeCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditEmployeeResult getResult() {
        return PartyResultFactory.getEditEmployeeResult();
    }

    @Override
    public EmployeeEdit getEdit() {
        return PartyEditFactory.getEmployeeEdit();
    }

    @Override
    public Party getEntity(EditEmployeeResult result) {
        var employeeControl = Session.getModelController(EmployeeControl.class);
        PartyEmployee partyEmployee;
        var employeeName = spec.getEmployeeName();

        if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
            partyEmployee = employeeControl.getPartyEmployeeByName(employeeName);
        } else { // EditMode.UPDATE
            partyEmployee = employeeControl.getPartyEmployeeByNameForUpdate(employeeName);
        }

        if(partyEmployee != null) {
            result.setEmployee(employeeControl.getEmployeeTransfer(getUserVisit(), partyEmployee.getParty()));
        } else {
            addExecutionError(ExecutionErrors.UnknownEmployeeName.name(), employeeName);
        }

        return partyEmployee.getParty();
    }

    @Override
    public Party getLockEntity(Party party) {
        return party;
    }

    @Override
    public void fillInResult(EditEmployeeResult result, Party party) {
        var employeeControl = Session.getModelController(EmployeeControl.class);

        result.setEmployee(employeeControl.getEmployeeTransfer(getUserVisit(), party));
    }

    @Override
    public void doLock(EmployeeEdit edit, Party party) {
        var employeeControl = Session.getModelController(EmployeeControl.class);
        var partyControl = Session.getModelController(PartyControl.class);
        var securityControl = Session.getModelController(SecurityControl.class);
        var partyEmployee = employeeControl.getPartyEmployee(party);
        var partyDetail = party.getLastDetail();
        var preferredLanguage = partyDetail.getPreferredLanguage();
        var preferredCurrency = partyDetail.getPreferredCurrency();
        var preferredTimeZone = partyDetail.getPreferredTimeZone();
        var dateTimeFormat = partyDetail.getPreferredDateTimeFormat();
        var person = partyControl.getPerson(party);
        var personalTitle = person.getPersonalTitle();
        var nameSuffix = person.getNameSuffix();
        var partySecurityRoleTemplateUse = securityControl.getPartySecurityRoleTemplateUse(party);

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
    }

    EmployeeType employeeType;
    Language preferredLanguage;
    TimeZone preferredTimeZone;
    DateTimeFormat preferredDateTimeFormat;
    Currency preferredCurrency;
    PartySecurityRoleTemplate partySecurityRoleTemplate;

    @Override
    public void canUpdate(Party party) {
        var employeeControl = Session.getModelController(EmployeeControl.class);
        var partyControl = Session.getModelController(PartyControl.class);
        var securityControl = Session.getModelController(SecurityControl.class);
        var employeeTypeName = edit.getEmployeeTypeName();

        employeeType = employeeControl.getEmployeeTypeByName(employeeTypeName);

        if(employeeType != null) {
            var preferredLanguageIsoName = edit.getPreferredLanguageIsoName();

            preferredLanguage = preferredLanguageIsoName == null ? null : partyControl.getLanguageByIsoName(preferredLanguageIsoName);

            if(preferredLanguageIsoName == null || (preferredLanguage != null)) {
                var preferredJavaTimeZoneName = edit.getPreferredJavaTimeZoneName();

                preferredTimeZone = preferredJavaTimeZoneName == null ? null : partyControl.getTimeZoneByJavaName(preferredJavaTimeZoneName);

                if(preferredJavaTimeZoneName == null || (preferredTimeZone != null)) {
                    var preferredDateTimeFormatName = edit.getPreferredDateTimeFormatName();

                    preferredDateTimeFormat = preferredDateTimeFormatName == null ? null : partyControl.getDateTimeFormatByName(preferredDateTimeFormatName);

                    if(preferredDateTimeFormatName == null || (preferredDateTimeFormat != null)) {
                        var preferredCurrencyIsoName = edit.getPreferredCurrencyIsoName();

                        if(preferredCurrencyIsoName == null) {
                            preferredCurrency = null;
                        } else {
                            var accountingControl = Session.getModelController(AccountingControl.class);
                            preferredCurrency = accountingControl.getCurrencyByIsoName(preferredCurrencyIsoName);
                        }

                        if(preferredCurrencyIsoName == null || (preferredCurrency != null)) {
                            var partySecurityRoleTemplateName = edit.getPartySecurityRoleTemplateName();

                            partySecurityRoleTemplate = securityControl.getPartySecurityRoleTemplateByName(partySecurityRoleTemplateName);

                            if(partySecurityRoleTemplate == null) {
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
    }

    @Override
    public void doUpdate(Party party) {
        var employeeControl = Session.getModelController(EmployeeControl.class);
        var partyControl = Session.getModelController(PartyControl.class);
        var securityControl = Session.getModelController(SecurityControl.class);
        var soundex = new Soundex();
        var partyDetailValue = partyControl.getPartyDetailValueForUpdate(party);
        var partyEmployee = employeeControl.getPartyEmployeeForUpdate(party);
        var partyEmployeeValue = employeeControl.getPartyEmployeeValue(partyEmployee);
        var personValue = partyControl.getPersonValueForUpdate(party);
        var personalTitleId = edit.getPersonalTitleId();
        var personalTitle = personalTitleId == null ? null : partyControl.convertPersonalTitleIdToEntity(personalTitleId, EntityPermission.READ_ONLY);
        var firstName = edit.getFirstName();
        var firstNameSdx = soundex.encode(firstName);
        var middleName = edit.getMiddleName();
        var middleNameSdx = middleName == null ? null : soundex.encode(middleName);
        var lastName = edit.getLastName();
        var lastNameSdx = soundex.encode(lastName);
        var nameSuffixId = edit.getNameSuffixId();
        var nameSuffix = nameSuffixId == null ? null : partyControl.convertNameSuffixIdToEntity(nameSuffixId, EntityPermission.READ_ONLY);
        var partySecurityRoleTemplateUseValue = securityControl.getPartySecurityRoleTemplateUseValueForUpdate(party);

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

        var updatedBy = getPartyPK();
        employeeControl.updatePartyEmployeeFromValue(partyEmployeeValue, updatedBy);
        partyControl.updatePartyFromValue(partyDetailValue, updatedBy);
        partyControl.updatePersonFromValue(personValue, updatedBy);
        securityControl.updatePartySecurityRoleTemplateUseFromValue(partySecurityRoleTemplateUseValue, updatedBy);
    }

}
