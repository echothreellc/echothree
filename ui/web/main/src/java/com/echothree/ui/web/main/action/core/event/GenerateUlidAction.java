// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

package com.echothree.ui.web.main.action.core.event;

import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.control.user.core.common.form.GenerateUlidForm;
import com.echothree.control.user.core.common.result.GenerateUlidResult;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.ExecutionResult;
import com.echothree.view.client.web.struts.CustomActionForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/Core/Event/GenerateUlid",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Core/Event/Main", redirect = true)
    }
)
public class GenerateUlidAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey = null;
        GenerateUlidForm commandForm = CoreUtil.getHome().getGenerateUlidForm();
        String entityRef = request.getParameter(ParameterConstants.ENTITY_REF);
        String key = request.getParameter(ParameterConstants.KEY);
        String guid = request.getParameter(ParameterConstants.GUID);
        String ulid = request.getParameter(ParameterConstants.ULID);

        commandForm.setEntityRef(entityRef);
        commandForm.setKey(key);
        commandForm.setGuid(guid);
        commandForm.setUlid(ulid);
        commandForm.setForceRegeneration(request.getParameter(ParameterConstants.FORCE_REGENERATION));

        CommandResult commandResult = CoreUtil.getHome().generateUlid(getUserVisitPK(request), commandForm);

        if(!commandResult.hasErrors()) {
            ExecutionResult executionResult = commandResult.getExecutionResult();
            GenerateUlidResult result = (GenerateUlidResult)executionResult.getResult();
            
            ulid = result.getUlid();
            
            forwardKey = ForwardConstants.DISPLAY;
        }
        
        CustomActionForward customActionForward = new CustomActionForward(mapping.findForward(forwardKey == null ? ForwardConstants.ERROR_404 : forwardKey));

        if(forwardKey != null) {
            Map<String, String> parameters = new HashMap<>(1);
            parameters.put(ParameterConstants.ULID, ulid);

            customActionForward.setParameters(parameters);
        }
        
        return customActionForward;
    }
    
}