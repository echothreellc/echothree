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

import com.echothree.model.control.party.common.transfer.RoleTypeTransfer;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.party.server.entity.RoleType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class RoleTypeTransferCache
        extends BasePartyTransferCache<RoleType, RoleTypeTransfer> {

    PartyControl partyControl = Session.getModelController(PartyControl.class);

    /** Creates a new instance of RoleTypeTransferCache */
    protected RoleTypeTransferCache() {
        super();
    }

    @Override
    public RoleTypeTransfer getTransfer(UserVisit userVisit, RoleType roleType) {
        var roleTypeTransfer = get(roleType);
        
        if(roleTypeTransfer == null) {
            var roleTypeName = roleType.getRoleTypeName();
            var parentRoleType = roleType.getParentRoleType();
            var parentRoleTypeTransfer = parentRoleType == null ? null : getTransfer(userVisit, parentRoleType);
            var description = partyControl.getBestRoleTypeDescription(roleType, getLanguage(userVisit));
            
            roleTypeTransfer = new RoleTypeTransfer(roleTypeName, parentRoleTypeTransfer, description);
            put(userVisit, roleType, roleTypeTransfer);
        }
        
        return roleTypeTransfer;
    }
    
}
