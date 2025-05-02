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

package com.echothree.control.user.party.common;

import com.echothree.control.user.party.common.form.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;

public interface PartyService
        extends PartyForms {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    String ping();
    
    // -------------------------------------------------------------------------
    //   Languages
    // -------------------------------------------------------------------------
    
    CommandResult createLanguage(UserVisitPK userVisitPK, CreateLanguageForm form);
    
    CommandResult getLanguageChoices(UserVisitPK userVisitPK, GetLanguageChoicesForm form);
    
    CommandResult getLanguages(UserVisitPK userVisitPK, GetLanguagesForm form);
    
    CommandResult getLanguage(UserVisitPK userVisitPK, GetLanguageForm form);
    
    CommandResult getPreferredLanguage(UserVisitPK userVisitPK, GetPreferredLanguageForm form);

    // -------------------------------------------------------------------------
    //   Language Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult createLanguageDescription(UserVisitPK userVisitPK, CreateLanguageDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Party Types
    // -------------------------------------------------------------------------
    
    CommandResult createPartyType(UserVisitPK userVisitPK, CreatePartyTypeForm form);
    
    CommandResult getPartyType(UserVisitPK userVisitPK, GetPartyTypeForm form);
    
    CommandResult getPartyTypes(UserVisitPK userVisitPK, GetPartyTypesForm form);
    
    CommandResult getPartyTypeChoices(UserVisitPK userVisitPK, GetPartyTypeChoicesForm form);
    
    // -------------------------------------------------------------------------
    //   Party Type Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult createPartyTypeDescription(UserVisitPK userVisitPK, CreatePartyTypeDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Party Type Use Types
    // -------------------------------------------------------------------------
    
    CommandResult createPartyTypeUseType(UserVisitPK userVisitPK, CreatePartyTypeUseTypeForm form);
    
    // -------------------------------------------------------------------------
    //   Party Type Use Type Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult createPartyTypeUseTypeDescription(UserVisitPK userVisitPK, CreatePartyTypeUseTypeDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Party Type Uses
    // -------------------------------------------------------------------------
    
    CommandResult createPartyTypeUse(UserVisitPK userVisitPK, CreatePartyTypeUseForm form);
    
    // -------------------------------------------------------------------------
    //   Party Type Password String Policies
    // -------------------------------------------------------------------------
    
    CommandResult createPartyTypePasswordStringPolicy(UserVisitPK userVisitPK, CreatePartyTypePasswordStringPolicyForm form);
    
    CommandResult editPartyTypePasswordStringPolicy(UserVisitPK userVisitPK, EditPartyTypePasswordStringPolicyForm form);
    
    CommandResult deletePartyTypePasswordStringPolicy(UserVisitPK userVisitPK, DeletePartyTypePasswordStringPolicyForm form);
    
    // -------------------------------------------------------------------------
    //   Party Type Lockout Policies
    // -------------------------------------------------------------------------
    
    CommandResult createPartyTypeLockoutPolicy(UserVisitPK userVisitPK, CreatePartyTypeLockoutPolicyForm form);
    
    CommandResult editPartyTypeLockoutPolicy(UserVisitPK userVisitPK, EditPartyTypeLockoutPolicyForm form);
    
    CommandResult deletePartyTypeLockoutPolicy(UserVisitPK userVisitPK, DeletePartyTypeLockoutPolicyForm form);
    
    // -------------------------------------------------------------------------
    //   Party Type Audit Policies
    // -------------------------------------------------------------------------
    
    CommandResult createPartyTypeAuditPolicy(UserVisitPK userVisitPK, CreatePartyTypeAuditPolicyForm form);
    
    CommandResult editPartyTypeAuditPolicy(UserVisitPK userVisitPK, EditPartyTypeAuditPolicyForm form);
    
    CommandResult deletePartyTypeAuditPolicy(UserVisitPK userVisitPK, DeletePartyTypeAuditPolicyForm form);
    
    // -------------------------------------------------------------------------
    //   Personal Titles
    // -------------------------------------------------------------------------
    
    CommandResult createPersonalTitle(UserVisitPK userVisitPK, CreatePersonalTitleForm form);
    
    CommandResult getPersonalTitleChoices(UserVisitPK userVisitPK, GetPersonalTitleChoicesForm form);
    
    CommandResult getPersonalTitles(UserVisitPK userVisitPK, GetPersonalTitlesForm form);
    
    CommandResult setDefaultPersonalTitle(UserVisitPK userVisitPK, SetDefaultPersonalTitleForm form);
    
    CommandResult editPersonalTitle(UserVisitPK userVisitPK, EditPersonalTitleForm form);
    
    CommandResult deletePersonalTitle(UserVisitPK userVisitPK, DeletePersonalTitleForm form);
    
    // -------------------------------------------------------------------------
    //   Name Suffixes
    // -------------------------------------------------------------------------
    
    CommandResult createNameSuffix(UserVisitPK userVisitPK, CreateNameSuffixForm form);
    
    CommandResult getNameSuffixChoices(UserVisitPK userVisitPK, GetNameSuffixChoicesForm form);
    
    CommandResult getNameSuffixes(UserVisitPK userVisitPK, GetNameSuffixesForm form);
    
    CommandResult setDefaultNameSuffix(UserVisitPK userVisitPK, SetDefaultNameSuffixForm form);
    
    CommandResult editNameSuffix(UserVisitPK userVisitPK, EditNameSuffixForm form);
    
    CommandResult deleteNameSuffix(UserVisitPK userVisitPK, DeleteNameSuffixForm form);
    
    // -------------------------------------------------------------------------
    //   Date Time Formats
    // -------------------------------------------------------------------------
    
    CommandResult createDateTimeFormat(UserVisitPK userVisitPK, CreateDateTimeFormatForm form);
    
    CommandResult getDateTimeFormatChoices(UserVisitPK userVisitPK, GetDateTimeFormatChoicesForm getDateTimeFormatChoicesForm);
    
    CommandResult getDateTimeFormat(UserVisitPK userVisitPK, GetDateTimeFormatForm form);

    CommandResult getPreferredDateTimeFormat(UserVisitPK userVisitPK, GetPreferredDateTimeFormatForm form);

    CommandResult getDateTimeFormats(UserVisitPK userVisitPK, GetDateTimeFormatsForm form);
    
    // -------------------------------------------------------------------------
    //   Date Time Format Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult createDateTimeFormatDescription(UserVisitPK userVisitPK, CreateDateTimeFormatDescriptionForm form);
    
    CommandResult getDateTimeFormatDescriptions(UserVisitPK userVisitPK, GetDateTimeFormatDescriptionsForm form);
    
    CommandResult editDateTimeFormatDescription(UserVisitPK userVisitPK, EditDateTimeFormatDescriptionForm form);
    
    CommandResult deleteDateTimeFormatDescription(UserVisitPK userVisitPK, DeleteDateTimeFormatDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Time Zones
    // -------------------------------------------------------------------------
    
    CommandResult createTimeZone(UserVisitPK userVisitPK, CreateTimeZoneForm form);
    
    CommandResult getTimeZoneChoices(UserVisitPK userVisitPK, GetTimeZoneChoicesForm form);
    
    CommandResult getTimeZone(UserVisitPK userVisitPK, GetTimeZoneForm form);

    CommandResult getPreferredTimeZone(UserVisitPK userVisitPK, GetPreferredTimeZoneForm form);

    CommandResult getTimeZones(UserVisitPK userVisitPK, GetTimeZonesForm form);
    
    // -------------------------------------------------------------------------
    //   Time Zone Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult createTimeZoneDescription(UserVisitPK userVisitPK, CreateTimeZoneDescriptionForm form);
    
    CommandResult getTimeZoneDescriptions(UserVisitPK userVisitPK, GetTimeZoneDescriptionsForm form);
    
    CommandResult editTimeZoneDescription(UserVisitPK userVisitPK, EditTimeZoneDescriptionForm form);
    
    CommandResult deleteTimeZoneDescription(UserVisitPK userVisitPK, DeleteTimeZoneDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Companies
    // -------------------------------------------------------------------------
    
    CommandResult createCompany(UserVisitPK userVisitPK, CreateCompanyForm form);
    
    CommandResult getCompanyChoices(UserVisitPK userVisitPK, GetCompanyChoicesForm form);
    
    CommandResult getCompanies(UserVisitPK userVisitPK, GetCompaniesForm form);
    
    CommandResult getCompany(UserVisitPK userVisitPK, GetCompanyForm form);
    
    CommandResult setDefaultCompany(UserVisitPK userVisitPK, SetDefaultCompanyForm form);
    
    CommandResult editCompany(UserVisitPK userVisitPK, EditCompanyForm form);
    
    CommandResult deleteCompany(UserVisitPK userVisitPK, DeleteCompanyForm form);
    
    // -------------------------------------------------------------------------
    //   Divisions
    // -------------------------------------------------------------------------
    
    CommandResult createDivision(UserVisitPK userVisitPK, CreateDivisionForm form);
    
    CommandResult getDivisionChoices(UserVisitPK userVisitPK, GetDivisionChoicesForm form);
    
    CommandResult getDivisions(UserVisitPK userVisitPK, GetDivisionsForm form);
    
    CommandResult getDivision(UserVisitPK userVisitPK, GetDivisionForm form);
    
    CommandResult setDefaultDivision(UserVisitPK userVisitPK, SetDefaultDivisionForm form);
    
    CommandResult editDivision(UserVisitPK userVisitPK, EditDivisionForm form);
    
    CommandResult deleteDivision(UserVisitPK userVisitPK, DeleteDivisionForm form);
    
    // -------------------------------------------------------------------------
    //   Departments
    // -------------------------------------------------------------------------
    
    CommandResult createDepartment(UserVisitPK userVisitPK, CreateDepartmentForm form);
    
    CommandResult getDepartmentChoices(UserVisitPK userVisitPK, GetDepartmentChoicesForm form);

    CommandResult getDepartments(UserVisitPK userVisitPK, GetDepartmentsForm form);
    
    CommandResult getDepartment(UserVisitPK userVisitPK, GetDepartmentForm form);
    
    CommandResult setDefaultDepartment(UserVisitPK userVisitPK, SetDefaultDepartmentForm form);
    
    CommandResult editDepartment(UserVisitPK userVisitPK, EditDepartmentForm form);
    
    CommandResult deleteDepartment(UserVisitPK userVisitPK, DeleteDepartmentForm form);
    
    // -------------------------------------------------------------------------
    //   Customers
    // -------------------------------------------------------------------------
    
    CommandResult createCustomer(UserVisitPK userVisitPK, CreateCustomerForm form);
    
    CommandResult createCustomerWithLogin(UserVisitPK userVisitPK, CreateCustomerWithLoginForm createCustomerWithLoginForm);
    
    // --------------------------------------------------------------------------------
    //   Employees
    // --------------------------------------------------------------------------------
    
    CommandResult createEmployee(UserVisitPK userVisitPK, CreateEmployeeForm form);
    
    CommandResult addEmployeeToCompany(UserVisitPK userVisitPK, AddEmployeeToCompanyForm form);

    CommandResult addEmployeeToDivision(UserVisitPK userVisitPK, AddEmployeeToDivisionForm form);

    CommandResult addEmployeeToDepartment(UserVisitPK userVisitPK, AddEmployeeToDepartmentForm form);

    CommandResult removeEmployeeFromCompany(UserVisitPK userVisitPK, RemoveEmployeeFromCompanyForm form);

    CommandResult removeEmployeeFromDivision(UserVisitPK userVisitPK, RemoveEmployeeFromDivisionForm form);

    CommandResult removeEmployeeFromDepartment(UserVisitPK userVisitPK, RemoveEmployeeFromDepartmentForm form);

    CommandResult getEmployeeStatusChoices(UserVisitPK userVisitPK, GetEmployeeStatusChoicesForm form);
    
    CommandResult setEmployeeStatus(UserVisitPK userVisitPK, SetEmployeeStatusForm form);
    
    CommandResult getEmployeeAvailabilityChoices(UserVisitPK userVisitPK, GetEmployeeAvailabilityChoicesForm form);
    
    CommandResult setEmployeeAvailability(UserVisitPK userVisitPK, SetEmployeeAvailabilityForm form);
    
    CommandResult editEmployee(UserVisitPK userVisitPK, EditEmployeeForm form);
    
    // --------------------------------------------------------------------------------
    //   Party Relationship Types
    // --------------------------------------------------------------------------------
    
    CommandResult createPartyRelationshipType(UserVisitPK userVisitPK, CreatePartyRelationshipTypeForm form);
    
    // --------------------------------------------------------------------------------
    //   Party Relationship Type Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult createPartyRelationshipTypeDescription(UserVisitPK userVisitPK, CreatePartyRelationshipTypeDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Party Relationships
    // --------------------------------------------------------------------------------

    CommandResult getPartyRelationships(UserVisitPK userVisitPK, GetPartyRelationshipsForm form);

    CommandResult getPartyRelationship(UserVisitPK userVisitPK, GetPartyRelationshipForm form);

    // --------------------------------------------------------------------------------
    //   Role Types
    // --------------------------------------------------------------------------------
    
    CommandResult createRoleType(UserVisitPK userVisitPK, CreateRoleTypeForm form);

    CommandResult getRoleType(UserVisitPK userVisitPK, GetRoleTypeForm form);

    CommandResult getRoleTypes(UserVisitPK userVisitPK, GetRoleTypesForm form);
    
    // --------------------------------------------------------------------------------
    //   Role Type Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult createRoleTypeDescription(UserVisitPK userVisitPK, CreateRoleTypeDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Vendors
    // --------------------------------------------------------------------------------
    
    CommandResult createVendor(UserVisitPK userVisitPK, CreateVendorForm form);
    
    CommandResult getVendorStatusChoices(UserVisitPK userVisitPK, GetVendorStatusChoicesForm form);

    CommandResult setVendorStatus(UserVisitPK userVisitPK, SetVendorStatusForm form);

    // --------------------------------------------------------------------------------
    //   Genders
    // --------------------------------------------------------------------------------
    
    CommandResult createGender(UserVisitPK userVisitPK, CreateGenderForm form);
    
    CommandResult getGenderChoices(UserVisitPK userVisitPK, GetGenderChoicesForm form);
    
    CommandResult getGender(UserVisitPK userVisitPK, GetGenderForm form);
    
    CommandResult getGenders(UserVisitPK userVisitPK, GetGendersForm form);
    
    CommandResult setDefaultGender(UserVisitPK userVisitPK, SetDefaultGenderForm form);
    
    CommandResult editGender(UserVisitPK userVisitPK, EditGenderForm form);
    
    CommandResult deleteGender(UserVisitPK userVisitPK, DeleteGenderForm form);
    
    // --------------------------------------------------------------------------------
    //   Gender Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult createGenderDescription(UserVisitPK userVisitPK, CreateGenderDescriptionForm form);
    
    CommandResult getGenderDescriptions(UserVisitPK userVisitPK, GetGenderDescriptionsForm form);
    
    CommandResult editGenderDescription(UserVisitPK userVisitPK, EditGenderDescriptionForm form);
    
    CommandResult deleteGenderDescription(UserVisitPK userVisitPK, DeleteGenderDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Moods
    // --------------------------------------------------------------------------------
    
    CommandResult createMood(UserVisitPK userVisitPK, CreateMoodForm form);
    
    CommandResult getMoodChoices(UserVisitPK userVisitPK, GetMoodChoicesForm form);
    
    CommandResult getMood(UserVisitPK userVisitPK, GetMoodForm form);
    
    CommandResult getMoods(UserVisitPK userVisitPK, GetMoodsForm form);
    
    CommandResult setDefaultMood(UserVisitPK userVisitPK, SetDefaultMoodForm form);
    
    CommandResult editMood(UserVisitPK userVisitPK, EditMoodForm form);
    
    CommandResult deleteMood(UserVisitPK userVisitPK, DeleteMoodForm form);
    
    // --------------------------------------------------------------------------------
    //   Mood Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult createMoodDescription(UserVisitPK userVisitPK, CreateMoodDescriptionForm form);
    
    CommandResult getMoodDescriptions(UserVisitPK userVisitPK, GetMoodDescriptionsForm form);
    
    CommandResult editMoodDescription(UserVisitPK userVisitPK, EditMoodDescriptionForm form);
    
    CommandResult deleteMoodDescription(UserVisitPK userVisitPK, DeleteMoodDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Birthday Formats
    // --------------------------------------------------------------------------------

    CommandResult createBirthdayFormat(UserVisitPK userVisitPK, CreateBirthdayFormatForm form);

    CommandResult getBirthdayFormatChoices(UserVisitPK userVisitPK, GetBirthdayFormatChoicesForm form);

    CommandResult getBirthdayFormat(UserVisitPK userVisitPK, GetBirthdayFormatForm form);

    CommandResult getBirthdayFormats(UserVisitPK userVisitPK, GetBirthdayFormatsForm form);

    CommandResult setDefaultBirthdayFormat(UserVisitPK userVisitPK, SetDefaultBirthdayFormatForm form);

    CommandResult editBirthdayFormat(UserVisitPK userVisitPK, EditBirthdayFormatForm form);

    CommandResult deleteBirthdayFormat(UserVisitPK userVisitPK, DeleteBirthdayFormatForm form);

    // --------------------------------------------------------------------------------
    //   Birthday Format Descriptions
    // --------------------------------------------------------------------------------

    CommandResult createBirthdayFormatDescription(UserVisitPK userVisitPK, CreateBirthdayFormatDescriptionForm form);

    CommandResult getBirthdayFormatDescription(UserVisitPK userVisitPK, GetBirthdayFormatDescriptionForm form);

    CommandResult getBirthdayFormatDescriptions(UserVisitPK userVisitPK, GetBirthdayFormatDescriptionsForm form);

    CommandResult editBirthdayFormatDescription(UserVisitPK userVisitPK, EditBirthdayFormatDescriptionForm form);

    CommandResult deleteBirthdayFormatDescription(UserVisitPK userVisitPK, DeleteBirthdayFormatDescriptionForm form);

    // --------------------------------------------------------------------------------
    //   Profiles
    // --------------------------------------------------------------------------------
    
    CommandResult createProfile(UserVisitPK userVisitPK, CreateProfileForm form);
    
    CommandResult editProfile(UserVisitPK userVisitPK, EditProfileForm form);
    
    // --------------------------------------------------------------------------------
    //   Party Alias Types
    // --------------------------------------------------------------------------------

    CommandResult createPartyAliasType(UserVisitPK userVisitPK, CreatePartyAliasTypeForm form);

    CommandResult getPartyAliasTypeChoices(UserVisitPK userVisitPK, GetPartyAliasTypeChoicesForm form);

    CommandResult getPartyAliasType(UserVisitPK userVisitPK, GetPartyAliasTypeForm form);

    CommandResult getPartyAliasTypes(UserVisitPK userVisitPK, GetPartyAliasTypesForm form);

    CommandResult setDefaultPartyAliasType(UserVisitPK userVisitPK, SetDefaultPartyAliasTypeForm form);

    CommandResult editPartyAliasType(UserVisitPK userVisitPK, EditPartyAliasTypeForm form);

    CommandResult deletePartyAliasType(UserVisitPK userVisitPK, DeletePartyAliasTypeForm form);

    // --------------------------------------------------------------------------------
    //   Party Alias Type Descriptions
    // --------------------------------------------------------------------------------

    CommandResult createPartyAliasTypeDescription(UserVisitPK userVisitPK, CreatePartyAliasTypeDescriptionForm form);

    CommandResult getPartyAliasTypeDescription(UserVisitPK userVisitPK, GetPartyAliasTypeDescriptionForm form);

    CommandResult getPartyAliasTypeDescriptions(UserVisitPK userVisitPK, GetPartyAliasTypeDescriptionsForm form);

    CommandResult editPartyAliasTypeDescription(UserVisitPK userVisitPK, EditPartyAliasTypeDescriptionForm form);

    CommandResult deletePartyAliasTypeDescription(UserVisitPK userVisitPK, DeletePartyAliasTypeDescriptionForm form);

    // --------------------------------------------------------------------------------
    //   Party Aliases
    // --------------------------------------------------------------------------------

    CommandResult createPartyAlias(UserVisitPK userVisitPK, CreatePartyAliasForm form);

    CommandResult getPartyAlias(UserVisitPK userVisitPK, GetPartyAliasForm form);

    CommandResult getPartyAliases(UserVisitPK userVisitPK, GetPartyAliasesForm form);

    CommandResult editPartyAlias(UserVisitPK userVisitPK, EditPartyAliasForm form);

    CommandResult deletePartyAlias(UserVisitPK userVisitPK, DeletePartyAliasForm form);

    // -------------------------------------------------------------------------
    //   Parties
    // -------------------------------------------------------------------------

    CommandResult getParty(UserVisitPK userVisitPK, GetPartyForm form);

    CommandResult getParties(UserVisitPK userVisitPK, GetPartiesForm form);

    // -------------------------------------------------------------------------
    //   Party Entity Types
    // -------------------------------------------------------------------------

    CommandResult createPartyEntityType(UserVisitPK userVisitPK, CreatePartyEntityTypeForm form);

    CommandResult editPartyEntityType(UserVisitPK userVisitPK, EditPartyEntityTypeForm form);

    CommandResult getPartyEntityType(UserVisitPK userVisitPK, GetPartyEntityTypeForm form);

    CommandResult getPartyEntityTypes(UserVisitPK userVisitPK, GetPartyEntityTypesForm form);

    CommandResult deletePartyEntityType(UserVisitPK userVisitPK, DeletePartyEntityTypeForm form);

    // --------------------------------------------------------------------------------
    //   Party Application Editor Uses
    // --------------------------------------------------------------------------------

    CommandResult createPartyApplicationEditorUse(UserVisitPK userVisitPK, CreatePartyApplicationEditorUseForm form);

    CommandResult getPartyApplicationEditorUse(UserVisitPK userVisitPK, GetPartyApplicationEditorUseForm form);

    CommandResult getPartyApplicationEditorUses(UserVisitPK userVisitPK, GetPartyApplicationEditorUsesForm form);

    CommandResult editPartyApplicationEditorUse(UserVisitPK userVisitPK, EditPartyApplicationEditorUseForm form);

    CommandResult deletePartyApplicationEditorUse(UserVisitPK userVisitPK, DeletePartyApplicationEditorUseForm form);

}
