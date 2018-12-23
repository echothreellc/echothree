// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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
import com.echothree.control.user.inventory.common.edit.InventoryLocationGroupDescriptionEdit;
import com.echothree.control.user.inventory.common.form.EditInventoryLocationGroupDescriptionForm;
import com.echothree.control.user.inventory.common.result.EditInventoryLocationGroupDescriptionResult;
import com.echothree.control.user.inventory.common.spec.InventoryLocationGroupDescriptionSpec;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.command.ExecutionResult;
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
    path = "/Inventory/InventoryLocationGroup/DescriptionEdit",
    mappingClass = SecureActionMapping.class,
    name = "InventoryLocationGroupDescriptionEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Inventory/InventoryLocationGroup/Description", redirect = true),
        @SproutForward(name = "Form", path = "/inventory/inventorylocationgroup/descriptionEdit.jsp")
    }
)
public class DescriptionEditAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
        String forwardKey = null;
        String warehouseName = request.getParameter(ParameterConstants.WAREHOUSE_NAME);
        String inventoryLocationGroupName = request.getParameter(ParameterConstants.INVENTORY_LOCATION_GROUP_NAME);
        String languageIsoName = request.getParameter(ParameterConstants.LANGUAGE_ISO_NAME);
        
        try {
            if(forwardKey == null) {
                DescriptionEditActionForm actionForm = (DescriptionEditActionForm)form;
                EditInventoryLocationGroupDescriptionForm commandForm = InventoryUtil.getHome().getEditInventoryLocationGroupDescriptionForm();
                InventoryLocationGroupDescriptionSpec spec = InventoryUtil.getHome().getInventoryLocationGroupDescriptionSpec();
                
                if(warehouseName == null)
                    warehouseName = actionForm.getWarehouseName();
                if(inventoryLocationGroupName == null)
                    inventoryLocationGroupName = actionForm.getInventoryLocationGroupName();
                if(languageIsoName == null)
                    languageIsoName = actionForm.getLanguageIsoName();
                
                commandForm.setSpec(spec);
                spec.setWarehouseName(warehouseName);
                spec.setInventoryLocationGroupName(inventoryLocationGroupName);
                spec.setLanguageIsoName(languageIsoName);
                
                if(wasPost(request)) {
                    InventoryLocationGroupDescriptionEdit edit = InventoryUtil.getHome().getInventoryLocationGroupDescriptionEdit();
                    
                    commandForm.setEditMode(EditMode.UPDATE);
                    commandForm.setEdit(edit);
                    edit.setDescription(actionForm.getDescription());
                    
                    CommandResult commandResult = InventoryUtil.getHome().editInventoryLocationGroupDescription(getUserVisitPK(request), commandForm);
                    
                    if(commandResult.hasErrors()) {
                        ExecutionResult executionResult = commandResult.getExecutionResult();
                        
                        if(executionResult != null) {
                            EditInventoryLocationGroupDescriptionResult result = (EditInventoryLocationGroupDescriptionResult)executionResult.getResult();
                            
                            request.setAttribute(AttributeConstants.ENTITY_LOCK, result.getEntityLock());
                        }
                        
                        setCommandResultAttribute(request, commandResult);
                        
                        forwardKey = ForwardConstants.FORM;
                    } else {
                        forwardKey = ForwardConstants.DISPLAY;
                    }
                } else {
                    commandForm.setEditMode(EditMode.LOCK);
                    
                    CommandResult commandResult = InventoryUtil.getHome().editInventoryLocationGroupDescription(getUserVisitPK(request), commandForm);
                    ExecutionResult executionResult = commandResult.getExecutionResult();
                    EditInventoryLocationGroupDescriptionResult result = (EditInventoryLocationGroupDescriptionResult)executionResult.getResult();
                    
                    if(result != null) {
                        InventoryLocationGroupDescriptionEdit edit = result.getEdit();
                        
                        if(edit != null) {
                            actionForm.setWarehouseName(warehouseName);
                            actionForm.setInventoryLocationGroupName(inventoryLocationGroupName);
                            actionForm.setLanguageIsoName(languageIsoName);
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
        
        CustomActionForward customActionForward = new CustomActionForward(mapping.findForward(forwardKey));
        if(forwardKey.equals(ForwardConstants.FORM)) {
            request.setAttribute(AttributeConstants.WAREHOUSE_NAME, warehouseName);
            request.setAttribute(AttributeConstants.INVENTORY_LOCATION_GROUP_NAME, inventoryLocationGroupName);
            request.setAttribute(AttributeConstants.LANGUAGE_ISO_NAME, languageIsoName);
        } else if(forwardKey.equals(ForwardConstants.DISPLAY)) {
            Map<String, String> parameters = new HashMap<>(2);
            
            parameters.put(ParameterConstants.WAREHOUSE_NAME, warehouseName);
            parameters.put(ParameterConstants.INVENTORY_LOCATION_GROUP_NAME, inventoryLocationGroupName);
            customActionForward.setParameters(parameters);
        }
        
        return customActionForward;
    }
    
}