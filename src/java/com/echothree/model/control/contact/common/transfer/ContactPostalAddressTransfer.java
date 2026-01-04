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

package com.echothree.model.control.contact.common.transfer;

import com.echothree.model.control.geo.common.transfer.CityTransfer;
import com.echothree.model.control.geo.common.transfer.CountryTransfer;
import com.echothree.model.control.geo.common.transfer.CountyTransfer;
import com.echothree.model.control.geo.common.transfer.PostalCodeTransfer;
import com.echothree.model.control.geo.common.transfer.StateTransfer;
import com.echothree.model.control.party.common.transfer.NameSuffixTransfer;
import com.echothree.model.control.party.common.transfer.PersonalTitleTransfer;
import com.echothree.model.control.workflow.common.transfer.WorkflowEntityStatusTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class ContactPostalAddressTransfer
        extends BaseTransfer {
    
    private PersonalTitleTransfer personalTitle;
    private String firstName;
    private String middleName;
    private String lastName;
    private NameSuffixTransfer nameSuffix;
    private String companyName;
    private String attention;
    private String address1;
    private String address2;
    private String address3;
    private String city;
    private CityTransfer cityGeoCode;
    private CountyTransfer countyGeoCode;
    private String state;
    private StateTransfer stateGeoCode;
    private String postalCode;
    private PostalCodeTransfer postalCodeGeoCode;
    private CountryTransfer countryGeoCode;
    private Boolean isCommercial;
    private WorkflowEntityStatusTransfer postalAddressStatus;
    
    /** Creates a new instance of ContactPostalAddressTransfer */
    public ContactPostalAddressTransfer(PersonalTitleTransfer personalTitle, String firstName, String middleName, String lastName,
            NameSuffixTransfer nameSuffix, String companyName, String attention, String address1, String address2, String address3, String city,
            CityTransfer cityGeoCode, CountyTransfer countyGeoCode, String state, StateTransfer stateGeoCode, String postalCode,
            PostalCodeTransfer postalCodeGeoCode, CountryTransfer countryGeoCode, Boolean isCommercial,
            WorkflowEntityStatusTransfer postalAddressStatus) {
        this.personalTitle = personalTitle;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.nameSuffix = nameSuffix;
        this.companyName = companyName;
        this.attention = attention;
        this.address1 = address1;
        this.address2 = address2;
        this.address3 = address3;
        this.city = city;
        this.cityGeoCode = cityGeoCode;
        this.countyGeoCode = countyGeoCode;
        this.state = state;
        this.stateGeoCode = stateGeoCode;
        this.postalCode = postalCode;
        this.postalCodeGeoCode = postalCodeGeoCode;
        this.countryGeoCode = countryGeoCode;
        this.isCommercial = isCommercial;
        this.postalAddressStatus = postalAddressStatus;
    }
    
    public PersonalTitleTransfer getPersonalTitle() {
        return personalTitle;
    }
    
    public void setPersonalTitle(PersonalTitleTransfer personalTitle) {
        this.personalTitle = personalTitle;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getMiddleName() {
        return middleName;
    }
    
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public NameSuffixTransfer getNameSuffix() {
        return nameSuffix;
    }
    
    public void setNameSuffix(NameSuffixTransfer nameSuffix) {
        this.nameSuffix = nameSuffix;
    }
    
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getAttention() {
        return attention;
    }

    public void setAttention(String attention) {
        this.attention = attention;
    }

    public String getAddress1() {
        return address1;
    }
    
    public void setAddress1(String address1) {
        this.address1 = address1;
    }
    
    public String getAddress2() {
        return address2;
    }
    
    public void setAddress2(String address2) {
        this.address2 = address2;
    }
    
    public String getAddress3() {
        return address3;
    }
    
    public void setAddress3(String address3) {
        this.address3 = address3;
    }
    
    public String getCity() {
        return city;
    }
    
    public void setCity(String city) {
        this.city = city;
    }
    
    public CityTransfer getCityGeoCode() {
        return cityGeoCode;
    }
    
    public void setCityGeoCode(CityTransfer cityGeoCode) {
        this.cityGeoCode = cityGeoCode;
    }
    
    public CountyTransfer getCountyGeoCode() {
        return countyGeoCode;
    }
    
    public void setCountyGeoCode(CountyTransfer countyGeoCode) {
        this.countyGeoCode = countyGeoCode;
    }
    
    public String getState() {
        return state;
    }
    
    public void setState(String state) {
        this.state = state;
    }
    
    public StateTransfer getStateGeoCode() {
        return stateGeoCode;
    }
    
    public void setStateGeoCode(StateTransfer stateGeoCode) {
        this.stateGeoCode = stateGeoCode;
    }
    
    public String getPostalCode() {
        return postalCode;
    }
    
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
    
    public PostalCodeTransfer getPostalCodeGeoCode() {
        return postalCodeGeoCode;
    }
    
    public void setPostalCodeGeoCode(PostalCodeTransfer postalCodeGeoCode) {
        this.postalCodeGeoCode = postalCodeGeoCode;
    }
    
    public CountryTransfer getCountryGeoCode() {
        return countryGeoCode;
    }
    
    public void setCountryGeoCode(CountryTransfer countryGeoCode) {
        this.countryGeoCode = countryGeoCode;
    }
    
    public Boolean getIsCommercial() {
        return isCommercial;
    }
    
    public void setIsCommercial(Boolean isCommercial) {
        this.isCommercial = isCommercial;
    }
    
    public WorkflowEntityStatusTransfer getPostalAddressStatus() {
        return postalAddressStatus;
    }
    
    public void setPostalAddressStatus(WorkflowEntityStatusTransfer postalAddressStatus) {
        this.postalAddressStatus = postalAddressStatus;
    }
    
}
