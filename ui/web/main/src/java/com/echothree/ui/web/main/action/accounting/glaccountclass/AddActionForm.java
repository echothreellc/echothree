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

package com.echothree.ui.web.main.action.accounting.glaccountclass;

import com.echothree.control.user.accounting.common.AccountingUtil;
import com.echothree.control.user.accounting.common.result.GetGlAccountClassChoicesResult;
import com.echothree.model.control.accounting.common.choice.GlAccountClassChoicesBean;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="GlAccountClassAdd")
public class AddActionForm
        extends BaseActionForm {
    
    private GlAccountClassChoicesBean parentGlAccountClassChoices;
    
    private String glAccountClassName;
    private String parentGlAccountClassChoice;
    private Boolean isDefault;
    private String sortOrder;
    private String description;
    
    private void setupParentGlAccountClassChoices()
            throws NamingException {
        if(parentGlAccountClassChoices == null) {
            var form = AccountingUtil.getHome().getGetGlAccountClassChoicesForm();

            form.setDefaultGlAccountClassChoice(parentGlAccountClassChoice);
            form.setAllowNullChoice(String.valueOf(true));

            var commandResult = AccountingUtil.getHome().getGlAccountClassChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var getGlAccountClassChoicesResult = (GetGlAccountClassChoicesResult)executionResult.getResult();
            parentGlAccountClassChoices = getGlAccountClassChoicesResult.getGlAccountClassChoices();

            if(parentGlAccountClassChoice == null)
                parentGlAccountClassChoice = parentGlAccountClassChoices.getDefaultValue();
        }
    }
    
    public void setGlAccountClassName(String glAccountClassName) {
        this.glAccountClassName = glAccountClassName;
    }
    
    public String getGlAccountClassName() {
        return glAccountClassName;
    }
    
    public String getParentGlAccountClassChoice() {
        return parentGlAccountClassChoice;
    }
    
    public void setParentGlAccountClassChoice(String parentGlAccountClassChoice) {
        this.parentGlAccountClassChoice = parentGlAccountClassChoice;
    }
    
    public List<LabelValueBean> getParentGlAccountClassChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupParentGlAccountClassChoices();
        if(parentGlAccountClassChoices != null)
            choices = convertChoices(parentGlAccountClassChoices);
        
        return choices;
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
