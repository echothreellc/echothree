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

package com.echothree.control.user.search.common.form;

import com.echothree.control.user.party.common.spec.PartySpec;
import com.echothree.control.user.vendor.common.spec.VendorSpec;

public interface SearchVendorsForm
        extends VendorSpec, PartySpec {
    
    String getSearchTypeName();
    void setSearchTypeName(String searchTypeName);
    
    String getFirstName();
    void setFirstName(String firstName);
    
    String getFirstNameSoundex();
    void setFirstNameSoundex(String firstNameSoundex);
    
    String getMiddleName();
    void setMiddleName(String middleName);
    
    String getMiddleNameSoundex();
    void setMiddleNameSoundex(String middleNameSoundex);
    
    String getLastName();
    void setLastName(String lastName);
    
    String getLastNameSoundex();
    void setLastNameSoundex(String lastNameSoundex);
    
    String getName();
    void setName(String name);

    String getPartyAliasTypeName();
    void setPartyAliasTypeName(String partyAliasTypeName);

    String getAlias();
    void setAlias(String alias);

    String getCreatedSince();
    void setCreatedSince(String createdSince);

    String getModifiedSince();
    void setModifiedSince(String modifiedSince);
    
    String getFields();
    void setFields(String fields);
    
}
