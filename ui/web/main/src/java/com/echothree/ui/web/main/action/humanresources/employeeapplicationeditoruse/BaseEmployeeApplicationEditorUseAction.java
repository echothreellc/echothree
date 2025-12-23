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

package com.echothree.ui.web.main.action.humanresources.employeeapplicationeditoruse;

import com.echothree.control.user.employee.common.EmployeeUtil;
import com.echothree.control.user.employee.common.result.GetEmployeeResult;
import com.echothree.control.user.party.common.PartyUtil;
import com.echothree.control.user.party.common.result.GetPartyApplicationEditorUseResult;
import com.echothree.model.control.party.common.transfer.PartyApplicationEditorUseTransfer;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionForm;

public abstract class BaseEmployeeApplicationEditorUseAction<A extends ActionForm>
        extends MainBaseAction<A> {

    public static void setupEmployee(HttpServletRequest request, String partyName)
            throws NamingException {
        var commandForm = EmployeeUtil.getHome().getGetEmployeeForm();

        commandForm.setPartyName(partyName);

        var commandResult = EmployeeUtil.getHome().getEmployee(getUserVisitPK(request), commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (GetEmployeeResult)executionResult.getResult();

        request.setAttribute(AttributeConstants.EMPLOYEE, result.getEmployee());
    }

    public void setupEmployee(HttpServletRequest request)
            throws NamingException {
        setupEmployee(request, request.getParameter(ParameterConstants.PARTY_NAME));
    }

    public static PartyApplicationEditorUseTransfer getPartyApplicationEditorUseTransfer(HttpServletRequest request, String partyName, String applicationName,
            String applicationEditorUseName)
            throws NamingException {
        var commandForm = PartyUtil.getHome().getGetPartyApplicationEditorUseForm();

        commandForm.setPartyName(partyName);
        commandForm.setApplicationName(applicationName);
        commandForm.setApplicationEditorUseName(applicationEditorUseName);

        var commandResult = PartyUtil.getHome().getPartyApplicationEditorUse(getUserVisitPK(request), commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (GetPartyApplicationEditorUseResult)executionResult.getResult();

        return result.getPartyApplicationEditorUse();
    }

    public static void setupPartyApplicationEditorUseTransfer(HttpServletRequest request, String partyName, String applicationName,
            String applicationEditorUseName)
            throws NamingException {
        request.setAttribute(AttributeConstants.PARTY_APPLICATION_EDITOR_USE, getPartyApplicationEditorUseTransfer(request, partyName, applicationName,
                applicationEditorUseName));
    }

}
