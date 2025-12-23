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

package com.echothree.model.control.party.common.transfer;

import com.echothree.util.common.transfer.BaseTransfer;

public class PersonTransfer
        extends BaseTransfer {
    
    private PersonalTitleTransfer personalTitle;
    private String firstName;
    private String middleName;
    private String lastName;
    private NameSuffixTransfer nameSuffix;
    
    /** Creates a new instance of PersonTransfer */
    public PersonTransfer(PersonalTitleTransfer personalTitle, String firstName, String middleName, String lastName,
            NameSuffixTransfer nameSuffix) {
        this.personalTitle = personalTitle;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.nameSuffix = nameSuffix;
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
    
}
