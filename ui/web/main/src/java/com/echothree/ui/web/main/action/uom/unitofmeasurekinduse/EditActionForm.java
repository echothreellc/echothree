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

package com.echothree.ui.web.main.action.uom.unitofmeasurekinduse;

import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;

@SproutForm(name="UnitOfMeasureKindUseEdit")
public class EditActionForm
        extends BaseActionForm {
    
    private String unitOfMeasureKindName;
    private String unitOfMeasureKindUseTypeName;
    private Boolean isDefault;
    private String sortOrder;
    private String forwardParameter;
    
    public String getUnitOfMeasureKindName() {
        return unitOfMeasureKindName;
    }
    
    public void setUnitOfMeasureKindName(String unitOfMeasureKindName) {
        this.unitOfMeasureKindName = unitOfMeasureKindName;
    }
    
    public String getUnitOfMeasureKindUseTypeName() {
        return unitOfMeasureKindUseTypeName;
    }
    
    public void setUnitOfMeasureKindUseTypeName(String unitOfMeasureKindUseTypeName) {
        this.unitOfMeasureKindUseTypeName = unitOfMeasureKindUseTypeName;
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
    
    public String getForwardParameter() {
        return forwardParameter;
    }
    
    public void setForwardParameter(String forwardParameter) {
        this.forwardParameter = forwardParameter;
    }
    
    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
        
        isDefault = false;
    }
    
}
