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

package com.echothree.control.user.geo.server;

import com.echothree.control.user.geo.common.GeoRemote;
import com.echothree.control.user.geo.common.form.*;
import com.echothree.control.user.geo.server.command.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import javax.ejb.Stateless;

@Stateless
public class GeoBean
        extends GeoFormsImpl
        implements GeoRemote, GeoLocal {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    @Override
    public String ping() {
        return "GeoBean is alive!";
    }
    
    // -------------------------------------------------------------------------
    //   Geo Code Types
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createGeoCodeType(UserVisitPK userVisitPK, CreateGeoCodeTypeForm form) {
        return new CreateGeoCodeTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getGeoCodeTypeChoices(UserVisitPK userVisitPK, GetGeoCodeTypeChoicesForm form) {
        return new GetGeoCodeTypeChoicesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getGeoCodeType(UserVisitPK userVisitPK, GetGeoCodeTypeForm form) {
        return new GetGeoCodeTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getGeoCodeTypes(UserVisitPK userVisitPK, GetGeoCodeTypesForm form) {
        return new GetGeoCodeTypesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultGeoCodeType(UserVisitPK userVisitPK, SetDefaultGeoCodeTypeForm form) {
        return new SetDefaultGeoCodeTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editGeoCodeType(UserVisitPK userVisitPK, EditGeoCodeTypeForm form) {
        return new EditGeoCodeTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteGeoCodeType(UserVisitPK userVisitPK, DeleteGeoCodeTypeForm form) {
        return new DeleteGeoCodeTypeCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Geo Code Type Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createGeoCodeTypeDescription(UserVisitPK userVisitPK, CreateGeoCodeTypeDescriptionForm form) {
        return new CreateGeoCodeTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getGeoCodeTypeDescription(UserVisitPK userVisitPK, GetGeoCodeTypeDescriptionForm form) {
        return new GetGeoCodeTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getGeoCodeTypeDescriptions(UserVisitPK userVisitPK, GetGeoCodeTypeDescriptionsForm form) {
        return new GetGeoCodeTypeDescriptionsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editGeoCodeTypeDescription(UserVisitPK userVisitPK, EditGeoCodeTypeDescriptionForm form) {
        return new EditGeoCodeTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteGeoCodeTypeDescription(UserVisitPK userVisitPK, DeleteGeoCodeTypeDescriptionForm form) {
        return new DeleteGeoCodeTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Geo Code Scopes
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createGeoCodeScope(UserVisitPK userVisitPK, CreateGeoCodeScopeForm form) {
        return new CreateGeoCodeScopeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getGeoCodeScopeChoices(UserVisitPK userVisitPK, GetGeoCodeScopeChoicesForm form) {
        return new GetGeoCodeScopeChoicesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getGeoCodeScope(UserVisitPK userVisitPK, GetGeoCodeScopeForm form) {
        return new GetGeoCodeScopeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getGeoCodeScopes(UserVisitPK userVisitPK, GetGeoCodeScopesForm form) {
        return new GetGeoCodeScopesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultGeoCodeScope(UserVisitPK userVisitPK, SetDefaultGeoCodeScopeForm form) {
        return new SetDefaultGeoCodeScopeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editGeoCodeScope(UserVisitPK userVisitPK, EditGeoCodeScopeForm form) {
        return new EditGeoCodeScopeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteGeoCodeScope(UserVisitPK userVisitPK, DeleteGeoCodeScopeForm form) {
        return new DeleteGeoCodeScopeCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Geo Code Scope Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createGeoCodeScopeDescription(UserVisitPK userVisitPK, CreateGeoCodeScopeDescriptionForm form) {
        return new CreateGeoCodeScopeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getGeoCodeScopeDescription(UserVisitPK userVisitPK, GetGeoCodeScopeDescriptionForm form) {
        return new GetGeoCodeScopeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getGeoCodeScopeDescriptions(UserVisitPK userVisitPK, GetGeoCodeScopeDescriptionsForm form) {
        return new GetGeoCodeScopeDescriptionsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editGeoCodeScopeDescription(UserVisitPK userVisitPK, EditGeoCodeScopeDescriptionForm form) {
        return new EditGeoCodeScopeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteGeoCodeScopeDescription(UserVisitPK userVisitPK, DeleteGeoCodeScopeDescriptionForm form) {
        return new DeleteGeoCodeScopeDescriptionCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Geo Code Alias Types
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createGeoCodeAliasType(UserVisitPK userVisitPK, CreateGeoCodeAliasTypeForm form) {
        return new CreateGeoCodeAliasTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getGeoCodeAliasTypeChoices(UserVisitPK userVisitPK, GetGeoCodeAliasTypeChoicesForm form) {
        return new GetGeoCodeAliasTypeChoicesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getGeoCodeAliasType(UserVisitPK userVisitPK, GetGeoCodeAliasTypeForm form) {
        return new GetGeoCodeAliasTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getGeoCodeAliasTypes(UserVisitPK userVisitPK, GetGeoCodeAliasTypesForm form) {
        return new GetGeoCodeAliasTypesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultGeoCodeAliasType(UserVisitPK userVisitPK, SetDefaultGeoCodeAliasTypeForm form) {
        return new SetDefaultGeoCodeAliasTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editGeoCodeAliasType(UserVisitPK userVisitPK, EditGeoCodeAliasTypeForm form) {
        return new EditGeoCodeAliasTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteGeoCodeAliasType(UserVisitPK userVisitPK, DeleteGeoCodeAliasTypeForm form) {
        return new DeleteGeoCodeAliasTypeCommand().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Geo Code Alias Type Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createGeoCodeAliasTypeDescription(UserVisitPK userVisitPK, CreateGeoCodeAliasTypeDescriptionForm form) {
        return new CreateGeoCodeAliasTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getGeoCodeAliasTypeDescription(UserVisitPK userVisitPK, GetGeoCodeAliasTypeDescriptionForm form) {
        return new GetGeoCodeAliasTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getGeoCodeAliasTypeDescriptions(UserVisitPK userVisitPK, GetGeoCodeAliasTypeDescriptionsForm form) {
        return new GetGeoCodeAliasTypeDescriptionsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editGeoCodeAliasTypeDescription(UserVisitPK userVisitPK, EditGeoCodeAliasTypeDescriptionForm form) {
        return new EditGeoCodeAliasTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteGeoCodeAliasTypeDescription(UserVisitPK userVisitPK, DeleteGeoCodeAliasTypeDescriptionForm form) {
        return new DeleteGeoCodeAliasTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Geo Codes
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult getGeoCode(UserVisitPK userVisitPK, GetGeoCodeForm form) {
        return new GetGeoCodeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultGeoCode(UserVisitPK userVisitPK, SetDefaultGeoCodeForm form) {
        return new SetDefaultGeoCodeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteGeoCode(UserVisitPK userVisitPK, DeleteGeoCodeForm form) {
        return new DeleteGeoCodeCommand().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Geo Code Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createGeoCodeDescription(UserVisitPK userVisitPK, CreateGeoCodeDescriptionForm form) {
        return new CreateGeoCodeDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getGeoCodeDescription(UserVisitPK userVisitPK, GetGeoCodeDescriptionForm form) {
        return new GetGeoCodeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getGeoCodeDescriptions(UserVisitPK userVisitPK, GetGeoCodeDescriptionsForm form) {
        return new GetGeoCodeDescriptionsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editGeoCodeDescription(UserVisitPK userVisitPK, EditGeoCodeDescriptionForm form) {
        return new EditGeoCodeDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteGeoCodeDescription(UserVisitPK userVisitPK, DeleteGeoCodeDescriptionForm form) {
        return new DeleteGeoCodeDescriptionCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Geo Code Aliases
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createGeoCodeAlias(UserVisitPK userVisitPK, CreateGeoCodeAliasForm form) {
        return new CreateGeoCodeAliasCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getGeoCodeAlias(UserVisitPK userVisitPK, GetGeoCodeAliasForm form) {
        return new GetGeoCodeAliasCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getGeoCodeAliases(UserVisitPK userVisitPK, GetGeoCodeAliasesForm form) {
        return new GetGeoCodeAliasesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editGeoCodeAlias(UserVisitPK userVisitPK, EditGeoCodeAliasForm form) {
        return new EditGeoCodeAliasCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteGeoCodeAlias(UserVisitPK userVisitPK, DeleteGeoCodeAliasForm form) {
        return new DeleteGeoCodeAliasCommand().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Geo Code Languages
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createGeoCodeLanguage(UserVisitPK userVisitPK, CreateGeoCodeLanguageForm form) {
        return new CreateGeoCodeLanguageCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getGeoCodeLanguage(UserVisitPK userVisitPK, GetGeoCodeLanguageForm form) {
        return new GetGeoCodeLanguageCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getGeoCodeLanguages(UserVisitPK userVisitPK, GetGeoCodeLanguagesForm form) {
        return new GetGeoCodeLanguagesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultGeoCodeLanguage(UserVisitPK userVisitPK, SetDefaultGeoCodeLanguageForm form) {
        return new SetDefaultGeoCodeLanguageCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editGeoCodeLanguage(UserVisitPK userVisitPK, EditGeoCodeLanguageForm form) {
        return new EditGeoCodeLanguageCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteGeoCodeLanguage(UserVisitPK userVisitPK, DeleteGeoCodeLanguageForm form) {
        return new DeleteGeoCodeLanguageCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Geo Code Currencies
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createGeoCodeCurrency(UserVisitPK userVisitPK, CreateGeoCodeCurrencyForm form) {
        return new CreateGeoCodeCurrencyCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getGeoCodeCurrency(UserVisitPK userVisitPK, GetGeoCodeCurrencyForm form) {
        return new GetGeoCodeCurrencyCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getGeoCodeCurrencies(UserVisitPK userVisitPK, GetGeoCodeCurrenciesForm form) {
        return new GetGeoCodeCurrenciesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultGeoCodeCurrency(UserVisitPK userVisitPK, SetDefaultGeoCodeCurrencyForm form) {
        return new SetDefaultGeoCodeCurrencyCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editGeoCodeCurrency(UserVisitPK userVisitPK, EditGeoCodeCurrencyForm form) {
        return new EditGeoCodeCurrencyCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteGeoCodeCurrency(UserVisitPK userVisitPK, DeleteGeoCodeCurrencyForm form) {
        return new DeleteGeoCodeCurrencyCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Geo Code Time Zones
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createGeoCodeTimeZone(UserVisitPK userVisitPK, CreateGeoCodeTimeZoneForm form) {
        return new CreateGeoCodeTimeZoneCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getGeoCodeTimeZone(UserVisitPK userVisitPK, GetGeoCodeTimeZoneForm form) {
        return new GetGeoCodeTimeZoneCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getGeoCodeTimeZones(UserVisitPK userVisitPK, GetGeoCodeTimeZonesForm form) {
        return new GetGeoCodeTimeZonesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultGeoCodeTimeZone(UserVisitPK userVisitPK, SetDefaultGeoCodeTimeZoneForm form) {
        return new SetDefaultGeoCodeTimeZoneCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editGeoCodeTimeZone(UserVisitPK userVisitPK, EditGeoCodeTimeZoneForm form) {
        return new EditGeoCodeTimeZoneCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteGeoCodeTimeZone(UserVisitPK userVisitPK, DeleteGeoCodeTimeZoneForm form) {
        return new DeleteGeoCodeTimeZoneCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Geo Code Date Time Formats
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createGeoCodeDateTimeFormat(UserVisitPK userVisitPK, CreateGeoCodeDateTimeFormatForm form) {
        return new CreateGeoCodeDateTimeFormatCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getGeoCodeDateTimeFormat(UserVisitPK userVisitPK, GetGeoCodeDateTimeFormatForm form) {
        return new GetGeoCodeDateTimeFormatCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getGeoCodeDateTimeFormats(UserVisitPK userVisitPK, GetGeoCodeDateTimeFormatsForm form) {
        return new GetGeoCodeDateTimeFormatsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultGeoCodeDateTimeFormat(UserVisitPK userVisitPK, SetDefaultGeoCodeDateTimeFormatForm form) {
        return new SetDefaultGeoCodeDateTimeFormatCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editGeoCodeDateTimeFormat(UserVisitPK userVisitPK, EditGeoCodeDateTimeFormatForm form) {
        return new EditGeoCodeDateTimeFormatCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteGeoCodeDateTimeFormat(UserVisitPK userVisitPK, DeleteGeoCodeDateTimeFormatForm form) {
        return new DeleteGeoCodeDateTimeFormatCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Countries
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCountry(UserVisitPK userVisitPK, CreateCountryForm form) {
        return new CreateCountryCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCountry(UserVisitPK userVisitPK, GetCountryForm form) {
        return new GetCountryCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCountries(UserVisitPK userVisitPK, GetCountriesForm form) {
        return new GetCountriesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCountryChoices(UserVisitPK userVisitPK, GetCountryChoicesForm form) {
        return new GetCountryChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editCountry(UserVisitPK userVisitPK, EditCountryForm form) {
        return new EditCountryCommand().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   States
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createState(UserVisitPK userVisitPK, CreateStateForm form) {
        return new CreateStateCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getState(UserVisitPK userVisitPK, GetStateForm form) {
        return new GetStateCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getStates(UserVisitPK userVisitPK, GetStatesForm form) {
        return new GetStatesCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Counties
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCounty(UserVisitPK userVisitPK, CreateCountyForm form) {
        return new CreateCountyCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCounty(UserVisitPK userVisitPK, GetCountyForm form) {
        return new GetCountyCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCounties(UserVisitPK userVisitPK, GetCountiesForm form) {
        return new GetCountiesCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Cities
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCity(UserVisitPK userVisitPK, CreateCityForm form) {
        return new CreateCityCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult addCityToCounty(UserVisitPK userVisitPK, AddCityToCountyForm form) {
        return new AddCityToCountyCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult addZipCodeToCity(UserVisitPK userVisitPK, AddZipCodeToCityForm form) {
        return new AddZipCodeToCityCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCity(UserVisitPK userVisitPK, GetCityForm form) {
        return new GetCityCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCities(UserVisitPK userVisitPK, GetCitiesForm form) {
        return new GetCitiesCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Zip Codes
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createZipCode(UserVisitPK userVisitPK, CreateZipCodeForm form) {
        return new CreateZipCodeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getZipCode(UserVisitPK userVisitPK, GetZipCodeForm form) {
        return new GetZipCodeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getZipCodes(UserVisitPK userVisitPK, GetZipCodesForm form) {
        return new GetZipCodesCommand().run(userVisitPK, form);
    }
    
}
