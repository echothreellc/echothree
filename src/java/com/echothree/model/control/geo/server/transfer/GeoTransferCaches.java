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

package com.echothree.model.control.geo.server.transfer;

import com.echothree.util.server.transfer.BaseTransferCaches;
import javax.enterprise.inject.spi.CDI;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class GeoTransferCaches
        extends BaseTransferCaches {
    
    protected CityTransferCache cityTransferCache;
    protected CountryTransferCache countryTransferCache;
    protected CountyTransferCache countyTransferCache;
    protected PostalCodeTransferCache postalCodeTransferCache;
    protected StateTransferCache stateTransferCache;
    protected GeoCodeTransferCache geoCodeTransferCache;
    protected GeoCodeAliasTransferCache geoCodeAliasTransferCache;
    protected GeoCodeAliasTypeTransferCache geoCodeAliasTypeTransferCache;
    protected GeoCodeScopeTransferCache geoCodeScopeTransferCache;
    protected GeoCodeTypeTransferCache geoCodeTypeTransferCache;
    protected GeoCodeRelationshipTransferCache geoCodeRelationshipTransferCache;
    protected GeoCodeCurrencyTransferCache geoCodeCurrencyTransferCache;
    protected GeoCodeLanguageTransferCache geoCodeLanguageTransferCache;
    protected GeoCodeTimeZoneTransferCache geoCodeTimeZoneTransferCache;
    protected GeoCodeDateTimeFormatTransferCache geoCodeDateTimeFormatTransferCache;
    protected GeoCodeDescriptionTransferCache geoCodeDescriptionTransferCache;
    protected GeoCodeScopeDescriptionTransferCache geoCodeScopeDescriptionTransferCache;
    protected GeoCodeAliasTypeDescriptionTransferCache geoCodeAliasTypeDescriptionTransferCache;
    protected GeoCodeTypeDescriptionTransferCache geoCodeTypeDescriptionTransferCache;
    
    /** Creates a new instance of GeoTransferCaches */
    protected GeoTransferCaches() {
        super();
    }
    
    public CityTransferCache getCityTransferCache() {
        if(cityTransferCache == null)
            cityTransferCache = CDI.current().select(CityTransferCache.class).get();
        
        return cityTransferCache;
    }
    
    public CountryTransferCache getCountryTransferCache() {
        if(countryTransferCache == null)
            countryTransferCache = CDI.current().select(CountryTransferCache.class).get();
        
        return countryTransferCache;
    }
    
    public CountyTransferCache getCountyTransferCache() {
        if(countyTransferCache == null)
            countyTransferCache = CDI.current().select(CountyTransferCache.class).get();
        
        return countyTransferCache;
    }
    
    public PostalCodeTransferCache getPostalCodeTransferCache() {
        if(postalCodeTransferCache == null)
            postalCodeTransferCache = CDI.current().select(PostalCodeTransferCache.class).get();
        
        return postalCodeTransferCache;
    }
    
    public StateTransferCache getStateTransferCache() {
        if(stateTransferCache == null)
            stateTransferCache = CDI.current().select(StateTransferCache.class).get();
        
        return stateTransferCache;
    }
    
    public GeoCodeTransferCache getGeoCodeTransferCache() {
        if(geoCodeTransferCache == null)
            geoCodeTransferCache = CDI.current().select(GeoCodeTransferCache.class).get();
        
        return geoCodeTransferCache;
    }
    
    public GeoCodeAliasTransferCache getGeoCodeAliasTransferCache() {
        if(geoCodeAliasTransferCache == null)
            geoCodeAliasTransferCache = CDI.current().select(GeoCodeAliasTransferCache.class).get();
        
        return geoCodeAliasTransferCache;
    }
    
    public GeoCodeAliasTypeTransferCache getGeoCodeAliasTypeTransferCache() {
        if(geoCodeAliasTypeTransferCache == null)
            geoCodeAliasTypeTransferCache = CDI.current().select(GeoCodeAliasTypeTransferCache.class).get();
        
        return geoCodeAliasTypeTransferCache;
    }
    
    public GeoCodeScopeTransferCache getGeoCodeScopeTransferCache() {
        if(geoCodeScopeTransferCache == null)
            geoCodeScopeTransferCache = CDI.current().select(GeoCodeScopeTransferCache.class).get();
        
        return geoCodeScopeTransferCache;
    }
    
    public GeoCodeTypeTransferCache getGeoCodeTypeTransferCache() {
        if(geoCodeTypeTransferCache == null)
            geoCodeTypeTransferCache = CDI.current().select(GeoCodeTypeTransferCache.class).get();
        
        return geoCodeTypeTransferCache;
    }
    
    public GeoCodeRelationshipTransferCache getGeoCodeRelationshipTransferCache() {
        if(geoCodeRelationshipTransferCache == null)
            geoCodeRelationshipTransferCache = CDI.current().select(GeoCodeRelationshipTransferCache.class).get();
        
        return geoCodeRelationshipTransferCache;
    }
    
    public GeoCodeCurrencyTransferCache getGeoCodeCurrencyTransferCache() {
        if(geoCodeCurrencyTransferCache == null)
            geoCodeCurrencyTransferCache = CDI.current().select(GeoCodeCurrencyTransferCache.class).get();
        
        return geoCodeCurrencyTransferCache;
    }
    
    public GeoCodeLanguageTransferCache getGeoCodeLanguageTransferCache() {
        if(geoCodeLanguageTransferCache == null)
            geoCodeLanguageTransferCache = CDI.current().select(GeoCodeLanguageTransferCache.class).get();
        
        return geoCodeLanguageTransferCache;
    }
    
    public GeoCodeTimeZoneTransferCache getGeoCodeTimeZoneTransferCache() {
        if(geoCodeTimeZoneTransferCache == null)
            geoCodeTimeZoneTransferCache = CDI.current().select(GeoCodeTimeZoneTransferCache.class).get();
        
        return geoCodeTimeZoneTransferCache;
    }
    
    public GeoCodeDateTimeFormatTransferCache getGeoCodeDateTimeFormatTransferCache() {
        if(geoCodeDateTimeFormatTransferCache == null)
            geoCodeDateTimeFormatTransferCache = CDI.current().select(GeoCodeDateTimeFormatTransferCache.class).get();
        
        return geoCodeDateTimeFormatTransferCache;
    }
    
    public GeoCodeDescriptionTransferCache getGeoCodeDescriptionTransferCache() {
        if(geoCodeDescriptionTransferCache == null)
            geoCodeDescriptionTransferCache = CDI.current().select(GeoCodeDescriptionTransferCache.class).get();
        
        return geoCodeDescriptionTransferCache;
    }
    
    public GeoCodeScopeDescriptionTransferCache getGeoCodeScopeDescriptionTransferCache() {
        if(geoCodeScopeDescriptionTransferCache == null)
            geoCodeScopeDescriptionTransferCache = CDI.current().select(GeoCodeScopeDescriptionTransferCache.class).get();
        
        return geoCodeScopeDescriptionTransferCache;
    }
    
    public GeoCodeAliasTypeDescriptionTransferCache getGeoCodeAliasTypeDescriptionTransferCache() {
        if(geoCodeAliasTypeDescriptionTransferCache == null)
            geoCodeAliasTypeDescriptionTransferCache = CDI.current().select(GeoCodeAliasTypeDescriptionTransferCache.class).get();
        
        return geoCodeAliasTypeDescriptionTransferCache;
    }
    
    public GeoCodeTypeDescriptionTransferCache getGeoCodeTypeDescriptionTransferCache() {
        if(geoCodeTypeDescriptionTransferCache == null)
            geoCodeTypeDescriptionTransferCache = CDI.current().select(GeoCodeTypeDescriptionTransferCache.class).get();
        
        return geoCodeTypeDescriptionTransferCache;
    }
    
}
