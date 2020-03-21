// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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
import com.echothree.control.user.search.common.form.GetLeaveResultsForm;
import com.echothree.control.user.search.common.form.SearchLeavesForm;
import com.echothree.control.user.search.common.result.GetLeaveResultsResult;
import com.echothree.control.user.search.common.result.SearchLeavesResult;
import com.echothree.model.control.search.common.SearchConstants;
import com.echothree.model.control.search.common.transfer.LeaveResultTransfer;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.ExecutionResult;
import com.echothree.view.client.web.struts.CustomActionForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
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
        GetLeaveResultsForm commandForm = SearchUtil.getHome().getGetLeaveResultsForm();
        String leaveName = null;
        
        commandForm.setSearchTypeName(SearchConstants.SearchType_LEAVE_MAINTENANCE);
        
        CommandResult commandResult = SearchUtil.getHome().getLeaveResults(getUserVisitPK(request), commandForm);
        ExecutionResult executionResult = commandResult.getExecutionResult();
        GetLeaveResultsResult result = (GetLeaveResultsResult)executionResult.getResult();
        List<LeaveResultTransfer> leaveResults = result.getLeaveResults();
        Iterator<LeaveResultTransfer> iter = leaveResults.iterator();
        if(iter.hasNext()) {
            leaveName = (iter.next()).getLeaveName();
        }
        
        return leaveName;
    }
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, SearchActionForm actionForm, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey;
        String leaveName = null;

        if(wasPost(request)) {
            SearchLeavesForm commandForm = SearchUtil.getHome().getSearchLeavesForm();

            commandForm.setSearchTypeName(SearchConstants.SearchType_LEAVE_MAINTENANCE);
            commandForm.setLeaveName(actionForm.getLeaveName());
            commandForm.setLeaveTypeName(actionForm.getLeaveTypeChoice());
            commandForm.setLeaveReasonName(actionForm.getLeaveReasonChoice());
            commandForm.setLeaveStatusChoice(actionForm.getLeaveStatusChoice());
            commandForm.setCreatedSince(actionForm.getCreatedSince());
            commandForm.setModifiedSince(actionForm.getModifiedSince());

            CommandResult commandResult = SearchUtil.getHome().searchLeaves(getUserVisitPK(request), commandForm);

            if(commandResult.hasErrors()) {
                setCommandResultAttribute(request, commandResult);
                forwardKey = ForwardConstants.FORM;
            } else {
                ExecutionResult executionResult = commandResult.getExecutionResult();
                SearchLeavesResult result = (SearchLeavesResult)executionResult.getResult();
                int count = result.getCount();

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
        
        CustomActionForward customActionForward = new CustomActionForward(mapping.findForward(forwardKey));
        if(forwardKey.equals(ForwardConstants.REVIEW)) {
            Map<String, String> parameters = new HashMap<>(1);
            
            parameters.put(ParameterConstants.LEAVE_NAME, leaveName);
            customActionForward.setParameters(parameters);
        }
        
        return customActionForward;
    }
    
}