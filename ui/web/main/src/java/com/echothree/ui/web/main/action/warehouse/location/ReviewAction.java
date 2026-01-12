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

package com.echothree.ui.web.main.action.warehouse.location;

import com.echothree.control.user.warehouse.common.WarehouseUtil;
import com.echothree.control.user.warehouse.common.result.GetLocationResult;
import com.echothree.model.control.core.common.CoreOptions;
import com.echothree.model.control.warehouse.common.WarehouseOptions;
import com.echothree.model.control.warehouse.common.transfer.LocationCapacityTransfer;
import com.echothree.model.control.warehouse.common.transfer.LocationNameElementTransfer;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.string.ContactPostalAddressUtils;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/Warehouse/Location/Review",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/warehouse/location/review.jsp")
    }
)
public class ReviewAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
        String forwardKey;
        
        try {
            var commandForm = WarehouseUtil.getHome().getGetLocationForm();
            var warehouseName = request.getParameter(ParameterConstants.WAREHOUSE_NAME);
            var locationName = request.getParameter(ParameterConstants.LOCATION_NAME);
            
            commandForm.setWarehouseName(warehouseName);
            commandForm.setLocationName(locationName);

            Set<String> options = new HashSet<>();
            options.add(WarehouseOptions.LocationIncludeEntityAttributeGroups);
            options.add(WarehouseOptions.LocationIncludeTagScopes);
            options.add(CoreOptions.EntityAttributeGroupIncludeEntityAttributes);
            options.add(CoreOptions.EntityAttributeIncludeValue);
            options.add(CoreOptions.EntityStringAttributeIncludeString);
            options.add(CoreOptions.EntityInstanceIncludeNames);
            options.add(CoreOptions.EntityInstanceIncludeEntityAppearance);
            options.add(CoreOptions.AppearanceIncludeTextDecorations);
            options.add(CoreOptions.AppearanceIncludeTextTransformations);
            commandForm.setOptions(ContactPostalAddressUtils.getInstance().addOptions(options));

            var commandResult = WarehouseUtil.getHome().getLocation(getUserVisitPK(request), commandForm);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetLocationResult)executionResult.getResult();
            Collection<LocationNameElementTransfer> locationNameElements = result.getLocationNameElements();
            Collection<LocationCapacityTransfer> locationCapacities = result.getLocationCapacities();
            
            request.setAttribute(AttributeConstants.LOCATION, result.getLocation());
            request.setAttribute(AttributeConstants.LOCATION_NAME_ELEMENTS, locationNameElements.isEmpty()? null: locationNameElements);
            request.setAttribute(AttributeConstants.LOCATION_VOLUME, result.getLocationVolume());
            request.setAttribute(AttributeConstants.LOCATION_CAPACITIES, locationCapacities.isEmpty()? null: locationCapacities);

            saveToken(request); // Required for LocationIncludeTagScopes and tagScopes.jsp
            forwardKey = ForwardConstants.DISPLAY;
        } catch (NamingException ne) {
            forwardKey = ForwardConstants.ERROR_500;
        }
        
        return mapping.findForward(forwardKey);
    }
    
}