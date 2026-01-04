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
import com.echothree.control.user.workeffort.common.result.GetWorkEffortScopeResult;
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
    path = "/Configuration/WorkEffortScope/Delete",
    mappingClass = SecureActionMapping.class,
    name = "WorkEffortScopeDelete",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Configuration/WorkEffortScope/Main", redirect = true),
        @SproutForward(name = "Form", path = "/configuration/workeffortscope/delete.jsp")
    }
)
public class DeleteAction
        extends MainBaseDeleteAction<DeleteActionForm> {

    @Override
    public String getEntityTypeName() {
        return EntityTypes.WorkEffortScope.name();
    }
    
    @Override
    public void setupParameters(DeleteActionForm actionForm, HttpServletRequest request) {
        actionForm.setWorkEffortTypeName(findParameter(request, ParameterConstants.WORK_EFFORT_TYPE_NAME, actionForm.getWorkEffortTypeName()));
        actionForm.setWorkEffortScopeName(findParameter(request, ParameterConstants.WORK_EFFORT_SCOPE_NAME, actionForm.getWorkEffortScopeName()));
    }
    
    @Override
    public void setupTransfer(DeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = WorkEffortUtil.getHome().getGetWorkEffortScopeForm();
        
        commandForm.setWorkEffortTypeName(actionForm.getWorkEffortTypeName());
        commandForm.setWorkEffortScopeName(actionForm.getWorkEffortScopeName());

        var commandResult = WorkEffortUtil.getHome().getWorkEffortScope(getUserVisitPK(request), commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (GetWorkEffortScopeResult)executionResult.getResult();
        
        request.setAttribute(AttributeConstants.WORK_EFFORT_SCOPE, result.getWorkEffortScope());
    }
    
    @Override
    public CommandResult doDelete(DeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = WorkEffortUtil.getHome().getDeleteWorkEffortScopeForm();

        commandForm.setWorkEffortTypeName(actionForm.getWorkEffortTypeName());
        commandForm.setWorkEffortScopeName(actionForm.getWorkEffortScopeName());

        return WorkEffortUtil.getHome().deleteWorkEffortScope(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(DeleteActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.WORK_EFFORT_TYPE_NAME, actionForm.getWorkEffortTypeName());
    }
    
}
