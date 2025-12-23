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

import com.echothree.control.user.comment.common.CommentUtil;
import com.echothree.control.user.customer.common.CustomerUtil;
import com.echothree.control.user.customer.common.result.GetCustomerResult;
import com.echothree.control.user.payment.common.PaymentUtil;
import com.echothree.control.user.payment.common.result.GetPartyPaymentMethodResult;
import com.echothree.model.control.comment.common.CommentConstants;
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
    path = "/Customer/CustomerPaymentMethod/CommentAdd",
    mappingClass = SecureActionMapping.class,
    name = "CustomerPaymentMethodCommentAdd",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Customer/CustomerPaymentMethod/Review", redirect = true),
        @SproutForward(name = "Form", path = "/customer/customerpaymentmethod/commentAdd.jsp")
    }
)
public class CommentAddAction
        extends MainBaseAddAction<CommentAddActionForm> {

    @Override
    public void setupParameters(CommentAddActionForm actionForm, HttpServletRequest request) {
        actionForm.setPartyName(findParameter(request, ParameterConstants.PARTY_NAME, actionForm.getPartyName()));
        actionForm.setPartyPaymentMethodName(findParameter(request, ParameterConstants.PARTY_PAYMENT_METHOD_NAME, actionForm.getPartyPaymentMethodName()));
    }
    
    public String getPartyPaymentMethodEntityRef(CommentAddActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = PaymentUtil.getHome().getGetPartyPaymentMethodForm();
        
        commandForm.setPartyPaymentMethodName(actionForm.getPartyPaymentMethodName());

        var commandResult = PaymentUtil.getHome().getPartyPaymentMethod(getUserVisitPK(request), commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (GetPartyPaymentMethodResult)executionResult.getResult();
        var partyPaymentMethod = result.getPartyPaymentMethod();
        
        request.setAttribute(AttributeConstants.PARTY_PAYMENT_METHOD, partyPaymentMethod);
        
        return partyPaymentMethod.getEntityInstance().getEntityRef();
    }
    
    public void setupCustomerTransfer(CommentAddActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = CustomerUtil.getHome().getGetCustomerForm();

        commandForm.setPartyName(actionForm.getPartyName());

        var commandResult = CustomerUtil.getHome().getCustomer(getUserVisitPK(request), commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (GetCustomerResult)executionResult.getResult();

        request.setAttribute(AttributeConstants.CUSTOMER, result.getCustomer());
    }

    @Override
    public void setupTransfer(CommentAddActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        if(request.getAttribute(AttributeConstants.PARTY_PAYMENT_METHOD) == null) {
            getPartyPaymentMethodEntityRef(actionForm, request);
        }
        
        setupCustomerTransfer(actionForm, request);
    }
    
    @Override
    public CommandResult doAdd(CommentAddActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = CommentUtil.getHome().getCreateCommentForm();

        commandForm.setEntityRef(getPartyPaymentMethodEntityRef(actionForm, request));
        commandForm.setCommentTypeName(CommentConstants.CommentType_PARTY_PAYMENT_METHOD);
        commandForm.setLanguageIsoName(actionForm.getLanguageChoice());
        commandForm.setDescription(actionForm.getDescription());
        commandForm.setMimeTypeName(actionForm.getMimeTypeChoice());
        commandForm.setClobComment(actionForm.getClobComment());

        return CommentUtil.getHome().createComment(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(CommentAddActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.PARTY_NAME, actionForm.getPartyName());
        parameters.put(ParameterConstants.PARTY_PAYMENT_METHOD_NAME, actionForm.getPartyPaymentMethodName());
    }
    
}
