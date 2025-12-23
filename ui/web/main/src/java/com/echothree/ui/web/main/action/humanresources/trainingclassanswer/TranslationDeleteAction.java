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

package com.echothree.ui.web.main.action.humanresources.trainingclassanswer;

import com.echothree.control.user.training.common.TrainingUtil;
import com.echothree.control.user.training.common.result.GetTrainingClassAnswerTranslationResult;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.MainBaseDeleteAction;
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
    path = "/HumanResources/TrainingClassAnswer/TranslationDelete",
    mappingClass = SecureActionMapping.class,
    name = "TrainingClassAnswerTranslationDelete",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/HumanResources/TrainingClassAnswer/Translation", redirect = true),
        @SproutForward(name = "Form", path = "/humanresources/trainingclassanswer/translationDelete.jsp")
    }
)
public class TranslationDeleteAction
        extends MainBaseDeleteAction<TranslationDeleteActionForm> {

    @Override
    public String getEntityTypeName() {
        return EntityTypes.TrainingClassAnswerTranslation.name();
    }
    
    @Override
    public void setupParameters(TranslationDeleteActionForm actionForm, HttpServletRequest request) {
        actionForm.setTrainingClassName(findParameter(request, ParameterConstants.TRAINING_CLASS_NAME, actionForm.getTrainingClassName()));
        actionForm.setTrainingClassSectionName(findParameter(request, ParameterConstants.TRAINING_CLASS_SECTION_NAME, actionForm.getTrainingClassSectionName()));
        actionForm.setTrainingClassQuestionName(findParameter(request, ParameterConstants.TRAINING_CLASS_QUESTION_NAME, actionForm.getTrainingClassQuestionName()));
        actionForm.setTrainingClassAnswerName(findParameter(request, ParameterConstants.TRAINING_CLASS_ANSWER_NAME, actionForm.getTrainingClassAnswerName()));
        actionForm.setLanguageIsoName(findParameter(request, ParameterConstants.LANGUAGE_ISO_NAME, actionForm.getLanguageIsoName()));
    }
    
    @Override
    public void setupTransfer(TranslationDeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = TrainingUtil.getHome().getGetTrainingClassAnswerTranslationForm();
        
        commandForm.setTrainingClassName(actionForm.getTrainingClassName());
        commandForm.setTrainingClassSectionName(actionForm.getTrainingClassSectionName());
        commandForm.setTrainingClassQuestionName(actionForm.getTrainingClassQuestionName());
        commandForm.setTrainingClassAnswerName(actionForm.getTrainingClassAnswerName());
        commandForm.setLanguageIsoName(actionForm.getLanguageIsoName());

        var commandResult = TrainingUtil.getHome().getTrainingClassAnswerTranslation(getUserVisitPK(request), commandForm);
        if(!commandResult.hasErrors()) {
            var executionResult = commandResult.getExecutionResult();
            var result = (GetTrainingClassAnswerTranslationResult)executionResult.getResult();

            request.setAttribute(AttributeConstants.TRAINING_CLASS_ANSWER_TRANSLATION, result.getTrainingClassAnswerTranslation());
        }
    }
    
    @Override
    public CommandResult doDelete(TranslationDeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = TrainingUtil.getHome().getDeleteTrainingClassAnswerTranslationForm();

        commandForm.setTrainingClassName(actionForm.getTrainingClassName());
        commandForm.setTrainingClassSectionName(actionForm.getTrainingClassSectionName());
        commandForm.setTrainingClassQuestionName(actionForm.getTrainingClassQuestionName());
        commandForm.setTrainingClassAnswerName(actionForm.getTrainingClassAnswerName());
        commandForm.setLanguageIsoName(actionForm.getLanguageIsoName());

        return TrainingUtil.getHome().deleteTrainingClassAnswerTranslation(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(TranslationDeleteActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.TRAINING_CLASS_NAME, actionForm.getTrainingClassName());
        parameters.put(ParameterConstants.TRAINING_CLASS_SECTION_NAME, actionForm.getTrainingClassSectionName());
        parameters.put(ParameterConstants.TRAINING_CLASS_QUESTION_NAME, actionForm.getTrainingClassQuestionName());
        parameters.put(ParameterConstants.TRAINING_CLASS_ANSWER_NAME, actionForm.getTrainingClassAnswerName());
    }
    
}
