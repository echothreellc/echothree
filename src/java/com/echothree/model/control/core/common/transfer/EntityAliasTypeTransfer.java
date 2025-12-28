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

package com.echothree.model.control.core.common.transfer;

import com.echothree.util.common.transfer.BaseTransfer;

public class EntityAliasTypeTransfer
        extends BaseTransfer {
    
    private EntityTypeTransfer entityType;
    private String entityAliasTypeName;
    private String validationPattern;
    private boolean isDefault;
    private Integer sortOrder;
    private String description;
    
    /** Creates a new instance of EntityAliasTypeTransfer */
    public EntityAliasTypeTransfer(EntityTypeTransfer entityType, String entityAliasTypeName,
            String validationPattern, boolean isDefault, Integer sortOrder, String description) {
        this.entityType = entityType;
        this.entityAliasTypeName = entityAliasTypeName;
        this.validationPattern = validationPattern;
        this.isDefault = isDefault;
        this.sortOrder = sortOrder;
        this.description = description;
    }

    public EntityTypeTransfer getEntityType() {
        return entityType;
    }

    public void setEntityType(final EntityTypeTransfer entityType) {
        this.entityType = entityType;
    }

    public String getEntityAliasTypeName() {
        return entityAliasTypeName;
    }

    public void setEntityAliasTypeName(final String entityAliasTypeName) {
        this.entityAliasTypeName = entityAliasTypeName;
    }

    public String getValidationPattern() {
        return validationPattern;
    }

    public void setValidationPattern(final String validationPattern) {
        this.validationPattern = validationPattern;
    }

    public boolean getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(final boolean isDefault) {
        this.isDefault = isDefault;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(final Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

}
