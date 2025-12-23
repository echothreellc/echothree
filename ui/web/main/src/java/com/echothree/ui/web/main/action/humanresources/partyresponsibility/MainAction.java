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

package com.echothree.ui.web.main.action.humanresources.partyresponsibility;

import com.echothree.control.user.employee.common.EmployeeUtil;
import com.echothree.control.user.employee.common.result.GetPartyResponsibilitiesResult;
import com.echothree.ui.web.main.action.humanresources.employee.EmployeeUtils;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/HumanResources/PartyResponsibility/Main",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/humanresources/partyresponsibility/main.jsp")
    }
)
public class MainAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        var partyName = request.getParameter(ParameterConstants.PARTY_NAME);
        var responsibilityTypeName = request.getParameter(ParameterConstants.RESPONSIBILITY_TYPE_NAME);
        var commandForm = EmployeeUtil.getHome().getGetPartyResponsibilitiesForm();

        commandForm.setPartyName(partyName);
        commandForm.setResponsibilityTypeName(responsibilityTypeName);

        var commandResult = EmployeeUtil.getHome().getPartyResponsibilities(getUserVisitPK(request), commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (GetPartyResponsibilitiesResult)executionResult.getResult();
        var party = result.getParty();
        var responsibilityType = result.getResponsibilityType();

        request.setAttribute(AttributeConstants.PARTY, party);
        request.setAttribute(AttributeConstants.RESPONSIBILITY_TYPE, responsibilityType);
        request.setAttribute(AttributeConstants.PARTY_RESPONSIBILITIES, result.getPartyResponsibilities());
        if(party != null) {
            request.setAttribute(AttributeConstants.EMPLOYEE, EmployeeUtils.getInstance().getEmployee(getUserVisitPK(request), party.getPartyName(), null));
        }
        
        return mapping.findForward(ForwardConstants.DISPLAY);
    }
    
}
