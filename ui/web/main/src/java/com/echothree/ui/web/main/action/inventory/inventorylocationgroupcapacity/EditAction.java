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

package com.echothree.ui.web.main.action.inventory.inventorylocationgroupcapacity;


import com.echothree.control.user.inventory.common.InventoryUtil;
import com.echothree.control.user.inventory.common.result.EditInventoryLocationGroupCapacityResult;
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
    path = "/Inventory/InventoryLocationGroupCapacity/Edit",
    mappingClass = SecureActionMapping.class,
    name = "InventoryLocationGroupCapacityEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Inventory/InventoryLocationGroupCapacity/Main", redirect = true),
        @SproutForward(name = "Form", path = "/inventory/inventorylocationgroupcapacity/edit.jsp")
    }
)
public class EditAction
        extends MainBaseAction<EditActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, EditActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey = null;
        var warehouseName = request.getParameter(ParameterConstants.WAREHOUSE_NAME);
        var inventoryLocationGroupName = request.getParameter(ParameterConstants.INVENTORY_LOCATION_GROUP_NAME);
        var unitOfMeasureKindName = request.getParameter(ParameterConstants.UNIT_OF_MEASURE_KIND_NAME);
        var unitOfMeasureTypeName = request.getParameter(ParameterConstants.UNIT_OF_MEASURE_TYPE_NAME);
        
        if(forwardKey == null) {
            var commandForm = InventoryUtil.getHome().getEditInventoryLocationGroupCapacityForm();
            var spec = InventoryUtil.getHome().getInventoryLocationGroupCapacitySpec();
            
            if(warehouseName == null)
                warehouseName = form.getWarehouseName();
            if(inventoryLocationGroupName == null)
                inventoryLocationGroupName = form.getInventoryLocationGroupName();
            if(unitOfMeasureKindName == null)
                unitOfMeasureKindName = form.getUnitOfMeasureKindName();
            if(unitOfMeasureTypeName == null)
                unitOfMeasureTypeName = form.getUnitOfMeasureTypeName();
            
            commandForm.setSpec(spec);
            spec.setWarehouseName(warehouseName);
            spec.setInventoryLocationGroupName(inventoryLocationGroupName);
            spec.setUnitOfMeasureKindName(unitOfMeasureKindName);
            spec.setUnitOfMeasureTypeName(unitOfMeasureTypeName);
            
            if(wasPost(request)) {
                var edit = InventoryUtil.getHome().getInventoryLocationGroupCapacityEdit();
                
                commandForm.setEditMode(EditMode.UPDATE);
                commandForm.setEdit(edit);
                
                edit.setCapacity(form.getCapacity());

                var commandResult = InventoryUtil.getHome().editInventoryLocationGroupCapacity(getUserVisitPK(request), commandForm);
                
                if(commandResult.hasErrors()) {
                    var executionResult = commandResult.getExecutionResult();
                    
                    if(executionResult != null) {
                        var result = (EditInventoryLocationGroupCapacityResult)executionResult.getResult();
                        
                        request.setAttribute(AttributeConstants.ENTITY_LOCK, result.getEntityLock());
                    }
                    
                    setCommandResultAttribute(request, commandResult);
                    
                    forwardKey = ForwardConstants.FORM;
                } else {
                    forwardKey = ForwardConstants.DISPLAY;
                }
            } else {
                commandForm.setEditMode(EditMode.LOCK);

                var commandResult = InventoryUtil.getHome().editInventoryLocationGroupCapacity(getUserVisitPK(request), commandForm);
                var executionResult = commandResult.getExecutionResult();
                var result = (EditInventoryLocationGroupCapacityResult)executionResult.getResult();
                
                if(result != null) {
                    var edit = result.getEdit();
                    
                    if(edit != null) {
                        form.setWarehouseName(warehouseName);
                        form.setInventoryLocationGroupName(inventoryLocationGroupName);
                        form.setUnitOfMeasureKindName(unitOfMeasureKindName);
                        form.setUnitOfMeasureTypeName(unitOfMeasureTypeName);
                        form.setCapacity(edit.getCapacity());
                    }
                    
                    request.setAttribute(AttributeConstants.ENTITY_LOCK, result.getEntityLock());
                }
                
                setCommandResultAttribute(request, commandResult);
                
                forwardKey = ForwardConstants.FORM;
            }
        }

        var customActionForward = new CustomActionForward(mapping.findForward(forwardKey));
        if(forwardKey.equals(ForwardConstants.FORM)) {
            request.setAttribute(AttributeConstants.WAREHOUSE_NAME, warehouseName);
            request.setAttribute(AttributeConstants.INVENTORY_LOCATION_GROUP_NAME, inventoryLocationGroupName);
            request.setAttribute(AttributeConstants.UNIT_OF_MEASURE_KIND_NAME, unitOfMeasureKindName);
            request.setAttribute(AttributeConstants.UNIT_OF_MEASURE_TYPE_NAME, unitOfMeasureTypeName);
        } else if(forwardKey.equals(ForwardConstants.DISPLAY)) {
            Map<String, String> parameters = new HashMap<>(4);
            
            parameters.put(ParameterConstants.WAREHOUSE_NAME, warehouseName);
            parameters.put(ParameterConstants.INVENTORY_LOCATION_GROUP_NAME, inventoryLocationGroupName);
            parameters.put(ParameterConstants.UNIT_OF_MEASURE_KIND_NAME, unitOfMeasureKindName);
            parameters.put(ParameterConstants.UNIT_OF_MEASURE_TYPE_NAME, unitOfMeasureTypeName);
            customActionForward.setParameters(parameters);
        }
        
        return customActionForward;
    }
    
}
