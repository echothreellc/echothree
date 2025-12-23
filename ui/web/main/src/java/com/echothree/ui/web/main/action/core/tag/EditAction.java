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

package com.echothree.ui.web.main.action.core.tag;

import com.echothree.control.user.tag.common.TagUtil;
import com.echothree.control.user.tag.common.edit.TagEdit;
import com.echothree.control.user.tag.common.form.EditTagForm;
import com.echothree.control.user.tag.common.result.EditTagResult;
import com.echothree.control.user.tag.common.spec.TagSpec;
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
    path = "/Core/Tag/Edit",
    mappingClass = SecureActionMapping.class,
    name = "TagEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Core/Tag/Main", redirect = true),
        @SproutForward(name = "Form", path = "/core/tag/edit.jsp")
    }
)
public class EditAction
        extends MainBaseEditAction<EditActionForm, TagSpec, TagEdit, EditTagForm, EditTagResult> {
    
    @Override
    protected TagSpec getSpec(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var spec = TagUtil.getHome().getTagSpec();
        
        spec.setTagScopeName(findParameter(request, ParameterConstants.TAG_SCOPE_NAME, actionForm.getTagScopeName()));
        spec.setTagName(findParameter(request, ParameterConstants.ORIGINAL_TAG_NAME, actionForm.getOriginalTagName()));
        
        return spec;
    }
    
    @Override
    protected TagEdit getEdit(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var edit = TagUtil.getHome().getTagEdit();

        edit.setTagName(actionForm.getTagName());

        return edit;
    }
    
    @Override
    protected EditTagForm getForm()
            throws NamingException {
        return TagUtil.getHome().getEditTagForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, EditActionForm actionForm, EditTagResult result, TagSpec spec, TagEdit edit) {
        actionForm.setTagScopeName(spec.getTagScopeName());
        actionForm.setTagName(spec.getTagName());
        actionForm.setOriginalTagName(spec.getTagName());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditTagForm commandForm)
            throws Exception {
        var commandResult = TagUtil.getHome().editTag(getUserVisitPK(request), commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (EditTagResult)executionResult.getResult();

        var tag = result.getTag();
        if(tag != null) {
            request.setAttribute(AttributeConstants.TAG, tag.getTagScope());
        }
        
        return commandResult;
    }
    
    @Override
    public void setupForwardParameters(EditActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.TAG_SCOPE_NAME, actionForm.getTagScopeName());
    }
    
    @Override
    protected void setupTransferForForm(HttpServletRequest request, EditActionForm actionForm, EditTagResult result) {
        request.setAttribute(AttributeConstants.TAG, result.getTag());
    }

}
