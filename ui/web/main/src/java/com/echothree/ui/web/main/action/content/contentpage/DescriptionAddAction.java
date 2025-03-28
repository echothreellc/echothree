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
    path = "/Content/ContentPage/DescriptionAdd",
    mappingClass = SecureActionMapping.class,
    name = "ContentPageDescriptionAdd",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Content/ContentPage/Description", redirect = true),
        @SproutForward(name = "Form", path = "/content/contentpage/descriptionAdd.jsp")
    }
)
public class DescriptionAddAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
        String forwardKey = null;
        var contentCollectionName = request.getParameter(ParameterConstants.CONTENT_COLLECTION_NAME);
        var contentSectionName = request.getParameter(ParameterConstants.CONTENT_SECTION_NAME);
        var contentPageName = request.getParameter(ParameterConstants.CONTENT_PAGE_NAME);
        var parentContentSectionName = request.getParameter(ParameterConstants.PARENT_CONTENT_SECTION_NAME);
        
        try {
            if(forwardKey == null) {
                var descriptionAddActionForm = (DescriptionAddActionForm)form;
                
                if(wasPost(request)) {
                    var createContentPageDescriptionForm = ContentUtil.getHome().getCreateContentPageDescriptionForm();
                    
                    if(contentCollectionName == null)
                        contentCollectionName = descriptionAddActionForm.getContentCollectionName();
                    if(contentSectionName == null)
                        contentSectionName = descriptionAddActionForm.getContentSectionName();
                    if(contentPageName == null)
                        contentPageName = descriptionAddActionForm.getContentPageName();
                    if(parentContentSectionName == null)
                        parentContentSectionName = descriptionAddActionForm.getParentContentSectionName();
                    
                    createContentPageDescriptionForm.setContentCollectionName(contentCollectionName);
                    createContentPageDescriptionForm.setContentSectionName(contentSectionName);
                    createContentPageDescriptionForm.setContentPageName(contentPageName);
                    createContentPageDescriptionForm.setLanguageIsoName(descriptionAddActionForm.getLanguageChoice());
                    createContentPageDescriptionForm.setDescription(descriptionAddActionForm.getDescription());

                    var commandResult = ContentUtil.getHome().createContentPageDescription(getUserVisitPK(request), createContentPageDescriptionForm);
                    
                    if(commandResult.hasErrors()) {
                        setCommandResultAttribute(request, commandResult);
                        forwardKey = ForwardConstants.FORM;
                    } else {
                        forwardKey = ForwardConstants.DISPLAY;
                    }
                } else {
                    descriptionAddActionForm.setContentCollectionName(contentCollectionName);
                    descriptionAddActionForm.setContentSectionName(contentSectionName);
                    descriptionAddActionForm.setContentPageName(contentPageName);
                    descriptionAddActionForm.setParentContentSectionName(parentContentSectionName);
                    forwardKey = ForwardConstants.FORM;
                }
            }
        } catch (NamingException ne) {
            forwardKey = ForwardConstants.ERROR_500;
        }

        var customActionForward = new CustomActionForward(mapping.findForward(forwardKey));
        if(forwardKey.equals(ForwardConstants.FORM)) {
            request.setAttribute("contentCollectionName", contentCollectionName);
            request.setAttribute("contentSectionName", contentSectionName);
            request.setAttribute("contentPageName", contentPageName);
            request.setAttribute("parentContentSectionName", parentContentSectionName);
        } else if(forwardKey.equals(ForwardConstants.DISPLAY)) {
            Map<String, String> parameters = new HashMap<>(3);
            
            parameters.put(ParameterConstants.CONTENT_COLLECTION_NAME, contentCollectionName);
            parameters.put(ParameterConstants.CONTENT_SECTION_NAME, contentSectionName);
            parameters.put(ParameterConstants.CONTENT_PAGE_NAME, contentPageName);
            if(parentContentSectionName != null)
                parameters.put(ParameterConstants.PARENT_CONTENT_SECTION_NAME, parentContentSectionName);
            customActionForward.setParameters(parameters);
        }
        
        return customActionForward;
    }
    
}