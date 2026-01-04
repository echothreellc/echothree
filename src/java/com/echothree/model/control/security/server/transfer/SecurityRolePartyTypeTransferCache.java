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

package com.echothree.model.control.security.server.transfer;

import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.transfer.SecurityRolePartyTypeTransfer;
import com.echothree.model.control.security.server.control.SecurityControl;
import com.echothree.model.control.selector.server.control.SelectorControl;
import com.echothree.model.data.security.server.entity.SecurityRolePartyType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class SecurityRolePartyTypeTransferCache
        extends BaseSecurityTransferCache<SecurityRolePartyType, SecurityRolePartyTypeTransfer> {
    
    PartyControl partyControl = Session.getModelController(PartyControl.class);
    SecurityControl securityControl = Session.getModelController(SecurityControl.class);
    SelectorControl selectorControl = Session.getModelController(SelectorControl.class);
        
    /** Creates a new instance of SecurityRolePartyTypeTransferCache */
    protected SecurityRolePartyTypeTransferCache() {
        super();
    }
    
    public SecurityRolePartyTypeTransfer getSecurityRolePartyTypeTransfer(UserVisit userVisit, SecurityRolePartyType securityRolePartyType) {
        var securityRolePartyTypeTransfer = get(securityRolePartyType);
        
        if(securityRolePartyTypeTransfer == null) {
            var securityRoleTransfer = securityControl.getSecurityRoleTransfer(userVisit, securityRolePartyType.getSecurityRole());
            var partyTypeTransfer = partyControl.getPartyTypeTransfer(userVisit, securityRolePartyType.getPartyType());
            var partySelector = securityRolePartyType.getPartySelector();
            var partySelectorTransfer = partySelector == null? null: selectorControl.getSelectorTransfer(userVisit, partySelector);
            
            securityRolePartyTypeTransfer = new SecurityRolePartyTypeTransfer(securityRoleTransfer, partyTypeTransfer, partySelectorTransfer);
            put(userVisit, securityRolePartyType, securityRolePartyTypeTransfer);
        }
        
        return securityRolePartyTypeTransfer;
    }
    
}
