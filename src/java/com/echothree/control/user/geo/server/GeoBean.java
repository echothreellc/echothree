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
import javax.enterprise.inject.spi.CDI;

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
        return CDI.current().select(CreateGeoCodeTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getGeoCodeTypeChoices(UserVisitPK userVisitPK, GetGeoCodeTypeChoicesForm form) {
        return CDI.current().select(GetGeoCodeTypeChoicesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getGeoCodeType(UserVisitPK userVisitPK, GetGeoCodeTypeForm form) {
        return CDI.current().select(GetGeoCodeTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getGeoCodeTypes(UserVisitPK userVisitPK, GetGeoCodeTypesForm form) {
        return CDI.current().select(GetGeoCodeTypesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultGeoCodeType(UserVisitPK userVisitPK, SetDefaultGeoCodeTypeForm form) {
        return CDI.current().select(SetDefaultGeoCodeTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editGeoCodeType(UserVisitPK userVisitPK, EditGeoCodeTypeForm form) {
        return CDI.current().select(EditGeoCodeTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteGeoCodeType(UserVisitPK userVisitPK, DeleteGeoCodeTypeForm form) {
        return CDI.current().select(DeleteGeoCodeTypeCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Geo Code Type Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createGeoCodeTypeDescription(UserVisitPK userVisitPK, CreateGeoCodeTypeDescriptionForm form) {
        return CDI.current().select(CreateGeoCodeTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getGeoCodeTypeDescription(UserVisitPK userVisitPK, GetGeoCodeTypeDescriptionForm form) {
        return CDI.current().select(GetGeoCodeTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getGeoCodeTypeDescriptions(UserVisitPK userVisitPK, GetGeoCodeTypeDescriptionsForm form) {
        return CDI.current().select(GetGeoCodeTypeDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editGeoCodeTypeDescription(UserVisitPK userVisitPK, EditGeoCodeTypeDescriptionForm form) {
        return CDI.current().select(EditGeoCodeTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteGeoCodeTypeDescription(UserVisitPK userVisitPK, DeleteGeoCodeTypeDescriptionForm form) {
        return CDI.current().select(DeleteGeoCodeTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Geo Code Scopes
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createGeoCodeScope(UserVisitPK userVisitPK, CreateGeoCodeScopeForm form) {
        return CDI.current().select(CreateGeoCodeScopeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getGeoCodeScopeChoices(UserVisitPK userVisitPK, GetGeoCodeScopeChoicesForm form) {
        return CDI.current().select(GetGeoCodeScopeChoicesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getGeoCodeScope(UserVisitPK userVisitPK, GetGeoCodeScopeForm form) {
        return CDI.current().select(GetGeoCodeScopeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getGeoCodeScopes(UserVisitPK userVisitPK, GetGeoCodeScopesForm form) {
        return CDI.current().select(GetGeoCodeScopesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultGeoCodeScope(UserVisitPK userVisitPK, SetDefaultGeoCodeScopeForm form) {
        return CDI.current().select(SetDefaultGeoCodeScopeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editGeoCodeScope(UserVisitPK userVisitPK, EditGeoCodeScopeForm form) {
        return CDI.current().select(EditGeoCodeScopeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteGeoCodeScope(UserVisitPK userVisitPK, DeleteGeoCodeScopeForm form) {
        return CDI.current().select(DeleteGeoCodeScopeCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Geo Code Scope Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createGeoCodeScopeDescription(UserVisitPK userVisitPK, CreateGeoCodeScopeDescriptionForm form) {
        return CDI.current().select(CreateGeoCodeScopeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getGeoCodeScopeDescription(UserVisitPK userVisitPK, GetGeoCodeScopeDescriptionForm form) {
        return CDI.current().select(GetGeoCodeScopeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getGeoCodeScopeDescriptions(UserVisitPK userVisitPK, GetGeoCodeScopeDescriptionsForm form) {
        return CDI.current().select(GetGeoCodeScopeDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editGeoCodeScopeDescription(UserVisitPK userVisitPK, EditGeoCodeScopeDescriptionForm form) {
        return CDI.current().select(EditGeoCodeScopeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteGeoCodeScopeDescription(UserVisitPK userVisitPK, DeleteGeoCodeScopeDescriptionForm form) {
        return CDI.current().select(DeleteGeoCodeScopeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Geo Code Alias Types
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createGeoCodeAliasType(UserVisitPK userVisitPK, CreateGeoCodeAliasTypeForm form) {
        return CDI.current().select(CreateGeoCodeAliasTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getGeoCodeAliasTypeChoices(UserVisitPK userVisitPK, GetGeoCodeAliasTypeChoicesForm form) {
        return CDI.current().select(GetGeoCodeAliasTypeChoicesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getGeoCodeAliasType(UserVisitPK userVisitPK, GetGeoCodeAliasTypeForm form) {
        return CDI.current().select(GetGeoCodeAliasTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getGeoCodeAliasTypes(UserVisitPK userVisitPK, GetGeoCodeAliasTypesForm form) {
        return CDI.current().select(GetGeoCodeAliasTypesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultGeoCodeAliasType(UserVisitPK userVisitPK, SetDefaultGeoCodeAliasTypeForm form) {
        return CDI.current().select(SetDefaultGeoCodeAliasTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editGeoCodeAliasType(UserVisitPK userVisitPK, EditGeoCodeAliasTypeForm form) {
        return CDI.current().select(EditGeoCodeAliasTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteGeoCodeAliasType(UserVisitPK userVisitPK, DeleteGeoCodeAliasTypeForm form) {
        return CDI.current().select(DeleteGeoCodeAliasTypeCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Geo Code Alias Type Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createGeoCodeAliasTypeDescription(UserVisitPK userVisitPK, CreateGeoCodeAliasTypeDescriptionForm form) {
        return CDI.current().select(CreateGeoCodeAliasTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getGeoCodeAliasTypeDescription(UserVisitPK userVisitPK, GetGeoCodeAliasTypeDescriptionForm form) {
        return CDI.current().select(GetGeoCodeAliasTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getGeoCodeAliasTypeDescriptions(UserVisitPK userVisitPK, GetGeoCodeAliasTypeDescriptionsForm form) {
        return CDI.current().select(GetGeoCodeAliasTypeDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editGeoCodeAliasTypeDescription(UserVisitPK userVisitPK, EditGeoCodeAliasTypeDescriptionForm form) {
        return CDI.current().select(EditGeoCodeAliasTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteGeoCodeAliasTypeDescription(UserVisitPK userVisitPK, DeleteGeoCodeAliasTypeDescriptionForm form) {
        return CDI.current().select(DeleteGeoCodeAliasTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Geo Codes
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult getGeoCode(UserVisitPK userVisitPK, GetGeoCodeForm form) {
        return CDI.current().select(GetGeoCodeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultGeoCode(UserVisitPK userVisitPK, SetDefaultGeoCodeForm form) {
        return CDI.current().select(SetDefaultGeoCodeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteGeoCode(UserVisitPK userVisitPK, DeleteGeoCodeForm form) {
        return CDI.current().select(DeleteGeoCodeCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Geo Code Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createGeoCodeDescription(UserVisitPK userVisitPK, CreateGeoCodeDescriptionForm form) {
        return CDI.current().select(CreateGeoCodeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getGeoCodeDescription(UserVisitPK userVisitPK, GetGeoCodeDescriptionForm form) {
        return CDI.current().select(GetGeoCodeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getGeoCodeDescriptions(UserVisitPK userVisitPK, GetGeoCodeDescriptionsForm form) {
        return CDI.current().select(GetGeoCodeDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editGeoCodeDescription(UserVisitPK userVisitPK, EditGeoCodeDescriptionForm form) {
        return CDI.current().select(EditGeoCodeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteGeoCodeDescription(UserVisitPK userVisitPK, DeleteGeoCodeDescriptionForm form) {
        return CDI.current().select(DeleteGeoCodeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Geo Code Aliases
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createGeoCodeAlias(UserVisitPK userVisitPK, CreateGeoCodeAliasForm form) {
        return CDI.current().select(CreateGeoCodeAliasCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getGeoCodeAlias(UserVisitPK userVisitPK, GetGeoCodeAliasForm form) {
        return CDI.current().select(GetGeoCodeAliasCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getGeoCodeAliases(UserVisitPK userVisitPK, GetGeoCodeAliasesForm form) {
        return CDI.current().select(GetGeoCodeAliasesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editGeoCodeAlias(UserVisitPK userVisitPK, EditGeoCodeAliasForm form) {
        return CDI.current().select(EditGeoCodeAliasCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteGeoCodeAlias(UserVisitPK userVisitPK, DeleteGeoCodeAliasForm form) {
        return CDI.current().select(DeleteGeoCodeAliasCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Geo Code Languages
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createGeoCodeLanguage(UserVisitPK userVisitPK, CreateGeoCodeLanguageForm form) {
        return CDI.current().select(CreateGeoCodeLanguageCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getGeoCodeLanguage(UserVisitPK userVisitPK, GetGeoCodeLanguageForm form) {
        return CDI.current().select(GetGeoCodeLanguageCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getGeoCodeLanguages(UserVisitPK userVisitPK, GetGeoCodeLanguagesForm form) {
        return CDI.current().select(GetGeoCodeLanguagesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultGeoCodeLanguage(UserVisitPK userVisitPK, SetDefaultGeoCodeLanguageForm form) {
        return CDI.current().select(SetDefaultGeoCodeLanguageCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editGeoCodeLanguage(UserVisitPK userVisitPK, EditGeoCodeLanguageForm form) {
        return CDI.current().select(EditGeoCodeLanguageCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteGeoCodeLanguage(UserVisitPK userVisitPK, DeleteGeoCodeLanguageForm form) {
        return CDI.current().select(DeleteGeoCodeLanguageCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Geo Code Currencies
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createGeoCodeCurrency(UserVisitPK userVisitPK, CreateGeoCodeCurrencyForm form) {
        return CDI.current().select(CreateGeoCodeCurrencyCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getGeoCodeCurrency(UserVisitPK userVisitPK, GetGeoCodeCurrencyForm form) {
        return CDI.current().select(GetGeoCodeCurrencyCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getGeoCodeCurrencies(UserVisitPK userVisitPK, GetGeoCodeCurrenciesForm form) {
        return CDI.current().select(GetGeoCodeCurrenciesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultGeoCodeCurrency(UserVisitPK userVisitPK, SetDefaultGeoCodeCurrencyForm form) {
        return CDI.current().select(SetDefaultGeoCodeCurrencyCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editGeoCodeCurrency(UserVisitPK userVisitPK, EditGeoCodeCurrencyForm form) {
        return CDI.current().select(EditGeoCodeCurrencyCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteGeoCodeCurrency(UserVisitPK userVisitPK, DeleteGeoCodeCurrencyForm form) {
        return CDI.current().select(DeleteGeoCodeCurrencyCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Geo Code Time Zones
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createGeoCodeTimeZone(UserVisitPK userVisitPK, CreateGeoCodeTimeZoneForm form) {
        return CDI.current().select(CreateGeoCodeTimeZoneCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getGeoCodeTimeZone(UserVisitPK userVisitPK, GetGeoCodeTimeZoneForm form) {
        return CDI.current().select(GetGeoCodeTimeZoneCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getGeoCodeTimeZones(UserVisitPK userVisitPK, GetGeoCodeTimeZonesForm form) {
        return CDI.current().select(GetGeoCodeTimeZonesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultGeoCodeTimeZone(UserVisitPK userVisitPK, SetDefaultGeoCodeTimeZoneForm form) {
        return CDI.current().select(SetDefaultGeoCodeTimeZoneCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editGeoCodeTimeZone(UserVisitPK userVisitPK, EditGeoCodeTimeZoneForm form) {
        return CDI.current().select(EditGeoCodeTimeZoneCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteGeoCodeTimeZone(UserVisitPK userVisitPK, DeleteGeoCodeTimeZoneForm form) {
        return CDI.current().select(DeleteGeoCodeTimeZoneCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Geo Code Date Time Formats
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createGeoCodeDateTimeFormat(UserVisitPK userVisitPK, CreateGeoCodeDateTimeFormatForm form) {
        return CDI.current().select(CreateGeoCodeDateTimeFormatCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getGeoCodeDateTimeFormat(UserVisitPK userVisitPK, GetGeoCodeDateTimeFormatForm form) {
        return CDI.current().select(GetGeoCodeDateTimeFormatCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getGeoCodeDateTimeFormats(UserVisitPK userVisitPK, GetGeoCodeDateTimeFormatsForm form) {
        return CDI.current().select(GetGeoCodeDateTimeFormatsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultGeoCodeDateTimeFormat(UserVisitPK userVisitPK, SetDefaultGeoCodeDateTimeFormatForm form) {
        return CDI.current().select(SetDefaultGeoCodeDateTimeFormatCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editGeoCodeDateTimeFormat(UserVisitPK userVisitPK, EditGeoCodeDateTimeFormatForm form) {
        return CDI.current().select(EditGeoCodeDateTimeFormatCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteGeoCodeDateTimeFormat(UserVisitPK userVisitPK, DeleteGeoCodeDateTimeFormatForm form) {
        return CDI.current().select(DeleteGeoCodeDateTimeFormatCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Countries
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCountry(UserVisitPK userVisitPK, CreateCountryForm form) {
        return CDI.current().select(CreateCountryCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCountry(UserVisitPK userVisitPK, GetCountryForm form) {
        return CDI.current().select(GetCountryCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCountries(UserVisitPK userVisitPK, GetCountriesForm form) {
        return CDI.current().select(GetCountriesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCountryChoices(UserVisitPK userVisitPK, GetCountryChoicesForm form) {
        return CDI.current().select(GetCountryChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editCountry(UserVisitPK userVisitPK, EditCountryForm form) {
        return CDI.current().select(EditCountryCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   States
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createState(UserVisitPK userVisitPK, CreateStateForm form) {
        return CDI.current().select(CreateStateCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getState(UserVisitPK userVisitPK, GetStateForm form) {
        return CDI.current().select(GetStateCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getStates(UserVisitPK userVisitPK, GetStatesForm form) {
        return CDI.current().select(GetStatesCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Counties
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCounty(UserVisitPK userVisitPK, CreateCountyForm form) {
        return CDI.current().select(CreateCountyCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCounty(UserVisitPK userVisitPK, GetCountyForm form) {
        return CDI.current().select(GetCountyCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCounties(UserVisitPK userVisitPK, GetCountiesForm form) {
        return CDI.current().select(GetCountiesCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Cities
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCity(UserVisitPK userVisitPK, CreateCityForm form) {
        return CDI.current().select(CreateCityCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult addCityToCounty(UserVisitPK userVisitPK, AddCityToCountyForm form) {
        return CDI.current().select(AddCityToCountyCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult addZipCodeToCity(UserVisitPK userVisitPK, AddZipCodeToCityForm form) {
        return CDI.current().select(AddZipCodeToCityCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCity(UserVisitPK userVisitPK, GetCityForm form) {
        return CDI.current().select(GetCityCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCities(UserVisitPK userVisitPK, GetCitiesForm form) {
        return CDI.current().select(GetCitiesCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Zip Codes
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createZipCode(UserVisitPK userVisitPK, CreateZipCodeForm form) {
        return CDI.current().select(CreateZipCodeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getZipCode(UserVisitPK userVisitPK, GetZipCodeForm form) {
        return CDI.current().select(GetZipCodeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getZipCodes(UserVisitPK userVisitPK, GetZipCodesForm form) {
        return CDI.current().select(GetZipCodesCommand.class).get().run(userVisitPK, form);
    }
    
}
