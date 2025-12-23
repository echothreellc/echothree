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
import com.echothree.control.user.content.common.result.GetContentPageResult;
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
    path = "/Content/ContentPage/Delete",
    mappingClass = SecureActionMapping.class,
    name = "ContentPageDelete",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Content/ContentPage/Main", redirect = true),
        @SproutForward(name = "Form", path = "/content/contentpage/delete.jsp")
    }
)
public class DeleteAction
        extends MainBaseDeleteAction<DeleteActionForm> {
    
    @Override
    public String getEntityTypeName() {
        return EntityTypes.ContentPage.name();
    }

    @Override
    public void setupParameters(DeleteActionForm actionForm, HttpServletRequest request) {
        actionForm.setContentCollectionName(findParameter(request, ParameterConstants.CONTENT_COLLECTION_NAME, actionForm.getContentCollectionName()));
        actionForm.setParentContentSectionName(findParameter(request, ParameterConstants.PARENT_CONTENT_SECTION_NAME, actionForm.getParentContentSectionName()));
        actionForm.setContentSectionName(findParameter(request, ParameterConstants.CONTENT_SECTION_NAME, actionForm.getContentSectionName()));
        actionForm.setContentPageName(findParameter(request, ParameterConstants.CONTENT_PAGE_NAME, actionForm.getContentPageName()));
    }

    @Override
    public void setupTransfer(DeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = ContentUtil.getHome().getGetContentPageForm();

        commandForm.setContentCollectionName(actionForm.getContentCollectionName());
        commandForm.setContentSectionName(actionForm.getContentSectionName());
        commandForm.setContentPageName(actionForm.getContentPageName());

        var commandResult = ContentUtil.getHome().getContentPage(getUserVisitPK(request), commandForm);
        if(!commandResult.hasErrors()) {
            var executionResult = commandResult.getExecutionResult();
            var result = (GetContentPageResult)executionResult.getResult();

            request.setAttribute(AttributeConstants.CONTENT_PAGE, result.getContentPage());
        }
    }

    @Override
    public CommandResult doDelete(DeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = ContentUtil.getHome().getDeleteContentPageForm();

        commandForm.setContentCollectionName(actionForm.getContentCollectionName());
        commandForm.setContentSectionName(actionForm.getContentSectionName());
        commandForm.setContentPageName(actionForm.getContentPageName());

        return ContentUtil.getHome().deleteContentPage(getUserVisitPK(request), commandForm);
    }

    @Override
    public void setupForwardParameters(DeleteActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.CONTENT_COLLECTION_NAME, actionForm.getContentCollectionName());
        parameters.put(ParameterConstants.PARENT_CONTENT_SECTION_NAME, actionForm.getParentContentSectionName());
        parameters.put(ParameterConstants.CONTENT_SECTION_NAME, actionForm.getContentSectionName());
    }

}
