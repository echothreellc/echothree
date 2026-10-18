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

package com.echothree.model.control.geo.server.control;

import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.geo.common.GeoCodeAliasTypes;
import com.echothree.model.control.geo.common.GeoCodeScopes;
import com.echothree.model.control.geo.common.GeoCodeTypes;
import com.echothree.model.control.geo.common.choice.CountryChoicesBean;
import com.echothree.model.control.geo.common.choice.GeoCodeAliasTypeChoicesBean;
import com.echothree.model.control.geo.common.choice.GeoCodeScopeChoicesBean;
import com.echothree.model.control.geo.common.choice.GeoCodeTypeChoicesBean;
import com.echothree.model.control.geo.common.transfer.CityTransfer;
import com.echothree.model.control.geo.common.transfer.CountryTransfer;
import com.echothree.model.control.geo.common.transfer.CountyTransfer;
import com.echothree.model.control.geo.common.transfer.GeoCodeAliasTransfer;
import com.echothree.model.control.geo.common.transfer.GeoCodeAliasTypeDescriptionTransfer;
import com.echothree.model.control.geo.common.transfer.GeoCodeAliasTypeTransfer;
import com.echothree.model.control.geo.common.transfer.GeoCodeCurrencyTransfer;
import com.echothree.model.control.geo.common.transfer.GeoCodeDateTimeFormatTransfer;
import com.echothree.model.control.geo.common.transfer.GeoCodeDescriptionTransfer;
import com.echothree.model.control.geo.common.transfer.GeoCodeLanguageTransfer;
import com.echothree.model.control.geo.common.transfer.GeoCodeRelationshipTransfer;
import com.echothree.model.control.geo.common.transfer.GeoCodeScopeDescriptionTransfer;
import com.echothree.model.control.geo.common.transfer.GeoCodeScopeTransfer;
import com.echothree.model.control.geo.common.transfer.GeoCodeTimeZoneTransfer;
import com.echothree.model.control.geo.common.transfer.GeoCodeTransfer;
import com.echothree.model.control.geo.common.transfer.GeoCodeTypeDescriptionTransfer;
import com.echothree.model.control.geo.common.transfer.GeoCodeTypeTransfer;
import com.echothree.model.control.geo.common.transfer.PostalCodeTransfer;
import com.echothree.model.control.geo.common.transfer.StateTransfer;
import com.echothree.model.control.geo.server.transfer.CityTransferCache;
import com.echothree.model.control.geo.server.transfer.CountryTransferCache;
import com.echothree.model.control.geo.server.transfer.CountyTransferCache;
import com.echothree.model.control.geo.server.transfer.GeoCodeAliasTransferCache;
import com.echothree.model.control.geo.server.transfer.GeoCodeAliasTypeDescriptionTransferCache;
import com.echothree.model.control.geo.server.transfer.GeoCodeAliasTypeTransferCache;
import com.echothree.model.control.geo.server.transfer.GeoCodeCurrencyTransferCache;
import com.echothree.model.control.geo.server.transfer.GeoCodeDateTimeFormatTransferCache;
import com.echothree.model.control.geo.server.transfer.GeoCodeDescriptionTransferCache;
import com.echothree.model.control.geo.server.transfer.GeoCodeLanguageTransferCache;
import com.echothree.model.control.geo.server.transfer.GeoCodeRelationshipTransferCache;
import com.echothree.model.control.geo.server.transfer.GeoCodeScopeDescriptionTransferCache;
import com.echothree.model.control.geo.server.transfer.GeoCodeScopeTransferCache;
import com.echothree.model.control.geo.server.transfer.GeoCodeTimeZoneTransferCache;
import com.echothree.model.control.geo.server.transfer.GeoCodeTransferCache;
import com.echothree.model.control.geo.server.transfer.GeoCodeTypeDescriptionTransferCache;
import com.echothree.model.control.geo.server.transfer.GeoCodeTypeTransferCache;
import com.echothree.model.control.geo.server.transfer.PostalCodeTransferCache;
import com.echothree.model.control.geo.server.transfer.StateTransferCache;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.contact.server.entity.PostalAddressFormat;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.geo.common.pk.GeoCodePK;
import com.echothree.model.data.geo.common.pk.GeoCodeScopePK;
import com.echothree.model.data.geo.common.pk.GeoCodeTypePK;
import com.echothree.model.data.geo.server.entity.GeoCode;
import com.echothree.model.data.geo.server.entity.GeoCodeAlias;
import com.echothree.model.data.geo.server.entity.GeoCodeAliasType;
import com.echothree.model.data.geo.server.entity.GeoCodeAliasTypeDescription;
import com.echothree.model.data.geo.server.entity.GeoCodeCountry;
import com.echothree.model.data.geo.server.entity.GeoCodeCurrency;
import com.echothree.model.data.geo.server.entity.GeoCodeDateTimeFormat;
import com.echothree.model.data.geo.server.entity.GeoCodeDescription;
import com.echothree.model.data.geo.server.entity.GeoCodeLanguage;
import com.echothree.model.data.geo.server.entity.GeoCodeRelationship;
import com.echothree.model.data.geo.server.entity.GeoCodeScope;
import com.echothree.model.data.geo.server.entity.GeoCodeScopeDescription;
import com.echothree.model.data.geo.server.entity.GeoCodeTimeZone;
import com.echothree.model.data.geo.server.entity.GeoCodeType;
import com.echothree.model.data.geo.server.entity.GeoCodeTypeDescription;
import com.echothree.model.data.geo.server.factory.GeoCodeAliasFactory;
import com.echothree.model.data.geo.server.factory.GeoCodeAliasTypeDescriptionFactory;
import com.echothree.model.data.geo.server.factory.GeoCodeAliasTypeDetailFactory;
import com.echothree.model.data.geo.server.factory.GeoCodeAliasTypeFactory;
import com.echothree.model.data.geo.server.factory.GeoCodeCountryFactory;
import com.echothree.model.data.geo.server.factory.GeoCodeCurrencyFactory;
import com.echothree.model.data.geo.server.factory.GeoCodeDateTimeFormatFactory;
import com.echothree.model.data.geo.server.factory.GeoCodeDescriptionFactory;
import com.echothree.model.data.geo.server.factory.GeoCodeDetailFactory;
import com.echothree.model.data.geo.server.factory.GeoCodeFactory;
import com.echothree.model.data.geo.server.factory.GeoCodeLanguageFactory;
import com.echothree.model.data.geo.server.factory.GeoCodeRelationshipFactory;
import com.echothree.model.data.geo.server.factory.GeoCodeScopeDescriptionFactory;
import com.echothree.model.data.geo.server.factory.GeoCodeScopeDetailFactory;
import com.echothree.model.data.geo.server.factory.GeoCodeScopeFactory;
import com.echothree.model.data.geo.server.factory.GeoCodeTimeZoneFactory;
import com.echothree.model.data.geo.server.factory.GeoCodeTypeDescriptionFactory;
import com.echothree.model.data.geo.server.factory.GeoCodeTypeDetailFactory;
import com.echothree.model.data.geo.server.factory.GeoCodeTypeFactory;
import com.echothree.model.data.geo.server.value.GeoCodeAliasTypeDescriptionValue;
import com.echothree.model.data.geo.server.value.GeoCodeAliasTypeDetailValue;
import com.echothree.model.data.geo.server.value.GeoCodeAliasValue;
import com.echothree.model.data.geo.server.value.GeoCodeCountryValue;
import com.echothree.model.data.geo.server.value.GeoCodeCurrencyValue;
import com.echothree.model.data.geo.server.value.GeoCodeDateTimeFormatValue;
import com.echothree.model.data.geo.server.value.GeoCodeDescriptionValue;
import com.echothree.model.data.geo.server.value.GeoCodeDetailValue;
import com.echothree.model.data.geo.server.value.GeoCodeLanguageValue;
import com.echothree.model.data.geo.server.value.GeoCodeScopeDescriptionValue;
import com.echothree.model.data.geo.server.value.GeoCodeScopeDetailValue;
import com.echothree.model.data.geo.server.value.GeoCodeTimeZoneValue;
import com.echothree.model.data.geo.server.value.GeoCodeTypeDescriptionValue;
import com.echothree.model.data.geo.server.value.GeoCodeTypeDetailValue;
import com.echothree.model.data.party.server.entity.DateTimeFormat;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.TimeZone;
import com.echothree.model.data.user.server.entity.UserVisit;
import static com.echothree.model.jooq.server.keys.geo.GeoForeignKeys.GEO_CODES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.geo.GeoForeignKeys.GEO_CODES_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.geo.GeoForeignKeys.GEO_CODE_ALIASES_GEO_CODE_ALIAS_TYPE_FK;
import static com.echothree.model.jooq.server.keys.geo.GeoForeignKeys.GEO_CODE_ALIASES_GEO_CODE_SCOPE_FK;
import static com.echothree.model.jooq.server.keys.geo.GeoForeignKeys.GEO_CODE_ALIAS_TYPES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.geo.GeoForeignKeys.GEO_CODE_ALIAS_TYPES_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.geo.GeoForeignKeys.GEO_CODE_ALIAS_TYPE_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.keys.geo.GeoForeignKeys.GEO_CODE_CURRENCIES_CURRENCY_FK;
import static com.echothree.model.jooq.server.keys.geo.GeoForeignKeys.GEO_CODE_CURRENCIES_GEO_CODE_FK;
import static com.echothree.model.jooq.server.keys.geo.GeoForeignKeys.GEO_CODE_DATE_TIME_FORMATS_DATE_TIME_FORMAT_FK;
import static com.echothree.model.jooq.server.keys.geo.GeoForeignKeys.GEO_CODE_DATE_TIME_FORMATS_GEO_CODE_FK;
import static com.echothree.model.jooq.server.keys.geo.GeoForeignKeys.GEO_CODE_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.keys.geo.GeoForeignKeys.GEO_CODE_LANGUAGES_GEO_CODE_FK;
import static com.echothree.model.jooq.server.keys.geo.GeoForeignKeys.GEO_CODE_LANGUAGES_LANGUAGE_FK;
import static com.echothree.model.jooq.server.keys.geo.GeoForeignKeys.GEO_CODE_RELATIONSHIPS_FROM_GEO_CODE_FK;
import static com.echothree.model.jooq.server.keys.geo.GeoForeignKeys.GEO_CODE_RELATIONSHIPS_TO_GEO_CODE_FK;
import static com.echothree.model.jooq.server.keys.geo.GeoForeignKeys.GEO_CODE_SCOPES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.geo.GeoForeignKeys.GEO_CODE_SCOPES_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.geo.GeoForeignKeys.GEO_CODE_SCOPE_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.keys.geo.GeoForeignKeys.GEO_CODE_TIME_ZONES_GEO_CODE_FK;
import static com.echothree.model.jooq.server.keys.geo.GeoForeignKeys.GEO_CODE_TIME_ZONES_TIME_ZONE_FK;
import static com.echothree.model.jooq.server.keys.geo.GeoForeignKeys.GEO_CODE_TYPES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.geo.GeoForeignKeys.GEO_CODE_TYPE_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.keys.party.PartyForeignKeys.DATE_TIME_FORMATS_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.party.PartyForeignKeys.TIME_ZONES_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.tables.accounting.Currencies.Currencies;
import static com.echothree.model.jooq.server.tables.geo.GeoCodeAliasTypeDescriptions.GeoCodeAliasTypeDescriptions;
import static com.echothree.model.jooq.server.tables.geo.GeoCodeAliasTypeDetails.GeoCodeAliasTypeDetails;
import static com.echothree.model.jooq.server.tables.geo.GeoCodeAliasTypes.GeoCodeAliasTypes;
import static com.echothree.model.jooq.server.tables.geo.GeoCodeAliases.GeoCodeAliases;
import static com.echothree.model.jooq.server.tables.geo.GeoCodeCountries.GeoCodeCountries;
import static com.echothree.model.jooq.server.tables.geo.GeoCodeCurrencies.GeoCodeCurrencies;
import static com.echothree.model.jooq.server.tables.geo.GeoCodeDateTimeFormats.GeoCodeDateTimeFormats;
import static com.echothree.model.jooq.server.tables.geo.GeoCodeDescriptions.GeoCodeDescriptions;
import static com.echothree.model.jooq.server.tables.geo.GeoCodeDetails.GeoCodeDetails;
import static com.echothree.model.jooq.server.tables.geo.GeoCodeLanguages.GeoCodeLanguages;
import static com.echothree.model.jooq.server.tables.geo.GeoCodeRelationships.GeoCodeRelationships;
import static com.echothree.model.jooq.server.tables.geo.GeoCodeScopeDescriptions.GeoCodeScopeDescriptions;
import static com.echothree.model.jooq.server.tables.geo.GeoCodeScopeDetails.GeoCodeScopeDetails;
import static com.echothree.model.jooq.server.tables.geo.GeoCodeScopes.GeoCodeScopes;
import static com.echothree.model.jooq.server.tables.geo.GeoCodeTimeZones.GeoCodeTimeZones;
import static com.echothree.model.jooq.server.tables.geo.GeoCodeTypeDescriptions.GeoCodeTypeDescriptions;
import static com.echothree.model.jooq.server.tables.geo.GeoCodeTypeDetails.GeoCodeTypeDetails;
import static com.echothree.model.jooq.server.tables.geo.GeoCodeTypes.GeoCodeTypes;
import static com.echothree.model.jooq.server.tables.geo.GeoCodes.GeoCodes;
import static com.echothree.model.jooq.server.tables.party.DateTimeFormatDetails.DateTimeFormatDetails;
import static com.echothree.model.jooq.server.tables.party.DateTimeFormats.DateTimeFormats;
import static com.echothree.model.jooq.server.tables.party.Languages.Languages;
import static com.echothree.model.jooq.server.tables.party.TimeZoneDetails.TimeZoneDetails;
import static com.echothree.model.jooq.server.tables.party.TimeZones.TimeZones;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.cdi.CommandScope;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;
import javax.inject.Inject;

