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
import com.echothree.control.user.tag.common.edit.TagScopeDescriptionEdit;
import com.echothree.control.user.tag.common.form.EditTagScopeDescriptionForm;
import com.echothree.control.user.tag.common.result.EditTagScopeDescriptionResult;
import com.echothree.control.user.tag.common.result.GetTagScopeResult;
import com.echothree.control.user.tag.common.spec.TagScopeDescriptionSpec;
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
    path = "/Core/TagScope/DescriptionEdit",
    mappingClass = SecureActionMapping.class,
    name = "TagScopeDescriptionEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Core/TagScope/Description", redirect = true),
        @SproutForward(name = "Form", path = "/core/tagscope/descriptionEdit.jsp")
    }
)
public class DescriptionEditAction
        extends MainBaseEditAction<DescriptionEditActionForm, TagScopeDescriptionSpec, TagScopeDescriptionEdit, EditTagScopeDescriptionForm, EditTagScopeDescriptionResult> {
    
    @Override
    protected TagScopeDescriptionSpec getSpec(HttpServletRequest request, DescriptionEditActionForm actionForm)
            throws NamingException {
        var spec = TagUtil.getHome().getTagScopeDescriptionSpec();
        
        spec.setTagScopeName(findParameter(request, ParameterConstants.TAG_SCOPE_NAME, actionForm.getTagScopeName()));
        spec.setLanguageIsoName(findParameter(request, ParameterConstants.LANGUAGE_ISO_NAME, actionForm.getLanguageIsoName()));
        
        return spec;
    }
    
    @Override
    protected TagScopeDescriptionEdit getEdit(HttpServletRequest request, DescriptionEditActionForm actionForm)
            throws NamingException {
        var edit = TagUtil.getHome().getTagScopeDescriptionEdit();

        edit.setDescription(actionForm.getDescription());

        return edit;
    }
    
    @Override
    protected EditTagScopeDescriptionForm getForm()
            throws NamingException {
        return TagUtil.getHome().getEditTagScopeDescriptionForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, DescriptionEditActionForm actionForm, EditTagScopeDescriptionResult result, TagScopeDescriptionSpec spec, TagScopeDescriptionEdit edit) {
        actionForm.setTagScopeName(spec.getTagScopeName());
        actionForm.setLanguageIsoName(spec.getLanguageIsoName());
        actionForm.setDescription(edit.getDescription());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditTagScopeDescriptionForm commandForm)
            throws Exception {
        var commandResult = TagUtil.getHome().editTagScopeDescription(getUserVisitPK(request), commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (EditTagScopeDescriptionResult)executionResult.getResult();

        var tagScopeDescription = result.getTagScopeDescription();
        if(tagScopeDescription != null) {
            request.setAttribute(AttributeConstants.TAG_SCOPE, tagScopeDescription.getTagScope());
        }
        
        return commandResult;
    }
    
    @Override
    public void setupForwardParameters(DescriptionEditActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.TAG_SCOPE_NAME, actionForm.getTagScopeName());
    }
    
    @Override
    public void setupTransfer(DescriptionEditActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = TagUtil.getHome().getGetTagScopeForm();

        commandForm.setTagScopeName(actionForm.getTagScopeName());

        var commandResult = TagUtil.getHome().getTagScope(getUserVisitPK(request), commandForm);
        if(!commandResult.hasErrors()) {
            var executionResult = commandResult.getExecutionResult();
            var result = (GetTagScopeResult)executionResult.getResult();
            
            request.setAttribute(AttributeConstants.TAG_SCOPE, result.getTagScope());
        }
    }
    
    @Override
    protected void setupTransferForForm(HttpServletRequest request, DescriptionEditActionForm actionForm, EditTagScopeDescriptionResult result) {
        request.setAttribute(AttributeConstants.TAG_SCOPE_DESCRIPTION, result.getTagScopeDescription());
    }

}
