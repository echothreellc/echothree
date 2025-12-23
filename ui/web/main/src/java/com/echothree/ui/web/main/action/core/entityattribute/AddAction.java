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
import com.echothree.model.control.core.common.EntityAttributeTypes;
import static com.echothree.model.control.core.common.EntityAttributeTypes.BLOB;
import static com.echothree.model.control.core.common.EntityAttributeTypes.LISTITEM;
import static com.echothree.model.control.core.common.EntityAttributeTypes.MULTIPLELISTITEM;
import static com.echothree.model.control.core.common.EntityAttributeTypes.STRING;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
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
    path = "/Core/EntityAttribute/Add",
    mappingClass = SecureActionMapping.class,
    name = "EntityAttributeAdd",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Core/EntityAttribute/Main", redirect = true),
        @SproutForward(name = "Form", path = "/core/entityattribute/add.jsp")
    }
)
public class AddAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
        String forwardKey = null;
        var componentVendorName = request.getParameter(ParameterConstants.COMPONENT_VENDOR_NAME);
        var entityTypeName = request.getParameter(ParameterConstants.ENTITY_TYPE_NAME);
        
        try {
            if(forwardKey == null) {
                var actionForm = (AddActionForm)form;
                
                if(wasPost(request)) {
                    var commandForm = CoreUtil.getHome().getCreateEntityAttributeForm();
                    var entityAttributeTypeChoice = actionForm.getEntityAttributeTypeChoice();
                    
                    if(componentVendorName == null)
                        componentVendorName = actionForm.getComponentVendorName();
                    if(entityTypeName == null)
                        entityTypeName = actionForm.getEntityTypeName();
                    
                    commandForm.setComponentVendorName(componentVendorName);
                    commandForm.setEntityTypeName(entityTypeName);
                    commandForm.setEntityAttributeName(actionForm.getEntityAttributeName());
                    commandForm.setEntityAttributeTypeName(entityAttributeTypeChoice);
                    commandForm.setTrackRevisions(actionForm.getTrackRevisions().toString());
                    commandForm.setSortOrder(actionForm.getSortOrder());
                    commandForm.setDescription(actionForm.getDescription());
                    
                    switch(EntityAttributeTypes.valueOf(entityAttributeTypeChoice)) {
                        case BLOB:
                            commandForm.setCheckContentWebAddress(actionForm.getCheckContentWebAddress().toString());
                            break;
                        case STRING:
                            commandForm.setValidationPattern(actionForm.getValidationPattern());
                            break;
                        case LISTITEM:
                        case MULTIPLELISTITEM:
                            commandForm.setEntityListItemSequenceName(actionForm.getEntityListItemSequenceChoice());
                            break;
                        default:
                            break;
                    }

                    var commandResult = CoreUtil.getHome().createEntityAttribute(getUserVisitPK(request), commandForm);
                    
                    if(commandResult.hasErrors()) {
                        setCommandResultAttribute(request, commandResult);
                        forwardKey = ForwardConstants.FORM;
                    } else {
                        forwardKey = ForwardConstants.DISPLAY;
                    }
                } else {
                    actionForm.setTrackRevisions(true);
                    actionForm.setSortOrder("1");
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
