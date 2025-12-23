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

package com.echothree.ui.web.main.action.item.itemshippingtime;

import com.echothree.control.user.item.common.ItemUtil;
import com.echothree.control.user.item.common.edit.ItemShippingTimeEdit;
import com.echothree.control.user.item.common.form.EditItemShippingTimeForm;
import com.echothree.control.user.item.common.result.EditItemShippingTimeResult;
import com.echothree.control.user.item.common.spec.ItemShippingTimeSpec;
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
    path = "/Item/ItemShippingTime/Edit",
    mappingClass = SecureActionMapping.class,
    name = "ItemShippingTimeEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Item/ItemShippingTime/Main", redirect = true),
        @SproutForward(name = "Form", path = "/item/itemshippingtime/edit.jsp")
    }
)
public class EditAction
        extends MainBaseEditAction<EditActionForm, ItemShippingTimeSpec, ItemShippingTimeEdit, EditItemShippingTimeForm, EditItemShippingTimeResult> {

    @Override
    protected ItemShippingTimeSpec getSpec(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var spec = ItemUtil.getHome().getItemShippingTimeSpec();

        spec.setItemName(findParameter(request, ParameterConstants.ITEM_NAME, actionForm.getItemName()));
        spec.setCustomerTypeName(findParameter(request, ParameterConstants.CUSTOMER_TYPE_NAME, actionForm.getCustomerTypeName()));

        return spec;
    }

    @Override
    protected ItemShippingTimeEdit getEdit(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var edit = ItemUtil.getHome().getItemShippingTimeEdit();

        edit.setShippingStartTime(actionForm.getShippingStartTime());
        edit.setShippingEndTime(actionForm.getShippingEndTime());

        return edit;
    }

    @Override
    protected EditItemShippingTimeForm getForm()
            throws NamingException {
        return ItemUtil.getHome().getEditItemShippingTimeForm();
    }

    @Override
    protected void setupActionForm(HttpServletRequest request, EditActionForm actionForm, EditItemShippingTimeResult result, ItemShippingTimeSpec spec, ItemShippingTimeEdit edit) {
        actionForm.setItemName(spec.getItemName());
        actionForm.setCustomerTypeName(spec.getCustomerTypeName());
        actionForm.setShippingStartTime(edit.getShippingStartTime());
        actionForm.setShippingEndTime(edit.getShippingEndTime());
    }

    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditItemShippingTimeForm commandForm)
            throws Exception {
        return ItemUtil.getHome().editItemShippingTime(getUserVisitPK(request), commandForm);
    }

    @Override
    public void setupForwardParameters(EditActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.ITEM_NAME, actionForm.getItemName());
    }

    @Override
    protected void setupTransferForForm(HttpServletRequest request, EditActionForm actionForm, EditItemShippingTimeResult result) {
        request.setAttribute(AttributeConstants.ITEM_SHIPPING_TIME, result.getItemShippingTime());
    }

}
