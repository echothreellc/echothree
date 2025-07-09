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

package com.echothree.ui.web.main.action.content.contentpage;

import com.echothree.control.user.content.common.ContentUtil;
import com.echothree.control.user.content.common.result.GetContentPageLayoutChoicesResult;
import com.echothree.model.control.content.common.choice.ContentPageLayoutChoicesBean;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="ContentPageAdd")
public class AddActionForm
        extends BaseActionForm {
    
    private ContentPageLayoutChoicesBean contentPageLayoutChoices;
    
    private String contentCollectionName;
    private String contentSectionName;
    private String contentPageName;
    private String contentPageLayoutChoice;
    private Boolean isDefault;
    private String sortOrder;
    private String description;
    private String parentContentSectionName;
    
    private void setupContentPageLayoutChoices()
            throws NamingException {
        if(contentPageLayoutChoices == null) {
            var getContentPageLayoutChoicesForm = ContentUtil.getHome().getGetContentPageLayoutChoicesForm();

            getContentPageLayoutChoicesForm.setDefaultContentPageLayoutChoice(contentPageLayoutChoice);

            var commandResult = ContentUtil.getHome().getContentPageLayoutChoices(userVisitPK, getContentPageLayoutChoicesForm);
            var executionResult = commandResult.getExecutionResult();
            var getContentPageLayoutChoicesResult = (GetContentPageLayoutChoicesResult)executionResult.getResult();
            contentPageLayoutChoices = getContentPageLayoutChoicesResult.getContentPageLayoutChoices();

            if(contentPageLayoutChoice == null)
                contentPageLayoutChoice = contentPageLayoutChoices.getDefaultValue();
        }
    }
    
    public List<LabelValueBean> getContentPageLayoutChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupContentPageLayoutChoices();
        if(contentPageLayoutChoices != null)
            choices = convertChoices(contentPageLayoutChoices);
        
        return choices;
    }
    
    public String getContentCollectionName() {
        return contentCollectionName;
    }
    
    public void setContentCollectionName(String contentCollectionName) {
        this.contentCollectionName = contentCollectionName;
    }
    
    public String getContentSectionName() {
        return contentSectionName;
    }
    
    public void setContentSectionName(String contentSectionName) {
        this.contentSectionName = contentSectionName;
    }
    
    public String getContentPageName() {
        return contentPageName;
    }
    
    public void setContentPageName(String contentPageName) {
        this.contentPageName = contentPageName;
    }
    
    public void setContentPageLayoutChoice(String contentPageLayoutChoice) {
        this.contentPageLayoutChoice = contentPageLayoutChoice;
    }
    
    public String getContentPageLayoutChoice()
            throws NamingException {
        setupContentPageLayoutChoices();
        return contentPageLayoutChoice;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
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
    
    public String getParentContentSectionName() {
        return parentContentSectionName;
    }
    
    public void setParentContentSectionName(String parentContentSectionName) {
        this.parentContentSectionName = parentContentSectionName;
    }
    
    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
        isDefault = false;
    }
    
}
