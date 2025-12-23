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

package com.echothree.ui.web.main.action.humanresources.leave;

import com.echothree.control.user.employee.common.EmployeeUtil;
import com.echothree.control.user.employee.common.result.GetLeaveResult;
import com.echothree.control.user.employee.common.result.SetLeaveStatusResult;
import com.echothree.ui.web.main.action.humanresources.employee.EmployeeUtils;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.MainBaseStatusAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.CommandResult;
import static com.echothree.view.client.web.struts.BaseAction.getUserVisitPK;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

@SproutAction(
    path = "/HumanResources/Leave/Status",
    mappingClass = SecureActionMapping.class,
    name = "LeaveStatus",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Result", path = "/action/HumanResources/Leave/Result", redirect = true),
        @SproutForward(name = "Review", path = "/action/HumanResources/Leave/Review", redirect = true),
        @SproutForward(name = "Display", path = "/action/HumanResources/Leave/Main", redirect = true),
        @SproutForward(name = "Form", path = "/humanresources/leave/status.jsp")
    }
)
public class StatusAction
        extends MainBaseStatusAction<StatusActionForm, SetLeaveStatusResult> {
    
    @Override
    public void setupParameters(StatusActionForm actionForm, HttpServletRequest request) {
        actionForm.setForwardKey(findParameter(request, ParameterConstants.FORWARD_KEY, actionForm.getForwardKey()));
        actionForm.setPartyName(findParameter(request, ParameterConstants.PARTY_NAME, actionForm.getPartyName()));
        actionForm.setLeaveName(findParameter(request, ParameterConstants.LEAVE_NAME, actionForm.getLeaveName()));
    }
    
   @Override
    public void setupTransfer(StatusActionForm actionForm, HttpServletRequest request)
            throws NamingException {
       var commandForm = EmployeeUtil.getHome().getGetLeaveForm();

        commandForm.setLeaveName(actionForm.getLeaveName());

       var commandResult = EmployeeUtil.getHome().getLeave(getUserVisitPK(request), commandForm);
       var executionResult = commandResult.getExecutionResult();
       var result = (GetLeaveResult)executionResult.getResult();
       var leave = result.getLeave();

        request.setAttribute(AttributeConstants.LEAVE, leave);
        request.setAttribute(AttributeConstants.EMPLOYEE, EmployeeUtils.getInstance().getEmployee(getUserVisitPK(request), leave.getParty().getPartyName(),
                null));
    }
    
    @Override
    public CommandResult doStatus(StatusActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = EmployeeUtil.getHome().getSetLeaveStatusForm();

        commandForm.setLeaveName(actionForm.getLeaveName());
        commandForm.setLeaveStatusChoice(actionForm.getLeaveStatusChoice());

        return EmployeeUtil.getHome().setLeaveStatus(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public String getDisplayForward(StatusActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        return actionForm.getForwardKey();
    }
    
    @Override
    public void setupForwardParameters(StatusActionForm actionForm, Map<String, String> parameters) {
        var partyName = actionForm.getPartyName();
        var leaveName = actionForm.getLeaveName();
        
        if(partyName != null) {
            parameters.put(ParameterConstants.PARTY_NAME, partyName);
        }
        
        if(leaveName != null) {
            parameters.put(ParameterConstants.LEAVE_NAME, leaveName);
        }
    }
    
}
