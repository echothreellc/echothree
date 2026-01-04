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

package com.echothree.ui.web.main.action.core.applicationeditoruse;

import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.control.user.core.common.edit.ApplicationEditorUseDescriptionEdit;
import com.echothree.control.user.core.common.form.EditApplicationEditorUseDescriptionForm;
import com.echothree.control.user.core.common.result.EditApplicationEditorUseDescriptionResult;
import com.echothree.control.user.core.common.spec.ApplicationEditorUseDescriptionSpec;
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
    path = "/Core/ApplicationEditorUse/DescriptionEdit",
    mappingClass = SecureActionMapping.class,
    name = "ApplicationEditorUseDescriptionEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Core/ApplicationEditorUse/Description", redirect = true),
        @SproutForward(name = "Form", path = "/core/applicationeditoruse/descriptionEdit.jsp")
    }
)
public class DescriptionEditAction
        extends MainBaseEditAction<DescriptionEditActionForm, ApplicationEditorUseDescriptionSpec, ApplicationEditorUseDescriptionEdit, EditApplicationEditorUseDescriptionForm, EditApplicationEditorUseDescriptionResult> {
    
    @Override
    protected ApplicationEditorUseDescriptionSpec getSpec(HttpServletRequest request, DescriptionEditActionForm actionForm)
            throws NamingException {
        var spec = CoreUtil.getHome().getApplicationEditorUseDescriptionSpec();
        
        spec.setApplicationName(findParameter(request, ParameterConstants.APPLICATION_NAME, actionForm.getApplicationName()));
        spec.setApplicationEditorUseName(findParameter(request, ParameterConstants.APPLICATION_EDITOR_USE_NAME, actionForm.getApplicationEditorUseName()));
        spec.setLanguageIsoName(findParameter(request, ParameterConstants.LANGUAGE_ISO_NAME, actionForm.getLanguageIsoName()));
        
        return spec;
    }
    
    @Override
    protected ApplicationEditorUseDescriptionEdit getEdit(HttpServletRequest request, DescriptionEditActionForm actionForm)
            throws NamingException {
        var edit = CoreUtil.getHome().getApplicationEditorUseDescriptionEdit();

        edit.setDescription(actionForm.getDescription());

        return edit;
    }
    
    @Override
    protected EditApplicationEditorUseDescriptionForm getForm()
            throws NamingException {
        return CoreUtil.getHome().getEditApplicationEditorUseDescriptionForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, DescriptionEditActionForm actionForm, EditApplicationEditorUseDescriptionResult result, ApplicationEditorUseDescriptionSpec spec, ApplicationEditorUseDescriptionEdit edit) {
        actionForm.setApplicationName(spec.getApplicationName());
        actionForm.setApplicationEditorUseName(spec.getApplicationEditorUseName());
        actionForm.setLanguageIsoName(spec.getLanguageIsoName());
        actionForm.setDescription(edit.getDescription());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditApplicationEditorUseDescriptionForm commandForm)
            throws Exception {
        return CoreUtil.getHome().editApplicationEditorUseDescription(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(DescriptionEditActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.APPLICATION_NAME, actionForm.getApplicationName());
        parameters.put(ParameterConstants.APPLICATION_EDITOR_USE_NAME, actionForm.getApplicationEditorUseName());
    }
    
    @Override
    protected void setupTransferForForm(HttpServletRequest request, DescriptionEditActionForm actionForm, EditApplicationEditorUseDescriptionResult result) {
        request.setAttribute(AttributeConstants.APPLICATION_EDITOR_USE_DESCRIPTION, result.getApplicationEditorUseDescription());
    }

}
