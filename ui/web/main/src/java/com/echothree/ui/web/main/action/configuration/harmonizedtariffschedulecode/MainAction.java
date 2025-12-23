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

import com.echothree.control.user.item.common.ItemUtil;
import com.echothree.control.user.item.common.result.GetHarmonizedTariffScheduleCodesResult;
import com.echothree.model.data.item.common.HarmonizedTariffScheduleCodeConstants;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.transfer.Limit;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;

@SproutAction(
    path = "/Configuration/HarmonizedTariffScheduleCode/Main",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/configuration/harmonizedtariffschedulecode/main.jsp")
    }
)
public class MainAction
        extends MainBaseAction<ActionForm> {
    
    final private int pageSize = 20;
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey;
        var countryName = request.getParameter(ParameterConstants.COUNTRY_NAME);
        var commandForm = ItemUtil.getHome().getGetHarmonizedTariffScheduleCodesForm();

        commandForm.setCountryName(countryName);

        var offsetParameter = request.getParameter(new ParamEncoder(AttributeConstants.HARMONIZED_TARIFF_SCHEDULE_CODE).encodeParameterName(TableTagParameters.PARAMETER_PAGE));
        var offset = offsetParameter == null ? null : (Integer.parseInt(offsetParameter) - 1) * pageSize;

        Map<String, Limit> limits = new HashMap<>();
        limits.put(HarmonizedTariffScheduleCodeConstants.ENTITY_TYPE_NAME, new Limit(Integer.toString(pageSize), offset == null ? null : offset.toString()));
        commandForm.setLimits(limits);

        var commandResult = ItemUtil.getHome().getHarmonizedTariffScheduleCodes(getUserVisitPK(request), commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (GetHarmonizedTariffScheduleCodesResult)executionResult.getResult();
        var country = result.getCountry();

        if(country == null) {
            forwardKey = ForwardConstants.ERROR_404;
        } else {
            request.setAttribute(AttributeConstants.COUNTRY, country);
            request.setAttribute(AttributeConstants.HARMONIZED_TARIFF_SCHEDULE_CODE_COUNT, Integer.valueOf(result.getHarmonizedTariffScheduleCodeCount().toString()));
            request.setAttribute(AttributeConstants.HARMONIZED_TARIFF_SCHEDULE_CODES, result.getHarmonizedTariffScheduleCodes());
            setupDtAttributes(request, AttributeConstants.HARMONIZED_TARIFF_SCHEDULE_CODE);
            forwardKey = ForwardConstants.DISPLAY;
        }
        
        return mapping.findForward(forwardKey);
    }
    
}