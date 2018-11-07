// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

package com.echothree.ui.web.main.action.wishlist.wishlisttypepriority;

import com.echothree.control.user.wishlist.common.WishlistUtil;
import com.echothree.control.user.wishlist.common.edit.WishlistTypePriorityDescriptionEdit;
import com.echothree.control.user.wishlist.common.form.EditWishlistTypePriorityDescriptionForm;
import com.echothree.control.user.wishlist.common.result.EditWishlistTypePriorityDescriptionResult;
import com.echothree.control.user.wishlist.common.spec.WishlistTypePriorityDescriptionSpec;
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
    path = "/Wishlist/WishlistTypePriority/DescriptionEdit",
    mappingClass = SecureActionMapping.class,
    name = "WishlistTypePriorityDescriptionEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Wishlist/WishlistTypePriority/Description", redirect = true),
        @SproutForward(name = "Form", path = "/wishlist/wishlisttypepriority/descriptionEdit.jsp")
    }
)
public class DescriptionEditAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
        String forwardKey = null;
        String wishlistTypeName = request.getParameter(ParameterConstants.WISHLIST_TYPE_NAME);
        String wishlistTypePriorityName = request.getParameter(ParameterConstants.WISHLIST_TYPE_PRIORITY_NAME);
        String languageIsoName = request.getParameter(ParameterConstants.LANGUAGE_ISO_NAME);
        
        try {
            if(forwardKey == null) {
                DescriptionEditActionForm actionForm = (DescriptionEditActionForm)form;
                EditWishlistTypePriorityDescriptionForm commandForm = WishlistUtil.getHome().getEditWishlistTypePriorityDescriptionForm();
                WishlistTypePriorityDescriptionSpec spec = WishlistUtil.getHome().getWishlistTypePriorityDescriptionSpec();
                
                if(wishlistTypeName == null)
                    wishlistTypeName = actionForm.getWishlistTypeName();
                if(wishlistTypePriorityName == null)
                    wishlistTypePriorityName = actionForm.getWishlistTypePriorityName();
                if(languageIsoName == null)
                    languageIsoName = actionForm.getLanguageIsoName();
                
                commandForm.setSpec(spec);
                spec.setWishlistTypeName(wishlistTypeName);
                spec.setWishlistTypePriorityName(wishlistTypePriorityName);
                spec.setLanguageIsoName(languageIsoName);
                
                if(wasPost(request)) {
                    WishlistTypePriorityDescriptionEdit edit = WishlistUtil.getHome().getWishlistTypePriorityDescriptionEdit();
                    
                    commandForm.setEditMode(EditMode.UPDATE);
                    commandForm.setEdit(edit);
                    edit.setDescription(actionForm.getDescription());
                    
                    CommandResult commandResult = WishlistUtil.getHome().editWishlistTypePriorityDescription(getUserVisitPK(request), commandForm);
                    
                    if(commandResult.hasErrors()) {
                        ExecutionResult executionResult = commandResult.getExecutionResult();
                        
                        if(executionResult != null) {
                            EditWishlistTypePriorityDescriptionResult result = (EditWishlistTypePriorityDescriptionResult)executionResult.getResult();
                            
                            request.setAttribute(AttributeConstants.ENTITY_LOCK, result.getEntityLock());
                        }
                        
                        setCommandResultAttribute(request, commandResult);
                        
                        forwardKey = ForwardConstants.FORM;
                    } else {
                        forwardKey = ForwardConstants.DISPLAY;
                    }
                } else {
                    commandForm.setEditMode(EditMode.LOCK);
                    
                    CommandResult commandResult = WishlistUtil.getHome().editWishlistTypePriorityDescription(getUserVisitPK(request), commandForm);
                    ExecutionResult executionResult = commandResult.getExecutionResult();
                    EditWishlistTypePriorityDescriptionResult result = (EditWishlistTypePriorityDescriptionResult)executionResult.getResult();
                    
                    if(result != null) {
                        WishlistTypePriorityDescriptionEdit edit = result.getEdit();
                        
                        if(edit != null) {
                            actionForm.setWishlistTypeName(wishlistTypeName);
                            actionForm.setWishlistTypePriorityName(wishlistTypePriorityName);
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
            request.setAttribute(AttributeConstants.WISHLIST_TYPE_NAME, wishlistTypeName);
            request.setAttribute(AttributeConstants.WISHLIST_TYPE_PRIORITY_NAME, wishlistTypePriorityName);
            request.setAttribute(AttributeConstants.LANGUAGE_ISO_NAME, languageIsoName);
        } else if(forwardKey.equals(ForwardConstants.DISPLAY)) {
            Map<String, String> parameters = new HashMap<>(2);
            
            parameters.put(ParameterConstants.WISHLIST_TYPE_NAME, wishlistTypeName);
            parameters.put(ParameterConstants.WISHLIST_TYPE_PRIORITY_NAME, wishlistTypePriorityName);
            customActionForward.setParameters(parameters);
        }
        
        return customActionForward;
    }
    
}