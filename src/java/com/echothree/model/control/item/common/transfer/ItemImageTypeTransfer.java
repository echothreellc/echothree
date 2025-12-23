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

import com.echothree.model.control.core.common.transfer.MimeTypeTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class ItemImageTypeTransfer
        extends BaseTransfer {
    
    private String itemImageTypeName;
    private MimeTypeTransfer preferredMimeType;
    private Integer quality;
    private Boolean isDefault;
    private Integer sortOrder;
    private String description;
    
    /** Creates a new instance of ItemImageTypeTransfer */
    public ItemImageTypeTransfer(String itemImageTypeName,  MimeTypeTransfer preferredMimeType, Integer quality, Boolean isDefault, Integer sortOrder, String description) {
        this.itemImageTypeName = itemImageTypeName;
        this.preferredMimeType = preferredMimeType;
        this.quality = quality;
        this.isDefault = isDefault;
        this.sortOrder = sortOrder;
        this.description = description;
    }
    
    public String getItemImageTypeName() {
        return itemImageTypeName;
    }
    
    public void setItemImageTypeName(String itemImageTypeName) {
        this.itemImageTypeName = itemImageTypeName;
    }
    
    /**
     * Returns the preferredMimeType.
     * @return the preferredMimeType
     */
    public MimeTypeTransfer getPreferredMimeType() {
        return preferredMimeType;
    }

    /**
     * Sets the preferredMimeType.
     * @param preferredMimeType the preferredMimeType to set
     */
    public void setPreferredMimeType(MimeTypeTransfer preferredMimeType) {
        this.preferredMimeType = preferredMimeType;
    }

    /**
     * Returns the quality.
     * @return the quality
     */
    public Integer getQuality() {
        return quality;
    }

    /**
     * Sets the quality.
     * @param quality the quality to set
     */
    public void setQuality(Integer quality) {
        this.quality = quality;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }
    
    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }
    
    public Boolean getIsDefault() {
        return isDefault;
    }
    
    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

}
