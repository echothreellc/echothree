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
import com.echothree.control.user.payment.common.edit.PaymentMethodEdit;
import com.echothree.control.user.payment.common.form.EditPaymentMethodForm;
import com.echothree.control.user.payment.common.result.EditPaymentMethodResult;
import com.echothree.control.user.payment.common.spec.PaymentMethodSpec;
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
    path = "/Payment/PaymentMethod/Edit",
    mappingClass = SecureActionMapping.class,
    name = "PaymentMethodEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Payment/PaymentMethod/Main", redirect = true),
        @SproutForward(name = "Form", path = "/payment/paymentmethod/edit/edit.jsp")
    }
)
public class EditAction
        extends MainBaseEditAction<EditActionForm, PaymentMethodSpec, PaymentMethodEdit, EditPaymentMethodForm, EditPaymentMethodResult> {
    
    @Override
    protected PaymentMethodSpec getSpec(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var spec = PaymentUtil.getHome().getPaymentMethodSpec();
        var originalPaymentMethodName = request.getParameter(ParameterConstants.ORIGINAL_PAYMENT_METHOD_NAME);

        if(originalPaymentMethodName == null) {
            originalPaymentMethodName = actionForm.getOriginalPaymentMethodName();
        }

        spec.setPaymentMethodName(originalPaymentMethodName);

        return spec;
    }

    @Override
    protected PaymentMethodEdit getEdit(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var edit = PaymentUtil.getHome().getPaymentMethodEdit();

        edit.setPaymentMethodName(actionForm.getPaymentMethodName());
        edit.setItemSelectorName(actionForm.getItemSelectorChoice());
        edit.setSalesOrderItemSelectorName(actionForm.getSalesOrderItemSelectorChoice());
        edit.setIsDefault(actionForm.getIsDefault().toString());
        edit.setSortOrder(actionForm.getSortOrder());
        edit.setDescription(actionForm.getDescription());
        edit.setRequestNameOnCard(actionForm.getRequestNameOnCard().toString());
        edit.setRequireNameOnCard(actionForm.getRequireNameOnCard().toString());
        edit.setCheckCardNumber(actionForm.getCheckCardNumber().toString());
        edit.setRequestExpirationDate(actionForm.getRequestExpirationDate().toString());
        edit.setRequireExpirationDate(actionForm.getRequireExpirationDate().toString());
        edit.setCheckExpirationDate(actionForm.getCheckExpirationDate().toString());
        edit.setRequestSecurityCode(actionForm.getRequestSecurityCode().toString());
        edit.setRequireSecurityCode(actionForm.getRequireSecurityCode().toString());
        edit.setCardNumberValidationPattern(actionForm.getCardNumberValidationPattern());
        edit.setSecurityCodeValidationPattern(actionForm.getSecurityCodeValidationPattern());
        edit.setRetainCreditCard(actionForm.getRetainCreditCard().toString());
        edit.setRetainSecurityCode(actionForm.getRetainSecurityCode().toString());
        edit.setRequestBilling(actionForm.getRequestBilling().toString());
        edit.setRequireBilling(actionForm.getRequireBilling().toString());
        edit.setRequestIssuer(actionForm.getRequestIssuer().toString());
        edit.setRequireIssuer(actionForm.getRequireIssuer().toString());
        edit.setHoldDays(actionForm.getHoldDays());

        return edit;
    }

    @Override
    protected EditPaymentMethodForm getForm()
            throws NamingException {
        return PaymentUtil.getHome().getEditPaymentMethodForm();
    }

    @Override
    protected void setupActionForm(HttpServletRequest request, EditActionForm actionForm, EditPaymentMethodResult result, PaymentMethodSpec spec, PaymentMethodEdit edit) {
        actionForm.setOriginalPaymentMethodName(edit.getPaymentMethodName());
        actionForm.setPaymentMethodName(edit.getPaymentMethodName());
        actionForm.setItemSelectorChoice(edit.getItemSelectorName());
        actionForm.setSalesOrderItemSelectorChoice(edit.getSalesOrderItemSelectorName());
        actionForm.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        actionForm.setSortOrder(edit.getSortOrder());
        actionForm.setDescription(edit.getDescription());
        actionForm.setRequestNameOnCard(Boolean.valueOf(edit.getRequestNameOnCard()));
        actionForm.setRequireNameOnCard(Boolean.valueOf(edit.getRequireNameOnCard()));
        actionForm.setCheckCardNumber(Boolean.valueOf(edit.getCheckCardNumber()));
        actionForm.setRequestExpirationDate(Boolean.valueOf(edit.getRequestExpirationDate()));
        actionForm.setRequireExpirationDate(Boolean.valueOf(edit.getRequireExpirationDate()));
        actionForm.setCheckExpirationDate(Boolean.valueOf(edit.getCheckExpirationDate()));
        actionForm.setRequestSecurityCode(Boolean.valueOf(edit.getRequestSecurityCode()));
        actionForm.setRequireSecurityCode(Boolean.valueOf(edit.getRequireSecurityCode()));
        actionForm.setCardNumberValidationPattern(edit.getCardNumberValidationPattern());
        actionForm.setSecurityCodeValidationPattern(edit.getSecurityCodeValidationPattern());
        actionForm.setRetainCreditCard(Boolean.valueOf(edit.getRetainCreditCard()));
        actionForm.setRetainSecurityCode(Boolean.valueOf(edit.getRetainSecurityCode()));
        actionForm.setRequestBilling(Boolean.valueOf(edit.getRequestBilling()));
        actionForm.setRequireBilling(Boolean.valueOf(edit.getRequireBilling()));
        actionForm.setRequestIssuer(Boolean.valueOf(edit.getRequestIssuer()));
        actionForm.setRequireIssuer(Boolean.valueOf(edit.getRequireIssuer()));
        actionForm.setHoldDays(edit.getHoldDays());
    }

    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditPaymentMethodForm commandForm)
            throws Exception {
        return PaymentUtil.getHome().editPaymentMethod(getUserVisitPK(request), commandForm);
    }

    @Override
    protected void setupTransferForForm(HttpServletRequest request, EditActionForm actionForm, EditPaymentMethodResult result) {
        request.setAttribute(AttributeConstants.PAYMENT_METHOD, result.getPaymentMethod());
    }

}
