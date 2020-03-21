// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

import com.echothree.control.user.search.common.SearchUtil;
import com.echothree.control.user.search.common.form.GetHarmonizedTariffScheduleCodeResultsForm;
import com.echothree.control.user.search.common.form.SearchHarmonizedTariffScheduleCodesForm;
import com.echothree.control.user.search.common.result.GetHarmonizedTariffScheduleCodeResultsResult;
import com.echothree.control.user.search.common.result.SearchHarmonizedTariffScheduleCodesResult;
import com.echothree.model.control.search.common.SearchConstants;
import com.echothree.model.control.search.common.transfer.HarmonizedTariffScheduleCodeResultTransfer;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.string.StringUtils;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.ExecutionResult;
import com.echothree.view.client.web.struts.CustomActionForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/Configuration/HarmonizedTariffScheduleCode/Search",
    mappingClass = SecureActionMapping.class,
    name = "HarmonizedTariffScheduleCodeSearch",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = ForwardConstants.DISPLAY, path = "/action/Configuration/HarmonizedTariffScheduleCode/Main"),
        @SproutForward(name = ForwardConstants.RESULT, path = "/action/Configuration/HarmonizedTariffScheduleCode/Result", redirect = true),
        @SproutForward(name = ForwardConstants.REVIEW, path = "/action/Configuration/HarmonizedTariffScheduleCode/Review", redirect = true)
    }
)
public class SearchAction
        extends MainBaseAction<SearchActionForm> {
    
    private String getHarmonizedTariffScheduleCodeName(HttpServletRequest request)
            throws NamingException {
        GetHarmonizedTariffScheduleCodeResultsForm commandForm = SearchUtil.getHome().getGetHarmonizedTariffScheduleCodeResultsForm();
        String harmonizedTariffScheduleCodeName = null;
        
        commandForm.setSearchTypeName(SearchConstants.SearchType_EMPLOYEE);
        
        CommandResult commandResult = SearchUtil.getHome().getHarmonizedTariffScheduleCodeResults(getUserVisitPK(request), commandForm);
        ExecutionResult executionResult = commandResult.getExecutionResult();
        GetHarmonizedTariffScheduleCodeResultsResult result = (GetHarmonizedTariffScheduleCodeResultsResult)executionResult.getResult();
        List<HarmonizedTariffScheduleCodeResultTransfer> harmonizedTariffScheduleCodeResults = result.getHarmonizedTariffScheduleCodeResults();
        Iterator<HarmonizedTariffScheduleCodeResultTransfer> iter = harmonizedTariffScheduleCodeResults.iterator();
        if(iter.hasNext()) {
            harmonizedTariffScheduleCodeName = iter.next().getHarmonizedTariffScheduleCodeName();
        }
        
        return harmonizedTariffScheduleCodeName;
    }
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, SearchActionForm actionForm, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey;
        String countryName = actionForm.getCountryName();
        String harmonizedTariffScheduleCodeName = null;

        if(wasPost(request)) {
            SearchHarmonizedTariffScheduleCodesForm commandForm = SearchUtil.getHome().getSearchHarmonizedTariffScheduleCodesForm();
            String q = StringUtils.getInstance().trimToNull(actionForm.getQ());

            commandForm.setSearchTypeName(SearchConstants.SearchType_EMPLOYEE);
            commandForm.setQ("countryGeoCodeName:" + countryName + (q == null ? "" : " AND " + q));

            CommandResult commandResult = SearchUtil.getHome().searchHarmonizedTariffScheduleCodes(getUserVisitPK(request), commandForm);

            if(commandResult.hasErrors()) {
                setCommandResultAttribute(request, commandResult);
                forwardKey = ForwardConstants.DISPLAY;
            } else {
                ExecutionResult executionResult = commandResult.getExecutionResult();
                SearchHarmonizedTariffScheduleCodesResult result = (SearchHarmonizedTariffScheduleCodesResult)executionResult.getResult();
                int count = result.getCount();

                if(count == 0 || count > 1) {
                    forwardKey = ForwardConstants.RESULT;
                } else {
                    harmonizedTariffScheduleCodeName = getHarmonizedTariffScheduleCodeName(request);
                    forwardKey = ForwardConstants.REVIEW;
                }
            }
        } else {
            forwardKey = ForwardConstants.DISPLAY;
        }
        
        CustomActionForward customActionForward = new CustomActionForward(mapping.findForward(forwardKey));
        if(forwardKey.equals(ForwardConstants.DISPLAY) || forwardKey.equals(ForwardConstants.RESULT)) {
            Map<String, String> parameters = new HashMap<>(1);
            
            parameters.put(ParameterConstants.COUNTRY_NAME, countryName);
            customActionForward.setParameters(parameters);
        } else if(forwardKey.equals(ForwardConstants.REVIEW)) {
            Map<String, String> parameters = new HashMap<>(2);
            
            parameters.put(ParameterConstants.COUNTRY_NAME, countryName);
            parameters.put(ParameterConstants.HARMONIZED_TARIFF_SCHEDULE_CODE_NAME, harmonizedTariffScheduleCodeName);
            customActionForward.setParameters(parameters);
        }
        
        return customActionForward;
    }
    
}