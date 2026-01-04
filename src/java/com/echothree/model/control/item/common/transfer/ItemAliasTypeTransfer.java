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

package com.echothree.model.control.item.common.transfer;

import com.echothree.util.common.transfer.BaseTransfer;

public class ItemAliasTypeTransfer
        extends BaseTransfer {
    
    private String itemAliasTypeName;
    private String validationPattern;
    private ItemAliasChecksumTypeTransfer itemAliasChecksumType;
    private Boolean allowMultiple;
    private Boolean isDefault;
    private Integer sortOrder;
    private String description;
    
    /** Creates a new instance of ItemAliasTypeTransfer */
    public ItemAliasTypeTransfer(String itemAliasTypeName, String validationPattern, ItemAliasChecksumTypeTransfer itemAliasChecksumType, Boolean allowMultiple,
            Boolean isDefault, Integer sortOrder, String description) {
        this.itemAliasTypeName = itemAliasTypeName;
        this.validationPattern = validationPattern;
        this.itemAliasChecksumType = itemAliasChecksumType;
        this.allowMultiple = allowMultiple;
        this.isDefault = isDefault;
        this.sortOrder = sortOrder;
        this.description = description;
    }
    
    public String getItemAliasTypeName() {
        return itemAliasTypeName;
    }
    
    public void setItemAliasTypeName(String itemAliasTypeName) {
        this.itemAliasTypeName = itemAliasTypeName;
    }
    
    public String getValidationPattern() {
        return validationPattern;
    }
    
    public void setValidationPattern(String validationPattern) {
        this.validationPattern = validationPattern;
    }
    
    /**
     * Returns the itemAliasChecksumType.
     * @return the itemAliasChecksumType
     */
    public ItemAliasChecksumTypeTransfer getItemAliasChecksumType() {
        return itemAliasChecksumType;
    }

    /**
     * Sets the itemAliasChecksumType.
     * @param itemAliasChecksumType the itemAliasChecksumType to set
     */
    public void setItemAliasChecksumType(ItemAliasChecksumTypeTransfer itemAliasChecksumType) {
        this.itemAliasChecksumType = itemAliasChecksumType;
    }

    public Boolean getAllowMultiple() {
        return allowMultiple;
    }
    
    public void setAllowMultiple(Boolean allowMultiple) {
        this.allowMultiple = allowMultiple;
    }
    
    public Boolean getIsDefault() {
        return isDefault;
    }
    
    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }
    
    public Integer getSortOrder() {
        return sortOrder;
    }
    
    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

}
