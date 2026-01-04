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
import com.echothree.control.user.training.common.edit.TrainingClassAnswerEdit;
import com.echothree.control.user.training.common.form.EditTrainingClassAnswerForm;
import com.echothree.control.user.training.common.result.EditTrainingClassAnswerResult;
import com.echothree.control.user.training.common.spec.TrainingClassAnswerSpec;
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
    path = "/HumanResources/TrainingClassAnswer/Edit",
    mappingClass = SecureActionMapping.class,
    name = "TrainingClassAnswerEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/HumanResources/TrainingClassAnswer/Main", redirect = true),
        @SproutForward(name = "Form", path = "/humanresources/trainingclassanswer/edit.jsp")
    }
)
public class EditAction
        extends MainBaseEditAction<EditActionForm, TrainingClassAnswerSpec, TrainingClassAnswerEdit, EditTrainingClassAnswerForm, EditTrainingClassAnswerResult> {
    
    @Override
    protected TrainingClassAnswerSpec getSpec(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var spec = TrainingUtil.getHome().getTrainingClassAnswerSpec();
        
        spec.setTrainingClassName(findParameter(request, ParameterConstants.TRAINING_CLASS_NAME, actionForm.getTrainingClassName()));
        spec.setTrainingClassSectionName(findParameter(request, ParameterConstants.TRAINING_CLASS_SECTION_NAME, actionForm.getTrainingClassSectionName()));
        spec.setTrainingClassQuestionName(findParameter(request, ParameterConstants.TRAINING_CLASS_QUESTION_NAME, actionForm.getTrainingClassQuestionName()));
        spec.setTrainingClassAnswerName(findParameter(request, ParameterConstants.ORIGINAL_TRAINING_CLASS_ANSWER_NAME, actionForm.getOriginalTrainingClassAnswerName()));
        
        return spec;
    }
    
    @Override
    protected TrainingClassAnswerEdit getEdit(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var edit = TrainingUtil.getHome().getTrainingClassAnswerEdit();

        edit.setTrainingClassAnswerName(actionForm.getTrainingClassAnswerName());
        edit.setIsCorrect(actionForm.getIsCorrect().toString());
        edit.setSortOrder(actionForm.getSortOrder());
        edit.setAnswerMimeTypeName(actionForm.getAnswerMimeTypeChoice());
        edit.setAnswer(actionForm.getAnswer());
        edit.setSelectedMimeTypeName(actionForm.getSelectedMimeTypeChoice());
        edit.setSelected(actionForm.getSelected());

        return edit;
    }
    
    @Override
    protected EditTrainingClassAnswerForm getForm()
            throws NamingException {
        return TrainingUtil.getHome().getEditTrainingClassAnswerForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, EditActionForm actionForm, EditTrainingClassAnswerResult result, TrainingClassAnswerSpec spec, TrainingClassAnswerEdit edit) {
        actionForm.setTrainingClassName(spec.getTrainingClassName());
        actionForm.setTrainingClassSectionName(spec.getTrainingClassSectionName());
        actionForm.setTrainingClassQuestionName(spec.getTrainingClassQuestionName());
        actionForm.setOriginalTrainingClassAnswerName(spec.getTrainingClassAnswerName());
        actionForm.setTrainingClassAnswerName(edit.getTrainingClassAnswerName());
        actionForm.setIsCorrect(Boolean.valueOf(edit.getIsCorrect()));
        actionForm.setSortOrder(edit.getSortOrder());
        actionForm.setAnswerMimeTypeChoice(edit.getAnswerMimeTypeName());
        actionForm.setAnswer(edit.getAnswer());
        actionForm.setSelectedMimeTypeChoice(edit.getSelectedMimeTypeName());
        actionForm.setSelected(edit.getSelected());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditTrainingClassAnswerForm commandForm)
            throws Exception {
        return TrainingUtil.getHome().editTrainingClassAnswer(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(EditActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.TRAINING_CLASS_NAME, actionForm.getTrainingClassName());
        parameters.put(ParameterConstants.TRAINING_CLASS_SECTION_NAME, actionForm.getTrainingClassSectionName());
        parameters.put(ParameterConstants.TRAINING_CLASS_QUESTION_NAME, actionForm.getTrainingClassQuestionName());
    }
    
    @Override
    protected void setupTransferForForm(HttpServletRequest request, EditActionForm actionForm, EditTrainingClassAnswerResult result) {
        request.setAttribute(AttributeConstants.TRAINING_CLASS_ANSWER, result.getTrainingClassAnswer());
    }

}
