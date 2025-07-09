// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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

package com.echothree.ui.web.main.action.core.editor;

import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;

@SproutForm(name="EditorAdd")
public class AddActionForm
        extends BaseActionForm {
    
    private String editorName;
    private Boolean hasDimensions;
    private String minimumHeight;
    private String minimumWidth;
    private String maximumHeight;
    private String maximumWidth;
    private String defaultHeight;
    private String defaultWidth;
    private Boolean isDefault;
    private String sortOrder;
    private String description;

    public String getEditorName() {
        return editorName;
    }

    public void setEditorName(String editorName) {
        this.editorName = editorName;
    }

    public Boolean getHasDimensions() {
        return hasDimensions;
    }

    public void setHasDimensions(Boolean hasDimensions) {
        this.hasDimensions = hasDimensions;
    }

    public String getMinimumHeight() {
        return minimumHeight;
    }

    public void setMinimumHeight(String minimumHeight) {
        this.minimumHeight = minimumHeight;
    }

    public String getMinimumWidth() {
        return minimumWidth;
    }

    public void setMinimumWidth(String minimumWidth) {
        this.minimumWidth = minimumWidth;
    }

    public String getMaximumHeight() {
        return maximumHeight;
    }

    public void setMaximumHeight(String maximumHeight) {
        this.maximumHeight = maximumHeight;
    }

    public String getMaximumWidth() {
        return maximumWidth;
    }

    public void setMaximumWidth(String maximumWidth) {
        this.maximumWidth = maximumWidth;
    }

    public String getDefaultHeight() {
        return defaultHeight;
    }

    public void setDefaultHeight(String defaultHeight) {
        this.defaultHeight = defaultHeight;
    }

    public String getDefaultWidth() {
        return defaultWidth;
    }

    public void setDefaultWidth(String defaultWidth) {
        this.defaultWidth = defaultWidth;
    }

    public Boolean getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
        
        hasDimensions = false;
        isDefault = false;
    }

}
