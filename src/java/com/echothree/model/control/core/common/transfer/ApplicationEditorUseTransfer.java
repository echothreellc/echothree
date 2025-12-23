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

public class ApplicationEditorUseTransfer
        extends BaseTransfer {
    
    private ApplicationTransfer application;
    private String applicationEditorUseName;
    private ApplicationEditorTransfer defaultApplicationEditor;
    private Integer defaultHeight;
    private Integer defaultWidth;
    private Boolean isDefault;
    private Integer sortOrder;
    private String description;
    
    /** Creates a new instance of ApplicationEditorUseTransfer */
    public ApplicationEditorUseTransfer(ApplicationTransfer application, String applicationEditorUseName, ApplicationEditorTransfer defaultApplicationEditor,
            Integer defaultHeight, Integer defaultWidth, Boolean isDefault, Integer sortOrder, String description) {
        this.application = application;
        this.applicationEditorUseName = applicationEditorUseName;
        this.defaultApplicationEditor = defaultApplicationEditor;
        this.defaultHeight = defaultHeight;
        this.defaultWidth = defaultWidth;
        this.isDefault = isDefault;
        this.sortOrder = sortOrder;
        this.description = description;
    }

    /**
     * Returns the application.
     * @return the application
     */
    public ApplicationTransfer getApplication() {
        return application;
    }

    /**
     * Sets the application.
     * @param application the application to set
     */
    public void setApplication(ApplicationTransfer application) {
        this.application = application;
    }

    /**
     * Returns the applicationEditorUseName.
     * @return the applicationEditorUseName
     */
    public String getApplicationEditorUseName() {
        return applicationEditorUseName;
    }

    /**
     * Sets the applicationEditorUseName.
     * @param applicationEditorUseName the applicationEditorUseName to set
     */
    public void setApplicationEditorUseName(String applicationEditorUseName) {
        this.applicationEditorUseName = applicationEditorUseName;
    }

    /**
     * Returns the defaultApplicationEditor.
     * @return the defaultApplicationEditor
     */
    public ApplicationEditorTransfer getDefaultApplicationEditor() {
        return defaultApplicationEditor;
    }

    /**
     * Sets the defaultApplicationEditor.
     * @param defaultApplicationEditor the defaultApplicationEditor to set
     */
    public void setDefaultApplicationEditor(ApplicationEditorTransfer defaultApplicationEditor) {
        this.defaultApplicationEditor = defaultApplicationEditor;
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
