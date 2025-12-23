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

package com.echothree.ui.web.main.action.payment.paymentmethod;

import com.echothree.control.user.payment.common.PaymentUtil;
import com.echothree.control.user.payment.common.edit.PaymentMethodDescriptionEdit;
import com.echothree.control.user.payment.common.form.EditPaymentMethodDescriptionForm;
import com.echothree.control.user.payment.common.result.EditPaymentMethodDescriptionResult;
import com.echothree.control.user.payment.common.result.GetPaymentMethodResult;
import com.echothree.control.user.payment.common.spec.PaymentMethodDescriptionSpec;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.MainBaseEditAction;
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
    path = "/Payment/PaymentMethod/DescriptionEdit",
    mappingClass = SecureActionMapping.class,
    name = "PaymentMethodDescriptionEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Payment/PaymentMethod/Description", redirect = true),
        @SproutForward(name = "Form", path = "/payment/paymentmethod/descriptionEdit.jsp")
    }
)
public class DescriptionEditAction
        extends MainBaseEditAction<DescriptionEditActionForm, PaymentMethodDescriptionSpec, PaymentMethodDescriptionEdit, EditPaymentMethodDescriptionForm, EditPaymentMethodDescriptionResult> {
    
    @Override
    protected PaymentMethodDescriptionSpec getSpec(HttpServletRequest request, DescriptionEditActionForm actionForm)
            throws NamingException {
        var spec = PaymentUtil.getHome().getPaymentMethodDescriptionSpec();
        
        spec.setPaymentMethodName(findParameter(request, ParameterConstants.PAYMENT_METHOD_NAME, actionForm.getPaymentMethodName()));
        spec.setLanguageIsoName(findParameter(request, ParameterConstants.LANGUAGE_ISO_NAME, actionForm.getLanguageIsoName()));
        
        return spec;
    }
    
    @Override
    protected PaymentMethodDescriptionEdit getEdit(HttpServletRequest request, DescriptionEditActionForm actionForm)
            throws NamingException {
        var edit = PaymentUtil.getHome().getPaymentMethodDescriptionEdit();

        edit.setDescription(actionForm.getDescription());

        return edit;
    }
    
    @Override
    protected EditPaymentMethodDescriptionForm getForm()
            throws NamingException {
        return PaymentUtil.getHome().getEditPaymentMethodDescriptionForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, DescriptionEditActionForm actionForm, EditPaymentMethodDescriptionResult result, PaymentMethodDescriptionSpec spec, PaymentMethodDescriptionEdit edit) {
        actionForm.setPaymentMethodName(spec.getPaymentMethodName());
        actionForm.setLanguageIsoName(spec.getLanguageIsoName());
        actionForm.setDescription(edit.getDescription());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditPaymentMethodDescriptionForm commandForm)
            throws Exception {
        var commandResult = PaymentUtil.getHome().editPaymentMethodDescription(getUserVisitPK(request), commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (EditPaymentMethodDescriptionResult)executionResult.getResult();

        var paymentMethodDescription = result.getPaymentMethodDescription();
        if(paymentMethodDescription != null) {
            request.setAttribute(AttributeConstants.PAYMENT_METHOD, paymentMethodDescription.getPaymentMethod());
        }
        
        return commandResult;
    }
    
    @Override
    public void setupForwardParameters(DescriptionEditActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.PAYMENT_METHOD_NAME, actionForm.getPaymentMethodName());
    }
    
    @Override
    public void setupTransfer(DescriptionEditActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = PaymentUtil.getHome().getGetPaymentMethodForm();

        commandForm.setPaymentMethodName(actionForm.getPaymentMethodName());

        var commandResult = PaymentUtil.getHome().getPaymentMethod(getUserVisitPK(request), commandForm);
        if(!commandResult.hasErrors()) {
            var executionResult = commandResult.getExecutionResult();
            var result = (GetPaymentMethodResult)executionResult.getResult();
            
            request.setAttribute(AttributeConstants.PAYMENT_METHOD, result.getPaymentMethod());
        }
    }
    
    @Override
    protected void setupTransferForForm(HttpServletRequest request, DescriptionEditActionForm actionForm, EditPaymentMethodDescriptionResult result) {
        request.setAttribute(AttributeConstants.PAYMENT_METHOD_DESCRIPTION, result.getPaymentMethodDescription());
    }

}
