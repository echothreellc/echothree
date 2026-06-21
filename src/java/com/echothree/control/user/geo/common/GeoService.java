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

public interface GeoService
        extends GeoForms {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    String ping();
    
    // -------------------------------------------------------------------------
    //   Geo Code Types
    // -------------------------------------------------------------------------
    
    CommandResult<?> createGeoCodeType(UserVisitPK userVisitPK, CreateGeoCodeTypeForm form);

    CommandResult<GetGeoCodeTypeChoicesResult> getGeoCodeTypeChoices(UserVisitPK userVisitPK, GetGeoCodeTypeChoicesForm form);

    CommandResult<GetGeoCodeTypeResult> getGeoCodeType(UserVisitPK userVisitPK, GetGeoCodeTypeForm form);

    CommandResult<GetGeoCodeTypesResult> getGeoCodeTypes(UserVisitPK userVisitPK, GetGeoCodeTypesForm form);

    CommandResult<?> setDefaultGeoCodeType(UserVisitPK userVisitPK, SetDefaultGeoCodeTypeForm form);

    CommandResult<EditGeoCodeTypeResult> editGeoCodeType(UserVisitPK userVisitPK, EditGeoCodeTypeForm form);

    CommandResult<?> deleteGeoCodeType(UserVisitPK userVisitPK, DeleteGeoCodeTypeForm form);
    
    // -------------------------------------------------------------------------
    //   Geo Code Type Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult<?> createGeoCodeTypeDescription(UserVisitPK userVisitPK, CreateGeoCodeTypeDescriptionForm form);

    CommandResult<GetGeoCodeTypeDescriptionResult> getGeoCodeTypeDescription(UserVisitPK userVisitPK, GetGeoCodeTypeDescriptionForm form);

    CommandResult<GetGeoCodeTypeDescriptionsResult> getGeoCodeTypeDescriptions(UserVisitPK userVisitPK, GetGeoCodeTypeDescriptionsForm form);

    CommandResult<EditGeoCodeTypeDescriptionResult> editGeoCodeTypeDescription(UserVisitPK userVisitPK, EditGeoCodeTypeDescriptionForm form);

    CommandResult<?> deleteGeoCodeTypeDescription(UserVisitPK userVisitPK, DeleteGeoCodeTypeDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Geo Code Scopes
    // -------------------------------------------------------------------------
    
    CommandResult<?> createGeoCodeScope(UserVisitPK userVisitPK, CreateGeoCodeScopeForm form);

    CommandResult<GetGeoCodeScopeChoicesResult> getGeoCodeScopeChoices(UserVisitPK userVisitPK, GetGeoCodeScopeChoicesForm form);

    CommandResult<GetGeoCodeScopeResult> getGeoCodeScope(UserVisitPK userVisitPK, GetGeoCodeScopeForm form);

    CommandResult<GetGeoCodeScopesResult> getGeoCodeScopes(UserVisitPK userVisitPK, GetGeoCodeScopesForm form);

    CommandResult<?> setDefaultGeoCodeScope(UserVisitPK userVisitPK, SetDefaultGeoCodeScopeForm form);

    CommandResult<EditGeoCodeScopeResult> editGeoCodeScope(UserVisitPK userVisitPK, EditGeoCodeScopeForm form);

    CommandResult<?> deleteGeoCodeScope(UserVisitPK userVisitPK, DeleteGeoCodeScopeForm form);
    
    // -------------------------------------------------------------------------
    //   Geo Code Scope Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult<?> createGeoCodeScopeDescription(UserVisitPK userVisitPK, CreateGeoCodeScopeDescriptionForm form);

    CommandResult<GetGeoCodeScopeDescriptionResult> getGeoCodeScopeDescription(UserVisitPK userVisitPK, GetGeoCodeScopeDescriptionForm form);

    CommandResult<GetGeoCodeScopeDescriptionsResult> getGeoCodeScopeDescriptions(UserVisitPK userVisitPK, GetGeoCodeScopeDescriptionsForm form);

    CommandResult<EditGeoCodeScopeDescriptionResult> editGeoCodeScopeDescription(UserVisitPK userVisitPK, EditGeoCodeScopeDescriptionForm form);

    CommandResult<?> deleteGeoCodeScopeDescription(UserVisitPK userVisitPK, DeleteGeoCodeScopeDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Geo Code Alias Types
    // -------------------------------------------------------------------------
    
    CommandResult<?> createGeoCodeAliasType(UserVisitPK userVisitPK, CreateGeoCodeAliasTypeForm form);

    CommandResult<GetGeoCodeAliasTypeChoicesResult> getGeoCodeAliasTypeChoices(UserVisitPK userVisitPK, GetGeoCodeAliasTypeChoicesForm form);

    CommandResult<GetGeoCodeAliasTypeResult> getGeoCodeAliasType(UserVisitPK userVisitPK, GetGeoCodeAliasTypeForm form);

    CommandResult<GetGeoCodeAliasTypesResult> getGeoCodeAliasTypes(UserVisitPK userVisitPK, GetGeoCodeAliasTypesForm form);

    CommandResult<?> setDefaultGeoCodeAliasType(UserVisitPK userVisitPK, SetDefaultGeoCodeAliasTypeForm form);

    CommandResult<EditGeoCodeAliasTypeResult> editGeoCodeAliasType(UserVisitPK userVisitPK, EditGeoCodeAliasTypeForm form);

    CommandResult<?> deleteGeoCodeAliasType(UserVisitPK userVisitPK, DeleteGeoCodeAliasTypeForm form);

    // -------------------------------------------------------------------------
    //   Geo Code Alias Type Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult<?> createGeoCodeAliasTypeDescription(UserVisitPK userVisitPK, CreateGeoCodeAliasTypeDescriptionForm form);

    CommandResult<GetGeoCodeAliasTypeDescriptionResult> getGeoCodeAliasTypeDescription(UserVisitPK userVisitPK, GetGeoCodeAliasTypeDescriptionForm form);

    CommandResult<GetGeoCodeAliasTypeDescriptionsResult> getGeoCodeAliasTypeDescriptions(UserVisitPK userVisitPK, GetGeoCodeAliasTypeDescriptionsForm form);

    CommandResult<EditGeoCodeAliasTypeDescriptionResult> editGeoCodeAliasTypeDescription(UserVisitPK userVisitPK, EditGeoCodeAliasTypeDescriptionForm form);

    CommandResult<?> deleteGeoCodeAliasTypeDescription(UserVisitPK userVisitPK, DeleteGeoCodeAliasTypeDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Geo Codes
    // -------------------------------------------------------------------------
    
    CommandResult<GetGeoCodeResult> getGeoCode(UserVisitPK userVisitPK, GetGeoCodeForm form);

    CommandResult<?> setDefaultGeoCode(UserVisitPK userVisitPK, SetDefaultGeoCodeForm form);

    CommandResult<?> deleteGeoCode(UserVisitPK userVisitPK, DeleteGeoCodeForm form);
    
    // --------------------------------------------------------------------------------
    //   Geo Code Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult<?> createGeoCodeDescription(UserVisitPK userVisitPK, CreateGeoCodeDescriptionForm form);
    
    CommandResult<GetGeoCodeDescriptionResult> getGeoCodeDescription(UserVisitPK userVisitPK, GetGeoCodeDescriptionForm form);

    CommandResult<GetGeoCodeDescriptionsResult> getGeoCodeDescriptions(UserVisitPK userVisitPK, GetGeoCodeDescriptionsForm form);
    
    CommandResult<EditGeoCodeDescriptionResult> editGeoCodeDescription(UserVisitPK userVisitPK, EditGeoCodeDescriptionForm form);
    
    CommandResult<?> deleteGeoCodeDescription(UserVisitPK userVisitPK, DeleteGeoCodeDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Geo Code Aliases
    // -------------------------------------------------------------------------

    CommandResult<?> createGeoCodeAlias(UserVisitPK userVisitPK, CreateGeoCodeAliasForm form);

    CommandResult<GetGeoCodeAliasResult> getGeoCodeAlias(UserVisitPK userVisitPK, GetGeoCodeAliasForm form);

    CommandResult<GetGeoCodeAliasesResult> getGeoCodeAliases(UserVisitPK userVisitPK, GetGeoCodeAliasesForm form);

    CommandResult<EditGeoCodeAliasResult> editGeoCodeAlias(UserVisitPK userVisitPK, EditGeoCodeAliasForm form);

    CommandResult<?> deleteGeoCodeAlias(UserVisitPK userVisitPK, DeleteGeoCodeAliasForm form);

    // -------------------------------------------------------------------------
    //   Geo Code Languages
    // -------------------------------------------------------------------------
    
    CommandResult<?> createGeoCodeLanguage(UserVisitPK userVisitPK, CreateGeoCodeLanguageForm form);
    
    CommandResult<GetGeoCodeLanguageResult> getGeoCodeLanguage(UserVisitPK userVisitPK, GetGeoCodeLanguageForm form);

    CommandResult<GetGeoCodeLanguagesResult> getGeoCodeLanguages(UserVisitPK userVisitPK, GetGeoCodeLanguagesForm form);

    CommandResult<?> setDefaultGeoCodeLanguage(UserVisitPK userVisitPK, SetDefaultGeoCodeLanguageForm form);
    
    CommandResult<EditGeoCodeLanguageResult> editGeoCodeLanguage(UserVisitPK userVisitPK, EditGeoCodeLanguageForm form);
    
    CommandResult<?> deleteGeoCodeLanguage(UserVisitPK userVisitPK, DeleteGeoCodeLanguageForm form);
    
    // -------------------------------------------------------------------------
    //   Geo Code Currencies
    // -------------------------------------------------------------------------
    
    CommandResult<?> createGeoCodeCurrency(UserVisitPK userVisitPK, CreateGeoCodeCurrencyForm form);
    
    CommandResult<GetGeoCodeCurrencyResult> getGeoCodeCurrency(UserVisitPK userVisitPK, GetGeoCodeCurrencyForm form);

    CommandResult<GetGeoCodeCurrenciesResult> getGeoCodeCurrencies(UserVisitPK userVisitPK, GetGeoCodeCurrenciesForm form);
    
    CommandResult<?> setDefaultGeoCodeCurrency(UserVisitPK userVisitPK, SetDefaultGeoCodeCurrencyForm form);
    
    CommandResult<EditGeoCodeCurrencyResult> editGeoCodeCurrency(UserVisitPK userVisitPK, EditGeoCodeCurrencyForm form);
    
    CommandResult<?> deleteGeoCodeCurrency(UserVisitPK userVisitPK, DeleteGeoCodeCurrencyForm form);
    
    // -------------------------------------------------------------------------
    //   Geo Code Time Zones
    // -------------------------------------------------------------------------
    
    CommandResult<?> createGeoCodeTimeZone(UserVisitPK userVisitPK, CreateGeoCodeTimeZoneForm form);

    CommandResult<GetGeoCodeTimeZoneResult> getGeoCodeTimeZone(UserVisitPK userVisitPK, GetGeoCodeTimeZoneForm form);

    CommandResult<GetGeoCodeTimeZonesResult> getGeoCodeTimeZones(UserVisitPK userVisitPK, GetGeoCodeTimeZonesForm form);
    
    CommandResult<?> setDefaultGeoCodeTimeZone(UserVisitPK userVisitPK, SetDefaultGeoCodeTimeZoneForm form);
    
    CommandResult<EditGeoCodeTimeZoneResult> editGeoCodeTimeZone(UserVisitPK userVisitPK, EditGeoCodeTimeZoneForm form);
    
    CommandResult<?> deleteGeoCodeTimeZone(UserVisitPK userVisitPK, DeleteGeoCodeTimeZoneForm form);
    
    // -------------------------------------------------------------------------
    //   Geo Code Date Time Formats
    // -------------------------------------------------------------------------
    
    CommandResult<?> createGeoCodeDateTimeFormat(UserVisitPK userVisitPK, CreateGeoCodeDateTimeFormatForm form);

    CommandResult<GetGeoCodeDateTimeFormatResult> getGeoCodeDateTimeFormat(UserVisitPK userVisitPK, GetGeoCodeDateTimeFormatForm form);

    CommandResult<GetGeoCodeDateTimeFormatsResult> getGeoCodeDateTimeFormats(UserVisitPK userVisitPK, GetGeoCodeDateTimeFormatsForm form);
    
    CommandResult<?> setDefaultGeoCodeDateTimeFormat(UserVisitPK userVisitPK, SetDefaultGeoCodeDateTimeFormatForm form);
    
    CommandResult<EditGeoCodeDateTimeFormatResult> editGeoCodeDateTimeFormat(UserVisitPK userVisitPK, EditGeoCodeDateTimeFormatForm form);
    
    CommandResult<?> deleteGeoCodeDateTimeFormat(UserVisitPK userVisitPK, DeleteGeoCodeDateTimeFormatForm form);
    
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
    
    CommandResult<?> addCityToCounty(UserVisitPK userVisitPK, AddCityToCountyForm form);
    
    CommandResult<?> addZipCodeToCity(UserVisitPK userVisitPK, AddZipCodeToCityForm form);
    
    CommandResult<GetCityResult> getCity(UserVisitPK userVisitPK, GetCityForm form);
    
    CommandResult<GetCitiesResult> getCities(UserVisitPK userVisitPK, GetCitiesForm form);
    
    // -------------------------------------------------------------------------
    //   Zip Codes
    // -------------------------------------------------------------------------
    
    CommandResult<CreateZipCodeResult> createZipCode(UserVisitPK userVisitPK, CreateZipCodeForm form);
    
    CommandResult<GetZipCodeResult> getZipCode(UserVisitPK userVisitPK, GetZipCodeForm form);
    
    CommandResult<GetZipCodesResult> getZipCodes(UserVisitPK userVisitPK, GetZipCodesForm form);
    
}
