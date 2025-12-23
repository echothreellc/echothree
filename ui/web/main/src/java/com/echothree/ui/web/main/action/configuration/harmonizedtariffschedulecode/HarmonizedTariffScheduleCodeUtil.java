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
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

public class HarmonizedTariffScheduleCodeUtil {

    private HarmonizedTariffScheduleCodeUtil() {
        super();
    }

    private static class HarmonizedTariffScheduleCodeUtilHolder {
        static HarmonizedTariffScheduleCodeUtil instance = new HarmonizedTariffScheduleCodeUtil();
    }

    public static HarmonizedTariffScheduleCodeUtil getInstance() {
        return HarmonizedTariffScheduleCodeUtilHolder.instance;
    }

    public void setupCountryTransfer(HttpServletRequest request, String countryName)
            throws NamingException {
        var commandForm = GeoUtil.getHome().getGetCountryForm();

        commandForm.setCountryName(countryName);

        var commandResult = GeoUtil.getHome().getCountry(MainBaseAction.getUserVisitPK(request), commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (GetCountryResult)executionResult.getResult();

        request.setAttribute(AttributeConstants.COUNTRY, result.getCountry());
    }

}
