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

package com.echothree.ui.web.main.action.configuration.workflowdestinationsecurityrole;

import com.echothree.control.user.workflow.common.WorkflowUtil;
import com.echothree.control.user.workflow.common.result.GetWorkflowDestinationSecurityRolesResult;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/Configuration/WorkflowDestinationSecurityRole/Main",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/configuration/workflowdestinationsecurityrole/main.jsp")
    }
)
public class MainAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey;
        var commandForm = WorkflowUtil.getHome().getGetWorkflowDestinationSecurityRolesForm();
        
        commandForm.setWorkflowName(request.getParameter(ParameterConstants.WORKFLOW_NAME));
        commandForm.setWorkflowStepName(request.getParameter(ParameterConstants.WORKFLOW_STEP_NAME));
        commandForm.setWorkflowDestinationName(request.getParameter(ParameterConstants.WORKFLOW_DESTINATION_NAME));
        commandForm.setPartyTypeName(request.getParameter(ParameterConstants.PARTY_TYPE_NAME));

        var commandResult = WorkflowUtil.getHome().getWorkflowDestinationSecurityRoles(getUserVisitPK(request), commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (GetWorkflowDestinationSecurityRolesResult)executionResult.getResult();
        var workflowDestinationPartyType = result.getWorkflowDestinationPartyType();
        
        if(workflowDestinationPartyType == null) {
            forwardKey = ForwardConstants.ERROR_404;
        } else {
            request.setAttribute(AttributeConstants.WORKFLOW_DESTINATION_PARTY_TYPE, workflowDestinationPartyType);
            request.setAttribute(AttributeConstants.WORKFLOW_DESTINATION_SECURITY_ROLES, result.getWorkflowDestinationSecurityRoles());
            forwardKey = ForwardConstants.DISPLAY;
        }
        
        return mapping.findForward(forwardKey);
    }
    
}