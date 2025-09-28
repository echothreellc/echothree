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

package com.echothree.ui.web.main.action.item.itemvolume;

import com.echothree.control.user.item.common.ItemUtil;
import com.echothree.control.user.item.common.result.GetItemResult;
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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/Item/ItemVolume/Add",
    mappingClass = SecureActionMapping.class,
    name = "ItemVolumeAdd",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Item/ItemVolume/Main", redirect = true),
        @SproutForward(name = "Form", path = "/item/itemvolume/add.jsp")
    }
)
public class AddAction
        extends MainBaseAction<AddActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, AddActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey;
        var itemName = request.getParameter(ParameterConstants.ITEM_NAME);
        
        if(itemName == null) {
            itemName = form.getItemName();
        }
        
        if(wasPost(request)) {
            var commandForm = ItemUtil.getHome().getCreateItemVolumeForm();
            
            commandForm.setItemName(itemName);
            commandForm.setUnitOfMeasureTypeName(form.getUnitOfMeasureTypeChoice());
            commandForm.setItemVolumeTypeName(form.getItemVolumeTypeChoice());
            commandForm.setHeight(form.getHeight());
            commandForm.setHeightUnitOfMeasureTypeName(form.getHeightUnitOfMeasureTypeChoice());
            commandForm.setWidth(form.getWidth());
            commandForm.setWidthUnitOfMeasureTypeName(form.getWidthUnitOfMeasureTypeChoice());
            commandForm.setDepth(form.getDepth());
            commandForm.setDepthUnitOfMeasureTypeName(form.getDepthUnitOfMeasureTypeChoice());

            var commandResult = ItemUtil.getHome().createItemVolume(getUserVisitPK(request), commandForm);
            
            if(commandResult.hasErrors()) {
                setCommandResultAttribute(request, commandResult);
                forwardKey = ForwardConstants.FORM;
            } else {
                forwardKey = ForwardConstants.DISPLAY;
            }
        } else {
            form.setItemName(itemName);
            forwardKey = ForwardConstants.FORM;
        }

        var customActionForward = new CustomActionForward(mapping.findForward(forwardKey));
        if(forwardKey.equals(ForwardConstants.FORM)) {
            if(itemName != null) {
                var commandForm = ItemUtil.getHome().getGetItemForm();
                
                commandForm.setItemName(itemName);

                var commandResult = ItemUtil.getHome().getItem(getUserVisitPK(request), commandForm);
                var executionResult = commandResult.getExecutionResult();
                var result = (GetItemResult)executionResult.getResult();
                
                request.setAttribute(AttributeConstants.ITEM, result.getItem());
            }
            
            request.setAttribute(AttributeConstants.ITEM_NAME, itemName);
        } else if(forwardKey.equals(ForwardConstants.DISPLAY)) {
            Map<String, String> parameters = new HashMap<>(1);
            
            parameters.put(ParameterConstants.ITEM_NAME, itemName);
            customActionForward.setParameters(parameters);
        }
        
        return customActionForward;
    }
    
}
