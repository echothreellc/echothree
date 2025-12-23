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
import com.echothree.control.user.core.common.result.GetAppearanceResult;
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
    path = "/Core/Appearance/TextTransformationAdd",
    mappingClass = SecureActionMapping.class,
    name = "AppearanceTextTransformationAdd",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Core/Appearance/TextTransformation", redirect = true),
        @SproutForward(name = "Form", path = "/core/appearance/textTransformationAdd.jsp")
    }
)
public class TextTransformationAddAction
        extends MainBaseAddAction<TextTransformationAddActionForm> {

    @Override
    public void setupParameters(TextTransformationAddActionForm actionForm, HttpServletRequest request) {
        actionForm.setAppearanceName(findParameter(request, ParameterConstants.APPEARANCE_NAME, actionForm.getAppearanceName()));
    }
    
    @Override
    public void setupTransfer(TextTransformationAddActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = CoreUtil.getHome().getGetAppearanceForm();

        commandForm.setAppearanceName(actionForm.getAppearanceName());

        var commandResult = CoreUtil.getHome().getAppearance(getUserVisitPK(request), commandForm);
        if(!commandResult.hasErrors()) {
            var executionResult = commandResult.getExecutionResult();
            var result = (GetAppearanceResult)executionResult.getResult();
            
            request.setAttribute(AttributeConstants.APPEARANCE, result.getAppearance());
        }
    }
    
    @Override
    public CommandResult doAdd(TextTransformationAddActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = CoreUtil.getHome().getCreateAppearanceTextTransformationForm();

        commandForm.setAppearanceName( actionForm.getAppearanceName());
        commandForm.setTextTransformationName(actionForm.getTextTransformationChoice());

        return CoreUtil.getHome().createAppearanceTextTransformation(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(TextTransformationAddActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.APPEARANCE_NAME, actionForm.getAppearanceName());
    }
    
}
