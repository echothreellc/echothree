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

package com.echothree.ui.web.main.action.inventory.inventoryconditionuse;

import com.echothree.control.user.inventory.common.InventoryUtil;
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
    path = "/Inventory/InventoryConditionUse/Add",
    mappingClass = SecureActionMapping.class,
    name = "InventoryConditionUseAdd",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Inventory/InventoryConditionUse/Main", redirect = true),
        @SproutForward(name = "Form", path = "/inventory/inventoryconditionuse/add.jsp")
    }
)
public class AddAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
        String forwardKey = null;
        var inventoryConditionUseTypeName = request.getParameter(ParameterConstants.INVENTORY_CONDITION_USE_TYPE_NAME);
        
        try {
            if(forwardKey == null) {
                var actionForm = (AddActionForm)form;
                
                    if(inventoryConditionUseTypeName == null)
                        inventoryConditionUseTypeName = actionForm.getInventoryConditionUseTypeName();
                
                if(wasPost(request)) {
                    var commandForm = InventoryUtil.getHome().getCreateInventoryConditionUseForm();
                    
                    commandForm.setInventoryConditionName(actionForm.getInventoryConditionChoice());
                    commandForm.setInventoryConditionUseTypeName(actionForm.getInventoryConditionUseTypeName());
                    commandForm.setIsDefault(actionForm.getIsDefault().toString());

                    var commandResult = InventoryUtil.getHome().createInventoryConditionUse(getUserVisitPK(request), commandForm);
                    
                    if(commandResult.hasErrors()) {
                        setCommandResultAttribute(request, commandResult);
                        forwardKey = ForwardConstants.FORM;
                    } else {
                        forwardKey = ForwardConstants.DISPLAY;
                    }
                } else {
                    actionForm.setInventoryConditionUseTypeName(inventoryConditionUseTypeName);
                    forwardKey = ForwardConstants.FORM;
                }
            }
        } catch (NamingException ne) {
            forwardKey = ForwardConstants.ERROR_500;
        }

        var customActionForward = new CustomActionForward(mapping.findForward(forwardKey));
        if(forwardKey.equals(ForwardConstants.FORM)) {
            request.setAttribute(AttributeConstants.INVENTORY_CONDITION_USE_TYPE_NAME, inventoryConditionUseTypeName);
        } else if(forwardKey.equals(ForwardConstants.DISPLAY)) {
            Map<String, String> parameters = new HashMap<>(1);
            
            parameters.put(ParameterConstants.INVENTORY_CONDITION_USE_TYPE_NAME, inventoryConditionUseTypeName);
            customActionForward.setParameters(parameters);
        }
        
        return customActionForward;
    }
    
}
