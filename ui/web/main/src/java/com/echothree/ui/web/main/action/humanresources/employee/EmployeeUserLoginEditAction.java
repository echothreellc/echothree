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
import com.echothree.control.user.party.common.spec.PartyUniversalSpec;
import com.echothree.control.user.user.common.UserUtil;
import com.echothree.control.user.user.common.edit.UserLoginEdit;
import com.echothree.control.user.user.common.form.EditUserLoginForm;
import com.echothree.control.user.user.common.result.EditUserLoginResult;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.MainBaseEditAction;
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
    path = "/HumanResources/Employee/EmployeeUserLoginEdit",
    mappingClass = SecureActionMapping.class,
    name = "EmployeeUserLoginEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/HumanResources/Employee/Review", redirect = true),
        @SproutForward(name = "Form", path = "/humanresources/employee/employeeUserLoginEdit.jsp")
    }
)
public class EmployeeUserLoginEditAction
        extends MainBaseEditAction<EmployeeUserLoginEditActionForm, PartyUniversalSpec, UserLoginEdit, EditUserLoginForm, EditUserLoginResult> {
    
    @Override
    protected PartyUniversalSpec getSpec(HttpServletRequest request, EmployeeUserLoginEditActionForm actionForm)
            throws NamingException {
        var spec = PartyUtil.getHome().getPartyUniversalSpec();
        
        spec.setPartyName(findParameter(request, ParameterConstants.PARTY_NAME, actionForm.getPartyName()));
        
        return spec;
    }
    
    @Override
    protected UserLoginEdit getEdit(HttpServletRequest request, EmployeeUserLoginEditActionForm actionForm)
            throws NamingException {
        var edit = UserUtil.getHome().getUserLoginEdit();

        edit.setUsername(actionForm.getUsername());

        return edit;
    }
    
    @Override
    protected EditUserLoginForm getForm()
            throws NamingException {
        return UserUtil.getHome().getEditUserLoginForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, EmployeeUserLoginEditActionForm actionForm, EditUserLoginResult result, PartyUniversalSpec spec, UserLoginEdit edit) {
        actionForm.setPartyName(spec.getPartyName());
        actionForm.setUsername(edit.getUsername());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditUserLoginForm commandForm)
            throws Exception {
        var commandResult = UserUtil.getHome().editUserLogin(getUserVisitPK(request), commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (EditUserLoginResult)executionResult.getResult();

        request.setAttribute(AttributeConstants.USER_LOGIN, result.getUserLogin());
        
        return commandResult;
    }
    
    @Override
    public void setupForwardParameters(EmployeeUserLoginEditActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.PARTY_NAME, actionForm.getPartyName());
    }
    
    @Override
    public void setupTransfer(EmployeeUserLoginEditActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = EmployeeUtil.getHome().getGetEmployeeForm();
        
        commandForm.setPartyName(actionForm.getPartyName());

        var commandResult = EmployeeUtil.getHome().getEmployee(getUserVisitPK(request), commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (GetEmployeeResult)executionResult.getResult();
        
        request.setAttribute(AttributeConstants.EMPLOYEE, result.getEmployee());
    }
    
}