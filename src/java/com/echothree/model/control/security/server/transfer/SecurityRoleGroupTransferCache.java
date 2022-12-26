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

package com.echothree.model.control.security.server.transfer;

import com.echothree.model.control.security.common.SecurityOptions;
import com.echothree.model.control.security.common.transfer.SecurityRoleGroupTransfer;
import com.echothree.model.control.security.common.transfer.SecurityRoleTransfer;
import com.echothree.model.control.security.server.control.SecurityControl;
import com.echothree.model.data.security.server.entity.SecurityRoleGroup;
import com.echothree.model.data.security.server.entity.SecurityRoleGroupDetail;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.transfer.MapWrapper;
import java.util.List;
import java.util.Set;

public class SecurityRoleGroupTransferCache
        extends BaseSecurityTransferCache<SecurityRoleGroup, SecurityRoleGroupTransfer> {
    
    boolean includeSecurityRolesCount;
    boolean includeSecurityRoles;

    /** Creates a new instance of SecurityRoleGroupTransferCache */
    public SecurityRoleGroupTransferCache(UserVisit userVisit, SecurityControl securityControl) {
        super(userVisit, securityControl);

        var options = session.getOptions();
        if(options != null) {
            includeSecurityRolesCount = options.contains(SecurityOptions.SecurityRoleGroupIncludeSecurityRolesCount);
            includeSecurityRoles = options.contains(SecurityOptions.SecurityRoleGroupIncludeSecurityRoles);
        }
        
        setIncludeEntityInstance(true);
    }
    
    public SecurityRoleGroupTransfer getSecurityRoleGroupTransfer(SecurityRoleGroup securityRoleGroup) {
        SecurityRoleGroupTransfer securityRoleGroupTransfer = get(securityRoleGroup);

        if(securityRoleGroupTransfer == null) {
            SecurityRoleGroupDetail securityRoleGroupDetail = securityRoleGroup.getLastDetail();
            String securityRoleGroupName = securityRoleGroupDetail.getSecurityRoleGroupName();
            SecurityRoleGroup parentSecurityRoleGroup = securityRoleGroupDetail.getParentSecurityRoleGroup();
            SecurityRoleGroupTransfer parentSecurityRoleGroupTransfer = parentSecurityRoleGroup == null ? null : getSecurityRoleGroupTransfer(parentSecurityRoleGroup);
            Boolean isDefault = securityRoleGroupDetail.getIsDefault();
            Integer sortOrder = securityRoleGroupDetail.getSortOrder();
            String description = securityControl.getBestSecurityRoleGroupDescription(securityRoleGroup, getLanguage());

            securityRoleGroupTransfer = new SecurityRoleGroupTransfer(securityRoleGroupName, parentSecurityRoleGroupTransfer, isDefault, sortOrder, description);
            put(securityRoleGroup, securityRoleGroupTransfer);

            if(includeSecurityRolesCount) {
                securityRoleGroupTransfer.setSecurityRoleCount(securityControl.countSecurityRolesBySecurityRoleGroup(securityRoleGroup));
            }

            if(includeSecurityRoles) {
                List<SecurityRoleTransfer> securityRoleTransfers = securityControl.getSecurityRoleTransfersBySecurityRoleGroup(userVisit, securityRoleGroup);
                MapWrapper<SecurityRoleTransfer> securityRoles = new MapWrapper<>(securityRoleTransfers.size());

                securityRoleTransfers.forEach((securityRoleTransfer) -> {
                    securityRoles.put(securityRoleTransfer.getSecurityRoleName(), securityRoleTransfer);
                });

                securityRoleGroupTransfer.setSecurityRoles(securityRoles);
            }
        }
        
        return securityRoleGroupTransfer;
    }
    
}
