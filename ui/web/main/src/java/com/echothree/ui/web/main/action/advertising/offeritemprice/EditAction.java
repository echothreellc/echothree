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
import com.echothree.control.user.offer.common.result.EditOfferItemPriceResult;
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
    path = "/Advertising/OfferItemPrice/Edit",
    mappingClass = SecureActionMapping.class,
    name = "OfferItemPriceEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Advertising/OfferItemPrice/Main", redirect = true),
        @SproutForward(name = "Form", path = "/advertising/offeritemprice/edit.jsp")
    }
)
public class EditAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
        String forwardKey = null;
        var offerName = request.getParameter(ParameterConstants.OFFER_NAME);
        var itemName = request.getParameter(ParameterConstants.ITEM_NAME);
        
        try {
            var inventoryConditionName = request.getParameter(ParameterConstants.INVENTORY_CONDITION_NAME);
            var unitOfMeasureTypeName = request.getParameter(ParameterConstants.UNIT_OF_MEASURE_TYPE_NAME);
            var currencyIsoName = request.getParameter(ParameterConstants.CURRENCY_ISO_NAME);
            
            if(forwardKey == null) {
                var actionForm = (EditActionForm)form;
                var commandForm = OfferUtil.getHome().getEditOfferItemPriceForm();
                var spec = OfferUtil.getHome().getOfferItemPriceSpec();
                
                if(offerName == null)
                    offerName = actionForm.getOfferName();
                if(itemName == null)
                    itemName = actionForm.getItemName();
                if(inventoryConditionName == null)
                    inventoryConditionName = actionForm.getInventoryConditionName();
                if(unitOfMeasureTypeName == null)
                    unitOfMeasureTypeName = actionForm.getUnitOfMeasureTypeName();
                if(currencyIsoName == null)
                    currencyIsoName = actionForm.getCurrencyIsoName();
                
                commandForm.setSpec(spec);
                spec.setOfferName(offerName);
                spec.setItemName(itemName);
                spec.setInventoryConditionName(inventoryConditionName);
                spec.setUnitOfMeasureTypeName(unitOfMeasureTypeName);
                spec.setCurrencyIsoName(currencyIsoName);
                            
                if(wasPost(request)) {
                    var edit = OfferUtil.getHome().getOfferItemPriceEdit();
                    
                    commandForm.setEditMode(EditMode.UPDATE);
                    commandForm.setEdit(edit);
                    
                    edit.setUnitPrice(actionForm.getUnitPrice());
                    edit.setMaximumUnitPrice(actionForm.getMaximumUnitPrice());
                    edit.setMinimumUnitPrice(actionForm.getMinimumUnitPrice());
                    edit.setUnitPriceIncrement(actionForm.getUnitPriceIncrement());

                    var commandResult = OfferUtil.getHome().editOfferItemPrice(getUserVisitPK(request), commandForm);
                    
                    if(commandResult.hasErrors()) {
                        var executionResult = commandResult.getExecutionResult();
                        
                        if(executionResult != null) {
                            var result = (EditOfferItemPriceResult)executionResult.getResult();
                            
                            request.setAttribute(AttributeConstants.ENTITY_LOCK, result.getEntityLock());
                        }
                        
                        setCommandResultAttribute(request, commandResult);
                        
                        forwardKey = ForwardConstants.FORM;
                    } else {
                        forwardKey = ForwardConstants.DISPLAY;
                    }
                } else {
                    commandForm.setEditMode(EditMode.LOCK);

                    var commandResult = OfferUtil.getHome().editOfferItemPrice(getUserVisitPK(request), commandForm);
                    var executionResult = commandResult.getExecutionResult();
                    var result = (EditOfferItemPriceResult)executionResult.getResult();
                    
                    if(result != null) {
                        var edit = result.getEdit();
                        
                        if(edit != null) {
                            actionForm.setOfferName(offerName);
                            actionForm.setItemName(itemName);
                            actionForm.setInventoryConditionName(inventoryConditionName);
                            actionForm.setUnitOfMeasureTypeName(unitOfMeasureTypeName);
                            actionForm.setCurrencyIsoName(currencyIsoName);
                            actionForm.setUnitPrice(edit.getUnitPrice());
                            actionForm.setMaximumUnitPrice(edit.getMaximumUnitPrice());
                            actionForm.setMinimumUnitPrice(edit.getMinimumUnitPrice());
                            actionForm.setUnitPriceIncrement(edit.getUnitPriceIncrement());
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