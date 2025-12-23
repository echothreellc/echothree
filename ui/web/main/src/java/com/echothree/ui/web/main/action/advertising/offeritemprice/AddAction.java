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

package com.echothree.ui.web.main.action.advertising.offeritemprice;

import com.echothree.control.user.item.common.ItemUtil;
import com.echothree.control.user.item.common.result.GetItemResult;
import com.echothree.control.user.offer.common.OfferUtil;
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
    path = "/Advertising/OfferItemPrice/Add",
    mappingClass = SecureActionMapping.class,
    name = "OfferItemPriceAdd",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Advertising/OfferItemPrice/Main", redirect = true),
        @SproutForward(name = "Form", path = "/advertising/offeritemprice/add.jsp")
    }
)
public class AddAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey = null;
        var offerName = request.getParameter(ParameterConstants.OFFER_NAME);
        var itemName = request.getParameter(ParameterConstants.ITEM_NAME);
        
        try {
            if(forwardKey == null) {
                var actionForm = (AddActionForm)form;
                
                if(offerName == null)
                    offerName = actionForm.getOfferName();
                if(itemName == null)
                    itemName = actionForm.getItemName();
                
                if(wasPost(request)) {
                    var commandForm = OfferUtil.getHome().getCreateOfferItemPriceForm();
                    
                    commandForm.setOfferName(offerName);
                    commandForm.setItemName(itemName);
                    commandForm.setInventoryConditionName(actionForm.getInventoryConditionChoice());
                    commandForm.setUnitOfMeasureTypeName(actionForm.getUnitOfMeasureTypeChoice());
                    commandForm.setCurrencyIsoName(actionForm.getCurrencyChoice());
                    commandForm.setUnitPrice(actionForm.getUnitPrice());
                    commandForm.setMinimumUnitPrice(actionForm.getMinimumUnitPrice());
                    commandForm.setMaximumUnitPrice(actionForm.getMaximumUnitPrice());
                    commandForm.setUnitPriceIncrement(actionForm.getUnitPriceIncrement());

                    var commandResult = OfferUtil.getHome().createOfferItemPrice(getUserVisitPK(request), commandForm);
                    
                    if(commandResult.hasErrors()) {
                        setCommandResultAttribute(request, commandResult);
                        forwardKey = ForwardConstants.FORM;
                    } else {
                        forwardKey = ForwardConstants.DISPLAY;
                    }
                } else {
                    actionForm.setOfferName(offerName);
                    actionForm.setItemName(itemName);
                    forwardKey = ForwardConstants.FORM;
                }
            }
        } catch (NamingException ne) {
            forwardKey = ForwardConstants.ERROR_500;
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
            
            request.setAttribute(AttributeConstants.OFFER_NAME, offerName);
            request.setAttribute(AttributeConstants.ITEM_NAME, itemName);
        } else if(forwardKey.equals(ForwardConstants.DISPLAY)) {
            Map<String, String> parameters = new HashMap<>(2);
            
            parameters.put(ParameterConstants.OFFER_NAME, offerName);
            parameters.put(ParameterConstants.ITEM_NAME, itemName);
            customActionForward.setParameters(parameters);
        }
        
        return customActionForward;
    }
    
}
