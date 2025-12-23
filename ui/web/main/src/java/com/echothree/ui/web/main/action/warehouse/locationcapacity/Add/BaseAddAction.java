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

import com.echothree.control.user.uom.common.UomUtil;
import com.echothree.control.user.uom.common.result.GetUnitOfMeasureTypeResult;
import com.echothree.control.user.warehouse.common.WarehouseUtil;
import com.echothree.control.user.warehouse.common.result.GetLocationResult;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionForm;

public abstract class BaseAddAction<A
        extends ActionForm> extends MainBaseAction<A> {
    
    protected void setupLocation(HttpServletRequest request, String warehouseName, String locationName)
            throws NamingException {
        var commandForm = WarehouseUtil.getHome().getGetLocationForm();
        
        commandForm.setWarehouseName(warehouseName);
        commandForm.setLocationName(locationName);

        var commandResult = WarehouseUtil.getHome().getLocation(getUserVisitPK(request), commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (GetLocationResult)executionResult.getResult();
        
        request.setAttribute(AttributeConstants.LOCATION, result.getLocation());
    }
    
    protected void setupUnitOfMeasureType(HttpServletRequest request, String unitOfMeasureKindName, String unitOfMeasureTypeName)
            throws NamingException {
        var commandForm = UomUtil.getHome().getGetUnitOfMeasureTypeForm();
            
            commandForm.setUnitOfMeasureKindName(unitOfMeasureKindName);
            commandForm.setUnitOfMeasureTypeName(unitOfMeasureTypeName);

        var commandResult = UomUtil.getHome().getUnitOfMeasureType(getUserVisitPK(request), commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (GetUnitOfMeasureTypeResult)executionResult.getResult();
            
            request.setAttribute(AttributeConstants.UNIT_OF_MEASURE_TYPE, result.getUnitOfMeasureType());
    }
    
}
