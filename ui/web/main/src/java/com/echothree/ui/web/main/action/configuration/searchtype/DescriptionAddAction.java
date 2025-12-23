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

package com.echothree.ui.web.main.action.configuration.searchtype;

import com.echothree.control.user.search.common.SearchUtil;
import com.echothree.control.user.search.common.result.GetSearchTypeResult;
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
    path = "/Configuration/SearchType/DescriptionAdd",
    mappingClass = SecureActionMapping.class,
    name = "SearchTypeDescriptionAdd",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Configuration/SearchType/Description", redirect = true),
        @SproutForward(name = "Form", path = "/configuration/searchtype/descriptionAdd.jsp")
    }
)
public class DescriptionAddAction
        extends MainBaseAddAction<DescriptionAddActionForm> {

    @Override
    public void setupParameters(DescriptionAddActionForm actionForm, HttpServletRequest request) {
        actionForm.setSearchKindName(findParameter(request, ParameterConstants.SEARCH_KIND_NAME, actionForm.getSearchKindName()));
        actionForm.setSearchTypeName(findParameter(request, ParameterConstants.SEARCH_TYPE_NAME, actionForm.getSearchTypeName()));
    }
    
    @Override
    public void setupTransfer(DescriptionAddActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = SearchUtil.getHome().getGetSearchTypeForm();

        commandForm.setSearchKindName(actionForm.getSearchKindName());
        commandForm.setSearchTypeName(actionForm.getSearchTypeName());

        var commandResult = SearchUtil.getHome().getSearchType(getUserVisitPK(request), commandForm);
        if(!commandResult.hasErrors()) {
            var executionResult = commandResult.getExecutionResult();
            var result = (GetSearchTypeResult)executionResult.getResult();
            
            request.setAttribute(AttributeConstants.SEARCH_TYPE, result.getSearchType());
        }
    }
    
    @Override
    public CommandResult doAdd(DescriptionAddActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = SearchUtil.getHome().getCreateSearchTypeDescriptionForm();

        commandForm.setSearchKindName(actionForm.getSearchKindName());
        commandForm.setSearchTypeName(actionForm.getSearchTypeName());
        commandForm.setLanguageIsoName(actionForm.getLanguageChoice());
        commandForm.setDescription(actionForm.getDescription());

        return SearchUtil.getHome().createSearchTypeDescription(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(DescriptionAddActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.SEARCH_KIND_NAME, actionForm.getSearchKindName());
        parameters.put(ParameterConstants.SEARCH_TYPE_NAME, actionForm.getSearchTypeName());
    }
    
}
