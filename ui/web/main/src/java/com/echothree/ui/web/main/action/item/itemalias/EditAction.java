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

package com.echothree.ui.web.main.action.item.itemalias;

import com.echothree.control.user.item.common.ItemUtil;
import com.echothree.control.user.item.common.edit.ItemAliasEdit;
import com.echothree.control.user.item.common.form.EditItemAliasForm;
import com.echothree.control.user.item.common.result.EditItemAliasResult;
import com.echothree.control.user.item.common.spec.ItemAliasSpec;
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
    path = "/Item/ItemAlias/Edit",
    mappingClass = SecureActionMapping.class,
    name = "ItemAliasEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Item/ItemAlias/Main", redirect = true),
        @SproutForward(name = "Form", path = "/item/itemalias/edit.jsp")
    }
)
public class EditAction
        extends MainBaseEditAction<EditActionForm, ItemAliasSpec, ItemAliasEdit, EditItemAliasForm, EditItemAliasResult> {

    @Override
    protected ItemAliasSpec getSpec(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var spec = ItemUtil.getHome().getItemAliasSpec();

        spec.setAlias(findParameter(request, ParameterConstants.ORIGINAL_ALIAS, actionForm.getOriginalAlias()));
        actionForm.setItemName(findParameter(request, ParameterConstants.ITEM_NAME, actionForm.getItemName()));

        return spec;
    }

    @Override
    protected ItemAliasEdit getEdit(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var edit = ItemUtil.getHome().getItemAliasEdit();

        edit.setUnitOfMeasureTypeName(actionForm.getUnitOfMeasureTypeChoice());
        edit.setItemAliasTypeName(actionForm.getItemAliasTypeChoice());
        edit.setAlias(actionForm.getAlias());

        return edit;
    }

    @Override
    protected EditItemAliasForm getForm()
            throws NamingException {
        return ItemUtil.getHome().getEditItemAliasForm();
    }

    @Override
    protected void setupActionForm(HttpServletRequest request, EditActionForm actionForm, EditItemAliasResult result, ItemAliasSpec spec, ItemAliasEdit edit) {
        actionForm.setUnitOfMeasureTypeChoice(edit.getUnitOfMeasureTypeName());
        actionForm.setItemAliasTypeChoice(edit.getItemAliasTypeName());
        actionForm.setAlias(edit.getAlias());
        actionForm.setOriginalAlias(edit.getAlias());
    }

    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditItemAliasForm commandForm)
            throws Exception {
        return ItemUtil.getHome().editItemAlias(getUserVisitPK(request), commandForm);
    }

    @Override
    public void setupForwardParameters(EditActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.ITEM_NAME, actionForm.getItemName());
    }

    @Override
    protected void setupTransferForForm(HttpServletRequest request, EditActionForm actionForm, EditItemAliasResult result) {
        request.setAttribute(AttributeConstants.ITEM_ALIAS, result.getItemAlias());
    }

}
