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
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
public class GeoTransferCaches
        extends BaseTransferCaches {
    
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
    
    @Inject
    GeoCodeTransferCache geoCodeTransferCache;
    
    @Inject
    GeoCodeAliasTransferCache geoCodeAliasTransferCache;
    
    @Inject
    GeoCodeAliasTypeTransferCache geoCodeAliasTypeTransferCache;
    
    @Inject
    GeoCodeScopeTransferCache geoCodeScopeTransferCache;
    
    @Inject
    GeoCodeTypeTransferCache geoCodeTypeTransferCache;
    
    @Inject
    GeoCodeRelationshipTransferCache geoCodeRelationshipTransferCache;
    
    @Inject
    GeoCodeCurrencyTransferCache geoCodeCurrencyTransferCache;
    
    @Inject
    GeoCodeLanguageTransferCache geoCodeLanguageTransferCache;
    
    @Inject
    GeoCodeTimeZoneTransferCache geoCodeTimeZoneTransferCache;
    
    @Inject
    GeoCodeDateTimeFormatTransferCache geoCodeDateTimeFormatTransferCache;
    
    @Inject
    GeoCodeDescriptionTransferCache geoCodeDescriptionTransferCache;
    
    @Inject
    GeoCodeScopeDescriptionTransferCache geoCodeScopeDescriptionTransferCache;
    
    @Inject
    GeoCodeAliasTypeDescriptionTransferCache geoCodeAliasTypeDescriptionTransferCache;
    
    @Inject
    GeoCodeTypeDescriptionTransferCache geoCodeTypeDescriptionTransferCache;

    /** Creates a new instance of GeoTransferCaches */
    protected GeoTransferCaches() {
        super();
    }
    
    public CityTransferCache getCityTransferCache() {
        return cityTransferCache;
    }
    
    public CountryTransferCache getCountryTransferCache() {
        return countryTransferCache;
    }
    
    public CountyTransferCache getCountyTransferCache() {
        return countyTransferCache;
    }
    
    public PostalCodeTransferCache getPostalCodeTransferCache() {
        return postalCodeTransferCache;
    }
    
    public StateTransferCache getStateTransferCache() {
        return stateTransferCache;
    }
    
    public GeoCodeTransferCache getGeoCodeTransferCache() {
        return geoCodeTransferCache;
    }
    
    public GeoCodeAliasTransferCache getGeoCodeAliasTransferCache() {
        return geoCodeAliasTransferCache;
    }
    
    public GeoCodeAliasTypeTransferCache getGeoCodeAliasTypeTransferCache() {
        return geoCodeAliasTypeTransferCache;
    }
    
    public GeoCodeScopeTransferCache getGeoCodeScopeTransferCache() {
        return geoCodeScopeTransferCache;
    }
    
    public GeoCodeTypeTransferCache getGeoCodeTypeTransferCache() {
        return geoCodeTypeTransferCache;
    }
    
    public GeoCodeRelationshipTransferCache getGeoCodeRelationshipTransferCache() {
        return geoCodeRelationshipTransferCache;
    }
    
    public GeoCodeCurrencyTransferCache getGeoCodeCurrencyTransferCache() {
        return geoCodeCurrencyTransferCache;
    }
    
    public GeoCodeLanguageTransferCache getGeoCodeLanguageTransferCache() {
        return geoCodeLanguageTransferCache;
    }
    
    public GeoCodeTimeZoneTransferCache getGeoCodeTimeZoneTransferCache() {
        return geoCodeTimeZoneTransferCache;
    }
    
    public GeoCodeDateTimeFormatTransferCache getGeoCodeDateTimeFormatTransferCache() {
        return geoCodeDateTimeFormatTransferCache;
    }
    
    public GeoCodeDescriptionTransferCache getGeoCodeDescriptionTransferCache() {
        return geoCodeDescriptionTransferCache;
    }
    
    public GeoCodeScopeDescriptionTransferCache getGeoCodeScopeDescriptionTransferCache() {
        return geoCodeScopeDescriptionTransferCache;
    }
    
    public GeoCodeAliasTypeDescriptionTransferCache getGeoCodeAliasTypeDescriptionTransferCache() {
        return geoCodeAliasTypeDescriptionTransferCache;
    }
    
    public GeoCodeTypeDescriptionTransferCache getGeoCodeTypeDescriptionTransferCache() {
        return geoCodeTypeDescriptionTransferCache;
    }
    
}
