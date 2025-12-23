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
import com.echothree.control.user.item.common.result.GetItemHarmonizedTariffScheduleCodeResult;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.MainBaseDeleteAction;
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
    path = "/Item/ItemHarmonizedTariffScheduleCode/Delete",
    mappingClass = SecureActionMapping.class,
    name = "ItemHarmonizedTariffScheduleCodeDelete",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Item/ItemHarmonizedTariffScheduleCode/Main", redirect = true),
        @SproutForward(name = "Form", path = "/item/itemharmonizedtariffschedulecode/delete.jsp")
    }
)
public class DeleteAction
        extends MainBaseDeleteAction<DeleteActionForm> {

    @Override
    public String getEntityTypeName() {
        return EntityTypes.ItemHarmonizedTariffScheduleCode.name();
    }

    @Override
    public void setupParameters(DeleteActionForm actionForm, HttpServletRequest request) {
        actionForm.setItemName(findParameter(request, ParameterConstants.ITEM_NAME, actionForm.getItemName()));
        actionForm.setCountryName(findParameter(request, ParameterConstants.COUNTRY_NAME, actionForm.getCountryName()));
        actionForm.setHarmonizedTariffScheduleCodeUseTypeName(findParameter(request, ParameterConstants.HARMONIZED_TARIFF_SCHEDULE_CODE_USE_TYPE_NAME, actionForm.getHarmonizedTariffScheduleCodeUseTypeName()));
    }

    @Override
    public void setupTransfer(DeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = ItemUtil.getHome().getGetItemHarmonizedTariffScheduleCodeForm();

        commandForm.setItemName(actionForm.getItemName());
        commandForm.setCountryName(actionForm.getCountryName());
        commandForm.setHarmonizedTariffScheduleCodeUseTypeName(actionForm.getHarmonizedTariffScheduleCodeUseTypeName());

        var commandResult = ItemUtil.getHome().getItemHarmonizedTariffScheduleCode(getUserVisitPK(request), commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (GetItemHarmonizedTariffScheduleCodeResult)executionResult.getResult();

        request.setAttribute(AttributeConstants.ITEM_HARMONIZED_TARIFF_SCHEDULE_CODE, result.getItemHarmonizedTariffScheduleCode());
    }

    @Override
    public CommandResult doDelete(DeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = ItemUtil.getHome().getDeleteItemHarmonizedTariffScheduleCodeForm();

        commandForm.setItemName(actionForm.getItemName());
        commandForm.setCountryName(actionForm.getCountryName());
        commandForm.setHarmonizedTariffScheduleCodeUseTypeName(actionForm.getHarmonizedTariffScheduleCodeUseTypeName());

        return ItemUtil.getHome().deleteItemHarmonizedTariffScheduleCode(getUserVisitPK(request), commandForm);
    }

    @Override
    public void setupForwardParameters(DeleteActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.ITEM_NAME, actionForm.getItemName());
    }

}
