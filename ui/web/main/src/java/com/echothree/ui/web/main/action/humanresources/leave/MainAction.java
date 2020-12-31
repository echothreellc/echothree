// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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

import com.echothree.control.user.employee.common.EmployeeUtil;
import com.echothree.control.user.employee.common.form.GetLeavesForm;
import com.echothree.control.user.employee.common.result.GetLeavesResult;
import com.echothree.model.control.employee.common.transfer.LeaveTransfer;
import com.echothree.model.control.party.common.PartyOptions;
import com.echothree.model.control.party.common.transfer.PartyTransfer;
import com.echothree.ui.web.main.action.humanresources.employee.EmployeeUtils;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.ExecutionResult;
import com.echothree.util.common.transfer.ListWrapper;
import static com.echothree.view.client.web.struts.BaseAction.getUserVisitPK;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/HumanResources/Leave/Main",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/humanresources/leave/main.jsp")
    }
)
public class MainAction
        extends MainBaseAction<ActionForm> {

    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey;
        GetLeavesForm commandForm = EmployeeUtil.getHome().getGetLeavesForm();
        String partyName = request.getParameter(ParameterConstants.PARTY_NAME);

        commandForm.setPartyName(partyName);

        Set<String> options = new HashSet<>();
        options.add(PartyOptions.PartyIncludeDescription);
        commandForm.setOptions(options);

        CommandResult commandResult = EmployeeUtil.getHome().getLeaves(getUserVisitPK(request), commandForm);
        PartyTransfer party = null;
        List<LeaveTransfer> leaves = null;

        if(!commandResult.hasErrors()) {
            ExecutionResult executionResult = commandResult.getExecutionResult();
            GetLeavesResult result = (GetLeavesResult)executionResult.getResult();
            
            party = result.getParty();
            leaves = result.getLeaves();
        }

        if(party == null) {
            forwardKey = ForwardConstants.ERROR_404;
        } else {
            request.setAttribute(AttributeConstants.PARTY, party);
            request.setAttribute(AttributeConstants.LEAVES, new ListWrapper<>(leaves));
            request.setAttribute(AttributeConstants.EMPLOYEE, EmployeeUtils.getInstance().getEmployee(getUserVisitPK(request), partyName, null));
            forwardKey = ForwardConstants.DISPLAY;
        }

        return mapping.findForward(forwardKey);
    }

}
