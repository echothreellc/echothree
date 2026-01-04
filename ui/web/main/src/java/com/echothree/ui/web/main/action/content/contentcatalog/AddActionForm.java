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

package com.echothree.ui.web.main.action.content.contentcatalog;

import com.echothree.control.user.offer.common.OfferUtil;
import com.echothree.control.user.offer.common.result.GetSourceChoicesResult;
import com.echothree.model.control.offer.common.choice.SourceChoicesBean;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="ContentCatalogAdd")
public class AddActionForm
        extends BaseActionForm {
    
    private SourceChoicesBean defaultSourceChoices;
    
    private String contentCollectionName;
    private String contentCatalogName;
    private String defaultSourceChoice;
    private Boolean isDefault;
    private String sortOrder;
    private String description;
    
    private void setupDefaultSourceChoices()
            throws NamingException {
        if(defaultSourceChoices == null) {
            var getSourceChoicesForm = OfferUtil.getHome().getGetSourceChoicesForm();

            getSourceChoicesForm.setDefaultSourceChoice(getDefaultSourceChoice());
            getSourceChoicesForm.setAllowNullChoice(String.valueOf(false));

            var commandResult = OfferUtil.getHome().getSourceChoices(userVisitPK, getSourceChoicesForm);
            var executionResult = commandResult.getExecutionResult();
            var getSourceChoicesResult = (GetSourceChoicesResult)executionResult.getResult();
            defaultSourceChoices = getSourceChoicesResult.getSourceChoices();

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
    
    public String getDefaultSourceChoice() {
        return defaultSourceChoice;
    }
    
    public void setDefaultSourceChoice(String defaultSourceChoice) {
        this.defaultSourceChoice = defaultSourceChoice;
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
    
    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
        setIsDefault(false);
    }
    
}
