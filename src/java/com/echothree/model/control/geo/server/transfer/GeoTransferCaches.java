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

import com.echothree.model.control.geo.server.control.GeoControl;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.transfer.BaseTransferCaches;

public class GeoTransferCaches
        extends BaseTransferCaches {
    
    protected GeoControl geoControl;
    
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
    public GeoTransferCaches(GeoControl geoControl) {
        super();
        
        this.geoControl = geoControl;
    }
    
    public CityTransferCache getCityTransferCache() {
        if(cityTransferCache == null)
            cityTransferCache = new CityTransferCache(geoControl);
        
        return cityTransferCache;
    }
    
    public CountryTransferCache getCountryTransferCache() {
        if(countryTransferCache == null)
            countryTransferCache = new CountryTransferCache(geoControl);
        
        return countryTransferCache;
    }
    
    public CountyTransferCache getCountyTransferCache() {
        if(countyTransferCache == null)
            countyTransferCache = new CountyTransferCache(geoControl);
        
        return countyTransferCache;
    }
    
    public PostalCodeTransferCache getPostalCodeTransferCache() {
        if(postalCodeTransferCache == null)
            postalCodeTransferCache = new PostalCodeTransferCache(geoControl);
        
        return postalCodeTransferCache;
    }
    
    public StateTransferCache getStateTransferCache() {
        if(stateTransferCache == null)
            stateTransferCache = new StateTransferCache(geoControl);
        
        return stateTransferCache;
    }
    
    public GeoCodeTransferCache getGeoCodeTransferCache() {
        if(geoCodeTransferCache == null)
            geoCodeTransferCache = new GeoCodeTransferCache(geoControl);
        
        return geoCodeTransferCache;
    }
    
    public GeoCodeAliasTransferCache getGeoCodeAliasTransferCache() {
        if(geoCodeAliasTransferCache == null)
            geoCodeAliasTransferCache = new GeoCodeAliasTransferCache(geoControl);
        
        return geoCodeAliasTransferCache;
    }
    
    public GeoCodeAliasTypeTransferCache getGeoCodeAliasTypeTransferCache() {
        if(geoCodeAliasTypeTransferCache == null)
            geoCodeAliasTypeTransferCache = new GeoCodeAliasTypeTransferCache(geoControl);
        
        return geoCodeAliasTypeTransferCache;
    }
    
    public GeoCodeScopeTransferCache getGeoCodeScopeTransferCache() {
        if(geoCodeScopeTransferCache == null)
            geoCodeScopeTransferCache = new GeoCodeScopeTransferCache(geoControl);
        
        return geoCodeScopeTransferCache;
    }
    
    public GeoCodeTypeTransferCache getGeoCodeTypeTransferCache() {
        if(geoCodeTypeTransferCache == null)
            geoCodeTypeTransferCache = new GeoCodeTypeTransferCache(geoControl);
        
        return geoCodeTypeTransferCache;
    }
    
    public GeoCodeRelationshipTransferCache getGeoCodeRelationshipTransferCache() {
        if(geoCodeRelationshipTransferCache == null)
            geoCodeRelationshipTransferCache = new GeoCodeRelationshipTransferCache(geoControl);
        
        return geoCodeRelationshipTransferCache;
    }
    
    public GeoCodeCurrencyTransferCache getGeoCodeCurrencyTransferCache() {
        if(geoCodeCurrencyTransferCache == null)
            geoCodeCurrencyTransferCache = new GeoCodeCurrencyTransferCache(geoControl);
        
        return geoCodeCurrencyTransferCache;
    }
    
    public GeoCodeLanguageTransferCache getGeoCodeLanguageTransferCache() {
        if(geoCodeLanguageTransferCache == null)
            geoCodeLanguageTransferCache = new GeoCodeLanguageTransferCache(geoControl);
        
        return geoCodeLanguageTransferCache;
    }
    
    public GeoCodeTimeZoneTransferCache getGeoCodeTimeZoneTransferCache() {
        if(geoCodeTimeZoneTransferCache == null)
            geoCodeTimeZoneTransferCache = new GeoCodeTimeZoneTransferCache(geoControl);
        
        return geoCodeTimeZoneTransferCache;
    }
    
    public GeoCodeDateTimeFormatTransferCache getGeoCodeDateTimeFormatTransferCache() {
        if(geoCodeDateTimeFormatTransferCache == null)
            geoCodeDateTimeFormatTransferCache = new GeoCodeDateTimeFormatTransferCache(geoControl);
        
        return geoCodeDateTimeFormatTransferCache;
    }
    
    public GeoCodeDescriptionTransferCache getGeoCodeDescriptionTransferCache() {
        if(geoCodeDescriptionTransferCache == null)
            geoCodeDescriptionTransferCache = new GeoCodeDescriptionTransferCache(geoControl);
        
        return geoCodeDescriptionTransferCache;
    }
    
    public GeoCodeScopeDescriptionTransferCache getGeoCodeScopeDescriptionTransferCache() {
        if(geoCodeScopeDescriptionTransferCache == null)
            geoCodeScopeDescriptionTransferCache = new GeoCodeScopeDescriptionTransferCache(geoControl);
        
        return geoCodeScopeDescriptionTransferCache;
    }
    
    public GeoCodeAliasTypeDescriptionTransferCache getGeoCodeAliasTypeDescriptionTransferCache() {
        if(geoCodeAliasTypeDescriptionTransferCache == null)
            geoCodeAliasTypeDescriptionTransferCache = new GeoCodeAliasTypeDescriptionTransferCache(geoControl);
        
        return geoCodeAliasTypeDescriptionTransferCache;
    }
    
    public GeoCodeTypeDescriptionTransferCache getGeoCodeTypeDescriptionTransferCache() {
        if(geoCodeTypeDescriptionTransferCache == null)
            geoCodeTypeDescriptionTransferCache = new GeoCodeTypeDescriptionTransferCache(geoControl);
        
        return geoCodeTypeDescriptionTransferCache;
    }
    
}
