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

package com.echothree.ui.web.main.action.payment.paymentprocessor.add;

import com.echothree.control.user.payment.common.PaymentUtil;
import com.echothree.control.user.payment.common.result.GetPaymentProcessorTypeResult;
import com.echothree.model.control.payment.common.transfer.PaymentProcessorTypeTransfer;
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
    path = "/Payment/PaymentProcessor/Add/Step2",
    mappingClass = SecureActionMapping.class,
    name = "PaymentProcessorAddStep2",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Payment/PaymentProcessor/Main", redirect = true),
        @SproutForward(name = "Form", path = "/payment/paymentprocessor/add/step2.jsp")
    }
)
public class Step2Action
        extends MainBaseAction<ActionForm> {
    
    private PaymentProcessorTypeTransfer getPaymentProcessorTypeTransfer(UserVisitPK userVisitPK, String paymentProcessorTypeName)
            throws NamingException {
        var commandForm = PaymentUtil.getHome().getGetPaymentProcessorTypeForm();
        
        commandForm.setPaymentProcessorTypeName(paymentProcessorTypeName);

        var commandResult = PaymentUtil.getHome().getPaymentProcessorType(userVisitPK, commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (GetPaymentProcessorTypeResult)executionResult.getResult();
        
        return result.getPaymentProcessorType();
    }
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey;
        PaymentProcessorTypeTransfer paymentProcessorType;
        var userVisitPK = getUserVisitPK(request);
        var actionForm = (Step2ActionForm)form;
        var paymentProcessorTypeName = request.getParameter(ParameterConstants.PAYMENT_PROCESSOR_TYPE_NAME);

        if(paymentProcessorTypeName == null)
            paymentProcessorTypeName = actionForm.getPaymentProcessorTypeName();

        paymentProcessorType = getPaymentProcessorTypeTransfer(userVisitPK, paymentProcessorTypeName);

        if(wasPost(request)) {
            var commandForm = PaymentUtil.getHome().getCreatePaymentProcessorForm();

            commandForm.setPaymentProcessorName(actionForm.getPaymentProcessorName());
            commandForm.setPaymentProcessorTypeName(actionForm.getPaymentProcessorTypeName());
            commandForm.setIsDefault(actionForm.getIsDefault().toString());
            commandForm.setSortOrder(actionForm.getSortOrder());
            commandForm.setDescription(actionForm.getDescription());

            var commandResult = PaymentUtil.getHome().createPaymentProcessor(userVisitPK, commandForm);

            if(commandResult.hasErrors()) {
                setCommandResultAttribute(request, commandResult);
                forwardKey = ForwardConstants.FORM;
            } else {
                forwardKey = ForwardConstants.DISPLAY;
            }
        } else {
            actionForm.setSortOrder("1");
            actionForm.setPaymentProcessorTypeName(paymentProcessorTypeName);
            forwardKey = ForwardConstants.FORM;
        }

        var customActionForward = new CustomActionForward(mapping.findForward(forwardKey));
        if(forwardKey.equals(ForwardConstants.FORM)) {
            request.setAttribute(AttributeConstants.PAYMENT_PROCESSOR_TYPE, paymentProcessorType);
        }
        
        return customActionForward;
    }
    
}