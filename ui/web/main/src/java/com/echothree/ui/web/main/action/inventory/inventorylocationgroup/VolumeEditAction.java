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
import com.echothree.control.user.inventory.common.result.EditInventoryLocationGroupVolumeResult;
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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/Inventory/InventoryLocationGroup/VolumeEdit",
    mappingClass = SecureActionMapping.class,
    name = "InventoryLocationGroupVolumeEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Inventory/InventoryLocationGroup/Main", redirect = true),
        @SproutForward(name = "Form", path = "/inventory/inventorylocationgroup/volumeEdit.jsp")
    }
)
public class VolumeEditAction
        extends MainBaseAction<VolumeEditActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, VolumeEditActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey;
        var warehouseName = request.getParameter(ParameterConstants.WAREHOUSE_NAME);
        var inventoryLocationGroupName = request.getParameter(ParameterConstants.INVENTORY_LOCATION_GROUP_NAME);
        var commandForm = InventoryUtil.getHome().getEditInventoryLocationGroupVolumeForm();
        var spec = InventoryUtil.getHome().getInventoryLocationGroupSpec();
        
        if(warehouseName == null)
            warehouseName = form.getWarehouseName();
        if(inventoryLocationGroupName == null)
            inventoryLocationGroupName = form.getInventoryLocationGroupName();
        
        commandForm.setSpec(spec);
        spec.setWarehouseName(warehouseName);
        spec.setInventoryLocationGroupName(inventoryLocationGroupName);
        
        if(wasPost(request)) {
            var edit = InventoryUtil.getHome().getInventoryLocationGroupVolumeEdit();
            
            commandForm.setEditMode(EditMode.UPDATE);
            commandForm.setEdit(edit);
            
            edit.setHeightUnitOfMeasureTypeName(form.getHeightUnitOfMeasureTypeChoice());
            edit.setHeight(form.getHeight());
            edit.setWidthUnitOfMeasureTypeName(form.getWidthUnitOfMeasureTypeChoice());
            edit.setWidth(form.getWidth());
            edit.setDepthUnitOfMeasureTypeName(form.getDepthUnitOfMeasureTypeChoice());
            edit.setDepth(form.getDepth());

            var commandResult = InventoryUtil.getHome().editInventoryLocationGroupVolume(getUserVisitPK(request), commandForm);
            
            if(commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                
                if(executionResult != null) {
                    var result = (EditInventoryLocationGroupVolumeResult)executionResult.getResult();
                    
                    request.setAttribute(AttributeConstants.ENTITY_LOCK, result.getEntityLock());
                }
                
                setCommandResultAttribute(request, commandResult);
                
                forwardKey = ForwardConstants.FORM;
            } else {
                forwardKey = ForwardConstants.DISPLAY;
            }
        } else {
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = InventoryUtil.getHome().editInventoryLocationGroupVolume(getUserVisitPK(request), commandForm);
            var executionResult = commandResult.getExecutionResult();
            var result = (EditInventoryLocationGroupVolumeResult)executionResult.getResult();
            
            if(result != null) {
                var edit = result.getEdit();
                
                if(edit != null) {
                    form.setWarehouseName(warehouseName);
                    form.setInventoryLocationGroupName(inventoryLocationGroupName);
                    form.setHeightUnitOfMeasureTypeChoice(edit.getHeightUnitOfMeasureTypeName());
                    form.setHeight(edit.getHeight());
                    form.setWidthUnitOfMeasureTypeChoice(edit.getWidthUnitOfMeasureTypeName());
                    form.setWidth(edit.getWidth());
                    form.setDepthUnitOfMeasureTypeChoice(edit.getDepthUnitOfMeasureTypeName());
                    form.setDepth(edit.getDepth());
                }
                
                request.setAttribute(AttributeConstants.ENTITY_LOCK, result.getEntityLock());
            }
            
            setCommandResultAttribute(request, commandResult);
            
            forwardKey = ForwardConstants.FORM;
        }

        var customActionForward = new CustomActionForward(mapping.findForward(forwardKey));
        if(forwardKey.equals(ForwardConstants.FORM)) {
            request.setAttribute(AttributeConstants.WAREHOUSE_NAME, warehouseName);
            request.setAttribute(AttributeConstants.INVENTORY_LOCATION_GROUP_NAME, inventoryLocationGroupName);
        } else if(forwardKey.equals(ForwardConstants.DISPLAY)) {
            Map<String, String> parameters = new HashMap<>(2);
            
            parameters.put(ParameterConstants.WAREHOUSE_NAME, warehouseName);
            customActionForward.setParameters(parameters);
        }
        
        return customActionForward;
    }
    
}
