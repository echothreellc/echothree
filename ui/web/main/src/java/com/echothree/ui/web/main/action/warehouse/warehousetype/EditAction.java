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

package com.echothree.ui.web.main.action.warehouse.warehousetype;

import com.echothree.control.user.warehouse.common.WarehouseUtil;
import com.echothree.control.user.warehouse.common.edit.WarehouseTypeEdit;
import com.echothree.control.user.warehouse.common.form.EditWarehouseTypeForm;
import com.echothree.control.user.warehouse.common.result.EditWarehouseTypeResult;
import com.echothree.control.user.warehouse.common.spec.WarehouseTypeUniversalSpec;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.MainBaseEditAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.CommandResult;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

@SproutAction(
    path = "/Warehouse/WarehouseType/Edit",
    mappingClass = SecureActionMapping.class,
    name = "WarehouseTypeEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Warehouse/WarehouseType/Main", redirect = true),
        @SproutForward(name = "Form", path = "/warehouse/warehousetype/edit.jsp")
    }
)
public class EditAction
        extends MainBaseEditAction<EditActionForm, WarehouseTypeUniversalSpec, WarehouseTypeEdit, EditWarehouseTypeForm, EditWarehouseTypeResult> {
    
    @Override
    protected WarehouseTypeUniversalSpec getSpec(final HttpServletRequest request, final EditActionForm actionForm)
            throws NamingException {
        var spec = WarehouseUtil.getHome().getWarehouseTypeUniversalSpec();
        var originalWarehouseTypeName = request.getParameter(ParameterConstants.ORIGINAL_WAREHOUSE_TYPE_NAME);

        if(originalWarehouseTypeName == null) {
            originalWarehouseTypeName = actionForm.getOriginalWarehouseTypeName();
        }

        spec.setWarehouseTypeName(originalWarehouseTypeName);
        
        return spec;
    }
    
    @Override
    protected WarehouseTypeEdit getEdit(final HttpServletRequest request, final EditActionForm actionForm)
            throws NamingException {
        var edit = WarehouseUtil.getHome().getWarehouseTypeEdit();

        edit.setWarehouseTypeName(actionForm.getWarehouseTypeName());
        edit.setPriority(actionForm.getPriority());
        edit.setIsDefault(actionForm.getIsDefault().toString());
        edit.setSortOrder(actionForm.getSortOrder());
        edit.setDescription(actionForm.getDescription());

        return edit;
    }
    
    @Override
    protected EditWarehouseTypeForm getForm()
            throws NamingException {
        return WarehouseUtil.getHome().getEditWarehouseTypeForm();
    }
    
    @Override
    protected void setupActionForm(final HttpServletRequest request, final EditActionForm actionForm, final EditWarehouseTypeResult result,
            final WarehouseTypeUniversalSpec spec, final WarehouseTypeEdit edit) {
        actionForm.setOriginalWarehouseTypeName(spec.getWarehouseTypeName());
        actionForm.setWarehouseTypeName(edit.getWarehouseTypeName());
        actionForm.setPriority(edit.getPriority());
        actionForm.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        actionForm.setSortOrder(edit.getSortOrder());
        actionForm.setDescription(edit.getDescription());
    }
    
    @Override
    protected CommandResult doEdit(final HttpServletRequest request, final EditWarehouseTypeForm commandForm)
            throws Exception {
        var commandResult = WarehouseUtil.getHome().editWarehouseType(getUserVisitPK(request), commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (EditWarehouseTypeResult)executionResult.getResult();

        request.setAttribute(AttributeConstants.WAREHOUSE_TYPE, result.getWarehouseType());
        
        return commandResult;
    }
    
}