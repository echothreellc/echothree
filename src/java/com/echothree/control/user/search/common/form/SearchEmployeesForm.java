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

package com.echothree.control.user.search.common.form;

import com.echothree.control.user.party.common.spec.EmployeeSpec;
import com.echothree.control.user.party.common.spec.PartySpec;

public interface SearchEmployeesForm
        extends EmployeeSpec, PartySpec  {
    
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

    String getPartyAliasTypeName();
    void setPartyAliasTypeName(String partyAliasTypeName);

    String getAlias();
    void setAlias(String alias);

    String getEmployeeStatusChoice();
    void setEmployeeStatusChoice(String employeeStatusChoice);

    String getEmployeeAvailabilityChoice();
    void setEmployeeAvailabilityChoice(String employeeAvailabilityChoice);
    
    String getCreatedSince();
    void setCreatedSince(String createdSince);

    String getModifiedSince();
    void setModifiedSince(String modifiedSince);

    String getFields();
    void setFields(String fields);
    
}
