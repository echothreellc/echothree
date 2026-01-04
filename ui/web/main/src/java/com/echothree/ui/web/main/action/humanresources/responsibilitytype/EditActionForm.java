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

package com.echothree.ui.web.main.action.humanresources.responsibilitytype;

import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;

@SproutForm(name="ResponsibilityTypeEdit")
public class EditActionForm
        extends BaseActionForm {
    
    private String originalResponsibilityTypeName;
    private String responsibilityTypeName;
    private Boolean isDefault;
    private String sortOrder;
    private String description;
    
    public String getOriginalResponsibilityTypeName() {
        return originalResponsibilityTypeName;
    }
    
    public void setOriginalResponsibilityTypeName(String originalResponsibilityTypeName) {
        this.originalResponsibilityTypeName = originalResponsibilityTypeName;
    }
    
    public void setResponsibilityTypeName(String responsibilityTypeName) {
        this.responsibilityTypeName = responsibilityTypeName;
    }
    
    public String getResponsibilityTypeName() {
        return responsibilityTypeName;
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
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
    
    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
        
        isDefault = false;
    }
    
}
