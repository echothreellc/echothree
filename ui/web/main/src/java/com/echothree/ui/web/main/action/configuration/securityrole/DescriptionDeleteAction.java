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

package com.echothree.ui.web.main.action.configuration.securityrole;

import com.echothree.control.user.security.common.SecurityUtil;
import com.echothree.control.user.security.common.result.GetSecurityRoleDescriptionResult;
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
    path = "/Configuration/SecurityRole/DescriptionDelete",
    mappingClass = SecureActionMapping.class,
    name = "SecurityRoleDescriptionDelete",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Configuration/SecurityRole/Description", redirect = true),
        @SproutForward(name = "Form", path = "/configuration/securityrole/descriptionDelete.jsp")
    }
)
public class DescriptionDeleteAction
        extends MainBaseDeleteAction<DescriptionDeleteActionForm> {

    @Override
    public String getEntityTypeName(final DescriptionDeleteActionForm actionForm) {
        return EntityTypes.SecurityRoleDescription.name();
    }
    
    @Override
    public void setupParameters(DescriptionDeleteActionForm actionForm, HttpServletRequest request) {
        actionForm.setSecurityRoleGroupName(findParameter(request, ParameterConstants.SECURITY_ROLE_GROUP_NAME, actionForm.getSecurityRoleGroupName()));
        actionForm.setSecurityRoleName(findParameter(request, ParameterConstants.SECURITY_ROLE_NAME, actionForm.getSecurityRoleName()));
        actionForm.setLanguageIsoName(findParameter(request, ParameterConstants.LANGUAGE_ISO_NAME, actionForm.getLanguageIsoName()));
    }
    
    @Override
    public void setupTransfer(DescriptionDeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = SecurityUtil.getHome().getGetSecurityRoleDescriptionForm();
        
        commandForm.setSecurityRoleGroupName(actionForm.getSecurityRoleGroupName());
        commandForm.setSecurityRoleName(actionForm.getSecurityRoleName());
        commandForm.setLanguageIsoName(actionForm.getLanguageIsoName());

        var commandResult = SecurityUtil.getHome().getSecurityRoleDescription(getUserVisitPK(request), commandForm);
        if(!commandResult.hasErrors()) {
            var executionResult = commandResult.getExecutionResult();
            var result = (GetSecurityRoleDescriptionResult)executionResult.getResult();

            request.setAttribute(AttributeConstants.SECURITY_ROLE_DESCRIPTION, result.getSecurityRoleDescription());
        }
    }
    
    @Override
    public CommandResult doDelete(DescriptionDeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = SecurityUtil.getHome().getDeleteSecurityRoleDescriptionForm();

        commandForm.setSecurityRoleGroupName(actionForm.getSecurityRoleGroupName());
        commandForm.setSecurityRoleName(actionForm.getSecurityRoleName());
        commandForm.setLanguageIsoName(actionForm.getLanguageIsoName());

        return SecurityUtil.getHome().deleteSecurityRoleDescription(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(DescriptionDeleteActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.SECURITY_ROLE_GROUP_NAME, actionForm.getSecurityRoleGroupName());
        parameters.put(ParameterConstants.SECURITY_ROLE_NAME, actionForm.getSecurityRoleName());
    }
    
}
