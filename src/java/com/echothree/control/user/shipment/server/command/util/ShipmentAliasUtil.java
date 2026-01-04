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

package com.echothree.control.user.shipment.server.command.util;

import com.echothree.control.user.shipment.common.spec.ShipmentTypeSpec;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.shipment.common.ShipmentTypes;
import java.util.HashMap;
import java.util.Map;

public class ShipmentAliasUtil {

    private ShipmentAliasUtil() {
        super();
    }

    private static class ShipmentAliasUtilHolder {
        static ShipmentAliasUtil instance = new ShipmentAliasUtil();
    }

    public static ShipmentAliasUtil getInstance() {
        return ShipmentAliasUtilHolder.instance;
    }

    private final static Map<String, String> securityRoleGroupNameByShipmentTypeName;

    static {
        securityRoleGroupNameByShipmentTypeName = new HashMap<>();
        securityRoleGroupNameByShipmentTypeName.put(ShipmentTypes.CUSTOMER_SHIPMENT.name(), SecurityRoleGroups.CustomerShipmentAlias.name());
    }

    public String getSecurityRoleGroupNameByShipmentTypeName(String shipmentTypeName) {
        String securityRoleGroupName = null;

        if(shipmentTypeName != null) {
            securityRoleGroupName = securityRoleGroupNameByShipmentTypeName.get(shipmentTypeName);
        }

        return securityRoleGroupName;
    }

    // Caution: no validation of the form has been done at this point.
    public String getSecurityRoleGroupNameByShipmentTypeSpec(ShipmentTypeSpec spec) {
        String securityRoleGroupName = null;

        if(spec != null) {
            var shipmentTypeName = spec.getShipmentTypeName();
            
            if(shipmentTypeName != null) {
                securityRoleGroupName = getSecurityRoleGroupNameByShipmentTypeName(shipmentTypeName);
            }
        }

        return securityRoleGroupName;
    }

}
