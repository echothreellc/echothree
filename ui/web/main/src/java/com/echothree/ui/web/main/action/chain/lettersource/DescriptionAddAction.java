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

package com.echothree.ui.web.main.action.chain.lettersource;

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
    path = "/Chain/LetterSource/DescriptionAdd",
    mappingClass = SecureActionMapping.class,
    name = "LetterSourceDescriptionAdd",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Chain/LetterSource/Description", redirect = true),
        @SproutForward(name = "Form", path = "/chain/lettersource/descriptionAdd.jsp")
    }
)
public class DescriptionAddAction
        extends MainBaseAction<DescriptionAddActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, DescriptionAddActionForm actionForm, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey;
        var letterSourceName = request.getParameter(ParameterConstants.LETTER_SOURCE_NAME);
        
        if(wasPost(request)) {
            var commandForm = LetterUtil.getHome().getCreateLetterSourceDescriptionForm();
            
            if(letterSourceName == null) {
                letterSourceName = actionForm.getLetterSourceName();
            }
            
            commandForm.setLetterSourceName(letterSourceName);
            commandForm.setLanguageIsoName(actionForm.getLanguageChoice());
            commandForm.setDescription(actionForm.getDescription());

            var commandResult = LetterUtil.getHome().createLetterSourceDescription(getUserVisitPK(request), commandForm);
            
            if(commandResult.hasErrors()) {
                setCommandResultAttribute(request, commandResult);
                forwardKey = ForwardConstants.FORM;
            } else {
                forwardKey = ForwardConstants.DISPLAY;
            }
        } else {
            actionForm.setLetterSourceName(letterSourceName);
            forwardKey = ForwardConstants.FORM;
        }

        var customActionForward = new CustomActionForward(mapping.findForward(forwardKey));
        if(forwardKey.equals(ForwardConstants.FORM)) {
            request.setAttribute(AttributeConstants.LETTER_SOURCE_NAME, letterSourceName);
        } else if(forwardKey.equals(ForwardConstants.DISPLAY)) {
            Map<String, String> parameters = new HashMap<>(1);
            parameters.put(ParameterConstants.LETTER_SOURCE_NAME, letterSourceName);
            customActionForward.setParameters(parameters);
        }
        
        return customActionForward;
    }
    
}