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
import com.echothree.control.user.item.common.edit.ItemEdit;
import com.echothree.control.user.item.common.form.EditItemForm;
import com.echothree.control.user.item.common.result.EditItemResult;
import com.echothree.control.user.item.common.spec.ItemSpec;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.MainBaseEditAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.CommandResult;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

@SproutAction(
    path = "/Item/Item/Edit",
    mappingClass = SecureActionMapping.class,
    name = "ItemEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Item/Item/Review", redirect = true),
        @SproutForward(name = "Form", path = "/item/item/edit.jsp")
    }
)
public class EditAction
        extends MainBaseEditAction<EditActionForm, ItemSpec, ItemEdit, EditItemForm, EditItemResult> {

    @Override
    protected ItemSpec getSpec(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var spec = ItemUtil.getHome().getItemSpec();

        spec.setItemName(findParameter(request, ParameterConstants.ORIGINAL_ITEM_NAME, actionForm.getOriginalItemName()));

        return spec;
    }

    @Override
    protected ItemEdit getEdit(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var edit = ItemUtil.getHome().getItemEdit();

        edit.setItemName(actionForm.getItemName());
        edit.setItemCategoryName(actionForm.getItemCategoryChoice());
        edit.setItemAccountingCategoryName(actionForm.getItemAccountingCategoryChoice());
        edit.setItemPurchasingCategoryName(actionForm.getItemPurchasingCategoryChoice());
        edit.setShippingChargeExempt(actionForm.getShippingChargeExempt().toString());
        edit.setSalesOrderStartTime(actionForm.getSalesOrderStartTime());
        edit.setSalesOrderEndTime(actionForm.getSalesOrderEndTime());
        edit.setPurchaseOrderStartTime(actionForm.getPurchaseOrderStartTime());
        edit.setPurchaseOrderEndTime(actionForm.getPurchaseOrderEndTime());
        edit.setShippingStartTime(actionForm.getShippingStartTime());
        edit.setShippingEndTime(actionForm.getShippingEndTime());
        edit.setAllowClubDiscounts(actionForm.getAllowClubDiscounts().toString());
        edit.setAllowCouponDiscounts(actionForm.getAllowCouponDiscounts().toString());
        edit.setAllowAssociatePayments(actionForm.getAllowAssociatePayments().toString());
        edit.setCancellationPolicyName(actionForm.getCancellationPolicyChoice());
        edit.setReturnPolicyName(actionForm.getReturnPolicyChoice());

        return edit;
    }

    @Override
    protected EditItemForm getForm()
            throws NamingException {
        return ItemUtil.getHome().getEditItemForm();
    }

    @Override
    protected void setupActionForm(HttpServletRequest request, EditActionForm actionForm, EditItemResult result, ItemSpec spec, ItemEdit edit) {
        actionForm.setOriginalItemName(edit.getItemName());
        actionForm.setItemName(edit.getItemName());
        actionForm.setItemCategoryChoice(edit.getItemCategoryName());
        actionForm.setItemAccountingCategoryChoice(edit.getItemAccountingCategoryName());
        actionForm.setItemPurchasingCategoryChoice(edit.getItemPurchasingCategoryName());
        actionForm.setShippingChargeExempt(Boolean.valueOf(edit.getShippingChargeExempt()));
        actionForm.setSalesOrderStartTime(edit.getSalesOrderStartTime());
        actionForm.setSalesOrderEndTime(edit.getSalesOrderEndTime());
        actionForm.setPurchaseOrderStartTime(edit.getPurchaseOrderStartTime());
        actionForm.setPurchaseOrderEndTime(edit.getPurchaseOrderEndTime());
        actionForm.setShippingStartTime(edit.getShippingStartTime());
        actionForm.setShippingEndTime(edit.getShippingEndTime());
        actionForm.setAllowClubDiscounts(Boolean.valueOf(edit.getAllowClubDiscounts()));
        actionForm.setAllowCouponDiscounts(Boolean.valueOf(edit.getAllowCouponDiscounts()));
        actionForm.setAllowAssociatePayments(Boolean.valueOf(edit.getAllowAssociatePayments()));
        actionForm.setCancellationPolicyChoice(edit.getCancellationPolicyName());
        actionForm.setReturnPolicyChoice(edit.getReturnPolicyName());
    }

    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditItemForm commandForm)
            throws Exception {
        return ItemUtil.getHome().editItem(getUserVisitPK(request), commandForm);
    }

    @Override
    public void setupForwardParameters(EditActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.ITEM_NAME, actionForm.getItemName());
    }

    @Override
    protected void setupTransferForForm(HttpServletRequest request, EditActionForm actionForm, EditItemResult result) {
        request.setAttribute(AttributeConstants.ITEM, result.getItem());
    }

}
