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

package com.echothree.model.control.geo.server.control;

import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.geo.common.GeoConstants;
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
import com.echothree.model.control.geo.server.transfer.GeoTransferCaches;
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
import com.echothree.util.common.exception.PersistenceDatabaseException;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;

public class GeoControl
        extends BaseModelControl {
    
    /** Creates a new instance of GeoControl */
    public GeoControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Geo Transfer Caches
    // --------------------------------------------------------------------------------
    
    private GeoTransferCaches geoTransferCaches;
    
    public GeoTransferCaches getGeoTransferCaches(UserVisit userVisit) {
        if(geoTransferCaches == null) {
            geoTransferCaches = new GeoTransferCaches(userVisit, this);
        }
        
        return geoTransferCaches;
    }
    
    // --------------------------------------------------------------------------------
    //   Transfer Object Utilities
    // --------------------------------------------------------------------------------
    
    public CountryTransfer getCountryTransfer(UserVisit userVisit, GeoCode geoCode) {
        return getGeoTransferCaches(userVisit).getCountryTransferCache().getCountryTransfer(geoCode);
    }
    
    public List<CountryTransfer> getCountryTransfers(UserVisit userVisit, Collection<GeoCode> geoCodes) {
        var countryTransfers = new ArrayList<CountryTransfer>(geoCodes.size());
        var countryTransferCache = getGeoTransferCaches(userVisit).getCountryTransferCache();
        
        geoCodes.forEach((geoCode) ->
                countryTransfers.add(countryTransferCache.getCountryTransfer(geoCode))
        );
        
        return countryTransfers;
    }
    
    public List<GeoCode> getCountries() {
        var geoCodeScope = getGeoCodeScopeByName(GeoConstants.GeoCodeScope_COUNTRIES);
        
        return getGeoCodesByGeoCodeScope(geoCodeScope);
    }

    public StateTransfer getStateTransfer(UserVisit userVisit, GeoCode geoCode) {
        return getGeoTransferCaches(userVisit).getStateTransferCache().getStateTransfer(geoCode);
    }
    
    public List<StateTransfer> getStateTransfers(UserVisit userVisit, Collection<GeoCode> geoCodes) {
        var stateTransfers = new ArrayList<StateTransfer>(geoCodes.size());
        var stateTransferCache = getGeoTransferCaches(userVisit).getStateTransferCache();
        
        geoCodes.forEach((geoCode) ->
                stateTransfers.add(stateTransferCache.getStateTransfer(geoCode))
        );
        
        return stateTransfers;
    }
    
    public List<GeoCode> getStatesByCountry(GeoCode countryGeoCode) {
        var countryGeoCodeAliasType = getGeoCodeAliasTypeByName(countryGeoCode.getLastDetail().getGeoCodeType(), GeoConstants.GeoCodeAliasType_ISO_2_LETTER);
        var countryGeoCodeAlias = getGeoCodeAlias(countryGeoCode, countryGeoCodeAliasType);
        var countryIso2Letter = countryGeoCodeAlias.getAlias();
        var geoCodeScopeName = countryIso2Letter + "_STATES";
        var geoCodeScope = getGeoCodeScopeByName(geoCodeScopeName);
        List<GeoCode> states;

        if(geoCodeScope != null) {
            states = getGeoCodesByGeoCodeScope(geoCodeScope);
        } else {
            states = new ArrayList<>();
        }

        return states;
    }

    public CountyTransfer getCountyTransfer(UserVisit userVisit, GeoCode geoCode) {
        return getGeoTransferCaches(userVisit).getCountyTransferCache().getCountyTransfer(geoCode);
    }
    
    public List<CountyTransfer> getCountyTransfers(UserVisit userVisit, Collection<GeoCode> geoCodes) {
        var countyTransfers = new ArrayList<CountyTransfer>(geoCodes.size());
        var countyTransferCache = getGeoTransferCaches(userVisit).getCountyTransferCache();
        
        geoCodes.forEach((geoCode) ->
                countyTransfers.add(countyTransferCache.getCountyTransfer(geoCode))
        );
        
        return countyTransfers;
    }
    
    public List<GeoCode> getCountiesByState(GeoCode stateGeoCode) {
        var stateGeoCodeAliasType = getGeoCodeAliasTypeByName(stateGeoCode.getLastDetail().getGeoCodeType(), GeoConstants.GeoCodeAliasType_POSTAL_2_LETTER);
        var stateGeoCodeAlias = getGeoCodeAlias(stateGeoCode, stateGeoCodeAliasType);
        var statePostal2Letter = stateGeoCodeAlias.getAlias();
        
        var countryGeoCodeType = getGeoCodeTypeByName(GeoConstants.GeoCodeType_COUNTRY);
        var stateRelationships = getGeoCodeRelationshipsByFromGeoCode(stateGeoCode);
        GeoCode countryGeoCode = null;
        for(var geoCodeRelationship : stateRelationships) {
            var toGeoCode = geoCodeRelationship.getToGeoCode();
            
            if(toGeoCode.getLastDetail().getGeoCodeType().equals(countryGeoCodeType)) {
                countryGeoCode = toGeoCode;
                break;
            }
        }
        
        var countryGeoCodeAliasType = getGeoCodeAliasTypeByName(countryGeoCode.getLastDetail().getGeoCodeType(), GeoConstants.GeoCodeAliasType_ISO_2_LETTER);
        var countryGeoCodeAlias = getGeoCodeAlias(countryGeoCode, countryGeoCodeAliasType);
        var countryIso2Letter = countryGeoCodeAlias.getAlias();
        
        var geoCodeScopeName = countryIso2Letter + "_" + statePostal2Letter + "_COUNTIES";
        var geoCodeScope = getGeoCodeScopeByName(geoCodeScopeName);
        List<GeoCode> counties;
        
        if(geoCodeScope != null) {
            counties = getGeoCodesByGeoCodeScope(geoCodeScope);
        } else {
            counties = new ArrayList<>();
        }
        
        return counties;
    }
    
    public CityTransfer getCityTransfer(UserVisit userVisit, GeoCode geoCode) {
        return getGeoTransferCaches(userVisit).getCityTransferCache().getCityTransfer(geoCode);
    }
    
    public List<CityTransfer> getCityTransfers(UserVisit userVisit, Collection<GeoCode> geoCodes) {
        var cityTransfers = new ArrayList<CityTransfer>(geoCodes.size());
        var cityTransferCache = getGeoTransferCaches(userVisit).getCityTransferCache();
        
        geoCodes.forEach((geoCode) ->
                cityTransfers.add(cityTransferCache.getCityTransfer(geoCode))
        );
        
        return cityTransfers;
    }
    
    public List<GeoCode> getCitiesByState(GeoCode stateGeoCode) {
        var stateGeoCodeAliasType = getGeoCodeAliasTypeByName(stateGeoCode.getLastDetail().getGeoCodeType(), GeoConstants.GeoCodeAliasType_POSTAL_2_LETTER);
        var stateGeoCodeAlias = getGeoCodeAlias(stateGeoCode, stateGeoCodeAliasType);
        var statePostal2Letter = stateGeoCodeAlias.getAlias();
        
        var countryGeoCodeType = getGeoCodeTypeByName(GeoConstants.GeoCodeType_COUNTRY);
        var stateRelationships = getGeoCodeRelationshipsByFromGeoCode(stateGeoCode);
        GeoCode countryGeoCode = null;
        for(var geoCodeRelationship : stateRelationships) {
            var toGeoCode = geoCodeRelationship.getToGeoCode();
            
            if(toGeoCode.getLastDetail().getGeoCodeType().equals(countryGeoCodeType)) {
                countryGeoCode = toGeoCode;
                break;
            }
        }
        
        var countryGeoCodeAliasType = getGeoCodeAliasTypeByName(countryGeoCode.getLastDetail().getGeoCodeType(), GeoConstants.GeoCodeAliasType_ISO_2_LETTER);
        var countryGeoCodeAlias = getGeoCodeAlias(countryGeoCode, countryGeoCodeAliasType);
        var countryIso2Letter = countryGeoCodeAlias.getAlias();
        
        var geoCodeScopeName = countryIso2Letter + "_" + statePostal2Letter + "_CITIES";
        var geoCodeScope = getGeoCodeScopeByName(geoCodeScopeName);
        List<GeoCode> cities;
        
        if(geoCodeScope != null) {
            cities = getGeoCodesByGeoCodeScope(geoCodeScope);
        } else {
            cities = new ArrayList<>();
        }
        
        return cities;
    }
    
    public PostalCodeTransfer getPostalCodeTransfer(UserVisit userVisit, GeoCode geoCode) {
        return getGeoTransferCaches(userVisit).getPostalCodeTransferCache().getPostalCodeTransfer(geoCode);
    }
    
    public List<PostalCodeTransfer> getPostalCodeTransfers(UserVisit userVisit, Collection<GeoCode> geoCodes) {
        var postalCodeTransfers = new ArrayList<PostalCodeTransfer>(geoCodes.size());
        var postalCodeTransferCache = getGeoTransferCaches(userVisit).getPostalCodeTransferCache();
        
        geoCodes.forEach((geoCode) ->
                postalCodeTransfers.add(postalCodeTransferCache.getPostalCodeTransfer(geoCode))
        );
        
        return postalCodeTransfers;
    }
    
    public List<GeoCode> getPostalCodesByCountry(GeoCode countryGeoCode) {
        var countryGeoCodeAliasType = getGeoCodeAliasTypeByName(countryGeoCode.getLastDetail().getGeoCodeType(), GeoConstants.GeoCodeAliasType_ISO_2_LETTER);
        var countryGeoCodeAlias = getGeoCodeAlias(countryGeoCode, countryGeoCodeAliasType);
        var countryIso2Letter = countryGeoCodeAlias.getAlias();
        var geoCodeScopeName = countryIso2Letter + "_ZIP_CODES";
        var geoCodeScope = getGeoCodeScopeByName(geoCodeScopeName);
        List<GeoCode> postalCodeTransfers;

        if(geoCodeScope != null) {
            postalCodeTransfers = getGeoCodesByGeoCodeScope(geoCodeScope);
        } else {
            postalCodeTransfers = new ArrayList<>();
        }

        return postalCodeTransfers;
    }

    // --------------------------------------------------------------------------------
    //   Choices Utilities
    // --------------------------------------------------------------------------------
    
    public CountryChoicesBean getCountryChoices(String defaultCountryChoice, Language language, boolean allowNullChoice) {
        var geoCodeType = getGeoCodeTypeByName(GeoConstants.GeoCodeType_COUNTRY);
        var geoCodeAliasType = getDefaultGeoCodeAliasType(geoCodeType);
        var geoCodeScope = getGeoCodeScopeByName(GeoConstants.GeoCodeScope_COUNTRIES);
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

        var geoCodeType = GeoCodeTypeFactory.getInstance().create();
        var geoCodeTypeDetail = GeoCodeTypeDetailFactory.getInstance().create(geoCodeType, geoCodeTypeName,
                parentGeoCodeType, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        geoCodeType = GeoCodeTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                geoCodeType.getPrimaryKey());
        geoCodeType.setActiveDetail(geoCodeTypeDetail);
        geoCodeType.setLastDetail(geoCodeTypeDetail);
        geoCodeType.store();
        
        sendEvent(geoCodeType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return geoCodeType;
    }

    public long countGeoCodeTypes() {
        return session.queryForLong("""
                SELECT COUNT(*)
                FROM geocodetypes, geocodetypedetails
                WHERE geot_activedetailid = geotdt_geocodetypedetailid
                """);
    }

    public long countGeoCodeTypesByParentGeoCodeType(GeoCodeType parentGeoCodeType) {
        return session.queryForLong("""
                SELECT COUNT(*)
                FROM geocodetypes, geocodetypedetails
                WHERE geot_activedetailid = geotdt_geocodetypedetailid
                AND geotdt_parentgeocodetypeid = ?
                """, parentGeoCodeType);
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.GeoCodeType */
    public GeoCodeType getGeoCodeTypeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new GeoCodeTypePK(entityInstance.getEntityUniqueId());

        return GeoCodeTypeFactory.getInstance().getEntityFromPK(entityPermission, pk);
    }

    public GeoCodeType getGeoCodeTypeByEntityInstance(EntityInstance entityInstance) {
        return getGeoCodeTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    private static final Map<EntityPermission, String> getGeoCodeTypeByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM geocodetypes, geocodetypedetails " +
                "WHERE geot_activedetailid = geotdt_geocodetypedetailid " +
                "AND geotdt_geocodetypename = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM geocodetypes, geocodetypedetails " +
                "WHERE geot_activedetailid = geotdt_geocodetypedetailid " +
                "AND geotdt_geocodetypename = ? " +
                "FOR UPDATE");
        getGeoCodeTypeByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    public GeoCodeType getGeoCodeTypeByName(String geoCodeTypeName, EntityPermission entityPermission) {
        return GeoCodeTypeFactory.getInstance().getEntityFromQuery(entityPermission, getGeoCodeTypeByNameQueries, geoCodeTypeName);
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

    private static final Map<EntityPermission, String> getDefaultGeoCodeTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM geocodetypes, geocodetypedetails " +
                "WHERE geot_activedetailid = geotdt_geocodetypedetailid " +
                "AND geotdt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM geocodetypes, geocodetypedetails " +
                "WHERE geot_activedetailid = geotdt_geocodetypedetailid " +
                "AND geotdt_isdefault = 1 " +
                "FOR UPDATE");
        getDefaultGeoCodeTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    public GeoCodeType getDefaultGeoCodeType(EntityPermission entityPermission) {
        return GeoCodeTypeFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultGeoCodeTypeQueries);
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

    private static final Map<EntityPermission, String> getGeoCodeTypesQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM geocodetypes, geocodetypedetails " +
                "WHERE geot_activedetailid = geotdt_geocodetypedetailid " +
                "ORDER BY geotdt_sortorder, geotdt_geocodetypename " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM geocodetypes, geocodetypedetails " +
                "WHERE geot_activedetailid = geotdt_geocodetypedetailid " +
                "FOR UPDATE");
        getGeoCodeTypesQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<GeoCodeType> getGeoCodeTypes(EntityPermission entityPermission) {
        return GeoCodeTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, getGeoCodeTypesQueries);
    }

    public List<GeoCodeType> getGeoCodeTypes() {
        return getGeoCodeTypes(EntityPermission.READ_ONLY);
    }

    public List<GeoCodeType> getGeoCodeTypesForUpdate() {
        return getGeoCodeTypes(EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getGeoCodeTypesByParentGeoCodeTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM geocodetypes, geocodetypedetails " +
                "WHERE geot_activedetailid = geotdt_geocodetypedetailid AND geotdt_parentgeocodetypeid = ? " +
                "ORDER BY geotdt_sortorder, geotdt_geocodetypename " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM geocodetypes, geocodetypedetails " +
                "WHERE geot_activedetailid = geotdt_geocodetypedetailid AND geotdt_parentgeocodetypeid = ? " +
                "FOR UPDATE");
        getGeoCodeTypesByParentGeoCodeTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<GeoCodeType> getGeoCodeTypesByParentGeoCodeType(GeoCodeType parentGeoCodeType,
            EntityPermission entityPermission) {
        return GeoCodeTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, getGeoCodeTypesByParentGeoCodeTypeQueries,
                parentGeoCodeType);
    }

    public List<GeoCodeType> getGeoCodeTypesByParentGeoCodeType(GeoCodeType parentGeoCodeType) {
        return getGeoCodeTypesByParentGeoCodeType(parentGeoCodeType, EntityPermission.READ_ONLY);
    }

    public List<GeoCodeType> getGeoCodeTypesByParentGeoCodeTypeForUpdate(GeoCodeType parentGeoCodeType) {
        return getGeoCodeTypesByParentGeoCodeType(parentGeoCodeType, EntityPermission.READ_WRITE);
    }

    public GeoCodeTypeTransfer getGeoCodeTypeTransfer(UserVisit userVisit, GeoCodeType geoCodeType) {
        return getGeoTransferCaches(userVisit).getGeoCodeTypeTransferCache().getGeoCodeTypeTransfer(geoCodeType);
    }

    public List<GeoCodeTypeTransfer> getGeoCodeTypeTransfers(UserVisit userVisit, Collection<GeoCodeType> geoCodeTypes) {
        List<GeoCodeTypeTransfer> geoCodeTypeTransfers = new ArrayList<>(geoCodeTypes.size());
        var geoCodeTypeTransferCache = getGeoTransferCaches(userVisit).getGeoCodeTypeTransferCache();

        geoCodeTypes.forEach((geoCodeType) ->
                geoCodeTypeTransfers.add(geoCodeTypeTransferCache.getGeoCodeTypeTransfer(geoCodeType))
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
            var geoCodeType = GeoCodeTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     geoCodeTypeDetailValue.getGeoCodeTypePK());
            var geoCodeTypeDetail = geoCodeType.getActiveDetailForUpdate();
            
            geoCodeTypeDetail.setThruTime(session.START_TIME_LONG);
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
            
            geoCodeTypeDetail = GeoCodeTypeDetailFactory.getInstance().create(geoCodeTypePK,
                    geoCodeTypeName, parentGeoCodeTypePK, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
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
        
        geoCodeTypeDetail.setThruTime(session.START_TIME_LONG);
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
    
    public GeoCodeTypeDescription createGeoCodeTypeDescription(GeoCodeType geoCodeType, Language language, String description, BasePK createdBy) {
        var geoCodeTypeDescription = GeoCodeTypeDescriptionFactory.getInstance().create(geoCodeType,
                language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(geoCodeType.getPrimaryKey(), EventTypes.MODIFY, geoCodeTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return geoCodeTypeDescription;
    }
    
    private GeoCodeTypeDescription getGeoCodeTypeDescription(GeoCodeType geoCodeType, Language language, EntityPermission entityPermission) {
        GeoCodeTypeDescription geoCodeTypeDescription;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM geocodetypedescriptions " +
                        "WHERE geotd_geot_geocodetypeid = ? AND geotd_lang_languageid = ? AND geotd_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM geocodetypedescriptions " +
                        "WHERE geotd_geot_geocodetypeid = ? AND geotd_lang_languageid = ? AND geotd_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = GeoCodeTypeDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, geoCodeType.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            geoCodeTypeDescription = GeoCodeTypeDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return geoCodeTypeDescription;
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
        List<GeoCodeTypeDescription> geoCodeTypeDescriptions;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM geocodetypedescriptions, languages " +
                        "WHERE geotd_geot_geocodetypeid = ? AND geotd_thrutime = ? AND geotd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM geocodetypedescriptions " +
                        "WHERE geotd_geot_geocodetypeid = ? AND geotd_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = GeoCodeTypeDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, geoCodeType.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            geoCodeTypeDescriptions = GeoCodeTypeDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return geoCodeTypeDescriptions;
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
            geoCodeTypeDescription = getGeoCodeTypeDescription(geoCodeType, getPartyControl().getDefaultLanguage());
        }
        
        if(geoCodeTypeDescription == null) {
            description = geoCodeType.getLastDetail().getGeoCodeTypeName();
        } else {
            description = geoCodeTypeDescription.getDescription();
        }
        
        return description;
    }
    
    public GeoCodeTypeDescriptionTransfer getGeoCodeTypeDescriptionTransfer(UserVisit userVisit, GeoCodeTypeDescription geoCodeTypeDescription) {
        return getGeoTransferCaches(userVisit).getGeoCodeTypeDescriptionTransferCache().getGeoCodeTypeDescriptionTransfer(geoCodeTypeDescription);
    }
    
    public List<GeoCodeTypeDescriptionTransfer> getGeoCodeTypeDescriptionTransfers(UserVisit userVisit, GeoCodeType geoCodeType) {
        var geoCodeTypeDescriptions = getGeoCodeTypeDescriptionsByGeoCodeType(geoCodeType);
        List<GeoCodeTypeDescriptionTransfer> geoCodeTypeDescriptionTransfers = new ArrayList<>(geoCodeTypeDescriptions.size());

        geoCodeTypeDescriptions.forEach((geoCodeTypeDescription) -> {
            geoCodeTypeDescriptionTransfers.add(getGeoTransferCaches(userVisit).getGeoCodeTypeDescriptionTransferCache().getGeoCodeTypeDescriptionTransfer(geoCodeTypeDescription));
        });

        return geoCodeTypeDescriptionTransfers;
    }
    
    public void updateGeoCodeTypeDescriptionFromValue(GeoCodeTypeDescriptionValue geoCodeTypeDescriptionValue, BasePK updatedBy) {
        if(geoCodeTypeDescriptionValue.hasBeenModified()) {
            var geoCodeTypeDescription = GeoCodeTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, geoCodeTypeDescriptionValue.getPrimaryKey());
            
            geoCodeTypeDescription.setThruTime(session.START_TIME_LONG);
            geoCodeTypeDescription.store();

            var geoCodeType = geoCodeTypeDescription.getGeoCodeType();
            var language = geoCodeTypeDescription.getLanguage();
            var description = geoCodeTypeDescriptionValue.getDescription();
            
            geoCodeTypeDescription = GeoCodeTypeDescriptionFactory.getInstance().create(geoCodeType, language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(geoCodeType.getPrimaryKey(), EventTypes.MODIFY, geoCodeTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteGeoCodeTypeDescription(GeoCodeTypeDescription geoCodeTypeDescription, BasePK deletedBy) {
        geoCodeTypeDescription.setThruTime(session.START_TIME_LONG);
        
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

        var geoCodeScope = GeoCodeScopeFactory.getInstance().create();
        var geoCodeScopeDetail = GeoCodeScopeDetailFactory.getInstance().create(session,
                geoCodeScope, geoCodeScopeName, isDefault, sortOrder, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
        // Convert to R/W
        geoCodeScope = GeoCodeScopeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                geoCodeScope.getPrimaryKey());
        geoCodeScope.setActiveDetail(geoCodeScopeDetail);
        geoCodeScope.setLastDetail(geoCodeScopeDetail);
        geoCodeScope.store();
        
        sendEvent(geoCodeScope.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return geoCodeScope;
    }

    public long countGeoCodeScopes() {
        return session.queryForLong("""
                SELECT COUNT(*)
                FROM geocodescopes, geocodescopedetails
                WHERE geos_activedetailid = geosdt_geocodescopedetailid
                """);
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.GeoCodeScope */
    public GeoCodeScope getGeoCodeScopeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new GeoCodeScopePK(entityInstance.getEntityUniqueId());

        return GeoCodeScopeFactory.getInstance().getEntityFromPK(entityPermission, pk);
    }

    public GeoCodeScope getGeoCodeScopeByEntityInstance(EntityInstance entityInstance) {
        return getGeoCodeScopeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public GeoCodeScope getGeoCodeScopeByName(String geoCodeScopeName, EntityPermission entityPermission) {
        GeoCodeScope geoCodeScope;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM geocodescopes, geocodescopedetails " +
                        "WHERE geos_activedetailid = geosdt_geocodescopedetailid AND geosdt_geocodescopename = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM geocodescopes, geocodescopedetails " +
                        "WHERE geos_activedetailid = geosdt_geocodescopedetailid AND geosdt_geocodescopename = ? " +
                        "FOR UPDATE";
            }

            var ps = GeoCodeScopeFactory.getInstance().prepareStatement(query);
            
            ps.setString(1, geoCodeScopeName);
            
            geoCodeScope = GeoCodeScopeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return geoCodeScope;
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
        String query = null;
        
        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM geocodescopes, geocodescopedetails " +
                    "WHERE geos_activedetailid = geosdt_geocodescopedetailid AND geosdt_isdefault = 1";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM geocodescopes, geocodescopedetails " +
                    "WHERE geos_activedetailid = geosdt_geocodescopedetailid AND geosdt_isdefault = 1 " +
                    "FOR UPDATE";
        }

        var ps = GeoCodeScopeFactory.getInstance().prepareStatement(query);
        
        return GeoCodeScopeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
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
        String query = null;
        
        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM geocodescopes, geocodescopedetails " +
                    "WHERE geos_activedetailid = geosdt_geocodescopedetailid " +
                    "ORDER BY geosdt_sortorder, geosdt_geocodescopename";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM geocodescopes, geocodescopedetails " +
                    "WHERE geos_activedetailid = geosdt_geocodescopedetailid " +
                    "FOR UPDATE";
        }

        var ps = GeoCodeScopeFactory.getInstance().prepareStatement(query);
        
        return GeoCodeScopeFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
    }
    
    public List<GeoCodeScope> getGeoCodeScopes() {
        return getGeoCodeScopes(EntityPermission.READ_ONLY);
    }
    
    public List<GeoCodeScope> getGeoCodeScopesForUpdate() {
        return getGeoCodeScopes(EntityPermission.READ_WRITE);
    }
    
    public GeoCodeScopeTransfer getGeoCodeScopeTransfer(UserVisit userVisit, GeoCodeScope geoCodeScope) {
        return getGeoTransferCaches(userVisit).getGeoCodeScopeTransferCache().getGeoCodeScopeTransfer(geoCodeScope);
    }

    public List<GeoCodeScopeTransfer> getGeoCodeScopeTransfers(UserVisit userVisit, Collection<GeoCodeScope> geoCodeScopes) {
        List<GeoCodeScopeTransfer> geoCodeScopeTransfers = new ArrayList<>(geoCodeScopes.size());
        var geoCodeScopeTransferCache = getGeoTransferCaches(userVisit).getGeoCodeScopeTransferCache();

        geoCodeScopes.forEach((geoCodeScope) ->
                geoCodeScopeTransfers.add(geoCodeScopeTransferCache.getGeoCodeScopeTransfer(geoCodeScope))
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
            var geoCodeScope = GeoCodeScopeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     geoCodeScopeDetailValue.getGeoCodeScopePK());
            var geoCodeScopeDetail = geoCodeScope.getActiveDetailForUpdate();
            
            geoCodeScopeDetail.setThruTime(session.START_TIME_LONG);
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
            
            geoCodeScopeDetail = GeoCodeScopeDetailFactory.getInstance().create(geoCodeScopePK, geoCodeScopeName,
                    isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
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
        geoCodeScopeDetail.setThruTime(session.START_TIME_LONG);
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
    
    public GeoCodeScopeDescription createGeoCodeScopeDescription(GeoCodeScope geoCodeScope, Language language, String description, BasePK createdBy) {
        var geoCodeScopeDescription = GeoCodeScopeDescriptionFactory.getInstance().create(geoCodeScope, language, description, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
        sendEvent(geoCodeScope.getPrimaryKey(), EventTypes.MODIFY, geoCodeScopeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return geoCodeScopeDescription;
    }
    
    private GeoCodeScopeDescription getGeoCodeScopeDescription(GeoCodeScope geoCodeScope, Language language, EntityPermission entityPermission) {
        GeoCodeScopeDescription geoCodeScopeDescription;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM geocodescopedescriptions " +
                        "WHERE geosd_geos_geocodescopeid = ? AND geosd_lang_languageid = ? AND geosd_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM geocodescopedescriptions " +
                        "WHERE geosd_geos_geocodescopeid = ? AND geosd_lang_languageid = ? AND geosd_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = GeoCodeScopeDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, geoCodeScope.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            geoCodeScopeDescription = GeoCodeScopeDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return geoCodeScopeDescription;
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
        List<GeoCodeScopeDescription> geoCodeScopeDescriptions;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM geocodescopedescriptions, languages " +
                        "WHERE geosd_geos_geocodescopeid = ? AND geosd_thrutime = ? AND geosd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM geocodescopedescriptions " +
                        "WHERE geosd_geos_geocodescopeid = ? AND geosd_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = GeoCodeScopeDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, geoCodeScope.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            geoCodeScopeDescriptions = GeoCodeScopeDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return geoCodeScopeDescriptions;
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
            geoCodeScopeDescription = getGeoCodeScopeDescription(geoCodeScope, getPartyControl().getDefaultLanguage());
        }
        
        if(geoCodeScopeDescription == null) {
            description = geoCodeScope.getLastDetail().getGeoCodeScopeName();
        } else {
            description = geoCodeScopeDescription.getDescription();
        }
        
        return description;
    }
    
    public GeoCodeScopeDescriptionTransfer getGeoCodeScopeDescriptionTransfer(UserVisit userVisit, GeoCodeScopeDescription geoCodeScopeDescription) {
        return getGeoTransferCaches(userVisit).getGeoCodeScopeDescriptionTransferCache().getGeoCodeScopeDescriptionTransfer(geoCodeScopeDescription);
    }
    
    public List<GeoCodeScopeDescriptionTransfer> getGeoCodeScopeDescriptionTransfers(UserVisit userVisit, GeoCodeScope geoCodeScope) {
        var geoCodeScopeDescriptions = getGeoCodeScopeDescriptionsByGeoCodeScope(geoCodeScope);
        List<GeoCodeScopeDescriptionTransfer> geoCodeScopeDescriptionTransfers = new ArrayList<>(geoCodeScopeDescriptions.size());

        geoCodeScopeDescriptions.forEach((geoCodeScopeDescription) -> {
            geoCodeScopeDescriptionTransfers.add(getGeoTransferCaches(userVisit).getGeoCodeScopeDescriptionTransferCache().getGeoCodeScopeDescriptionTransfer(geoCodeScopeDescription));
        });

        return geoCodeScopeDescriptionTransfers;
    }
    
    public void updateGeoCodeScopeDescriptionFromValue(GeoCodeScopeDescriptionValue geoCodeScopeDescriptionValue, BasePK updatedBy) {
        if(geoCodeScopeDescriptionValue.hasBeenModified()) {
            var geoCodeScopeDescription = GeoCodeScopeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, geoCodeScopeDescriptionValue.getPrimaryKey());
            
            geoCodeScopeDescription.setThruTime(session.START_TIME_LONG);
            geoCodeScopeDescription.store();

            var geoCodeScope = geoCodeScopeDescription.getGeoCodeScope();
            var language = geoCodeScopeDescription.getLanguage();
            var description = geoCodeScopeDescriptionValue.getDescription();
            
            geoCodeScopeDescription = GeoCodeScopeDescriptionFactory.getInstance().create(geoCodeScope, language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(geoCodeScope.getPrimaryKey(), EventTypes.MODIFY, geoCodeScopeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteGeoCodeScopeDescription(GeoCodeScopeDescription geoCodeScopeDescription, BasePK deletedBy) {
        geoCodeScopeDescription.setThruTime(session.START_TIME_LONG);
        
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

        var geoCodeAliasType = GeoCodeAliasTypeFactory.getInstance().create();
        var geoCodeAliasTypeDetail = GeoCodeAliasTypeDetailFactory.getInstance().create(session, geoCodeAliasType, geoCodeType,
                geoCodeAliasTypeName, validationPattern, isRequired, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        geoCodeAliasType = GeoCodeAliasTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                geoCodeAliasType.getPrimaryKey());
        geoCodeAliasType.setActiveDetail(geoCodeAliasTypeDetail);
        geoCodeAliasType.setLastDetail(geoCodeAliasTypeDetail);
        geoCodeAliasType.store();
        
        sendEvent(geoCodeAliasType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return geoCodeAliasType;
    }
    
    private GeoCodeAliasType getGeoCodeAliasTypeByName(GeoCodeType geoCodeType, String geoCodeAliasTypeName, EntityPermission entityPermission) {
        GeoCodeAliasType geoCodeAliasType;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM geocodealiastypes, geocodealiastypedetails " +
                        "WHERE geoat_activedetailid = geoatdt_geocodealiastypedetailid AND geoatdt_geot_geocodetypeid = ? " +
                        "AND geoatdt_geocodealiastypename = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM geocodealiastypes, geocodealiastypedetails " +
                        "WHERE geoat_activedetailid = geoatdt_geocodealiastypedetailid AND geoatdt_geot_geocodetypeid = ? " +
                        "AND geoatdt_geocodealiastypename = ? " +
                        "FOR UPDATE";
            }

            var ps = GeoCodeAliasTypeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, geoCodeType.getPrimaryKey().getEntityId());
            ps.setString(2, geoCodeAliasTypeName);
            
            geoCodeAliasType = GeoCodeAliasTypeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return geoCodeAliasType;
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
        GeoCodeAliasType geoCodeAliasType;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM geocodealiastypes, geocodealiastypedetails " +
                        "WHERE geoat_activedetailid = geoatdt_geocodealiastypedetailid AND geoatdt_geot_geocodetypeid = ? " +
                        "AND geoatdt_isdefault = 1";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM geocodealiastypes, geocodealiastypedetails " +
                        "WHERE geoat_activedetailid = geoatdt_geocodealiastypedetailid AND geoatdt_geot_geocodetypeid = ? " +
                        "AND geoatdt_isdefault = 1 " +
                        "FOR UPDATE";
            }

            var ps = GeoCodeAliasTypeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, geoCodeType.getPrimaryKey().getEntityId());
            
            geoCodeAliasType = GeoCodeAliasTypeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return geoCodeAliasType;
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
        List<GeoCodeAliasType> geoCodeAliasTypes;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM geocodealiastypes, geocodealiastypedetails " +
                        "WHERE geoat_activedetailid = geoatdt_geocodealiastypedetailid AND geoatdt_geot_geocodetypeid = ? " +
                        "ORDER BY geoatdt_sortorder, geoatdt_geocodealiastypename";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM geocodealiastypes, geocodealiastypedetails " +
                        "WHERE geoat_activedetailid = geoatdt_geocodealiastypedetailid AND geoatdt_geot_geocodetypeid = ? " +
                        "FOR UPDATE";
            }

            var ps = GeoCodeAliasTypeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, geoCodeType.getPrimaryKey().getEntityId());
            
            geoCodeAliasTypes = GeoCodeAliasTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return geoCodeAliasTypes;
    }
    
    public List<GeoCodeAliasType> getGeoCodeAliasTypes(GeoCodeType geoCodeType) {
        return getGeoCodeAliasTypes(geoCodeType, EntityPermission.READ_ONLY);
    }
    
    public List<GeoCodeAliasType> getGeoCodeAliasTypesForUpdate(GeoCodeType geoCodeType) {
        return getGeoCodeAliasTypes(geoCodeType, EntityPermission.READ_WRITE);
    }
    
    private List<GeoCodeAliasType> getGeoCodeAliasTypesExceptDefault(GeoCodeType geoCodeType, EntityPermission entityPermission) {
        List<GeoCodeAliasType> geoCodeAliasTypes;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM geocodealiastypes, geocodealiastypedetails " +
                        "WHERE geoat_activedetailid = geoatdt_geocodealiastypedetailid AND geoatdt_geot_geocodetypeid = ? " +
                        "AND geoatdt_isdefault = 0 " +
                        "ORDER BY geoatdt_sortorder, geoatdt_geocodealiastypename";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM geocodealiastypes, geocodealiastypedetails " +
                        "WHERE geoat_activedetailid = geoatdt_geocodealiastypedetailid AND geoatdt_geot_geocodetypeid = ? " +
                        "AND geoatdt_isdefault = 0 " +
                        "FOR UPDATE";
            }

            var ps = GeoCodeAliasTypeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, geoCodeType.getPrimaryKey().getEntityId());
            
            geoCodeAliasTypes = GeoCodeAliasTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return geoCodeAliasTypes;
    }
    
    public List<GeoCodeAliasType> getGeoCodeAliasTypesExceptDefault(GeoCodeType geoCodeType) {
        return getGeoCodeAliasTypesExceptDefault(geoCodeType, EntityPermission.READ_ONLY);
    }
    
    public List<GeoCodeAliasType> getGeoCodeAliasTypesExceptDefaultForUpdate(GeoCodeType geoCodeType) {
        return getGeoCodeAliasTypesExceptDefault(geoCodeType, EntityPermission.READ_WRITE);
    }
    
    public GeoCodeAliasTypeTransfer getGeoCodeAliasTypeTransfer(UserVisit userVisit, GeoCodeAliasType geoCodeAliasType) {
        return getGeoTransferCaches(userVisit).getGeoCodeAliasTypeTransferCache().getGeoCodeAliasTypeTransfer(geoCodeAliasType);
    }
    
    public List<GeoCodeAliasTypeTransfer> getGeoCodeAliasTypeTransfers(UserVisit userVisit, GeoCodeType geoCodeType) {
        var geoCodeAliasTypes = getGeoCodeAliasTypes(geoCodeType);
        List<GeoCodeAliasTypeTransfer> geoCodeAliasTypeTransfers = new ArrayList<>(geoCodeAliasTypes.size());
        var geoCodeAliasTypeTransferCache = getGeoTransferCaches(userVisit).getGeoCodeAliasTypeTransferCache();
        
        geoCodeAliasTypes.forEach((geoCodeAliasType) ->
                geoCodeAliasTypeTransfers.add(geoCodeAliasTypeTransferCache.getGeoCodeAliasTypeTransfer(geoCodeAliasType))
        );
        
        return geoCodeAliasTypeTransfers;
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
            var geoCodeAliasType = GeoCodeAliasTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     geoCodeAliasTypeDetailValue.getGeoCodeAliasTypePK());
            var geoCodeAliasTypeDetail = geoCodeAliasType.getActiveDetailForUpdate();
            
            geoCodeAliasTypeDetail.setThruTime(session.START_TIME_LONG);
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
            
            geoCodeAliasTypeDetail = GeoCodeAliasTypeDetailFactory.getInstance().create(geoCodeAliasTypePK, geoCodeTypePK, geoCodeAliasTypeName,
                    validationPattern, isRequired, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
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
        geoCodeAliasTypeDetail.setThruTime(session.START_TIME_LONG);
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
    
    public GeoCodeAliasTypeDescription createGeoCodeAliasTypeDescription(GeoCodeAliasType geoCodeAliasType, Language language, String description, BasePK createdBy) {
        var geoCodeAliasTypeDescription = GeoCodeAliasTypeDescriptionFactory.getInstance().create(geoCodeAliasType, language, description,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(geoCodeAliasType.getPrimaryKey(), EventTypes.MODIFY, geoCodeAliasTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return geoCodeAliasTypeDescription;
    }
    
    private GeoCodeAliasTypeDescription getGeoCodeAliasTypeDescription(GeoCodeAliasType geoCodeAliasType, Language language, EntityPermission entityPermission) {
        GeoCodeAliasTypeDescription geoCodeAliasTypeDescription;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM geocodealiastypedescriptions " +
                        "WHERE geoatd_geoat_geocodealiastypeid = ? AND geoatd_lang_languageid = ? AND geoatd_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM geocodealiastypedescriptions " +
                        "WHERE geoatd_geoat_geocodealiastypeid = ? AND geoatd_lang_languageid = ? AND geoatd_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = GeoCodeAliasTypeDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, geoCodeAliasType.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            geoCodeAliasTypeDescription = GeoCodeAliasTypeDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return geoCodeAliasTypeDescription;
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
        List<GeoCodeAliasTypeDescription> geoCodeAliasTypeDescriptions;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM geocodealiastypedescriptions, languages " +
                        "WHERE geoatd_geoat_geocodealiastypeid = ? AND geoatd_thrutime = ? AND geoatd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM geocodealiastypedescriptions " +
                        "WHERE geoatd_geoat_geocodealiastypeid = ? AND geoatd_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = GeoCodeAliasTypeDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, geoCodeAliasType.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            geoCodeAliasTypeDescriptions = GeoCodeAliasTypeDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return geoCodeAliasTypeDescriptions;
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
            geoCodeAliasTypeDescription = getGeoCodeAliasTypeDescription(geoCodeAliasType, getPartyControl().getDefaultLanguage());
        }
        
        if(geoCodeAliasTypeDescription == null) {
            description = geoCodeAliasType.getLastDetail().getGeoCodeAliasTypeName();
        } else {
            description = geoCodeAliasTypeDescription.getDescription();
        }
        
        return description;
    }
    
    public GeoCodeAliasTypeDescriptionTransfer getGeoCodeAliasTypeDescriptionTransfer(UserVisit userVisit, GeoCodeAliasTypeDescription geoCodeAliasTypeDescription) {
        return getGeoTransferCaches(userVisit).getGeoCodeAliasTypeDescriptionTransferCache().getGeoCodeAliasTypeDescriptionTransfer(geoCodeAliasTypeDescription);
    }
    
    public List<GeoCodeAliasTypeDescriptionTransfer> getGeoCodeAliasTypeDescriptionTransfers(UserVisit userVisit, GeoCodeAliasType geoCodeAliasType) {
        var geoCodeAliasTypeDescriptions = getGeoCodeAliasTypeDescriptionsByGeoCodeAliasType(geoCodeAliasType);
        List<GeoCodeAliasTypeDescriptionTransfer> geoCodeAliasTypeDescriptionTransfers = new ArrayList<>(geoCodeAliasTypeDescriptions.size());

        geoCodeAliasTypeDescriptions.forEach((geoCodeAliasTypeDescription) -> {
            geoCodeAliasTypeDescriptionTransfers.add(getGeoTransferCaches(userVisit).getGeoCodeAliasTypeDescriptionTransferCache().getGeoCodeAliasTypeDescriptionTransfer(geoCodeAliasTypeDescription));
        });
        
        return geoCodeAliasTypeDescriptionTransfers;
    }
    
    public void updateGeoCodeAliasTypeDescriptionFromValue(GeoCodeAliasTypeDescriptionValue geoCodeAliasTypeDescriptionValue, BasePK updatedBy) {
        if(geoCodeAliasTypeDescriptionValue.hasBeenModified()) {
            var geoCodeAliasTypeDescription = GeoCodeAliasTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, geoCodeAliasTypeDescriptionValue.getPrimaryKey());
            
            geoCodeAliasTypeDescription.setThruTime(session.START_TIME_LONG);
            geoCodeAliasTypeDescription.store();

            var geoCodeAliasType = geoCodeAliasTypeDescription.getGeoCodeAliasType();
            var language = geoCodeAliasTypeDescription.getLanguage();
            var description = geoCodeAliasTypeDescriptionValue.getDescription();
            
            geoCodeAliasTypeDescription = GeoCodeAliasTypeDescriptionFactory.getInstance().create(geoCodeAliasType, language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(geoCodeAliasType.getPrimaryKey(), EventTypes.MODIFY, geoCodeAliasTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteGeoCodeAliasTypeDescription(GeoCodeAliasTypeDescription geoCodeAliasTypeDescription, BasePK deletedBy) {
        geoCodeAliasTypeDescription.setThruTime(session.START_TIME_LONG);
        
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

        var geoCode = GeoCodeFactory.getInstance().create();
        var geoCodeDetail = GeoCodeDetailFactory.getInstance().create(geoCode, geoCodeName, geoCodeType,
                geoCodeScope, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        geoCode = GeoCodeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, geoCode.getPrimaryKey());
        geoCode.setActiveDetail(geoCodeDetail);
        geoCode.setLastDetail(geoCodeDetail);
        geoCode.store();
        
        sendEvent(geoCode.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return geoCode;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.GeoCode */
    public GeoCode getGeoCodeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new GeoCodePK(entityInstance.getEntityUniqueId());

        return GeoCodeFactory.getInstance().getEntityFromPK(entityPermission, pk);
    }

    public GeoCode getGeoCodeByEntityInstance(EntityInstance entityInstance) {
        return getGeoCodeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public long countGeoCodes() {
        return session.queryForLong("""
                SELECT COUNT(*)
                FROM geocodes, geocodedetails
                WHERE geo_activedetailid = geodt_geocodedetailid
                """);
    }

    public long countGeoCodesByGeoCodeType(GeoCodeType geoCodeType) {
        return session.queryForLong("""
                SELECT COUNT(*)
                FROM geocodes, geocodedetails
                WHERE geo_activedetailid = geodt_geocodedetailid
                AND geodt_geot_geocodetypeid = ?
                """, geoCodeType);
    }

    public long countGeoCodesByGeoCodeScope(GeoCodeScope geoCodeScope) {
        return session.queryForLong("""
                SELECT COUNT(*)
                FROM geocodes, geocodedetails
                WHERE geo_activedetailid = geodt_geocodedetailid
                AND geodt_geos_geocodescopeid = ?
                """, geoCodeScope);
    }

    public GeoCodeDetailValue getGeoCodeDetailValueForUpdate(GeoCode geoCode) {
        return geoCode == null? null: geoCode.getLastDetailForUpdate().getGeoCodeDetailValue().clone();
    }
    
    private GeoCode getDefaultGeoCode(GeoCodeScope geoCodeScope, EntityPermission entityPermission) {
        GeoCode geoCode;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM geocodes, geocodedetails " +
                        "WHERE geo_activedetailid = geodt_geocodedetailid AND geodt_geos_geocodescopeid = ? AND geodt_isdefault = 1";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM geocodes, geocodedetails " +
                        "WHERE geo_activedetailid = geodt_geocodedetailid AND geodt_geos_geocodescopeid = ? AND geodt_isdefault = 1 " +
                        "FOR UPDATE";
            }

            var ps = GeoCodeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, geoCodeScope.getPrimaryKey().getEntityId());
            
            geoCode = GeoCodeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return geoCode;
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
        GeoCode geoCode;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM geocodes, geocodedetails " +
                        "WHERE geo_activedetailid = geodt_geocodedetailid AND geodt_geocodename = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM geocodes, geocodedetails " +
                        "WHERE geo_activedetailid = geodt_geocodedetailid AND geodt_geocodename = ? " +
                        "FOR UPDATE";
            }

            var ps = GeoCodeFactory.getInstance().prepareStatement(query);
            
            ps.setString(1, geoCodeName);
            
            geoCode = GeoCodeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return geoCode;
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
        List<GeoCode> geoCode;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM geocodes, geocodedetails " +
                        "WHERE geo_activedetailid = geodt_geocodedetailid AND geodt_geos_geocodescopeid = ? " +
                        "ORDER BY geodt_sortorder, geodt_geocodename";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM geocodes, geocodedetails " +
                        "WHERE geo_activedetailid = geodt_geocodedetailid AND geodt_geos_geocodescopeid = ? " +
                        "FOR UPDATE";
            }

            var ps = GeoCodeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, geoCodeScope.getPrimaryKey().getEntityId());
            
            geoCode = GeoCodeFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return geoCode;
    }
    
    public List<GeoCode> getGeoCodesByGeoCodeScope(GeoCodeScope geoCodeScope) {
        return getGeoCodesByGeoCodeScope(geoCodeScope, EntityPermission.READ_ONLY);
    }
    
    public List<GeoCode> getGeoCodesByGeoCodeScopeForUpdate(GeoCodeScope geoCodeScope) {
        return getGeoCodesByGeoCodeScope(geoCodeScope, EntityPermission.READ_WRITE);
    }
    
    public GeoCodeTransfer getGeoCodeTransfer(UserVisit userVisit, GeoCode geoCode) {
        return getGeoTransferCaches(userVisit).getGeoCodeTransferCache().getGeoCodeTransfer(geoCode);
    }
    
    public List<GeoCodeTransfer> getGeoCodeTransfers(UserVisit userVisit, Collection<GeoCode> geoCodes) {
        List<GeoCodeTransfer> geoCodeTransfers = new ArrayList<>(geoCodes.size());
        var geoCodeTransferCache = getGeoTransferCaches(userVisit).getGeoCodeTransferCache();
        
        geoCodes.forEach((geoCode) ->
                geoCodeTransfers.add(geoCodeTransferCache.getGeoCodeTransfer(geoCode))
        );
        
        return geoCodeTransfers;
    }
    
    public List<GeoCodeTransfer> getGeoCodeTransfersByGeoCodeScope(UserVisit userVisit, GeoCodeScope geoCodeScope) {
        return getGeoCodeTransfers(userVisit, getGeoCodesByGeoCodeScope(geoCodeScope));
    }
    
    private void updateGeoCodeFromValue(GeoCodeDetailValue geoCodeDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(geoCodeDetailValue.hasBeenModified()) {
            var geoCode = GeoCodeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     geoCodeDetailValue.getGeoCodePK());
            var geoCodeDetail = geoCode.getActiveDetailForUpdate();
            
            geoCodeDetail.setThruTime(session.START_TIME_LONG);
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
            
            geoCodeDetail = GeoCodeDetailFactory.getInstance().create(geoCodePK, geoCodeName, geoCodeTypePK, geoCodeScope.getPrimaryKey(), isDefault,
                    sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
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

        if(geoCodeDetail.getGeoCodeType().getLastDetail().getGeoCodeTypeName().equals(GeoConstants.GeoCodeType_COUNTRY)) {
            var itemControl = Session.getModelController(ItemControl.class);
            
            itemControl.deleteHarmonizedTariffScheduleCodesByCountryGeoCode(geoCode, deletedBy);
            itemControl.deleteItemHarmonizedTariffScheduleCodesByCountryGeoCode(geoCode, deletedBy);
            
            deleteGeoCodeCountryByGeoCode(geoCode, deletedBy);
        }

        geoCodeDetail.setThruTime(session.START_TIME_LONG);
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
    
    public GeoCodeDescription createGeoCodeDescription(GeoCode geoCode, Language language, String description, BasePK createdBy) {
        var geoCodeDescription = GeoCodeDescriptionFactory.getInstance().create(geoCode, language, description, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
        sendEvent(geoCode.getPrimaryKey(), EventTypes.MODIFY, geoCodeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return geoCodeDescription;
    }
    
    private GeoCodeDescription getGeoCodeDescription(GeoCode geoCode, Language language, EntityPermission entityPermission) {
        GeoCodeDescription geoCodeDescription;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM geocodedescriptions " +
                        "WHERE geod_geo_geocodeid = ? AND geod_lang_languageid = ? AND geod_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM geocodedescriptions " +
                        "WHERE geod_geo_geocodeid = ? AND geod_lang_languageid = ? AND geod_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = GeoCodeDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, geoCode.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            geoCodeDescription = GeoCodeDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return geoCodeDescription;
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
        List<GeoCodeDescription> geoCodeDescriptions;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM geocodedescriptions, languages " +
                        "WHERE geod_geo_geocodeid = ? AND geod_thrutime = ? AND geod_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM geocodedescriptions " +
                        "WHERE geod_geo_geocodeid = ? AND geod_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = GeoCodeDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, geoCode.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            geoCodeDescriptions = GeoCodeDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return geoCodeDescriptions;
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
            geoCodeDescription = getGeoCodeDescription(geoCode, getPartyControl().getDefaultLanguage());
        }
        
        if(geoCodeDescription == null) {
            description = geoCode.getLastDetail().getGeoCodeName();
        } else {
            description = geoCodeDescription.getDescription();
        }
        
        return description;
    }
    
    public GeoCodeDescriptionTransfer getGeoCodeDescriptionTransfer(UserVisit userVisit, GeoCodeDescription geoCodeDescription) {
        return getGeoTransferCaches(userVisit).getGeoCodeDescriptionTransferCache().getGeoCodeDescriptionTransfer(geoCodeDescription);
    }
    
    public List<GeoCodeDescriptionTransfer> getGeoCodeDescriptionTransfers(UserVisit userVisit, GeoCode geoCode) {
        var geoCodeDescriptions = getGeoCodeDescriptionsByGeoCode(geoCode);
        List<GeoCodeDescriptionTransfer> geoCodeDescriptionTransfers = new ArrayList<>(geoCodeDescriptions.size());

        geoCodeDescriptions.forEach((geoCodeDescription) -> {
            geoCodeDescriptionTransfers.add(getGeoTransferCaches(userVisit).getGeoCodeDescriptionTransferCache().getGeoCodeDescriptionTransfer(geoCodeDescription));
        });

        return geoCodeDescriptionTransfers;
    }
    
    public void updateGeoCodeDescriptionFromValue(GeoCodeDescriptionValue geoCodeDescriptionValue, BasePK updatedBy) {
        if(geoCodeDescriptionValue.hasBeenModified()) {
            var geoCodeDescription = GeoCodeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, geoCodeDescriptionValue.getPrimaryKey());
            
            geoCodeDescription.setThruTime(session.START_TIME_LONG);
            geoCodeDescription.store();

            var geoCode = geoCodeDescription.getGeoCode();
            var language = geoCodeDescription.getLanguage();
            var description = geoCodeDescriptionValue.getDescription();
            
            geoCodeDescription = GeoCodeDescriptionFactory.getInstance().create(geoCode, language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(geoCode.getPrimaryKey(), EventTypes.MODIFY, geoCodeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteGeoCodeDescription(GeoCodeDescription geoCodeDescription, BasePK deletedBy) {
        geoCodeDescription.setThruTime(session.START_TIME_LONG);
        
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
    
    public GeoCodeAlias createGeoCodeAlias(GeoCode geoCode, GeoCodeAliasType geoCodeAliasType, String alias, BasePK createdBy) {
        var geoCodeAlias = GeoCodeAliasFactory.getInstance().create(geoCode, geoCode.getLastDetail().getGeoCodeScope(), geoCodeAliasType, alias,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(geoCode.getPrimaryKey(), EventTypes.MODIFY, geoCodeAlias.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return geoCodeAlias;
    }
    
    private static final Map<EntityPermission, String> getGeoCodeAliasQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM geocodealiases " +
                "WHERE geoa_geo_geocodeid = ? AND geoa_geoat_geocodealiastypeid = ? AND geoa_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM geocodealiases " +
                "WHERE geoa_geo_geocodeid = ? AND geoa_geoat_geocodealiastypeid = ? AND geoa_thrutime = ? " +
                "FOR UPDATE");
        getGeoCodeAliasQueries = Collections.unmodifiableMap(queryMap);
    }

    private GeoCodeAlias getGeoCodeAlias(GeoCode geoCode, GeoCodeAliasType geoCodeAliasType, EntityPermission entityPermission) {
        return GeoCodeAliasFactory.getInstance().getEntityFromQuery(entityPermission, getGeoCodeAliasQueries,
                geoCode, geoCodeAliasType, Session.MAX_TIME_LONG);
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

    private static final Map<EntityPermission, String> getGeoCodeAliasByAliasWithinScopeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM geocodealiases " +
                "WHERE geoa_geos_geocodescopeid = ? AND geoa_geoat_geocodealiastypeid = ? AND geoa_alias = ? AND geoa_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM geocodealiases " +
                "WHERE geoa_geos_geocodescopeid = ? AND geoa_geoat_geocodealiastypeid = ? AND geoa_alias = ? AND geoa_thrutime = ? " +
                "FOR UPDATE");
        getGeoCodeAliasByAliasWithinScopeQueries = Collections.unmodifiableMap(queryMap);
    }

    private GeoCodeAlias getGeoCodeAliasByAliasWithinScope(GeoCodeScope geoCodeScope, GeoCodeAliasType geoCodeAliasType, String alias, EntityPermission entityPermission) {
        return GeoCodeAliasFactory.getInstance().getEntityFromQuery(entityPermission, getGeoCodeAliasByAliasWithinScopeQueries,
                geoCodeScope, geoCodeAliasType, alias, Session.MAX_TIME_LONG);
    }

    public GeoCodeAlias getGeoCodeAliasByAliasWithinScope(GeoCodeScope geoCodeScope, GeoCodeAliasType geoCodeAliasType, String alias) {
        return getGeoCodeAliasByAliasWithinScope(geoCodeScope, geoCodeAliasType, alias, EntityPermission.READ_ONLY);
    }

    public GeoCodeAlias getGeoCodeAliasByAliasWithinScopeForUpdate(GeoCodeScope geoCodeScope, GeoCodeAliasType geoCodeAliasType, String alias) {
        return getGeoCodeAliasByAliasWithinScope(geoCodeScope, geoCodeAliasType, alias, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getGeoCodeAliasesByGeoCodeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM geocodealiases, geocodealiastypes, geocodealiastypedetails " +
                "WHERE geoa_geo_geocodeid = ? AND geoa_thrutime = ? " +
                "AND geoa_geoat_geocodealiastypeid = geoat_geocodealiastypeid AND geoat_lastdetailid = geoatdt_geocodealiastypedetailid " +
                "ORDER BY geoatdt_sortorder, geoatdt_geocodealiastypename " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM geocodealiases, geocodealiastypes, geocodealiastypedetails " +
                "WHERE geoa_geo_geocodeid = ? AND geoa_thrutime = ? " +
                "AND geoa_geoat_geocodealiastypeid = geoat_geocodealiastypeid AND geoat_lastdetailid = geoatdt_geocodealiastypedetailid " +
                "FOR UPDATE");
        getGeoCodeAliasesByGeoCodeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<GeoCodeAlias> getGeoCodeAliasesByGeoCode(GeoCode geoCode, EntityPermission entityPermission) {
        return GeoCodeAliasFactory.getInstance().getEntitiesFromQuery(entityPermission, getGeoCodeAliasesByGeoCodeQueries,
                geoCode, Session.MAX_TIME_LONG);
    }

    public List<GeoCodeAlias> getGeoCodeAliasesByGeoCode(GeoCode geoCode) {
        return getGeoCodeAliasesByGeoCode(geoCode, EntityPermission.READ_ONLY);
    }

    public List<GeoCodeAlias> getGeoCodeAliasesByGeoCodeForUpdate(GeoCode geoCode) {
        return getGeoCodeAliasesByGeoCode(geoCode, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getGeoCodeAliasesByGeoCodeScopeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM geocodealiases " +
                "WHERE geoa_geos_geocodescopeid = ? AND geoa_thrutime = ? " +
                "_LIMIT_"); // TODO: ORDER BY needed.
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM geocodealiases " +
                "WHERE geoa_geos_geocodescopeid = ? AND geoa_thrutime = ? " +
                "FOR UPDATE");
        getGeoCodeAliasesByGeoCodeScopeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<GeoCodeAlias> getGeoCodeAliasesByGeoCodeScope(GeoCodeScope geoCodeScope, EntityPermission entityPermission) {
        return GeoCodeAliasFactory.getInstance().getEntitiesFromQuery(entityPermission, getGeoCodeAliasesByGeoCodeScopeQueries,
                geoCodeScope, Session.MAX_TIME_LONG);
    }

    public List<GeoCodeAlias> getGeoCodeAliasesByGeoCodeScope(GeoCodeScope geoCodeScope) {
        return getGeoCodeAliasesByGeoCodeScope(geoCodeScope, EntityPermission.READ_ONLY);
    }

    public List<GeoCodeAlias> getGeoCodeAliasesByGeoCodeScopeForUpdate(GeoCodeScope geoCodeScope) {
        return getGeoCodeAliasesByGeoCodeScope(geoCodeScope, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getGeoCodeAliasesByGeoCodeAliasTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM geocodealiases " +
                "WHERE geoa_geoat_geocodealiastypeid = ? AND geoa_thrutime = ? " +
                "_LIMIT_"); // TODO: ORDER BY needed.
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM geocodealiases " +
                "WHERE geoa_geoat_geocodealiastypeid = ? AND geoa_thrutime = ? " +
                "FOR UPDATE");
        getGeoCodeAliasesByGeoCodeAliasTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<GeoCodeAlias> getGeoCodeAliasesByGeoCodeAliasType(GeoCodeAliasType geoCodeAliasType, EntityPermission entityPermission) {
        return GeoCodeAliasFactory.getInstance().getEntitiesFromQuery(entityPermission, getGeoCodeAliasesByGeoCodeAliasTypeQueries,
                geoCodeAliasType, Session.MAX_TIME_LONG);
    }

    public List<GeoCodeAlias> getGeoCodeAliasesByGeoCodeAliasType(GeoCodeAliasType geoCodeAliasType) {
        return getGeoCodeAliasesByGeoCodeAliasType(geoCodeAliasType, EntityPermission.READ_ONLY);
    }

    public List<GeoCodeAlias> getGeoCodeAliasesByGeoCodeAliasTypeForUpdate(GeoCodeAliasType geoCodeAliasType) {
        return getGeoCodeAliasesByGeoCodeAliasType(geoCodeAliasType, EntityPermission.READ_WRITE);
    }

    public GeoCodeAliasTransfer getGeoCodeAliasTransfer(UserVisit userVisit, GeoCodeAlias geoCodeAlias) {
        return getGeoTransferCaches(userVisit).getGeoCodeAliasTransferCache().getGeoCodeAliasTransfer(geoCodeAlias);
    }
    
    public List<GeoCodeAliasTransfer> getGeoCodeAliasTransfers(UserVisit userVisit, Collection<GeoCodeAlias> geoCodeAliases) {
        List<GeoCodeAliasTransfer> geoCodeAliasTransfers = new ArrayList<>(geoCodeAliases.size());
        var geoCodeAliasTransferCache = getGeoTransferCaches(userVisit).getGeoCodeAliasTransferCache();
        
        geoCodeAliases.forEach((geoCodeAlias) ->
                geoCodeAliasTransfers.add(geoCodeAliasTransferCache.getGeoCodeAliasTransfer(geoCodeAlias))
        );
        
        return geoCodeAliasTransfers;
    }
    
    public List<GeoCodeAliasTransfer> getGeoCodeAliasTransfersByGeoCode(UserVisit userVisit, GeoCode geoCode) {
        return getGeoCodeAliasTransfers(userVisit, getGeoCodeAliasesByGeoCode(geoCode));
    }

    public void updateGeoCodeAliasFromValue(GeoCodeAliasValue geoCodeAliasValue, BasePK updatedBy) {
        if(geoCodeAliasValue.hasBeenModified()) {
            var geoCodeAlias = GeoCodeAliasFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    geoCodeAliasValue.getPrimaryKey());

            geoCodeAlias.setThruTime(session.START_TIME_LONG);
            geoCodeAlias.store();

            var geoCodePK = geoCodeAlias.getGeoCodePK(); // Not updated
            var geoCodeScopePK = geoCodeAlias.getGeoCodeScopePK(); // Not updated
            var geoCodeAliasTypePK = geoCodeAlias.getGeoCodeAliasTypePK(); // Not updated
            var alias = geoCodeAliasValue.getAlias();

            geoCodeAlias = GeoCodeAliasFactory.getInstance().create(geoCodePK, geoCodeScopePK, geoCodeAliasTypePK, alias, session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);

            sendEvent(geoCodePK, EventTypes.MODIFY, geoCodeAlias.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteGeoCodeAlias(GeoCodeAlias geoCodeAlias, BasePK deletedBy) {
        geoCodeAlias.setThruTime(session.START_TIME_LONG);

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
    
    public GeoCodeRelationship createGeoCodeRelationship(GeoCode fromGeoCode, GeoCode toGeoCode, BasePK createdBy) {
        var geoCodeRelationship = GeoCodeRelationshipFactory.getInstance().create(fromGeoCode, toGeoCode,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(fromGeoCode.getPrimaryKey(), EventTypes.MODIFY, geoCodeRelationship.getPrimaryKey(), null, createdBy);
        
        return geoCodeRelationship;
    }
    
    public long countGeoCodeRelationshipsByFromGeoCode(GeoCode fromGeoCode) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM geocoderelationships " +
                "WHERE geor_fromgeocodeid = ? AND geor_thrutime = ?",
                fromGeoCode, Session.MAX_TIME_LONG);
    }

    public long countGeoCodeRelationshipsByToGeoCode(GeoCode toGeoCode) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM geocoderelationships " +
                "WHERE geor_togeocodeid = ? AND geor_thrutime = ?",
                toGeoCode, Session.MAX_TIME_LONG);
    }

    public GeoCodeRelationship getGeoCodeRelationship(GeoCode fromGeoCode, GeoCode toGeoCode) {
        GeoCodeRelationship geoCodeRelationship;
        
        try {
            var ps = GeoCodeRelationshipFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM geocoderelationships " +
                    "WHERE geor_fromgeocodeid = ? AND geor_togeocodeid = ? AND geor_thrutime = ?");
            
            ps.setLong(1, fromGeoCode.getPrimaryKey().getEntityId());
            ps.setLong(2, toGeoCode.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            geoCodeRelationship = GeoCodeRelationshipFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return geoCodeRelationship;
    }
    
    private static final Map<EntityPermission, String> getGeoCodeRelationshipsByFromGeoCodeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM geocoderelationships " +
                "WHERE geor_fromgeocodeid = ? AND geor_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM geocoderelationships " +
                "WHERE geor_fromgeocodeid = ? AND geor_thrutime = ? " +
                "FOR UPDATE");
        getGeoCodeRelationshipsByFromGeoCodeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<GeoCodeRelationship> getGeoCodeRelationshipsByFromGeoCode(GeoCode fromGeoCode, EntityPermission entityPermission) {
        return GeoCodeRelationshipFactory.getInstance().getEntitiesFromQuery(entityPermission, getGeoCodeRelationshipsByFromGeoCodeQueries,
                fromGeoCode, Session.MAX_TIME_LONG);
    }

    public List<GeoCodeRelationship> getGeoCodeRelationshipsByFromGeoCode(GeoCode fromGeoCode) {
        return getGeoCodeRelationshipsByFromGeoCode(fromGeoCode, EntityPermission.READ_ONLY);
    }

    public List<GeoCodeRelationship> getGeoCodeRelationshipsByFromGeoCodeForUpdate(GeoCode fromGeoCode) {
        return getGeoCodeRelationshipsByFromGeoCode(fromGeoCode, EntityPermission.READ_WRITE);
    }

    public List<GeoCodeRelationship> getGeoCodeRelationshipsByFromGeoCodeAndGeoCodeType(GeoCode fromGeoCode, GeoCodeType geoCodeType) {
        List<GeoCodeRelationship> geoCodeRelationships;
        
        try {
            var ps = GeoCodeRelationshipFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM geocoderelationships, geocodes, geocodedetails " +
                    "WHERE geor_fromgeocodeid = ? AND geor_thrutime = ? " +
                    "AND geor_togeocodeid = geo_geocodeid AND geo_lastdetailid = geodt_geocodedetailid " +
                    "AND geodt_geot_geocodetypeid = ?");
            
            ps.setLong(1, fromGeoCode.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            ps.setLong(3, geoCodeType.getPrimaryKey().getEntityId());
            
            geoCodeRelationships = GeoCodeRelationshipFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return geoCodeRelationships;
    }
    
    private static final Map<EntityPermission, String> getGeoCodeRelationshipsByToGeoCodeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM geocoderelationships " +
                "WHERE geor_togeocodeid = ? AND geor_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM geocoderelationships " +
                "WHERE geor_togeocodeid = ? AND geor_thrutime = ? " +
                "FOR UPDATE");
        getGeoCodeRelationshipsByToGeoCodeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<GeoCodeRelationship> getGeoCodeRelationshipsByToGeoCode(GeoCode toGeoCode, EntityPermission entityPermission) {
        return GeoCodeRelationshipFactory.getInstance().getEntitiesFromQuery(entityPermission, getGeoCodeRelationshipsByToGeoCodeQueries,
                toGeoCode, Session.MAX_TIME_LONG);
    }

    public List<GeoCodeRelationship> getGeoCodeRelationshipsByToGeoCode(GeoCode toGeoCode) {
        return getGeoCodeRelationshipsByToGeoCode(toGeoCode, EntityPermission.READ_ONLY);
    }

    public List<GeoCodeRelationship> getGeoCodeRelationshipsByToGeoCodeForUpdate(GeoCode toGeoCode) {
        return getGeoCodeRelationshipsByToGeoCode(toGeoCode, EntityPermission.READ_WRITE);
    }

    public GeoCodeRelationshipTransfer getGeoCodeRelationshipTransfer(UserVisit userVisit, GeoCodeRelationship geoCodeRelationship) {
        return getGeoTransferCaches(userVisit).getGeoCodeRelationshipTransferCache().getGeoCodeRelationshipTransfer(geoCodeRelationship);
    }

    public void deleteGeoCodeRelationship(GeoCodeRelationship geoCodeRelationship, BasePK deletedBy) {
        geoCodeRelationship.setThruTime(session.START_TIME_LONG);
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

        var geoCodeLanguage = GeoCodeLanguageFactory.getInstance().create(geoCode, language, isDefault,
                sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(geoCode.getPrimaryKey(), EventTypes.MODIFY, geoCodeLanguage.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return geoCodeLanguage;
    }
    
    private GeoCodeLanguage getGeoCodeLanguage(GeoCode geoCode, Language language, EntityPermission entityPermission) {
        GeoCodeLanguage geoCodeLanguage;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM geocodelanguages " +
                        "WHERE geol_geo_geocodeid = ? AND geol_lang_languageid = ? AND geol_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM geocodelanguages " +
                        "WHERE geol_geo_geocodeid = ? AND geol_lang_languageid = ? AND geol_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = GeoCodeLanguageFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, geoCode.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            geoCodeLanguage = GeoCodeLanguageFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return geoCodeLanguage;
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
        GeoCodeLanguage geoCodeLanguage;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM geocodelanguages " +
                        "WHERE geol_geo_geocodeid = ? AND geol_isdefault = 1 AND geol_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM geocodelanguages " +
                        "WHERE geol_geo_geocodeid = ? AND geol_isdefault = 1 AND geol_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = GeoCodeLanguageFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, geoCode.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            geoCodeLanguage = GeoCodeLanguageFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return geoCodeLanguage;
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
        List<GeoCodeLanguage> geoCodeLanguages;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM geocodelanguages, languages " +
                        "WHERE geol_geo_geocodeid = ? AND geol_thrutime = ? " +
                        "AND geol_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM geocodelanguages " +
                        "WHERE geol_geo_geocodeid = ? AND geol_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = GeoCodeLanguageFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, geoCode.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            geoCodeLanguages = GeoCodeLanguageFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return geoCodeLanguages;
    }
    
    public List<GeoCodeLanguage> getGeoCodeLanguagesByGeoCode(GeoCode geoCode) {
        return getGeoCodeLanguagesByGeoCode(geoCode, EntityPermission.READ_ONLY);
    }
    
    public List<GeoCodeLanguage> getGeoCodeLanguagesByGeoCodeForUpdate(GeoCode geoCode) {
        return getGeoCodeLanguagesByGeoCode(geoCode, EntityPermission.READ_WRITE);
    }
    
    private List<GeoCodeLanguage> getGeoCodeLanguagesByLanguage(Language language, EntityPermission entityPermission) {
        List<GeoCodeLanguage> geoCodeLanguages;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM geocodelanguages, geocodes, geocodedetails " +
                        "WHERE geol_lang_languageid = ? AND geol_thrutime = ? " +
                        "AND geol_geo_geocodeid = geo_geocodeid AND geo_lastdetailid = geodt_geocodedetailid " +
                        "ORDER BY geodt_sortorder, geodt_geocodename";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM geocodelanguages " +
                        "WHERE geol_lang_languageid = ? AND geol_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = GeoCodeLanguageFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, language.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            geoCodeLanguages = GeoCodeLanguageFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return geoCodeLanguages;
    }
    
    public List<GeoCodeLanguage> getGeoCodeLanguagesByLanguage(Language language) {
        return getGeoCodeLanguagesByLanguage(language, EntityPermission.READ_ONLY);
    }
    
    public List<GeoCodeLanguage> getGeoCodeLanguagesByLanguageForUpdate(Language language) {
        return getGeoCodeLanguagesByLanguage(language, EntityPermission.READ_WRITE);
    }
    
    public List<GeoCodeLanguageTransfer> getGeoCodeLanguageTransfers(UserVisit userVisit, Collection<GeoCodeLanguage> geoCodeLanguages) {
        List<GeoCodeLanguageTransfer> geoCodeLanguageTransfers = new ArrayList<>(geoCodeLanguages.size());
        var geoCodeLanguageTransferCache = getGeoTransferCaches(userVisit).getGeoCodeLanguageTransferCache();
        
        geoCodeLanguages.forEach((geoCodeLanguage) ->
                geoCodeLanguageTransfers.add(geoCodeLanguageTransferCache.getGeoCodeLanguageTransfer(geoCodeLanguage))
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
        return getGeoTransferCaches(userVisit).getGeoCodeLanguageTransferCache().getGeoCodeLanguageTransfer(geoCodeLanguage);
    }
    
    private void updateGeoCodeLanguageFromValue(GeoCodeLanguageValue geoCodeLanguageValue, boolean checkDefault, BasePK updatedBy) {
        if(geoCodeLanguageValue.hasBeenModified()) {
            var geoCodeLanguage = GeoCodeLanguageFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     geoCodeLanguageValue.getPrimaryKey());
            
            geoCodeLanguage.setThruTime(session.START_TIME_LONG);
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
            
            geoCodeLanguage = GeoCodeLanguageFactory.getInstance().create(geoCodePK, languagePK, isDefault, sortOrder,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(geoCodePK, EventTypes.MODIFY, geoCodeLanguage.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void updateGeoCodeLanguageFromValue(GeoCodeLanguageValue geoCodeLanguageValue, BasePK updatedBy) {
        updateGeoCodeLanguageFromValue(geoCodeLanguageValue, true, updatedBy);
    }
    
    public void deleteGeoCodeLanguage(GeoCodeLanguage geoCodeLanguage, BasePK deletedBy) {
        geoCodeLanguage.setThruTime(session.START_TIME_LONG);
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

        var geoCodeCurrency = GeoCodeCurrencyFactory.getInstance().create(geoCode, currency, isDefault,
                sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(geoCode.getPrimaryKey(), EventTypes.MODIFY, geoCodeCurrency.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return geoCodeCurrency;
    }
    
    private GeoCodeCurrency getGeoCodeCurrency(GeoCode geoCode, Currency currency, EntityPermission entityPermission) {
        GeoCodeCurrency geoCodeCurrency;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM geocodecurrencies " +
                        "WHERE geocur_geo_geocodeid = ? AND geocur_cur_currencyid = ? AND geocur_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM geocodecurrencies " +
                        "WHERE geocur_geo_geocodeid = ? AND geocur_cur_currencyid = ? AND geocur_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = GeoCodeCurrencyFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, geoCode.getPrimaryKey().getEntityId());
            ps.setLong(2, currency.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            geoCodeCurrency = GeoCodeCurrencyFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return geoCodeCurrency;
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
        GeoCodeCurrency geoCodeCurrency;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM geocodecurrencies " +
                        "WHERE geocur_geo_geocodeid = ? AND geocur_isdefault = 1 AND geocur_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM geocodecurrencies " +
                        "WHERE geocur_geo_geocodeid = ? AND geocur_isdefault = 1 AND geocur_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = GeoCodeCurrencyFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, geoCode.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            geoCodeCurrency = GeoCodeCurrencyFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return geoCodeCurrency;
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
        List<GeoCodeCurrency> geoCodeCurrencies;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM geocodecurrencies, currencies " +
                        "WHERE geocur_geo_geocodeid = ? AND geocur_thrutime = ? " +
                        "AND geocur_cur_currencyid = cur_currencyid " +
                        "ORDER BY cur_sortorder, cur_currencyisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM geocodecurrencies " +
                        "WHERE geocur_geo_geocodeid = ? AND geocur_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = GeoCodeCurrencyFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, geoCode.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            geoCodeCurrencies = GeoCodeCurrencyFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return geoCodeCurrencies;
    }
    
    public List<GeoCodeCurrency> getGeoCodeCurrenciesByGeoCode(GeoCode geoCode) {
        return getGeoCodeCurrenciesByGeoCode(geoCode, EntityPermission.READ_ONLY);
    }
    
    public List<GeoCodeCurrency> getGeoCodeCurrenciesByGeoCodeForUpdate(GeoCode geoCode) {
        return getGeoCodeCurrenciesByGeoCode(geoCode, EntityPermission.READ_WRITE);
    }
    
    private List<GeoCodeCurrency> getGeoCodeCurrenciesByCurrency(Currency currency, EntityPermission entityPermission) {
        List<GeoCodeCurrency> geoCodeCurrencies;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM geocodecurrencies, geocodes, geocodedetails " +
                        "WHERE geocur_cur_currencyid = ? AND geocur_thrutime = ? " +
                        "AND geocur_geo_geocodeid = geo_geocodeid AND geo_lastdetailid = geodt_geocodedetailid " +
                        "ORDER BY geodt_sortorder, geodt_geocodename";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM geocodecurrencies " +
                        "WHERE geocur_cur_currencyid = ? AND geocur_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = GeoCodeCurrencyFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, currency.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            geoCodeCurrencies = GeoCodeCurrencyFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return geoCodeCurrencies;
    }
    
    public List<GeoCodeCurrency> getGeoCodeCurrenciesByCurrency(Currency currency) {
        return getGeoCodeCurrenciesByCurrency(currency, EntityPermission.READ_ONLY);
    }
    
    public List<GeoCodeCurrency> getGeoCodeCurrenciesByCurrencyForUpdate(Currency currency) {
        return getGeoCodeCurrenciesByCurrency(currency, EntityPermission.READ_WRITE);
    }
    
    public List<GeoCodeCurrencyTransfer> getGeoCodeCurrencyTransfers(UserVisit userVisit, Collection<GeoCodeCurrency> geoCodeCurrencies) {
        List<GeoCodeCurrencyTransfer> geoCodeCurrencyTransfers = new ArrayList<>(geoCodeCurrencies.size());
        var geoCodeCurrencyTransferCache = getGeoTransferCaches(userVisit).getGeoCodeCurrencyTransferCache();
        
        geoCodeCurrencies.forEach((geoCodeCurrency) ->
                geoCodeCurrencyTransfers.add(geoCodeCurrencyTransferCache.getGeoCodeCurrencyTransfer(geoCodeCurrency))
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
        return getGeoTransferCaches(userVisit).getGeoCodeCurrencyTransferCache().getGeoCodeCurrencyTransfer(geoCodeCurrency);
    }
    
    private void updateGeoCodeCurrencyFromValue(GeoCodeCurrencyValue geoCodeCurrencyValue, boolean checkDefault, BasePK updatedBy) {
        if(geoCodeCurrencyValue.hasBeenModified()) {
            var geoCodeCurrency = GeoCodeCurrencyFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     geoCodeCurrencyValue.getPrimaryKey());
            
            geoCodeCurrency.setThruTime(session.START_TIME_LONG);
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
            
            geoCodeCurrency = GeoCodeCurrencyFactory.getInstance().create(geoCodePK, currencyPK, isDefault, sortOrder,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(geoCodePK, EventTypes.MODIFY, geoCodeCurrency.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void updateGeoCodeCurrencyFromValue(GeoCodeCurrencyValue geoCodeCurrencyValue, BasePK updatedBy) {
        updateGeoCodeCurrencyFromValue(geoCodeCurrencyValue, true, updatedBy);
    }
    
    public void deleteGeoCodeCurrency(GeoCodeCurrency geoCodeCurrency, BasePK deletedBy) {
        geoCodeCurrency.setThruTime(session.START_TIME_LONG);
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

        var geoCodeTimeZone = GeoCodeTimeZoneFactory.getInstance().create(geoCode, timeZone, isDefault,
                sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(geoCode.getPrimaryKey(), EventTypes.MODIFY, geoCodeTimeZone.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return geoCodeTimeZone;
    }
    
    private GeoCodeTimeZone getGeoCodeTimeZone(GeoCode geoCode, TimeZone timeZone, EntityPermission entityPermission) {
        GeoCodeTimeZone geoCodeTimeZone;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM geocodetimezones " +
                        "WHERE geotz_geo_geocodeid = ? AND geotz_tz_timeZoneid = ? AND geotz_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM geocodetimezones " +
                        "WHERE geotz_geo_geocodeid = ? AND geotz_tz_timeZoneid = ? AND geotz_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = GeoCodeTimeZoneFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, geoCode.getPrimaryKey().getEntityId());
            ps.setLong(2, timeZone.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            geoCodeTimeZone = GeoCodeTimeZoneFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return geoCodeTimeZone;
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
        GeoCodeTimeZone geoCodeTimeZone;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM geocodetimezones " +
                        "WHERE geotz_geo_geocodeid = ? AND geotz_isdefault = 1 AND geotz_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM geocodetimezones " +
                        "WHERE geotz_geo_geocodeid = ? AND geotz_isdefault = 1 AND geotz_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = GeoCodeTimeZoneFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, geoCode.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            geoCodeTimeZone = GeoCodeTimeZoneFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return geoCodeTimeZone;
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
        List<GeoCodeTimeZone> geoCodeTimeZones;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM geocodetimezones, timezones, timezonedetails " +
                        "WHERE geotz_geo_geocodeid = ? AND geotz_thrutime = ? " +
                        "AND geotz_tz_timezoneid = tz_timezoneid AND tz_lastdetailid = tzdt_timezonedetailid " +
                        "ORDER BY tzdt_sortorder, tzdt_javatimezonename";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM geocodetimezones " +
                        "WHERE geotz_geo_geocodeid = ? AND geotz_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = GeoCodeTimeZoneFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, geoCode.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            geoCodeTimeZones = GeoCodeTimeZoneFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return geoCodeTimeZones;
    }
    
    public List<GeoCodeTimeZone> getGeoCodeTimeZonesByGeoCode(GeoCode geoCode) {
        return getGeoCodeTimeZonesByGeoCode(geoCode, EntityPermission.READ_ONLY);
    }
    
    public List<GeoCodeTimeZone> getGeoCodeTimeZonesByGeoCodeForUpdate(GeoCode geoCode) {
        return getGeoCodeTimeZonesByGeoCode(geoCode, EntityPermission.READ_WRITE);
    }
    
    private List<GeoCodeTimeZone> getGeoCodeTimeZonesByTimeZone(TimeZone timeZone, EntityPermission entityPermission) {
        List<GeoCodeTimeZone> geoCodeTimeZones;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM geocodetimezones, geocodes, geocodedetails " +
                        "WHERE geotz_tz_timeZoneid = ? AND geotz_thrutime = ? " +
                        "AND geotz_geo_geocodeid = geo_geocodeid AND geo_lastdetailid = geodt_geocodedetailid " +
                        "ORDER BY geodt_sortorder, geodt_geocodename";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM geocodetimezones " +
                        "WHERE geotz_tz_timeZoneid = ? AND geotz_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = GeoCodeTimeZoneFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, timeZone.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            geoCodeTimeZones = GeoCodeTimeZoneFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return geoCodeTimeZones;
    }
    
    public List<GeoCodeTimeZone> getGeoCodeTimeZonesByTimeZone(TimeZone timeZone) {
        return getGeoCodeTimeZonesByTimeZone(timeZone, EntityPermission.READ_ONLY);
    }
    
    public List<GeoCodeTimeZone> getGeoCodeTimeZonesByTimeZoneForUpdate(TimeZone timeZone) {
        return getGeoCodeTimeZonesByTimeZone(timeZone, EntityPermission.READ_WRITE);
    }
    
    public List<GeoCodeTimeZoneTransfer> getGeoCodeTimeZoneTransfers(UserVisit userVisit, Collection<GeoCodeTimeZone> geoCodeTimeZones) {
        List<GeoCodeTimeZoneTransfer> geoCodeTimeZoneTransfers = new ArrayList<>(geoCodeTimeZones.size());
        var geoCodeTimeZoneTransferCache = getGeoTransferCaches(userVisit).getGeoCodeTimeZoneTransferCache();
        
        geoCodeTimeZones.forEach((geoCodeTimeZone) ->
                geoCodeTimeZoneTransfers.add(geoCodeTimeZoneTransferCache.getGeoCodeTimeZoneTransfer(geoCodeTimeZone))
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
        return getGeoTransferCaches(userVisit).getGeoCodeTimeZoneTransferCache().getGeoCodeTimeZoneTransfer(geoCodeTimeZone);
    }
    
    private void updateGeoCodeTimeZoneFromValue(GeoCodeTimeZoneValue geoCodeTimeZoneValue, boolean checkDefault, BasePK updatedBy) {
        if(geoCodeTimeZoneValue.hasBeenModified()) {
            var geoCodeTimeZone = GeoCodeTimeZoneFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     geoCodeTimeZoneValue.getPrimaryKey());
            
            geoCodeTimeZone.setThruTime(session.START_TIME_LONG);
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
            
            geoCodeTimeZone = GeoCodeTimeZoneFactory.getInstance().create(geoCodePK, timeZonePK, isDefault, sortOrder,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(geoCodePK, EventTypes.MODIFY, geoCodeTimeZone.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void updateGeoCodeTimeZoneFromValue(GeoCodeTimeZoneValue geoCodeTimeZoneValue, BasePK updatedBy) {
        updateGeoCodeTimeZoneFromValue(geoCodeTimeZoneValue, true, updatedBy);
    }
    
    public void deleteGeoCodeTimeZone(GeoCodeTimeZone geoCodeTimeZone, BasePK deletedBy) {
        geoCodeTimeZone.setThruTime(session.START_TIME_LONG);
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

        var geoCodeDateTimeFormat = GeoCodeDateTimeFormatFactory.getInstance().create(geoCode,
                dateTimeFormat, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(geoCode.getPrimaryKey(), EventTypes.MODIFY, geoCodeDateTimeFormat.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return geoCodeDateTimeFormat;
    }
    
    private GeoCodeDateTimeFormat getGeoCodeDateTimeFormat(GeoCode geoCode, DateTimeFormat dateTimeFormat, EntityPermission entityPermission) {
        GeoCodeDateTimeFormat geoCodeDateTimeFormat;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM geocodedatetimeformats " +
                        "WHERE geodtf_geo_geocodeid = ? AND geodtf_dtf_datetimeformatid = ? AND geodtf_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM geocodedatetimeformats " +
                        "WHERE geodtf_geo_geocodeid = ? AND geodtf_dtf_datetimeformatid = ? AND geodtf_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = GeoCodeDateTimeFormatFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, geoCode.getPrimaryKey().getEntityId());
            ps.setLong(2, dateTimeFormat.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            geoCodeDateTimeFormat = GeoCodeDateTimeFormatFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return geoCodeDateTimeFormat;
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
        GeoCodeDateTimeFormat geoCodeDateTimeFormat;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM geocodedatetimeformats " +
                        "WHERE geodtf_geo_geocodeid = ? AND geodtf_isdefault = 1 AND geodtf_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM geocodedatetimeformats " +
                        "WHERE geodtf_geo_geocodeid = ? AND geodtf_isdefault = 1 AND geodtf_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = GeoCodeDateTimeFormatFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, geoCode.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            geoCodeDateTimeFormat = GeoCodeDateTimeFormatFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return geoCodeDateTimeFormat;
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
        List<GeoCodeDateTimeFormat> geoCodeDateTimeFormats;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM geocodedatetimeformats, datetimeformats, datetimeformatdetails " +
                        "WHERE geodtf_geo_geocodeid = ? AND geodtf_thrutime = ? " +
                        "AND geodtf_dtf_datetimeformatid = dtf_datetimeformatid AND dtf_lastdetailid = dtfdt_datetimeformatdetailid " +
                        "ORDER BY dtfdt_sortorder, dtfdt_datetimeformatname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM geocodedatetimeformats " +
                        "WHERE geodtf_geo_geocodeid = ? AND geodtf_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = GeoCodeDateTimeFormatFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, geoCode.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            geoCodeDateTimeFormats = GeoCodeDateTimeFormatFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return geoCodeDateTimeFormats;
    }
    
    public List<GeoCodeDateTimeFormat> getGeoCodeDateTimeFormatsByGeoCode(GeoCode geoCode) {
        return getGeoCodeDateTimeFormatsByGeoCode(geoCode, EntityPermission.READ_ONLY);
    }
    
    public List<GeoCodeDateTimeFormat> getGeoCodeDateTimeFormatsByGeoCodeForUpdate(GeoCode geoCode) {
        return getGeoCodeDateTimeFormatsByGeoCode(geoCode, EntityPermission.READ_WRITE);
    }
    
    private List<GeoCodeDateTimeFormat> getGeoCodeDateTimeFormatsByDateTimeFormat(DateTimeFormat dateTimeFormat, EntityPermission entityPermission) {
        List<GeoCodeDateTimeFormat> geoCodeDateTimeFormats;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM geocodedatetimeformats, geocodes, geocodedetails " +
                        "WHERE geodtf_dtf_datetimeformatid = ? AND geodtf_thrutime = ? " +
                        "AND geodtf_geo_geocodeid = geo_geocodeid AND geo_lastdetailid = geodt_geocodedetailid " +
                        "ORDER BY geodt_sortorder, geodt_geocodename";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM geocodedatetimeformats " +
                        "WHERE geodtf_dtf_datetimeformatid = ? AND geodtf_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = GeoCodeDateTimeFormatFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, dateTimeFormat.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            geoCodeDateTimeFormats = GeoCodeDateTimeFormatFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return geoCodeDateTimeFormats;
    }
    
    public List<GeoCodeDateTimeFormat> getGeoCodeDateTimeFormatsByDateTimeFormat(DateTimeFormat dateTimeFormat) {
        return getGeoCodeDateTimeFormatsByDateTimeFormat(dateTimeFormat, EntityPermission.READ_ONLY);
    }
    
    public List<GeoCodeDateTimeFormat> getGeoCodeDateTimeFormatsByDateTimeFormatForUpdate(DateTimeFormat dateTimeFormat) {
        return getGeoCodeDateTimeFormatsByDateTimeFormat(dateTimeFormat, EntityPermission.READ_WRITE);
    }
    
    public List<GeoCodeDateTimeFormatTransfer> getGeoCodeDateTimeFormatTransfers(UserVisit userVisit, Collection<GeoCodeDateTimeFormat> geoCodeDateTimeFormats) {
        List<GeoCodeDateTimeFormatTransfer> geoCodeDateTimeFormatTransfers = new ArrayList<>(geoCodeDateTimeFormats.size());
        var geoCodeDateTimeFormatTransferCache = getGeoTransferCaches(userVisit).getGeoCodeDateTimeFormatTransferCache();
        
        geoCodeDateTimeFormats.forEach((geoCodeDateTimeFormat) ->
                geoCodeDateTimeFormatTransfers.add(geoCodeDateTimeFormatTransferCache.getGeoCodeDateTimeFormatTransfer(geoCodeDateTimeFormat))
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
        return getGeoTransferCaches(userVisit).getGeoCodeDateTimeFormatTransferCache().getGeoCodeDateTimeFormatTransfer(geoCodeDateTimeFormat);
    }
    
    private void updateGeoCodeDateTimeFormatFromValue(GeoCodeDateTimeFormatValue geoCodeDateTimeFormatValue, boolean checkDefault,
            BasePK updatedBy) {
        if(geoCodeDateTimeFormatValue.hasBeenModified()) {
            var geoCodeDateTimeFormat = GeoCodeDateTimeFormatFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     geoCodeDateTimeFormatValue.getPrimaryKey());
            
            geoCodeDateTimeFormat.setThruTime(session.START_TIME_LONG);
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
            
            geoCodeDateTimeFormat = GeoCodeDateTimeFormatFactory.getInstance().create(geoCodePK, dateTimeFormatPK,
                    isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(geoCodePK, EventTypes.MODIFY, geoCodeDateTimeFormat.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void updateGeoCodeDateTimeFormatFromValue(GeoCodeDateTimeFormatValue geoCodeDateTimeFormatValue, BasePK updatedBy) {
        updateGeoCodeDateTimeFormatFromValue(geoCodeDateTimeFormatValue, true, updatedBy);
    }
    
    public void deleteGeoCodeDateTimeFormat(GeoCodeDateTimeFormat geoCodeDateTimeFormat, BasePK deletedBy) {
        geoCodeDateTimeFormat.setThruTime(session.START_TIME_LONG);
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
    
    public GeoCodeCountry createGeoCodeCountry(GeoCode geoCode, String telephoneCode, String areaCodePattern,
            Boolean areaCodeRequired, String areaCodeExample, String telephoneNumberPattern, String telephoneNumberExample,
            PostalAddressFormat postalAddressFormat, Boolean cityRequired, Boolean cityGeoCodeRequired, Boolean stateRequired,
            Boolean stateGeoCodeRequired, String postalCodePattern, Boolean postalCodeRequired, Boolean postalCodeGeoCodeRequired,
            Integer postalCodeLength, Integer postalCodeGeoCodeLength, String postalCodeExample, BasePK createdBy) {

        var geoCodeCountry = GeoCodeCountryFactory.getInstance().create(geoCode, telephoneCode, areaCodePattern,
                areaCodeRequired, areaCodeExample, telephoneNumberPattern, telephoneNumberExample, postalAddressFormat,
                cityRequired, cityGeoCodeRequired, stateRequired, stateGeoCodeRequired, postalCodePattern, postalCodeRequired,
                postalCodeGeoCodeRequired, postalCodeLength, postalCodeGeoCodeLength, postalCodeExample, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
        sendEvent(geoCode.getPrimaryKey(), EventTypes.MODIFY, geoCodeCountry.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return geoCodeCountry;
    }
    
    private GeoCodeCountry getGeoCodeCountry(GeoCode geoCode, EntityPermission entityPermission) {
        GeoCodeCountry geoCodeCountry;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM geocodecountries " +
                        "WHERE geoc_geo_geocodeid = ? AND geoc_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM geocodecountries " +
                        "WHERE geoc_geo_geocodeid = ? AND geoc_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = GeoCodeCountryFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, geoCode.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            geoCodeCountry = GeoCodeCountryFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return geoCodeCountry;
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
            var geoCodeCountry = GeoCodeCountryFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     geoCodeCountryValue.getPrimaryKey());
            
            geoCodeCountry.setThruTime(session.START_TIME_LONG);
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
            
            geoCodeCountry = GeoCodeCountryFactory.getInstance().create(geoCodePK, telephoneCode, areaCodePattern,
                    areaCodeRequired, areaCodeExample, telephoneNumberPattern, telephoneNumberExample, postalAddressFormatPK,
                    cityRequired, cityGeoCodeRequired, stateRequired, stateGeoCodeRequired, postalCodePattern, postalCodeRequired,
                    postalCodeGeoCodeRequired, postalCodeLength, postalCodeGeoCodeLength, postalCodeExample,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(geoCodePK, EventTypes.MODIFY, geoCodeCountry.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteGeoCodeCountry(GeoCodeCountry geoCodeCountry, BasePK deletedBy) {
        geoCodeCountry.setThruTime(session.START_TIME_LONG);
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
        return getGeoCodeByTypeScopeAndAlias(GeoConstants.GeoCodeType_COUNTRY, GeoConstants.GeoCodeScope_COUNTRIES, countryAlias);
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
                GeoConstants.GeoCodeAliasType_ISO_2_LETTER);
        var countryGeoCodeAlias = getGeoCodeAlias(countryGeoCode, countryGeoCodeAliasType);
        var countryIso2Letter = countryGeoCodeAlias.getAlias();
        var geoCodeScopeName = countryIso2Letter + "_ZIP_CODES";
        
        return getGeoCodeByTypeScopeAndAlias(GeoConstants.GeoCodeType_ZIP_CODE, geoCodeScopeName, postalCodeAlias);
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
                GeoConstants.GeoCodeAliasType_ISO_2_LETTER);
        var countryGeoCodeAlias = getGeoCodeAlias(countryGeoCode, countryGeoCodeAliasType);
        var countryIso2Letter = countryGeoCodeAlias.getAlias();
        var geoCodeScopeName = countryIso2Letter + "_STATES";
        
        return getGeoCodeByTypeScopeAndAlias(GeoConstants.GeoCodeType_STATE, geoCodeScopeName, stateAlias);
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
                GeoConstants.GeoCodeAliasType_POSTAL_2_LETTER);
        var stateGeoCodeAlias = getGeoCodeAlias(stateGeoCode, stateGeoCodeAliasType);
        var statePostal2Letter = stateGeoCodeAlias.getAlias();

        var countryGeoCodeType = getGeoCodeTypeByName(GeoConstants.GeoCodeType_COUNTRY);
        var stateRelationships = getGeoCodeRelationshipsByFromGeoCodeAndGeoCodeType(stateGeoCode, countryGeoCodeType);
        if(stateRelationships.size() != 1) {
            getLog().error("non-1 stateRelationships.size()");
        }
        var countryGeoCode = stateRelationships.iterator().next().getToGeoCode();

        var countryGeoCodeAliasType = getGeoCodeAliasTypeByName(countryGeoCode.getLastDetail().getGeoCodeType(),
                GeoConstants.GeoCodeAliasType_ISO_2_LETTER);
        var countryGeoCodeAlias = getGeoCodeAlias(countryGeoCode, countryGeoCodeAliasType);
        var countryIso2Letter = countryGeoCodeAlias.getAlias();

        var geoCodeScopeName = countryIso2Letter + "_" + statePostal2Letter + "_CITIES";
        
        return getGeoCodeByTypeScopeAndAlias(GeoConstants.GeoCodeType_CITY, geoCodeScopeName, cityAlias);
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
