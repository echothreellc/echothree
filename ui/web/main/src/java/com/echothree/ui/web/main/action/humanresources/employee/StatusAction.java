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

package com.echothree.ui.web.main.action.humanresources.employee;

import com.echothree.control.user.employee.common.EmployeeUtil;
import com.echothree.control.user.employee.common.result.GetEmployeeResult;
import com.echothree.control.user.party.common.PartyUtil;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.CommandResult;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/HumanResources/Employee/Status",
    mappingClass = SecureActionMapping.class,
    name = "HREmployeeStatus",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/HumanResources/Employee/Result", redirect = true),
        @SproutForward(name = "Form", path = "/humanresources/employee/status.jsp")
    }
)
public class StatusAction
        extends MainBaseAction<StatusActionForm> {

    public void setupEmployee(HttpServletRequest request, String employeeName)
            throws NamingException {
        var commandForm = EmployeeUtil.getHome().getGetEmployeeForm();

        commandForm.setEmployeeName(employeeName);

        var commandResult = EmployeeUtil.getHome().getEmployee(getUserVisitPK(request), commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (GetEmployeeResult)executionResult.getResult();

        request.setAttribute(AttributeConstants.EMPLOYEE, result.getEmployee());
    }

    @Override
    public ActionForward executeAction(ActionMapping mapping, StatusActionForm actionForm, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey;
        var returnUrl = request.getParameter(ParameterConstants.RETURN_URL);
        var employeeName = request.getParameter(ParameterConstants.EMPLOYEE_NAME);

        if(returnUrl == null) {
            returnUrl = actionForm.getReturnUrl();
        }
        if(employeeName == null) {
            employeeName = actionForm.getEmployeeName();
        }

        if(wasPost(request)) {
            CommandResult commandResult = null;

            if(!wasCanceled(request)) {
                var commandForm = PartyUtil.getHome().getSetEmployeeStatusForm();

                commandForm.setEmployeeName(employeeName);
                commandForm.setEmployeeStatusChoice(actionForm.getEmployeeStatusChoice());

                commandResult = PartyUtil.getHome().setEmployeeStatus(getUserVisitPK(request), commandForm);
            }

            if(commandResult != null && commandResult.hasErrors()) {
                setCommandResultAttribute(request, commandResult);
                forwardKey = ForwardConstants.FORM;
            } else {
                forwardKey = ForwardConstants.DISPLAY;
            }
        } else {
            actionForm.setReturnUrl(returnUrl);
            actionForm.setEmployeeName(employeeName);
            forwardKey = ForwardConstants.FORM;
        }

        if(forwardKey.equals(ForwardConstants.FORM)) {
            setupEmployee(request, employeeName);
        }

        return forwardKey.equals(ForwardConstants.DISPLAY) ? new ActionForward(returnUrl, true) : mapping.findForward(forwardKey);
    }

}
