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

package com.echothree.ui.web.main.action.customer.customer;

import com.echothree.control.user.search.common.SearchUtil;
import com.echothree.control.user.search.common.result.GetCustomerResultsResult;
import com.echothree.model.control.core.common.CoreOptions;
import com.echothree.model.control.search.common.SearchOptions;
import com.echothree.model.control.search.common.SearchTypes;
import com.echothree.model.data.search.common.SearchResultConstants;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;

@SproutAction(
    path = "/Customer/Customer/Result",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/customer/customer/result.jsp")
    }
)
public class ResultAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        var commandForm = SearchUtil.getHome().getGetCustomerResultsForm();
        var results = request.getParameter(ParameterConstants.RESULTS);

        commandForm.setSearchTypeName(SearchTypes.ORDER_ENTRY.name());

        Set<String> options = new HashSet<>();
        options.add(SearchOptions.CustomerResultIncludeCustomer);
        options.add(CoreOptions.EntityInstanceIncludeEntityAppearance);
        options.add(CoreOptions.AppearanceIncludeTextDecorations);
        options.add(CoreOptions.AppearanceIncludeTextTransformations);
        commandForm.setOptions(options);

        if(results == null) {
            var offsetParameter = request.getParameter(new ParamEncoder("customerResult").encodeParameterName(TableTagParameters.PARAMETER_PAGE));
            var offset = offsetParameter == null ? null : (Integer.parseInt(offsetParameter) - 1) * 20;

            Map<String, Limit> limits = new HashMap<>();
            limits.put(SearchResultConstants.ENTITY_TYPE_NAME, new Limit("20", offset == null ? null : offset.toString()));
            commandForm.setLimits(limits);
        }

        var commandResult = setCommandResultAttribute(request, SearchUtil.getHome().getCustomerResults(getUserVisitPK(request), commandForm));
        if(!commandResult.hasErrors()) {
            var executionResult = commandResult.getExecutionResult();
            var result = (GetCustomerResultsResult)executionResult.getResult();

            var customerResultCount = result.getCustomerResultCount();
            if(customerResultCount != null) {
                request.setAttribute(AttributeConstants.CUSTOMER_RESULT_COUNT, toIntExact(customerResultCount));
            }

            request.setAttribute(AttributeConstants.CUSTOMER_RESULTS, new ListWrapper<>(result.getCustomerResults()));
        }

        return mapping.findForward(ForwardConstants.DISPLAY);
    }
    
}
