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

package com.echothree.model.control.security.server.transfer;

import com.echothree.model.control.party.remote.transfer.PartyTypeTransfer;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.security.remote.transfer.SecurityRolePartyTypeTransfer;
import com.echothree.model.control.security.remote.transfer.SecurityRoleTransfer;
import com.echothree.model.control.security.server.SecurityControl;
import com.echothree.model.control.selector.remote.transfer.SelectorTransfer;
import com.echothree.model.control.selector.server.SelectorControl;
import com.echothree.model.data.security.server.entity.SecurityRolePartyType;
import com.echothree.model.data.selector.server.entity.Selector;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class SecurityRolePartyTypeTransferCache
        extends BaseSecurityTransferCache<SecurityRolePartyType, SecurityRolePartyTypeTransfer> {
    
    PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);
    SelectorControl selectorControl = (SelectorControl)Session.getModelController(SelectorControl.class);
        
    /** Creates a new instance of SecurityRolePartyTypeTransferCache */
    public SecurityRolePartyTypeTransferCache(UserVisit userVisit, SecurityControl securityControl) {
        super(userVisit, securityControl);
    }
    
    public SecurityRolePartyTypeTransfer getSecurityRolePartyTypeTransfer(SecurityRolePartyType securityRolePartyType) {
        SecurityRolePartyTypeTransfer securityRolePartyTypeTransfer = get(securityRolePartyType);
        
        if(securityRolePartyTypeTransfer == null) {
            SecurityRoleTransfer securityRoleTransfer = securityControl.getSecurityRoleTransfer(userVisit, securityRolePartyType.getSecurityRole());
            PartyTypeTransfer partyTypeTransfer = partyControl.getPartyTypeTransfer(userVisit, securityRolePartyType.getPartyType());
            Selector partySelector = securityRolePartyType.getPartySelector();
            SelectorTransfer partySelectorTransfer = partySelector == null? null: selectorControl.getSelectorTransfer(userVisit, partySelector);
            
            securityRolePartyTypeTransfer = new SecurityRolePartyTypeTransfer(securityRoleTransfer, partyTypeTransfer, partySelectorTransfer);
            put(securityRolePartyType, securityRolePartyTypeTransfer);
        }
        
        return securityRolePartyTypeTransfer;
    }
    
}
