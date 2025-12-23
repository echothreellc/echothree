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

package com.echothree.ui.web.main.action.item.item;

import com.echothree.control.user.search.common.SearchUtil;
import com.echothree.control.user.search.common.result.GetItemResultsResult;
import com.echothree.control.user.search.common.result.SearchItemsResult;
import com.echothree.model.control.search.common.SearchTypes;
import com.echothree.model.control.search.common.SearchUseTypes;
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
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/Item/Item/Main",
    mappingClass = SecureActionMapping.class,
    name = "ItemMain",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Item/Item/Result", redirect = true),
        @SproutForward(name = "Review", path = "/action/Item/Item/Review", redirect = true),
        @SproutForward(name = "Form", path = "/item/item/main.jsp")
    }
)
public class MainAction
        extends MainBaseAction<MainActionForm> {
    
    private String getItemName(HttpServletRequest request)
            throws NamingException {
        var commandForm = SearchUtil.getHome().getGetItemResultsForm();
        String itemName = null;
        
        commandForm.setSearchTypeName(SearchTypes.ITEM_MAINTENANCE.name());

        var commandResult = SearchUtil.getHome().getItemResults(getUserVisitPK(request), commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (GetItemResultsResult)executionResult.getResult();
        var itemResults = result.getItemResults();
        var iter = itemResults.iterator();
        if(iter.hasNext()) {
            itemName = iter.next().getItemName();
        }
        
        return itemName;
    }
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, MainActionForm actionForm, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey;
        String itemName = null;

        if(wasPost(request)) {
            var commandForm = SearchUtil.getHome().getSearchItemsForm();

            commandForm.setSearchTypeName(SearchTypes.ITEM_MAINTENANCE.name());
            commandForm.setItemNameOrAlias(actionForm.getItemNameOrAlias());
            commandForm.setDescription(actionForm.getDescription());
            commandForm.setItemTypeName(actionForm.getItemTypeChoice());
            commandForm.setItemUseTypeName(actionForm.getItemUseTypeChoice());
            commandForm.setItemStatusChoice(actionForm.getItemStatusChoice());
            commandForm.setCreatedSince(actionForm.getCreatedSince());
            commandForm.setModifiedSince(actionForm.getModifiedSince());
            commandForm.setSearchDefaultOperatorName(actionForm.getSearchDefaultOperatorChoice());
            commandForm.setSearchSortOrderName(actionForm.getSearchSortOrderChoice());
            commandForm.setSearchSortDirectionName(actionForm.getSearchSortDirectionChoice());
            commandForm.setRememberPreferences(actionForm.getRememberPreferences().toString());
            commandForm.setSearchUseTypeName(SearchUseTypes.INITIAL.name());

            var commandResult = SearchUtil.getHome().searchItems(getUserVisitPK(request), commandForm);

            if(commandResult.hasErrors()) {
                setCommandResultAttribute(request, commandResult);
                forwardKey = ForwardConstants.FORM;
            } else {
                var executionResult = commandResult.getExecutionResult();
                var result = (SearchItemsResult)executionResult.getResult();
                var count = result.getCount();

                if(count == 0 || count > 1) {
                    forwardKey = ForwardConstants.DISPLAY;
                } else {
                    itemName = getItemName(request);
                    forwardKey = ForwardConstants.REVIEW;
                }
            }
        } else {
            forwardKey = ForwardConstants.FORM;
        }

        var customActionForward = new CustomActionForward(mapping.findForward(forwardKey));
        if(forwardKey.equals(ForwardConstants.REVIEW)) {
            Map<String, String> parameters = new HashMap<>(1);
            
            parameters.put(ParameterConstants.ITEM_NAME, itemName);
            customActionForward.setParameters(parameters);
        }
        
        return customActionForward;
    }
    
}