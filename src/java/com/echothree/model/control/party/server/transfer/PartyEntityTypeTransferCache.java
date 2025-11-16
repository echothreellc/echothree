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

import com.echothree.model.control.core.server.control.EntityTypeControl;
import com.echothree.model.control.core.server.transfer.BaseCoreTransferCache;
import com.echothree.model.control.party.common.transfer.PartyEntityTypeTransfer;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.party.server.entity.PartyEntityType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class PartyEntityTypeTransferCache
        extends BaseCoreTransferCache<PartyEntityType, PartyEntityTypeTransfer> {

    EntityTypeControl entityTypeControl = Session.getModelController(EntityTypeControl.class);
    PartyControl partyControl = Session.getModelController(PartyControl.class);
    
    /** Creates a new instance of PartyEntityTypeTransferCache */
    protected PartyEntityTypeTransferCache() {
        super();
    }
    
    public PartyEntityTypeTransfer getPartyEntityTypeTransfer(UserVisit userVisit, PartyEntityType partyEntityType) {
        var partyEntityTypeTransfer = get(partyEntityType);
        
        if(partyEntityTypeTransfer == null) {
            var party = partyControl.getPartyTransfer(userVisit, partyEntityType.getParty());
            var entityType = entityTypeControl.getEntityTypeTransfer(userVisit, partyEntityType.getEntityType());
            var confirmDelete = partyEntityType.getConfirmDelete();
            
            partyEntityTypeTransfer = new PartyEntityTypeTransfer(party, entityType, confirmDelete);
            put(userVisit, partyEntityType, partyEntityTypeTransfer);
        }
        
        return partyEntityTypeTransfer;
    }
    
}
