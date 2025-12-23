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

package com.echothree.ui.web.main.action.payment.paymentprocessor;

import com.echothree.control.user.payment.common.PaymentUtil;
import com.echothree.control.user.payment.common.edit.PaymentProcessorEdit;
import com.echothree.control.user.payment.common.form.EditPaymentProcessorForm;
import com.echothree.control.user.payment.common.result.EditPaymentProcessorResult;
import com.echothree.control.user.payment.common.spec.PaymentProcessorSpec;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.MainBaseEditAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.CommandResult;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

@SproutAction(
    path = "/Payment/PaymentProcessor/Edit",
    mappingClass = SecureActionMapping.class,
    name = "PaymentProcessorEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Payment/PaymentProcessor/Main", redirect = true),
        @SproutForward(name = "Form", path = "/payment/paymentprocessor/edit/edit.jsp")
    }
)
public class EditAction
        extends MainBaseEditAction<EditActionForm, PaymentProcessorSpec, PaymentProcessorEdit, EditPaymentProcessorForm, EditPaymentProcessorResult> {
    
    @Override
    protected PaymentProcessorSpec getSpec(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var spec = PaymentUtil.getHome().getPaymentProcessorSpec();
        var originalPaymentProcessorName = request.getParameter(ParameterConstants.ORIGINAL_PAYMENT_PROCESSOR_NAME);

        if(originalPaymentProcessorName == null) {
            originalPaymentProcessorName = actionForm.getOriginalPaymentProcessorName();
        }

        spec.setPaymentProcessorName(originalPaymentProcessorName);
        
        return spec;
    }
    
    @Override
    protected PaymentProcessorEdit getEdit(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var edit = PaymentUtil.getHome().getPaymentProcessorEdit();

        edit.setPaymentProcessorName(actionForm.getPaymentProcessorName());
        edit.setIsDefault(actionForm.getIsDefault().toString());
        edit.setSortOrder(actionForm.getSortOrder());
        edit.setDescription(actionForm.getDescription());

        return edit;
    }
    
    @Override
    protected EditPaymentProcessorForm getForm()
            throws NamingException {
        return PaymentUtil.getHome().getEditPaymentProcessorForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, EditActionForm actionForm, EditPaymentProcessorResult result, PaymentProcessorSpec spec, PaymentProcessorEdit edit) {
        actionForm.setOriginalPaymentProcessorName(spec.getPaymentProcessorName());
        actionForm.setPaymentProcessorName(edit.getPaymentProcessorName());
        actionForm.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        actionForm.setSortOrder(edit.getSortOrder());
        actionForm.setDescription(edit.getDescription());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditPaymentProcessorForm commandForm)
            throws Exception {
        return PaymentUtil.getHome().editPaymentProcessor(getUserVisitPK(request), commandForm);
    }
    

    @Override
    protected void setupTransferForForm(HttpServletRequest request, EditActionForm actionForm, EditPaymentProcessorResult result) {
        request.setAttribute(AttributeConstants.PAYMENT_PROCESSOR, result.getPaymentProcessor());
    }

}
