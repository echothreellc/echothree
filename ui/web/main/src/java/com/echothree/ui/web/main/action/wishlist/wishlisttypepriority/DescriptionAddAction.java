// --------------------------------------------------------------------------------
// Copyright 2002-2023 Echo Three, LLC
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
import com.echothree.control.user.wishlist.common.form.CreateWishlistTypePriorityDescriptionForm;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.CommandResult;
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
    path = "/Wishlist/WishlistTypePriority/DescriptionAdd",
    mappingClass = SecureActionMapping.class,
    name = "WishlistTypePriorityDescriptionAdd",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Wishlist/WishlistTypePriority/Description", redirect = true),
        @SproutForward(name = "Form", path = "/wishlist/wishlisttypepriority/descriptionAdd.jsp")
    }
)
public class DescriptionAddAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
        String forwardKey = null;
        String wishlistTypeName = request.getParameter(ParameterConstants.WISHLIST_TYPE_NAME);
        String wishlistTypePriorityName = request.getParameter(ParameterConstants.WISHLIST_TYPE_PRIORITY_NAME);
        
        try {
            if(forwardKey == null) {
                DescriptionAddActionForm actionForm = (DescriptionAddActionForm)form;
                
                if(wasPost(request)) {
                    CreateWishlistTypePriorityDescriptionForm commandForm = WishlistUtil.getHome().getCreateWishlistTypePriorityDescriptionForm();
                    
                    if(wishlistTypeName == null)
                        wishlistTypeName = actionForm.getWishlistTypeName();
                    if(wishlistTypePriorityName == null)
                        wishlistTypePriorityName = actionForm.getWishlistTypePriorityName();
                    
                    commandForm.setWishlistTypeName(wishlistTypeName);
                    commandForm.setWishlistTypePriorityName(wishlistTypePriorityName);
                    commandForm.setLanguageIsoName(actionForm.getLanguageChoice());
                    commandForm.setDescription(actionForm.getDescription());
                    
                    CommandResult commandResult = WishlistUtil.getHome().createWishlistTypePriorityDescription(getUserVisitPK(request), commandForm);
                    
                    if(commandResult.hasErrors()) {
                        setCommandResultAttribute(request, commandResult);
                        forwardKey = ForwardConstants.FORM;
                    } else {
                        forwardKey = ForwardConstants.DISPLAY;
                    }
                } else {
                    actionForm.setWishlistTypeName(wishlistTypeName);
                    actionForm.setWishlistTypePriorityName(wishlistTypePriorityName);
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
        } else if(forwardKey.equals(ForwardConstants.DISPLAY)) {
            Map<String, String> parameters = new HashMap<>(2);
            
            parameters.put(ParameterConstants.WISHLIST_TYPE_NAME, wishlistTypeName);
            parameters.put(ParameterConstants.WISHLIST_TYPE_PRIORITY_NAME, wishlistTypePriorityName);
            customActionForward.setParameters(parameters);
        }
        
        return customActionForward;
    }
    
}