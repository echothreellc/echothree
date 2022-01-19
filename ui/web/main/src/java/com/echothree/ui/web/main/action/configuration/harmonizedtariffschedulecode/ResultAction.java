// --------------------------------------------------------------------------------
// Copyright 2002-2022 Echo Three, LLC
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
import com.echothree.control.user.geo.common.form.GetCountryForm;
import com.echothree.control.user.geo.common.result.GetCountryResult;
import com.echothree.control.user.search.common.SearchUtil;
import com.echothree.control.user.search.common.form.GetHarmonizedTariffScheduleCodeResultsForm;
import com.echothree.control.user.search.common.result.GetHarmonizedTariffScheduleCodeResultsResult;
import com.echothree.model.control.search.common.SearchConstants;
import com.echothree.model.control.search.common.SearchOptions;
import com.echothree.model.data.search.common.SearchResultConstants;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.ExecutionResult;
import com.echothree.util.common.transfer.Limit;
import com.echothree.util.common.transfer.ListWrapper;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import static java.lang.Math.toIntExact;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;

@SproutAction(
    path = "/Configuration/HarmonizedTariffScheduleCode/Result",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/configuration/harmonizedtariffschedulecode/result.jsp")
    }
)
public class ResultAction
        extends MainBaseAction<ActionForm> {
    
    final private int pageSize = 20;
    
    public void setupTransfer(HttpServletRequest request)
            throws NamingException {
        GetCountryForm commandForm = GeoUtil.getHome().getGetCountryForm();
        
        commandForm.setGeoCodeName(request.getParameter(ParameterConstants.COUNTRY_NAME));
        
        CommandResult commandResult = GeoUtil.getHome().getCountry(getUserVisitPK(request), commandForm);
        ExecutionResult executionResult = commandResult.getExecutionResult();
        GetCountryResult result = (GetCountryResult)executionResult.getResult();
        
        request.setAttribute(AttributeConstants.COUNTRY, result.getCountry());
    }
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        GetHarmonizedTariffScheduleCodeResultsForm commandForm = SearchUtil.getHome().getGetHarmonizedTariffScheduleCodeResultsForm();
        String results = request.getParameter(ParameterConstants.RESULTS);

        commandForm.setSearchTypeName(SearchConstants.SearchType_EMPLOYEE);

        Set<String> options = new HashSet<>();
        options.add(SearchOptions.HarmonizedTariffScheduleCodeResultIncludeHarmonizedTariffScheduleCode);
        commandForm.setOptions(options);

        if(results == null) {
            String offsetParameter = request.getParameter(new ParamEncoder(AttributeConstants.HARMONIZED_TARIFF_SCHEDULE_CODE_RESULT).encodeParameterName(TableTagParameters.PARAMETER_PAGE));
            Integer offset = offsetParameter == null ? null : (Integer.parseInt(offsetParameter) - 1) * pageSize;

            Map<String, Limit> limits = new HashMap<>();
            limits.put(SearchResultConstants.ENTITY_TYPE_NAME, new Limit(Integer.toString(pageSize), offset == null ? null : offset.toString()));
            commandForm.setLimits(limits);
        }

        CommandResult commandResult = setCommandResultAttribute(request, SearchUtil.getHome().getHarmonizedTariffScheduleCodeResults(getUserVisitPK(request), commandForm));
        if(!commandResult.hasErrors()) {
            ExecutionResult executionResult = commandResult.getExecutionResult();
            GetHarmonizedTariffScheduleCodeResultsResult result = (GetHarmonizedTariffScheduleCodeResultsResult)executionResult.getResult();

            var harmonizedTariffScheduleCodeResultCount = result.getHarmonizedTariffScheduleCodeResultCount();
            if(harmonizedTariffScheduleCodeResultCount != null) {
                request.setAttribute(AttributeConstants.HARMONIZED_TARIFF_SCHEDULE_CODE_RESULT_COUNT, toIntExact(harmonizedTariffScheduleCodeResultCount));
            }

            request.setAttribute(AttributeConstants.HARMONIZED_TARIFF_SCHEDULE_CODE_RESULTS, new ListWrapper<>(result.getHarmonizedTariffScheduleCodeResults()));
        }
        
        setupTransfer(request);
        
        return mapping.findForward(ForwardConstants.DISPLAY);
    }
    
}
