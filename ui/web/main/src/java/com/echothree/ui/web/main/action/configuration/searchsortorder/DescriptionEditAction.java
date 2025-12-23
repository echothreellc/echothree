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

package com.echothree.ui.web.main.action.configuration.searchsortorder;

import com.echothree.control.user.search.common.SearchUtil;
import com.echothree.control.user.search.common.edit.SearchSortOrderDescriptionEdit;
import com.echothree.control.user.search.common.form.EditSearchSortOrderDescriptionForm;
import com.echothree.control.user.search.common.result.EditSearchSortOrderDescriptionResult;
import com.echothree.control.user.search.common.spec.SearchSortOrderDescriptionSpec;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.MainBaseEditAction;
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
    path = "/Configuration/SearchSortOrder/DescriptionEdit",
    mappingClass = SecureActionMapping.class,
    name = "SearchSortOrderDescriptionEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Configuration/SearchSortOrder/Description", redirect = true),
        @SproutForward(name = "Form", path = "/configuration/searchsortorder/descriptionEdit.jsp")
    }
)
public class DescriptionEditAction
        extends MainBaseEditAction<DescriptionEditActionForm, SearchSortOrderDescriptionSpec, SearchSortOrderDescriptionEdit, EditSearchSortOrderDescriptionForm, EditSearchSortOrderDescriptionResult> {
    
    @Override
    protected SearchSortOrderDescriptionSpec getSpec(HttpServletRequest request, DescriptionEditActionForm actionForm)
            throws NamingException {
        var spec = SearchUtil.getHome().getSearchSortOrderDescriptionSpec();
        
        spec.setSearchKindName(findParameter(request, ParameterConstants.SEARCH_KIND_NAME, actionForm.getSearchKindName()));
        spec.setSearchSortOrderName(findParameter(request, ParameterConstants.SEARCH_SORT_ORDER_NAME, actionForm.getSearchSortOrderName()));
        spec.setLanguageIsoName(findParameter(request, ParameterConstants.LANGUAGE_ISO_NAME, actionForm.getLanguageIsoName()));
        
        return spec;
    }
    
    @Override
    protected SearchSortOrderDescriptionEdit getEdit(HttpServletRequest request, DescriptionEditActionForm actionForm)
            throws NamingException {
        var edit = SearchUtil.getHome().getSearchSortOrderDescriptionEdit();

        edit.setDescription(actionForm.getDescription());

        return edit;
    }
    
    @Override
    protected EditSearchSortOrderDescriptionForm getForm()
            throws NamingException {
        return SearchUtil.getHome().getEditSearchSortOrderDescriptionForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, DescriptionEditActionForm actionForm, EditSearchSortOrderDescriptionResult result, SearchSortOrderDescriptionSpec spec, SearchSortOrderDescriptionEdit edit) {
        actionForm.setSearchKindName(spec.getSearchKindName());
        actionForm.setSearchSortOrderName(spec.getSearchSortOrderName());
        actionForm.setLanguageIsoName(spec.getLanguageIsoName());
        actionForm.setDescription(edit.getDescription());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditSearchSortOrderDescriptionForm commandForm)
            throws Exception {
        return SearchUtil.getHome().editSearchSortOrderDescription(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(DescriptionEditActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.SEARCH_KIND_NAME, actionForm.getSearchKindName());
        parameters.put(ParameterConstants.SEARCH_SORT_ORDER_NAME, actionForm.getSearchSortOrderName());
    }
    
    @Override
    protected void setupTransferForForm(HttpServletRequest request, DescriptionEditActionForm actionForm, EditSearchSortOrderDescriptionResult result) {
        request.setAttribute(AttributeConstants.SEARCH_SORT_ORDER_DESCRIPTION, result.getSearchSortOrderDescription());
    }

}
