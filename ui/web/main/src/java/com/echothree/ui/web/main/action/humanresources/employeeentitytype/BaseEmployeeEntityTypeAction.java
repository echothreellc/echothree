// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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

import com.echothree.control.user.employee.common.EmployeeUtil;
import com.echothree.control.user.employee.common.result.GetEmployeeResult;
import com.echothree.control.user.party.common.PartyUtil;
import com.echothree.control.user.party.common.result.GetPartyEntityTypeResult;
import com.echothree.model.control.party.common.transfer.PartyEntityTypeTransfer;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionForm;

public abstract class BaseEmployeeEntityTypeAction<A extends ActionForm>
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

    public static PartyEntityTypeTransfer getPartyEntityTypeTransfer(HttpServletRequest request, String partyName, String componentVendorName,
            String entityTypeName)
            throws NamingException {
        var commandForm = PartyUtil.getHome().getGetPartyEntityTypeForm();

        commandForm.setPartyName(partyName);
        commandForm.setComponentVendorName(componentVendorName);
        commandForm.setEntityTypeName(entityTypeName);

        var commandResult = PartyUtil.getHome().getPartyEntityType(getUserVisitPK(request), commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (GetPartyEntityTypeResult)executionResult.getResult();

        return result.getPartyEntityType();
    }

    public static void setupPartyEntityTypeTransfer(HttpServletRequest request, String partyName, String componentVendorName, String entityTypeName)
            throws NamingException {
        request.setAttribute(AttributeConstants.PARTY_ENTITY_TYPE, getPartyEntityTypeTransfer(request, partyName, componentVendorName, entityTypeName));
    }

}
