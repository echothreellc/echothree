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

package com.echothree.ui.web.main.action.item.partyinventorylevel;

import com.echothree.control.user.inventory.common.InventoryUtil;
import com.echothree.control.user.inventory.common.edit.PartyInventoryLevelEdit;
import com.echothree.control.user.inventory.common.form.EditPartyInventoryLevelForm;
import com.echothree.control.user.inventory.common.result.EditPartyInventoryLevelResult;
import com.echothree.control.user.inventory.common.spec.PartyInventoryLevelSpec;
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
    path = "/Item/PartyInventoryLevel/Edit",
    mappingClass = SecureActionMapping.class,
    name = "PartyInventoryLevelEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Item/PartyInventoryLevel/Main", redirect = true),
        @SproutForward(name = "Form", path = "/item/partyinventorylevel/edit.jsp")
    }
)
public class EditAction
        extends MainBaseEditAction<EditActionForm, PartyInventoryLevelSpec, PartyInventoryLevelEdit, EditPartyInventoryLevelForm, EditPartyInventoryLevelResult> {

    @Override
    protected PartyInventoryLevelSpec getSpec(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var spec = InventoryUtil.getHome().getPartyInventoryLevelSpec();

        spec.setItemName(findParameter(request, ParameterConstants.ITEM_NAME, actionForm.getItemName()));
        spec.setPartyName(findParameter(request, ParameterConstants.PARTY_NAME, actionForm.getPartyName()));
        spec.setInventoryConditionName(findParameter(request, ParameterConstants.INVENTORY_CONDITION_NAME, actionForm.getInventoryConditionName()));

        return spec;
    }

    @Override
    protected PartyInventoryLevelEdit getEdit(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var edit = InventoryUtil.getHome().getPartyInventoryLevelEdit();

        edit.setMinimumInventoryUnitOfMeasureTypeName(actionForm.getMinimumInventoryUnitOfMeasureTypeChoice());
        edit.setMinimumInventory(actionForm.getMinimumInventory());
        edit.setMaximumInventoryUnitOfMeasureTypeName(actionForm.getMaximumInventoryUnitOfMeasureTypeChoice());
        edit.setMaximumInventory(actionForm.getMaximumInventory());
        edit.setReorderQuantityUnitOfMeasureTypeName(actionForm.getReorderQuantityUnitOfMeasureTypeChoice());
        edit.setReorderQuantity(actionForm.getReorderQuantity());

        return edit;
    }

    @Override
    protected EditPartyInventoryLevelForm getForm()
            throws NamingException {
        return InventoryUtil.getHome().getEditPartyInventoryLevelForm();
    }

    @Override
    protected void setupActionForm(HttpServletRequest request, EditActionForm actionForm, EditPartyInventoryLevelResult result, PartyInventoryLevelSpec spec, PartyInventoryLevelEdit edit) {
        actionForm.setItemName(spec.getItemName());
        actionForm.setPartyName(spec.getPartyName());
        actionForm.setInventoryConditionName(spec.getInventoryConditionName());
        actionForm.setMinimumInventoryUnitOfMeasureTypeChoice(edit.getMinimumInventoryUnitOfMeasureTypeName());
        actionForm.setMinimumInventory(edit.getMinimumInventory());
        actionForm.setMaximumInventoryUnitOfMeasureTypeChoice(edit.getMaximumInventoryUnitOfMeasureTypeName());
        actionForm.setMaximumInventory(edit.getMaximumInventory());
        actionForm.setReorderQuantityUnitOfMeasureTypeChoice(edit.getReorderQuantityUnitOfMeasureTypeName());
        actionForm.setReorderQuantity(edit.getReorderQuantity());
    }

    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditPartyInventoryLevelForm commandForm)
            throws Exception {
        return InventoryUtil.getHome().editPartyInventoryLevel(getUserVisitPK(request), commandForm);
    }

    @Override
    public void setupForwardParameters(EditActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.ITEM_NAME, actionForm.getItemName());
    }

    @Override
    protected void setupTransferForForm(HttpServletRequest request, EditActionForm actionForm, EditPartyInventoryLevelResult result) {
        request.setAttribute(AttributeConstants.PARTY_INVENTORY_LEVEL, result.getPartyInventoryLevel());
    }

}
