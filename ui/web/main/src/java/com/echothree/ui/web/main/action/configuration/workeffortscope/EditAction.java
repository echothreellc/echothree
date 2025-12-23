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
import com.echothree.control.user.workeffort.common.edit.WorkEffortScopeEdit;
import com.echothree.control.user.workeffort.common.form.EditWorkEffortScopeForm;
import com.echothree.control.user.workeffort.common.result.EditWorkEffortScopeResult;
import com.echothree.control.user.workeffort.common.spec.WorkEffortScopeSpec;
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
    path = "/Configuration/WorkEffortScope/Edit",
    mappingClass = SecureActionMapping.class,
    name = "WorkEffortScopeEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Configuration/WorkEffortScope/Main", redirect = true),
        @SproutForward(name = "Form", path = "/configuration/workeffortscope/edit.jsp")
    }
)
public class EditAction
        extends MainBaseEditAction<EditActionForm, WorkEffortScopeSpec, WorkEffortScopeEdit, EditWorkEffortScopeForm, EditWorkEffortScopeResult> {
    
    @Override
    protected WorkEffortScopeSpec getSpec(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var spec = WorkEffortUtil.getHome().getWorkEffortScopeSpec();
        
        spec.setWorkEffortTypeName(findParameter(request, ParameterConstants.WORK_EFFORT_TYPE_NAME, actionForm.getWorkEffortTypeName()));
        spec.setWorkEffortScopeName(findParameter(request, ParameterConstants.ORIGINAL_WORK_EFFORT_SCOPE_NAME, actionForm.getOriginalWorkEffortScopeName()));
        
        return spec;
    }
    
    @Override
    protected WorkEffortScopeEdit getEdit(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var edit = WorkEffortUtil.getHome().getWorkEffortScopeEdit();

        edit.setWorkEffortScopeName(actionForm.getWorkEffortScopeName());
        edit.setScheduledTime(actionForm.getScheduledTime());
        edit.setScheduledTimeUnitOfMeasureTypeName(actionForm.getScheduledTimeUnitOfMeasureTypeChoice());
        edit.setEstimatedTimeAllowed(actionForm.getEstimatedTimeAllowed());
        edit.setEstimatedTimeAllowedUnitOfMeasureTypeName(actionForm.getEstimatedTimeAllowedUnitOfMeasureTypeChoice());
        edit.setMaximumTimeAllowed(actionForm.getMaximumTimeAllowed());
        edit.setMaximumTimeAllowedUnitOfMeasureTypeName(actionForm.getMaximumTimeAllowedUnitOfMeasureTypeChoice());
        edit.setIsDefault(actionForm.getIsDefault().toString());
        edit.setSortOrder(actionForm.getSortOrder());
        edit.setDescription(actionForm.getDescription());

        return edit;
    }
    
    @Override
    protected EditWorkEffortScopeForm getForm()
            throws NamingException {
        return WorkEffortUtil.getHome().getEditWorkEffortScopeForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, EditActionForm actionForm, EditWorkEffortScopeResult result, WorkEffortScopeSpec spec, WorkEffortScopeEdit edit) {
        actionForm.setWorkEffortTypeName(spec.getWorkEffortTypeName());
        actionForm.setOriginalWorkEffortScopeName(spec.getWorkEffortScopeName());
        actionForm.setWorkEffortScopeName(edit.getWorkEffortScopeName());
        actionForm.setScheduledTime(edit.getScheduledTime());
        actionForm.setScheduledTimeUnitOfMeasureTypeChoice(edit.getScheduledTimeUnitOfMeasureTypeName());
        actionForm.setEstimatedTimeAllowed(edit.getEstimatedTimeAllowed());
        actionForm.setEstimatedTimeAllowedUnitOfMeasureTypeChoice(edit.getEstimatedTimeAllowedUnitOfMeasureTypeName());
        actionForm.setMaximumTimeAllowed(edit.getMaximumTimeAllowed());
        actionForm.setMaximumTimeAllowedUnitOfMeasureTypeChoice(edit.getMaximumTimeAllowedUnitOfMeasureTypeName());
        actionForm.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        actionForm.setSortOrder(edit.getSortOrder());
        actionForm.setDescription(edit.getDescription());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditWorkEffortScopeForm commandForm)
            throws Exception {
        return WorkEffortUtil.getHome().editWorkEffortScope(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(EditActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.WORK_EFFORT_TYPE_NAME, actionForm.getWorkEffortTypeName());
    }
    
    @Override
    protected void setupTransferForForm(HttpServletRequest request, EditActionForm actionForm, EditWorkEffortScopeResult result) {
        request.setAttribute(AttributeConstants.WORK_EFFORT_SCOPE, result.getWorkEffortScope());
    }

}
