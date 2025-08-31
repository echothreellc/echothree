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

package com.echothree.ui.web.main.action.core.entitylongrange;

import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;

@SproutForm(name="EntityLongRangeAdd")
public class AddActionForm
        extends BaseActionForm {
    
    private String componentVendorName;
    private String entityTypeName;
    private String entityAttributeName;
    private String entityLongRangeName;
    private String minimumLongValue;
    private String maximumLongValue;
    private Boolean isDefault;
    private String sortOrder;
    private String description;
    
    public void setComponentVendorName(String componentVendorName) {
        this.componentVendorName = componentVendorName;
    }
    
    public String getComponentVendorName() {
        return componentVendorName;
    }
    
    public void setEntityTypeName(String entityTypeName) {
        this.entityTypeName = entityTypeName;
    }
    
    public String getEntityTypeName() {
        return entityTypeName;
    }
    
    public void setEntityAttributeName(String entityAttributeName) {
        this.entityAttributeName = entityAttributeName;
    }
    
    public String getEntityAttributeName() {
        return entityAttributeName;
    }
    
    public void setEntityLongRangeName(String entityLongRangeName) {
        this.entityLongRangeName = entityLongRangeName;
    }
    
    public String getEntityLongRangeName() {
        return entityLongRangeName;
    }
    
    public String getMinimumLongValue() {
        return minimumLongValue;
    }
    
    public void setMinimumLongValue(String minimumLongValue) {
        this.minimumLongValue = minimumLongValue;
    }
    
    public String getMaximumLongValue() {
        return maximumLongValue;
    }
    
    public void setMaximumLongValue(String maximumLongValue) {
        this.maximumLongValue = maximumLongValue;
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
        
        componentVendorName = request.getParameter(ParameterConstants.COMPONENT_VENDOR_NAME);
        entityTypeName = request.getParameter(ParameterConstants.ENTITY_TYPE_NAME);
        entityAttributeName = request.getParameter(ParameterConstants.ENTITY_ATTRIBUTE_NAME);
        this.isDefault = false;
    }
    
}
