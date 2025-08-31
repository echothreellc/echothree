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

package com.echothree.ui.web.main.action.humanresources.trainingclasspage;

import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.control.user.core.common.result.GetMimeTypeChoicesResult;
import com.echothree.model.control.core.common.MimeTypeUsageTypes;
import com.echothree.model.control.core.common.choice.MimeTypeChoicesBean;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="TrainingClassPageTranslationEdit")
public class TranslationEditActionForm
        extends BaseActionForm {
    
    private MimeTypeChoicesBean pageMimeTypeChoices;
    
    private String trainingClassName;
    private String trainingClassSectionName;
    private String trainingClassPageName;
    private String languageIsoName;
    private String description;
    private String pageMimeTypeChoice;
    private String page;
    
     private void setupPageMimeTypeChoices() {
        if(pageMimeTypeChoices == null) {
            try {
                var commandForm = CoreUtil.getHome().getGetMimeTypeChoicesForm();
                
                commandForm.setDefaultMimeTypeChoice(pageMimeTypeChoice);
                commandForm.setAllowNullChoice(String.valueOf(false));
                commandForm.setMimeTypeUsageTypeName(MimeTypeUsageTypes.TEXT.name());

                var commandResult = CoreUtil.getHome().getMimeTypeChoices(userVisitPK, commandForm);
                var executionResult = commandResult.getExecutionResult();
                var result = (GetMimeTypeChoicesResult)executionResult.getResult();
                pageMimeTypeChoices = result.getMimeTypeChoices();
                
                if(pageMimeTypeChoice == null) {
                    pageMimeTypeChoice = pageMimeTypeChoices.getDefaultValue();
                }
            } catch (NamingException ne) {
                // failed, pageMimeTypeChoices remains null, no default
            }
        }
    }
    
    public void setTrainingClassName(String trainingClassName) {
        this.trainingClassName = trainingClassName;
    }
    
    public String getTrainingClassName() {
        return trainingClassName;
    }
    
    public void setTrainingClassSectionName(String trainingClassSectionName) {
        this.trainingClassSectionName = trainingClassSectionName;
    }

    public String getTrainingClassSectionName() {
        return trainingClassSectionName;
    }

    public void setTrainingClassPageName(String trainingClassPageName) {
        this.trainingClassPageName = trainingClassPageName;
    }
    
    public String getTrainingClassPageName() {
        return trainingClassPageName;
    }
    
    public String getLanguageIsoName() {
        return languageIsoName;
    }
    
    public void setLanguageIsoName(String languageIsoName) {
        this.languageIsoName = languageIsoName;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
    
    public List<LabelValueBean> getPageMimeTypeChoices() {
        List<LabelValueBean> choices = null;
        
        setupPageMimeTypeChoices();
        if(pageMimeTypeChoices != null) {
            choices = convertChoices(pageMimeTypeChoices);
        }
        
        return choices;
    }
    
    public void setPageMimeTypeChoice(String pageMimeTypeChoice) {
        this.pageMimeTypeChoice = pageMimeTypeChoice;
    }
    
    public String getPageMimeTypeChoice() {
        setupPageMimeTypeChoices();
        
        return pageMimeTypeChoice;
    }
    
    public String getPage() {
        return page;
    }
    
    public void setPage(String page) {
        this.page = page;
    }
    
}
