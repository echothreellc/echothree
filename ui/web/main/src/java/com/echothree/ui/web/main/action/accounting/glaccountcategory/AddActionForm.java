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

package com.echothree.ui.web.main.action.accounting.glaccountcategory;

import com.echothree.control.user.accounting.common.AccountingUtil;
import com.echothree.control.user.accounting.common.result.GetGlAccountCategoryChoicesResult;
import com.echothree.model.control.accounting.common.choice.GlAccountCategoryChoicesBean;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="GlAccountCategoryAdd")
public class AddActionForm
        extends BaseActionForm {
    
    private GlAccountCategoryChoicesBean parentGlAccountCategoryChoices;
    
    private String glAccountCategoryName;
    private String parentGlAccountCategoryChoice;
    private Boolean isDefault;
    private String sortOrder;
    private String description;
    
    private void setupParentGlAccountCategoryChoices()
            throws NamingException {
        if(parentGlAccountCategoryChoices == null) {
            var form = AccountingUtil.getHome().getGetGlAccountCategoryChoicesForm();

            form.setDefaultGlAccountCategoryChoice(parentGlAccountCategoryChoice);
            form.setAllowNullChoice(String.valueOf(true));

            var commandResult = AccountingUtil.getHome().getGlAccountCategoryChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var getGlAccountCategoryChoicesResult = (GetGlAccountCategoryChoicesResult)executionResult.getResult();
            parentGlAccountCategoryChoices = getGlAccountCategoryChoicesResult.getGlAccountCategoryChoices();

            if(parentGlAccountCategoryChoice == null)
                parentGlAccountCategoryChoice = parentGlAccountCategoryChoices.getDefaultValue();
        }
    }
    
    public void setGlAccountCategoryName(String glAccountCategoryName) {
        this.glAccountCategoryName = glAccountCategoryName;
    }
    
    public String getGlAccountCategoryName() {
        return glAccountCategoryName;
    }
    
    public String getParentGlAccountCategoryChoice() {
        return parentGlAccountCategoryChoice;
    }
    
    public void setParentGlAccountCategoryChoice(String parentGlAccountCategoryChoice) {
        this.parentGlAccountCategoryChoice = parentGlAccountCategoryChoice;
    }
    
    public List<LabelValueBean> getParentGlAccountCategoryChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupParentGlAccountCategoryChoices();
        if(parentGlAccountCategoryChoices != null)
            choices = convertChoices(parentGlAccountCategoryChoices);
        
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
