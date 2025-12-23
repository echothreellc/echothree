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

package com.echothree.ui.web.main.action.configuration.securityrolegroup;

import com.echothree.control.user.security.common.SecurityUtil;
import com.echothree.control.user.security.common.result.GetSecurityRoleGroupResult;
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
    path = "/Configuration/SecurityRoleGroup/Delete",
    mappingClass = SecureActionMapping.class,
    name = "SecurityRoleGroupDelete",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Configuration/SecurityRoleGroup/Main", redirect = true),
        @SproutForward(name = "Form", path = "/configuration/securityrolegroup/delete.jsp")
    }
)
public class DeleteAction
        extends MainBaseDeleteAction<DeleteActionForm> {

    @Override
    public String getEntityTypeName() {
        return EntityTypes.SecurityRoleGroup.name();
    }
    
    @Override
    public void setupParameters(DeleteActionForm actionForm, HttpServletRequest request) {
        actionForm.setSecurityRoleGroupName(findParameter(request, ParameterConstants.SECURITY_ROLE_GROUP_NAME, actionForm.getSecurityRoleGroupName()));
        actionForm.setParentSecurityRoleGroupName(findParameter(request, ParameterConstants.PARENT_SECURITY_ROLE_GROUP_NAME, actionForm.getParentSecurityRoleGroupName()));
    }
    
    @Override
    public void setupTransfer(DeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = SecurityUtil.getHome().getGetSecurityRoleGroupForm();
        
        commandForm.setSecurityRoleGroupName(actionForm.getSecurityRoleGroupName());

        var commandResult = SecurityUtil.getHome().getSecurityRoleGroup(getUserVisitPK(request), commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (GetSecurityRoleGroupResult)executionResult.getResult();
        
        request.setAttribute(AttributeConstants.SECURITY_ROLE_GROUP, result.getSecurityRoleGroup());
    }
    
    @Override
    public CommandResult doDelete(DeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = SecurityUtil.getHome().getDeleteSecurityRoleGroupForm();

        commandForm.setSecurityRoleGroupName(actionForm.getSecurityRoleGroupName());

        return SecurityUtil.getHome().deleteSecurityRoleGroup(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(DeleteActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.PARENT_SECURITY_ROLE_GROUP_NAME, actionForm.getParentSecurityRoleGroupName());
    }
    
}
