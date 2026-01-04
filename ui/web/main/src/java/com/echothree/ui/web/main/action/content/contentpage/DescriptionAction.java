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
import com.echothree.control.user.content.common.result.GetContentPageDescriptionsResult;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/Content/ContentPage/Description",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/content/contentpage/description.jsp")
    }
)
public class DescriptionAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
        String forwardKey;
        
        try {
            var contentCollectionName = request.getParameter(ParameterConstants.CONTENT_COLLECTION_NAME);
            var contentSectionName = request.getParameter(ParameterConstants.CONTENT_SECTION_NAME);
            var contentPageName = request.getParameter(ParameterConstants.CONTENT_PAGE_NAME);
            var getContentPageDescriptionsForm = ContentUtil.getHome().getGetContentPageDescriptionsForm();
            
            getContentPageDescriptionsForm.setContentCollectionName(contentCollectionName);
            getContentPageDescriptionsForm.setContentSectionName(contentSectionName);
            getContentPageDescriptionsForm.setContentPageName(contentPageName);

            var commandResult = ContentUtil.getHome().getContentPageDescriptions(getUserVisitPK(request), getContentPageDescriptionsForm);
            var executionResult = commandResult.getExecutionResult();
            var getContentPageDescriptionsResult = (GetContentPageDescriptionsResult)executionResult.getResult();
            var contentCollectionTransfer = getContentPageDescriptionsResult.getContentCollection();
            var contentSectionTransfer = getContentPageDescriptionsResult.getContentSection();
            var contentPageTransfer = getContentPageDescriptionsResult.getContentPage();
            var parentContentSectionTransfer = contentSectionTransfer.getParentContentSection();
            
            request.setAttribute("contentCollection", contentCollectionTransfer);
            request.setAttribute("contentCollectionName", contentCollectionTransfer.getContentCollectionName());
            request.setAttribute("contentSection", contentSectionTransfer);
            request.setAttribute("contentSectionName", contentSectionTransfer.getContentSectionName());
            request.setAttribute("contentPage", contentPageTransfer);
            request.setAttribute("contentPageName", contentPageTransfer.getContentPageName());
            request.setAttribute("parentContentSection", parentContentSectionTransfer);
            request.setAttribute("parentContentSectionName", parentContentSectionTransfer.getContentSectionName());
            request.setAttribute("contentPageDescriptions", getContentPageDescriptionsResult.getContentPageDescriptions());
            forwardKey = ForwardConstants.DISPLAY;
        } catch (NamingException ne) {
            forwardKey = ForwardConstants.ERROR_500;
        }
        
        return mapping.findForward(forwardKey);
    }
    
}