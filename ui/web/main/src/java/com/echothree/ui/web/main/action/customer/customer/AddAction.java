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

package com.echothree.ui.web.main.action.customer.customer;

import com.echothree.control.user.party.common.PartyUtil;
import com.echothree.control.user.party.common.result.CreateCustomerResult;
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
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/Customer/Customer/Add",
    mappingClass = SecureActionMapping.class,
    name = "CustomerAdd",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Review", path = "/action/Customer/Customer/Review", redirect = true),
        @SproutForward(name = "Form", path = "/customer/customer/add.jsp")
    }
)
public class AddAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        var actionForm = (AddActionForm)form;
        String forwardKey;
        String customerName = null;
        
        if(wasPost(request)) {
            var commandForm = PartyUtil.getHome().getCreateCustomerForm();
            
            commandForm.setCustomerTypeName(actionForm.getCustomerTypeChoice());
            commandForm.setCancellationPolicyName(actionForm.getCancellationPolicyChoice());
            commandForm.setReturnPolicyName(actionForm.getReturnPolicyChoice());
            commandForm.setArGlAccountName(actionForm.getArGlAccountChoice());
            commandForm.setInitialSourceName(actionForm.getInitialSourceChoice());
            commandForm.setPersonalTitleId(actionForm.getPersonalTitleChoice());
            commandForm.setFirstName(actionForm.getFirstName());
            commandForm.setMiddleName(actionForm.getMiddleName());
            commandForm.setLastName(actionForm.getLastName());
            commandForm.setNameSuffixId(actionForm.getNameSuffixChoice());
            commandForm.setName(actionForm.getName());
            commandForm.setPreferredLanguageIsoName(actionForm.getLanguageChoice());
            commandForm.setPreferredCurrencyIsoName(actionForm.getCurrencyChoice());
            commandForm.setPreferredJavaTimeZoneName(actionForm.getTimeZoneChoice());
            commandForm.setPreferredDateTimeFormatName(actionForm.getDateTimeFormatChoice());
            commandForm.setEmailAddress(actionForm.getEmailAddress());
            commandForm.setAllowSolicitation(actionForm.getAllowSolicitation().toString());
            commandForm.setCustomerStatusChoice(actionForm.getCustomerStatusChoice());
            commandForm.setCustomerCreditStatusChoice(actionForm.getCustomerCreditStatusChoice());

            var commandResult = PartyUtil.getHome().createCustomer(getUserVisitPK(request), commandForm);
            
            if(commandResult.hasErrors()) {
                setCommandResultAttribute(request, commandResult);
                forwardKey = ForwardConstants.FORM;
            } else {
                var executionResult = commandResult.getExecutionResult();
                var result = (CreateCustomerResult)executionResult.getResult();
                
                forwardKey = ForwardConstants.REVIEW;
                customerName = result.getCustomerName();
            }
        } else {
            actionForm.setFirstName(request.getParameter(ParameterConstants.FIRST_NAME));
            actionForm.setMiddleName(request.getParameter(ParameterConstants.MIDDLE_NAME));
            actionForm.setLastName(request.getParameter(ParameterConstants.LAST_NAME));
            actionForm.setName(request.getParameter(ParameterConstants.NAME));
            forwardKey = ForwardConstants.FORM;
        }

        var customActionForward = new CustomActionForward(mapping.findForward(forwardKey));
        if(forwardKey.equals(ForwardConstants.REVIEW)) {
            Map<String, String> parameters = new HashMap<>(1);
            
            parameters.put(ParameterConstants.CUSTOMER_NAME, customerName);
            customActionForward.setParameters(parameters);
        }
        
        return customActionForward;
    }
    
}
