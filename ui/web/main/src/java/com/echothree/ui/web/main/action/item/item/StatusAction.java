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

package com.echothree.ui.web.main.action.item.item;

import com.echothree.control.user.item.common.ItemUtil;
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
    path = "/Item/Item/Status",
    mappingClass = SecureActionMapping.class,
    name = "ItemStatus",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Result", path = "/action/Item/Item/Result", redirect = true),
        @SproutForward(name = "Review", path = "/action/Item/Item/Review", redirect = true),
        @SproutForward(name = "Form", path = "/item/item/status.jsp")
    }
)
public class StatusAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        var actionForm = (StatusActionForm)form;
        var forwardKey = request.getParameter(ParameterConstants.FORWARD_KEY);
        var itemName = request.getParameter(ParameterConstants.ITEM_NAME);
        
        try {
            var commandForm = ItemUtil.getHome().getSetItemStatusForm();
            
            if(itemName == null)
                itemName = actionForm.getItemName();
            
            if(wasPost(request)) {
                commandForm.setItemName(itemName);
                commandForm.setItemStatusChoice(actionForm.getItemStatusChoice());

                var commandResult = ItemUtil.getHome().setItemStatus(getUserVisitPK(request), commandForm);
                
                if(commandResult.hasErrors()) {
                    setCommandResultAttribute(request, commandResult);
                    forwardKey = ForwardConstants.FORM;
                } else {
                    forwardKey = actionForm.getForwardKey();
                }
            } else {
                actionForm.setForwardKey(forwardKey);
                actionForm.setItemName(itemName);
                actionForm.setupItemStatusChoices();
                forwardKey = ForwardConstants.FORM;
            }
        } catch (NamingException ne) {
            forwardKey = ForwardConstants.ERROR_500;
        }

        var customActionForward = new CustomActionForward(mapping.findForward(forwardKey));
        if(forwardKey.equals(ForwardConstants.FORM)) {
            request.setAttribute(AttributeConstants.ITEM_NAME, itemName);
            request.setAttribute(AttributeConstants.FORWARD_KEY, actionForm.getForwardKey());
        } else if(forwardKey.equals(ForwardConstants.REVIEW)) {
            Map<String, String> parameters = new HashMap<>(1);
            
            parameters.put(ParameterConstants.ITEM_NAME, itemName);
            customActionForward.setParameters(parameters);
        }
        
        return customActionForward;
    }
    
}
