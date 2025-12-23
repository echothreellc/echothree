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

package com.echothree.ui.web.main.action.accounting.itemaccountingcategory;

import com.echothree.control.user.accounting.common.AccountingUtil;
import com.echothree.control.user.accounting.common.result.GetItemAccountingCategoryChoicesResult;
import com.echothree.model.control.accounting.common.choice.ItemAccountingCategoryChoicesBean;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="ItemAccountingCategoryAdd")
public class AddActionForm
        extends BaseActionForm {
    
    private ItemAccountingCategoryChoicesBean parentItemAccountingCategoryChoices;
    
    private String itemAccountingCategoryName;
    private String parentItemAccountingCategoryChoice;
    private Boolean isDefault;
    private String sortOrder;
    private String description;
    
    private void setupParentItemAccountingCategoryChoices()
            throws NamingException {
        if(parentItemAccountingCategoryChoices == null) {
            var form = AccountingUtil.getHome().getGetItemAccountingCategoryChoicesForm();

            form.setDefaultItemAccountingCategoryChoice(parentItemAccountingCategoryChoice);
            form.setAllowNullChoice(String.valueOf(true));

            var commandResult = AccountingUtil.getHome().getItemAccountingCategoryChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var getItemAccountingCategoryChoicesResult = (GetItemAccountingCategoryChoicesResult)executionResult.getResult();
            parentItemAccountingCategoryChoices = getItemAccountingCategoryChoicesResult.getItemAccountingCategoryChoices();

            if(parentItemAccountingCategoryChoice == null)
                parentItemAccountingCategoryChoice = parentItemAccountingCategoryChoices.getDefaultValue();
        }
    }
    
    public void setItemAccountingCategoryName(String itemAccountingCategoryName) {
        this.itemAccountingCategoryName = itemAccountingCategoryName;
    }
    
    public String getItemAccountingCategoryName() {
        return itemAccountingCategoryName;
    }
    
    public String getParentItemAccountingCategoryChoice() {
        return parentItemAccountingCategoryChoice;
    }
    
    public void setParentItemAccountingCategoryChoice(String parentItemAccountingCategoryChoice) {
        this.parentItemAccountingCategoryChoice = parentItemAccountingCategoryChoice;
    }
    
    public List<LabelValueBean> getParentItemAccountingCategoryChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupParentItemAccountingCategoryChoices();
        if(parentItemAccountingCategoryChoices != null)
            choices = convertChoices(parentItemAccountingCategoryChoices);
        
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
