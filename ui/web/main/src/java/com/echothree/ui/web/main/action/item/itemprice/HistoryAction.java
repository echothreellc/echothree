// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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

package com.echothree.ui.web.main.action.item.itemprice;

import com.echothree.control.user.item.common.ItemUtil;
import com.echothree.control.user.item.common.result.GetItemPriceResult;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/Item/ItemPrice/History",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/item/itemprice/history.jsp")
    }
)
public class HistoryAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey = null;
        var commandForm = ItemUtil.getHome().getGetItemPriceForm();

        commandForm.setItemName(request.getParameter(ParameterConstants.ITEM_NAME));
        commandForm.setInventoryConditionName(request.getParameter(ParameterConstants.INVENTORY_CONDITION_NAME));
        commandForm.setUnitOfMeasureTypeName(request.getParameter(ParameterConstants.UNIT_OF_MEASURE_TYPE_NAME));
        commandForm.setCurrencyIsoName(request.getParameter(ParameterConstants.CURRENCY_ISO_NAME));
        commandForm.setIncludeHistory(String.valueOf(true));

        var commandResult = ItemUtil.getHome().getItemPrice(getUserVisitPK(request), commandForm);
        
        if(!commandResult.hasErrors()) {
            var executionResult = commandResult.getExecutionResult();
            var result = (GetItemPriceResult)executionResult.getResult();

            request.setAttribute(AttributeConstants.ITEM_PRICE, result.getItemPrice());
            request.setAttribute(AttributeConstants.ITEM_PRICE_HISTORY, result.getHistory());
            forwardKey = ForwardConstants.DISPLAY;
        }

        return mapping.findForward(forwardKey == null ? ForwardConstants.ERROR_404 : forwardKey);
    }
    
}