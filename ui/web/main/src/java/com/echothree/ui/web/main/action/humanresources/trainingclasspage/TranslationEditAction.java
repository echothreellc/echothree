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

package com.echothree.ui.web.main.action.humanresources.trainingclasspage;

import com.echothree.control.user.training.common.TrainingUtil;
import com.echothree.control.user.training.common.edit.TrainingClassPageTranslationEdit;
import com.echothree.control.user.training.common.form.EditTrainingClassPageTranslationForm;
import com.echothree.control.user.training.common.result.EditTrainingClassPageTranslationResult;
import com.echothree.control.user.training.common.spec.TrainingClassPageTranslationSpec;
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
    path = "/HumanResources/TrainingClassPage/TranslationEdit",
    mappingClass = SecureActionMapping.class,
    name = "TrainingClassPageTranslationEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/HumanResources/TrainingClassPage/Translation", redirect = true),
        @SproutForward(name = "Form", path = "/humanresources/trainingclasspage/translationEdit.jsp")
    }
)
public class TranslationEditAction
        extends MainBaseEditAction<TranslationEditActionForm, TrainingClassPageTranslationSpec, TrainingClassPageTranslationEdit, EditTrainingClassPageTranslationForm, EditTrainingClassPageTranslationResult> {
    
    @Override
    protected TrainingClassPageTranslationSpec getSpec(HttpServletRequest request, TranslationEditActionForm actionForm)
            throws NamingException {
        var spec = TrainingUtil.getHome().getTrainingClassPageTranslationSpec();
        
        spec.setTrainingClassName(findParameter(request, ParameterConstants.TRAINING_CLASS_NAME, actionForm.getTrainingClassName()));
        spec.setTrainingClassSectionName(findParameter(request, ParameterConstants.TRAINING_CLASS_SECTION_NAME, actionForm.getTrainingClassSectionName()));
        spec.setTrainingClassPageName(findParameter(request, ParameterConstants.TRAINING_CLASS_PAGE_NAME, actionForm.getTrainingClassPageName()));
        spec.setLanguageIsoName(findParameter(request, ParameterConstants.LANGUAGE_ISO_NAME, actionForm.getLanguageIsoName()));
        
        return spec;
    }
    
    @Override
    protected TrainingClassPageTranslationEdit getEdit(HttpServletRequest request, TranslationEditActionForm actionForm)
            throws NamingException {
        var edit = TrainingUtil.getHome().getTrainingClassPageTranslationEdit();

        edit.setDescription(actionForm.getDescription());
        edit.setPageMimeTypeName(actionForm.getPageMimeTypeChoice());
        edit.setPage(actionForm.getPage());

        return edit;
    }
    
    @Override
    protected EditTrainingClassPageTranslationForm getForm()
            throws NamingException {
        return TrainingUtil.getHome().getEditTrainingClassPageTranslationForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, TranslationEditActionForm actionForm, EditTrainingClassPageTranslationResult result, TrainingClassPageTranslationSpec spec, TrainingClassPageTranslationEdit edit) {
        actionForm.setTrainingClassName(spec.getTrainingClassName());
        actionForm.setTrainingClassSectionName(spec.getTrainingClassSectionName());
        actionForm.setTrainingClassPageName(spec.getTrainingClassPageName());
        actionForm.setLanguageIsoName(spec.getLanguageIsoName());
        actionForm.setDescription(edit.getDescription());
        actionForm.setPageMimeTypeChoice(edit.getPageMimeTypeName());
        actionForm.setPage(edit.getPage());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditTrainingClassPageTranslationForm commandForm)
            throws Exception {
        return TrainingUtil.getHome().editTrainingClassPageTranslation(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(TranslationEditActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.TRAINING_CLASS_NAME, actionForm.getTrainingClassName());
        parameters.put(ParameterConstants.TRAINING_CLASS_SECTION_NAME, actionForm.getTrainingClassSectionName());
        parameters.put(ParameterConstants.TRAINING_CLASS_PAGE_NAME, actionForm.getTrainingClassPageName());
    }
    
    @Override
    protected void setupTransferForForm(HttpServletRequest request, TranslationEditActionForm actionForm, EditTrainingClassPageTranslationResult result) {
        request.setAttribute(AttributeConstants.TRAINING_CLASS_PAGE_TRANSLATION, result.getTrainingClassPageTranslation());
    }

}
