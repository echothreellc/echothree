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

package com.echothree.ui.web.main.action.content.contentsection;

import com.echothree.control.user.content.common.ContentUtil;
import com.echothree.control.user.content.common.edit.ContentSectionEdit;
import com.echothree.control.user.content.common.form.EditContentSectionForm;
import com.echothree.control.user.content.common.result.EditContentSectionResult;
import com.echothree.control.user.content.common.spec.ContentSectionSpec;
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
    path = "/Content/ContentSection/Edit",
    mappingClass = SecureActionMapping.class,
    name = "ContentSectionEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Content/ContentSection/Main", redirect = true),
        @SproutForward(name = "Form", path = "/content/contentsection/edit.jsp")
    }
)
public class EditAction
        extends MainBaseEditAction<EditActionForm, ContentSectionSpec, ContentSectionEdit, EditContentSectionForm, EditContentSectionResult> {
    
    @Override
    protected ContentSectionSpec getSpec(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var spec = ContentUtil.getHome().getContentSectionSpec();
        var contentCollectionName = request.getParameter(ParameterConstants.CONTENT_COLLECTION_NAME);
        var originalContentSectionName = request.getParameter(ParameterConstants.ORIGINAL_CONTENT_SECTION_NAME);

        if(contentCollectionName == null) {
            contentCollectionName = actionForm.getContentCollectionName();
        }
        if(originalContentSectionName == null) {
            originalContentSectionName = actionForm.getOriginalContentSectionName();
        }

        spec.setContentCollectionName(contentCollectionName);
        spec.setContentSectionName(originalContentSectionName);
        
        return spec;
    }
    
    @Override
    protected ContentSectionEdit getEdit(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var edit = ContentUtil.getHome().getContentSectionEdit();

        edit.setContentSectionName(actionForm.getContentSectionName());
        edit.setParentContentSectionName(actionForm.getParentContentSectionChoice());
        edit.setIsDefault(actionForm.getIsDefault().toString());
        edit.setSortOrder(actionForm.getSortOrder());
        edit.setDescription(actionForm.getDescription());

        return edit;
    }
    
    @Override
    protected EditContentSectionForm getForm()
            throws NamingException {
        return ContentUtil.getHome().getEditContentSectionForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, EditActionForm actionForm, EditContentSectionResult result, ContentSectionSpec spec, ContentSectionEdit edit) {
        actionForm.setContentCollectionName(spec.getContentCollectionName());
        actionForm.setParentContentSectionName(request.getParameter(ParameterConstants.PARENT_CONTENT_SECTION_NAME));
        actionForm.setContentSectionName(spec.getContentSectionName());
        actionForm.setOriginalContentSectionName(spec.getContentSectionName());
        actionForm.setParentContentSectionChoice(edit.getParentContentSectionName());
        actionForm.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        actionForm.setSortOrder(edit.getSortOrder());
        actionForm.setDescription(edit.getDescription());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditContentSectionForm commandForm)
            throws Exception {
        var commandResult = ContentUtil.getHome().editContentSection(getUserVisitPK(request), commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (EditContentSectionResult)executionResult.getResult();

        request.setAttribute(AttributeConstants.CONTENT_SECTION, result.getContentSection());
        
        return commandResult;
    }
    
    @Override
    public void setupForwardParameters(EditActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.CONTENT_COLLECTION_NAME, actionForm.getContentCollectionName());
        parameters.put(ParameterConstants.PARENT_CONTENT_SECTION_NAME, actionForm.getParentContentSectionName());
    }
    
}