// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

package com.echothree.ui.web.main.action.core.entitylistitem;

import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.control.user.core.common.form.GetEntityListItemDescriptionsForm;
import com.echothree.control.user.core.common.result.GetEntityListItemDescriptionsResult;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.ExecutionResult;
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
    path = "/Core/EntityListItem/Description",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/core/entitylistitem/description.jsp")
    }
)
public class DescriptionAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
        String forwardKey = null;
        
        try {
            String componentVendorName = request.getParameter(ParameterConstants.COMPONENT_VENDOR_NAME);
            String entityTypeName = request.getParameter(ParameterConstants.ENTITY_TYPE_NAME);
            String entityAttributeName = request.getParameter(ParameterConstants.ENTITY_ATTRIBUTE_NAME);
            String entityListItemName = request.getParameter(ParameterConstants.ENTITY_LIST_ITEM_NAME);
            GetEntityListItemDescriptionsForm commandForm = CoreUtil.getHome().getGetEntityListItemDescriptionsForm();
            
            commandForm.setComponentVendorName(componentVendorName);
            commandForm.setEntityTypeName(entityTypeName);
            commandForm.setEntityAttributeName(entityAttributeName);
            commandForm.setEntityListItemName(entityListItemName);
            
            CommandResult commandResult = CoreUtil.getHome().getEntityListItemDescriptions(getUserVisitPK(request), commandForm);
            ExecutionResult executionResult = commandResult.getExecutionResult();
            GetEntityListItemDescriptionsResult result = (GetEntityListItemDescriptionsResult)executionResult.getResult();
            
            request.setAttribute(AttributeConstants.ENTITY_LIST_ITEM, result.getEntityListItem());
            request.setAttribute(AttributeConstants.ENTITY_LIST_ITEM_DESCRIPTIONS, result.getEntityListItemDescriptions());
            forwardKey = ForwardConstants.DISPLAY;
        } catch (NamingException ne) {
            forwardKey = ForwardConstants.ERROR_500;
        }
        
        return mapping.findForward(forwardKey);
    }
    
}