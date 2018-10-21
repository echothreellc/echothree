// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

import com.echothree.model.control.party.remote.transfer.PartyAliasTransfer;
import com.echothree.model.control.party.remote.transfer.PartyAliasTypeTransfer;
import com.echothree.model.control.party.remote.transfer.PartyTransfer;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.data.party.server.entity.PartyAlias;
import com.echothree.model.data.user.server.entity.UserVisit;

public class PartyAliasTransferCache
        extends BasePartyTransferCache<PartyAlias, PartyAliasTransfer> {
    
    /** Creates a new instance of PartyAliasTransferCache */
    public PartyAliasTransferCache(UserVisit userVisit, PartyControl partyControl) {
        super(userVisit, partyControl);
        
        setIncludeEntityInstance(true);
    }
    
    public PartyAliasTransfer getPartyAliasTransfer(PartyAlias partyAlias) {
        PartyAliasTransfer partyAliasTransfer = get(partyAlias);
        
        if(partyAliasTransfer == null) {
            PartyTransfer party = partyControl.getPartyTransfer(userVisit, partyAlias.getParty());
            PartyAliasTypeTransfer partyAliasType = partyControl.getPartyAliasTypeTransfer(userVisit, partyAlias.getPartyAliasType());
            String alias = partyAlias.getAlias();
            
            partyAliasTransfer = new PartyAliasTransfer(party, partyAliasType, alias);
            put(partyAlias, partyAliasTransfer);
        }
        
        return partyAliasTransfer;
    }
    
}
