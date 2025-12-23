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

public class ApplicationEditorTransfer
        extends BaseTransfer {
    
    private ApplicationTransfer application;
    private EditorTransfer editor;
    private Boolean isDefault;
    private Integer sortOrder;
    
    /** Creates a new instance of ApplicationEditorTransfer */
    public ApplicationEditorTransfer(ApplicationTransfer application, EditorTransfer editor, Boolean isDefault, Integer sortOrder) {
        this.application = application;
        this.editor = editor;
        this.isDefault = isDefault;
        this.sortOrder = sortOrder;
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
     * Returns the editor.
     * @return the editor
     */
    public EditorTransfer getEditor() {
        return editor;
    }

    /**
     * Sets the editor.
     * @param editor the editor to set
     */
    public void setEditor(EditorTransfer editor) {
        this.editor = editor;
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

}
