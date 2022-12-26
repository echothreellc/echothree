// --------------------------------------------------------------------------------
// Copyright 2002-2023 Echo Three, LLC
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

package com.echothree.ui.web.main.action.humanresources.employeeentitytype;

import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.control.user.core.common.form.GetPartyEntityTypeForm;
import com.echothree.control.user.core.common.result.GetPartyEntityTypeResult;
import com.echothree.control.user.employee.common.EmployeeUtil;
import com.echothree.control.user.employee.common.form.GetEmployeeForm;
import com.echothree.control.user.employee.common.result.GetEmployeeResult;
import com.echothree.model.control.core.common.transfer.PartyEntityTypeTransfer;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.ExecutionResult;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionForm;

public abstract class BaseEmployeeEntityTypeAction<A extends ActionForm>
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

    public static PartyEntityTypeTransfer getPartyEntityTypeTransfer(HttpServletRequest request, String partyName, String componentVendorName,
            String entityTypeName)
            throws NamingException {
        GetPartyEntityTypeForm commandForm = CoreUtil.getHome().getGetPartyEntityTypeForm();

        commandForm.setPartyName(partyName);
        commandForm.setComponentVendorName(componentVendorName);
        commandForm.setEntityTypeName(entityTypeName);

        CommandResult commandResult = CoreUtil.getHome().getPartyEntityType(getUserVisitPK(request), commandForm);
        ExecutionResult executionResult = commandResult.getExecutionResult();
        GetPartyEntityTypeResult result = (GetPartyEntityTypeResult)executionResult.getResult();

        return result.getPartyEntityType();
    }

    public static void setupPartyEntityTypeTransfer(HttpServletRequest request, String partyName, String componentVendorName, String entityTypeName)
            throws NamingException {
        request.setAttribute(AttributeConstants.PARTY_ENTITY_TYPE, getPartyEntityTypeTransfer(request, partyName, componentVendorName, entityTypeName));
    }

}
