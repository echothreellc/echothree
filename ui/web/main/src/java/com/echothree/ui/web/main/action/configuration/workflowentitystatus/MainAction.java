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

package com.echothree.ui.web.main.action.configuration.workflowentitystatus;

import com.echothree.control.user.workflow.common.WorkflowUtil;
import com.echothree.control.user.workflow.common.result.GetWorkflowEntityStatusesResult;
import com.echothree.model.control.workflow.common.WorkflowOptions;
import com.echothree.model.control.workflow.common.transfer.WorkflowTransfer;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import java.util.HashSet;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/Configuration/WorkflowEntityStatus/Main",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/configuration/workflowentitystatus/main.jsp")
    }
)
public class MainAction
        extends MainBaseAction<ActionForm> {

    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey;
        var commandForm = WorkflowUtil.getHome().getGetWorkflowEntityStatusesForm();

        commandForm.setWorkflowName(request.getParameter(ParameterConstants.WORKFLOW_NAME));

        Set<String> options = new HashSet<>();
        options.add(WorkflowOptions.WorkflowEntityStatusIncludeTriggerTime);
        commandForm.setOptions(options);

        var commandResult = WorkflowUtil.getHome().getWorkflowEntityStatuses(getUserVisitPK(request), commandForm);
        GetWorkflowEntityStatusesResult result = null;
        WorkflowTransfer workflow = null;

        if(!commandResult.hasErrors()) {
            var executionResult = commandResult.getExecutionResult();
            
            result = (GetWorkflowEntityStatusesResult)executionResult.getResult();
            workflow = result.getWorkflow();
        }

        if(workflow == null) {
            forwardKey = ForwardConstants.ERROR_404;
        } else {
            request.setAttribute(AttributeConstants.WORKFLOW, workflow);
            request.setAttribute(AttributeConstants.WORKFLOW_ENTITY_STATUSES, result.getWorkflowEntityStatuses());
            forwardKey = ForwardConstants.DISPLAY;
        }

        return mapping.findForward(forwardKey);
    }

}