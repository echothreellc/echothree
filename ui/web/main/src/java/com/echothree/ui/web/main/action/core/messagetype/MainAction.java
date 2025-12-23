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

package com.echothree.ui.web.main.action.core.messagetype;

import com.echothree.control.user.message.common.MessageUtil;
import com.echothree.control.user.message.common.result.GetMessageTypesResult;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/Core/MessageType/Main",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/core/messagetype/main.jsp")
    }
)
public class MainAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
        String forwardKey;
        
        try {
            var componentVendorName = request.getParameter(ParameterConstants.COMPONENT_VENDOR_NAME);
            var entityTypeName = request.getParameter(ParameterConstants.ENTITY_TYPE_NAME);
            var commandForm = MessageUtil.getHome().getGetMessageTypesForm();
            
            commandForm.setComponentVendorName(componentVendorName);
            commandForm.setEntityTypeName(entityTypeName);

            var commandResult = MessageUtil.getHome().getMessageTypes(getUserVisitPK(request), commandForm);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetMessageTypesResult)executionResult.getResult();
            var componentVendorTransfer = result.getComponentVendor();
            var entityTypeTransfer = result.getEntityType();
            
            request.setAttribute(AttributeConstants.COMPONENT_VENDOR, componentVendorTransfer);
            request.setAttribute(AttributeConstants.COMPONENT_VENDOR_NAME, componentVendorTransfer.getComponentVendorName());
            request.setAttribute(AttributeConstants.ENTITY_TYPE, entityTypeTransfer);
            request.setAttribute(AttributeConstants.ENTITY_TYPE_NAME, entityTypeTransfer.getEntityTypeName());
            request.setAttribute(AttributeConstants.MESSAGE_TYPES, result.getMessageTypes());
            forwardKey = ForwardConstants.DISPLAY;
        } catch (NamingException ne) {
            forwardKey = ForwardConstants.ERROR_500;
        }
        
        return mapping.findForward(forwardKey);
    }
    
}