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

package com.echothree.ui.web.main.action.configuration.workeffortscope;

import com.echothree.control.user.workeffort.common.WorkEffortUtil;
import com.echothree.control.user.workeffort.common.result.GetWorkEffortScopeDescriptionResult;
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
    path = "/Configuration/WorkEffortScope/DescriptionDelete",
    mappingClass = SecureActionMapping.class,
    name = "WorkEffortScopeDescriptionDelete",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Configuration/WorkEffortScope/Description", redirect = true),
        @SproutForward(name = "Form", path = "/configuration/workeffortscope/descriptionDelete.jsp")
    }
)
public class DescriptionDeleteAction
        extends MainBaseDeleteAction<DescriptionDeleteActionForm> {

    @Override
    public String getEntityTypeName() {
        return EntityTypes.WorkEffortScopeDescription.name();
    }
    
    @Override
    public void setupParameters(DescriptionDeleteActionForm actionForm, HttpServletRequest request) {
        actionForm.setWorkEffortTypeName(findParameter(request, ParameterConstants.WORK_EFFORT_TYPE_NAME, actionForm.getWorkEffortTypeName()));
        actionForm.setWorkEffortScopeName(findParameter(request, ParameterConstants.WORK_EFFORT_SCOPE_NAME, actionForm.getWorkEffortScopeName()));
        actionForm.setLanguageIsoName(findParameter(request, ParameterConstants.LANGUAGE_ISO_NAME, actionForm.getLanguageIsoName()));
    }
    
    @Override
    public void setupTransfer(DescriptionDeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = WorkEffortUtil.getHome().getGetWorkEffortScopeDescriptionForm();
        
        commandForm.setWorkEffortTypeName(actionForm.getWorkEffortTypeName());
        commandForm.setWorkEffortScopeName(actionForm.getWorkEffortScopeName());
        commandForm.setLanguageIsoName(actionForm.getLanguageIsoName());

        var commandResult = WorkEffortUtil.getHome().getWorkEffortScopeDescription(getUserVisitPK(request), commandForm);
        if(!commandResult.hasErrors()) {
            var executionResult = commandResult.getExecutionResult();
            var result = (GetWorkEffortScopeDescriptionResult)executionResult.getResult();

            request.setAttribute(AttributeConstants.WORK_EFFORT_SCOPE_DESCRIPTION, result.getWorkEffortScopeDescription());
        }
    }
    
    @Override
    public CommandResult doDelete(DescriptionDeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = WorkEffortUtil.getHome().getDeleteWorkEffortScopeDescriptionForm();

        commandForm.setWorkEffortTypeName(actionForm.getWorkEffortTypeName());
        commandForm.setWorkEffortScopeName(actionForm.getWorkEffortScopeName());
        commandForm.setLanguageIsoName(actionForm.getLanguageIsoName());

        return WorkEffortUtil.getHome().deleteWorkEffortScopeDescription(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(DescriptionDeleteActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.WORK_EFFORT_TYPE_NAME, actionForm.getWorkEffortTypeName());
        parameters.put(ParameterConstants.WORK_EFFORT_SCOPE_NAME, actionForm.getWorkEffortScopeName());
    }
    
}
