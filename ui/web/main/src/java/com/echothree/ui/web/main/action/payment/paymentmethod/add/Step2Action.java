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

package com.echothree.ui.web.main.action.payment.paymentmethod.add;

import com.echothree.control.user.payment.common.PaymentUtil;
import com.echothree.control.user.payment.common.result.GetPaymentMethodTypeResult;
import com.echothree.model.control.payment.common.transfer.PaymentMethodTypeTransfer;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.view.client.web.struts.CustomActionForward;
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
    path = "/Payment/PaymentMethod/Add/Step2",
    mappingClass = SecureActionMapping.class,
    name = "PaymentMethodAddStep2",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Payment/PaymentMethod/Main", redirect = true),
        @SproutForward(name = "Form", path = "/payment/paymentmethod/add/step2.jsp")
    }
)
public class Step2Action
        extends MainBaseAction<ActionForm> {
    
    private PaymentMethodTypeTransfer getPaymentMethodTypeTransfer(UserVisitPK userVisitPK, String paymentMethodTypeName)
            throws NamingException {
        var commandForm = PaymentUtil.getHome().getGetPaymentMethodTypeForm();
        
        commandForm.setPaymentMethodTypeName(paymentMethodTypeName);

        var commandResult = PaymentUtil.getHome().getPaymentMethodType(userVisitPK, commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (GetPaymentMethodTypeResult)executionResult.getResult();
        
        return result.getPaymentMethodType();
    }
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey;
        PaymentMethodTypeTransfer paymentMethodType;
        var userVisitPK = getUserVisitPK(request);
        var actionForm = (Step2ActionForm)form;
        var paymentMethodTypeName = request.getParameter(ParameterConstants.PAYMENT_METHOD_TYPE_NAME);

        if(paymentMethodTypeName == null)
            paymentMethodTypeName = actionForm.getPaymentMethodTypeName();

        paymentMethodType = getPaymentMethodTypeTransfer(userVisitPK, paymentMethodTypeName);

        if(wasPost(request)) {
            var commandForm = PaymentUtil.getHome().getCreatePaymentMethodForm();

            commandForm.setPaymentMethodName(actionForm.getPaymentMethodName());
            commandForm.setPaymentMethodTypeName(actionForm.getPaymentMethodTypeName());
            commandForm.setPaymentProcessorName(actionForm.getPaymentProcessorChoice());
            commandForm.setItemSelectorName(actionForm.getItemSelectorChoice());
            commandForm.setSalesOrderItemSelectorName(actionForm.getSalesOrderItemSelectorChoice());
            commandForm.setIsDefault(actionForm.getIsDefault().toString());
            commandForm.setSortOrder(actionForm.getSortOrder());
            commandForm.setDescription(actionForm.getDescription());
            commandForm.setRequestNameOnCard(actionForm.getRequestNameOnCard().toString());
            commandForm.setRequireNameOnCard(actionForm.getRequireNameOnCard().toString());
            commandForm.setCheckCardNumber(actionForm.getCheckCardNumber().toString());
            commandForm.setRequestExpirationDate(actionForm.getRequestExpirationDate().toString());
            commandForm.setRequireExpirationDate(actionForm.getRequireExpirationDate().toString());
            commandForm.setCheckExpirationDate(actionForm.getCheckExpirationDate().toString());
            commandForm.setRequestSecurityCode(actionForm.getRequestSecurityCode().toString());
            commandForm.setRequireSecurityCode(actionForm.getRequireSecurityCode().toString());
            commandForm.setCardNumberValidationPattern(actionForm.getCardNumberValidationPattern());
            commandForm.setSecurityCodeValidationPattern(actionForm.getSecurityCodeValidationPattern());
            commandForm.setRetainCreditCard(actionForm.getRetainCreditCard().toString());
            commandForm.setRetainSecurityCode(actionForm.getRetainSecurityCode().toString());
            commandForm.setRequestBilling(actionForm.getRequestBilling().toString());
            commandForm.setRequireBilling(actionForm.getRequireBilling().toString());
            commandForm.setRequestIssuer(actionForm.getRequestIssuer().toString());
            commandForm.setRequireIssuer(actionForm.getRequireIssuer().toString());
            commandForm.setHoldDays(actionForm.getHoldDays());

            var commandResult = PaymentUtil.getHome().createPaymentMethod(userVisitPK, commandForm);

            if(commandResult.hasErrors()) {
                setCommandResultAttribute(request, commandResult);
                forwardKey = ForwardConstants.FORM;
            } else {
                forwardKey = ForwardConstants.DISPLAY;
            }
        } else {
            actionForm.setSortOrder("1");
            actionForm.setPaymentMethodTypeName(paymentMethodTypeName);
            forwardKey = ForwardConstants.FORM;
        }

        var customActionForward = new CustomActionForward(mapping.findForward(forwardKey));
        if(forwardKey.equals(ForwardConstants.FORM)) {
            request.setAttribute(AttributeConstants.PAYMENT_METHOD_TYPE, paymentMethodType);
        }
        
        return customActionForward;
    }
    
}
