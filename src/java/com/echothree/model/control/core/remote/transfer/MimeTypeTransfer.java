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

package com.echothree.model.control.core.remote.transfer;

import com.echothree.util.remote.transfer.BaseTransfer;
import com.echothree.util.remote.transfer.ListWrapper;

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
     * @return the mimeTypeName
     */
    public String getMimeTypeName() {
        return mimeTypeName;
    }

    /**
     * @param mimeTypeName the mimeTypeName to set
     */
    public void setMimeTypeName(String mimeTypeName) {
        this.mimeTypeName = mimeTypeName;
    }

    /**
     * @return the entityAttributeType
     */
    public EntityAttributeTypeTransfer getEntityAttributeType() {
        return entityAttributeType;
    }

    /**
     * @param entityAttributeType the entityAttributeType to set
     */
    public void setEntityAttributeType(EntityAttributeTypeTransfer entityAttributeType) {
        this.entityAttributeType = entityAttributeType;
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

    /**
     * @return the mimeTypeFileExtensions
     */
    public ListWrapper<MimeTypeFileExtensionTransfer> getMimeTypeFileExtensions() {
        return mimeTypeFileExtensions;
    }

    /**
     * @param mimeTypeFileExtensions the mimeTypeFileExtensions to set
     */
    public void setMimeTypeFileExtensions(ListWrapper<MimeTypeFileExtensionTransfer> mimeTypeFileExtensions) {
        this.mimeTypeFileExtensions = mimeTypeFileExtensions;
    }

}
