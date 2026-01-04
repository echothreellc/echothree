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

package com.echothree.control.user.party.server.command.util;

import com.echothree.control.user.party.common.spec.PartySpec;
import com.echothree.control.user.party.common.spec.PartyTypeSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
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
        securityRoleGroupNameByPartyTypeName.put(PartyTypes.EMPLOYEE.name(), SecurityRoleGroups.EmployeeAlias.name());
        securityRoleGroupNameByPartyTypeName.put(PartyTypes.VENDOR.name(), SecurityRoleGroups.VendorAlias.name());
        securityRoleGroupNameByPartyTypeName.put(PartyTypes.WAREHOUSE.name(), SecurityRoleGroups.WarehouseAlias.name());
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
            var party = partyControl.getPartyByName(partyName);

            if(party != null) {
                securityRoleGroupName = getSecurityRoleGroupNameByPartyTypeName(party.getLastDetail().getPartyType().getPartyTypeName());
            }
        }

        return securityRoleGroupName;
    }

    public String getSecurityRoleGroupNameByPartySpec(PartySpec spec) {
        String securityRoleGroupName = null;

        if(spec != null) {
            var partyName = spec.getPartyName();

            if(partyName != null) {
                var partyControl = Session.getModelController(PartyControl.class);
                var party = partyControl.getPartyByName(partyName);

                if(party != null) {
                    securityRoleGroupName = getSecurityRoleGroupNameByPartyTypeName(party.getLastDetail().getPartyType().getPartyTypeName());
                }
            }
        }

        return securityRoleGroupName;
    }

    public String getSecurityRoleGroupNameByPartyTypeSpec(PartyTypeSpec spec) {
        String securityRoleGroupName = null;

        if(spec != null) {
            var partyTypeName = spec.getPartyTypeName();

            if(partyTypeName != null) {
                var partyControl = Session.getModelController(PartyControl.class);
                var partyType = partyControl.getPartyTypeByName(partyTypeName);

                if(partyType != null) {
                    securityRoleGroupName = getSecurityRoleGroupNameByPartyTypeName(partyType.getPartyTypeName());
                }
            }
        }

        return securityRoleGroupName;
    }

    public String getSecurityRoleGroupNameBySpecs(PartySpec partySpec, PartyTypeSpec partyTypeSpec) {
        var securityRoleGroupName1 = getSecurityRoleGroupNameByPartySpec(partySpec);
        var securityRoleGroupName2 = getSecurityRoleGroupNameByPartyTypeSpec(partyTypeSpec);
        var securityRoleGroupNameCount = (securityRoleGroupName1 == null ? 0 : 1) + (securityRoleGroupName2 == null ? 0 : 1);

        // You may only specify either the PartyName or the PartyTypeName = never both.
        return securityRoleGroupNameCount == 1 ? (securityRoleGroupName1 == null ? securityRoleGroupName2 : securityRoleGroupName1) : null;
    }

}
