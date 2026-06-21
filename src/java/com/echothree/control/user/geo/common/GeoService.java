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

package com.echothree.control.user.geo.common;

import com.echothree.control.user.geo.common.form.*;
import com.echothree.control.user.geo.common.result.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.VoidResult;

public interface GeoService
        extends GeoForms {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    String ping();
    
    // -------------------------------------------------------------------------
    //   Geo Code Types
    // -------------------------------------------------------------------------
    
    CommandResult<VoidResult> createGeoCodeType(UserVisitPK userVisitPK, CreateGeoCodeTypeForm form);

    CommandResult<GetGeoCodeTypeChoicesResult> getGeoCodeTypeChoices(UserVisitPK userVisitPK, GetGeoCodeTypeChoicesForm form);

    CommandResult<GetGeoCodeTypeResult> getGeoCodeType(UserVisitPK userVisitPK, GetGeoCodeTypeForm form);

    CommandResult<GetGeoCodeTypesResult> getGeoCodeTypes(UserVisitPK userVisitPK, GetGeoCodeTypesForm form);

    CommandResult<VoidResult> setDefaultGeoCodeType(UserVisitPK userVisitPK, SetDefaultGeoCodeTypeForm form);

    CommandResult<EditGeoCodeTypeResult> editGeoCodeType(UserVisitPK userVisitPK, EditGeoCodeTypeForm form);

    CommandResult<VoidResult> deleteGeoCodeType(UserVisitPK userVisitPK, DeleteGeoCodeTypeForm form);
    
    // -------------------------------------------------------------------------
    //   Geo Code Type Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult<VoidResult> createGeoCodeTypeDescription(UserVisitPK userVisitPK, CreateGeoCodeTypeDescriptionForm form);

    CommandResult<GetGeoCodeTypeDescriptionResult> getGeoCodeTypeDescription(UserVisitPK userVisitPK, GetGeoCodeTypeDescriptionForm form);

    CommandResult<GetGeoCodeTypeDescriptionsResult> getGeoCodeTypeDescriptions(UserVisitPK userVisitPK, GetGeoCodeTypeDescriptionsForm form);

    CommandResult<EditGeoCodeTypeDescriptionResult> editGeoCodeTypeDescription(UserVisitPK userVisitPK, EditGeoCodeTypeDescriptionForm form);

    CommandResult<VoidResult> deleteGeoCodeTypeDescription(UserVisitPK userVisitPK, DeleteGeoCodeTypeDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Geo Code Scopes
    // -------------------------------------------------------------------------
    
    CommandResult<VoidResult> createGeoCodeScope(UserVisitPK userVisitPK, CreateGeoCodeScopeForm form);

    CommandResult<GetGeoCodeScopeChoicesResult> getGeoCodeScopeChoices(UserVisitPK userVisitPK, GetGeoCodeScopeChoicesForm form);

    CommandResult<GetGeoCodeScopeResult> getGeoCodeScope(UserVisitPK userVisitPK, GetGeoCodeScopeForm form);

    CommandResult<GetGeoCodeScopesResult> getGeoCodeScopes(UserVisitPK userVisitPK, GetGeoCodeScopesForm form);

    CommandResult<VoidResult> setDefaultGeoCodeScope(UserVisitPK userVisitPK, SetDefaultGeoCodeScopeForm form);

    CommandResult<EditGeoCodeScopeResult> editGeoCodeScope(UserVisitPK userVisitPK, EditGeoCodeScopeForm form);

    CommandResult<VoidResult> deleteGeoCodeScope(UserVisitPK userVisitPK, DeleteGeoCodeScopeForm form);
    
    // -------------------------------------------------------------------------
    //   Geo Code Scope Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult<VoidResult> createGeoCodeScopeDescription(UserVisitPK userVisitPK, CreateGeoCodeScopeDescriptionForm form);

    CommandResult<GetGeoCodeScopeDescriptionResult> getGeoCodeScopeDescription(UserVisitPK userVisitPK, GetGeoCodeScopeDescriptionForm form);

    CommandResult<GetGeoCodeScopeDescriptionsResult> getGeoCodeScopeDescriptions(UserVisitPK userVisitPK, GetGeoCodeScopeDescriptionsForm form);

    CommandResult<EditGeoCodeScopeDescriptionResult> editGeoCodeScopeDescription(UserVisitPK userVisitPK, EditGeoCodeScopeDescriptionForm form);

    CommandResult<VoidResult> deleteGeoCodeScopeDescription(UserVisitPK userVisitPK, DeleteGeoCodeScopeDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Geo Code Alias Types
    // -------------------------------------------------------------------------
    
    CommandResult<CreateGeoCodeAliasTypeResult> createGeoCodeAliasType(UserVisitPK userVisitPK, CreateGeoCodeAliasTypeForm form);

    CommandResult<GetGeoCodeAliasTypeChoicesResult> getGeoCodeAliasTypeChoices(UserVisitPK userVisitPK, GetGeoCodeAliasTypeChoicesForm form);

    CommandResult<GetGeoCodeAliasTypeResult> getGeoCodeAliasType(UserVisitPK userVisitPK, GetGeoCodeAliasTypeForm form);

    CommandResult<GetGeoCodeAliasTypesResult> getGeoCodeAliasTypes(UserVisitPK userVisitPK, GetGeoCodeAliasTypesForm form);

    CommandResult<VoidResult> setDefaultGeoCodeAliasType(UserVisitPK userVisitPK, SetDefaultGeoCodeAliasTypeForm form);

    CommandResult<EditGeoCodeAliasTypeResult> editGeoCodeAliasType(UserVisitPK userVisitPK, EditGeoCodeAliasTypeForm form);

    CommandResult<VoidResult> deleteGeoCodeAliasType(UserVisitPK userVisitPK, DeleteGeoCodeAliasTypeForm form);

    // -------------------------------------------------------------------------
    //   Geo Code Alias Type Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult<VoidResult> createGeoCodeAliasTypeDescription(UserVisitPK userVisitPK, CreateGeoCodeAliasTypeDescriptionForm form);

    CommandResult<GetGeoCodeAliasTypeDescriptionResult> getGeoCodeAliasTypeDescription(UserVisitPK userVisitPK, GetGeoCodeAliasTypeDescriptionForm form);

    CommandResult<GetGeoCodeAliasTypeDescriptionsResult> getGeoCodeAliasTypeDescriptions(UserVisitPK userVisitPK, GetGeoCodeAliasTypeDescriptionsForm form);

    CommandResult<EditGeoCodeAliasTypeDescriptionResult> editGeoCodeAliasTypeDescription(UserVisitPK userVisitPK, EditGeoCodeAliasTypeDescriptionForm form);

    CommandResult<VoidResult> deleteGeoCodeAliasTypeDescription(UserVisitPK userVisitPK, DeleteGeoCodeAliasTypeDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Geo Codes
    // -------------------------------------------------------------------------
    
    CommandResult<GetGeoCodeResult> getGeoCode(UserVisitPK userVisitPK, GetGeoCodeForm form);

    CommandResult<VoidResult> setDefaultGeoCode(UserVisitPK userVisitPK, SetDefaultGeoCodeForm form);

    CommandResult<VoidResult> deleteGeoCode(UserVisitPK userVisitPK, DeleteGeoCodeForm form);
    
    // --------------------------------------------------------------------------------
    //   Geo Code Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createGeoCodeDescription(UserVisitPK userVisitPK, CreateGeoCodeDescriptionForm form);
    
    CommandResult<GetGeoCodeDescriptionResult> getGeoCodeDescription(UserVisitPK userVisitPK, GetGeoCodeDescriptionForm form);

    CommandResult<GetGeoCodeDescriptionsResult> getGeoCodeDescriptions(UserVisitPK userVisitPK, GetGeoCodeDescriptionsForm form);
    
    CommandResult<EditGeoCodeDescriptionResult> editGeoCodeDescription(UserVisitPK userVisitPK, EditGeoCodeDescriptionForm form);
    
    CommandResult<VoidResult> deleteGeoCodeDescription(UserVisitPK userVisitPK, DeleteGeoCodeDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Geo Code Aliases
    // -------------------------------------------------------------------------

    CommandResult<VoidResult> createGeoCodeAlias(UserVisitPK userVisitPK, CreateGeoCodeAliasForm form);

    CommandResult<GetGeoCodeAliasResult> getGeoCodeAlias(UserVisitPK userVisitPK, GetGeoCodeAliasForm form);

    CommandResult<GetGeoCodeAliasesResult> getGeoCodeAliases(UserVisitPK userVisitPK, GetGeoCodeAliasesForm form);

    CommandResult<EditGeoCodeAliasResult> editGeoCodeAlias(UserVisitPK userVisitPK, EditGeoCodeAliasForm form);

    CommandResult<VoidResult> deleteGeoCodeAlias(UserVisitPK userVisitPK, DeleteGeoCodeAliasForm form);

    // -------------------------------------------------------------------------
    //   Geo Code Languages
    // -------------------------------------------------------------------------
    
    CommandResult<VoidResult> createGeoCodeLanguage(UserVisitPK userVisitPK, CreateGeoCodeLanguageForm form);
    
    CommandResult<GetGeoCodeLanguageResult> getGeoCodeLanguage(UserVisitPK userVisitPK, GetGeoCodeLanguageForm form);

    CommandResult<GetGeoCodeLanguagesResult> getGeoCodeLanguages(UserVisitPK userVisitPK, GetGeoCodeLanguagesForm form);

    CommandResult<VoidResult> setDefaultGeoCodeLanguage(UserVisitPK userVisitPK, SetDefaultGeoCodeLanguageForm form);
    
    CommandResult<EditGeoCodeLanguageResult> editGeoCodeLanguage(UserVisitPK userVisitPK, EditGeoCodeLanguageForm form);
    
    CommandResult<VoidResult> deleteGeoCodeLanguage(UserVisitPK userVisitPK, DeleteGeoCodeLanguageForm form);
    
    // -------------------------------------------------------------------------
    //   Geo Code Currencies
    // -------------------------------------------------------------------------
    
    CommandResult<VoidResult> createGeoCodeCurrency(UserVisitPK userVisitPK, CreateGeoCodeCurrencyForm form);
    
    CommandResult<GetGeoCodeCurrencyResult> getGeoCodeCurrency(UserVisitPK userVisitPK, GetGeoCodeCurrencyForm form);

    CommandResult<GetGeoCodeCurrenciesResult> getGeoCodeCurrencies(UserVisitPK userVisitPK, GetGeoCodeCurrenciesForm form);
    
    CommandResult<VoidResult> setDefaultGeoCodeCurrency(UserVisitPK userVisitPK, SetDefaultGeoCodeCurrencyForm form);
    
    CommandResult<EditGeoCodeCurrencyResult> editGeoCodeCurrency(UserVisitPK userVisitPK, EditGeoCodeCurrencyForm form);
    
    CommandResult<VoidResult> deleteGeoCodeCurrency(UserVisitPK userVisitPK, DeleteGeoCodeCurrencyForm form);
    
    // -------------------------------------------------------------------------
    //   Geo Code Time Zones
    // -------------------------------------------------------------------------
    
    CommandResult<VoidResult> createGeoCodeTimeZone(UserVisitPK userVisitPK, CreateGeoCodeTimeZoneForm form);

    CommandResult<GetGeoCodeTimeZoneResult> getGeoCodeTimeZone(UserVisitPK userVisitPK, GetGeoCodeTimeZoneForm form);

    CommandResult<GetGeoCodeTimeZonesResult> getGeoCodeTimeZones(UserVisitPK userVisitPK, GetGeoCodeTimeZonesForm form);
    
    CommandResult<VoidResult> setDefaultGeoCodeTimeZone(UserVisitPK userVisitPK, SetDefaultGeoCodeTimeZoneForm form);
    
    CommandResult<EditGeoCodeTimeZoneResult> editGeoCodeTimeZone(UserVisitPK userVisitPK, EditGeoCodeTimeZoneForm form);
    
    CommandResult<VoidResult> deleteGeoCodeTimeZone(UserVisitPK userVisitPK, DeleteGeoCodeTimeZoneForm form);
    
    // -------------------------------------------------------------------------
    //   Geo Code Date Time Formats
    // -------------------------------------------------------------------------
    
    CommandResult<VoidResult> createGeoCodeDateTimeFormat(UserVisitPK userVisitPK, CreateGeoCodeDateTimeFormatForm form);

    CommandResult<GetGeoCodeDateTimeFormatResult> getGeoCodeDateTimeFormat(UserVisitPK userVisitPK, GetGeoCodeDateTimeFormatForm form);

    CommandResult<GetGeoCodeDateTimeFormatsResult> getGeoCodeDateTimeFormats(UserVisitPK userVisitPK, GetGeoCodeDateTimeFormatsForm form);
    
    CommandResult<VoidResult> setDefaultGeoCodeDateTimeFormat(UserVisitPK userVisitPK, SetDefaultGeoCodeDateTimeFormatForm form);
    
    CommandResult<EditGeoCodeDateTimeFormatResult> editGeoCodeDateTimeFormat(UserVisitPK userVisitPK, EditGeoCodeDateTimeFormatForm form);
    
    CommandResult<VoidResult> deleteGeoCodeDateTimeFormat(UserVisitPK userVisitPK, DeleteGeoCodeDateTimeFormatForm form);
    
    // -------------------------------------------------------------------------
    //   Countries
    // -------------------------------------------------------------------------
    
    CommandResult<CreateCountryResult> createCountry(UserVisitPK userVisitPK, CreateCountryForm form);
    
    CommandResult<GetCountryResult> getCountry(UserVisitPK userVisitPK, GetCountryForm form);
    
    CommandResult<GetCountriesResult> getCountries(UserVisitPK userVisitPK, GetCountriesForm form);
    
    CommandResult<GetCountryChoicesResult> getCountryChoices(UserVisitPK userVisitPK, GetCountryChoicesForm form);
    
    CommandResult<EditCountryResult> editCountry(UserVisitPK userVisitPK, EditCountryForm form);

    // -------------------------------------------------------------------------
    //   States
    // -------------------------------------------------------------------------
    
    CommandResult<CreateStateResult> createState(UserVisitPK userVisitPK, CreateStateForm form);
    
    CommandResult<GetStateResult> getState(UserVisitPK userVisitPK, GetStateForm form);
    
    CommandResult<GetStatesResult> getStates(UserVisitPK userVisitPK, GetStatesForm form);
    
    // -------------------------------------------------------------------------
    //   Counties
    // -------------------------------------------------------------------------
    
    CommandResult<CreateCountyResult> createCounty(UserVisitPK userVisitPK, CreateCountyForm form);
    
    CommandResult<GetCountyResult> getCounty(UserVisitPK userVisitPK, GetCountyForm form);
    
    CommandResult<GetCountiesResult> getCounties(UserVisitPK userVisitPK, GetCountiesForm form);
    
    // -------------------------------------------------------------------------
    //   Cities
    // -------------------------------------------------------------------------
    
    CommandResult<CreateCityResult> createCity(UserVisitPK userVisitPK, CreateCityForm form);
    
    CommandResult<VoidResult> addCityToCounty(UserVisitPK userVisitPK, AddCityToCountyForm form);
    
    CommandResult<VoidResult> addZipCodeToCity(UserVisitPK userVisitPK, AddZipCodeToCityForm form);
    
    CommandResult<GetCityResult> getCity(UserVisitPK userVisitPK, GetCityForm form);
    
    CommandResult<GetCitiesResult> getCities(UserVisitPK userVisitPK, GetCitiesForm form);
    
    // -------------------------------------------------------------------------
    //   Zip Codes
    // -------------------------------------------------------------------------
    
    CommandResult<CreateZipCodeResult> createZipCode(UserVisitPK userVisitPK, CreateZipCodeForm form);
    
    CommandResult<GetZipCodeResult> getZipCode(UserVisitPK userVisitPK, GetZipCodeForm form);
    
    CommandResult<GetZipCodesResult> getZipCodes(UserVisitPK userVisitPK, GetZipCodesForm form);
    
}
