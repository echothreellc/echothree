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

package com.echothree.ui.web.main.action.item.itemprice;

import com.echothree.control.user.item.common.ItemUtil;
import com.echothree.control.user.item.common.edit.ItemPriceEdit;
import com.echothree.control.user.item.common.form.EditItemPriceForm;
import com.echothree.control.user.item.common.result.EditItemPriceResult;
import com.echothree.control.user.item.common.spec.ItemPriceSpec;
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
    path = "/Item/ItemPrice/Edit",
    mappingClass = SecureActionMapping.class,
    name = "ItemPriceEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Item/ItemPrice/Main", redirect = true),
        @SproutForward(name = "Form", path = "/item/itemprice/edit.jsp")
    }
)
public class EditAction
        extends MainBaseEditAction<EditActionForm, ItemPriceSpec, ItemPriceEdit, EditItemPriceForm, EditItemPriceResult> {

    @Override
    protected ItemPriceSpec getSpec(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var spec = ItemUtil.getHome().getItemPriceSpec();

        spec.setItemName(findParameter(request, ParameterConstants.ITEM_NAME, actionForm.getItemName()));
        spec.setInventoryConditionName(findParameter(request, ParameterConstants.INVENTORY_CONDITION_NAME, actionForm.getInventoryConditionName()));
        spec.setUnitOfMeasureTypeName(findParameter(request, ParameterConstants.UNIT_OF_MEASURE_TYPE_NAME, actionForm.getUnitOfMeasureTypeName()));
        spec.setCurrencyIsoName(findParameter(request, ParameterConstants.CURRENCY_ISO_NAME, actionForm.getCurrencyIsoName()));

        return spec;
    }

    @Override
    protected ItemPriceEdit getEdit(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var edit = ItemUtil.getHome().getItemPriceEdit();

        edit.setUnitPrice(actionForm.getUnitPrice());
        edit.setMaximumUnitPrice(actionForm.getMaximumUnitPrice());
        edit.setMinimumUnitPrice(actionForm.getMinimumUnitPrice());
        edit.setUnitPriceIncrement(actionForm.getUnitPriceIncrement());

        return edit;
    }

    @Override
    protected EditItemPriceForm getForm()
            throws NamingException {
        return ItemUtil.getHome().getEditItemPriceForm();
    }

    @Override
    protected void setupActionForm(HttpServletRequest request, EditActionForm actionForm, EditItemPriceResult result, ItemPriceSpec spec, ItemPriceEdit edit) {
        actionForm.setItemName(spec.getItemName());
        actionForm.setInventoryConditionName(spec.getInventoryConditionName());
        actionForm.setUnitOfMeasureTypeName(spec.getUnitOfMeasureTypeName());
        actionForm.setCurrencyIsoName(spec.getCurrencyIsoName());
        actionForm.setUnitPrice(edit.getUnitPrice());
        actionForm.setMaximumUnitPrice(edit.getMaximumUnitPrice());
        actionForm.setMinimumUnitPrice(edit.getMinimumUnitPrice());
        actionForm.setUnitPriceIncrement(edit.getUnitPriceIncrement());
    }

    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditItemPriceForm commandForm)
            throws Exception {
        return ItemUtil.getHome().editItemPrice(getUserVisitPK(request), commandForm);
    }

    @Override
    public void setupForwardParameters(EditActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.ITEM_NAME, actionForm.getItemName());
    }

    @Override
    protected void setupTransferForForm(HttpServletRequest request, EditActionForm actionForm, EditItemPriceResult result) {
        request.setAttribute(AttributeConstants.ITEM_PRICE, result.getItemPrice());
    }

}
