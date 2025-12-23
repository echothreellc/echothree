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

package com.echothree.ui.web.main.action.configuration.workflowdestinationstep;

import com.echothree.control.user.workflow.common.WorkflowUtil;
import com.echothree.control.user.workflow.common.result.GetWorkflowDestinationStepResult;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.MainBaseDeleteAction;
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
    path = "/Configuration/WorkflowDestinationStep/Delete",
    mappingClass = SecureActionMapping.class,
    name = "WorkflowDestinationStepDelete",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Configuration/WorkflowDestinationStep/Main", redirect = true),
        @SproutForward(name = "Form", path = "/configuration/workflowdestinationstep/delete.jsp")
    }
)
public class DeleteAction
        extends MainBaseDeleteAction<DeleteActionForm> {

    @Override
    public String getEntityTypeName() {
        return EntityTypes.WorkflowDestinationStep.name();
    }
    
    @Override
    public void setupParameters(DeleteActionForm actionForm, HttpServletRequest request) {
        actionForm.setWorkflowName(findParameter(request, ParameterConstants.WORKFLOW_NAME, actionForm.getWorkflowName()));
        actionForm.setWorkflowStepName(findParameter(request, ParameterConstants.WORKFLOW_STEP_NAME, actionForm.getWorkflowStepName()));
        actionForm.setWorkflowDestinationName(findParameter(request, ParameterConstants.WORKFLOW_DESTINATION_NAME, actionForm.getWorkflowDestinationName()));
        actionForm.setDestinationWorkflowName(findParameter(request, ParameterConstants.DESTINATION_WORKFLOW_NAME, actionForm.getDestinationWorkflowName()));
        actionForm.setDestinationWorkflowStepName(findParameter(request, ParameterConstants.DESTINATION_WORKFLOW_STEP_NAME, actionForm.getDestinationWorkflowStepName()));
    }
    
    @Override
    public void setupTransfer(DeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = WorkflowUtil.getHome().getGetWorkflowDestinationStepForm();
        
        commandForm.setWorkflowName(actionForm.getWorkflowName());
        commandForm.setWorkflowStepName(actionForm.getWorkflowStepName());
        commandForm.setWorkflowDestinationName(actionForm.getWorkflowDestinationName());
        commandForm.setDestinationWorkflowName(actionForm.getDestinationWorkflowName());
        commandForm.setDestinationWorkflowStepName(actionForm.getDestinationWorkflowStepName());

        var commandResult = WorkflowUtil.getHome().getWorkflowDestinationStep(getUserVisitPK(request), commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (GetWorkflowDestinationStepResult)executionResult.getResult();
        
        request.setAttribute(AttributeConstants.WORKFLOW_DESTINATION_STEP, result.getWorkflowDestinationStep());
    }
    
    @Override
    public CommandResult doDelete(DeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = WorkflowUtil.getHome().getDeleteWorkflowDestinationStepForm();

        commandForm.setWorkflowName(actionForm.getWorkflowName());
        commandForm.setWorkflowStepName(actionForm.getWorkflowStepName());
        commandForm.setWorkflowDestinationName(actionForm.getWorkflowDestinationName());
        commandForm.setDestinationWorkflowName(actionForm.getDestinationWorkflowName());
        commandForm.setDestinationWorkflowStepName(actionForm.getDestinationWorkflowStepName());

        return WorkflowUtil.getHome().deleteWorkflowDestinationStep(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(DeleteActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.WORKFLOW_NAME, actionForm.getWorkflowName());
        parameters.put(ParameterConstants.WORKFLOW_STEP_NAME, actionForm.getWorkflowStepName());
        parameters.put(ParameterConstants.WORKFLOW_DESTINATION_NAME, actionForm.getWorkflowDestinationName());
    }
    
}
