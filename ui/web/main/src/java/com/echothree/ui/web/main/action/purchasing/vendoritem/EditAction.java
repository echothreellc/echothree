// --------------------------------------------------------------------------------
// Copyright 2002-2024 Echo Three, LLC
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

package com.echothree.ui.web.main.action.purchasing.vendoritem;

import com.echothree.control.user.vendor.common.VendorUtil;
import com.echothree.control.user.vendor.common.edit.VendorItemEdit;
import com.echothree.control.user.vendor.common.form.EditVendorItemForm;
import com.echothree.control.user.vendor.common.result.EditVendorItemResult;
import com.echothree.control.user.vendor.common.spec.VendorItemSpec;
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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/Purchasing/VendorItem/Edit",
    mappingClass = SecureActionMapping.class,
    name = "VendorItemEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Purchasing/VendorItem/Main", redirect = true),
        @SproutForward(name = "Form", path = "/purchasing/vendoritem/edit.jsp")
    }
)
public class EditAction
        extends MainBaseAction<EditActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, EditActionForm actionForm, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey = null;
        String vendorName = request.getParameter(ParameterConstants.VENDOR_NAME);
        String originalVendorItemName = request.getParameter(ParameterConstants.ORIGINAL_VENDOR_ITEM_NAME);
        EditVendorItemForm commandForm = VendorUtil.getHome().getEditVendorItemForm();
        var spec = VendorUtil.getHome().getVendorItemUniversalSpec();
        
        if(vendorName == null)
            vendorName = actionForm.getVendorName();
        if(originalVendorItemName == null)
            originalVendorItemName = actionForm.getOriginalVendorItemName();
        
        commandForm.setSpec(spec);
        spec.setVendorName(vendorName);
        spec.setVendorItemName(originalVendorItemName);
        
        if(wasPost(request)) {
            VendorItemEdit edit = VendorUtil.getHome().getVendorItemEdit();
            
            commandForm.setEditMode(EditMode.UPDATE);
            commandForm.setEdit(edit);
            
            edit.setVendorItemName(actionForm.getVendorItemName());
            edit.setDescription(actionForm.getDescription());
            edit.setPriority(actionForm.getPriority());
            edit.setCancellationPolicyName(actionForm.getCancellationPolicyChoice());
            edit.setReturnPolicyName(actionForm.getReturnPolicyChoice());
            
            CommandResult commandResult = VendorUtil.getHome().editVendorItem(getUserVisitPK(request), commandForm);
            
            if(commandResult.hasErrors()) {
                ExecutionResult executionResult = commandResult.getExecutionResult();
                
                if(executionResult != null) {
                    EditVendorItemResult result = (EditVendorItemResult)executionResult.getResult();
                    
                    request.setAttribute(AttributeConstants.ENTITY_LOCK, result.getEntityLock());
                }
                
                setCommandResultAttribute(request, commandResult);
                
                forwardKey = ForwardConstants.FORM;
            } else {
                forwardKey = ForwardConstants.DISPLAY;
            }
        } else {
            commandForm.setEditMode(EditMode.LOCK);
            
            CommandResult commandResult = VendorUtil.getHome().editVendorItem(getUserVisitPK(request), commandForm);
            ExecutionResult executionResult = commandResult.getExecutionResult();
            EditVendorItemResult result = (EditVendorItemResult)executionResult.getResult();
            
            if(result != null) {
                VendorItemEdit edit = result.getEdit();
                
                if(edit != null) {
                    actionForm.setVendorName(vendorName);
                    actionForm.setOriginalVendorItemName(edit.getVendorItemName());
                    actionForm.setVendorItemName(edit.getVendorItemName());
                    actionForm.setDescription(edit.getDescription());
                    actionForm.setPriority(edit.getPriority());
                    actionForm.setCancellationPolicyChoice(edit.getCancellationPolicyName());
                    actionForm.setReturnPolicyChoice(edit.getReturnPolicyName());
                }
                
                request.setAttribute(AttributeConstants.ENTITY_LOCK, result.getEntityLock());
            }
            
            setCommandResultAttribute(request, commandResult);
            
            forwardKey = ForwardConstants.FORM;
        }
        
        CustomActionForward customActionForward = new CustomActionForward(mapping.findForward(forwardKey));
        if(forwardKey.equals(ForwardConstants.FORM)) {
            request.setAttribute(AttributeConstants.VENDOR_NAME, vendorName);
            request.setAttribute(AttributeConstants.ORIGINAL_VENDOR_ITEM_NAME, originalVendorItemName);
        } else if(forwardKey.equals(ForwardConstants.DISPLAY)) {
            Map<String, String> parameters = new HashMap<>(2);
            
            parameters.put(ParameterConstants.VENDOR_NAME, vendorName);
            customActionForward.setParameters(parameters);
        }
        
        return customActionForward;
    }
    
}