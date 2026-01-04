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

package com.echothree.ui.web.main.action.humanresources.leave;

import com.echothree.control.user.search.common.SearchUtil;
import com.echothree.control.user.search.common.result.GetLeaveResultsResult;
import com.echothree.control.user.search.common.result.SearchLeavesResult;
import com.echothree.model.control.search.common.SearchTypes;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.view.client.web.struts.CustomActionForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import java.util.HashMap;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/HumanResources/Leave/Search",
    mappingClass = SecureActionMapping.class,
    name = "LeaveSearch",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/HumanResources/Leave/Result", redirect = true),
        @SproutForward(name = "Review", path = "/action/HumanResources/Leave/Review", redirect = true),
        @SproutForward(name = "Form", path = "/humanresources/leave/search.jsp")
    }
)
public class SearchAction
        extends MainBaseAction<SearchActionForm> {
    
    private String getLeaveName(HttpServletRequest request)
            throws NamingException {
        var commandForm = SearchUtil.getHome().getGetLeaveResultsForm();
        String leaveName = null;
        
        commandForm.setSearchTypeName(SearchTypes.LEAVE_MAINTENANCE.name());

        var commandResult = SearchUtil.getHome().getLeaveResults(getUserVisitPK(request), commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (GetLeaveResultsResult)executionResult.getResult();
        var leaveResults = result.getLeaveResults();
        var iter = leaveResults.iterator();
        if(iter.hasNext()) {
            leaveName = iter.next().getLeaveName();
        }
        
        return leaveName;
    }
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, SearchActionForm actionForm, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey;
        String leaveName = null;

        if(wasPost(request)) {
            var commandForm = SearchUtil.getHome().getSearchLeavesForm();

            commandForm.setSearchTypeName(SearchTypes.LEAVE_MAINTENANCE.name());
            commandForm.setLeaveName(actionForm.getLeaveName());
            commandForm.setLeaveTypeName(actionForm.getLeaveTypeChoice());
            commandForm.setLeaveReasonName(actionForm.getLeaveReasonChoice());
            commandForm.setLeaveStatusChoice(actionForm.getLeaveStatusChoice());
            commandForm.setCreatedSince(actionForm.getCreatedSince());
            commandForm.setModifiedSince(actionForm.getModifiedSince());

            var commandResult = SearchUtil.getHome().searchLeaves(getUserVisitPK(request), commandForm);

            if(commandResult.hasErrors()) {
                setCommandResultAttribute(request, commandResult);
                forwardKey = ForwardConstants.FORM;
            } else {
                var executionResult = commandResult.getExecutionResult();
                var result = (SearchLeavesResult)executionResult.getResult();
                var count = result.getCount();

                if(count == 0 || count > 1) {
                    forwardKey = ForwardConstants.DISPLAY;
                } else {
                    leaveName = getLeaveName(request);
                    forwardKey = ForwardConstants.REVIEW;
                }
            }
        } else {
            forwardKey = ForwardConstants.FORM;
        }

        var customActionForward = new CustomActionForward(mapping.findForward(forwardKey));
        if(forwardKey.equals(ForwardConstants.REVIEW)) {
            Map<String, String> parameters = new HashMap<>(1);
            
            parameters.put(ParameterConstants.LEAVE_NAME, leaveName);
            customActionForward.setParameters(parameters);
        }
        
        return customActionForward;
    }
    
}