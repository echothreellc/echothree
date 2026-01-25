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
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.MainBaseDeleteAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.CommandResult;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

@SproutAction(
    path = "/Warehouse/Warehouse/Delete",
    mappingClass = SecureActionMapping.class,
    name = "WarehouseDelete",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Warehouse/Warehouse/Main", redirect = true),
        @SproutForward(name = "Form", path = "/warehouse/warehouse/delete.jsp")
    }
)
public class DeleteAction
        extends MainBaseDeleteAction<DeleteActionForm> {

    @Override
    public String getEntityTypeName(final DeleteActionForm actionForm) {
        return EntityTypes.Warehouse.name();
    }

    @Override
    public void setupParameters(final DeleteActionForm actionForm, final HttpServletRequest request) {
        actionForm.setWarehouseName(findParameter(request, ParameterConstants.WAREHOUSE_NAME, actionForm.getWarehouseName()));
    }

    @Override
    public void setupTransfer(final DeleteActionForm actionForm, final HttpServletRequest request)
            throws NamingException {
        var commandForm = WarehouseUtil.getHome().getGetWarehouseForm();

        commandForm.setWarehouseName(actionForm.getWarehouseName());

        var commandResult = WarehouseUtil.getHome().getWarehouse(getUserVisitPK(request), commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (GetWarehouseResult)executionResult.getResult();

        request.setAttribute(AttributeConstants.WAREHOUSE, result.getWarehouse());
    }

    @Override
    public CommandResult doDelete(final DeleteActionForm actionForm, final HttpServletRequest request)
            throws NamingException {
        var commandForm = WarehouseUtil.getHome().getDeleteWarehouseForm();

        commandForm.setWarehouseName(actionForm.getWarehouseName());

        return WarehouseUtil.getHome().deleteWarehouse(getUserVisitPK(request), commandForm);
    }
    
}