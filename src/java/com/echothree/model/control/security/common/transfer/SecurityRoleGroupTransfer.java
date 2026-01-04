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

import com.echothree.util.common.transfer.BaseTransfer;
import com.echothree.util.common.transfer.MapWrapper;

public class SecurityRoleGroupTransfer
        extends BaseTransfer {
    
    private String securityRoleGroupName;
    private SecurityRoleGroupTransfer parentSecurityRoleGroup;
    private Boolean isDefault;
    private Integer sortOrder;
    private String description;

    private Long securityRoleCount;
    private MapWrapper<SecurityRoleTransfer> securityRoles;
    
    /** Creates a new instance of SecurityRoleGroupTransfer */
    public SecurityRoleGroupTransfer(String securityRoleGroupName, SecurityRoleGroupTransfer parentSecurityRoleGroup,
            Boolean isDefault, Integer sortOrder, String description) {
        this.securityRoleGroupName = securityRoleGroupName;
        this.parentSecurityRoleGroup = parentSecurityRoleGroup;
        this.isDefault = isDefault;
        this.sortOrder = sortOrder;
        this.description = description;
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
     * Returns the parentSecurityRoleGroup.
     * @return the parentSecurityRoleGroup
     */
    public SecurityRoleGroupTransfer getParentSecurityRoleGroup() {
        return parentSecurityRoleGroup;
    }

    /**
     * Sets the parentSecurityRoleGroup.
     * @param parentSecurityRoleGroup the parentSecurityRoleGroup to set
     */
    public void setParentSecurityRoleGroup(SecurityRoleGroupTransfer parentSecurityRoleGroup) {
        this.parentSecurityRoleGroup = parentSecurityRoleGroup;
    }

    /**
     * Returns the isDefault.
     * @return the isDefault
     */
    public Boolean getIsDefault() {
        return isDefault;
    }

    /**
     * Sets the isDefault.
     * @param isDefault the isDefault to set
     */
    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }

    /**
     * Returns the sortOrder.
     * @return the sortOrder
     */
    public Integer getSortOrder() {
        return sortOrder;
    }

    /**
     * Sets the sortOrder.
     * @param sortOrder the sortOrder to set
     */
    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    /**
     * Returns the description.
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description.
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the securityRoleCount.
     * @return the securityRoleCount
     */
    public Long getSecurityRoleCount() {
        return securityRoleCount;
    }

    /**
     * Sets the securityRoleCount.
     * @param securityRoleCount the securityRoleCount to set
     */
    public void setSecurityRoleCount(Long securityRoleCount) {
        this.securityRoleCount = securityRoleCount;
    }

    /**
     * Returns the securityRoles.
     * @return the securityRoles
     */
    public MapWrapper<SecurityRoleTransfer> getSecurityRoles() {
        return securityRoles;
    }

    /**
     * Sets the securityRoles.
     * @param securityRoles the securityRoles to set
     */
    public void setSecurityRoles(MapWrapper<SecurityRoleTransfer> securityRoles) {
        this.securityRoles = securityRoles;
    }
    
}
