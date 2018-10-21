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
import com.echothree.control.user.wishlist.remote.edit.WishlistTypePriorityEdit;
import com.echothree.control.user.wishlist.remote.form.EditWishlistTypePriorityForm;
import com.echothree.control.user.wishlist.remote.result.EditWishlistTypePriorityResult;
import com.echothree.control.user.wishlist.remote.spec.WishlistTypePrioritySpec;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.remote.command.CommandResult;
import com.echothree.util.remote.command.EditMode;
import com.echothree.util.remote.command.ExecutionResult;
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
    path = "/Wishlist/WishlistTypePriority/Edit",
    mappingClass = SecureActionMapping.class,
    name = "WishlistTypePriorityEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Wishlist/WishlistTypePriority/Main", redirect = true),
        @SproutForward(name = "Form", path = "/wishlist/wishlisttypepriority/edit.jsp")
    }
)
public class EditAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
        String forwardKey = null;
        String wishlistTypeName = request.getParameter(ParameterConstants.WISHLIST_TYPE_NAME);
        String originalWishlistTypePriorityName = request.getParameter(ParameterConstants.ORIGINAL_WISHLIST_TYPE_PRIORITY_NAME);
        
        try {
            if(forwardKey == null) {
                EditActionForm actionForm = (EditActionForm)form;
                EditWishlistTypePriorityForm commandForm = WishlistUtil.getHome().getEditWishlistTypePriorityForm();
                WishlistTypePrioritySpec spec = WishlistUtil.getHome().getWishlistTypePrioritySpec();
                
                if(wishlistTypeName == null)
                    wishlistTypeName = actionForm.getWishlistTypeName();
                if(originalWishlistTypePriorityName == null)
                    originalWishlistTypePriorityName = actionForm.getOriginalWishlistTypePriorityName();
                
                commandForm.setSpec(spec);
                spec.setWishlistTypeName(wishlistTypeName);
                spec.setWishlistTypePriorityName(originalWishlistTypePriorityName);
                
                if(wasPost(request)) {
                    WishlistTypePriorityEdit edit = WishlistUtil.getHome().getWishlistTypePriorityEdit();
                    
                    commandForm.setEditMode(EditMode.UPDATE);
                    commandForm.setEdit(edit);
                    
                    edit.setWishlistTypePriorityName(actionForm.getWishlistTypePriorityName());
                    edit.setIsDefault(actionForm.getIsDefault().toString());
                    edit.setSortOrder(actionForm.getSortOrder());
                    edit.setDescription(actionForm.getDescription());
                    
                    CommandResult commandResult = WishlistUtil.getHome().editWishlistTypePriority(getUserVisitPK(request), commandForm);
                    
                    if(commandResult.hasErrors()) {
                        ExecutionResult executionResult = commandResult.getExecutionResult();
                        
                        if(executionResult != null) {
                            EditWishlistTypePriorityResult result = (EditWishlistTypePriorityResult)executionResult.getResult();
                            
                            request.setAttribute(AttributeConstants.ENTITY_LOCK, result.getEntityLock());
                        }
                        
                        setCommandResultAttribute(request, commandResult);
                        
                        forwardKey = ForwardConstants.FORM;
                    } else {
                        forwardKey = ForwardConstants.DISPLAY;
                    }
                } else {
                    commandForm.setEditMode(EditMode.LOCK);
                    
                    CommandResult commandResult = WishlistUtil.getHome().editWishlistTypePriority(getUserVisitPK(request), commandForm);
                    ExecutionResult executionResult = commandResult.getExecutionResult();
                    EditWishlistTypePriorityResult result = (EditWishlistTypePriorityResult)executionResult.getResult();
                    
                    if(result != null) {
                        WishlistTypePriorityEdit edit = result.getEdit();
                        
                        if(edit != null) {
                            actionForm.setWishlistTypeName(wishlistTypeName);
                            actionForm.setOriginalWishlistTypePriorityName(edit.getWishlistTypePriorityName());
                            actionForm.setWishlistTypePriorityName(edit.getWishlistTypePriorityName());
                            actionForm.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
                            actionForm.setSortOrder(edit.getSortOrder());
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
        } else if(forwardKey.equals(ForwardConstants.DISPLAY)) {
            Map<String, String> parameters = new HashMap<>(1);
            
            parameters.put(ParameterConstants.WISHLIST_TYPE_NAME, wishlistTypeName);
            customActionForward.setParameters(parameters);
        }
        
        return customActionForward;
    }
    
}