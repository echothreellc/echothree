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
import com.echothree.control.user.content.common.result.EditContentPageAreaResult;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.EditMode;
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
    path = "/Content/ContentPageArea/Edit",
    mappingClass = SecureActionMapping.class,
    name = "ContentPageAreaEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Content/ContentPageArea/Main", redirect = true),
        @SproutForward(name = "Form", path = "/content/contentpagearea/edit.jsp")
    }
)
public class EditAction
        extends MainBaseAction<ActionForm> {

    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey = null;
        var contentCollectionName = request.getParameter(ParameterConstants.CONTENT_COLLECTION_NAME);
        var contentSectionName = request.getParameter(ParameterConstants.CONTENT_SECTION_NAME);
        var contentPageName = request.getParameter(ParameterConstants.CONTENT_PAGE_NAME);
        var sortOrder = request.getParameter(ParameterConstants.SORT_ORDER);
        var languageIsoName = request.getParameter(ParameterConstants.LANGUAGE_ISO_NAME);
        var parentContentSectionName = request.getParameter(ParameterConstants.PARENT_CONTENT_SECTION_NAME);

        try {
            if(forwardKey == null) {
                var actionForm = (EditActionForm)form;
                var commandForm = ContentUtil.getHome().getEditContentPageAreaForm();
                var spec = ContentUtil.getHome().getContentPageAreaSpec();
                EditContentPageAreaResult result = null;

                if(contentCollectionName == null) {
                    contentCollectionName = actionForm.getContentCollectionName();
                }
                if(contentSectionName == null) {
                    contentSectionName = actionForm.getContentSectionName();
                }
                if(contentPageName == null) {
                    contentPageName = actionForm.getContentPageName();
                }
                if(sortOrder == null) {
                    sortOrder = actionForm.getSortOrder();
                }
                if(languageIsoName == null) {
                    languageIsoName = actionForm.getLanguageIsoName();
                }
                if(parentContentSectionName == null) {
                    parentContentSectionName = actionForm.getParentContentSectionName();
                }
                
                commandForm.setSpec(spec);
                spec.setContentCollectionName(contentCollectionName);
                spec.setContentSectionName(contentSectionName);
                spec.setContentPageName(contentPageName);
                spec.setSortOrder(sortOrder);
                spec.setLanguageIsoName(languageIsoName);

                if(wasPost(request)) {
                    var wasCanceled = wasCanceled(request);

                    if(wasCanceled) {
                        commandForm.setEditMode(EditMode.ABANDON);
                    } else {
                        var edit = ContentUtil.getHome().getContentPageAreaEdit();

                        commandForm.setEditMode(EditMode.UPDATE);
                        commandForm.setEdit(edit);
                        edit.setMimeTypeName(actionForm.getMimeTypeChoice());
                        edit.setDescription(actionForm.getDescription());
                        edit.setContentPageAreaClob(actionForm.getContentPageAreaClob());
                        edit.setContentPageAreaUrl(actionForm.getContentPageAreaUrl());
                    }

                    var commandResult = ContentUtil.getHome().editContentPageArea(getUserVisitPK(request), commandForm);

                    if(commandResult.hasErrors() && !wasCanceled) {
                        var executionResult = commandResult.getExecutionResult();

                        if(executionResult != null) {
                            result = (EditContentPageAreaResult)executionResult.getResult();

                            request.setAttribute(AttributeConstants.ENTITY_LOCK, result.getEntityLock());
                        }

                        setCommandResultAttribute(request, commandResult);

                        forwardKey = ForwardConstants.FORM;
                    } else {
                        forwardKey = ForwardConstants.DISPLAY;
                    }
                } else {
                    commandForm.setEditMode(EditMode.LOCK);

                    var commandResult = ContentUtil.getHome().editContentPageArea(getUserVisitPK(request), commandForm);
                    var executionResult = commandResult.getExecutionResult();
                    result = (EditContentPageAreaResult)executionResult.getResult();

                    if(result != null) {
                        var edit = result.getEdit();

                        if(edit != null) {
                            actionForm.setContentCollectionName(contentCollectionName);
                            actionForm.setContentSectionName(contentSectionName);
                            actionForm.setContentPageName(contentPageName);
                            actionForm.setSortOrder(sortOrder);
                            actionForm.setLanguageIsoName(languageIsoName);
                            actionForm.setParentContentSectionName(parentContentSectionName);
                            actionForm.setMimeTypeChoice(edit.getMimeTypeName());
                            actionForm.setDescription(edit.getDescription());
                            actionForm.setContentPageAreaClob(edit.getContentPageAreaClob());
                            actionForm.setContentPageAreaUrl(edit.getContentPageAreaUrl());
                        }

                        request.setAttribute(AttributeConstants.ENTITY_LOCK, result.getEntityLock());
                    }

                    setCommandResultAttribute(request, commandResult);

                    forwardKey = ForwardConstants.FORM;
                }

                if(result != null) {
                    request.setAttribute(AttributeConstants.CONTENT_PAGE_AREA, result.getContentPageArea());
                }
            }
        } catch(NamingException ne) {
            forwardKey = ForwardConstants.ERROR_500;
        }

        var customActionForward = new CustomActionForward(mapping.findForward(forwardKey));
        if(forwardKey.equals(ForwardConstants.FORM)) {
            request.setAttribute(AttributeConstants.CONTENT_COLLECTION_NAME, contentCollectionName);
            request.setAttribute(AttributeConstants.CONTENT_SECTION_NAME, contentSectionName);
            request.setAttribute(AttributeConstants.CONTENT_PAGE_NAME, contentPageName);
            request.setAttribute(AttributeConstants.SORT_ORDER, sortOrder);
            request.setAttribute(AttributeConstants.LANGUAGE_ISO_NAME, languageIsoName);
            request.setAttribute(AttributeConstants.PARENT_CONTENT_SECTION_NAME, parentContentSectionName);
        } else if(forwardKey.equals(ForwardConstants.DISPLAY)) {
            Map<String, String> parameters = new HashMap<>(4);

            parameters.put(ParameterConstants.CONTENT_COLLECTION_NAME, contentCollectionName);
            parameters.put(ParameterConstants.CONTENT_SECTION_NAME, contentSectionName);
            parameters.put(ParameterConstants.CONTENT_PAGE_NAME, contentPageName);
            parameters.put(ParameterConstants.PARENT_CONTENT_SECTION_NAME, parentContentSectionName);
            customActionForward.setParameters(parameters);
        }

        return customActionForward;
    }
}