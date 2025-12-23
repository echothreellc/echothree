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

package com.echothree.ui.web.main.action.shipping.shippingmethod;

import com.echothree.control.user.comment.common.CommentUtil;
import com.echothree.control.user.shipping.common.ShippingUtil;
import com.echothree.control.user.shipping.common.result.GetShippingMethodResult;
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
    path = "/Shipping/ShippingMethod/CommentAdd",
    mappingClass = SecureActionMapping.class,
    name = "ShippingMethodCommentAdd",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Shipping/ShippingMethod/Review", redirect = true),
        @SproutForward(name = "Form", path = "/shipping/shippingmethod/commentAdd.jsp")
    }
)
public class CommentAddAction
        extends MainBaseAddAction<CommentAddActionForm> {

    @Override
    public void setupParameters(CommentAddActionForm actionForm, HttpServletRequest request) {
        actionForm.setShippingMethodName(findParameter(request, ParameterConstants.SHIPPING_METHOD_NAME, actionForm.getShippingMethodName()));
    }
    
    public String getShippingMethodEntityRef(CommentAddActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = ShippingUtil.getHome().getGetShippingMethodForm();
        
        commandForm.setShippingMethodName(actionForm.getShippingMethodName());

        var commandResult = ShippingUtil.getHome().getShippingMethod(getUserVisitPK(request), commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (GetShippingMethodResult)executionResult.getResult();
        var shippingMethod = result.getShippingMethod();
        
        request.setAttribute(AttributeConstants.SHIPPING_METHOD, shippingMethod);
        
        return shippingMethod.getEntityInstance().getEntityRef();
    }
    
    @Override
    public void setupTransfer(CommentAddActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        if(request.getAttribute(AttributeConstants.SHIPPING_METHOD) == null) {
            getShippingMethodEntityRef(actionForm, request);
        }
    }
    
    @Override
    public CommandResult doAdd(CommentAddActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = CommentUtil.getHome().getCreateCommentForm();

        commandForm.setEntityRef(getShippingMethodEntityRef(actionForm, request));
        commandForm.setCommentTypeName(CommentConstants.CommentType_SHIPPING_METHOD);
        commandForm.setLanguageIsoName(actionForm.getLanguageChoice());
        commandForm.setDescription(actionForm.getDescription());
        commandForm.setMimeTypeName(actionForm.getMimeTypeChoice());
        commandForm.setClobComment(actionForm.getClobComment());

        return CommentUtil.getHome().createComment(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(CommentAddActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.SHIPPING_METHOD_NAME, actionForm.getShippingMethodName());
    }
    
}
