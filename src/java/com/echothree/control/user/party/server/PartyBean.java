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

package com.echothree.control.user.party.server;

import com.echothree.control.user.party.common.PartyRemote;
import com.echothree.control.user.party.common.form.*;
import com.echothree.control.user.party.server.command.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import javax.ejb.Stateless;

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
        return new CreatePartyTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getPartyType(UserVisitPK userVisitPK, GetPartyTypeForm form) {
        return new GetPartyTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getPartyTypes(UserVisitPK userVisitPK, GetPartyTypesForm form) {
        return new GetPartyTypesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getPartyTypeChoices(UserVisitPK userVisitPK, GetPartyTypeChoicesForm form) {
        return new GetPartyTypeChoicesCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Party Type Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createPartyTypeDescription(UserVisitPK userVisitPK, CreatePartyTypeDescriptionForm form) {
        return new CreatePartyTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Party Type Use Types
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createPartyTypeUseType(UserVisitPK userVisitPK, CreatePartyTypeUseTypeForm form) {
        return new CreatePartyTypeUseTypeCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Party Type Use Type Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createPartyTypeUseTypeDescription(UserVisitPK userVisitPK, CreatePartyTypeUseTypeDescriptionForm form) {
        return new CreatePartyTypeUseTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Party Type Uses
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createPartyTypeUse(UserVisitPK userVisitPK, CreatePartyTypeUseForm form) {
        return new CreatePartyTypeUseCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Languages
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createLanguage(UserVisitPK userVisitPK, CreateLanguageForm form) {
        return new CreateLanguageCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getLanguageChoices(UserVisitPK userVisitPK, GetLanguageChoicesForm form) {
        return new GetLanguageChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getLanguages(UserVisitPK userVisitPK, GetLanguagesForm form) {
        return new GetLanguagesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getLanguage(UserVisitPK userVisitPK, GetLanguageForm form) {
        return new GetLanguageCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPreferredLanguage(UserVisitPK userVisitPK, GetPreferredLanguageForm form) {
        return new GetPreferredLanguageCommand().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Language Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createLanguageDescription(UserVisitPK userVisitPK, CreateLanguageDescriptionForm form) {
        return new CreateLanguageDescriptionCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Party Type Password String Policies
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createPartyTypePasswordStringPolicy(UserVisitPK userVisitPK, CreatePartyTypePasswordStringPolicyForm form) {
        return new CreatePartyTypePasswordStringPolicyCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editPartyTypePasswordStringPolicy(UserVisitPK userVisitPK, EditPartyTypePasswordStringPolicyForm form) {
        return new EditPartyTypePasswordStringPolicyCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deletePartyTypePasswordStringPolicy(UserVisitPK userVisitPK, DeletePartyTypePasswordStringPolicyForm form) {
        return new DeletePartyTypePasswordStringPolicyCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Party Type Lockout Policies
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createPartyTypeLockoutPolicy(UserVisitPK userVisitPK, CreatePartyTypeLockoutPolicyForm form) {
        return new CreatePartyTypeLockoutPolicyCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editPartyTypeLockoutPolicy(UserVisitPK userVisitPK, EditPartyTypeLockoutPolicyForm form) {
        return new EditPartyTypeLockoutPolicyCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deletePartyTypeLockoutPolicy(UserVisitPK userVisitPK, DeletePartyTypeLockoutPolicyForm form) {
        return new DeletePartyTypeLockoutPolicyCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Party Type Audit Policies
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createPartyTypeAuditPolicy(UserVisitPK userVisitPK, CreatePartyTypeAuditPolicyForm form) {
        return new CreatePartyTypeAuditPolicyCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editPartyTypeAuditPolicy(UserVisitPK userVisitPK, EditPartyTypeAuditPolicyForm form) {
        return new EditPartyTypeAuditPolicyCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deletePartyTypeAuditPolicy(UserVisitPK userVisitPK, DeletePartyTypeAuditPolicyForm form) {
        return new DeletePartyTypeAuditPolicyCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Personal Titles
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createPersonalTitle(UserVisitPK userVisitPK, CreatePersonalTitleForm form) {
        return new CreatePersonalTitleCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getPersonalTitleChoices(UserVisitPK userVisitPK, GetPersonalTitleChoicesForm form) {
        return new GetPersonalTitleChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getPersonalTitles(UserVisitPK userVisitPK, GetPersonalTitlesForm form) {
        return new GetPersonalTitlesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultPersonalTitle(UserVisitPK userVisitPK, SetDefaultPersonalTitleForm form) {
        return new SetDefaultPersonalTitleCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editPersonalTitle(UserVisitPK userVisitPK, EditPersonalTitleForm form) {
        return new EditPersonalTitleCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deletePersonalTitle(UserVisitPK userVisitPK, DeletePersonalTitleForm form) {
        return new DeletePersonalTitleCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Name Suffixes
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createNameSuffix(UserVisitPK userVisitPK, CreateNameSuffixForm form) {
        return new CreateNameSuffixCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getNameSuffixChoices(UserVisitPK userVisitPK, GetNameSuffixChoicesForm form) {
        return new GetNameSuffixChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getNameSuffixes(UserVisitPK userVisitPK, GetNameSuffixesForm form) {
        return new GetNameSuffixesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultNameSuffix(UserVisitPK userVisitPK, SetDefaultNameSuffixForm form) {
        return new SetDefaultNameSuffixCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editNameSuffix(UserVisitPK userVisitPK, EditNameSuffixForm form) {
        return new EditNameSuffixCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteNameSuffix(UserVisitPK userVisitPK, DeleteNameSuffixForm form) {
        return new DeleteNameSuffixCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Date Time Formats
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createDateTimeFormat(UserVisitPK userVisitPK, CreateDateTimeFormatForm form) {
        return new CreateDateTimeFormatCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getDateTimeFormatChoices(UserVisitPK userVisitPK, GetDateTimeFormatChoicesForm form) {
        return new GetDateTimeFormatChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getDateTimeFormat(UserVisitPK userVisitPK, GetDateTimeFormatForm form) {
        return new GetDateTimeFormatCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPreferredDateTimeFormat(UserVisitPK userVisitPK, GetPreferredDateTimeFormatForm form) {
        return new GetPreferredDateTimeFormatCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getDateTimeFormats(UserVisitPK userVisitPK, GetDateTimeFormatsForm form) {
        return new GetDateTimeFormatsCommand().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Date Time Format Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createDateTimeFormatDescription(UserVisitPK userVisitPK, CreateDateTimeFormatDescriptionForm form) {
        return new CreateDateTimeFormatDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getDateTimeFormatDescriptions(UserVisitPK userVisitPK, GetDateTimeFormatDescriptionsForm form) {
        return new GetDateTimeFormatDescriptionsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editDateTimeFormatDescription(UserVisitPK userVisitPK, EditDateTimeFormatDescriptionForm form) {
        return new EditDateTimeFormatDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteDateTimeFormatDescription(UserVisitPK userVisitPK, DeleteDateTimeFormatDescriptionForm form) {
        return new DeleteDateTimeFormatDescriptionCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Time Zones
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createTimeZone(UserVisitPK userVisitPK, CreateTimeZoneForm form) {
        return new CreateTimeZoneCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTimeZoneChoices(UserVisitPK userVisitPK, GetTimeZoneChoicesForm form) {
        return new GetTimeZoneChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTimeZone(UserVisitPK userVisitPK, GetTimeZoneForm form) {
        return new GetTimeZoneCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPreferredTimeZone(UserVisitPK userVisitPK, GetPreferredTimeZoneForm form) {
        return new GetPreferredTimeZoneCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getTimeZones(UserVisitPK userVisitPK, GetTimeZonesForm form) {
        return new GetTimeZonesCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Time Zone Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createTimeZoneDescription(UserVisitPK userVisitPK, CreateTimeZoneDescriptionForm form) {
        return new CreateTimeZoneDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTimeZoneDescriptions(UserVisitPK userVisitPK, GetTimeZoneDescriptionsForm form) {
        return new GetTimeZoneDescriptionsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editTimeZoneDescription(UserVisitPK userVisitPK, EditTimeZoneDescriptionForm form) {
        return new EditTimeZoneDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteTimeZoneDescription(UserVisitPK userVisitPK, DeleteTimeZoneDescriptionForm form) {
        return new DeleteTimeZoneDescriptionCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Companies
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCompany(UserVisitPK userVisitPK, CreateCompanyForm form) {
        return new CreateCompanyCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCompanyChoices(UserVisitPK userVisitPK, GetCompanyChoicesForm form) {
        return new GetCompanyChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCompanies(UserVisitPK userVisitPK, GetCompaniesForm form) {
        return new GetCompaniesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCompany(UserVisitPK userVisitPK, GetCompanyForm form) {
        return new GetCompanyCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultCompany(UserVisitPK userVisitPK, SetDefaultCompanyForm form) {
        return new SetDefaultCompanyCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editCompany(UserVisitPK userVisitPK, EditCompanyForm form) {
        return new EditCompanyCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteCompany(UserVisitPK userVisitPK, DeleteCompanyForm form) {
        return new DeleteCompanyCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Divisions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createDivision(UserVisitPK userVisitPK, CreateDivisionForm form) {
        return new CreateDivisionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getDivisionChoices(UserVisitPK userVisitPK, GetDivisionChoicesForm form) {
        return new GetDivisionChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getDivisions(UserVisitPK userVisitPK, GetDivisionsForm form) {
        return new GetDivisionsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getDivision(UserVisitPK userVisitPK, GetDivisionForm form) {
        return new GetDivisionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultDivision(UserVisitPK userVisitPK, SetDefaultDivisionForm form) {
        return new SetDefaultDivisionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editDivision(UserVisitPK userVisitPK, EditDivisionForm form) {
        return new EditDivisionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteDivision(UserVisitPK userVisitPK, DeleteDivisionForm form) {
        return new DeleteDivisionCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Departments
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createDepartment(UserVisitPK userVisitPK, CreateDepartmentForm form) {
        return new CreateDepartmentCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getDepartmentChoices(UserVisitPK userVisitPK, GetDepartmentChoicesForm form) {
        return new GetDepartmentChoicesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getDepartments(UserVisitPK userVisitPK, GetDepartmentsForm form) {
        return new GetDepartmentsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getDepartment(UserVisitPK userVisitPK, GetDepartmentForm form) {
        return new GetDepartmentCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultDepartment(UserVisitPK userVisitPK, SetDefaultDepartmentForm form) {
        return new SetDefaultDepartmentCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editDepartment(UserVisitPK userVisitPK, EditDepartmentForm form) {
        return new EditDepartmentCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteDepartment(UserVisitPK userVisitPK, DeleteDepartmentForm form) {
        return new DeleteDepartmentCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Party Relationship Types
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createPartyRelationshipType(UserVisitPK userVisitPK, CreatePartyRelationshipTypeForm form) {
        return new CreatePartyRelationshipTypeCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Party Relationship Type Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createPartyRelationshipTypeDescription(UserVisitPK userVisitPK, CreatePartyRelationshipTypeDescriptionForm form) {
        return new CreatePartyRelationshipTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Party Relationships
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult getPartyRelationships(UserVisitPK userVisitPK, GetPartyRelationshipsForm form) {
        return new GetPartyRelationshipsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPartyRelationship(UserVisitPK userVisitPK, GetPartyRelationshipForm form) {
        return new GetPartyRelationshipCommand().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Role Types
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createRoleType(UserVisitPK userVisitPK, CreateRoleTypeForm form) {
        return new CreateRoleTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getRoleType(UserVisitPK userVisitPK, GetRoleTypeForm form) {
        return new GetRoleTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getRoleTypes(UserVisitPK userVisitPK, GetRoleTypesForm form) {
        return new GetRoleTypesCommand().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Role Type Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createRoleTypeDescription(UserVisitPK userVisitPK, CreateRoleTypeDescriptionForm form) {
        return new CreateRoleTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Customers
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCustomer(UserVisitPK userVisitPK, CreateCustomerForm form) {
        return new CreateCustomerCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult createCustomerWithLogin(UserVisitPK userVisitPK, CreateCustomerWithLoginForm form) {
        return new CreateCustomerWithLoginCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Employees
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createEmployee(UserVisitPK userVisitPK, CreateEmployeeForm form) {
        return new CreateEmployeeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult addEmployeeToCompany(UserVisitPK userVisitPK, AddEmployeeToCompanyForm form) {
        return new AddEmployeeToCompanyCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult addEmployeeToDivision(UserVisitPK userVisitPK, AddEmployeeToDivisionForm form) {
        return new AddEmployeeToDivisionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult addEmployeeToDepartment(UserVisitPK userVisitPK, AddEmployeeToDepartmentForm form) {
        return new AddEmployeeToDepartmentCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult removeEmployeeFromCompany(UserVisitPK userVisitPK, RemoveEmployeeFromCompanyForm form) {
        return new RemoveEmployeeFromCompanyCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult removeEmployeeFromDivision(UserVisitPK userVisitPK, RemoveEmployeeFromDivisionForm form) {
        return new RemoveEmployeeFromDivisionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult removeEmployeeFromDepartment(UserVisitPK userVisitPK, RemoveEmployeeFromDepartmentForm form) {
        return new RemoveEmployeFromDepartmentCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getEmployeeStatusChoices(UserVisitPK userVisitPK, GetEmployeeStatusChoicesForm form) {
        return new GetEmployeeStatusChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setEmployeeStatus(UserVisitPK userVisitPK, SetEmployeeStatusForm form) {
        return new SetEmployeeStatusCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getEmployeeAvailabilityChoices(UserVisitPK userVisitPK, GetEmployeeAvailabilityChoicesForm form) {
        return new GetEmployeeAvailabilityChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setEmployeeAvailability(UserVisitPK userVisitPK, SetEmployeeAvailabilityForm form) {
        return new SetEmployeeAvailabilityCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editEmployee(UserVisitPK userVisitPK, EditEmployeeForm form) {
        return new EditEmployeeCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Vendors
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createVendor(UserVisitPK userVisitPK, CreateVendorForm form) {
        return new CreateVendorCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getVendorStatusChoices(UserVisitPK userVisitPK, GetVendorStatusChoicesForm form) {
        return new GetVendorStatusChoicesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult setVendorStatus(UserVisitPK userVisitPK, SetVendorStatusForm form) {
        return new SetVendorStatusCommand().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Genders
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createGender(UserVisitPK userVisitPK, CreateGenderForm form) {
        return new CreateGenderCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getGenderChoices(UserVisitPK userVisitPK, GetGenderChoicesForm form) {
        return new GetGenderChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getGender(UserVisitPK userVisitPK, GetGenderForm form) {
        return new GetGenderCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getGenders(UserVisitPK userVisitPK, GetGendersForm form) {
        return new GetGendersCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultGender(UserVisitPK userVisitPK, SetDefaultGenderForm form) {
        return new SetDefaultGenderCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editGender(UserVisitPK userVisitPK, EditGenderForm form) {
        return new EditGenderCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteGender(UserVisitPK userVisitPK, DeleteGenderForm form) {
        return new DeleteGenderCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Gender Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createGenderDescription(UserVisitPK userVisitPK, CreateGenderDescriptionForm form) {
        return new CreateGenderDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getGenderDescriptions(UserVisitPK userVisitPK, GetGenderDescriptionsForm form) {
        return new GetGenderDescriptionsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editGenderDescription(UserVisitPK userVisitPK, EditGenderDescriptionForm form) {
        return new EditGenderDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteGenderDescription(UserVisitPK userVisitPK, DeleteGenderDescriptionForm form) {
        return new DeleteGenderDescriptionCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Moods
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createMood(UserVisitPK userVisitPK, CreateMoodForm form) {
        return new CreateMoodCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getMoodChoices(UserVisitPK userVisitPK, GetMoodChoicesForm form) {
        return new GetMoodChoicesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getMood(UserVisitPK userVisitPK, GetMoodForm form) {
        return new GetMoodCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getMoods(UserVisitPK userVisitPK, GetMoodsForm form) {
        return new GetMoodsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultMood(UserVisitPK userVisitPK, SetDefaultMoodForm form) {
        return new SetDefaultMoodCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editMood(UserVisitPK userVisitPK, EditMoodForm form) {
        return new EditMoodCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteMood(UserVisitPK userVisitPK, DeleteMoodForm form) {
        return new DeleteMoodCommand().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Mood Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createMoodDescription(UserVisitPK userVisitPK, CreateMoodDescriptionForm form) {
        return new CreateMoodDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getMoodDescriptions(UserVisitPK userVisitPK, GetMoodDescriptionsForm form) {
        return new GetMoodDescriptionsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editMoodDescription(UserVisitPK userVisitPK, EditMoodDescriptionForm form) {
        return new EditMoodDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteMoodDescription(UserVisitPK userVisitPK, DeleteMoodDescriptionForm form) {
        return new DeleteMoodDescriptionCommand().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Birthday Formats
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createBirthdayFormat(UserVisitPK userVisitPK, CreateBirthdayFormatForm form) {
        return new CreateBirthdayFormatCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getBirthdayFormatChoices(UserVisitPK userVisitPK, GetBirthdayFormatChoicesForm form) {
        return new GetBirthdayFormatChoicesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getBirthdayFormat(UserVisitPK userVisitPK, GetBirthdayFormatForm form) {
        return new GetBirthdayFormatCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getBirthdayFormats(UserVisitPK userVisitPK, GetBirthdayFormatsForm form) {
        return new GetBirthdayFormatsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultBirthdayFormat(UserVisitPK userVisitPK, SetDefaultBirthdayFormatForm form) {
        return new SetDefaultBirthdayFormatCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editBirthdayFormat(UserVisitPK userVisitPK, EditBirthdayFormatForm form) {
        return new EditBirthdayFormatCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteBirthdayFormat(UserVisitPK userVisitPK, DeleteBirthdayFormatForm form) {
        return new DeleteBirthdayFormatCommand().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Birthday Format Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createBirthdayFormatDescription(UserVisitPK userVisitPK, CreateBirthdayFormatDescriptionForm form) {
        return new CreateBirthdayFormatDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getBirthdayFormatDescription(UserVisitPK userVisitPK, GetBirthdayFormatDescriptionForm form) {
        return new GetBirthdayFormatDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getBirthdayFormatDescriptions(UserVisitPK userVisitPK, GetBirthdayFormatDescriptionsForm form) {
        return new GetBirthdayFormatDescriptionsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editBirthdayFormatDescription(UserVisitPK userVisitPK, EditBirthdayFormatDescriptionForm form) {
        return new EditBirthdayFormatDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteBirthdayFormatDescription(UserVisitPK userVisitPK, DeleteBirthdayFormatDescriptionForm form) {
        return new DeleteBirthdayFormatDescriptionCommand().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Profiles
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createProfile(UserVisitPK userVisitPK, CreateProfileForm form) {
        return new CreateProfileCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editProfile(UserVisitPK userVisitPK, EditProfileForm form) {
        return new EditProfileCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Party Alias Types
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createPartyAliasType(UserVisitPK userVisitPK, CreatePartyAliasTypeForm form) {
        return new CreatePartyAliasTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPartyAliasTypeChoices(UserVisitPK userVisitPK, GetPartyAliasTypeChoicesForm form) {
        return new GetPartyAliasTypeChoicesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPartyAliasType(UserVisitPK userVisitPK, GetPartyAliasTypeForm form) {
        return new GetPartyAliasTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPartyAliasTypes(UserVisitPK userVisitPK, GetPartyAliasTypesForm form) {
        return new GetPartyAliasTypesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultPartyAliasType(UserVisitPK userVisitPK, SetDefaultPartyAliasTypeForm form) {
        return new SetDefaultPartyAliasTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editPartyAliasType(UserVisitPK userVisitPK, EditPartyAliasTypeForm form) {
        return new EditPartyAliasTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deletePartyAliasType(UserVisitPK userVisitPK, DeletePartyAliasTypeForm form) {
        return new DeletePartyAliasTypeCommand().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Party Alias Type Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createPartyAliasTypeDescription(UserVisitPK userVisitPK, CreatePartyAliasTypeDescriptionForm form) {
        return new CreatePartyAliasTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPartyAliasTypeDescription(UserVisitPK userVisitPK, GetPartyAliasTypeDescriptionForm form) {
        return new GetPartyAliasTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPartyAliasTypeDescriptions(UserVisitPK userVisitPK, GetPartyAliasTypeDescriptionsForm form) {
        return new GetPartyAliasTypeDescriptionsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editPartyAliasTypeDescription(UserVisitPK userVisitPK, EditPartyAliasTypeDescriptionForm form) {
        return new EditPartyAliasTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deletePartyAliasTypeDescription(UserVisitPK userVisitPK, DeletePartyAliasTypeDescriptionForm form) {
        return new DeletePartyAliasTypeDescriptionCommand().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Party Aliases
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createPartyAlias(UserVisitPK userVisitPK, CreatePartyAliasForm form) {
        return new CreatePartyAliasCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPartyAlias(UserVisitPK userVisitPK, GetPartyAliasForm form) {
        return new GetPartyAliasCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPartyAliases(UserVisitPK userVisitPK, GetPartyAliasesForm form) {
        return new GetPartyAliasesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editPartyAlias(UserVisitPK userVisitPK, EditPartyAliasForm form) {
        return new EditPartyAliasCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deletePartyAlias(UserVisitPK userVisitPK, DeletePartyAliasForm form) {
        return new DeletePartyAliasCommand().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Parties
    // -------------------------------------------------------------------------

    @Override
    public CommandResult getParty(UserVisitPK userVisitPK, GetPartyForm form) {
        return new GetPartyCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getParties(UserVisitPK userVisitPK, GetPartiesForm form) {
        return new GetPartiesCommand().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Party Entity Types
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createPartyEntityType(UserVisitPK userVisitPK, CreatePartyEntityTypeForm form) {
        return new CreatePartyEntityTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editPartyEntityType(UserVisitPK userVisitPK, EditPartyEntityTypeForm form) {
        return new EditPartyEntityTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPartyEntityType(UserVisitPK userVisitPK, GetPartyEntityTypeForm form) {
        return new GetPartyEntityTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPartyEntityTypes(UserVisitPK userVisitPK, GetPartyEntityTypesForm form) {
        return new GetPartyEntityTypesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deletePartyEntityType(UserVisitPK userVisitPK, DeletePartyEntityTypeForm form) {
        return new DeletePartyEntityTypeCommand().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Party Application Editor Uses
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createPartyApplicationEditorUse(UserVisitPK userVisitPK, CreatePartyApplicationEditorUseForm form) {
        return new CreatePartyApplicationEditorUseCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPartyApplicationEditorUse(UserVisitPK userVisitPK, GetPartyApplicationEditorUseForm form) {
        return new GetPartyApplicationEditorUseCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPartyApplicationEditorUses(UserVisitPK userVisitPK, GetPartyApplicationEditorUsesForm form) {
        return new GetPartyApplicationEditorUsesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editPartyApplicationEditorUse(UserVisitPK userVisitPK, EditPartyApplicationEditorUseForm form) {
        return new EditPartyApplicationEditorUseCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deletePartyApplicationEditorUse(UserVisitPK userVisitPK, DeletePartyApplicationEditorUseForm form) {
        return new DeletePartyApplicationEditorUseCommand().run(userVisitPK, form);
    }

}
