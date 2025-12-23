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

package com.echothree.ui.web.main.action.configuration.workflowdestination;

import com.echothree.control.user.workflow.common.WorkflowUtil;
import com.echothree.control.user.workflow.common.result.GetWorkflowDestinationResult;
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
    path = "/Configuration/WorkflowDestination/Delete",
    mappingClass = SecureActionMapping.class,
    name = "WorkflowDestinationDelete",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Configuration/WorkflowDestination/Main", redirect = true),
        @SproutForward(name = "Form", path = "/configuration/workflowdestination/delete.jsp")
    }
)
public class DeleteAction
        extends MainBaseDeleteAction<DeleteActionForm> {

    @Override
    public String getEntityTypeName() {
        return EntityTypes.WorkflowDestination.name();
    }
    
    @Override
    public void setupParameters(DeleteActionForm actionForm, HttpServletRequest request) {
        actionForm.setWorkflowName(findParameter(request, ParameterConstants.WORKFLOW_NAME, actionForm.getWorkflowName()));
        actionForm.setWorkflowStepName(findParameter(request, ParameterConstants.WORKFLOW_STEP_NAME, actionForm.getWorkflowStepName()));
        actionForm.setWorkflowDestinationName(findParameter(request, ParameterConstants.WORKFLOW_DESTINATION_NAME, actionForm.getWorkflowDestinationName()));
    }
    
    @Override
    public void setupTransfer(DeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = WorkflowUtil.getHome().getGetWorkflowDestinationForm();
        
        commandForm.setWorkflowName(actionForm.getWorkflowName());
        commandForm.setWorkflowStepName(actionForm.getWorkflowStepName());
        commandForm.setWorkflowDestinationName(actionForm.getWorkflowDestinationName());

        var commandResult = WorkflowUtil.getHome().getWorkflowDestination(getUserVisitPK(request), commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (GetWorkflowDestinationResult)executionResult.getResult();
        
        request.setAttribute(AttributeConstants.WORKFLOW_DESTINATION, result.getWorkflowDestination());
    }
    
    @Override
    public CommandResult doDelete(DeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = WorkflowUtil.getHome().getDeleteWorkflowDestinationForm();

        commandForm.setWorkflowName(actionForm.getWorkflowName());
        commandForm.setWorkflowStepName(actionForm.getWorkflowStepName());
        commandForm.setWorkflowDestinationName(actionForm.getWorkflowDestinationName());

        return WorkflowUtil.getHome().deleteWorkflowDestination(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(DeleteActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.WORKFLOW_NAME, actionForm.getWorkflowName());
        parameters.put(ParameterConstants.WORKFLOW_STEP_NAME, actionForm.getWorkflowStepName());
    }
    
}
