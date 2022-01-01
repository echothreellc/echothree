// --------------------------------------------------------------------------------
// Copyright 2002-2022 Echo Three, LLC
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

import com.echothree.model.control.security.common.transfer.SecurityRoleGroupTransfer;
import com.echothree.model.control.security.common.transfer.SecurityRoleTransfer;
import com.echothree.model.control.security.server.control.SecurityControl;
import com.echothree.model.data.security.server.entity.SecurityRole;
import com.echothree.model.data.security.server.entity.SecurityRoleDetail;
import com.echothree.model.data.user.server.entity.UserVisit;

public class SecurityRoleTransferCache
        extends BaseSecurityTransferCache<SecurityRole, SecurityRoleTransfer> {
    
    /** Creates a new instance of SecurityRoleTransferCache */
    public SecurityRoleTransferCache(UserVisit userVisit, SecurityControl securityControl) {
        super(userVisit, securityControl);
        
        setIncludeEntityInstance(true);
    }
    
    public SecurityRoleTransfer getSecurityRoleTransfer(SecurityRole securityRole) {
        SecurityRoleTransfer securityRoleTransfer = get(securityRole);
        
        if(securityRoleTransfer == null) {
            SecurityRoleDetail securityRoleDetail = securityRole.getLastDetail();
            String securityRoleName = securityRoleDetail.getSecurityRoleName();
            SecurityRoleGroupTransfer securityRoleGroupTransfer = securityControl.getSecurityRoleGroupTransfer(userVisit, securityRoleDetail.getSecurityRoleGroup());
            Boolean isDefault = securityRoleDetail.getIsDefault();
            Integer sortOrder = securityRoleDetail.getSortOrder();
            String description = securityControl.getBestSecurityRoleDescription(securityRole, getLanguage());
            
            securityRoleTransfer = new SecurityRoleTransfer(securityRoleGroupTransfer, securityRoleName, isDefault, sortOrder, description);
            put(securityRole, securityRoleTransfer);
        }
        return securityRoleTransfer;
    }
    
}
