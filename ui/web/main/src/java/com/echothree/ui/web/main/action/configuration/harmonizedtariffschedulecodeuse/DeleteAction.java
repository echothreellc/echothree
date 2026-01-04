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

package com.echothree.ui.web.main.action.configuration.harmonizedtariffschedulecodeuse;

import com.echothree.control.user.item.common.ItemUtil;
import com.echothree.control.user.item.common.result.GetHarmonizedTariffScheduleCodeUseResult;
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
    path = "/Configuration/HarmonizedTariffScheduleCodeUse/Delete",
    mappingClass = SecureActionMapping.class,
    name = "HarmonizedTariffScheduleCodeUseDelete",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Configuration/HarmonizedTariffScheduleCodeUse/Main", redirect = true),
        @SproutForward(name = "Form", path = "/configuration/harmonizedtariffschedulecodeuse/delete.jsp")
    }
)
public class DeleteAction
        extends MainBaseDeleteAction<DeleteActionForm> {

    @Override
    public String getEntityTypeName() {
        return EntityTypes.HarmonizedTariffScheduleCodeUse.name();
    }
    
    @Override
    public void setupParameters(DeleteActionForm actionForm, HttpServletRequest request) {
        actionForm.setCountryName(findParameter(request, ParameterConstants.COUNTRY_NAME, actionForm.getCountryName()));
        actionForm.setHarmonizedTariffScheduleCodeName(findParameter(request, ParameterConstants.HARMONIZED_TARIFF_SCHEDULE_CODE_NAME, actionForm.getHarmonizedTariffScheduleCodeName()));
        actionForm.setHarmonizedTariffScheduleCodeUseTypeName(findParameter(request, ParameterConstants.HARMONIZED_TARIFF_SCHEDULE_CODE_USE_TYPE_NAME, actionForm.getHarmonizedTariffScheduleCodeUseTypeName()));
    }
    
    @Override
    public void setupTransfer(DeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = ItemUtil.getHome().getGetHarmonizedTariffScheduleCodeUseForm();
        
        commandForm.setCountryName(actionForm.getCountryName());
        commandForm.setHarmonizedTariffScheduleCodeName(actionForm.getHarmonizedTariffScheduleCodeName());
        commandForm.setHarmonizedTariffScheduleCodeUseTypeName(actionForm.getHarmonizedTariffScheduleCodeUseTypeName());

        var commandResult = ItemUtil.getHome().getHarmonizedTariffScheduleCodeUse(getUserVisitPK(request), commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (GetHarmonizedTariffScheduleCodeUseResult)executionResult.getResult();
        
        request.setAttribute(AttributeConstants.HARMONIZED_TARIFF_SCHEDULE_CODE_USE, result.getHarmonizedTariffScheduleCodeUse());
    }
    
    @Override
    public CommandResult doDelete(DeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = ItemUtil.getHome().getDeleteHarmonizedTariffScheduleCodeUseForm();

        commandForm.setCountryName(actionForm.getCountryName());
        commandForm.setHarmonizedTariffScheduleCodeName(actionForm.getHarmonizedTariffScheduleCodeName());
        commandForm.setHarmonizedTariffScheduleCodeUseTypeName(actionForm.getHarmonizedTariffScheduleCodeUseTypeName());

        return ItemUtil.getHome().deleteHarmonizedTariffScheduleCodeUse(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(DeleteActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.COUNTRY_NAME, actionForm.getCountryName());
        parameters.put(ParameterConstants.HARMONIZED_TARIFF_SCHEDULE_CODE_NAME, actionForm.getHarmonizedTariffScheduleCodeName());
    }
    
}
