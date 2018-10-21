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

package com.echothree.model.control.search.remote.transfer;

import com.echothree.model.control.security.remote.transfer.SecurityRoleTransfer;
import com.echothree.util.remote.transfer.BaseTransfer;

public class SecurityRoleResultTransfer
        extends BaseTransfer {
    
    private String securityRoleGroupName;
    private String securityRoleName;
    private SecurityRoleTransfer securityRole;
    
    /** Creates a new instance of SecurityRoleResultTransfer */
    public SecurityRoleResultTransfer(String securityRoleGroupName, String securityRoleName, SecurityRoleTransfer securityRole) {
        this.securityRoleGroupName = securityRoleGroupName;
        this.securityRoleName = securityRoleName;
        this.securityRole = securityRole;
    }

    /**
     * @return the securityRoleGroupName
     */
    public String getSecurityRoleGroupName() {
        return securityRoleGroupName;
    }

    /**
     * @param securityRoleGroupName the securityRoleGroupName to set
     */
    public void setSecurityRoleGroupName(String securityRoleGroupName) {
        this.securityRoleGroupName = securityRoleGroupName;
    }

    /**
     * @return the securityRoleName
     */
    public String getSecurityRoleName() {
        return securityRoleName;
    }

    /**
     * @param securityRoleName the securityRoleName to set
     */
    public void setSecurityRoleName(String securityRoleName) {
        this.securityRoleName = securityRoleName;
    }

    /**
     * @return the securityRole
     */
    public SecurityRoleTransfer getSecurityRole() {
        return securityRole;
    }

    /**
     * @param securityRole the securityRole to set
     */
    public void setSecurityRole(SecurityRoleTransfer securityRole) {
        this.securityRole = securityRole;
    }

 }
