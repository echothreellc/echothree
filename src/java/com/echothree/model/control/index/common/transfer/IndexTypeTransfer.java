// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

package com.echothree.model.control.index.common.transfer;

import com.echothree.model.control.core.common.transfer.EntityTypeTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class IndexTypeTransfer
        extends BaseTransfer {
    
    private String indexTypeName;
    private EntityTypeTransfer entityType;
    private Boolean isDefault;
    private Integer sortOrder;
    private String description;
    
    /** Creates a new instance of IndexTypeTransfer */
    public IndexTypeTransfer(String indexTypeName, EntityTypeTransfer entityType, Boolean isDefault, Integer sortOrder, String description) {
        this.indexTypeName = indexTypeName;
        this.entityType = entityType;
        this.isDefault = isDefault;
        this.sortOrder = sortOrder;
        this.description = description;
    }

    /**
     * @return the indexTypeName
     */
    public String getIndexTypeName() {
        return indexTypeName;
    }

    /**
     * @param indexTypeName the indexTypeName to set
     */
    public void setIndexTypeName(String indexTypeName) {
        this.indexTypeName = indexTypeName;
    }

    /**
     * @return the entityType
     */
    public EntityTypeTransfer getEntityType() {
        return entityType;
    }

    /**
     * @param entityType the entityType to set
     */
    public void setEntityType(EntityTypeTransfer entityType) {
        this.entityType = entityType;
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
