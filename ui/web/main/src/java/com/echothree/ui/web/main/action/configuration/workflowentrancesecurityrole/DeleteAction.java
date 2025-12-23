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

package com.echothree.ui.web.main.action.configuration.workflowentrancesecurityrole;

import com.echothree.control.user.workflow.common.WorkflowUtil;
import com.echothree.control.user.workflow.common.result.GetWorkflowEntranceSecurityRoleResult;
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
    path = "/Configuration/WorkflowEntranceSecurityRole/Delete",
    mappingClass = SecureActionMapping.class,
    name = "WorkflowEntranceSecurityRoleDelete",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Configuration/WorkflowEntranceSecurityRole/Main", redirect = true),
        @SproutForward(name = "Form", path = "/configuration/workflowdestinationsecurityrole/delete.jsp")
    }
)
public class DeleteAction
        extends MainBaseDeleteAction<DeleteActionForm> {

    @Override
    public String getEntityTypeName() {
        return EntityTypes.WorkflowEntranceSecurityRole.name();
    }
    
    @Override
    public void setupParameters(DeleteActionForm actionForm, HttpServletRequest request) {
        actionForm.setWorkflowName(findParameter(request, ParameterConstants.WORKFLOW_NAME, actionForm.getWorkflowName()));
        actionForm.setWorkflowEntranceName(findParameter(request, ParameterConstants.WORKFLOW_ENTRANCE_NAME, actionForm.getWorkflowEntranceName()));
        actionForm.setPartyTypeName(findParameter(request, ParameterConstants.PARTY_TYPE_NAME, actionForm.getPartyTypeName()));
        actionForm.setSecurityRoleName(findParameter(request, ParameterConstants.SECURITY_ROLE_NAME, actionForm.getSecurityRoleName()));
    }
    
    @Override
    public void setupTransfer(DeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = WorkflowUtil.getHome().getGetWorkflowEntranceSecurityRoleForm();
        
        commandForm.setWorkflowName(actionForm.getWorkflowName());
        commandForm.setWorkflowEntranceName(actionForm.getWorkflowEntranceName());
        commandForm.setPartyTypeName(actionForm.getPartyTypeName());
        commandForm.setSecurityRoleName(actionForm.getSecurityRoleName());

        var commandResult = WorkflowUtil.getHome().getWorkflowEntranceSecurityRole(getUserVisitPK(request), commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (GetWorkflowEntranceSecurityRoleResult)executionResult.getResult();
        
        request.setAttribute(AttributeConstants.WORKFLOW_ENTRANCE_SECURITY_ROLE, result.getWorkflowEntranceSecurityRole());
    }
    
    @Override
    public CommandResult doDelete(DeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = WorkflowUtil.getHome().getDeleteWorkflowEntranceSecurityRoleForm();

        commandForm.setWorkflowName(actionForm.getWorkflowName());
        commandForm.setWorkflowEntranceName(actionForm.getWorkflowEntranceName());
        commandForm.setPartyTypeName(actionForm.getPartyTypeName());
        commandForm.setSecurityRoleName(actionForm.getSecurityRoleName());

        return WorkflowUtil.getHome().deleteWorkflowEntranceSecurityRole(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(DeleteActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.WORKFLOW_NAME, actionForm.getWorkflowName());
        parameters.put(ParameterConstants.WORKFLOW_ENTRANCE_NAME, actionForm.getWorkflowEntranceName());
        parameters.put(ParameterConstants.PARTY_TYPE_NAME, actionForm.getPartyTypeName());
    }
    
}
