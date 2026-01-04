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
import com.echothree.control.user.training.common.edit.TrainingClassQuestionTranslationEdit;
import com.echothree.control.user.training.common.form.EditTrainingClassQuestionTranslationForm;
import com.echothree.control.user.training.common.result.EditTrainingClassQuestionTranslationResult;
import com.echothree.control.user.training.common.spec.TrainingClassQuestionTranslationSpec;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.MainBaseEditAction;
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
    path = "/HumanResources/TrainingClassQuestion/TranslationEdit",
    mappingClass = SecureActionMapping.class,
    name = "TrainingClassQuestionTranslationEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/HumanResources/TrainingClassQuestion/Translation", redirect = true),
        @SproutForward(name = "Form", path = "/humanresources/trainingclassquestion/translationEdit.jsp")
    }
)
public class TranslationEditAction
        extends MainBaseEditAction<TranslationEditActionForm, TrainingClassQuestionTranslationSpec, TrainingClassQuestionTranslationEdit, EditTrainingClassQuestionTranslationForm, EditTrainingClassQuestionTranslationResult> {
    
    @Override
    protected TrainingClassQuestionTranslationSpec getSpec(HttpServletRequest request, TranslationEditActionForm actionForm)
            throws NamingException {
        var spec = TrainingUtil.getHome().getTrainingClassQuestionTranslationSpec();
        
        spec.setTrainingClassName(findParameter(request, ParameterConstants.TRAINING_CLASS_NAME, actionForm.getTrainingClassName()));
        spec.setTrainingClassSectionName(findParameter(request, ParameterConstants.TRAINING_CLASS_SECTION_NAME, actionForm.getTrainingClassSectionName()));
        spec.setTrainingClassQuestionName(findParameter(request, ParameterConstants.TRAINING_CLASS_QUESTION_NAME, actionForm.getTrainingClassQuestionName()));
        spec.setLanguageIsoName(findParameter(request, ParameterConstants.LANGUAGE_ISO_NAME, actionForm.getLanguageIsoName()));
        
        return spec;
    }
    
    @Override
    protected TrainingClassQuestionTranslationEdit getEdit(HttpServletRequest request, TranslationEditActionForm actionForm)
            throws NamingException {
        var edit = TrainingUtil.getHome().getTrainingClassQuestionTranslationEdit();

        edit.setQuestionMimeTypeName(actionForm.getQuestionMimeTypeChoice());
        edit.setQuestion(actionForm.getQuestion());

        return edit;
    }
    
    @Override
    protected EditTrainingClassQuestionTranslationForm getForm()
            throws NamingException {
        return TrainingUtil.getHome().getEditTrainingClassQuestionTranslationForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, TranslationEditActionForm actionForm, EditTrainingClassQuestionTranslationResult result, TrainingClassQuestionTranslationSpec spec, TrainingClassQuestionTranslationEdit edit) {
        actionForm.setTrainingClassName(spec.getTrainingClassName());
        actionForm.setTrainingClassSectionName(spec.getTrainingClassSectionName());
        actionForm.setTrainingClassQuestionName(spec.getTrainingClassQuestionName());
        actionForm.setLanguageIsoName(spec.getLanguageIsoName());
        actionForm.setQuestionMimeTypeChoice(edit.getQuestionMimeTypeName());
        actionForm.setQuestion(edit.getQuestion());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditTrainingClassQuestionTranslationForm commandForm)
            throws Exception {
        return TrainingUtil.getHome().editTrainingClassQuestionTranslation(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(TranslationEditActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.TRAINING_CLASS_NAME, actionForm.getTrainingClassName());
        parameters.put(ParameterConstants.TRAINING_CLASS_SECTION_NAME, actionForm.getTrainingClassSectionName());
        parameters.put(ParameterConstants.TRAINING_CLASS_QUESTION_NAME, actionForm.getTrainingClassQuestionName());
    }
    
    @Override
    protected void setupTransferForForm(HttpServletRequest request, TranslationEditActionForm actionForm, EditTrainingClassQuestionTranslationResult result) {
        request.setAttribute(AttributeConstants.TRAINING_CLASS_QUESTION_TRANSLATION, result.getTrainingClassQuestionTranslation());
    }

}
