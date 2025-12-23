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

package com.echothree.ui.web.main.action.configuration.workflowstep;

import com.echothree.control.user.workflow.common.WorkflowUtil;
import com.echothree.control.user.workflow.common.edit.WorkflowStepEdit;
import com.echothree.control.user.workflow.common.form.EditWorkflowStepForm;
import com.echothree.control.user.workflow.common.result.EditWorkflowStepResult;
import com.echothree.control.user.workflow.common.spec.WorkflowStepUniversalSpec;
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
    path = "/Configuration/WorkflowStep/Edit",
    mappingClass = SecureActionMapping.class,
    name = "WorkflowStepEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Configuration/WorkflowStep/Main", redirect = true),
        @SproutForward(name = "Form", path = "/configuration/workflowstep/edit.jsp")
    }
)
public class EditAction
        extends MainBaseEditAction<EditActionForm, WorkflowStepUniversalSpec, WorkflowStepEdit, EditWorkflowStepForm, EditWorkflowStepResult> {
    
    @Override
    protected WorkflowStepUniversalSpec getSpec(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var spec = WorkflowUtil.getHome().getWorkflowStepUniversalSpec();

        spec.setWorkflowName(findParameter(request, ParameterConstants.WORKFLOW_NAME, actionForm.getWorkflowName()));
        spec.setWorkflowStepName(findParameter(request, ParameterConstants.ORIGINAL_WORKFLOW_STEP_NAME, actionForm.getOriginalWorkflowStepName()));
        
        return spec;
    }
    
    @Override
    protected WorkflowStepEdit getEdit(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var edit = WorkflowUtil.getHome().getWorkflowStepEdit();

        edit.setWorkflowStepName(actionForm.getWorkflowStepName());
        edit.setWorkflowStepTypeName(actionForm.getWorkflowStepTypeChoice());
        edit.setIsDefault(actionForm.getIsDefault().toString());
        edit.setSortOrder(actionForm.getSortOrder());
        edit.setDescription(actionForm.getDescription());

        return edit;
    }
    
    @Override
    protected EditWorkflowStepForm getForm()
            throws NamingException {
        return WorkflowUtil.getHome().getEditWorkflowStepForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, EditActionForm actionForm, EditWorkflowStepResult result, WorkflowStepUniversalSpec spec, WorkflowStepEdit edit) {
        actionForm.setWorkflowName(spec.getWorkflowName());
        actionForm.setWorkflowStepName(spec.getWorkflowStepName());
        actionForm.setOriginalWorkflowStepName(spec.getWorkflowStepName());
        actionForm.setWorkflowStepTypeChoice(edit.getWorkflowStepTypeName());
        actionForm.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        actionForm.setSortOrder(edit.getSortOrder());
        actionForm.setDescription(edit.getDescription());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditWorkflowStepForm commandForm)
            throws Exception {
        var commandResult = WorkflowUtil.getHome().editWorkflowStep(getUserVisitPK(request), commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (EditWorkflowStepResult)executionResult.getResult();

        request.setAttribute(AttributeConstants.WORKFLOW_STEP, result.getWorkflowStep());
        
        return commandResult;
    }
    
    @Override
    public void setupForwardParameters(EditActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.WORKFLOW_NAME, actionForm.getWorkflowName());
    }
    
}