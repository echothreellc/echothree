// --------------------------------------------------------------------------------
// Copyright 2002-2024 Echo Three, LLC
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

package com.echothree.ui.web.main.action.configuration.workrequirementscope;

import com.echothree.control.user.workrequirement.common.WorkRequirementUtil;
import com.echothree.control.user.workrequirement.common.form.GetWorkRequirementScopeForm;
import com.echothree.control.user.workrequirement.common.result.GetWorkRequirementScopeResult;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.ExecutionResult;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/Configuration/WorkRequirementScope/Review",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/configuration/workrequirementscope/review.jsp")
    }
)
public class ReviewAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
        String forwardKey = null;
        
        try {
            GetWorkRequirementScopeForm commandForm = WorkRequirementUtil.getHome().getGetWorkRequirementScopeForm();
            String workEffortTypeName = request.getParameter(ParameterConstants.WORK_EFFORT_TYPE_NAME);
            String workRequirementTypeName = request.getParameter(ParameterConstants.WORK_REQUIREMENT_TYPE_NAME);
            String workEffortScopeName = request.getParameter(ParameterConstants.WORK_EFFORT_SCOPE_NAME);
            
            commandForm.setWorkEffortTypeName(workEffortTypeName);
            commandForm.setWorkRequirementTypeName(workRequirementTypeName);
            commandForm.setWorkEffortScopeName(workEffortScopeName);
            
            CommandResult commandResult = WorkRequirementUtil.getHome().getWorkRequirementScope(getUserVisitPK(request), commandForm);
            ExecutionResult executionResult = commandResult.getExecutionResult();
            GetWorkRequirementScopeResult result = (GetWorkRequirementScopeResult)executionResult.getResult();
            
            request.setAttribute(AttributeConstants.WORK_REQUIREMENT_SCOPE, result.getWorkRequirementScope());
            forwardKey = ForwardConstants.DISPLAY;
        } catch (NamingException ne) {
            forwardKey = ForwardConstants.ERROR_500;
        }
        
        return mapping.findForward(forwardKey);
    }
    
}
