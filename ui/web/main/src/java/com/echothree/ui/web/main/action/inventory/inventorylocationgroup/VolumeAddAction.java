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

package com.echothree.ui.web.main.action.inventory.inventorylocationgroup;

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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/Inventory/InventoryLocationGroup/VolumeAdd",
    mappingClass = SecureActionMapping.class,
    name = "InventoryLocationGroupVolumeAdd",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Inventory/InventoryLocationGroup/Main", redirect = true),
        @SproutForward(name = "Form", path = "/inventory/inventorylocationgroup/volumeAdd.jsp")
    }
)
public class VolumeAddAction
        extends MainBaseAction<VolumeAddActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, VolumeAddActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey;
        var warehouseName = request.getParameter(ParameterConstants.WAREHOUSE_NAME);
        var inventoryLocationGroupName = request.getParameter(ParameterConstants.LOCATION_NAME);
        
        if(wasPost(request)) {
            var commandForm = InventoryUtil.getHome().getCreateInventoryLocationGroupVolumeForm();
            
            if(warehouseName == null)
                warehouseName = form.getWarehouseName();
            if(inventoryLocationGroupName == null)
                inventoryLocationGroupName = form.getInventoryLocationGroupName();
            
            commandForm.setWarehouseName(warehouseName);
            commandForm.setInventoryLocationGroupName(form.getInventoryLocationGroupName());
            commandForm.setHeight(form.getHeight());
            commandForm.setHeightUnitOfMeasureTypeName(form.getHeightUnitOfMeasureTypeChoice());
            commandForm.setWidth(form.getWidth());
            commandForm.setWidthUnitOfMeasureTypeName(form.getWidthUnitOfMeasureTypeChoice());
            commandForm.setDepth(form.getDepth());
            commandForm.setDepthUnitOfMeasureTypeName(form.getDepthUnitOfMeasureTypeChoice());

            var commandResult = InventoryUtil.getHome().createInventoryLocationGroupVolume(getUserVisitPK(request), commandForm);
            
            if(commandResult.hasErrors()) {
                setCommandResultAttribute(request, commandResult);
                forwardKey = ForwardConstants.FORM;
            } else {
                forwardKey = ForwardConstants.DISPLAY;
            }
        } else {
            form.setWarehouseName(warehouseName);
            form.setInventoryLocationGroupName(inventoryLocationGroupName);
            forwardKey = ForwardConstants.FORM;
        }

        var customActionForward = new CustomActionForward(mapping.findForward(forwardKey));
        if(forwardKey.equals(ForwardConstants.FORM)) {
            request.setAttribute(AttributeConstants.WAREHOUSE_NAME, warehouseName);
            request.setAttribute(AttributeConstants.LOCATION_NAME, inventoryLocationGroupName);
        } else if(forwardKey.equals(ForwardConstants.DISPLAY)) {
            Map<String, String> parameters = new HashMap<>(2);
            
            parameters.put(ParameterConstants.WAREHOUSE_NAME, warehouseName);
            parameters.put(ParameterConstants.LOCATION_NAME, inventoryLocationGroupName);
            customActionForward.setParameters(parameters);
        }
        
        return customActionForward;
    }
    
}
