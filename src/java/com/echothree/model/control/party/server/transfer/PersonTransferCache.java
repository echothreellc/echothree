// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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

package com.echothree.model.control.party.server.transfer;

import com.echothree.model.control.party.common.transfer.NameSuffixTransfer;
import com.echothree.model.control.party.common.transfer.PersonTransfer;
import com.echothree.model.control.party.common.transfer.PersonalTitleTransfer;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.party.server.entity.NameSuffix;
import com.echothree.model.data.party.server.entity.Person;
import com.echothree.model.data.party.server.entity.PersonalTitle;
import com.echothree.model.data.user.server.entity.UserVisit;

public class PersonTransferCache
        extends BasePartyTransferCache<Person, PersonTransfer> {
    
    /** Creates a new instance of PersonTransferCache */
    public PersonTransferCache(UserVisit userVisit, PartyControl partyControl) {
        super(userVisit, partyControl);
    }
    
    public PersonTransfer getPersonTransfer(Person person) {
        PersonTransfer personTransfer = get(person);
        
        if(personTransfer == null) {
            PersonalTitle personalTitle = person.getPersonalTitle();
            PersonalTitleTransfer personalTitleTransfer = personalTitle == null? null: partyControl.getPersonalTitleTransfer(userVisit, personalTitle);
            String firstName = person.getFirstName();
            String middleName = person.getMiddleName();
            String lastName = person.getLastName();
            NameSuffix nameSuffix = person.getNameSuffix();
            NameSuffixTransfer nameSuffixTransfer = nameSuffix == null? null: partyControl.getNameSuffixTransfer(userVisit, nameSuffix);
            
            personTransfer = new PersonTransfer(personalTitleTransfer, firstName, middleName, lastName, nameSuffixTransfer);
            put(person, personTransfer);
        }
        
        return personTransfer;
    }
    
}
