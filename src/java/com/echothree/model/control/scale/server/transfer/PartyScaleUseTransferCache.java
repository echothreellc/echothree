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

package com.echothree.model.control.scale.server.transfer;

import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.scale.common.transfer.PartyScaleUseTransfer;
import com.echothree.model.control.scale.server.control.ScaleControl;
import com.echothree.model.data.scale.server.entity.PartyScaleUse;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class PartyScaleUseTransferCache
        extends BaseScaleTransferCache<PartyScaleUse, PartyScaleUseTransfer> {
    
    PartyControl partyControl;
    
    /** Creates a new instance of PartyScaleUseTransferCache */
    public PartyScaleUseTransferCache(ScaleControl scaleControl) {
        super(scaleControl);
        partyControl = Session.getModelController(PartyControl.class);
    }
    
    public PartyScaleUseTransfer getPartyScaleUseTransfer(UserVisit userVisit, PartyScaleUse partyScaleUse) {
        var partyScaleUseTransfer = get(partyScaleUse);
        
        if(partyScaleUseTransfer == null) {
            var party = partyControl.getPartyTransfer(userVisit, partyScaleUse.getParty());
            var scaleUseType = scaleControl.getScaleUseTypeTransfer(userVisit, partyScaleUse.getScaleUseType());
            var scale = scaleControl.getScaleTransfer(userVisit, partyScaleUse.getScale());
            
            partyScaleUseTransfer = new PartyScaleUseTransfer(party, scaleUseType, scale);
            put(userVisit, partyScaleUse, partyScaleUseTransfer);
        }
        
        return partyScaleUseTransfer;
    }
    
}
