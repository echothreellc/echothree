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
import com.echothree.control.user.party.server.command.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
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
    public CommandResult createPartyType(UserVisitPK userVisitPK, CreatePartyTypeForm form) {
        return CDI.current().select(CreatePartyTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getPartyType(UserVisitPK userVisitPK, GetPartyTypeForm form) {
        return CDI.current().select(GetPartyTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getPartyTypes(UserVisitPK userVisitPK, GetPartyTypesForm form) {
        return CDI.current().select(GetPartyTypesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getPartyTypeChoices(UserVisitPK userVisitPK, GetPartyTypeChoicesForm form) {
        return CDI.current().select(GetPartyTypeChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Party Type Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createPartyTypeDescription(UserVisitPK userVisitPK, CreatePartyTypeDescriptionForm form) {
        return CDI.current().select(CreatePartyTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Party Type Use Types
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createPartyTypeUseType(UserVisitPK userVisitPK, CreatePartyTypeUseTypeForm form) {
        return CDI.current().select(CreatePartyTypeUseTypeCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Party Type Use Type Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createPartyTypeUseTypeDescription(UserVisitPK userVisitPK, CreatePartyTypeUseTypeDescriptionForm form) {
        return CDI.current().select(CreatePartyTypeUseTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Party Type Uses
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createPartyTypeUse(UserVisitPK userVisitPK, CreatePartyTypeUseForm form) {
        return CDI.current().select(CreatePartyTypeUseCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Languages
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createLanguage(UserVisitPK userVisitPK, CreateLanguageForm form) {
        return CDI.current().select(CreateLanguageCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getLanguageChoices(UserVisitPK userVisitPK, GetLanguageChoicesForm form) {
        return CDI.current().select(GetLanguageChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getLanguages(UserVisitPK userVisitPK, GetLanguagesForm form) {
        return CDI.current().select(GetLanguagesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getLanguage(UserVisitPK userVisitPK, GetLanguageForm form) {
        return CDI.current().select(GetLanguageCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPreferredLanguage(UserVisitPK userVisitPK, GetPreferredLanguageForm form) {
        return CDI.current().select(GetPreferredLanguageCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Language Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createLanguageDescription(UserVisitPK userVisitPK, CreateLanguageDescriptionForm form) {
        return CDI.current().select(CreateLanguageDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Party Type Password String Policies
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createPartyTypePasswordStringPolicy(UserVisitPK userVisitPK, CreatePartyTypePasswordStringPolicyForm form) {
        return CDI.current().select(CreatePartyTypePasswordStringPolicyCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editPartyTypePasswordStringPolicy(UserVisitPK userVisitPK, EditPartyTypePasswordStringPolicyForm form) {
        return CDI.current().select(EditPartyTypePasswordStringPolicyCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deletePartyTypePasswordStringPolicy(UserVisitPK userVisitPK, DeletePartyTypePasswordStringPolicyForm form) {
        return CDI.current().select(DeletePartyTypePasswordStringPolicyCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Party Type Lockout Policies
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createPartyTypeLockoutPolicy(UserVisitPK userVisitPK, CreatePartyTypeLockoutPolicyForm form) {
        return CDI.current().select(CreatePartyTypeLockoutPolicyCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editPartyTypeLockoutPolicy(UserVisitPK userVisitPK, EditPartyTypeLockoutPolicyForm form) {
        return CDI.current().select(EditPartyTypeLockoutPolicyCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deletePartyTypeLockoutPolicy(UserVisitPK userVisitPK, DeletePartyTypeLockoutPolicyForm form) {
        return CDI.current().select(DeletePartyTypeLockoutPolicyCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Party Type Audit Policies
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createPartyTypeAuditPolicy(UserVisitPK userVisitPK, CreatePartyTypeAuditPolicyForm form) {
        return CDI.current().select(CreatePartyTypeAuditPolicyCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editPartyTypeAuditPolicy(UserVisitPK userVisitPK, EditPartyTypeAuditPolicyForm form) {
        return CDI.current().select(EditPartyTypeAuditPolicyCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deletePartyTypeAuditPolicy(UserVisitPK userVisitPK, DeletePartyTypeAuditPolicyForm form) {
        return CDI.current().select(DeletePartyTypeAuditPolicyCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Personal Titles
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createPersonalTitle(UserVisitPK userVisitPK, CreatePersonalTitleForm form) {
        return CDI.current().select(CreatePersonalTitleCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getPersonalTitleChoices(UserVisitPK userVisitPK, GetPersonalTitleChoicesForm form) {
        return CDI.current().select(GetPersonalTitleChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getPersonalTitles(UserVisitPK userVisitPK, GetPersonalTitlesForm form) {
        return CDI.current().select(GetPersonalTitlesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultPersonalTitle(UserVisitPK userVisitPK, SetDefaultPersonalTitleForm form) {
        return CDI.current().select(SetDefaultPersonalTitleCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editPersonalTitle(UserVisitPK userVisitPK, EditPersonalTitleForm form) {
        return CDI.current().select(EditPersonalTitleCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deletePersonalTitle(UserVisitPK userVisitPK, DeletePersonalTitleForm form) {
        return CDI.current().select(DeletePersonalTitleCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Name Suffixes
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createNameSuffix(UserVisitPK userVisitPK, CreateNameSuffixForm form) {
        return CDI.current().select(CreateNameSuffixCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getNameSuffixChoices(UserVisitPK userVisitPK, GetNameSuffixChoicesForm form) {
        return CDI.current().select(GetNameSuffixChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getNameSuffixes(UserVisitPK userVisitPK, GetNameSuffixesForm form) {
        return CDI.current().select(GetNameSuffixesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultNameSuffix(UserVisitPK userVisitPK, SetDefaultNameSuffixForm form) {
        return CDI.current().select(SetDefaultNameSuffixCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editNameSuffix(UserVisitPK userVisitPK, EditNameSuffixForm form) {
        return CDI.current().select(EditNameSuffixCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteNameSuffix(UserVisitPK userVisitPK, DeleteNameSuffixForm form) {
        return CDI.current().select(DeleteNameSuffixCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Date Time Formats
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createDateTimeFormat(UserVisitPK userVisitPK, CreateDateTimeFormatForm form) {
        return CDI.current().select(CreateDateTimeFormatCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getDateTimeFormatChoices(UserVisitPK userVisitPK, GetDateTimeFormatChoicesForm form) {
        return CDI.current().select(GetDateTimeFormatChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getDateTimeFormat(UserVisitPK userVisitPK, GetDateTimeFormatForm form) {
        return CDI.current().select(GetDateTimeFormatCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPreferredDateTimeFormat(UserVisitPK userVisitPK, GetPreferredDateTimeFormatForm form) {
        return CDI.current().select(GetPreferredDateTimeFormatCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getDateTimeFormats(UserVisitPK userVisitPK, GetDateTimeFormatsForm form) {
        return CDI.current().select(GetDateTimeFormatsCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Date Time Format Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createDateTimeFormatDescription(UserVisitPK userVisitPK, CreateDateTimeFormatDescriptionForm form) {
        return CDI.current().select(CreateDateTimeFormatDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getDateTimeFormatDescriptions(UserVisitPK userVisitPK, GetDateTimeFormatDescriptionsForm form) {
        return CDI.current().select(GetDateTimeFormatDescriptionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editDateTimeFormatDescription(UserVisitPK userVisitPK, EditDateTimeFormatDescriptionForm form) {
        return CDI.current().select(EditDateTimeFormatDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteDateTimeFormatDescription(UserVisitPK userVisitPK, DeleteDateTimeFormatDescriptionForm form) {
        return CDI.current().select(DeleteDateTimeFormatDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Time Zones
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createTimeZone(UserVisitPK userVisitPK, CreateTimeZoneForm form) {
        return CDI.current().select(CreateTimeZoneCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTimeZoneChoices(UserVisitPK userVisitPK, GetTimeZoneChoicesForm form) {
        return CDI.current().select(GetTimeZoneChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTimeZone(UserVisitPK userVisitPK, GetTimeZoneForm form) {
        return CDI.current().select(GetTimeZoneCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPreferredTimeZone(UserVisitPK userVisitPK, GetPreferredTimeZoneForm form) {
        return CDI.current().select(GetPreferredTimeZoneCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getTimeZones(UserVisitPK userVisitPK, GetTimeZonesForm form) {
        return CDI.current().select(GetTimeZonesCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Time Zone Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createTimeZoneDescription(UserVisitPK userVisitPK, CreateTimeZoneDescriptionForm form) {
        return CDI.current().select(CreateTimeZoneDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTimeZoneDescriptions(UserVisitPK userVisitPK, GetTimeZoneDescriptionsForm form) {
        return CDI.current().select(GetTimeZoneDescriptionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editTimeZoneDescription(UserVisitPK userVisitPK, EditTimeZoneDescriptionForm form) {
        return CDI.current().select(EditTimeZoneDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteTimeZoneDescription(UserVisitPK userVisitPK, DeleteTimeZoneDescriptionForm form) {
        return CDI.current().select(DeleteTimeZoneDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Companies
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCompany(UserVisitPK userVisitPK, CreateCompanyForm form) {
        return CDI.current().select(CreateCompanyCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCompanyChoices(UserVisitPK userVisitPK, GetCompanyChoicesForm form) {
        return CDI.current().select(GetCompanyChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCompanies(UserVisitPK userVisitPK, GetCompaniesForm form) {
        return CDI.current().select(GetCompaniesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCompany(UserVisitPK userVisitPK, GetCompanyForm form) {
        return CDI.current().select(GetCompanyCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultCompany(UserVisitPK userVisitPK, SetDefaultCompanyForm form) {
        return CDI.current().select(SetDefaultCompanyCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editCompany(UserVisitPK userVisitPK, EditCompanyForm form) {
        return CDI.current().select(EditCompanyCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteCompany(UserVisitPK userVisitPK, DeleteCompanyForm form) {
        return CDI.current().select(DeleteCompanyCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Divisions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createDivision(UserVisitPK userVisitPK, CreateDivisionForm form) {
        return CDI.current().select(CreateDivisionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getDivisionChoices(UserVisitPK userVisitPK, GetDivisionChoicesForm form) {
        return CDI.current().select(GetDivisionChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getDivisions(UserVisitPK userVisitPK, GetDivisionsForm form) {
        return CDI.current().select(GetDivisionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getDivision(UserVisitPK userVisitPK, GetDivisionForm form) {
        return CDI.current().select(GetDivisionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultDivision(UserVisitPK userVisitPK, SetDefaultDivisionForm form) {
        return CDI.current().select(SetDefaultDivisionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editDivision(UserVisitPK userVisitPK, EditDivisionForm form) {
        return CDI.current().select(EditDivisionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteDivision(UserVisitPK userVisitPK, DeleteDivisionForm form) {
        return CDI.current().select(DeleteDivisionCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Departments
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createDepartment(UserVisitPK userVisitPK, CreateDepartmentForm form) {
        return CDI.current().select(CreateDepartmentCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getDepartmentChoices(UserVisitPK userVisitPK, GetDepartmentChoicesForm form) {
        return CDI.current().select(GetDepartmentChoicesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getDepartments(UserVisitPK userVisitPK, GetDepartmentsForm form) {
        return CDI.current().select(GetDepartmentsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getDepartment(UserVisitPK userVisitPK, GetDepartmentForm form) {
        return CDI.current().select(GetDepartmentCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultDepartment(UserVisitPK userVisitPK, SetDefaultDepartmentForm form) {
        return CDI.current().select(SetDefaultDepartmentCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editDepartment(UserVisitPK userVisitPK, EditDepartmentForm form) {
        return CDI.current().select(EditDepartmentCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteDepartment(UserVisitPK userVisitPK, DeleteDepartmentForm form) {
        return CDI.current().select(DeleteDepartmentCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Party Relationship Types
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createPartyRelationshipType(UserVisitPK userVisitPK, CreatePartyRelationshipTypeForm form) {
        return CDI.current().select(CreatePartyRelationshipTypeCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Party Relationship Type Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createPartyRelationshipTypeDescription(UserVisitPK userVisitPK, CreatePartyRelationshipTypeDescriptionForm form) {
        return CDI.current().select(CreatePartyRelationshipTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Party Relationships
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult getPartyRelationships(UserVisitPK userVisitPK, GetPartyRelationshipsForm form) {
        return CDI.current().select(GetPartyRelationshipsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPartyRelationship(UserVisitPK userVisitPK, GetPartyRelationshipForm form) {
        return CDI.current().select(GetPartyRelationshipCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Role Types
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createRoleType(UserVisitPK userVisitPK, CreateRoleTypeForm form) {
        return CDI.current().select(CreateRoleTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getRoleType(UserVisitPK userVisitPK, GetRoleTypeForm form) {
        return CDI.current().select(GetRoleTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getRoleTypes(UserVisitPK userVisitPK, GetRoleTypesForm form) {
        return CDI.current().select(GetRoleTypesCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Role Type Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createRoleTypeDescription(UserVisitPK userVisitPK, CreateRoleTypeDescriptionForm form) {
        return CDI.current().select(CreateRoleTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Customers
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCustomer(UserVisitPK userVisitPK, CreateCustomerForm form) {
        return CDI.current().select(CreateCustomerCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult createCustomerWithLogin(UserVisitPK userVisitPK, CreateCustomerWithLoginForm form) {
        return CDI.current().select(CreateCustomerWithLoginCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Employees
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createEmployee(UserVisitPK userVisitPK, CreateEmployeeForm form) {
        return CDI.current().select(CreateEmployeeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult addEmployeeToCompany(UserVisitPK userVisitPK, AddEmployeeToCompanyForm form) {
        return CDI.current().select(AddEmployeeToCompanyCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult addEmployeeToDivision(UserVisitPK userVisitPK, AddEmployeeToDivisionForm form) {
        return CDI.current().select(AddEmployeeToDivisionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult addEmployeeToDepartment(UserVisitPK userVisitPK, AddEmployeeToDepartmentForm form) {
        return CDI.current().select(AddEmployeeToDepartmentCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult removeEmployeeFromCompany(UserVisitPK userVisitPK, RemoveEmployeeFromCompanyForm form) {
        return CDI.current().select(RemoveEmployeeFromCompanyCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult removeEmployeeFromDivision(UserVisitPK userVisitPK, RemoveEmployeeFromDivisionForm form) {
        return CDI.current().select(RemoveEmployeeFromDivisionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult removeEmployeeFromDepartment(UserVisitPK userVisitPK, RemoveEmployeeFromDepartmentForm form) {
        return CDI.current().select(RemoveEmployeFromDepartmentCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getEmployeeStatusChoices(UserVisitPK userVisitPK, GetEmployeeStatusChoicesForm form) {
        return CDI.current().select(GetEmployeeStatusChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setEmployeeStatus(UserVisitPK userVisitPK, SetEmployeeStatusForm form) {
        return CDI.current().select(SetEmployeeStatusCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getEmployeeAvailabilityChoices(UserVisitPK userVisitPK, GetEmployeeAvailabilityChoicesForm form) {
        return CDI.current().select(GetEmployeeAvailabilityChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setEmployeeAvailability(UserVisitPK userVisitPK, SetEmployeeAvailabilityForm form) {
        return CDI.current().select(SetEmployeeAvailabilityCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editEmployee(UserVisitPK userVisitPK, EditEmployeeForm form) {
        return CDI.current().select(EditEmployeeCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Vendors
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createVendor(UserVisitPK userVisitPK, CreateVendorForm form) {
        return CDI.current().select(CreateVendorCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getVendorStatusChoices(UserVisitPK userVisitPK, GetVendorStatusChoicesForm form) {
        return CDI.current().select(GetVendorStatusChoicesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult setVendorStatus(UserVisitPK userVisitPK, SetVendorStatusForm form) {
        return CDI.current().select(SetVendorStatusCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Genders
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createGender(UserVisitPK userVisitPK, CreateGenderForm form) {
        return CDI.current().select(CreateGenderCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getGenderChoices(UserVisitPK userVisitPK, GetGenderChoicesForm form) {
        return CDI.current().select(GetGenderChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getGender(UserVisitPK userVisitPK, GetGenderForm form) {
        return CDI.current().select(GetGenderCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getGenders(UserVisitPK userVisitPK, GetGendersForm form) {
        return CDI.current().select(GetGendersCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultGender(UserVisitPK userVisitPK, SetDefaultGenderForm form) {
        return CDI.current().select(SetDefaultGenderCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editGender(UserVisitPK userVisitPK, EditGenderForm form) {
        return CDI.current().select(EditGenderCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteGender(UserVisitPK userVisitPK, DeleteGenderForm form) {
        return CDI.current().select(DeleteGenderCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Gender Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createGenderDescription(UserVisitPK userVisitPK, CreateGenderDescriptionForm form) {
        return CDI.current().select(CreateGenderDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getGenderDescriptions(UserVisitPK userVisitPK, GetGenderDescriptionsForm form) {
        return CDI.current().select(GetGenderDescriptionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editGenderDescription(UserVisitPK userVisitPK, EditGenderDescriptionForm form) {
        return CDI.current().select(EditGenderDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteGenderDescription(UserVisitPK userVisitPK, DeleteGenderDescriptionForm form) {
        return CDI.current().select(DeleteGenderDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Moods
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createMood(UserVisitPK userVisitPK, CreateMoodForm form) {
        return CDI.current().select(CreateMoodCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getMoodChoices(UserVisitPK userVisitPK, GetMoodChoicesForm form) {
        return CDI.current().select(GetMoodChoicesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getMood(UserVisitPK userVisitPK, GetMoodForm form) {
        return CDI.current().select(GetMoodCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getMoods(UserVisitPK userVisitPK, GetMoodsForm form) {
        return CDI.current().select(GetMoodsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultMood(UserVisitPK userVisitPK, SetDefaultMoodForm form) {
        return CDI.current().select(SetDefaultMoodCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editMood(UserVisitPK userVisitPK, EditMoodForm form) {
        return CDI.current().select(EditMoodCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteMood(UserVisitPK userVisitPK, DeleteMoodForm form) {
        return CDI.current().select(DeleteMoodCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Mood Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createMoodDescription(UserVisitPK userVisitPK, CreateMoodDescriptionForm form) {
        return CDI.current().select(CreateMoodDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getMoodDescriptions(UserVisitPK userVisitPK, GetMoodDescriptionsForm form) {
        return CDI.current().select(GetMoodDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editMoodDescription(UserVisitPK userVisitPK, EditMoodDescriptionForm form) {
        return CDI.current().select(EditMoodDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteMoodDescription(UserVisitPK userVisitPK, DeleteMoodDescriptionForm form) {
        return CDI.current().select(DeleteMoodDescriptionCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Birthday Formats
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createBirthdayFormat(UserVisitPK userVisitPK, CreateBirthdayFormatForm form) {
        return CDI.current().select(CreateBirthdayFormatCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getBirthdayFormatChoices(UserVisitPK userVisitPK, GetBirthdayFormatChoicesForm form) {
        return CDI.current().select(GetBirthdayFormatChoicesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getBirthdayFormat(UserVisitPK userVisitPK, GetBirthdayFormatForm form) {
        return CDI.current().select(GetBirthdayFormatCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getBirthdayFormats(UserVisitPK userVisitPK, GetBirthdayFormatsForm form) {
        return CDI.current().select(GetBirthdayFormatsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultBirthdayFormat(UserVisitPK userVisitPK, SetDefaultBirthdayFormatForm form) {
        return CDI.current().select(SetDefaultBirthdayFormatCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editBirthdayFormat(UserVisitPK userVisitPK, EditBirthdayFormatForm form) {
        return CDI.current().select(EditBirthdayFormatCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteBirthdayFormat(UserVisitPK userVisitPK, DeleteBirthdayFormatForm form) {
        return CDI.current().select(DeleteBirthdayFormatCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Birthday Format Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createBirthdayFormatDescription(UserVisitPK userVisitPK, CreateBirthdayFormatDescriptionForm form) {
        return CDI.current().select(CreateBirthdayFormatDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getBirthdayFormatDescription(UserVisitPK userVisitPK, GetBirthdayFormatDescriptionForm form) {
        return CDI.current().select(GetBirthdayFormatDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getBirthdayFormatDescriptions(UserVisitPK userVisitPK, GetBirthdayFormatDescriptionsForm form) {
        return CDI.current().select(GetBirthdayFormatDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editBirthdayFormatDescription(UserVisitPK userVisitPK, EditBirthdayFormatDescriptionForm form) {
        return CDI.current().select(EditBirthdayFormatDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteBirthdayFormatDescription(UserVisitPK userVisitPK, DeleteBirthdayFormatDescriptionForm form) {
        return CDI.current().select(DeleteBirthdayFormatDescriptionCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Profiles
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createProfile(UserVisitPK userVisitPK, CreateProfileForm form) {
        return CDI.current().select(CreateProfileCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editProfile(UserVisitPK userVisitPK, EditProfileForm form) {
        return CDI.current().select(EditProfileCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Party Alias Types
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createPartyAliasType(UserVisitPK userVisitPK, CreatePartyAliasTypeForm form) {
        return CDI.current().select(CreatePartyAliasTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPartyAliasTypeChoices(UserVisitPK userVisitPK, GetPartyAliasTypeChoicesForm form) {
        return CDI.current().select(GetPartyAliasTypeChoicesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPartyAliasType(UserVisitPK userVisitPK, GetPartyAliasTypeForm form) {
        return CDI.current().select(GetPartyAliasTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPartyAliasTypes(UserVisitPK userVisitPK, GetPartyAliasTypesForm form) {
        return CDI.current().select(GetPartyAliasTypesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultPartyAliasType(UserVisitPK userVisitPK, SetDefaultPartyAliasTypeForm form) {
        return CDI.current().select(SetDefaultPartyAliasTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editPartyAliasType(UserVisitPK userVisitPK, EditPartyAliasTypeForm form) {
        return CDI.current().select(EditPartyAliasTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deletePartyAliasType(UserVisitPK userVisitPK, DeletePartyAliasTypeForm form) {
        return CDI.current().select(DeletePartyAliasTypeCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Party Alias Type Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createPartyAliasTypeDescription(UserVisitPK userVisitPK, CreatePartyAliasTypeDescriptionForm form) {
        return CDI.current().select(CreatePartyAliasTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPartyAliasTypeDescription(UserVisitPK userVisitPK, GetPartyAliasTypeDescriptionForm form) {
        return CDI.current().select(GetPartyAliasTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPartyAliasTypeDescriptions(UserVisitPK userVisitPK, GetPartyAliasTypeDescriptionsForm form) {
        return CDI.current().select(GetPartyAliasTypeDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editPartyAliasTypeDescription(UserVisitPK userVisitPK, EditPartyAliasTypeDescriptionForm form) {
        return CDI.current().select(EditPartyAliasTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deletePartyAliasTypeDescription(UserVisitPK userVisitPK, DeletePartyAliasTypeDescriptionForm form) {
        return CDI.current().select(DeletePartyAliasTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Party Aliases
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createPartyAlias(UserVisitPK userVisitPK, CreatePartyAliasForm form) {
        return CDI.current().select(CreatePartyAliasCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPartyAlias(UserVisitPK userVisitPK, GetPartyAliasForm form) {
        return CDI.current().select(GetPartyAliasCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPartyAliases(UserVisitPK userVisitPK, GetPartyAliasesForm form) {
        return CDI.current().select(GetPartyAliasesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editPartyAlias(UserVisitPK userVisitPK, EditPartyAliasForm form) {
        return CDI.current().select(EditPartyAliasCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deletePartyAlias(UserVisitPK userVisitPK, DeletePartyAliasForm form) {
        return CDI.current().select(DeletePartyAliasCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Parties
    // -------------------------------------------------------------------------

    @Override
    public CommandResult getParty(UserVisitPK userVisitPK, GetPartyForm form) {
        return CDI.current().select(GetPartyCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getParties(UserVisitPK userVisitPK, GetPartiesForm form) {
        return CDI.current().select(GetPartiesCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Party Entity Types
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createPartyEntityType(UserVisitPK userVisitPK, CreatePartyEntityTypeForm form) {
        return CDI.current().select(CreatePartyEntityTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editPartyEntityType(UserVisitPK userVisitPK, EditPartyEntityTypeForm form) {
        return CDI.current().select(EditPartyEntityTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPartyEntityType(UserVisitPK userVisitPK, GetPartyEntityTypeForm form) {
        return CDI.current().select(GetPartyEntityTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPartyEntityTypes(UserVisitPK userVisitPK, GetPartyEntityTypesForm form) {
        return CDI.current().select(GetPartyEntityTypesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deletePartyEntityType(UserVisitPK userVisitPK, DeletePartyEntityTypeForm form) {
        return CDI.current().select(DeletePartyEntityTypeCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Party Application Editor Uses
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createPartyApplicationEditorUse(UserVisitPK userVisitPK, CreatePartyApplicationEditorUseForm form) {
        return CDI.current().select(CreatePartyApplicationEditorUseCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPartyApplicationEditorUse(UserVisitPK userVisitPK, GetPartyApplicationEditorUseForm form) {
        return CDI.current().select(GetPartyApplicationEditorUseCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPartyApplicationEditorUses(UserVisitPK userVisitPK, GetPartyApplicationEditorUsesForm form) {
        return CDI.current().select(GetPartyApplicationEditorUsesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editPartyApplicationEditorUse(UserVisitPK userVisitPK, EditPartyApplicationEditorUseForm form) {
        return CDI.current().select(EditPartyApplicationEditorUseCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deletePartyApplicationEditorUse(UserVisitPK userVisitPK, DeletePartyApplicationEditorUseForm form) {
        return CDI.current().select(DeletePartyApplicationEditorUseCommand.class).get().run(userVisitPK, form);
    }

}
