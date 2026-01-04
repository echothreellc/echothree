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
    
    CommandResult createGeoCodeType(UserVisitPK userVisitPK, CreateGeoCodeTypeForm form);

    CommandResult getGeoCodeTypeChoices(UserVisitPK userVisitPK, GetGeoCodeTypeChoicesForm form);

    CommandResult getGeoCodeType(UserVisitPK userVisitPK, GetGeoCodeTypeForm form);

    CommandResult getGeoCodeTypes(UserVisitPK userVisitPK, GetGeoCodeTypesForm form);

    CommandResult setDefaultGeoCodeType(UserVisitPK userVisitPK, SetDefaultGeoCodeTypeForm form);

    CommandResult editGeoCodeType(UserVisitPK userVisitPK, EditGeoCodeTypeForm form);

    CommandResult deleteGeoCodeType(UserVisitPK userVisitPK, DeleteGeoCodeTypeForm form);
    
    // -------------------------------------------------------------------------
    //   Geo Code Type Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult createGeoCodeTypeDescription(UserVisitPK userVisitPK, CreateGeoCodeTypeDescriptionForm form);

    CommandResult getGeoCodeTypeDescription(UserVisitPK userVisitPK, GetGeoCodeTypeDescriptionForm form);

    CommandResult getGeoCodeTypeDescriptions(UserVisitPK userVisitPK, GetGeoCodeTypeDescriptionsForm form);

    CommandResult editGeoCodeTypeDescription(UserVisitPK userVisitPK, EditGeoCodeTypeDescriptionForm form);

    CommandResult deleteGeoCodeTypeDescription(UserVisitPK userVisitPK, DeleteGeoCodeTypeDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Geo Code Scopes
    // -------------------------------------------------------------------------
    
    CommandResult createGeoCodeScope(UserVisitPK userVisitPK, CreateGeoCodeScopeForm form);

    CommandResult getGeoCodeScopeChoices(UserVisitPK userVisitPK, GetGeoCodeScopeChoicesForm form);

    CommandResult getGeoCodeScope(UserVisitPK userVisitPK, GetGeoCodeScopeForm form);

    CommandResult getGeoCodeScopes(UserVisitPK userVisitPK, GetGeoCodeScopesForm form);

    CommandResult setDefaultGeoCodeScope(UserVisitPK userVisitPK, SetDefaultGeoCodeScopeForm form);

    CommandResult editGeoCodeScope(UserVisitPK userVisitPK, EditGeoCodeScopeForm form);

    CommandResult deleteGeoCodeScope(UserVisitPK userVisitPK, DeleteGeoCodeScopeForm form);
    
    // -------------------------------------------------------------------------
    //   Geo Code Scope Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult createGeoCodeScopeDescription(UserVisitPK userVisitPK, CreateGeoCodeScopeDescriptionForm form);

    CommandResult getGeoCodeScopeDescription(UserVisitPK userVisitPK, GetGeoCodeScopeDescriptionForm form);

    CommandResult getGeoCodeScopeDescriptions(UserVisitPK userVisitPK, GetGeoCodeScopeDescriptionsForm form);

    CommandResult editGeoCodeScopeDescription(UserVisitPK userVisitPK, EditGeoCodeScopeDescriptionForm form);

    CommandResult deleteGeoCodeScopeDescription(UserVisitPK userVisitPK, DeleteGeoCodeScopeDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Geo Code Alias Types
    // -------------------------------------------------------------------------
    
    CommandResult createGeoCodeAliasType(UserVisitPK userVisitPK, CreateGeoCodeAliasTypeForm form);

    CommandResult getGeoCodeAliasTypeChoices(UserVisitPK userVisitPK, GetGeoCodeAliasTypeChoicesForm form);

    CommandResult getGeoCodeAliasType(UserVisitPK userVisitPK, GetGeoCodeAliasTypeForm form);

    CommandResult getGeoCodeAliasTypes(UserVisitPK userVisitPK, GetGeoCodeAliasTypesForm form);

    CommandResult setDefaultGeoCodeAliasType(UserVisitPK userVisitPK, SetDefaultGeoCodeAliasTypeForm form);

    CommandResult editGeoCodeAliasType(UserVisitPK userVisitPK, EditGeoCodeAliasTypeForm form);

    CommandResult deleteGeoCodeAliasType(UserVisitPK userVisitPK, DeleteGeoCodeAliasTypeForm form);

    // -------------------------------------------------------------------------
    //   Geo Code Alias Type Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult createGeoCodeAliasTypeDescription(UserVisitPK userVisitPK, CreateGeoCodeAliasTypeDescriptionForm form);

    CommandResult getGeoCodeAliasTypeDescription(UserVisitPK userVisitPK, GetGeoCodeAliasTypeDescriptionForm form);

    CommandResult getGeoCodeAliasTypeDescriptions(UserVisitPK userVisitPK, GetGeoCodeAliasTypeDescriptionsForm form);

    CommandResult editGeoCodeAliasTypeDescription(UserVisitPK userVisitPK, EditGeoCodeAliasTypeDescriptionForm form);

    CommandResult deleteGeoCodeAliasTypeDescription(UserVisitPK userVisitPK, DeleteGeoCodeAliasTypeDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Geo Codes
    // -------------------------------------------------------------------------
    
    CommandResult getGeoCode(UserVisitPK userVisitPK, GetGeoCodeForm form);

    CommandResult setDefaultGeoCode(UserVisitPK userVisitPK, SetDefaultGeoCodeForm form);

    CommandResult deleteGeoCode(UserVisitPK userVisitPK, DeleteGeoCodeForm form);
    
    // --------------------------------------------------------------------------------
    //   Geo Code Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult createGeoCodeDescription(UserVisitPK userVisitPK, CreateGeoCodeDescriptionForm form);
    
    CommandResult getGeoCodeDescription(UserVisitPK userVisitPK, GetGeoCodeDescriptionForm form);

    CommandResult getGeoCodeDescriptions(UserVisitPK userVisitPK, GetGeoCodeDescriptionsForm form);
    
    CommandResult editGeoCodeDescription(UserVisitPK userVisitPK, EditGeoCodeDescriptionForm form);
    
    CommandResult deleteGeoCodeDescription(UserVisitPK userVisitPK, DeleteGeoCodeDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Geo Code Aliases
    // -------------------------------------------------------------------------

    CommandResult createGeoCodeAlias(UserVisitPK userVisitPK, CreateGeoCodeAliasForm form);

    CommandResult getGeoCodeAlias(UserVisitPK userVisitPK, GetGeoCodeAliasForm form);

    CommandResult getGeoCodeAliases(UserVisitPK userVisitPK, GetGeoCodeAliasesForm form);

    CommandResult editGeoCodeAlias(UserVisitPK userVisitPK, EditGeoCodeAliasForm form);

    CommandResult deleteGeoCodeAlias(UserVisitPK userVisitPK, DeleteGeoCodeAliasForm form);

    // -------------------------------------------------------------------------
    //   Geo Code Languages
    // -------------------------------------------------------------------------
    
    CommandResult createGeoCodeLanguage(UserVisitPK userVisitPK, CreateGeoCodeLanguageForm form);
    
    CommandResult getGeoCodeLanguage(UserVisitPK userVisitPK, GetGeoCodeLanguageForm form);

    CommandResult getGeoCodeLanguages(UserVisitPK userVisitPK, GetGeoCodeLanguagesForm form);

    CommandResult setDefaultGeoCodeLanguage(UserVisitPK userVisitPK, SetDefaultGeoCodeLanguageForm form);
    
    CommandResult editGeoCodeLanguage(UserVisitPK userVisitPK, EditGeoCodeLanguageForm form);
    
    CommandResult deleteGeoCodeLanguage(UserVisitPK userVisitPK, DeleteGeoCodeLanguageForm form);
    
    // -------------------------------------------------------------------------
    //   Geo Code Currencies
    // -------------------------------------------------------------------------
    
    CommandResult createGeoCodeCurrency(UserVisitPK userVisitPK, CreateGeoCodeCurrencyForm form);
    
    CommandResult getGeoCodeCurrency(UserVisitPK userVisitPK, GetGeoCodeCurrencyForm form);

    CommandResult getGeoCodeCurrencies(UserVisitPK userVisitPK, GetGeoCodeCurrenciesForm form);
    
    CommandResult setDefaultGeoCodeCurrency(UserVisitPK userVisitPK, SetDefaultGeoCodeCurrencyForm form);
    
    CommandResult editGeoCodeCurrency(UserVisitPK userVisitPK, EditGeoCodeCurrencyForm form);
    
    CommandResult deleteGeoCodeCurrency(UserVisitPK userVisitPK, DeleteGeoCodeCurrencyForm form);
    
    // -------------------------------------------------------------------------
    //   Geo Code Time Zones
    // -------------------------------------------------------------------------
    
    CommandResult createGeoCodeTimeZone(UserVisitPK userVisitPK, CreateGeoCodeTimeZoneForm form);

    CommandResult getGeoCodeTimeZone(UserVisitPK userVisitPK, GetGeoCodeTimeZoneForm form);

    CommandResult getGeoCodeTimeZones(UserVisitPK userVisitPK, GetGeoCodeTimeZonesForm form);
    
    CommandResult setDefaultGeoCodeTimeZone(UserVisitPK userVisitPK, SetDefaultGeoCodeTimeZoneForm form);
    
    CommandResult editGeoCodeTimeZone(UserVisitPK userVisitPK, EditGeoCodeTimeZoneForm form);
    
    CommandResult deleteGeoCodeTimeZone(UserVisitPK userVisitPK, DeleteGeoCodeTimeZoneForm form);
    
    // -------------------------------------------------------------------------
    //   Geo Code Date Time Formats
    // -------------------------------------------------------------------------
    
    CommandResult createGeoCodeDateTimeFormat(UserVisitPK userVisitPK, CreateGeoCodeDateTimeFormatForm form);

    CommandResult getGeoCodeDateTimeFormat(UserVisitPK userVisitPK, GetGeoCodeDateTimeFormatForm form);

    CommandResult getGeoCodeDateTimeFormats(UserVisitPK userVisitPK, GetGeoCodeDateTimeFormatsForm form);
    
    CommandResult setDefaultGeoCodeDateTimeFormat(UserVisitPK userVisitPK, SetDefaultGeoCodeDateTimeFormatForm form);
    
    CommandResult editGeoCodeDateTimeFormat(UserVisitPK userVisitPK, EditGeoCodeDateTimeFormatForm form);
    
    CommandResult deleteGeoCodeDateTimeFormat(UserVisitPK userVisitPK, DeleteGeoCodeDateTimeFormatForm form);
    
    // -------------------------------------------------------------------------
    //   Countries
    // -------------------------------------------------------------------------
    
    CommandResult createCountry(UserVisitPK userVisitPK, CreateCountryForm form);
    
    CommandResult getCountry(UserVisitPK userVisitPK, GetCountryForm form);
    
    CommandResult getCountries(UserVisitPK userVisitPK, GetCountriesForm form);
    
    CommandResult getCountryChoices(UserVisitPK userVisitPK, GetCountryChoicesForm form);
    
    CommandResult editCountry(UserVisitPK userVisitPK, EditCountryForm form);

    // -------------------------------------------------------------------------
    //   States
    // -------------------------------------------------------------------------
    
    CommandResult createState(UserVisitPK userVisitPK, CreateStateForm form);
    
    CommandResult getState(UserVisitPK userVisitPK, GetStateForm form);
    
    CommandResult getStates(UserVisitPK userVisitPK, GetStatesForm form);
    
    // -------------------------------------------------------------------------
    //   Counties
    // -------------------------------------------------------------------------
    
    CommandResult createCounty(UserVisitPK userVisitPK, CreateCountyForm form);
    
    CommandResult getCounty(UserVisitPK userVisitPK, GetCountyForm form);
    
    CommandResult getCounties(UserVisitPK userVisitPK, GetCountiesForm form);
    
    // -------------------------------------------------------------------------
    //   Cities
    // -------------------------------------------------------------------------
    
    CommandResult createCity(UserVisitPK userVisitPK, CreateCityForm form);
    
    CommandResult addCityToCounty(UserVisitPK userVisitPK, AddCityToCountyForm form);
    
    CommandResult addZipCodeToCity(UserVisitPK userVisitPK, AddZipCodeToCityForm form);
    
    CommandResult getCity(UserVisitPK userVisitPK, GetCityForm form);
    
    CommandResult getCities(UserVisitPK userVisitPK, GetCitiesForm form);
    
    // -------------------------------------------------------------------------
    //   Zip Codes
    // -------------------------------------------------------------------------
    
    CommandResult createZipCode(UserVisitPK userVisitPK, CreateZipCodeForm form);
    
    CommandResult getZipCode(UserVisitPK userVisitPK, GetZipCodeForm form);
    
    CommandResult getZipCodes(UserVisitPK userVisitPK, GetZipCodesForm form);
    
}
