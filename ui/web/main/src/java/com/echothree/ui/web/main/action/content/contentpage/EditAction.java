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

package com.echothree.ui.web.main.action.content.contentpage;

import com.echothree.control.user.content.common.ContentUtil;
import com.echothree.control.user.content.common.edit.ContentPageEdit;
import com.echothree.control.user.content.common.form.EditContentPageForm;
import com.echothree.control.user.content.common.result.EditContentPageResult;
import com.echothree.control.user.content.common.spec.ContentPageSpec;
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
    path = "/Content/ContentPage/Edit",
    mappingClass = SecureActionMapping.class,
    name = "ContentPageEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Content/ContentPage/Main", redirect = true),
        @SproutForward(name = "Form", path = "/content/contentpage/edit.jsp")
    }
)
public class EditAction
        extends MainBaseEditAction<EditActionForm, ContentPageSpec, ContentPageEdit, EditContentPageForm, EditContentPageResult> {
    
    @Override
    protected ContentPageSpec getSpec(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var spec = ContentUtil.getHome().getContentPageSpec();
        var contentCollectionName = request.getParameter(ParameterConstants.CONTENT_COLLECTION_NAME);
        var contentSectionName = request.getParameter(ParameterConstants.CONTENT_SECTION_NAME);
        var originalContentPageName = request.getParameter(ParameterConstants.ORIGINAL_CONTENT_PAGE_NAME);

        if(contentCollectionName == null) {
            contentCollectionName = actionForm.getContentCollectionName();
        }
        if(contentSectionName == null) {
            contentSectionName = actionForm.getContentSectionName();
        }
        if(originalContentPageName == null) {
            originalContentPageName = actionForm.getOriginalContentPageName();
        }

        spec.setContentCollectionName(contentCollectionName);
        spec.setContentSectionName(contentSectionName);
        spec.setContentPageName(originalContentPageName);
        
        return spec;
    }
    
    @Override
    protected ContentPageEdit getEdit(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var edit = ContentUtil.getHome().getContentPageEdit();

        edit.setContentPageName(actionForm.getContentPageName());
        edit.setContentPageLayoutName(actionForm.getContentPageLayoutChoice());
        edit.setIsDefault(actionForm.getIsDefault().toString());
        edit.setSortOrder(actionForm.getSortOrder());
        edit.setDescription(actionForm.getDescription());

        return edit;
    }
    
    @Override
    protected EditContentPageForm getForm()
            throws NamingException {
        return ContentUtil.getHome().getEditContentPageForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, EditActionForm actionForm, EditContentPageResult result, ContentPageSpec spec, ContentPageEdit edit) {
        actionForm.setContentCollectionName(spec.getContentCollectionName());
        actionForm.setParentContentSectionName(request.getParameter(ParameterConstants.PARENT_CONTENT_SECTION_NAME));
        actionForm.setContentSectionName(spec.getContentSectionName());
        actionForm.setContentPageName(spec.getContentPageName());
        actionForm.setOriginalContentPageName(spec.getContentPageName());
        actionForm.setContentPageLayoutChoice(edit.getContentPageLayoutName());
        actionForm.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        actionForm.setSortOrder(edit.getSortOrder());
        actionForm.setDescription(edit.getDescription());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditContentPageForm commandForm)
            throws Exception {
        var commandResult = ContentUtil.getHome().editContentPage(getUserVisitPK(request), commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (EditContentPageResult)executionResult.getResult();

        request.setAttribute(AttributeConstants.CONTENT_PAGE, result.getContentPage());
        
        return commandResult;
    }
    
    @Override
    public void setupForwardParameters(EditActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.CONTENT_COLLECTION_NAME, actionForm.getContentCollectionName());
        parameters.put(ParameterConstants.CONTENT_SECTION_NAME, actionForm.getContentSectionName());
        parameters.put(ParameterConstants.PARENT_CONTENT_SECTION_NAME, actionForm.getParentContentSectionName());
    }
    
}