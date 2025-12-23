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
import com.echothree.control.user.security.common.edit.SecurityRoleDescriptionEdit;
import com.echothree.control.user.security.common.form.EditSecurityRoleDescriptionForm;
import com.echothree.control.user.security.common.result.EditSecurityRoleDescriptionResult;
import com.echothree.control.user.security.common.spec.SecurityRoleDescriptionSpec;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.MainBaseEditAction;
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
    path = "/Configuration/SecurityRole/DescriptionEdit",
    mappingClass = SecureActionMapping.class,
    name = "SecurityRoleDescriptionEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Configuration/SecurityRole/Description", redirect = true),
        @SproutForward(name = "Form", path = "/configuration/securityrole/descriptionEdit.jsp")
    }
)
public class DescriptionEditAction
        extends MainBaseEditAction<DescriptionEditActionForm, SecurityRoleDescriptionSpec, SecurityRoleDescriptionEdit, EditSecurityRoleDescriptionForm, EditSecurityRoleDescriptionResult> {
    
    @Override
    protected SecurityRoleDescriptionSpec getSpec(HttpServletRequest request, DescriptionEditActionForm actionForm)
            throws NamingException {
        var spec = SecurityUtil.getHome().getSecurityRoleDescriptionSpec();
        
        spec.setSecurityRoleGroupName(findParameter(request, ParameterConstants.SECURITY_ROLE_GROUP_NAME, actionForm.getSecurityRoleGroupName()));
        spec.setSecurityRoleName(findParameter(request, ParameterConstants.SECURITY_ROLE_NAME, actionForm.getSecurityRoleName()));
        spec.setLanguageIsoName(findParameter(request, ParameterConstants.LANGUAGE_ISO_NAME, actionForm.getLanguageIsoName()));
        
        return spec;
    }
    
    @Override
    protected SecurityRoleDescriptionEdit getEdit(HttpServletRequest request, DescriptionEditActionForm actionForm)
            throws NamingException {
        var edit = SecurityUtil.getHome().getSecurityRoleDescriptionEdit();

        edit.setDescription(actionForm.getDescription());

        return edit;
    }
    
    @Override
    protected EditSecurityRoleDescriptionForm getForm()
            throws NamingException {
        return SecurityUtil.getHome().getEditSecurityRoleDescriptionForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, DescriptionEditActionForm actionForm, EditSecurityRoleDescriptionResult result, SecurityRoleDescriptionSpec spec, SecurityRoleDescriptionEdit edit) {
        actionForm.setSecurityRoleGroupName(spec.getSecurityRoleGroupName());
        actionForm.setSecurityRoleName(spec.getSecurityRoleName());
        actionForm.setLanguageIsoName(spec.getLanguageIsoName());
        actionForm.setDescription(edit.getDescription());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditSecurityRoleDescriptionForm commandForm)
            throws Exception {
        return SecurityUtil.getHome().editSecurityRoleDescription(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(DescriptionEditActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.SECURITY_ROLE_GROUP_NAME, actionForm.getSecurityRoleGroupName());
        parameters.put(ParameterConstants.SECURITY_ROLE_NAME, actionForm.getSecurityRoleName());
    }
    
    @Override
    protected void setupTransferForForm(HttpServletRequest request, DescriptionEditActionForm actionForm, EditSecurityRoleDescriptionResult result) {
        request.setAttribute(AttributeConstants.SECURITY_ROLE_DESCRIPTION, result.getSecurityRoleDescription());
    }

}
