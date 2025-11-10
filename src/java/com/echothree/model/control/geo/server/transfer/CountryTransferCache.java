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

import com.echothree.model.control.contact.server.control.ContactControl;
import com.echothree.model.control.geo.common.GeoOptions;
import com.echothree.model.control.geo.common.transfer.CountryTransfer;
import com.echothree.model.control.geo.server.control.GeoControl;
import com.echothree.model.data.geo.server.entity.GeoCode;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class CountryTransferCache
        extends BaseGeoCodeTransferCache<GeoCode, CountryTransfer> {
    
    ContactControl contactControl = Session.getModelController(ContactControl.class);
    boolean includeAliases;
    
    /** Creates a new instance of CountryTransferCache */
    public CountryTransferCache(UserVisit userVisit, GeoControl geoControl) {
        super(userVisit, geoControl);
        
        var options = session.getOptions();
        if(options != null) {
            includeAliases = options.contains(GeoOptions.CountryIncludeAliases);
        }
        
        setIncludeEntityInstance(true);
    }
    
    public CountryTransfer getCountryTransfer(GeoCode geoCode) {
        var countryTransfer = get(geoCode);
        
        if(countryTransfer == null) {
            var geoCodeDetail = geoCode.getLastDetail();
            var geoCodeCountry = geoControl.getGeoCodeCountry(geoCode);
            var geoCodeName = geoCodeDetail.getGeoCodeName();
            var geoCodeType = geoControl.getGeoCodeTypeTransfer(userVisit, geoCodeDetail.getGeoCodeType());
            var geoCodeScope = geoControl.getGeoCodeScopeTransfer(userVisit, geoCodeDetail.getGeoCodeScope());
            var isDefault = geoCodeDetail.getIsDefault();
            var sortOrder = geoCodeDetail.getSortOrder();
            var telephoneCode = geoCodeCountry.getTelephoneCode();
            var areaCodePattern = geoCodeCountry.getAreaCodePattern();
            var areaCodeRequired = geoCodeCountry.getAreaCodeRequired();
            var areaCodeExample = geoCodeCountry.getAreaCodeExample();
            var telephoneNumberPattern = geoCodeCountry.getTelephoneNumberPattern();
            var telephoneNumberExample = geoCodeCountry.getTelephoneNumberExample();
            var postalAddressFormat = contactControl.getPostalAddressFormatTransfer(userVisit, geoCodeCountry.getPostalAddressFormat());
            var cityRequired = geoCodeCountry.getCityRequired();
            var cityGeoCodeRequired = geoCodeCountry.getCityGeoCodeRequired();
            var stateRequired = geoCodeCountry.getStateRequired();
            var stateGeoCodeRequired = geoCodeCountry.getCityGeoCodeRequired();
            var postalCodePattern = geoCodeCountry.getPostalCodePattern();
            var postalCodeRequired = geoCodeCountry.getPostalCodeRequired();
            var postalCodeGeoCodeRequired = geoCodeCountry.getPostalCodeGeoCodeRequired();
            var postalCodeLength = geoCodeCountry.getPostalCodeLength();
            var postalCodeGeoCodeLength = geoCodeCountry.getPostalCodeGeoCodeLength();
            var postalCodeExample = geoCodeCountry.getPostalCodeExample();
            var description = geoControl.getBestGeoCodeDescription(geoCode, getLanguage(userVisit));
            
            countryTransfer = new CountryTransfer(geoCodeName, geoCodeType, geoCodeScope, isDefault, sortOrder, telephoneCode,
                    areaCodePattern, areaCodeRequired, areaCodeExample, telephoneNumberPattern, telephoneNumberExample,
                    postalAddressFormat, cityRequired, cityGeoCodeRequired, stateRequired, stateGeoCodeRequired, postalCodePattern,
                    postalCodeRequired, postalCodeGeoCodeRequired, postalCodeLength, postalCodeGeoCodeLength, postalCodeExample,
                    description);
            put(userVisit, geoCode, countryTransfer);
            
            if(includeAliases) {
                setupGeoCodeAliasTransfers(geoCode, countryTransfer);
            }
        }
        
        return countryTransfer;
    }
    
}
