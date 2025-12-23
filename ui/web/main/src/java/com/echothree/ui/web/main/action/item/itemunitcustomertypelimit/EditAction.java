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

package com.echothree.ui.web.main.action.item.itemunitcustomertypelimit;

import com.echothree.control.user.item.common.ItemUtil;
import com.echothree.control.user.item.common.edit.ItemUnitCustomerTypeLimitEdit;
import com.echothree.control.user.item.common.form.EditItemUnitCustomerTypeLimitForm;
import com.echothree.control.user.item.common.result.EditItemUnitCustomerTypeLimitResult;
import com.echothree.control.user.item.common.spec.ItemUnitCustomerTypeLimitSpec;
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
    path = "/Item/ItemUnitCustomerTypeLimit/Edit",
    mappingClass = SecureActionMapping.class,
    name = "ItemUnitCustomerTypeLimitEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Item/ItemUnitCustomerTypeLimit/Main", redirect = true),
        @SproutForward(name = "Form", path = "/item/itemunitcustomertypelimit/edit.jsp")
    }
)
public class EditAction
        extends MainBaseEditAction<EditActionForm, ItemUnitCustomerTypeLimitSpec, ItemUnitCustomerTypeLimitEdit, EditItemUnitCustomerTypeLimitForm, EditItemUnitCustomerTypeLimitResult> {

    @Override
    protected ItemUnitCustomerTypeLimitSpec getSpec(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var spec = ItemUtil.getHome().getItemUnitCustomerTypeLimitSpec();

        spec.setItemName(findParameter(request, ParameterConstants.ITEM_NAME, actionForm.getItemName()));
        spec.setInventoryConditionName(findParameter(request, ParameterConstants.INVENTORY_CONDITION_NAME, actionForm.getInventoryConditionName()));
        spec.setUnitOfMeasureTypeName(findParameter(request, ParameterConstants.UNIT_OF_MEASURE_TYPE_NAME, actionForm.getUnitOfMeasureTypeName()));
        spec.setCustomerTypeName(findParameter(request, ParameterConstants.CUSTOMER_TYPE_NAME, actionForm.getCustomerTypeName()));

        return spec;
    }

    @Override
    protected ItemUnitCustomerTypeLimitEdit getEdit(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var edit = ItemUtil.getHome().getItemUnitCustomerTypeLimitEdit();

        edit.setMinimumQuantity(actionForm.getMinimumQuantity());
        edit.setMaximumQuantity(actionForm.getMaximumQuantity());

        return edit;
    }

    @Override
    protected EditItemUnitCustomerTypeLimitForm getForm()
            throws NamingException {
        return ItemUtil.getHome().getEditItemUnitCustomerTypeLimitForm();
    }

    @Override
    protected void setupActionForm(HttpServletRequest request, EditActionForm actionForm, EditItemUnitCustomerTypeLimitResult result, ItemUnitCustomerTypeLimitSpec spec, ItemUnitCustomerTypeLimitEdit edit) {
        actionForm.setItemName(spec.getItemName());
        actionForm.setInventoryConditionName(spec.getInventoryConditionName());
        actionForm.setUnitOfMeasureTypeName(spec.getUnitOfMeasureTypeName());
        actionForm.setCustomerTypeName(spec.getCustomerTypeName());
        actionForm.setMinimumQuantity(edit.getMinimumQuantity());
        actionForm.setMaximumQuantity(edit.getMaximumQuantity());
    }

    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditItemUnitCustomerTypeLimitForm commandForm)
            throws Exception {
        return ItemUtil.getHome().editItemUnitCustomerTypeLimit(getUserVisitPK(request), commandForm);
    }

    @Override
    public void setupForwardParameters(EditActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.ITEM_NAME, actionForm.getItemName());
    }

    @Override
    protected void setupTransferForForm(HttpServletRequest request, EditActionForm actionForm, EditItemUnitCustomerTypeLimitResult result) {
        request.setAttribute(AttributeConstants.ITEM_UNIT_CUSTOMER_TYPE_LIMIT, result.getItemUnitCustomerTypeLimit());
    }

}
