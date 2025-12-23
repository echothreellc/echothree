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

package com.echothree.ui.web.main.action.item.itemcategory;

import com.echothree.control.user.item.common.ItemUtil;
import com.echothree.control.user.item.common.result.GetItemCategoryChoicesResult;
import com.echothree.model.control.item.common.choice.ItemCategoryChoicesBean;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="ItemCategoryAdd")
public class AddActionForm
        extends BaseActionForm {
    
    private ItemCategoryChoicesBean parentItemCategoryChoices;

    private String itemCategoryName;
    private String parentItemCategoryChoice;
    private Boolean isDefault;
    private String sortOrder;
    private String description;
    
    private void setupParentItemCategoryChoices()
            throws NamingException {
        if(parentItemCategoryChoices == null) {
            var form = ItemUtil.getHome().getGetItemCategoryChoicesForm();

            form.setDefaultItemCategoryChoice(parentItemCategoryChoice);
            form.setAllowNullChoice(String.valueOf(true));

            var commandResult = ItemUtil.getHome().getItemCategoryChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var getItemCategoryChoicesResult = (GetItemCategoryChoicesResult)executionResult.getResult();
            parentItemCategoryChoices = getItemCategoryChoicesResult.getItemCategoryChoices();

            if(parentItemCategoryChoice == null) {
                parentItemCategoryChoice = parentItemCategoryChoices.getDefaultValue();
            }
        }
    }

   public void setItemCategoryName(String itemCategoryName) {
        this.itemCategoryName = itemCategoryName;
    }
    
    public String getItemCategoryName() {
        return itemCategoryName;
    }
    
    public String getParentItemCategoryChoice()
            throws NamingException {
        setupParentItemCategoryChoices();
        return parentItemCategoryChoice;
    }

    public void setParentItemCategoryChoice(String parentItemCategoryChoice) {
        this.parentItemCategoryChoice = parentItemCategoryChoice;
    }

    public List<LabelValueBean> getParentItemCategoryChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;

        setupParentItemCategoryChoices();
        if(parentItemCategoryChoices != null)
            choices = convertChoices(parentItemCategoryChoices);

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
