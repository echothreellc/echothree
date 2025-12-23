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

package com.echothree.model.control.icon.common.transfer;

import com.echothree.model.control.document.common.transfer.DocumentTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class IconTransfer
        extends BaseTransfer {
    
    private String iconName;
    private DocumentTransfer document;
    private Boolean isDefault;
    private Integer sortOrder;
    
    /** Creates a new instance of IconTransfer */
    public IconTransfer(String iconName, DocumentTransfer document, Boolean isDefault, Integer sortOrder) {
        this.iconName = iconName;
        this.document = document;
        this.isDefault = isDefault;
        this.sortOrder = sortOrder;
    }
    
    public String getIconName() {
        return iconName;
    }
    
    public void setIconName(String iconName) {
        this.iconName = iconName;
    }
    
    public DocumentTransfer getDocument() {
        return document;
    }
    
    public void setDocument(DocumentTransfer document) {
        this.document = document;
    }
    
    public Boolean getIsDefault() {
        return isDefault;
    }
    
    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }
    
    public Integer getSortOrder() {
        return sortOrder;
    }
    
    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }
    
}
