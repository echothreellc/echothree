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

package com.echothree.ui.web.main.action.customer.customer;

import com.echothree.control.user.customer.common.CustomerUtil;
import com.echothree.control.user.customer.common.result.GetCustomerResult;
import com.echothree.control.user.party.common.PartyUtil;
import com.echothree.control.user.party.common.spec.PartySpec;
import com.echothree.control.user.user.common.UserUtil;
import com.echothree.control.user.user.common.edit.RecoveryAnswerEdit;
import com.echothree.control.user.user.common.form.EditRecoveryAnswerForm;
import com.echothree.control.user.user.common.result.EditRecoveryAnswerResult;
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
    path = "/Customer/Customer/CustomerRecoveryAnswerEdit",
    mappingClass = SecureActionMapping.class,
    name = "CustomerRecoveryAnswerEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Customer/Customer/Review", redirect = true),
        @SproutForward(name = "Form", path = "/customer/customer/customerRecoveryAnswerEdit.jsp")
    }
)
public class CustomerRecoveryAnswerEditAction
        extends MainBaseEditAction<CustomerRecoveryAnswerEditActionForm, PartySpec, RecoveryAnswerEdit, EditRecoveryAnswerForm, EditRecoveryAnswerResult> {
    
    @Override
    protected PartySpec getSpec(HttpServletRequest request, CustomerRecoveryAnswerEditActionForm actionForm)
            throws NamingException {
        var spec = PartyUtil.getHome().getPartySpec();
        
        spec.setPartyName(findParameter(request, ParameterConstants.PARTY_NAME, actionForm.getPartyName()));
        
        return spec;
    }
    
    @Override
    protected RecoveryAnswerEdit getEdit(HttpServletRequest request, CustomerRecoveryAnswerEditActionForm actionForm)
            throws NamingException {
        var edit = UserUtil.getHome().getRecoveryAnswerEdit();

        edit.setRecoveryQuestionName(actionForm.getRecoveryQuestionChoice());
        edit.setAnswer(actionForm.getAnswer());

        return edit;
    }
    
    @Override
    protected EditRecoveryAnswerForm getForm()
            throws NamingException {
        return UserUtil.getHome().getEditRecoveryAnswerForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, CustomerRecoveryAnswerEditActionForm actionForm, EditRecoveryAnswerResult result, PartySpec spec, RecoveryAnswerEdit edit) {
        actionForm.setPartyName(spec.getPartyName());
        actionForm.setRecoveryQuestionChoice(edit.getRecoveryQuestionName());
        actionForm.setAnswer(edit.getAnswer());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditRecoveryAnswerForm commandForm)
            throws Exception {
        var commandResult = UserUtil.getHome().editRecoveryAnswer(getUserVisitPK(request), commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (EditRecoveryAnswerResult)executionResult.getResult();

        request.setAttribute(AttributeConstants.RECOVERY_ANSWER, result.getRecoveryAnswer());
        
        return commandResult;
    }
    
    @Override
    public void setupForwardParameters(CustomerRecoveryAnswerEditActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.PARTY_NAME, actionForm.getPartyName());
    }
    
    @Override
    public void setupTransfer(CustomerRecoveryAnswerEditActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = CustomerUtil.getHome().getGetCustomerForm();
        
        commandForm.setPartyName(actionForm.getPartyName());

        var commandResult = CustomerUtil.getHome().getCustomer(getUserVisitPK(request), commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (GetCustomerResult)executionResult.getResult();
        
        request.setAttribute(AttributeConstants.CUSTOMER, result.getCustomer());
    }
    
}