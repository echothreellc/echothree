// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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
import com.echothree.model.control.core.common.transfer.MimeTypeUsageTypeTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class ItemDescriptionTypeTransfer
        extends BaseTransfer {
    
    private String itemDescriptionTypeName;
    private ItemDescriptionTypeTransfer parentItemDescriptionType;
    private Boolean useParentIfMissing;
    private MimeTypeUsageTypeTransfer mimeTypeUsageType;
    private Boolean checkContentWebAddress;
    private Boolean includeInIndex;
    private Boolean indexDefault;
    private Boolean isDefault;
    private Integer sortOrder;
    private String description;
    private Integer minimumHeight;
    private Integer minimumWidth;
    private Integer maximumHeight;
    private Integer maximumWidth;
    private Integer preferredHeight;
    private Integer preferredWidth;
    private MimeTypeTransfer preferredMimeType;
    private Integer quality;
    private Boolean scaleFromParent;

    /** Creates a new instance of ItemDescriptionTypeTransfer */
    public ItemDescriptionTypeTransfer(String itemDescriptionTypeName, ItemDescriptionTypeTransfer parentItemDescriptionType, Boolean useParentIfMissing,
            MimeTypeUsageTypeTransfer mimeTypeUsageType, Boolean checkContentWebAddress, Boolean includeInIndex, Boolean indexDefault, Boolean isDefault,
            Integer sortOrder, String description, Integer minimumHeight, Integer minimumWidth, Integer maximumHeight, Integer maximumWidth,
            Integer preferredHeight, Integer preferredWidth, MimeTypeTransfer preferredMimeType, Integer quality, Boolean scaleFromParent) {
        this.itemDescriptionTypeName = itemDescriptionTypeName;
        this.parentItemDescriptionType = parentItemDescriptionType;
        this.useParentIfMissing = useParentIfMissing;
        this.mimeTypeUsageType = mimeTypeUsageType;
        this.checkContentWebAddress = checkContentWebAddress;
        this.includeInIndex = includeInIndex;
        this.indexDefault = indexDefault;
        this.isDefault = isDefault;
        this.sortOrder = sortOrder;
        this.description = description;
        this.minimumHeight = minimumHeight;
        this.minimumWidth = minimumWidth;
        this.maximumHeight = maximumHeight;
        this.maximumWidth = maximumWidth;
        this.preferredHeight = preferredHeight;
        this.preferredWidth = preferredWidth;
        this.preferredMimeType = preferredMimeType;
        this.quality = quality;
        this.scaleFromParent = scaleFromParent;
    }

    /**
     * @return the itemDescriptionTypeName
     */
    public String getItemDescriptionTypeName() {
        return itemDescriptionTypeName;
    }

    /**
     * @param itemDescriptionTypeName the itemDescriptionTypeName to set
     */
    public void setItemDescriptionTypeName(String itemDescriptionTypeName) {
        this.itemDescriptionTypeName = itemDescriptionTypeName;
    }

    /**
     * @return the parentItemDescriptionType
     */
    public ItemDescriptionTypeTransfer getParentItemDescriptionType() {
        return parentItemDescriptionType;
    }

    /**
     * @param parentItemDescriptionType the parentItemDescriptionType to set
     */
    public void setParentItemDescriptionType(ItemDescriptionTypeTransfer parentItemDescriptionType) {
        this.parentItemDescriptionType = parentItemDescriptionType;
    }

    /**
     * @return the useParentIfMissing
     */
    public Boolean getUseParentIfMissing() {
        return useParentIfMissing;
    }

    /**
     * @param useParentIfMissing the useParentIfMissing to set
     */
    public void setUseParentIfMissing(Boolean useParentIfMissing) {
        this.useParentIfMissing = useParentIfMissing;
    }

    /**
     * @return the mimeTypeUsageType
     */
    public MimeTypeUsageTypeTransfer getMimeTypeUsageType() {
        return mimeTypeUsageType;
    }

    /**
     * @param mimeTypeUsageType the mimeTypeUsageType to set
     */
    public void setMimeTypeUsageType(MimeTypeUsageTypeTransfer mimeTypeUsageType) {
        this.mimeTypeUsageType = mimeTypeUsageType;
    }

    /**
     * @return the checkContentWebAddress
     */
    public Boolean getCheckContentWebAddress() {
        return checkContentWebAddress;
    }

    /**
     * @param checkContentWebAddress the checkContentWebAddress to set
     */
    public void setCheckContentWebAddress(Boolean checkContentWebAddress) {
        this.checkContentWebAddress = checkContentWebAddress;
    }

    /**
     * @return the includeInIndex
     */
    public Boolean getIncludeInIndex() {
        return includeInIndex;
    }

    /**
     * @param includeInIndex the includeInIndex to set
     */
    public void setIncludeInIndex(Boolean includeInIndex) {
        this.includeInIndex = includeInIndex;
    }

    /**
     * @return the indexDefault
     */
    public Boolean getIndexDefault() {
        return indexDefault;
    }

    /**
     * @param indexDefault the indexDefault to set
     */
    public void setIndexDefault(Boolean indexDefault) {
        this.indexDefault = indexDefault;
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
     * @return the minimumHeight
     */
    public Integer getMinimumHeight() {
        return minimumHeight;
    }

    /**
     * @param minimumHeight the minimumHeight to set
     */
    public void setMinimumHeight(Integer minimumHeight) {
        this.minimumHeight = minimumHeight;
    }

    /**
     * @return the minimumWidth
     */
    public Integer getMinimumWidth() {
        return minimumWidth;
    }

    /**
     * @param minimumWidth the minimumWidth to set
     */
    public void setMinimumWidth(Integer minimumWidth) {
        this.minimumWidth = minimumWidth;
    }

    /**
     * @return the maximumHeight
     */
    public Integer getMaximumHeight() {
        return maximumHeight;
    }

    /**
     * @param maximumHeight the maximumHeight to set
     */
    public void setMaximumHeight(Integer maximumHeight) {
        this.maximumHeight = maximumHeight;
    }

    /**
     * @return the maximumWidth
     */
    public Integer getMaximumWidth() {
        return maximumWidth;
    }

    /**
     * @param maximumWidth the maximumWidth to set
     */
    public void setMaximumWidth(Integer maximumWidth) {
        this.maximumWidth = maximumWidth;
    }

    /**
     * @return the preferredHeight
     */
    public Integer getPreferredHeight() {
        return preferredHeight;
    }

    /**
     * @param preferredHeight the preferredHeight to set
     */
    public void setPreferredHeight(Integer preferredHeight) {
        this.preferredHeight = preferredHeight;
    }

    /**
     * @return the preferredWidth
     */
    public Integer getPreferredWidth() {
        return preferredWidth;
    }

    /**
     * @param preferredWidth the preferredWidth to set
     */
    public void setPreferredWidth(Integer preferredWidth) {
        this.preferredWidth = preferredWidth;
    }

    /**
     * @return the preferredMimeType
     */
    public MimeTypeTransfer getPreferredMimeType() {
        return preferredMimeType;
    }

    /**
     * @param preferredMimeType the preferredMimeType to set
     */
    public void setPreferredMimeType(MimeTypeTransfer preferredMimeType) {
        this.preferredMimeType = preferredMimeType;
    }

    /**
     * @return the quality
     */
    public Integer getQuality() {
        return quality;
    }

    /**
     * @param quality the quality to set
     */
    public void setQuality(Integer quality) {
        this.quality = quality;
    }

    /**
     * @return the scaleFromParent
     */
    public Boolean getScaleFromParent() {
        return scaleFromParent;
    }

    /**
     * @param scaleFromParent the scaleFromParent to set
     */
    public void setScaleFromParent(Boolean scaleFromParent) {
        this.scaleFromParent = scaleFromParent;
    }
    
}
