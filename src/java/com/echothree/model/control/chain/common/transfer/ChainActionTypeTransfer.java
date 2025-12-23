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

package com.echothree.model.control.chain.common.transfer;

import com.echothree.util.common.transfer.BaseTransfer;

public class ChainActionTypeTransfer
        extends BaseTransfer {
    
    private String chainActionTypeName;
    private Boolean allowMultiple;
    private Boolean isDefault;
    private Integer sortOrder;
    private String description;
    
    /** Creates a new instance of ChainActionTypeTransfer */
    public ChainActionTypeTransfer(String chainActionTypeName, Boolean allowMultiple, Boolean isDefault, Integer sortOrder,
            String description) {
        this.chainActionTypeName = chainActionTypeName;
        this.allowMultiple = allowMultiple;
        this.isDefault = isDefault;
        this.sortOrder = sortOrder;
        this.description = description;
    }

    /**
     * Returns the chainActionTypeName.
     * @return the chainActionTypeName
     */
    public String getChainActionTypeName() {
        return chainActionTypeName;
    }

    /**
     * Sets the chainActionTypeName.
     * @param chainActionTypeName the chainActionTypeName to set
     */
    public void setChainActionTypeName(String chainActionTypeName) {
        this.chainActionTypeName = chainActionTypeName;
    }

    /**
     * Returns the allowMultiple.
     * @return the allowMultiple
     */
    public Boolean getAllowMultiple() {
        return allowMultiple;
    }

    /**
     * Sets the allowMultiple.
     * @param allowMultiple the allowMultiple to set
     */
    public void setAllowMultiple(Boolean allowMultiple) {
        this.allowMultiple = allowMultiple;
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
    
}
