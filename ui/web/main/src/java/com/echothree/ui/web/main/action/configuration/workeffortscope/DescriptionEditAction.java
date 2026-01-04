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

package com.echothree.ui.web.main.action.configuration.workeffortscope;

import com.echothree.control.user.workeffort.common.WorkEffortUtil;
import com.echothree.control.user.workeffort.common.edit.WorkEffortScopeDescriptionEdit;
import com.echothree.control.user.workeffort.common.form.EditWorkEffortScopeDescriptionForm;
import com.echothree.control.user.workeffort.common.result.EditWorkEffortScopeDescriptionResult;
import com.echothree.control.user.workeffort.common.spec.WorkEffortScopeDescriptionSpec;
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
    path = "/Configuration/WorkEffortScope/DescriptionEdit",
    mappingClass = SecureActionMapping.class,
    name = "WorkEffortScopeDescriptionEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Configuration/WorkEffortScope/Description", redirect = true),
        @SproutForward(name = "Form", path = "/configuration/workeffortscope/descriptionEdit.jsp")
    }
)
public class DescriptionEditAction
        extends MainBaseEditAction<DescriptionEditActionForm, WorkEffortScopeDescriptionSpec, WorkEffortScopeDescriptionEdit, EditWorkEffortScopeDescriptionForm, EditWorkEffortScopeDescriptionResult> {
    
    @Override
    protected WorkEffortScopeDescriptionSpec getSpec(HttpServletRequest request, DescriptionEditActionForm actionForm)
            throws NamingException {
        var spec = WorkEffortUtil.getHome().getWorkEffortScopeDescriptionSpec();
        
        spec.setWorkEffortTypeName(findParameter(request, ParameterConstants.WORK_EFFORT_TYPE_NAME, actionForm.getWorkEffortTypeName()));
        spec.setWorkEffortScopeName(findParameter(request, ParameterConstants.WORK_EFFORT_SCOPE_NAME, actionForm.getWorkEffortScopeName()));
        spec.setLanguageIsoName(findParameter(request, ParameterConstants.LANGUAGE_ISO_NAME, actionForm.getLanguageIsoName()));
        
        return spec;
    }
    
    @Override
    protected WorkEffortScopeDescriptionEdit getEdit(HttpServletRequest request, DescriptionEditActionForm actionForm)
            throws NamingException {
        var edit = WorkEffortUtil.getHome().getWorkEffortScopeDescriptionEdit();

        edit.setDescription(actionForm.getDescription());

        return edit;
    }
    
    @Override
    protected EditWorkEffortScopeDescriptionForm getForm()
            throws NamingException {
        return WorkEffortUtil.getHome().getEditWorkEffortScopeDescriptionForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, DescriptionEditActionForm actionForm, EditWorkEffortScopeDescriptionResult result, WorkEffortScopeDescriptionSpec spec, WorkEffortScopeDescriptionEdit edit) {
        actionForm.setWorkEffortTypeName(spec.getWorkEffortTypeName());
        actionForm.setWorkEffortScopeName(spec.getWorkEffortScopeName());
        actionForm.setLanguageIsoName(spec.getLanguageIsoName());
        actionForm.setDescription(edit.getDescription());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditWorkEffortScopeDescriptionForm commandForm)
            throws Exception {
        return WorkEffortUtil.getHome().editWorkEffortScopeDescription(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(DescriptionEditActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.WORK_EFFORT_TYPE_NAME, actionForm.getWorkEffortTypeName());
        parameters.put(ParameterConstants.WORK_EFFORT_SCOPE_NAME, actionForm.getWorkEffortScopeName());
    }
    
    @Override
    protected void setupTransferForForm(HttpServletRequest request, DescriptionEditActionForm actionForm, EditWorkEffortScopeDescriptionResult result) {
        request.setAttribute(AttributeConstants.WORK_EFFORT_SCOPE_DESCRIPTION, result.getWorkEffortScopeDescription());
    }

}
