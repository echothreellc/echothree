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

package com.echothree.model.control.selector.server.transfer;

import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.selector.common.transfer.SelectorPartyTransfer;
import com.echothree.model.control.selector.server.control.SelectorControl;
import com.echothree.model.data.selector.server.entity.SelectorParty;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class SelectorPartyTransferCache
        extends BaseSelectorTransferCache<SelectorParty, SelectorPartyTransfer> {
    
    PartyControl partyControl = Session.getModelController(PartyControl.class);
    SelectorControl selectorControl = Session.getModelController(SelectorControl.class);

    /** Creates a new instance of SelectorPartyTransferCache */
    public SelectorPartyTransferCache() {
        super();
    }
    
    public SelectorPartyTransfer getSelectorPartyTransfer(UserVisit userVisit, SelectorParty selectorParty) {
        var selectorPartyTransfer = get(selectorParty);
        
        if(selectorPartyTransfer == null) {
            var selectorTransfer = selectorControl.getSelectorTransfer(userVisit, selectorParty.getSelector());
            var partyTransfer = partyControl.getPartyTransfer(userVisit, selectorParty.getParty());
            
            selectorPartyTransfer = new SelectorPartyTransfer(selectorTransfer, partyTransfer);
            put(userVisit, selectorParty, selectorPartyTransfer);
        }
        
        return selectorPartyTransfer;
    }
    
}
