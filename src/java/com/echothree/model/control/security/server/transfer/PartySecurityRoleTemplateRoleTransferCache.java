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

import com.echothree.model.control.security.common.transfer.PartySecurityRoleTemplateRoleTransfer;
import com.echothree.model.control.security.server.control.SecurityControl;
import com.echothree.model.data.security.server.entity.PartySecurityRoleTemplateRole;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class PartySecurityRoleTemplateRoleTransferCache
        extends BaseSecurityTransferCache<PartySecurityRoleTemplateRole, PartySecurityRoleTemplateRoleTransfer> {

    SecurityControl securityControl = Session.getModelController(SecurityControl.class);

    /** Creates a new instance of PartySecurityRoleTemplateRoleTransferCache */
    protected PartySecurityRoleTemplateRoleTransferCache() {
        super();
    }
    
    public PartySecurityRoleTemplateRoleTransfer getPartySecurityRoleTemplateRoleTransfer(UserVisit userVisit, PartySecurityRoleTemplateRole partySecurityRoleTemplateRole) {
        var partySecurityRoleTemplateRoleTransfer = get(partySecurityRoleTemplateRole);
        
        if(partySecurityRoleTemplateRoleTransfer == null) {
            var partySecurityRoleTemplateTransfer = securityControl.getPartySecurityRoleTemplateTransfer(userVisit, partySecurityRoleTemplateRole.getPartySecurityRoleTemplate());
            var securityRoleTransfer = securityControl.getSecurityRoleTransfer(userVisit, partySecurityRoleTemplateRole.getSecurityRole());
            
            partySecurityRoleTemplateRoleTransfer = new PartySecurityRoleTemplateRoleTransfer(partySecurityRoleTemplateTransfer, securityRoleTransfer);
            put(userVisit, partySecurityRoleTemplateRole, partySecurityRoleTemplateRoleTransfer);
        }
        
        return partySecurityRoleTemplateRoleTransfer;
    }
    
}
