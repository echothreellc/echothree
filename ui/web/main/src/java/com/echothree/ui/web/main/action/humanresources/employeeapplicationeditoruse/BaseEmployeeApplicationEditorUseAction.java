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

package com.echothree.ui.web.main.action.humanresources.employeeapplicationeditoruse;

import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.control.user.core.common.form.GetPartyApplicationEditorUseForm;
import com.echothree.control.user.core.common.result.GetPartyApplicationEditorUseResult;
import com.echothree.control.user.employee.common.EmployeeUtil;
import com.echothree.control.user.employee.common.form.GetEmployeeForm;
import com.echothree.control.user.employee.common.result.GetEmployeeResult;
import com.echothree.model.control.core.common.transfer.PartyApplicationEditorUseTransfer;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.ExecutionResult;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionForm;

public abstract class BaseEmployeeApplicationEditorUseAction<A extends ActionForm>
        extends MainBaseAction<A> {

    public static void setupEmployee(HttpServletRequest request, String partyName)
            throws NamingException {
        GetEmployeeForm commandForm = EmployeeUtil.getHome().getGetEmployeeForm();

        commandForm.setPartyName(partyName);

        CommandResult commandResult = EmployeeUtil.getHome().getEmployee(getUserVisitPK(request), commandForm);
        ExecutionResult executionResult = commandResult.getExecutionResult();
        GetEmployeeResult result = (GetEmployeeResult)executionResult.getResult();

        request.setAttribute(AttributeConstants.EMPLOYEE, result.getEmployee());
    }

    public void setupEmployee(HttpServletRequest request)
            throws NamingException {
        setupEmployee(request, request.getParameter(ParameterConstants.PARTY_NAME));
    }

    public static PartyApplicationEditorUseTransfer getPartyApplicationEditorUseTransfer(HttpServletRequest request, String partyName, String applicationName,
            String applicationEditorUseName)
            throws NamingException {
        GetPartyApplicationEditorUseForm commandForm = CoreUtil.getHome().getGetPartyApplicationEditorUseForm();

        commandForm.setPartyName(partyName);
        commandForm.setApplicationName(applicationName);
        commandForm.setApplicationEditorUseName(applicationEditorUseName);

        CommandResult commandResult = CoreUtil.getHome().getPartyApplicationEditorUse(getUserVisitPK(request), commandForm);
        ExecutionResult executionResult = commandResult.getExecutionResult();
        GetPartyApplicationEditorUseResult result = (GetPartyApplicationEditorUseResult)executionResult.getResult();

        return result.getPartyApplicationEditorUse();
    }

    public static void setupPartyApplicationEditorUseTransfer(HttpServletRequest request, String partyName, String applicationName,
            String applicationEditorUseName)
            throws NamingException {
        request.setAttribute(AttributeConstants.PARTY_APPLICATION_EDITOR_USE, getPartyApplicationEditorUseTransfer(request, partyName, applicationName,
                applicationEditorUseName));
    }

}
