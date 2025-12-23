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
import com.echothree.control.user.training.common.edit.TrainingClassPageEdit;
import com.echothree.control.user.training.common.form.EditTrainingClassPageForm;
import com.echothree.control.user.training.common.result.EditTrainingClassPageResult;
import com.echothree.control.user.training.common.spec.TrainingClassPageSpec;
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
    path = "/HumanResources/TrainingClassPage/Edit",
    mappingClass = SecureActionMapping.class,
    name = "TrainingClassPageEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/HumanResources/TrainingClassPage/Main", redirect = true),
        @SproutForward(name = "Form", path = "/humanresources/trainingclasspage/edit.jsp")
    }
)
public class EditAction
        extends MainBaseEditAction<EditActionForm, TrainingClassPageSpec, TrainingClassPageEdit, EditTrainingClassPageForm, EditTrainingClassPageResult> {
    
    @Override
    protected TrainingClassPageSpec getSpec(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var spec = TrainingUtil.getHome().getTrainingClassPageSpec();
        
        spec.setTrainingClassName(findParameter(request, ParameterConstants.TRAINING_CLASS_NAME, actionForm.getTrainingClassName()));
        spec.setTrainingClassSectionName(findParameter(request, ParameterConstants.TRAINING_CLASS_SECTION_NAME, actionForm.getTrainingClassSectionName()));
        spec.setTrainingClassPageName(findParameter(request, ParameterConstants.ORIGINAL_TRAINING_CLASS_PAGE_NAME, actionForm.getOriginalTrainingClassPageName()));
        
        return spec;
    }
    
    @Override
    protected TrainingClassPageEdit getEdit(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var edit = TrainingUtil.getHome().getTrainingClassPageEdit();

        edit.setTrainingClassPageName(actionForm.getTrainingClassPageName());
        edit.setSortOrder(actionForm.getSortOrder());
        edit.setDescription(actionForm.getDescription());
        edit.setPageMimeTypeName(actionForm.getPageMimeTypeChoice());
        edit.setPage(actionForm.getPage());

        return edit;
    }
    
    @Override
    protected EditTrainingClassPageForm getForm()
            throws NamingException {
        return TrainingUtil.getHome().getEditTrainingClassPageForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, EditActionForm actionForm, EditTrainingClassPageResult result, TrainingClassPageSpec spec, TrainingClassPageEdit edit) {
        actionForm.setTrainingClassName(spec.getTrainingClassName());
        actionForm.setTrainingClassSectionName(spec.getTrainingClassSectionName());
        actionForm.setOriginalTrainingClassPageName(spec.getTrainingClassPageName());
        actionForm.setTrainingClassPageName(edit.getTrainingClassPageName());
        actionForm.setSortOrder(edit.getSortOrder());
        actionForm.setDescription(edit.getDescription());
        actionForm.setPageMimeTypeChoice(edit.getPageMimeTypeName());
        actionForm.setPage(edit.getPage());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditTrainingClassPageForm commandForm)
            throws Exception {
        return TrainingUtil.getHome().editTrainingClassPage(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(EditActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.TRAINING_CLASS_NAME, actionForm.getTrainingClassName());
        parameters.put(ParameterConstants.TRAINING_CLASS_SECTION_NAME, actionForm.getTrainingClassSectionName());
    }
    
    @Override
    protected void setupTransferForForm(HttpServletRequest request, EditActionForm actionForm, EditTrainingClassPageResult result) {
        request.setAttribute(AttributeConstants.TRAINING_CLASS_PAGE, result.getTrainingClassPage());
    }

}
