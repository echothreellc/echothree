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

package com.echothree.ui.web.main.action.uom.unitofmeasuretype;

import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;

@SproutForm(name="UnitOfMeasureTypeDescriptionEdit")
public class DescriptionEditActionForm
        extends BaseActionForm {
    
    private String unitOfMeasureKindName;
    private String unitOfMeasureTypeName;
    private String languageIsoName;
    private String singularDescription;
    private String pluralDescription;
    private String symbol;
    
    public void setUnitOfMeasureKindName(String unitOfMeasureKindName) {
        this.unitOfMeasureKindName = unitOfMeasureKindName;
    }
    
    public String getUnitOfMeasureKindName() {
        return unitOfMeasureKindName;
    }
    
    public void setUnitOfMeasureTypeName(String unitOfMeasureTypeName) {
        this.unitOfMeasureTypeName = unitOfMeasureTypeName;
    }
    
    public String getUnitOfMeasureTypeName() {
        return unitOfMeasureTypeName;
    }
    
    public String getLanguageIsoName() {
        return languageIsoName;
    }
    
    public void setLanguageIsoName(String languageIsoName) {
        this.languageIsoName = languageIsoName;
    }
    
    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
    }
    
    public String getSingularDescription() {
        return singularDescription;
    }
    
    public void setSingularDescription(String singularDescription) {
        this.singularDescription = singularDescription;
    }
    
    public String getPluralDescription() {
        return pluralDescription;
    }
    
    public void setPluralDescription(String pluralDescription) {
        this.pluralDescription = pluralDescription;
    }
    
    public String getSymbol() {
        return symbol;
    }
    
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
    
}
