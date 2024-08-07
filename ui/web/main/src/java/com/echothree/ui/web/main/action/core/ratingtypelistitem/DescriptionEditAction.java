// --------------------------------------------------------------------------------
// Copyright 2002-2024 Echo Three, LLC
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

package com.echothree.ui.web.main.action.core.ratingtypelistitem;

import com.echothree.control.user.rating.common.RatingUtil;
import com.echothree.control.user.rating.common.edit.RatingTypeListItemDescriptionEdit;
import com.echothree.control.user.rating.common.form.EditRatingTypeListItemDescriptionForm;
import com.echothree.control.user.rating.common.result.EditRatingTypeListItemDescriptionResult;
import com.echothree.control.user.rating.common.spec.RatingTypeListItemDescriptionSpec;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.command.ExecutionResult;
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
    path = "/Core/RatingTypeListItem/DescriptionEdit",
    mappingClass = SecureActionMapping.class,
    name = "RatingTypeListItemDescriptionEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Core/RatingTypeListItem/Description", redirect = true),
        @SproutForward(name = "Form", path = "/core/ratingtypelistitem/descriptionEdit.jsp")
    }
)
public class DescriptionEditAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
        String forwardKey = null;
        String componentVendorName = request.getParameter(ParameterConstants.COMPONENT_VENDOR_NAME);
        String entityTypeName = request.getParameter(ParameterConstants.ENTITY_TYPE_NAME);
        String ratingTypeName = request.getParameter(ParameterConstants.RATING_TYPE_NAME);
        String ratingTypeListItemName = request.getParameter(ParameterConstants.RATING_TYPE_LIST_ITEM_NAME);
        String languageIsoName = request.getParameter(ParameterConstants.LANGUAGE_ISO_NAME);
        
        try {
            if(forwardKey == null) {
                DescriptionEditActionForm actionForm = (DescriptionEditActionForm)form;
                EditRatingTypeListItemDescriptionForm commandForm = RatingUtil.getHome().getEditRatingTypeListItemDescriptionForm();
                RatingTypeListItemDescriptionSpec spec = RatingUtil.getHome().getRatingTypeListItemDescriptionSpec();
                
                if(componentVendorName == null)
                    componentVendorName = actionForm.getComponentVendorName();
                if(entityTypeName == null)
                    entityTypeName = actionForm.getEntityTypeName();
                if(ratingTypeName == null)
                    ratingTypeName = actionForm.getRatingTypeName();
                if(ratingTypeListItemName == null)
                    ratingTypeListItemName = actionForm.getRatingTypeListItemName();
                if(languageIsoName == null)
                    languageIsoName = actionForm.getLanguageIsoName();
                
                commandForm.setSpec(spec);
                spec.setComponentVendorName(componentVendorName);
                spec.setEntityTypeName(entityTypeName);
                spec.setRatingTypeName(ratingTypeName);
                spec.setRatingTypeListItemName(ratingTypeListItemName);
                spec.setLanguageIsoName(languageIsoName);
                
                if(wasPost(request)) {
                    RatingTypeListItemDescriptionEdit edit = RatingUtil.getHome().getRatingTypeListItemDescriptionEdit();
                    
                    commandForm.setEditMode(EditMode.UPDATE);
                    commandForm.setEdit(edit);
                    edit.setDescription(actionForm.getDescription());
                    
                    CommandResult commandResult = RatingUtil.getHome().editRatingTypeListItemDescription(getUserVisitPK(request), commandForm);
                    
                    if(commandResult.hasErrors()) {
                        ExecutionResult executionResult = commandResult.getExecutionResult();
                        
                        if(executionResult != null) {
                            EditRatingTypeListItemDescriptionResult result = (EditRatingTypeListItemDescriptionResult)executionResult.getResult();
                            
                            request.setAttribute(AttributeConstants.ENTITY_LOCK, result.getEntityLock());
                        }
                        
                        setCommandResultAttribute(request, commandResult);
                        
                        forwardKey = ForwardConstants.FORM;
                    } else {
                        forwardKey = ForwardConstants.DISPLAY;
                    }
                } else {
                    commandForm.setEditMode(EditMode.LOCK);
                    
                    CommandResult commandResult = RatingUtil.getHome().editRatingTypeListItemDescription(getUserVisitPK(request), commandForm);
                    ExecutionResult executionResult = commandResult.getExecutionResult();
                    EditRatingTypeListItemDescriptionResult result = (EditRatingTypeListItemDescriptionResult)executionResult.getResult();
                    
                    if(result != null) {
                        RatingTypeListItemDescriptionEdit edit = result.getEdit();
                        
                        if(edit != null) {
                            actionForm.setComponentVendorName(componentVendorName);
                            actionForm.setEntityTypeName(entityTypeName);
                            actionForm.setRatingTypeName(ratingTypeName);
                            actionForm.setRatingTypeListItemName(ratingTypeListItemName);
                            actionForm.setLanguageIsoName(languageIsoName);
                            actionForm.setDescription(edit.getDescription());
                        }
                        
                        request.setAttribute(AttributeConstants.ENTITY_LOCK, result.getEntityLock());
                    }
                    
                    setCommandResultAttribute(request, commandResult);
                    
                    forwardKey = ForwardConstants.FORM;
                }
            }
        } catch (NamingException ne) {
            forwardKey = ForwardConstants.ERROR_500;
        }
        
        CustomActionForward customActionForward = new CustomActionForward(mapping.findForward(forwardKey));
        if(forwardKey.equals(ForwardConstants.FORM)) {
            request.setAttribute(AttributeConstants.COMPONENT_VENDOR_NAME, componentVendorName);
            request.setAttribute(AttributeConstants.ENTITY_TYPE_NAME, entityTypeName);
            request.setAttribute(AttributeConstants.RATING_TYPE_NAME, ratingTypeName);
            request.setAttribute(AttributeConstants.RATING_TYPE_LIST_ITEM_NAME, ratingTypeListItemName);
            request.setAttribute(AttributeConstants.LANGUAGE_ISO_NAME, languageIsoName);
        } else if(forwardKey.equals(ForwardConstants.DISPLAY)) {
            Map<String, String> parameters = new HashMap<>(4);
            
            parameters.put(ParameterConstants.COMPONENT_VENDOR_NAME, componentVendorName);
            parameters.put(ParameterConstants.ENTITY_TYPE_NAME, entityTypeName);
            parameters.put(ParameterConstants.RATING_TYPE_NAME, ratingTypeName);
            parameters.put(ParameterConstants.RATING_TYPE_LIST_ITEM_NAME, ratingTypeListItemName);
            customActionForward.setParameters(parameters);
        }
        
        return customActionForward;
    }
    
}