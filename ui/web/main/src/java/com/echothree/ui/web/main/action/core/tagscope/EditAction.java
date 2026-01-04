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

package com.echothree.ui.web.main.action.core.tagscope;

import com.echothree.control.user.tag.common.TagUtil;
import com.echothree.control.user.tag.common.edit.TagScopeEdit;
import com.echothree.control.user.tag.common.form.EditTagScopeForm;
import com.echothree.control.user.tag.common.result.EditTagScopeResult;
import com.echothree.control.user.tag.common.spec.TagScopeSpec;
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
    path = "/Core/TagScope/Edit",
    mappingClass = SecureActionMapping.class,
    name = "TagScopeEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Core/TagScope/Main", redirect = true),
        @SproutForward(name = "Form", path = "/core/tagscope/edit.jsp")
    }
)
public class EditAction
        extends MainBaseEditAction<EditActionForm, TagScopeSpec, TagScopeEdit, EditTagScopeForm, EditTagScopeResult> {
    
    @Override
    protected TagScopeSpec getSpec(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var spec = TagUtil.getHome().getTagScopeSpec();
        var originalTagScopeName = request.getParameter(ParameterConstants.ORIGINAL_TAG_SCOPE_NAME);

        if(originalTagScopeName == null) {
            originalTagScopeName = actionForm.getOriginalTagScopeName();
        }

        spec.setTagScopeName(originalTagScopeName);
        
        return spec;
    }
    
    @Override
    protected TagScopeEdit getEdit(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var edit = TagUtil.getHome().getTagScopeEdit();

        edit.setTagScopeName(actionForm.getTagScopeName());
        edit.setIsDefault(actionForm.getIsDefault().toString());
        edit.setSortOrder(actionForm.getSortOrder());
        edit.setDescription(actionForm.getDescription());

        return edit;
    }
    
    @Override
    protected EditTagScopeForm getForm()
            throws NamingException {
        return TagUtil.getHome().getEditTagScopeForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, EditActionForm actionForm, EditTagScopeResult result, TagScopeSpec spec, TagScopeEdit edit) {
        actionForm.setOriginalTagScopeName(spec.getTagScopeName());
        actionForm.setTagScopeName(edit.getTagScopeName());
        actionForm.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        actionForm.setSortOrder(edit.getSortOrder());
        actionForm.setDescription(edit.getDescription());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditTagScopeForm commandForm)
            throws Exception {
        return TagUtil.getHome().editTagScope(getUserVisitPK(request), commandForm);
    }
    
}
