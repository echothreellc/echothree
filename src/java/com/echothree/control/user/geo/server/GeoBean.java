// --------------------------------------------------------------------------------
// Copyright 2002-2024 Echo Three, LLC
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
        return new CreateGeoCodeTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getGeoCodeTypeChoices(UserVisitPK userVisitPK, GetGeoCodeTypeChoicesForm form) {
        return new GetGeoCodeTypeChoicesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getGeoCodeType(UserVisitPK userVisitPK, GetGeoCodeTypeForm form) {
        return new GetGeoCodeTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getGeoCodeTypes(UserVisitPK userVisitPK, GetGeoCodeTypesForm form) {
        return new GetGeoCodeTypesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult setDefaultGeoCodeType(UserVisitPK userVisitPK, SetDefaultGeoCodeTypeForm form) {
        return new SetDefaultGeoCodeTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editGeoCodeType(UserVisitPK userVisitPK, EditGeoCodeTypeForm form) {
        return new EditGeoCodeTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteGeoCodeType(UserVisitPK userVisitPK, DeleteGeoCodeTypeForm form) {
        return new DeleteGeoCodeTypeCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Geo Code Type Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createGeoCodeTypeDescription(UserVisitPK userVisitPK, CreateGeoCodeTypeDescriptionForm form) {
        return new CreateGeoCodeTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getGeoCodeTypeDescription(UserVisitPK userVisitPK, GetGeoCodeTypeDescriptionForm form) {
        return new GetGeoCodeTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getGeoCodeTypeDescriptions(UserVisitPK userVisitPK, GetGeoCodeTypeDescriptionsForm form) {
        return new GetGeoCodeTypeDescriptionsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editGeoCodeTypeDescription(UserVisitPK userVisitPK, EditGeoCodeTypeDescriptionForm form) {
        return new EditGeoCodeTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteGeoCodeTypeDescription(UserVisitPK userVisitPK, DeleteGeoCodeTypeDescriptionForm form) {
        return new DeleteGeoCodeTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Geo Code Scopes
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createGeoCodeScope(UserVisitPK userVisitPK, CreateGeoCodeScopeForm form) {
        return new CreateGeoCodeScopeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getGeoCodeScopeChoices(UserVisitPK userVisitPK, GetGeoCodeScopeChoicesForm form) {
        return new GetGeoCodeScopeChoicesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getGeoCodeScope(UserVisitPK userVisitPK, GetGeoCodeScopeForm form) {
        return new GetGeoCodeScopeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getGeoCodeScopes(UserVisitPK userVisitPK, GetGeoCodeScopesForm form) {
        return new GetGeoCodeScopesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult setDefaultGeoCodeScope(UserVisitPK userVisitPK, SetDefaultGeoCodeScopeForm form) {
        return new SetDefaultGeoCodeScopeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editGeoCodeScope(UserVisitPK userVisitPK, EditGeoCodeScopeForm form) {
        return new EditGeoCodeScopeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteGeoCodeScope(UserVisitPK userVisitPK, DeleteGeoCodeScopeForm form) {
        return new DeleteGeoCodeScopeCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Geo Code Scope Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createGeoCodeScopeDescription(UserVisitPK userVisitPK, CreateGeoCodeScopeDescriptionForm form) {
        return new CreateGeoCodeScopeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getGeoCodeScopeDescription(UserVisitPK userVisitPK, GetGeoCodeScopeDescriptionForm form) {
        return new GetGeoCodeScopeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getGeoCodeScopeDescriptions(UserVisitPK userVisitPK, GetGeoCodeScopeDescriptionsForm form) {
        return new GetGeoCodeScopeDescriptionsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editGeoCodeScopeDescription(UserVisitPK userVisitPK, EditGeoCodeScopeDescriptionForm form) {
        return new EditGeoCodeScopeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteGeoCodeScopeDescription(UserVisitPK userVisitPK, DeleteGeoCodeScopeDescriptionForm form) {
        return new DeleteGeoCodeScopeDescriptionCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Geo Code Alias Types
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createGeoCodeAliasType(UserVisitPK userVisitPK, CreateGeoCodeAliasTypeForm form) {
        return new CreateGeoCodeAliasTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getGeoCodeAliasTypeChoices(UserVisitPK userVisitPK, GetGeoCodeAliasTypeChoicesForm form) {
        return new GetGeoCodeAliasTypeChoicesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getGeoCodeAliasType(UserVisitPK userVisitPK, GetGeoCodeAliasTypeForm form) {
        return new GetGeoCodeAliasTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getGeoCodeAliasTypes(UserVisitPK userVisitPK, GetGeoCodeAliasTypesForm form) {
        return new GetGeoCodeAliasTypesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult setDefaultGeoCodeAliasType(UserVisitPK userVisitPK, SetDefaultGeoCodeAliasTypeForm form) {
        return new SetDefaultGeoCodeAliasTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editGeoCodeAliasType(UserVisitPK userVisitPK, EditGeoCodeAliasTypeForm form) {
        return new EditGeoCodeAliasTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteGeoCodeAliasType(UserVisitPK userVisitPK, DeleteGeoCodeAliasTypeForm form) {
        return new DeleteGeoCodeAliasTypeCommand(userVisitPK, form).run();
    }

    // -------------------------------------------------------------------------
    //   Geo Code Alias Type Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createGeoCodeAliasTypeDescription(UserVisitPK userVisitPK, CreateGeoCodeAliasTypeDescriptionForm form) {
        return new CreateGeoCodeAliasTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getGeoCodeAliasTypeDescription(UserVisitPK userVisitPK, GetGeoCodeAliasTypeDescriptionForm form) {
        return new GetGeoCodeAliasTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getGeoCodeAliasTypeDescriptions(UserVisitPK userVisitPK, GetGeoCodeAliasTypeDescriptionsForm form) {
        return new GetGeoCodeAliasTypeDescriptionsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editGeoCodeAliasTypeDescription(UserVisitPK userVisitPK, EditGeoCodeAliasTypeDescriptionForm form) {
        return new EditGeoCodeAliasTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteGeoCodeAliasTypeDescription(UserVisitPK userVisitPK, DeleteGeoCodeAliasTypeDescriptionForm form) {
        return new DeleteGeoCodeAliasTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Geo Codes
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult getGeoCode(UserVisitPK userVisitPK, GetGeoCodeForm form) {
        return new GetGeoCodeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult setDefaultGeoCode(UserVisitPK userVisitPK, SetDefaultGeoCodeForm form) {
        return new SetDefaultGeoCodeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteGeoCode(UserVisitPK userVisitPK, DeleteGeoCodeForm form) {
        return new DeleteGeoCodeCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Geo Code Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createGeoCodeDescription(UserVisitPK userVisitPK, CreateGeoCodeDescriptionForm form) {
        return new CreateGeoCodeDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getGeoCodeDescription(UserVisitPK userVisitPK, GetGeoCodeDescriptionForm form) {
        return new GetGeoCodeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getGeoCodeDescriptions(UserVisitPK userVisitPK, GetGeoCodeDescriptionsForm form) {
        return new GetGeoCodeDescriptionsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editGeoCodeDescription(UserVisitPK userVisitPK, EditGeoCodeDescriptionForm form) {
        return new EditGeoCodeDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteGeoCodeDescription(UserVisitPK userVisitPK, DeleteGeoCodeDescriptionForm form) {
        return new DeleteGeoCodeDescriptionCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Geo Code Aliases
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createGeoCodeAlias(UserVisitPK userVisitPK, CreateGeoCodeAliasForm form) {
        return new CreateGeoCodeAliasCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getGeoCodeAlias(UserVisitPK userVisitPK, GetGeoCodeAliasForm form) {
        return new GetGeoCodeAliasCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getGeoCodeAliases(UserVisitPK userVisitPK, GetGeoCodeAliasesForm form) {
        return new GetGeoCodeAliasesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editGeoCodeAlias(UserVisitPK userVisitPK, EditGeoCodeAliasForm form) {
        return new EditGeoCodeAliasCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteGeoCodeAlias(UserVisitPK userVisitPK, DeleteGeoCodeAliasForm form) {
        return new DeleteGeoCodeAliasCommand(userVisitPK, form).run();
    }

    // -------------------------------------------------------------------------
    //   Geo Code Languages
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createGeoCodeLanguage(UserVisitPK userVisitPK, CreateGeoCodeLanguageForm form) {
        return new CreateGeoCodeLanguageCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getGeoCodeLanguage(UserVisitPK userVisitPK, GetGeoCodeLanguageForm form) {
        return new GetGeoCodeLanguageCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getGeoCodeLanguages(UserVisitPK userVisitPK, GetGeoCodeLanguagesForm form) {
        return new GetGeoCodeLanguagesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult setDefaultGeoCodeLanguage(UserVisitPK userVisitPK, SetDefaultGeoCodeLanguageForm form) {
        return new SetDefaultGeoCodeLanguageCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editGeoCodeLanguage(UserVisitPK userVisitPK, EditGeoCodeLanguageForm form) {
        return new EditGeoCodeLanguageCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteGeoCodeLanguage(UserVisitPK userVisitPK, DeleteGeoCodeLanguageForm form) {
        return new DeleteGeoCodeLanguageCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Geo Code Currencies
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createGeoCodeCurrency(UserVisitPK userVisitPK, CreateGeoCodeCurrencyForm form) {
        return new CreateGeoCodeCurrencyCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getGeoCodeCurrency(UserVisitPK userVisitPK, GetGeoCodeCurrencyForm form) {
        return new GetGeoCodeCurrencyCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getGeoCodeCurrencies(UserVisitPK userVisitPK, GetGeoCodeCurrenciesForm form) {
        return new GetGeoCodeCurrenciesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultGeoCodeCurrency(UserVisitPK userVisitPK, SetDefaultGeoCodeCurrencyForm form) {
        return new SetDefaultGeoCodeCurrencyCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editGeoCodeCurrency(UserVisitPK userVisitPK, EditGeoCodeCurrencyForm form) {
        return new EditGeoCodeCurrencyCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteGeoCodeCurrency(UserVisitPK userVisitPK, DeleteGeoCodeCurrencyForm form) {
        return new DeleteGeoCodeCurrencyCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Geo Code Time Zones
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createGeoCodeTimeZone(UserVisitPK userVisitPK, CreateGeoCodeTimeZoneForm form) {
        return new CreateGeoCodeTimeZoneCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getGeoCodeTimeZone(UserVisitPK userVisitPK, GetGeoCodeTimeZoneForm form) {
        return new GetGeoCodeTimeZoneCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getGeoCodeTimeZones(UserVisitPK userVisitPK, GetGeoCodeTimeZonesForm form) {
        return new GetGeoCodeTimeZonesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult setDefaultGeoCodeTimeZone(UserVisitPK userVisitPK, SetDefaultGeoCodeTimeZoneForm form) {
        return new SetDefaultGeoCodeTimeZoneCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editGeoCodeTimeZone(UserVisitPK userVisitPK, EditGeoCodeTimeZoneForm form) {
        return new EditGeoCodeTimeZoneCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteGeoCodeTimeZone(UserVisitPK userVisitPK, DeleteGeoCodeTimeZoneForm form) {
        return new DeleteGeoCodeTimeZoneCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Geo Code Date Time Formats
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createGeoCodeDateTimeFormat(UserVisitPK userVisitPK, CreateGeoCodeDateTimeFormatForm form) {
        return new CreateGeoCodeDateTimeFormatCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getGeoCodeDateTimeFormat(UserVisitPK userVisitPK, GetGeoCodeDateTimeFormatForm form) {
        return new GetGeoCodeDateTimeFormatCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getGeoCodeDateTimeFormats(UserVisitPK userVisitPK, GetGeoCodeDateTimeFormatsForm form) {
        return new GetGeoCodeDateTimeFormatsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult setDefaultGeoCodeDateTimeFormat(UserVisitPK userVisitPK, SetDefaultGeoCodeDateTimeFormatForm form) {
        return new SetDefaultGeoCodeDateTimeFormatCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editGeoCodeDateTimeFormat(UserVisitPK userVisitPK, EditGeoCodeDateTimeFormatForm form) {
        return new EditGeoCodeDateTimeFormatCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteGeoCodeDateTimeFormat(UserVisitPK userVisitPK, DeleteGeoCodeDateTimeFormatForm form) {
        return new DeleteGeoCodeDateTimeFormatCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Countries
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCountry(UserVisitPK userVisitPK, CreateCountryForm form) {
        return new CreateCountryCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getCountry(UserVisitPK userVisitPK, GetCountryForm form) {
        return new GetCountryCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getCountries(UserVisitPK userVisitPK, GetCountriesForm form) {
        return new GetCountriesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getCountryChoices(UserVisitPK userVisitPK, GetCountryChoicesForm form) {
        return new GetCountryChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editCountry(UserVisitPK userVisitPK, EditCountryForm form) {
        return new EditCountryCommand(userVisitPK, form).run();
    }

    // -------------------------------------------------------------------------
    //   States
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createState(UserVisitPK userVisitPK, CreateStateForm form) {
        return new CreateStateCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getState(UserVisitPK userVisitPK, GetStateForm form) {
        return new GetStateCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getStates(UserVisitPK userVisitPK, GetStatesForm form) {
        return new GetStatesCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Counties
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCounty(UserVisitPK userVisitPK, CreateCountyForm form) {
        return new CreateCountyCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getCounty(UserVisitPK userVisitPK, GetCountyForm form) {
        return new GetCountyCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getCounties(UserVisitPK userVisitPK, GetCountiesForm form) {
        return new GetCountiesCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Cities
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCity(UserVisitPK userVisitPK, CreateCityForm form) {
        return new CreateCityCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult addCityToCounty(UserVisitPK userVisitPK, AddCityToCountyForm form) {
        return new AddCityToCountyCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult addZipCodeToCity(UserVisitPK userVisitPK, AddZipCodeToCityForm form) {
        return new AddZipCodeToCityCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getCity(UserVisitPK userVisitPK, GetCityForm form) {
        return new GetCityCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getCities(UserVisitPK userVisitPK, GetCitiesForm form) {
        return new GetCitiesCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Zip Codes
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createZipCode(UserVisitPK userVisitPK, CreateZipCodeForm form) {
        return new CreateZipCodeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getZipCode(UserVisitPK userVisitPK, GetZipCodeForm form) {
        return new GetZipCodeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getZipCodes(UserVisitPK userVisitPK, GetZipCodesForm form) {
        return new GetZipCodesCommand(userVisitPK, form).run();
    }
    
}
