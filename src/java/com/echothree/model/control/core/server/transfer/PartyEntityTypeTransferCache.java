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

package com.echothree.model.control.core.server.transfer;

import com.echothree.model.control.core.remote.transfer.EntityTypeTransfer;
import com.echothree.model.control.core.remote.transfer.PartyEntityTypeTransfer;
import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.control.party.remote.transfer.PartyTransfer;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.data.core.server.entity.PartyEntityType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class PartyEntityTypeTransferCache
        extends BaseCoreTransferCache<PartyEntityType, PartyEntityTypeTransfer> {
    
    PartyControl partyControl;
    
    /** Creates a new instance of PartyEntityTypeTransferCache */
    public PartyEntityTypeTransferCache(UserVisit userVisit, CoreControl coreControl) {
        super(userVisit, coreControl);
        
        partyControl = (PartyControl)Session.getModelController(PartyControl.class);
    }
    
    public PartyEntityTypeTransfer getPartyEntityTypeTransfer(PartyEntityType partyEntityType) {
        PartyEntityTypeTransfer partyEntityTypeTransfer = get(partyEntityType);
        
        if(partyEntityTypeTransfer == null) {
            PartyTransfer party = partyControl.getPartyTransfer(userVisit, partyEntityType.getParty());
            EntityTypeTransfer entityType = coreControl.getEntityTypeTransfer(userVisit, partyEntityType.getEntityType());
            Boolean confirmDelete = partyEntityType.getConfirmDelete();
            
            partyEntityTypeTransfer = new PartyEntityTypeTransfer(party, entityType, confirmDelete);
            put(partyEntityType, partyEntityTypeTransfer);
        }
        
        return partyEntityTypeTransfer;
    }
    
}
