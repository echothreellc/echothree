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
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.MainBaseDeleteAction;
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
    path = "/Configuration/SearchType/Delete",
    mappingClass = SecureActionMapping.class,
    name = "SearchTypeDelete",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Configuration/SearchType/Main", redirect = true),
        @SproutForward(name = "Form", path = "/configuration/searchtype/delete.jsp")
    }
)
public class DeleteAction
        extends MainBaseDeleteAction<DeleteActionForm> {

    @Override
    public String getEntityTypeName(final DeleteActionForm actionForm) {
        return EntityTypes.SearchType.name();
    }
    
    @Override
    public void setupParameters(DeleteActionForm actionForm, HttpServletRequest request) {
        actionForm.setSearchKindName(findParameter(request, ParameterConstants.SEARCH_KIND_NAME, actionForm.getSearchKindName()));
        actionForm.setSearchTypeName(findParameter(request, ParameterConstants.SEARCH_TYPE_NAME, actionForm.getSearchTypeName()));
    }
    
    @Override
    public void setupTransfer(DeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = SearchUtil.getHome().getGetSearchTypeForm();
        
        commandForm.setSearchKindName(actionForm.getSearchKindName());
        commandForm.setSearchTypeName(actionForm.getSearchTypeName());

        var commandResult = SearchUtil.getHome().getSearchType(getUserVisitPK(request), commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (GetSearchTypeResult)executionResult.getResult();
        
        request.setAttribute(AttributeConstants.SEARCH_TYPE, result.getSearchType());
    }
    
    @Override
    public CommandResult doDelete(DeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = SearchUtil.getHome().getDeleteSearchTypeForm();

        commandForm.setSearchKindName(actionForm.getSearchKindName());
        commandForm.setSearchTypeName(actionForm.getSearchTypeName());

        return SearchUtil.getHome().deleteSearchType(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(DeleteActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.SEARCH_KIND_NAME, actionForm.getSearchKindName());
    }
    
}
