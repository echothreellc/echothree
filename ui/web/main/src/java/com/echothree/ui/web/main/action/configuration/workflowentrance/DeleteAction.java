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

package com.echothree.ui.web.main.action.configuration.workflowentrance;

import com.echothree.control.user.workflow.common.WorkflowUtil;
import com.echothree.control.user.workflow.common.result.GetWorkflowEntranceResult;
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
    path = "/Configuration/WorkflowEntrance/Delete",
    mappingClass = SecureActionMapping.class,
    name = "WorkflowEntranceDelete",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Configuration/WorkflowEntrance/Main", redirect = true),
        @SproutForward(name = "Form", path = "/configuration/workflowentrance/delete.jsp")
    }
)
public class DeleteAction
        extends MainBaseDeleteAction<DeleteActionForm> {

    @Override
    public String getEntityTypeName(final DeleteActionForm actionForm) {
        return EntityTypes.WorkflowEntrance.name();
    }
    
    @Override
    public void setupParameters(DeleteActionForm actionForm, HttpServletRequest request) {
        actionForm.setWorkflowName(findParameter(request, ParameterConstants.WORKFLOW_NAME, actionForm.getWorkflowName()));
        actionForm.setWorkflowEntranceName(findParameter(request, ParameterConstants.WORKFLOW_ENTRANCE_NAME, actionForm.getWorkflowEntranceName()));
    }
    
    @Override
    public void setupTransfer(DeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = WorkflowUtil.getHome().getGetWorkflowEntranceForm();
        
        commandForm.setWorkflowName(actionForm.getWorkflowName());
        commandForm.setWorkflowEntranceName(actionForm.getWorkflowEntranceName());

        var commandResult = WorkflowUtil.getHome().getWorkflowEntrance(getUserVisitPK(request), commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (GetWorkflowEntranceResult)executionResult.getResult();
        
        request.setAttribute(AttributeConstants.WORKFLOW_ENTRANCE, result.getWorkflowEntrance());
    }
    
    @Override
    public CommandResult doDelete(DeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = WorkflowUtil.getHome().getDeleteWorkflowEntranceForm();

        commandForm.setWorkflowName(actionForm.getWorkflowName());
        commandForm.setWorkflowEntranceName(actionForm.getWorkflowEntranceName());

        return WorkflowUtil.getHome().deleteWorkflowEntrance(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(DeleteActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.WORKFLOW_NAME, actionForm.getWorkflowName());
    }
    
}
