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
import com.echothree.control.user.content.common.result.GetContentCategoryDescriptionsResult;
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
    path = "/Content/ContentCategory/Description",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/content/contentcategory/description.jsp")
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
            var contentCatalogName = request.getParameter(ParameterConstants.CONTENT_CATALOG_NAME);
            var contentCategoryName = request.getParameter(ParameterConstants.CONTENT_CATEGORY_NAME);
            var commandForm = ContentUtil.getHome().getGetContentCategoryDescriptionsForm();
            
            commandForm.setContentCollectionName(contentCollectionName);
            commandForm.setContentCatalogName(contentCatalogName);
            commandForm.setContentCategoryName(contentCategoryName);

            var commandResult = ContentUtil.getHome().getContentCategoryDescriptions(getUserVisitPK(request), commandForm);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetContentCategoryDescriptionsResult)executionResult.getResult();
            var contentCollectionTransfer = result.getContentCollection();
            var contentCatalogTransfer = result.getContentCatalog();
            var contentCategoryTransfer = result.getContentCategory();
            var parentContentCategoryTransfer = contentCategoryTransfer.getParentContentCategory();
            
            request.setAttribute("contentCollection", contentCollectionTransfer);
            request.setAttribute("contentCollectionName", contentCollectionTransfer.getContentCollectionName());
            request.setAttribute("contentCatalog", contentCatalogTransfer);
            request.setAttribute("contentCatalogName", contentCatalogTransfer.getContentCatalogName());
            request.setAttribute("contentCategory", contentCategoryTransfer);
            request.setAttribute("contentCategoryName", contentCategoryTransfer.getContentCategoryName());
            request.setAttribute("parentContentCategory", parentContentCategoryTransfer);
            request.setAttribute("parentContentCategoryName", parentContentCategoryTransfer.getContentCategoryName());
            request.setAttribute("contentCategoryDescriptions", result.getContentCategoryDescriptions());
            forwardKey = ForwardConstants.DISPLAY;
        } catch (NamingException ne) {
            forwardKey = ForwardConstants.ERROR_500;
        }
        
        return mapping.findForward(forwardKey);
    }
    
}