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

package com.echothree.ui.web.main.action.inventory.inventorylocationgroup;

import com.echothree.control.user.inventory.common.InventoryUtil;
import com.echothree.control.user.inventory.common.result.GetInventoryLocationGroupResult;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.MainBaseDeleteAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.CommandResult;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

@SproutAction(
    path = "/Inventory/InventoryLocationGroup/Delete",
    mappingClass = SecureActionMapping.class,
    name = "InventoryLocationGroupDelete",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Inventory/InventoryLocationGroup/Main", redirect = true),
        @SproutForward(name = "Form", path = "/inventory/inventorylocationgroup/delete.jsp")
    }
)
public class DeleteAction
        extends MainBaseDeleteAction<DeleteActionForm> {
    
    @Override
    public String getEntityTypeName(final DeleteActionForm actionForm) {
        return EntityTypes.InventoryLocationGroup.name();
    }

    @Override
    public void setupParameters(final DeleteActionForm actionForm, final HttpServletRequest request) {
        actionForm.setWarehouseName(findParameter(request, ParameterConstants.WAREHOUSE_NAME, actionForm.getWarehouseName()));
        actionForm.setInventoryLocationGroupName(findParameter(request, ParameterConstants.INVENTORY_LOCATION_GROUP_NAME, actionForm.getInventoryLocationGroupName()));
    }

    @Override
    public void setupTransfer(final DeleteActionForm actionForm, final HttpServletRequest request)
            throws NamingException {
        var commandForm = InventoryUtil.getHome().getGetInventoryLocationGroupForm();

        commandForm.setWarehouseName(actionForm.getWarehouseName());
        commandForm.setInventoryLocationGroupName(actionForm.getInventoryLocationGroupName());

        var commandResult = InventoryUtil.getHome().getInventoryLocationGroup(getUserVisitPK(request), commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (GetInventoryLocationGroupResult)executionResult.getResult();

        request.setAttribute(AttributeConstants.INVENTORY_LOCATION_GROUP, result.getInventoryLocationGroup());
    }

    @Override
    public CommandResult doDelete(final DeleteActionForm actionForm, final HttpServletRequest request)
            throws NamingException {
        var commandForm = InventoryUtil.getHome().getDeleteInventoryLocationGroupForm();

        commandForm.setWarehouseName(actionForm.getWarehouseName());
        commandForm.setInventoryLocationGroupName(actionForm.getInventoryLocationGroupName());

        return InventoryUtil.getHome().deleteInventoryLocationGroup(getUserVisitPK(request), commandForm);
    }

    @Override
    public void setupForwardParameters(final DeleteActionForm actionForm, final Map<String, String> parameters) {
        parameters.put(ParameterConstants.WAREHOUSE_NAME, actionForm.getWarehouseName());
    }
    
}