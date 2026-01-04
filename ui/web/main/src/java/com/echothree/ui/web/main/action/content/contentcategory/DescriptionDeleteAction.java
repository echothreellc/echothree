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

package com.echothree.ui.web.main.action.content.contentcategory;

import com.echothree.control.user.content.common.ContentUtil;
import com.echothree.control.user.content.common.result.GetContentCategoryDescriptionResult;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.MainBaseDeleteAction;
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
    path = "/Content/ContentCategory/DescriptionDelete",
    mappingClass = SecureActionMapping.class,
    name = "ContentCategoryDescriptionDelete",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Content/ContentCategory/Description", redirect = true),
        @SproutForward(name = "Form", path = "/content/contentcategory/descriptionDelete.jsp")
    }
)
public class DescriptionDeleteAction
        extends MainBaseDeleteAction<DescriptionDeleteActionForm> {
    
    @Override
    public String getEntityTypeName() {
        return EntityTypes.ContentCategoryDescription.name();
    }

    @Override
    public void setupParameters(DescriptionDeleteActionForm actionForm, HttpServletRequest request) {
        actionForm.setContentCollectionName(findParameter(request, ParameterConstants.CONTENT_COLLECTION_NAME, actionForm.getContentCollectionName()));
        actionForm.setContentCatalogName(findParameter(request, ParameterConstants.CONTENT_CATALOG_NAME, actionForm.getContentCatalogName()));
        actionForm.setContentCategoryName(findParameter(request, ParameterConstants.CONTENT_CATEGORY_NAME, actionForm.getContentCategoryName()));
        actionForm.setLanguageIsoName(findParameter(request, ParameterConstants.LANGUAGE_ISO_NAME, actionForm.getLanguageIsoName()));
        actionForm.setParentContentCategoryName(findParameter(request, ParameterConstants.PARENT_CONTENT_CATEGORY_NAME, actionForm.getParentContentCategoryName()));
    }

    @Override
    public void setupTransfer(DescriptionDeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = ContentUtil.getHome().getGetContentCategoryDescriptionForm();

        commandForm.setContentCollectionName(actionForm.getContentCollectionName());
        commandForm.setContentCatalogName(actionForm.getContentCatalogName());
        commandForm.setContentCategoryName(actionForm.getContentCategoryName());
        commandForm.setLanguageIsoName(actionForm.getLanguageIsoName());

        var commandResult = ContentUtil.getHome().getContentCategoryDescription(getUserVisitPK(request), commandForm);
        if(!commandResult.hasErrors()) {
            var executionResult = commandResult.getExecutionResult();
            var result = (GetContentCategoryDescriptionResult)executionResult.getResult();

            request.setAttribute(AttributeConstants.CONTENT_CATEGORY_DESCRIPTION, result.getContentCategoryDescription());
        }
    }

    @Override
    public CommandResult doDelete(DescriptionDeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = ContentUtil.getHome().getDeleteContentCategoryDescriptionForm();

        commandForm.setContentCollectionName(actionForm.getContentCollectionName());
        commandForm.setContentCatalogName(actionForm.getContentCatalogName());
        commandForm.setContentCategoryName(actionForm.getContentCategoryName());
        commandForm.setLanguageIsoName(actionForm.getLanguageIsoName());

        return ContentUtil.getHome().deleteContentCategoryDescription(getUserVisitPK(request), commandForm);
    }

    @Override
    public void setupForwardParameters(DescriptionDeleteActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.CONTENT_COLLECTION_NAME, actionForm.getContentCollectionName());
        parameters.put(ParameterConstants.CONTENT_CATALOG_NAME, actionForm.getContentCatalogName());
        parameters.put(ParameterConstants.CONTENT_CATEGORY_NAME, actionForm.getContentCategoryName());
        parameters.put(ParameterConstants.PARENT_CONTENT_CATEGORY_NAME, actionForm.getParentContentCategoryName());
    }
    
}
