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

package com.echothree.ui.web.main.action.warehouse.warehouse;

import com.echothree.control.user.warehouse.common.WarehouseUtil;
import com.echothree.control.user.warehouse.common.result.GetWarehouseResult;
import com.echothree.model.control.contact.common.ContactOptions;
import com.echothree.model.control.core.common.CoreOptions;
import com.echothree.model.control.party.common.PartyOptions;
import com.echothree.model.control.warehouse.common.WarehouseOptions;
import com.echothree.model.data.warehouse.common.LocationConstants;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.string.ContactPostalAddressUtils;
import com.echothree.util.common.transfer.Limit;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/Warehouse/Warehouse/Review",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/warehouse/warehouse/review.jsp")
    }
)
public class ReviewAction
        extends MainBaseAction<ActionForm> {

    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey;
        var commandForm = WarehouseUtil.getHome().getGetWarehouseForm();

        commandForm.setWarehouseName(request.getParameter(ParameterConstants.WAREHOUSE_NAME));
        commandForm.setPartyName(request.getParameter(ParameterConstants.PARTY_NAME));

        Set<String> options = new HashSet<>();
        options.add(PartyOptions.PartyIncludePartyAliases);
        options.add(PartyOptions.PartyIncludePartyContactMechanisms);
        options.add(PartyOptions.PartyIncludePartyPrinterGroupUses);
        options.add(ContactOptions.PartyContactMechanismIncludePartyContactMechanismPurposes);
        options.add(ContactOptions.PartyContactMechanismIncludePartyContactMechanismRelationshipsByFromPartyContactMechanism);
        options.add(WarehouseOptions.WarehouseIncludeLocationsCount);
        options.add(WarehouseOptions.WarehouseIncludeLocations);
        options.add(WarehouseOptions.WarehouseIncludeEntityAttributeGroups);
        options.add(WarehouseOptions.WarehouseIncludeTagScopes);
        options.add(CoreOptions.EntityAttributeGroupIncludeEntityAttributes);
        options.add(CoreOptions.EntityAttributeIncludeValue);
        options.add(CoreOptions.EntityStringAttributeIncludeString);
        options.add(CoreOptions.EntityInstanceIncludeNames);
        options.add(CoreOptions.EntityInstanceIncludeEntityAppearance);
        options.add(CoreOptions.AppearanceIncludeTextDecorations);
        options.add(CoreOptions.AppearanceIncludeTextTransformations);
        commandForm.setOptions(ContactPostalAddressUtils.getInstance().addOptions(options));

        Map<String, Limit> limits = new HashMap<>();
        limits.put(LocationConstants.ENTITY_TYPE_NAME, new Limit("10"));
        commandForm.setLimits(limits);

        var commandResult = WarehouseUtil.getHome().getWarehouse(getUserVisitPK(request), commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (GetWarehouseResult)executionResult.getResult();
        var warehouse = result.getWarehouse();

        if(warehouse != null) {
            saveToken(request); // Required for WarehouseIncludeTagScopes and tagScopes.jsp
            request.setAttribute(AttributeConstants.WAREHOUSE, warehouse);
            forwardKey = ForwardConstants.DISPLAY;
        } else {
            forwardKey = ForwardConstants.ERROR_404;
        }

        return mapping.findForward(forwardKey);
    }

}
