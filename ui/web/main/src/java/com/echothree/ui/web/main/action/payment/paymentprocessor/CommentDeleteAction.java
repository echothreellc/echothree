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

import com.echothree.control.user.comment.common.CommentUtil;
import com.echothree.control.user.comment.common.result.GetCommentResult;
import com.echothree.control.user.payment.common.PaymentUtil;
import com.echothree.control.user.payment.common.result.GetPaymentProcessorResult;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.MainBaseDeleteAction;
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
    path = "/Payment/PaymentProcessor/CommentDelete",
    mappingClass = SecureActionMapping.class,
    name = "PaymentProcessorCommentDelete",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Payment/PaymentProcessor/Review", redirect = true),
        @SproutForward(name = "Form", path = "/payment/paymentprocessor/commentDelete.jsp")
    }
)
public class CommentDeleteAction
        extends MainBaseDeleteAction<CommentDeleteActionForm> {
    
    @Override
    public String getEntityTypeName() {
        return EntityTypes.Comment.name();
    }
    
    @Override
    public void setupParameters(CommentDeleteActionForm actionForm, HttpServletRequest request) {
        actionForm.setPaymentProcessorName(findParameter(request, ParameterConstants.PAYMENT_PROCESSOR_NAME, actionForm.getPaymentProcessorName()));
        actionForm.setCommentName(findParameter(request, ParameterConstants.COMMENT_NAME, actionForm.getCommentName()));
    }
    
    public void setupPaymentProcessorTransfer(CommentDeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = PaymentUtil.getHome().getGetPaymentProcessorForm();
        
        commandForm.setPaymentProcessorName(actionForm.getPaymentProcessorName());

        var commandResult = PaymentUtil.getHome().getPaymentProcessor(getUserVisitPK(request), commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (GetPaymentProcessorResult)executionResult.getResult();
        
        request.setAttribute(AttributeConstants.PAYMENT_PROCESSOR, result.getPaymentProcessor());
    }
    
    public void setupCommentTransfer(CommentDeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = CommentUtil.getHome().getGetCommentForm();
        
        commandForm.setCommentName(actionForm.getCommentName());

        var commandResult = CommentUtil.getHome().getComment(getUserVisitPK(request), commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (GetCommentResult)executionResult.getResult();
        
        request.setAttribute(AttributeConstants.COMMENT, result.getComment());
    }
    
    @Override
    public void setupTransfer(CommentDeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        setupPaymentProcessorTransfer(actionForm, request);
        setupCommentTransfer(actionForm, request);
    }
    
    @Override
    public CommandResult doDelete(CommentDeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = CommentUtil.getHome().getDeleteCommentForm();
        
        commandForm.setCommentName(actionForm.getCommentName());

        return CommentUtil.getHome().deleteComment(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(CommentDeleteActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.PAYMENT_PROCESSOR_NAME, actionForm.getPaymentProcessorName());
    }
    
}
