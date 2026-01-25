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
import com.echothree.control.user.core.common.result.GetAppearanceTextTransformationResult;
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
    path = "/Core/Appearance/TextTransformationDelete",
    mappingClass = SecureActionMapping.class,
    name = "AppearanceTextTransformationDelete",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Core/Appearance/TextTransformation", redirect = true),
        @SproutForward(name = "Form", path = "/core/appearance/textTransformationDelete.jsp")
    }
)
public class TextTransformationDeleteAction
        extends MainBaseDeleteAction<TextTransformationDeleteActionForm> {

    @Override
    public String getEntityTypeName(final TextTransformationDeleteActionForm actionForm) {
        return EntityTypes.AppearanceTextTransformation.name();
    }
    
    @Override
    public void setupParameters(TextTransformationDeleteActionForm actionForm, HttpServletRequest request) {
        actionForm.setAppearanceName(findParameter(request, ParameterConstants.APPEARANCE_NAME, actionForm.getAppearanceName()));
        actionForm.setTextTransformationName(findParameter(request, ParameterConstants.TEXT_TRANSFORMATION_NAME, actionForm.getTextTransformationName()));
    }
    
    @Override
    public void setupTransfer(TextTransformationDeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = CoreUtil.getHome().getGetAppearanceTextTransformationForm();
        
        commandForm.setAppearanceName(actionForm.getAppearanceName());
        commandForm.setTextTransformationName(actionForm.getTextTransformationName());

        var commandResult = CoreUtil.getHome().getAppearanceTextTransformation(getUserVisitPK(request), commandForm);
        if(!commandResult.hasErrors()) {
            var executionResult = commandResult.getExecutionResult();
            var result = (GetAppearanceTextTransformationResult)executionResult.getResult();

            request.setAttribute(AttributeConstants.APPEARANCE_TEXT_TRANSFORMATION, result.getAppearanceTextTransformation());
        }
    }
    
    @Override
    public CommandResult doDelete(TextTransformationDeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = CoreUtil.getHome().getDeleteAppearanceTextTransformationForm();

        commandForm.setAppearanceName(actionForm.getAppearanceName());
        commandForm.setTextTransformationName(actionForm.getTextTransformationName());

        return CoreUtil.getHome().deleteAppearanceTextTransformation(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(TextTransformationDeleteActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.APPEARANCE_NAME, actionForm.getAppearanceName());
    }
    
}
