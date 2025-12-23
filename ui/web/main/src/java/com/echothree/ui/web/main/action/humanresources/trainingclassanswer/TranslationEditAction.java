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
import com.echothree.control.user.training.common.edit.TrainingClassAnswerTranslationEdit;
import com.echothree.control.user.training.common.form.EditTrainingClassAnswerTranslationForm;
import com.echothree.control.user.training.common.result.EditTrainingClassAnswerTranslationResult;
import com.echothree.control.user.training.common.spec.TrainingClassAnswerTranslationSpec;
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
    path = "/HumanResources/TrainingClassAnswer/TranslationEdit",
    mappingClass = SecureActionMapping.class,
    name = "TrainingClassAnswerTranslationEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/HumanResources/TrainingClassAnswer/Translation", redirect = true),
        @SproutForward(name = "Form", path = "/humanresources/trainingclassanswer/translationEdit.jsp")
    }
)
public class TranslationEditAction
        extends MainBaseEditAction<TranslationEditActionForm, TrainingClassAnswerTranslationSpec, TrainingClassAnswerTranslationEdit, EditTrainingClassAnswerTranslationForm, EditTrainingClassAnswerTranslationResult> {
    
    @Override
    protected TrainingClassAnswerTranslationSpec getSpec(HttpServletRequest request, TranslationEditActionForm actionForm)
            throws NamingException {
        var spec = TrainingUtil.getHome().getTrainingClassAnswerTranslationSpec();
        
        spec.setTrainingClassName(findParameter(request, ParameterConstants.TRAINING_CLASS_NAME, actionForm.getTrainingClassName()));
        spec.setTrainingClassSectionName(findParameter(request, ParameterConstants.TRAINING_CLASS_SECTION_NAME, actionForm.getTrainingClassSectionName()));
        spec.setTrainingClassQuestionName(findParameter(request, ParameterConstants.TRAINING_CLASS_QUESTION_NAME, actionForm.getTrainingClassQuestionName()));
        spec.setTrainingClassAnswerName(findParameter(request, ParameterConstants.TRAINING_CLASS_ANSWER_NAME, actionForm.getTrainingClassAnswerName()));
        spec.setLanguageIsoName(findParameter(request, ParameterConstants.LANGUAGE_ISO_NAME, actionForm.getLanguageIsoName()));
        
        return spec;
    }
    
    @Override
    protected TrainingClassAnswerTranslationEdit getEdit(HttpServletRequest request, TranslationEditActionForm actionForm)
            throws NamingException {
        var edit = TrainingUtil.getHome().getTrainingClassAnswerTranslationEdit();

        edit.setAnswerMimeTypeName(actionForm.getAnswerMimeTypeChoice());
        edit.setAnswer(actionForm.getAnswer());
        edit.setSelectedMimeTypeName(actionForm.getSelectedMimeTypeChoice());
        edit.setSelected(actionForm.getSelected());

        return edit;
    }
    
    @Override
    protected EditTrainingClassAnswerTranslationForm getForm()
            throws NamingException {
        return TrainingUtil.getHome().getEditTrainingClassAnswerTranslationForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, TranslationEditActionForm actionForm, EditTrainingClassAnswerTranslationResult result, TrainingClassAnswerTranslationSpec spec, TrainingClassAnswerTranslationEdit edit) {
        actionForm.setTrainingClassName(spec.getTrainingClassName());
        actionForm.setTrainingClassSectionName(spec.getTrainingClassSectionName());
        actionForm.setTrainingClassQuestionName(spec.getTrainingClassQuestionName());
        actionForm.setTrainingClassAnswerName(spec.getTrainingClassAnswerName());
        actionForm.setLanguageIsoName(spec.getLanguageIsoName());
        actionForm.setAnswerMimeTypeChoice(edit.getAnswerMimeTypeName());
        actionForm.setAnswer(edit.getAnswer());
        actionForm.setSelectedMimeTypeChoice(edit.getSelectedMimeTypeName());
        actionForm.setSelected(edit.getSelected());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditTrainingClassAnswerTranslationForm commandForm)
            throws Exception {
        return TrainingUtil.getHome().editTrainingClassAnswerTranslation(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(TranslationEditActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.TRAINING_CLASS_NAME, actionForm.getTrainingClassName());
        parameters.put(ParameterConstants.TRAINING_CLASS_SECTION_NAME, actionForm.getTrainingClassSectionName());
        parameters.put(ParameterConstants.TRAINING_CLASS_QUESTION_NAME, actionForm.getTrainingClassQuestionName());
        parameters.put(ParameterConstants.TRAINING_CLASS_ANSWER_NAME, actionForm.getTrainingClassAnswerName());
    }
    
    @Override
    protected void setupTransferForForm(HttpServletRequest request, TranslationEditActionForm actionForm, EditTrainingClassAnswerTranslationResult result) {
        request.setAttribute(AttributeConstants.TRAINING_CLASS_ANSWER_TRANSLATION, result.getTrainingClassAnswerTranslation());
    }

}
