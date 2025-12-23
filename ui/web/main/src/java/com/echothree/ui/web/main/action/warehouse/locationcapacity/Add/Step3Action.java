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

package com.echothree.ui.web.main.action.warehouse.locationcapacity.Add;

import com.echothree.control.user.warehouse.common.WarehouseUtil;
import com.echothree.ui.web.main.action.warehouse.locationcapacity.AddActionForm;
import com.echothree.ui.web.main.framework.ForwardConstants;
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
    path = "/Warehouse/LocationCapacity/Add/Step3",
    mappingClass = SecureActionMapping.class,
    name = "LocationCapacityAdd",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Warehouse/LocationCapacity/Main", redirect = true),
        @SproutForward(name = "Form", path = "/warehouse/locationcapacity/add/step3.jsp")
    }
)
public class Step3Action
        extends BaseAddAction<AddActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, AddActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey;
        var warehouseName = request.getParameter(ParameterConstants.WAREHOUSE_NAME);
        var locationName = request.getParameter(ParameterConstants.LOCATION_NAME);
        var unitOfMeasureKindName = request.getParameter(ParameterConstants.UNIT_OF_MEASURE_KIND_NAME);
        var unitOfMeasureTypeName = request.getParameter(ParameterConstants.UNIT_OF_MEASURE_TYPE_NAME);
        
        if(warehouseName == null) {
            warehouseName = form.getWarehouseName();
        }
        
        if(locationName == null) {
            locationName = form.getLocationName();
        }
        
        if(unitOfMeasureKindName == null) {
            unitOfMeasureKindName = form.getUnitOfMeasureKindName();
        }
        
        if(unitOfMeasureTypeName == null) {
            unitOfMeasureTypeName = form.getUnitOfMeasureTypeName();
        }
        
        if(wasPost(request)) {
            var commandForm = WarehouseUtil.getHome().getCreateLocationCapacityForm();
            
            commandForm.setWarehouseName(warehouseName);
            commandForm.setLocationName(locationName);
            commandForm.setUnitOfMeasureKindName(unitOfMeasureKindName);
            commandForm.setUnitOfMeasureTypeName(unitOfMeasureTypeName);
            commandForm.setCapacity(form.getCapacity());

            var commandResult = WarehouseUtil.getHome().createLocationCapacity(getUserVisitPK(request), commandForm);
            
            if(commandResult.hasErrors()) {
                setCommandResultAttribute(request, commandResult);
                forwardKey = ForwardConstants.FORM;
            } else {
                forwardKey = ForwardConstants.DISPLAY;
            }
        } else {
            form.setWarehouseName(warehouseName);
            form.setLocationName(locationName);
            form.setUnitOfMeasureKindName(unitOfMeasureKindName);
            form.setUnitOfMeasureTypeName(unitOfMeasureTypeName);
            form.setCapacity("1");
            forwardKey = ForwardConstants.FORM;
        }

        var customActionForward = new CustomActionForward(mapping.findForward(forwardKey));
        if(forwardKey.equals(ForwardConstants.FORM)) {
            setupLocation(request, warehouseName, locationName);
            setupUnitOfMeasureType(request, unitOfMeasureKindName, unitOfMeasureTypeName);
        } else if(forwardKey.equals(ForwardConstants.DISPLAY)) {
            Map<String, String> parameters = new HashMap<>(2);
            
            parameters.put(ParameterConstants.WAREHOUSE_NAME, warehouseName);
            parameters.put(ParameterConstants.LOCATION_NAME, locationName);
            customActionForward.setParameters(parameters);
        }
        
        return customActionForward;
    }
    
}
