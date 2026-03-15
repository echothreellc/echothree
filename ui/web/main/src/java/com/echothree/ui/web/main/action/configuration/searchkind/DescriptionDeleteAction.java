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

package com.echothree.ui.web.main.action.configuration.searchkind;

import com.echothree.control.user.search.common.SearchUtil;
import com.echothree.control.user.search.common.result.GetSearchKindDescriptionResult;
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
    path = "/Configuration/SearchKind/DescriptionDelete",
    mappingClass = SecureActionMapping.class,
    name = "SearchKindDescriptionDelete",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Configuration/SearchKind/Description", redirect = true),
        @SproutForward(name = "Form", path = "/configuration/searchkind/descriptionDelete.jsp")
    }
)
public class DescriptionDeleteAction
        extends MainBaseDeleteAction<DescriptionDeleteActionForm> {

    @Override
    public String getEntityTypeName(final DescriptionDeleteActionForm actionForm) {
        return EntityTypes.SearchKindDescription.name();
    }
    
    @Override
    public void setupParameters(DescriptionDeleteActionForm actionForm, HttpServletRequest request) {
        actionForm.setSearchKindName(findParameter(request, ParameterConstants.SEARCH_KIND_NAME, actionForm.getSearchKindName()));
        actionForm.setLanguageIsoName(findParameter(request, ParameterConstants.LANGUAGE_ISO_NAME, actionForm.getLanguageIsoName()));
    }
    
    @Override
    public void setupTransfer(DescriptionDeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = SearchUtil.getHome().getGetSearchKindDescriptionForm();
        
        commandForm.setSearchKindName(actionForm.getSearchKindName());
        commandForm.setLanguageIsoName(actionForm.getLanguageIsoName());

        var commandResult = SearchUtil.getHome().getSearchKindDescription(getUserVisitPK(request), commandForm);
        if(!commandResult.hasErrors()) {
            var executionResult = commandResult.getExecutionResult();
            var result = (GetSearchKindDescriptionResult)executionResult.getResult();

            request.setAttribute(AttributeConstants.SEARCH_KIND_DESCRIPTION, result.getSearchKindDescription());
        }
    }
    
    @Override
    public CommandResult doDelete(DescriptionDeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = SearchUtil.getHome().getDeleteSearchKindDescriptionForm();

        commandForm.setSearchKindName(actionForm.getSearchKindName());
        commandForm.setLanguageIsoName(actionForm.getLanguageIsoName());

        return SearchUtil.getHome().deleteSearchKindDescription(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(DescriptionDeleteActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.SEARCH_KIND_NAME, actionForm.getSearchKindName());
    }
    
}
