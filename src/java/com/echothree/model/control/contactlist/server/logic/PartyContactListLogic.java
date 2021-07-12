// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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

package com.echothree.model.control.contactlist.server.logic;

import com.echothree.model.control.contact.server.control.ContactControl;
import com.echothree.model.control.contactlist.common.exception.DuplicatePartyContactListException;
import com.echothree.model.control.contactlist.common.exception.UnknownPreferredContactMechanismPurposeNameException;
import com.echothree.model.control.contactlist.server.control.ContactListControl;
import com.echothree.model.control.party.server.logic.PartyLogic;
import com.echothree.model.data.contact.server.entity.ContactMechanismPurpose;
import com.echothree.model.data.contactlist.server.entity.ContactListContactMechanismPurpose;
import com.echothree.model.data.contactlist.server.entity.PartyContactList;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;

public class PartyContactListLogic
    extends BaseLogic {

    private PartyContactListLogic() {
        super();
    }
    
    private static class PartyContactListLogicHolder {
        static PartyContactListLogic instance = new PartyContactListLogic();
    }
    
    public static PartyContactListLogic getInstance() {
        return PartyContactListLogicHolder.instance;
    }

    public PartyContactList createPartyContactList(final ExecutionErrorAccumulator eea, final UserVisitPK userVisitPK,
            final String partyName, final String contactListName, final String preferredContactMechanismPurposeName,
            final BasePK createdBy) {
        var contactListLogic = ContactListLogic.getInstance();
        PartyContactList partyContactList = null;
        var party = PartyLogic.getInstance().getParty(eea, userVisitPK, partyName);
        var contactList = contactListLogic.getContactListByName(eea, contactListName);

        if(!eea.hasExecutionErrors()) {
            var contactListControl = (ContactListControl)Session.getModelController(ContactListControl.class);
            partyContactList = contactListControl.getPartyContactList(party, contactList);

            if(partyContactList == null) {
                var contactControl = (ContactControl)Session.getModelController(ContactControl.class);
                ContactMechanismPurpose preferredContactMechanismPurpose = preferredContactMechanismPurposeName == null ?
                        null : contactControl.getContactMechanismPurposeByName(preferredContactMechanismPurposeName);

                if(preferredContactMechanismPurposeName == null || preferredContactMechanismPurpose != null) {
                    ContactListContactMechanismPurpose preferredContactListContactMechanismPurpose = preferredContactMechanismPurpose == null ?
                            null : contactListLogic.getContactListContactMechanismPurpose(eea, contactList, preferredContactMechanismPurpose);

                    if(!eea.hasExecutionErrors()) {
                        // ExecutionErrorAccumulator is passed in as null so that an Exception will be thrown if there is an error.
                        // This has to do with insertion into the Workflow - if there is an error locating the correct
                        // Workflow or WorkflowEntrance, handling of that is poor currently.
                        partyContactList = contactListLogic.addContactListToParty(null, party, contactList,
                                preferredContactListContactMechanismPurpose, createdBy);
                    }
                } else {
                    handleExecutionError(UnknownPreferredContactMechanismPurposeNameException.class, eea,
                            ExecutionErrors.UnknownPreferredContactMechanismPurposeName.name(), partyName, contactListName);
                }
            } else {
                handleExecutionError(DuplicatePartyContactListException.class, eea,
                        ExecutionErrors.DuplicatePartyContactList.name(), partyName, contactListName);
            }
        }

        return partyContactList;
    }
    
}
