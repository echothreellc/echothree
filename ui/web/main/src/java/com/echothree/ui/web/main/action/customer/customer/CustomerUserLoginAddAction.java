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
import com.echothree.control.user.user.common.UserUtil;
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
    path = "/Customer/Customer/CustomerUserLoginAdd",
    mappingClass = SecureActionMapping.class,
    name = "CustomerUserLoginAdd",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Customer/Customer/Review", redirect = true),
        @SproutForward(name = "Form", path = "/customer/customer/customerUserLoginAdd.jsp")
    }
)
public class CustomerUserLoginAddAction
        extends MainBaseAddAction<CustomerUserLoginAddActionForm> {
    
    @Override
    public void setupParameters(CustomerUserLoginAddActionForm actionForm, HttpServletRequest request) {
        actionForm.setPartyName(findParameter(request, ParameterConstants.PARTY_NAME, actionForm.getPartyName()));
    }

    @Override
    public void setupTransfer(CustomerUserLoginAddActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = CustomerUtil.getHome().getGetCustomerForm();
        
        commandForm.setPartyName(actionForm.getPartyName());

        var commandResult = CustomerUtil.getHome().getCustomer(getUserVisitPK(request), commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (GetCustomerResult)executionResult.getResult();
        
        request.setAttribute(AttributeConstants.CUSTOMER, result.getCustomer());
    }
    
    @Override
    public CommandResult doAdd(CustomerUserLoginAddActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = UserUtil.getHome().getCreateUserLoginForm();

        commandForm.setPartyName(actionForm.getPartyName());
        commandForm.setUsername(actionForm.getUsername());
        commandForm.setPassword1(actionForm.getPassword1());
        commandForm.setPassword2(actionForm.getPassword2());
        commandForm.setRecoveryQuestionName(actionForm.getRecoveryQuestionChoice());
        commandForm.setAnswer(actionForm.getAnswer());

        return UserUtil.getHome().createUserLogin(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(CustomerUserLoginAddActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.PARTY_NAME, actionForm.getPartyName());
    }
    
}