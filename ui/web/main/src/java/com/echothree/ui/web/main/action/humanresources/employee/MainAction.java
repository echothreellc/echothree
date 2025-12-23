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

import com.echothree.control.user.search.common.SearchUtil;
import com.echothree.control.user.search.common.result.GetEmployeeResultsResult;
import com.echothree.control.user.search.common.result.SearchEmployeesResult;
import com.echothree.model.control.employee.common.transfer.EmployeeResultTransfer;
import com.echothree.model.control.search.common.SearchTypes;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.view.client.web.struts.CustomActionForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/HumanResources/Employee/Main",
    mappingClass = SecureActionMapping.class,
    name = "EmployeeMain",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/HumanResources/Employee/Result", redirect = true),
        @SproutForward(name = "Review", path = "/action/HumanResources/Employee/Review", redirect = true),
        @SproutForward(name = "Form", path = "/humanresources/employee/main.jsp")
    }
)
public class MainAction
        extends MainBaseAction<MainActionForm> {
    
    private String getPartyName(HttpServletRequest request)
            throws NamingException {
        var commandForm = SearchUtil.getHome().getGetEmployeeResultsForm();
        String partyName = null;
        
        commandForm.setSearchTypeName(SearchTypes.HUMAN_RESOURCES.name());

        var commandResult = SearchUtil.getHome().getEmployeeResults(getUserVisitPK(request), commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (GetEmployeeResultsResult)executionResult.getResult();
        Collection employeeResults = result.getEmployeeResults();
        var iter = employeeResults.iterator();
        if(iter.hasNext()) {
            partyName = ((EmployeeResultTransfer)iter.next()).getPartyName();
        }
        
        return partyName;
    }
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, MainActionForm actionForm, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey;
        String partyName = null;
        var firstName = actionForm.getFirstName();
        var middleName = actionForm.getMiddleName();
        var lastName = actionForm.getLastName();

        if(wasPost(request)) {
            var commandForm = SearchUtil.getHome().getSearchEmployeesForm();

            commandForm.setSearchTypeName(SearchTypes.HUMAN_RESOURCES.name());
            commandForm.setFirstName(firstName);
            commandForm.setFirstNameSoundex(actionForm.getFirstNameSoundex().toString());
            commandForm.setMiddleName(middleName);
            commandForm.setMiddleNameSoundex(actionForm.getMiddleNameSoundex().toString());
            commandForm.setLastName(lastName);
            commandForm.setLastNameSoundex(actionForm.getLastNameSoundex().toString());
            commandForm.setEmployeeName(actionForm.getEmployeeName());
            commandForm.setPartyName(actionForm.getPartyName());
            commandForm.setEmployeeStatusChoice(actionForm.getEmployeeStatusChoice());
            commandForm.setEmployeeAvailabilityChoice(actionForm.getEmployeeAvailabilityChoice());
            commandForm.setCreatedSince(actionForm.getCreatedSince());
            commandForm.setModifiedSince(actionForm.getModifiedSince());

            var commandResult = SearchUtil.getHome().searchEmployees(getUserVisitPK(request), commandForm);

            if(commandResult.hasErrors()) {
                setCommandResultAttribute(request, commandResult);
                forwardKey = ForwardConstants.FORM;
            } else {
                var executionResult = commandResult.getExecutionResult();
                var result = (SearchEmployeesResult)executionResult.getResult();
                var count = result.getCount();

                if(count == 0 || count > 1) {
                    forwardKey = ForwardConstants.DISPLAY;
                } else {
                    partyName = getPartyName(request);
                    forwardKey = ForwardConstants.REVIEW;
                }
            }
        } else {
            actionForm.setFirstName(request.getParameter(ParameterConstants.FIRST_NAME));
            actionForm.setMiddleName(request.getParameter(ParameterConstants.MIDDLE_NAME));
            actionForm.setLastName(request.getParameter(ParameterConstants.LAST_NAME));
            forwardKey = ForwardConstants.FORM;
        }

        var customActionForward = new CustomActionForward(mapping.findForward(forwardKey));
        if(forwardKey.equals(ForwardConstants.REVIEW)) {
            Map<String, String> parameters = new HashMap<>(1);
            
            parameters.put(ParameterConstants.PARTY_NAME, partyName);
            customActionForward.setParameters(parameters);
        } else if(forwardKey.equals(ForwardConstants.DISPLAY)) {
            Map<String, String> parameters = new HashMap<>(4);

            if(firstName != null) {
                parameters.put(ParameterConstants.FIRST_NAME, firstName);
            }

            if(middleName != null) {
                parameters.put(ParameterConstants.MIDDLE_NAME, middleName);
            }

            if(lastName != null) {
                parameters.put(ParameterConstants.LAST_NAME, lastName);
            }

            customActionForward.setParameters(parameters);
        }
        
        return customActionForward;
    }
    
}