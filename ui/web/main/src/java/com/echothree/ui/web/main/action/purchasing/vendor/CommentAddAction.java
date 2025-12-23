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

package com.echothree.ui.web.main.action.purchasing.vendor;

import com.echothree.control.user.comment.common.CommentUtil;
import com.echothree.control.user.comment.common.result.GetCommentTypeResult;
import com.echothree.control.user.vendor.common.VendorUtil;
import com.echothree.control.user.vendor.common.result.GetVendorResult;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
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
    path = "/Purchasing/Vendor/CommentAdd",
    mappingClass = SecureActionMapping.class,
    name = "VendorCommentAdd",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Purchasing/Vendor/Review", redirect = true),
        @SproutForward(name = "Form", path = "/purchasing/vendor/commentAdd.jsp")
    }
)
public class CommentAddAction
        extends MainBaseAddAction<CommentAddActionForm> {

    @Override
    public void setupParameters(CommentAddActionForm actionForm, HttpServletRequest request) {
        actionForm.setPartyName(findParameter(request, ParameterConstants.PARTY_NAME, actionForm.getPartyName()));
        actionForm.setCommentTypeName(findParameter(request, ParameterConstants.COMMENT_TYPE_NAME, actionForm.getCommentTypeName()));
    }
    
    public String getVendorEntityRef(CommentAddActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = VendorUtil.getHome().getGetVendorForm();
        
        commandForm.setPartyName(actionForm.getPartyName());

        var commandResult = VendorUtil.getHome().getVendor(getUserVisitPK(request), commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (GetVendorResult)executionResult.getResult();
        var vendor = result.getVendor();
        
        request.setAttribute(AttributeConstants.VENDOR, vendor);
        
        return vendor.getEntityInstance().getEntityRef();
    }
    
    public void setupCommentTypeTransfer(CommentAddActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = CommentUtil.getHome().getGetCommentTypeForm();
        
        commandForm.setComponentVendorName(ComponentVendors.ECHO_THREE.name());
        commandForm.setEntityTypeName(EntityTypes.Party.name());
        commandForm.setCommentTypeName(actionForm.getCommentTypeName());

        var commandResult = CommentUtil.getHome().getCommentType(getUserVisitPK(request), commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (GetCommentTypeResult)executionResult.getResult();
        
        request.setAttribute(AttributeConstants.COMMENT_TYPE, result.getCommentType());
    }
    
    @Override
    public void setupTransfer(CommentAddActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        if(request.getAttribute(AttributeConstants.VENDOR) == null) {
            getVendorEntityRef(actionForm, request);
        }
        
        setupCommentTypeTransfer(actionForm, request);
    }
    
    @Override
    public CommandResult doAdd(CommentAddActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = CommentUtil.getHome().getCreateCommentForm();

        commandForm.setEntityRef(getVendorEntityRef(actionForm, request));
        commandForm.setCommentTypeName(actionForm.getCommentTypeName());
        commandForm.setLanguageIsoName(actionForm.getLanguageChoice());
        commandForm.setDescription(actionForm.getDescription());
        commandForm.setMimeTypeName(actionForm.getMimeTypeChoice());
        commandForm.setClobComment(actionForm.getClobComment());

        return CommentUtil.getHome().createComment(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(CommentAddActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.PARTY_NAME, actionForm.getPartyName());
    }
    
}
