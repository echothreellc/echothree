// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/Inventory/InventoryLocationGroupCapacity/Delete",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Inventory/InventoryLocationGroupCapacity/Main", redirect = true)
    }
)
public class DeleteAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        var warehouseName = request.getParameter(ParameterConstants.WAREHOUSE_NAME);
        var inventoryLocationGroupName = request.getParameter(ParameterConstants.INVENTORY_LOCATION_GROUP_NAME);
        var unitOfMeasureKindName = request.getParameter(ParameterConstants.UNIT_OF_MEASURE_KIND_NAME);
        var unitOfMeasureTypeName = request.getParameter(ParameterConstants.UNIT_OF_MEASURE_TYPE_NAME);
        var commandForm = InventoryUtil.getHome().getDeleteInventoryLocationGroupCapacityForm();
        
        commandForm.setWarehouseName(warehouseName);
        commandForm.setInventoryLocationGroupName(inventoryLocationGroupName);
        commandForm.setUnitOfMeasureKindName(unitOfMeasureKindName);
        commandForm.setUnitOfMeasureTypeName(unitOfMeasureTypeName);
        
        InventoryUtil.getHome().deleteInventoryLocationGroupCapacity(getUserVisitPK(request), commandForm);

        var customActionForward = new CustomActionForward(mapping.findForward(ForwardConstants.DISPLAY));
        Map<String, String> parameters = new HashMap<>(2);
        
        parameters.put(ParameterConstants.WAREHOUSE_NAME, warehouseName);
        parameters.put(ParameterConstants.INVENTORY_LOCATION_GROUP_NAME, inventoryLocationGroupName);
        customActionForward.setParameters(parameters);
        
        return customActionForward;
    }
    
}