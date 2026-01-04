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
import com.echothree.control.user.core.common.edit.ApplicationEditorUseEdit;
import com.echothree.control.user.core.common.form.EditApplicationEditorUseForm;
import com.echothree.control.user.core.common.result.EditApplicationEditorUseResult;
import com.echothree.control.user.core.common.spec.ApplicationEditorUseSpec;
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
    path = "/Core/ApplicationEditorUse/Edit",
    mappingClass = SecureActionMapping.class,
    name = "ApplicationEditorUseEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Core/ApplicationEditorUse/Main", redirect = true),
        @SproutForward(name = "Form", path = "/core/applicationeditoruse/edit.jsp")
    }
)
public class EditAction
        extends MainBaseEditAction<EditActionForm, ApplicationEditorUseSpec, ApplicationEditorUseEdit, EditApplicationEditorUseForm, EditApplicationEditorUseResult> {
    
    @Override
    protected ApplicationEditorUseSpec getSpec(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var spec = CoreUtil.getHome().getApplicationEditorUseSpec();
        
        spec.setApplicationName(findParameter(request, ParameterConstants.APPLICATION_NAME, actionForm.getApplicationName()));
        spec.setApplicationEditorUseName(findParameter(request, ParameterConstants.ORIGINAL_APPLICATION_EDITOR_USE_NAME, actionForm.getOriginalApplicationEditorUseName()));
        
        return spec;
    }
    
    @Override
    protected ApplicationEditorUseEdit getEdit(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var edit = CoreUtil.getHome().getApplicationEditorUseEdit();

        edit.setApplicationEditorUseName(actionForm.getApplicationEditorUseName());
        edit.setDefaultEditorName(actionForm.getDefaultEditorChoice());
        edit.setDefaultHeight(actionForm.getDefaultHeight());
        edit.setDefaultWidth(actionForm.getDefaultWidth());
        edit.setIsDefault(actionForm.getIsDefault().toString());
        edit.setSortOrder(actionForm.getSortOrder());
        edit.setDescription(actionForm.getDescription());

        return edit;
    }
    
    @Override
    protected EditApplicationEditorUseForm getForm()
            throws NamingException {
        return CoreUtil.getHome().getEditApplicationEditorUseForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, EditActionForm actionForm, EditApplicationEditorUseResult result, ApplicationEditorUseSpec spec, ApplicationEditorUseEdit edit) {
        actionForm.setApplicationName(spec.getApplicationName());
        actionForm.setOriginalApplicationEditorUseName(spec.getApplicationEditorUseName());
        actionForm.setApplicationEditorUseName(edit.getApplicationEditorUseName());
        actionForm.setDefaultEditorChoice(edit.getDefaultEditorName());
        actionForm.setDefaultHeight(edit.getDefaultHeight());
        actionForm.setDefaultWidth(edit.getDefaultWidth());
        actionForm.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        actionForm.setSortOrder(edit.getSortOrder());
        actionForm.setDescription(edit.getDescription());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditApplicationEditorUseForm commandForm)
            throws Exception {
        return CoreUtil.getHome().editApplicationEditorUse(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(EditActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.APPLICATION_NAME, actionForm.getApplicationName());
    }
    
    @Override
    protected void setupTransferForForm(HttpServletRequest request, EditActionForm actionForm, EditApplicationEditorUseResult result) {
        request.setAttribute(AttributeConstants.APPLICATION_EDITOR_USE, result.getApplicationEditorUse());
    }

}
