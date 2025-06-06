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

package com.echothree.ui.web.main.action.purchasing.vendoritemcost;

import com.echothree.control.user.vendor.common.VendorUtil;
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
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/Purchasing/VendorItemCost/Add",
    mappingClass = SecureActionMapping.class,
    name = "VendorItemCostAdd",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Purchasing/VendorItemCost/Main", redirect = true),
        @SproutForward(name = "Form", path = "/purchasing/vendoritemcost/add.jsp")
    }
)
public class AddAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
        String forwardKey = null;
        var vendorName = request.getParameter(ParameterConstants.VENDOR_NAME);
        var vendorItemName = request.getParameter(ParameterConstants.VENDOR_ITEM_NAME);
        
        try {
            if(forwardKey == null) {
                var actionForm = (AddActionForm)form;
                
                    if(vendorName == null)
                        vendorName = actionForm.getVendorName();
                    if(vendorItemName == null)
                        vendorItemName = actionForm.getVendorItemName();
                
                if(wasPost(request)) {
                    var commandForm = VendorUtil.getHome().getCreateVendorItemCostForm();
                    
                    commandForm.setVendorName(vendorName);
                    commandForm.setVendorItemName(vendorItemName);
                    commandForm.setInventoryConditionName(actionForm.getInventoryConditionChoice());
                    commandForm.setUnitOfMeasureTypeName(actionForm.getUnitOfMeasureTypeChoice());
                    commandForm.setUnitCost(actionForm.getUnitCost());

                    var commandResult = VendorUtil.getHome().createVendorItemCost(getUserVisitPK(request), commandForm);
                    
                    if(commandResult.hasErrors()) {
                        setCommandResultAttribute(request, commandResult);
                        forwardKey = ForwardConstants.FORM;
                    } else {
                        forwardKey = ForwardConstants.DISPLAY;
                    }
                } else {
                    actionForm.setVendorName(vendorName);
                    actionForm.setVendorItemName(vendorItemName);
                    forwardKey = ForwardConstants.FORM;
                }
            }
        } catch (NamingException ne) {
            forwardKey = ForwardConstants.ERROR_500;
        }

        var customActionForward = new CustomActionForward(mapping.findForward(forwardKey));
        if(forwardKey.equals(ForwardConstants.FORM)) {
            request.setAttribute(AttributeConstants.VENDOR_NAME, vendorName);
            request.setAttribute(AttributeConstants.VENDOR_ITEM_NAME, vendorItemName);
        } else if(forwardKey.equals(ForwardConstants.DISPLAY)) {
            Map<String, String> parameters = new HashMap<>(2);
            
            parameters.put(ParameterConstants.VENDOR_NAME, vendorName);
            parameters.put(ParameterConstants.VENDOR_ITEM_NAME, vendorItemName);
            customActionForward.setParameters(parameters);
        }
        
        return customActionForward;
    }
    
}
