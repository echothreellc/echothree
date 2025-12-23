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

package com.echothree.ui.web.main.action.wishlist.wishlistpriority;

import com.echothree.control.user.wishlist.common.WishlistUtil;
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
    path = "/Wishlist/WishlistPriority/DescriptionAdd",
    mappingClass = SecureActionMapping.class,
    name = "WishlistPriorityDescriptionAdd",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Wishlist/WishlistPriority/Description", redirect = true),
        @SproutForward(name = "Form", path = "/wishlist/wishlistpriority/descriptionAdd.jsp")
    }
)
public class DescriptionAddAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
        String forwardKey = null;
        var wishlistTypeName = request.getParameter(ParameterConstants.WISHLIST_TYPE_NAME);
        var wishlistPriorityName = request.getParameter(ParameterConstants.WISHLIST_TYPE_PRIORITY_NAME);
        
        try {
            if(forwardKey == null) {
                var actionForm = (DescriptionAddActionForm)form;
                
                if(wasPost(request)) {
                    var commandForm = WishlistUtil.getHome().getCreateWishlistPriorityDescriptionForm();
                    
                    if(wishlistTypeName == null)
                        wishlistTypeName = actionForm.getWishlistTypeName();
                    if(wishlistPriorityName == null)
                        wishlistPriorityName = actionForm.getWishlistPriorityName();
                    
                    commandForm.setWishlistTypeName(wishlistTypeName);
                    commandForm.setWishlistPriorityName(wishlistPriorityName);
                    commandForm.setLanguageIsoName(actionForm.getLanguageChoice());
                    commandForm.setDescription(actionForm.getDescription());

                    var commandResult = WishlistUtil.getHome().createWishlistPriorityDescription(getUserVisitPK(request), commandForm);
                    
                    if(commandResult.hasErrors()) {
                        setCommandResultAttribute(request, commandResult);
                        forwardKey = ForwardConstants.FORM;
                    } else {
                        forwardKey = ForwardConstants.DISPLAY;
                    }
                } else {
                    actionForm.setWishlistTypeName(wishlistTypeName);
                    actionForm.setWishlistPriorityName(wishlistPriorityName);
                    forwardKey = ForwardConstants.FORM;
                }
            }
        } catch (NamingException ne) {
            forwardKey = ForwardConstants.ERROR_500;
        }

        var customActionForward = new CustomActionForward(mapping.findForward(forwardKey));
        if(forwardKey.equals(ForwardConstants.FORM)) {
            request.setAttribute(AttributeConstants.WISHLIST_TYPE_NAME, wishlistTypeName);
            request.setAttribute(AttributeConstants.WISHLIST_TYPE_PRIORITY_NAME, wishlistPriorityName);
        } else if(forwardKey.equals(ForwardConstants.DISPLAY)) {
            Map<String, String> parameters = new HashMap<>(2);
            
            parameters.put(ParameterConstants.WISHLIST_TYPE_NAME, wishlistTypeName);
            parameters.put(ParameterConstants.WISHLIST_TYPE_PRIORITY_NAME, wishlistPriorityName);
            customActionForward.setParameters(parameters);
        }
        
        return customActionForward;
    }
    
}