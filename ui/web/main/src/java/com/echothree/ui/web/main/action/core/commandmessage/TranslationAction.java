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

package com.echothree.ui.web.main.action.core.commandmessage;

import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.control.user.core.common.result.GetCommandMessageTranslationsResult;
import com.echothree.model.control.core.common.transfer.CommandMessageTransfer;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
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
    path = "/Core/CommandMessage/Translation",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/core/commandmessage/translation.jsp")
    }
)
public class TranslationAction
        extends MainBaseAction<ActionForm> {

    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey;
        var commandForm = CoreUtil.getHome().getGetCommandMessageTranslationsForm();

        commandForm.setCommandMessageTypeName(request.getParameter(ParameterConstants.COMMAND_MESSAGE_TYPE_NAME));
        commandForm.setCommandMessageKey(request.getParameter(ParameterConstants.COMMAND_MESSAGE_KEY));

        var commandResult = CoreUtil.getHome().getCommandMessageTranslations(getUserVisitPK(request), commandForm);
        GetCommandMessageTranslationsResult result = null;
        CommandMessageTransfer trainingClassSection = null;
        
        if(!commandResult.hasErrors()) {
            var executionResult = commandResult.getExecutionResult();
            
            result = (GetCommandMessageTranslationsResult) executionResult.getResult();
            trainingClassSection = result.getCommandMessage();
            
            forwardKey = ForwardConstants.DISPLAY;
        } else {
            forwardKey = ForwardConstants.ERROR_404;
        }
        
        if(trainingClassSection == null) {
            forwardKey = ForwardConstants.ERROR_404;
        } else {
            request.setAttribute(AttributeConstants.COMMAND_MESSAGE, trainingClassSection);
            request.setAttribute(AttributeConstants.COMMAND_MESSAGE_TRANSLATIONS, result.getCommandMessageTranslations());
            forwardKey = ForwardConstants.DISPLAY;
        }

        return mapping.findForward(forwardKey);
    }
    
}
