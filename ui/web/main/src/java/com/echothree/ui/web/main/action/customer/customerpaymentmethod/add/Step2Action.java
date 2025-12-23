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

package com.echothree.ui.web.main.action.customer.customerpaymentmethod.add;

import com.echothree.control.user.customer.common.CustomerUtil;
import com.echothree.control.user.customer.common.result.GetCustomerResult;
import com.echothree.control.user.payment.common.PaymentUtil;
import com.echothree.control.user.payment.common.result.GetPaymentMethodResult;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.MainBaseAddAction;
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
    path = "/Customer/CustomerPaymentMethod/Add/Step2",
    mappingClass = SecureActionMapping.class,
    name = "CustomerPaymentMethodAdd",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Customer/CustomerPaymentMethod/Main", redirect = true),
        @SproutForward(name = "Form", path = "/customer/customerpaymentmethod/add/step2.jsp")
    }
)
public class Step2Action
        extends MainBaseAddAction<Step2ActionForm> {

    @Override
    public void setupParameters(Step2ActionForm actionForm, HttpServletRequest request) {
        actionForm.setPartyName(findParameter(request, ParameterConstants.PARTY_NAME, actionForm.getPartyName()));
        actionForm.setPaymentMethodName(findParameter(request, ParameterConstants.PAYMENT_METHOD_NAME, actionForm.getPaymentMethodName()));
    }
    
    public void setupCustomer(Step2ActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = CustomerUtil.getHome().getGetCustomerForm();

        commandForm.setPartyName(actionForm.getPartyName());

        var commandResult = CustomerUtil.getHome().getCustomer(getUserVisitPK(request), commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (GetCustomerResult)executionResult.getResult();

        request.setAttribute(AttributeConstants.CUSTOMER, result.getCustomer());
    }

    public void setupPaymentMethod(Step2ActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = PaymentUtil.getHome().getGetPaymentMethodForm();

        commandForm.setPaymentMethodName(actionForm.getPaymentMethodName());

        var commandResult = PaymentUtil.getHome().getPaymentMethod(getUserVisitPK(request), commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (GetPaymentMethodResult)executionResult.getResult();

        request.setAttribute(AttributeConstants.PAYMENT_METHOD, result.getPaymentMethod());
    }

    @Override
    public void setupTransfer(Step2ActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        setupCustomer(actionForm, request);
        setupPaymentMethod(actionForm, request);
    }
    
    @Override
    public void setupDefaults(Step2ActionForm actionForm)
            throws NamingException {
        actionForm.setSortOrder("1");
    }

    @Override
    public CommandResult doAdd(Step2ActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = PaymentUtil.getHome().getCreatePartyPaymentMethodForm();

        commandForm.setPartyName(actionForm.getPartyName());
        commandForm.setDescription(actionForm.getDescription());
        commandForm.setPaymentMethodName(actionForm.getPaymentMethodName());
        commandForm.setDeleteWhenUnused(actionForm.getDeleteWhenUnused().toString());
        commandForm.setIsDefault(actionForm.getIsDefault().toString());
        commandForm.setSortOrder(actionForm.getSortOrder());
        commandForm.setPersonalTitleId(actionForm.getPersonalTitleChoice());
        commandForm.setFirstName(actionForm.getFirstName());
        commandForm.setMiddleName(actionForm.getMiddleName());
        commandForm.setLastName(actionForm.getLastName());
        commandForm.setNameSuffixId(actionForm.getNameSuffixChoice());
        commandForm.setName(actionForm.getName());
        commandForm.setNumber(actionForm.getNumber());
        commandForm.setSecurityCode(actionForm.getSecurityCode());
        commandForm.setExpirationMonth(actionForm.getExpirationMonth());
        commandForm.setExpirationYear(actionForm.getExpirationYear());
        commandForm.setBillingContactMechanismName(actionForm.getBillingContactMechanismChoice());
        commandForm.setIssuerName(actionForm.getIssuerName());
        commandForm.setIssuerContactMechanismName(actionForm.getIssuerContactMechanismChoice());

        return PaymentUtil.getHome().createPartyPaymentMethod(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(Step2ActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.PARTY_NAME, actionForm.getPartyName());
    }
    
}
