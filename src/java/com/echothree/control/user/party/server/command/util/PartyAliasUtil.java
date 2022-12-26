// --------------------------------------------------------------------------------
// Copyright 2002-2023 Echo Three, LLC
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

package com.echothree.control.user.party.server.command.util;

import com.echothree.control.user.party.common.spec.PartySpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.util.server.persistence.Session;
import java.util.HashMap;
import java.util.Map;

public class PartyAliasUtil {

    private PartyAliasUtil() {
        super();
    }

    private static class PartyAliasUtilHolder {
        static PartyAliasUtil instance = new PartyAliasUtil();
    }

    public static PartyAliasUtil getInstance() {
        return PartyAliasUtilHolder.instance;
    }

    private final static Map<String, String> securityRoleGroupNameByPartyTypeName;

    static {
        securityRoleGroupNameByPartyTypeName = new HashMap<>();
        securityRoleGroupNameByPartyTypeName.put(PartyTypes.CUSTOMER.name(), SecurityRoleGroups.CustomerAlias.name());
    }

    public String getSecurityRoleGroupNameByPartyTypeName(String partyTypeName) {
        String securityRoleGroupName = null;

        if(partyTypeName != null) {
            securityRoleGroupName = securityRoleGroupNameByPartyTypeName.get(partyTypeName);
        }

        return securityRoleGroupName;
    }

    // Caution: no validation of the form has been done at this point.
    public String getSecurityRoleGroupNameByPartyName(String partyName) {
        String securityRoleGroupName = null;

        if(partyName != null) {
            var partyControl = Session.getModelController(PartyControl.class);
            Party party = partyControl.getPartyByName(partyName);

            if(party != null) {
                securityRoleGroupName = getSecurityRoleGroupNameByPartyTypeName(party.getLastDetail().getPartyType().getPartyTypeName());
            }
        }

        return securityRoleGroupName;
    }

    public String getSecurityRoleGroupNameByPartySpec(PartySpec spec) {
        String securityRoleGroupName = null;

        if(spec != null) {
            String partyName = spec.getPartyName();
            
            if(partyName != null) {
                var partyControl = Session.getModelController(PartyControl.class);
                Party party = partyControl.getPartyByName(partyName);

                if(party != null) {
                    securityRoleGroupName = getSecurityRoleGroupNameByPartyTypeName(party.getLastDetail().getPartyType().getPartyTypeName());
                }
            }
        }

        return securityRoleGroupName;
    }

}
