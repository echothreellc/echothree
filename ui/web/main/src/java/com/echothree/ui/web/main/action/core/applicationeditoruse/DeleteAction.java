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

package com.echothree.ui.web.main.action.core.applicationeditoruse;

import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.control.user.core.common.result.GetApplicationEditorUseResult;
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
    path = "/Core/ApplicationEditorUse/Delete",
    mappingClass = SecureActionMapping.class,
    name = "ApplicationEditorUseDelete",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Core/ApplicationEditorUse/Main", redirect = true),
        @SproutForward(name = "Form", path = "/core/applicationeditoruse/delete.jsp")
    }
)
public class DeleteAction
        extends MainBaseDeleteAction<DeleteActionForm> {

    @Override
    public String getEntityTypeName() {
        return EntityTypes.ApplicationEditorUse.name();
    }
    
    @Override
    public void setupParameters(DeleteActionForm actionForm, HttpServletRequest request) {
        actionForm.setApplicationName(findParameter(request, ParameterConstants.APPLICATION_NAME, actionForm.getApplicationName()));
        actionForm.setApplicationEditorUseName(findParameter(request, ParameterConstants.APPLICATION_EDITOR_USE_NAME, actionForm.getApplicationEditorUseName()));
    }
    
    @Override
    public void setupTransfer(DeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = CoreUtil.getHome().getGetApplicationEditorUseForm();
        
        commandForm.setApplicationName(actionForm.getApplicationName());
        commandForm.setApplicationEditorUseName(actionForm.getApplicationEditorUseName());

        var commandResult = CoreUtil.getHome().getApplicationEditorUse(getUserVisitPK(request), commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (GetApplicationEditorUseResult)executionResult.getResult();
        
        request.setAttribute(AttributeConstants.APPLICATION_EDITOR_USE, result.getApplicationEditorUse());
    }
    
    @Override
    public CommandResult doDelete(DeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = CoreUtil.getHome().getDeleteApplicationEditorUseForm();

        commandForm.setApplicationName(actionForm.getApplicationName());
        commandForm.setApplicationEditorUseName(actionForm.getApplicationEditorUseName());

        return CoreUtil.getHome().deleteApplicationEditorUse(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(DeleteActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.APPLICATION_NAME, actionForm.getApplicationName());
    }
    
}
