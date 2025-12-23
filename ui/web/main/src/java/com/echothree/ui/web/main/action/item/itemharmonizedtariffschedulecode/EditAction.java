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

package com.echothree.ui.web.main.action.item.itemharmonizedtariffschedulecode;

import com.echothree.control.user.item.common.ItemUtil;
import com.echothree.control.user.item.common.edit.ItemHarmonizedTariffScheduleCodeEdit;
import com.echothree.control.user.item.common.form.EditItemHarmonizedTariffScheduleCodeForm;
import com.echothree.control.user.item.common.result.EditItemHarmonizedTariffScheduleCodeResult;
import com.echothree.control.user.item.common.spec.ItemHarmonizedTariffScheduleCodeSpec;
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
    path = "/Item/ItemHarmonizedTariffScheduleCode/Edit",
    mappingClass = SecureActionMapping.class,
    name = "ItemHarmonizedTariffScheduleCodeEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Item/ItemHarmonizedTariffScheduleCode/Main", redirect = true),
        @SproutForward(name = "Form", path = "/item/itemharmonizedtariffschedulecode/edit.jsp")
    }
)
public class EditAction
        extends MainBaseEditAction<EditActionForm, ItemHarmonizedTariffScheduleCodeSpec, ItemHarmonizedTariffScheduleCodeEdit, EditItemHarmonizedTariffScheduleCodeForm, EditItemHarmonizedTariffScheduleCodeResult> {

    @Override
    protected ItemHarmonizedTariffScheduleCodeSpec getSpec(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var spec = ItemUtil.getHome().getItemHarmonizedTariffScheduleCodeSpec();

        spec.setItemName(findParameter(request, ParameterConstants.ITEM_NAME, actionForm.getItemName()));
        spec.setCountryName(findParameter(request, ParameterConstants.COUNTRY_NAME, actionForm.getCountryName()));
        spec.setHarmonizedTariffScheduleCodeUseTypeName(findParameter(request, ParameterConstants.HARMONIZED_TARIFF_SCHEDULE_CODE_USE_TYPE_NAME, actionForm.getHarmonizedTariffScheduleCodeUseTypeName()));

        return spec;
    }

    @Override
    protected ItemHarmonizedTariffScheduleCodeEdit getEdit(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var edit = ItemUtil.getHome().getItemHarmonizedTariffScheduleCodeEdit();

        edit.setHarmonizedTariffScheduleCodeName(actionForm.getHarmonizedTariffScheduleCodeName());

        return edit;
    }

    @Override
    protected EditItemHarmonizedTariffScheduleCodeForm getForm()
            throws NamingException {
        return ItemUtil.getHome().getEditItemHarmonizedTariffScheduleCodeForm();
    }

    @Override
    protected void setupActionForm(HttpServletRequest request, EditActionForm actionForm, EditItemHarmonizedTariffScheduleCodeResult result, ItemHarmonizedTariffScheduleCodeSpec spec, ItemHarmonizedTariffScheduleCodeEdit edit) {
        actionForm.setItemName(spec.getItemName());
        actionForm.setCountryName(spec.getCountryName());
        actionForm.setHarmonizedTariffScheduleCodeUseTypeName(spec.getHarmonizedTariffScheduleCodeUseTypeName());
        actionForm.setHarmonizedTariffScheduleCodeName(edit.getHarmonizedTariffScheduleCodeName());
    }

    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditItemHarmonizedTariffScheduleCodeForm commandForm)
            throws Exception {
        return ItemUtil.getHome().editItemHarmonizedTariffScheduleCode(getUserVisitPK(request), commandForm);
    }

    @Override
    public void setupForwardParameters(EditActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.ITEM_NAME, actionForm.getItemName());
    }

    @Override
    protected void setupTransferForForm(HttpServletRequest request, EditActionForm actionForm, EditItemHarmonizedTariffScheduleCodeResult result) {
        request.setAttribute(AttributeConstants.ITEM_HARMONIZED_TARIFF_SCHEDULE_CODE, result.getItemHarmonizedTariffScheduleCode());
    }

}
