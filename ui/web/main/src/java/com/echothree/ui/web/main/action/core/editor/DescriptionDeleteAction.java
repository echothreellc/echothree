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

package com.echothree.ui.web.main.action.core.editor;

import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.control.user.core.common.result.GetEditorDescriptionResult;
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
    path = "/Core/Editor/DescriptionDelete",
    mappingClass = SecureActionMapping.class,
    name = "EditorDescriptionDelete",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Core/Editor/Description", redirect = true),
        @SproutForward(name = "Form", path = "/core/editor/descriptionDelete.jsp")
    }
)
public class DescriptionDeleteAction
        extends MainBaseDeleteAction<DescriptionDeleteActionForm> {

    @Override
    public String getEntityTypeName(final DescriptionDeleteActionForm actionForm) {
        return EntityTypes.EditorDescription.name();
    }
    
    @Override
    public void setupParameters(DescriptionDeleteActionForm actionForm, HttpServletRequest request) {
        actionForm.setEditorName(findParameter(request, ParameterConstants.EDITOR_NAME, actionForm.getEditorName()));
        actionForm.setLanguageIsoName(findParameter(request, ParameterConstants.LANGUAGE_ISO_NAME, actionForm.getLanguageIsoName()));
    }
    
    @Override
    public void setupTransfer(DescriptionDeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = CoreUtil.getHome().getGetEditorDescriptionForm();
        
        commandForm.setEditorName(actionForm.getEditorName());
        commandForm.setLanguageIsoName(actionForm.getLanguageIsoName());

        var commandResult = CoreUtil.getHome().getEditorDescription(getUserVisitPK(request), commandForm);
        if(!commandResult.hasErrors()) {
            var executionResult = commandResult.getExecutionResult();
            var result = (GetEditorDescriptionResult)executionResult.getResult();

            request.setAttribute(AttributeConstants.EDITOR_DESCRIPTION, result.getEditorDescription());
        }
    }
    
    @Override
    public CommandResult doDelete(DescriptionDeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = CoreUtil.getHome().getDeleteEditorDescriptionForm();

        commandForm.setEditorName(actionForm.getEditorName());
        commandForm.setLanguageIsoName(actionForm.getLanguageIsoName());

        return CoreUtil.getHome().deleteEditorDescription(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(DescriptionDeleteActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.EDITOR_NAME, actionForm.getEditorName());
    }
    
}
