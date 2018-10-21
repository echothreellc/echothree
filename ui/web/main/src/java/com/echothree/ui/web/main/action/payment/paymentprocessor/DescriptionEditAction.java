// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

package com.echothree.ui.web.main.action.payment.paymentprocessor;

import com.echothree.control.user.payment.common.PaymentUtil;
import com.echothree.control.user.payment.remote.edit.PaymentProcessorDescriptionEdit;
import com.echothree.control.user.payment.remote.form.EditPaymentProcessorDescriptionForm;
import com.echothree.control.user.payment.remote.form.GetPaymentProcessorForm;
import com.echothree.control.user.payment.remote.result.EditPaymentProcessorDescriptionResult;
import com.echothree.control.user.payment.remote.result.GetPaymentProcessorResult;
import com.echothree.control.user.payment.remote.spec.PaymentProcessorDescriptionSpec;
import com.echothree.model.control.payment.remote.transfer.PaymentProcessorDescriptionTransfer;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.MainBaseEditAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.remote.command.CommandResult;
import com.echothree.util.remote.command.ExecutionResult;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

@SproutAction(
    path = "/Payment/PaymentProcessor/DescriptionEdit",
    mappingClass = SecureActionMapping.class,
    name = "PaymentProcessorDescriptionEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Payment/PaymentProcessor/Description", redirect = true),
        @SproutForward(name = "Form", path = "/payment/paymentprocessor/descriptionEdit.jsp")
    }
)
public class DescriptionEditAction
        extends MainBaseEditAction<DescriptionEditActionForm, PaymentProcessorDescriptionSpec, PaymentProcessorDescriptionEdit, EditPaymentProcessorDescriptionForm, EditPaymentProcessorDescriptionResult> {
    
    @Override
    protected PaymentProcessorDescriptionSpec getSpec(HttpServletRequest request, DescriptionEditActionForm actionForm)
            throws NamingException {
        PaymentProcessorDescriptionSpec spec = PaymentUtil.getHome().getPaymentProcessorDescriptionSpec();
        
        spec.setPaymentProcessorName(findParameter(request, ParameterConstants.PAYMENT_PROCESSOR_NAME, actionForm.getPaymentProcessorName()));
        spec.setLanguageIsoName(findParameter(request, ParameterConstants.LANGUAGE_ISO_NAME, actionForm.getLanguageIsoName()));
        
        return spec;
    }
    
    @Override
    protected PaymentProcessorDescriptionEdit getEdit(HttpServletRequest request, DescriptionEditActionForm actionForm)
            throws NamingException {
        PaymentProcessorDescriptionEdit edit = PaymentUtil.getHome().getPaymentProcessorDescriptionEdit();

        edit.setDescription(actionForm.getDescription());

        return edit;
    }
    
    @Override
    protected EditPaymentProcessorDescriptionForm getForm()
            throws NamingException {
        return PaymentUtil.getHome().getEditPaymentProcessorDescriptionForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, DescriptionEditActionForm actionForm, EditPaymentProcessorDescriptionResult result, PaymentProcessorDescriptionSpec spec, PaymentProcessorDescriptionEdit edit) {
        actionForm.setPaymentProcessorName(spec.getPaymentProcessorName());
        actionForm.setLanguageIsoName(spec.getLanguageIsoName());
        actionForm.setDescription(edit.getDescription());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditPaymentProcessorDescriptionForm commandForm)
            throws Exception {
        CommandResult commandResult = PaymentUtil.getHome().editPaymentProcessorDescription(getUserVisitPK(request), commandForm);
        ExecutionResult executionResult = commandResult.getExecutionResult();
        EditPaymentProcessorDescriptionResult result = (EditPaymentProcessorDescriptionResult)executionResult.getResult();

        PaymentProcessorDescriptionTransfer paymentProcessorDescription = result.getPaymentProcessorDescription();
        if(paymentProcessorDescription != null) {
            request.setAttribute(AttributeConstants.PAYMENT_PROCESSOR, paymentProcessorDescription.getPaymentProcessor());
        }
        
        return commandResult;
    }
    
    @Override
    public void setupForwardParameters(DescriptionEditActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.PAYMENT_PROCESSOR_NAME, actionForm.getPaymentProcessorName());
    }
    
    @Override
    public void setupTransfer(DescriptionEditActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        GetPaymentProcessorForm commandForm = PaymentUtil.getHome().getGetPaymentProcessorForm();

        commandForm.setPaymentProcessorName(actionForm.getPaymentProcessorName());
        
        CommandResult commandResult = PaymentUtil.getHome().getPaymentProcessor(getUserVisitPK(request), commandForm);
        if(!commandResult.hasErrors()) {
            ExecutionResult executionResult = commandResult.getExecutionResult();
            GetPaymentProcessorResult result = (GetPaymentProcessorResult)executionResult.getResult();
            
            request.setAttribute(AttributeConstants.PAYMENT_PROCESSOR, result.getPaymentProcessor());
        }
    }
    
    @Override
    protected void setupTransferForForm(HttpServletRequest request, DescriptionEditActionForm actionForm, EditPaymentProcessorDescriptionResult result) {
        request.setAttribute(AttributeConstants.PAYMENT_PROCESSOR_DESCRIPTION, result.getPaymentProcessorDescription());
    }

}
