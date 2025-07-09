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

package com.echothree.ui.web.main.action.core.applicationeditoruse;

import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.control.user.core.common.result.GetApplicationEditorChoicesResult;
import com.echothree.model.control.core.common.choice.ApplicationEditorChoicesBean;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="ApplicationEditorUseAdd")
public class AddActionForm
        extends BaseActionForm {
    
    private ApplicationEditorChoicesBean defaultApplicationEditorChoices;
    
    private String applicationName;
    private String applicationEditorUseName;
    private String defaultEditorChoice;
    private String defaultHeight;
    private String defaultWidth;
    private Boolean isDefault;
    private String sortOrder;
    private String description;
    
    private void setupDefaultApplicationEditorChoices()
            throws NamingException {
        if(defaultApplicationEditorChoices == null) {
            var commandForm = CoreUtil.getHome().getGetApplicationEditorChoicesForm();

            commandForm.setApplicationName(applicationName);
            commandForm.setDefaultEditorChoice(defaultEditorChoice);
            commandForm.setAllowNullChoice(String.valueOf(true));

            var commandResult = CoreUtil.getHome().getApplicationEditorChoices(userVisitPK, commandForm);
            var executionResult = commandResult.getExecutionResult();
            var getApplicationEditorChoicesResult = (GetApplicationEditorChoicesResult)executionResult.getResult();
            defaultApplicationEditorChoices = getApplicationEditorChoicesResult.getApplicationEditorChoices();

            if(defaultEditorChoice == null) {
                defaultEditorChoice = defaultApplicationEditorChoices.getDefaultValue();
            }
        }
    }
    
    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationEditorUseName(String applicationEditorUseName) {
        this.applicationEditorUseName = applicationEditorUseName;
    }

    public String getApplicationEditorUseName() {
        return applicationEditorUseName;
    }

    public List<LabelValueBean> getDefaultApplicationEditorChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupDefaultApplicationEditorChoices();
        if(defaultApplicationEditorChoices != null) {
            choices = convertChoices(defaultApplicationEditorChoices);
        }
        
        return choices;
    }
    
    public void setDefaultEditorChoice(String defaultEditorChoice) {
        this.defaultEditorChoice = defaultEditorChoice;
    }
    
    public String getDefaultEditorChoice()
            throws NamingException {
        setupDefaultApplicationEditorChoices();
        return defaultEditorChoice;
    }
    
    public String getDefaultHeight() {
        return defaultHeight;
    }

    public void setDefaultHeight(String defaultHeight) {
        this.defaultHeight = defaultHeight;
    }

    public String getDefaultWidth() {
        return defaultWidth;
    }

    public void setDefaultWidth(String defaultWidth) {
        this.defaultWidth = defaultWidth;
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
