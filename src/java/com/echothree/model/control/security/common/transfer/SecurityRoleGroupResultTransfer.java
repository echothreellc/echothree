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

package com.echothree.model.control.security.common.transfer;

import com.echothree.model.control.security.common.transfer.SecurityRoleGroupTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class SecurityRoleGroupResultTransfer
        extends BaseTransfer {
    
    private String securityRoleGroupName;
    private SecurityRoleGroupTransfer securityRoleGroup;
    
    /** Creates a new instance of SecurityRoleGroupResultTransfer */
    public SecurityRoleGroupResultTransfer(String securityRoleGroupName, SecurityRoleGroupTransfer securityRoleGroup) {
        this.securityRoleGroupName = securityRoleGroupName;
        this.securityRoleGroup = securityRoleGroup;
    }

    /**
     * Returns the securityRoleGroupName.
     * @return the securityRoleGroupName
     */
    public String getSecurityRoleGroupName() {
        return securityRoleGroupName;
    }

    /**
     * Sets the securityRoleGroupName.
     * @param securityRoleGroupName the securityRoleGroupName to set
     */
    public void setSecurityRoleGroupName(String securityRoleGroupName) {
        this.securityRoleGroupName = securityRoleGroupName;
    }

    /**
     * Returns the securityRoleGroup.
     * @return the securityRoleGroup
     */
    public SecurityRoleGroupTransfer getSecurityRoleGroup() {
        return securityRoleGroup;
    }

    /**
     * Sets the securityRoleGroup.
     * @param securityRoleGroup the securityRoleGroup to set
     */
    public void setSecurityRoleGroup(SecurityRoleGroupTransfer securityRoleGroup) {
        this.securityRoleGroup = securityRoleGroup;
    }

 }
