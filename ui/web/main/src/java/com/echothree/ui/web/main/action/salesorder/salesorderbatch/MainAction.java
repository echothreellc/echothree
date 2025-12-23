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

package com.echothree.ui.web.main.action.salesorder.salesorderbatch;

import com.echothree.control.user.search.common.SearchUtil;
import com.echothree.control.user.search.common.result.GetSalesOrderBatchResultsResult;
import com.echothree.control.user.search.common.result.SearchSalesOrderBatchesResult;
import com.echothree.model.control.search.common.SearchTypes;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.view.client.web.struts.CustomActionForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import java.util.HashMap;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/SalesOrder/SalesOrderBatch/Main",
    mappingClass = SecureActionMapping.class,
    name = "SalesOrderBatchMain",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/SalesOrder/SalesOrderBatch/Result", redirect = true),
        @SproutForward(name = "Review", path = "/action/SalesOrder/SalesOrderBatch/Review", redirect = true),
        @SproutForward(name = "Form", path = "/salesorder/salesorderbatch/main.jsp")
    }
)
public class MainAction
        extends MainBaseAction<ActionForm> {
    
    private String getBatchName(HttpServletRequest request)
            throws NamingException {
        var commandForm = SearchUtil.getHome().getGetSalesOrderBatchResultsForm();
        String batchName = null;
        
        commandForm.setSearchTypeName(SearchTypes.SALES_ORDER_BATCH_MAINTENANCE.name());

        var commandResult = SearchUtil.getHome().getSalesOrderBatchResults(getUserVisitPK(request), commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (GetSalesOrderBatchResultsResult)executionResult.getResult();
        var salesOrderBatchResults = result.getSalesOrderBatchResults();
        var iter = salesOrderBatchResults.iterator();
        if(iter.hasNext()) {
            batchName = iter.next().getBatchName();
        }
        
        return batchName;
    }
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey = null;
        String batchName = null;
        
        if(forwardKey == null) {
            var actionForm = (MainActionForm)form;

            if(wasPost(request)) {
                var commandForm = SearchUtil.getHome().getSearchSalesOrderBatchesForm();

                commandForm.setSearchTypeName(SearchTypes.SALES_ORDER_BATCH_MAINTENANCE.name());
                commandForm.setBatchName(actionForm.getBatchName());
                commandForm.setCurrencyIsoName(actionForm.getCurrencyChoice());
                commandForm.setPaymentMethodName(actionForm.getPaymentMethodChoice());
                commandForm.setSalesOrderBatchStatusChoice(actionForm.getSalesOrderBatchStatusChoice());
                commandForm.setBatchAliasTypeName(actionForm.getBatchAliasTypeChoice());
                commandForm.setAlias(actionForm.getAlias());
                commandForm.setCreatedSince(actionForm.getCreatedSince());
                commandForm.setModifiedSince(actionForm.getModifiedSince());

                var commandResult = SearchUtil.getHome().searchSalesOrderBatches(getUserVisitPK(request), commandForm);

                if(commandResult.hasErrors()) {
                    setCommandResultAttribute(request, commandResult);
                    forwardKey = ForwardConstants.FORM;
                } else {
                    var executionResult = commandResult.getExecutionResult();
                    var result = (SearchSalesOrderBatchesResult)executionResult.getResult();
                    var count = result.getCount();

                    if(count == 0 || count > 1) {
                        forwardKey = ForwardConstants.DISPLAY;
                    } else {
                        batchName = getBatchName(request);
                        forwardKey = ForwardConstants.REVIEW;
                    }
                }
            } else {
                forwardKey = ForwardConstants.FORM;
            }
        }

        var customActionForward = new CustomActionForward(mapping.findForward(forwardKey));
        if(forwardKey.equals(ForwardConstants.REVIEW)) {
            Map<String, String> parameters = new HashMap<>(1);
            
            parameters.put(ParameterConstants.BATCH_NAME, batchName);
            customActionForward.setParameters(parameters);
        }
        
        return customActionForward;
    }
    
}