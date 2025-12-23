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

package com.echothree.model.control.user.common.transfer;

import com.echothree.model.control.party.common.transfer.PartyRelationshipTransfer;
import com.echothree.model.control.party.common.transfer.PartyTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class UserKeyTransfer
        extends BaseTransfer {
    
    private String userKeyName;
    private PartyTransfer party;
    private PartyRelationshipTransfer partyRelationship;
    
    /** Creates a new instance of UserKeyTransfer */
    public UserKeyTransfer(String userKeyName, PartyTransfer party, PartyRelationshipTransfer partyRelationship) {
        this.userKeyName = userKeyName;
        this.party = party;
        this.partyRelationship = partyRelationship;
    }
    
    public String getUserKeyName() {
        return userKeyName;
    }
    
    public void setUserKeyName(String userKeyName) {
        this.userKeyName = userKeyName;
    }
    
    public PartyTransfer getParty() {
        return party;
    }
    
    public void setParty(PartyTransfer party) {
        this.party = party;
    }
    
    public PartyRelationshipTransfer getPartyRelationship() {
        return partyRelationship;
    }
    
    public void setPartyRelationship(PartyRelationshipTransfer partyRelationship) {
        this.partyRelationship = partyRelationship;
    }
    
}
