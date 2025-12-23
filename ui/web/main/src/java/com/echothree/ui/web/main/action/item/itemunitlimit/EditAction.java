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

package com.echothree.ui.web.main.action.item.itemunitlimit;

import com.echothree.control.user.item.common.ItemUtil;
import com.echothree.control.user.item.common.edit.ItemUnitLimitEdit;
import com.echothree.control.user.item.common.form.EditItemUnitLimitForm;
import com.echothree.control.user.item.common.result.EditItemUnitLimitResult;
import com.echothree.control.user.item.common.spec.ItemUnitLimitSpec;
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
    path = "/Item/ItemUnitLimit/Edit",
    mappingClass = SecureActionMapping.class,
    name = "ItemUnitLimitEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Item/ItemUnitLimit/Main", redirect = true),
        @SproutForward(name = "Form", path = "/item/itemunitlimit/edit.jsp")
    }
)
public class EditAction
        extends MainBaseEditAction<EditActionForm, ItemUnitLimitSpec, ItemUnitLimitEdit, EditItemUnitLimitForm, EditItemUnitLimitResult> {

    @Override
    protected ItemUnitLimitSpec getSpec(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var spec = ItemUtil.getHome().getItemUnitLimitSpec();

        spec.setItemName(findParameter(request, ParameterConstants.ITEM_NAME, actionForm.getItemName()));
        spec.setInventoryConditionName(findParameter(request, ParameterConstants.INVENTORY_CONDITION_NAME, actionForm.getInventoryConditionName()));
        spec.setUnitOfMeasureTypeName(findParameter(request, ParameterConstants.UNIT_OF_MEASURE_TYPE_NAME, actionForm.getUnitOfMeasureTypeName()));

        return spec;
    }

    @Override
    protected ItemUnitLimitEdit getEdit(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var edit = ItemUtil.getHome().getItemUnitLimitEdit();

        edit.setMinimumQuantity(actionForm.getMinimumQuantity());
        edit.setMaximumQuantity(actionForm.getMaximumQuantity());

        return edit;
    }

    @Override
    protected EditItemUnitLimitForm getForm()
            throws NamingException {
        return ItemUtil.getHome().getEditItemUnitLimitForm();
    }

    @Override
    protected void setupActionForm(HttpServletRequest request, EditActionForm actionForm, EditItemUnitLimitResult result, ItemUnitLimitSpec spec, ItemUnitLimitEdit edit) {
        actionForm.setItemName(spec.getItemName());
        actionForm.setInventoryConditionName(spec.getInventoryConditionName());
        actionForm.setUnitOfMeasureTypeName(spec.getUnitOfMeasureTypeName());
        actionForm.setMinimumQuantity(edit.getMinimumQuantity());
        actionForm.setMaximumQuantity(edit.getMaximumQuantity());
    }

    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditItemUnitLimitForm commandForm)
            throws Exception {
        return ItemUtil.getHome().editItemUnitLimit(getUserVisitPK(request), commandForm);
    }

    @Override
    public void setupForwardParameters(EditActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.ITEM_NAME, actionForm.getItemName());
    }

    @Override
    protected void setupTransferForForm(HttpServletRequest request, EditActionForm actionForm, EditItemUnitLimitResult result) {
        request.setAttribute(AttributeConstants.ITEM_UNIT_LIMIT, result.getItemUnitLimit());
    }

}
