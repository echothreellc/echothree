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

package com.echothree.ui.web.main.action.purchasing.vendorcontactmechanism;

import com.echothree.control.user.comment.common.CommentUtil;
import com.echothree.control.user.contact.common.ContactUtil;
import com.echothree.control.user.contact.common.result.GetContactMechanismResult;
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
    path = "/Purchasing/VendorContactMechanism/CommentAdd",
    mappingClass = SecureActionMapping.class,
    name = "VendorContactMechanismCommentAdd",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Purchasing/VendorContactMechanism/Review", redirect = true),
        @SproutForward(name = "Form", path = "/purchasing/vendorcontactmechanism/commentAdd.jsp")
    }
)
public class CommentAddAction
        extends MainBaseAddAction<CommentAddActionForm> {

    @Override
    public void setupParameters(CommentAddActionForm actionForm, HttpServletRequest request) {
        actionForm.setPartyName(findParameter(request, ParameterConstants.PARTY_NAME, actionForm.getPartyName()));
        actionForm.setContactMechanismName(findParameter(request, ParameterConstants.CONTACT_MECHANISM_NAME, actionForm.getContactMechanismName()));
    }
    
    public String getContactMechanismEntityRef(CommentAddActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = ContactUtil.getHome().getGetContactMechanismForm();
        
        commandForm.setContactMechanismName(actionForm.getContactMechanismName());

        var commandResult = ContactUtil.getHome().getContactMechanism(getUserVisitPK(request), commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (GetContactMechanismResult)executionResult.getResult();
        var contactMechanism = result.getContactMechanism();
        
        request.setAttribute(AttributeConstants.CONTACT_MECHANISM, contactMechanism);
        
        return contactMechanism.getEntityInstance().getEntityRef();
    }
    
    @Override
    public void setupTransfer(CommentAddActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        if(request.getAttribute(AttributeConstants.CONTACT_MECHANISM) == null) {
            getContactMechanismEntityRef(actionForm, request);
        }

        BaseVendorContactMechanismAction.setupVendor(request, actionForm.getPartyName());
    }
    
    @Override
    public CommandResult doAdd(CommentAddActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = CommentUtil.getHome().getCreateCommentForm();

        commandForm.setEntityRef(getContactMechanismEntityRef(actionForm, request));
        commandForm.setCommentTypeName(CommentConstants.CommentType_CONTACT_MECHANISM);
        commandForm.setLanguageIsoName(actionForm.getLanguageChoice());
        commandForm.setDescription(actionForm.getDescription());
        commandForm.setMimeTypeName(actionForm.getMimeTypeChoice());
        commandForm.setClobComment(actionForm.getClobComment());

        return CommentUtil.getHome().createComment(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(CommentAddActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.PARTY_NAME, actionForm.getPartyName());
        parameters.put(ParameterConstants.CONTACT_MECHANISM_NAME, actionForm.getContactMechanismName());
    }
    
}
