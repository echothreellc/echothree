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
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.MainBaseDeleteAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.CommandResult;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

@SproutAction(
    path = "/Customer/CustomerPaymentMethod/Delete",
    mappingClass = SecureActionMapping.class,
    name = "CustomerPaymentMethodDelete",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Customer/CustomerPaymentMethod/Main", redirect = true),
        @SproutForward(name = "Form", path = "/customer/customerpaymentmethod/delete.jsp")
    }
)
public class DeleteAction
        extends MainBaseDeleteAction<DeleteActionForm> {
    
    @Override
    public String getEntityTypeName(final DeleteActionForm actionForm) {
        return EntityTypes.PartyPaymentMethod.name();
    }
    
    @Override
    public void setupParameters(DeleteActionForm actionForm, HttpServletRequest request) {
        actionForm.setPartyName(findParameter(request, ParameterConstants.PARTY_NAME, actionForm.getPartyName()));
        actionForm.setPartyPaymentMethodName(findParameter(request, ParameterConstants.PARTY_PAYMENT_METHOD_NAME, actionForm.getPartyPaymentMethodName()));
    }
    
    public void setupCustomer(HttpServletRequest request, String partyName)
            throws NamingException {
        var commandForm = CustomerUtil.getHome().getGetCustomerForm();

        commandForm.setPartyName(partyName);

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

    public void setupPartyPaymentMethodTransfer(HttpServletRequest request, String partyPaymentMethodName)
            throws NamingException {
        var commandForm = PaymentUtil.getHome().getGetPartyPaymentMethodForm();

        commandForm.setPartyPaymentMethodName(partyPaymentMethodName);

        var commandResult = PaymentUtil.getHome().getPartyPaymentMethod(getUserVisitPK(request), commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (GetPartyPaymentMethodResult)executionResult.getResult();

        request.setAttribute(AttributeConstants.PARTY_PAYMENT_METHOD, result.getPartyPaymentMethod());
    }

    @Override
    public void setupTransfer(DeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        setupPartyPaymentMethodTransfer(request, actionForm.getPartyPaymentMethodName());
        setupCustomer(request, actionForm.getPartyName());
    }
    
    @Override
    public CommandResult doDelete(DeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = PaymentUtil.getHome().getDeletePartyPaymentMethodForm();
        
        commandForm.setPartyPaymentMethodName(actionForm.getPartyPaymentMethodName());

        return PaymentUtil.getHome().deletePartyPaymentMethod(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(DeleteActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.PARTY_NAME, actionForm.getPartyName());
    }
    
}
