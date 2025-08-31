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

package com.echothree.ui.web.main.action.purchasing.itempurchasingcategory;

import com.echothree.control.user.vendor.common.VendorUtil;
import com.echothree.control.user.vendor.common.result.GetItemPurchasingCategoryChoicesResult;
import com.echothree.model.control.vendor.common.choice.ItemPurchasingCategoryChoicesBean;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="ItemPurchasingCategoryAdd")
public class AddActionForm
        extends BaseActionForm {
    
    private ItemPurchasingCategoryChoicesBean parentItemPurchasingCategoryChoices;
    
    private String itemPurchasingCategoryName;
    private String parentItemPurchasingCategoryChoice;
    private Boolean isDefault;
    private String sortOrder;
    private String description;
    
    private void setupParentItemPurchasingCategoryChoices()
            throws NamingException {
        if(parentItemPurchasingCategoryChoices == null) {
            var form = VendorUtil.getHome().getGetItemPurchasingCategoryChoicesForm();

            form.setDefaultItemPurchasingCategoryChoice(parentItemPurchasingCategoryChoice);
            form.setAllowNullChoice(String.valueOf(true));

            var commandResult = VendorUtil.getHome().getItemPurchasingCategoryChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var getItemPurchasingCategoryChoicesResult = (GetItemPurchasingCategoryChoicesResult)executionResult.getResult();
            parentItemPurchasingCategoryChoices = getItemPurchasingCategoryChoicesResult.getItemPurchasingCategoryChoices();

            if(parentItemPurchasingCategoryChoice == null)
                parentItemPurchasingCategoryChoice = parentItemPurchasingCategoryChoices.getDefaultValue();
        }
    }
    
    public void setItemPurchasingCategoryName(String itemPurchasingCategoryName) {
        this.itemPurchasingCategoryName = itemPurchasingCategoryName;
    }
    
    public String getItemPurchasingCategoryName() {
        return itemPurchasingCategoryName;
    }
    
    public String getParentItemPurchasingCategoryChoice() {
        return parentItemPurchasingCategoryChoice;
    }
    
    public void setParentItemPurchasingCategoryChoice(String parentItemPurchasingCategoryChoice) {
        this.parentItemPurchasingCategoryChoice = parentItemPurchasingCategoryChoice;
    }
    
    public List<LabelValueBean> getParentItemPurchasingCategoryChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupParentItemPurchasingCategoryChoices();
        if(parentItemPurchasingCategoryChoices != null)
            choices = convertChoices(parentItemPurchasingCategoryChoices);
        
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
