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

package com.echothree.ui.web.main.action.accounting.companycontactmechanism.contactmechanismadd;

import com.echothree.control.user.contact.common.ContactUtil;
import com.echothree.ui.web.main.action.accounting.companycontactmechanism.BaseCompanyContactMechanismAction;
import com.echothree.ui.web.main.framework.ForwardConstants;
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
    path = "/Accounting/CompanyContactMechanism/ContactMechanismAdd/ContactTelephoneAdd",
    mappingClass = SecureActionMapping.class,
    name = "CompanyContactTelephoneAdd",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Accounting/CompanyContactMechanism/Main", redirect = true),
        @SproutForward(name = "Form", path = "/accounting/companycontactmechanism/contactmechanismadd/contactTelephoneAdd.jsp")
    }
)
public class ContactTelephoneAddAction
        extends BaseCompanyContactMechanismAction<ContactTelephoneAddActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ContactTelephoneAddActionForm actionForm, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
        String forwardKey;
        var partyName = request.getParameter(ParameterConstants.PARTY_NAME);
        var countryName = request.getParameter(ParameterConstants.COUNTRY_NAME);
        var commandForm = ContactUtil.getHome().getCreateContactTelephoneForm();

        if(partyName == null) {
            partyName = actionForm.getPartyName();
        }
        if(countryName == null) {
            countryName = actionForm.getCountryName();
        }
        
        if(wasPost(request)) {
            commandForm.setPartyName(partyName);
            commandForm.setCountryName(countryName);
            commandForm.setAllowSolicitation(actionForm.getAllowSolicitation().toString());
            commandForm.setAreaCode(actionForm.getAreaCode());
            commandForm.setTelephoneNumber(actionForm.getTelephoneNumber());
            commandForm.setTelephoneExtension(actionForm.getTelephoneExtension());
            commandForm.setDescription(actionForm.getDescription());

            var commandResult = ContactUtil.getHome().createContactTelephone(getUserVisitPK(request), commandForm);

            if(commandResult.hasErrors()) {
                setCommandResultAttribute(request, commandResult);
                forwardKey = ForwardConstants.FORM;
            } else {
                forwardKey = ForwardConstants.DISPLAY;
            }
        } else {
            actionForm.setPartyName(partyName);
            actionForm.setCountryName(countryName);
            forwardKey = ForwardConstants.FORM;
        }

        var customActionForward = new CustomActionForward(mapping.findForward(forwardKey));
        if(forwardKey.equals(ForwardConstants.FORM)) {
            setupCompany(request, partyName);
            setupCountry(request, countryName);
        } else if(forwardKey.equals(ForwardConstants.DISPLAY)) {
            Map<String, String> parameters = new HashMap<>(1);
            
            parameters.put(ParameterConstants.PARTY_NAME, partyName);
            customActionForward.setParameters(parameters);
        }
        
        return customActionForward;
    }
    
}