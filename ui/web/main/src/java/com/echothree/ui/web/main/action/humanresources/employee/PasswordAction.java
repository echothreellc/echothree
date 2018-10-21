// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

import com.echothree.control.user.authentication.common.AuthenticationUtil;
import com.echothree.control.user.authentication.remote.form.SetPasswordForm;
import com.echothree.control.user.employee.common.EmployeeUtil;
import com.echothree.control.user.employee.remote.form.GetEmployeeForm;
import com.echothree.control.user.employee.remote.result.GetEmployeeResult;
import com.echothree.control.user.party.common.PartyUtil;
import com.echothree.control.user.party.remote.form.GetPartyTypeForm;
import com.echothree.control.user.party.remote.result.GetPartyTypeResult;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.party.common.PartyOptions;
import com.echothree.model.control.party.remote.transfer.PartyTypePasswordStringPolicyTransfer;
import com.echothree.model.control.party.remote.transfer.PartyTypeTransfer;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.remote.command.CommandResult;
import com.echothree.util.remote.command.ExecutionResult;
import static com.echothree.view.client.web.struts.BaseAction.getUserVisitPK;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import java.util.HashSet;
import java.util.Set;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/HumanResources/Employee/Password",
    mappingClass = SecureActionMapping.class,
    name = "EmployeePassword",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/HumanResources/Employee/Main", redirect = true),
        @SproutForward(name = "Form", path = "/humanresources/employee/password.jsp")
    }
)
public class PasswordAction
        extends MainBaseAction<PasswordActionForm> {
    
    public void setupEmployee(HttpServletRequest request, String employeeName)
            throws NamingException {
        GetEmployeeForm commandForm = EmployeeUtil.getHome().getGetEmployeeForm();

        commandForm.setEmployeeName(employeeName);

        CommandResult commandResult = EmployeeUtil.getHome().getEmployee(getUserVisitPK(request), commandForm);
        ExecutionResult executionResult = commandResult.getExecutionResult();
        GetEmployeeResult result = (GetEmployeeResult)executionResult.getResult();

        request.setAttribute(AttributeConstants.EMPLOYEE, result.getEmployee());
    }

    @Override
    public ActionForward executeAction(ActionMapping mapping, PasswordActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey = null;
        String returnUrl = request.getParameter(ParameterConstants.RETURN_URL);
        String employeeName = request.getParameter(ParameterConstants.EMPLOYEE_NAME);
        PasswordActionForm actionForm = (PasswordActionForm)form;
        
        if(returnUrl == null) {
            returnUrl = actionForm.getReturnUrl();
        }
        if(employeeName == null) {
            employeeName = actionForm.getEmployeeName();
        }
        
        if(wasPost(request)) {
            SetPasswordForm commandForm = AuthenticationUtil.getHome().getSetPasswordForm();
            
            commandForm.setEmployeeName(actionForm.getEmployeeName());
            commandForm.setNewPassword1(actionForm.getNewPassword1());
            commandForm.setNewPassword2(actionForm.getNewPassword2());
            
            CommandResult commandResult = AuthenticationUtil.getHome().setPassword(getUserVisitPK(request), commandForm);
            
            if(commandResult.hasErrors()) {
                setCommandResultAttribute(request, commandResult);
            } else {
                forwardKey = ForwardConstants.DISPLAY;
            }
        } else {
            actionForm.setReturnUrl(returnUrl);
            actionForm.setEmployeeName(employeeName);
        }
        
        if(forwardKey == null) {
            GetPartyTypeForm commandForm = PartyUtil.getHome().getGetPartyTypeForm();
            
            commandForm.setPartyTypeName(PartyConstants.PartyType_EMPLOYEE);
            
            Set<String> options = new HashSet<>();
            options.add(PartyOptions.PartyTypeIncludePasswordStringPolicy);
            commandForm.setOptions(options);
            
            CommandResult commandResult = PartyUtil.getHome().getPartyType(getUserVisitPK(request), commandForm);
            ExecutionResult executionResult = commandResult.getExecutionResult();
            GetPartyTypeResult result = (GetPartyTypeResult)executionResult.getResult();
            PartyTypeTransfer partyType = result.getPartyType();
            PartyTypePasswordStringPolicyTransfer partyTypePasswordStringPolicy = partyType == null? null: partyType.getPartyTypePasswordStringPolicy();
            
            if(partyTypePasswordStringPolicy != null) {
                request.setAttribute(AttributeConstants.PARTY_TYPE_PASSWORD_STRING_POLICY, partyTypePasswordStringPolicy);
            }
            
            forwardKey = ForwardConstants.FORM;
        }
        
        if(forwardKey.equals(ForwardConstants.FORM)) {
            setupEmployee(request, employeeName);
        }

        return forwardKey.equals(ForwardConstants.DISPLAY) ? new ActionForward(returnUrl, true) : mapping.findForward(forwardKey);
    }
    
}
