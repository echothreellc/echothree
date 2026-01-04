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
import com.echothree.control.user.search.common.edit.SearchSortOrderEdit;
import com.echothree.control.user.search.common.form.EditSearchSortOrderForm;
import com.echothree.control.user.search.common.result.EditSearchSortOrderResult;
import com.echothree.control.user.search.common.spec.SearchSortOrderSpec;
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
    path = "/Configuration/SearchSortOrder/Edit",
    mappingClass = SecureActionMapping.class,
    name = "SearchSortOrderEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Configuration/SearchSortOrder/Main", redirect = true),
        @SproutForward(name = "Form", path = "/configuration/searchsortorder/edit.jsp")
    }
)
public class EditAction
        extends MainBaseEditAction<EditActionForm, SearchSortOrderSpec, SearchSortOrderEdit, EditSearchSortOrderForm, EditSearchSortOrderResult> {
    
    @Override
    protected SearchSortOrderSpec getSpec(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var spec = SearchUtil.getHome().getSearchSortOrderSpec();
        
        spec.setSearchKindName(findParameter(request, ParameterConstants.SEARCH_KIND_NAME, actionForm.getSearchKindName()));
        spec.setSearchSortOrderName(findParameter(request, ParameterConstants.ORIGINAL_SEARCH_SORT_ORDER_NAME, actionForm.getOriginalSearchSortOrderName()));
        
        return spec;
    }
    
    @Override
    protected SearchSortOrderEdit getEdit(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var edit = SearchUtil.getHome().getSearchSortOrderEdit();

        edit.setSearchSortOrderName(actionForm.getSearchSortOrderName());
        edit.setIsDefault(actionForm.getIsDefault().toString());
        edit.setSortOrder(actionForm.getSortOrder());
        edit.setDescription(actionForm.getDescription());

        return edit;
    }
    
    @Override
    protected EditSearchSortOrderForm getForm()
            throws NamingException {
        return SearchUtil.getHome().getEditSearchSortOrderForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, EditActionForm actionForm, EditSearchSortOrderResult result, SearchSortOrderSpec spec, SearchSortOrderEdit edit) {
        actionForm.setSearchKindName(spec.getSearchKindName());
        actionForm.setOriginalSearchSortOrderName(spec.getSearchSortOrderName());
        actionForm.setSearchSortOrderName(edit.getSearchSortOrderName());
        actionForm.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        actionForm.setSortOrder(edit.getSortOrder());
        actionForm.setDescription(edit.getDescription());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditSearchSortOrderForm commandForm)
            throws Exception {
        return SearchUtil.getHome().editSearchSortOrder(getUserVisitPK(request), commandForm);
    }
    
    @Override
    protected void setupTransferForForm(HttpServletRequest request, EditActionForm actionForm, EditSearchSortOrderResult result) {
        request.setAttribute(AttributeConstants.SEARCH_SORT_ORDER, result.getSearchSortOrder());
    }

    @Override
    public void setupForwardParameters(EditActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.SEARCH_KIND_NAME, actionForm.getSearchKindName());
    }
    
}
