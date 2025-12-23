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
import com.echothree.control.user.core.common.result.EditEntityAttributeResult;
import com.echothree.model.control.core.common.EntityAttributeTypes;
import static com.echothree.model.control.core.common.EntityAttributeTypes.BLOB;
import static com.echothree.model.control.core.common.EntityAttributeTypes.LISTITEM;
import static com.echothree.model.control.core.common.EntityAttributeTypes.MULTIPLELISTITEM;
import static com.echothree.model.control.core.common.EntityAttributeTypes.STRING;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.EditMode;
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
    path = "/Core/EntityAttribute/Edit",
    mappingClass = SecureActionMapping.class,
    name = "EntityAttributeEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Core/EntityAttribute/Main", redirect = true),
        @SproutForward(name = "Form", path = "/core/entityattribute/edit.jsp")
    }
)
public class EditAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
        String forwardKey = null;
        var componentVendorName = request.getParameter(ParameterConstants.COMPONENT_VENDOR_NAME);
        var entityTypeName = request.getParameter(ParameterConstants.ENTITY_TYPE_NAME);
        
        try {
            if(forwardKey == null) {
                var actionForm = (EditActionForm)form;
                var commandForm = CoreUtil.getHome().getEditEntityAttributeForm();
                var spec = CoreUtil.getHome().getEntityAttributeUniversalSpec();
                var originalEntityAttributeName = request.getParameter(ParameterConstants.ORIGINAL_ENTITY_ATTRIBUTE_NAME);
                
                if(componentVendorName == null)
                    componentVendorName = actionForm.getComponentVendorName();
                if(entityTypeName == null)
                    entityTypeName = actionForm.getEntityTypeName();
                if(originalEntityAttributeName == null)
                    originalEntityAttributeName = actionForm.getOriginalEntityAttributeName();
                
                commandForm.setSpec(spec);
                spec.setComponentVendorName(componentVendorName);
                spec.setEntityTypeName(entityTypeName);
                spec.setEntityAttributeName(originalEntityAttributeName);
                
                if(wasPost(request)) {
                    var edit = CoreUtil.getHome().getEntityAttributeEdit();
                    
                    commandForm.setEditMode(EditMode.UPDATE);
                    commandForm.setEdit(edit);
                    
                    edit.setEntityAttributeName(actionForm.getEntityAttributeName());
                    edit.setTrackRevisions(actionForm.getTrackRevisions().toString());
                    edit.setSortOrder(actionForm.getSortOrder());
                    edit.setDescription(actionForm.getDescription());
                    
                    switch(EntityAttributeTypes.valueOf(actionForm.getEntityAttributeTypeChoice())) {
                        case BLOB:
                            edit.setCheckContentWebAddress(actionForm.getCheckContentWebAddress().toString());
                            break;
                        case STRING:
                            edit.setValidationPattern(actionForm.getValidationPattern());
                            break;
                        case LISTITEM:
                        case MULTIPLELISTITEM:
                            edit.setEntityListItemSequenceName(actionForm.getEntityListItemSequenceChoice());
                            break;
                        default:
                            break;
                    }

                    var commandResult = CoreUtil.getHome().editEntityAttribute(getUserVisitPK(request), commandForm);
                    
                    if(commandResult.hasErrors()) {
                        var executionResult = commandResult.getExecutionResult();
                        
                        if(executionResult != null) {
                            var result = (EditEntityAttributeResult)executionResult.getResult();
                            
                            request.setAttribute(AttributeConstants.ENTITY_LOCK, result.getEntityLock());
                        }
                        
                        setCommandResultAttribute(request, commandResult);
                        
                        forwardKey = ForwardConstants.FORM;
                    } else {
                        forwardKey = ForwardConstants.DISPLAY;
                    }
                } else {
                    commandForm.setEditMode(EditMode.LOCK);

                    var commandResult = CoreUtil.getHome().editEntityAttribute(getUserVisitPK(request), commandForm);
                    var executionResult = commandResult.getExecutionResult();
                    var result = (EditEntityAttributeResult)executionResult.getResult();
                    
                    if(result != null) {
                        var edit = result.getEdit();
                        
                        if(edit != null) {
                            actionForm.setEntityAttributeTypeChoice(result.getEntityAttribute().getEntityAttributeType().getEntityAttributeTypeName());
                            
                            actionForm.setComponentVendorName(componentVendorName);
                            actionForm.setEntityTypeName(entityTypeName);
                            actionForm.setOriginalEntityAttributeName(edit.getEntityAttributeName());
                            actionForm.setEntityAttributeName(edit.getEntityAttributeName());
                            actionForm.setTrackRevisions(Boolean.valueOf(edit.getTrackRevisions()));
                            actionForm.setCheckContentWebAddress(Boolean.valueOf(edit.getCheckContentWebAddress()));
                            actionForm.setValidationPattern(edit.getValidationPattern());
                            actionForm.setEntityListItemSequenceChoice(edit.getEntityListItemSequenceName());
                            actionForm.setSortOrder(edit.getSortOrder());
                            actionForm.setDescription(edit.getDescription());
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

        var customActionForward = new CustomActionForward(mapping.findForward(forwardKey));
        if(forwardKey.equals(ForwardConstants.FORM)) {
            request.setAttribute(AttributeConstants.COMPONENT_VENDOR_NAME, componentVendorName);
            request.setAttribute(AttributeConstants.ENTITY_TYPE_NAME, entityTypeName);
        } else if(forwardKey.equals(ForwardConstants.DISPLAY)) {
            Map<String, String> parameters = new HashMap<>(2);
            
            parameters.put(ParameterConstants.COMPONENT_VENDOR_NAME, componentVendorName);
            parameters.put(ParameterConstants.ENTITY_TYPE_NAME, entityTypeName);
            customActionForward.setParameters(parameters);
        }
        
        return customActionForward;
    }
    
}