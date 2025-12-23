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

package com.echothree.ui.web.main.action.content.contentcollection;

import com.echothree.control.user.content.common.ContentUtil;
import com.echothree.control.user.content.common.edit.ContentCollectionEdit;
import com.echothree.control.user.content.common.form.EditContentCollectionForm;
import com.echothree.control.user.content.common.result.EditContentCollectionResult;
import com.echothree.control.user.content.common.spec.ContentCollectionSpec;
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
    path = "/Content/ContentCollection/Edit",
    mappingClass = SecureActionMapping.class,
    name = "ContentCollectionEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Content/ContentCollection/Main", redirect = true),
        @SproutForward(name = "Form", path = "/content/contentcollection/edit.jsp")
    }
)
public class EditAction
        extends MainBaseEditAction<EditActionForm, ContentCollectionSpec, ContentCollectionEdit, EditContentCollectionForm, EditContentCollectionResult> {
    
    @Override
    protected ContentCollectionSpec getSpec(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var spec = ContentUtil.getHome().getContentCollectionSpec();
        var originalContentCollectionName = request.getParameter(ParameterConstants.ORIGINAL_CONTENT_COLLECTION_NAME);

        if(originalContentCollectionName == null) {
            originalContentCollectionName = actionForm.getOriginalContentCollectionName();
        }

        spec.setContentCollectionName(originalContentCollectionName);
        
        return spec;
    }
    
    @Override
    protected ContentCollectionEdit getEdit(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var edit = ContentUtil.getHome().getContentCollectionEdit();

        edit.setContentCollectionName(actionForm.getContentCollectionName());
        edit.setDefaultSourceName(actionForm.getDefaultSourceChoice());
        edit.setDescription(actionForm.getDescription());

        return edit;
    }
    
    @Override
    protected EditContentCollectionForm getForm()
            throws NamingException {
        return ContentUtil.getHome().getEditContentCollectionForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, EditActionForm actionForm, EditContentCollectionResult result, ContentCollectionSpec spec, ContentCollectionEdit edit) {
        actionForm.setOriginalContentCollectionName(spec.getContentCollectionName());
        actionForm.setContentCollectionName(edit.getContentCollectionName());
        actionForm.setDefaultSourceChoice(edit.getDefaultSourceName());
        actionForm.setDescription(edit.getDescription());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditContentCollectionForm commandForm)
            throws Exception {
        return ContentUtil.getHome().editContentCollection(getUserVisitPK(request), commandForm);
    }
    
}