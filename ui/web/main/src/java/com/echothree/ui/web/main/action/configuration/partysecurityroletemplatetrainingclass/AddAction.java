// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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

package com.echothree.ui.web.main.action.configuration.partysecurityroletemplatetrainingclass;

import com.echothree.control.user.security.common.SecurityUtil;
import com.echothree.control.user.security.common.form.CreatePartySecurityRoleTemplateTrainingClassForm;
import com.echothree.control.user.security.common.form.GetPartySecurityRoleTemplateForm;
import com.echothree.control.user.security.common.result.GetPartySecurityRoleTemplateResult;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.MainBaseAddAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.ExecutionResult;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

@SproutAction(
    path = "/Configuration/PartySecurityRoleTemplateTrainingClass/Add",
    mappingClass = SecureActionMapping.class,
    name = "PartySecurityRoleTemplateTrainingClassAdd",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Configuration/PartySecurityRoleTemplateTrainingClass/Main", redirect = true),
        @SproutForward(name = "Form", path = "/configuration/partysecurityroletemplatetrainingclass/add.jsp")
    }
)
public class AddAction
        extends MainBaseAddAction<AddActionForm> {

    @Override
    public void setupParameters(AddActionForm actionForm, HttpServletRequest request) {
        actionForm.setPartySecurityRoleTemplateName(findParameter(request, ParameterConstants.PARTY_SECURITY_ROLE_TEMPLATE_NAME, actionForm.getPartySecurityRoleTemplateName()));
    }
    
    @Override
    public void setupTransfer(AddActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        GetPartySecurityRoleTemplateForm commandForm = SecurityUtil.getHome().getGetPartySecurityRoleTemplateForm();

        commandForm.setPartySecurityRoleTemplateName(actionForm.getPartySecurityRoleTemplateName());

        CommandResult commandResult = SecurityUtil.getHome().getPartySecurityRoleTemplate(getUserVisitPK(request), commandForm);
        if(!commandResult.hasErrors()) {
            ExecutionResult executionResult = commandResult.getExecutionResult();
            GetPartySecurityRoleTemplateResult result = (GetPartySecurityRoleTemplateResult)executionResult.getResult();

            request.setAttribute(AttributeConstants.PARTY_SECURITY_ROLE_TEMPLATE, result.getPartySecurityRoleTemplate());
        }
    }
    
    @Override
    public CommandResult doAdd(AddActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        CreatePartySecurityRoleTemplateTrainingClassForm commandForm = SecurityUtil.getHome().getCreatePartySecurityRoleTemplateTrainingClassForm();

        commandForm.setPartySecurityRoleTemplateName(actionForm.getPartySecurityRoleTemplateName());
        commandForm.setTrainingClassName(actionForm.getTrainingClassChoice());

        return SecurityUtil.getHome().createPartySecurityRoleTemplateTrainingClass(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(AddActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.PARTY_SECURITY_ROLE_TEMPLATE_NAME, actionForm.getPartySecurityRoleTemplateName());
    }
    
}
