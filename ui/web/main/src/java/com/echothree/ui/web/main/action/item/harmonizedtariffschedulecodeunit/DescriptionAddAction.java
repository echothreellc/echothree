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

package com.echothree.ui.web.main.action.item.harmonizedtariffschedulecodeunit;

import com.echothree.control.user.item.common.ItemUtil;
import com.echothree.control.user.item.common.result.GetHarmonizedTariffScheduleCodeUnitResult;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.MainBaseAddAction;
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
    path = "/Item/HarmonizedTariffScheduleCodeUnit/DescriptionAdd",
    mappingClass = SecureActionMapping.class,
    name = "HarmonizedTariffScheduleCodeUnitDescriptionAdd",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Item/HarmonizedTariffScheduleCodeUnit/Description", redirect = true),
        @SproutForward(name = "Form", path = "/item/harmonizedtariffschedulecodeunit/descriptionAdd.jsp")
    }
)
public class DescriptionAddAction
        extends MainBaseAddAction<DescriptionAddActionForm> {

    @Override
    public void setupParameters(DescriptionAddActionForm actionForm, HttpServletRequest request) {
        actionForm.setHarmonizedTariffScheduleCodeUnitName(findParameter(request, ParameterConstants.HARMONIZED_TARIFF_SCHEDULE_CODE_UNIT_NAME, actionForm.getHarmonizedTariffScheduleCodeUnitName()));
    }
    
    @Override
    public void setupTransfer(DescriptionAddActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = ItemUtil.getHome().getGetHarmonizedTariffScheduleCodeUnitForm();

        commandForm.setHarmonizedTariffScheduleCodeUnitName(actionForm.getHarmonizedTariffScheduleCodeUnitName());

        var commandResult = ItemUtil.getHome().getHarmonizedTariffScheduleCodeUnit(getUserVisitPK(request), commandForm);
        if(!commandResult.hasErrors()) {
            var executionResult = commandResult.getExecutionResult();
            var result = (GetHarmonizedTariffScheduleCodeUnitResult)executionResult.getResult();
            
            request.setAttribute(AttributeConstants.HARMONIZED_TARIFF_SCHEDULE_CODE_UNIT, result.getHarmonizedTariffScheduleCodeUnit());
        }
    }
    
    @Override
    public CommandResult doAdd(DescriptionAddActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = ItemUtil.getHome().getCreateHarmonizedTariffScheduleCodeUnitDescriptionForm();

        commandForm.setHarmonizedTariffScheduleCodeUnitName( actionForm.getHarmonizedTariffScheduleCodeUnitName());
        commandForm.setLanguageIsoName(actionForm.getLanguageChoice());
        commandForm.setDescription(actionForm.getDescription());

        return ItemUtil.getHome().createHarmonizedTariffScheduleCodeUnitDescription(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(DescriptionAddActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.HARMONIZED_TARIFF_SCHEDULE_CODE_UNIT_NAME, actionForm.getHarmonizedTariffScheduleCodeUnitName());
    }
    
}
