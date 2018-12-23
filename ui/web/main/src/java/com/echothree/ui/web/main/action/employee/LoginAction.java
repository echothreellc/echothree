// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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

package com.echothree.ui.web.main.action.employee;

import com.echothree.control.user.authentication.common.AuthenticationUtil;
import com.echothree.control.user.authentication.common.form.EmployeeLoginForm;
import com.echothree.control.user.authentication.common.result.GetEmployeeLoginDefaultsResult;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.message.ExecutionWarnings;
import com.echothree.util.common.string.StringUtils;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.ExecutionResult;
import com.echothree.util.common.message.Messages;
import com.echothree.view.client.web.struts.CustomActionForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/Employee/Login",
    mappingClass = SecureActionMapping.class,
    name = "EmployeeLogin",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Warning", path = "/employee/loginWarning.jsp"),
        @SproutForward(name = "Force", path = "/employee/loginForce.jsp"),
        @SproutForward(name = "Form", path = "/employee/login.jsp")
    }
)
public class LoginAction
        extends MainBaseAction<LoginActionForm> {
    
    /** Creates a new instance of LoginAction */
    public LoginAction() {
        super(false, false);
    }
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, LoginActionForm actionForm, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey = null;
        
        if(wasPost(request)) {
            EmployeeLoginForm commandForm = AuthenticationUtil.getHome().getEmployeeLoginForm();
            
            commandForm.setUsername(actionForm.getUsername());
            commandForm.setPassword(actionForm.getPassword());
            commandForm.setCompanyName(actionForm.getCompanyChoice());
            commandForm.setRemoteInet4Address(request.getRemoteAddr());
            
            CommandResult commandResult = AuthenticationUtil.getHome().employeeLogin(getUserVisitPK(request), commandForm);
            
            if(commandResult.hasErrors() || commandResult.hasWarnings()) {
                setCommandResultAttribute(request, commandResult);
                    
                if(commandResult.hasErrors()) {
                    forwardKey = ForwardConstants.FORM;
                } else if(commandResult.hasWarnings()) {
                    ExecutionResult executionResult = commandResult.getExecutionResult();
                    Messages executionWarnings = executionResult.getExecutionWarnings();

                    if(executionWarnings.containsKey(Messages.EXECUTION_WARNING, ExecutionWarnings.PasswordExpired.name())
                            || executionWarnings.containsKey(Messages.EXECUTION_WARNING, ExecutionWarnings.PasswordExpiration.name())) {
                        forwardKey = ForwardConstants.WARNING;
                    } else if(executionWarnings.containsKey(Messages.EXECUTION_WARNING, ExecutionWarnings.ForcePasswordChange.name())) {
                        forwardKey = ForwardConstants.FORCE;
                    }
                }
            } else {
                forwardKey = ForwardConstants.PORTAL;
            }
        } else {
            CommandResult commandResult = AuthenticationUtil.getHome().getEmployeeLoginDefaults(getUserVisitPK(request), null);
            ExecutionResult executionResult = commandResult.getExecutionResult();
            GetEmployeeLoginDefaultsResult result = (GetEmployeeLoginDefaultsResult)executionResult.getResult();
            EmployeeLoginForm commandForm = result.getEmployeeLoginForm();
            
            actionForm.setUsername(commandForm.getUsername());
            actionForm.setCompanyChoice(commandForm.getCompanyName());
            actionForm.setReturnUrl(request.getParameter(ParameterConstants.RETURN_URL));
            
            forwardKey = ForwardConstants.FORM;
        }
        
        ActionForward actionForward = null;
        if(!forwardKey.equals(ForwardConstants.FORM)) {
            String returnUrl = StringUtils.getInstance().trimToNull(actionForm.getReturnUrl());

            if(returnUrl != null) {
                if(forwardKey.equals(ForwardConstants.PORTAL)) {
                    actionForward = new ActionForward(returnUrl, true);
                } else if(forwardKey.equals(ForwardConstants.WARNING) || forwardKey.equals(ForwardConstants.FORCE)) {
                    CustomActionForward customActionForward = new CustomActionForward(mapping.findForward(forwardKey));
                    Map<String, String> parameters = new HashMap<>(1);

                    parameters.put(ParameterConstants.RETURN_URL, returnUrl);
                    customActionForward.setParameters(parameters);

                    actionForward = customActionForward;
                }
            }
        }

        return actionForward == null ? mapping.findForward(forwardKey) : actionForward;
    }
    
}