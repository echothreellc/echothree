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

package com.echothree.ui.web.main.action.accounting.division;

import com.echothree.control.user.party.common.PartyUtil;
import com.echothree.ui.web.main.framework.AttributeConstants;
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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/Accounting/Division/Add",
    mappingClass = SecureActionMapping.class,
    name = "DivisionAdd",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Accounting/Division/Main", redirect = true),
        @SproutForward(name = "Form", path = "/accounting/division/add.jsp")
    }
)
public class AddAction
        extends MainBaseAction<AddActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, AddActionForm actionForm, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey;
        var companyName = request.getParameter(ParameterConstants.COMPANY_NAME);
        
        if(wasPost(request)) {
            var commandForm = PartyUtil.getHome().getCreateDivisionForm();
            
            if(companyName == null)
                companyName = actionForm.getCompanyName();
            
            commandForm.setCompanyName(companyName);
            commandForm.setDivisionName(actionForm.getDivisionName());
            commandForm.setName(actionForm.getName());
            commandForm.setPreferredLanguageIsoName(actionForm.getLanguageChoice());
            commandForm.setPreferredCurrencyIsoName(actionForm.getCurrencyChoice());
            commandForm.setPreferredJavaTimeZoneName(actionForm.getTimeZoneChoice());
            commandForm.setPreferredDateTimeFormatName(actionForm.getDateTimeFormatChoice());
            commandForm.setIsDefault(actionForm.getIsDefault().toString());
            commandForm.setSortOrder(actionForm.getSortOrder());

            var commandResult = PartyUtil.getHome().createDivision(getUserVisitPK(request), commandForm);
            
            if(commandResult.hasErrors()) {
                setCommandResultAttribute(request, commandResult);
                forwardKey = ForwardConstants.FORM;
            } else {
                forwardKey = ForwardConstants.DISPLAY;
            }
        } else {
            actionForm.setCompanyName(companyName);
            actionForm.setSortOrder("1");
            forwardKey = ForwardConstants.FORM;
        }

        var customActionForward = new CustomActionForward(mapping.findForward(forwardKey));
        if(forwardKey.equals(ForwardConstants.FORM)) {
            request.setAttribute(AttributeConstants.COMPANY_NAME, companyName);
        } else if(forwardKey.equals(ForwardConstants.DISPLAY)) {
            Map<String, String> parameters = new HashMap<>(1);
            
            parameters.put(ParameterConstants.COMPANY_NAME, companyName);
            customActionForward.setParameters(parameters);
        }
        
        return customActionForward;
    }
    
}