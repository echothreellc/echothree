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

import com.echothree.control.user.party.common.PartyUtil;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/HumanResources/Employee/Add",
    mappingClass = SecureActionMapping.class,
    name = "HumanResourcesEmployeeAdd",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/HumanResources/Employee/Main", redirect = true),
        @SproutForward(name = "Form", path = "/humanresources/employee/add.jsp")
    }
)
public class AddAction
        extends MainBaseAction<AddActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, AddActionForm actionForm, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey;
        
        if(wasPost(request)) {
            var commandForm = PartyUtil.getHome().getCreateEmployeeForm();
            
            commandForm.setEmployeeTypeName(actionForm.getEmployeeTypeChoice());
            commandForm.setPersonalTitleId(actionForm.getPersonalTitleChoice());
            commandForm.setFirstName(actionForm.getFirstName());
            commandForm.setMiddleName(actionForm.getMiddleName());
            commandForm.setLastName(actionForm.getLastName());
            commandForm.setNameSuffixId(actionForm.getNameSuffixChoice());
            commandForm.setPreferredLanguageIsoName(actionForm.getLanguageChoice());
            commandForm.setPreferredCurrencyIsoName(actionForm.getCurrencyChoice());
            commandForm.setPreferredJavaTimeZoneName(actionForm.getTimeZoneChoice());
            commandForm.setPreferredDateTimeFormatName(actionForm.getDateTimeFormatChoice());
            commandForm.setEmailAddress(actionForm.getEmailAddress());
            commandForm.setAllowSolicitation(Boolean.toString(actionForm.getAllowSolicitation()));
            commandForm.setUsername(actionForm.getUsername());
            commandForm.setPassword1(actionForm.getPassword1());
            commandForm.setPassword2(actionForm.getPassword2());
            commandForm.setPartySecurityRoleTemplateName(actionForm.getPartySecurityRoleTemplateChoice());

            var commandResult = PartyUtil.getHome().createEmployee(getUserVisitPK(request), commandForm);
            
            if(commandResult.hasErrors()) {
                setCommandResultAttribute(request, commandResult);
                forwardKey = ForwardConstants.FORM;
            } else {
                forwardKey = ForwardConstants.DISPLAY;
            }
        } else {
            forwardKey = ForwardConstants.FORM;
        }
        
        return mapping.findForward(forwardKey);
    }
    
}
