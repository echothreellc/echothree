// --------------------------------------------------------------------------------
// Copyright 2002-2023 Echo Three, LLC
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

import com.echothree.model.control.party.common.transfer.PartyRelationshipTransfer;
import com.echothree.model.control.party.common.transfer.PartyTransfer;
import com.echothree.model.control.party.common.transfer.RoleTypeTransfer;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.party.server.entity.PartyRelationship;
import com.echothree.model.data.user.server.entity.UserVisit;

public class PartyRelationshipTransferCache
        extends BasePartyTransferCache<PartyRelationship, PartyRelationshipTransfer> {
    
    /** Creates a new instance of PartyRelationshipTransferCache */
    public PartyRelationshipTransferCache(UserVisit userVisit, PartyControl partyControl) {
        super(userVisit, partyControl);
    }
    
    public PartyRelationshipTransfer getPartyRelationshipTransfer(PartyRelationship partyRelationship) {
        PartyRelationshipTransfer partyRelationshipTransfer = get(partyRelationship);
        
        if(partyRelationshipTransfer == null) {
            PartyTransfer fromParty = partyControl.getPartyTransfer(userVisit, partyRelationship.getFromParty());
            RoleTypeTransfer fromRoleType = partyControl.getRoleTypeTransfer(userVisit, partyRelationship.getFromRoleType());
            PartyTransfer toParty = partyControl.getPartyTransfer(userVisit, partyRelationship.getToParty());
            RoleTypeTransfer toRoleType = partyControl.getRoleTypeTransfer(userVisit, partyRelationship.getFromRoleType());
            
            partyRelationshipTransfer = new PartyRelationshipTransfer(fromParty, fromRoleType, toParty, toRoleType);
            put(partyRelationship, partyRelationshipTransfer);
        }
        
        return partyRelationshipTransfer;
    }
    
}
