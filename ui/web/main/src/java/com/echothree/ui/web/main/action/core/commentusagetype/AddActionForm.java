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

package com.echothree.ui.web.main.action.core.commentusagetype;

import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;

@SproutForm(name="CommentUsageTypeAdd")
public class AddActionForm
        extends BaseActionForm {
    
    private String componentVendorName;
    private String entityTypeName;
    private String commentTypeName;
    private String commentUsageTypeName;
    private Boolean selectedByDefault;
    private String sortOrder;
    private String description;
    
    public String getComponentVendorName() {
        return componentVendorName;
    }
    
    public void setComponentVendorName(String componentVendorName) {
        this.componentVendorName = componentVendorName;
    }
    
    public String getEntityTypeName() {
        return entityTypeName;
    }
    
    public void setEntityTypeName(String entityTypeName) {
        this.entityTypeName = entityTypeName;
    }
    
    public void setCommentTypeName(String commentTypeName) {
        this.commentTypeName = commentTypeName;
    }
    
    public String getCommentTypeName() {
        return commentTypeName;
    }
    
    public void setCommentUsageTypeName(String commentUsageTypeName) {
        this.commentUsageTypeName = commentUsageTypeName;
    }
    
    public String getCommentUsageTypeName() {
        return commentUsageTypeName;
    }
    
    public Boolean getSelectedByDefault() {
        return selectedByDefault;
    }
    
    public void setSelectedByDefault(Boolean selectedByDefault) {
        this.selectedByDefault = selectedByDefault;
    }
    
    public String getSortOrder() {
        return sortOrder;
    }
    
    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
    
    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
        
        this.setSelectedByDefault(false);
    }
    
}
