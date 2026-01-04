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

package com.echothree.ui.web.main.action.humanresources.trainingclasssection;

import com.echothree.control.user.training.common.TrainingUtil;
import com.echothree.control.user.training.common.edit.TrainingClassSectionEdit;
import com.echothree.control.user.training.common.form.EditTrainingClassSectionForm;
import com.echothree.control.user.training.common.result.EditTrainingClassSectionResult;
import com.echothree.control.user.training.common.spec.TrainingClassSectionSpec;
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
    path = "/HumanResources/TrainingClassSection/Edit",
    mappingClass = SecureActionMapping.class,
    name = "TrainingClassSectionEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/HumanResources/TrainingClassSection/Main", redirect = true),
        @SproutForward(name = "Form", path = "/humanresources/trainingclasssection/edit.jsp")
    }
)
public class EditAction
        extends MainBaseEditAction<EditActionForm, TrainingClassSectionSpec, TrainingClassSectionEdit, EditTrainingClassSectionForm, EditTrainingClassSectionResult> {
    
    @Override
    protected TrainingClassSectionSpec getSpec(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var spec = TrainingUtil.getHome().getTrainingClassSectionSpec();
        
        spec.setTrainingClassName(findParameter(request, ParameterConstants.TRAINING_CLASS_NAME, actionForm.getTrainingClassName()));
        spec.setTrainingClassSectionName(findParameter(request, ParameterConstants.ORIGINAL_TRAINING_CLASS_SECTION_NAME, actionForm.getOriginalTrainingClassSectionName()));
        
        return spec;
    }
    
    @Override
    protected TrainingClassSectionEdit getEdit(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var edit = TrainingUtil.getHome().getTrainingClassSectionEdit();

        edit.setTrainingClassSectionName(actionForm.getTrainingClassSectionName());
        edit.setPercentageToPass(actionForm.getPercentageToPass());
        edit.setQuestionCount(actionForm.getQuestionCount());
        edit.setSortOrder(actionForm.getSortOrder());
        edit.setDescription(actionForm.getDescription());
        edit.setOverviewMimeTypeName(actionForm.getOverviewMimeTypeChoice());
        edit.setOverview(actionForm.getOverview());
        edit.setIntroductionMimeTypeName(actionForm.getIntroductionMimeTypeChoice());
        edit.setIntroduction(actionForm.getIntroduction());

        return edit;
    }
    
    @Override
    protected EditTrainingClassSectionForm getForm()
            throws NamingException {
        return TrainingUtil.getHome().getEditTrainingClassSectionForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, EditActionForm actionForm, EditTrainingClassSectionResult result, TrainingClassSectionSpec spec, TrainingClassSectionEdit edit) {
        actionForm.setTrainingClassName(spec.getTrainingClassName());
        actionForm.setOriginalTrainingClassSectionName(spec.getTrainingClassSectionName());
        actionForm.setTrainingClassSectionName(edit.getTrainingClassSectionName());
        actionForm.setPercentageToPass(edit.getPercentageToPass());
        actionForm.setQuestionCount(edit.getQuestionCount());
        actionForm.setSortOrder(edit.getSortOrder());
        actionForm.setDescription(edit.getDescription());
        actionForm.setOverviewMimeTypeChoice(edit.getOverviewMimeTypeName());
        actionForm.setOverview(edit.getOverview());
        actionForm.setIntroductionMimeTypeChoice(edit.getIntroductionMimeTypeName());
        actionForm.setIntroduction(edit.getIntroduction());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditTrainingClassSectionForm commandForm)
            throws Exception {
        return TrainingUtil.getHome().editTrainingClassSection(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(EditActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.TRAINING_CLASS_NAME, actionForm.getTrainingClassName());
    }
    
    @Override
    protected void setupTransferForForm(HttpServletRequest request, EditActionForm actionForm, EditTrainingClassSectionResult result) {
        request.setAttribute(AttributeConstants.TRAINING_CLASS_SECTION, result.getTrainingClassSection());
    }

}
