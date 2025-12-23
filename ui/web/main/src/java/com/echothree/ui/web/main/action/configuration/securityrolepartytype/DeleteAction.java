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

package com.echothree.ui.web.main.action.configuration.securityrolepartytype;

import com.echothree.control.user.security.common.SecurityUtil;
import com.echothree.control.user.security.common.result.GetSecurityRolePartyTypeResult;
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
    path = "/Configuration/SecurityRolePartyType/Delete",
    mappingClass = SecureActionMapping.class,
    name = "SecurityRolePartyTypeDelete",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Configuration/SecurityRolePartyType/Main", redirect = true),
        @SproutForward(name = "Form", path = "/configuration/securityrolepartytype/delete.jsp")
    }
)
public class DeleteAction
        extends MainBaseDeleteAction<DeleteActionForm> {

    @Override
    public String getEntityTypeName() {
        return EntityTypes.SecurityRolePartyType.name();
    }
    
    @Override
    public void setupParameters(DeleteActionForm actionForm, HttpServletRequest request) {
        actionForm.setSecurityRoleGroupName(findParameter(request, ParameterConstants.SECURITY_ROLE_GROUP_NAME, actionForm.getSecurityRoleGroupName()));
        actionForm.setSecurityRoleName(findParameter(request, ParameterConstants.SECURITY_ROLE_NAME, actionForm.getSecurityRoleName()));
        actionForm.setPartyTypeName(findParameter(request, ParameterConstants.PARTY_TYPE_NAME, actionForm.getPartyTypeName()));
    }
    
    @Override
    public void setupTransfer(DeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = SecurityUtil.getHome().getGetSecurityRolePartyTypeForm();
        
        commandForm.setSecurityRoleGroupName(actionForm.getSecurityRoleGroupName());
        commandForm.setSecurityRoleName(actionForm.getSecurityRoleName());
        commandForm.setPartyTypeName(actionForm.getPartyTypeName());

        var commandResult = SecurityUtil.getHome().getSecurityRolePartyType(getUserVisitPK(request), commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (GetSecurityRolePartyTypeResult)executionResult.getResult();
        
        request.setAttribute(AttributeConstants.SECURITY_ROLE_PARTY_TYPE, result.getSecurityRolePartyType());
    }
    
    @Override
    public CommandResult doDelete(DeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = SecurityUtil.getHome().getDeleteSecurityRolePartyTypeForm();

        commandForm.setSecurityRoleGroupName(actionForm.getSecurityRoleGroupName());
        commandForm.setSecurityRoleName(actionForm.getSecurityRoleName());
        commandForm.setPartyTypeName(actionForm.getPartyTypeName());

        return SecurityUtil.getHome().deleteSecurityRolePartyType(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(DeleteActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.SECURITY_ROLE_GROUP_NAME, actionForm.getSecurityRoleGroupName());
        parameters.put(ParameterConstants.SECURITY_ROLE_NAME, actionForm.getSecurityRoleName());
    }
    
}
