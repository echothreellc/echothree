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

import com.echothree.control.user.customer.common.CustomerUtil;
import com.echothree.control.user.customer.common.result.GetCustomerResult;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.CommandResult;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/Customer/Customer/CustomerStatus",
    mappingClass = SecureActionMapping.class,
    name = "CustomerStatus",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Customer/Customer/Review", redirect = true),
        @SproutForward(name = "Form", path = "/customer/customer/customerStatus.jsp")
    }
)
public class CustomerStatusAction
        extends MainBaseAction<ActionForm> {
    
    public void setupCustomer(HttpServletRequest request, String customerName)
            throws NamingException {
        var commandForm = CustomerUtil.getHome().getGetCustomerForm();

        commandForm.setCustomerName(customerName);

        var commandResult = CustomerUtil.getHome().getCustomer(getUserVisitPK(request), commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (GetCustomerResult)executionResult.getResult();

        request.setAttribute(AttributeConstants.CUSTOMER, result.getCustomer());
    }

    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
        String forwardKey;
        var actionForm = (CustomerStatusActionForm)form;
        var returnUrl = request.getParameter(ParameterConstants.RETURN_URL);
        var customerName = request.getParameter(ParameterConstants.CUSTOMER_NAME);

        if(returnUrl == null) {
            returnUrl = actionForm.getReturnUrl();
        }
        if(customerName == null) {
            customerName = actionForm.getCustomerName();
        }

        if(wasPost(request)) {
            CommandResult commandResult = null;

            if(!wasCanceled(request)) {
                var commandForm = CustomerUtil.getHome().getSetCustomerStatusForm();

                commandForm.setCustomerName(customerName);
                commandForm.setCustomerStatusChoice(actionForm.getCustomerStatusChoice());

                commandResult = CustomerUtil.getHome().setCustomerStatus(getUserVisitPK(request), commandForm);
            }

            if(commandResult != null && commandResult.hasErrors()) {
                setCommandResultAttribute(request, commandResult);
                forwardKey = ForwardConstants.FORM;
            } else {
                forwardKey = ForwardConstants.DISPLAY;
            }
        } else {
            actionForm.setReturnUrl(returnUrl);
            actionForm.setCustomerName(customerName);
            forwardKey = ForwardConstants.FORM;
        }

        if(forwardKey.equals(ForwardConstants.FORM)) {
            setupCustomer(request, customerName);
        }

        return forwardKey.equals(ForwardConstants.DISPLAY)? new ActionForward(returnUrl, true): mapping.findForward(forwardKey);
    }
    
}