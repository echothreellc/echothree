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
        return new CreatePartyTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getPartyType(UserVisitPK userVisitPK, GetPartyTypeForm form) {
        return new GetPartyTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getPartyTypes(UserVisitPK userVisitPK, GetPartyTypesForm form) {
        return new GetPartyTypesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getPartyTypeChoices(UserVisitPK userVisitPK, GetPartyTypeChoicesForm form) {
        return new GetPartyTypeChoicesCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Party Type Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createPartyTypeDescription(UserVisitPK userVisitPK, CreatePartyTypeDescriptionForm form) {
        return new CreatePartyTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Party Type Use Types
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createPartyTypeUseType(UserVisitPK userVisitPK, CreatePartyTypeUseTypeForm form) {
        return new CreatePartyTypeUseTypeCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Party Type Use Type Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createPartyTypeUseTypeDescription(UserVisitPK userVisitPK, CreatePartyTypeUseTypeDescriptionForm form) {
        return new CreatePartyTypeUseTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Party Type Uses
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createPartyTypeUse(UserVisitPK userVisitPK, CreatePartyTypeUseForm form) {
        return new CreatePartyTypeUseCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Languages
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createLanguage(UserVisitPK userVisitPK, CreateLanguageForm form) {
        return new CreateLanguageCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getLanguageChoices(UserVisitPK userVisitPK, GetLanguageChoicesForm form) {
        return new GetLanguageChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getLanguages(UserVisitPK userVisitPK, GetLanguagesForm form) {
        return new GetLanguagesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getLanguage(UserVisitPK userVisitPK, GetLanguageForm form) {
        return new GetLanguageCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getPreferredLanguage(UserVisitPK userVisitPK, GetPreferredLanguageForm form) {
        return new GetPreferredLanguageCommand(userVisitPK, form).run();
    }

    // -------------------------------------------------------------------------
    //   Language Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createLanguageDescription(UserVisitPK userVisitPK, CreateLanguageDescriptionForm form) {
        return new CreateLanguageDescriptionCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Party Type Password String Policies
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createPartyTypePasswordStringPolicy(UserVisitPK userVisitPK, CreatePartyTypePasswordStringPolicyForm form) {
        return new CreatePartyTypePasswordStringPolicyCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editPartyTypePasswordStringPolicy(UserVisitPK userVisitPK, EditPartyTypePasswordStringPolicyForm form) {
        return new EditPartyTypePasswordStringPolicyCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deletePartyTypePasswordStringPolicy(UserVisitPK userVisitPK, DeletePartyTypePasswordStringPolicyForm form) {
        return new DeletePartyTypePasswordStringPolicyCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Party Type Lockout Policies
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createPartyTypeLockoutPolicy(UserVisitPK userVisitPK, CreatePartyTypeLockoutPolicyForm form) {
        return new CreatePartyTypeLockoutPolicyCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editPartyTypeLockoutPolicy(UserVisitPK userVisitPK, EditPartyTypeLockoutPolicyForm form) {
        return new EditPartyTypeLockoutPolicyCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deletePartyTypeLockoutPolicy(UserVisitPK userVisitPK, DeletePartyTypeLockoutPolicyForm form) {
        return new DeletePartyTypeLockoutPolicyCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Party Type Audit Policies
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createPartyTypeAuditPolicy(UserVisitPK userVisitPK, CreatePartyTypeAuditPolicyForm form) {
        return new CreatePartyTypeAuditPolicyCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editPartyTypeAuditPolicy(UserVisitPK userVisitPK, EditPartyTypeAuditPolicyForm form) {
        return new EditPartyTypeAuditPolicyCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deletePartyTypeAuditPolicy(UserVisitPK userVisitPK, DeletePartyTypeAuditPolicyForm form) {
        return new DeletePartyTypeAuditPolicyCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Personal Titles
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createPersonalTitle(UserVisitPK userVisitPK, CreatePersonalTitleForm form) {
        return new CreatePersonalTitleCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getPersonalTitleChoices(UserVisitPK userVisitPK, GetPersonalTitleChoicesForm form) {
        return new GetPersonalTitleChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getPersonalTitles(UserVisitPK userVisitPK, GetPersonalTitlesForm form) {
        return new GetPersonalTitlesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultPersonalTitle(UserVisitPK userVisitPK, SetDefaultPersonalTitleForm form) {
        return new SetDefaultPersonalTitleCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editPersonalTitle(UserVisitPK userVisitPK, EditPersonalTitleForm form) {
        return new EditPersonalTitleCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deletePersonalTitle(UserVisitPK userVisitPK, DeletePersonalTitleForm form) {
        return new DeletePersonalTitleCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Name Suffixes
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createNameSuffix(UserVisitPK userVisitPK, CreateNameSuffixForm form) {
        return new CreateNameSuffixCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getNameSuffixChoices(UserVisitPK userVisitPK, GetNameSuffixChoicesForm form) {
        return new GetNameSuffixChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getNameSuffixes(UserVisitPK userVisitPK, GetNameSuffixesForm form) {
        return new GetNameSuffixesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultNameSuffix(UserVisitPK userVisitPK, SetDefaultNameSuffixForm form) {
        return new SetDefaultNameSuffixCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editNameSuffix(UserVisitPK userVisitPK, EditNameSuffixForm form) {
        return new EditNameSuffixCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteNameSuffix(UserVisitPK userVisitPK, DeleteNameSuffixForm form) {
        return new DeleteNameSuffixCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Date Time Formats
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createDateTimeFormat(UserVisitPK userVisitPK, CreateDateTimeFormatForm form) {
        return new CreateDateTimeFormatCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getDateTimeFormatChoices(UserVisitPK userVisitPK, GetDateTimeFormatChoicesForm form) {
        return new GetDateTimeFormatChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getDateTimeFormat(UserVisitPK userVisitPK, GetDateTimeFormatForm form) {
        return new GetDateTimeFormatCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getPreferredDateTimeFormat(UserVisitPK userVisitPK, GetPreferredDateTimeFormatForm form) {
        return new GetPreferredDateTimeFormatCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getDateTimeFormats(UserVisitPK userVisitPK, GetDateTimeFormatsForm form) {
        return new GetDateTimeFormatsCommand(userVisitPK, form).run();
    }

    // -------------------------------------------------------------------------
    //   Date Time Format Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createDateTimeFormatDescription(UserVisitPK userVisitPK, CreateDateTimeFormatDescriptionForm form) {
        return new CreateDateTimeFormatDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getDateTimeFormatDescriptions(UserVisitPK userVisitPK, GetDateTimeFormatDescriptionsForm form) {
        return new GetDateTimeFormatDescriptionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editDateTimeFormatDescription(UserVisitPK userVisitPK, EditDateTimeFormatDescriptionForm form) {
        return new EditDateTimeFormatDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteDateTimeFormatDescription(UserVisitPK userVisitPK, DeleteDateTimeFormatDescriptionForm form) {
        return new DeleteDateTimeFormatDescriptionCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Time Zones
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createTimeZone(UserVisitPK userVisitPK, CreateTimeZoneForm form) {
        return new CreateTimeZoneCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getTimeZoneChoices(UserVisitPK userVisitPK, GetTimeZoneChoicesForm form) {
        return new GetTimeZoneChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getTimeZone(UserVisitPK userVisitPK, GetTimeZoneForm form) {
        return new GetTimeZoneCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getPreferredTimeZone(UserVisitPK userVisitPK, GetPreferredTimeZoneForm form) {
        return new GetPreferredTimeZoneCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getTimeZones(UserVisitPK userVisitPK, GetTimeZonesForm form) {
        return new GetTimeZonesCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Time Zone Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createTimeZoneDescription(UserVisitPK userVisitPK, CreateTimeZoneDescriptionForm form) {
        return new CreateTimeZoneDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getTimeZoneDescriptions(UserVisitPK userVisitPK, GetTimeZoneDescriptionsForm form) {
        return new GetTimeZoneDescriptionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editTimeZoneDescription(UserVisitPK userVisitPK, EditTimeZoneDescriptionForm form) {
        return new EditTimeZoneDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteTimeZoneDescription(UserVisitPK userVisitPK, DeleteTimeZoneDescriptionForm form) {
        return new DeleteTimeZoneDescriptionCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Companies
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCompany(UserVisitPK userVisitPK, CreateCompanyForm form) {
        return new CreateCompanyCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getCompanyChoices(UserVisitPK userVisitPK, GetCompanyChoicesForm form) {
        return new GetCompanyChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getCompanies(UserVisitPK userVisitPK, GetCompaniesForm form) {
        return new GetCompaniesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getCompany(UserVisitPK userVisitPK, GetCompanyForm form) {
        return new GetCompanyCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultCompany(UserVisitPK userVisitPK, SetDefaultCompanyForm form) {
        return new SetDefaultCompanyCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editCompany(UserVisitPK userVisitPK, EditCompanyForm form) {
        return new EditCompanyCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteCompany(UserVisitPK userVisitPK, DeleteCompanyForm form) {
        return new DeleteCompanyCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Divisions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createDivision(UserVisitPK userVisitPK, CreateDivisionForm form) {
        return new CreateDivisionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getDivisionChoices(UserVisitPK userVisitPK, GetDivisionChoicesForm form) {
        return new GetDivisionChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getDivisions(UserVisitPK userVisitPK, GetDivisionsForm form) {
        return new GetDivisionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getDivision(UserVisitPK userVisitPK, GetDivisionForm form) {
        return new GetDivisionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultDivision(UserVisitPK userVisitPK, SetDefaultDivisionForm form) {
        return new SetDefaultDivisionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editDivision(UserVisitPK userVisitPK, EditDivisionForm form) {
        return new EditDivisionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteDivision(UserVisitPK userVisitPK, DeleteDivisionForm form) {
        return new DeleteDivisionCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Departments
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createDepartment(UserVisitPK userVisitPK, CreateDepartmentForm form) {
        return new CreateDepartmentCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getDepartmentChoices(UserVisitPK userVisitPK, GetDepartmentChoicesForm form) {
        return new GetDepartmentChoicesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getDepartments(UserVisitPK userVisitPK, GetDepartmentsForm form) {
        return new GetDepartmentsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getDepartment(UserVisitPK userVisitPK, GetDepartmentForm form) {
        return new GetDepartmentCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultDepartment(UserVisitPK userVisitPK, SetDefaultDepartmentForm form) {
        return new SetDefaultDepartmentCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editDepartment(UserVisitPK userVisitPK, EditDepartmentForm form) {
        return new EditDepartmentCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteDepartment(UserVisitPK userVisitPK, DeleteDepartmentForm form) {
        return new DeleteDepartmentCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Party Relationship Types
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createPartyRelationshipType(UserVisitPK userVisitPK, CreatePartyRelationshipTypeForm form) {
        return new CreatePartyRelationshipTypeCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Party Relationship Type Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createPartyRelationshipTypeDescription(UserVisitPK userVisitPK, CreatePartyRelationshipTypeDescriptionForm form) {
        return new CreatePartyRelationshipTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Party Relationships
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult getPartyRelationships(UserVisitPK userVisitPK, GetPartyRelationshipsForm form) {
        return new GetPartyRelationshipsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getPartyRelationship(UserVisitPK userVisitPK, GetPartyRelationshipForm form) {
        return new GetPartyRelationshipCommand(userVisitPK, form).run();
    }

    // -------------------------------------------------------------------------
    //   Role Types
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createRoleType(UserVisitPK userVisitPK, CreateRoleTypeForm form) {
        return new CreateRoleTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getRoleType(UserVisitPK userVisitPK, GetRoleTypeForm form) {
        return new GetRoleTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getRoleTypes(UserVisitPK userVisitPK, GetRoleTypesForm form) {
        return new GetRoleTypesCommand(userVisitPK, form).run();
    }

    // -------------------------------------------------------------------------
    //   Role Type Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createRoleTypeDescription(UserVisitPK userVisitPK, CreateRoleTypeDescriptionForm form) {
        return new CreateRoleTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Customers
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCustomer(UserVisitPK userVisitPK, CreateCustomerForm form) {
        return new CreateCustomerCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult createCustomerWithLogin(UserVisitPK userVisitPK, CreateCustomerWithLoginForm form) {
        return new CreateCustomerWithLoginCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Employees
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createEmployee(UserVisitPK userVisitPK, CreateEmployeeForm form) {
        return new CreateEmployeeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult addEmployeeToCompany(UserVisitPK userVisitPK, AddEmployeeToCompanyForm form) {
        return new AddEmployeeToCompanyCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult addEmployeeToDivision(UserVisitPK userVisitPK, AddEmployeeToDivisionForm form) {
        return new AddEmployeeToDivisionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult addEmployeeToDepartment(UserVisitPK userVisitPK, AddEmployeeToDepartmentForm form) {
        return new AddEmployeeToDepartmentCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult removeEmployeeFromCompany(UserVisitPK userVisitPK, RemoveEmployeeFromCompanyForm form) {
        return new RemoveEmployeeFromCompanyCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult removeEmployeeFromDivision(UserVisitPK userVisitPK, RemoveEmployeeFromDivisionForm form) {
        return new RemoveEmployeeFromDivisionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult removeEmployeeFromDepartment(UserVisitPK userVisitPK, RemoveEmployeeFromDepartmentForm form) {
        return new RemoveEmployeFromDepartmentCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getEmployeeStatusChoices(UserVisitPK userVisitPK, GetEmployeeStatusChoicesForm form) {
        return new GetEmployeeStatusChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setEmployeeStatus(UserVisitPK userVisitPK, SetEmployeeStatusForm form) {
        return new SetEmployeeStatusCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getEmployeeAvailabilityChoices(UserVisitPK userVisitPK, GetEmployeeAvailabilityChoicesForm form) {
        return new GetEmployeeAvailabilityChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setEmployeeAvailability(UserVisitPK userVisitPK, SetEmployeeAvailabilityForm form) {
        return new SetEmployeeAvailabilityCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editEmployee(UserVisitPK userVisitPK, EditEmployeeForm form) {
        return new EditEmployeeCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Vendors
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createVendor(UserVisitPK userVisitPK, CreateVendorForm form) {
        return new CreateVendorCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getVendorStatusChoices(UserVisitPK userVisitPK, GetVendorStatusChoicesForm form) {
        return new GetVendorStatusChoicesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult setVendorStatus(UserVisitPK userVisitPK, SetVendorStatusForm form) {
        return new SetVendorStatusCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Genders
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createGender(UserVisitPK userVisitPK, CreateGenderForm form) {
        return new CreateGenderCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getGenderChoices(UserVisitPK userVisitPK, GetGenderChoicesForm form) {
        return new GetGenderChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getGender(UserVisitPK userVisitPK, GetGenderForm form) {
        return new GetGenderCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getGenders(UserVisitPK userVisitPK, GetGendersForm form) {
        return new GetGendersCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultGender(UserVisitPK userVisitPK, SetDefaultGenderForm form) {
        return new SetDefaultGenderCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editGender(UserVisitPK userVisitPK, EditGenderForm form) {
        return new EditGenderCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteGender(UserVisitPK userVisitPK, DeleteGenderForm form) {
        return new DeleteGenderCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Gender Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createGenderDescription(UserVisitPK userVisitPK, CreateGenderDescriptionForm form) {
        return new CreateGenderDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getGenderDescriptions(UserVisitPK userVisitPK, GetGenderDescriptionsForm form) {
        return new GetGenderDescriptionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editGenderDescription(UserVisitPK userVisitPK, EditGenderDescriptionForm form) {
        return new EditGenderDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteGenderDescription(UserVisitPK userVisitPK, DeleteGenderDescriptionForm form) {
        return new DeleteGenderDescriptionCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Moods
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createMood(UserVisitPK userVisitPK, CreateMoodForm form) {
        return new CreateMoodCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getMoodChoices(UserVisitPK userVisitPK, GetMoodChoicesForm form) {
        return new GetMoodChoicesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getMood(UserVisitPK userVisitPK, GetMoodForm form) {
        return new GetMoodCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getMoods(UserVisitPK userVisitPK, GetMoodsForm form) {
        return new GetMoodsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult setDefaultMood(UserVisitPK userVisitPK, SetDefaultMoodForm form) {
        return new SetDefaultMoodCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editMood(UserVisitPK userVisitPK, EditMoodForm form) {
        return new EditMoodCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteMood(UserVisitPK userVisitPK, DeleteMoodForm form) {
        return new DeleteMoodCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Mood Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createMoodDescription(UserVisitPK userVisitPK, CreateMoodDescriptionForm form) {
        return new CreateMoodDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getMoodDescriptions(UserVisitPK userVisitPK, GetMoodDescriptionsForm form) {
        return new GetMoodDescriptionsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editMoodDescription(UserVisitPK userVisitPK, EditMoodDescriptionForm form) {
        return new EditMoodDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteMoodDescription(UserVisitPK userVisitPK, DeleteMoodDescriptionForm form) {
        return new DeleteMoodDescriptionCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Birthday Formats
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createBirthdayFormat(UserVisitPK userVisitPK, CreateBirthdayFormatForm form) {
        return new CreateBirthdayFormatCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getBirthdayFormatChoices(UserVisitPK userVisitPK, GetBirthdayFormatChoicesForm form) {
        return new GetBirthdayFormatChoicesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getBirthdayFormat(UserVisitPK userVisitPK, GetBirthdayFormatForm form) {
        return new GetBirthdayFormatCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getBirthdayFormats(UserVisitPK userVisitPK, GetBirthdayFormatsForm form) {
        return new GetBirthdayFormatsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult setDefaultBirthdayFormat(UserVisitPK userVisitPK, SetDefaultBirthdayFormatForm form) {
        return new SetDefaultBirthdayFormatCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editBirthdayFormat(UserVisitPK userVisitPK, EditBirthdayFormatForm form) {
        return new EditBirthdayFormatCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteBirthdayFormat(UserVisitPK userVisitPK, DeleteBirthdayFormatForm form) {
        return new DeleteBirthdayFormatCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Birthday Format Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createBirthdayFormatDescription(UserVisitPK userVisitPK, CreateBirthdayFormatDescriptionForm form) {
        return new CreateBirthdayFormatDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getBirthdayFormatDescription(UserVisitPK userVisitPK, GetBirthdayFormatDescriptionForm form) {
        return new GetBirthdayFormatDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getBirthdayFormatDescriptions(UserVisitPK userVisitPK, GetBirthdayFormatDescriptionsForm form) {
        return new GetBirthdayFormatDescriptionsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editBirthdayFormatDescription(UserVisitPK userVisitPK, EditBirthdayFormatDescriptionForm form) {
        return new EditBirthdayFormatDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteBirthdayFormatDescription(UserVisitPK userVisitPK, DeleteBirthdayFormatDescriptionForm form) {
        return new DeleteBirthdayFormatDescriptionCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Profiles
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createProfile(UserVisitPK userVisitPK, CreateProfileForm form) {
        return new CreateProfileCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editProfile(UserVisitPK userVisitPK, EditProfileForm form) {
        return new EditProfileCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Party Alias Types
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createPartyAliasType(UserVisitPK userVisitPK, CreatePartyAliasTypeForm form) {
        return new CreatePartyAliasTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getPartyAliasTypeChoices(UserVisitPK userVisitPK, GetPartyAliasTypeChoicesForm form) {
        return new GetPartyAliasTypeChoicesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getPartyAliasType(UserVisitPK userVisitPK, GetPartyAliasTypeForm form) {
        return new GetPartyAliasTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getPartyAliasTypes(UserVisitPK userVisitPK, GetPartyAliasTypesForm form) {
        return new GetPartyAliasTypesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult setDefaultPartyAliasType(UserVisitPK userVisitPK, SetDefaultPartyAliasTypeForm form) {
        return new SetDefaultPartyAliasTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editPartyAliasType(UserVisitPK userVisitPK, EditPartyAliasTypeForm form) {
        return new EditPartyAliasTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deletePartyAliasType(UserVisitPK userVisitPK, DeletePartyAliasTypeForm form) {
        return new DeletePartyAliasTypeCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Party Alias Type Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createPartyAliasTypeDescription(UserVisitPK userVisitPK, CreatePartyAliasTypeDescriptionForm form) {
        return new CreatePartyAliasTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getPartyAliasTypeDescription(UserVisitPK userVisitPK, GetPartyAliasTypeDescriptionForm form) {
        return new GetPartyAliasTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getPartyAliasTypeDescriptions(UserVisitPK userVisitPK, GetPartyAliasTypeDescriptionsForm form) {
        return new GetPartyAliasTypeDescriptionsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editPartyAliasTypeDescription(UserVisitPK userVisitPK, EditPartyAliasTypeDescriptionForm form) {
        return new EditPartyAliasTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deletePartyAliasTypeDescription(UserVisitPK userVisitPK, DeletePartyAliasTypeDescriptionForm form) {
        return new DeletePartyAliasTypeDescriptionCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Party Aliases
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createPartyAlias(UserVisitPK userVisitPK, CreatePartyAliasForm form) {
        return new CreatePartyAliasCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getPartyAlias(UserVisitPK userVisitPK, GetPartyAliasForm form) {
        return new GetPartyAliasCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getPartyAliases(UserVisitPK userVisitPK, GetPartyAliasesForm form) {
        return new GetPartyAliasesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editPartyAlias(UserVisitPK userVisitPK, EditPartyAliasForm form) {
        return new EditPartyAliasCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deletePartyAlias(UserVisitPK userVisitPK, DeletePartyAliasForm form) {
        return new DeletePartyAliasCommand(userVisitPK, form).run();
    }

    // -------------------------------------------------------------------------
    //   Parties
    // -------------------------------------------------------------------------

    @Override
    public CommandResult getParty(UserVisitPK userVisitPK, GetPartyForm form) {
        return new GetPartyCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getParties(UserVisitPK userVisitPK, GetPartiesForm form) {
        return new GetPartiesCommand(userVisitPK, form).run();
    }

    // -------------------------------------------------------------------------
    //   Party Entity Types
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createPartyEntityType(UserVisitPK userVisitPK, CreatePartyEntityTypeForm form) {
        return new CreatePartyEntityTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editPartyEntityType(UserVisitPK userVisitPK, EditPartyEntityTypeForm form) {
        return new EditPartyEntityTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getPartyEntityType(UserVisitPK userVisitPK, GetPartyEntityTypeForm form) {
        return new GetPartyEntityTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getPartyEntityTypes(UserVisitPK userVisitPK, GetPartyEntityTypesForm form) {
        return new GetPartyEntityTypesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deletePartyEntityType(UserVisitPK userVisitPK, DeletePartyEntityTypeForm form) {
        return new DeletePartyEntityTypeCommand(userVisitPK, form).run();
    }

}
