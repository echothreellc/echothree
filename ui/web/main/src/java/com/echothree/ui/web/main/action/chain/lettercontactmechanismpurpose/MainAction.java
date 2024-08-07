// --------------------------------------------------------------------------------
// Copyright 2002-2024 Echo Three, LLC
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
import com.echothree.control.user.letter.common.form.GetLetterContactMechanismPurposesForm;
import com.echothree.control.user.letter.common.result.GetLetterContactMechanismPurposesResult;
import com.echothree.model.control.letter.common.transfer.LetterTransfer;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.ExecutionResult;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/Chain/LetterContactMechanismPurpose/Main",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/chain/lettercontactmechanismpurpose/main.jsp")
    }
)
public class MainAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey = null;
        GetLetterContactMechanismPurposesForm commandForm = LetterUtil.getHome().getGetLetterContactMechanismPurposesForm();
        String chainKindName = request.getParameter(ParameterConstants.CHAIN_KIND_NAME);
        String chainTypeName = request.getParameter(ParameterConstants.CHAIN_TYPE_NAME);
        String letterName = request.getParameter(ParameterConstants.LETTER_NAME);
        
        commandForm.setChainKindName(chainKindName);
        commandForm.setChainTypeName(chainTypeName);
        commandForm.setLetterName(letterName);
        
        CommandResult commandResult = LetterUtil.getHome().getLetterContactMechanismPurposes(getUserVisitPK(request), commandForm);
        ExecutionResult executionResult = commandResult.getExecutionResult();
        GetLetterContactMechanismPurposesResult result = (GetLetterContactMechanismPurposesResult)executionResult.getResult();
        LetterTransfer letter = result.getLetter();
        
        if(letter == null) {
            forwardKey = ForwardConstants.ERROR_404;
        } else {
            request.setAttribute(AttributeConstants.LETTER, result.getLetter());
            request.setAttribute(AttributeConstants.LETTER_CONTACT_MECHANISM_PURPOSES, result.getLetterContactMechanismPurposes());
            forwardKey = ForwardConstants.DISPLAY;
        }
        
        return mapping.findForward(forwardKey);
    }
    
}
