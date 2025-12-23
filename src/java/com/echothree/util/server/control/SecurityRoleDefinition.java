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

package com.echothree.util.server.control;

public class SecurityRoleDefinition {
    
    private String securityRoleGroupName;
    private String securityRoleName;

    /** Creates a new instance of SecurityRoleDefinition */
    public SecurityRoleDefinition(String securityRoleGroupName, String securityRoleName) {
        this.securityRoleGroupName = securityRoleGroupName;
        this.securityRoleName = securityRoleName;
    }
    
    public String getSecurityRoleGroupName() {
        return securityRoleGroupName;
    }

    public void setSecurityRoleGroupName(String securityRoleGroupName) {
        this.securityRoleGroupName = securityRoleGroupName;
    }

    public String getSecurityRoleName() {
        return securityRoleName;
    }

    public void setSecurityRoleName(String securityRoleName) {
        this.securityRoleName = securityRoleName;
    }

}
