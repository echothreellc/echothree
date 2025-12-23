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

package com.echothree.control.user.geo.common.edit;

import com.echothree.util.common.form.BaseEdit;

public interface CountryEdit
        extends BaseEdit {
    
    String getCountryName();
    void setCountryName(String countryName);
    
    String getIso3Number();
    void setIso3Number(String iso3Number);
    
    String getIso3Letter();
    void setIso3Letter(String iso3Letter);
    
    String getIso2Letter();
    void setIso2Letter(String iso2Letter);
    
    String getTelephoneCode();
    void setTelephoneCode(String telephoneCode);
    
    String getAreaCodePattern();
    void setAreaCodePattern(String areaCodePattern);
    
    String getAreaCodeRequired();
    void setAreaCodeRequired(String areaCodeRequired);
    
    String getAreaCodeExample();
    void setAreaCodeExample(String areaCodeExample);
    
    String getTelephoneNumberPattern();
    void setTelephoneNumberPattern(String telephoneNumberPattern);
    
    String getTelephoneNumberExample();
    void setTelephoneNumberExample(String telephoneNumberExample);
    
    String getPostalAddressFormatName();
    void setPostalAddressFormatName(String postalAddressFormatName);
    
    String getCityRequired();
    void setCityRequired(String cityRequired);
    
    String getCityGeoCodeRequired();
    void setCityGeoCodeRequired(String cityGeoCodeRequired);
    
    String getStateRequired();
    void setStateRequired(String stateRequired);
    
    String getStateGeoCodeRequired();
    void setStateGeoCodeRequired(String stateGeoCodeRequired);
    
    String getPostalCodePattern();
    void setPostalCodePattern(String postalCodePattern);
    
    String getPostalCodeRequired();
    void setPostalCodeRequired(String postalCodeRequired);
    
    String getPostalCodeGeoCodeRequired();
    void setPostalCodeGeoCodeRequired(String postalCodeGeoCodeRequired);
    
    String getPostalCodeLength();
    void setPostalCodeLength(String postalCodeLength);
    
    String getPostalCodeGeoCodeLength();
    void setPostalCodeGeoCodeLength(String postalCodeGeoCodeLength);
    
    String getPostalCodeExample();
    void setPostalCodeExample(String postalCodeExample);
    
    String getIsDefault();
    void setIsDefault(String isDefault);
    
    String getSortOrder();
    void setSortOrder(String sortOrder);
    
    String getDescription();
    void setDescription(String description);
    
}
