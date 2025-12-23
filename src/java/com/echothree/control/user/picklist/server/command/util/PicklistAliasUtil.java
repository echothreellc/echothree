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

package com.echothree.control.user.picklist.server.command.util;

import com.echothree.control.user.picklist.common.spec.PicklistTypeSpec;
import java.util.HashMap;
import java.util.Map;

public class PicklistAliasUtil {

    private PicklistAliasUtil() {
        super();
    }

    private static class PicklistAliasUtilHolder {
        static PicklistAliasUtil instance = new PicklistAliasUtil();
    }

    public static PicklistAliasUtil getInstance() {
        return PicklistAliasUtilHolder.instance;
    }

    private final static Map<String, String> securityRoleGroupNameByPicklistTypeName;

    static {
        securityRoleGroupNameByPicklistTypeName = new HashMap<>();
//        securityRoleGroupNameByPicklistTypeName.put(PicklistConstants.PicklistType_SALES_ORDER, SecurityRoleGroups.SalesOrderPicklistAlias.name());
    }

    public String getSecurityRoleGroupNameByPicklistTypeName(String picklistTypeName) {
        String securityRoleGroupName = null;

        if(picklistTypeName != null) {
            securityRoleGroupName = securityRoleGroupNameByPicklistTypeName.get(picklistTypeName);
        }

        return securityRoleGroupName;
    }

    // Caution: no validation of the form has been done at this point.
    public String getSecurityRoleGroupNameByPicklistTypeSpec(PicklistTypeSpec spec) {
        String securityRoleGroupName = null;

        if(spec != null) {
            var picklistTypeName = spec.getPicklistTypeName();
            
            if(picklistTypeName != null) {
                securityRoleGroupName = getSecurityRoleGroupNameByPicklistTypeName(picklistTypeName);
            }
        }

        return securityRoleGroupName;
    }

}
