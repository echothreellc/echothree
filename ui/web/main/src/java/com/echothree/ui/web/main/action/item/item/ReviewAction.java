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
import com.echothree.control.user.item.common.result.GetItemResult;
import com.echothree.model.control.comment.common.CommentOptions;
import com.echothree.model.control.core.common.CoreOptions;
import com.echothree.model.control.geo.common.GeoOptions;
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
    path = "/Item/Item/Review",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/item/item/review.jsp")
    }
)
public class ReviewAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey = null;
        var commandForm = ItemUtil.getHome().getGetItemForm();
        var itemName = request.getParameter(ParameterConstants.ITEM_NAME);
        var itemNameOrAlias = request.getParameter(ParameterConstants.ITEM_NAME_OR_ALIAS);

        commandForm.setItemName(itemName);
        commandForm.setItemNameOrAlias(itemNameOrAlias);

        Set<String> options = new HashSet<>();
        options.add(GeoOptions.CountryIncludeAliases);
        options.add(ItemOptions.ItemDescriptionIncludeString);
        options.add(ItemOptions.ItemDescriptionIncludeImageDescription);
        options.add(ItemOptions.ItemIncludeItemShippingTimes);
        options.add(ItemOptions.ItemIncludeItemAliases);
        options.add(ItemOptions.ItemIncludeItemPrices);
        options.add(ItemOptions.ItemIncludeItemUnitOfMeasureTypes);
        options.add(ItemOptions.ItemIncludeItemDescriptions);
        options.add(ItemOptions.ItemIncludeItemVolumes);
        options.add(ItemOptions.ItemIncludeItemWeights);
        options.add(ItemOptions.ItemIncludeOfferItems);
        options.add(ItemOptions.ItemIncludeVendorItems);
        options.add(ItemOptions.ItemIncludeItemCountryOfOrigins);
        options.add(ItemOptions.ItemIncludeItemHarmonizedTariffScheduleCodes);
        options.add(ItemOptions.ItemIncludeItemKitMembers);
        options.add(ItemOptions.ItemIncludeItemPackCheckRequirements);
        options.add(ItemOptions.ItemIncludeItemUnitCustomerTypeLimits);
        options.add(ItemOptions.ItemIncludeItemUnitLimits);
        options.add(ItemOptions.ItemIncludeItemUnitPriceLimits);
        options.add(ItemOptions.ItemIncludeCustomerComments);
        options.add(ItemOptions.ItemIncludeCustomerServiceComments);
        options.add(ItemOptions.ItemIncludePurchasingComments);
        options.add(ItemOptions.ItemIncludeCustomerRatings);
        options.add(ItemOptions.ItemIncludeEntityAttributeGroups);
        options.add(ItemOptions.ItemIncludeTagScopes);
        options.add(CommentOptions.CommentIncludeClob);
        options.add(CommentOptions.CommentIncludeWorkflowStep);
        options.add(CoreOptions.EntityAttributeGroupIncludeEntityAttributes);
        options.add(CoreOptions.EntityAttributeIncludeValue);
        options.add(CoreOptions.EntityStringAttributeIncludeString);
        options.add(CoreOptions.EntityInstanceIncludeNames);
        options.add(CoreOptions.EntityInstanceIncludeEntityAppearance);
        options.add(CoreOptions.AppearanceIncludeTextDecorations);
        options.add(CoreOptions.AppearanceIncludeTextTransformations);
        commandForm.setOptions(options);

        var commandResult = ItemUtil.getHome().getItem(getUserVisitPK(request), commandForm);
        if(!commandResult.hasErrors()) {
            var executionResult = commandResult.getExecutionResult();
            var result = (GetItemResult)executionResult.getResult();
            var item = result.getItem();

            if(item != null) {
                saveToken(request); // Required for ItemIncludeTagScopes and tagScopes.jsp
                request.setAttribute(AttributeConstants.ITEM, item);
                forwardKey = ForwardConstants.DISPLAY;
            }
        }
        
        return mapping.findForward(forwardKey == null ? ForwardConstants.ERROR_404 : forwardKey);
    }
    
}