// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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
import com.echothree.control.user.content.common.result.GetContentSectionsResult;
import com.echothree.ui.web.main.framework.AttributeConstants;
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
    path = "/Content/ContentSection/Main",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/content/contentsection/main.jsp")
    }
)
public class MainAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
        String forwardKey;
        
        try {
            var commandForm = ContentUtil.getHome().getGetContentSectionsForm();
            var contentCollectionName = request.getParameter(ParameterConstants.CONTENT_COLLECTION_NAME);
            var parentContentSectionName = request.getParameter(ParameterConstants.PARENT_CONTENT_SECTION_NAME);
            
            commandForm.setContentCollectionName(contentCollectionName);
            commandForm.setParentContentSectionName(parentContentSectionName);

            var commandResult = ContentUtil.getHome().getContentSections(getUserVisitPK(request), commandForm);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetContentSectionsResult)executionResult.getResult();
            var contentCollectionTransfer = result.getContentCollection();
            var parentContentSectionTransfer = result.getParentContentSection();
            
            request.setAttribute(AttributeConstants.CONTENT_COLLECTION, contentCollectionTransfer);
            request.setAttribute(AttributeConstants.CONTENT_COLLECTION_NAME, contentCollectionTransfer.getContentCollectionName());
            request.setAttribute(AttributeConstants.PARENT_CONTENT_SECTION, parentContentSectionTransfer);
            request.setAttribute(AttributeConstants.PARENT_CONTENT_SECTION_NAME, parentContentSectionTransfer.getContentSectionName());
            request.setAttribute(AttributeConstants.CONTENT_SECTIONS, result.getContentSections());
            forwardKey = ForwardConstants.DISPLAY;
        } catch (NamingException ne) {
            forwardKey = ForwardConstants.ERROR_500;
        }
        
        return mapping.findForward(forwardKey);
    }
    
}