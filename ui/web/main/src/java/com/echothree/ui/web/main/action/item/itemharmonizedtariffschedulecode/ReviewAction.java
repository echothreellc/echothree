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

package com.echothree.ui.web.main.action.item.itemharmonizedtariffschedulecode;

import com.echothree.control.user.item.common.ItemUtil;
import com.echothree.control.user.item.common.result.GetItemHarmonizedTariffScheduleCodeResult;
import com.echothree.model.control.core.common.CoreOptions;
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
    path = "/Item/ItemHarmonizedTariffScheduleCode/Review",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/item/itemharmonizedtariffschedulecode/review.jsp")
    }
)
public class ReviewAction
        extends MainBaseAction<ActionForm> {

    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey = null;
        var commandForm = ItemUtil.getHome().getGetItemHarmonizedTariffScheduleCodeForm();

        commandForm.setItemName(request.getParameter(ParameterConstants.ITEM_NAME));
        commandForm.setCountryName(request.getParameter(ParameterConstants.COUNTRY_NAME));
        commandForm.setHarmonizedTariffScheduleCodeUseTypeName(request.getParameter(ParameterConstants.HARMONIZED_TARIFF_SCHEDULE_CODE_USE_TYPE_NAME));

        Set<String> options = new HashSet<>();
        options.add(ItemOptions.ItemHarmonizedTariffScheduleCodeIncludeEntityAttributeGroups);
        options.add(ItemOptions.ItemHarmonizedTariffScheduleCodeIncludeTagScopes);
        options.add(CoreOptions.EntityAttributeGroupIncludeEntityAttributes);
        options.add(CoreOptions.EntityAttributeIncludeValue);
        options.add(CoreOptions.EntityStringAttributeIncludeString);
        options.add(CoreOptions.EntityInstanceIncludeNames);
        commandForm.setOptions(options);

        var commandResult = ItemUtil.getHome().getItemHarmonizedTariffScheduleCode(getUserVisitPK(request), commandForm);
        if(!commandResult.hasErrors()) {
            var executionResult = commandResult.getExecutionResult();
            var result = (GetItemHarmonizedTariffScheduleCodeResult)executionResult.getResult();
            var itemHarmonizedTariffScheduleCode = result.getItemHarmonizedTariffScheduleCode();

            if(itemHarmonizedTariffScheduleCode != null) {
                saveToken(request); // Required for ItemHarmonizedTariffScheduleCodeIncludeTagScopes and tagScopes.jsp
                request.setAttribute(AttributeConstants.ITEM_HARMONIZED_TARIFF_SCHEDULE_CODE, itemHarmonizedTariffScheduleCode);
                forwardKey = ForwardConstants.DISPLAY;
            }
        }
        
        return mapping.findForward(forwardKey == null ? ForwardConstants.ERROR_404 : forwardKey);
    }
    
}
