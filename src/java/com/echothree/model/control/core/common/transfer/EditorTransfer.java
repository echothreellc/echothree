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

public class EditorTransfer
        extends BaseTransfer {
    
    private String editorName;
    private Boolean hasDimensions;
    private Integer minimumHeight;
    private Integer minimumWidth;
    private Integer maximumHeight;
    private Integer maximumWidth;
    private Integer defaultHeight;
    private Integer defaultWidth;
    private Boolean isDefault;
    private Integer sortOrder;
    private String description;
    
    /** Creates a new instance of EditorTransfer */
    public EditorTransfer(String editorName, Boolean hasDimensions, Integer minimumHeight, Integer minimumWidth, Integer maximumHeight, Integer maximumWidth,
            Integer defaultHeight, Integer defaultWidth, Boolean isDefault, Integer sortOrder, String description) {
        this.editorName = editorName;
        this.hasDimensions = hasDimensions;
        this.minimumHeight = minimumHeight;
        this.minimumWidth = minimumWidth;
        this.maximumHeight = maximumHeight;
        this.maximumWidth = maximumWidth;
        this.defaultHeight = defaultHeight;
        this.defaultWidth = defaultWidth;
        this.isDefault = isDefault;
        this.sortOrder = sortOrder;
        this.description = description;
    }

    /**
     * Returns the editorName.
     * @return the editorName
     */
    public String getEditorName() {
        return editorName;
    }

    /**
     * Sets the editorName.
     * @param editorName the editorName to set
     */
    public void setEditorName(String editorName) {
        this.editorName = editorName;
    }

    /**
     * Returns the hasDimensions.
     * @return the hasDimensions
     */
    public Boolean getHasDimensions() {
        return hasDimensions;
    }

    /**
     * Sets the hasDimensions.
     * @param hasDimensions the hasDimensions to set
     */
    public void setHasDimensions(Boolean hasDimensions) {
        this.hasDimensions = hasDimensions;
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
     * Returns the defaultHeight.
     * @return the defaultHeight
     */
    public Integer getDefaultHeight() {
        return defaultHeight;
    }

    /**
     * Sets the defaultHeight.
     * @param defaultHeight the defaultHeight to set
     */
    public void setDefaultHeight(Integer defaultHeight) {
        this.defaultHeight = defaultHeight;
    }

    /**
     * Returns the defaultWidth.
     * @return the defaultWidth
     */
    public Integer getDefaultWidth() {
        return defaultWidth;
    }

    /**
     * Sets the defaultWidth.
     * @param defaultWidth the defaultWidth to set
     */
    public void setDefaultWidth(Integer defaultWidth) {
        this.defaultWidth = defaultWidth;
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
