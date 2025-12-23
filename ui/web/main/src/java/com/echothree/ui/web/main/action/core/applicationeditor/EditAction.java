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

package com.echothree.ui.web.main.action.core.applicationeditor;

import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.control.user.core.common.edit.ApplicationEditorEdit;
import com.echothree.control.user.core.common.form.EditApplicationEditorForm;
import com.echothree.control.user.core.common.result.EditApplicationEditorResult;
import com.echothree.control.user.core.common.spec.ApplicationEditorSpec;
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
    path = "/Core/ApplicationEditor/Edit",
    mappingClass = SecureActionMapping.class,
    name = "ApplicationEditorEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Core/ApplicationEditor/Main", redirect = true),
        @SproutForward(name = "Form", path = "/core/applicationeditor/edit.jsp")
    }
)
public class EditAction
        extends MainBaseEditAction<EditActionForm, ApplicationEditorSpec, ApplicationEditorEdit, EditApplicationEditorForm, EditApplicationEditorResult> {
    
    @Override
    protected ApplicationEditorSpec getSpec(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var spec = CoreUtil.getHome().getApplicationEditorSpec();
        
        spec.setApplicationName(findParameter(request, ParameterConstants.APPLICATION_NAME, actionForm.getApplicationName()));
        spec.setEditorName(findParameter(request, ParameterConstants.EDITOR_NAME, actionForm.getEditorName()));
        
        return spec;
    }
    
    @Override
    protected ApplicationEditorEdit getEdit(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var edit = CoreUtil.getHome().getApplicationEditorEdit();

        edit.setIsDefault(actionForm.getIsDefault().toString());
        edit.setSortOrder(actionForm.getSortOrder());

        return edit;
    }
    
    @Override
    protected EditApplicationEditorForm getForm()
            throws NamingException {
        return CoreUtil.getHome().getEditApplicationEditorForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, EditActionForm actionForm, EditApplicationEditorResult result, ApplicationEditorSpec spec, ApplicationEditorEdit edit) {
        actionForm.setApplicationName(spec.getApplicationName());
        actionForm.setEditorName(spec.getEditorName());
        actionForm.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        actionForm.setSortOrder(edit.getSortOrder());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditApplicationEditorForm commandForm)
            throws Exception {
        return CoreUtil.getHome().editApplicationEditor(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(EditActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.APPLICATION_NAME, actionForm.getApplicationName());
    }
    
    @Override
    protected void setupTransferForForm(HttpServletRequest request, EditActionForm actionForm, EditApplicationEditorResult result) {
        request.setAttribute(AttributeConstants.APPLICATION_EDITOR, result.getApplicationEditor());
    }

}
