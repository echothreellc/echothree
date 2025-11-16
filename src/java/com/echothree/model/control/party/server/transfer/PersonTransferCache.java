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

package com.echothree.model.control.party.server.transfer;

import com.echothree.model.control.party.common.transfer.PersonTransfer;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.party.server.entity.Person;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class PersonTransferCache
        extends BasePartyTransferCache<Person, PersonTransfer> {

    PartyControl partyControl = Session.getModelController(PartyControl.class);

    /** Creates a new instance of PersonTransferCache */
    public PersonTransferCache() {
        super();
    }

    @Override
    public PersonTransfer getTransfer(UserVisit userVisit, Person person) {
        var personTransfer = get(person);
        
        if(personTransfer == null) {
            var personalTitle = person.getPersonalTitle();
            var personalTitleTransfer = personalTitle == null? null: partyControl.getPersonalTitleTransfer(userVisit, personalTitle);
            var firstName = person.getFirstName();
            var middleName = person.getMiddleName();
            var lastName = person.getLastName();
            var nameSuffix = person.getNameSuffix();
            var nameSuffixTransfer = nameSuffix == null? null: partyControl.getNameSuffixTransfer(userVisit, nameSuffix);
            
            personTransfer = new PersonTransfer(personalTitleTransfer, firstName, middleName, lastName, nameSuffixTransfer);
            put(userVisit, person, personTransfer);
        }
        
        return personTransfer;
    }
    
}
