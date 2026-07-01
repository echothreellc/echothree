// --------------------------------------------------------------------------------
// Copyright 2002-2026 Echo Three, LLC
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

package com.echothree.control.user.party.server;

import com.echothree.control.user.party.common.PartyRemote;
import com.echothree.control.user.party.common.form.*;
import com.echothree.control.user.party.common.result.*;
import com.echothree.control.user.party.server.command.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.VoidResult;
import javax.ejb.Stateless;
import javax.enterprise.inject.spi.CDI;

@Stateless
public class PartyBean
        extends PartyFormsImpl
        implements PartyRemote, PartyLocal {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    @Override
    public String ping() {
        return "PartyBean is alive!";
    }
    
    // -------------------------------------------------------------------------
    //   Party Types
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult<VoidResult> createPartyType(UserVisitPK userVisitPK, CreatePartyTypeForm form) {
        return CDI.current().select(CreatePartyTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetPartyTypeResult> getPartyType(UserVisitPK userVisitPK, GetPartyTypeForm form) {
        return CDI.current().select(GetPartyTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetPartyTypesResult> getPartyTypes(UserVisitPK userVisitPK, GetPartyTypesForm form) {
        return CDI.current().select(GetPartyTypesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetPartyTypeChoicesResult> getPartyTypeChoices(UserVisitPK userVisitPK, GetPartyTypeChoicesForm form) {
        return CDI.current().select(GetPartyTypeChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Party Type Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult<VoidResult> createPartyTypeDescription(UserVisitPK userVisitPK, CreatePartyTypeDescriptionForm form) {
        return CDI.current().select(CreatePartyTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Party Type Use Types
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult<VoidResult> createPartyTypeUseType(UserVisitPK userVisitPK, CreatePartyTypeUseTypeForm form) {
        return CDI.current().select(CreatePartyTypeUseTypeCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Party Type Use Type Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult<VoidResult> createPartyTypeUseTypeDescription(UserVisitPK userVisitPK, CreatePartyTypeUseTypeDescriptionForm form) {
        return CDI.current().select(CreatePartyTypeUseTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Party Type Uses
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult<VoidResult> createPartyTypeUse(UserVisitPK userVisitPK, CreatePartyTypeUseForm form) {
        return CDI.current().select(CreatePartyTypeUseCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Languages
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult<VoidResult> createLanguage(UserVisitPK userVisitPK, CreateLanguageForm form) {
        return CDI.current().select(CreateLanguageCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetLanguageChoicesResult> getLanguageChoices(UserVisitPK userVisitPK, GetLanguageChoicesForm form) {
        return CDI.current().select(GetLanguageChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetLanguagesResult> getLanguages(UserVisitPK userVisitPK, GetLanguagesForm form) {
        return CDI.current().select(GetLanguagesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetLanguageResult> getLanguage(UserVisitPK userVisitPK, GetLanguageForm form) {
        return CDI.current().select(GetLanguageCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetPreferredLanguageResult> getPreferredLanguage(UserVisitPK userVisitPK, GetPreferredLanguageForm form) {
        return CDI.current().select(GetPreferredLanguageCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Language Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult<VoidResult> createLanguageDescription(UserVisitPK userVisitPK, CreateLanguageDescriptionForm form) {
        return CDI.current().select(CreateLanguageDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Party Type Password String Policies
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult<VoidResult> createPartyTypePasswordStringPolicy(UserVisitPK userVisitPK, CreatePartyTypePasswordStringPolicyForm form) {
        return CDI.current().select(CreatePartyTypePasswordStringPolicyCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<EditPartyTypePasswordStringPolicyResult> editPartyTypePasswordStringPolicy(UserVisitPK userVisitPK, EditPartyTypePasswordStringPolicyForm form) {
        return CDI.current().select(EditPartyTypePasswordStringPolicyCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<VoidResult> deletePartyTypePasswordStringPolicy(UserVisitPK userVisitPK, DeletePartyTypePasswordStringPolicyForm form) {
        return CDI.current().select(DeletePartyTypePasswordStringPolicyCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Party Type Lockout Policies
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult<VoidResult> createPartyTypeLockoutPolicy(UserVisitPK userVisitPK, CreatePartyTypeLockoutPolicyForm form) {
        return CDI.current().select(CreatePartyTypeLockoutPolicyCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<EditPartyTypeLockoutPolicyResult> editPartyTypeLockoutPolicy(UserVisitPK userVisitPK, EditPartyTypeLockoutPolicyForm form) {
        return CDI.current().select(EditPartyTypeLockoutPolicyCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<VoidResult> deletePartyTypeLockoutPolicy(UserVisitPK userVisitPK, DeletePartyTypeLockoutPolicyForm form) {
        return CDI.current().select(DeletePartyTypeLockoutPolicyCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Party Type Audit Policies
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult<VoidResult> createPartyTypeAuditPolicy(UserVisitPK userVisitPK, CreatePartyTypeAuditPolicyForm form) {
        return CDI.current().select(CreatePartyTypeAuditPolicyCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<EditPartyTypeAuditPolicyResult> editPartyTypeAuditPolicy(UserVisitPK userVisitPK, EditPartyTypeAuditPolicyForm form) {
        return CDI.current().select(EditPartyTypeAuditPolicyCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<VoidResult> deletePartyTypeAuditPolicy(UserVisitPK userVisitPK, DeletePartyTypeAuditPolicyForm form) {
        return CDI.current().select(DeletePartyTypeAuditPolicyCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Personal Titles
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult<CreatePersonalTitleResult> createPersonalTitle(UserVisitPK userVisitPK, CreatePersonalTitleForm form) {
        return CDI.current().select(CreatePersonalTitleCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetPersonalTitleChoicesResult> getPersonalTitleChoices(UserVisitPK userVisitPK, GetPersonalTitleChoicesForm form) {
        return CDI.current().select(GetPersonalTitleChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetPersonalTitlesResult> getPersonalTitles(UserVisitPK userVisitPK, GetPersonalTitlesForm form) {
        return CDI.current().select(GetPersonalTitlesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<VoidResult> setDefaultPersonalTitle(UserVisitPK userVisitPK, SetDefaultPersonalTitleForm form) {
        return CDI.current().select(SetDefaultPersonalTitleCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<EditPersonalTitleResult> editPersonalTitle(UserVisitPK userVisitPK, EditPersonalTitleForm form) {
        return CDI.current().select(EditPersonalTitleCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<VoidResult> deletePersonalTitle(UserVisitPK userVisitPK, DeletePersonalTitleForm form) {
        return CDI.current().select(DeletePersonalTitleCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Name Suffixes
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult<CreateNameSuffixResult> createNameSuffix(UserVisitPK userVisitPK, CreateNameSuffixForm form) {
        return CDI.current().select(CreateNameSuffixCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetNameSuffixChoicesResult> getNameSuffixChoices(UserVisitPK userVisitPK, GetNameSuffixChoicesForm form) {
        return CDI.current().select(GetNameSuffixChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetNameSuffixesResult> getNameSuffixes(UserVisitPK userVisitPK, GetNameSuffixesForm form) {
        return CDI.current().select(GetNameSuffixesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<VoidResult> setDefaultNameSuffix(UserVisitPK userVisitPK, SetDefaultNameSuffixForm form) {
        return CDI.current().select(SetDefaultNameSuffixCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<EditNameSuffixResult> editNameSuffix(UserVisitPK userVisitPK, EditNameSuffixForm form) {
        return CDI.current().select(EditNameSuffixCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<VoidResult> deleteNameSuffix(UserVisitPK userVisitPK, DeleteNameSuffixForm form) {
        return CDI.current().select(DeleteNameSuffixCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Date Time Formats
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult<VoidResult> createDateTimeFormat(UserVisitPK userVisitPK, CreateDateTimeFormatForm form) {
        return CDI.current().select(CreateDateTimeFormatCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetDateTimeFormatChoicesResult> getDateTimeFormatChoices(UserVisitPK userVisitPK, GetDateTimeFormatChoicesForm form) {
        return CDI.current().select(GetDateTimeFormatChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetDateTimeFormatResult> getDateTimeFormat(UserVisitPK userVisitPK, GetDateTimeFormatForm form) {
        return CDI.current().select(GetDateTimeFormatCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetPreferredDateTimeFormatResult> getPreferredDateTimeFormat(UserVisitPK userVisitPK, GetPreferredDateTimeFormatForm form) {
        return CDI.current().select(GetPreferredDateTimeFormatCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetDateTimeFormatsResult> getDateTimeFormats(UserVisitPK userVisitPK, GetDateTimeFormatsForm form) {
        return CDI.current().select(GetDateTimeFormatsCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Date Time Format Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult<VoidResult> createDateTimeFormatDescription(UserVisitPK userVisitPK, CreateDateTimeFormatDescriptionForm form) {
        return CDI.current().select(CreateDateTimeFormatDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetDateTimeFormatDescriptionsResult> getDateTimeFormatDescriptions(UserVisitPK userVisitPK, GetDateTimeFormatDescriptionsForm form) {
        return CDI.current().select(GetDateTimeFormatDescriptionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<EditDateTimeFormatDescriptionResult> editDateTimeFormatDescription(UserVisitPK userVisitPK, EditDateTimeFormatDescriptionForm form) {
        return CDI.current().select(EditDateTimeFormatDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<VoidResult> deleteDateTimeFormatDescription(UserVisitPK userVisitPK, DeleteDateTimeFormatDescriptionForm form) {
        return CDI.current().select(DeleteDateTimeFormatDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Time Zones
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult<VoidResult> createTimeZone(UserVisitPK userVisitPK, CreateTimeZoneForm form) {
        return CDI.current().select(CreateTimeZoneCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetTimeZoneChoicesResult> getTimeZoneChoices(UserVisitPK userVisitPK, GetTimeZoneChoicesForm form) {
        return CDI.current().select(GetTimeZoneChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetTimeZoneResult> getTimeZone(UserVisitPK userVisitPK, GetTimeZoneForm form) {
        return CDI.current().select(GetTimeZoneCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetPreferredTimeZoneResult> getPreferredTimeZone(UserVisitPK userVisitPK, GetPreferredTimeZoneForm form) {
        return CDI.current().select(GetPreferredTimeZoneCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetTimeZonesResult> getTimeZones(UserVisitPK userVisitPK, GetTimeZonesForm form) {
        return CDI.current().select(GetTimeZonesCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Time Zone Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult<VoidResult> createTimeZoneDescription(UserVisitPK userVisitPK, CreateTimeZoneDescriptionForm form) {
        return CDI.current().select(CreateTimeZoneDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetTimeZoneDescriptionsResult> getTimeZoneDescriptions(UserVisitPK userVisitPK, GetTimeZoneDescriptionsForm form) {
        return CDI.current().select(GetTimeZoneDescriptionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<EditTimeZoneDescriptionResult> editTimeZoneDescription(UserVisitPK userVisitPK, EditTimeZoneDescriptionForm form) {
        return CDI.current().select(EditTimeZoneDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<VoidResult> deleteTimeZoneDescription(UserVisitPK userVisitPK, DeleteTimeZoneDescriptionForm form) {
        return CDI.current().select(DeleteTimeZoneDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Companies
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult<CreateCompanyResult> createCompany(UserVisitPK userVisitPK, CreateCompanyForm form) {
        return CDI.current().select(CreateCompanyCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetCompanyChoicesResult> getCompanyChoices(UserVisitPK userVisitPK, GetCompanyChoicesForm form) {
        return CDI.current().select(GetCompanyChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetCompaniesResult> getCompanies(UserVisitPK userVisitPK, GetCompaniesForm form) {
        return CDI.current().select(GetCompaniesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetCompanyResult> getCompany(UserVisitPK userVisitPK, GetCompanyForm form) {
        return CDI.current().select(GetCompanyCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<VoidResult> setDefaultCompany(UserVisitPK userVisitPK, SetDefaultCompanyForm form) {
        return CDI.current().select(SetDefaultCompanyCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<EditCompanyResult> editCompany(UserVisitPK userVisitPK, EditCompanyForm form) {
        return CDI.current().select(EditCompanyCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<VoidResult> deleteCompany(UserVisitPK userVisitPK, DeleteCompanyForm form) {
        return CDI.current().select(DeleteCompanyCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Divisions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult<CreateDivisionResult> createDivision(UserVisitPK userVisitPK, CreateDivisionForm form) {
        return CDI.current().select(CreateDivisionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetDivisionChoicesResult> getDivisionChoices(UserVisitPK userVisitPK, GetDivisionChoicesForm form) {
        return CDI.current().select(GetDivisionChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetDivisionsResult> getDivisions(UserVisitPK userVisitPK, GetDivisionsForm form) {
        return CDI.current().select(GetDivisionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetDivisionResult> getDivision(UserVisitPK userVisitPK, GetDivisionForm form) {
        return CDI.current().select(GetDivisionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<VoidResult> setDefaultDivision(UserVisitPK userVisitPK, SetDefaultDivisionForm form) {
        return CDI.current().select(SetDefaultDivisionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<EditDivisionResult> editDivision(UserVisitPK userVisitPK, EditDivisionForm form) {
        return CDI.current().select(EditDivisionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<VoidResult> deleteDivision(UserVisitPK userVisitPK, DeleteDivisionForm form) {
        return CDI.current().select(DeleteDivisionCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Departments
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult<CreateDepartmentResult> createDepartment(UserVisitPK userVisitPK, CreateDepartmentForm form) {
        return CDI.current().select(CreateDepartmentCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetDepartmentChoicesResult> getDepartmentChoices(UserVisitPK userVisitPK, GetDepartmentChoicesForm form) {
        return CDI.current().select(GetDepartmentChoicesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetDepartmentsResult> getDepartments(UserVisitPK userVisitPK, GetDepartmentsForm form) {
        return CDI.current().select(GetDepartmentsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetDepartmentResult> getDepartment(UserVisitPK userVisitPK, GetDepartmentForm form) {
        return CDI.current().select(GetDepartmentCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<VoidResult> setDefaultDepartment(UserVisitPK userVisitPK, SetDefaultDepartmentForm form) {
        return CDI.current().select(SetDefaultDepartmentCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<EditDepartmentResult> editDepartment(UserVisitPK userVisitPK, EditDepartmentForm form) {
        return CDI.current().select(EditDepartmentCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<VoidResult> deleteDepartment(UserVisitPK userVisitPK, DeleteDepartmentForm form) {
        return CDI.current().select(DeleteDepartmentCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Party Relationship Types
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult<VoidResult> createPartyRelationshipType(UserVisitPK userVisitPK, CreatePartyRelationshipTypeForm form) {
        return CDI.current().select(CreatePartyRelationshipTypeCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Party Relationship Type Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult<VoidResult> createPartyRelationshipTypeDescription(UserVisitPK userVisitPK, CreatePartyRelationshipTypeDescriptionForm form) {
        return CDI.current().select(CreatePartyRelationshipTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Party Relationships
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult<GetPartyRelationshipsResult> getPartyRelationships(UserVisitPK userVisitPK, GetPartyRelationshipsForm form) {
        return CDI.current().select(GetPartyRelationshipsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetPartyRelationshipResult> getPartyRelationship(UserVisitPK userVisitPK, GetPartyRelationshipForm form) {
        return CDI.current().select(GetPartyRelationshipCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Role Types
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult<VoidResult> createRoleType(UserVisitPK userVisitPK, CreateRoleTypeForm form) {
        return CDI.current().select(CreateRoleTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetRoleTypeResult> getRoleType(UserVisitPK userVisitPK, GetRoleTypeForm form) {
        return CDI.current().select(GetRoleTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetRoleTypesResult> getRoleTypes(UserVisitPK userVisitPK, GetRoleTypesForm form) {
        return CDI.current().select(GetRoleTypesCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Role Type Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult<VoidResult> createRoleTypeDescription(UserVisitPK userVisitPK, CreateRoleTypeDescriptionForm form) {
        return CDI.current().select(CreateRoleTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Customers
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult<CreateCustomerResult> createCustomer(UserVisitPK userVisitPK, CreateCustomerForm form) {
        return CDI.current().select(CreateCustomerCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<CreateCustomerWithLoginResult> createCustomerWithLogin(UserVisitPK userVisitPK, CreateCustomerWithLoginForm form) {
        return CDI.current().select(CreateCustomerWithLoginCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Employees
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult<CreateEmployeeResult> createEmployee(UserVisitPK userVisitPK, CreateEmployeeForm form) {
        return CDI.current().select(CreateEmployeeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<VoidResult> addEmployeeToCompany(UserVisitPK userVisitPK, AddEmployeeToCompanyForm form) {
        return CDI.current().select(AddEmployeeToCompanyCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<VoidResult> addEmployeeToDivision(UserVisitPK userVisitPK, AddEmployeeToDivisionForm form) {
        return CDI.current().select(AddEmployeeToDivisionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<VoidResult> addEmployeeToDepartment(UserVisitPK userVisitPK, AddEmployeeToDepartmentForm form) {
        return CDI.current().select(AddEmployeeToDepartmentCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<VoidResult> removeEmployeeFromCompany(UserVisitPK userVisitPK, RemoveEmployeeFromCompanyForm form) {
        return CDI.current().select(RemoveEmployeeFromCompanyCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<VoidResult> removeEmployeeFromDivision(UserVisitPK userVisitPK, RemoveEmployeeFromDivisionForm form) {
        return CDI.current().select(RemoveEmployeeFromDivisionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<VoidResult> removeEmployeeFromDepartment(UserVisitPK userVisitPK, RemoveEmployeeFromDepartmentForm form) {
        return CDI.current().select(RemoveEmployeFromDepartmentCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetEmployeeStatusChoicesResult> getEmployeeStatusChoices(UserVisitPK userVisitPK, GetEmployeeStatusChoicesForm form) {
        return CDI.current().select(GetEmployeeStatusChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<VoidResult> setEmployeeStatus(UserVisitPK userVisitPK, SetEmployeeStatusForm form) {
        return CDI.current().select(SetEmployeeStatusCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetEmployeeAvailabilityChoicesResult> getEmployeeAvailabilityChoices(UserVisitPK userVisitPK, GetEmployeeAvailabilityChoicesForm form) {
        return CDI.current().select(GetEmployeeAvailabilityChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<VoidResult> setEmployeeAvailability(UserVisitPK userVisitPK, SetEmployeeAvailabilityForm form) {
        return CDI.current().select(SetEmployeeAvailabilityCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<EditEmployeeResult> editEmployee(UserVisitPK userVisitPK, EditEmployeeForm form) {
        return CDI.current().select(EditEmployeeCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Vendors
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<CreateVendorResult> createVendor(UserVisitPK userVisitPK, CreateVendorForm form) {
        return CDI.current().select(CreateVendorCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetVendorStatusChoicesResult> getVendorStatusChoices(UserVisitPK userVisitPK, GetVendorStatusChoicesForm form) {
        return CDI.current().select(GetVendorStatusChoicesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<VoidResult> setVendorStatus(UserVisitPK userVisitPK, SetVendorStatusForm form) {
        return CDI.current().select(SetVendorStatusCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Genders
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<VoidResult> createGender(UserVisitPK userVisitPK, CreateGenderForm form) {
        return CDI.current().select(CreateGenderCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetGenderChoicesResult> getGenderChoices(UserVisitPK userVisitPK, GetGenderChoicesForm form) {
        return CDI.current().select(GetGenderChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetGenderResult> getGender(UserVisitPK userVisitPK, GetGenderForm form) {
        return CDI.current().select(GetGenderCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetGendersResult> getGenders(UserVisitPK userVisitPK, GetGendersForm form) {
        return CDI.current().select(GetGendersCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<VoidResult> setDefaultGender(UserVisitPK userVisitPK, SetDefaultGenderForm form) {
        return CDI.current().select(SetDefaultGenderCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<EditGenderResult> editGender(UserVisitPK userVisitPK, EditGenderForm form) {
        return CDI.current().select(EditGenderCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<VoidResult> deleteGender(UserVisitPK userVisitPK, DeleteGenderForm form) {
        return CDI.current().select(DeleteGenderCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Gender Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<VoidResult> createGenderDescription(UserVisitPK userVisitPK, CreateGenderDescriptionForm form) {
        return CDI.current().select(CreateGenderDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetGenderDescriptionsResult> getGenderDescriptions(UserVisitPK userVisitPK, GetGenderDescriptionsForm form) {
        return CDI.current().select(GetGenderDescriptionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<EditGenderDescriptionResult> editGenderDescription(UserVisitPK userVisitPK, EditGenderDescriptionForm form) {
        return CDI.current().select(EditGenderDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<VoidResult> deleteGenderDescription(UserVisitPK userVisitPK, DeleteGenderDescriptionForm form) {
        return CDI.current().select(DeleteGenderDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Birthday Formats
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult<VoidResult> createBirthdayFormat(UserVisitPK userVisitPK, CreateBirthdayFormatForm form) {
        return CDI.current().select(CreateBirthdayFormatCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetBirthdayFormatChoicesResult> getBirthdayFormatChoices(UserVisitPK userVisitPK, GetBirthdayFormatChoicesForm form) {
        return CDI.current().select(GetBirthdayFormatChoicesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetBirthdayFormatResult> getBirthdayFormat(UserVisitPK userVisitPK, GetBirthdayFormatForm form) {
        return CDI.current().select(GetBirthdayFormatCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetBirthdayFormatsResult> getBirthdayFormats(UserVisitPK userVisitPK, GetBirthdayFormatsForm form) {
        return CDI.current().select(GetBirthdayFormatsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<VoidResult> setDefaultBirthdayFormat(UserVisitPK userVisitPK, SetDefaultBirthdayFormatForm form) {
        return CDI.current().select(SetDefaultBirthdayFormatCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<EditBirthdayFormatResult> editBirthdayFormat(UserVisitPK userVisitPK, EditBirthdayFormatForm form) {
        return CDI.current().select(EditBirthdayFormatCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<VoidResult> deleteBirthdayFormat(UserVisitPK userVisitPK, DeleteBirthdayFormatForm form) {
        return CDI.current().select(DeleteBirthdayFormatCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Birthday Format Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult<VoidResult> createBirthdayFormatDescription(UserVisitPK userVisitPK, CreateBirthdayFormatDescriptionForm form) {
        return CDI.current().select(CreateBirthdayFormatDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetBirthdayFormatDescriptionResult> getBirthdayFormatDescription(UserVisitPK userVisitPK, GetBirthdayFormatDescriptionForm form) {
        return CDI.current().select(GetBirthdayFormatDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetBirthdayFormatDescriptionsResult> getBirthdayFormatDescriptions(UserVisitPK userVisitPK, GetBirthdayFormatDescriptionsForm form) {
        return CDI.current().select(GetBirthdayFormatDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<EditBirthdayFormatDescriptionResult> editBirthdayFormatDescription(UserVisitPK userVisitPK, EditBirthdayFormatDescriptionForm form) {
        return CDI.current().select(EditBirthdayFormatDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<VoidResult> deleteBirthdayFormatDescription(UserVisitPK userVisitPK, DeleteBirthdayFormatDescriptionForm form) {
        return CDI.current().select(DeleteBirthdayFormatDescriptionCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Profiles
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<VoidResult> createProfile(UserVisitPK userVisitPK, CreateProfileForm form) {
        return CDI.current().select(CreateProfileCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<EditProfileResult> editProfile(UserVisitPK userVisitPK, EditProfileForm form) {
        return CDI.current().select(EditProfileCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Party Alias Types
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult<CreatePartyAliasTypeResult> createPartyAliasType(UserVisitPK userVisitPK, CreatePartyAliasTypeForm form) {
        return CDI.current().select(CreatePartyAliasTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetPartyAliasTypeChoicesResult> getPartyAliasTypeChoices(UserVisitPK userVisitPK, GetPartyAliasTypeChoicesForm form) {
        return CDI.current().select(GetPartyAliasTypeChoicesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetPartyAliasTypeResult> getPartyAliasType(UserVisitPK userVisitPK, GetPartyAliasTypeForm form) {
        return CDI.current().select(GetPartyAliasTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetPartyAliasTypesResult> getPartyAliasTypes(UserVisitPK userVisitPK, GetPartyAliasTypesForm form) {
        return CDI.current().select(GetPartyAliasTypesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<VoidResult> setDefaultPartyAliasType(UserVisitPK userVisitPK, SetDefaultPartyAliasTypeForm form) {
        return CDI.current().select(SetDefaultPartyAliasTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<EditPartyAliasTypeResult> editPartyAliasType(UserVisitPK userVisitPK, EditPartyAliasTypeForm form) {
        return CDI.current().select(EditPartyAliasTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<VoidResult> deletePartyAliasType(UserVisitPK userVisitPK, DeletePartyAliasTypeForm form) {
        return CDI.current().select(DeletePartyAliasTypeCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Party Alias Type Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult<VoidResult> createPartyAliasTypeDescription(UserVisitPK userVisitPK, CreatePartyAliasTypeDescriptionForm form) {
        return CDI.current().select(CreatePartyAliasTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetPartyAliasTypeDescriptionResult> getPartyAliasTypeDescription(UserVisitPK userVisitPK, GetPartyAliasTypeDescriptionForm form) {
        return CDI.current().select(GetPartyAliasTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetPartyAliasTypeDescriptionsResult> getPartyAliasTypeDescriptions(UserVisitPK userVisitPK, GetPartyAliasTypeDescriptionsForm form) {
        return CDI.current().select(GetPartyAliasTypeDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<EditPartyAliasTypeDescriptionResult> editPartyAliasTypeDescription(UserVisitPK userVisitPK, EditPartyAliasTypeDescriptionForm form) {
        return CDI.current().select(EditPartyAliasTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<VoidResult> deletePartyAliasTypeDescription(UserVisitPK userVisitPK, DeletePartyAliasTypeDescriptionForm form) {
        return CDI.current().select(DeletePartyAliasTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Party Aliases
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult<VoidResult> createPartyAlias(UserVisitPK userVisitPK, CreatePartyAliasForm form) {
        return CDI.current().select(CreatePartyAliasCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetPartyAliasResult> getPartyAlias(UserVisitPK userVisitPK, GetPartyAliasForm form) {
        return CDI.current().select(GetPartyAliasCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetPartyAliasesResult> getPartyAliases(UserVisitPK userVisitPK, GetPartyAliasesForm form) {
        return CDI.current().select(GetPartyAliasesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<EditPartyAliasResult> editPartyAlias(UserVisitPK userVisitPK, EditPartyAliasForm form) {
        return CDI.current().select(EditPartyAliasCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<VoidResult> deletePartyAlias(UserVisitPK userVisitPK, DeletePartyAliasForm form) {
        return CDI.current().select(DeletePartyAliasCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Parties
    // -------------------------------------------------------------------------

    @Override
    public CommandResult<GetPartyResult> getParty(UserVisitPK userVisitPK, GetPartyForm form) {
        return CDI.current().select(GetPartyCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetPartiesResult> getParties(UserVisitPK userVisitPK, GetPartiesForm form) {
        return CDI.current().select(GetPartiesCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Party Entity Types
    // -------------------------------------------------------------------------

    @Override
    public CommandResult<VoidResult> createPartyEntityType(UserVisitPK userVisitPK, CreatePartyEntityTypeForm form) {
        return CDI.current().select(CreatePartyEntityTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<EditPartyEntityTypeResult> editPartyEntityType(UserVisitPK userVisitPK, EditPartyEntityTypeForm form) {
        return CDI.current().select(EditPartyEntityTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetPartyEntityTypeResult> getPartyEntityType(UserVisitPK userVisitPK, GetPartyEntityTypeForm form) {
        return CDI.current().select(GetPartyEntityTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetPartyEntityTypesResult> getPartyEntityTypes(UserVisitPK userVisitPK, GetPartyEntityTypesForm form) {
        return CDI.current().select(GetPartyEntityTypesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<VoidResult> deletePartyEntityType(UserVisitPK userVisitPK, DeletePartyEntityTypeForm form) {
        return CDI.current().select(DeletePartyEntityTypeCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Party Application Editor Uses
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult<VoidResult> createPartyApplicationEditorUse(UserVisitPK userVisitPK, CreatePartyApplicationEditorUseForm form) {
        return CDI.current().select(CreatePartyApplicationEditorUseCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetPartyApplicationEditorUseResult> getPartyApplicationEditorUse(UserVisitPK userVisitPK, GetPartyApplicationEditorUseForm form) {
        return CDI.current().select(GetPartyApplicationEditorUseCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetPartyApplicationEditorUsesResult> getPartyApplicationEditorUses(UserVisitPK userVisitPK, GetPartyApplicationEditorUsesForm form) {
        return CDI.current().select(GetPartyApplicationEditorUsesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<EditPartyApplicationEditorUseResult> editPartyApplicationEditorUse(UserVisitPK userVisitPK, EditPartyApplicationEditorUseForm form) {
        return CDI.current().select(EditPartyApplicationEditorUseCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<VoidResult> deletePartyApplicationEditorUse(UserVisitPK userVisitPK, DeletePartyApplicationEditorUseForm form) {
        return CDI.current().select(DeletePartyApplicationEditorUseCommand.class).get().run(userVisitPK, form);
    }

}
