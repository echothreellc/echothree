// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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
import com.echothree.control.user.warehouse.common.edit.WarehouseEdit;
import com.echothree.control.user.warehouse.common.form.EditWarehouseForm;
import com.echothree.control.user.warehouse.common.result.EditWarehouseResult;
import com.echothree.control.user.warehouse.common.spec.WarehouseSpec;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.MainBaseEditAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.ExecutionResult;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

@SproutAction(
    path = "/Warehouse/Warehouse/WarehouseEdit",
    mappingClass = SecureActionMapping.class,
    name = "WarehouseEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Warehouse/Warehouse/Main", redirect = true),
        @SproutForward(name = "Form", path = "/warehouse/warehouse/warehouseEdit.jsp")
    }
)
public class WarehouseEditAction
        extends MainBaseEditAction<WarehouseEditActionForm, WarehouseSpec, WarehouseEdit, EditWarehouseForm, EditWarehouseResult> {
    
    @Override
    protected WarehouseSpec getSpec(HttpServletRequest request, WarehouseEditActionForm actionForm)
            throws NamingException {
        WarehouseSpec spec = WarehouseUtil.getHome().getWarehouseSpec();
        String originalWarehouseName = request.getParameter(ParameterConstants.ORIGINAL_WAREHOUSE_NAME);

        if(originalWarehouseName == null) {
            originalWarehouseName = actionForm.getOriginalWarehouseName();
        }

        spec.setWarehouseName(originalWarehouseName);
        
        return spec;
    }
    
    @Override
    protected WarehouseEdit getEdit(HttpServletRequest request, WarehouseEditActionForm actionForm)
            throws NamingException {
        WarehouseEdit edit = WarehouseUtil.getHome().getWarehouseEdit();

        edit.setWarehouseName(actionForm.getWarehouseName());
        edit.setName(actionForm.getName());
        edit.setPreferredLanguageIsoName(actionForm.getLanguageChoice());
        edit.setPreferredCurrencyIsoName(actionForm.getCurrencyChoice());
        edit.setPreferredJavaTimeZoneName(actionForm.getTimeZoneChoice());
        edit.setPreferredDateTimeFormatName(actionForm.getDateTimeFormatChoice());
        edit.setIsDefault(actionForm.getIsDefault().toString());
        edit.setSortOrder(actionForm.getSortOrder());
        edit.setInventoryMovePrinterGroupName(actionForm.getInventoryMovePrinterGroupChoice());
        edit.setPicklistPrinterGroupName(actionForm.getPicklistPrinterGroupChoice());
        edit.setPackingListPrinterGroupName(actionForm.getPackingListPrinterGroupChoice());
        edit.setShippingManifestPrinterGroupName(actionForm.getShippingManifestPrinterGroupChoice());

        return edit;
    }
    
    @Override
    protected EditWarehouseForm getForm()
            throws NamingException {
        return WarehouseUtil.getHome().getEditWarehouseForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, WarehouseEditActionForm actionForm, EditWarehouseResult result, WarehouseSpec spec, WarehouseEdit edit) {
        actionForm.setOriginalWarehouseName(spec.getWarehouseName());
        actionForm.setWarehouseName(edit.getWarehouseName());
        actionForm.setName(edit.getName());
        actionForm.setLanguageChoice(edit.getPreferredLanguageIsoName());
        actionForm.setCurrencyChoice(edit.getPreferredCurrencyIsoName());
        actionForm.setTimeZoneChoice(edit.getPreferredJavaTimeZoneName());
        actionForm.setDateTimeFormatChoice(edit.getPreferredDateTimeFormatName());
        actionForm.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        actionForm.setSortOrder(edit.getSortOrder());
        actionForm.setInventoryMovePrinterGroupChoice(edit.getInventoryMovePrinterGroupName());
        actionForm.setPicklistPrinterGroupChoice(edit.getPicklistPrinterGroupName());
        actionForm.setPackingListPrinterGroupChoice(edit.getPackingListPrinterGroupName());
        actionForm.setShippingManifestPrinterGroupChoice(edit.getShippingManifestPrinterGroupName());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditWarehouseForm commandForm)
            throws Exception {
        CommandResult commandResult = WarehouseUtil.getHome().editWarehouse(getUserVisitPK(request), commandForm);
        ExecutionResult executionResult = commandResult.getExecutionResult();
        EditWarehouseResult result = (EditWarehouseResult)executionResult.getResult();

        request.setAttribute(AttributeConstants.VENDOR, result.getWarehouse());
        
        return commandResult;
    }
    
}