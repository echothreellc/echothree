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

package com.echothree.ui.cli.dataloader.util.zipcode;

public class ZipCodeData {
    
    private String copyrightDetailCode;
    private String zipCode;
    private String cityStateName;
    private String cityStateNameFacilityCode;
    private String cityStateMailingNameIndicator;
    private String preferredLastLineCityStateName;
    private String stateAbbreviation;
    private String countyNumber;
    private String countyName;
    
    /** Creates a new instance of ZipCodeData */
    public ZipCodeData(String zipCodeLine) {
        var length = zipCodeLine.length();
        
        try {
            copyrightDetailCode = zipCodeLine.substring(0, 1);

            if("D".equals(copyrightDetailCode)) {
                zipCode = zipCodeLine.substring(1, 6).trim();
                cityStateName = zipCodeLine.substring(13, 41).trim();
                cityStateNameFacilityCode = zipCodeLine.substring(54, 55);
                cityStateMailingNameIndicator = zipCodeLine.substring(55, 56);
                preferredLastLineCityStateName = zipCodeLine.substring(62, 90).trim();
                stateAbbreviation = zipCodeLine.substring(99, 101);

                if(length > 101) {
                    countyNumber = zipCodeLine.substring(101, 104);
                    countyName = zipCodeLine.substring(104).trim();
                } else {
                    countyNumber = null;
                    countyName = null;
                }
            }
        } catch (StringIndexOutOfBoundsException sioobe) {
            System.err.println("StringIndexOutOfBoundsException: \"" + zipCodeLine + "\", length = " + length);
            throw sioobe;
        }
    }
    
    @Override
    public String toString() {
        var sb = new StringBuilder();
        sb.append("{ copyrightDetailCode = '").append(copyrightDetailCode);
        sb.append(", zipCode = '").append(zipCode);
        sb.append("', cityStateName = '").append(cityStateName);
        sb.append("', cityStateNameFacilityCode = '").append(cityStateNameFacilityCode);
        sb.append("', cityStateMailingNameIndicator = '").append(cityStateMailingNameIndicator);
        sb.append("', preferredLastLineCityStateName = '").append(preferredLastLineCityStateName);
        sb.append("', stateAbbreviation = '").append(stateAbbreviation);
        sb.append("', countyNumber = '").append(countyNumber);
        sb.append("', countyName = '").append(countyName).append("\' }");
        return sb.toString();
    }
    
    public String getCopyrightDetailCode() {
        return copyrightDetailCode;
    }
    
    public void setCopyrightDetailCode(String copyrightDetailCode) {
        this.copyrightDetailCode = copyrightDetailCode;
    }
    
    public String getZipCode() {
        return zipCode;
    }
    
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
    
    public String getCityStateName() {
        return cityStateName;
    }
    
    public void setCityStateName(String cityStateName) {
        this.cityStateName = cityStateName;
    }
    
    public String getCityStateNameFacilityCode() {
        return cityStateNameFacilityCode;
    }
    
    public void setCityStateNameFacilityCode(String cityStateNameFacilityCode) {
        this.cityStateNameFacilityCode = cityStateNameFacilityCode;
    }
    
    public String getCityStateMailingNameIndicator() {
        return cityStateMailingNameIndicator;
    }
    
    public void setCityStateMailingNameIndicator(String cityStateMailingNameIndicator) {
        this.cityStateMailingNameIndicator = cityStateMailingNameIndicator;
    }
    
    public String getPreferredLastLineCityStateName() {
        return preferredLastLineCityStateName;
    }
    
    public void setPreferredLastLineCityStateName(String preferredLastLineCityStateName) {
        this.preferredLastLineCityStateName = preferredLastLineCityStateName;
    }
    
    public String getStateAbbreviation() {
        return stateAbbreviation;
    }
    
    public void setStateAbbreviation(String stateAbbreviation) {
        this.stateAbbreviation = stateAbbreviation;
    }
    
    public String getCountyNumber() {
        return countyNumber;
    }
    
    public void setCountyNumber(String countyNumber) {
        this.countyNumber = countyNumber;
    }
    
    public String getCountyName() {
        return countyName;
    }
    
    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }
    
}
