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
import com.echothree.control.user.search.common.edit.SearchKindEdit;
import com.echothree.control.user.search.common.form.EditSearchKindForm;
import com.echothree.control.user.search.common.result.EditSearchKindResult;
import com.echothree.control.user.search.common.spec.SearchKindSpec;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.MainBaseEditAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.CommandResult;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

@SproutAction(
    path = "/Configuration/SearchKind/Edit",
    mappingClass = SecureActionMapping.class,
    name = "SearchKindEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Configuration/SearchKind/Main", redirect = true),
        @SproutForward(name = "Form", path = "/configuration/searchkind/edit.jsp")
    }
)
public class EditAction
        extends MainBaseEditAction<EditActionForm, SearchKindSpec, SearchKindEdit, EditSearchKindForm, EditSearchKindResult> {
    
    @Override
    protected SearchKindSpec getSpec(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var spec = SearchUtil.getHome().getSearchKindSpec();
        
        spec.setSearchKindName(findParameter(request, ParameterConstants.ORIGINAL_SEARCH_KIND_NAME, actionForm.getOriginalSearchKindName()));
        
        return spec;
    }
    
    @Override
    protected SearchKindEdit getEdit(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var edit = SearchUtil.getHome().getSearchKindEdit();

        edit.setSearchKindName(actionForm.getSearchKindName());
        edit.setIsDefault(actionForm.getIsDefault().toString());
        edit.setSortOrder(actionForm.getSortOrder());
        edit.setDescription(actionForm.getDescription());

        return edit;
    }
    
    @Override
    protected EditSearchKindForm getForm()
            throws NamingException {
        return SearchUtil.getHome().getEditSearchKindForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, EditActionForm actionForm, EditSearchKindResult result, SearchKindSpec spec, SearchKindEdit edit) {
        actionForm.setOriginalSearchKindName(spec.getSearchKindName());
        actionForm.setSearchKindName(edit.getSearchKindName());
        actionForm.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        actionForm.setSortOrder(edit.getSortOrder());
        actionForm.setDescription(edit.getDescription());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditSearchKindForm commandForm)
            throws Exception {
        return SearchUtil.getHome().editSearchKind(getUserVisitPK(request), commandForm);
    }
    
    @Override
    protected void setupTransferForForm(HttpServletRequest request, EditActionForm actionForm, EditSearchKindResult result) {
        request.setAttribute(AttributeConstants.SEARCH_KIND, result.getSearchKind());
    }

}
