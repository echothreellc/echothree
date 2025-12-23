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

import com.echothree.control.user.training.common.TrainingUtil;
import com.echothree.control.user.training.common.result.GetTrainingClassQuestionResult;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.MainBaseAddAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.CommandResult;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

@SproutAction(
    path = "/HumanResources/TrainingClassQuestion/TranslationAdd",
    mappingClass = SecureActionMapping.class,
    name = "TrainingClassQuestionTranslationAdd",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/HumanResources/TrainingClassQuestion/Translation", redirect = true),
        @SproutForward(name = "Form", path = "/humanresources/trainingclassquestion/translationAdd.jsp")
    }
)
public class TranslationAddAction
        extends MainBaseAddAction<TranslationAddActionForm> {

    @Override
    public void setupParameters(TranslationAddActionForm actionForm, HttpServletRequest request) {
        actionForm.setTrainingClassName(findParameter(request, ParameterConstants.TRAINING_CLASS_NAME, actionForm.getTrainingClassName()));
        actionForm.setTrainingClassSectionName(findParameter(request, ParameterConstants.TRAINING_CLASS_SECTION_NAME, actionForm.getTrainingClassSectionName()));
        actionForm.setTrainingClassQuestionName(findParameter(request, ParameterConstants.TRAINING_CLASS_QUESTION_NAME, actionForm.getTrainingClassQuestionName()));
    }
    
    @Override
    public void setupTransfer(TranslationAddActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = TrainingUtil.getHome().getGetTrainingClassQuestionForm();

        commandForm.setTrainingClassName(actionForm.getTrainingClassName());
        commandForm.setTrainingClassSectionName(actionForm.getTrainingClassSectionName());
        commandForm.setTrainingClassQuestionName(actionForm.getTrainingClassQuestionName());

        var commandResult = TrainingUtil.getHome().getTrainingClassQuestion(getUserVisitPK(request), commandForm);
        if(!commandResult.hasErrors()) {
            var executionResult = commandResult.getExecutionResult();
            var result = (GetTrainingClassQuestionResult)executionResult.getResult();
            
            request.setAttribute(AttributeConstants.TRAINING_CLASS_QUESTION, result.getTrainingClassQuestion());
        }
    }
    
    @Override
    public CommandResult doAdd(TranslationAddActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = TrainingUtil.getHome().getCreateTrainingClassQuestionTranslationForm();

        commandForm.setTrainingClassName(actionForm.getTrainingClassName());
        commandForm.setTrainingClassSectionName(actionForm.getTrainingClassSectionName());
        commandForm.setTrainingClassQuestionName(actionForm.getTrainingClassQuestionName());
        commandForm.setLanguageIsoName(actionForm.getLanguageChoice());
        commandForm.setQuestionMimeTypeName(actionForm.getQuestionMimeTypeChoice());
        commandForm.setQuestion(actionForm.getQuestion());

        return TrainingUtil.getHome().createTrainingClassQuestionTranslation(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(TranslationAddActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.TRAINING_CLASS_NAME, actionForm.getTrainingClassName());
        parameters.put(ParameterConstants.TRAINING_CLASS_SECTION_NAME, actionForm.getTrainingClassSectionName());
        parameters.put(ParameterConstants.TRAINING_CLASS_QUESTION_NAME, actionForm.getTrainingClassQuestionName());
    }
    
}
