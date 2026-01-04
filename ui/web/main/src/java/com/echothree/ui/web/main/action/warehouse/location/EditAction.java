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
import com.echothree.control.user.warehouse.common.result.EditLocationResult;
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
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/Warehouse/Location/Edit",
    mappingClass = SecureActionMapping.class,
    name = "LocationEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Warehouse/Location/Main", redirect = true),
        @SproutForward(name = "Form", path = "/warehouse/location/edit.jsp")
    }
)
public class EditAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
        String forwardKey = null;
        var warehouseName = request.getParameter(ParameterConstants.WAREHOUSE_NAME);
        var originalLocationName = request.getParameter(ParameterConstants.ORIGINAL_LOCATION_NAME);
        
        try {
            if(forwardKey == null) {
                var actionForm = (EditActionForm)form;
                var commandForm = WarehouseUtil.getHome().getEditLocationForm();
                var spec = WarehouseUtil.getHome().getLocationSpec();
                
                if(warehouseName == null)
                    warehouseName = actionForm.getWarehouseName();
                if(originalLocationName == null)
                    originalLocationName = actionForm.getOriginalLocationName();
                
                commandForm.setSpec(spec);
                spec.setWarehouseName(warehouseName);
                spec.setLocationName(originalLocationName);
                
                if(wasPost(request)) {
                    var edit = WarehouseUtil.getHome().getLocationEdit();
                    
                    commandForm.setEditMode(EditMode.UPDATE);
                    commandForm.setEdit(edit);
                    
                    edit.setLocationName(actionForm.getLocationName());
                    edit.setLocationTypeName(actionForm.getLocationTypeChoice());
                    edit.setLocationUseTypeName(actionForm.getLocationUseTypeChoice());
                    edit.setVelocity(actionForm.getVelocity());
                    edit.setInventoryLocationGroupName(actionForm.getInventoryLocationGroupChoice());
                    edit.setDescription(actionForm.getDescription());

                    var commandResult = WarehouseUtil.getHome().editLocation(getUserVisitPK(request), commandForm);
                    
                    if(commandResult.hasErrors()) {
                        var executionResult = commandResult.getExecutionResult();
                        
                        if(executionResult != null) {
                            var result = (EditLocationResult)executionResult.getResult();
                            
                            request.setAttribute(AttributeConstants.ENTITY_LOCK, result.getEntityLock());
                        }
                        
                        setCommandResultAttribute(request, commandResult);
                        
                        forwardKey = ForwardConstants.FORM;
                    } else {
                        forwardKey = ForwardConstants.DISPLAY;
                    }
                } else {
                    commandForm.setEditMode(EditMode.LOCK);

                    var commandResult = WarehouseUtil.getHome().editLocation(getUserVisitPK(request), commandForm);
                    var executionResult = commandResult.getExecutionResult();
                    var result = (EditLocationResult)executionResult.getResult();
                    
                    if(result != null) {
                        var edit = result.getEdit();
                        
                        if(edit != null) {
                            actionForm.setWarehouseName(warehouseName);
                            actionForm.setOriginalLocationName(edit.getLocationName());
                            actionForm.setLocationName(edit.getLocationName());
                            actionForm.setLocationTypeChoice(edit.getLocationTypeName());
                            actionForm.setLocationUseTypeChoice(edit.getLocationUseTypeName());
                            actionForm.setVelocity(edit.getVelocity());
                            actionForm.setInventoryLocationGroupChoice(edit.getInventoryLocationGroupName());
                            actionForm.setDescription(edit.getDescription());
                        }
                        
                        request.setAttribute(AttributeConstants.ENTITY_LOCK, result.getEntityLock());
                    }
                    
                    setCommandResultAttribute(request, commandResult);
                    
                    forwardKey = ForwardConstants.FORM;
                }
            }
        } catch (NamingException ne) {
            forwardKey = ForwardConstants.ERROR_500;
        }

        var customActionForward = new CustomActionForward(mapping.findForward(forwardKey));
        if(forwardKey.equals(ForwardConstants.FORM)) {
            request.setAttribute(AttributeConstants.WAREHOUSE_NAME, warehouseName);
        } else if(forwardKey.equals(ForwardConstants.DISPLAY)) {
            Map<String, String> parameters = new HashMap<>(1);
            
            parameters.put(ParameterConstants.WAREHOUSE_NAME, warehouseName);
            customActionForward.setParameters(parameters);
        }
        
        return customActionForward;
    }
    
}