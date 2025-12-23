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

package com.echothree.ui.web.main.action.customer.customerpaymentmethod;

import com.echothree.control.user.customer.common.CustomerUtil;
import com.echothree.control.user.customer.common.result.GetCustomerResult;
import com.echothree.control.user.payment.common.PaymentUtil;
import com.echothree.control.user.payment.common.result.GetPartyPaymentMethodResult;
import com.echothree.model.control.comment.common.CommentOptions;
import com.echothree.model.control.payment.common.PaymentOptions;
import com.echothree.model.control.payment.common.transfer.PartyPaymentMethodTransfer;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import java.util.HashSet;
import java.util.Set;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/Customer/CustomerPaymentMethod/Review",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/customer/customerpaymentmethod/review.jsp")
    }
)
public class ReviewAction
        extends MainBaseAction<ActionForm> {

    public void setupCustomerTransfer(HttpServletRequest request, HttpServletResponse response)
            throws NamingException {
        var commandForm = CustomerUtil.getHome().getGetCustomerForm();

        commandForm.setPartyName(request.getParameter(ParameterConstants.PARTY_NAME));

        var commandResult = CustomerUtil.getHome().getCustomer(getUserVisitPK(request), commandForm);

        if(!commandResult.hasErrors()) {
            var executionResult = commandResult.getExecutionResult();
            var result = (GetCustomerResult)executionResult.getResult();
            var customer = result.getCustomer();

            if(customer != null) {
                request.setAttribute(AttributeConstants.CUSTOMER, customer);
            }
        }
    }

    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey;
        var commandForm = PaymentUtil.getHome().getGetPartyPaymentMethodForm();
        var partyPaymentMethodName = request.getParameter(ParameterConstants.PARTY_PAYMENT_METHOD_NAME);

        commandForm.setPartyPaymentMethodName(partyPaymentMethodName);

        Set<String> options = new HashSet<>();
        options.add(CommentOptions.CommentIncludeClob);
        options.add(PaymentOptions.PartyPaymentMethodIncludeComments);
        options.add(PaymentOptions.PartyPaymentMethodIncludeNumber);
        options.add(PaymentOptions.PartyPaymentMethodIncludePartyPaymentMethodContactMechanisms);
        commandForm.setOptions(options);

        var commandResult = PaymentUtil.getHome().getPartyPaymentMethod(getUserVisitPK(request), commandForm);
        PartyPaymentMethodTransfer partyPaymentMethod = null;

        if(!commandResult.hasErrors()) {
            var executionResult = commandResult.getExecutionResult();
            var result = (GetPartyPaymentMethodResult)executionResult.getResult();
            partyPaymentMethod = result.getPartyPaymentMethod();
        }

        if(partyPaymentMethod == null) {
            forwardKey = ForwardConstants.ERROR_404;
        } else {
            request.setAttribute(AttributeConstants.PARTY_PAYMENT_METHOD, partyPaymentMethod);
            setupCustomerTransfer(request, response);
            forwardKey = ForwardConstants.DISPLAY;
        }

        return mapping.findForward(forwardKey);
    }
    
}