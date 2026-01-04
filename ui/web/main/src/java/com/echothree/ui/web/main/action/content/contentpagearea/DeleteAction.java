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

package com.echothree.ui.web.main.action.content.contentpagearea;

import com.echothree.control.user.content.common.ContentUtil;
import com.echothree.control.user.content.common.result.GetContentPageAreaResult;
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
    path = "/Content/ContentPageArea/Delete",
    mappingClass = SecureActionMapping.class,
    name = "ContentPageAreaDelete",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Content/ContentPageArea/Main", redirect = true),
        @SproutForward(name = "Form", path = "/content/contentpagearea/delete.jsp")
    }
)
public class DeleteAction
        extends MainBaseDeleteAction<DeleteActionForm> {
    
    @Override
    public String getEntityTypeName() {
        return EntityTypes.ContentPageArea.name();
    }

    @Override
    public void setupParameters(DeleteActionForm actionForm, HttpServletRequest request) {
        actionForm.setContentCollectionName(findParameter(request, ParameterConstants.CONTENT_COLLECTION_NAME, actionForm.getContentCollectionName()));
        actionForm.setParentContentSectionName(findParameter(request, ParameterConstants.PARENT_CONTENT_SECTION_NAME, actionForm.getParentContentSectionName()));
        actionForm.setContentSectionName(findParameter(request, ParameterConstants.CONTENT_SECTION_NAME, actionForm.getContentSectionName()));
        actionForm.setContentPageName(findParameter(request, ParameterConstants.CONTENT_PAGE_NAME, actionForm.getContentPageName()));
        actionForm.setSortOrder(findParameter(request, ParameterConstants.SORT_ORDER, actionForm.getSortOrder()));
        actionForm.setLanguageIsoName(findParameter(request, ParameterConstants.LANGUAGE_ISO_NAME, actionForm.getLanguageIsoName()));
    }

    @Override
    public void setupTransfer(DeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = ContentUtil.getHome().getGetContentPageAreaForm();

        commandForm.setContentCollectionName(actionForm.getContentCollectionName());
        commandForm.setContentSectionName(actionForm.getContentSectionName());
        commandForm.setContentPageName(actionForm.getContentPageName());
        commandForm.setSortOrder(actionForm.getSortOrder());
        commandForm.setLanguageIsoName(actionForm.getLanguageIsoName());

        var commandResult = ContentUtil.getHome().getContentPageArea(getUserVisitPK(request), commandForm);
        if(!commandResult.hasErrors()) {
            var executionResult = commandResult.getExecutionResult();
            var result = (GetContentPageAreaResult)executionResult.getResult();

            request.setAttribute(AttributeConstants.CONTENT_PAGE_AREA, result.getContentPageArea());
        }
    }

    @Override
    public CommandResult doDelete(DeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = ContentUtil.getHome().getDeleteContentPageAreaForm();

        commandForm.setContentCollectionName(actionForm.getContentCollectionName());
        commandForm.setContentSectionName(actionForm.getContentSectionName());
        commandForm.setContentPageName(actionForm.getContentPageName());
        commandForm.setSortOrder(actionForm.getSortOrder());
        commandForm.setLanguageIsoName(actionForm.getLanguageIsoName());

        return ContentUtil.getHome().deleteContentPageArea(getUserVisitPK(request), commandForm);
    }

    @Override
    public void setupForwardParameters(DeleteActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.CONTENT_COLLECTION_NAME, actionForm.getContentCollectionName());
        parameters.put(ParameterConstants.PARENT_CONTENT_SECTION_NAME, actionForm.getParentContentSectionName());
        parameters.put(ParameterConstants.CONTENT_SECTION_NAME, actionForm.getContentSectionName());
        parameters.put(ParameterConstants.CONTENT_PAGE_NAME, actionForm.getContentPageName());
        parameters.put(ParameterConstants.SORT_ORDER, actionForm.getSortOrder());
    }
    
}
