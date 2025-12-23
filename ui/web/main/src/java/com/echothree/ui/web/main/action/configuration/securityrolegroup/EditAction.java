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
import com.echothree.control.user.security.common.edit.SecurityRoleGroupEdit;
import com.echothree.control.user.security.common.form.EditSecurityRoleGroupForm;
import com.echothree.control.user.security.common.result.EditSecurityRoleGroupResult;
import com.echothree.control.user.security.common.spec.SecurityRoleGroupSpec;
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
    path = "/Configuration/SecurityRoleGroup/Edit",
    mappingClass = SecureActionMapping.class,
    name = "SecurityRoleGroupEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Configuration/SecurityRoleGroup/Main", redirect = true),
        @SproutForward(name = "Form", path = "/configuration/securityrolegroup/edit.jsp")
    }
)
public class EditAction
        extends MainBaseEditAction<EditActionForm, SecurityRoleGroupSpec, SecurityRoleGroupEdit, EditSecurityRoleGroupForm, EditSecurityRoleGroupResult> {
    
    @Override
    protected SecurityRoleGroupSpec getSpec(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var spec = SecurityUtil.getHome().getSecurityRoleGroupSpec();
        
        spec.setSecurityRoleGroupName(findParameter(request, ParameterConstants.ORIGINAL_SECURITY_ROLE_GROUP_NAME, actionForm.getOriginalSecurityRoleGroupName()));
        
        return spec;
    }
    
    @Override
    protected SecurityRoleGroupEdit getEdit(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var edit = SecurityUtil.getHome().getSecurityRoleGroupEdit();

        edit.setSecurityRoleGroupName(actionForm.getSecurityRoleGroupName());
        edit.setParentSecurityRoleGroupName(actionForm.getParentSecurityRoleGroupChoice());
        edit.setIsDefault(actionForm.getIsDefault().toString());
        edit.setSortOrder(actionForm.getSortOrder());
        edit.setDescription(actionForm.getDescription());

        return edit;
    }
    
    @Override
    protected EditSecurityRoleGroupForm getForm()
            throws NamingException {
        return SecurityUtil.getHome().getEditSecurityRoleGroupForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, EditActionForm actionForm, EditSecurityRoleGroupResult result, SecurityRoleGroupSpec spec, SecurityRoleGroupEdit edit) {
        actionForm.setOriginalSecurityRoleGroupName(spec.getSecurityRoleGroupName());
        actionForm.setSecurityRoleGroupName(edit.getSecurityRoleGroupName());
        actionForm.setParentSecurityRoleGroupChoice(edit.getParentSecurityRoleGroupName());
        actionForm.setParentSecurityRoleGroupName(edit.getParentSecurityRoleGroupName());
        actionForm.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        actionForm.setSortOrder(edit.getSortOrder());
        actionForm.setDescription(edit.getDescription());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditSecurityRoleGroupForm commandForm)
            throws Exception {
        return SecurityUtil.getHome().editSecurityRoleGroup(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(EditActionForm actionForm, Map<String, String> parameters) {
        var parentSecurityRoleGroupName = actionForm.getParentSecurityRoleGroupName();
        
        if(parentSecurityRoleGroupName != null && parentSecurityRoleGroupName.length() > 0) {
            parameters.put(ParameterConstants.PARENT_SECURITY_ROLE_GROUP_NAME, parentSecurityRoleGroupName);
        }
    }
    
    @Override
    protected void setupTransferForForm(HttpServletRequest request, EditActionForm actionForm, EditSecurityRoleGroupResult result) {
        request.setAttribute(AttributeConstants.SECURITY_ROLE_GROUP, result.getSecurityRoleGroup());
    }

}
