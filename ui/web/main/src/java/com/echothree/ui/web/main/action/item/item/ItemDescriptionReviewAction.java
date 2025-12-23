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
import com.echothree.control.user.item.common.result.GetItemDescriptionResult;
import com.echothree.model.control.item.common.ItemOptions;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import java.util.HashSet;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/Item/Item/ItemDescriptionReview",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/item/item/itemDescriptionReview.jsp")
    }
)
public class ItemDescriptionReviewAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey = null;
        var itemName = request.getParameter(ParameterConstants.ITEM_NAME);
        var itemDescriptionTypeName = request.getParameter(ParameterConstants.ITEM_DESCRIPTION_TYPE_NAME);
        var languageIsoName = request.getParameter(ParameterConstants.LANGUAGE_ISO_NAME);
        var commandForm = ItemUtil.getHome().getGetItemDescriptionForm();

        commandForm.setItemDescriptionTypeName(itemDescriptionTypeName);
        commandForm.setItemName(itemName);
        commandForm.setLanguageIsoName(languageIsoName);

        Set<String> options = new HashSet<>();
        options.add(ItemOptions.ItemDescriptionIncludeClob);
        options.add(ItemOptions.ItemDescriptionIncludeTagScopes);
        commandForm.setOptions(options);

        var commandResult = ItemUtil.getHome().getItemDescription(getUserVisitPK(request), commandForm);
        if(!commandResult.hasErrors()) {
            var executionResult = commandResult.getExecutionResult();
            var result = (GetItemDescriptionResult)executionResult.getResult();
            var itemDescription = result.getItemDescription();

            if(itemDescription != null) {
                request.setAttribute(AttributeConstants.ITEM_DESCRIPTION, itemDescription);
                forwardKey = ForwardConstants.DISPLAY;
            }
        }

        return mapping.findForward(forwardKey == null ? ForwardConstants.ERROR_404 : forwardKey);
    }
    
}
