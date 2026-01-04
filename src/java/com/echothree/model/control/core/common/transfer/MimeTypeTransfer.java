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
import com.echothree.util.common.transfer.ListWrapper;

public class MimeTypeTransfer
        extends BaseTransfer {

    private String mimeTypeName;
    private EntityAttributeTypeTransfer entityAttributeType;
    private Boolean isDefault;
    private Integer sortOrder;
    private String description;

    private ListWrapper<MimeTypeFileExtensionTransfer> mimeTypeFileExtensions;
    
    /** Creates a new instance of MimeTypeTransfer */
    public MimeTypeTransfer(String mimeTypeName, EntityAttributeTypeTransfer entityAttributeType, Boolean isDefault, Integer sortOrder,
            String description) {
        this.mimeTypeName = mimeTypeName;
        this.entityAttributeType = entityAttributeType;
        this.isDefault = isDefault;
        this.sortOrder = sortOrder;
        this.description = description;
    }

    /**
     * Returns the mimeTypeName.
     * @return the mimeTypeName
     */
    public String getMimeTypeName() {
        return mimeTypeName;
    }

    /**
     * Sets the mimeTypeName.
     * @param mimeTypeName the mimeTypeName to set
     */
    public void setMimeTypeName(String mimeTypeName) {
        this.mimeTypeName = mimeTypeName;
    }

    /**
     * Returns the entityAttributeType.
     * @return the entityAttributeType
     */
    public EntityAttributeTypeTransfer getEntityAttributeType() {
        return entityAttributeType;
    }

    /**
     * Sets the entityAttributeType.
     * @param entityAttributeType the entityAttributeType to set
     */
    public void setEntityAttributeType(EntityAttributeTypeTransfer entityAttributeType) {
        this.entityAttributeType = entityAttributeType;
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
     * Returns the mimeTypeFileExtensions.
     * @return the mimeTypeFileExtensions
     */
    public ListWrapper<MimeTypeFileExtensionTransfer> getMimeTypeFileExtensions() {
        return mimeTypeFileExtensions;
    }

    /**
     * Sets the mimeTypeFileExtensions.
     * @param mimeTypeFileExtensions the mimeTypeFileExtensions to set
     */
    public void setMimeTypeFileExtensions(ListWrapper<MimeTypeFileExtensionTransfer> mimeTypeFileExtensions) {
        this.mimeTypeFileExtensions = mimeTypeFileExtensions;
    }

}
