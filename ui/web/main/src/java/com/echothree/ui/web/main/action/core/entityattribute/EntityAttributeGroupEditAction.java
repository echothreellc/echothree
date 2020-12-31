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

package com.echothree.ui.web.main.action.core.entityattribute;

import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.control.user.core.common.edit.EntityAttributeEntityAttributeGroupEdit;
import com.echothree.control.user.core.common.form.EditEntityAttributeEntityAttributeGroupForm;
import com.echothree.control.user.core.common.result.EditEntityAttributeEntityAttributeGroupResult;
import com.echothree.control.user.core.common.spec.EntityAttributeEntityAttributeGroupSpec;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.command.ExecutionResult;
import com.echothree.view.client.web.struts.CustomActionForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import java.util.HashMap;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/Core/EntityAttribute/EntityAttributeGroupEdit",
    mappingClass = SecureActionMapping.class,
    name = "EntityAttributeEntityAttributeGroupEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Core/EntityAttribute/EntityAttributeGroup", redirect = true),
        @SproutForward(name = "Form", path = "/core/entityattribute/entityAttributeGroupEdit.jsp")
    }
)
public class EntityAttributeGroupEditAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
        String forwardKey = null;
        String componentVendorName = request.getParameter(ParameterConstants.COMPONENT_VENDOR_NAME);
        String entityTypeName = request.getParameter(ParameterConstants.ENTITY_TYPE_NAME);
        String entityAttributeName = request.getParameter(ParameterConstants.ENTITY_ATTRIBUTE_NAME);
        String entityAttributeGroupName = request.getParameter(ParameterConstants.ENTITY_ATTRIBUTE_GROUP_NAME);
        
        try {
            if(forwardKey == null) {
                EntityAttributeGroupEditActionForm actionForm = (EntityAttributeGroupEditActionForm)form;
                EditEntityAttributeEntityAttributeGroupForm commandForm = CoreUtil.getHome().getEditEntityAttributeEntityAttributeGroupForm();
                EntityAttributeEntityAttributeGroupSpec spec = CoreUtil.getHome().getEntityAttributeEntityAttributeGroupSpec();
                
                if(componentVendorName == null)
                    componentVendorName = actionForm.getComponentVendorName();
                if(entityTypeName == null)
                    entityTypeName = actionForm.getEntityTypeName();
                if(entityAttributeName == null)
                    entityAttributeName = actionForm.getEntityAttributeName();
                if(entityAttributeGroupName == null)
                    entityAttributeGroupName = actionForm.getEntityAttributeGroupName();
                
                commandForm.setSpec(spec);
                spec.setComponentVendorName(componentVendorName);
                spec.setEntityTypeName(entityTypeName);
                spec.setEntityAttributeName(entityAttributeName);
                spec.setEntityAttributeGroupName(entityAttributeGroupName);
                
                if(wasPost(request)) {
                    EntityAttributeEntityAttributeGroupEdit edit = CoreUtil.getHome().getEntityAttributeEntityAttributeGroupEdit();
                    
                    commandForm.setEditMode(EditMode.UPDATE);
                    commandForm.setEdit(edit);
                    edit.setSortOrder(actionForm.getSortOrder());
                    
                    CommandResult commandResult = CoreUtil.getHome().editEntityAttributeEntityAttributeGroup(getUserVisitPK(request), commandForm);
                    
                    if(commandResult.hasErrors()) {
                        ExecutionResult executionResult = commandResult.getExecutionResult();
                        
                        if(executionResult != null) {
                            EditEntityAttributeEntityAttributeGroupResult result = (EditEntityAttributeEntityAttributeGroupResult)executionResult.getResult();
                            
                            request.setAttribute(AttributeConstants.ENTITY_LOCK, result.getEntityLock());
                        }
                        
                        setCommandResultAttribute(request, commandResult);
                        
                        forwardKey = ForwardConstants.FORM;
                    } else {
                        forwardKey = ForwardConstants.DISPLAY;
                    }
                } else {
                    commandForm.setEditMode(EditMode.LOCK);
                    
                    CommandResult commandResult = CoreUtil.getHome().editEntityAttributeEntityAttributeGroup(getUserVisitPK(request), commandForm);
                    ExecutionResult executionResult = commandResult.getExecutionResult();
                    EditEntityAttributeEntityAttributeGroupResult result = (EditEntityAttributeEntityAttributeGroupResult)executionResult.getResult();
                    
                    if(result != null) {
                        EntityAttributeEntityAttributeGroupEdit edit = result.getEdit();
                        
                        if(edit != null) {
                            actionForm.setComponentVendorName(componentVendorName);
                            actionForm.setEntityTypeName(entityTypeName);
                            actionForm.setEntityAttributeName(entityAttributeName);
                            actionForm.setEntityAttributeGroupName(entityAttributeGroupName);
                            actionForm.setSortOrder(edit.getSortOrder());
                        }
                        
                        request.setAttribute(AttributeConstants.ENTITY_LOCK, result.getEntityLock());
                    }
                    
                    setCommandResultAttribute(request, commandResult);
                    
                    forwardKey = ForwardConstants.FORM;
                }
            }
        } catch (NamingException ne) {
            forwardKey = ForwardConstants.ERROR_500;
        }
        
        CustomActionForward customActionForward = new CustomActionForward(mapping.findForward(forwardKey));
        if(forwardKey.equals(ForwardConstants.FORM)) {
            request.setAttribute(AttributeConstants.COMPONENT_VENDOR_NAME, componentVendorName);
            request.setAttribute(AttributeConstants.ENTITY_TYPE_NAME, entityTypeName);
            request.setAttribute(AttributeConstants.ENTITY_ATTRIBUTE_NAME, entityAttributeName);
            request.setAttribute(AttributeConstants.LANGUAGE_ISO_NAME, entityAttributeGroupName);
        } else if(forwardKey.equals(ForwardConstants.DISPLAY)) {
            Map<String, String> parameters = new HashMap<>(3);
            
            parameters.put(ParameterConstants.COMPONENT_VENDOR_NAME, componentVendorName);
            parameters.put(ParameterConstants.ENTITY_TYPE_NAME, entityTypeName);
            parameters.put(ParameterConstants.ENITTY_ATTRIBUTE_NAME, entityAttributeName);
            customActionForward.setParameters(parameters);
        }
        
        return customActionForward;
    }
    
}