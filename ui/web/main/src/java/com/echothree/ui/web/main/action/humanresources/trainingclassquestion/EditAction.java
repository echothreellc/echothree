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
import com.echothree.control.user.training.common.edit.TrainingClassQuestionEdit;
import com.echothree.control.user.training.common.form.EditTrainingClassQuestionForm;
import com.echothree.control.user.training.common.result.EditTrainingClassQuestionResult;
import com.echothree.control.user.training.common.spec.TrainingClassQuestionSpec;
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
    path = "/HumanResources/TrainingClassQuestion/Edit",
    mappingClass = SecureActionMapping.class,
    name = "TrainingClassQuestionEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/HumanResources/TrainingClassQuestion/Main", redirect = true),
        @SproutForward(name = "Form", path = "/humanresources/trainingclassquestion/edit.jsp")
    }
)
public class EditAction
        extends MainBaseEditAction<EditActionForm, TrainingClassQuestionSpec, TrainingClassQuestionEdit, EditTrainingClassQuestionForm, EditTrainingClassQuestionResult> {
    
    @Override
    protected TrainingClassQuestionSpec getSpec(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var spec = TrainingUtil.getHome().getTrainingClassQuestionSpec();
        
        spec.setTrainingClassName(findParameter(request, ParameterConstants.TRAINING_CLASS_NAME, actionForm.getTrainingClassName()));
        spec.setTrainingClassSectionName(findParameter(request, ParameterConstants.TRAINING_CLASS_SECTION_NAME, actionForm.getTrainingClassSectionName()));
        spec.setTrainingClassQuestionName(findParameter(request, ParameterConstants.ORIGINAL_TRAINING_CLASS_QUESTION_NAME, actionForm.getOriginalTrainingClassQuestionName()));
        
        return spec;
    }
    
    @Override
    protected TrainingClassQuestionEdit getEdit(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var edit = TrainingUtil.getHome().getTrainingClassQuestionEdit();

        edit.setTrainingClassQuestionName(actionForm.getTrainingClassQuestionName());
        edit.setAskingRequired(actionForm.getAskingRequired().toString());
        edit.setPassingRequired(actionForm.getPassingRequired().toString());
        edit.setSortOrder(actionForm.getSortOrder());
        edit.setQuestionMimeTypeName(actionForm.getQuestionMimeTypeChoice());
        edit.setQuestion(actionForm.getQuestion());

        return edit;
    }
    
    @Override
    protected EditTrainingClassQuestionForm getForm()
            throws NamingException {
        return TrainingUtil.getHome().getEditTrainingClassQuestionForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, EditActionForm actionForm, EditTrainingClassQuestionResult result, TrainingClassQuestionSpec spec, TrainingClassQuestionEdit edit) {
        actionForm.setTrainingClassName(spec.getTrainingClassName());
        actionForm.setTrainingClassSectionName(spec.getTrainingClassSectionName());
        actionForm.setOriginalTrainingClassQuestionName(spec.getTrainingClassQuestionName());
        actionForm.setTrainingClassQuestionName(edit.getTrainingClassQuestionName());
        actionForm.setAskingRequired(Boolean.valueOf(edit.getAskingRequired()));
        actionForm.setPassingRequired(Boolean.valueOf(edit.getPassingRequired()));
        actionForm.setSortOrder(edit.getSortOrder());
        actionForm.setQuestionMimeTypeChoice(edit.getQuestionMimeTypeName());
        actionForm.setQuestion(edit.getQuestion());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditTrainingClassQuestionForm commandForm)
            throws Exception {
        return TrainingUtil.getHome().editTrainingClassQuestion(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(EditActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.TRAINING_CLASS_NAME, actionForm.getTrainingClassName());
        parameters.put(ParameterConstants.TRAINING_CLASS_SECTION_NAME, actionForm.getTrainingClassSectionName());
    }
    
    @Override
    protected void setupTransferForForm(HttpServletRequest request, EditActionForm actionForm, EditTrainingClassQuestionResult result) {
        request.setAttribute(AttributeConstants.TRAINING_CLASS_QUESTION, result.getTrainingClassQuestion());
    }

}
