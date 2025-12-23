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
import com.echothree.control.user.training.common.edit.TrainingClassSectionTranslationEdit;
import com.echothree.control.user.training.common.form.EditTrainingClassSectionTranslationForm;
import com.echothree.control.user.training.common.result.EditTrainingClassSectionTranslationResult;
import com.echothree.control.user.training.common.spec.TrainingClassSectionTranslationSpec;
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
    path = "/HumanResources/TrainingClassSection/TranslationEdit",
    mappingClass = SecureActionMapping.class,
    name = "TrainingClassSectionTranslationEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/HumanResources/TrainingClassSection/Translation", redirect = true),
        @SproutForward(name = "Form", path = "/humanresources/trainingclasssection/translationEdit.jsp")
    }
)
public class TranslationEditAction
        extends MainBaseEditAction<TranslationEditActionForm, TrainingClassSectionTranslationSpec, TrainingClassSectionTranslationEdit, EditTrainingClassSectionTranslationForm, EditTrainingClassSectionTranslationResult> {
    
    @Override
    protected TrainingClassSectionTranslationSpec getSpec(HttpServletRequest request, TranslationEditActionForm actionForm)
            throws NamingException {
        var spec = TrainingUtil.getHome().getTrainingClassSectionTranslationSpec();
        
        spec.setTrainingClassName(findParameter(request, ParameterConstants.TRAINING_CLASS_NAME, actionForm.getTrainingClassName()));
        spec.setTrainingClassSectionName(findParameter(request, ParameterConstants.TRAINING_CLASS_SECTION_NAME, actionForm.getTrainingClassSectionName()));
        spec.setLanguageIsoName(findParameter(request, ParameterConstants.LANGUAGE_ISO_NAME, actionForm.getLanguageIsoName()));
        
        return spec;
    }
    
    @Override
    protected TrainingClassSectionTranslationEdit getEdit(HttpServletRequest request, TranslationEditActionForm actionForm)
            throws NamingException {
        var edit = TrainingUtil.getHome().getTrainingClassSectionTranslationEdit();

        edit.setDescription(actionForm.getDescription());
        edit.setOverviewMimeTypeName(actionForm.getOverviewMimeTypeChoice());
        edit.setOverview(actionForm.getOverview());
        edit.setIntroductionMimeTypeName(actionForm.getIntroductionMimeTypeChoice());
        edit.setIntroduction(actionForm.getIntroduction());

        return edit;
    }
    
    @Override
    protected EditTrainingClassSectionTranslationForm getForm()
            throws NamingException {
        return TrainingUtil.getHome().getEditTrainingClassSectionTranslationForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, TranslationEditActionForm actionForm, EditTrainingClassSectionTranslationResult result, TrainingClassSectionTranslationSpec spec, TrainingClassSectionTranslationEdit edit) {
        actionForm.setTrainingClassName(spec.getTrainingClassName());
        actionForm.setTrainingClassSectionName(spec.getTrainingClassSectionName());
        actionForm.setLanguageIsoName(spec.getLanguageIsoName());
        actionForm.setDescription(edit.getDescription());
        actionForm.setOverviewMimeTypeChoice(edit.getOverviewMimeTypeName());
        actionForm.setOverview(edit.getOverview());
        actionForm.setIntroductionMimeTypeChoice(edit.getIntroductionMimeTypeName());
        actionForm.setIntroduction(edit.getIntroduction());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditTrainingClassSectionTranslationForm commandForm)
            throws Exception {
        return TrainingUtil.getHome().editTrainingClassSectionTranslation(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(TranslationEditActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.TRAINING_CLASS_NAME, actionForm.getTrainingClassName());
        parameters.put(ParameterConstants.TRAINING_CLASS_SECTION_NAME, actionForm.getTrainingClassSectionName());
    }
    
    @Override
    protected void setupTransferForForm(HttpServletRequest request, TranslationEditActionForm actionForm, EditTrainingClassSectionTranslationResult result) {
        request.setAttribute(AttributeConstants.TRAINING_CLASS_SECTION_TRANSLATION, result.getTrainingClassSectionTranslation());
    }

}
