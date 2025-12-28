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

package com.echothree.ui.web.main.action.core.entityaliastype;

import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.control.user.core.common.result.GetEntityAliasTypeResult;
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
    path = "/Core/EntityAliasType/Delete",
    mappingClass = SecureActionMapping.class,
    name = "EntityAliasTypeDelete",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Core/EntityAliasType/Main", redirect = true),
        @SproutForward(name = "Form", path = "/core/entityaliastype/delete.jsp")
    }
)
public class DeleteAction
        extends MainBaseDeleteAction<DeleteActionForm> {

    @Override
    public String getEntityTypeName() {
        return EntityTypes.EntityAliasType.name();
    }
    
    @Override
    public void setupParameters(DeleteActionForm actionForm, HttpServletRequest request) {
        actionForm.setComponentVendorName(findParameter(request, ParameterConstants.COMPONENT_VENDOR_NAME, actionForm.getComponentVendorName()));
        actionForm.setEntityTypeName(findParameter(request, ParameterConstants.ENTITY_TYPE_NAME, actionForm.getEntityTypeName()));
        actionForm.setEntityAliasTypeName(findParameter(request, ParameterConstants.ENTITY_ALIAS_TYPE_NAME, actionForm.getEntityAliasTypeName()));
    }
    
    @Override
    public void setupTransfer(DeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = CoreUtil.getHome().getGetEntityAliasTypeForm();
        
        commandForm.setComponentVendorName(actionForm.getComponentVendorName());
        commandForm.setEntityTypeName(actionForm.getEntityTypeName());
        commandForm.setEntityAliasTypeName(actionForm.getEntityAliasTypeName());

        var commandResult = CoreUtil.getHome().getEntityAliasType(getUserVisitPK(request), commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (GetEntityAliasTypeResult)executionResult.getResult();
        
        request.setAttribute(AttributeConstants.ENTITY_ALIAS_TYPE, result.getEntityAliasType());
    }
    
    @Override
    public CommandResult doDelete(DeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = CoreUtil.getHome().getDeleteEntityAliasTypeForm();

        commandForm.setComponentVendorName(actionForm.getComponentVendorName());
        commandForm.setEntityTypeName(actionForm.getEntityTypeName());
        commandForm.setEntityAliasTypeName(actionForm.getEntityAliasTypeName());

        return CoreUtil.getHome().deleteEntityAliasType(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(DeleteActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.COMPONENT_VENDOR_NAME, actionForm.getComponentVendorName());
        parameters.put(ParameterConstants.ENTITY_TYPE_NAME, actionForm.getEntityTypeName());
    }
    
}
