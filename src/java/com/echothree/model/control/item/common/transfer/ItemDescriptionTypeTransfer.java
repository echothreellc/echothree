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
     * Returns the itemDescriptionTypeName.
     * @return the itemDescriptionTypeName
     */
    public String getItemDescriptionTypeName() {
        return itemDescriptionTypeName;
    }

    /**
     * Sets the itemDescriptionTypeName.
     * @param itemDescriptionTypeName the itemDescriptionTypeName to set
     */
    public void setItemDescriptionTypeName(String itemDescriptionTypeName) {
        this.itemDescriptionTypeName = itemDescriptionTypeName;
    }

    /**
     * Returns the parentItemDescriptionType.
     * @return the parentItemDescriptionType
     */
    public ItemDescriptionTypeTransfer getParentItemDescriptionType() {
        return parentItemDescriptionType;
    }

    /**
     * Sets the parentItemDescriptionType.
     * @param parentItemDescriptionType the parentItemDescriptionType to set
     */
    public void setParentItemDescriptionType(ItemDescriptionTypeTransfer parentItemDescriptionType) {
        this.parentItemDescriptionType = parentItemDescriptionType;
    }

    /**
     * Returns the useParentIfMissing.
     * @return the useParentIfMissing
     */
    public Boolean getUseParentIfMissing() {
        return useParentIfMissing;
    }

    /**
     * Sets the useParentIfMissing.
     * @param useParentIfMissing the useParentIfMissing to set
     */
    public void setUseParentIfMissing(Boolean useParentIfMissing) {
        this.useParentIfMissing = useParentIfMissing;
    }

    /**
     * Returns the mimeTypeUsageType.
     * @return the mimeTypeUsageType
     */
    public MimeTypeUsageTypeTransfer getMimeTypeUsageType() {
        return mimeTypeUsageType;
    }

    /**
     * Sets the mimeTypeUsageType.
     * @param mimeTypeUsageType the mimeTypeUsageType to set
     */
    public void setMimeTypeUsageType(MimeTypeUsageTypeTransfer mimeTypeUsageType) {
        this.mimeTypeUsageType = mimeTypeUsageType;
    }

    /**
     * Returns the checkContentWebAddress.
     * @return the checkContentWebAddress
     */
    public Boolean getCheckContentWebAddress() {
        return checkContentWebAddress;
    }

    /**
     * Sets the checkContentWebAddress.
     * @param checkContentWebAddress the checkContentWebAddress to set
     */
    public void setCheckContentWebAddress(Boolean checkContentWebAddress) {
        this.checkContentWebAddress = checkContentWebAddress;
    }

    /**
     * Returns the includeInIndex.
     * @return the includeInIndex
     */
    public Boolean getIncludeInIndex() {
        return includeInIndex;
    }

    /**
     * Sets the includeInIndex.
     * @param includeInIndex the includeInIndex to set
     */
    public void setIncludeInIndex(Boolean includeInIndex) {
        this.includeInIndex = includeInIndex;
    }

    /**
     * Returns the indexDefault.
     * @return the indexDefault
     */
    public Boolean getIndexDefault() {
        return indexDefault;
    }

    /**
     * Sets the indexDefault.
     * @param indexDefault the indexDefault to set
     */
    public void setIndexDefault(Boolean indexDefault) {
        this.indexDefault = indexDefault;
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
     * Returns the minimumHeight.
     * @return the minimumHeight
     */
    public Integer getMinimumHeight() {
        return minimumHeight;
    }

    /**
     * Sets the minimumHeight.
     * @param minimumHeight the minimumHeight to set
     */
    public void setMinimumHeight(Integer minimumHeight) {
        this.minimumHeight = minimumHeight;
    }

    /**
     * Returns the minimumWidth.
     * @return the minimumWidth
     */
    public Integer getMinimumWidth() {
        return minimumWidth;
    }

    /**
     * Sets the minimumWidth.
     * @param minimumWidth the minimumWidth to set
     */
    public void setMinimumWidth(Integer minimumWidth) {
        this.minimumWidth = minimumWidth;
    }

    /**
     * Returns the maximumHeight.
     * @return the maximumHeight
     */
    public Integer getMaximumHeight() {
        return maximumHeight;
    }

    /**
     * Sets the maximumHeight.
     * @param maximumHeight the maximumHeight to set
     */
    public void setMaximumHeight(Integer maximumHeight) {
        this.maximumHeight = maximumHeight;
    }

    /**
     * Returns the maximumWidth.
     * @return the maximumWidth
     */
    public Integer getMaximumWidth() {
        return maximumWidth;
    }

    /**
     * Sets the maximumWidth.
     * @param maximumWidth the maximumWidth to set
     */
    public void setMaximumWidth(Integer maximumWidth) {
        this.maximumWidth = maximumWidth;
    }

    /**
     * Returns the preferredHeight.
     * @return the preferredHeight
     */
    public Integer getPreferredHeight() {
        return preferredHeight;
    }

    /**
     * Sets the preferredHeight.
     * @param preferredHeight the preferredHeight to set
     */
    public void setPreferredHeight(Integer preferredHeight) {
        this.preferredHeight = preferredHeight;
    }

    /**
     * Returns the preferredWidth.
     * @return the preferredWidth
     */
    public Integer getPreferredWidth() {
        return preferredWidth;
    }

    /**
     * Sets the preferredWidth.
     * @param preferredWidth the preferredWidth to set
     */
    public void setPreferredWidth(Integer preferredWidth) {
        this.preferredWidth = preferredWidth;
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

    /**
     * Returns the scaleFromParent.
     * @return the scaleFromParent
     */
    public Boolean getScaleFromParent() {
        return scaleFromParent;
    }

    /**
     * Sets the scaleFromParent.
     * @param scaleFromParent the scaleFromParent to set
     */
    public void setScaleFromParent(Boolean scaleFromParent) {
        this.scaleFromParent = scaleFromParent;
    }
    
}
