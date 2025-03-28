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

package com.echothree.ui.web.main.action.content.contentpage;

import com.echothree.control.user.content.common.ContentUtil;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.view.client.web.struts.CustomActionForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import java.util.HashMap;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/Content/ContentPage/Add",
    mappingClass = SecureActionMapping.class,
    name = "ContentPageAdd",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Content/ContentPage/Main", redirect = true),
        @SproutForward(name = "Form", path = "/content/contentpage/add.jsp")
    }
)
public class AddAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
        String forwardKey = null;
        var contentCollectionName = request.getParameter(ParameterConstants.CONTENT_COLLECTION_NAME);
        var contentSectionName = request.getParameter(ParameterConstants.CONTENT_SECTION_NAME);
        var parentContentSectionName = request.getParameter(ParameterConstants.PARENT_CONTENT_SECTION_NAME);
        
        try {
            if(forwardKey == null) {
                var addActionForm = (AddActionForm)form;
                
                if(wasPost(request)) {
                    var createContentPageForm = ContentUtil.getHome().getCreateContentPageForm();
                    
                    if(contentCollectionName == null)
                        contentCollectionName = addActionForm.getContentCollectionName();
                    if(contentSectionName == null)
                        contentSectionName = addActionForm.getContentSectionName();
                    if(parentContentSectionName == null)
                        parentContentSectionName = addActionForm.getParentContentSectionName();
                    
                    createContentPageForm.setContentCollectionName(contentCollectionName);
                    createContentPageForm.setContentSectionName(contentSectionName);
                    createContentPageForm.setContentPageName(addActionForm.getContentPageName());
                    createContentPageForm.setContentPageLayoutName(addActionForm.getContentPageLayoutChoice());
                    createContentPageForm.setIsDefault(addActionForm.getIsDefault().toString());
                    createContentPageForm.setSortOrder(addActionForm.getSortOrder());
                    createContentPageForm.setDescription(addActionForm.getDescription());

                    var commandResult = ContentUtil.getHome().createContentPage(getUserVisitPK(request), createContentPageForm);
                    
                    if(commandResult.hasErrors()) {
                        setCommandResultAttribute(request, commandResult);
                        forwardKey = ForwardConstants.FORM;
                    } else {
                        forwardKey = ForwardConstants.DISPLAY;
                    }
                } else {
                    addActionForm.setContentCollectionName(contentCollectionName);
                    addActionForm.setContentSectionName(contentSectionName);
                    addActionForm.setParentContentSectionName(parentContentSectionName);
                    addActionForm.setSortOrder("1");
                    forwardKey = ForwardConstants.FORM;
                }
            }
        } catch (NamingException ne) {
            forwardKey = ForwardConstants.ERROR_500;
        }

        var customActionForward = new CustomActionForward(mapping.findForward(forwardKey));
        if(forwardKey.equals(ForwardConstants.FORM)) {
            request.setAttribute(AttributeConstants.CONTENT_COLLECTION_NAME, contentCollectionName);
            request.setAttribute(AttributeConstants.CONTENT_SECTION_NAME, contentSectionName);
            request.setAttribute(AttributeConstants.PARENT_CONTENT_SECTION_NAME, parentContentSectionName);
        } if(forwardKey.equals(ForwardConstants.DISPLAY)) {
            Map<String, String> parameters = new HashMap<>(3);
            
            parameters.put(ParameterConstants.CONTENT_COLLECTION_NAME, contentCollectionName);
            parameters.put(ParameterConstants.CONTENT_SECTION_NAME, contentSectionName);
            parameters.put(ParameterConstants.PARENT_CONTENT_SECTION_NAME, parentContentSectionName);
            customActionForward.setParameters(parameters);
        }
        
        return customActionForward;
    }
    
}