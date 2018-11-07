// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

package com.echothree.model.control.geo.server;

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
import com.echothree.model.control.geo.server.transfer.CityTransferCache;
import com.echothree.model.control.geo.server.transfer.CountryTransferCache;
import com.echothree.model.control.geo.server.transfer.CountyTransferCache;
import com.echothree.model.control.geo.server.transfer.GeoCodeAliasTransferCache;
import com.echothree.model.control.geo.server.transfer.GeoCodeAliasTypeTransferCache;
import com.echothree.model.control.geo.server.transfer.GeoCodeCurrencyTransferCache;
import com.echothree.model.control.geo.server.transfer.GeoCodeDateTimeFormatTransferCache;
import com.echothree.model.control.geo.server.transfer.GeoCodeLanguageTransferCache;
import com.echothree.model.control.geo.server.transfer.GeoCodeScopeTransferCache;
import com.echothree.model.control.geo.server.transfer.GeoCodeTimeZoneTransferCache;
import com.echothree.model.control.geo.server.transfer.GeoCodeTransferCache;
import com.echothree.model.control.geo.server.transfer.GeoCodeTypeTransferCache;
import com.echothree.model.control.geo.server.transfer.GeoTransferCaches;
import com.echothree.model.control.geo.server.transfer.PostalCodeTransferCache;
import com.echothree.model.control.geo.server.transfer.StateTransferCache;
import com.echothree.model.control.item.server.ItemControl;
import com.echothree.model.data.accounting.common.pk.CurrencyPK;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.contact.common.pk.PostalAddressFormatPK;
import com.echothree.model.data.contact.server.entity.PostalAddressFormat;
import com.echothree.model.data.geo.common.pk.GeoCodeAliasTypePK;
import com.echothree.model.data.geo.common.pk.GeoCodePK;
import com.echothree.model.data.geo.common.pk.GeoCodeScopePK;
import com.echothree.model.data.geo.common.pk.GeoCodeTypePK;
import com.echothree.model.data.geo.server.entity.GeoCode;
import com.echothree.model.data.geo.server.entity.GeoCodeAlias;
import com.echothree.model.data.geo.server.entity.GeoCodeAliasType;
import com.echothree.model.data.geo.server.entity.GeoCodeAliasTypeDescription;
import com.echothree.model.data.geo.server.entity.GeoCodeAliasTypeDetail;
import com.echothree.model.data.geo.server.entity.GeoCodeCountry;
import com.echothree.model.data.geo.server.entity.GeoCodeCurrency;
import com.echothree.model.data.geo.server.entity.GeoCodeDateTimeFormat;
import com.echothree.model.data.geo.server.entity.GeoCodeDescription;
import com.echothree.model.data.geo.server.entity.GeoCodeDetail;
import com.echothree.model.data.geo.server.entity.GeoCodeLanguage;
import com.echothree.model.data.geo.server.entity.GeoCodeRelationship;
import com.echothree.model.data.geo.server.entity.GeoCodeScope;
import com.echothree.model.data.geo.server.entity.GeoCodeScopeDescription;
import com.echothree.model.data.geo.server.entity.GeoCodeScopeDetail;
import com.echothree.model.data.geo.server.entity.GeoCodeTimeZone;
import com.echothree.model.data.geo.server.entity.GeoCodeType;
import com.echothree.model.data.geo.server.entity.GeoCodeTypeDescription;
import com.echothree.model.data.geo.server.entity.GeoCodeTypeDetail;
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
import com.echothree.model.data.party.common.pk.DateTimeFormatPK;
import com.echothree.model.data.party.common.pk.LanguagePK;
import com.echothree.model.data.party.common.pk.TimeZonePK;
import com.echothree.model.data.party.server.entity.DateTimeFormat;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.TimeZone;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.exception.PersistenceDatabaseException;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
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
    
    private GeoTransferCaches geoTransferCaches = null;
    
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
    
    public List<CountryTransfer> getCountryTransfers(UserVisit userVisit, List<GeoCode> geoCodes) {
        List<CountryTransfer> countryTransfers = new ArrayList<>(geoCodes.size());
        CountryTransferCache countryTransferCache = getGeoTransferCaches(userVisit).getCountryTransferCache();
        
        geoCodes.stream().forEach((geoCode) -> {
            countryTransfers.add(countryTransferCache.getCountryTransfer(geoCode));
        });
        
        return countryTransfers;
    }
    
    public List<CountryTransfer> getCountryTransfers(UserVisit userVisit) {
        GeoCodeScope geoCodeScope = getGeoCodeScopeByName(GeoConstants.GeoCodeScope_COUNTRIES);
        
        return getCountryTransfers(userVisit, getGeoCodesByGeoCodeScope(geoCodeScope));
    }
    
    public StateTransfer getStateTransfer(UserVisit userVisit, GeoCode geoCode) {
        return getGeoTransferCaches(userVisit).getStateTransferCache().getStateTransfer(geoCode);
    }
    
    public List<StateTransfer> getStateTransfers(UserVisit userVisit, List<GeoCode> geoCodes) {
        List<StateTransfer> stateTransfers = new ArrayList<>(geoCodes.size());
        StateTransferCache stateTransferCache = getGeoTransferCaches(userVisit).getStateTransferCache();
        
        geoCodes.stream().forEach((geoCode) -> {
            stateTransfers.add(stateTransferCache.getStateTransfer(geoCode));
        });
        
        return stateTransfers;
    }
    
    public List<StateTransfer> getStateTransfersByCountry(UserVisit userVisit, GeoCode countryGeoCode) {
        GeoCodeAliasType countryGeoCodeAliasType = getGeoCodeAliasTypeByName(countryGeoCode.getLastDetail().getGeoCodeType(), GeoConstants.GeoCodeAliasType_ISO_2_LETTER);
        GeoCodeAlias countryGeoCodeAlias = getGeoCodeAlias(countryGeoCode, countryGeoCodeAliasType);
        String countryIso2Letter = countryGeoCodeAlias.getAlias();
        String geoCodeScopeName = new StringBuilder().append(countryIso2Letter).append("_STATES").toString();
        GeoCodeScope geoCodeScope = getGeoCodeScopeByName(geoCodeScopeName);
        List<StateTransfer> stateTransfers = null;
        
        if(geoCodeScope != null) {
            stateTransfers = getStateTransfers(userVisit, getGeoCodesByGeoCodeScope(geoCodeScope));
        } else {
            stateTransfers = new ArrayList<>();
        }
        
        return stateTransfers;
    }
    
    public CountyTransfer getCountyTransfer(UserVisit userVisit, GeoCode geoCode) {
        return getGeoTransferCaches(userVisit).getCountyTransferCache().getCountyTransfer(geoCode);
    }
    
    public List<CountyTransfer> getCountyTransfers(UserVisit userVisit, List<GeoCode> geoCodes) {
        List<CountyTransfer> countyTransfers = new ArrayList<>(geoCodes.size());
        CountyTransferCache countyTransferCache = getGeoTransferCaches(userVisit).getCountyTransferCache();
        
        geoCodes.stream().forEach((geoCode) -> {
            countyTransfers.add(countyTransferCache.getCountyTransfer(geoCode));
        });
        
        return countyTransfers;
    }
    
    public List<CountyTransfer> getCountyTransfersByState(UserVisit userVisit, GeoCode stateGeoCode) {
        GeoCodeAliasType stateGeoCodeAliasType = getGeoCodeAliasTypeByName(stateGeoCode.getLastDetail().getGeoCodeType(), GeoConstants.GeoCodeAliasType_POSTAL_2_LETTER);
        GeoCodeAlias stateGeoCodeAlias = getGeoCodeAlias(stateGeoCode, stateGeoCodeAliasType);
        String statePostal2Letter = stateGeoCodeAlias.getAlias();
        
        GeoCodeType countryGeoCodeType = getGeoCodeTypeByName(GeoConstants.GeoCodeType_COUNTRY);
        GeoCode countryGeoCode = null;
        List<GeoCodeRelationship> stateRelationships = getGeoCodeRelationshipsByFromGeoCode(stateGeoCode);
        for(GeoCodeRelationship geoCodeRelationship: stateRelationships) {
            GeoCode toGeoCode = geoCodeRelationship.getToGeoCode();
            
            if(toGeoCode.getLastDetail().getGeoCodeType().equals(countryGeoCodeType)) {
                countryGeoCode = toGeoCode;
                break;
            }
        }
        
        GeoCodeAliasType countryGeoCodeAliasType = getGeoCodeAliasTypeByName(countryGeoCode.getLastDetail().getGeoCodeType(), GeoConstants.GeoCodeAliasType_ISO_2_LETTER);
        GeoCodeAlias countryGeoCodeAlias = getGeoCodeAlias(countryGeoCode, countryGeoCodeAliasType);
        String countryIso2Letter = countryGeoCodeAlias.getAlias();
        
        String geoCodeScopeName = new StringBuilder().append(countryIso2Letter).append("_").append(statePostal2Letter).append("_COUNTIES").toString();
        GeoCodeScope geoCodeScope = getGeoCodeScopeByName(geoCodeScopeName);
        List<CountyTransfer> countyTransfers = null;
        
        if(geoCodeScope != null) {
            countyTransfers = getCountyTransfers(userVisit, getGeoCodesByGeoCodeScope(geoCodeScope));
        } else {
            countyTransfers = new ArrayList<>();
        }
        
        return countyTransfers;
    }
    
    public CityTransfer getCityTransfer(UserVisit userVisit, GeoCode geoCode) {
        return getGeoTransferCaches(userVisit).getCityTransferCache().getCityTransfer(geoCode);
    }
    
    public List<CityTransfer> getCityTransfers(UserVisit userVisit, List<GeoCode> geoCodes) {
        List<CityTransfer> cityTransfers = new ArrayList<>(geoCodes.size());
        CityTransferCache cityTransferCache = getGeoTransferCaches(userVisit).getCityTransferCache();
        
        geoCodes.stream().forEach((geoCode) -> {
            cityTransfers.add(cityTransferCache.getCityTransfer(geoCode));
        });
        
        return cityTransfers;
    }
    
    public List<CityTransfer> getCityTransfersByState(UserVisit userVisit, GeoCode stateGeoCode) {
        GeoCodeAliasType stateGeoCodeAliasType = getGeoCodeAliasTypeByName(stateGeoCode.getLastDetail().getGeoCodeType(), GeoConstants.GeoCodeAliasType_POSTAL_2_LETTER);
        GeoCodeAlias stateGeoCodeAlias = getGeoCodeAlias(stateGeoCode, stateGeoCodeAliasType);
        String statePostal2Letter = stateGeoCodeAlias.getAlias();
        
        GeoCodeType countryGeoCodeType = getGeoCodeTypeByName(GeoConstants.GeoCodeType_COUNTRY);
        GeoCode countryGeoCode = null;
        List<GeoCodeRelationship> stateRelationships = getGeoCodeRelationshipsByFromGeoCode(stateGeoCode);
        for(GeoCodeRelationship geoCodeRelationship: stateRelationships) {
            GeoCode toGeoCode = geoCodeRelationship.getToGeoCode();
            
            if(toGeoCode.getLastDetail().getGeoCodeType().equals(countryGeoCodeType)) {
                countryGeoCode = toGeoCode;
                break;
            }
        }
        
        GeoCodeAliasType countryGeoCodeAliasType = getGeoCodeAliasTypeByName(countryGeoCode.getLastDetail().getGeoCodeType(), GeoConstants.GeoCodeAliasType_ISO_2_LETTER);
        GeoCodeAlias countryGeoCodeAlias = getGeoCodeAlias(countryGeoCode, countryGeoCodeAliasType);
        String countryIso2Letter = countryGeoCodeAlias.getAlias();
        
        String geoCodeScopeName = new StringBuilder().append(countryIso2Letter).append("_").append(statePostal2Letter).append("_CITIES").toString();
        GeoCodeScope geoCodeScope = getGeoCodeScopeByName(geoCodeScopeName);
        List<CityTransfer> cityTransfers = null;
        
        if(geoCodeScope != null) {
            cityTransfers = getCityTransfers(userVisit, getGeoCodesByGeoCodeScope(geoCodeScope));
        } else {
            cityTransfers = new ArrayList<>();
        }
        
        return cityTransfers;
    }
    
    public PostalCodeTransfer getPostalCodeTransfer(UserVisit userVisit, GeoCode geoCode) {
        return getGeoTransferCaches(userVisit).getPostalCodeTransferCache().getPostalCodeTransfer(geoCode);
    }
    
    public List<PostalCodeTransfer> getPostalCodeTransfers(UserVisit userVisit, List<GeoCode> geoCodes) {
        List<PostalCodeTransfer> postalCodeTransfers = new ArrayList<>(geoCodes.size());
        PostalCodeTransferCache postalCodeTransferCache = getGeoTransferCaches(userVisit).getPostalCodeTransferCache();
        
        geoCodes.stream().forEach((geoCode) -> {
            postalCodeTransfers.add(postalCodeTransferCache.getPostalCodeTransfer(geoCode));
        });
        
        return postalCodeTransfers;
    }
    
    public List<PostalCodeTransfer> getPostalCodeTransfersByCountry(UserVisit userVisit, GeoCode countryGeoCode) {
        GeoCodeAliasType countryGeoCodeAliasType = getGeoCodeAliasTypeByName(countryGeoCode.getLastDetail().getGeoCodeType(), GeoConstants.GeoCodeAliasType_ISO_2_LETTER);
        GeoCodeAlias countryGeoCodeAlias = getGeoCodeAlias(countryGeoCode, countryGeoCodeAliasType);
        String countryIso2Letter = countryGeoCodeAlias.getAlias();
        String geoCodeScopeName = new StringBuilder().append(countryIso2Letter).append("_ZIP_CODES").toString();
        GeoCodeScope geoCodeScope = getGeoCodeScopeByName(geoCodeScopeName);
        List<PostalCodeTransfer> postalCodeTransfers = null;
        
        if(geoCodeScope != null) {
            postalCodeTransfers = getPostalCodeTransfers(userVisit, getGeoCodesByGeoCodeScope(geoCodeScope));
        } else {
            postalCodeTransfers = new ArrayList<>();
        }
        
        return postalCodeTransfers;
    }
    
    // --------------------------------------------------------------------------------
    //   Choices Utilities
    // --------------------------------------------------------------------------------
    
    public CountryChoicesBean getCountryChoices(String defaultCountryChoice, Language language, boolean allowNullChoice) {
        GeoCodeType geoCodeType = getGeoCodeTypeByName(GeoConstants.GeoCodeType_COUNTRY);
        GeoCodeAliasType geoCodeAliasType = getDefaultGeoCodeAliasType(geoCodeType);
        GeoCodeScope geoCodeScope = getGeoCodeScopeByName(GeoConstants.GeoCodeScope_COUNTRIES);
        List<GeoCode> geoCodes = getGeoCodesByGeoCodeScope(geoCodeScope);
        int size = geoCodes.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultCountryChoice == null) {
                defaultValue = "";
            }
        }
        
        for(GeoCode geoCode: geoCodes) {
            GeoCodeDetail geoCodeDetail = geoCode.getLastDetail();
            
            String label = getBestGeoCodeDescription(geoCode, language);
            String value = getAliasForCountry(geoCode, geoCodeType, geoCodeAliasType);
            
            labels.add(label == null? value: label);
            values.add(value);
            
            boolean usingDefaultChoice = defaultCountryChoice == null? false: defaultCountryChoice.equals(value);
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
        GeoCodeType defaultGeoCodeType = getDefaultGeoCodeType();
        boolean defaultFound = defaultGeoCodeType != null;
        
        if(defaultFound && isDefault) {
            GeoCodeTypeDetailValue defaultGeoCodeTypeDetailValue = getDefaultGeoCodeTypeDetailValueForUpdate();
            
            defaultGeoCodeTypeDetailValue.setIsDefault(Boolean.FALSE);
            updateGeoCodeTypeFromValue(defaultGeoCodeTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }
        
        GeoCodeType geoCodeType = GeoCodeTypeFactory.getInstance().create();
        GeoCodeTypeDetail geoCodeTypeDetail = GeoCodeTypeDetailFactory.getInstance().create(geoCodeType, geoCodeTypeName,
                parentGeoCodeType, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        geoCodeType = GeoCodeTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                geoCodeType.getPrimaryKey());
        geoCodeType.setActiveDetail(geoCodeTypeDetail);
        geoCodeType.setLastDetail(geoCodeTypeDetail);
        geoCodeType.store();
        
        sendEventUsingNames(geoCodeType.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);
        
        return geoCodeType;
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

    private GeoCodeType getGeoCodeTypeByName(String geoCodeTypeName, EntityPermission entityPermission) {
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

    private GeoCodeType getDefaultGeoCodeType(EntityPermission entityPermission) {
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
    
    public List<GeoCodeTypeTransfer> getGeoCodeTypeTransfers(UserVisit userVisit) {
        List<GeoCodeType> geoCodeTypes = getGeoCodeTypes();
        List<GeoCodeTypeTransfer> geoCodeTypeTransfers = new ArrayList<>(geoCodeTypes.size());
        GeoCodeTypeTransferCache geoCodeTypeTransferCache = getGeoTransferCaches(userVisit).getGeoCodeTypeTransferCache();
        
        geoCodeTypes.stream().forEach((geoCodeType) -> {
            geoCodeTypeTransfers.add(geoCodeTypeTransferCache.getGeoCodeTypeTransfer(geoCodeType));
        });
        
        return geoCodeTypeTransfers;
    }
    
    public GeoCodeTypeChoicesBean getGeoCodeTypeChoices(String defaultGeoCodeTypeChoice, Language language,
            boolean allowNullChoice) {
        List<GeoCodeType> geoCodeTypes = getGeoCodeTypes();
        int size = geoCodeTypes.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultGeoCodeTypeChoice == null) {
                defaultValue = "";
            }
        }
        
        for(GeoCodeType geoCodeType: geoCodeTypes) {
            GeoCodeTypeDetail geoCodeTypeDetail = geoCodeType.getLastDetail();
            
            String label = getBestGeoCodeTypeDescription(geoCodeType, language);
            String value = geoCodeTypeDetail.getGeoCodeTypeName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            boolean usingDefaultChoice = defaultGeoCodeTypeChoice == null? false: defaultGeoCodeTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && geoCodeTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new GeoCodeTypeChoicesBean(labels, values, defaultValue);
    }
    
    public boolean isParentGeoCodeTypeSafe(GeoCodeType geoCodeType, GeoCodeType parentGeoCodeType) {
        boolean safe = true;

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
            GeoCodeType geoCodeType = GeoCodeTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     geoCodeTypeDetailValue.getGeoCodeTypePK());
            GeoCodeTypeDetail geoCodeTypeDetail = geoCodeType.getActiveDetailForUpdate();
            
            geoCodeTypeDetail.setThruTime(session.START_TIME_LONG);
            geoCodeTypeDetail.store();
            
            GeoCodeTypePK geoCodeTypePK = geoCodeTypeDetail.getGeoCodeTypePK();
            String geoCodeTypeName = geoCodeTypeDetailValue.getGeoCodeTypeName();
            GeoCodeTypePK parentGeoCodeTypePK = geoCodeTypeDetailValue.getParentGeoCodeTypePK();
            Boolean isDefault = geoCodeTypeDetailValue.getIsDefault();
            Integer sortOrder = geoCodeTypeDetailValue.getSortOrder();
            
            if(checkDefault) {
                GeoCodeType defaultGeoCodeType = getDefaultGeoCodeType();
                boolean defaultFound = defaultGeoCodeType != null && !defaultGeoCodeType.equals(geoCodeType);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    GeoCodeTypeDetailValue defaultGeoCodeTypeDetailValue = getDefaultGeoCodeTypeDetailValueForUpdate();
                    
                    defaultGeoCodeTypeDetailValue.setIsDefault(Boolean.FALSE);
                    updateGeoCodeTypeFromValue(defaultGeoCodeTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }
            
            geoCodeTypeDetail = GeoCodeTypeDetailFactory.getInstance().create(geoCodeTypePK,
                    geoCodeTypeName, parentGeoCodeTypePK, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            geoCodeType.setActiveDetail(geoCodeTypeDetail);
            geoCodeType.setLastDetail(geoCodeTypeDetail);
            
            sendEventUsingNames(geoCodeTypePK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }
    
    public void updateGeoCodeTypeFromValue(GeoCodeTypeDetailValue geoCodeTypeDetailValue, BasePK updatedBy) {
        updateGeoCodeTypeFromValue(geoCodeTypeDetailValue, true, updatedBy);
    }
    
    private void deleteGeoCodeType(GeoCodeType geoCodeType, boolean checkDefault, BasePK deletedBy) {
        GeoCodeTypeDetail geoCodeTypeDetail = geoCodeType.getLastDetailForUpdate();

        deleteGeoCodeTypesByParentGeoCodeType(geoCodeType, deletedBy);
        // deleteGeoCodesByGeoCodeType(geoCodeType, deletedBy);
        // deleteGeoCodeAliasTypesByGeoCodeType(geoCodeType, deletedBy);
        deleteGeoCodeTypeDescriptionsByGeoCodeType(geoCodeType, deletedBy);
        
        geoCodeTypeDetail.setThruTime(session.START_TIME_LONG);
        geoCodeType.setActiveDetail(null);
        geoCodeType.store();

        if(checkDefault) {
            // Check for default, and pick one if necessary
            GeoCodeType defaultGeoCodeType = getDefaultGeoCodeType();

            if(defaultGeoCodeType == null) {
                List<GeoCodeType> geoCodeTypes = getGeoCodeTypesForUpdate();

                if(!geoCodeTypes.isEmpty()) {
                    Iterator<GeoCodeType> iter = geoCodeTypes.iterator();
                    if(iter.hasNext()) {
                        defaultGeoCodeType = iter.next();
                    }
                    GeoCodeTypeDetailValue geoCodeTypeDetailValue = defaultGeoCodeType.getLastDetailForUpdate().getGeoCodeTypeDetailValue().clone();

                    geoCodeTypeDetailValue.setIsDefault(Boolean.TRUE);
                    updateGeoCodeTypeFromValue(geoCodeTypeDetailValue, false, deletedBy);
                }
            }
        }

        sendEventUsingNames(geoCodeType.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }
    
    public void deleteGeoCodeType(GeoCodeType geoCodeType, BasePK deletedBy) {
        deleteGeoCodeType(geoCodeType, true, deletedBy);
    }

    private void deleteGeoCodeTypes(List<GeoCodeType> geoCodeTypes, boolean checkDefault, BasePK deletedBy) {
        geoCodeTypes.stream().forEach((geoCodeType) -> {
            deleteGeoCodeType(geoCodeType, checkDefault, deletedBy);
        });
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
        GeoCodeTypeDescription geoCodeTypeDescription = GeoCodeTypeDescriptionFactory.getInstance().create(geoCodeType,
                language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(geoCodeType.getPrimaryKey(), EventTypes.MODIFY.name(), geoCodeTypeDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return geoCodeTypeDescription;
    }
    
    private GeoCodeTypeDescription getGeoCodeTypeDescription(GeoCodeType geoCodeType, Language language, EntityPermission entityPermission) {
        GeoCodeTypeDescription geoCodeTypeDescription = null;
        
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
            
            PreparedStatement ps = GeoCodeTypeDescriptionFactory.getInstance().prepareStatement(query);
            
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
        List<GeoCodeTypeDescription> geoCodeTypeDescriptions = null;
        
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
            
            PreparedStatement ps = GeoCodeTypeDescriptionFactory.getInstance().prepareStatement(query);
            
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
        GeoCodeTypeDescription geoCodeTypeDescription = getGeoCodeTypeDescription(geoCodeType, language);
        
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
        List<GeoCodeTypeDescription> geoCodeTypeDescriptions = getGeoCodeTypeDescriptionsByGeoCodeType(geoCodeType);
        List<GeoCodeTypeDescriptionTransfer> geoCodeTypeDescriptionTransfers = new ArrayList<>(geoCodeTypeDescriptions.size());

        geoCodeTypeDescriptions.stream().forEach((geoCodeTypeDescription) -> {
            geoCodeTypeDescriptionTransfers.add(getGeoTransferCaches(userVisit).getGeoCodeTypeDescriptionTransferCache().getGeoCodeTypeDescriptionTransfer(geoCodeTypeDescription));
        });

        return geoCodeTypeDescriptionTransfers;
    }
    
    public void updateGeoCodeTypeDescriptionFromValue(GeoCodeTypeDescriptionValue geoCodeTypeDescriptionValue, BasePK updatedBy) {
        if(geoCodeTypeDescriptionValue.hasBeenModified()) {
            GeoCodeTypeDescription geoCodeTypeDescription = GeoCodeTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, geoCodeTypeDescriptionValue.getPrimaryKey());
            
            geoCodeTypeDescription.setThruTime(session.START_TIME_LONG);
            geoCodeTypeDescription.store();
            
            GeoCodeType geoCodeType = geoCodeTypeDescription.getGeoCodeType();
            Language language = geoCodeTypeDescription.getLanguage();
            String description = geoCodeTypeDescriptionValue.getDescription();
            
            geoCodeTypeDescription = GeoCodeTypeDescriptionFactory.getInstance().create(geoCodeType, language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(geoCodeType.getPrimaryKey(), EventTypes.MODIFY.name(), geoCodeTypeDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteGeoCodeTypeDescription(GeoCodeTypeDescription geoCodeTypeDescription, BasePK deletedBy) {
        geoCodeTypeDescription.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(geoCodeTypeDescription.getGeoCodeTypePK(), EventTypes.MODIFY.name(), geoCodeTypeDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
        
    }
    
    public void deleteGeoCodeTypeDescriptionsByGeoCodeType(GeoCodeType geoCodeType, BasePK deletedBy) {
        List<GeoCodeTypeDescription> geoCodeTypeDescriptions = getGeoCodeTypeDescriptionsByGeoCodeTypeForUpdate(geoCodeType);
        
        geoCodeTypeDescriptions.stream().forEach((geoCodeTypeDescription) -> {
            deleteGeoCodeTypeDescription(geoCodeTypeDescription, deletedBy);
        });
    }
    
    // --------------------------------------------------------------------------------
    //   Geo Code Scopes
    // --------------------------------------------------------------------------------
    
    public GeoCodeScope createGeoCodeScope(String geoCodeScopeName, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        GeoCodeScope defaultGeoCodeScope = getDefaultGeoCodeScope();
        boolean defaultFound = defaultGeoCodeScope != null;
        
        if(defaultFound && isDefault) {
            GeoCodeScopeDetailValue defaultGeoCodeScopeDetailValue = getDefaultGeoCodeScopeDetailValueForUpdate();
            
            defaultGeoCodeScopeDetailValue.setIsDefault(Boolean.FALSE);
            updateGeoCodeScopeFromValue(defaultGeoCodeScopeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }
        
        GeoCodeScope geoCodeScope = GeoCodeScopeFactory.getInstance().create();
        GeoCodeScopeDetail geoCodeScopeDetail = GeoCodeScopeDetailFactory.getInstance().create(session,
                geoCodeScope, geoCodeScopeName, isDefault, sortOrder, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
        // Convert to R/W
        geoCodeScope = GeoCodeScopeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                geoCodeScope.getPrimaryKey());
        geoCodeScope.setActiveDetail(geoCodeScopeDetail);
        geoCodeScope.setLastDetail(geoCodeScopeDetail);
        geoCodeScope.store();
        
        sendEventUsingNames(geoCodeScope.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);
        
        return geoCodeScope;
    }
    
    private GeoCodeScope getGeoCodeScopeByName(String geoCodeScopeName, EntityPermission entityPermission) {
        GeoCodeScope geoCodeScope = null;
        
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
            
            PreparedStatement ps = GeoCodeScopeFactory.getInstance().prepareStatement(query);
            
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
    
    private GeoCodeScope getDefaultGeoCodeScope(EntityPermission entityPermission) {
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
        
        PreparedStatement ps = GeoCodeScopeFactory.getInstance().prepareStatement(query);
        
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
        
        PreparedStatement ps = GeoCodeScopeFactory.getInstance().prepareStatement(query);
        
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
    
    public List<GeoCodeScopeTransfer> getGeoCodeScopeTransfers(UserVisit userVisit) {
        List<GeoCodeScope> geoCodeScopes = getGeoCodeScopes();
        List<GeoCodeScopeTransfer> geoCodeScopeTransfers = new ArrayList<>(geoCodeScopes.size());
        GeoCodeScopeTransferCache geoCodeScopeTransferCache = getGeoTransferCaches(userVisit).getGeoCodeScopeTransferCache();
        
        geoCodeScopes.stream().forEach((geoCodeScope) -> {
            geoCodeScopeTransfers.add(geoCodeScopeTransferCache.getGeoCodeScopeTransfer(geoCodeScope));
        });
        
        return geoCodeScopeTransfers;
    }
    
    public GeoCodeScopeChoicesBean getGeoCodeScopeChoices(String defaultGeoCodeScopeChoice, Language language,
            boolean allowNullChoice) {
        List<GeoCodeScope> geoCodeScopes = getGeoCodeScopes();
        int size = geoCodeScopes.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultGeoCodeScopeChoice == null) {
                defaultValue = "";
            }
        }
        
        for(GeoCodeScope geoCodeScope: geoCodeScopes) {
            GeoCodeScopeDetail geoCodeScopeDetail = geoCodeScope.getLastDetail();
            
            String label = getBestGeoCodeScopeDescription(geoCodeScope, language);
            String value = geoCodeScopeDetail.getGeoCodeScopeName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            boolean usingDefaultChoice = defaultGeoCodeScopeChoice == null? false: defaultGeoCodeScopeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && geoCodeScopeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new GeoCodeScopeChoicesBean(labels, values, defaultValue);
    }
    
    private void updateGeoCodeScopeFromValue(GeoCodeScopeDetailValue geoCodeScopeDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(geoCodeScopeDetailValue.hasBeenModified()) {
            GeoCodeScope geoCodeScope = GeoCodeScopeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     geoCodeScopeDetailValue.getGeoCodeScopePK());
            GeoCodeScopeDetail geoCodeScopeDetail = geoCodeScope.getActiveDetailForUpdate();
            
            geoCodeScopeDetail.setThruTime(session.START_TIME_LONG);
            geoCodeScopeDetail.store();
            
            GeoCodeScopePK geoCodeScopePK = geoCodeScopeDetail.getGeoCodeScopePK();
            String geoCodeScopeName = geoCodeScopeDetailValue.getGeoCodeScopeName();
            Boolean isDefault = geoCodeScopeDetailValue.getIsDefault();
            Integer sortOrder = geoCodeScopeDetailValue.getSortOrder();
            
            if(checkDefault) {
                GeoCodeScope defaultGeoCodeScope = getDefaultGeoCodeScope();
                boolean defaultFound = defaultGeoCodeScope != null && !defaultGeoCodeScope.equals(geoCodeScope);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    GeoCodeScopeDetailValue defaultGeoCodeScopeDetailValue = getDefaultGeoCodeScopeDetailValueForUpdate();
                    
                    defaultGeoCodeScopeDetailValue.setIsDefault(Boolean.FALSE);
                    updateGeoCodeScopeFromValue(defaultGeoCodeScopeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }
            
            geoCodeScopeDetail = GeoCodeScopeDetailFactory.getInstance().create(geoCodeScopePK, geoCodeScopeName,
                    isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            geoCodeScope.setActiveDetail(geoCodeScopeDetail);
            geoCodeScope.setLastDetail(geoCodeScopeDetail);
            
            sendEventUsingNames(geoCodeScopePK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }
    
    public void updateGeoCodeScopeFromValue(GeoCodeScopeDetailValue geoCodeScopeDetailValue, BasePK updatedBy) {
        updateGeoCodeScopeFromValue(geoCodeScopeDetailValue, true, updatedBy);
    }
    
    public void deleteGeoCodeScope(GeoCodeScope geoCodeScope, BasePK deletedBy) {
        // TODO: deleteGeoCodesByGeoCodeScope(geoCodeScope, deletedBy);
        deleteGeoCodeAliasesByGeoCodeScope(geoCodeScope, deletedBy);
        deleteGeoCodeScopeDescriptionsByGeoCodeScope(geoCodeScope, deletedBy);
        
        GeoCodeScopeDetail geoCodeScopeDetail = geoCodeScope.getLastDetailForUpdate();
        geoCodeScopeDetail.setThruTime(session.START_TIME_LONG);
        geoCodeScope.setActiveDetail(null);
        geoCodeScope.store();
        
        // Check for default, and pick one if necessary
        GeoCodeScope defaultGeoCodeScope = getDefaultGeoCodeScope();
        if(defaultGeoCodeScope == null) {
            List<GeoCodeScope> geoCodeScopes = getGeoCodeScopesForUpdate();
            
            if(!geoCodeScopes.isEmpty()) {
                Iterator<GeoCodeScope> iter = geoCodeScopes.iterator();
                if(iter.hasNext()) {
                    defaultGeoCodeScope = iter.next();
                }
                GeoCodeScopeDetailValue geoCodeScopeDetailValue = defaultGeoCodeScope.getLastDetailForUpdate().getGeoCodeScopeDetailValue().clone();
                
                geoCodeScopeDetailValue.setIsDefault(Boolean.TRUE);
                updateGeoCodeScopeFromValue(geoCodeScopeDetailValue, false, deletedBy);
            }
        }
        
        sendEventUsingNames(geoCodeScope.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Geo Code Scope Descriptions
    // --------------------------------------------------------------------------------
    
    public GeoCodeScopeDescription createGeoCodeScopeDescription(GeoCodeScope geoCodeScope, Language language, String description, BasePK createdBy) {
        GeoCodeScopeDescription geoCodeScopeDescription = GeoCodeScopeDescriptionFactory.getInstance().create(geoCodeScope, language, description, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
        sendEventUsingNames(geoCodeScope.getPrimaryKey(), EventTypes.MODIFY.name(), geoCodeScopeDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return geoCodeScopeDescription;
    }
    
    private GeoCodeScopeDescription getGeoCodeScopeDescription(GeoCodeScope geoCodeScope, Language language, EntityPermission entityPermission) {
        GeoCodeScopeDescription geoCodeScopeDescription = null;
        
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
            
            PreparedStatement ps = GeoCodeScopeDescriptionFactory.getInstance().prepareStatement(query);
            
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
        List<GeoCodeScopeDescription> geoCodeScopeDescriptions = null;
        
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
            
            PreparedStatement ps = GeoCodeScopeDescriptionFactory.getInstance().prepareStatement(query);
            
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
        GeoCodeScopeDescription geoCodeScopeDescription = getGeoCodeScopeDescription(geoCodeScope, language);
        
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
        List<GeoCodeScopeDescription> geoCodeScopeDescriptions = getGeoCodeScopeDescriptionsByGeoCodeScope(geoCodeScope);
        List<GeoCodeScopeDescriptionTransfer> geoCodeScopeDescriptionTransfers = new ArrayList<>(geoCodeScopeDescriptions.size());

        geoCodeScopeDescriptions.stream().forEach((geoCodeScopeDescription) -> {
            geoCodeScopeDescriptionTransfers.add(getGeoTransferCaches(userVisit).getGeoCodeScopeDescriptionTransferCache().getGeoCodeScopeDescriptionTransfer(geoCodeScopeDescription));
        });

        return geoCodeScopeDescriptionTransfers;
    }
    
    public void updateGeoCodeScopeDescriptionFromValue(GeoCodeScopeDescriptionValue geoCodeScopeDescriptionValue, BasePK updatedBy) {
        if(geoCodeScopeDescriptionValue.hasBeenModified()) {
            GeoCodeScopeDescription geoCodeScopeDescription = GeoCodeScopeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, geoCodeScopeDescriptionValue.getPrimaryKey());
            
            geoCodeScopeDescription.setThruTime(session.START_TIME_LONG);
            geoCodeScopeDescription.store();
            
            GeoCodeScope geoCodeScope = geoCodeScopeDescription.getGeoCodeScope();
            Language language = geoCodeScopeDescription.getLanguage();
            String description = geoCodeScopeDescriptionValue.getDescription();
            
            geoCodeScopeDescription = GeoCodeScopeDescriptionFactory.getInstance().create(geoCodeScope, language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(geoCodeScope.getPrimaryKey(), EventTypes.MODIFY.name(), geoCodeScopeDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteGeoCodeScopeDescription(GeoCodeScopeDescription geoCodeScopeDescription, BasePK deletedBy) {
        geoCodeScopeDescription.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(geoCodeScopeDescription.getGeoCodeScopePK(), EventTypes.MODIFY.name(), geoCodeScopeDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
        
    }
    
    public void deleteGeoCodeScopeDescriptionsByGeoCodeScope(GeoCodeScope geoCodeScope, BasePK deletedBy) {
        List<GeoCodeScopeDescription> geoCodeScopeDescriptions = getGeoCodeScopeDescriptionsByGeoCodeScopeForUpdate(geoCodeScope);
        
        geoCodeScopeDescriptions.stream().forEach((geoCodeScopeDescription) -> {
            deleteGeoCodeScopeDescription(geoCodeScopeDescription, deletedBy);
        });
    }
    
    // --------------------------------------------------------------------------------
    //   Geo Code Alias Types
    // --------------------------------------------------------------------------------
    
    public GeoCodeAliasType createGeoCodeAliasType(GeoCodeType geoCodeType, String geoCodeAliasTypeName, String validationPattern, Boolean isRequired,
            Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        GeoCodeAliasType defaultGeoCodeAliasType = getDefaultGeoCodeAliasType(geoCodeType);
        boolean defaultFound = defaultGeoCodeAliasType != null;
        
        if(defaultFound && isDefault) {
            GeoCodeAliasTypeDetailValue defaultGeoCodeAliasTypeDetailValue = getDefaultGeoCodeAliasTypeDetailValueForUpdate(geoCodeType);
            
            defaultGeoCodeAliasTypeDetailValue.setIsDefault(Boolean.FALSE);
            updateGeoCodeAliasTypeFromValue(defaultGeoCodeAliasTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }
        
        GeoCodeAliasType geoCodeAliasType = GeoCodeAliasTypeFactory.getInstance().create();
        GeoCodeAliasTypeDetail geoCodeAliasTypeDetail = GeoCodeAliasTypeDetailFactory.getInstance().create(session, geoCodeAliasType, geoCodeType,
                geoCodeAliasTypeName, validationPattern, isRequired, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        geoCodeAliasType = GeoCodeAliasTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                geoCodeAliasType.getPrimaryKey());
        geoCodeAliasType.setActiveDetail(geoCodeAliasTypeDetail);
        geoCodeAliasType.setLastDetail(geoCodeAliasTypeDetail);
        geoCodeAliasType.store();
        
        sendEventUsingNames(geoCodeAliasType.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);
        
        return geoCodeAliasType;
    }
    
    private GeoCodeAliasType getGeoCodeAliasTypeByName(GeoCodeType geoCodeType, String geoCodeAliasTypeName, EntityPermission entityPermission) {
        GeoCodeAliasType geoCodeAliasType = null;
        
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
            
            PreparedStatement ps = GeoCodeAliasTypeFactory.getInstance().prepareStatement(query);
            
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
        GeoCodeAliasType geoCodeAliasType = null;
        
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
            
            PreparedStatement ps = GeoCodeAliasTypeFactory.getInstance().prepareStatement(query);
            
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
        List<GeoCodeAliasType> geoCodeAliasTypes = null;
        
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
            
            PreparedStatement ps = GeoCodeAliasTypeFactory.getInstance().prepareStatement(query);
            
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
        List<GeoCodeAliasType> geoCodeAliasTypes = null;
        
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
            
            PreparedStatement ps = GeoCodeAliasTypeFactory.getInstance().prepareStatement(query);
            
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
        List<GeoCodeAliasType> geoCodeAliasTypes = getGeoCodeAliasTypes(geoCodeType);
        List<GeoCodeAliasTypeTransfer> geoCodeAliasTypeTransfers = new ArrayList<>(geoCodeAliasTypes.size());
        GeoCodeAliasTypeTransferCache geoCodeAliasTypeTransferCache = getGeoTransferCaches(userVisit).getGeoCodeAliasTypeTransferCache();
        
        geoCodeAliasTypes.stream().forEach((geoCodeAliasType) -> {
            geoCodeAliasTypeTransfers.add(geoCodeAliasTypeTransferCache.getGeoCodeAliasTypeTransfer(geoCodeAliasType));
        });
        
        return geoCodeAliasTypeTransfers;
    }
    
    public GeoCodeAliasTypeChoicesBean getGeoCodeAliasTypeChoices(String defaultGeoCodeAliasTypeChoice, Language language,
            boolean allowNullChoice, GeoCodeType geoCodeType) {
        List<GeoCodeAliasType> geoCodeAliasTypes = getGeoCodeAliasTypes(geoCodeType);
        int size = geoCodeAliasTypes.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultGeoCodeAliasTypeChoice == null) {
                defaultValue = "";
            }
        }
        
        for(GeoCodeAliasType geoCodeAliasType: geoCodeAliasTypes) {
            GeoCodeAliasTypeDetail geoCodeAliasTypeDetail = geoCodeAliasType.getLastDetail();
            
            String label = getBestGeoCodeAliasTypeDescription(geoCodeAliasType, language);
            String value = geoCodeAliasTypeDetail.getGeoCodeAliasTypeName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            boolean usingDefaultChoice = defaultGeoCodeAliasTypeChoice == null? false: defaultGeoCodeAliasTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && geoCodeAliasTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new GeoCodeAliasTypeChoicesBean(labels, values, defaultValue);
    }
    
    private void updateGeoCodeAliasTypeFromValue(GeoCodeAliasTypeDetailValue geoCodeAliasTypeDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(geoCodeAliasTypeDetailValue.hasBeenModified()) {
            GeoCodeAliasType geoCodeAliasType = GeoCodeAliasTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     geoCodeAliasTypeDetailValue.getGeoCodeAliasTypePK());
            GeoCodeAliasTypeDetail geoCodeAliasTypeDetail = geoCodeAliasType.getActiveDetailForUpdate();
            
            geoCodeAliasTypeDetail.setThruTime(session.START_TIME_LONG);
            geoCodeAliasTypeDetail.store();
            
            GeoCodeAliasTypePK geoCodeAliasTypePK = geoCodeAliasTypeDetail.getGeoCodeAliasTypePK();
            GeoCodeType geoCodeType = geoCodeAliasTypeDetail.getGeoCodeType();
            GeoCodeTypePK geoCodeTypePK = geoCodeType.getPrimaryKey();
            String geoCodeAliasTypeName = geoCodeAliasTypeDetailValue.getGeoCodeAliasTypeName();
            String validationPattern = geoCodeAliasTypeDetailValue.getValidationPattern();
            Boolean isRequired = geoCodeAliasTypeDetailValue.getIsRequired();
            Boolean isDefault = geoCodeAliasTypeDetailValue.getIsDefault();
            Integer sortOrder = geoCodeAliasTypeDetailValue.getSortOrder();
            
            if(checkDefault) {
                GeoCodeAliasType defaultGeoCodeAliasType = getDefaultGeoCodeAliasType(geoCodeType);
                boolean defaultFound = defaultGeoCodeAliasType != null && !defaultGeoCodeAliasType.equals(geoCodeAliasType);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    GeoCodeAliasTypeDetailValue defaultGeoCodeAliasTypeDetailValue = getDefaultGeoCodeAliasTypeDetailValueForUpdate(geoCodeType);
                    
                    defaultGeoCodeAliasTypeDetailValue.setIsDefault(Boolean.FALSE);
                    updateGeoCodeAliasTypeFromValue(defaultGeoCodeAliasTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }
            
            geoCodeAliasTypeDetail = GeoCodeAliasTypeDetailFactory.getInstance().create(geoCodeAliasTypePK, geoCodeTypePK, geoCodeAliasTypeName,
                    validationPattern, isRequired, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            geoCodeAliasType.setActiveDetail(geoCodeAliasTypeDetail);
            geoCodeAliasType.setLastDetail(geoCodeAliasTypeDetail);
            
            sendEventUsingNames(geoCodeAliasTypePK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }
    
    public void updateGeoCodeAliasTypeFromValue(GeoCodeAliasTypeDetailValue geoCodeAliasTypeDetailValue, BasePK updatedBy) {
        updateGeoCodeAliasTypeFromValue(geoCodeAliasTypeDetailValue, true, updatedBy);
    }
    
    public void deleteGeoCodeAliasType(GeoCodeAliasType geoCodeAliasType, BasePK deletedBy) {
        deleteGeoCodeAliasesByGeoCodeAliasType(geoCodeAliasType, deletedBy);
        deleteGeoCodeAliasTypeDescriptionsByGeoCodeAliasType(geoCodeAliasType, deletedBy);
        
        GeoCodeAliasTypeDetail geoCodeAliasTypeDetail = geoCodeAliasType.getLastDetailForUpdate();
        geoCodeAliasTypeDetail.setThruTime(session.START_TIME_LONG);
        geoCodeAliasType.setActiveDetail(null);
        geoCodeAliasType.store();
        
        // Check for default, and pick one if necessary
        GeoCodeType geoCodeType = geoCodeAliasTypeDetail.getGeoCodeType();
        GeoCodeAliasType defaultGeoCodeAliasType = getDefaultGeoCodeAliasType(geoCodeType);
        if(defaultGeoCodeAliasType == null) {
            List<GeoCodeAliasType> geoCodeAliasTypes = getGeoCodeAliasTypesForUpdate(geoCodeType);
            
            if(!geoCodeAliasTypes.isEmpty()) {
                Iterator<GeoCodeAliasType> iter = geoCodeAliasTypes.iterator();
                if(iter.hasNext()) {
                    defaultGeoCodeAliasType = iter.next();
                }
                GeoCodeAliasTypeDetailValue geoCodeAliasTypeDetailValue = defaultGeoCodeAliasType.getLastDetailForUpdate().getGeoCodeAliasTypeDetailValue().clone();
                
                geoCodeAliasTypeDetailValue.setIsDefault(Boolean.TRUE);
                updateGeoCodeAliasTypeFromValue(geoCodeAliasTypeDetailValue, false, deletedBy);
            }
        }
        
        sendEventUsingNames(geoCodeAliasType.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Geo Code Alias Type Descriptions
    // --------------------------------------------------------------------------------
    
    public GeoCodeAliasTypeDescription createGeoCodeAliasTypeDescription(GeoCodeAliasType geoCodeAliasType, Language language, String description, BasePK createdBy) {
        GeoCodeAliasTypeDescription geoCodeAliasTypeDescription = GeoCodeAliasTypeDescriptionFactory.getInstance().create(geoCodeAliasType, language, description,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(geoCodeAliasType.getPrimaryKey(), EventTypes.MODIFY.name(), geoCodeAliasTypeDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return geoCodeAliasTypeDescription;
    }
    
    private GeoCodeAliasTypeDescription getGeoCodeAliasTypeDescription(GeoCodeAliasType geoCodeAliasType, Language language, EntityPermission entityPermission) {
        GeoCodeAliasTypeDescription geoCodeAliasTypeDescription = null;
        
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
            
            PreparedStatement ps = GeoCodeAliasTypeDescriptionFactory.getInstance().prepareStatement(query);
            
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
        List<GeoCodeAliasTypeDescription> geoCodeAliasTypeDescriptions = null;
        
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
            
            PreparedStatement ps = GeoCodeAliasTypeDescriptionFactory.getInstance().prepareStatement(query);
            
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
        GeoCodeAliasTypeDescription geoCodeAliasTypeDescription = getGeoCodeAliasTypeDescription(geoCodeAliasType, language);
        
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
        List<GeoCodeAliasTypeDescription> geoCodeAliasTypeDescriptions = getGeoCodeAliasTypeDescriptionsByGeoCodeAliasType(geoCodeAliasType);
        List<GeoCodeAliasTypeDescriptionTransfer> geoCodeAliasTypeDescriptionTransfers = new ArrayList<>(geoCodeAliasTypeDescriptions.size());

        geoCodeAliasTypeDescriptions.stream().forEach((geoCodeAliasTypeDescription) -> {
            geoCodeAliasTypeDescriptionTransfers.add(getGeoTransferCaches(userVisit).getGeoCodeAliasTypeDescriptionTransferCache().getGeoCodeAliasTypeDescriptionTransfer(geoCodeAliasTypeDescription));
        });
        
        return geoCodeAliasTypeDescriptionTransfers;
    }
    
    public void updateGeoCodeAliasTypeDescriptionFromValue(GeoCodeAliasTypeDescriptionValue geoCodeAliasTypeDescriptionValue, BasePK updatedBy) {
        if(geoCodeAliasTypeDescriptionValue.hasBeenModified()) {
            GeoCodeAliasTypeDescription geoCodeAliasTypeDescription = GeoCodeAliasTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, geoCodeAliasTypeDescriptionValue.getPrimaryKey());
            
            geoCodeAliasTypeDescription.setThruTime(session.START_TIME_LONG);
            geoCodeAliasTypeDescription.store();
            
            GeoCodeAliasType geoCodeAliasType = geoCodeAliasTypeDescription.getGeoCodeAliasType();
            Language language = geoCodeAliasTypeDescription.getLanguage();
            String description = geoCodeAliasTypeDescriptionValue.getDescription();
            
            geoCodeAliasTypeDescription = GeoCodeAliasTypeDescriptionFactory.getInstance().create(geoCodeAliasType, language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(geoCodeAliasType.getPrimaryKey(), EventTypes.MODIFY.name(), geoCodeAliasTypeDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteGeoCodeAliasTypeDescription(GeoCodeAliasTypeDescription geoCodeAliasTypeDescription, BasePK deletedBy) {
        geoCodeAliasTypeDescription.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(geoCodeAliasTypeDescription.getGeoCodeAliasTypePK(), EventTypes.MODIFY.name(), geoCodeAliasTypeDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
        
    }
    
    public void deleteGeoCodeAliasTypeDescriptionsByGeoCodeAliasType(GeoCodeAliasType geoCodeAliasType, BasePK deletedBy) {
        List<GeoCodeAliasTypeDescription> geoCodeAliasTypeDescriptions = getGeoCodeAliasTypeDescriptionsByGeoCodeAliasTypeForUpdate(geoCodeAliasType);
        
        geoCodeAliasTypeDescriptions.stream().forEach((geoCodeAliasTypeDescription) -> {
            deleteGeoCodeAliasTypeDescription(geoCodeAliasTypeDescription, deletedBy);
        });
    }
    
    // --------------------------------------------------------------------------------
    //   Geo Codes
    // --------------------------------------------------------------------------------
    
    public GeoCode createGeoCode(String geoCodeName, GeoCodeType geoCodeType, GeoCodeScope geoCodeScope, Boolean isDefault,
            Integer sortOrder, BasePK createdBy) {
        GeoCode defaultGeoCode = getDefaultGeoCode(geoCodeScope);
        boolean defaultFound = defaultGeoCode != null;
        
        if(defaultFound && isDefault) {
            GeoCodeDetailValue defaultGeoCodeDetailValue = getDefaultGeoCodeDetailValueForUpdate(geoCodeScope);
            
            defaultGeoCodeDetailValue.setIsDefault(Boolean.FALSE);
            updateGeoCodeFromValue(defaultGeoCodeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }
        
        GeoCode geoCode = GeoCodeFactory.getInstance().create();
        GeoCodeDetail geoCodeDetail = GeoCodeDetailFactory.getInstance().create(geoCode, geoCodeName, geoCodeType,
                geoCodeScope, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        geoCode = GeoCodeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, geoCode.getPrimaryKey());
        geoCode.setActiveDetail(geoCodeDetail);
        geoCode.setLastDetail(geoCodeDetail);
        geoCode.store();
        
        sendEventUsingNames(geoCode.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);
        
        return geoCode;
    }
    
    public GeoCodeDetailValue getGeoCodeDetailValueForUpdate(GeoCode geoCode) {
        return geoCode == null? null: geoCode.getLastDetailForUpdate().getGeoCodeDetailValue().clone();
    }
    
    private GeoCode getDefaultGeoCode(GeoCodeScope geoCodeScope, EntityPermission entityPermission) {
        GeoCode geoCode = null;
        
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
            
            PreparedStatement ps = GeoCodeFactory.getInstance().prepareStatement(query);
            
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
    
    private GeoCode getGeoCodeByName(String geoCodeName, EntityPermission entityPermission) {
        GeoCode geoCode = null;
        
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
            
            PreparedStatement ps = GeoCodeFactory.getInstance().prepareStatement(query);
            
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
        List<GeoCode> geoCode = null;
        
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
            
            PreparedStatement ps = GeoCodeFactory.getInstance().prepareStatement(query);
            
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
    
    public List<GeoCodeTransfer> getGeoCodeTransfers(UserVisit userVisit, List<GeoCode> geoCodes) {
        List<GeoCodeTransfer> geoCodeTransfers = new ArrayList<>(geoCodes.size());
        GeoCodeTransferCache geoCodeTransferCache = getGeoTransferCaches(userVisit).getGeoCodeTransferCache();
        
        geoCodes.stream().forEach((geoCode) -> {
            geoCodeTransfers.add(geoCodeTransferCache.getGeoCodeTransfer(geoCode));
        });
        
        return geoCodeTransfers;
    }
    
    public List<GeoCodeTransfer> getGeoCodeTransfersByGeoCodeScope(UserVisit userVisit, GeoCodeScope geoCodeScope) {
        return getGeoCodeTransfers(userVisit, getGeoCodesByGeoCodeScope(geoCodeScope));
    }
    
    private void updateGeoCodeFromValue(GeoCodeDetailValue geoCodeDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(geoCodeDetailValue.hasBeenModified()) {
            GeoCode geoCode = GeoCodeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     geoCodeDetailValue.getGeoCodePK());
            GeoCodeDetail geoCodeDetail = geoCode.getActiveDetailForUpdate();
            
            geoCodeDetail.setThruTime(session.START_TIME_LONG);
            geoCodeDetail.store();
            
            GeoCodePK geoCodePK = geoCodeDetail.getGeoCodePK(); // Not updated
            String geoCodeName = geoCodeDetail.getGeoCodeName(); // Not updated
            GeoCodeTypePK geoCodeTypePK = geoCodeDetail.getGeoCodeTypePK(); // Not updated
            GeoCodeScope geoCodeScope = geoCodeDetail.getGeoCodeScope(); // Not updated
            Boolean isDefault = geoCodeDetailValue.getIsDefault();
            Integer sortOrder = geoCodeDetailValue.getSortOrder();
            
            if(checkDefault) {
                GeoCode defaultGeoCode = getDefaultGeoCode(geoCodeScope);
                boolean defaultFound = defaultGeoCode != null && !defaultGeoCode.equals(geoCode);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    GeoCodeDetailValue defaultGeoCodeDetailValue = getDefaultGeoCodeDetailValueForUpdate(geoCodeScope);
                    
                    defaultGeoCodeDetailValue.setIsDefault(Boolean.FALSE);
                    updateGeoCodeFromValue(defaultGeoCodeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }
            
            geoCodeDetail = GeoCodeDetailFactory.getInstance().create(geoCodePK, geoCodeName, geoCodeTypePK, geoCodeScope.getPrimaryKey(), isDefault,
                    sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            geoCode.setActiveDetail(geoCodeDetail);
            geoCode.setLastDetail(geoCodeDetail);
            
            sendEventUsingNames(geoCodePK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }
    
    public void updateGeoCodeFromValue(GeoCodeDetailValue geoCodeDetailValue, BasePK updatedBy) {
        updateGeoCodeFromValue(geoCodeDetailValue, true, updatedBy);
    }

    public void deleteGeoCode(GeoCode geoCode, BasePK deletedBy) {
        GeoCodeDetail geoCodeDetail = geoCode.getLastDetailForUpdate();

        deleteGeoCodeDescriptionsByGeoCode(geoCode, deletedBy);
        deleteGeoCodeAliasesByGeoCode(geoCode, deletedBy);
        deleteGeoCodeRelationshipsByGeoCode(geoCode, deletedBy);
        deleteGeoCodeLanguagesByGeoCode(geoCode, deletedBy);
        deleteGeoCodeCurrenciesByGeoCode(geoCode, deletedBy);
        deleteGeoCodeTimeZonesByGeoCode(geoCode, deletedBy);
        deleteGeoCodeDateTimeFormatsByGeoCode(geoCode, deletedBy);

        if(geoCodeDetail.getGeoCodeType().getLastDetail().getGeoCodeTypeName().equals(GeoConstants.GeoCodeType_COUNTRY)) {
            ItemControl itemControl = (ItemControl)Session.getModelController(ItemControl.class);
            
            itemControl.deleteHarmonizedTariffScheduleCodesByCountryGeoCode(geoCode, deletedBy);
            itemControl.deleteItemHarmonizedTariffScheduleCodesByCountryGeoCode(geoCode, deletedBy);
            
            deleteGeoCodeCountryByGeoCode(geoCode, deletedBy);
        }

        geoCodeDetail.setThruTime(session.START_TIME_LONG);
        geoCode.setActiveDetail(null);
        geoCode.store();

        // Check for default, and pick one if necessary
        GeoCodeScope geoCodeScope = geoCodeDetail.getGeoCodeScope();
        GeoCode defaultGeoCode = getDefaultGeoCode(geoCodeScope);
        if(defaultGeoCode == null) {
            List<GeoCode> geoCodes = getGeoCodesByGeoCodeScopeForUpdate(geoCodeScope);

            if(!geoCodes.isEmpty()) {
                Iterator<GeoCode> iter = geoCodes.iterator();
                if(iter.hasNext()) {
                    defaultGeoCode = iter.next();
                }
                GeoCodeDetailValue geoCodeDetailValue = defaultGeoCode.getLastDetailForUpdate().getGeoCodeDetailValue().clone();

                geoCodeDetailValue.setIsDefault(Boolean.TRUE);
                updateGeoCodeFromValue(geoCodeDetailValue, false, deletedBy);
            }
        }

        sendEventUsingNames(geoCode.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Geo Code Descriptions
    // --------------------------------------------------------------------------------
    
    public GeoCodeDescription createGeoCodeDescription(GeoCode geoCode, Language language, String description, BasePK createdBy) {
        GeoCodeDescription geoCodeDescription = GeoCodeDescriptionFactory.getInstance().create(geoCode, language, description, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
        sendEventUsingNames(geoCode.getPrimaryKey(), EventTypes.MODIFY.name(), geoCodeDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return geoCodeDescription;
    }
    
    private GeoCodeDescription getGeoCodeDescription(GeoCode geoCode, Language language, EntityPermission entityPermission) {
        GeoCodeDescription geoCodeDescription = null;
        
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
            
            PreparedStatement ps = GeoCodeDescriptionFactory.getInstance().prepareStatement(query);
            
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
        List<GeoCodeDescription> geoCodeDescriptions = null;
        
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
            
            PreparedStatement ps = GeoCodeDescriptionFactory.getInstance().prepareStatement(query);
            
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
        GeoCodeDescription geoCodeDescription = getGeoCodeDescription(geoCode, language);
        
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
        List<GeoCodeDescription> geoCodeDescriptions = getGeoCodeDescriptionsByGeoCode(geoCode);
        List<GeoCodeDescriptionTransfer> geoCodeDescriptionTransfers = new ArrayList<>(geoCodeDescriptions.size());

        geoCodeDescriptions.stream().forEach((geoCodeDescription) -> {
            geoCodeDescriptionTransfers.add(getGeoTransferCaches(userVisit).getGeoCodeDescriptionTransferCache().getGeoCodeDescriptionTransfer(geoCodeDescription));
        });

        return geoCodeDescriptionTransfers;
    }
    
    public void updateGeoCodeDescriptionFromValue(GeoCodeDescriptionValue geoCodeDescriptionValue, BasePK updatedBy) {
        if(geoCodeDescriptionValue.hasBeenModified()) {
            GeoCodeDescription geoCodeDescription = GeoCodeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, geoCodeDescriptionValue.getPrimaryKey());
            
            geoCodeDescription.setThruTime(session.START_TIME_LONG);
            geoCodeDescription.store();
            
            GeoCode geoCode = geoCodeDescription.getGeoCode();
            Language language = geoCodeDescription.getLanguage();
            String description = geoCodeDescriptionValue.getDescription();
            
            geoCodeDescription = GeoCodeDescriptionFactory.getInstance().create(geoCode, language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(geoCode.getPrimaryKey(), EventTypes.MODIFY.name(), geoCodeDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteGeoCodeDescription(GeoCodeDescription geoCodeDescription, BasePK deletedBy) {
        geoCodeDescription.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(geoCodeDescription.getGeoCodePK(), EventTypes.MODIFY.name(), geoCodeDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
        
    }
    
    public void deleteGeoCodeDescriptionsByGeoCode(GeoCode geoCode, BasePK deletedBy) {
        List<GeoCodeDescription> geoCodeDescriptions = getGeoCodeDescriptionsByGeoCodeForUpdate(geoCode);
        
        geoCodeDescriptions.stream().forEach((geoCodeDescription) -> {
            deleteGeoCodeDescription(geoCodeDescription, deletedBy);
        });
    }
    
    // --------------------------------------------------------------------------------
    //   Geo Code Aliases
    // --------------------------------------------------------------------------------
    
    public GeoCodeAlias createGeoCodeAlias(GeoCode geoCode, GeoCodeAliasType geoCodeAliasType, String alias, BasePK createdBy) {
        GeoCodeAlias geoCodeAlias = GeoCodeAliasFactory.getInstance().create(geoCode, geoCode.getLastDetail().getGeoCodeScope(), geoCodeAliasType, alias,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(geoCode.getPrimaryKey(), EventTypes.MODIFY.name(), geoCodeAlias.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
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
    
    public List<GeoCodeAliasTransfer> getGeoCodeAliasTransfers(UserVisit userVisit, List<GeoCodeAlias> geoCodeAliases) {
        List<GeoCodeAliasTransfer> geoCodeAliasTransfers = new ArrayList<>(geoCodeAliases.size());
        GeoCodeAliasTransferCache geoCodeAliasTransferCache = getGeoTransferCaches(userVisit).getGeoCodeAliasTransferCache();
        
        geoCodeAliases.stream().forEach((geoCodeAlias) -> {
            geoCodeAliasTransfers.add(geoCodeAliasTransferCache.getGeoCodeAliasTransfer(geoCodeAlias));
        });
        
        return geoCodeAliasTransfers;
    }
    
    public List<GeoCodeAliasTransfer> getGeoCodeAliasTransfersByGeoCode(UserVisit userVisit, GeoCode geoCode) {
        return getGeoCodeAliasTransfers(userVisit, getGeoCodeAliasesByGeoCode(geoCode));
    }

    public void updateGeoCodeAliasFromValue(GeoCodeAliasValue geoCodeAliasValue, BasePK updatedBy) {
        if(geoCodeAliasValue.hasBeenModified()) {
            GeoCodeAlias geoCodeAlias = GeoCodeAliasFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    geoCodeAliasValue.getPrimaryKey());

            geoCodeAlias.setThruTime(session.START_TIME_LONG);
            geoCodeAlias.store();

            GeoCodePK geoCodePK = geoCodeAlias.getGeoCodePK(); // Not updated
            GeoCodeScopePK geoCodeScopePK = geoCodeAlias.getGeoCodeScopePK(); // Not updated
            GeoCodeAliasTypePK geoCodeAliasTypePK = geoCodeAlias.getGeoCodeAliasTypePK(); // Not updated
            String alias = geoCodeAliasValue.getAlias();

            geoCodeAlias = GeoCodeAliasFactory.getInstance().create(geoCodePK, geoCodeScopePK, geoCodeAliasTypePK, alias, session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);

            sendEventUsingNames(geoCodePK, EventTypes.MODIFY.name(), geoCodeAlias.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }

    public void deleteGeoCodeAlias(GeoCodeAlias geoCodeAlias, BasePK deletedBy) {
        geoCodeAlias.setThruTime(session.START_TIME_LONG);

        sendEventUsingNames(geoCodeAlias.getGeoCodePK(), EventTypes.MODIFY.name(), geoCodeAlias.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }

    public void deleteGeoCodeAliases(List<GeoCodeAlias> geoCodeAliases, BasePK deletedBy) {
        geoCodeAliases.stream().forEach((geoCodeAlias) -> {
            deleteGeoCodeAlias(geoCodeAlias, deletedBy);
        });
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
        GeoCodeRelationship geoCodeRelationship = GeoCodeRelationshipFactory.getInstance().create(fromGeoCode, toGeoCode,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(fromGeoCode.getPrimaryKey(), EventTypes.MODIFY.name(), geoCodeRelationship.getPrimaryKey(), null, createdBy);
        
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
        GeoCodeRelationship geoCodeRelationship = null;
        
        try {
            PreparedStatement ps = GeoCodeRelationshipFactory.getInstance().prepareStatement(
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
        List<GeoCodeRelationship> geoCodeRelationships = null;
        
        try {
            PreparedStatement ps = GeoCodeRelationshipFactory.getInstance().prepareStatement(
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
        geoCodeRelationships.stream().forEach((geoCodeRelationship) -> {
            deleteGeoCodeRelationship(geoCodeRelationship, deletedBy);
        });
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
        GeoCodeLanguage defaultGeoCodeLanguage = getDefaultGeoCodeLanguage(geoCode);
        boolean defaultFound = defaultGeoCodeLanguage != null;
        
        if(defaultFound && isDefault) {
            GeoCodeLanguageValue defaultGeoCodeLanguageValue = getDefaultGeoCodeLanguageValueForUpdate(geoCode);
            
            defaultGeoCodeLanguageValue.setIsDefault(Boolean.FALSE);
            updateGeoCodeLanguageFromValue(defaultGeoCodeLanguageValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }
        
        GeoCodeLanguage geoCodeLanguage = GeoCodeLanguageFactory.getInstance().create(geoCode, language, isDefault,
                sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(geoCode.getPrimaryKey(), EventTypes.MODIFY.name(), geoCodeLanguage.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return geoCodeLanguage;
    }
    
    private GeoCodeLanguage getGeoCodeLanguage(GeoCode geoCode, Language language, EntityPermission entityPermission) {
        GeoCodeLanguage geoCodeLanguage = null;
        
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
            
            PreparedStatement ps = GeoCodeLanguageFactory.getInstance().prepareStatement(query);
            
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
        GeoCodeLanguage geoCodeLanguage = null;
        
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
            
            PreparedStatement ps = GeoCodeLanguageFactory.getInstance().prepareStatement(query);
            
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
        GeoCodeLanguage geoCodeLanguage = getDefaultGeoCodeLanguageForUpdate(geoCode);
        
        return geoCodeLanguage == null? null: geoCodeLanguage.getGeoCodeLanguageValue().clone();
    }
    
    private List<GeoCodeLanguage> getGeoCodeLanguagesByGeoCode(GeoCode geoCode, EntityPermission entityPermission) {
        List<GeoCodeLanguage> geoCodeLanguages = null;
        
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
            
            PreparedStatement ps = GeoCodeLanguageFactory.getInstance().prepareStatement(query);
            
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
        List<GeoCodeLanguage> geoCodeLanguages = null;
        
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
            
            PreparedStatement ps = GeoCodeLanguageFactory.getInstance().prepareStatement(query);
            
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
    
    public List<GeoCodeLanguageTransfer> getGeoCodeLanguageTransfers(UserVisit userVisit, List<GeoCodeLanguage> geoCodeLanguages) {
        List<GeoCodeLanguageTransfer> geoCodeLanguageTransfers = new ArrayList<>(geoCodeLanguages.size());
        GeoCodeLanguageTransferCache geoCodeLanguageTransferCache = getGeoTransferCaches(userVisit).getGeoCodeLanguageTransferCache();
        
        geoCodeLanguages.stream().forEach((geoCodeLanguage) -> {
            geoCodeLanguageTransfers.add(geoCodeLanguageTransferCache.getGeoCodeLanguageTransfer(geoCodeLanguage));
        });
        
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
            GeoCodeLanguage geoCodeLanguage = GeoCodeLanguageFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     geoCodeLanguageValue.getPrimaryKey());
            
            geoCodeLanguage.setThruTime(session.START_TIME_LONG);
            geoCodeLanguage.store();
            
            GeoCode geoCode = geoCodeLanguage.getGeoCode(); // Not Updated
            GeoCodePK geoCodePK = geoCode.getPrimaryKey(); // Not Updated
            LanguagePK languagePK = geoCodeLanguage.getLanguagePK(); // Not Updated
            Boolean isDefault = geoCodeLanguageValue.getIsDefault();
            Integer sortOrder = geoCodeLanguageValue.getSortOrder();
            
            if(checkDefault) {
                GeoCodeLanguage defaultGeoCodeLanguage = getDefaultGeoCodeLanguage(geoCode);
                boolean defaultFound = defaultGeoCodeLanguage != null && !defaultGeoCodeLanguage.equals(geoCodeLanguage);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    GeoCodeLanguageValue defaultGeoCodeLanguageValue = getDefaultGeoCodeLanguageValueForUpdate(geoCode);
                    
                    defaultGeoCodeLanguageValue.setIsDefault(Boolean.FALSE);
                    updateGeoCodeLanguageFromValue(defaultGeoCodeLanguageValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }
            
            geoCodeLanguage = GeoCodeLanguageFactory.getInstance().create(geoCodePK, languagePK, isDefault, sortOrder,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(geoCodePK, EventTypes.MODIFY.name(), geoCodeLanguage.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void updateGeoCodeLanguageFromValue(GeoCodeLanguageValue geoCodeLanguageValue, BasePK updatedBy) {
        updateGeoCodeLanguageFromValue(geoCodeLanguageValue, true, updatedBy);
    }
    
    public void deleteGeoCodeLanguage(GeoCodeLanguage geoCodeLanguage, BasePK deletedBy) {
        geoCodeLanguage.setThruTime(session.START_TIME_LONG);
        geoCodeLanguage.store();
        
        // Check for default, and pick one if necessary
        GeoCode geoCode = geoCodeLanguage.getGeoCode();
        GeoCodeLanguage defaultGeoCodeLanguage = getDefaultGeoCodeLanguage(geoCode);
        if(defaultGeoCodeLanguage == null) {
            List<GeoCodeLanguage> geoCodeLanguages = getGeoCodeLanguagesByGeoCodeForUpdate(geoCode);
            
            if(!geoCodeLanguages.isEmpty()) {
                Iterator<GeoCodeLanguage> iter = geoCodeLanguages.iterator();
                if(iter.hasNext()) {
                    defaultGeoCodeLanguage = iter.next();
                }
                GeoCodeLanguageValue geoCodeLanguageValue = defaultGeoCodeLanguage.getGeoCodeLanguageValue().clone();
                
                geoCodeLanguageValue.setIsDefault(Boolean.TRUE);
                updateGeoCodeLanguageFromValue(geoCodeLanguageValue, false, deletedBy);
            }
        }
        
        sendEventUsingNames(geoCodeLanguage.getGeoCodePK(), EventTypes.MODIFY.name(), geoCodeLanguage.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deleteGeoCodeLanguages(List<GeoCodeLanguage> geoCodeLanguages, BasePK deletedBy) {
        geoCodeLanguages.stream().forEach((geoCodeLanguage) -> {
            deleteGeoCodeLanguage(geoCodeLanguage, deletedBy);
        });
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
        GeoCodeCurrency defaultGeoCodeCurrency = getDefaultGeoCodeCurrency(geoCode);
        boolean defaultFound = defaultGeoCodeCurrency != null;
        
        if(defaultFound && isDefault) {
            GeoCodeCurrencyValue defaultGeoCodeCurrencyValue = getDefaultGeoCodeCurrencyValueForUpdate(geoCode);
            
            defaultGeoCodeCurrencyValue.setIsDefault(Boolean.FALSE);
            updateGeoCodeCurrencyFromValue(defaultGeoCodeCurrencyValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }
        
        GeoCodeCurrency geoCodeCurrency = GeoCodeCurrencyFactory.getInstance().create(geoCode, currency, isDefault,
                sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(geoCode.getPrimaryKey(), EventTypes.MODIFY.name(), geoCodeCurrency.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return geoCodeCurrency;
    }
    
    private GeoCodeCurrency getGeoCodeCurrency(GeoCode geoCode, Currency currency, EntityPermission entityPermission) {
        GeoCodeCurrency geoCodeCurrency = null;
        
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
            
            PreparedStatement ps = GeoCodeCurrencyFactory.getInstance().prepareStatement(query);
            
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
        GeoCodeCurrency geoCodeCurrency = null;
        
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
            
            PreparedStatement ps = GeoCodeCurrencyFactory.getInstance().prepareStatement(query);
            
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
        GeoCodeCurrency geoCodeCurrency = getDefaultGeoCodeCurrencyForUpdate(geoCode);
        
        return geoCodeCurrency == null? null: geoCodeCurrency.getGeoCodeCurrencyValue().clone();
    }
    
    private List<GeoCodeCurrency> getGeoCodeCurrenciesByGeoCode(GeoCode geoCode, EntityPermission entityPermission) {
        List<GeoCodeCurrency> geoCodeCurrencies = null;
        
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
            
            PreparedStatement ps = GeoCodeCurrencyFactory.getInstance().prepareStatement(query);
            
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
        List<GeoCodeCurrency> geoCodeCurrencies = null;
        
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
            
            PreparedStatement ps = GeoCodeCurrencyFactory.getInstance().prepareStatement(query);
            
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
    
    public List<GeoCodeCurrencyTransfer> getGeoCodeCurrencyTransfers(UserVisit userVisit, List<GeoCodeCurrency> geoCodeCurrencies) {
        List<GeoCodeCurrencyTransfer> geoCodeCurrencyTransfers = new ArrayList<>(geoCodeCurrencies.size());
        GeoCodeCurrencyTransferCache geoCodeCurrencyTransferCache = getGeoTransferCaches(userVisit).getGeoCodeCurrencyTransferCache();
        
        geoCodeCurrencies.stream().forEach((geoCodeCurrency) -> {
            geoCodeCurrencyTransfers.add(geoCodeCurrencyTransferCache.getGeoCodeCurrencyTransfer(geoCodeCurrency));
        });
        
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
            GeoCodeCurrency geoCodeCurrency = GeoCodeCurrencyFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     geoCodeCurrencyValue.getPrimaryKey());
            
            geoCodeCurrency.setThruTime(session.START_TIME_LONG);
            geoCodeCurrency.store();
            
            GeoCode geoCode = geoCodeCurrency.getGeoCode(); // Not Updated
            GeoCodePK geoCodePK = geoCode.getPrimaryKey(); // Not Updated
            CurrencyPK currencyPK = geoCodeCurrency.getCurrencyPK(); // Not Updated
            Boolean isDefault = geoCodeCurrencyValue.getIsDefault();
            Integer sortOrder = geoCodeCurrencyValue.getSortOrder();
            
            if(checkDefault) {
                GeoCodeCurrency defaultGeoCodeCurrency = getDefaultGeoCodeCurrency(geoCode);
                boolean defaultFound = defaultGeoCodeCurrency != null && !defaultGeoCodeCurrency.equals(geoCodeCurrency);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    GeoCodeCurrencyValue defaultGeoCodeCurrencyValue = getDefaultGeoCodeCurrencyValueForUpdate(geoCode);
                    
                    defaultGeoCodeCurrencyValue.setIsDefault(Boolean.FALSE);
                    updateGeoCodeCurrencyFromValue(defaultGeoCodeCurrencyValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }
            
            geoCodeCurrency = GeoCodeCurrencyFactory.getInstance().create(geoCodePK, currencyPK, isDefault, sortOrder,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(geoCodePK, EventTypes.MODIFY.name(), geoCodeCurrency.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void updateGeoCodeCurrencyFromValue(GeoCodeCurrencyValue geoCodeCurrencyValue, BasePK updatedBy) {
        updateGeoCodeCurrencyFromValue(geoCodeCurrencyValue, true, updatedBy);
    }
    
    public void deleteGeoCodeCurrency(GeoCodeCurrency geoCodeCurrency, BasePK deletedBy) {
        geoCodeCurrency.setThruTime(session.START_TIME_LONG);
        geoCodeCurrency.store();
        
        // Check for default, and pick one if necessary
        GeoCode geoCode = geoCodeCurrency.getGeoCode();
        GeoCodeCurrency defaultGeoCodeCurrency = getDefaultGeoCodeCurrency(geoCode);
        if(defaultGeoCodeCurrency == null) {
            List<GeoCodeCurrency> geoCodeCurrencies = getGeoCodeCurrenciesByGeoCodeForUpdate(geoCode);
            
            if(!geoCodeCurrencies.isEmpty()) {
                Iterator<GeoCodeCurrency> iter = geoCodeCurrencies.iterator();
                if(iter.hasNext()) {
                    defaultGeoCodeCurrency = iter.next();
                }
                GeoCodeCurrencyValue geoCodeCurrencyValue = defaultGeoCodeCurrency.getGeoCodeCurrencyValue().clone();
                
                geoCodeCurrencyValue.setIsDefault(Boolean.TRUE);
                updateGeoCodeCurrencyFromValue(geoCodeCurrencyValue, false, deletedBy);
            }
        }
        
        sendEventUsingNames(geoCodeCurrency.getGeoCodePK(), EventTypes.MODIFY.name(), geoCodeCurrency.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deleteGeoCodeCurrencies(List<GeoCodeCurrency> geoCodeCurrencies, BasePK deletedBy) {
        geoCodeCurrencies.stream().forEach((geoCodeCurrency) -> {
            deleteGeoCodeCurrency(geoCodeCurrency, deletedBy);
        });
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
        GeoCodeTimeZone defaultGeoCodeTimeZone = getDefaultGeoCodeTimeZone(geoCode);
        boolean defaultFound = defaultGeoCodeTimeZone != null;
        
        if(defaultFound && isDefault) {
            GeoCodeTimeZoneValue defaultGeoCodeTimeZoneValue = getDefaultGeoCodeTimeZoneValueForUpdate(geoCode);
            
            defaultGeoCodeTimeZoneValue.setIsDefault(Boolean.FALSE);
            updateGeoCodeTimeZoneFromValue(defaultGeoCodeTimeZoneValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }
        
        GeoCodeTimeZone geoCodeTimeZone = GeoCodeTimeZoneFactory.getInstance().create(geoCode, timeZone, isDefault,
                sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(geoCode.getPrimaryKey(), EventTypes.MODIFY.name(), geoCodeTimeZone.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return geoCodeTimeZone;
    }
    
    private GeoCodeTimeZone getGeoCodeTimeZone(GeoCode geoCode, TimeZone timeZone, EntityPermission entityPermission) {
        GeoCodeTimeZone geoCodeTimeZone = null;
        
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
            
            PreparedStatement ps = GeoCodeTimeZoneFactory.getInstance().prepareStatement(query);
            
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
        GeoCodeTimeZone geoCodeTimeZone = null;
        
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
            
            PreparedStatement ps = GeoCodeTimeZoneFactory.getInstance().prepareStatement(query);
            
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
        GeoCodeTimeZone geoCodeTimeZone = getDefaultGeoCodeTimeZoneForUpdate(geoCode);
        
        return geoCodeTimeZone == null? null: geoCodeTimeZone.getGeoCodeTimeZoneValue().clone();
    }
    
    private List<GeoCodeTimeZone> getGeoCodeTimeZonesByGeoCode(GeoCode geoCode, EntityPermission entityPermission) {
        List<GeoCodeTimeZone> geoCodeTimeZones = null;
        
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
            
            PreparedStatement ps = GeoCodeTimeZoneFactory.getInstance().prepareStatement(query);
            
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
        List<GeoCodeTimeZone> geoCodeTimeZones = null;
        
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
            
            PreparedStatement ps = GeoCodeTimeZoneFactory.getInstance().prepareStatement(query);
            
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
    
    public List<GeoCodeTimeZoneTransfer> getGeoCodeTimeZoneTransfers(UserVisit userVisit, List<GeoCodeTimeZone> geoCodeTimeZones) {
        List<GeoCodeTimeZoneTransfer> geoCodeTimeZoneTransfers = new ArrayList<>(geoCodeTimeZones.size());
        GeoCodeTimeZoneTransferCache geoCodeTimeZoneTransferCache = getGeoTransferCaches(userVisit).getGeoCodeTimeZoneTransferCache();
        
        geoCodeTimeZones.stream().forEach((geoCodeTimeZone) -> {
            geoCodeTimeZoneTransfers.add(geoCodeTimeZoneTransferCache.getGeoCodeTimeZoneTransfer(geoCodeTimeZone));
        });
        
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
            GeoCodeTimeZone geoCodeTimeZone = GeoCodeTimeZoneFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     geoCodeTimeZoneValue.getPrimaryKey());
            
            geoCodeTimeZone.setThruTime(session.START_TIME_LONG);
            geoCodeTimeZone.store();
            
            GeoCode geoCode = geoCodeTimeZone.getGeoCode(); // Not Updated
            GeoCodePK geoCodePK = geoCode.getPrimaryKey(); // Not Updated
            TimeZonePK timeZonePK = geoCodeTimeZone.getTimeZonePK(); // Not Updated
            Boolean isDefault = geoCodeTimeZoneValue.getIsDefault();
            Integer sortOrder = geoCodeTimeZoneValue.getSortOrder();
            
            if(checkDefault) {
                GeoCodeTimeZone defaultGeoCodeTimeZone = getDefaultGeoCodeTimeZone(geoCode);
                boolean defaultFound = defaultGeoCodeTimeZone != null && !defaultGeoCodeTimeZone.equals(geoCodeTimeZone);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    GeoCodeTimeZoneValue defaultGeoCodeTimeZoneValue = getDefaultGeoCodeTimeZoneValueForUpdate(geoCode);
                    
                    defaultGeoCodeTimeZoneValue.setIsDefault(Boolean.FALSE);
                    updateGeoCodeTimeZoneFromValue(defaultGeoCodeTimeZoneValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }
            
            geoCodeTimeZone = GeoCodeTimeZoneFactory.getInstance().create(geoCodePK, timeZonePK, isDefault, sortOrder,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(geoCodePK, EventTypes.MODIFY.name(), geoCodeTimeZone.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void updateGeoCodeTimeZoneFromValue(GeoCodeTimeZoneValue geoCodeTimeZoneValue, BasePK updatedBy) {
        updateGeoCodeTimeZoneFromValue(geoCodeTimeZoneValue, true, updatedBy);
    }
    
    public void deleteGeoCodeTimeZone(GeoCodeTimeZone geoCodeTimeZone, BasePK deletedBy) {
        geoCodeTimeZone.setThruTime(session.START_TIME_LONG);
        geoCodeTimeZone.store();
        
        // Check for default, and pick one if necessary
        GeoCode geoCode = geoCodeTimeZone.getGeoCode();
        GeoCodeTimeZone defaultGeoCodeTimeZone = getDefaultGeoCodeTimeZone(geoCode);
        if(defaultGeoCodeTimeZone == null) {
            List<GeoCodeTimeZone> geoCodeTimeZones = getGeoCodeTimeZonesByGeoCodeForUpdate(geoCode);
            
            if(!geoCodeTimeZones.isEmpty()) {
                Iterator<GeoCodeTimeZone> iter = geoCodeTimeZones.iterator();
                if(iter.hasNext()) {
                    defaultGeoCodeTimeZone = iter.next();
                }
                GeoCodeTimeZoneValue geoCodeTimeZoneValue = defaultGeoCodeTimeZone.getGeoCodeTimeZoneValue().clone();
                
                geoCodeTimeZoneValue.setIsDefault(Boolean.TRUE);
                updateGeoCodeTimeZoneFromValue(geoCodeTimeZoneValue, false, deletedBy);
            }
        }
        
        sendEventUsingNames(geoCodeTimeZone.getGeoCodePK(), EventTypes.MODIFY.name(), geoCodeTimeZone.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deleteGeoCodeTimeZones(List<GeoCodeTimeZone> geoCodeTimeZones, BasePK deletedBy) {
        geoCodeTimeZones.stream().forEach((geoCodeTimeZone) -> {
            deleteGeoCodeTimeZone(geoCodeTimeZone, deletedBy);
        });
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
        GeoCodeDateTimeFormat defaultGeoCodeDateTimeFormat = getDefaultGeoCodeDateTimeFormat(geoCode);
        boolean defaultFound = defaultGeoCodeDateTimeFormat != null;
        
        if(defaultFound && isDefault) {
            GeoCodeDateTimeFormatValue defaultGeoCodeDateTimeFormatValue = getDefaultGeoCodeDateTimeFormatValueForUpdate(geoCode);
            
            defaultGeoCodeDateTimeFormatValue.setIsDefault(Boolean.FALSE);
            updateGeoCodeDateTimeFormatFromValue(defaultGeoCodeDateTimeFormatValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }
        
        GeoCodeDateTimeFormat geoCodeDateTimeFormat = GeoCodeDateTimeFormatFactory.getInstance().create(geoCode,
                dateTimeFormat, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(geoCode.getPrimaryKey(), EventTypes.MODIFY.name(), geoCodeDateTimeFormat.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return geoCodeDateTimeFormat;
    }
    
    private GeoCodeDateTimeFormat getGeoCodeDateTimeFormat(GeoCode geoCode, DateTimeFormat dateTimeFormat, EntityPermission entityPermission) {
        GeoCodeDateTimeFormat geoCodeDateTimeFormat = null;
        
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
            
            PreparedStatement ps = GeoCodeDateTimeFormatFactory.getInstance().prepareStatement(query);
            
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
        GeoCodeDateTimeFormat geoCodeDateTimeFormat = null;
        
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
            
            PreparedStatement ps = GeoCodeDateTimeFormatFactory.getInstance().prepareStatement(query);
            
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
        GeoCodeDateTimeFormat geoCodeDateTimeFormat = getDefaultGeoCodeDateTimeFormatForUpdate(geoCode);
        
        return geoCodeDateTimeFormat == null? null: geoCodeDateTimeFormat.getGeoCodeDateTimeFormatValue().clone();
    }
    
    private List<GeoCodeDateTimeFormat> getGeoCodeDateTimeFormatsByGeoCode(GeoCode geoCode, EntityPermission entityPermission) {
        List<GeoCodeDateTimeFormat> geoCodeDateTimeFormats = null;
        
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
            
            PreparedStatement ps = GeoCodeDateTimeFormatFactory.getInstance().prepareStatement(query);
            
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
        List<GeoCodeDateTimeFormat> geoCodeDateTimeFormats = null;
        
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
            
            PreparedStatement ps = GeoCodeDateTimeFormatFactory.getInstance().prepareStatement(query);
            
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
    
    public List<GeoCodeDateTimeFormatTransfer> getGeoCodeDateTimeFormatTransfers(UserVisit userVisit, List<GeoCodeDateTimeFormat> geoCodeDateTimeFormats) {
        List<GeoCodeDateTimeFormatTransfer> geoCodeDateTimeFormatTransfers = new ArrayList<>(geoCodeDateTimeFormats.size());
        GeoCodeDateTimeFormatTransferCache geoCodeDateTimeFormatTransferCache = getGeoTransferCaches(userVisit).getGeoCodeDateTimeFormatTransferCache();
        
        geoCodeDateTimeFormats.stream().forEach((geoCodeDateTimeFormat) -> {
            geoCodeDateTimeFormatTransfers.add(geoCodeDateTimeFormatTransferCache.getGeoCodeDateTimeFormatTransfer(geoCodeDateTimeFormat));
        });
        
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
            GeoCodeDateTimeFormat geoCodeDateTimeFormat = GeoCodeDateTimeFormatFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     geoCodeDateTimeFormatValue.getPrimaryKey());
            
            geoCodeDateTimeFormat.setThruTime(session.START_TIME_LONG);
            geoCodeDateTimeFormat.store();
            
            GeoCode geoCode = geoCodeDateTimeFormat.getGeoCode(); // Not Updated
            GeoCodePK geoCodePK = geoCode.getPrimaryKey(); // Not Updated
            DateTimeFormatPK dateTimeFormatPK = geoCodeDateTimeFormat.getDateTimeFormatPK(); // Not Updated
            Boolean isDefault = geoCodeDateTimeFormatValue.getIsDefault();
            Integer sortOrder = geoCodeDateTimeFormatValue.getSortOrder();
            
            if(checkDefault) {
                GeoCodeDateTimeFormat defaultGeoCodeDateTimeFormat = getDefaultGeoCodeDateTimeFormat(geoCode);
                boolean defaultFound = defaultGeoCodeDateTimeFormat != null && !defaultGeoCodeDateTimeFormat.equals(geoCodeDateTimeFormat);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    GeoCodeDateTimeFormatValue defaultGeoCodeDateTimeFormatValue = getDefaultGeoCodeDateTimeFormatValueForUpdate(geoCode);
                    
                    defaultGeoCodeDateTimeFormatValue.setIsDefault(Boolean.FALSE);
                    updateGeoCodeDateTimeFormatFromValue(defaultGeoCodeDateTimeFormatValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }
            
            geoCodeDateTimeFormat = GeoCodeDateTimeFormatFactory.getInstance().create(geoCodePK, dateTimeFormatPK,
                    isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(geoCodePK, EventTypes.MODIFY.name(), geoCodeDateTimeFormat.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void updateGeoCodeDateTimeFormatFromValue(GeoCodeDateTimeFormatValue geoCodeDateTimeFormatValue, BasePK updatedBy) {
        updateGeoCodeDateTimeFormatFromValue(geoCodeDateTimeFormatValue, true, updatedBy);
    }
    
    public void deleteGeoCodeDateTimeFormat(GeoCodeDateTimeFormat geoCodeDateTimeFormat, BasePK deletedBy) {
        geoCodeDateTimeFormat.setThruTime(session.START_TIME_LONG);
        geoCodeDateTimeFormat.store();
        
        // Check for default, and pick one if necessary
        GeoCode geoCode = geoCodeDateTimeFormat.getGeoCode();
        GeoCodeDateTimeFormat defaultGeoCodeDateTimeFormat = getDefaultGeoCodeDateTimeFormat(geoCode);
        if(defaultGeoCodeDateTimeFormat == null) {
            List<GeoCodeDateTimeFormat> geoCodeDateTimeFormats = getGeoCodeDateTimeFormatsByGeoCodeForUpdate(geoCode);
            
            if(!geoCodeDateTimeFormats.isEmpty()) {
                Iterator<GeoCodeDateTimeFormat> iter = geoCodeDateTimeFormats.iterator();
                if(iter.hasNext()) {
                    defaultGeoCodeDateTimeFormat = iter.next();
                }
                GeoCodeDateTimeFormatValue geoCodeDateTimeFormatValue = defaultGeoCodeDateTimeFormat.getGeoCodeDateTimeFormatValue().clone();
                
                geoCodeDateTimeFormatValue.setIsDefault(Boolean.TRUE);
                updateGeoCodeDateTimeFormatFromValue(geoCodeDateTimeFormatValue, false, deletedBy);
            }
        }
        
        sendEventUsingNames(geoCodeDateTimeFormat.getGeoCodePK(), EventTypes.MODIFY.name(), geoCodeDateTimeFormat.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deleteGeoCodeDateTimeFormats(List<GeoCodeDateTimeFormat> geoCodeDateTimeFormats, BasePK deletedBy) {
        geoCodeDateTimeFormats.stream().forEach((geoCodeDateTimeFormat) -> {
            deleteGeoCodeDateTimeFormat(geoCodeDateTimeFormat, deletedBy);
        });
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
        
        GeoCodeCountry geoCodeCountry = GeoCodeCountryFactory.getInstance().create(geoCode, telephoneCode, areaCodePattern,
                areaCodeRequired, areaCodeExample, telephoneNumberPattern, telephoneNumberExample, postalAddressFormat,
                cityRequired, cityGeoCodeRequired, stateRequired, stateGeoCodeRequired, postalCodePattern, postalCodeRequired,
                postalCodeGeoCodeRequired, postalCodeLength, postalCodeGeoCodeLength, postalCodeExample, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
        sendEventUsingNames(geoCode.getPrimaryKey(), EventTypes.MODIFY.name(), geoCodeCountry.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return geoCodeCountry;
    }
    
    private GeoCodeCountry getGeoCodeCountry(GeoCode geoCode, EntityPermission entityPermission) {
        GeoCodeCountry geoCodeCountry = null;
        
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
            
            PreparedStatement ps = GeoCodeCountryFactory.getInstance().prepareStatement(query);
            
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
            GeoCodeCountry geoCodeCountry = GeoCodeCountryFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     geoCodeCountryValue.getPrimaryKey());
            
            geoCodeCountry.setThruTime(session.START_TIME_LONG);
            geoCodeCountry.store();
            
            GeoCodePK geoCodePK = geoCodeCountry.getGeoCodePK(); // Not Updated
            String telephoneCode = geoCodeCountryValue.getTelephoneCode();
            String areaCodePattern = geoCodeCountryValue.getAreaCodePattern();
            Boolean areaCodeRequired = geoCodeCountryValue.getAreaCodeRequired();
            String areaCodeExample = geoCodeCountryValue.getAreaCodeExample();
            String telephoneNumberPattern = geoCodeCountryValue.getTelephoneNumberPattern();
            String telephoneNumberExample = geoCodeCountryValue.getTelephoneNumberExample();
            PostalAddressFormatPK postalAddressFormatPK = geoCodeCountryValue.getPostalAddressFormatPK();
            Boolean cityRequired = geoCodeCountryValue.getCityGeoCodeRequired();
            Boolean cityGeoCodeRequired = geoCodeCountryValue.getCityGeoCodeRequired();
            Boolean stateRequired = geoCodeCountryValue.getStateRequired();
            Boolean stateGeoCodeRequired = geoCodeCountryValue.getStateGeoCodeRequired();
            String postalCodePattern = geoCodeCountryValue.getPostalCodePattern();
            Boolean postalCodeRequired = geoCodeCountryValue.getPostalCodeRequired();
            Boolean postalCodeGeoCodeRequired = geoCodeCountryValue.getPostalCodeGeoCodeRequired();
            Integer postalCodeLength = geoCodeCountryValue.getPostalCodeLength();
            Integer postalCodeGeoCodeLength = geoCodeCountryValue.getPostalCodeGeoCodeLength();
            String postalCodeExample = geoCodeCountryValue.getPostalCodeExample();
            
            geoCodeCountry = GeoCodeCountryFactory.getInstance().create(geoCodePK, telephoneCode, areaCodePattern,
                    areaCodeRequired, areaCodeExample, telephoneNumberPattern, telephoneNumberExample, postalAddressFormatPK,
                    cityRequired, cityGeoCodeRequired, stateRequired, stateGeoCodeRequired, postalCodePattern, postalCodeRequired,
                    postalCodeGeoCodeRequired, postalCodeLength, postalCodeGeoCodeLength, postalCodeExample,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(geoCodePK, EventTypes.MODIFY.name(), geoCodeCountry.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteGeoCodeCountry(GeoCodeCountry geoCodeCountry, BasePK deletedBy) {
        geoCodeCountry.setThruTime(session.START_TIME_LONG);
        geoCodeCountry.store();
        
        sendEventUsingNames(geoCodeCountry.getGeoCodePK(), EventTypes.MODIFY.name(), geoCodeCountry.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deleteGeoCodeCountryByGeoCode(GeoCode geoCode, BasePK deletedBy) {
        deleteGeoCodeCountry(getGeoCodeCountryForUpdate(geoCode), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Utilities
    // --------------------------------------------------------------------------------
    
    private GeoCode getGeoCodeByTypeScopeAndAlias(String geoCodeTypeName, String geoCodeScopeName, String alias) {
        GeoCode geoCode = null;
        GeoCodeType geoCodeType = getGeoCodeTypeByName(geoCodeTypeName);
        
        if(geoCodeType != null) {
            GeoCodeScope geoCodeScope = getGeoCodeScopeByName(geoCodeScopeName);
            
            if(geoCodeScope != null) {
                geoCode = getGeoCodeByName(alias);
                
                // If we were passed a geoCodeName for the alias, check to see if its valid for the Type and Scope that
                // we're looking for. If its not, clear it, and search using aliases.
                if(geoCode != null) {
                    GeoCodeDetail geoCodeDetail = geoCode.getLastDetail();
                    
                    if(!geoCodeDetail.getGeoCodeType().equals(geoCodeType) || !geoCodeDetail.getGeoCodeScope().equals(geoCodeScope)) {
                        geoCode = null;
                    }
                }
                
                if(geoCode == null) {
                    GeoCodeAliasType geoCodeAliasType = getDefaultGeoCodeAliasType(geoCodeType);
                    GeoCodeAlias geoCodeAlias = null;
                    
                    if(geoCodeAliasType != null)
                        geoCodeAlias = getGeoCodeAliasByAliasWithinScope(geoCodeScope, geoCodeAliasType, alias);
                    
                    if(geoCodeAlias == null) {
                        List<GeoCodeAliasType> geoCodeAliasTypes = getGeoCodeAliasTypesExceptDefault(geoCodeType);
                        Iterator<GeoCodeAliasType> iter = geoCodeAliasTypes.iterator();
                        
                        while(geoCodeAlias == null && iter.hasNext()) {
                            geoCodeAliasType = iter.next();
                            
                            GeoCodeAliasTypeDetail getoCodeAliasTypeDetail = geoCodeAliasType.getLastDetail();
                            String validationPattern = getoCodeAliasTypeDetail.getValidationPattern();
                            boolean patternMatches = false;
                            
                            if(validationPattern != null) {
                                Pattern pattern = Pattern.compile(validationPattern);
                                Matcher m = pattern.matcher(alias);
                                
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
        GeoCodeAlias geoCodeAlias = getGeoCodeAlias(countryGeoCode, geoCodeAliasType);
        String countryAlias = geoCodeAlias == null? null: geoCodeAlias.getAlias();
        
        if(countryAlias == null) {
            List<GeoCodeAliasType> geoCodeAliasTypes = getGeoCodeAliasTypesExceptDefault(geoCodeType);
            
            for(GeoCodeAliasType loopGeoCodeAliasType: geoCodeAliasTypes) {
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
        GeoCodeType geoCodeType = countryGeoCode.getLastDetail().getGeoCodeType();
        GeoCodeAliasType geoCodeAliasType = getDefaultGeoCodeAliasType(geoCodeType);
        
        return getAliasForCountry(countryGeoCode, geoCodeType, geoCodeAliasType);
    }
    
    public GeoCode getPostalCodeByAlias(GeoCode countryGeoCode, String postalCodeAlias) {
        GeoCodeAliasType countryGeoCodeAliasType = getGeoCodeAliasTypeByName(countryGeoCode.getLastDetail().getGeoCodeType(),
                GeoConstants.GeoCodeAliasType_ISO_2_LETTER);
        GeoCodeAlias countryGeoCodeAlias = getGeoCodeAlias(countryGeoCode, countryGeoCodeAliasType);
        String countryIso2Letter = countryGeoCodeAlias.getAlias();
        String geoCodeScopeName = new StringBuilder().append(countryIso2Letter).append("_ZIP_CODES").toString();
        
        return getGeoCodeByTypeScopeAndAlias(GeoConstants.GeoCodeType_ZIP_CODE, geoCodeScopeName, postalCodeAlias);
    }
    
    public String getAliasForPostalCode(GeoCode postalCodeGeoCode) {
        GeoCodeType geoCodeType = postalCodeGeoCode.getLastDetail().getGeoCodeType();
        GeoCodeAliasType geoCodeAliasType = getDefaultGeoCodeAliasType(geoCodeType);
        GeoCodeAlias geoCodeAlias = getGeoCodeAlias(postalCodeGeoCode, geoCodeAliasType);
        String postalCodeAlias = geoCodeAlias == null? null: geoCodeAlias.getAlias();
        
        if(postalCodeAlias == null) {
            List<GeoCodeAliasType> geoCodeAliasTypes = getGeoCodeAliasTypesExceptDefault(geoCodeType);
            
            for(GeoCodeAliasType loopGeoCodeAliasType: geoCodeAliasTypes) {
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
        GeoCodeAliasType countryGeoCodeAliasType = getGeoCodeAliasTypeByName(countryGeoCode.getLastDetail().getGeoCodeType(),
                GeoConstants.GeoCodeAliasType_ISO_2_LETTER);
        GeoCodeAlias countryGeoCodeAlias = getGeoCodeAlias(countryGeoCode, countryGeoCodeAliasType);
        String countryIso2Letter = countryGeoCodeAlias.getAlias();
        String geoCodeScopeName = new StringBuilder().append(countryIso2Letter).append("_STATES").toString();
        
        return getGeoCodeByTypeScopeAndAlias(GeoConstants.GeoCodeType_STATE, geoCodeScopeName, stateAlias);
    }
    
    public String getAliasForState(GeoCode stateGeoCode) {
        GeoCodeType geoCodeType = stateGeoCode.getLastDetail().getGeoCodeType();
        GeoCodeAliasType geoCodeAliasType = getDefaultGeoCodeAliasType(geoCodeType);
        GeoCodeAlias geoCodeAlias = getGeoCodeAlias(stateGeoCode, geoCodeAliasType);
        String stateAlias = geoCodeAlias == null? null: geoCodeAlias.getAlias();
        
        if(stateAlias == null) {
            List<GeoCodeAliasType> geoCodeAliasTypes = getGeoCodeAliasTypesExceptDefault(geoCodeType);
            
            for(GeoCodeAliasType loopGeoCodeAliasType: geoCodeAliasTypes) {
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
        GeoCodeAliasType stateGeoCodeAliasType = getGeoCodeAliasTypeByName(stateGeoCode.getLastDetail().getGeoCodeType(),
                GeoConstants.GeoCodeAliasType_POSTAL_2_LETTER);
        GeoCodeAlias stateGeoCodeAlias = getGeoCodeAlias(stateGeoCode, stateGeoCodeAliasType);
        String statePostal2Letter = stateGeoCodeAlias.getAlias();
        
        GeoCodeType countryGeoCodeType = getGeoCodeTypeByName(GeoConstants.GeoCodeType_COUNTRY);
        List<GeoCodeRelationship> stateRelationships = getGeoCodeRelationshipsByFromGeoCodeAndGeoCodeType(stateGeoCode, countryGeoCodeType);
        if(stateRelationships.size() != 1) {
            getLog().error("non-1 stateRelationships.size()");
        }
        GeoCode countryGeoCode = stateRelationships.iterator().next().getToGeoCode();
        
        GeoCodeAliasType countryGeoCodeAliasType = getGeoCodeAliasTypeByName(countryGeoCode.getLastDetail().getGeoCodeType(),
                GeoConstants.GeoCodeAliasType_ISO_2_LETTER);
        GeoCodeAlias countryGeoCodeAlias = getGeoCodeAlias(countryGeoCode, countryGeoCodeAliasType);
        String countryIso2Letter = countryGeoCodeAlias.getAlias();
        
        String geoCodeScopeName = new StringBuilder().append(countryIso2Letter).append("_").append(statePostal2Letter).append("_CITIES").toString();
        
        return getGeoCodeByTypeScopeAndAlias(GeoConstants.GeoCodeType_CITY, geoCodeScopeName, cityAlias);
    }
    
    public String getAliasForCity(GeoCode cityGeoCode) {
        GeoCodeType geoCodeType = cityGeoCode.getLastDetail().getGeoCodeType();
        GeoCodeAliasType geoCodeAliasType = getDefaultGeoCodeAliasType(geoCodeType);
        GeoCodeAlias geoCodeAlias = getGeoCodeAlias(cityGeoCode, geoCodeAliasType);
        String cityAlias = geoCodeAlias == null? null: geoCodeAlias.getAlias();
        
        if(cityAlias == null) {
            List<GeoCodeAliasType> geoCodeAliasTypes = getGeoCodeAliasTypesExceptDefault(geoCodeType);
            
            for(GeoCodeAliasType loopGeoCodeAliasType: geoCodeAliasTypes) {
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
