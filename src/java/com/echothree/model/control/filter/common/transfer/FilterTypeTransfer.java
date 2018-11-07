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

package com.echothree.model.control.filter.common.transfer;

import com.echothree.util.common.transfer.BaseTransfer;

public class FilterTypeTransfer
        extends BaseTransfer {
    
    private FilterKindTransfer filterKind;
    private String filterTypeName;
    private Boolean isDefault;
    private Integer sortOrder;
    private String description;
    
    /** Creates a new instance of FilterTypeTransfer */
    public FilterTypeTransfer(FilterKindTransfer filterKind, String filterTypeName, Boolean isDefault, Integer sortOrder, String description) {
        this.filterKind = filterKind;
        this.filterTypeName = filterTypeName;
        this.isDefault = isDefault;
        this.sortOrder = sortOrder;
        this.description = description;
    }

    /**
     * @return the filterKind
     */
    public FilterKindTransfer getFilterKind() {
        return filterKind;
    }

    /**
     * @param filterKind the filterKind to set
     */
    public void setFilterKind(FilterKindTransfer filterKind) {
        this.filterKind = filterKind;
    }

    /**
     * @return the filterTypeName
     */
    public String getFilterTypeName() {
        return filterTypeName;
    }

    /**
     * @param filterTypeName the filterTypeName to set
     */
    public void setFilterTypeName(String filterTypeName) {
        this.filterTypeName = filterTypeName;
    }

    /**
     * @return the isDefault
     */
    public Boolean getIsDefault() {
        return isDefault;
    }

    /**
     * @param isDefault the isDefault to set
     */
    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }

    /**
     * @return the sortOrder
     */
    public Integer getSortOrder() {
        return sortOrder;
    }

    /**
     * @param sortOrder the sortOrder to set
     */
    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }
    
}
