// --------------------------------------------------------------------------------
// Copyright 2002-2022 Echo Three, LLC
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
import com.echothree.control.user.search.common.form.GetEmployeeResultsForm;
import com.echothree.control.user.search.common.result.GetEmployeeResultsResult;
import com.echothree.model.control.party.common.PartyOptions;
import com.echothree.model.control.search.common.SearchConstants;
import com.echothree.model.control.search.common.SearchOptions;
import com.echothree.model.data.search.common.SearchResultConstants;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.ExecutionResult;
import com.echothree.util.common.transfer.Limit;
import com.echothree.util.common.transfer.ListWrapper;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import static java.lang.Math.toIntExact;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;

@SproutAction(
    path = "/HumanResources/Employee/Result",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/humanresources/employee/result.jsp")
    }
)
public class ResultAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        GetEmployeeResultsForm commandForm = SearchUtil.getHome().getGetEmployeeResultsForm();
        String results = request.getParameter(ParameterConstants.RESULTS);

        commandForm.setSearchTypeName(SearchConstants.SearchType_HUMAN_RESOURCES);

        Set<String> options = new HashSet<>();
        options.add(SearchOptions.EmployeeResultIncludeEmployee);
        options.add(PartyOptions.PartyIncludeUserLogin);
        commandForm.setOptions(options);

        if(results == null) {
            String offsetParameter = request.getParameter(new ParamEncoder("employeeResult").encodeParameterName(TableTagParameters.PARAMETER_PAGE));
            Integer offset = offsetParameter == null ? null : (Integer.parseInt(offsetParameter) - 1) * 20;

            Map<String, Limit> limits = new HashMap<>();
            limits.put(SearchResultConstants.ENTITY_TYPE_NAME, new Limit("20", offset == null ? null : offset.toString()));
            commandForm.setLimits(limits);
        }

        CommandResult commandResult = setCommandResultAttribute(request, SearchUtil.getHome().getEmployeeResults(getUserVisitPK(request), commandForm));
        if(!commandResult.hasErrors()) {
            ExecutionResult executionResult = commandResult.getExecutionResult();
            GetEmployeeResultsResult result = (GetEmployeeResultsResult)executionResult.getResult();

            var employeeResultCount = result.getEmployeeResultCount();
            if(employeeResultCount != null) {
                request.setAttribute(AttributeConstants.EMPLOYEE_RESULT_COUNT, toIntExact(employeeResultCount));
            }

            request.setAttribute(AttributeConstants.EMPLOYEE_RESULTS, new ListWrapper<>(result.getEmployeeResults()));
        }

        return mapping.findForward(ForwardConstants.DISPLAY);
    }
    
}
