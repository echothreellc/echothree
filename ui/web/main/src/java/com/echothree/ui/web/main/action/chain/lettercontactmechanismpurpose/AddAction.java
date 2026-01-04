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

package com.echothree.ui.web.main.action.chain.lettercontactmechanismpurpose;

import com.echothree.control.user.letter.common.LetterUtil;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.view.client.web.struts.CustomActionForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/Chain/LetterContactMechanismPurpose/Add",
    mappingClass = SecureActionMapping.class,
    name = "LetterContactMechanismPurposeAdd",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Chain/LetterContactMechanismPurpose/Main", redirect = true),
        @SproutForward(name = "Form", path = "/chain/lettercontactmechanismpurpose/add.jsp")
    }
)
public class AddAction
        extends MainBaseAction<AddActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, AddActionForm actionForm, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey;
        var chainKindName = request.getParameter(ParameterConstants.CHAIN_KIND_NAME);
        var chainTypeName = request.getParameter(ParameterConstants.CHAIN_TYPE_NAME);
        var letterName = request.getParameter(ParameterConstants.LETTER_NAME);
        
        if(wasPost(request)) {
            var commandForm = LetterUtil.getHome().getCreateLetterContactMechanismPurposeForm();
            
            if(chainKindName == null) {
                chainKindName = actionForm.getChainKindName();
            }
            
            if(chainTypeName == null) {
                chainTypeName = actionForm.getChainTypeName();
            }
            
            if(letterName == null) {
                letterName = actionForm.getLetterName();
            }
            
            commandForm.setChainKindName(chainKindName);
            commandForm.setChainTypeName(chainTypeName);
            commandForm.setLetterName(actionForm.getLetterName());
            commandForm.setPriority(actionForm.getPriority());
            commandForm.setContactMechanismPurposeName(actionForm.getContactMechanismPurposeChoice());

            var commandResult = LetterUtil.getHome().createLetterContactMechanismPurpose(getUserVisitPK(request), commandForm);
            
            if(commandResult.hasErrors()) {
                setCommandResultAttribute(request, commandResult);
                forwardKey = ForwardConstants.FORM;
            } else {
                forwardKey = ForwardConstants.DISPLAY;
            }
        } else {
            actionForm.setChainKindName(chainKindName);
            actionForm.setChainTypeName(chainTypeName);
            actionForm.setLetterName(letterName);
            forwardKey = ForwardConstants.FORM;
        }

        var customActionForward = new CustomActionForward(mapping.findForward(forwardKey));
        if(forwardKey.equals(ForwardConstants.FORM)) {
            request.setAttribute(AttributeConstants.CHAIN_KIND_NAME, chainKindName);
            request.setAttribute(AttributeConstants.CHAIN_TYPE_NAME, chainTypeName);
            request.setAttribute(AttributeConstants.LETTER_NAME, letterName);
        } else if(forwardKey.equals(ForwardConstants.DISPLAY)) {
            Map<String, String> parameters = new HashMap<>(3);
            
            parameters.put(ParameterConstants.CHAIN_KIND_NAME, chainKindName);
            parameters.put(ParameterConstants.CHAIN_TYPE_NAME, chainTypeName);
            parameters.put(ParameterConstants.LETTER_NAME, letterName);
            customActionForward.setParameters(parameters);
        }
        
        return customActionForward;
    }
    
}
