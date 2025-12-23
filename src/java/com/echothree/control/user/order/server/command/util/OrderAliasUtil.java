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

package com.echothree.control.user.order.server.command.util;

import com.echothree.control.user.order.common.spec.OrderTypeSpec;
import com.echothree.model.control.order.common.OrderTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import java.util.HashMap;
import java.util.Map;

public class OrderAliasUtil {

    private OrderAliasUtil() {
        super();
    }

    private static class OrderAliasUtilHolder {
        static OrderAliasUtil instance = new OrderAliasUtil();
    }

    public static OrderAliasUtil getInstance() {
        return OrderAliasUtilHolder.instance;
    }

    private final static Map<String, String> securityRoleGroupNameByOrderTypeName;

    static {
        securityRoleGroupNameByOrderTypeName = new HashMap<>();
        securityRoleGroupNameByOrderTypeName.put(OrderTypes.SALES_ORDER.name(), SecurityRoleGroups.SalesOrderAlias.name());
    }

    public String getSecurityRoleGroupNameByOrderTypeName(String orderTypeName) {
        String securityRoleGroupName = null;

        if(orderTypeName != null) {
            securityRoleGroupName = securityRoleGroupNameByOrderTypeName.get(orderTypeName);
        }

        return securityRoleGroupName;
    }

    // Caution: no validation of the form has been done at this point.
    public String getSecurityRoleGroupNameByOrderTypeSpec(OrderTypeSpec spec) {
        String securityRoleGroupName = null;

        if(spec != null) {
            var orderTypeName = spec.getOrderTypeName();
            
            if(orderTypeName != null) {
                securityRoleGroupName = getSecurityRoleGroupNameByOrderTypeName(orderTypeName);
            }
        }

        return securityRoleGroupName;
    }

}
