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
import com.echothree.control.user.search.common.edit.SearchTypeEdit;
import com.echothree.control.user.search.common.form.EditSearchTypeForm;
import com.echothree.control.user.search.common.result.EditSearchTypeResult;
import com.echothree.control.user.search.common.spec.SearchTypeSpec;
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
    path = "/Configuration/SearchType/Edit",
    mappingClass = SecureActionMapping.class,
    name = "SearchTypeEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Configuration/SearchType/Main", redirect = true),
        @SproutForward(name = "Form", path = "/configuration/searchtype/edit.jsp")
    }
)
public class EditAction
        extends MainBaseEditAction<EditActionForm, SearchTypeSpec, SearchTypeEdit, EditSearchTypeForm, EditSearchTypeResult> {
    
    @Override
    protected SearchTypeSpec getSpec(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var spec = SearchUtil.getHome().getSearchTypeSpec();
        
        spec.setSearchKindName(findParameter(request, ParameterConstants.SEARCH_KIND_NAME, actionForm.getSearchKindName()));
        spec.setSearchTypeName(findParameter(request, ParameterConstants.ORIGINAL_SEARCH_TYPE_NAME, actionForm.getOriginalSearchTypeName()));
        
        return spec;
    }
    
    @Override
    protected SearchTypeEdit getEdit(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var edit = SearchUtil.getHome().getSearchTypeEdit();

        edit.setSearchTypeName(actionForm.getSearchTypeName());
        edit.setIsDefault(actionForm.getIsDefault().toString());
        edit.setSortOrder(actionForm.getSortOrder());
        edit.setDescription(actionForm.getDescription());

        return edit;
    }
    
    @Override
    protected EditSearchTypeForm getForm()
            throws NamingException {
        return SearchUtil.getHome().getEditSearchTypeForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, EditActionForm actionForm, EditSearchTypeResult result, SearchTypeSpec spec, SearchTypeEdit edit) {
        actionForm.setSearchKindName(spec.getSearchKindName());
        actionForm.setOriginalSearchTypeName(spec.getSearchTypeName());
        actionForm.setSearchTypeName(edit.getSearchTypeName());
        actionForm.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        actionForm.setSortOrder(edit.getSortOrder());
        actionForm.setDescription(edit.getDescription());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditSearchTypeForm commandForm)
            throws Exception {
        return SearchUtil.getHome().editSearchType(getUserVisitPK(request), commandForm);
    }
    
    @Override
    protected void setupTransferForForm(HttpServletRequest request, EditActionForm actionForm, EditSearchTypeResult result) {
        request.setAttribute(AttributeConstants.SEARCH_TYPE, result.getSearchType());
    }

    @Override
    public void setupForwardParameters(EditActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.SEARCH_KIND_NAME, actionForm.getSearchKindName());
    }
    
}
