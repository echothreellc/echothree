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

package com.echothree.ui.web.main.action.content.contentcategory;

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
    path = "/Content/ContentCategory/DescriptionAdd",
    mappingClass = SecureActionMapping.class,
    name = "ContentCategoryDescriptionAdd",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Content/ContentCategory/Description", redirect = true),
        @SproutForward(name = "Form", path = "/content/contentcategory/descriptionAdd.jsp")
    }
)
public class DescriptionAddAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
        String forwardKey = null;
        var contentCollectionName = request.getParameter(ParameterConstants.CONTENT_COLLECTION_NAME);
        var contentCatalogName = request.getParameter(ParameterConstants.CONTENT_CATALOG_NAME);
        var contentCategoryName = request.getParameter(ParameterConstants.CONTENT_CATEGORY_NAME);
        var parentContentCategoryName = request.getParameter(ParameterConstants.PARENT_CONTENT_CATEGORY_NAME);
        
        try {
            if(forwardKey == null) {
                var actionForm = (DescriptionAddActionForm)form;
                
                if(wasPost(request)) {
                    var commandForm = ContentUtil.getHome().getCreateContentCategoryDescriptionForm();
                    
                    if(contentCollectionName == null)
                        contentCollectionName = actionForm.getContentCollectionName();
                    if(contentCatalogName == null)
                        contentCatalogName = actionForm.getContentCatalogName();
                    if(contentCategoryName == null)
                        contentCategoryName = actionForm.getContentCategoryName();
                    if(parentContentCategoryName == null)
                        parentContentCategoryName = actionForm.getParentContentCategoryName();
                    
                    commandForm.setContentCollectionName(contentCollectionName);
                    commandForm.setContentCatalogName(contentCatalogName);
                    commandForm.setContentCategoryName(contentCategoryName);
                    commandForm.setLanguageIsoName(actionForm.getLanguageChoice());
                    commandForm.setDescription(actionForm.getDescription());

                    var commandResult = ContentUtil.getHome().createContentCategoryDescription(getUserVisitPK(request), commandForm);
                    
                    if(commandResult.hasErrors()) {
                        setCommandResultAttribute(request, commandResult);
                        forwardKey = ForwardConstants.FORM;
                    } else {
                        forwardKey = ForwardConstants.DISPLAY;
                    }
                } else {
                    actionForm.setContentCollectionName(contentCollectionName);
                    actionForm.setContentCatalogName(contentCatalogName);
                    actionForm.setContentCategoryName(contentCategoryName);
                    actionForm.setParentContentCategoryName(parentContentCategoryName);
                    forwardKey = ForwardConstants.FORM;
                }
            }
        } catch (NamingException ne) {
            forwardKey = ForwardConstants.ERROR_500;
        }

        var customActionForward = new CustomActionForward(mapping.findForward(forwardKey));
        if(forwardKey.equals(ForwardConstants.FORM)) {
            request.setAttribute(AttributeConstants.CONTENT_COLLECTION_NAME, contentCollectionName);
            request.setAttribute(AttributeConstants.CONTENT_CATALOG_NAME, contentCatalogName);
            request.setAttribute(AttributeConstants.CONTENT_CATEGORY_NAME, contentCategoryName);
            request.setAttribute(AttributeConstants.PARENT_CONTENT_CATEGORY_NAME, parentContentCategoryName);
        } else if(forwardKey.equals(ForwardConstants.DISPLAY)) {
            Map<String, String> parameters = new HashMap<>(4);
            
            parameters.put(ParameterConstants.CONTENT_COLLECTION_NAME, contentCollectionName);
            parameters.put(ParameterConstants.CONTENT_CATALOG_NAME, contentCatalogName);
            parameters.put(ParameterConstants.CONTENT_CATEGORY_NAME, contentCategoryName);
            if(parentContentCategoryName != null)
                parameters.put(ParameterConstants.PARENT_CONTENT_CATEGORY_NAME, parentContentCategoryName);
            customActionForward.setParameters(parameters);
        }
        
        return customActionForward;
    }
    
}