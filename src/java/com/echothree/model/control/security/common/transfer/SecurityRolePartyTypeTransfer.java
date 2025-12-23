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

package com.echothree.model.control.security.common.transfer;

import com.echothree.model.control.party.common.transfer.PartyTypeTransfer;
import com.echothree.model.control.selector.common.transfer.SelectorTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class SecurityRolePartyTypeTransfer
        extends BaseTransfer {
    
    private SecurityRoleTransfer securityRole;
    private PartyTypeTransfer partyType;
    private SelectorTransfer partySelector;
    
    /** Creates a new instance of SecurityRolePartyTypeTransfer */
    public SecurityRolePartyTypeTransfer(SecurityRoleTransfer securityRole, PartyTypeTransfer partyType, SelectorTransfer partySelector) {
        this.securityRole = securityRole;
        this.partyType = partyType;
        this.partySelector = partySelector;
    }

    public SecurityRoleTransfer getSecurityRole() {
        return securityRole;
    }

    public void setSecurityRole(SecurityRoleTransfer securityRole) {
        this.securityRole = securityRole;
    }

    public PartyTypeTransfer getPartyType() {
        return partyType;
    }

    public void setPartyType(PartyTypeTransfer partyType) {
        this.partyType = partyType;
    }

    public SelectorTransfer getPartySelector() {
        return partySelector;
    }

    public void setPartySelector(SelectorTransfer partySelector) {
        this.partySelector = partySelector;
    }
    
}
