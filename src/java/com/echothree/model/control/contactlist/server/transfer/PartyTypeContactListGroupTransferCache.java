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

package com.echothree.model.control.contactlist.server.transfer;

import com.echothree.model.control.contactlist.common.transfer.PartyTypeContactListGroupTransfer;
import com.echothree.model.control.contactlist.server.control.ContactListControl;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.contactlist.server.entity.PartyTypeContactListGroup;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class PartyTypeContactListGroupTransferCache
        extends BaseContactListTransferCache<PartyTypeContactListGroup, PartyTypeContactListGroupTransfer> {
    
    ContactListControl contactListControl = Session.getModelController(ContactListControl.class);
    PartyControl partyControl = Session.getModelController(PartyControl.class);
    
    /** Creates a new instance of PartyTypeContactListGroupTransferCache */
    protected PartyTypeContactListGroupTransferCache() {
        super();
    }
    
    public PartyTypeContactListGroupTransfer getPartyTypeContactListGroupTransfer(UserVisit userVisit, PartyTypeContactListGroup partyTypeContactListGroup) {
        var partyTypeContactListGroupTransfer = get(partyTypeContactListGroup);
        
        if(partyTypeContactListGroupTransfer == null) {
            var partyTypeTransfer = partyControl.getPartyTypeTransfer(userVisit, partyTypeContactListGroup.getPartyType());
            var contactListGroupTransfer = contactListControl.getContactListGroupTransfer(userVisit, partyTypeContactListGroup.getContactListGroup());
            var addWhenCreated = partyTypeContactListGroup.getAddWhenCreated();
            
            partyTypeContactListGroupTransfer = new PartyTypeContactListGroupTransfer(partyTypeTransfer, contactListGroupTransfer, addWhenCreated);
            put(userVisit, partyTypeContactListGroup, partyTypeContactListGroupTransfer);
        }
        
        return partyTypeContactListGroupTransfer;
    }
    
}
