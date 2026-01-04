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

package com.echothree.ui.web.main.action.content.contentcatalog;

import com.echothree.control.user.content.common.ContentUtil;
import com.echothree.control.user.content.common.edit.ContentCatalogEdit;
import com.echothree.control.user.content.common.form.EditContentCatalogForm;
import com.echothree.control.user.content.common.result.EditContentCatalogResult;
import com.echothree.control.user.content.common.spec.ContentCatalogSpec;
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
    path = "/Content/ContentCatalog/Edit",
    mappingClass = SecureActionMapping.class,
    name = "ContentCatalogEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Content/ContentCatalog/Main", redirect = true),
        @SproutForward(name = "Form", path = "/content/contentcatalog/edit.jsp")
    }
)
public class EditAction
        extends MainBaseEditAction<EditActionForm, ContentCatalogSpec, ContentCatalogEdit, EditContentCatalogForm, EditContentCatalogResult> {
    
    @Override
    protected ContentCatalogSpec getSpec(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var spec = ContentUtil.getHome().getContentCatalogSpec();
        var contentCollectioNname = request.getParameter(ParameterConstants.CONTENT_COLLECTION_NAME);
        var originalContentCatalogName = request.getParameter(ParameterConstants.ORIGINAL_CONTENT_CATALOG_NAME);

        if(contentCollectioNname == null) {
            contentCollectioNname = actionForm.getContentCollectionName();
        }
        if(originalContentCatalogName == null) {
            originalContentCatalogName = actionForm.getOriginalContentCatalogName();
        }

        spec.setContentCollectionName(contentCollectioNname);
        spec.setContentCatalogName(originalContentCatalogName);
        
        return spec;
    }
    
    @Override
    protected ContentCatalogEdit getEdit(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException{
        var edit = ContentUtil.getHome().getContentCatalogEdit();

        edit.setContentCatalogName(actionForm.getContentCatalogName());
        edit.setDefaultSourceName(actionForm.getDefaultSourceChoice());
        edit.setIsDefault(actionForm.getIsDefault().toString());
        edit.setSortOrder(actionForm.getSortOrder());
        edit.setDescription(actionForm.getDescription());

        return edit;
    }
    
    @Override
    protected EditContentCatalogForm getForm()
            throws NamingException {
        return ContentUtil.getHome().getEditContentCatalogForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, EditActionForm actionForm, EditContentCatalogResult result, ContentCatalogSpec spec, ContentCatalogEdit edit) {
        actionForm.setContentCollectionName(spec.getContentCollectionName());
        actionForm.setOriginalContentCatalogName(spec.getContentCatalogName());
        actionForm.setContentCatalogName(edit.getContentCatalogName());
        actionForm.setDefaultSourceChoice(edit.getDefaultSourceName());
        actionForm.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        actionForm.setSortOrder(edit.getSortOrder());
        actionForm.setDescription(edit.getDescription());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditContentCatalogForm commandForm)
            throws Exception {
        var commandResult = ContentUtil.getHome().editContentCatalog(getUserVisitPK(request), commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (EditContentCatalogResult)executionResult.getResult();

        request.setAttribute(AttributeConstants.CONTENT_CATALOG, result.getContentCatalog());
        
        return commandResult;
    }
    
    @Override
    public void setupForwardParameters(EditActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.CONTENT_COLLECTION_NAME, actionForm.getContentCollectionName());
    }
    
}