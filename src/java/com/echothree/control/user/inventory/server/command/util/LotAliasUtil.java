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

package com.echothree.control.user.inventory.server.command.util;

import com.echothree.control.user.inventory.remote.spec.LotTypeSpec;
import java.util.HashMap;
import java.util.Map;

public class LotAliasUtil {

    private LotAliasUtil() {
        super();
    }

    private static class LotAliasUtilHolder {
        static LotAliasUtil instance = new LotAliasUtil();
    }

    public static LotAliasUtil getInstance() {
        return LotAliasUtilHolder.instance;
    }

    private final static Map<String, String> securityRoleGroupNameByLotTypeName;

    static {
        securityRoleGroupNameByLotTypeName = new HashMap<>();
        // TODO: securityRoleGroupNameByLotTypeName.put(LotConstants.LotType_CUSTOMER_SHIPMENT, SecurityRoleGroups.CustomerLotAlias.name());
    }

    public String getSecurityRoleGroupNameByLotTypeName(String lotTypeName) {
        String securityRoleGroupName = null;

        if(lotTypeName != null) {
            securityRoleGroupName = securityRoleGroupNameByLotTypeName.get(lotTypeName);
        }

        return securityRoleGroupName;
    }

    // Caution: no validation of the form has been done at this point.
    public String getSecurityRoleGroupNameByLotTypeSpec(LotTypeSpec spec) {
        String securityRoleGroupName = null;

        if(spec != null) {
            String lotTypeName = spec.getLotTypeName();
            
            if(lotTypeName != null) {
                securityRoleGroupName = getSecurityRoleGroupNameByLotTypeName(lotTypeName);
            }
        }

        return securityRoleGroupName;
    }

}
