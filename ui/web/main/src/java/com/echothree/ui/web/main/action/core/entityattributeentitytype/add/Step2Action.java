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

package com.echothree.ui.web.main.action.core.entityattributeentitytype.add;

import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.control.user.core.common.result.GetEntityTypesResult;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
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
    path = "/Core/EntityAttributeEntityType/Add/Step2",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Core/EntityAttributeEntityType/Main", redirect = true),
        @SproutForward(name = "Form", path = "/core/entityattributeentitytype/add/step2.jsp")
    }
)
public class Step2Action
        extends BaseAddAction {
    
    private void setEntityTypeTransfers(HttpServletRequest request, String componentVendorName)
        throws NamingException {
        var commandForm = CoreUtil.getHome().getGetEntityTypesForm();

        commandForm.setComponentVendorName(componentVendorName);

        var commandResult = CoreUtil.getHome().getEntityTypes(getUserVisitPK(request), commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (GetEntityTypesResult)executionResult.getResult();

        request.setAttribute(AttributeConstants.ENTITY_TYPES, result.getEntityTypes());
    }
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey;
        var componentVendorName = request.getParameter(ParameterConstants.COMPONENT_VENDOR_NAME);
        var entityTypeName = request.getParameter(ParameterConstants.ENTITY_TYPE_NAME);
        var entityAttributeName = request.getParameter(ParameterConstants.ENTITY_ATTRIBUTE_NAME);
        var allowedComponentVendorName = request.getParameter(ParameterConstants.ALLOWED_COMPONENT_VENDOR_NAME);
        var allowedEntityTypeName = request.getParameter(ParameterConstants.ALLOWED_ENTITY_TYPE_NAME);
        
        if(allowedEntityTypeName == null) {
            forwardKey = ForwardConstants.FORM;
        } else {
            var commandForm = CoreUtil.getHome().getCreateEntityAttributeEntityTypeForm();

            commandForm.setComponentVendorName(componentVendorName);
            commandForm.setEntityTypeName(entityTypeName);
            commandForm.setEntityAttributeName(entityAttributeName);
            commandForm.setAllowedComponentVendorName(allowedComponentVendorName);
            commandForm.setAllowedEntityTypeName(allowedEntityTypeName);

            var commandResult = CoreUtil.getHome().createEntityAttributeEntityType(getUserVisitPK(request), commandForm);

            if(commandResult.hasErrors()) {
                setCommandResultAttribute(request, commandResult);
                forwardKey = ForwardConstants.FORM;
            } else {
                forwardKey = ForwardConstants.DISPLAY;
            }
        }

        var customActionForward = new CustomActionForward(mapping.findForward(forwardKey));
        if(forwardKey.equals(ForwardConstants.FORM)) {
            setupEntityAttributeTransfer(request, componentVendorName, entityTypeName, entityAttributeName);
            setEntityTypeTransfers(request, allowedComponentVendorName);
        } else if(forwardKey.equals(ForwardConstants.DISPLAY)) {
            Map<String, String> parameters = new HashMap<>(2);
            
            parameters.put(ParameterConstants.COMPONENT_VENDOR_NAME, componentVendorName);
            parameters.put(ParameterConstants.ENTITY_TYPE_NAME, entityTypeName);
            parameters.put(ParameterConstants.ENTITY_ATTRIBUTE_NAME, entityAttributeName);
            customActionForward.setParameters(parameters);
        }
        
        return customActionForward;
    }
    
}
