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

package com.echothree.model.control.geo.common.transfer;

import com.echothree.model.control.contact.common.transfer.PostalAddressFormatTransfer;

public class CountryTransfer
        extends GeoCodeTransfer {
    
    private String telephoneCode;
    private String areaCodePattern;
    private Boolean areaCodeRequired;
    private String areaCodeExample;
    private String telephoneNumberPattern;
    private String telephoneNumberExample;
    private PostalAddressFormatTransfer postalAddressFormat;
    private Boolean cityRequired;
    private Boolean cityGeoCodeRequired;
    private Boolean stateRequired;
    private Boolean stateGeoCodeRequired;
    private String postalCodePattern;
    private Boolean postalCodeRequired;
    private Boolean postalCodeGeoCodeRequired;
    private Integer postalCodeLength;
    private Integer postalCodeGeoCodeLength;
    private String postalCodeExample;
    
    /** Creates a new instance of CountryTransfer */
    public CountryTransfer(String geoCodeName, GeoCodeTypeTransfer geoCodeType, GeoCodeScopeTransfer geoCodeScope,
            Boolean isDefault, Integer sortOrder, String telephoneCode, String areaCodePattern, Boolean areaCodeRequired,
            String areaCodeExample, String telephoneNumberPattern, String telephoneNumberExample,
            PostalAddressFormatTransfer postalAddressFormat, Boolean cityRequired, Boolean cityGeoCodeRequired,
            Boolean stateRequired, Boolean stateGeoCodeRequired, String postalCodePattern, Boolean postalCodeRequired,
            Boolean postalCodeGeoCodeRequired, Integer postalCodeLength, Integer postalCodeGeoCodeLength, String postalCodeExample,
            String description) {
        super(geoCodeName, geoCodeType, geoCodeScope, isDefault, sortOrder, description);
        
        this.telephoneCode = telephoneCode;
        this.areaCodePattern = areaCodePattern;
        this.areaCodeRequired = areaCodeRequired;
        this.areaCodeExample = areaCodeExample;
        this.telephoneNumberPattern = telephoneNumberPattern;
        this.telephoneNumberExample = telephoneNumberExample;
        this.postalAddressFormat = postalAddressFormat;
        this.cityRequired = cityRequired;
        this.cityGeoCodeRequired = cityGeoCodeRequired;
        this.stateRequired = stateRequired;
        this.stateGeoCodeRequired = stateGeoCodeRequired;
        this.postalCodePattern = postalCodePattern;
        this.postalCodeRequired = postalCodeRequired;
        this.postalCodeGeoCodeRequired = postalCodeGeoCodeRequired;
        this.postalCodeLength = postalCodeLength;
        this.postalCodeGeoCodeLength = postalCodeGeoCodeLength;
        this.postalCodeExample = postalCodeExample;
    }
    
    public String getTelephoneCode() {
        return telephoneCode;
    }
    
    public void setTelephoneCode(String telephoneCode) {
        this.telephoneCode = telephoneCode;
    }
    
    public String getAreaCodePattern() {
        return areaCodePattern;
    }
    
    public void setAreaCodePattern(String areaCodePattern) {
        this.areaCodePattern = areaCodePattern;
    }
    
    public Boolean getAreaCodeRequired() {
        return areaCodeRequired;
    }
    
    public void setAreaCodeRequired(Boolean areaCodeRequired) {
        this.areaCodeRequired = areaCodeRequired;
    }
    
    public String getAreaCodeExample() {
        return areaCodeExample;
    }
    
    public void setAreaCodeExample(String areaCodeExample) {
        this.areaCodeExample = areaCodeExample;
    }
    
    public String getTelephoneNumberPattern() {
        return telephoneNumberPattern;
    }
    
    public void setTelephoneNumberPattern(String telephoneNumberPattern) {
        this.telephoneNumberPattern = telephoneNumberPattern;
    }
    
    public String getTelephoneNumberExample() {
        return telephoneNumberExample;
    }
    
    public void setTelephoneNumberExample(String telephoneNumberExample) {
        this.telephoneNumberExample = telephoneNumberExample;
    }
    
    public PostalAddressFormatTransfer getPostalAddressFormat() {
        return postalAddressFormat;
    }
    
    public void setPostalAddressFormat(PostalAddressFormatTransfer postalAddressFormat) {
        this.postalAddressFormat = postalAddressFormat;
    }
    
    public Boolean getCityRequired() {
        return cityRequired;
    }
    
    public void setCityRequired(Boolean cityRequired) {
        this.cityRequired = cityRequired;
    }
    
    public Boolean getCityGeoCodeRequired() {
        return cityGeoCodeRequired;
    }
    
    public void setCityGeoCodeRequired(Boolean cityGeoCodeRequired) {
        this.cityGeoCodeRequired = cityGeoCodeRequired;
    }
    
    public Boolean getStateRequired() {
        return stateRequired;
    }
    
    public void setStateRequired(Boolean stateRequired) {
        this.stateRequired = stateRequired;
    }
    
    public Boolean getStateGeoCodeRequired() {
        return stateGeoCodeRequired;
    }
    
    public void setStateGeoCodeRequired(Boolean stateGeoCodeRequired) {
        this.stateGeoCodeRequired = stateGeoCodeRequired;
    }
    
    public String getPostalCodePattern() {
        return postalCodePattern;
    }
    
    public void setPostalCodePattern(String postalCodePattern) {
        this.postalCodePattern = postalCodePattern;
    }
    
    public Boolean getPostalCodeRequired() {
        return postalCodeRequired;
    }
    
    public void setPostalCodeRequired(Boolean postalCodeRequired) {
        this.postalCodeRequired = postalCodeRequired;
    }
    
    public Boolean getPostalCodeGeoCodeRequired() {
        return postalCodeGeoCodeRequired;
    }
    
    public void setPostalCodeGeoCodeRequired(Boolean postalCodeGeoCodeRequired) {
        this.postalCodeGeoCodeRequired = postalCodeGeoCodeRequired;
    }
    
    public Integer getPostalCodeLength() {
        return postalCodeLength;
    }
    
    public void setPostalCodeLength(Integer postalCodeLength) {
        this.postalCodeLength = postalCodeLength;
    }
    
    public Integer getPostalCodeGeoCodeLength() {
        return postalCodeGeoCodeLength;
    }
    
    public void setPostalCodeGeoCodeLength(Integer postalCodeGeoCodeLength) {
        this.postalCodeGeoCodeLength = postalCodeGeoCodeLength;
    }
    
    public String getPostalCodeExample() {
        return postalCodeExample;
    }
    
    public void setPostalCodeExample(String postalCodeExample) {
        this.postalCodeExample = postalCodeExample;
    }
    
}
