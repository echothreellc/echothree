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

package com.echothree.ui.web.main.action.item.item;

import com.echothree.control.user.item.common.ItemUtil;
import com.echothree.control.user.item.common.result.CreateItemResult;
import com.echothree.model.control.item.common.ItemTypes;
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
    path = "/Item/Item/Add",
    mappingClass = SecureActionMapping.class,
    name = "ItemAdd",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Review", path = "/action/Item/Item/Review", redirect = true),
        @SproutForward(name = "Form", path = "/item/item/add.jsp")
    }
)
public class AddAction
        extends MainBaseAction<AddActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, AddActionForm actionForm, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey;
        String itemName = null;
        
        if(wasPost(request)) {
            var commandForm = ItemUtil.getHome().getCreateItemForm();
            var itemTypeName = actionForm.getItemTypeChoice();
            var isKitOrStyle = itemTypeName == null ? false : itemTypeName.equals(ItemTypes.KIT.name())
                    || itemTypeName.equals(ItemTypes.STYLE.name());
            var inventorySerialized = actionForm.getInventorySerialized();
            
            commandForm.setItemName(actionForm.getItemName());
            commandForm.setItemTypeName(itemTypeName);
            commandForm.setItemUseTypeName(actionForm.getItemUseTypeChoice());
            commandForm.setItemCategoryName(actionForm.getItemCategoryChoice());
            commandForm.setItemAccountingCategoryName(actionForm.getItemAccountingCategoryChoice());
            commandForm.setItemPurchasingCategoryName(actionForm.getItemPurchasingCategoryChoice());
            commandForm.setCompanyName(actionForm.getCompanyChoice());
            commandForm.setItemDeliveryTypeName(actionForm.getItemDeliveryTypeChoice());
            commandForm.setItemInventoryTypeName(actionForm.getItemInventoryTypeChoice());
            commandForm.setInventorySerialized(isKitOrStyle? !inventorySerialized? null: inventorySerialized.toString(): inventorySerialized.toString());
            commandForm.setShippingChargeExempt(actionForm.getShippingChargeExempt().toString());
            commandForm.setSalesOrderStartTime(actionForm.getSalesOrderStartTime());
            commandForm.setSalesOrderEndTime(actionForm.getSalesOrderEndTime());
            commandForm.setPurchaseOrderStartTime(actionForm.getPurchaseOrderStartTime());
            commandForm.setPurchaseOrderEndTime(actionForm.getPurchaseOrderEndTime());
            commandForm.setShippingStartTime(actionForm.getShippingStartTime());
            commandForm.setShippingEndTime(actionForm.getShippingEndTime());
            commandForm.setAllowClubDiscounts(actionForm.getAllowClubDiscounts().toString());
            commandForm.setAllowCouponDiscounts(actionForm.getAllowCouponDiscounts().toString());
            commandForm.setAllowAssociatePayments(actionForm.getAllowAssociatePayments().toString());
            commandForm.setItemStatus(actionForm.getItemStatusChoice());
            commandForm.setUnitOfMeasureKindName(actionForm.getUnitOfMeasureKindChoice());
            commandForm.setItemPriceTypeName(actionForm.getItemPriceTypeChoice());
            commandForm.setCancellationPolicyName(actionForm.getCancellationPolicyChoice());
            commandForm.setReturnPolicyName(actionForm.getReturnPolicyChoice());

            var commandResult = ItemUtil.getHome().createItem(getUserVisitPK(request), commandForm);
            
            if(commandResult.hasErrors()) {
                setCommandResultAttribute(request, commandResult);
                forwardKey = ForwardConstants.FORM;
            } else {
                var createItemResult = (CreateItemResult)commandResult.getExecutionResult().getResult();
                
                forwardKey = ForwardConstants.REVIEW;
                itemName = createItemResult.getItemName();
            }
        } else {
            forwardKey = ForwardConstants.FORM;
        }

        var customActionForward = new CustomActionForward(mapping.findForward(forwardKey));
        if(forwardKey.equals(ForwardConstants.REVIEW)) {
            Map<String, String> parameters = new HashMap<>(1);
            
            parameters.put(ParameterConstants.ITEM_NAME, itemName);
            customActionForward.setParameters(parameters);
        }
        
        return customActionForward;
    }
    
}