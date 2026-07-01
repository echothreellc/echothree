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
import com.echothree.util.common.command.VoidResult;

public interface PartyService
        extends PartyForms {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    String ping();
    
    // -------------------------------------------------------------------------
    //   Languages
    // -------------------------------------------------------------------------
    
    CommandResult<VoidResult> createLanguage(UserVisitPK userVisitPK, CreateLanguageForm form);
    
    CommandResult<GetLanguageChoicesResult> getLanguageChoices(UserVisitPK userVisitPK, GetLanguageChoicesForm form);
    
    CommandResult<GetLanguagesResult> getLanguages(UserVisitPK userVisitPK, GetLanguagesForm form);
    
    CommandResult<GetLanguageResult> getLanguage(UserVisitPK userVisitPK, GetLanguageForm form);
    
    CommandResult<GetPreferredLanguageResult> getPreferredLanguage(UserVisitPK userVisitPK, GetPreferredLanguageForm form);

    // -------------------------------------------------------------------------
    //   Language Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult<VoidResult> createLanguageDescription(UserVisitPK userVisitPK, CreateLanguageDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Party Types
    // -------------------------------------------------------------------------
    
    CommandResult<VoidResult> createPartyType(UserVisitPK userVisitPK, CreatePartyTypeForm form);
    
    CommandResult<GetPartyTypeResult> getPartyType(UserVisitPK userVisitPK, GetPartyTypeForm form);
    
    CommandResult<GetPartyTypesResult> getPartyTypes(UserVisitPK userVisitPK, GetPartyTypesForm form);
    
    CommandResult<GetPartyTypeChoicesResult> getPartyTypeChoices(UserVisitPK userVisitPK, GetPartyTypeChoicesForm form);
    
    // -------------------------------------------------------------------------
    //   Party Type Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult<VoidResult> createPartyTypeDescription(UserVisitPK userVisitPK, CreatePartyTypeDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Party Type Use Types
    // -------------------------------------------------------------------------
    
    CommandResult<VoidResult> createPartyTypeUseType(UserVisitPK userVisitPK, CreatePartyTypeUseTypeForm form);
    
    // -------------------------------------------------------------------------
    //   Party Type Use Type Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult<VoidResult> createPartyTypeUseTypeDescription(UserVisitPK userVisitPK, CreatePartyTypeUseTypeDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Party Type Uses
    // -------------------------------------------------------------------------
    
    CommandResult<VoidResult> createPartyTypeUse(UserVisitPK userVisitPK, CreatePartyTypeUseForm form);
    
    // -------------------------------------------------------------------------
    //   Party Type Password String Policies
    // -------------------------------------------------------------------------
    
    CommandResult<VoidResult> createPartyTypePasswordStringPolicy(UserVisitPK userVisitPK, CreatePartyTypePasswordStringPolicyForm form);
    
    CommandResult<EditPartyTypePasswordStringPolicyResult> editPartyTypePasswordStringPolicy(UserVisitPK userVisitPK, EditPartyTypePasswordStringPolicyForm form);
    
    CommandResult<VoidResult> deletePartyTypePasswordStringPolicy(UserVisitPK userVisitPK, DeletePartyTypePasswordStringPolicyForm form);
    
    // -------------------------------------------------------------------------
    //   Party Type Lockout Policies
    // -------------------------------------------------------------------------
    
    CommandResult<VoidResult> createPartyTypeLockoutPolicy(UserVisitPK userVisitPK, CreatePartyTypeLockoutPolicyForm form);
    
    CommandResult<EditPartyTypeLockoutPolicyResult> editPartyTypeLockoutPolicy(UserVisitPK userVisitPK, EditPartyTypeLockoutPolicyForm form);
    
    CommandResult<VoidResult> deletePartyTypeLockoutPolicy(UserVisitPK userVisitPK, DeletePartyTypeLockoutPolicyForm form);
    
    // -------------------------------------------------------------------------
    //   Party Type Audit Policies
    // -------------------------------------------------------------------------
    
    CommandResult<VoidResult> createPartyTypeAuditPolicy(UserVisitPK userVisitPK, CreatePartyTypeAuditPolicyForm form);
    
    CommandResult<EditPartyTypeAuditPolicyResult> editPartyTypeAuditPolicy(UserVisitPK userVisitPK, EditPartyTypeAuditPolicyForm form);
    
    CommandResult<VoidResult> deletePartyTypeAuditPolicy(UserVisitPK userVisitPK, DeletePartyTypeAuditPolicyForm form);
    
    // -------------------------------------------------------------------------
    //   Personal Titles
    // -------------------------------------------------------------------------
    
    CommandResult<CreatePersonalTitleResult> createPersonalTitle(UserVisitPK userVisitPK, CreatePersonalTitleForm form);
    
    CommandResult<GetPersonalTitleChoicesResult> getPersonalTitleChoices(UserVisitPK userVisitPK, GetPersonalTitleChoicesForm form);
    
    CommandResult<GetPersonalTitlesResult> getPersonalTitles(UserVisitPK userVisitPK, GetPersonalTitlesForm form);
    
    CommandResult<VoidResult> setDefaultPersonalTitle(UserVisitPK userVisitPK, SetDefaultPersonalTitleForm form);
    
    CommandResult<EditPersonalTitleResult> editPersonalTitle(UserVisitPK userVisitPK, EditPersonalTitleForm form);
    
    CommandResult<VoidResult> deletePersonalTitle(UserVisitPK userVisitPK, DeletePersonalTitleForm form);
    
    // -------------------------------------------------------------------------
    //   Name Suffixes
    // -------------------------------------------------------------------------
    
    CommandResult<CreateNameSuffixResult> createNameSuffix(UserVisitPK userVisitPK, CreateNameSuffixForm form);
    
    CommandResult<GetNameSuffixChoicesResult> getNameSuffixChoices(UserVisitPK userVisitPK, GetNameSuffixChoicesForm form);
    
    CommandResult<GetNameSuffixesResult> getNameSuffixes(UserVisitPK userVisitPK, GetNameSuffixesForm form);
    
    CommandResult<VoidResult> setDefaultNameSuffix(UserVisitPK userVisitPK, SetDefaultNameSuffixForm form);
    
    CommandResult<EditNameSuffixResult> editNameSuffix(UserVisitPK userVisitPK, EditNameSuffixForm form);
    
    CommandResult<VoidResult> deleteNameSuffix(UserVisitPK userVisitPK, DeleteNameSuffixForm form);
    
    // -------------------------------------------------------------------------
    //   Date Time Formats
    // -------------------------------------------------------------------------
    
    CommandResult<VoidResult> createDateTimeFormat(UserVisitPK userVisitPK, CreateDateTimeFormatForm form);
    
    CommandResult<GetDateTimeFormatChoicesResult> getDateTimeFormatChoices(UserVisitPK userVisitPK, GetDateTimeFormatChoicesForm getDateTimeFormatChoicesForm);
    
    CommandResult<GetDateTimeFormatResult> getDateTimeFormat(UserVisitPK userVisitPK, GetDateTimeFormatForm form);

    CommandResult<GetPreferredDateTimeFormatResult> getPreferredDateTimeFormat(UserVisitPK userVisitPK, GetPreferredDateTimeFormatForm form);

    CommandResult<GetDateTimeFormatsResult> getDateTimeFormats(UserVisitPK userVisitPK, GetDateTimeFormatsForm form);
    
    // -------------------------------------------------------------------------
    //   Date Time Format Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult<VoidResult> createDateTimeFormatDescription(UserVisitPK userVisitPK, CreateDateTimeFormatDescriptionForm form);
    
    CommandResult<GetDateTimeFormatDescriptionsResult> getDateTimeFormatDescriptions(UserVisitPK userVisitPK, GetDateTimeFormatDescriptionsForm form);
    
    CommandResult<EditDateTimeFormatDescriptionResult> editDateTimeFormatDescription(UserVisitPK userVisitPK, EditDateTimeFormatDescriptionForm form);
    
    CommandResult<VoidResult> deleteDateTimeFormatDescription(UserVisitPK userVisitPK, DeleteDateTimeFormatDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Time Zones
    // -------------------------------------------------------------------------
    
    CommandResult<VoidResult> createTimeZone(UserVisitPK userVisitPK, CreateTimeZoneForm form);
    
    CommandResult<GetTimeZoneChoicesResult> getTimeZoneChoices(UserVisitPK userVisitPK, GetTimeZoneChoicesForm form);
    
    CommandResult<GetTimeZoneResult> getTimeZone(UserVisitPK userVisitPK, GetTimeZoneForm form);

    CommandResult<GetPreferredTimeZoneResult> getPreferredTimeZone(UserVisitPK userVisitPK, GetPreferredTimeZoneForm form);

    CommandResult<GetTimeZonesResult> getTimeZones(UserVisitPK userVisitPK, GetTimeZonesForm form);
    
    // -------------------------------------------------------------------------
    //   Time Zone Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult<VoidResult> createTimeZoneDescription(UserVisitPK userVisitPK, CreateTimeZoneDescriptionForm form);
    
    CommandResult<GetTimeZoneDescriptionsResult> getTimeZoneDescriptions(UserVisitPK userVisitPK, GetTimeZoneDescriptionsForm form);
    
    CommandResult<EditTimeZoneDescriptionResult> editTimeZoneDescription(UserVisitPK userVisitPK, EditTimeZoneDescriptionForm form);
    
    CommandResult<VoidResult> deleteTimeZoneDescription(UserVisitPK userVisitPK, DeleteTimeZoneDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Companies
    // -------------------------------------------------------------------------
    
    CommandResult<CreateCompanyResult> createCompany(UserVisitPK userVisitPK, CreateCompanyForm form);
    
    CommandResult<GetCompanyChoicesResult> getCompanyChoices(UserVisitPK userVisitPK, GetCompanyChoicesForm form);
    
    CommandResult<GetCompaniesResult> getCompanies(UserVisitPK userVisitPK, GetCompaniesForm form);
    
    CommandResult<GetCompanyResult> getCompany(UserVisitPK userVisitPK, GetCompanyForm form);
    
    CommandResult<VoidResult> setDefaultCompany(UserVisitPK userVisitPK, SetDefaultCompanyForm form);
    
    CommandResult<EditCompanyResult> editCompany(UserVisitPK userVisitPK, EditCompanyForm form);
    
    CommandResult<VoidResult> deleteCompany(UserVisitPK userVisitPK, DeleteCompanyForm form);
    
    // -------------------------------------------------------------------------
    //   Divisions
    // -------------------------------------------------------------------------
    
    CommandResult<CreateDivisionResult> createDivision(UserVisitPK userVisitPK, CreateDivisionForm form);
    
    CommandResult<GetDivisionChoicesResult> getDivisionChoices(UserVisitPK userVisitPK, GetDivisionChoicesForm form);
    
    CommandResult<GetDivisionsResult> getDivisions(UserVisitPK userVisitPK, GetDivisionsForm form);
    
    CommandResult<GetDivisionResult> getDivision(UserVisitPK userVisitPK, GetDivisionForm form);
    
    CommandResult<VoidResult> setDefaultDivision(UserVisitPK userVisitPK, SetDefaultDivisionForm form);
    
    CommandResult<EditDivisionResult> editDivision(UserVisitPK userVisitPK, EditDivisionForm form);
    
    CommandResult<VoidResult> deleteDivision(UserVisitPK userVisitPK, DeleteDivisionForm form);
    
    // -------------------------------------------------------------------------
    //   Departments
    // -------------------------------------------------------------------------
    
    CommandResult<CreateDepartmentResult> createDepartment(UserVisitPK userVisitPK, CreateDepartmentForm form);
    
    CommandResult<GetDepartmentChoicesResult> getDepartmentChoices(UserVisitPK userVisitPK, GetDepartmentChoicesForm form);

    CommandResult<GetDepartmentsResult> getDepartments(UserVisitPK userVisitPK, GetDepartmentsForm form);
    
    CommandResult<GetDepartmentResult> getDepartment(UserVisitPK userVisitPK, GetDepartmentForm form);
    
    CommandResult<VoidResult> setDefaultDepartment(UserVisitPK userVisitPK, SetDefaultDepartmentForm form);
    
    CommandResult<EditDepartmentResult> editDepartment(UserVisitPK userVisitPK, EditDepartmentForm form);
    
    CommandResult<VoidResult> deleteDepartment(UserVisitPK userVisitPK, DeleteDepartmentForm form);
    
    // -------------------------------------------------------------------------
    //   Customers
    // -------------------------------------------------------------------------
    
    CommandResult<CreateCustomerResult> createCustomer(UserVisitPK userVisitPK, CreateCustomerForm form);
    
    CommandResult<CreateCustomerWithLoginResult> createCustomerWithLogin(UserVisitPK userVisitPK, CreateCustomerWithLoginForm createCustomerWithLoginForm);
    
    // --------------------------------------------------------------------------------
    //   Employees
    // --------------------------------------------------------------------------------
    
    CommandResult<CreateEmployeeResult> createEmployee(UserVisitPK userVisitPK, CreateEmployeeForm form);
    
    CommandResult<VoidResult> addEmployeeToCompany(UserVisitPK userVisitPK, AddEmployeeToCompanyForm form);

    CommandResult<VoidResult> addEmployeeToDivision(UserVisitPK userVisitPK, AddEmployeeToDivisionForm form);

    CommandResult<VoidResult> addEmployeeToDepartment(UserVisitPK userVisitPK, AddEmployeeToDepartmentForm form);

    CommandResult<VoidResult> removeEmployeeFromCompany(UserVisitPK userVisitPK, RemoveEmployeeFromCompanyForm form);

    CommandResult<VoidResult> removeEmployeeFromDivision(UserVisitPK userVisitPK, RemoveEmployeeFromDivisionForm form);

    CommandResult<VoidResult> removeEmployeeFromDepartment(UserVisitPK userVisitPK, RemoveEmployeeFromDepartmentForm form);

    CommandResult<GetEmployeeStatusChoicesResult> getEmployeeStatusChoices(UserVisitPK userVisitPK, GetEmployeeStatusChoicesForm form);
    
    CommandResult<VoidResult> setEmployeeStatus(UserVisitPK userVisitPK, SetEmployeeStatusForm form);
    
    CommandResult<GetEmployeeAvailabilityChoicesResult> getEmployeeAvailabilityChoices(UserVisitPK userVisitPK, GetEmployeeAvailabilityChoicesForm form);
    
    CommandResult<VoidResult> setEmployeeAvailability(UserVisitPK userVisitPK, SetEmployeeAvailabilityForm form);
    
    CommandResult<EditEmployeeResult> editEmployee(UserVisitPK userVisitPK, EditEmployeeForm form);
    
    // --------------------------------------------------------------------------------
    //   Party Relationship Types
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createPartyRelationshipType(UserVisitPK userVisitPK, CreatePartyRelationshipTypeForm form);
    
    // --------------------------------------------------------------------------------
    //   Party Relationship Type Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createPartyRelationshipTypeDescription(UserVisitPK userVisitPK, CreatePartyRelationshipTypeDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Party Relationships
    // --------------------------------------------------------------------------------

    CommandResult<GetPartyRelationshipsResult> getPartyRelationships(UserVisitPK userVisitPK, GetPartyRelationshipsForm form);

    CommandResult<GetPartyRelationshipResult> getPartyRelationship(UserVisitPK userVisitPK, GetPartyRelationshipForm form);

    // --------------------------------------------------------------------------------
    //   Role Types
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createRoleType(UserVisitPK userVisitPK, CreateRoleTypeForm form);

    CommandResult<GetRoleTypeResult> getRoleType(UserVisitPK userVisitPK, GetRoleTypeForm form);

    CommandResult<GetRoleTypesResult> getRoleTypes(UserVisitPK userVisitPK, GetRoleTypesForm form);
    
    // --------------------------------------------------------------------------------
    //   Role Type Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createRoleTypeDescription(UserVisitPK userVisitPK, CreateRoleTypeDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Vendors
    // --------------------------------------------------------------------------------
    
    CommandResult<CreateVendorResult> createVendor(UserVisitPK userVisitPK, CreateVendorForm form);
    
    CommandResult<GetVendorStatusChoicesResult> getVendorStatusChoices(UserVisitPK userVisitPK, GetVendorStatusChoicesForm form);

    CommandResult<VoidResult> setVendorStatus(UserVisitPK userVisitPK, SetVendorStatusForm form);

    // --------------------------------------------------------------------------------
    //   Genders
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createGender(UserVisitPK userVisitPK, CreateGenderForm form);
    
    CommandResult<GetGenderChoicesResult> getGenderChoices(UserVisitPK userVisitPK, GetGenderChoicesForm form);
    
    CommandResult<GetGenderResult> getGender(UserVisitPK userVisitPK, GetGenderForm form);
    
    CommandResult<GetGendersResult> getGenders(UserVisitPK userVisitPK, GetGendersForm form);
    
    CommandResult<VoidResult> setDefaultGender(UserVisitPK userVisitPK, SetDefaultGenderForm form);
    
    CommandResult<EditGenderResult> editGender(UserVisitPK userVisitPK, EditGenderForm form);
    
    CommandResult<VoidResult> deleteGender(UserVisitPK userVisitPK, DeleteGenderForm form);
    
    // --------------------------------------------------------------------------------
    //   Gender Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createGenderDescription(UserVisitPK userVisitPK, CreateGenderDescriptionForm form);
    
    CommandResult<GetGenderDescriptionsResult> getGenderDescriptions(UserVisitPK userVisitPK, GetGenderDescriptionsForm form);
    
    CommandResult<EditGenderDescriptionResult> editGenderDescription(UserVisitPK userVisitPK, EditGenderDescriptionForm form);
    
    CommandResult<VoidResult> deleteGenderDescription(UserVisitPK userVisitPK, DeleteGenderDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Birthday Formats
    // --------------------------------------------------------------------------------

    CommandResult<VoidResult> createBirthdayFormat(UserVisitPK userVisitPK, CreateBirthdayFormatForm form);

    CommandResult<GetBirthdayFormatChoicesResult> getBirthdayFormatChoices(UserVisitPK userVisitPK, GetBirthdayFormatChoicesForm form);

    CommandResult<GetBirthdayFormatResult> getBirthdayFormat(UserVisitPK userVisitPK, GetBirthdayFormatForm form);

    CommandResult<GetBirthdayFormatsResult> getBirthdayFormats(UserVisitPK userVisitPK, GetBirthdayFormatsForm form);

    CommandResult<VoidResult> setDefaultBirthdayFormat(UserVisitPK userVisitPK, SetDefaultBirthdayFormatForm form);

    CommandResult<EditBirthdayFormatResult> editBirthdayFormat(UserVisitPK userVisitPK, EditBirthdayFormatForm form);

    CommandResult<VoidResult> deleteBirthdayFormat(UserVisitPK userVisitPK, DeleteBirthdayFormatForm form);

    // --------------------------------------------------------------------------------
    //   Birthday Format Descriptions
    // --------------------------------------------------------------------------------

    CommandResult<VoidResult> createBirthdayFormatDescription(UserVisitPK userVisitPK, CreateBirthdayFormatDescriptionForm form);

    CommandResult<GetBirthdayFormatDescriptionResult> getBirthdayFormatDescription(UserVisitPK userVisitPK, GetBirthdayFormatDescriptionForm form);

    CommandResult<GetBirthdayFormatDescriptionsResult> getBirthdayFormatDescriptions(UserVisitPK userVisitPK, GetBirthdayFormatDescriptionsForm form);

    CommandResult<EditBirthdayFormatDescriptionResult> editBirthdayFormatDescription(UserVisitPK userVisitPK, EditBirthdayFormatDescriptionForm form);

    CommandResult<VoidResult> deleteBirthdayFormatDescription(UserVisitPK userVisitPK, DeleteBirthdayFormatDescriptionForm form);

    // --------------------------------------------------------------------------------
    //   Profiles
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createProfile(UserVisitPK userVisitPK, CreateProfileForm form);
    
    CommandResult<EditProfileResult> editProfile(UserVisitPK userVisitPK, EditProfileForm form);
    
    // --------------------------------------------------------------------------------
    //   Party Alias Types
    // --------------------------------------------------------------------------------

    CommandResult<CreatePartyAliasTypeResult> createPartyAliasType(UserVisitPK userVisitPK, CreatePartyAliasTypeForm form);

    CommandResult<GetPartyAliasTypeChoicesResult> getPartyAliasTypeChoices(UserVisitPK userVisitPK, GetPartyAliasTypeChoicesForm form);

    CommandResult<GetPartyAliasTypeResult> getPartyAliasType(UserVisitPK userVisitPK, GetPartyAliasTypeForm form);

    CommandResult<GetPartyAliasTypesResult> getPartyAliasTypes(UserVisitPK userVisitPK, GetPartyAliasTypesForm form);

    CommandResult<VoidResult> setDefaultPartyAliasType(UserVisitPK userVisitPK, SetDefaultPartyAliasTypeForm form);

    CommandResult<EditPartyAliasTypeResult> editPartyAliasType(UserVisitPK userVisitPK, EditPartyAliasTypeForm form);

    CommandResult<VoidResult> deletePartyAliasType(UserVisitPK userVisitPK, DeletePartyAliasTypeForm form);

    // --------------------------------------------------------------------------------
    //   Party Alias Type Descriptions
    // --------------------------------------------------------------------------------

    CommandResult<VoidResult> createPartyAliasTypeDescription(UserVisitPK userVisitPK, CreatePartyAliasTypeDescriptionForm form);

    CommandResult<GetPartyAliasTypeDescriptionResult> getPartyAliasTypeDescription(UserVisitPK userVisitPK, GetPartyAliasTypeDescriptionForm form);

    CommandResult<GetPartyAliasTypeDescriptionsResult> getPartyAliasTypeDescriptions(UserVisitPK userVisitPK, GetPartyAliasTypeDescriptionsForm form);

    CommandResult<EditPartyAliasTypeDescriptionResult> editPartyAliasTypeDescription(UserVisitPK userVisitPK, EditPartyAliasTypeDescriptionForm form);

    CommandResult<VoidResult> deletePartyAliasTypeDescription(UserVisitPK userVisitPK, DeletePartyAliasTypeDescriptionForm form);

    // --------------------------------------------------------------------------------
    //   Party Aliases
    // --------------------------------------------------------------------------------

    CommandResult<VoidResult> createPartyAlias(UserVisitPK userVisitPK, CreatePartyAliasForm form);

    CommandResult<GetPartyAliasResult> getPartyAlias(UserVisitPK userVisitPK, GetPartyAliasForm form);

    CommandResult<GetPartyAliasesResult> getPartyAliases(UserVisitPK userVisitPK, GetPartyAliasesForm form);

    CommandResult<EditPartyAliasResult> editPartyAlias(UserVisitPK userVisitPK, EditPartyAliasForm form);

    CommandResult<VoidResult> deletePartyAlias(UserVisitPK userVisitPK, DeletePartyAliasForm form);

    // -------------------------------------------------------------------------
    //   Parties
    // -------------------------------------------------------------------------

    CommandResult<GetPartyResult> getParty(UserVisitPK userVisitPK, GetPartyForm form);

    CommandResult<GetPartiesResult> getParties(UserVisitPK userVisitPK, GetPartiesForm form);

    // -------------------------------------------------------------------------
    //   Party Entity Types
    // -------------------------------------------------------------------------

    CommandResult<VoidResult> createPartyEntityType(UserVisitPK userVisitPK, CreatePartyEntityTypeForm form);

    CommandResult<EditPartyEntityTypeResult> editPartyEntityType(UserVisitPK userVisitPK, EditPartyEntityTypeForm form);

    CommandResult<GetPartyEntityTypeResult> getPartyEntityType(UserVisitPK userVisitPK, GetPartyEntityTypeForm form);

    CommandResult<GetPartyEntityTypesResult> getPartyEntityTypes(UserVisitPK userVisitPK, GetPartyEntityTypesForm form);

    CommandResult<VoidResult> deletePartyEntityType(UserVisitPK userVisitPK, DeletePartyEntityTypeForm form);

    // --------------------------------------------------------------------------------
    //   Party Application Editor Uses
    // --------------------------------------------------------------------------------

    CommandResult<VoidResult> createPartyApplicationEditorUse(UserVisitPK userVisitPK, CreatePartyApplicationEditorUseForm form);

    CommandResult<GetPartyApplicationEditorUseResult> getPartyApplicationEditorUse(UserVisitPK userVisitPK, GetPartyApplicationEditorUseForm form);

    CommandResult<GetPartyApplicationEditorUsesResult> getPartyApplicationEditorUses(UserVisitPK userVisitPK, GetPartyApplicationEditorUsesForm form);

    CommandResult<EditPartyApplicationEditorUseResult> editPartyApplicationEditorUse(UserVisitPK userVisitPK, EditPartyApplicationEditorUseForm form);

    CommandResult<VoidResult> deletePartyApplicationEditorUse(UserVisitPK userVisitPK, DeletePartyApplicationEditorUseForm form);

}
