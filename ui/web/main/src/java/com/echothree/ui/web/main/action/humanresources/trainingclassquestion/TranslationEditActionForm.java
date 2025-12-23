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

package com.echothree.ui.web.main.action.humanresources.trainingclassquestion;

import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.control.user.core.common.result.GetMimeTypeChoicesResult;
import com.echothree.model.control.core.common.MimeTypeUsageTypes;
import com.echothree.model.control.core.common.choice.MimeTypeChoicesBean;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="TrainingClassQuestionTranslationEdit")
public class TranslationEditActionForm
        extends BaseActionForm {
    
    private MimeTypeChoicesBean questionMimeTypeChoices;

    private String trainingClassName;
    private String trainingClassSectionName;
    private String trainingClassQuestionName;
    private String languageIsoName;
    private String questionMimeTypeChoice;
    private String question;
    
     private void setupQuestionMimeTypeChoices()
             throws NamingException {
        if(questionMimeTypeChoices == null) {
            var commandForm = CoreUtil.getHome().getGetMimeTypeChoicesForm();

            commandForm.setDefaultMimeTypeChoice(questionMimeTypeChoice);
            commandForm.setAllowNullChoice(String.valueOf(false));
            commandForm.setMimeTypeUsageTypeName(MimeTypeUsageTypes.TEXT.name());

            var commandResult = CoreUtil.getHome().getMimeTypeChoices(userVisitPK, commandForm);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetMimeTypeChoicesResult)executionResult.getResult();
            questionMimeTypeChoices = result.getMimeTypeChoices();

            if(questionMimeTypeChoice == null) {
                questionMimeTypeChoice = questionMimeTypeChoices.getDefaultValue();
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

    public void setTrainingClassQuestionName(String trainingClassQuestionName) {
        this.trainingClassQuestionName = trainingClassQuestionName;
    }
    
    public String getTrainingClassQuestionName() {
        return trainingClassQuestionName;
    }
    
    public String getLanguageIsoName() {
        return languageIsoName;
    }
    
    public void setLanguageIsoName(String languageIsoName) {
        this.languageIsoName = languageIsoName;
    }
    
    public List<LabelValueBean> getQuestionMimeTypeChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupQuestionMimeTypeChoices();
        if(questionMimeTypeChoices != null) {
            choices = convertChoices(questionMimeTypeChoices);
        }
        
        return choices;
    }
    
    public void setQuestionMimeTypeChoice(String questionMimeTypeChoice) {
        this.questionMimeTypeChoice = questionMimeTypeChoice;
    }
    
    public String getQuestionMimeTypeChoice()
            throws NamingException {
        setupQuestionMimeTypeChoices();
        
        return questionMimeTypeChoice;
    }
    
    public String getQuestion() {
        return question;
    }
    
    public void setQuestion(String question) {
        this.question = question;
    }
    
}
