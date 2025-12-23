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
import com.echothree.control.user.payment.common.edit.PartyPaymentMethodEdit;
import com.echothree.control.user.payment.common.form.EditPartyPaymentMethodForm;
import com.echothree.control.user.payment.common.result.EditPartyPaymentMethodResult;
import com.echothree.control.user.payment.common.spec.PartyPaymentMethodSpec;
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
    path = "/Customer/CustomerPaymentMethod/Edit",
    mappingClass = SecureActionMapping.class,
    name = "CustomerPaymentMethodEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Customer/CustomerPaymentMethod/Main", redirect = true),
        @SproutForward(name = "Form", path = "/customer/customerpaymentmethod/edit/edit.jsp")
    }
)
public class EditAction
        extends MainBaseEditAction<EditActionForm, PartyPaymentMethodSpec, PartyPaymentMethodEdit, EditPartyPaymentMethodForm, EditPartyPaymentMethodResult> {
    
    @Override
    protected PartyPaymentMethodSpec getSpec(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var spec = PaymentUtil.getHome().getPartyPaymentMethodSpec();

        spec.setPartyPaymentMethodName(findParameter(request, ParameterConstants.PARTY_PAYMENT_METHOD_NAME, actionForm.getPartyPaymentMethodName()));
        
        return spec;
    }
    
    @Override
    protected PartyPaymentMethodEdit getEdit(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var edit = PaymentUtil.getHome().getPartyPaymentMethodEdit();

        edit.setDescription(actionForm.getDescription());
        edit.setDeleteWhenUnused(actionForm.getDeleteWhenUnused().toString());
        edit.setIsDefault(actionForm.getIsDefault().toString());
        edit.setSortOrder(actionForm.getSortOrder());
        edit.setPersonalTitleId(actionForm.getPersonalTitleChoice());
        edit.setFirstName(actionForm.getFirstName());
        edit.setMiddleName(actionForm.getMiddleName());
        edit.setLastName(actionForm.getLastName());
        edit.setNameSuffixId(actionForm.getNameSuffixChoice());
        edit.setName(actionForm.getName());
        edit.setNumber(actionForm.getNumber());
        edit.setSecurityCode(actionForm.getSecurityCode());
        edit.setExpirationMonth(actionForm.getExpirationMonth());
        edit.setExpirationYear(actionForm.getExpirationYear());
        edit.setBillingContactMechanismName(actionForm.getBillingContactMechanismChoice());
        edit.setIssuerName(actionForm.getIssuerName());
        edit.setIssuerContactMechanismName(actionForm.getIssuerContactMechanismChoice());

        return edit;
    }
    
    @Override
    protected EditPartyPaymentMethodForm getForm()
            throws NamingException {
        return PaymentUtil.getHome().getEditPartyPaymentMethodForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, EditActionForm actionForm, EditPartyPaymentMethodResult result, PartyPaymentMethodSpec spec, PartyPaymentMethodEdit edit) {
        actionForm.setPartyName(request.getParameter(ParameterConstants.PARTY_NAME));
        actionForm.setPartyPaymentMethodName(request.getParameter(ParameterConstants.PARTY_PAYMENT_METHOD_NAME));
        actionForm.setDescription(edit.getDescription());
        actionForm.setDeleteWhenUnused(Boolean.valueOf(edit.getDeleteWhenUnused()));
        actionForm.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        actionForm.setSortOrder(edit.getSortOrder());
        actionForm.setPersonalTitleChoice(edit.getPersonalTitleId());
        actionForm.setFirstName(edit.getFirstName());
        actionForm.setMiddleName(edit.getMiddleName());
        actionForm.setLastName(edit.getLastName());
        actionForm.setNameSuffixChoice(edit.getNameSuffixId());
        actionForm.setName(edit.getName());
        actionForm.setNumber(edit.getNumber());
        actionForm.setSecurityCode(edit.getSecurityCode());
        actionForm.setExpirationMonth(edit.getExpirationMonth());
        actionForm.setExpirationYear(edit.getExpirationYear());
        actionForm.setBillingContactMechanismChoice(edit.getBillingContactMechanismName());
        actionForm.setIssuerName(edit.getIssuerName());
        actionForm.setIssuerContactMechanismChoice(edit.getIssuerContactMechanismName());
    }
    
    public void setupCustomerTransfer(String partyName, HttpServletRequest request)
            throws NamingException {
        var commandForm = CustomerUtil.getHome().getGetCustomerForm();

        commandForm.setPartyName(partyName);

        var commandResult = CustomerUtil.getHome().getCustomer(getUserVisitPK(request), commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (GetCustomerResult)executionResult.getResult();

        request.setAttribute(AttributeConstants.CUSTOMER, result.getCustomer());
    }

    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditPartyPaymentMethodForm commandForm)
            throws Exception {
        var commandResult = PaymentUtil.getHome().editPartyPaymentMethod(getUserVisitPK(request), commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (EditPartyPaymentMethodResult)executionResult.getResult();
        var partyPaymentMethod = result.getPartyPaymentMethod();

        if(partyPaymentMethod != null) {
            request.setAttribute(AttributeConstants.PARTY_PAYMENT_METHOD, partyPaymentMethod);

            setupCustomerTransfer(partyPaymentMethod.getParty().getPartyName(), request);
        }

        return commandResult;
    }
    
    @Override
    public void setupForwardParameters(EditActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.PARTY_NAME, actionForm.getPartyName());
    }

}