@CommandScope
public class GeoControl
        extends BaseModelControl {
    
    @Inject
    protected ItemControl itemControl;

    /** Creates a new instance of GeoControl */
    protected GeoControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Transfer Object Utilities
    // --------------------------------------------------------------------------------

    @Inject
    CityTransferCache cityTransferCache;

    @Inject
    CountryTransferCache countryTransferCache;

    @Inject
    CountyTransferCache countyTransferCache;

    @Inject
    PostalCodeTransferCache postalCodeTransferCache;

    @Inject
    StateTransferCache stateTransferCache;

    public CountryTransfer getCountryTransfer(UserVisit userVisit, GeoCode geoCode) {
        return countryTransferCache.getCountryTransfer(userVisit, geoCode);
    }
    
    public List<CountryTransfer> getCountryTransfers(UserVisit userVisit, Collection<GeoCode> geoCodes) {
        var countryTransfers = new ArrayList<CountryTransfer>(geoCodes.size());
        
        geoCodes.forEach((geoCode) ->
                countryTransfers.add(countryTransferCache.getCountryTransfer(userVisit, geoCode))
        );
        
        return countryTransfers;
    }

    public GeoCodeScope getCountriesGeoCodeScope() {
        return getGeoCodeScopeByName(com.echothree.model.control.geo.common.GeoCodeScopes.COUNTRIES.name());
    }
    
    public List<GeoCode> getCountries(GeoCodeScope countriesGeoCodeScope) {
        return getGeoCodesByGeoCodeScope(countriesGeoCodeScope);
    }

    public StateTransfer getStateTransfer(UserVisit userVisit, GeoCode geoCode) {
        return stateTransferCache.getStateTransfer(userVisit, geoCode);
    }
    
    public List<StateTransfer> getStateTransfers(UserVisit userVisit, Collection<GeoCode> geoCodes) {
        var stateTransfers = new ArrayList<StateTransfer>(geoCodes.size());
        
        geoCodes.forEach((geoCode) ->
                stateTransfers.add(stateTransferCache.getStateTransfer(userVisit, geoCode))
        );
        
        return stateTransfers;
    }

    public GeoCodeScope getStatesGeoCodeScope(GeoCode countryGeoCode) {
        var countryGeoCodeAliasType = getGeoCodeAliasTypeByName(countryGeoCode.getLastDetail().getGeoCodeType(), com.echothree.model.control.geo.common.GeoCodeAliasTypes.ISO_2_LETTER.name());
        var countryGeoCodeAlias = getGeoCodeAlias(countryGeoCode, countryGeoCodeAliasType);
        var countryIso2Letter = countryGeoCodeAlias.getAlias();
        var geoCodeScopeName = countryIso2Letter + "_STATES";

        return getGeoCodeScopeByName(geoCodeScopeName);
    }

    public List<GeoCode> getStatesByCountry(GeoCodeScope statesGeoCodeScope) {
        List<GeoCode> states;

        if(statesGeoCodeScope != null) {
            states = getGeoCodesByGeoCodeScope(statesGeoCodeScope);
        } else {
            states = new ArrayList<>();
        }

        return states;
    }

    public CountyTransfer getCountyTransfer(UserVisit userVisit, GeoCode geoCode) {
        return countyTransferCache.getCountyTransfer(userVisit, geoCode);
    }
    
    public List<CountyTransfer> getCountyTransfers(UserVisit userVisit, Collection<GeoCode> geoCodes) {
        var countyTransfers = new ArrayList<CountyTransfer>(geoCodes.size());
        
        geoCodes.forEach((geoCode) ->
                countyTransfers.add(countyTransferCache.getCountyTransfer(userVisit, geoCode))
        );
        
        return countyTransfers;
    }

    public GeoCodeScope getCountiesGeoCodeScope(GeoCode stateGeoCode) {
        var stateGeoCodeAliasType = getGeoCodeAliasTypeByName(stateGeoCode.getLastDetail().getGeoCodeType(), com.echothree.model.control.geo.common.GeoCodeAliasTypes.POSTAL_2_LETTER.name());
        var stateGeoCodeAlias = getGeoCodeAlias(stateGeoCode, stateGeoCodeAliasType);
        var statePostal2Letter = stateGeoCodeAlias.getAlias();

        var countryGeoCodeType = getGeoCodeTypeByName(com.echothree.model.control.geo.common.GeoCodeTypes.COUNTRY.name());
        var stateRelationships = getGeoCodeRelationshipsByFromGeoCode(stateGeoCode);
        GeoCode countryGeoCode = null;
        for(var geoCodeRelationship : stateRelationships) {
            var toGeoCode = geoCodeRelationship.getToGeoCode();

            if(toGeoCode.getLastDetail().getGeoCodeType().equals(countryGeoCodeType)) {
                countryGeoCode = toGeoCode;
                break;
            }
        }

        var countryGeoCodeAliasType = getGeoCodeAliasTypeByName(countryGeoCode.getLastDetail().getGeoCodeType(), com.echothree.model.control.geo.common.GeoCodeAliasTypes.ISO_2_LETTER.name());
        var countryGeoCodeAlias = getGeoCodeAlias(countryGeoCode, countryGeoCodeAliasType);
        var countryIso2Letter = countryGeoCodeAlias.getAlias();

        var geoCodeScopeName = countryIso2Letter + "_" + statePostal2Letter + "_COUNTIES";

        return getGeoCodeScopeByName(geoCodeScopeName);
    }

    public List<GeoCode> getCountiesByState(GeoCodeScope countiesGeoCodeScope) {
        List<GeoCode> counties;
        
        if(countiesGeoCodeScope != null) {
            counties = getGeoCodesByGeoCodeScope(countiesGeoCodeScope);
        } else {
            counties = new ArrayList<>();
        }
        
        return counties;
    }
    
    public CityTransfer getCityTransfer(UserVisit userVisit, GeoCode geoCode) {
        return cityTransferCache.getCityTransfer(userVisit, geoCode);
    }
    
    public List<CityTransfer> getCityTransfers(UserVisit userVisit, Collection<GeoCode> geoCodes) {
        var cityTransfers = new ArrayList<CityTransfer>(geoCodes.size());
        
        geoCodes.forEach((geoCode) ->
                cityTransfers.add(cityTransferCache.getCityTransfer(userVisit, geoCode))
        );
        
        return cityTransfers;
    }

    public GeoCodeScope getCitiesGeoCodeScope(GeoCode stateGeoCode) {
        var stateGeoCodeAliasType = getGeoCodeAliasTypeByName(stateGeoCode.getLastDetail().getGeoCodeType(), com.echothree.model.control.geo.common.GeoCodeAliasTypes.POSTAL_2_LETTER.name());
        var stateGeoCodeAlias = getGeoCodeAlias(stateGeoCode, stateGeoCodeAliasType);
        var statePostal2Letter = stateGeoCodeAlias.getAlias();

        var countryGeoCodeType = getGeoCodeTypeByName(com.echothree.model.control.geo.common.GeoCodeTypes.COUNTRY.name());
        var stateRelationships = getGeoCodeRelationshipsByFromGeoCode(stateGeoCode);
        GeoCode countryGeoCode = null;
        for(var geoCodeRelationship : stateRelationships) {
            var toGeoCode = geoCodeRelationship.getToGeoCode();

            if(toGeoCode.getLastDetail().getGeoCodeType().equals(countryGeoCodeType)) {
                countryGeoCode = toGeoCode;
                break;
            }
        }

        var countryGeoCodeAliasType = getGeoCodeAliasTypeByName(countryGeoCode.getLastDetail().getGeoCodeType(), com.echothree.model.control.geo.common.GeoCodeAliasTypes.ISO_2_LETTER.name());
        var countryGeoCodeAlias = getGeoCodeAlias(countryGeoCode, countryGeoCodeAliasType);
        var countryIso2Letter = countryGeoCodeAlias.getAlias();

        var geoCodeScopeName = countryIso2Letter + "_" + statePostal2Letter + "_CITIES";

        return getGeoCodeScopeByName(geoCodeScopeName);
    }

    public List<GeoCode> getCitiesByState(GeoCodeScope citiesGeoCodeScope) {
        List<GeoCode> cities;
        
        if(citiesGeoCodeScope != null) {
            cities = getGeoCodesByGeoCodeScope(citiesGeoCodeScope);
        } else {
            cities = new ArrayList<>();
        }
        
        return cities;
    }
    
    public PostalCodeTransfer getPostalCodeTransfer(UserVisit userVisit, GeoCode geoCode) {
        return postalCodeTransferCache.getPostalCodeTransfer(userVisit, geoCode);
    }
    
    public List<PostalCodeTransfer> getPostalCodeTransfers(UserVisit userVisit, Collection<GeoCode> geoCodes) {
        var postalCodeTransfers = new ArrayList<PostalCodeTransfer>(geoCodes.size());
        
        geoCodes.forEach((geoCode) ->
                postalCodeTransfers.add(postalCodeTransferCache.getPostalCodeTransfer(userVisit, geoCode))
        );
        
        return postalCodeTransfers;
    }

    public GeoCodeScope getPostalCodesGeoCodeScope(GeoCode countryGeoCode) {
        var countryGeoCodeAliasType = getGeoCodeAliasTypeByName(countryGeoCode.getLastDetail().getGeoCodeType(), com.echothree.model.control.geo.common.GeoCodeAliasTypes.ISO_2_LETTER.name());
        var countryGeoCodeAlias = getGeoCodeAlias(countryGeoCode, countryGeoCodeAliasType);
        var countryIso2Letter = countryGeoCodeAlias.getAlias();
        var geoCodeScopeName = countryIso2Letter + "_ZIP_CODES";

        return getGeoCodeScopeByName(geoCodeScopeName);
    }

    public List<GeoCode> getPostalCodesByCountry(GeoCodeScope postalCodeGeoCodeScope) {
        List<GeoCode> postalCodeTransfers;

        if(postalCodeGeoCodeScope != null) {
            postalCodeTransfers = getGeoCodesByGeoCodeScope(postalCodeGeoCodeScope);
        } else {
            postalCodeTransfers = new ArrayList<>();
        }

        return postalCodeTransfers;
    }

    // --------------------------------------------------------------------------------
    //   Choices Utilities
    // --------------------------------------------------------------------------------
    
    public CountryChoicesBean getCountryChoices(String defaultCountryChoice, Language language, boolean allowNullChoice) {
        var geoCodeType = getGeoCodeTypeByName(com.echothree.model.control.geo.common.GeoCodeTypes.COUNTRY.name());
        var geoCodeAliasType = getDefaultGeoCodeAliasType(geoCodeType);
        var geoCodeScope = getGeoCodeScopeByName(com.echothree.model.control.geo.common.GeoCodeScopes.COUNTRIES.name());
        var geoCodes = getGeoCodesByGeoCodeScope(geoCodeScope);
        var size = geoCodes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultCountryChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var geoCode : geoCodes) {
            var label = getBestGeoCodeDescription(geoCode, language);
            var value = getAliasForCountry(geoCode, geoCodeType, geoCodeAliasType);
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultCountryChoice != null && defaultCountryChoice.equals(value);
            if(usingDefaultChoice || defaultValue == null)
                defaultValue = value;
        }
        
        return new CountryChoicesBean(labels, values, defaultValue);
        
    }
    
    // --------------------------------------------------------------------------------
    //   Geo Code Types
    // --------------------------------------------------------------------------------

    @Inject
    protected GeoCodeTypeDetailFactory geoCodeTypeDetailFactory;

    @Inject
    protected GeoCodeTypeFactory geoCodeTypeFactory;

    @Inject
    GeoCodeTypeTransferCache geoCodeTypeTransferCache;

    public GeoCodeType createGeoCodeType(String geoCodeTypeName, GeoCodeType parentGeoCodeType, Boolean isDefault,
            Integer sortOrder, BasePK createdBy) {
        var defaultGeoCodeType = getDefaultGeoCodeType();
        var defaultFound = defaultGeoCodeType != null;
        
        if(defaultFound && isDefault) {
            var defaultGeoCodeTypeDetailValue = getDefaultGeoCodeTypeDetailValueForUpdate();
            
            defaultGeoCodeTypeDetailValue.setIsDefault(false);
            updateGeoCodeTypeFromValue(defaultGeoCodeTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var geoCodeType = geoCodeTypeFactory.create();
        var geoCodeTypeDetail = geoCodeTypeDetailFactory.create(geoCodeType, geoCodeTypeName,
                parentGeoCodeType, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);
        
        // Convert to R/W
        geoCodeType = geoCodeTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                geoCodeType.getPrimaryKey());
        geoCodeType.setActiveDetail(geoCodeTypeDetail);
        geoCodeType.setLastDetail(geoCodeTypeDetail);
        geoCodeType.store();
        
        sendEvent(geoCodeType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return geoCodeType;
    }

    public long countGeoCodeTypes() {
        return session.getDslContext()
                .selectCount()
                .from(GeoCodeTypes)
                .join(GeoCodeTypeDetails).onKey(GEO_CODE_TYPES_ACTIVE_DETAIL_FK)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countGeoCodeTypesByParentGeoCodeType(GeoCodeType parentGeoCodeType) {
        return session.getDslContext()
                .selectCount()
                .from(GeoCodeTypes)
                .join(GeoCodeTypeDetails).onKey(GEO_CODE_TYPES_ACTIVE_DETAIL_FK)
                .where(GeoCodeTypeDetails.PARENT_GEO_CODE_TYPE.eq(parentGeoCodeType.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.GeoCodeType */
    public GeoCodeType getGeoCodeTypeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new GeoCodeTypePK(entityInstance.getEntityUniqueId());

        return geoCodeTypeFactory.getEntityFromPK(entityPermission, pk);
    }

    public GeoCodeType getGeoCodeTypeByEntityInstance(EntityInstance entityInstance) {
        return getGeoCodeTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public GeoCodeType getGeoCodeTypeByName(String geoCodeTypeName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(GeoCodeTypes.fields())
                .from(GeoCodeTypes)
                .join(GeoCodeTypeDetails).onKey(GEO_CODE_TYPES_ACTIVE_DETAIL_FK)
                .where(GeoCodeTypeDetails.GEO_CODE_TYPE_NAME.eq(geoCodeTypeName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return geoCodeTypeFactory.getEntityFromQuery(entityPermission, query);
    }

    public GeoCodeType getGeoCodeTypeByName(String geoCodeTypeName) {
        return getGeoCodeTypeByName(geoCodeTypeName, EntityPermission.READ_ONLY);
    }

    public GeoCodeType getGeoCodeTypeByNameForUpdate(String geoCodeTypeName) {
        return getGeoCodeTypeByName(geoCodeTypeName, EntityPermission.READ_WRITE);
    }

    public GeoCodeTypeDetailValue getGeoCodeTypeDetailValueForUpdate(GeoCodeType geoCodeType) {
        return geoCodeType == null? null: geoCodeType.getLastDetailForUpdate().getGeoCodeTypeDetailValue().clone();
    }

    public GeoCodeTypeDetailValue getGeoCodeTypeDetailValueByNameForUpdate(String geoCodeTypeName) {
        return getGeoCodeTypeDetailValueForUpdate(getGeoCodeTypeByNameForUpdate(geoCodeTypeName));
    }

    public GeoCodeType getDefaultGeoCodeType(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(GeoCodeTypes.fields())
                .from(GeoCodeTypes)
                .join(GeoCodeTypeDetails).onKey(GEO_CODE_TYPES_ACTIVE_DETAIL_FK)
                .where(GeoCodeTypeDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return geoCodeTypeFactory.getEntityFromQuery(entityPermission, query);
    }

    public GeoCodeType getDefaultGeoCodeType() {
        return getDefaultGeoCodeType(EntityPermission.READ_ONLY);
    }

    public GeoCodeType getDefaultGeoCodeTypeForUpdate() {
        return getDefaultGeoCodeType(EntityPermission.READ_WRITE);
    }

    public GeoCodeTypeDetailValue getDefaultGeoCodeTypeDetailValueForUpdate() {
        return getDefaultGeoCodeTypeForUpdate().getLastDetailForUpdate().getGeoCodeTypeDetailValue().clone();
    }

    private List<GeoCodeType> getGeoCodeTypes(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(GeoCodeTypes.fields())
                .from(GeoCodeTypes)
                .join(GeoCodeTypeDetails).onKey(GEO_CODE_TYPES_ACTIVE_DETAIL_FK);

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(GeoCodeTypeDetails.SORT_ORDER, GeoCodeTypeDetails.GEO_CODE_TYPE_NAME),
                    GeoCodeTypeFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return geoCodeTypeFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<GeoCodeType> getGeoCodeTypes() {
        return getGeoCodeTypes(EntityPermission.READ_ONLY);
    }

    public List<GeoCodeType> getGeoCodeTypesForUpdate() {
        return getGeoCodeTypes(EntityPermission.READ_WRITE);
    }

    private List<GeoCodeType> getGeoCodeTypesByParentGeoCodeType(GeoCodeType parentGeoCodeType,
            EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(GeoCodeTypes.fields())
                .from(GeoCodeTypes)
                .join(GeoCodeTypeDetails).onKey(GEO_CODE_TYPES_ACTIVE_DETAIL_FK)
                .where(GeoCodeTypeDetails.PARENT_GEO_CODE_TYPE.eq(parentGeoCodeType.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(GeoCodeTypeDetails.SORT_ORDER, GeoCodeTypeDetails.GEO_CODE_TYPE_NAME),
                    GeoCodeTypeFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return geoCodeTypeFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<GeoCodeType> getGeoCodeTypesByParentGeoCodeType(GeoCodeType parentGeoCodeType) {
        return getGeoCodeTypesByParentGeoCodeType(parentGeoCodeType, EntityPermission.READ_ONLY);
    }

    public List<GeoCodeType> getGeoCodeTypesByParentGeoCodeTypeForUpdate(GeoCodeType parentGeoCodeType) {
        return getGeoCodeTypesByParentGeoCodeType(parentGeoCodeType, EntityPermission.READ_WRITE);
    }

    public GeoCodeTypeTransfer getGeoCodeTypeTransfer(UserVisit userVisit, GeoCodeType geoCodeType) {
        return geoCodeTypeTransferCache.getGeoCodeTypeTransfer(userVisit, geoCodeType);
    }

    public List<GeoCodeTypeTransfer> getGeoCodeTypeTransfers(UserVisit userVisit, Collection<GeoCodeType> geoCodeTypes) {
        List<GeoCodeTypeTransfer> geoCodeTypeTransfers = new ArrayList<>(geoCodeTypes.size());

        geoCodeTypes.forEach((geoCodeType) ->
                geoCodeTypeTransfers.add(geoCodeTypeTransferCache.getGeoCodeTypeTransfer(userVisit, geoCodeType))
        );

        return geoCodeTypeTransfers;
    }

    public List<GeoCodeTypeTransfer> getGeoCodeTypeTransfers(UserVisit userVisit) {
        return getGeoCodeTypeTransfers(userVisit, getGeoCodeTypes());
    }

    public GeoCodeTypeChoicesBean getGeoCodeTypeChoices(String defaultGeoCodeTypeChoice, Language language,
            boolean allowNullChoice) {
        var geoCodeTypes = getGeoCodeTypes();
        var size = geoCodeTypes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultGeoCodeTypeChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var geoCodeType : geoCodeTypes) {
            var geoCodeTypeDetail = geoCodeType.getLastDetail();
            
            var label = getBestGeoCodeTypeDescription(geoCodeType, language);
            var value = geoCodeTypeDetail.getGeoCodeTypeName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultGeoCodeTypeChoice != null && defaultGeoCodeTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && geoCodeTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new GeoCodeTypeChoicesBean(labels, values, defaultValue);
    }
    
    public boolean isParentGeoCodeTypeSafe(GeoCodeType geoCodeType, GeoCodeType parentGeoCodeType) {
        var safe = true;

        if(parentGeoCodeType != null) {
            Set<GeoCodeType> parentGeoCodeTypes = new HashSet<>();

            parentGeoCodeTypes.add(geoCodeType);
            do {
                if(parentGeoCodeTypes.contains(parentGeoCodeType)) {
                    safe = false;
                    break;
                }

                parentGeoCodeTypes.add(parentGeoCodeType);
                parentGeoCodeType = parentGeoCodeType.getLastDetail().getParentGeoCodeType();
            } while(parentGeoCodeType != null);
        }

        return safe;
    }

    private void updateGeoCodeTypeFromValue(GeoCodeTypeDetailValue geoCodeTypeDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(geoCodeTypeDetailValue.hasBeenModified()) {
            var geoCodeType = geoCodeTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     geoCodeTypeDetailValue.getGeoCodeTypePK());
            var geoCodeTypeDetail = geoCodeType.getActiveDetailForUpdate();
            
            geoCodeTypeDetail.setThruTime(session.getStartTime());
            geoCodeTypeDetail.store();

            var geoCodeTypePK = geoCodeTypeDetail.getGeoCodeTypePK();
            var geoCodeTypeName = geoCodeTypeDetailValue.getGeoCodeTypeName();
            var parentGeoCodeTypePK = geoCodeTypeDetailValue.getParentGeoCodeTypePK();
            var isDefault = geoCodeTypeDetailValue.getIsDefault();
            var sortOrder = geoCodeTypeDetailValue.getSortOrder();
            
            if(checkDefault) {
                var defaultGeoCodeType = getDefaultGeoCodeType();
                var defaultFound = defaultGeoCodeType != null && !defaultGeoCodeType.equals(geoCodeType);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultGeoCodeTypeDetailValue = getDefaultGeoCodeTypeDetailValueForUpdate();
                    
                    defaultGeoCodeTypeDetailValue.setIsDefault(false);
                    updateGeoCodeTypeFromValue(defaultGeoCodeTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }
            
            geoCodeTypeDetail = geoCodeTypeDetailFactory.create(geoCodeTypePK,
                    geoCodeTypeName, parentGeoCodeTypePK, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);
            
            geoCodeType.setActiveDetail(geoCodeTypeDetail);
            geoCodeType.setLastDetail(geoCodeTypeDetail);
            
            sendEvent(geoCodeTypePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void updateGeoCodeTypeFromValue(GeoCodeTypeDetailValue geoCodeTypeDetailValue, BasePK updatedBy) {
        updateGeoCodeTypeFromValue(geoCodeTypeDetailValue, true, updatedBy);
    }
    
    private void deleteGeoCodeType(GeoCodeType geoCodeType, boolean checkDefault, BasePK deletedBy) {
        var geoCodeTypeDetail = geoCodeType.getLastDetailForUpdate();

        deleteGeoCodeTypesByParentGeoCodeType(geoCodeType, deletedBy);
        // deleteGeoCodesByGeoCodeType(geoCodeType, deletedBy);
        // deleteGeoCodeAliasTypesByGeoCodeType(geoCodeType, deletedBy);
        deleteGeoCodeTypeDescriptionsByGeoCodeType(geoCodeType, deletedBy);
        
        geoCodeTypeDetail.setThruTime(session.getStartTime());
        geoCodeType.setActiveDetail(null);
        geoCodeType.store();

        if(checkDefault) {
            // Check for default, and pick one if necessary
            var defaultGeoCodeType = getDefaultGeoCodeType();

            if(defaultGeoCodeType == null) {
                var geoCodeTypes = getGeoCodeTypesForUpdate();

                if(!geoCodeTypes.isEmpty()) {
                    var iter = geoCodeTypes.iterator();
                    if(iter.hasNext()) {
                        defaultGeoCodeType = iter.next();
                    }
                    var geoCodeTypeDetailValue = Objects.requireNonNull(defaultGeoCodeType).getLastDetailForUpdate().getGeoCodeTypeDetailValue().clone();

                    geoCodeTypeDetailValue.setIsDefault(true);
                    updateGeoCodeTypeFromValue(geoCodeTypeDetailValue, false, deletedBy);
                }
            }
        }

        sendEvent(geoCodeType.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    public void deleteGeoCodeType(GeoCodeType geoCodeType, BasePK deletedBy) {
        deleteGeoCodeType(geoCodeType, true, deletedBy);
    }

    private void deleteGeoCodeTypes(List<GeoCodeType> geoCodeTypes, boolean checkDefault, BasePK deletedBy) {
        geoCodeTypes.forEach((geoCodeType) -> deleteGeoCodeType(geoCodeType, checkDefault, deletedBy));
    }

    public void deleteGeoCodeTypes(List<GeoCodeType> geoCodeTypes, BasePK deletedBy) {
        deleteGeoCodeTypes(geoCodeTypes, true, deletedBy);
    }

    private void deleteGeoCodeTypesByParentGeoCodeType(GeoCodeType parentGeoCodeType, BasePK deletedBy) {
        deleteGeoCodeTypes(getGeoCodeTypesByParentGeoCodeTypeForUpdate(parentGeoCodeType), false, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Geo Code Type Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    protected GeoCodeTypeDescriptionFactory geoCodeTypeDescriptionFactory;

    @Inject
    GeoCodeTypeDescriptionTransferCache geoCodeTypeDescriptionTransferCache;

    public GeoCodeTypeDescription createGeoCodeTypeDescription(GeoCodeType geoCodeType, Language language, String description, BasePK createdBy) {
        var geoCodeTypeDescription = geoCodeTypeDescriptionFactory.create(geoCodeType,
                language, description, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(geoCodeType.getPrimaryKey(), EventTypes.MODIFY, geoCodeTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return geoCodeTypeDescription;
    }
    
    private GeoCodeTypeDescription getGeoCodeTypeDescription(GeoCodeType geoCodeType, Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(GeoCodeTypeDescriptions.fields())
                .from(GeoCodeTypeDescriptions)
                .where(GeoCodeTypeDescriptions.GEO_CODE_TYPE.eq(geoCodeType.getPrimaryKey()),
                        GeoCodeTypeDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        GeoCodeTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return geoCodeTypeDescriptionFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public GeoCodeTypeDescription getGeoCodeTypeDescription(GeoCodeType geoCodeType, Language language) {
        return getGeoCodeTypeDescription(geoCodeType, language, EntityPermission.READ_ONLY);
    }
    
    public GeoCodeTypeDescription getGeoCodeTypeDescriptionForUpdate(GeoCodeType geoCodeType, Language language) {
        return getGeoCodeTypeDescription(geoCodeType, language, EntityPermission.READ_WRITE);
    }
    
    public GeoCodeTypeDescriptionValue getGeoCodeTypeDescriptionValue(GeoCodeTypeDescription geoCodeTypeDescription) {
        return geoCodeTypeDescription == null? null: geoCodeTypeDescription.getGeoCodeTypeDescriptionValue().clone();
    }
    
    public GeoCodeTypeDescriptionValue getGeoCodeTypeDescriptionValueForUpdate(GeoCodeType geoCodeType, Language language) {
        return getGeoCodeTypeDescriptionValue(getGeoCodeTypeDescriptionForUpdate(geoCodeType, language));
    }
    
    
    private List<GeoCodeTypeDescription> getGeoCodeTypeDescriptionsByGeoCodeType(GeoCodeType geoCodeType, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(GeoCodeTypeDescriptions.fields())
                    .from(GeoCodeTypeDescriptions)
                    .join(Languages).onKey(GEO_CODE_TYPE_DESCRIPTIONS_LANGUAGE_FK)
                    .where(GeoCodeTypeDescriptions.GEO_CODE_TYPE.eq(geoCodeType.getPrimaryKey()),
                            GeoCodeTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    GeoCodeTypeDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(GeoCodeTypeDescriptions.fields())
                    .from(GeoCodeTypeDescriptions)
                    .where(GeoCodeTypeDescriptions.GEO_CODE_TYPE.eq(geoCodeType.getPrimaryKey()),
                            GeoCodeTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return geoCodeTypeDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<GeoCodeTypeDescription> getGeoCodeTypeDescriptionsByGeoCodeType(GeoCodeType geoCodeType) {
        return getGeoCodeTypeDescriptionsByGeoCodeType(geoCodeType, EntityPermission.READ_ONLY);
    }
    
    public List<GeoCodeTypeDescription> getGeoCodeTypeDescriptionsByGeoCodeTypeForUpdate(GeoCodeType geoCodeType) {
        return getGeoCodeTypeDescriptionsByGeoCodeType(geoCodeType, EntityPermission.READ_WRITE);
    }
    
    public String getBestGeoCodeTypeDescription(GeoCodeType geoCodeType, Language language) {
        String description;
        var geoCodeTypeDescription = getGeoCodeTypeDescription(geoCodeType, language);
        
        if(geoCodeTypeDescription == null && !language.getIsDefault()) {
            geoCodeTypeDescription = getGeoCodeTypeDescription(geoCodeType, partyControl.getDefaultLanguage());
        }
        
        if(geoCodeTypeDescription == null) {
            description = geoCodeType.getLastDetail().getGeoCodeTypeName();
        } else {
            description = geoCodeTypeDescription.getDescription();
        }
        
        return description;
    }
    
    public GeoCodeTypeDescriptionTransfer getGeoCodeTypeDescriptionTransfer(UserVisit userVisit, GeoCodeTypeDescription geoCodeTypeDescription) {
        return geoCodeTypeDescriptionTransferCache.getGeoCodeTypeDescriptionTransfer(userVisit, geoCodeTypeDescription);
    }
    
    public List<GeoCodeTypeDescriptionTransfer> getGeoCodeTypeDescriptionTransfers(UserVisit userVisit, GeoCodeType geoCodeType) {
        var geoCodeTypeDescriptions = getGeoCodeTypeDescriptionsByGeoCodeType(geoCodeType);
        List<GeoCodeTypeDescriptionTransfer> geoCodeTypeDescriptionTransfers = new ArrayList<>(geoCodeTypeDescriptions.size());

        geoCodeTypeDescriptions.forEach((geoCodeTypeDescription) -> {
            geoCodeTypeDescriptionTransfers.add(geoCodeTypeDescriptionTransferCache.getGeoCodeTypeDescriptionTransfer(userVisit, geoCodeTypeDescription));
        });

        return geoCodeTypeDescriptionTransfers;
    }
    
    public void updateGeoCodeTypeDescriptionFromValue(GeoCodeTypeDescriptionValue geoCodeTypeDescriptionValue, BasePK updatedBy) {
        if(geoCodeTypeDescriptionValue.hasBeenModified()) {
            var geoCodeTypeDescription = geoCodeTypeDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE, geoCodeTypeDescriptionValue.getPrimaryKey());
            
            geoCodeTypeDescription.setThruTime(session.getStartTime());
            geoCodeTypeDescription.store();

            var geoCodeType = geoCodeTypeDescription.getGeoCodeType();
            var language = geoCodeTypeDescription.getLanguage();
            var description = geoCodeTypeDescriptionValue.getDescription();
            
            geoCodeTypeDescription = geoCodeTypeDescriptionFactory.create(geoCodeType, language, description, session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(geoCodeType.getPrimaryKey(), EventTypes.MODIFY, geoCodeTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteGeoCodeTypeDescription(GeoCodeTypeDescription geoCodeTypeDescription, BasePK deletedBy) {
        geoCodeTypeDescription.setThruTime(session.getStartTime());
        
        sendEvent(geoCodeTypeDescription.getGeoCodeTypePK(), EventTypes.MODIFY, geoCodeTypeDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
        
    }
    
    public void deleteGeoCodeTypeDescriptionsByGeoCodeType(GeoCodeType geoCodeType, BasePK deletedBy) {
        var geoCodeTypeDescriptions = getGeoCodeTypeDescriptionsByGeoCodeTypeForUpdate(geoCodeType);
        
        geoCodeTypeDescriptions.forEach((geoCodeTypeDescription) -> 
                deleteGeoCodeTypeDescription(geoCodeTypeDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Geo Code Scopes
    // --------------------------------------------------------------------------------

    @Inject
    protected GeoCodeScopeDetailFactory geoCodeScopeDetailFactory;

    @Inject
    protected GeoCodeScopeFactory geoCodeScopeFactory;

    @Inject
    GeoCodeScopeTransferCache geoCodeScopeTransferCache;

    public GeoCodeScope createGeoCodeScope(String geoCodeScopeName, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultGeoCodeScope = getDefaultGeoCodeScope();
        var defaultFound = defaultGeoCodeScope != null;
        
        if(defaultFound && isDefault) {
            var defaultGeoCodeScopeDetailValue = getDefaultGeoCodeScopeDetailValueForUpdate();
            
            defaultGeoCodeScopeDetailValue.setIsDefault(false);
            updateGeoCodeScopeFromValue(defaultGeoCodeScopeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var geoCodeScope = geoCodeScopeFactory.create();
        var geoCodeScopeDetail = geoCodeScopeDetailFactory.create(
                geoCodeScope, geoCodeScopeName, isDefault, sortOrder, session.getStartTime(),
                Session.MAX_TIME);
        
        // Convert to R/W
        geoCodeScope = geoCodeScopeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                geoCodeScope.getPrimaryKey());
        geoCodeScope.setActiveDetail(geoCodeScopeDetail);
        geoCodeScope.setLastDetail(geoCodeScopeDetail);
        geoCodeScope.store();
        
        sendEvent(geoCodeScope.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return geoCodeScope;
    }

    public long countGeoCodeScopes() {
        return session.getDslContext()
                .selectCount()
                .from(GeoCodeScopes)
                .join(GeoCodeScopeDetails).onKey(GEO_CODE_SCOPES_ACTIVE_DETAIL_FK)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.GeoCodeScope */
    public GeoCodeScope getGeoCodeScopeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new GeoCodeScopePK(entityInstance.getEntityUniqueId());

        return geoCodeScopeFactory.getEntityFromPK(entityPermission, pk);
    }

    public GeoCodeScope getGeoCodeScopeByEntityInstance(EntityInstance entityInstance) {
        return getGeoCodeScopeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public GeoCodeScope getGeoCodeScopeByName(String geoCodeScopeName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(GeoCodeScopes.fields())
                .from(GeoCodeScopes)
                .join(GeoCodeScopeDetails).onKey(GEO_CODE_SCOPES_ACTIVE_DETAIL_FK)
                .where(GeoCodeScopeDetails.GEO_CODE_SCOPE_NAME.eq(geoCodeScopeName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return geoCodeScopeFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public GeoCodeScope getGeoCodeScopeByName(String geoCodeScopeName) {
        return getGeoCodeScopeByName(geoCodeScopeName, EntityPermission.READ_ONLY);
    }
    
    public GeoCodeScope getGeoCodeScopeByNameForUpdate(String geoCodeScopeName) {
        return getGeoCodeScopeByName(geoCodeScopeName, EntityPermission.READ_WRITE);
    }
    
    public GeoCodeScopeDetailValue getGeoCodeScopeDetailValueForUpdate(GeoCodeScope geoCodeScope) {
        return geoCodeScope == null? null: geoCodeScope.getLastDetailForUpdate().getGeoCodeScopeDetailValue().clone();
    }
    
    public GeoCodeScopeDetailValue getGeoCodeScopeDetailValueByNameForUpdate(String geoCodeScopeName) {
        return getGeoCodeScopeDetailValueForUpdate(getGeoCodeScopeByNameForUpdate(geoCodeScopeName));
    }
    
    public GeoCodeScope getDefaultGeoCodeScope(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(GeoCodeScopes.fields())
                .from(GeoCodeScopes)
                .join(GeoCodeScopeDetails).onKey(GEO_CODE_SCOPES_ACTIVE_DETAIL_FK)
                .where(GeoCodeScopeDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return geoCodeScopeFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public GeoCodeScope getDefaultGeoCodeScope() {
        return getDefaultGeoCodeScope(EntityPermission.READ_ONLY);
    }
    
    public GeoCodeScope getDefaultGeoCodeScopeForUpdate() {
        return getDefaultGeoCodeScope(EntityPermission.READ_WRITE);
    }
    
    public GeoCodeScopeDetailValue getDefaultGeoCodeScopeDetailValueForUpdate() {
        return getDefaultGeoCodeScopeForUpdate().getLastDetailForUpdate().getGeoCodeScopeDetailValue().clone();
    }
    
    private List<GeoCodeScope> getGeoCodeScopes(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(GeoCodeScopes.fields())
                .from(GeoCodeScopes)
                .join(GeoCodeScopeDetails).onKey(GEO_CODE_SCOPES_ACTIVE_DETAIL_FK);

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(GeoCodeScopeDetails.SORT_ORDER, GeoCodeScopeDetails.GEO_CODE_SCOPE_NAME),
                    GeoCodeScopeFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return geoCodeScopeFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<GeoCodeScope> getGeoCodeScopes() {
        return getGeoCodeScopes(EntityPermission.READ_ONLY);
    }
    
    public List<GeoCodeScope> getGeoCodeScopesForUpdate() {
        return getGeoCodeScopes(EntityPermission.READ_WRITE);
    }
    
    public GeoCodeScopeTransfer getGeoCodeScopeTransfer(UserVisit userVisit, GeoCodeScope geoCodeScope) {
        return geoCodeScopeTransferCache.getGeoCodeScopeTransfer(userVisit, geoCodeScope);
    }

    public List<GeoCodeScopeTransfer> getGeoCodeScopeTransfers(UserVisit userVisit, Collection<GeoCodeScope> geoCodeScopes) {
        List<GeoCodeScopeTransfer> geoCodeScopeTransfers = new ArrayList<>(geoCodeScopes.size());

        geoCodeScopes.forEach((geoCodeScope) ->
                geoCodeScopeTransfers.add(geoCodeScopeTransferCache.getGeoCodeScopeTransfer(userVisit, geoCodeScope))
        );

        return geoCodeScopeTransfers;
    }

    public List<GeoCodeScopeTransfer> getGeoCodeScopeTransfers(UserVisit userVisit) {
        return getGeoCodeScopeTransfers(userVisit, getGeoCodeScopes());
    }

    public GeoCodeScopeChoicesBean getGeoCodeScopeChoices(String defaultGeoCodeScopeChoice, Language language,
            boolean allowNullChoice) {
        var geoCodeScopes = getGeoCodeScopes();
        var size = geoCodeScopes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultGeoCodeScopeChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var geoCodeScope : geoCodeScopes) {
            var geoCodeScopeDetail = geoCodeScope.getLastDetail();
            
            var label = getBestGeoCodeScopeDescription(geoCodeScope, language);
            var value = geoCodeScopeDetail.getGeoCodeScopeName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultGeoCodeScopeChoice != null && defaultGeoCodeScopeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && geoCodeScopeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new GeoCodeScopeChoicesBean(labels, values, defaultValue);
    }
    
    private void updateGeoCodeScopeFromValue(GeoCodeScopeDetailValue geoCodeScopeDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(geoCodeScopeDetailValue.hasBeenModified()) {
            var geoCodeScope = geoCodeScopeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     geoCodeScopeDetailValue.getGeoCodeScopePK());
            var geoCodeScopeDetail = geoCodeScope.getActiveDetailForUpdate();
            
            geoCodeScopeDetail.setThruTime(session.getStartTime());
            geoCodeScopeDetail.store();

            var geoCodeScopePK = geoCodeScopeDetail.getGeoCodeScopePK();
            var geoCodeScopeName = geoCodeScopeDetailValue.getGeoCodeScopeName();
            var isDefault = geoCodeScopeDetailValue.getIsDefault();
            var sortOrder = geoCodeScopeDetailValue.getSortOrder();
            
            if(checkDefault) {
                var defaultGeoCodeScope = getDefaultGeoCodeScope();
                var defaultFound = defaultGeoCodeScope != null && !defaultGeoCodeScope.equals(geoCodeScope);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultGeoCodeScopeDetailValue = getDefaultGeoCodeScopeDetailValueForUpdate();
                    
                    defaultGeoCodeScopeDetailValue.setIsDefault(false);
                    updateGeoCodeScopeFromValue(defaultGeoCodeScopeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }
            
            geoCodeScopeDetail = geoCodeScopeDetailFactory.create(geoCodeScopePK, geoCodeScopeName,
                    isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);
            
            geoCodeScope.setActiveDetail(geoCodeScopeDetail);
            geoCodeScope.setLastDetail(geoCodeScopeDetail);
            
            sendEvent(geoCodeScopePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void updateGeoCodeScopeFromValue(GeoCodeScopeDetailValue geoCodeScopeDetailValue, BasePK updatedBy) {
        updateGeoCodeScopeFromValue(geoCodeScopeDetailValue, true, updatedBy);
    }
    
    public void deleteGeoCodeScope(GeoCodeScope geoCodeScope, BasePK deletedBy) {
        // TODO: deleteGeoCodesByGeoCodeScope(geoCodeScope, deletedBy);
        deleteGeoCodeAliasesByGeoCodeScope(geoCodeScope, deletedBy);
        deleteGeoCodeScopeDescriptionsByGeoCodeScope(geoCodeScope, deletedBy);

        var geoCodeScopeDetail = geoCodeScope.getLastDetailForUpdate();
        geoCodeScopeDetail.setThruTime(session.getStartTime());
        geoCodeScope.setActiveDetail(null);
        geoCodeScope.store();
        
        // Check for default, and pick one if necessary
        var defaultGeoCodeScope = getDefaultGeoCodeScope();
        if(defaultGeoCodeScope == null) {
            var geoCodeScopes = getGeoCodeScopesForUpdate();
            
            if(!geoCodeScopes.isEmpty()) {
                var iter = geoCodeScopes.iterator();
                if(iter.hasNext()) {
                    defaultGeoCodeScope = iter.next();
                }
                var geoCodeScopeDetailValue = Objects.requireNonNull(defaultGeoCodeScope).getLastDetailForUpdate().getGeoCodeScopeDetailValue().clone();
                
                geoCodeScopeDetailValue.setIsDefault(true);
                updateGeoCodeScopeFromValue(geoCodeScopeDetailValue, false, deletedBy);
            }
        }
        
        sendEvent(geoCodeScope.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Geo Code Scope Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    protected GeoCodeScopeDescriptionFactory geoCodeScopeDescriptionFactory;

    @Inject
    GeoCodeScopeDescriptionTransferCache geoCodeScopeDescriptionTransferCache;

    public GeoCodeScopeDescription createGeoCodeScopeDescription(GeoCodeScope geoCodeScope, Language language, String description, BasePK createdBy) {
        var geoCodeScopeDescription = geoCodeScopeDescriptionFactory.create(geoCodeScope, language, description, session.getStartTime(),
                Session.MAX_TIME);
        
        sendEvent(geoCodeScope.getPrimaryKey(), EventTypes.MODIFY, geoCodeScopeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return geoCodeScopeDescription;
    }
    
    private GeoCodeScopeDescription getGeoCodeScopeDescription(GeoCodeScope geoCodeScope, Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(GeoCodeScopeDescriptions.fields())
                .from(GeoCodeScopeDescriptions)
                .where(GeoCodeScopeDescriptions.GEO_CODE_SCOPE.eq(geoCodeScope.getPrimaryKey()),
                        GeoCodeScopeDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        GeoCodeScopeDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return geoCodeScopeDescriptionFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public GeoCodeScopeDescription getGeoCodeScopeDescription(GeoCodeScope geoCodeScope, Language language) {
        return getGeoCodeScopeDescription(geoCodeScope, language, EntityPermission.READ_ONLY);
    }
    
    public GeoCodeScopeDescription getGeoCodeScopeDescriptionForUpdate(GeoCodeScope geoCodeScope, Language language) {
        return getGeoCodeScopeDescription(geoCodeScope, language, EntityPermission.READ_WRITE);
    }
    
    public GeoCodeScopeDescriptionValue getGeoCodeScopeDescriptionValue(GeoCodeScopeDescription geoCodeScopeDescription) {
        return geoCodeScopeDescription == null? null: geoCodeScopeDescription.getGeoCodeScopeDescriptionValue().clone();
    }
    
    public GeoCodeScopeDescriptionValue getGeoCodeScopeDescriptionValueForUpdate(GeoCodeScope geoCodeScope, Language language) {
        return getGeoCodeScopeDescriptionValue(getGeoCodeScopeDescriptionForUpdate(geoCodeScope, language));
    }
    
    
    private List<GeoCodeScopeDescription> getGeoCodeScopeDescriptionsByGeoCodeScope(GeoCodeScope geoCodeScope, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(GeoCodeScopeDescriptions.fields())
                    .from(GeoCodeScopeDescriptions)
                    .join(Languages).onKey(GEO_CODE_SCOPE_DESCRIPTIONS_LANGUAGE_FK)
                    .where(GeoCodeScopeDescriptions.GEO_CODE_SCOPE.eq(geoCodeScope.getPrimaryKey()),
                            GeoCodeScopeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    GeoCodeScopeDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(GeoCodeScopeDescriptions.fields())
                    .from(GeoCodeScopeDescriptions)
                    .where(GeoCodeScopeDescriptions.GEO_CODE_SCOPE.eq(geoCodeScope.getPrimaryKey()),
                            GeoCodeScopeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return geoCodeScopeDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<GeoCodeScopeDescription> getGeoCodeScopeDescriptionsByGeoCodeScope(GeoCodeScope geoCodeScope) {
        return getGeoCodeScopeDescriptionsByGeoCodeScope(geoCodeScope, EntityPermission.READ_ONLY);
    }
    
    public List<GeoCodeScopeDescription> getGeoCodeScopeDescriptionsByGeoCodeScopeForUpdate(GeoCodeScope geoCodeScope) {
        return getGeoCodeScopeDescriptionsByGeoCodeScope(geoCodeScope, EntityPermission.READ_WRITE);
    }
    
    public String getBestGeoCodeScopeDescription(GeoCodeScope geoCodeScope, Language language) {
        String description;
        var geoCodeScopeDescription = getGeoCodeScopeDescription(geoCodeScope, language);
        
        if(geoCodeScopeDescription == null && !language.getIsDefault()) {
            geoCodeScopeDescription = getGeoCodeScopeDescription(geoCodeScope, partyControl.getDefaultLanguage());
        }
        
        if(geoCodeScopeDescription == null) {
            description = geoCodeScope.getLastDetail().getGeoCodeScopeName();
        } else {
            description = geoCodeScopeDescription.getDescription();
        }
        
        return description;
    }
    
    public GeoCodeScopeDescriptionTransfer getGeoCodeScopeDescriptionTransfer(UserVisit userVisit, GeoCodeScopeDescription geoCodeScopeDescription) {
        return geoCodeScopeDescriptionTransferCache.getGeoCodeScopeDescriptionTransfer(userVisit, geoCodeScopeDescription);
    }
    
    public List<GeoCodeScopeDescriptionTransfer> getGeoCodeScopeDescriptionTransfers(UserVisit userVisit, GeoCodeScope geoCodeScope) {
        var geoCodeScopeDescriptions = getGeoCodeScopeDescriptionsByGeoCodeScope(geoCodeScope);
        List<GeoCodeScopeDescriptionTransfer> geoCodeScopeDescriptionTransfers = new ArrayList<>(geoCodeScopeDescriptions.size());

        geoCodeScopeDescriptions.forEach((geoCodeScopeDescription) -> {
            geoCodeScopeDescriptionTransfers.add(geoCodeScopeDescriptionTransferCache.getGeoCodeScopeDescriptionTransfer(userVisit, geoCodeScopeDescription));
        });

        return geoCodeScopeDescriptionTransfers;
    }
    
    public void updateGeoCodeScopeDescriptionFromValue(GeoCodeScopeDescriptionValue geoCodeScopeDescriptionValue, BasePK updatedBy) {
        if(geoCodeScopeDescriptionValue.hasBeenModified()) {
            var geoCodeScopeDescription = geoCodeScopeDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE, geoCodeScopeDescriptionValue.getPrimaryKey());
            
            geoCodeScopeDescription.setThruTime(session.getStartTime());
            geoCodeScopeDescription.store();

            var geoCodeScope = geoCodeScopeDescription.getGeoCodeScope();
            var language = geoCodeScopeDescription.getLanguage();
            var description = geoCodeScopeDescriptionValue.getDescription();
            
            geoCodeScopeDescription = geoCodeScopeDescriptionFactory.create(geoCodeScope, language, description, session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(geoCodeScope.getPrimaryKey(), EventTypes.MODIFY, geoCodeScopeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteGeoCodeScopeDescription(GeoCodeScopeDescription geoCodeScopeDescription, BasePK deletedBy) {
        geoCodeScopeDescription.setThruTime(session.getStartTime());
        
        sendEvent(geoCodeScopeDescription.getGeoCodeScopePK(), EventTypes.MODIFY, geoCodeScopeDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
        
    }
    
    public void deleteGeoCodeScopeDescriptionsByGeoCodeScope(GeoCodeScope geoCodeScope, BasePK deletedBy) {
        var geoCodeScopeDescriptions = getGeoCodeScopeDescriptionsByGeoCodeScopeForUpdate(geoCodeScope);
        
        geoCodeScopeDescriptions.forEach((geoCodeScopeDescription) -> 
                deleteGeoCodeScopeDescription(geoCodeScopeDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Geo Code Alias Types
    // --------------------------------------------------------------------------------

    @Inject
    protected GeoCodeAliasTypeDetailFactory geoCodeAliasTypeDetailFactory;

    @Inject
    protected GeoCodeAliasTypeFactory geoCodeAliasTypeFactory;

    @Inject
    GeoCodeAliasTypeTransferCache geoCodeAliasTypeTransferCache;

    public GeoCodeAliasType createGeoCodeAliasType(GeoCodeType geoCodeType, String geoCodeAliasTypeName, String validationPattern, Boolean isRequired,
            Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultGeoCodeAliasType = getDefaultGeoCodeAliasType(geoCodeType);
        var defaultFound = defaultGeoCodeAliasType != null;
        
        if(defaultFound && isDefault) {
            var defaultGeoCodeAliasTypeDetailValue = getDefaultGeoCodeAliasTypeDetailValueForUpdate(geoCodeType);
            
            defaultGeoCodeAliasTypeDetailValue.setIsDefault(false);
            updateGeoCodeAliasTypeFromValue(defaultGeoCodeAliasTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var geoCodeAliasType = geoCodeAliasTypeFactory.create();
        var geoCodeAliasTypeDetail = geoCodeAliasTypeDetailFactory.create( geoCodeAliasType, geoCodeType,
                geoCodeAliasTypeName, validationPattern, isRequired, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);
        
        // Convert to R/W
        geoCodeAliasType = geoCodeAliasTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                geoCodeAliasType.getPrimaryKey());
        geoCodeAliasType.setActiveDetail(geoCodeAliasTypeDetail);
        geoCodeAliasType.setLastDetail(geoCodeAliasTypeDetail);
        geoCodeAliasType.store();
        
        sendEvent(geoCodeAliasType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return geoCodeAliasType;
    }

    public long countGeoCodeAliasTypesByGeoCodeType(GeoCodeType geoCodeType) {
        return session.getDslContext()
                .selectCount()
                .from(GeoCodeAliasTypes)
                .join(GeoCodeAliasTypeDetails).onKey(GEO_CODE_ALIAS_TYPES_ACTIVE_DETAIL_FK)
                .where(GeoCodeAliasTypeDetails.GEO_CODE_TYPE.eq(geoCodeType.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private GeoCodeAliasType getGeoCodeAliasTypeByName(GeoCodeType geoCodeType, String geoCodeAliasTypeName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(GeoCodeAliasTypes.fields())
                .from(GeoCodeAliasTypes)
                .join(GeoCodeAliasTypeDetails).onKey(GEO_CODE_ALIAS_TYPES_ACTIVE_DETAIL_FK)
                .where(GeoCodeAliasTypeDetails.GEO_CODE_TYPE.eq(geoCodeType.getPrimaryKey()),
                        GeoCodeAliasTypeDetails.GEO_CODE_ALIAS_TYPE_NAME.eq(geoCodeAliasTypeName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return geoCodeAliasTypeFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public GeoCodeAliasType getGeoCodeAliasTypeByName(GeoCodeType geoCodeType, String geoCodeAliasTypeName) {
        return getGeoCodeAliasTypeByName(geoCodeType, geoCodeAliasTypeName, EntityPermission.READ_ONLY);
    }
    
    public GeoCodeAliasType getGeoCodeAliasTypeByNameForUpdate(GeoCodeType geoCodeType, String geoCodeAliasTypeName) {
        return getGeoCodeAliasTypeByName(geoCodeType, geoCodeAliasTypeName, EntityPermission.READ_WRITE);
    }
    
    public GeoCodeAliasTypeDetailValue getGeoCodeAliasTypeDetailValueForUpdate(GeoCodeAliasType geoCodeAliasType) {
        return geoCodeAliasType == null? null: geoCodeAliasType.getLastDetailForUpdate().getGeoCodeAliasTypeDetailValue().clone();
    }
    
    public GeoCodeAliasTypeDetailValue getGeoCodeAliasTypeDetailValueByNameForUpdate(GeoCodeType geoCodeType,
            String geoCodeAliasTypeName) {
        return getGeoCodeAliasTypeDetailValueForUpdate(getGeoCodeAliasTypeByNameForUpdate(geoCodeType, geoCodeAliasTypeName));
    }
    
    private GeoCodeAliasType getDefaultGeoCodeAliasType(GeoCodeType geoCodeType, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(GeoCodeAliasTypes.fields())
                .from(GeoCodeAliasTypes)
                .join(GeoCodeAliasTypeDetails).onKey(GEO_CODE_ALIAS_TYPES_ACTIVE_DETAIL_FK)
                .where(GeoCodeAliasTypeDetails.GEO_CODE_TYPE.eq(geoCodeType.getPrimaryKey()),
                        GeoCodeAliasTypeDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return geoCodeAliasTypeFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public GeoCodeAliasType getDefaultGeoCodeAliasType(GeoCodeType geoCodeType) {
        return getDefaultGeoCodeAliasType(geoCodeType, EntityPermission.READ_ONLY);
    }
    
    public GeoCodeAliasType getDefaultGeoCodeAliasTypeForUpdate(GeoCodeType geoCodeType) {
        return getDefaultGeoCodeAliasType(geoCodeType, EntityPermission.READ_WRITE);
    }
    
    public GeoCodeAliasTypeDetailValue getDefaultGeoCodeAliasTypeDetailValueForUpdate(GeoCodeType geoCodeType) {
        return getDefaultGeoCodeAliasTypeForUpdate(geoCodeType).getLastDetailForUpdate().getGeoCodeAliasTypeDetailValue().clone();
    }
    
    private List<GeoCodeAliasType> getGeoCodeAliasTypes(GeoCodeType geoCodeType, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(GeoCodeAliasTypes.fields())
                .from(GeoCodeAliasTypes)
                .join(GeoCodeAliasTypeDetails).onKey(GEO_CODE_ALIAS_TYPES_ACTIVE_DETAIL_FK)
                .where(GeoCodeAliasTypeDetails.GEO_CODE_TYPE.eq(geoCodeType.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(GeoCodeAliasTypeDetails.SORT_ORDER, GeoCodeAliasTypeDetails.GEO_CODE_ALIAS_TYPE_NAME),
                    GeoCodeAliasTypeFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return geoCodeAliasTypeFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<GeoCodeAliasType> getGeoCodeAliasTypes(GeoCodeType geoCodeType) {
        return getGeoCodeAliasTypes(geoCodeType, EntityPermission.READ_ONLY);
    }
    
    public List<GeoCodeAliasType> getGeoCodeAliasTypesForUpdate(GeoCodeType geoCodeType) {
        return getGeoCodeAliasTypes(geoCodeType, EntityPermission.READ_WRITE);
    }
    
    private List<GeoCodeAliasType> getGeoCodeAliasTypesExceptDefault(GeoCodeType geoCodeType, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(GeoCodeAliasTypes.fields())
                .from(GeoCodeAliasTypes)
                .join(GeoCodeAliasTypeDetails).onKey(GEO_CODE_ALIAS_TYPES_ACTIVE_DETAIL_FK)
                .where(GeoCodeAliasTypeDetails.GEO_CODE_TYPE.eq(geoCodeType.getPrimaryKey()),
                        GeoCodeAliasTypeDetails.IS_DEFAULT.eq(false));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(GeoCodeAliasTypeDetails.SORT_ORDER, GeoCodeAliasTypeDetails.GEO_CODE_ALIAS_TYPE_NAME),
                    GeoCodeAliasTypeFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return geoCodeAliasTypeFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<GeoCodeAliasType> getGeoCodeAliasTypesExceptDefault(GeoCodeType geoCodeType) {
        return getGeoCodeAliasTypesExceptDefault(geoCodeType, EntityPermission.READ_ONLY);
    }
    
    public List<GeoCodeAliasType> getGeoCodeAliasTypesExceptDefaultForUpdate(GeoCodeType geoCodeType) {
        return getGeoCodeAliasTypesExceptDefault(geoCodeType, EntityPermission.READ_WRITE);
    }
    
    public GeoCodeAliasTypeTransfer getGeoCodeAliasTypeTransfer(UserVisit userVisit, GeoCodeAliasType geoCodeAliasType) {
        return geoCodeAliasTypeTransferCache.getGeoCodeAliasTypeTransfer(userVisit, geoCodeAliasType);
    }

    public List<GeoCodeAliasTypeTransfer> getGeoCodeAliasTypeTransfers(UserVisit userVisit, Collection<GeoCodeAliasType> geoCodeAliasTypes) {
        List<GeoCodeAliasTypeTransfer> geoCodeAliasTypeTransfers = new ArrayList<>(geoCodeAliasTypes.size());

        geoCodeAliasTypes.forEach((geoCodeAliasType) ->
                geoCodeAliasTypeTransfers.add(geoCodeAliasTypeTransferCache.getGeoCodeAliasTypeTransfer(userVisit, geoCodeAliasType))
        );

        return geoCodeAliasTypeTransfers;
    }

    public List<GeoCodeAliasTypeTransfer> getGeoCodeAliasTypeTransfers(UserVisit userVisit, GeoCodeType geoCodeType) {
        return getGeoCodeAliasTypeTransfers(userVisit, getGeoCodeAliasTypes(geoCodeType));
    }

    public GeoCodeAliasTypeChoicesBean getGeoCodeAliasTypeChoices(String defaultGeoCodeAliasTypeChoice, Language language,
            boolean allowNullChoice, GeoCodeType geoCodeType) {
        var geoCodeAliasTypes = getGeoCodeAliasTypes(geoCodeType);
        var size = geoCodeAliasTypes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultGeoCodeAliasTypeChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var geoCodeAliasType : geoCodeAliasTypes) {
            var geoCodeAliasTypeDetail = geoCodeAliasType.getLastDetail();
            
            var label = getBestGeoCodeAliasTypeDescription(geoCodeAliasType, language);
            var value = geoCodeAliasTypeDetail.getGeoCodeAliasTypeName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultGeoCodeAliasTypeChoice != null && defaultGeoCodeAliasTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && geoCodeAliasTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new GeoCodeAliasTypeChoicesBean(labels, values, defaultValue);
    }
    
    private void updateGeoCodeAliasTypeFromValue(GeoCodeAliasTypeDetailValue geoCodeAliasTypeDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(geoCodeAliasTypeDetailValue.hasBeenModified()) {
            var geoCodeAliasType = geoCodeAliasTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     geoCodeAliasTypeDetailValue.getGeoCodeAliasTypePK());
            var geoCodeAliasTypeDetail = geoCodeAliasType.getActiveDetailForUpdate();
            
            geoCodeAliasTypeDetail.setThruTime(session.getStartTime());
            geoCodeAliasTypeDetail.store();

            var geoCodeAliasTypePK = geoCodeAliasTypeDetail.getGeoCodeAliasTypePK();
            var geoCodeType = geoCodeAliasTypeDetail.getGeoCodeType();
            var geoCodeTypePK = geoCodeType.getPrimaryKey();
            var geoCodeAliasTypeName = geoCodeAliasTypeDetailValue.getGeoCodeAliasTypeName();
            var validationPattern = geoCodeAliasTypeDetailValue.getValidationPattern();
            var isRequired = geoCodeAliasTypeDetailValue.getIsRequired();
            var isDefault = geoCodeAliasTypeDetailValue.getIsDefault();
            var sortOrder = geoCodeAliasTypeDetailValue.getSortOrder();
            
            if(checkDefault) {
                var defaultGeoCodeAliasType = getDefaultGeoCodeAliasType(geoCodeType);
                var defaultFound = defaultGeoCodeAliasType != null && !defaultGeoCodeAliasType.equals(geoCodeAliasType);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultGeoCodeAliasTypeDetailValue = getDefaultGeoCodeAliasTypeDetailValueForUpdate(geoCodeType);
                    
                    defaultGeoCodeAliasTypeDetailValue.setIsDefault(false);
                    updateGeoCodeAliasTypeFromValue(defaultGeoCodeAliasTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }
            
            geoCodeAliasTypeDetail = geoCodeAliasTypeDetailFactory.create(geoCodeAliasTypePK, geoCodeTypePK, geoCodeAliasTypeName,
                    validationPattern, isRequired, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);
            
            geoCodeAliasType.setActiveDetail(geoCodeAliasTypeDetail);
            geoCodeAliasType.setLastDetail(geoCodeAliasTypeDetail);
            
            sendEvent(geoCodeAliasTypePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void updateGeoCodeAliasTypeFromValue(GeoCodeAliasTypeDetailValue geoCodeAliasTypeDetailValue, BasePK updatedBy) {
        updateGeoCodeAliasTypeFromValue(geoCodeAliasTypeDetailValue, true, updatedBy);
    }
    
    public void deleteGeoCodeAliasType(GeoCodeAliasType geoCodeAliasType, BasePK deletedBy) {
        deleteGeoCodeAliasesByGeoCodeAliasType(geoCodeAliasType, deletedBy);
        deleteGeoCodeAliasTypeDescriptionsByGeoCodeAliasType(geoCodeAliasType, deletedBy);

        var geoCodeAliasTypeDetail = geoCodeAliasType.getLastDetailForUpdate();
        geoCodeAliasTypeDetail.setThruTime(session.getStartTime());
        geoCodeAliasType.setActiveDetail(null);
        geoCodeAliasType.store();
        
        // Check for default, and pick one if necessary
        var geoCodeType = geoCodeAliasTypeDetail.getGeoCodeType();
        var defaultGeoCodeAliasType = getDefaultGeoCodeAliasType(geoCodeType);
        if(defaultGeoCodeAliasType == null) {
            var geoCodeAliasTypes = getGeoCodeAliasTypesForUpdate(geoCodeType);
            
            if(!geoCodeAliasTypes.isEmpty()) {
                var iter = geoCodeAliasTypes.iterator();
                if(iter.hasNext()) {
                    defaultGeoCodeAliasType = iter.next();
                }
                var geoCodeAliasTypeDetailValue = Objects.requireNonNull(defaultGeoCodeAliasType).getLastDetailForUpdate().getGeoCodeAliasTypeDetailValue().clone();
                
                geoCodeAliasTypeDetailValue.setIsDefault(true);
                updateGeoCodeAliasTypeFromValue(geoCodeAliasTypeDetailValue, false, deletedBy);
            }
        }
        
        sendEvent(geoCodeAliasType.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Geo Code Alias Type Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    protected GeoCodeAliasTypeDescriptionFactory geoCodeAliasTypeDescriptionFactory;

    @Inject
    GeoCodeAliasTypeDescriptionTransferCache geoCodeAliasTypeDescriptionTransferCache;

    public GeoCodeAliasTypeDescription createGeoCodeAliasTypeDescription(GeoCodeAliasType geoCodeAliasType, Language language, String description, BasePK createdBy) {
        var geoCodeAliasTypeDescription = geoCodeAliasTypeDescriptionFactory.create(geoCodeAliasType, language, description,
                session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(geoCodeAliasType.getPrimaryKey(), EventTypes.MODIFY, geoCodeAliasTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return geoCodeAliasTypeDescription;
    }
    
    private GeoCodeAliasTypeDescription getGeoCodeAliasTypeDescription(GeoCodeAliasType geoCodeAliasType, Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(GeoCodeAliasTypeDescriptions.fields())
                .from(GeoCodeAliasTypeDescriptions)
                .where(GeoCodeAliasTypeDescriptions.GEO_CODE_ALIAS_TYPE.eq(geoCodeAliasType.getPrimaryKey()),
                        GeoCodeAliasTypeDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        GeoCodeAliasTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return geoCodeAliasTypeDescriptionFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public GeoCodeAliasTypeDescription getGeoCodeAliasTypeDescription(GeoCodeAliasType geoCodeAliasType, Language language) {
        return getGeoCodeAliasTypeDescription(geoCodeAliasType, language, EntityPermission.READ_ONLY);
    }
    
    public GeoCodeAliasTypeDescription getGeoCodeAliasTypeDescriptionForUpdate(GeoCodeAliasType geoCodeAliasType, Language language) {
        return getGeoCodeAliasTypeDescription(geoCodeAliasType, language, EntityPermission.READ_WRITE);
    }
    
    public GeoCodeAliasTypeDescriptionValue getGeoCodeAliasTypeDescriptionValue(GeoCodeAliasTypeDescription geoCodeAliasTypeDescription) {
        return geoCodeAliasTypeDescription == null? null: geoCodeAliasTypeDescription.getGeoCodeAliasTypeDescriptionValue().clone();
    }
    
    public GeoCodeAliasTypeDescriptionValue getGeoCodeAliasTypeDescriptionValueForUpdate(GeoCodeAliasType geoCodeAliasType, Language language) {
        return getGeoCodeAliasTypeDescriptionValue(getGeoCodeAliasTypeDescriptionForUpdate(geoCodeAliasType, language));
    }
    
    private List<GeoCodeAliasTypeDescription> getGeoCodeAliasTypeDescriptionsByGeoCodeAliasType(GeoCodeAliasType geoCodeAliasType, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(GeoCodeAliasTypeDescriptions.fields())
                    .from(GeoCodeAliasTypeDescriptions)
                    .join(Languages).onKey(GEO_CODE_ALIAS_TYPE_DESCRIPTIONS_LANGUAGE_FK)
                    .where(GeoCodeAliasTypeDescriptions.GEO_CODE_ALIAS_TYPE.eq(geoCodeAliasType.getPrimaryKey()),
                            GeoCodeAliasTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    GeoCodeAliasTypeDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(GeoCodeAliasTypeDescriptions.fields())
                    .from(GeoCodeAliasTypeDescriptions)
                    .where(GeoCodeAliasTypeDescriptions.GEO_CODE_ALIAS_TYPE.eq(geoCodeAliasType.getPrimaryKey()),
                            GeoCodeAliasTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return geoCodeAliasTypeDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<GeoCodeAliasTypeDescription> getGeoCodeAliasTypeDescriptionsByGeoCodeAliasType(GeoCodeAliasType geoCodeAliasType) {
        return getGeoCodeAliasTypeDescriptionsByGeoCodeAliasType(geoCodeAliasType, EntityPermission.READ_ONLY);
    }
    
    public List<GeoCodeAliasTypeDescription> getGeoCodeAliasTypeDescriptionsByGeoCodeAliasTypeForUpdate(GeoCodeAliasType geoCodeAliasType) {
        return getGeoCodeAliasTypeDescriptionsByGeoCodeAliasType(geoCodeAliasType, EntityPermission.READ_WRITE);
    }
    
    public String getBestGeoCodeAliasTypeDescription(GeoCodeAliasType geoCodeAliasType, Language language) {
        String description;
        var geoCodeAliasTypeDescription = getGeoCodeAliasTypeDescription(geoCodeAliasType, language);
        
        if(geoCodeAliasTypeDescription == null && !language.getIsDefault()) {
            geoCodeAliasTypeDescription = getGeoCodeAliasTypeDescription(geoCodeAliasType, partyControl.getDefaultLanguage());
        }
        
        if(geoCodeAliasTypeDescription == null) {
            description = geoCodeAliasType.getLastDetail().getGeoCodeAliasTypeName();
        } else {
            description = geoCodeAliasTypeDescription.getDescription();
        }
        
        return description;
    }
    
    public GeoCodeAliasTypeDescriptionTransfer getGeoCodeAliasTypeDescriptionTransfer(UserVisit userVisit, GeoCodeAliasTypeDescription geoCodeAliasTypeDescription) {
        return geoCodeAliasTypeDescriptionTransferCache.getGeoCodeAliasTypeDescriptionTransfer(userVisit, geoCodeAliasTypeDescription);
    }
    
    public List<GeoCodeAliasTypeDescriptionTransfer> getGeoCodeAliasTypeDescriptionTransfers(UserVisit userVisit, GeoCodeAliasType geoCodeAliasType) {
        var geoCodeAliasTypeDescriptions = getGeoCodeAliasTypeDescriptionsByGeoCodeAliasType(geoCodeAliasType);
        List<GeoCodeAliasTypeDescriptionTransfer> geoCodeAliasTypeDescriptionTransfers = new ArrayList<>(geoCodeAliasTypeDescriptions.size());

        geoCodeAliasTypeDescriptions.forEach((geoCodeAliasTypeDescription) -> {
            geoCodeAliasTypeDescriptionTransfers.add(geoCodeAliasTypeDescriptionTransferCache.getGeoCodeAliasTypeDescriptionTransfer(userVisit, geoCodeAliasTypeDescription));
        });
        
        return geoCodeAliasTypeDescriptionTransfers;
    }
    
    public void updateGeoCodeAliasTypeDescriptionFromValue(GeoCodeAliasTypeDescriptionValue geoCodeAliasTypeDescriptionValue, BasePK updatedBy) {
        if(geoCodeAliasTypeDescriptionValue.hasBeenModified()) {
            var geoCodeAliasTypeDescription = geoCodeAliasTypeDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE, geoCodeAliasTypeDescriptionValue.getPrimaryKey());
            
            geoCodeAliasTypeDescription.setThruTime(session.getStartTime());
            geoCodeAliasTypeDescription.store();

            var geoCodeAliasType = geoCodeAliasTypeDescription.getGeoCodeAliasType();
            var language = geoCodeAliasTypeDescription.getLanguage();
            var description = geoCodeAliasTypeDescriptionValue.getDescription();
            
            geoCodeAliasTypeDescription = geoCodeAliasTypeDescriptionFactory.create(geoCodeAliasType, language, description, session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(geoCodeAliasType.getPrimaryKey(), EventTypes.MODIFY, geoCodeAliasTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteGeoCodeAliasTypeDescription(GeoCodeAliasTypeDescription geoCodeAliasTypeDescription, BasePK deletedBy) {
        geoCodeAliasTypeDescription.setThruTime(session.getStartTime());
        
        sendEvent(geoCodeAliasTypeDescription.getGeoCodeAliasTypePK(), EventTypes.MODIFY, geoCodeAliasTypeDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
        
    }
    
    public void deleteGeoCodeAliasTypeDescriptionsByGeoCodeAliasType(GeoCodeAliasType geoCodeAliasType, BasePK deletedBy) {
        var geoCodeAliasTypeDescriptions = getGeoCodeAliasTypeDescriptionsByGeoCodeAliasTypeForUpdate(geoCodeAliasType);
        
        geoCodeAliasTypeDescriptions.forEach((geoCodeAliasTypeDescription) -> 
                deleteGeoCodeAliasTypeDescription(geoCodeAliasTypeDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Geo Codes
    // --------------------------------------------------------------------------------

    @Inject
    protected GeoCodeDetailFactory geoCodeDetailFactory;

    @Inject
    protected GeoCodeFactory geoCodeFactory;

    @Inject
    GeoCodeTransferCache geoCodeTransferCache;

    public GeoCode createGeoCode(String geoCodeName, GeoCodeType geoCodeType, GeoCodeScope geoCodeScope, Boolean isDefault,
            Integer sortOrder, BasePK createdBy) {
        var defaultGeoCode = getDefaultGeoCode(geoCodeScope);
        var defaultFound = defaultGeoCode != null;
        
        if(defaultFound && isDefault) {
            var defaultGeoCodeDetailValue = getDefaultGeoCodeDetailValueForUpdate(geoCodeScope);
            
            defaultGeoCodeDetailValue.setIsDefault(false);
            updateGeoCodeFromValue(defaultGeoCodeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var geoCode = geoCodeFactory.create();
        var geoCodeDetail = geoCodeDetailFactory.create(geoCode, geoCodeName, geoCodeType,
                geoCodeScope, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);
        
        // Convert to R/W
        geoCode = geoCodeFactory.getEntityFromPK(EntityPermission.READ_WRITE, geoCode.getPrimaryKey());
        geoCode.setActiveDetail(geoCodeDetail);
        geoCode.setLastDetail(geoCodeDetail);
        geoCode.store();
        
        sendEvent(geoCode.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return geoCode;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.GeoCode */
    public GeoCode getGeoCodeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new GeoCodePK(entityInstance.getEntityUniqueId());

        return geoCodeFactory.getEntityFromPK(entityPermission, pk);
    }

    public GeoCode getGeoCodeByEntityInstance(EntityInstance entityInstance) {
        return getGeoCodeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public long countGeoCodes() {
        return session.getDslContext()
                .selectCount()
                .from(GeoCodes)
                .join(GeoCodeDetails).onKey(GEO_CODES_ACTIVE_DETAIL_FK)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countGeoCodesByGeoCodeType(GeoCodeType geoCodeType) {
        return session.getDslContext()
                .selectCount()
                .from(GeoCodes)
                .join(GeoCodeDetails).onKey(GEO_CODES_ACTIVE_DETAIL_FK)
                .where(GeoCodeDetails.GEO_CODE_TYPE.eq(geoCodeType.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countGeoCodesByGeoCodeScope(GeoCodeScope geoCodeScope) {
        return session.getDslContext()
                .selectCount()
                .from(GeoCodes)
                .join(GeoCodeDetails).onKey(GEO_CODES_ACTIVE_DETAIL_FK)
                .where(GeoCodeDetails.GEO_CODE_SCOPE.eq(geoCodeScope.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public GeoCodeDetailValue getGeoCodeDetailValueForUpdate(GeoCode geoCode) {
        return geoCode == null? null: geoCode.getLastDetailForUpdate().getGeoCodeDetailValue().clone();
    }
    
    private GeoCode getDefaultGeoCode(GeoCodeScope geoCodeScope, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(GeoCodes.fields())
                .from(GeoCodes)
                .join(GeoCodeDetails).onKey(GEO_CODES_ACTIVE_DETAIL_FK)
                .where(GeoCodeDetails.GEO_CODE_SCOPE.eq(geoCodeScope.getPrimaryKey()),
                        GeoCodeDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return geoCodeFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public GeoCode getDefaultGeoCode(GeoCodeScope geoCodeScope) {
        return getDefaultGeoCode(geoCodeScope, EntityPermission.READ_ONLY);
    }
    
    public GeoCode getDefaultGeoCodeForUpdate(GeoCodeScope geoCodeScope) {
        return getDefaultGeoCode(geoCodeScope, EntityPermission.READ_WRITE);
    }
    
    public GeoCodeDetailValue getDefaultGeoCodeDetailValueForUpdate(GeoCodeScope geoCodeScope) {
        return getGeoCodeDetailValueForUpdate(getDefaultGeoCodeForUpdate(geoCodeScope));
    }
    
    public GeoCode getGeoCodeByName(String geoCodeName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(GeoCodes.fields())
                .from(GeoCodes)
                .join(GeoCodeDetails).onKey(GEO_CODES_ACTIVE_DETAIL_FK)
                .where(GeoCodeDetails.GEO_CODE_NAME.eq(geoCodeName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return geoCodeFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public GeoCode getGeoCodeByName(String geoCodeName) {
        return getGeoCodeByName(geoCodeName, EntityPermission.READ_ONLY);
    }
    
    public GeoCode getGeoCodeByNameForUpdate(String geoCodeName) {
        return getGeoCodeByName(geoCodeName, EntityPermission.READ_WRITE);
    }
    
    public GeoCodeDetailValue getGeoCodeDetailValueByNameForUpdate(String geoCodeName) {
        return getGeoCodeDetailValueForUpdate(getGeoCodeByNameForUpdate(geoCodeName));
    }
    
    private List<GeoCode> getGeoCodesByGeoCodeScope(GeoCodeScope geoCodeScope, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(GeoCodes.fields())
                .from(GeoCodes)
                .join(GeoCodeDetails).onKey(GEO_CODES_ACTIVE_DETAIL_FK)
                .where(GeoCodeDetails.GEO_CODE_SCOPE.eq(geoCodeScope.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(GeoCodeDetails.SORT_ORDER, GeoCodeDetails.GEO_CODE_NAME),
                    GeoCodeFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return geoCodeFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<GeoCode> getGeoCodesByGeoCodeScope(GeoCodeScope geoCodeScope) {
        return getGeoCodesByGeoCodeScope(geoCodeScope, EntityPermission.READ_ONLY);
    }
    
    public List<GeoCode> getGeoCodesByGeoCodeScopeForUpdate(GeoCodeScope geoCodeScope) {
        return getGeoCodesByGeoCodeScope(geoCodeScope, EntityPermission.READ_WRITE);
    }
    
    public GeoCodeTransfer getGeoCodeTransfer(UserVisit userVisit, GeoCode geoCode) {
        return geoCodeTransferCache.getGeoCodeTransfer(userVisit, geoCode);
    }
    
    public List<GeoCodeTransfer> getGeoCodeTransfers(UserVisit userVisit, Collection<GeoCode> geoCodes) {
        List<GeoCodeTransfer> geoCodeTransfers = new ArrayList<>(geoCodes.size());
        
        geoCodes.forEach((geoCode) ->
                geoCodeTransfers.add(geoCodeTransferCache.getGeoCodeTransfer(userVisit, geoCode))
        );
        
        return geoCodeTransfers;
    }
    
    public List<GeoCodeTransfer> getGeoCodeTransfersByGeoCodeScope(UserVisit userVisit, GeoCodeScope geoCodeScope) {
        return getGeoCodeTransfers(userVisit, getGeoCodesByGeoCodeScope(geoCodeScope));
    }
    
    private void updateGeoCodeFromValue(GeoCodeDetailValue geoCodeDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(geoCodeDetailValue.hasBeenModified()) {
            var geoCode = geoCodeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     geoCodeDetailValue.getGeoCodePK());
            var geoCodeDetail = geoCode.getActiveDetailForUpdate();
            
            geoCodeDetail.setThruTime(session.getStartTime());
            geoCodeDetail.store();

            var geoCodePK = geoCodeDetail.getGeoCodePK(); // Not updated
            var geoCodeName = geoCodeDetail.getGeoCodeName(); // Not updated
            var geoCodeTypePK = geoCodeDetail.getGeoCodeTypePK(); // Not updated
            var geoCodeScope = geoCodeDetail.getGeoCodeScope(); // Not updated
            var isDefault = geoCodeDetailValue.getIsDefault();
            var sortOrder = geoCodeDetailValue.getSortOrder();
            
            if(checkDefault) {
                var defaultGeoCode = getDefaultGeoCode(geoCodeScope);
                var defaultFound = defaultGeoCode != null && !defaultGeoCode.equals(geoCode);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultGeoCodeDetailValue = getDefaultGeoCodeDetailValueForUpdate(geoCodeScope);
                    
                    defaultGeoCodeDetailValue.setIsDefault(false);
                    updateGeoCodeFromValue(defaultGeoCodeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }
            
            geoCodeDetail = geoCodeDetailFactory.create(geoCodePK, geoCodeName, geoCodeTypePK, geoCodeScope.getPrimaryKey(), isDefault,
                    sortOrder, session.getStartTime(), Session.MAX_TIME);
            
            geoCode.setActiveDetail(geoCodeDetail);
            geoCode.setLastDetail(geoCodeDetail);
            
            sendEvent(geoCodePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void updateGeoCodeFromValue(GeoCodeDetailValue geoCodeDetailValue, BasePK updatedBy) {
        updateGeoCodeFromValue(geoCodeDetailValue, true, updatedBy);
    }

    public void deleteGeoCode(GeoCode geoCode, BasePK deletedBy) {
        var geoCodeDetail = geoCode.getLastDetailForUpdate();

        deleteGeoCodeDescriptionsByGeoCode(geoCode, deletedBy);
        deleteGeoCodeAliasesByGeoCode(geoCode, deletedBy);
        deleteGeoCodeRelationshipsByGeoCode(geoCode, deletedBy);
        deleteGeoCodeLanguagesByGeoCode(geoCode, deletedBy);
        deleteGeoCodeCurrenciesByGeoCode(geoCode, deletedBy);
        deleteGeoCodeTimeZonesByGeoCode(geoCode, deletedBy);
        deleteGeoCodeDateTimeFormatsByGeoCode(geoCode, deletedBy);

        if(geoCodeDetail.getGeoCodeType().getLastDetail().getGeoCodeTypeName().equals(com.echothree.model.control.geo.common.GeoCodeTypes.COUNTRY.name())) {
            
            itemControl.deleteHarmonizedTariffScheduleCodesByCountryGeoCode(geoCode, deletedBy);
            itemControl.deleteItemHarmonizedTariffScheduleCodesByCountryGeoCode(geoCode, deletedBy);
            
            deleteGeoCodeCountryByGeoCode(geoCode, deletedBy);
        }

        geoCodeDetail.setThruTime(session.getStartTime());
        geoCode.setActiveDetail(null);
        geoCode.store();

        // Check for default, and pick one if necessary
        var geoCodeScope = geoCodeDetail.getGeoCodeScope();
        var defaultGeoCode = getDefaultGeoCode(geoCodeScope);
        if(defaultGeoCode == null) {
            var geoCodes = getGeoCodesByGeoCodeScopeForUpdate(geoCodeScope);

            if(!geoCodes.isEmpty()) {
                var iter = geoCodes.iterator();
                if(iter.hasNext()) {
                    defaultGeoCode = iter.next();
                }
                var geoCodeDetailValue = Objects.requireNonNull(defaultGeoCode).getLastDetailForUpdate().getGeoCodeDetailValue().clone();

                geoCodeDetailValue.setIsDefault(true);
                updateGeoCodeFromValue(geoCodeDetailValue, false, deletedBy);
            }
        }

        sendEvent(geoCode.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Geo Code Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    protected GeoCodeDescriptionFactory geoCodeDescriptionFactory;

    @Inject
    GeoCodeDescriptionTransferCache geoCodeDescriptionTransferCache;

    public GeoCodeDescription createGeoCodeDescription(GeoCode geoCode, Language language, String description, BasePK createdBy) {
        var geoCodeDescription = geoCodeDescriptionFactory.create(geoCode, language, description, session.getStartTime(),
                Session.MAX_TIME);
        
        sendEvent(geoCode.getPrimaryKey(), EventTypes.MODIFY, geoCodeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return geoCodeDescription;
    }
    
    private GeoCodeDescription getGeoCodeDescription(GeoCode geoCode, Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(GeoCodeDescriptions.fields())
                .from(GeoCodeDescriptions)
                .where(GeoCodeDescriptions.GEO_CODE.eq(geoCode.getPrimaryKey()),
                        GeoCodeDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        GeoCodeDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return geoCodeDescriptionFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public GeoCodeDescription getGeoCodeDescription(GeoCode geoCode, Language language) {
        return getGeoCodeDescription(geoCode, language, EntityPermission.READ_ONLY);
    }
    
    public GeoCodeDescription getGeoCodeDescriptionForUpdate(GeoCode geoCode, Language language) {
        return getGeoCodeDescription(geoCode, language, EntityPermission.READ_WRITE);
    }
    
    public GeoCodeDescriptionValue getGeoCodeDescriptionValue(GeoCodeDescription geoCodeDescription) {
        return geoCodeDescription == null? null: geoCodeDescription.getGeoCodeDescriptionValue().clone();
    }
    
    public GeoCodeDescriptionValue getGeoCodeDescriptionValueForUpdate(GeoCode geoCode, Language language) {
        return getGeoCodeDescriptionValue(getGeoCodeDescriptionForUpdate(geoCode, language));
    }
    
    
    private List<GeoCodeDescription> getGeoCodeDescriptionsByGeoCode(GeoCode geoCode, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(GeoCodeDescriptions.fields())
                    .from(GeoCodeDescriptions)
                    .join(Languages).onKey(GEO_CODE_DESCRIPTIONS_LANGUAGE_FK)
                    .where(GeoCodeDescriptions.GEO_CODE.eq(geoCode.getPrimaryKey()),
                            GeoCodeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    GeoCodeDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(GeoCodeDescriptions.fields())
                    .from(GeoCodeDescriptions)
                    .where(GeoCodeDescriptions.GEO_CODE.eq(geoCode.getPrimaryKey()),
                            GeoCodeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return geoCodeDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<GeoCodeDescription> getGeoCodeDescriptionsByGeoCode(GeoCode geoCode) {
        return getGeoCodeDescriptionsByGeoCode(geoCode, EntityPermission.READ_ONLY);
    }
    
    public List<GeoCodeDescription> getGeoCodeDescriptionsByGeoCodeForUpdate(GeoCode geoCode) {
        return getGeoCodeDescriptionsByGeoCode(geoCode, EntityPermission.READ_WRITE);
    }
    
    public String getBestGeoCodeDescription(GeoCode geoCode, Language language) {
        String description;
        var geoCodeDescription = getGeoCodeDescription(geoCode, language);
        
        if(geoCodeDescription == null && !language.getIsDefault()) {
            geoCodeDescription = getGeoCodeDescription(geoCode, partyControl.getDefaultLanguage());
        }
        
        if(geoCodeDescription == null) {
            description = geoCode.getLastDetail().getGeoCodeName();
        } else {
            description = geoCodeDescription.getDescription();
        }
        
        return description;
    }
    
    public GeoCodeDescriptionTransfer getGeoCodeDescriptionTransfer(UserVisit userVisit, GeoCodeDescription geoCodeDescription) {
        return geoCodeDescriptionTransferCache.getGeoCodeDescriptionTransfer(userVisit, geoCodeDescription);
    }
    
    public List<GeoCodeDescriptionTransfer> getGeoCodeDescriptionTransfers(UserVisit userVisit, GeoCode geoCode) {
        var geoCodeDescriptions = getGeoCodeDescriptionsByGeoCode(geoCode);
        List<GeoCodeDescriptionTransfer> geoCodeDescriptionTransfers = new ArrayList<>(geoCodeDescriptions.size());

        geoCodeDescriptions.forEach((geoCodeDescription) -> {
            geoCodeDescriptionTransfers.add(geoCodeDescriptionTransferCache.getGeoCodeDescriptionTransfer(userVisit, geoCodeDescription));
        });

        return geoCodeDescriptionTransfers;
    }
    
    public void updateGeoCodeDescriptionFromValue(GeoCodeDescriptionValue geoCodeDescriptionValue, BasePK updatedBy) {
        if(geoCodeDescriptionValue.hasBeenModified()) {
            var geoCodeDescription = geoCodeDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE, geoCodeDescriptionValue.getPrimaryKey());
            
            geoCodeDescription.setThruTime(session.getStartTime());
            geoCodeDescription.store();

            var geoCode = geoCodeDescription.getGeoCode();
            var language = geoCodeDescription.getLanguage();
            var description = geoCodeDescriptionValue.getDescription();
            
            geoCodeDescription = geoCodeDescriptionFactory.create(geoCode, language, description, session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(geoCode.getPrimaryKey(), EventTypes.MODIFY, geoCodeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteGeoCodeDescription(GeoCodeDescription geoCodeDescription, BasePK deletedBy) {
        geoCodeDescription.setThruTime(session.getStartTime());
        
        sendEvent(geoCodeDescription.getGeoCodePK(), EventTypes.MODIFY, geoCodeDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
        
    }
    
    public void deleteGeoCodeDescriptionsByGeoCode(GeoCode geoCode, BasePK deletedBy) {
        var geoCodeDescriptions = getGeoCodeDescriptionsByGeoCodeForUpdate(geoCode);
        
        geoCodeDescriptions.forEach((geoCodeDescription) -> 
                deleteGeoCodeDescription(geoCodeDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Geo Code Aliases
    // --------------------------------------------------------------------------------

    @Inject
    protected GeoCodeAliasFactory geoCodeAliasFactory;

    @Inject
    GeoCodeAliasTransferCache geoCodeAliasTransferCache;

    public GeoCodeAlias createGeoCodeAlias(GeoCode geoCode, GeoCodeAliasType geoCodeAliasType, String alias, BasePK createdBy) {
        var geoCodeAlias = geoCodeAliasFactory.create(geoCode, geoCode.getLastDetail().getGeoCodeScope(), geoCodeAliasType, alias,
                session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(geoCode.getPrimaryKey(), EventTypes.MODIFY, geoCodeAlias.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return geoCodeAlias;
    }

    public long countGeoCodeAliasesByGeoCode(GeoCode geoCode) {
        return session.getDslContext()
                .selectCount()
                .from(GeoCodeAliases)
                .where(GeoCodeAliases.GEO_CODE.eq(geoCode.getPrimaryKey()),
                        GeoCodeAliases.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countGeoCodeAliasesByGeoCodeAliasType(GeoCodeAliasType geoCodeAliasType) {
        return session.getDslContext()
                .selectCount()
                .from(GeoCodeAliases)
                .where(GeoCodeAliases.GEO_CODE_ALIAS_TYPE.eq(geoCodeAliasType.getPrimaryKey()),
                        GeoCodeAliases.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private GeoCodeAlias getGeoCodeAlias(GeoCode geoCode, GeoCodeAliasType geoCodeAliasType, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(GeoCodeAliases.fields())
                .from(GeoCodeAliases)
                .where(GeoCodeAliases.GEO_CODE.eq(geoCode.getPrimaryKey()),
                        GeoCodeAliases.GEO_CODE_ALIAS_TYPE.eq(geoCodeAliasType.getPrimaryKey()),
                        GeoCodeAliases.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return geoCodeAliasFactory.getEntityFromQuery(entityPermission, query);
    }

    public GeoCodeAlias getGeoCodeAlias(GeoCode geoCode, GeoCodeAliasType geoCodeAliasType) {
        return getGeoCodeAlias(geoCode, geoCodeAliasType, EntityPermission.READ_ONLY);
    }

    public GeoCodeAlias getGeoCodeAliasForUpdate(GeoCode geoCode, GeoCodeAliasType geoCodeAliasType) {
        return getGeoCodeAlias(geoCode, geoCodeAliasType, EntityPermission.READ_WRITE);
    }

    public GeoCodeAliasValue getGeoCodeAliasValue(GeoCodeAlias geoCodeAlias) {
        return geoCodeAlias == null? null: geoCodeAlias.getGeoCodeAliasValue().clone();
    }

    public GeoCodeAliasValue getGeoCodeAliasValueForUpdate(GeoCode geoCode, GeoCodeAliasType geoCodeAliasType) {
        return getGeoCodeAliasValue(getGeoCodeAliasForUpdate(geoCode, geoCodeAliasType));
    }

    private GeoCodeAlias getGeoCodeAliasByAliasWithinScope(GeoCodeScope geoCodeScope, GeoCodeAliasType geoCodeAliasType, String alias, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(GeoCodeAliases.fields())
                .from(GeoCodeAliases)
                .where(GeoCodeAliases.GEO_CODE_SCOPE.eq(geoCodeScope.getPrimaryKey()),
                        GeoCodeAliases.GEO_CODE_ALIAS_TYPE.eq(geoCodeAliasType.getPrimaryKey()),
                        GeoCodeAliases.ALIAS.eq(alias),
                        GeoCodeAliases.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return geoCodeAliasFactory.getEntityFromQuery(entityPermission, query);
    }

    public GeoCodeAlias getGeoCodeAliasByAliasWithinScope(GeoCodeScope geoCodeScope, GeoCodeAliasType geoCodeAliasType, String alias) {
        return getGeoCodeAliasByAliasWithinScope(geoCodeScope, geoCodeAliasType, alias, EntityPermission.READ_ONLY);
    }

    public GeoCodeAlias getGeoCodeAliasByAliasWithinScopeForUpdate(GeoCodeScope geoCodeScope, GeoCodeAliasType geoCodeAliasType, String alias) {
        return getGeoCodeAliasByAliasWithinScope(geoCodeScope, geoCodeAliasType, alias, EntityPermission.READ_WRITE);
    }

    private List<GeoCodeAlias> getGeoCodeAliasesByGeoCode(GeoCode geoCode, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(GeoCodeAliases.fields())
                .from(GeoCodeAliases)
                .join(GeoCodeAliasTypes).onKey(GEO_CODE_ALIASES_GEO_CODE_ALIAS_TYPE_FK)
                .join(GeoCodeAliasTypeDetails).onKey(GEO_CODE_ALIAS_TYPES_LAST_DETAIL_FK)
                .where(GeoCodeAliases.GEO_CODE.eq(geoCode.getPrimaryKey()),
                        GeoCodeAliases.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(GeoCodeAliasTypeDetails.SORT_ORDER, GeoCodeAliasTypeDetails.GEO_CODE_ALIAS_TYPE_NAME),
                    GeoCodeAliasFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return geoCodeAliasFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<GeoCodeAlias> getGeoCodeAliasesByGeoCode(GeoCode geoCode) {
        return getGeoCodeAliasesByGeoCode(geoCode, EntityPermission.READ_ONLY);
    }

    public List<GeoCodeAlias> getGeoCodeAliasesByGeoCodeForUpdate(GeoCode geoCode) {
        return getGeoCodeAliasesByGeoCode(geoCode, EntityPermission.READ_WRITE);
    }

    private List<GeoCodeAlias> getGeoCodeAliasesByGeoCodeScope(GeoCodeScope geoCodeScope, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(GeoCodeAliases.fields())
                .from(GeoCodeAliases)
                .where(GeoCodeAliases.GEO_CODE_SCOPE.eq(geoCodeScope.getPrimaryKey()),
                        GeoCodeAliases.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(GeoCodeAliases.fields())
                    .from(GeoCodeAliases)
                    .join(GeoCodeAliasTypes).onKey(GEO_CODE_ALIASES_GEO_CODE_ALIAS_TYPE_FK)
                    .join(GeoCodeAliasTypeDetails).onKey(GEO_CODE_ALIAS_TYPES_LAST_DETAIL_FK)
                    .where(GeoCodeAliases.GEO_CODE_SCOPE.eq(geoCodeScope.getPrimaryKey()),
                            GeoCodeAliases.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(GeoCodeAliasTypeDetails.SORT_ORDER, GeoCodeAliasTypeDetails.GEO_CODE_ALIAS_TYPE_NAME, GeoCodeAliases.ALIAS),
                    GeoCodeAliasFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return geoCodeAliasFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<GeoCodeAlias> getGeoCodeAliasesByGeoCodeScope(GeoCodeScope geoCodeScope) {
        return getGeoCodeAliasesByGeoCodeScope(geoCodeScope, EntityPermission.READ_ONLY);
    }

    public List<GeoCodeAlias> getGeoCodeAliasesByGeoCodeScopeForUpdate(GeoCodeScope geoCodeScope) {
        return getGeoCodeAliasesByGeoCodeScope(geoCodeScope, EntityPermission.READ_WRITE);
    }

    private List<GeoCodeAlias> getGeoCodeAliasesByGeoCodeAliasType(GeoCodeAliasType geoCodeAliasType, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(GeoCodeAliases.fields())
                .from(GeoCodeAliases)
                .where(GeoCodeAliases.GEO_CODE_ALIAS_TYPE.eq(geoCodeAliasType.getPrimaryKey()),
                        GeoCodeAliases.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(GeoCodeAliases.fields())
                    .from(GeoCodeAliases)
                    .join(GeoCodeScopes).onKey(GEO_CODE_ALIASES_GEO_CODE_SCOPE_FK)
                    .join(GeoCodeScopeDetails).onKey(GEO_CODE_SCOPES_LAST_DETAIL_FK)
                    .where(GeoCodeAliases.GEO_CODE_ALIAS_TYPE.eq(geoCodeAliasType.getPrimaryKey()),
                            GeoCodeAliases.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(GeoCodeScopeDetails.SORT_ORDER, GeoCodeScopeDetails.GEO_CODE_SCOPE_NAME, GeoCodeAliases.ALIAS),
                    GeoCodeAliasFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return geoCodeAliasFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<GeoCodeAlias> getGeoCodeAliasesByGeoCodeAliasType(GeoCodeAliasType geoCodeAliasType) {
        return getGeoCodeAliasesByGeoCodeAliasType(geoCodeAliasType, EntityPermission.READ_ONLY);
    }

    public List<GeoCodeAlias> getGeoCodeAliasesByGeoCodeAliasTypeForUpdate(GeoCodeAliasType geoCodeAliasType) {
        return getGeoCodeAliasesByGeoCodeAliasType(geoCodeAliasType, EntityPermission.READ_WRITE);
    }

    public GeoCodeAliasTransfer getGeoCodeAliasTransfer(UserVisit userVisit, GeoCodeAlias geoCodeAlias) {
        return geoCodeAliasTransferCache.getGeoCodeAliasTransfer(userVisit, geoCodeAlias);
    }
    
    public List<GeoCodeAliasTransfer> getGeoCodeAliasTransfers(UserVisit userVisit, Collection<GeoCodeAlias> geoCodeAliases) {
        List<GeoCodeAliasTransfer> geoCodeAliasTransfers = new ArrayList<>(geoCodeAliases.size());
        
        geoCodeAliases.forEach((geoCodeAlias) ->
                geoCodeAliasTransfers.add(geoCodeAliasTransferCache.getGeoCodeAliasTransfer(userVisit, geoCodeAlias))
        );
        
        return geoCodeAliasTransfers;
    }
    
    public List<GeoCodeAliasTransfer> getGeoCodeAliasTransfersByGeoCode(UserVisit userVisit, GeoCode geoCode) {
        return getGeoCodeAliasTransfers(userVisit, getGeoCodeAliasesByGeoCode(geoCode));
    }

    public void updateGeoCodeAliasFromValue(GeoCodeAliasValue geoCodeAliasValue, BasePK updatedBy) {
        if(geoCodeAliasValue.hasBeenModified()) {
            var geoCodeAlias = geoCodeAliasFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    geoCodeAliasValue.getPrimaryKey());

            geoCodeAlias.setThruTime(session.getStartTime());
            geoCodeAlias.store();

            var geoCodePK = geoCodeAlias.getGeoCodePK(); // Not updated
            var geoCodeScopePK = geoCodeAlias.getGeoCodeScopePK(); // Not updated
            var geoCodeAliasTypePK = geoCodeAlias.getGeoCodeAliasTypePK(); // Not updated
            var alias = geoCodeAliasValue.getAlias();

            geoCodeAlias = geoCodeAliasFactory.create(geoCodePK, geoCodeScopePK, geoCodeAliasTypePK, alias, session.getStartTime(),
                    Session.MAX_TIME);

            sendEvent(geoCodePK, EventTypes.MODIFY, geoCodeAlias.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteGeoCodeAlias(GeoCodeAlias geoCodeAlias, BasePK deletedBy) {
        geoCodeAlias.setThruTime(session.getStartTime());

        sendEvent(geoCodeAlias.getGeoCodePK(), EventTypes.MODIFY, geoCodeAlias.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }

    public void deleteGeoCodeAliases(List<GeoCodeAlias> geoCodeAliases, BasePK deletedBy) {
        geoCodeAliases.forEach((geoCodeAlias) -> 
                deleteGeoCodeAlias(geoCodeAlias, deletedBy)
        );
    }

    public void deleteGeoCodeAliasesByGeoCode(GeoCode geoCode, BasePK deletedBy) {
        deleteGeoCodeAliases(getGeoCodeAliasesByGeoCodeForUpdate(geoCode), deletedBy);
    }

    public void deleteGeoCodeAliasesByGeoCodeScope(GeoCodeScope geoCodeScope, BasePK deletedBy) {
        deleteGeoCodeAliases(getGeoCodeAliasesByGeoCodeScopeForUpdate(geoCodeScope), deletedBy);
    }

    public void deleteGeoCodeAliasesByGeoCodeAliasType(GeoCodeAliasType geoCodeAliasType, BasePK deletedBy) {
        deleteGeoCodeAliases(getGeoCodeAliasesByGeoCodeAliasTypeForUpdate(geoCodeAliasType), deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Geo Code Relationships
    // --------------------------------------------------------------------------------

    @Inject
    protected GeoCodeRelationshipFactory geoCodeRelationshipFactory;

    @Inject
    GeoCodeRelationshipTransferCache geoCodeRelationshipTransferCache;

    public GeoCodeRelationship createGeoCodeRelationship(GeoCode fromGeoCode, GeoCode toGeoCode, BasePK createdBy) {
        var geoCodeRelationship = geoCodeRelationshipFactory.create(fromGeoCode, toGeoCode,
                session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(fromGeoCode.getPrimaryKey(), EventTypes.MODIFY, geoCodeRelationship.getPrimaryKey(), null, createdBy);
        
        return geoCodeRelationship;
    }
    
    public long countGeoCodeRelationshipsByFromGeoCode(GeoCode fromGeoCode) {
        return session.getDslContext()
                .selectCount()
                .from(GeoCodeRelationships)
                .where(GeoCodeRelationships.FROM_GEO_CODE.eq(fromGeoCode.getPrimaryKey()),
                        GeoCodeRelationships.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countGeoCodeRelationshipsByToGeoCode(GeoCode toGeoCode) {
        return session.getDslContext()
                .selectCount()
                .from(GeoCodeRelationships)
                .where(GeoCodeRelationships.TO_GEO_CODE.eq(toGeoCode.getPrimaryKey()),
                        GeoCodeRelationships.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public GeoCodeRelationship getGeoCodeRelationship(GeoCode fromGeoCode, GeoCode toGeoCode) {
        var query = session.getDslContext()
                .select(GeoCodeRelationships.fields())
                .from(GeoCodeRelationships)
                .where(GeoCodeRelationships.FROM_GEO_CODE.eq(fromGeoCode.getPrimaryKey()),
                        GeoCodeRelationships.TO_GEO_CODE.eq(toGeoCode.getPrimaryKey()),
                        GeoCodeRelationships.THRU_TIME.eq(Session.MAX_TIME));

        return geoCodeRelationshipFactory.getEntityFromQuery(EntityPermission.READ_ONLY, query);
    }

    private List<GeoCodeRelationship> getGeoCodeRelationshipsByFromGeoCode(GeoCode fromGeoCode, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(GeoCodeRelationships.fields())
                .from(GeoCodeRelationships)
                .where(GeoCodeRelationships.FROM_GEO_CODE.eq(fromGeoCode.getPrimaryKey()),
                        GeoCodeRelationships.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(GeoCodeRelationships.fields())
                    .from(GeoCodeRelationships)
                    .join(GeoCodes).onKey(GEO_CODE_RELATIONSHIPS_TO_GEO_CODE_FK)
                    .join(GeoCodeDetails).onKey(GEO_CODES_LAST_DETAIL_FK)
                    .where(GeoCodeRelationships.FROM_GEO_CODE.eq(fromGeoCode.getPrimaryKey()),
                            GeoCodeRelationships.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(GeoCodeDetails.SORT_ORDER, GeoCodeDetails.GEO_CODE_NAME),
                    GeoCodeRelationshipFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return geoCodeRelationshipFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<GeoCodeRelationship> getGeoCodeRelationshipsByFromGeoCode(GeoCode fromGeoCode) {
        return getGeoCodeRelationshipsByFromGeoCode(fromGeoCode, EntityPermission.READ_ONLY);
    }

    public List<GeoCodeRelationship> getGeoCodeRelationshipsByFromGeoCodeForUpdate(GeoCode fromGeoCode) {
        return getGeoCodeRelationshipsByFromGeoCode(fromGeoCode, EntityPermission.READ_WRITE);
    }

    public List<GeoCodeRelationship> getGeoCodeRelationshipsByFromGeoCodeAndGeoCodeType(GeoCode fromGeoCode, GeoCodeType geoCodeType) {
        var query = session.applyLimit(session.getDslContext()
                .select(GeoCodeRelationships.fields())
                .from(GeoCodeRelationships)
                .join(GeoCodes).onKey(GEO_CODE_RELATIONSHIPS_TO_GEO_CODE_FK)
                .join(GeoCodeDetails).onKey(GEO_CODES_LAST_DETAIL_FK)
                .where(GeoCodeRelationships.FROM_GEO_CODE.eq(fromGeoCode.getPrimaryKey()),
                        GeoCodeRelationships.THRU_TIME.eq(Session.MAX_TIME),
                        GeoCodeDetails.GEO_CODE_TYPE.eq(geoCodeType.getPrimaryKey()))
                .orderBy(GeoCodeDetails.SORT_ORDER, GeoCodeDetails.GEO_CODE_NAME),
                GeoCodeRelationshipFactory.class);

        return geoCodeRelationshipFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, query);
    }

    private List<GeoCodeRelationship> getGeoCodeRelationshipsByToGeoCode(GeoCode toGeoCode, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(GeoCodeRelationships.fields())
                .from(GeoCodeRelationships)
                .where(GeoCodeRelationships.TO_GEO_CODE.eq(toGeoCode.getPrimaryKey()),
                        GeoCodeRelationships.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(GeoCodeRelationships.fields())
                    .from(GeoCodeRelationships)
                    .join(GeoCodes).onKey(GEO_CODE_RELATIONSHIPS_FROM_GEO_CODE_FK)
                    .join(GeoCodeDetails).onKey(GEO_CODES_LAST_DETAIL_FK)
                    .where(GeoCodeRelationships.TO_GEO_CODE.eq(toGeoCode.getPrimaryKey()),
                            GeoCodeRelationships.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(GeoCodeDetails.SORT_ORDER, GeoCodeDetails.GEO_CODE_NAME),
                    GeoCodeRelationshipFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return geoCodeRelationshipFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<GeoCodeRelationship> getGeoCodeRelationshipsByToGeoCode(GeoCode toGeoCode) {
        return getGeoCodeRelationshipsByToGeoCode(toGeoCode, EntityPermission.READ_ONLY);
    }

    public List<GeoCodeRelationship> getGeoCodeRelationshipsByToGeoCodeForUpdate(GeoCode toGeoCode) {
        return getGeoCodeRelationshipsByToGeoCode(toGeoCode, EntityPermission.READ_WRITE);
    }

    public GeoCodeRelationshipTransfer getGeoCodeRelationshipTransfer(UserVisit userVisit, GeoCodeRelationship geoCodeRelationship) {
        return geoCodeRelationshipTransferCache.getGeoCodeRelationshipTransfer(userVisit, geoCodeRelationship);
    }

    public void deleteGeoCodeRelationship(GeoCodeRelationship geoCodeRelationship, BasePK deletedBy) {
        geoCodeRelationship.setThruTime(session.getStartTime());
        geoCodeRelationship.store();
    }

    public void deleteGeoCodeRelationships(List<GeoCodeRelationship> geoCodeRelationships, BasePK deletedBy) {
        geoCodeRelationships.forEach((geoCodeRelationship) -> 
                deleteGeoCodeRelationship(geoCodeRelationship, deletedBy)
        );
    }

    public void deleteGeoCodeRelationshipsByFromGeoCode(GeoCode fromGeoCode, BasePK deletedBy) {
        deleteGeoCodeRelationships(getGeoCodeRelationshipsByFromGeoCodeForUpdate(fromGeoCode), deletedBy);
    }

    public void deleteGeoCodeRelationshipsByToGeoCode(GeoCode toGeoCode, BasePK deletedBy) {
        deleteGeoCodeRelationships(getGeoCodeRelationshipsByToGeoCodeForUpdate(toGeoCode), deletedBy);
    }

    public void deleteGeoCodeRelationshipsByGeoCode(GeoCode geoCode, BasePK deletedBy) {
        deleteGeoCodeRelationshipsByFromGeoCode(geoCode, deletedBy);
        deleteGeoCodeRelationshipsByToGeoCode(geoCode, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Geo Code Languages
    // --------------------------------------------------------------------------------

    @Inject
    protected GeoCodeLanguageFactory geoCodeLanguageFactory;

    @Inject
    GeoCodeLanguageTransferCache geoCodeLanguageTransferCache;

    public GeoCodeLanguage createGeoCodeLanguage(GeoCode geoCode, Language language, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultGeoCodeLanguage = getDefaultGeoCodeLanguage(geoCode);
        var defaultFound = defaultGeoCodeLanguage != null;
        
        if(defaultFound && isDefault) {
            var defaultGeoCodeLanguageValue = getDefaultGeoCodeLanguageValueForUpdate(geoCode);
            
            defaultGeoCodeLanguageValue.setIsDefault(false);
            updateGeoCodeLanguageFromValue(defaultGeoCodeLanguageValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var geoCodeLanguage = geoCodeLanguageFactory.create(geoCode, language, isDefault,
                sortOrder, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(geoCode.getPrimaryKey(), EventTypes.MODIFY, geoCodeLanguage.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return geoCodeLanguage;
    }

    public long countGeoCodeLanguagesByGeoCode(GeoCode geoCode) {
        return session.getDslContext()
                .selectCount()
                .from(GeoCodeLanguages)
                .where(GeoCodeLanguages.GEO_CODE.eq(geoCode.getPrimaryKey()),
                        GeoCodeLanguages.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countGeoCodeLanguagesByLanguage(Language language) {
        return session.getDslContext()
                .selectCount()
                .from(GeoCodeLanguages)
                .where(GeoCodeLanguages.LANGUAGE.eq(language.getPrimaryKey()),
                        GeoCodeLanguages.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private GeoCodeLanguage getGeoCodeLanguage(GeoCode geoCode, Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(GeoCodeLanguages.fields())
                .from(GeoCodeLanguages)
                .where(GeoCodeLanguages.GEO_CODE.eq(geoCode.getPrimaryKey()),
                        GeoCodeLanguages.LANGUAGE.eq(language.getPrimaryKey()),
                        GeoCodeLanguages.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return geoCodeLanguageFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public GeoCodeLanguage getGeoCodeLanguage(GeoCode geoCode, Language language) {
        return getGeoCodeLanguage(geoCode, language, EntityPermission.READ_ONLY);
    }
    
    public GeoCodeLanguage getGeoCodeLanguageForUpdate(GeoCode geoCode, Language language) {
        return getGeoCodeLanguage(geoCode, language, EntityPermission.READ_WRITE);
    }
    
    public GeoCodeLanguageValue getGeoCodeLanguageValue(GeoCodeLanguage geoCodeLanguage) {
        return geoCodeLanguage == null? null: geoCodeLanguage.getGeoCodeLanguageValue().clone();
    }
    
    public GeoCodeLanguageValue getGeoCodeLanguageValueForUpdate(GeoCode geoCode, Language language) {
        return getGeoCodeLanguageValue(getGeoCodeLanguageForUpdate(geoCode, language));
    }
    
    private GeoCodeLanguage getDefaultGeoCodeLanguage(GeoCode geoCode, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(GeoCodeLanguages.fields())
                .from(GeoCodeLanguages)
                .where(GeoCodeLanguages.GEO_CODE.eq(geoCode.getPrimaryKey()),
                        GeoCodeLanguages.IS_DEFAULT.eq(true),
                        GeoCodeLanguages.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return geoCodeLanguageFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public GeoCodeLanguage getDefaultGeoCodeLanguage(GeoCode geoCode) {
        return getDefaultGeoCodeLanguage(geoCode, EntityPermission.READ_ONLY);
    }
    
    public GeoCodeLanguage getDefaultGeoCodeLanguageForUpdate(GeoCode geoCode) {
        return getDefaultGeoCodeLanguage(geoCode, EntityPermission.READ_WRITE);
    }
    
    public GeoCodeLanguageValue getDefaultGeoCodeLanguageValueForUpdate(GeoCode geoCode) {
        var geoCodeLanguage = getDefaultGeoCodeLanguageForUpdate(geoCode);
        
        return geoCodeLanguage == null? null: geoCodeLanguage.getGeoCodeLanguageValue().clone();
    }
    
    private List<GeoCodeLanguage> getGeoCodeLanguagesByGeoCode(GeoCode geoCode, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(GeoCodeLanguages.fields())
                    .from(GeoCodeLanguages)
                    .join(Languages).onKey(GEO_CODE_LANGUAGES_LANGUAGE_FK)
                    .where(GeoCodeLanguages.GEO_CODE.eq(geoCode.getPrimaryKey()),
                            GeoCodeLanguages.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    GeoCodeLanguageFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(GeoCodeLanguages.fields())
                    .from(GeoCodeLanguages)
                    .where(GeoCodeLanguages.GEO_CODE.eq(geoCode.getPrimaryKey()),
                            GeoCodeLanguages.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return geoCodeLanguageFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<GeoCodeLanguage> getGeoCodeLanguagesByGeoCode(GeoCode geoCode) {
        return getGeoCodeLanguagesByGeoCode(geoCode, EntityPermission.READ_ONLY);
    }
    
    public List<GeoCodeLanguage> getGeoCodeLanguagesByGeoCodeForUpdate(GeoCode geoCode) {
        return getGeoCodeLanguagesByGeoCode(geoCode, EntityPermission.READ_WRITE);
    }
    
    private List<GeoCodeLanguage> getGeoCodeLanguagesByLanguage(Language language, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(GeoCodeLanguages.fields())
                    .from(GeoCodeLanguages)
                    .join(GeoCodes).onKey(GEO_CODE_LANGUAGES_GEO_CODE_FK)
                    .join(GeoCodeDetails).onKey(GEO_CODES_LAST_DETAIL_FK)
                    .where(GeoCodeLanguages.LANGUAGE.eq(language.getPrimaryKey()),
                            GeoCodeLanguages.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(GeoCodeDetails.SORT_ORDER, GeoCodeDetails.GEO_CODE_NAME),
                    GeoCodeLanguageFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(GeoCodeLanguages.fields())
                    .from(GeoCodeLanguages)
                    .where(GeoCodeLanguages.LANGUAGE.eq(language.getPrimaryKey()),
                            GeoCodeLanguages.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return geoCodeLanguageFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<GeoCodeLanguage> getGeoCodeLanguagesByLanguage(Language language) {
        return getGeoCodeLanguagesByLanguage(language, EntityPermission.READ_ONLY);
    }
    
    public List<GeoCodeLanguage> getGeoCodeLanguagesByLanguageForUpdate(Language language) {
        return getGeoCodeLanguagesByLanguage(language, EntityPermission.READ_WRITE);
    }
    
    public List<GeoCodeLanguageTransfer> getGeoCodeLanguageTransfers(UserVisit userVisit, Collection<GeoCodeLanguage> geoCodeLanguages) {
        List<GeoCodeLanguageTransfer> geoCodeLanguageTransfers = new ArrayList<>(geoCodeLanguages.size());
        
        geoCodeLanguages.forEach((geoCodeLanguage) ->
                geoCodeLanguageTransfers.add(geoCodeLanguageTransferCache.getGeoCodeLanguageTransfer(userVisit, geoCodeLanguage))
        );
        
        return geoCodeLanguageTransfers;
    }
    
    public List<GeoCodeLanguageTransfer> getGeoCodeLanguageTransfersByGeoCode(UserVisit userVisit, GeoCode geoCode) {
        return getGeoCodeLanguageTransfers(userVisit, getGeoCodeLanguagesByGeoCode(geoCode));
    }
    
    public List<GeoCodeLanguageTransfer> getGeoCodeLanguageTransfersByLanguage(UserVisit userVisit, Language language) {
        return getGeoCodeLanguageTransfers(userVisit, getGeoCodeLanguagesByLanguage(language));
    }
    
    public GeoCodeLanguageTransfer getGeoCodeLanguageTransfer(UserVisit userVisit, GeoCodeLanguage geoCodeLanguage) {
        return geoCodeLanguageTransferCache.getGeoCodeLanguageTransfer(userVisit, geoCodeLanguage);
    }
    
    private void updateGeoCodeLanguageFromValue(GeoCodeLanguageValue geoCodeLanguageValue, boolean checkDefault, BasePK updatedBy) {
        if(geoCodeLanguageValue.hasBeenModified()) {
            var geoCodeLanguage = geoCodeLanguageFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     geoCodeLanguageValue.getPrimaryKey());
            
            geoCodeLanguage.setThruTime(session.getStartTime());
            geoCodeLanguage.store();

            var geoCode = geoCodeLanguage.getGeoCode(); // Not Updated
            var geoCodePK = geoCode.getPrimaryKey(); // Not Updated
            var languagePK = geoCodeLanguage.getLanguagePK(); // Not Updated
            var isDefault = geoCodeLanguageValue.getIsDefault();
            var sortOrder = geoCodeLanguageValue.getSortOrder();
            
            if(checkDefault) {
                var defaultGeoCodeLanguage = getDefaultGeoCodeLanguage(geoCode);
                var defaultFound = defaultGeoCodeLanguage != null && !defaultGeoCodeLanguage.equals(geoCodeLanguage);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultGeoCodeLanguageValue = getDefaultGeoCodeLanguageValueForUpdate(geoCode);
                    
                    defaultGeoCodeLanguageValue.setIsDefault(false);
                    updateGeoCodeLanguageFromValue(defaultGeoCodeLanguageValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }
            
            geoCodeLanguage = geoCodeLanguageFactory.create(geoCodePK, languagePK, isDefault, sortOrder,
                    session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(geoCodePK, EventTypes.MODIFY, geoCodeLanguage.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void updateGeoCodeLanguageFromValue(GeoCodeLanguageValue geoCodeLanguageValue, BasePK updatedBy) {
        updateGeoCodeLanguageFromValue(geoCodeLanguageValue, true, updatedBy);
    }
    
    public void deleteGeoCodeLanguage(GeoCodeLanguage geoCodeLanguage, BasePK deletedBy) {
        geoCodeLanguage.setThruTime(session.getStartTime());
        geoCodeLanguage.store();
        
        // Check for default, and pick one if necessary
        var geoCode = geoCodeLanguage.getGeoCode();
        var defaultGeoCodeLanguage = getDefaultGeoCodeLanguage(geoCode);
        if(defaultGeoCodeLanguage == null) {
            var geoCodeLanguages = getGeoCodeLanguagesByGeoCodeForUpdate(geoCode);
            
            if(!geoCodeLanguages.isEmpty()) {
                var iter = geoCodeLanguages.iterator();
                if(iter.hasNext()) {
                    defaultGeoCodeLanguage = iter.next();
                }
                var geoCodeLanguageValue = defaultGeoCodeLanguage.getGeoCodeLanguageValue().clone();
                
                geoCodeLanguageValue.setIsDefault(true);
                updateGeoCodeLanguageFromValue(geoCodeLanguageValue, false, deletedBy);
            }
        }
        
        sendEvent(geoCodeLanguage.getGeoCodePK(), EventTypes.MODIFY, geoCodeLanguage.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteGeoCodeLanguages(List<GeoCodeLanguage> geoCodeLanguages, BasePK deletedBy) {
        geoCodeLanguages.forEach((geoCodeLanguage) -> 
                deleteGeoCodeLanguage(geoCodeLanguage, deletedBy)
        );
    }
    
    public void deleteGeoCodeLanguagesByGeoCode(GeoCode geoCode, BasePK deletedBy) {
        deleteGeoCodeLanguages(getGeoCodeLanguagesByGeoCodeForUpdate(geoCode), deletedBy);
    }
    
    public void deleteGeoCodeLanguagesByLanguage(Language language, BasePK deletedBy) {
        deleteGeoCodeLanguages(getGeoCodeLanguagesByLanguageForUpdate(language), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Geo Code Currencies
    // --------------------------------------------------------------------------------

    @Inject
    protected GeoCodeCurrencyFactory geoCodeCurrencyFactory;

    @Inject
    GeoCodeCurrencyTransferCache geoCodeCurrencyTransferCache;

    public GeoCodeCurrency createGeoCodeCurrency(GeoCode geoCode, Currency currency, Boolean isDefault, Integer sortOrder,
            BasePK createdBy) {
        var defaultGeoCodeCurrency = getDefaultGeoCodeCurrency(geoCode);
        var defaultFound = defaultGeoCodeCurrency != null;
        
        if(defaultFound && isDefault) {
            var defaultGeoCodeCurrencyValue = getDefaultGeoCodeCurrencyValueForUpdate(geoCode);
            
            defaultGeoCodeCurrencyValue.setIsDefault(false);
            updateGeoCodeCurrencyFromValue(defaultGeoCodeCurrencyValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var geoCodeCurrency = geoCodeCurrencyFactory.create(geoCode, currency, isDefault,
                sortOrder, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(geoCode.getPrimaryKey(), EventTypes.MODIFY, geoCodeCurrency.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return geoCodeCurrency;
    }

    public long countGeoCodeCurrenciesByGeoCode(GeoCode geoCode) {
        return session.getDslContext()
                .selectCount()
                .from(GeoCodeCurrencies)
                .where(GeoCodeCurrencies.GEO_CODE.eq(geoCode.getPrimaryKey()),
                        GeoCodeCurrencies.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countGeoCodeCurrenciesByCurrency(Currency currency) {
        return session.getDslContext()
                .selectCount()
                .from(GeoCodeCurrencies)
                .where(GeoCodeCurrencies.CURRENCY.eq(currency.getPrimaryKey()),
                        GeoCodeCurrencies.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public GeoCodeCurrency getGeoCodeCurrency(GeoCode geoCode, Currency currency, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(GeoCodeCurrencies.fields())
                .from(GeoCodeCurrencies)
                .where(GeoCodeCurrencies.GEO_CODE.eq(geoCode.getPrimaryKey()),
                        GeoCodeCurrencies.CURRENCY.eq(currency.getPrimaryKey()),
                        GeoCodeCurrencies.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return geoCodeCurrencyFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public GeoCodeCurrency getGeoCodeCurrency(GeoCode geoCode, Currency currency) {
        return getGeoCodeCurrency(geoCode, currency, EntityPermission.READ_ONLY);
    }
    
    public GeoCodeCurrency getGeoCodeCurrencyForUpdate(GeoCode geoCode, Currency currency) {
        return getGeoCodeCurrency(geoCode, currency, EntityPermission.READ_WRITE);
    }
    
    public GeoCodeCurrencyValue getGeoCodeCurrencyValue(GeoCodeCurrency geoCodeCurrency) {
        return geoCodeCurrency == null? null: geoCodeCurrency.getGeoCodeCurrencyValue().clone();
    }
    
    public GeoCodeCurrencyValue getGeoCodeCurrencyValueForUpdate(GeoCode geoCode, Currency currency) {
        return getGeoCodeCurrencyValue(getGeoCodeCurrencyForUpdate(geoCode, currency));
    }
    
    private GeoCodeCurrency getDefaultGeoCodeCurrency(GeoCode geoCode, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(GeoCodeCurrencies.fields())
                .from(GeoCodeCurrencies)
                .where(GeoCodeCurrencies.GEO_CODE.eq(geoCode.getPrimaryKey()),
                        GeoCodeCurrencies.IS_DEFAULT.eq(true),
                        GeoCodeCurrencies.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return geoCodeCurrencyFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public GeoCodeCurrency getDefaultGeoCodeCurrency(GeoCode geoCode) {
        return getDefaultGeoCodeCurrency(geoCode, EntityPermission.READ_ONLY);
    }
    
    public GeoCodeCurrency getDefaultGeoCodeCurrencyForUpdate(GeoCode geoCode) {
        return getDefaultGeoCodeCurrency(geoCode, EntityPermission.READ_WRITE);
    }
    
    public GeoCodeCurrencyValue getDefaultGeoCodeCurrencyValueForUpdate(GeoCode geoCode) {
        var geoCodeCurrency = getDefaultGeoCodeCurrencyForUpdate(geoCode);
        
        return geoCodeCurrency == null? null: geoCodeCurrency.getGeoCodeCurrencyValue().clone();
    }
    private List<GeoCodeCurrency> getGeoCodeCurrenciesByGeoCode(GeoCode geoCode, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(GeoCodeCurrencies.fields())
                    .from(GeoCodeCurrencies)
                    .join(Currencies).onKey(GEO_CODE_CURRENCIES_CURRENCY_FK)
                    .where(GeoCodeCurrencies.GEO_CODE.eq(geoCode.getPrimaryKey()),
                            GeoCodeCurrencies.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Currencies.SORT_ORDER, Currencies.CURRENCY_ISO_NAME),
                    GeoCodeCurrencyFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(GeoCodeCurrencies.fields())
                    .from(GeoCodeCurrencies)
                    .where(GeoCodeCurrencies.GEO_CODE.eq(geoCode.getPrimaryKey()),
                            GeoCodeCurrencies.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return geoCodeCurrencyFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<GeoCodeCurrency> getGeoCodeCurrenciesByGeoCode(GeoCode geoCode) {
        return getGeoCodeCurrenciesByGeoCode(geoCode, EntityPermission.READ_ONLY);
    }
    
    public List<GeoCodeCurrency> getGeoCodeCurrenciesByGeoCodeForUpdate(GeoCode geoCode) {
        return getGeoCodeCurrenciesByGeoCode(geoCode, EntityPermission.READ_WRITE);
    }
    
    private List<GeoCodeCurrency> getGeoCodeCurrenciesByCurrency(Currency currency, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(GeoCodeCurrencies.fields())
                    .from(GeoCodeCurrencies)
                    .join(GeoCodes).onKey(GEO_CODE_CURRENCIES_GEO_CODE_FK)
                    .join(GeoCodeDetails).onKey(GEO_CODES_LAST_DETAIL_FK)
                    .where(GeoCodeCurrencies.CURRENCY.eq(currency.getPrimaryKey()),
                            GeoCodeCurrencies.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(GeoCodeDetails.SORT_ORDER, GeoCodeDetails.GEO_CODE_NAME),
                    GeoCodeCurrencyFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(GeoCodeCurrencies.fields())
                    .from(GeoCodeCurrencies)
                    .where(GeoCodeCurrencies.CURRENCY.eq(currency.getPrimaryKey()),
                            GeoCodeCurrencies.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return geoCodeCurrencyFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<GeoCodeCurrency> getGeoCodeCurrenciesByCurrency(Currency currency) {
        return getGeoCodeCurrenciesByCurrency(currency, EntityPermission.READ_ONLY);
    }
    
    public List<GeoCodeCurrency> getGeoCodeCurrenciesByCurrencyForUpdate(Currency currency) {
        return getGeoCodeCurrenciesByCurrency(currency, EntityPermission.READ_WRITE);
    }
    
    public List<GeoCodeCurrencyTransfer> getGeoCodeCurrencyTransfers(UserVisit userVisit, Collection<GeoCodeCurrency> geoCodeCurrencies) {
        List<GeoCodeCurrencyTransfer> geoCodeCurrencyTransfers = new ArrayList<>(geoCodeCurrencies.size());
        
        geoCodeCurrencies.forEach((geoCodeCurrency) ->
                geoCodeCurrencyTransfers.add(geoCodeCurrencyTransferCache.getGeoCodeCurrencyTransfer(userVisit, geoCodeCurrency))
        );
        
        return geoCodeCurrencyTransfers;
    }
    
    public List<GeoCodeCurrencyTransfer> getGeoCodeCurrencyTransfersByGeoCode(UserVisit userVisit, GeoCode geoCode) {
        return getGeoCodeCurrencyTransfers(userVisit, getGeoCodeCurrenciesByGeoCode(geoCode));
    }
    
    public List<GeoCodeCurrencyTransfer> getGeoCodeCurrencyTransfersByCurrency(UserVisit userVisit, Currency currency) {
        return getGeoCodeCurrencyTransfers(userVisit, getGeoCodeCurrenciesByCurrency(currency));
    }
    
    public GeoCodeCurrencyTransfer getGeoCodeCurrencyTransfer(UserVisit userVisit, GeoCodeCurrency geoCodeCurrency) {
        return geoCodeCurrencyTransferCache.getGeoCodeCurrencyTransfer(userVisit, geoCodeCurrency);
    }
    
    private void updateGeoCodeCurrencyFromValue(GeoCodeCurrencyValue geoCodeCurrencyValue, boolean checkDefault, BasePK updatedBy) {
        if(geoCodeCurrencyValue.hasBeenModified()) {
            var geoCodeCurrency = geoCodeCurrencyFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     geoCodeCurrencyValue.getPrimaryKey());
            
            geoCodeCurrency.setThruTime(session.getStartTime());
            geoCodeCurrency.store();

            var geoCode = geoCodeCurrency.getGeoCode(); // Not Updated
            var geoCodePK = geoCode.getPrimaryKey(); // Not Updated
            var currencyPK = geoCodeCurrency.getCurrencyPK(); // Not Updated
            var isDefault = geoCodeCurrencyValue.getIsDefault();
            var sortOrder = geoCodeCurrencyValue.getSortOrder();
            
            if(checkDefault) {
                var defaultGeoCodeCurrency = getDefaultGeoCodeCurrency(geoCode);
                var defaultFound = defaultGeoCodeCurrency != null && !defaultGeoCodeCurrency.equals(geoCodeCurrency);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultGeoCodeCurrencyValue = getDefaultGeoCodeCurrencyValueForUpdate(geoCode);
                    
                    defaultGeoCodeCurrencyValue.setIsDefault(false);
                    updateGeoCodeCurrencyFromValue(defaultGeoCodeCurrencyValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }
            
            geoCodeCurrency = geoCodeCurrencyFactory.create(geoCodePK, currencyPK, isDefault, sortOrder,
                    session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(geoCodePK, EventTypes.MODIFY, geoCodeCurrency.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void updateGeoCodeCurrencyFromValue(GeoCodeCurrencyValue geoCodeCurrencyValue, BasePK updatedBy) {
        updateGeoCodeCurrencyFromValue(geoCodeCurrencyValue, true, updatedBy);
    }
    
    public void deleteGeoCodeCurrency(GeoCodeCurrency geoCodeCurrency, BasePK deletedBy) {
        geoCodeCurrency.setThruTime(session.getStartTime());
        geoCodeCurrency.store();
        
        // Check for default, and pick one if necessary
        var geoCode = geoCodeCurrency.getGeoCode();
        var defaultGeoCodeCurrency = getDefaultGeoCodeCurrency(geoCode);
        if(defaultGeoCodeCurrency == null) {
            var geoCodeCurrencies = getGeoCodeCurrenciesByGeoCodeForUpdate(geoCode);
            
            if(!geoCodeCurrencies.isEmpty()) {
                var iter = geoCodeCurrencies.iterator();
                if(iter.hasNext()) {
                    defaultGeoCodeCurrency = iter.next();
                }
                var geoCodeCurrencyValue = defaultGeoCodeCurrency.getGeoCodeCurrencyValue().clone();
                
                geoCodeCurrencyValue.setIsDefault(true);
                updateGeoCodeCurrencyFromValue(geoCodeCurrencyValue, false, deletedBy);
            }
        }
        
        sendEvent(geoCodeCurrency.getGeoCodePK(), EventTypes.MODIFY, geoCodeCurrency.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteGeoCodeCurrencies(List<GeoCodeCurrency> geoCodeCurrencies, BasePK deletedBy) {
        geoCodeCurrencies.forEach((geoCodeCurrency) -> 
                deleteGeoCodeCurrency(geoCodeCurrency, deletedBy)
        );
    }
    
    public void deleteGeoCodeCurrenciesByGeoCode(GeoCode geoCode, BasePK deletedBy) {
        deleteGeoCodeCurrencies(getGeoCodeCurrenciesByGeoCodeForUpdate(geoCode), deletedBy);
    }
    
    public void deleteGeoCodeCurrenciesByCurrency(Currency currency, BasePK deletedBy) {
        deleteGeoCodeCurrencies(getGeoCodeCurrenciesByCurrencyForUpdate(currency), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Geo Code Time Zones
    // --------------------------------------------------------------------------------

    @Inject
    protected GeoCodeTimeZoneFactory geoCodeTimeZoneFactory;

    @Inject
    GeoCodeTimeZoneTransferCache geoCodeTimeZoneTransferCache;

    public GeoCodeTimeZone createGeoCodeTimeZone(GeoCode geoCode, TimeZone timeZone, Boolean isDefault, Integer sortOrder,
            BasePK createdBy) {
        var defaultGeoCodeTimeZone = getDefaultGeoCodeTimeZone(geoCode);
        var defaultFound = defaultGeoCodeTimeZone != null;
        
        if(defaultFound && isDefault) {
            var defaultGeoCodeTimeZoneValue = getDefaultGeoCodeTimeZoneValueForUpdate(geoCode);
            
            defaultGeoCodeTimeZoneValue.setIsDefault(false);
            updateGeoCodeTimeZoneFromValue(defaultGeoCodeTimeZoneValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var geoCodeTimeZone = geoCodeTimeZoneFactory.create(geoCode, timeZone, isDefault,
                sortOrder, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(geoCode.getPrimaryKey(), EventTypes.MODIFY, geoCodeTimeZone.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return geoCodeTimeZone;
    }

    public long countGeoCodeTimeZonesByGeoCode(GeoCode geoCode) {
        return session.getDslContext()
                .selectCount()
                .from(GeoCodeTimeZones)
                .where(GeoCodeTimeZones.GEO_CODE.eq(geoCode.getPrimaryKey()),
                        GeoCodeTimeZones.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countGeoCodeTimeZonesByTimeZone(TimeZone timeZone) {
        return session.getDslContext()
                .selectCount()
                .from(GeoCodeTimeZones)
                .where(GeoCodeTimeZones.TIME_ZONE.eq(timeZone.getPrimaryKey()),
                        GeoCodeTimeZones.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private GeoCodeTimeZone getGeoCodeTimeZone(GeoCode geoCode, TimeZone timeZone, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(GeoCodeTimeZones.fields())
                .from(GeoCodeTimeZones)
                .where(GeoCodeTimeZones.GEO_CODE.eq(geoCode.getPrimaryKey()),
                        GeoCodeTimeZones.TIME_ZONE.eq(timeZone.getPrimaryKey()),
                        GeoCodeTimeZones.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return geoCodeTimeZoneFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public GeoCodeTimeZone getGeoCodeTimeZone(GeoCode geoCode, TimeZone timeZone) {
        return getGeoCodeTimeZone(geoCode, timeZone, EntityPermission.READ_ONLY);
    }
    
    public GeoCodeTimeZone getGeoCodeTimeZoneForUpdate(GeoCode geoCode, TimeZone timeZone) {
        return getGeoCodeTimeZone(geoCode, timeZone, EntityPermission.READ_WRITE);
    }
    
    public GeoCodeTimeZoneValue getGeoCodeTimeZoneValue(GeoCodeTimeZone geoCodeTimeZone) {
        return geoCodeTimeZone == null? null: geoCodeTimeZone.getGeoCodeTimeZoneValue().clone();
    }
    
    public GeoCodeTimeZoneValue getGeoCodeTimeZoneValueForUpdate(GeoCode geoCode, TimeZone timeZone) {
        return getGeoCodeTimeZoneValue(getGeoCodeTimeZoneForUpdate(geoCode, timeZone));
    }
    
    private GeoCodeTimeZone getDefaultGeoCodeTimeZone(GeoCode geoCode, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(GeoCodeTimeZones.fields())
                .from(GeoCodeTimeZones)
                .where(GeoCodeTimeZones.GEO_CODE.eq(geoCode.getPrimaryKey()),
                        GeoCodeTimeZones.IS_DEFAULT.eq(true),
                        GeoCodeTimeZones.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return geoCodeTimeZoneFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public GeoCodeTimeZone getDefaultGeoCodeTimeZone(GeoCode geoCode) {
        return getDefaultGeoCodeTimeZone(geoCode, EntityPermission.READ_ONLY);
    }
    
    public GeoCodeTimeZone getDefaultGeoCodeTimeZoneForUpdate(GeoCode geoCode) {
        return getDefaultGeoCodeTimeZone(geoCode, EntityPermission.READ_WRITE);
    }
    
    public GeoCodeTimeZoneValue getDefaultGeoCodeTimeZoneValueForUpdate(GeoCode geoCode) {
        var geoCodeTimeZone = getDefaultGeoCodeTimeZoneForUpdate(geoCode);
        
        return geoCodeTimeZone == null? null: geoCodeTimeZone.getGeoCodeTimeZoneValue().clone();
    }
    
    private List<GeoCodeTimeZone> getGeoCodeTimeZonesByGeoCode(GeoCode geoCode, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(GeoCodeTimeZones.fields())
                    .from(GeoCodeTimeZones)
                    .join(TimeZones).onKey(GEO_CODE_TIME_ZONES_TIME_ZONE_FK)
                    .join(TimeZoneDetails).onKey(TIME_ZONES_LAST_DETAIL_FK)
                    .where(GeoCodeTimeZones.GEO_CODE.eq(geoCode.getPrimaryKey()),
                            GeoCodeTimeZones.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(TimeZoneDetails.SORT_ORDER, TimeZoneDetails.JAVA_TIME_ZONE_NAME),
                    GeoCodeTimeZoneFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(GeoCodeTimeZones.fields())
                    .from(GeoCodeTimeZones)
                    .where(GeoCodeTimeZones.GEO_CODE.eq(geoCode.getPrimaryKey()),
                            GeoCodeTimeZones.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return geoCodeTimeZoneFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<GeoCodeTimeZone> getGeoCodeTimeZonesByGeoCode(GeoCode geoCode) {
        return getGeoCodeTimeZonesByGeoCode(geoCode, EntityPermission.READ_ONLY);
    }
    
    public List<GeoCodeTimeZone> getGeoCodeTimeZonesByGeoCodeForUpdate(GeoCode geoCode) {
        return getGeoCodeTimeZonesByGeoCode(geoCode, EntityPermission.READ_WRITE);
    }
    
    private List<GeoCodeTimeZone> getGeoCodeTimeZonesByTimeZone(TimeZone timeZone, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(GeoCodeTimeZones.fields())
                    .from(GeoCodeTimeZones)
                    .join(GeoCodes).onKey(GEO_CODE_TIME_ZONES_GEO_CODE_FK)
                    .join(GeoCodeDetails).onKey(GEO_CODES_LAST_DETAIL_FK)
                    .where(GeoCodeTimeZones.TIME_ZONE.eq(timeZone.getPrimaryKey()),
                            GeoCodeTimeZones.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(GeoCodeDetails.SORT_ORDER, GeoCodeDetails.GEO_CODE_NAME),
                    GeoCodeTimeZoneFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(GeoCodeTimeZones.fields())
                    .from(GeoCodeTimeZones)
                    .where(GeoCodeTimeZones.TIME_ZONE.eq(timeZone.getPrimaryKey()),
                            GeoCodeTimeZones.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return geoCodeTimeZoneFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<GeoCodeTimeZone> getGeoCodeTimeZonesByTimeZone(TimeZone timeZone) {
        return getGeoCodeTimeZonesByTimeZone(timeZone, EntityPermission.READ_ONLY);
    }
    
    public List<GeoCodeTimeZone> getGeoCodeTimeZonesByTimeZoneForUpdate(TimeZone timeZone) {
        return getGeoCodeTimeZonesByTimeZone(timeZone, EntityPermission.READ_WRITE);
    }
    
    public List<GeoCodeTimeZoneTransfer> getGeoCodeTimeZoneTransfers(UserVisit userVisit, Collection<GeoCodeTimeZone> geoCodeTimeZones) {
        List<GeoCodeTimeZoneTransfer> geoCodeTimeZoneTransfers = new ArrayList<>(geoCodeTimeZones.size());
        
        geoCodeTimeZones.forEach((geoCodeTimeZone) ->
                geoCodeTimeZoneTransfers.add(geoCodeTimeZoneTransferCache.getGeoCodeTimeZoneTransfer(userVisit, geoCodeTimeZone))
        );
        
        return geoCodeTimeZoneTransfers;
    }
    
    public List<GeoCodeTimeZoneTransfer> getGeoCodeTimeZoneTransfersByGeoCode(UserVisit userVisit, GeoCode geoCode) {
        return getGeoCodeTimeZoneTransfers(userVisit, getGeoCodeTimeZonesByGeoCode(geoCode));
    }
    
    public List<GeoCodeTimeZoneTransfer> getGeoCodeTimeZoneTransfersByTimeZone(UserVisit userVisit, TimeZone timeZone) {
        return getGeoCodeTimeZoneTransfers(userVisit, getGeoCodeTimeZonesByTimeZone(timeZone));
    }
    
    public GeoCodeTimeZoneTransfer getGeoCodeTimeZoneTransfer(UserVisit userVisit, GeoCodeTimeZone geoCodeTimeZone) {
        return geoCodeTimeZoneTransferCache.getGeoCodeTimeZoneTransfer(userVisit, geoCodeTimeZone);
    }
    
    private void updateGeoCodeTimeZoneFromValue(GeoCodeTimeZoneValue geoCodeTimeZoneValue, boolean checkDefault, BasePK updatedBy) {
        if(geoCodeTimeZoneValue.hasBeenModified()) {
            var geoCodeTimeZone = geoCodeTimeZoneFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     geoCodeTimeZoneValue.getPrimaryKey());
            
            geoCodeTimeZone.setThruTime(session.getStartTime());
            geoCodeTimeZone.store();

            var geoCode = geoCodeTimeZone.getGeoCode(); // Not Updated
            var geoCodePK = geoCode.getPrimaryKey(); // Not Updated
            var timeZonePK = geoCodeTimeZone.getTimeZonePK(); // Not Updated
            var isDefault = geoCodeTimeZoneValue.getIsDefault();
            var sortOrder = geoCodeTimeZoneValue.getSortOrder();
            
            if(checkDefault) {
                var defaultGeoCodeTimeZone = getDefaultGeoCodeTimeZone(geoCode);
                var defaultFound = defaultGeoCodeTimeZone != null && !defaultGeoCodeTimeZone.equals(geoCodeTimeZone);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultGeoCodeTimeZoneValue = getDefaultGeoCodeTimeZoneValueForUpdate(geoCode);
                    
                    defaultGeoCodeTimeZoneValue.setIsDefault(false);
                    updateGeoCodeTimeZoneFromValue(defaultGeoCodeTimeZoneValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }
            
            geoCodeTimeZone = geoCodeTimeZoneFactory.create(geoCodePK, timeZonePK, isDefault, sortOrder,
                    session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(geoCodePK, EventTypes.MODIFY, geoCodeTimeZone.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void updateGeoCodeTimeZoneFromValue(GeoCodeTimeZoneValue geoCodeTimeZoneValue, BasePK updatedBy) {
        updateGeoCodeTimeZoneFromValue(geoCodeTimeZoneValue, true, updatedBy);
    }
    
    public void deleteGeoCodeTimeZone(GeoCodeTimeZone geoCodeTimeZone, BasePK deletedBy) {
        geoCodeTimeZone.setThruTime(session.getStartTime());
        geoCodeTimeZone.store();
        
        // Check for default, and pick one if necessary
        var geoCode = geoCodeTimeZone.getGeoCode();
        var defaultGeoCodeTimeZone = getDefaultGeoCodeTimeZone(geoCode);
        if(defaultGeoCodeTimeZone == null) {
            var geoCodeTimeZones = getGeoCodeTimeZonesByGeoCodeForUpdate(geoCode);
            
            if(!geoCodeTimeZones.isEmpty()) {
                var iter = geoCodeTimeZones.iterator();
                if(iter.hasNext()) {
                    defaultGeoCodeTimeZone = iter.next();
                }
                var geoCodeTimeZoneValue = defaultGeoCodeTimeZone.getGeoCodeTimeZoneValue().clone();
                
                geoCodeTimeZoneValue.setIsDefault(true);
                updateGeoCodeTimeZoneFromValue(geoCodeTimeZoneValue, false, deletedBy);
            }
        }
        
        sendEvent(geoCodeTimeZone.getGeoCodePK(), EventTypes.MODIFY, geoCodeTimeZone.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteGeoCodeTimeZones(List<GeoCodeTimeZone> geoCodeTimeZones, BasePK deletedBy) {
        geoCodeTimeZones.forEach((geoCodeTimeZone) -> 
                deleteGeoCodeTimeZone(geoCodeTimeZone, deletedBy)
        );
    }
    
    public void deleteGeoCodeTimeZonesByGeoCode(GeoCode geoCode, BasePK deletedBy) {
        deleteGeoCodeTimeZones(getGeoCodeTimeZonesByGeoCodeForUpdate(geoCode), deletedBy);
    }
    
    public void deleteGeoCodeTimeZonesByTimeZone(TimeZone timeZone, BasePK deletedBy) {
        deleteGeoCodeTimeZones(getGeoCodeTimeZonesByTimeZoneForUpdate(timeZone), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Geo Code Date Time Formats
    // --------------------------------------------------------------------------------

    @Inject
    protected GeoCodeDateTimeFormatFactory geoCodeDateTimeFormatFactory;

    @Inject
    GeoCodeDateTimeFormatTransferCache geoCodeDateTimeFormatTransferCache;

    public GeoCodeDateTimeFormat createGeoCodeDateTimeFormat(GeoCode geoCode, DateTimeFormat dateTimeFormat, Boolean isDefault,
            Integer sortOrder, BasePK createdBy) {
        var defaultGeoCodeDateTimeFormat = getDefaultGeoCodeDateTimeFormat(geoCode);
        var defaultFound = defaultGeoCodeDateTimeFormat != null;
        
        if(defaultFound && isDefault) {
            var defaultGeoCodeDateTimeFormatValue = getDefaultGeoCodeDateTimeFormatValueForUpdate(geoCode);
            
            defaultGeoCodeDateTimeFormatValue.setIsDefault(false);
            updateGeoCodeDateTimeFormatFromValue(defaultGeoCodeDateTimeFormatValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var geoCodeDateTimeFormat = geoCodeDateTimeFormatFactory.create(geoCode,
                dateTimeFormat, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(geoCode.getPrimaryKey(), EventTypes.MODIFY, geoCodeDateTimeFormat.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return geoCodeDateTimeFormat;
    }

    public long countGeoCodeDateTimeFormatsByGeoCode(GeoCode geoCode) {
        return session.getDslContext()
                .selectCount()
                .from(GeoCodeDateTimeFormats)
                .where(GeoCodeDateTimeFormats.GEO_CODE.eq(geoCode.getPrimaryKey()),
                        GeoCodeDateTimeFormats.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countGeoCodeDateTimeFormatsByDateTimeFormat(DateTimeFormat dateTimeFormat) {
        return session.getDslContext()
                .selectCount()
                .from(GeoCodeDateTimeFormats)
                .where(GeoCodeDateTimeFormats.DATE_TIME_FORMAT.eq(dateTimeFormat.getPrimaryKey()),
                        GeoCodeDateTimeFormats.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private GeoCodeDateTimeFormat getGeoCodeDateTimeFormat(GeoCode geoCode, DateTimeFormat dateTimeFormat, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(GeoCodeDateTimeFormats.fields())
                .from(GeoCodeDateTimeFormats)
                .where(GeoCodeDateTimeFormats.GEO_CODE.eq(geoCode.getPrimaryKey()),
                        GeoCodeDateTimeFormats.DATE_TIME_FORMAT.eq(dateTimeFormat.getPrimaryKey()),
                        GeoCodeDateTimeFormats.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return geoCodeDateTimeFormatFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public GeoCodeDateTimeFormat getGeoCodeDateTimeFormat(GeoCode geoCode, DateTimeFormat dateTimeFormat) {
        return getGeoCodeDateTimeFormat(geoCode, dateTimeFormat, EntityPermission.READ_ONLY);
    }
    
    public GeoCodeDateTimeFormat getGeoCodeDateTimeFormatForUpdate(GeoCode geoCode, DateTimeFormat dateTimeFormat) {
        return getGeoCodeDateTimeFormat(geoCode, dateTimeFormat, EntityPermission.READ_WRITE);
    }
    
    public GeoCodeDateTimeFormatValue getGeoCodeDateTimeFormatValue(GeoCodeDateTimeFormat geoCodeDateTimeFormat) {
        return geoCodeDateTimeFormat == null? null: geoCodeDateTimeFormat.getGeoCodeDateTimeFormatValue().clone();
    }
    
    public GeoCodeDateTimeFormatValue getGeoCodeDateTimeFormatValueForUpdate(GeoCode geoCode, DateTimeFormat dateTimeFormat) {
        return getGeoCodeDateTimeFormatValue(getGeoCodeDateTimeFormatForUpdate(geoCode, dateTimeFormat));
    }
    
    private GeoCodeDateTimeFormat getDefaultGeoCodeDateTimeFormat(GeoCode geoCode, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(GeoCodeDateTimeFormats.fields())
                .from(GeoCodeDateTimeFormats)
                .where(GeoCodeDateTimeFormats.GEO_CODE.eq(geoCode.getPrimaryKey()),
                        GeoCodeDateTimeFormats.IS_DEFAULT.eq(true),
                        GeoCodeDateTimeFormats.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return geoCodeDateTimeFormatFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public GeoCodeDateTimeFormat getDefaultGeoCodeDateTimeFormat(GeoCode geoCode) {
        return getDefaultGeoCodeDateTimeFormat(geoCode, EntityPermission.READ_ONLY);
    }
    
    public GeoCodeDateTimeFormat getDefaultGeoCodeDateTimeFormatForUpdate(GeoCode geoCode) {
        return getDefaultGeoCodeDateTimeFormat(geoCode, EntityPermission.READ_WRITE);
    }
    
    public GeoCodeDateTimeFormatValue getDefaultGeoCodeDateTimeFormatValueForUpdate(GeoCode geoCode) {
        var geoCodeDateTimeFormat = getDefaultGeoCodeDateTimeFormatForUpdate(geoCode);
        
        return geoCodeDateTimeFormat == null? null: geoCodeDateTimeFormat.getGeoCodeDateTimeFormatValue().clone();
    }
    
    private List<GeoCodeDateTimeFormat> getGeoCodeDateTimeFormatsByGeoCode(GeoCode geoCode, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(GeoCodeDateTimeFormats.fields())
                    .from(GeoCodeDateTimeFormats)
                    .join(DateTimeFormats).onKey(GEO_CODE_DATE_TIME_FORMATS_DATE_TIME_FORMAT_FK)
                    .join(DateTimeFormatDetails).onKey(DATE_TIME_FORMATS_LAST_DETAIL_FK)
                    .where(GeoCodeDateTimeFormats.GEO_CODE.eq(geoCode.getPrimaryKey()),
                            GeoCodeDateTimeFormats.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(DateTimeFormatDetails.SORT_ORDER, DateTimeFormatDetails.DATE_TIME_FORMAT_NAME),
                    GeoCodeDateTimeFormatFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(GeoCodeDateTimeFormats.fields())
                    .from(GeoCodeDateTimeFormats)
                    .where(GeoCodeDateTimeFormats.GEO_CODE.eq(geoCode.getPrimaryKey()),
                            GeoCodeDateTimeFormats.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return geoCodeDateTimeFormatFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<GeoCodeDateTimeFormat> getGeoCodeDateTimeFormatsByGeoCode(GeoCode geoCode) {
        return getGeoCodeDateTimeFormatsByGeoCode(geoCode, EntityPermission.READ_ONLY);
    }
    
    public List<GeoCodeDateTimeFormat> getGeoCodeDateTimeFormatsByGeoCodeForUpdate(GeoCode geoCode) {
        return getGeoCodeDateTimeFormatsByGeoCode(geoCode, EntityPermission.READ_WRITE);
    }
    
    private List<GeoCodeDateTimeFormat> getGeoCodeDateTimeFormatsByDateTimeFormat(DateTimeFormat dateTimeFormat, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(GeoCodeDateTimeFormats.fields())
                    .from(GeoCodeDateTimeFormats)
                    .join(GeoCodes).onKey(GEO_CODE_DATE_TIME_FORMATS_GEO_CODE_FK)
                    .join(GeoCodeDetails).onKey(GEO_CODES_LAST_DETAIL_FK)
                    .where(GeoCodeDateTimeFormats.DATE_TIME_FORMAT.eq(dateTimeFormat.getPrimaryKey()),
                            GeoCodeDateTimeFormats.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(GeoCodeDetails.SORT_ORDER, GeoCodeDetails.GEO_CODE_NAME),
                    GeoCodeDateTimeFormatFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(GeoCodeDateTimeFormats.fields())
                    .from(GeoCodeDateTimeFormats)
                    .where(GeoCodeDateTimeFormats.DATE_TIME_FORMAT.eq(dateTimeFormat.getPrimaryKey()),
                            GeoCodeDateTimeFormats.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return geoCodeDateTimeFormatFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<GeoCodeDateTimeFormat> getGeoCodeDateTimeFormatsByDateTimeFormat(DateTimeFormat dateTimeFormat) {
        return getGeoCodeDateTimeFormatsByDateTimeFormat(dateTimeFormat, EntityPermission.READ_ONLY);
    }
    
    public List<GeoCodeDateTimeFormat> getGeoCodeDateTimeFormatsByDateTimeFormatForUpdate(DateTimeFormat dateTimeFormat) {
        return getGeoCodeDateTimeFormatsByDateTimeFormat(dateTimeFormat, EntityPermission.READ_WRITE);
    }
    
    public List<GeoCodeDateTimeFormatTransfer> getGeoCodeDateTimeFormatTransfers(UserVisit userVisit, Collection<GeoCodeDateTimeFormat> geoCodeDateTimeFormats) {
        List<GeoCodeDateTimeFormatTransfer> geoCodeDateTimeFormatTransfers = new ArrayList<>(geoCodeDateTimeFormats.size());
        
        geoCodeDateTimeFormats.forEach((geoCodeDateTimeFormat) ->
                geoCodeDateTimeFormatTransfers.add(geoCodeDateTimeFormatTransferCache.getGeoCodeDateTimeFormatTransfer(userVisit, geoCodeDateTimeFormat))
        );
        
        return geoCodeDateTimeFormatTransfers;
    }
    
    public List<GeoCodeDateTimeFormatTransfer> getGeoCodeDateTimeFormatTransfersByGeoCode(UserVisit userVisit, GeoCode geoCode) {
        return getGeoCodeDateTimeFormatTransfers(userVisit, getGeoCodeDateTimeFormatsByGeoCode(geoCode));
    }
    
    public List<GeoCodeDateTimeFormatTransfer> getGeoCodeDateTimeFormatTransfersByDateTimeFormat(UserVisit userVisit,
            DateTimeFormat dateTimeFormat) {
        return getGeoCodeDateTimeFormatTransfers(userVisit, getGeoCodeDateTimeFormatsByDateTimeFormat(dateTimeFormat));
    }
    
    public GeoCodeDateTimeFormatTransfer getGeoCodeDateTimeFormatTransfer(UserVisit userVisit,
            GeoCodeDateTimeFormat geoCodeDateTimeFormat) {
        return geoCodeDateTimeFormatTransferCache.getGeoCodeDateTimeFormatTransfer(userVisit, geoCodeDateTimeFormat);
    }
    
    private void updateGeoCodeDateTimeFormatFromValue(GeoCodeDateTimeFormatValue geoCodeDateTimeFormatValue, boolean checkDefault,
            BasePK updatedBy) {
        if(geoCodeDateTimeFormatValue.hasBeenModified()) {
            var geoCodeDateTimeFormat = geoCodeDateTimeFormatFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     geoCodeDateTimeFormatValue.getPrimaryKey());
            
            geoCodeDateTimeFormat.setThruTime(session.getStartTime());
            geoCodeDateTimeFormat.store();

            var geoCode = geoCodeDateTimeFormat.getGeoCode(); // Not Updated
            var geoCodePK = geoCode.getPrimaryKey(); // Not Updated
            var dateTimeFormatPK = geoCodeDateTimeFormat.getDateTimeFormatPK(); // Not Updated
            var isDefault = geoCodeDateTimeFormatValue.getIsDefault();
            var sortOrder = geoCodeDateTimeFormatValue.getSortOrder();
            
            if(checkDefault) {
                var defaultGeoCodeDateTimeFormat = getDefaultGeoCodeDateTimeFormat(geoCode);
                var defaultFound = defaultGeoCodeDateTimeFormat != null && !defaultGeoCodeDateTimeFormat.equals(geoCodeDateTimeFormat);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultGeoCodeDateTimeFormatValue = getDefaultGeoCodeDateTimeFormatValueForUpdate(geoCode);
                    
                    defaultGeoCodeDateTimeFormatValue.setIsDefault(false);
                    updateGeoCodeDateTimeFormatFromValue(defaultGeoCodeDateTimeFormatValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }
            
            geoCodeDateTimeFormat = geoCodeDateTimeFormatFactory.create(geoCodePK, dateTimeFormatPK,
                    isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(geoCodePK, EventTypes.MODIFY, geoCodeDateTimeFormat.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void updateGeoCodeDateTimeFormatFromValue(GeoCodeDateTimeFormatValue geoCodeDateTimeFormatValue, BasePK updatedBy) {
        updateGeoCodeDateTimeFormatFromValue(geoCodeDateTimeFormatValue, true, updatedBy);
    }
    
    public void deleteGeoCodeDateTimeFormat(GeoCodeDateTimeFormat geoCodeDateTimeFormat, BasePK deletedBy) {
        geoCodeDateTimeFormat.setThruTime(session.getStartTime());
        geoCodeDateTimeFormat.store();
        
        // Check for default, and pick one if necessary
        var geoCode = geoCodeDateTimeFormat.getGeoCode();
        var defaultGeoCodeDateTimeFormat = getDefaultGeoCodeDateTimeFormat(geoCode);
        if(defaultGeoCodeDateTimeFormat == null) {
            var geoCodeDateTimeFormats = getGeoCodeDateTimeFormatsByGeoCodeForUpdate(geoCode);
            
            if(!geoCodeDateTimeFormats.isEmpty()) {
                var iter = geoCodeDateTimeFormats.iterator();
                if(iter.hasNext()) {
                    defaultGeoCodeDateTimeFormat = iter.next();
                }
                var geoCodeDateTimeFormatValue = defaultGeoCodeDateTimeFormat.getGeoCodeDateTimeFormatValue().clone();
                
                geoCodeDateTimeFormatValue.setIsDefault(true);
                updateGeoCodeDateTimeFormatFromValue(geoCodeDateTimeFormatValue, false, deletedBy);
            }
        }
        
        sendEvent(geoCodeDateTimeFormat.getGeoCodePK(), EventTypes.MODIFY, geoCodeDateTimeFormat.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteGeoCodeDateTimeFormats(List<GeoCodeDateTimeFormat> geoCodeDateTimeFormats, BasePK deletedBy) {
        geoCodeDateTimeFormats.forEach((geoCodeDateTimeFormat) -> 
                deleteGeoCodeDateTimeFormat(geoCodeDateTimeFormat, deletedBy)
        );
    }
    
    public void deleteGeoCodeDateTimeFormatsByGeoCode(GeoCode geoCode, BasePK deletedBy) {
        deleteGeoCodeDateTimeFormats(getGeoCodeDateTimeFormatsByGeoCodeForUpdate(geoCode), deletedBy);
    }
    
    public void deleteGeoCodeDateTimeFormatsByDateTimeFormat(DateTimeFormat dateTimeFormat, BasePK deletedBy) {
        deleteGeoCodeDateTimeFormats(getGeoCodeDateTimeFormatsByDateTimeFormatForUpdate(dateTimeFormat), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Geo Code Countries
    // --------------------------------------------------------------------------------

    @Inject
    protected GeoCodeCountryFactory geoCodeCountryFactory;

    public GeoCodeCountry createGeoCodeCountry(GeoCode geoCode, String telephoneCode, String areaCodePattern,
            Boolean areaCodeRequired, String areaCodeExample, String telephoneNumberPattern, String telephoneNumberExample,
            PostalAddressFormat postalAddressFormat, Boolean cityRequired, Boolean cityGeoCodeRequired, Boolean stateRequired,
            Boolean stateGeoCodeRequired, String postalCodePattern, Boolean postalCodeRequired, Boolean postalCodeGeoCodeRequired,
            Integer postalCodeLength, Integer postalCodeGeoCodeLength, String postalCodeExample, BasePK createdBy) {

        var geoCodeCountry = geoCodeCountryFactory.create(geoCode, telephoneCode, areaCodePattern,
                areaCodeRequired, areaCodeExample, telephoneNumberPattern, telephoneNumberExample, postalAddressFormat,
                cityRequired, cityGeoCodeRequired, stateRequired, stateGeoCodeRequired, postalCodePattern, postalCodeRequired,
                postalCodeGeoCodeRequired, postalCodeLength, postalCodeGeoCodeLength, postalCodeExample, session.getStartTime(),
                Session.MAX_TIME);
        
        sendEvent(geoCode.getPrimaryKey(), EventTypes.MODIFY, geoCodeCountry.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return geoCodeCountry;
    }
    
    private GeoCodeCountry getGeoCodeCountry(GeoCode geoCode, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(GeoCodeCountries.fields())
                .from(GeoCodeCountries)
                .where(GeoCodeCountries.GEO_CODE.eq(geoCode.getPrimaryKey()),
                        GeoCodeCountries.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return geoCodeCountryFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public GeoCodeCountry getGeoCodeCountry(GeoCode geoCode) {
        return getGeoCodeCountry(geoCode, EntityPermission.READ_ONLY);
    }
    
    public GeoCodeCountry getGeoCodeCountryForUpdate(GeoCode geoCode) {
        return getGeoCodeCountry(geoCode, EntityPermission.READ_WRITE);
    }
    
    public GeoCodeCountryValue getGeoCodeCountryValue(GeoCodeCountry geoCodeCountry) {
        return geoCodeCountry == null ? null : geoCodeCountry.getGeoCodeCountryValue().clone();
    }

    public GeoCodeCountryValue getGeoCodeCountryValueForUpdate(GeoCode geoCode) {
        return getGeoCodeCountryValue(getGeoCodeCountryForUpdate(geoCode));
    }
    
    public void updateGeoCodeCountryFromValue(GeoCodeCountryValue geoCodeCountryValue, BasePK updatedBy) {
        if(geoCodeCountryValue.hasBeenModified()) {
            var geoCodeCountry = geoCodeCountryFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     geoCodeCountryValue.getPrimaryKey());
            
            geoCodeCountry.setThruTime(session.getStartTime());
            geoCodeCountry.store();

            var geoCodePK = geoCodeCountry.getGeoCodePK(); // Not Updated
            var telephoneCode = geoCodeCountryValue.getTelephoneCode();
            var areaCodePattern = geoCodeCountryValue.getAreaCodePattern();
            var areaCodeRequired = geoCodeCountryValue.getAreaCodeRequired();
            var areaCodeExample = geoCodeCountryValue.getAreaCodeExample();
            var telephoneNumberPattern = geoCodeCountryValue.getTelephoneNumberPattern();
            var telephoneNumberExample = geoCodeCountryValue.getTelephoneNumberExample();
            var postalAddressFormatPK = geoCodeCountryValue.getPostalAddressFormatPK();
            var cityRequired = geoCodeCountryValue.getCityGeoCodeRequired();
            var cityGeoCodeRequired = geoCodeCountryValue.getCityGeoCodeRequired();
            var stateRequired = geoCodeCountryValue.getStateRequired();
            var stateGeoCodeRequired = geoCodeCountryValue.getStateGeoCodeRequired();
            var postalCodePattern = geoCodeCountryValue.getPostalCodePattern();
            var postalCodeRequired = geoCodeCountryValue.getPostalCodeRequired();
            var postalCodeGeoCodeRequired = geoCodeCountryValue.getPostalCodeGeoCodeRequired();
            var postalCodeLength = geoCodeCountryValue.getPostalCodeLength();
            var postalCodeGeoCodeLength = geoCodeCountryValue.getPostalCodeGeoCodeLength();
            var postalCodeExample = geoCodeCountryValue.getPostalCodeExample();
            
            geoCodeCountry = geoCodeCountryFactory.create(geoCodePK, telephoneCode, areaCodePattern,
                    areaCodeRequired, areaCodeExample, telephoneNumberPattern, telephoneNumberExample, postalAddressFormatPK,
                    cityRequired, cityGeoCodeRequired, stateRequired, stateGeoCodeRequired, postalCodePattern, postalCodeRequired,
                    postalCodeGeoCodeRequired, postalCodeLength, postalCodeGeoCodeLength, postalCodeExample,
                    session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(geoCodePK, EventTypes.MODIFY, geoCodeCountry.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteGeoCodeCountry(GeoCodeCountry geoCodeCountry, BasePK deletedBy) {
        geoCodeCountry.setThruTime(session.getStartTime());
        geoCodeCountry.store();
        
        sendEvent(geoCodeCountry.getGeoCodePK(), EventTypes.MODIFY, geoCodeCountry.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteGeoCodeCountryByGeoCode(GeoCode geoCode, BasePK deletedBy) {
        deleteGeoCodeCountry(getGeoCodeCountryForUpdate(geoCode), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Utilities
    // --------------------------------------------------------------------------------
    
    private GeoCode getGeoCodeByTypeScopeAndAlias(String geoCodeTypeName, String geoCodeScopeName, String alias) {
        GeoCode geoCode = null;
        var geoCodeType = getGeoCodeTypeByName(geoCodeTypeName);
        
        if(geoCodeType != null) {
            var geoCodeScope = getGeoCodeScopeByName(geoCodeScopeName);
            
            if(geoCodeScope != null) {
                geoCode = getGeoCodeByName(alias);
                
                // If we were passed a geoCodeName for the alias, check to see if its valid for the Type and Scope that
                // we're looking for. If its not, clear it, and search using aliases.
                if(geoCode != null) {
                    var geoCodeDetail = geoCode.getLastDetail();
                    
                    if(!geoCodeDetail.getGeoCodeType().equals(geoCodeType) || !geoCodeDetail.getGeoCodeScope().equals(geoCodeScope)) {
                        geoCode = null;
                    }
                }
                
                if(geoCode == null) {
                    var geoCodeAliasType = getDefaultGeoCodeAliasType(geoCodeType);
                    GeoCodeAlias geoCodeAlias = null;
                    
                    if(geoCodeAliasType != null)
                        geoCodeAlias = getGeoCodeAliasByAliasWithinScope(geoCodeScope, geoCodeAliasType, alias);
                    
                    if(geoCodeAlias == null) {
                        var geoCodeAliasTypes = getGeoCodeAliasTypesExceptDefault(geoCodeType);
                        var iter = geoCodeAliasTypes.iterator();
                        
                        while(geoCodeAlias == null && iter.hasNext()) {
                            geoCodeAliasType = iter.next();

                            var getoCodeAliasTypeDetail = geoCodeAliasType.getLastDetail();
                            var validationPattern = getoCodeAliasTypeDetail.getValidationPattern();
                            var patternMatches = false;
                            
                            if(validationPattern != null) {
                                var pattern = Pattern.compile(validationPattern);
                                var m = pattern.matcher(alias);
                                
                                if(m.matches()) {
                                    patternMatches = true;
                                }
                            } else {
                                patternMatches = true;
                            }
                            
                            if(patternMatches) {
                                geoCodeAlias = getGeoCodeAliasByAliasWithinScope(geoCodeScope, geoCodeAliasType, alias);
                            }
                        }
                    }
                    
                    if(geoCodeAlias != null) {
                        geoCode = geoCodeAlias.getGeoCode();
                    }
                }
            }
        }
        
        return geoCode;
    }
    
    /**
     * Attempt to find a GeoCode for the country specified in the alias
     * parameter. First an attempt is made using the default GeoCodeAliasType for the
     * GeoCodeType COUNTRY. If there is no match, then a second attempt is made using
     * all other GeoCodeAliasTypes.
     * @param countryAlias Country's alias
     * @return GeoCode, if a match was found, otherwise null
     */
    public GeoCode getCountryByAlias(String countryAlias) {
        return getGeoCodeByTypeScopeAndAlias(com.echothree.model.control.geo.common.GeoCodeTypes.COUNTRY.name(), com.echothree.model.control.geo.common.GeoCodeScopes.COUNTRIES.name(), countryAlias);
    }
    
    private String getAliasForCountry(GeoCode countryGeoCode, GeoCodeType geoCodeType, GeoCodeAliasType geoCodeAliasType) {
        var geoCodeAlias = getGeoCodeAlias(countryGeoCode, geoCodeAliasType);
        var countryAlias = geoCodeAlias == null? null: geoCodeAlias.getAlias();
        
        if(countryAlias == null) {
            var geoCodeAliasTypes = getGeoCodeAliasTypesExceptDefault(geoCodeType);
            
            for(var loopGeoCodeAliasType : geoCodeAliasTypes) {
                geoCodeAlias = getGeoCodeAlias(countryGeoCode, loopGeoCodeAliasType);
                
                if(geoCodeAlias != null) {
                    countryAlias = geoCodeAlias == null? null: geoCodeAlias.getAlias();
                    break;
                }
            }
        }
        
        return countryAlias;
    }
    
    public String getAliasForCountry(GeoCode countryGeoCode) {
        var geoCodeType = countryGeoCode.getLastDetail().getGeoCodeType();
        var geoCodeAliasType = getDefaultGeoCodeAliasType(geoCodeType);
        
        return getAliasForCountry(countryGeoCode, geoCodeType, geoCodeAliasType);
    }
    
    public GeoCode getPostalCodeByAlias(GeoCode countryGeoCode, String postalCodeAlias) {
        var countryGeoCodeAliasType = getGeoCodeAliasTypeByName(countryGeoCode.getLastDetail().getGeoCodeType(),
                com.echothree.model.control.geo.common.GeoCodeAliasTypes.ISO_2_LETTER.name());
        var countryGeoCodeAlias = getGeoCodeAlias(countryGeoCode, countryGeoCodeAliasType);
        var countryIso2Letter = countryGeoCodeAlias.getAlias();
        var geoCodeScopeName = countryIso2Letter + "_ZIP_CODES";
        
        return getGeoCodeByTypeScopeAndAlias(com.echothree.model.control.geo.common.GeoCodeTypes.ZIP_CODE.name(), geoCodeScopeName, postalCodeAlias);
    }
    
    public String getAliasForPostalCode(GeoCode postalCodeGeoCode) {
        var geoCodeType = postalCodeGeoCode.getLastDetail().getGeoCodeType();
        var geoCodeAliasType = getDefaultGeoCodeAliasType(geoCodeType);
        var geoCodeAlias = getGeoCodeAlias(postalCodeGeoCode, geoCodeAliasType);
        var postalCodeAlias = geoCodeAlias == null? null: geoCodeAlias.getAlias();
        
        if(postalCodeAlias == null) {
            var geoCodeAliasTypes = getGeoCodeAliasTypesExceptDefault(geoCodeType);
            
            for(var loopGeoCodeAliasType : geoCodeAliasTypes) {
                geoCodeAlias = getGeoCodeAlias(postalCodeGeoCode, loopGeoCodeAliasType);
                
                if(geoCodeAlias != null) {
                    postalCodeAlias = geoCodeAlias == null? null: geoCodeAlias.getAlias();
                    break;
                }
            }
        }
        
        return postalCodeAlias;
    }
    
    public GeoCode getStateByAlias(GeoCode countryGeoCode, String stateAlias) {
        var countryGeoCodeAliasType = getGeoCodeAliasTypeByName(countryGeoCode.getLastDetail().getGeoCodeType(),
                com.echothree.model.control.geo.common.GeoCodeAliasTypes.ISO_2_LETTER.name());
        var countryGeoCodeAlias = getGeoCodeAlias(countryGeoCode, countryGeoCodeAliasType);
        var countryIso2Letter = countryGeoCodeAlias.getAlias();
        var geoCodeScopeName = countryIso2Letter + "_STATES";
        
        return getGeoCodeByTypeScopeAndAlias(com.echothree.model.control.geo.common.GeoCodeTypes.STATE.name(), geoCodeScopeName, stateAlias);
    }
    
    public String getAliasForState(GeoCode stateGeoCode) {
        var geoCodeType = stateGeoCode.getLastDetail().getGeoCodeType();
        var geoCodeAliasType = getDefaultGeoCodeAliasType(geoCodeType);
        var geoCodeAlias = getGeoCodeAlias(stateGeoCode, geoCodeAliasType);
        var stateAlias = geoCodeAlias == null? null: geoCodeAlias.getAlias();
        
        if(stateAlias == null) {
            var geoCodeAliasTypes = getGeoCodeAliasTypesExceptDefault(geoCodeType);
            
            for(var loopGeoCodeAliasType : geoCodeAliasTypes) {
                geoCodeAlias = getGeoCodeAlias(stateGeoCode, loopGeoCodeAliasType);
                
                if(geoCodeAlias != null) {
                    stateAlias = geoCodeAlias == null? null: geoCodeAlias.getAlias();
                    break;
                }
            }
        }
        
        return stateAlias;
    }
    
    public GeoCode getCityByAlias(GeoCode stateGeoCode, String cityAlias) {
        var stateGeoCodeAliasType = getGeoCodeAliasTypeByName(stateGeoCode.getLastDetail().getGeoCodeType(),
                com.echothree.model.control.geo.common.GeoCodeAliasTypes.POSTAL_2_LETTER.name());
        var stateGeoCodeAlias = getGeoCodeAlias(stateGeoCode, stateGeoCodeAliasType);
        var statePostal2Letter = stateGeoCodeAlias.getAlias();

        var countryGeoCodeType = getGeoCodeTypeByName(com.echothree.model.control.geo.common.GeoCodeTypes.COUNTRY.name());
        var stateRelationships = getGeoCodeRelationshipsByFromGeoCodeAndGeoCodeType(stateGeoCode, countryGeoCodeType);
        if(stateRelationships.size() != 1) {
            log.error("non-1 stateRelationships.size()");
        }
        var countryGeoCode = stateRelationships.iterator().next().getToGeoCode();

        var countryGeoCodeAliasType = getGeoCodeAliasTypeByName(countryGeoCode.getLastDetail().getGeoCodeType(),
                com.echothree.model.control.geo.common.GeoCodeAliasTypes.ISO_2_LETTER.name());
        var countryGeoCodeAlias = getGeoCodeAlias(countryGeoCode, countryGeoCodeAliasType);
        var countryIso2Letter = countryGeoCodeAlias.getAlias();

        var geoCodeScopeName = countryIso2Letter + "_" + statePostal2Letter + "_CITIES";
        
        return getGeoCodeByTypeScopeAndAlias(com.echothree.model.control.geo.common.GeoCodeTypes.CITY.name(), geoCodeScopeName, cityAlias);
    }
    
    public String getAliasForCity(GeoCode cityGeoCode) {
        var geoCodeType = cityGeoCode.getLastDetail().getGeoCodeType();
        var geoCodeAliasType = getDefaultGeoCodeAliasType(geoCodeType);
        var geoCodeAlias = getGeoCodeAlias(cityGeoCode, geoCodeAliasType);
        var cityAlias = geoCodeAlias == null? null: geoCodeAlias.getAlias();
        
        if(cityAlias == null) {
            var geoCodeAliasTypes = getGeoCodeAliasTypesExceptDefault(geoCodeType);
            
            for(var loopGeoCodeAliasType : geoCodeAliasTypes) {
                geoCodeAlias = getGeoCodeAlias(cityGeoCode, loopGeoCodeAliasType);
                
                if(geoCodeAlias != null) {
                    cityAlias = geoCodeAlias == null? null: geoCodeAlias.getAlias();
                    break;
                }
            }
        }
        
        return cityAlias;
    }
    
}
