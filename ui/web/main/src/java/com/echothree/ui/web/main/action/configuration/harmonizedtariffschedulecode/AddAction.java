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

package com.echothree.ui.web.main.action.configuration.harmonizedtariffschedulecode;

import com.echothree.control.user.geo.common.GeoUtil;
import com.echothree.control.user.geo.common.result.GetCountryResult;
import com.echothree.control.user.item.common.ItemUtil;
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
    path = "/Configuration/HarmonizedTariffScheduleCode/Add",
    mappingClass = SecureActionMapping.class,
    name = "HarmonizedTariffScheduleCodeAdd",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Configuration/HarmonizedTariffScheduleCode/Main", redirect = true),
        @SproutForward(name = "Form", path = "/configuration/harmonizedtariffschedulecode/add.jsp")
    }
)
public class AddAction
        extends MainBaseAddAction<AddActionForm> {

    @Override
    public void setupParameters(AddActionForm actionForm, HttpServletRequest request) {
        actionForm.setCountryName(findParameter(request, ParameterConstants.COUNTRY_NAME, actionForm.getCountryName()));
    }
    
    @Override
    public void setupDefaults(AddActionForm actionForm)
            throws NamingException {
        actionForm.setSortOrder("1");
    }
    
    @Override
    public void setupTransfer(AddActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = GeoUtil.getHome().getGetCountryForm();

        commandForm.setGeoCodeName(actionForm.getCountryName());

        var commandResult = GeoUtil.getHome().getCountry(getUserVisitPK(request), commandForm);
        if(!commandResult.hasErrors()) {
            var executionResult = commandResult.getExecutionResult();
            var result = (GetCountryResult)executionResult.getResult();
            
            request.setAttribute(AttributeConstants.COUNTRY, result.getCountry());
        }
    }
    
    @Override
    public CommandResult doAdd(AddActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = ItemUtil.getHome().getCreateHarmonizedTariffScheduleCodeForm();

        commandForm.setCountryName(actionForm.getCountryName());
        commandForm.setHarmonizedTariffScheduleCodeName(actionForm.getHarmonizedTariffScheduleCodeName());
        commandForm.setFirstHarmonizedTariffScheduleCodeUnitName(actionForm.getFirstHarmonizedTariffScheduleCodeUnitChoice());
        commandForm.setSecondHarmonizedTariffScheduleCodeUnitName(actionForm.getSecondHarmonizedTariffScheduleCodeUnitChoice());
        commandForm.setIsDefault(actionForm.getIsDefault().toString());
        commandForm.setSortOrder(actionForm.getSortOrder());
        commandForm.setDescription(actionForm.getDescription());
        commandForm.setOverviewMimeTypeName(actionForm.getOverviewMimeTypeChoice());
        commandForm.setOverview(actionForm.getOverview());

        return ItemUtil.getHome().createHarmonizedTariffScheduleCode(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(AddActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.COUNTRY_NAME, actionForm.getCountryName());
    }
    
}
