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

package com.echothree.ui.web.main.action.humanresources.company;

import com.echothree.control.user.party.common.PartyUtil;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.ui.web.main.action.humanresources.employee.EmployeeUtils;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.MainBaseAddAction;
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
    path = "/HumanResources/Company/Add",
    mappingClass = SecureActionMapping.class,
    name = "HRCompanyAdd",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/HumanResources/Company/Main", redirect = true),
        @SproutForward(name = "Form", path = "/humanresources/company/add.jsp")
    }
)
public class AddAction
        extends MainBaseAddAction<AddActionForm> {

    @Override
    public void setupParameters(AddActionForm actionForm, HttpServletRequest request) {
        actionForm.setPartyName(findParameter(request, ParameterConstants.PARTY_NAME, actionForm.getPartyName()));
        actionForm.setCompanyName(findParameter(request, ParameterConstants.COMPANY_NAME, actionForm.getCompanyName()));
        actionForm.setDivisionName(findParameter(request, ParameterConstants.DIVISION_NAME, actionForm.getDivisionName()));
    }

    @Override
    public void setupTransfer(AddActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        String partyTypeName;

        request.setAttribute(AttributeConstants.EMPLOYEE, EmployeeUtils.getInstance().getEmployee(getUserVisitPK(request), actionForm.getPartyName(), null));

        if(actionForm.getCompanyName() == null) {
            partyTypeName = PartyTypes.COMPANY.name();
        } else if(actionForm.getDivisionName() == null) {
            partyTypeName = PartyTypes.DIVISION.name();
        } else {
            partyTypeName = PartyTypes.DEPARTMENT.name();
        }

        request.setAttribute(AttributeConstants.PARTY_TYPE_NAME, partyTypeName);
    }

    @Override
    public CommandResult doAdd(AddActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var companyName = actionForm.getCompanyName();
        var divisionName = actionForm.getDivisionName();
        CommandResult commandResult;

        if(companyName == null) {
            var commandForm = PartyUtil.getHome().getAddEmployeeToCompanyForm();

            commandForm.setPartyName(actionForm.getPartyName());
            commandForm.setCompanyName(actionForm.getCompanyChoice());

            commandResult = PartyUtil.getHome().addEmployeeToCompany(getUserVisitPK(request), commandForm);
        } else if(divisionName == null) {
            var commandForm = PartyUtil.getHome().getAddEmployeeToDivisionForm();

            commandForm.setPartyName(actionForm.getPartyName());
            commandForm.setCompanyName(actionForm.getCompanyName());
            commandForm.setDivisionName(actionForm.getDivisionChoice());

            commandResult = PartyUtil.getHome().addEmployeeToDivision(getUserVisitPK(request), commandForm);
        } else {
            var commandForm = PartyUtil.getHome().getAddEmployeeToDepartmentForm();

            commandForm.setPartyName(actionForm.getPartyName());
            commandForm.setCompanyName(actionForm.getCompanyName());
            commandForm.setDivisionName(actionForm.getDivisionName());
            commandForm.setDepartmentName(actionForm.getDepartmentChoice());

            commandResult = PartyUtil.getHome().addEmployeeToDepartment(getUserVisitPK(request), commandForm);
        }

        return commandResult;
    }

    @Override
    public void setupForwardParameters(AddActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.PARTY_NAME, actionForm.getPartyName());
    }

}
