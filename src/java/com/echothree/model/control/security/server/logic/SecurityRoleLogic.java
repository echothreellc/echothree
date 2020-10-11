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

package com.echothree.model.control.security.server.logic;

import com.echothree.model.control.security.common.exception.UnknownSecurityRoleNameException;
import com.echothree.model.control.security.server.control.SecurityControl;
import com.echothree.model.control.selector.server.SelectorControl;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.security.server.entity.SecurityRole;
import com.echothree.model.data.security.server.entity.SecurityRoleGroup;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;

public class SecurityRoleLogic
        extends BaseLogic {

    private SecurityRoleLogic() {
        super();
    }

    private static class SecurityRoleLogicHolder {
        static SecurityRoleLogic instance = new SecurityRoleLogic();
    }

    public static SecurityRoleLogic getInstance() {
        return SecurityRoleLogicHolder.instance;
    }

    public SecurityRole getSecurityRoleByName(final ExecutionErrorAccumulator eea, final SecurityRoleGroup securityRoleGroup, final String securityRoleName) {
        var securityControl = (SecurityControl)Session.getModelController(SecurityControl.class);
        var securityRole = securityControl.getSecurityRoleByName(securityRoleGroup, securityRoleName);

        if(securityRole == null) {
            handleExecutionError(UnknownSecurityRoleNameException.class, eea, ExecutionErrors.UnknownSecurityRoleName.name(),
                    securityRoleGroup.getLastDetail().getSecurityRoleGroupName(), securityRoleName);
        }

        return securityRole;
    }

    public SecurityRole getSecurityRoleByName(final ExecutionErrorAccumulator eea, final String securityRoleGroupName, final String securityRoleName) {
        var securityRoleGroup = SecurityRoleGroupLogic.getInstance().getSecurityRoleGroupByName(eea, securityRoleGroupName);
        SecurityRole securityRole = null;
        
        if(!hasExecutionErrors(eea)) {
            securityRole = getSecurityRoleByName(eea, securityRoleGroup, securityRoleName);
        }
        
        return securityRole;
    }

    public boolean hasSecurityRole(final Party party, final SecurityRole securityRole) {
        boolean result = false;

        if(party != null) {
            var securityControl = (SecurityControl)Session.getModelController(SecurityControl.class);
            var partySecurityRole = securityControl.getPartySecurityRole(party, securityRole);

            if(partySecurityRole != null) {
                var securityRolePartyType = securityControl.getSecurityRolePartyType(securityRole, party.getLastDetail().getPartyType());

                if(securityRolePartyType == null) {
                    result = true;
                } else {
                    var partySelector = securityRolePartyType.getPartySelector();

                    if(partySelector != null) {
                        var selectorControl = (SelectorControl)Session.getModelController(SelectorControl.class);

                        if(selectorControl.getSelectorParty(partySelector, party) != null) {
                            result = true;
                        }
                    }
                }
            }
        }
        
        return result;
    }

    public boolean hasSecurityRoleUsingNames(final ExecutionErrorAccumulator eea, final Party party, final String securityRoleGroupName,
            final String securityRoleName) {
        var securityRole = getSecurityRoleByName(eea, securityRoleGroupName, securityRoleName);
        var result = false;
        
        if(!hasExecutionErrors(eea)) {
            result = hasSecurityRole(party, securityRole);
        }
        
        return result;
    }
    
}