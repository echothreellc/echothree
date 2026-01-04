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

package com.echothree.ui.web.main.action.warehouse.warehouse;

import com.echothree.control.user.search.common.SearchUtil;
import com.echothree.control.user.search.common.result.GetWarehouseResultsResult;
import com.echothree.control.user.search.common.result.SearchWarehousesResult;
import com.echothree.model.control.search.common.SearchOptions;
import com.echothree.model.control.search.common.SearchTypes;
import com.echothree.model.data.search.common.SearchResultConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.string.StringUtils;
import com.echothree.util.common.transfer.Limit;
import com.echothree.view.client.web.struts.CustomActionForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import java.util.HashMap;
import java.util.HashSet;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/Warehouse/Warehouse/Search",
    mappingClass = SecureActionMapping.class,
    name = "WarehouseSearch",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = ForwardConstants.DISPLAY, path = "/action/Warehouse/Warehouse/Main"),
        @SproutForward(name = ForwardConstants.RESULT, path = "/action/Warehouse/Warehouse/Result", redirect = true),
        @SproutForward(name = ForwardConstants.REVIEW, path = "/action/Warehouse/Warehouse/Review", redirect = true)
    }
)
public class SearchAction
        extends MainBaseAction<SearchActionForm> {
    
    private String getWarehouseName(HttpServletRequest request)
            throws NamingException {
        var commandForm = SearchUtil.getHome().getGetWarehouseResultsForm();
        String warehouseName = null;

        var options = new HashSet<String>();
        options.add(SearchOptions.WarehouseResultIncludeWarehouse);
        commandForm.setOptions(options);

        var limits = new HashMap<String, Limit>();
        limits.put(SearchResultConstants.ENTITY_TYPE_NAME, new Limit("1", "0"));
        commandForm.setLimits(limits);

        commandForm.setSearchTypeName(SearchTypes.EMPLOYEE.name());

        var commandResult = SearchUtil.getHome().getWarehouseResults(getUserVisitPK(request), commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (GetWarehouseResultsResult)executionResult.getResult();
        var iter = result.getWarehouseResults().iterator();
        if(iter.hasNext()) {
            warehouseName = iter.next().getWarehouse().getWarehouseName();
        }
        
        return warehouseName;
    }
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, SearchActionForm actionForm, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey;
        String warehouseName = null;

        if(wasPost(request)) {
            var commandForm = SearchUtil.getHome().getSearchWarehousesForm();
            var q = StringUtils.getInstance().trimToNull(actionForm.getQ());

            commandForm.setSearchTypeName(SearchTypes.EMPLOYEE.name());
            commandForm.setQ(q);

            var commandResult = SearchUtil.getHome().searchWarehouses(getUserVisitPK(request), commandForm);

            if(commandResult.hasErrors()) {
                setCommandResultAttribute(request, commandResult);
                forwardKey = ForwardConstants.DISPLAY;
            } else {
                var executionResult = commandResult.getExecutionResult();
                var result = (SearchWarehousesResult)executionResult.getResult();
                var count = result.getCount();

                if(count == 0 || count > 1) {
                    forwardKey = ForwardConstants.RESULT;
                } else {
                    warehouseName = getWarehouseName(request);
                    forwardKey = ForwardConstants.REVIEW;
                }
            }
        } else {
            forwardKey = ForwardConstants.DISPLAY;
        }

        var customActionForward = new CustomActionForward(mapping.findForward(forwardKey));
        if(forwardKey.equals(ForwardConstants.REVIEW)) {
            var parameters = new HashMap<String, String>(1);
            
            parameters.put(ParameterConstants.WAREHOUSE_NAME, warehouseName);
            customActionForward.setParameters(parameters);
        }
        
        return customActionForward;
    }
    
}