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

package com.echothree.control.user.contact.common.edit;

public interface ContactPostalAddressEdit
        extends ContactMechanismEdit, PartyContactMechanismEdit {
    
    String getPersonalTitleId();
    void setPersonalTitleId(String personalTitleId);
    
    String getFirstName();
    void setFirstName(String firstName);
    
    String getMiddleName();
    void setMiddleName(String middleName);
    
    String getLastName();
    void setLastName(String lastName);
    
    String getNameSuffixId();
    void setNameSuffixId(String nameSuffixId);
    
    String getCompanyName();
    void setCompanyName(String companyName);

    String getAttention();
    void setAttention(String attention);

    String getAddress1();
    void setAddress1(String address1);
    
    String getAddress2();
    void setAddress2(String address2);
    
    String getAddress3();
    void setAddress3(String address3);
    
    String getCity();
    void setCity(String city);
    
    String getState();
    void setState(String state);
    
    String getPostalCode();
    void setPostalCode(String postalCode);
    
    String getCountryName();
    void setCountryName(String countryName);
    
    String getIsCommercial();
    void setIsCommercial(String isCommercial);
    
}
