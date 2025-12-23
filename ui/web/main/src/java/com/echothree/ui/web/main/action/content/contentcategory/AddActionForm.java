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

package com.echothree.ui.web.main.action.content.contentcategory;

import com.echothree.control.user.offer.common.OfferUtil;
import com.echothree.control.user.offer.common.result.GetSourceChoicesResult;
import com.echothree.control.user.selector.common.SelectorUtil;
import com.echothree.control.user.selector.common.result.GetSelectorChoicesResult;
import com.echothree.model.control.offer.common.choice.SourceChoicesBean;
import com.echothree.model.control.selector.common.SelectorKinds;
import com.echothree.model.control.selector.common.SelectorTypes;
import com.echothree.model.control.selector.common.choice.SelectorChoicesBean;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="ContentCategoryAdd")
public class AddActionForm
        extends BaseActionForm {
    
    private SourceChoicesBean defaultSourceChoices;
    private SelectorChoicesBean contentCategoryContentCategoryItemSelectorChoices;
    
    protected String contentCollectionName = null;
    protected String contentCatalogName = null;
    private String contentCategoryName;
    private String defaultSourceChoice;
    private String contentCategoryContentCategoryItemSelectorChoice;
    private Boolean isDefault;
    private String sortOrder;
    private String description;
    private String parentContentCategoryName;
    
    private void setupDefaultSourceChoices()
            throws NamingException {
        if(defaultSourceChoices == null) {
            var form = OfferUtil.getHome().getGetSourceChoicesForm();

            form.setDefaultSourceChoice(getDefaultSourceChoice());
            form.setAllowNullChoice(String.valueOf(true));

            var commandResult = OfferUtil.getHome().getSourceChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetSourceChoicesResult)executionResult.getResult();
            defaultSourceChoices = result.getSourceChoices();

            if(getDefaultSourceChoice() == null)
                setDefaultSourceChoice(defaultSourceChoices.getDefaultValue());
        }
    }
    
    public List<LabelValueBean> getDefaultSourceChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupDefaultSourceChoices();
        if(defaultSourceChoices != null)
            choices = convertChoices(defaultSourceChoices);
        
        return choices;
    }
    
    private void setupContentCategoryItemSelectorChoices()
            throws NamingException {
        if(contentCategoryContentCategoryItemSelectorChoices == null) {
            var form = SelectorUtil.getHome().getGetSelectorChoicesForm();

            form.setSelectorKindName(SelectorKinds.ITEM.name());
            form.setSelectorTypeName(SelectorTypes.CONTENT_CATEGORY.name());
            form.setDefaultSelectorChoice(getContentCategoryItemSelectorChoice());
            form.setAllowNullChoice(String.valueOf(true));

            var commandResult = SelectorUtil.getHome().getSelectorChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetSelectorChoicesResult)executionResult.getResult();
            contentCategoryContentCategoryItemSelectorChoices = result.getSelectorChoices();

            if(getContentCategoryItemSelectorChoice() == null)
                setContentCategoryItemSelectorChoice(contentCategoryContentCategoryItemSelectorChoices.getDefaultValue());
        }
    }
    
    public List<LabelValueBean> getContentCategoryItemSelectorChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupContentCategoryItemSelectorChoices();
        if(contentCategoryContentCategoryItemSelectorChoices != null)
            choices = convertChoices(contentCategoryContentCategoryItemSelectorChoices);
        
        return choices;
    }
    
    public String getContentCollectionName() {
        return contentCollectionName;
    }
    
    public void setContentCollectionName(String contentCollectionName) {
        this.contentCollectionName = contentCollectionName;
    }
    
    public String getContentCatalogName() {
        return contentCatalogName;
    }
    
    public void setContentCatalogName(String contentCatalogName) {
        this.contentCatalogName = contentCatalogName;
    }
    
    public String getContentCategoryName() {
        return contentCategoryName;
    }
    
    public void setContentCategoryName(String contentCategoryName) {
        this.contentCategoryName = contentCategoryName;
    }
    
    public String getDefaultSourceChoice() {
        return defaultSourceChoice;
    }
    
    public void setDefaultSourceChoice(String defaultSourceChoice) {
        this.defaultSourceChoice = defaultSourceChoice;
    }
    
    public String getContentCategoryItemSelectorChoice() {
        return contentCategoryContentCategoryItemSelectorChoice;
    }
    
    public void setContentCategoryItemSelectorChoice(String contentCategoryContentCategoryItemSelectorChoice) {
        this.contentCategoryContentCategoryItemSelectorChoice = contentCategoryContentCategoryItemSelectorChoice;
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
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getParentContentCategoryName() {
        return parentContentCategoryName;
    }
    
    public void setParentContentCategoryName(String parentContentCategoryName) {
        this.parentContentCategoryName = parentContentCategoryName;
    }
    
    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
        
        setIsDefault(false);
    }
    
}
