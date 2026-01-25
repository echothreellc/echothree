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

package com.echothree.ui.web.main.action.core.appearance;

import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.control.user.core.common.result.GetAppearanceTextDecorationResult;
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
    path = "/Core/Appearance/TextDecorationDelete",
    mappingClass = SecureActionMapping.class,
    name = "AppearanceTextDecorationDelete",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Core/Appearance/TextDecoration", redirect = true),
        @SproutForward(name = "Form", path = "/core/appearance/textDecorationDelete.jsp")
    }
)
public class TextDecorationDeleteAction
        extends MainBaseDeleteAction<TextDecorationDeleteActionForm> {

    @Override
    public String getEntityTypeName(final TextDecorationDeleteActionForm actionForm) {
        return EntityTypes.AppearanceTextDecoration.name();
    }
    
    @Override
    public void setupParameters(TextDecorationDeleteActionForm actionForm, HttpServletRequest request) {
        actionForm.setAppearanceName(findParameter(request, ParameterConstants.APPEARANCE_NAME, actionForm.getAppearanceName()));
        actionForm.setTextDecorationName(findParameter(request, ParameterConstants.TEXT_DECORATION_NAME, actionForm.getTextDecorationName()));
    }
    
    @Override
    public void setupTransfer(TextDecorationDeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = CoreUtil.getHome().getGetAppearanceTextDecorationForm();
        
        commandForm.setAppearanceName(actionForm.getAppearanceName());
        commandForm.setTextDecorationName(actionForm.getTextDecorationName());

        var commandResult = CoreUtil.getHome().getAppearanceTextDecoration(getUserVisitPK(request), commandForm);
        if(!commandResult.hasErrors()) {
            var executionResult = commandResult.getExecutionResult();
            var result = (GetAppearanceTextDecorationResult)executionResult.getResult();

            request.setAttribute(AttributeConstants.APPEARANCE_TEXT_DECORATION, result.getAppearanceTextDecoration());
        }
    }
    
    @Override
    public CommandResult doDelete(TextDecorationDeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = CoreUtil.getHome().getDeleteAppearanceTextDecorationForm();

        commandForm.setAppearanceName(actionForm.getAppearanceName());
        commandForm.setTextDecorationName(actionForm.getTextDecorationName());

        return CoreUtil.getHome().deleteAppearanceTextDecoration(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(TextDecorationDeleteActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.APPEARANCE_NAME, actionForm.getAppearanceName());
    }
    
}
