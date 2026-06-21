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

package com.echothree.control.user.party.common;

import com.echothree.control.user.party.common.form.*;
import com.echothree.control.user.party.common.result.*;
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
    
    CommandResult<?> createLanguage(UserVisitPK userVisitPK, CreateLanguageForm form);
    
    CommandResult<GetLanguageChoicesResult> getLanguageChoices(UserVisitPK userVisitPK, GetLanguageChoicesForm form);
    
    CommandResult<GetLanguagesResult> getLanguages(UserVisitPK userVisitPK, GetLanguagesForm form);
    
    CommandResult<GetLanguageResult> getLanguage(UserVisitPK userVisitPK, GetLanguageForm form);
    
    CommandResult<GetPreferredLanguageResult> getPreferredLanguage(UserVisitPK userVisitPK, GetPreferredLanguageForm form);

    // -------------------------------------------------------------------------
    //   Language Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult<?> createLanguageDescription(UserVisitPK userVisitPK, CreateLanguageDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Party Types
    // -------------------------------------------------------------------------
    
    CommandResult<?> createPartyType(UserVisitPK userVisitPK, CreatePartyTypeForm form);
    
    CommandResult<GetPartyTypeResult> getPartyType(UserVisitPK userVisitPK, GetPartyTypeForm form);
    
    CommandResult<GetPartyTypesResult> getPartyTypes(UserVisitPK userVisitPK, GetPartyTypesForm form);
    
    CommandResult<GetPartyTypeChoicesResult> getPartyTypeChoices(UserVisitPK userVisitPK, GetPartyTypeChoicesForm form);
    
    // -------------------------------------------------------------------------
    //   Party Type Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult<?> createPartyTypeDescription(UserVisitPK userVisitPK, CreatePartyTypeDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Party Type Use Types
    // -------------------------------------------------------------------------
    
    CommandResult<?> createPartyTypeUseType(UserVisitPK userVisitPK, CreatePartyTypeUseTypeForm form);
    
    // -------------------------------------------------------------------------
    //   Party Type Use Type Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult<?> createPartyTypeUseTypeDescription(UserVisitPK userVisitPK, CreatePartyTypeUseTypeDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Party Type Uses
    // -------------------------------------------------------------------------
    
    CommandResult<?> createPartyTypeUse(UserVisitPK userVisitPK, CreatePartyTypeUseForm form);
    
    // -------------------------------------------------------------------------
    //   Party Type Password String Policies
    // -------------------------------------------------------------------------
    
    CommandResult<?> createPartyTypePasswordStringPolicy(UserVisitPK userVisitPK, CreatePartyTypePasswordStringPolicyForm form);
    
    CommandResult<EditPartyTypePasswordStringPolicyResult> editPartyTypePasswordStringPolicy(UserVisitPK userVisitPK, EditPartyTypePasswordStringPolicyForm form);
    
    CommandResult<?> deletePartyTypePasswordStringPolicy(UserVisitPK userVisitPK, DeletePartyTypePasswordStringPolicyForm form);
    
    // -------------------------------------------------------------------------
    //   Party Type Lockout Policies
    // -------------------------------------------------------------------------
    
    CommandResult<?> createPartyTypeLockoutPolicy(UserVisitPK userVisitPK, CreatePartyTypeLockoutPolicyForm form);
    
    CommandResult<EditPartyTypeLockoutPolicyResult> editPartyTypeLockoutPolicy(UserVisitPK userVisitPK, EditPartyTypeLockoutPolicyForm form);
    
    CommandResult<?> deletePartyTypeLockoutPolicy(UserVisitPK userVisitPK, DeletePartyTypeLockoutPolicyForm form);
    
    // -------------------------------------------------------------------------
    //   Party Type Audit Policies
    // -------------------------------------------------------------------------
    
    CommandResult<?> createPartyTypeAuditPolicy(UserVisitPK userVisitPK, CreatePartyTypeAuditPolicyForm form);
    
    CommandResult<EditPartyTypeAuditPolicyResult> editPartyTypeAuditPolicy(UserVisitPK userVisitPK, EditPartyTypeAuditPolicyForm form);
    
    CommandResult<?> deletePartyTypeAuditPolicy(UserVisitPK userVisitPK, DeletePartyTypeAuditPolicyForm form);
    
    // -------------------------------------------------------------------------
    //   Personal Titles
    // -------------------------------------------------------------------------
    
    CommandResult<?> createPersonalTitle(UserVisitPK userVisitPK, CreatePersonalTitleForm form);
    
    CommandResult<GetPersonalTitleChoicesResult> getPersonalTitleChoices(UserVisitPK userVisitPK, GetPersonalTitleChoicesForm form);
    
    CommandResult<GetPersonalTitlesResult> getPersonalTitles(UserVisitPK userVisitPK, GetPersonalTitlesForm form);
    
    CommandResult<?> setDefaultPersonalTitle(UserVisitPK userVisitPK, SetDefaultPersonalTitleForm form);
    
    CommandResult<EditPersonalTitleResult> editPersonalTitle(UserVisitPK userVisitPK, EditPersonalTitleForm form);
    
    CommandResult<?> deletePersonalTitle(UserVisitPK userVisitPK, DeletePersonalTitleForm form);
    
    // -------------------------------------------------------------------------
    //   Name Suffixes
    // -------------------------------------------------------------------------
    
    CommandResult<?> createNameSuffix(UserVisitPK userVisitPK, CreateNameSuffixForm form);
    
    CommandResult<GetNameSuffixChoicesResult> getNameSuffixChoices(UserVisitPK userVisitPK, GetNameSuffixChoicesForm form);
    
    CommandResult<GetNameSuffixesResult> getNameSuffixes(UserVisitPK userVisitPK, GetNameSuffixesForm form);
    
    CommandResult<?> setDefaultNameSuffix(UserVisitPK userVisitPK, SetDefaultNameSuffixForm form);
    
    CommandResult<EditNameSuffixResult> editNameSuffix(UserVisitPK userVisitPK, EditNameSuffixForm form);
    
    CommandResult<?> deleteNameSuffix(UserVisitPK userVisitPK, DeleteNameSuffixForm form);
    
    // -------------------------------------------------------------------------
    //   Date Time Formats
    // -------------------------------------------------------------------------
    
    CommandResult<?> createDateTimeFormat(UserVisitPK userVisitPK, CreateDateTimeFormatForm form);
    
    CommandResult<?> getDateTimeFormatChoices(UserVisitPK userVisitPK, GetDateTimeFormatChoicesForm getDateTimeFormatChoicesForm);
    
    CommandResult<GetDateTimeFormatResult> getDateTimeFormat(UserVisitPK userVisitPK, GetDateTimeFormatForm form);

    CommandResult<GetPreferredDateTimeFormatResult> getPreferredDateTimeFormat(UserVisitPK userVisitPK, GetPreferredDateTimeFormatForm form);

    CommandResult<GetDateTimeFormatsResult> getDateTimeFormats(UserVisitPK userVisitPK, GetDateTimeFormatsForm form);
    
    // -------------------------------------------------------------------------
    //   Date Time Format Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult<?> createDateTimeFormatDescription(UserVisitPK userVisitPK, CreateDateTimeFormatDescriptionForm form);
    
    CommandResult<GetDateTimeFormatDescriptionsResult> getDateTimeFormatDescriptions(UserVisitPK userVisitPK, GetDateTimeFormatDescriptionsForm form);
    
    CommandResult<EditDateTimeFormatDescriptionResult> editDateTimeFormatDescription(UserVisitPK userVisitPK, EditDateTimeFormatDescriptionForm form);
    
    CommandResult<?> deleteDateTimeFormatDescription(UserVisitPK userVisitPK, DeleteDateTimeFormatDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Time Zones
    // -------------------------------------------------------------------------
    
    CommandResult<?> createTimeZone(UserVisitPK userVisitPK, CreateTimeZoneForm form);
    
    CommandResult<GetTimeZoneChoicesResult> getTimeZoneChoices(UserVisitPK userVisitPK, GetTimeZoneChoicesForm form);
    
    CommandResult<GetTimeZoneResult> getTimeZone(UserVisitPK userVisitPK, GetTimeZoneForm form);

    CommandResult<GetPreferredTimeZoneResult> getPreferredTimeZone(UserVisitPK userVisitPK, GetPreferredTimeZoneForm form);

    CommandResult<GetTimeZonesResult> getTimeZones(UserVisitPK userVisitPK, GetTimeZonesForm form);
    
    // -------------------------------------------------------------------------
    //   Time Zone Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult<?> createTimeZoneDescription(UserVisitPK userVisitPK, CreateTimeZoneDescriptionForm form);
    
    CommandResult<GetTimeZoneDescriptionsResult> getTimeZoneDescriptions(UserVisitPK userVisitPK, GetTimeZoneDescriptionsForm form);
    
    CommandResult<EditTimeZoneDescriptionResult> editTimeZoneDescription(UserVisitPK userVisitPK, EditTimeZoneDescriptionForm form);
    
    CommandResult<?> deleteTimeZoneDescription(UserVisitPK userVisitPK, DeleteTimeZoneDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Companies
    // -------------------------------------------------------------------------
    
    CommandResult<?> createCompany(UserVisitPK userVisitPK, CreateCompanyForm form);
    
    CommandResult<GetCompanyChoicesResult> getCompanyChoices(UserVisitPK userVisitPK, GetCompanyChoicesForm form);
    
    CommandResult<GetCompaniesResult> getCompanies(UserVisitPK userVisitPK, GetCompaniesForm form);
    
    CommandResult<GetCompanyResult> getCompany(UserVisitPK userVisitPK, GetCompanyForm form);
    
    CommandResult<?> setDefaultCompany(UserVisitPK userVisitPK, SetDefaultCompanyForm form);
    
    CommandResult<EditCompanyResult> editCompany(UserVisitPK userVisitPK, EditCompanyForm form);
    
    CommandResult<?> deleteCompany(UserVisitPK userVisitPK, DeleteCompanyForm form);
    
    // -------------------------------------------------------------------------
    //   Divisions
    // -------------------------------------------------------------------------
    
    CommandResult<?> createDivision(UserVisitPK userVisitPK, CreateDivisionForm form);
    
    CommandResult<GetDivisionChoicesResult> getDivisionChoices(UserVisitPK userVisitPK, GetDivisionChoicesForm form);
    
    CommandResult<GetDivisionsResult> getDivisions(UserVisitPK userVisitPK, GetDivisionsForm form);
    
    CommandResult<GetDivisionResult> getDivision(UserVisitPK userVisitPK, GetDivisionForm form);
    
    CommandResult<?> setDefaultDivision(UserVisitPK userVisitPK, SetDefaultDivisionForm form);
    
    CommandResult<EditDivisionResult> editDivision(UserVisitPK userVisitPK, EditDivisionForm form);
    
    CommandResult<?> deleteDivision(UserVisitPK userVisitPK, DeleteDivisionForm form);
    
    // -------------------------------------------------------------------------
    //   Departments
    // -------------------------------------------------------------------------
    
    CommandResult<?> createDepartment(UserVisitPK userVisitPK, CreateDepartmentForm form);
    
    CommandResult<GetDepartmentChoicesResult> getDepartmentChoices(UserVisitPK userVisitPK, GetDepartmentChoicesForm form);

    CommandResult<GetDepartmentsResult> getDepartments(UserVisitPK userVisitPK, GetDepartmentsForm form);
    
    CommandResult<GetDepartmentResult> getDepartment(UserVisitPK userVisitPK, GetDepartmentForm form);
    
    CommandResult<?> setDefaultDepartment(UserVisitPK userVisitPK, SetDefaultDepartmentForm form);
    
    CommandResult<EditDepartmentResult> editDepartment(UserVisitPK userVisitPK, EditDepartmentForm form);
    
    CommandResult<?> deleteDepartment(UserVisitPK userVisitPK, DeleteDepartmentForm form);
    
    // -------------------------------------------------------------------------
    //   Customers
    // -------------------------------------------------------------------------
    
    CommandResult<?> createCustomer(UserVisitPK userVisitPK, CreateCustomerForm form);
    
    CommandResult<?> createCustomerWithLogin(UserVisitPK userVisitPK, CreateCustomerWithLoginForm createCustomerWithLoginForm);
    
    // --------------------------------------------------------------------------------
    //   Employees
    // --------------------------------------------------------------------------------
    
    CommandResult<?> createEmployee(UserVisitPK userVisitPK, CreateEmployeeForm form);
    
    CommandResult<?> addEmployeeToCompany(UserVisitPK userVisitPK, AddEmployeeToCompanyForm form);

    CommandResult<?> addEmployeeToDivision(UserVisitPK userVisitPK, AddEmployeeToDivisionForm form);

    CommandResult<?> addEmployeeToDepartment(UserVisitPK userVisitPK, AddEmployeeToDepartmentForm form);

    CommandResult<?> removeEmployeeFromCompany(UserVisitPK userVisitPK, RemoveEmployeeFromCompanyForm form);

    CommandResult<?> removeEmployeeFromDivision(UserVisitPK userVisitPK, RemoveEmployeeFromDivisionForm form);

    CommandResult<?> removeEmployeeFromDepartment(UserVisitPK userVisitPK, RemoveEmployeeFromDepartmentForm form);

    CommandResult<GetEmployeeStatusChoicesResult> getEmployeeStatusChoices(UserVisitPK userVisitPK, GetEmployeeStatusChoicesForm form);
    
    CommandResult<?> setEmployeeStatus(UserVisitPK userVisitPK, SetEmployeeStatusForm form);
    
    CommandResult<GetEmployeeAvailabilityChoicesResult> getEmployeeAvailabilityChoices(UserVisitPK userVisitPK, GetEmployeeAvailabilityChoicesForm form);
    
    CommandResult<?> setEmployeeAvailability(UserVisitPK userVisitPK, SetEmployeeAvailabilityForm form);
    
    CommandResult<EditEmployeeResult> editEmployee(UserVisitPK userVisitPK, EditEmployeeForm form);
    
    // --------------------------------------------------------------------------------
    //   Party Relationship Types
    // --------------------------------------------------------------------------------
    
    CommandResult<?> createPartyRelationshipType(UserVisitPK userVisitPK, CreatePartyRelationshipTypeForm form);
    
    // --------------------------------------------------------------------------------
    //   Party Relationship Type Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult<?> createPartyRelationshipTypeDescription(UserVisitPK userVisitPK, CreatePartyRelationshipTypeDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Party Relationships
    // --------------------------------------------------------------------------------

    CommandResult<GetPartyRelationshipsResult> getPartyRelationships(UserVisitPK userVisitPK, GetPartyRelationshipsForm form);

    CommandResult<GetPartyRelationshipResult> getPartyRelationship(UserVisitPK userVisitPK, GetPartyRelationshipForm form);

    // --------------------------------------------------------------------------------
    //   Role Types
    // --------------------------------------------------------------------------------
    
    CommandResult<?> createRoleType(UserVisitPK userVisitPK, CreateRoleTypeForm form);

    CommandResult<GetRoleTypeResult> getRoleType(UserVisitPK userVisitPK, GetRoleTypeForm form);

    CommandResult<GetRoleTypesResult> getRoleTypes(UserVisitPK userVisitPK, GetRoleTypesForm form);
    
    // --------------------------------------------------------------------------------
    //   Role Type Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult<?> createRoleTypeDescription(UserVisitPK userVisitPK, CreateRoleTypeDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Vendors
    // --------------------------------------------------------------------------------
    
    CommandResult<?> createVendor(UserVisitPK userVisitPK, CreateVendorForm form);
    
    CommandResult<GetVendorStatusChoicesResult> getVendorStatusChoices(UserVisitPK userVisitPK, GetVendorStatusChoicesForm form);

    CommandResult<?> setVendorStatus(UserVisitPK userVisitPK, SetVendorStatusForm form);

    // --------------------------------------------------------------------------------
    //   Genders
    // --------------------------------------------------------------------------------
    
    CommandResult<?> createGender(UserVisitPK userVisitPK, CreateGenderForm form);
    
    CommandResult<GetGenderChoicesResult> getGenderChoices(UserVisitPK userVisitPK, GetGenderChoicesForm form);
    
    CommandResult<GetGenderResult> getGender(UserVisitPK userVisitPK, GetGenderForm form);
    
    CommandResult<GetGendersResult> getGenders(UserVisitPK userVisitPK, GetGendersForm form);
    
    CommandResult<?> setDefaultGender(UserVisitPK userVisitPK, SetDefaultGenderForm form);
    
    CommandResult<EditGenderResult> editGender(UserVisitPK userVisitPK, EditGenderForm form);
    
    CommandResult<?> deleteGender(UserVisitPK userVisitPK, DeleteGenderForm form);
    
    // --------------------------------------------------------------------------------
    //   Gender Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult<?> createGenderDescription(UserVisitPK userVisitPK, CreateGenderDescriptionForm form);
    
    CommandResult<GetGenderDescriptionsResult> getGenderDescriptions(UserVisitPK userVisitPK, GetGenderDescriptionsForm form);
    
    CommandResult<EditGenderDescriptionResult> editGenderDescription(UserVisitPK userVisitPK, EditGenderDescriptionForm form);
    
    CommandResult<?> deleteGenderDescription(UserVisitPK userVisitPK, DeleteGenderDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Birthday Formats
    // --------------------------------------------------------------------------------

    CommandResult<?> createBirthdayFormat(UserVisitPK userVisitPK, CreateBirthdayFormatForm form);

    CommandResult<GetBirthdayFormatChoicesResult> getBirthdayFormatChoices(UserVisitPK userVisitPK, GetBirthdayFormatChoicesForm form);

    CommandResult<GetBirthdayFormatResult> getBirthdayFormat(UserVisitPK userVisitPK, GetBirthdayFormatForm form);

    CommandResult<GetBirthdayFormatsResult> getBirthdayFormats(UserVisitPK userVisitPK, GetBirthdayFormatsForm form);

    CommandResult<?> setDefaultBirthdayFormat(UserVisitPK userVisitPK, SetDefaultBirthdayFormatForm form);

    CommandResult<EditBirthdayFormatResult> editBirthdayFormat(UserVisitPK userVisitPK, EditBirthdayFormatForm form);

    CommandResult<?> deleteBirthdayFormat(UserVisitPK userVisitPK, DeleteBirthdayFormatForm form);

    // --------------------------------------------------------------------------------
    //   Birthday Format Descriptions
    // --------------------------------------------------------------------------------

    CommandResult<?> createBirthdayFormatDescription(UserVisitPK userVisitPK, CreateBirthdayFormatDescriptionForm form);

    CommandResult<GetBirthdayFormatDescriptionResult> getBirthdayFormatDescription(UserVisitPK userVisitPK, GetBirthdayFormatDescriptionForm form);

    CommandResult<GetBirthdayFormatDescriptionsResult> getBirthdayFormatDescriptions(UserVisitPK userVisitPK, GetBirthdayFormatDescriptionsForm form);

    CommandResult<EditBirthdayFormatDescriptionResult> editBirthdayFormatDescription(UserVisitPK userVisitPK, EditBirthdayFormatDescriptionForm form);

    CommandResult<?> deleteBirthdayFormatDescription(UserVisitPK userVisitPK, DeleteBirthdayFormatDescriptionForm form);

    // --------------------------------------------------------------------------------
    //   Profiles
    // --------------------------------------------------------------------------------
    
    CommandResult<?> createProfile(UserVisitPK userVisitPK, CreateProfileForm form);
    
    CommandResult<EditProfileResult> editProfile(UserVisitPK userVisitPK, EditProfileForm form);
    
    // --------------------------------------------------------------------------------
    //   Party Alias Types
    // --------------------------------------------------------------------------------

    CommandResult<?> createPartyAliasType(UserVisitPK userVisitPK, CreatePartyAliasTypeForm form);

    CommandResult<GetPartyAliasTypeChoicesResult> getPartyAliasTypeChoices(UserVisitPK userVisitPK, GetPartyAliasTypeChoicesForm form);

    CommandResult<GetPartyAliasTypeResult> getPartyAliasType(UserVisitPK userVisitPK, GetPartyAliasTypeForm form);

    CommandResult<GetPartyAliasTypesResult> getPartyAliasTypes(UserVisitPK userVisitPK, GetPartyAliasTypesForm form);

    CommandResult<?> setDefaultPartyAliasType(UserVisitPK userVisitPK, SetDefaultPartyAliasTypeForm form);

    CommandResult<EditPartyAliasTypeResult> editPartyAliasType(UserVisitPK userVisitPK, EditPartyAliasTypeForm form);

    CommandResult<?> deletePartyAliasType(UserVisitPK userVisitPK, DeletePartyAliasTypeForm form);

    // --------------------------------------------------------------------------------
    //   Party Alias Type Descriptions
    // --------------------------------------------------------------------------------

    CommandResult<?> createPartyAliasTypeDescription(UserVisitPK userVisitPK, CreatePartyAliasTypeDescriptionForm form);

    CommandResult<GetPartyAliasTypeDescriptionResult> getPartyAliasTypeDescription(UserVisitPK userVisitPK, GetPartyAliasTypeDescriptionForm form);

    CommandResult<GetPartyAliasTypeDescriptionsResult> getPartyAliasTypeDescriptions(UserVisitPK userVisitPK, GetPartyAliasTypeDescriptionsForm form);

    CommandResult<EditPartyAliasTypeDescriptionResult> editPartyAliasTypeDescription(UserVisitPK userVisitPK, EditPartyAliasTypeDescriptionForm form);

    CommandResult<?> deletePartyAliasTypeDescription(UserVisitPK userVisitPK, DeletePartyAliasTypeDescriptionForm form);

    // --------------------------------------------------------------------------------
    //   Party Aliases
    // --------------------------------------------------------------------------------

    CommandResult<?> createPartyAlias(UserVisitPK userVisitPK, CreatePartyAliasForm form);

    CommandResult<GetPartyAliasResult> getPartyAlias(UserVisitPK userVisitPK, GetPartyAliasForm form);

    CommandResult<GetPartyAliasesResult> getPartyAliases(UserVisitPK userVisitPK, GetPartyAliasesForm form);

    CommandResult<EditPartyAliasResult> editPartyAlias(UserVisitPK userVisitPK, EditPartyAliasForm form);

    CommandResult<?> deletePartyAlias(UserVisitPK userVisitPK, DeletePartyAliasForm form);

    // -------------------------------------------------------------------------
    //   Parties
    // -------------------------------------------------------------------------

    CommandResult<GetPartyResult> getParty(UserVisitPK userVisitPK, GetPartyForm form);

    CommandResult<GetPartiesResult> getParties(UserVisitPK userVisitPK, GetPartiesForm form);

    // -------------------------------------------------------------------------
    //   Party Entity Types
    // -------------------------------------------------------------------------

    CommandResult<?> createPartyEntityType(UserVisitPK userVisitPK, CreatePartyEntityTypeForm form);

    CommandResult<EditPartyEntityTypeResult> editPartyEntityType(UserVisitPK userVisitPK, EditPartyEntityTypeForm form);

    CommandResult<GetPartyEntityTypeResult> getPartyEntityType(UserVisitPK userVisitPK, GetPartyEntityTypeForm form);

    CommandResult<GetPartyEntityTypesResult> getPartyEntityTypes(UserVisitPK userVisitPK, GetPartyEntityTypesForm form);

    CommandResult<?> deletePartyEntityType(UserVisitPK userVisitPK, DeletePartyEntityTypeForm form);

    // --------------------------------------------------------------------------------
    //   Party Application Editor Uses
    // --------------------------------------------------------------------------------

    CommandResult<?> createPartyApplicationEditorUse(UserVisitPK userVisitPK, CreatePartyApplicationEditorUseForm form);

    CommandResult<GetPartyApplicationEditorUseResult> getPartyApplicationEditorUse(UserVisitPK userVisitPK, GetPartyApplicationEditorUseForm form);

    CommandResult<GetPartyApplicationEditorUsesResult> getPartyApplicationEditorUses(UserVisitPK userVisitPK, GetPartyApplicationEditorUsesForm form);

    CommandResult<EditPartyApplicationEditorUseResult> editPartyApplicationEditorUse(UserVisitPK userVisitPK, EditPartyApplicationEditorUseForm form);

    CommandResult<?> deletePartyApplicationEditorUse(UserVisitPK userVisitPK, DeletePartyApplicationEditorUseForm form);

}
