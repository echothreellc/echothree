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

package com.echothree.ui.web.main.action.core.entityattribute;

import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.control.user.core.common.result.GetEntityAttributeEntityAttributeGroupResult;
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
    path = "/Core/EntityAttribute/EntityAttributeGroupDelete",
    mappingClass = SecureActionMapping.class,
    name = "EntityAttributeEntityAttributeGroupDelete",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Core/EntityAttribute/EntityAttributeGroup", redirect = true),
        @SproutForward(name = "Form", path = "/core/entityattribute/entityAttributeGroupDelete.jsp")
    }
)
public class EntityAttributeGroupDeleteAction
        extends MainBaseDeleteAction<EntityAttributeGroupDeleteActionForm> {

    @Override
    public String getEntityTypeName() {
        return EntityTypes.EntityAttributeEntityAttributeGroup.name();
    }
    
    @Override
    public void setupParameters(EntityAttributeGroupDeleteActionForm actionForm, HttpServletRequest request) {
        actionForm.setComponentVendorName(findParameter(request, ParameterConstants.COMPONENT_VENDOR_NAME, actionForm.getComponentVendorName()));
        actionForm.setEntityTypeName(findParameter(request, ParameterConstants.ENTITY_TYPE_NAME, actionForm.getEntityTypeName()));
        actionForm.setEntityAttributeName(findParameter(request, ParameterConstants.ENTITY_ATTRIBUTE_NAME, actionForm.getEntityAttributeName()));
        actionForm.setEntityAttributeGroupName(findParameter(request, ParameterConstants.ENTITY_ATTRIBUTE_GROUP_NAME, actionForm.getEntityAttributeGroupName()));
    }
    
    @Override
    public void setupTransfer(EntityAttributeGroupDeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = CoreUtil.getHome().getGetEntityAttributeEntityAttributeGroupForm();
        
        commandForm.setComponentVendorName(actionForm.getComponentVendorName());
        commandForm.setEntityTypeName(actionForm.getEntityTypeName());
        commandForm.setEntityAttributeName(actionForm.getEntityAttributeName());
        commandForm.setEntityAttributeGroupName(actionForm.getEntityAttributeGroupName());

        var commandResult = CoreUtil.getHome().getEntityAttributeEntityAttributeGroup(getUserVisitPK(request), commandForm);
        if(!commandResult.hasErrors()) {
            var executionResult = commandResult.getExecutionResult();
            var result = (GetEntityAttributeEntityAttributeGroupResult)executionResult.getResult();

            request.setAttribute(AttributeConstants.ENTITY_ATTRIBUTE_ENTITY_ATTRIBUTE_GROUP, result.getEntityAttributeEntityAttributeGroup());
        }
    }
    
    @Override
    public CommandResult doDelete(EntityAttributeGroupDeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = CoreUtil.getHome().getDeleteEntityAttributeEntityAttributeGroupForm();

        commandForm.setComponentVendorName(actionForm.getComponentVendorName());
        commandForm.setEntityTypeName(actionForm.getEntityTypeName());
        commandForm.setEntityAttributeName(actionForm.getEntityAttributeName());
        commandForm.setEntityAttributeGroupName(actionForm.getEntityAttributeGroupName());

        return CoreUtil.getHome().deleteEntityAttributeEntityAttributeGroup(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(EntityAttributeGroupDeleteActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.COMPONENT_VENDOR_NAME, actionForm.getComponentVendorName());
        parameters.put(ParameterConstants.ENTITY_TYPE_NAME, actionForm.getEntityTypeName());
        parameters.put(ParameterConstants.ENTITY_ATTRIBUTE_NAME, actionForm.getEntityAttributeName());
    }
    
}
