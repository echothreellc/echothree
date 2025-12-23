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

package com.echothree.control.user.batch.server.command.util;

import com.echothree.control.user.batch.common.spec.BatchTypeSpec;
import com.echothree.model.control.batch.common.BatchConstants;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import java.util.HashMap;
import java.util.Map;

public class BatchAliasUtil {

    private BatchAliasUtil() {
        super();
    }

    private static class BatchAliasUtilHolder {
        static BatchAliasUtil instance = new BatchAliasUtil();
    }

    public static BatchAliasUtil getInstance() {
        return BatchAliasUtilHolder.instance;
    }

    private final static Map<String, String> securityRoleGroupNameByBatchTypeName;

    static {
        securityRoleGroupNameByBatchTypeName = new HashMap<>();
        securityRoleGroupNameByBatchTypeName.put(BatchConstants.BatchType_SALES_ORDER, SecurityRoleGroups.SalesOrderBatchAlias.name());
    }

    public String getSecurityRoleGroupNameByBatchTypeName(String batchTypeName) {
        String securityRoleGroupName = null;

        if(batchTypeName != null) {
            securityRoleGroupName = securityRoleGroupNameByBatchTypeName.get(batchTypeName);
        }

        return securityRoleGroupName;
    }

    // Caution: no validation of the form has been done at this point.
    public String getSecurityRoleGroupNameByBatchTypeSpec(BatchTypeSpec spec) {
        String securityRoleGroupName = null;

        if(spec != null) {
            var batchTypeName = spec.getBatchTypeName();
            
            if(batchTypeName != null) {
                securityRoleGroupName = getSecurityRoleGroupNameByBatchTypeName(batchTypeName);
            }
        }

        return securityRoleGroupName;
    }

}
