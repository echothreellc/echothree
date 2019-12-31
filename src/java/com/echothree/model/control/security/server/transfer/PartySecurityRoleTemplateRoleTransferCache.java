// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

import com.echothree.model.control.security.common.transfer.PartySecurityRoleTemplateRoleTransfer;
import com.echothree.model.control.security.common.transfer.PartySecurityRoleTemplateTransfer;
import com.echothree.model.control.security.common.transfer.SecurityRoleTransfer;
import com.echothree.model.control.security.server.SecurityControl;
import com.echothree.model.data.security.server.entity.PartySecurityRoleTemplateRole;
import com.echothree.model.data.user.server.entity.UserVisit;

public class PartySecurityRoleTemplateRoleTransferCache
        extends BaseSecurityTransferCache<PartySecurityRoleTemplateRole, PartySecurityRoleTemplateRoleTransfer> {
    
    /** Creates a new instance of PartySecurityRoleTemplateRoleTransferCache */
    public PartySecurityRoleTemplateRoleTransferCache(UserVisit userVisit, SecurityControl securityControl) {
        super(userVisit, securityControl);
    }
    
    public PartySecurityRoleTemplateRoleTransfer getPartySecurityRoleTemplateRoleTransfer(PartySecurityRoleTemplateRole partySecurityRoleTemplateRole) {
        PartySecurityRoleTemplateRoleTransfer partySecurityRoleTemplateRoleTransfer = get(partySecurityRoleTemplateRole);
        
        if(partySecurityRoleTemplateRoleTransfer == null) {
            PartySecurityRoleTemplateTransferCache partySecurityRoleTemplateTransferCache = securityControl.getSecurityTransferCaches(userVisit).getPartySecurityRoleTemplateTransferCache();
            PartySecurityRoleTemplateTransfer partySecurityRoleTemplateTransfer = partySecurityRoleTemplateTransferCache.getPartySecurityRoleTemplateTransfer(partySecurityRoleTemplateRole.getPartySecurityRoleTemplate());
            SecurityRoleTransferCache securityRoleTransferCache = securityControl.getSecurityTransferCaches(userVisit).getSecurityRoleTransferCache();
            SecurityRoleTransfer securityRoleTransfer = securityRoleTransferCache.getSecurityRoleTransfer(partySecurityRoleTemplateRole.getSecurityRole());
            
            partySecurityRoleTemplateRoleTransfer = new PartySecurityRoleTemplateRoleTransfer(partySecurityRoleTemplateTransfer, securityRoleTransfer);
            put(partySecurityRoleTemplateRole, partySecurityRoleTemplateRoleTransfer);
        }
        
        return partySecurityRoleTemplateRoleTransfer;
    }
    
}
