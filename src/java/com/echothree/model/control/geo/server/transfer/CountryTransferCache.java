// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

import com.echothree.model.control.contact.common.transfer.PostalAddressFormatTransfer;
import com.echothree.model.control.contact.server.control.ContactControl;
import com.echothree.model.control.geo.common.GeoOptions;
import com.echothree.model.control.geo.common.transfer.CountryTransfer;
import com.echothree.model.control.geo.common.transfer.GeoCodeScopeTransfer;
import com.echothree.model.control.geo.common.transfer.GeoCodeTypeTransfer;
import com.echothree.model.control.geo.server.control.GeoControl;
import com.echothree.model.data.geo.server.entity.GeoCode;
import com.echothree.model.data.geo.server.entity.GeoCodeCountry;
import com.echothree.model.data.geo.server.entity.GeoCodeDetail;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import java.util.Set;

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
        CountryTransfer countryTransfer = get(geoCode);
        
        if(countryTransfer == null) {
            GeoCodeDetail geoCodeDetail = geoCode.getLastDetail();
            GeoCodeCountry geoCodeCountry = geoControl.getGeoCodeCountry(geoCode);
            String geoCodeName = geoCodeDetail.getGeoCodeName();
            GeoCodeTypeTransfer geoCodeType = geoControl.getGeoCodeTypeTransfer(userVisit, geoCodeDetail.getGeoCodeType());
            GeoCodeScopeTransfer geoCodeScope = geoControl.getGeoCodeScopeTransfer(userVisit, geoCodeDetail.getGeoCodeScope());
            Boolean isDefault = geoCodeDetail.getIsDefault();
            Integer sortOrder = geoCodeDetail.getSortOrder();
            String telephoneCode = geoCodeCountry.getTelephoneCode();
            String areaCodePattern = geoCodeCountry.getAreaCodePattern();
            Boolean areaCodeRequired = geoCodeCountry.getAreaCodeRequired();
            String areaCodeExample = geoCodeCountry.getAreaCodeExample();
            String telephoneNumberPattern = geoCodeCountry.getTelephoneNumberPattern();
            String telephoneNumberExample = geoCodeCountry.getTelephoneNumberExample();
            PostalAddressFormatTransfer postalAddressFormat = contactControl.getPostalAddressFormatTransfer(userVisit, geoCodeCountry.getPostalAddressFormat());
            Boolean cityRequired = geoCodeCountry.getCityRequired();
            Boolean cityGeoCodeRequired = geoCodeCountry.getCityGeoCodeRequired();
            Boolean stateRequired = geoCodeCountry.getStateRequired();
            Boolean stateGeoCodeRequired = geoCodeCountry.getCityGeoCodeRequired();
            String postalCodePattern = geoCodeCountry.getPostalCodePattern();
            Boolean postalCodeRequired = geoCodeCountry.getPostalCodeRequired();
            Boolean postalCodeGeoCodeRequired = geoCodeCountry.getPostalCodeGeoCodeRequired();
            Integer postalCodeLength = geoCodeCountry.getPostalCodeLength();
            Integer postalCodeGeoCodeLength = geoCodeCountry.getPostalCodeGeoCodeLength();
            String postalCodeExample = geoCodeCountry.getPostalCodeExample();
            String description = geoControl.getBestGeoCodeDescription(geoCode, getLanguage());
            
            countryTransfer = new CountryTransfer(geoCodeName, geoCodeType, geoCodeScope, isDefault, sortOrder, telephoneCode,
                    areaCodePattern, areaCodeRequired, areaCodeExample, telephoneNumberPattern, telephoneNumberExample,
                    postalAddressFormat, cityRequired, cityGeoCodeRequired, stateRequired, stateGeoCodeRequired, postalCodePattern,
                    postalCodeRequired, postalCodeGeoCodeRequired, postalCodeLength, postalCodeGeoCodeLength, postalCodeExample,
                    description);
            put(geoCode, countryTransfer);
            
            if(includeAliases) {
                setupGeoCodeAliasTransfers(geoCode, countryTransfer);
            }
        }
        
        return countryTransfer;
    }
    
}
