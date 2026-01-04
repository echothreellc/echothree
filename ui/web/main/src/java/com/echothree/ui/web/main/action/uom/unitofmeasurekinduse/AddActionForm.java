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

package com.echothree.ui.web.main.action.uom.unitofmeasurekinduse;

import com.echothree.control.user.uom.common.UomUtil;
import com.echothree.control.user.uom.common.result.GetUnitOfMeasureKindChoicesResult;
import com.echothree.control.user.uom.common.result.GetUnitOfMeasureKindUseTypeChoicesResult;
import com.echothree.model.control.uom.common.choice.UnitOfMeasureKindChoicesBean;
import com.echothree.model.control.uom.common.choice.UnitOfMeasureKindUseTypeChoicesBean;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="UnitOfMeasureKindUseAdd")
public class AddActionForm
        extends BaseActionForm {
    
    private UnitOfMeasureKindChoicesBean unitOfMeasureKindChoices;
    private UnitOfMeasureKindUseTypeChoicesBean unitOfMeasureKindUseTypeChoices;
    
    private String unitOfMeasureKindName;
    private String unitOfMeasureKindUseTypeName;
    private String unitOfMeasureKindChoice;
    private String unitOfMeasureKindUseTypeChoice;
    private Boolean isDefault;
    private String sortOrder;
    private String forwardParameter;
    
    private void setupUnitOfMeasureKindChoices()
            throws NamingException {
        if(unitOfMeasureKindChoices == null) {
            var form = UomUtil.getHome().getGetUnitOfMeasureKindChoicesForm();

            form.setDefaultUnitOfMeasureKindChoice(unitOfMeasureKindChoice);
            form.setAllowNullChoice(String.valueOf(false));

            var commandResult = UomUtil.getHome().getUnitOfMeasureKindChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var getUnitOfMeasureKindChoicesResult = (GetUnitOfMeasureKindChoicesResult)executionResult.getResult();
            unitOfMeasureKindChoices = getUnitOfMeasureKindChoicesResult.getUnitOfMeasureKindChoices();

            if(unitOfMeasureKindChoice == null) {
                unitOfMeasureKindChoice = unitOfMeasureKindChoices.getDefaultValue();
            }
        }
    }
    
    private void setupUnitOfMeasureKindUseTypeChoices()
            throws NamingException {
        if(unitOfMeasureKindUseTypeChoices == null) {
            var form = UomUtil.getHome().getGetUnitOfMeasureKindUseTypeChoicesForm();

            form.setDefaultUnitOfMeasureKindUseTypeChoice(unitOfMeasureKindUseTypeChoice);
            form.setAllowNullChoice(String.valueOf(false));

            var commandResult = UomUtil.getHome().getUnitOfMeasureKindUseTypeChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var getUnitOfMeasureKindUseTypeChoicesResult = (GetUnitOfMeasureKindUseTypeChoicesResult)executionResult.getResult();
            unitOfMeasureKindUseTypeChoices = getUnitOfMeasureKindUseTypeChoicesResult.getUnitOfMeasureKindUseTypeChoices();

            if(unitOfMeasureKindUseTypeChoice == null) {
                unitOfMeasureKindUseTypeChoice = unitOfMeasureKindUseTypeChoices.getDefaultValue();
            }
        }
    }
    
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
    
    public List<LabelValueBean> getUnitOfMeasureKindChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupUnitOfMeasureKindChoices();
        if(unitOfMeasureKindChoices != null) {
            choices = convertChoices(unitOfMeasureKindChoices);
        }
        
        return choices;
    }
    
    public String getUnitOfMeasureKindChoice()
            throws NamingException {
        setupUnitOfMeasureKindChoices();
        
        return unitOfMeasureKindChoice;
    }
    
    public void setUnitOfMeasureKindChoice(String unitOfMeasureKindChoice) {
        this.unitOfMeasureKindChoice = unitOfMeasureKindChoice;
    }
    
    public List<LabelValueBean> getUnitOfMeasureKindUseTypeChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupUnitOfMeasureKindUseTypeChoices();
        if(unitOfMeasureKindUseTypeChoices != null) {
            choices = convertChoices(unitOfMeasureKindUseTypeChoices);
        }
        
        return choices;
    }
    
    public String getUnitOfMeasureKindUseTypeChoice()
            throws NamingException {
        setupUnitOfMeasureKindUseTypeChoices();
        
        return unitOfMeasureKindUseTypeChoice;
    }
    
    public void setUnitOfMeasureKindUseTypeChoice(String unitOfMeasureKindUseTypeChoice) {
        this.unitOfMeasureKindUseTypeChoice = unitOfMeasureKindUseTypeChoice;
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
