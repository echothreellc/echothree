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
import com.echothree.control.user.search.common.edit.SearchTypeDescriptionEdit;
import com.echothree.control.user.search.common.form.EditSearchTypeDescriptionForm;
import com.echothree.control.user.search.common.result.EditSearchTypeDescriptionResult;
import com.echothree.control.user.search.common.spec.SearchTypeDescriptionSpec;
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
    path = "/Configuration/SearchType/DescriptionEdit",
    mappingClass = SecureActionMapping.class,
    name = "SearchTypeDescriptionEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Configuration/SearchType/Description", redirect = true),
        @SproutForward(name = "Form", path = "/configuration/searchtype/descriptionEdit.jsp")
    }
)
public class DescriptionEditAction
        extends MainBaseEditAction<DescriptionEditActionForm, SearchTypeDescriptionSpec, SearchTypeDescriptionEdit, EditSearchTypeDescriptionForm, EditSearchTypeDescriptionResult> {
    
    @Override
    protected SearchTypeDescriptionSpec getSpec(HttpServletRequest request, DescriptionEditActionForm actionForm)
            throws NamingException {
        var spec = SearchUtil.getHome().getSearchTypeDescriptionSpec();
        
        spec.setSearchKindName(findParameter(request, ParameterConstants.SEARCH_KIND_NAME, actionForm.getSearchKindName()));
        spec.setSearchTypeName(findParameter(request, ParameterConstants.SEARCH_TYPE_NAME, actionForm.getSearchTypeName()));
        spec.setLanguageIsoName(findParameter(request, ParameterConstants.LANGUAGE_ISO_NAME, actionForm.getLanguageIsoName()));
        
        return spec;
    }
    
    @Override
    protected SearchTypeDescriptionEdit getEdit(HttpServletRequest request, DescriptionEditActionForm actionForm)
            throws NamingException {
        var edit = SearchUtil.getHome().getSearchTypeDescriptionEdit();

        edit.setDescription(actionForm.getDescription());

        return edit;
    }
    
    @Override
    protected EditSearchTypeDescriptionForm getForm()
            throws NamingException {
        return SearchUtil.getHome().getEditSearchTypeDescriptionForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, DescriptionEditActionForm actionForm, EditSearchTypeDescriptionResult result, SearchTypeDescriptionSpec spec, SearchTypeDescriptionEdit edit) {
        actionForm.setSearchKindName(spec.getSearchKindName());
        actionForm.setSearchTypeName(spec.getSearchTypeName());
        actionForm.setLanguageIsoName(spec.getLanguageIsoName());
        actionForm.setDescription(edit.getDescription());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditSearchTypeDescriptionForm commandForm)
            throws Exception {
        return SearchUtil.getHome().editSearchTypeDescription(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(DescriptionEditActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.SEARCH_KIND_NAME, actionForm.getSearchKindName());
        parameters.put(ParameterConstants.SEARCH_TYPE_NAME, actionForm.getSearchTypeName());
    }
    
    @Override
    protected void setupTransferForForm(HttpServletRequest request, DescriptionEditActionForm actionForm, EditSearchTypeDescriptionResult result) {
        request.setAttribute(AttributeConstants.SEARCH_TYPE_DESCRIPTION, result.getSearchTypeDescription());
    }

}
